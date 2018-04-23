package com.wisdom.business.service.account;

import com.aliyun.oss.ServiceException;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.IncomeMapper;
import com.wisdom.business.mapper.account.IncomeRecordManagementMapper;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.FrontUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sunxiao on 2017/6/26.
 */

@Service
@Transactional(readOnly = false)
public class IncomeRecordManagementService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IncomeRecordManagementMapper incomeRecordManagementMapper;

    public List<IncomeRecordManagementDTO> getIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO){
        return incomeRecordManagementMapper.getIncomeRecordManagement(incomeRecordManagementDTO);
    };

    public void insertIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO){
        incomeRecordManagementMapper.insertIncomeRecordManagement(incomeRecordManagementDTO);
    };

    public void updateIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO){
        incomeRecordManagementMapper.updateIncomeRecordManagement(incomeRecordManagementDTO);
    };


}
