package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;

import java.util.List;

/**
 * FileName: CardService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ShopCardService {

    /**
     * 查询某个用户的充值卡列表
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    List<ShopUserRechargeCardDTO> getUserRechargeCardList(ShopUserRechargeCardDTO shopUserRechargeCardDTO);

    /**
     * 更新用户充值卡信息
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    int updateUserRechargeCard(ShopUserRechargeCardDTO shopUserRechargeCardDTO);
}
