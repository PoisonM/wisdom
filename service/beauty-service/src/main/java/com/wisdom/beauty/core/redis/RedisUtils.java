package com.wisdom.beauty.core.redis;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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
        if (shopAppointServiceDTO == null || StringUtils.isBlank(shopAppointServiceDTO.getId())
                ||StringUtils.isBlank(shopAppointServiceDTO.getSysShopId()) ||
                StringUtils.isBlank((shopAppointServiceDTO.getSysClerkId()))) {
            logger.error("保存预约详情到redis传入参数异常，{}","shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
            return;
        }

        //保存预约信息
        JedisUtils.setObject(shopAppointServiceDTO.getId(),shopAppointServiceDTO,appointCacheSeconds);

        //保存美容师与预约的关系 格式 zadd shopId_clerkId createDate appointmentId
        String key = new StringBuffer(shopAppointServiceDTO.getSysShopId()).append("_").
                append(shopAppointServiceDTO.getSysClerkId()).toString();
        double score = Double.parseDouble(DateUtils.DateToStr(shopAppointServiceDTO.getCreateDate(),"datetimesec"));
        String member = shopAppointServiceDTO.getId();
        JedisUtils.zadd(key,score,member,appointCacheSeconds);
    }

    /**
     * 获取用户的预约详情
     * @param appointmentId
     */
    public ShopAppointServiceDTO getShopAppointInfoFromRedis(String appointmentId) {
        logger.info("获取用户的预约详情传入参数={}", "appointmentId = [" + appointmentId + "]");
        return (ShopAppointServiceDTO) JedisUtils.getObject(appointmentId);
    }

    /**
     * 根据分数过滤与某个美容师相关的预约信息
     * 如：ZRANGEBYSCORE shopId_clerkId (20180000000000 20190000000000
     * @param shopIdClerkId
     * @param min
     * @param max
     * @return
     */
    public Set<String> getAppointmentIdByShopClerk(String shopIdClerkId,String min, String max ){
        logger.info("根据分数过滤与某个美容师相关的预约信息传入参数={}", "shopIdClerkId = [" + shopIdClerkId + "], min = [" + min + "], max = [" + max + "]");
        Set<String> set = JedisUtils.zRangeByScore(shopIdClerkId, min, max);
        return set;
    }

    /**
     * 更新预约详情
     * @param shopAppointServiceDTO
     */
    public void updateShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO) {
        logger.info("更新预约详情传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
        saveShopAppointInfoToRedis(shopAppointServiceDTO);
    }

    public String getShopIdClerkIdKey(String shopId, String clerkId) {
        return new StringBuffer(shopId).append("_").append(clerkId).toString();
    }

    /**
     * 根据项目主键查询项目信息
     */
    public ShopProjectInfoDTO getShopProjectInfoFromRedis(String projectInfoId) {
        ShopProjectInfoDTO shopProjectInfoDTO = (ShopProjectInfoDTO) JedisUtils.getObject(projectInfoId);

        if (null == shopProjectInfoDTO) {
            shopProjectInfoDTO = new ShopProjectInfoDTO();
            shopProjectInfoDTO.setId(projectInfoId);
            List<ShopProjectInfoDTO> projectList = shopProjectService.getShopCourseProjectList(shopProjectInfoDTO);
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

}
