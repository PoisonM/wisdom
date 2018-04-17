package com.wisdom.business.mapper.account;

import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
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
public interface IncomeRecordManagementMapper {


    List<IncomeRecordManagementDTO> getIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO);

    void insertIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO);

    void updateIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO);

}
