package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;

/**
 * ClassName: ShopProjectGroupService
 *
 * @Author： huan
 * @Description: 套卡相关的接口
 * @Date:Created in 2018/4/11 15:01
 * @since JDK 1.8
 */
public interface ShopProjectGroupService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取套卡列表
     * @Date:2018/4/11 15:14
     */
    List<ShopProjectGroupDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO);


}
