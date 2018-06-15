package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ExtSysShopMapper;
import com.wisdom.beauty.core.mapper.ShopUserRelationMapper;
import com.wisdom.beauty.core.mapper.SysShopMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopCustomerArchivesService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.JedisUtils;
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
    private ShopCustomerArchivesService shopCustomerArchivesService;

    @Autowired
    private SysShopMapper sysShopMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ShopUserRelationService shopUserRelationService;

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
            //默认返回未绑定的状态
            return "1";
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
            logger.error("用户绑定会员传入参数为空");
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
    public ResponseDTO<String> userBinding(String openId, String shopId ,String userId) {
        //查询用户是否为建档用户
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysUserId(userId);
        shopUserArchivesDTO.setSysShopId(shopId);
        List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
        if(CommonUtils.objectIsEmpty(shopUserArchivesInfo)){
            logger.info("openId={},非建档用户",openId);
            JedisUtils.set(shopId+"_"+userId,"notArchives", ConfigConstant.logintokenPeriod);
        }else{
            //判断是否本人操作(userId和openId查出唯一条数据)
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
            if(userInfoDTOS.size()>1){
                logger.info("非当前用户操作，或此用户有多个微信,多条数据证明已存用户又扫了一次绑定二维码，openId={}",openId);
                JedisUtils.set(shopId+"_"+userId,"otherUser", ConfigConstant.logintokenPeriod);
            }else{
                //查询此用户与本店的绑定关系
                ShopUserRelationDTO shopUserRelationDTO = new ShopUserRelationDTO();
                shopUserRelationDTO.setSysUserId(userId);
                shopUserRelationDTO.setShopId(shopId);
                List<ShopUserRelationDTO> shopListByCondition = getShopListByCondition(shopUserRelationDTO);
                if(CommonUtils.objectIsEmpty(shopListByCondition)){
                    logger.info("此用户没有绑定过店铺，创建绑定关系");
                    shopUserRelationDTO.setStatus(CommonCodeEnum.BINDING.getCode());
                    shopUserRelationService.saveUserShopRelation(shopUserRelationDTO);
                    JedisUtils.set(shopId+"_"+userId,"binding", ConfigConstant.logintokenPeriod);
                }
            }
        }
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
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
            String o =  extSysShopDTO.getShopImageUrl();
            extSysShopDTO.setShopImageUrl(o);
        }

        int update = sysShopMapper.updateByPrimaryKeySelective(extSysShopDTO);
        mongoUtils.updateImageUrl(extSysShopDTO.getImageList(), extSysShopDTO.getId());
        return update;
    }
}
