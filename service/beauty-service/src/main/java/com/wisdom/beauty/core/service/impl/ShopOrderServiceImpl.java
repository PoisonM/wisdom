package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.core.service.ShopOrderService;
import org.springframework.stereotype.Service;

/**
 * FileName: ShopOrderServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 订单相关
 */
@Service("shopOrderService")
public class ShopOrderServiceImpl implements ShopOrderService {


    /**
     * 保存用户的订单信息
     *
     * @param extShopUserConsumeRecordDTO
     * @return
     */
    @Override
    public int saveShopUserOrderInfo(ShopUserOrderDTO extShopUserConsumeRecordDTO) {
        return 0;
    }
}
