package com.wisdom.business.service.account;

import com.wisdom.business.mapper.account.IncomeMapper;
import com.wisdom.business.mapper.transaction.PayRecordMapper;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.FrontUtils;
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
public class IncomeService {

    @Autowired
    private IncomeMapper incomeMapper;
    @Autowired
    private PayRecordMapper payRecordMapper;
    
    public List<IncomeRecordDTO> getUserIncomeInfoByDate(String userId, Date date) {
        List<IncomeRecordDTO> incomeRecordDTOS = incomeMapper.getUserIncomeInfoByDate(userId,date);
        return incomeRecordDTOS;
    }

    public void insertUserIncomeInfo(IncomeRecordDTO incomeRecordDTO) {
        incomeMapper.insertUserIncomeInfo(incomeRecordDTO);
    }

    public void updateIncomeInfo(IncomeRecordDTO incomeRecordDTO) {
        incomeMapper.updateIncomeInfo(incomeRecordDTO);
    }

    public List<IncomeRecordDTO> getUserIncomeRecordInfo(IncomeRecordDTO incomeRecordDTO) {
        return  incomeMapper.getUserIncomeInfo(incomeRecordDTO);
    }

    public PageParamDTO<List<IncomeRecordDTO>> queryUserIncomeByParameters(String phoneAndIdentify, String incomeType,
                                                                           String applyStartTime, String applyEndTime,String isExportExcel, Integer pageNo, Integer pageSize) {
        PageParamDTO<List<IncomeRecordDTO>> page = new  PageParamDTO<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int pageStartNo = (pageNo-1)*pageSize;
        int count = incomeMapper.queryUserIncomeCountByParameters(
                phoneAndIdentify,incomeType,applyStartTime,applyEndTime);
        page.setTotalCount(count);
        List<IncomeRecordDTO> commissionDTOList = incomeMapper.queryUserIncomeByParameters(phoneAndIdentify,incomeType,applyStartTime,applyEndTime,isExportExcel,pageStartNo,pageSize);

        for(IncomeRecordDTO incomeRecordDTO : commissionDTOList){
            incomeRecordDTO.setNickName(CommonUtils.nameDecoder(incomeRecordDTO.getNickName()));
        }
        page.setResponseData(commissionDTOList);

        return page;
    }

    public PageParamDTO<List<IncomeRecordDTO>> queryAllUserIncome(PageParamDTO pageParamDTO) {
        PageParamDTO<List<IncomeRecordDTO>> pageResult = new  PageParamDTO<>();
        String currentPage = String.valueOf(pageParamDTO.getPageNo());
        String pageSize = String.valueOf(pageParamDTO.getPageSize());
        Page<IncomeRecordDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<IncomeRecordDTO> resultPage = incomeMapper.queryAllUserIncome(page);
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }

    public List<IncomeRecordDTO> getUserIncomeRecordInfoByPage(String userId, PageParamDTO pageParamDTO) {
        return incomeMapper.getUserIncomeRecordInfoByPage(userId,pageParamDTO.getPageNo(),pageParamDTO.getPageSize());
    }

    public IncomeRecordDTO getIncomeRecordDetail(String transactionId) {
        return incomeMapper.getIncomeRecordDetail(transactionId);
    }

