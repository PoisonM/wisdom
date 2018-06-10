package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ExtShopRechargeCardDTO;

import java.math.BigDecimal;
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

    /**
     * 获取充值卡总金额
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    BigDecimal getUserRechargeCardSumAmount(ShopUserRechargeCardDTO shopUserRechargeCardDTO);

    /**
     * 保存充值卡信息
     *
     * @param extShopRechargeCardDTO
     * @return
     */
    int saveRechargeCardInfo(ExtShopRechargeCardDTO extShopRechargeCardDTO);

    /**
     * 查询某个店的充值卡列表
     * @param shopRechargeCardDTO
     * @return
     */
    List<ShopRechargeCardDTO> getShopRechargeCardInfo(ShopRechargeCardDTO shopRechargeCardDTO);

    /**
     * 保存用户余额充值账号
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    int saveUserSpecialRechargeCardInfo(ShopUserRechargeCardDTO shopUserProjectRelationDTO);

    /**
     * 修改充值卡信息
     *
     * @param extShopRechargeCardDTO
     * @return
     */
    int updateRechargeCardInfo(ExtShopRechargeCardDTO extShopRechargeCardDTO);

    /**
     * 根据虚拟商品id获取虚拟商品列表
     *
     * @param shopProjectProductCardRelationDTO
     * @return
     */
    List<ShopProjectProductCardRelationDTO> getShopProjectProductCardRelationList(ShopProjectProductCardRelationDTO shopProjectProductCardRelationDTO);
}
