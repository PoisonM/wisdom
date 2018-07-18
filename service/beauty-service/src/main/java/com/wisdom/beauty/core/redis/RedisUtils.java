package com.wisdom.beauty.core.redis;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.enums.ImageEnum;
import com.wisdom.beauty.api.enums.LoginEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.extDto.ShopUserLoginDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: redisUtils
 *
 * @author: 赵得良
 * Date:     2018/4/4 0004 11:03
 * Description: B端redis帮助类
 */
@Service("redisUtils")
public class RedisUtils {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopProjectService shopProjectService;

    /**
     * 预约详情缓存时常，30天
     */
    private int appointCacheSeconds = 1296000;

    @Resource
    private ShopAppointmentService appointmentService;

    @Resource
    private ShopUserRelationService shopUserRelationService;

    @Resource
    private UserServiceClient userServiceClient;

    @Value("${test.msg}")
    private String msg;


    /**
     * 预约详情缓存时常，10天
     */
    private int projectInfoCacheSeconds = 10 * 24 * 60 * 60;

    /**
     * 保存预约详情
     * @param shopAppointServiceDTO
     */
    public void saveShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO) {
        logger.info("保存预约详情到redis，预约详情为{}",shopAppointServiceDTO);
        if (shopAppointServiceDTO == null || StringUtils.isBlank(shopAppointServiceDTO.getId())) {
            logger.error("保存预约详情到redis传入参数异常，{}","shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
            return;
        }

        //保存预约信息
        JedisUtils.setObject(shopAppointServiceDTO.getId(),shopAppointServiceDTO,appointCacheSeconds);
    }

    /**
     * 获取用户的预约详情
     * @param appointmentId
     */
    public ShopAppointServiceDTO getShopAppointInfoFromRedis(String appointmentId) {
        logger.info("获取用户的预约详情传入参数={}", "appointmentId = [" + appointmentId + "]");

        ShopAppointServiceDTO shopAppointServiceDTO = (ShopAppointServiceDTO) JedisUtils.getObject(appointmentId);

        //redis中没有查出数据，再次缓存到redis中
        if (null == shopAppointServiceDTO) {
            shopAppointServiceDTO = new ShopAppointServiceDTO();
            shopAppointServiceDTO.setId(appointmentId);
            shopAppointServiceDTO = appointmentService.getShopAppointService(shopAppointServiceDTO);
            if (null != shopAppointServiceDTO) {
                saveShopAppointInfoToRedis(shopAppointServiceDTO);
            }
        }

        return shopAppointServiceDTO;
    }

    /**
     * 更新预约详情
     * @param shopAppointServiceDTO
     */
    public void updateShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO) {
        logger.info("更新预约详情传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
        saveShopAppointInfoToRedis(shopAppointServiceDTO);
    }

    /**
     * 根据项目主键查询项目信息
     */
    public ShopProjectInfoDTO getShopProjectInfoFromRedis(String projectInfoId) {
        ShopProjectInfoDTO shopProjectInfoDTO = (ShopProjectInfoDTO) JedisUtils.getObject(projectInfoId);

        if (null == shopProjectInfoDTO) {
            shopProjectInfoDTO = new ShopProjectInfoDTO();
            shopProjectInfoDTO.setId(projectInfoId);
            ExtShopProjectInfoDTO extShopProjectInfoDTO=(ExtShopProjectInfoDTO)shopProjectInfoDTO;
            List<ShopProjectInfoDTO> projectList = shopProjectService.getShopCourseProjectList(extShopProjectInfoDTO);
            if (CommonUtils.objectIsEmpty(projectList)) {
                logger.error("根据项目主键查询项目信息为空");
                throw new ServiceException("根据项目主键查询项目信息为空");
            }
            shopProjectInfoDTO = projectList.get(0);
            //保存到redis
            saveShopProjectInfoToRedis(shopProjectInfoDTO);
            return shopProjectInfoDTO;
        }

        return shopProjectInfoDTO;
    }

    /**
     * 缓存项目信息
     *
     * @param shopProjectInfoDTO
     */
    public void saveShopProjectInfoToRedis(ShopProjectInfoDTO shopProjectInfoDTO) {
        JedisUtils.setObject(shopProjectInfoDTO.getId(), shopProjectInfoDTO, projectInfoCacheSeconds);
    }

    /**
     * 获取用户当前登陆的店铺信息
     */
    public ShopUserLoginDTO getUserLoginShop(String sysUserId) {

        logger.info("获取用户当前登陆的店铺信息传入参数={}", "sysUserId = [" + sysUserId + "]");

        ShopUserLoginDTO userLoginDTO = (ShopUserLoginDTO) JedisUtils.getObject("shop_" + sysUserId);

        //获取用户登陆信息
        if (null == userLoginDTO) {
            ShopUserRelationDTO shopUserRelationDTO = new ShopUserRelationDTO();
            shopUserRelationDTO.setSysUserId(sysUserId);
            List<ShopUserRelationDTO> shopListByCondition = shopUserRelationService.getShopListByCondition(shopUserRelationDTO);
            //不为空，初始化关联一个店铺
            if (CommonUtils.objectIsNotEmpty(shopListByCondition)) {
                ShopUserRelationDTO relationDTO = shopListByCondition.get(0);
                ShopUserLoginDTO loginDTO = new ShopUserLoginDTO();
                loginDTO.setSysShopId(relationDTO.getSysShopId());
                loginDTO.setSysShopName(relationDTO.getSysShopName());
                loginDTO.setSysUserId(sysUserId);
                loginDTO.setSysShopPhoto(relationDTO.getShopPhoto());
                UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(sysUserId);
                loginDTO.setPhone(userInfoDTO.getMobile());
                JedisUtils.setObject("shop_" + sysUserId, loginDTO, appointCacheSeconds);
                return loginDTO;
            }else{
                ShopUserLoginDTO loginDTO = new ShopUserLoginDTO();
                UserInfoDTO userInfo = UserUtils.getUserInfo();
                loginDTO.setSysUserId(sysUserId);
                loginDTO.setSysShopPhoto(userInfo.getPhoto()==null? ImageEnum.USER_HEAD.getDesc():userInfo.getPhoto());
                loginDTO.setBindingDesc("请扫描店铺二维码进行绑定");
                return loginDTO;
            }
        }
        return userLoginDTO;
    }

    /**
     * 更改用户当前登陆的店铺信息
     */
    public ShopUserLoginDTO updateUserLoginShop(String sysUserId, String sysShopId) {

        logger.info("获取用户当前登陆的店铺信息传入参数={}", "sysUserId = [" + sysUserId + "]");

        ShopUserLoginDTO userLoginDTO = (ShopUserLoginDTO) JedisUtils.getObject("shop_" + sysUserId);

        ShopUserRelationDTO shopUserRelationDTO = new ShopUserRelationDTO();
        shopUserRelationDTO.setSysUserId(sysUserId);
        List<ShopUserRelationDTO> shopListByCondition = shopUserRelationService.getShopListByCondition(shopUserRelationDTO);

        if (CommonUtils.objectIsNotEmpty(shopListByCondition)) {
            for (ShopUserRelationDTO relationDTO : shopListByCondition) {
                if (relationDTO.getSysShopId().equalsIgnoreCase(sysShopId)) {
                    ShopUserLoginDTO loginDTO = new ShopUserLoginDTO();
                    loginDTO.setSysShopId(relationDTO.getSysShopId());
                    loginDTO.setSysShopName(relationDTO.getSysShopName());
                    loginDTO.setSysUserId(sysUserId);
                    loginDTO.setSysShopPhoto(relationDTO.getShopPhoto());
                    UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(sysUserId);
                    loginDTO.setPhone(userInfoDTO.getMobile());
                    JedisUtils.setObject("shop_" + sysUserId, loginDTO, appointCacheSeconds);
                }
            }
        }
        return userLoginDTO;
    }

    /**
     * 根据clerkId获取店员信息
     */
    public SysClerkDTO getSysClerkDTO(String sysClerkId) {

        logger.info("从redis中获取店员的相关信息传入参数={}", "sysClerkId = [" + sysClerkId + "]");

        Object object = JedisUtils.getObject(sysClerkId);

        if (CommonUtils.objectIsEmpty(object)) {

            logger.info("redis中未获取到店员相关信息传入参数={}", sysClerkId);
            List<SysClerkDTO> clerkInfoByClerkId = userServiceClient.getClerkInfoByClerkId(sysClerkId);
            if (CommonUtils.objectIsNotEmpty(clerkInfoByClerkId)) {
                JedisUtils.setObject(sysClerkId, clerkInfoByClerkId.get(0), appointCacheSeconds);
                return clerkInfoByClerkId.get(0);
            }
        }

        return (SysClerkDTO) object;
    }

    public String getShopId() {
        String sysShopId = null;
        //pad端
        if (null != UserUtils.getClerkInfo()) {
            System.out.println("pad端登陆");
            SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
            sysShopId = clerkInfo.getSysShopId();
        }
        //用户端
        if (null != UserUtils.getUserInfo()) {
            System.out.println("用户端登陆");
            sysShopId = getUserLoginShop(UserUtils.getUserInfo().getId()).getSysShopId();
        }
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (null != bossInfo) {
            sysShopId = bossInfo.getCurrentShopId();
            if(StringUtils.isBlank(sysShopId)){
                Gson gson = new Gson();
                String logintoken = UserUtils.getUserToken(LoginEnum.BOSS);
                bossInfo = userServiceClient.getBossInfo(bossInfo);
                String bossInfoStr = gson.toJson(bossInfo);
                JedisUtils.set(logintoken,bossInfoStr, ConfigConstant.logintokenPeriod);
                sysShopId = bossInfo.getCurrentShopId();
            }
            if(StringUtils.isBlank(sysShopId)){
                logger.error("沒有查詢老闆,bossInfoId={}的店鋪信息"+bossInfo.getId());
                throw new RuntimeException("沒有查詢老闆"+"bossInfoId"+bossInfo+"的店鋪信息");
            }
        }
        return sysShopId;
    }

    public String getBossCode() {
        String sysBossCode = null;
        //pad端
        if (null != UserUtils.getClerkInfo()) {
            System.out.println("pad端登陆");
            SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
            sysBossCode = clerkInfo.getSysBossCode();
        }
        if (null != UserUtils.getBossInfo()) {
            sysBossCode = UserUtils.getBossInfo().getSysBossCode();
        }
        return sysBossCode;
    }
}
