package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;

/**
 * FileName: ShopOrderService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 订单相关
 */
public interface ShopOrderService {

    /**
     * 保存用户的订单信息
     */
    int saveShopUserOrderInfo(ShopUserOrderDTO extShopUserConsumeRecordDTO);

}
