package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;

/**
 * FileName: ShopUserConsumService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 用户消费相关
 */
public interface ShopUserConsumeService {


    /**
     * 用户消费充值卡信息
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    int userConsumeRechargeCard(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO);

    /**
     * 用户充值操作
     */
    int userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO);
}
