package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ExtSysShopMapper;
import com.wisdom.beauty.core.mapper.ShopUserRelationMapper;
import com.wisdom.beauty.core.mapper.SysShopMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private ExtSysShopMapper extSysShopMapper;

    @Autowired
    private SysShopMapper sysShopMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private MongoUtils mongoUtils;

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

    /**
     * 用户绑定会员
     *
     * @param shopUserRelationDTO
     * @return
     */
    @Override
    public int saveUserShopRelation(ShopUserRelationDTO shopUserRelationDTO) {
        if (null == shopUserRelationDTO) {
            logger.error("用户绑定会员传入参数为空，{}", "shopUserRelationDTO = [" + shopUserRelationDTO + "]");
            return 0;
        }
        List<ShopUserRelationDTO> shopListByCondition = getShopListByCondition(shopUserRelationDTO);
        //之前没有绑定过
        if (CommonUtils.objectIsEmpty(shopListByCondition)) {
            shopUserRelationDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
            shopUserRelationDTO.setCreateDate(new Date());
            shopUserRelationDTO.setId(IdGen.uuid());
            int i = shopUserRelationMapper.insertSelective(shopUserRelationDTO);
            return i;
        }
        //之前绑定过，更新状态
        else {
            ShopUserRelationDTO relationDTO = shopListByCondition.get(0);
            relationDTO.setStatus(shopUserRelationDTO.getStatus());
            int i = shopUserRelationMapper.updateByPrimaryKeySelective(relationDTO);
            return i;
        }
    }

    @Override
    public List<ShopUserRelationDTO> getShopListByCondition(ShopUserRelationDTO shopUserRelationDTO) {

        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserRelationDTO.getSysBossCode())) {
            logger.info("getShopListByCondition方法传入的参数bossId={}", shopUserRelationDTO.getSysBossCode());
            c.andSysBossCodeEqualTo(shopUserRelationDTO.getSysBossCode());
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
            responseDTO.setResponseData(CommonCodeEnum.Y.getCode());
            return responseDTO;
        }
        shopUserRelationDTO = shopListByCondition.get(0);
        //status为0  为绑定关系
        if (CommonCodeEnum.Y.getCode().equals(shopUserRelationDTO.getStatus())) {
            responseDTO.setResponseData(CommonCodeEnum.Y.getCode());
        } else {

            responseDTO.setResponseData(CommonCodeEnum.N.getCode());
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 查询美容院信息
     */
    @Override
    public List<ExtSysShopDTO> getBossShopInfo(ExtSysShopDTO extSysShopDTO) {
        if (CommonUtils.objectIsEmpty(extSysShopDTO)) {
            logger.error("查询美容院信息为空{}", "extSysShopDTO = [" + extSysShopDTO + "]");
            return null;
        }
        List<ExtSysShopDTO> extSysShopDTOS = extSysShopMapper.selectBossShopInfo(extSysShopDTO);
        if (CommonUtils.objectIsNotEmpty(extSysShopDTO)) {
            for (ExtSysShopDTO dto : extSysShopDTOS) {
                dto.setImageList(mongoUtils.getImageUrl(dto.getId()));
            }
        }
        return extSysShopDTOS;
    }

    /**
     * 更新店铺信息
     * @param extSysShopDTO
     * @return
     */
    @Override
    public int updateShopInfo(ExtSysShopDTO extSysShopDTO) {
        if (null == extSysShopDTO || StringUtils.isBlank(extSysShopDTO.getId())) {
            logger.error("更新店铺信息传入信息有误，请核查，{}", "extSysShopDTO = [" + extSysShopDTO + "]");
            return 0;
        }
        if (null != extSysShopDTO.getImageList()) {
            String o = (String) extSysShopDTO.getImageList().get(0);
            extSysShopDTO.setShopImageUrl(o);
        }

        int update = sysShopMapper.updateByPrimaryKeySelective(extSysShopDTO);
        mongoUtils.saveImageUrl(extSysShopDTO.getImageList(), extSysShopDTO.getId());
        return update;
    }
}
