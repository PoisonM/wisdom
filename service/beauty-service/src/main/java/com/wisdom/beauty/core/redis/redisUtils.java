package com.wisdom.beauty.core.redis;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * FileName: redisUtils
 *
 * @author: 赵得良
 * Date:     2018/4/4 0004 11:03
 * Description: B端redis帮助类
 */
public class redisUtils {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 预约详情缓存时常，30天
     */
    private int appointCacheSeconds = 1296000;

    /**
     * 保存预约详情
     * @param shopAppointServiceDTO
     */
    public void saveShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO){

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
     * @param shopAppointServiceDTO
     */
    public ShopAppointServiceDTO getShopAppointInfoFromRedis(ShopAppointServiceDTO shopAppointServiceDTO){
        return (ShopAppointServiceDTO)JedisUtils.getObject(shopAppointServiceDTO.getId());
    }

    /**
     * 根据分数过滤与某个美容师相关的预约信息
     * @param shopIdClerkId
     * @param min
     * @param max
     * @return
     */
    public Set<String> getAppointmentIdByShopClerk(String shopIdClerkId,double min, double max ){
        Set<String> set = JedisUtils.zRangeByScore(shopIdClerkId, min, max);
        return set;
    }

    /**
     * 更新预约详情
     * @param shopAppointServiceDTO
     */
    public void updateShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO){
        saveShopAppointInfoToRedis(shopAppointServiceDTO);
    }

}
