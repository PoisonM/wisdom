package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCashFlowDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/10.
 * 收入支出分析
 */
public interface IncomeExpenditureAnalysisService {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return: map的key是：{0:银行卡支付 1：微信支付 2:支付宝支付 3:现金支付} ,values是：金额
     * @Description: 获取老板下的收支情况
     * @Date:2018/5/11 10:04
     */
    Map<String, BigDecimal> getBossIncomeExpenditure(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO);

    /**
     * @Author:zhagnhuan
     * @Param:
     * @Return: 美容院的总收益，。现金收入，美容店名字
     * @Description: 获取所有美容院的总收益和现金收入
     * @Date:2018/5/11 16:06
     */
    List<ExpenditureAndIncomeResponseDTO> getAllShopIncomeExpenditure(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:现金趋势图
     * @Date:2018/5/11 19:07
     */
    List<ExpenditureAndIncomeResponseDTO> getCashEarningsTendency(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取某个店的收入明细
     * @Date:2018/5/14 11:01
     */
    List<ExpenditureAndIncomeResponseDTO> getIncomeExpenditureAnalysisDetail(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO);

}
