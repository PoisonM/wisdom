package com.wisdom.beauty.core.redis;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.common.util.JedisUtils;
/**
 * FileName: redisUtils
 *
 * @author: 赵得良
 * Date:     2018/4/4 0004 11:03
 * Description: B端redis帮助类
 */
public class redisUtils {

    /**
     * 预约详情缓存时常，30天
     */
    private int appointCacheSeconds = 1296000;

    /**
     * 保存预约详情至redis中
     * @param shopAppointServiceDTO
     */
    public void saveShopAppointInfoToRedis(ShopAppointServiceDTO shopAppointServiceDTO){
        JedisUtils.setObject(shopAppointServiceDTO.getId(),shopAppointServiceDTO,appointCacheSeconds);
    }

    /**
     * 从redis中获取用户的预约详情
     * @param shopAppointServiceDTO
     */
    public ShopAppointServiceDTO getShopAppointInfoFromRedis(ShopAppointServiceDTO shopAppointServiceDTO){
        return (ShopAppointServiceDTO)JedisUtils.getObject(shopAppointServiceDTO.getId());
    }


}
