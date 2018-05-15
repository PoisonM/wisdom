package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ShopUserRelationMapper;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopUserRelationServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 13:54
 * @since JDK 1.8
 */
@Service("shopUserRelationService")
public class ShopUserRelationServiceImpl implements ShopUserRelationService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserRelationMapper shopUserRelationMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public String isMember(String userId) {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null || StringUtils.isBlank(userId)) {
            throw new ServiceException("isMember方法从redis获取sysClerkDTO为空");
        }
        logger.info("isMember方法传入的参数userId={}，sysShopId={}", userId, sysClerkDTO.getSysShopId());

        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();
        c.andSysUserIdEqualTo(userId);
        c.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
        List<ShopUserRelationDTO> list = shopUserRelationMapper.selectByCriteria(criteria);
        ShopUserRelationDTO shopUserRelationDTO = null;
        if (CollectionUtils.isEmpty(list)) {
            logger.info("list集合为空");
            return null;
        }

        shopUserRelationDTO = list.get(0);
        return shopUserRelationDTO.getStatus();
    }

    @Override
    public List<ShopUserRelationDTO> getShopListByCondition(ShopUserRelationDTO shopUserRelationDTO) {

        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserRelationDTO.getSysBossId())) {
            logger.info("getShopListByCondition方法传入的参数bossId={}", shopUserRelationDTO.getSysBossId());
            c.andSysBossIdEqualTo(shopUserRelationDTO.getSysBossId());
        }

        if (StringUtils.isNotBlank(shopUserRelationDTO.getSysUserId())) {
            c.andSysUserIdEqualTo(shopUserRelationDTO.getSysUserId());
        }

        if (StringUtils.isNotBlank(shopUserRelationDTO.getShopId())) {
            c.andShopIdEqualTo(shopUserRelationDTO.getShopId());
        }

        List<ShopUserRelationDTO> shopUserRelations = shopUserRelationMapper.selectByCriteria(criteria);
        return shopUserRelations;
    }

    @Override
    public ResponseDTO<String> userBinding(String openId, String shopId) {
        ResponseDTO responseDTO = new ResponseDTO();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        userInfoDTO.setSource("beauty");
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        if (CommonUtils.objectIsEmpty(userInfoDTOS)) {
            logger.error("根据openId查询出来的用户记录为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("根据openId查询出来的用户记录为空");
            return responseDTO;
        }
        //根据openId查询用户记录
        UserInfoDTO dto = userInfoDTOS.get(0);

        ShopUserRelationDTO shopUserRelationDTO = new ShopUserRelationDTO();
        shopUserRelationDTO.setSysUserId(dto.getId());
        shopUserRelationDTO.setShopId(shopId);
        List<ShopUserRelationDTO> shopListByCondition = getShopListByCondition(shopUserRelationDTO);
        //没有查出绑定关系，说明是未绑定状态
        if (CommonUtils.objectIsEmpty(shopListByCondition)) {
            logger.error("查询用户绑定关系为空");
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData("N");
            return responseDTO;
        }
        shopUserRelationDTO = shopListByCondition.get(0);
        //status为0  为绑定关系
        if (CommonCodeEnum.SUCCESS.getCode().equals(shopUserRelationDTO.getStatus())) {
            responseDTO.setResponseData("N");
        } else {

            responseDTO.setResponseData("Y");
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }
}
