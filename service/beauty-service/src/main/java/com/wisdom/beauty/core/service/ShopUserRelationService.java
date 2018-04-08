package com.wisdom.beauty.core.service;

/**
 * ClassName: ShopUserRelationService
 *
 * @Author： huan
 * @Description: 用户和美容院关系相关的
 * @Date:Created in 2018/4/3 18:39
 * @since JDK 1.8
 */
public interface ShopUserRelationService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据userId查询此人是否绑定会员
     * @Date:2018/4/3 18:42
     */
    String isMember(String userId);
}
