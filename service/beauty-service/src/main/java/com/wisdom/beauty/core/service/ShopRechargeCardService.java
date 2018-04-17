package com.wisdom.beauty.core.service;


import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;
import java.util.Map;

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
    List<ShopRechargeCardResponseDTO> getShopRechargeCardList(PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据充值卡id查询充值卡信息
     * @Date:2018/4/11 12:02
     */
    ShopRechargeCardDTO getShopRechargeCard(String id);

    /**
     * @Author:huan
     * @Param: queryCriteria :项目id或者产品id  rechargeCardId:套卡id type 类型
     * @Return:
     * @Description: 根据产品id或者项目Id查询折扣信息
     * @Date:2018/4/12 11:33
     */
    Float getDiscount(String queryCriteria, String rechargeCardId, String type);
    /**
    *@Author:huan
    *@Param:  充值卡id
    *@Return:  map  key--充值卡id   value---map
    *@Description: 获取折扣信息
    *@Date:2018/4/16 18:29
    */
    Map<String,Map<String,Object>>getDiscount(List<String> rechargeCardIds);
}
