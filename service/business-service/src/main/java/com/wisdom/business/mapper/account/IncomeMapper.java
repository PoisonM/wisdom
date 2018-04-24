package com.wisdom.business.mapper.account;

import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.ExportIncomeRecordExcelDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * Created by sunxiao on 2017/6/20.
 */
@MyBatisDao
@Repository
public interface IncomeMapper {


    List<IncomeRecordDTO> getUserIncomeInfoByDate(@Param("userId") String userId, @Param("date") Date date);

    void insertUserIncomeInfo(IncomeRecordDTO incomeRecordDTO);

    void updateIncomeInfo(IncomeRecordDTO incomeRecordDTO);

    List<IncomeRecordDTO> getUserIncomeInfo(IncomeRecordDTO incomeRecordDTO);

    /**
     * 根据条件查询提现信息
     * @param phoneAndIdentify
     * @param applyStartTime
     * @param applyEndTime
     * @param pageStartNo
     * @param pageSize
     * @return
     */
    List<IncomeRecordDTO> queryUserIncomeByParameters(@Param("phoneAndIdentify") String phoneAndIdentify,
                                                      @Param("incomeType") String incomeType,
                                                      @Param("createStartDate") String applyStartTime,
                                                      @Param("createEndDate") String applyEndTime,
                                                      @Param("isExportExcel") String isExportExcel,
                                                      @Param("pageStartNo") int pageStartNo,
                                                      @Param("pageSize") int pageSize);

    /**
     * 获取提现信息Count根据条件
     * @param phoneAndIdentify
     * @param applyStartTime
     * @param applyEndTime
     * @return
     */
    int queryUserIncomeCountByParameters(@Param("phoneAndIdentify") String phoneAndIdentify,
                                         @Param("incomeType") String incomeType,
                                         @Param("createStartDate") String applyStartTime,
                                         @Param("createEndDate") String applyEndTime);

    //查询所有提现信息
    Page<IncomeRecordDTO> queryAllUserIncome(Page<IncomeRecordDTO> page);

    List<IncomeRecordDTO> getUserIncomeRecordInfoByPage(@Param("userId") String userId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    IncomeRecordDTO getIncomeRecordDetail(@Param("transactionId") String transactionId);

    //查询即时返现和统计推荐下的订单详情
    List<PayRecordDTO> queryInstanceInfoByTransactionId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    //查询月结下的详情
    //Page<MonthTransactionRecordDTO> queryMonthTransactionRecordByIncomeRecord(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO, Page<MonthTransactionRecordDTO> page);
    List<MonthTransactionRecordDTO> queryMonthRecordByParentRelation(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    //审核用户收入的金额是否可提现
    void updateIncomeRecord(IncomeRecordDTO incomeRecordDTO);

    //根据用户id查询这个月都消费了哪些订单
//    Page<PayRecordDTO> queryMonthPayRecordByUserId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO, Page<PayRecordDTO> page);

    List<PayRecordDTO> queryMonthPayRecordByUserId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    //查询即时奖励总额(个人)
    String selectIncomeInstanceByUserId(@Param("userId") String userId);

    //查询月结下详情交易Count
    int queryMonthTransactionRecordByIncomeRecordCount(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    //根据用户id查询这个月都消费了哪些订单Count
    int queryMonthPayRecordCountByUserId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    List<MonthTransactionRecordDTO> queryAllMonthTransactionRecord();

    void updateMonthTransactionRecord(MonthTransactionRecordDTO monthTransactionRecordDTO);

    List<IncomeRecordDTO> getIncomeRecordByPageParam(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    int getIncomeRecordCountByPageParam(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    List<IncomeRecordDTO> getIncomeRecordByIncomeManagement(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    int getIncomeRecordCountByIncomeManagement(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    int queryMonthRecordCountByParentRelation(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);

    List<ExportIncomeRecordExcelDTO> exportExcelIncomeRecord(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO);
}
