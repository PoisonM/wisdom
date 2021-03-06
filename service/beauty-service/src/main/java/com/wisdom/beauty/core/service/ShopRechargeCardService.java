package com.wisdom.beauty.core.service;


import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopRechargeCardOrderDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;

/**
 * ClassName: ShopRechargeCard
 *
 * @Author： huan
 * @Description: 充值卡相关的接口
 * @Date:Created in 2018/4/11 11:37
 * @since JDK 1.8
 */
public interface ShopRechargeCardService {

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取充值卡列表
     * @Date:2018/4/11 11:39
     */
    List<ShopRechargeCardOrderDTO> getShopRechargeCardList(PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据充值卡id查询充值卡信息
     * @Date:2018/4/11 12:02
     */
    ShopRechargeCardResponseDTO getShopRechargeCard(ShopRechargeCardDTO shopRechargeCardDTO);

    /**
     * 更新用户的充值卡信息
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    int updateRechargeCard(ShopUserRechargeCardDTO shopUserRechargeCardDTO);


    /**
     * 查询用户的充值卡
     *
     * @param shopUserRechargeCardDTO
     * @return
     */
    ShopUserRechargeCardDTO getShopUserRechargeInfo(ShopUserRechargeCardDTO shopUserRechargeCardDTO);

    /**
     * 查询用户充值卡
     * @param userRechargeCardId
     * @return
     */
    ShopUserRechargeCardDTO getShopUserRechargeCardDTOById(String userRechargeCardId);

    /**
     * 生产用户的充值卡
     *
     * @param userRechargeCardDTO
     * @return
     */
    int saveShopUserRechargeCardInfo(ShopUserRechargeCardDTO userRechargeCardDTO);
}
