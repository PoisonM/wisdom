package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;

import java.util.List;

/**
 * ClassName: ShopUerConsumeRecordService
 *
 * @Author： huan
 * @Description: 消费记录下相关功能
 * @Date:Created in 2018/4/8 18:14
 * @since JDK 1.8
 */
public interface ShopUerConsumeRecordService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据条件查询某个美容院某个用户的账户记录，包括收银记录和划卡记录
     * @Date:2018/4/3 18:57
     */
    List<ShopUserConsumeRecordDTO> getShopCustomerConsumeRecordList(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO);
}
