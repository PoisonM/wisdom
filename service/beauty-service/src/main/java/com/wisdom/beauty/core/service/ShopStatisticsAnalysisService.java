package com.wisdom.beauty.core.service;

import java.math.BigDecimal;
import java.util.Date;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: ShopStatisticsAnalysisService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ShopStatisticsAnalysisService {
    /**
     * @Author:huan
     * @Param: ShopUserRechargeCardDTO shopUserRechargeCardDTO
     * @Return:
     * @Description: 根据条件获取业绩
     * @Date:2018/4/13 10:24
     */
    BigDecimal getPerformance(PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO);

    /**
     * 查询美容店某个时间段的耗卡金额
     */
    BigDecimal getShopCardConsumeAmount(String shopId, Date startDate, Date endDate);

    /**
     * 查询新客个数
     */
    int getShopNewUserNumber(String shopId, Date startDate, Date endDate);
    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 查询人头数
    *@Date:2018/4/13 11:34
    */
    Integer getUserConsumeNumber(String sysClerkId, Date startDate, Date endDate);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/4/23 11:17
    */
    List<ExpenditureAndIncomeResponseDTO> getPerformanceList(PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO);

}
