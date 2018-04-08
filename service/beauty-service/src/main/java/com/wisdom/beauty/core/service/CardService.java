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
public interface CardService {

    /**
     * 查询某个用户预约的项目列表
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    List<ShopUserRechargeCardDTO> getUserRechargeCardList(ShopUserRechargeCardDTO shopUserRechargeCardDTO);

}
