package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopPunchClockDTO;

import java.util.List;

/**
 * ClassName: ShopPunchClockService
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/4 14:56
 * @since JDK 1.8
 */
public interface ShopPunchClockService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 查询考情记录
     * @Date:2018/4/4 14:57
     */
    List<ShopPunchClockDTO> findShopPunchClockList(ShopPunchClockDTO shopPunchClockDTO);
}
