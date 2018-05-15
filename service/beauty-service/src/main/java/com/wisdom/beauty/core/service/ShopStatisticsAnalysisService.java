package com.wisdom.beauty.core.service;

import java.math.BigDecimal;
import java.util.Date;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;
import java.util.Map;

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
    BigDecimal getPerformance(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取耗卡
     * @Date:2018/4/27 20:08
     */
    BigDecimal getExpenditure(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * 查询美容店某个时间段的耗卡金额
     */
    BigDecimal getShopCardConsumeAmount(String shopId, Date startDate, Date endDate);

    /**
     * 查询新客个数
     */
    int getShopNewUserNumber(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 查询人头数
     * @Date:2018/4/13 11:34
     */
    Integer getUserConsumeNumber(String sysClerkId, String startDate, String endDate);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: f复写getUserConsumeNumber方法
    *@Date:2018/5/8 9:39
    */
    Integer getUserConsumeNumber(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据sysShopId, startTime, endTime获取业绩和耗卡
     * @Date:2018/4/23 11:17
     */
    List<ExpenditureAndIncomeResponseDTO> getExpenditureAndIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据boss, startTime, endTime获取所有美容店业绩和耗卡
     * @Date:2018/4/23 11:17
     */
    List<ExpenditureAndIncomeResponseDTO> getShopExpenditureAndIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 查看某个美容店的充值和消费金额(业绩)
     * @Date:2018/4/25 14:54
     */
    BigDecimal getShopConsumeAndRecharge(String shopId, String goodType, String consumeType, Boolean isCardConsume, Date startDate, Date endDate);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 查看全部家人中店员信息和业绩情况
     * @Date:2018/4/25 19:21
     */
    List<ExpenditureAndIncomeResponseDTO> getClerkExpenditureAndIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 查询耗卡的集合
    *@Date:2018/4/27 20:10
    */
    List<ExpenditureAndIncomeResponseDTO> getExpenditureList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取业绩集合
    *@Date:2018/4/27 20:10
    */
    List<ExpenditureAndIncomeResponseDTO> getIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取充值金额，消费金额 ，划卡金额，单次消费金额，卡耗消费金额
    *@Date:2018/4/30 15:56
    */
    Map<String,String> getShopConsumeAndRecharge(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取所有员工的业绩，卡耗，耗卡，人次数，服务次数
    *@Date:2018/5/14 15:32
    */
    List<ExpenditureAndIncomeResponseDTO> getClerkAchievementList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取所有美容院的顾客到店情况
    *@Date:2018/5/15 15:50
    */
    Map<String,Object> getCustomerArriveList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
}
