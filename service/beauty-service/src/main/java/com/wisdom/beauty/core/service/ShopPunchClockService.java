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

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 用户打卡
     * @Date:2018/4/12 15:07
     */
    void punchClock(ShopPunchClockDTO shopPunchClockDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据时间，美容店id，店员id
     * @Date:2018/4/12 15:21
     */
    ShopPunchClockDTO getShopPunchClockDTO(ShopPunchClockDTO shopPunchClockDTO);
}
