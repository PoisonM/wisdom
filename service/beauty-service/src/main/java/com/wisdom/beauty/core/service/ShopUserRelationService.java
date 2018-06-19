package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.common.dto.system.ResponseDTO;

import java.util.List;

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

    /**
     * 用户绑定会员
     *
     * @param shopUserRelationDTO
     * @return
     */
    int saveUserShopRelation(ShopUserRelationDTO shopUserRelationDTO);

    /**
     * @Author:Administrator
     * @Param:
     * @Return:
     * @Description: 根据条件查询美容院
     * @Date:2018/4/23 17:49
     */
    List<ShopUserRelationDTO> getShopListByCondition(ShopUserRelationDTO shopUserRelationDTO);

    /**
     * 查询某个用户与店的绑定关系
     *
     * @param openId
     * @param shopId
     * @return
     */
    ResponseDTO<String> userBinding(String openId, String shopId,String userId);

    /**
     * 查询美容院信息
     * @param extSysShopDTO
     * @return
     */
    List<ExtSysShopDTO> getBossShopInfo(ExtSysShopDTO extSysShopDTO);

    /**
     * 更新店铺信息
     */
    int updateShopInfo(ExtSysShopDTO sysShopDTO);
}