    public PageParamDTO<List<PayRecordDTO>> queryInstanceInfoByTransactionId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        PageParamDTO<List<PayRecordDTO>> page = new  PageParamDTO<>();
        List<PayRecordDTO> payRecordDTOList = incomeMapper.queryInstanceInfoByTransactionId(pageParamVoDTO);
        for(PayRecordDTO payRecordDTO : payRecordDTOList){
            try {
                payRecordDTO.setNickName(URLDecoder.decode(payRecordDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        page.setResponseData(payRecordDTOList);
        return page;
    }

    public PageParamDTO<List<MonthTransactionRecordDTO>> queryMonthTransactionRecordByIncomeRecord(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        PageParamDTO<List<MonthTransactionRecordDTO>> pageResult = new  PageParamDTO<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = DateUtils.StrToDate(pageParamVoDTO.getEndTime(),"date");//string转Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 26);//设定日期为26号
        String endData = dateFormat.format(calendar.getTime());
        pageParamVoDTO.setEndTime(null);//设定当前时间
        calendar.add(Calendar.MONTH,-1);//当前月份减一
        calendar.set(Calendar.DAY_OF_MONTH, 25);//设定日期为25号
        String startData = dateFormat.format(calendar.getTime());

        pageParamVoDTO.setStartTime(null);

        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());
        String pageSize = String.valueOf(pageParamVoDTO.getPageSize());
        Page<MonthTransactionRecordDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        int count = incomeMapper.queryMonthTransactionRecordByIncomeRecordCount(pageParamVoDTO);
//        Page<MonthTransactionRecordDTO> pageData = incomeMapper.queryMonthTransactionRecordByIncomeRecord(pageParamVoDTO,page);
        List<MonthTransactionRecordDTO> pageData = incomeMapper.queryMonthTransactionRecordByIncomeRecord(pageParamVoDTO);
        for(MonthTransactionRecordDTO monthTransactionRecordDTO : pageData){
            try {
                List<PayRecordDTO> payRecordDTOS =payRecordMapper.queryUserInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
                if(payRecordDTOS.size()>0){
                    PayRecordDTO payRecordDTO = payRecordDTOS.get(0);
                    monthTransactionRecordDTO.setUserId(payRecordDTO.getSysUserId());
                    monthTransactionRecordDTO.setNickName(payRecordDTO.getNickName());
                    monthTransactionRecordDTO.setUserType(payRecordDTO.getUserType());
                    monthTransactionRecordDTO.setMobile(payRecordDTO.getMobile());
                    monthTransactionRecordDTO.setIdentifyNumber(payRecordDTO.getIdentifyNumber());
                }
                monthTransactionRecordDTO.setNickName(URLDecoder.decode(monthTransactionRecordDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        pageResult.setTotalCount(count);
        pageResult.setResponseData(pageData);
        return pageResult;
    }

    public PageParamDTO<List<PayRecordDTO>> queryMonthPayRecordByUserId(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        PageParamDTO<List<PayRecordDTO>> pageResult = new  PageParamDTO<>();
//        if( pageParamVoDTO.getEndTime() == null || "".equals(pageParamVoDTO.getEndTime())){
//            pageParamVoDTO.setEndTime(DateUtils.getDate());//设定当前时间
//        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = DateUtils.StrToDate(pageParamVoDTO.getEndTime(),"date");//string转Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 26);//设定日期为26号
        String endData = dateFormat.format(calendar.getTime());
        pageParamVoDTO.setEndTime(null);//设定当前时间
        calendar.add(Calendar.MONTH,-1);//当前月份减一
        calendar.set(Calendar.DAY_OF_MONTH, 25);//设定日期为25号
        String startData = dateFormat.format(calendar.getTime());

        pageParamVoDTO.setStartTime(null);

        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());
        String pageSize = String.valueOf(pageParamVoDTO.getPageSize());
        Page<PayRecordDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        int count = incomeMapper.queryMonthPayRecordCountByUserId(pageParamVoDTO);
//        Page<PayRecordDTO> pageData = incomeMapper.queryMonthPayRecordByUserId(pageParamVoDTO,page);
        List<PayRecordDTO> pageData = incomeMapper.queryMonthPayRecordByUserId(pageParamVoDTO);
        for(PayRecordDTO payRecordDTO : pageData){
            try {
                payRecordDTO.setNickName(URLDecoder.decode(payRecordDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        pageResult.setTotalCount(count);
        pageResult.setResponseData(pageData);
        return pageResult;
    }

    public void updateIncomeRecord(IncomeRecordDTO incomeRecordDTO) {
        incomeMapper.updateIncomeRecord(incomeRecordDTO);
    }

    //查询即时奖励总额(个人)
    public String selectIncomeInstanceByUserId(String userId) {
        return incomeMapper.selectIncomeInstanceByUserId(userId);
    }
}
