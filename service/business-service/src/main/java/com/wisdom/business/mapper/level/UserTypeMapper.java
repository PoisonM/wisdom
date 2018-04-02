package com.wisdom.business.mapper.level;

import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
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
public interface UserTypeMapper {

    List<UserBusinessTypeDTO> getUserBusinessType(UserBusinessTypeDTO userBusinessTypeDTO);

    void insertUserBusinessType(UserBusinessTypeDTO userBusinessTypeDTO);

    void updateUserBusinessType(UserBusinessTypeDTO userBusinessTypeDTO);
}
