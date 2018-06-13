package com.wisdom.business.service.account;

import com.aliyun.oss.ServiceException;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.IncomeMapper;
import com.wisdom.business.mapper.account.IncomeRecordManagementMapper;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.mapper.transaction.PromotionTransactionRelationMapper;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.*;
import com.wisdom.common.dto.system.ExportIncomeRecordExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.transaction.PromotionTransactionRelation;
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
import java.util.*;

/**
 * Created by sunxiao on 2017/6/26.
 */

@Service
@Transactional(readOnly = false)
public class IncomeService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IncomeMapper incomeMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private IncomeRecordManagementMapper incomeRecordManagementMapper;

    @Autowired
    private PromotionTransactionRelationMapper promotionTransactionRelationMapper;

    @Autowired
    private AccountService accountService;

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

    public List<IncomeRecordDTO> getUserIncomeRecordInfoByPage(String userId, PageParamDTO pageParamDTO) {
        return incomeMapper.getUserIncomeRecordInfoByPage(userId,pageParamDTO.getPageNo(),pageParamDTO.getPageSize());
    }

    public IncomeRecordDTO getIncomeRecordDetail(String transactionId) {
        return incomeMapper.getIncomeRecordDetail(transactionId);
    }

    public void updateIncomeRecord(IncomeRecordDTO incomeRecordDTO) {
        incomeMapper.updateIncomeRecord(incomeRecordDTO);
    }

    //查询即时奖励总额(个人)
    public String selectIncomeInstanceByUserId(String userId) {
        return incomeMapper.selectIncomeInstanceByUserId(userId);
    }

    public List<IncomeRecordManagementDTO> getIncomeRecordManagement(IncomeRecordManagementDTO incomeRecordManagementDTO) {
        return incomeRecordManagementMapper.getIncomeRecordManagement(incomeRecordManagementDTO);
    }

    public List<IncomeRecordDTO> getIncomeRecordByPageParam(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        logger.info("根据条件查询用户佣金奖励");



        //获取审核状态条件
        String CheckStatus =pageParamVoDTO.getRequestData().getCheckStatus();
        List<IncomeRecordDTO> incomeRecordDTOS = new ArrayList<>();
        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());

        //全部信息
        if(("").equals(CheckStatus)){

            incomeRecordDTOS =incomeMapper.getIncomeRecordByPageParam(pageParamVoDTO);

        }else{

            incomeRecordDTOS = incomeMapper.findIncomeForUnaudited(pageParamVoDTO);
        }

        if(incomeRecordDTOS.size()==0){
            logger.info("佣金奖励条件查询 查出数据incomeRecordDTOS为0","审核状态"+CheckStatus );
            return incomeRecordDTOS;
        }

        //查询该订单用户提升等级信息queryIncomeInfoByIncomeId
        for(IncomeRecordDTO income :incomeRecordDTOS){
           PromotionTransactionRelation promotionTransactionRelation =  promotionTransactionRelationMapper.getIsImportLevel(income.getTransactionId());
           if(promotionTransactionRelation!=null){
               if(promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_A)){
                   income.setIsImportLevel(ConfigConstant.businessA1);
               }else if(promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_B)){
                   income.setIsImportLevel(ConfigConstant.businessB1);
               }else{
                   income.setIsImportLevel("未提升等级");
               }

           }

        }

        IncomeRecordManagementDTO incomeRecordManagementDTO =new IncomeRecordManagementDTO();
        Iterator<IncomeRecordDTO> iterator = incomeRecordDTOS.iterator();
        while (iterator.hasNext()){
            String orderStatus ="2";
            String orderId ="";
            String orderAmount ="0";
            IncomeRecordDTO incomeRecordDTO = iterator.next();
            try {
                //查詢审核信息
                incomeRecordManagementDTO.setIncomeRecordId(incomeRecordDTO.getId());
                List<IncomeRecordManagementDTO> incomeRecordManagementDTOS = incomeRecordManagementMapper.getIncomeRecordManagement(incomeRecordManagementDTO);
                //一条数据,这说明有一方已审核,
                if(incomeRecordManagementDTOS.size()>0 && incomeRecordManagementDTOS.size() ==1) {
                    //运营人员审核
                    if(ConfigConstant.operationMember.equals(incomeRecordManagementDTOS.get(0).getUserType())){
                        if(incomeRecordManagementDTOS.get(0).getStatus().equals("0")){
                            incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_AUDIT_REJECTION);
                        }else{
                            incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_OPERATION);
                        }
                        incomeRecordDTO.setCheckUserType(incomeRecordManagementDTOS.get(0).getUserType());
                        incomeRecordDTO.setCheckStatus(incomeRecordManagementDTOS.get(0).getStatus());
                        incomeRecordDTO.setCheckUserName(URLDecoder.decode(incomeRecordManagementDTOS.get(0).getUserName(),"utf-8"));
                        incomeRecordDTO.setCheckSysUserId(incomeRecordManagementDTOS.get(0).getSysUserId());
                    }else if(ConfigConstant.financeMember.equals(incomeRecordManagementDTOS.get(0).getUserType())){
                        //财务人员审核
                        if(incomeRecordManagementDTOS.get(0).getStatus().equals("0")){
                            incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_AUDIT_REJECTION);
                        }else{
                            incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_FINANCE);
                        }
                        incomeRecordDTO.setCheckUserType(incomeRecordManagementDTOS.get(0).getUserType());
                        incomeRecordDTO.setCheckStatus(incomeRecordManagementDTOS.get(0).getStatus());
                        incomeRecordDTO.setCheckUserName(URLDecoder.decode(incomeRecordManagementDTOS.get(0).getUserName(),"utf-8"));
                        incomeRecordDTO.setCheckSysUserId(incomeRecordManagementDTOS.get(0).getSysUserId());
                    }
                }else if(incomeRecordManagementDTOS.size() == 2){
                    boolean status = true;
                    for (IncomeRecordManagementDTO incomeRecordManagementDTO1: incomeRecordManagementDTOS){
                        //说明有一方拒绝,插入3标记和拒绝标记以及拒绝人的信息
                        if(("0").equals(incomeRecordManagementDTO1.getStatus())){
                            incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_AUDIT_REJECTION);
                            status = false;
                            incomeRecordDTO.setCheckUserType(incomeRecordManagementDTO1.getUserType());
                            incomeRecordDTO.setCheckStatus(incomeRecordManagementDTO1.getStatus());
                            incomeRecordDTO.setCheckUserName(URLDecoder.decode(incomeRecordManagementDTO1.getUserName(),"utf-8"));
                            incomeRecordDTO.setCheckSysUserId(incomeRecordManagementDTO1.getSysUserId());
                        }
                    }
                    //经过双方审核,已通过
                    if(status){
                        //记录双方通过标记
                        incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_AUDITED);
                        //记录审核状态为通过
                    }
                }else {
                    //没有数据,则说明没有被审核,标记为未审核
                    incomeRecordDTO.setSecondCheckStatus(ConfigConstant.INCOME_UNAUDITED);
                }
                if(!"".equals(incomeRecordDTO.getNickName()) && incomeRecordDTO.getNickName() != null)
                {
                    String nickNameW = incomeRecordDTO.getNickName().replaceAll("%", "%25");
                    while(true){
                        if(nickNameW!=null&&nickNameW!=""){
                            if(nickNameW.contains("%25")){
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                            }else{
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                break;
                            }
                        }else{
                            break;
                        }

                    }
                    incomeRecordDTO.setNickName(nickNameW);
                }
                if(!"".equals(incomeRecordDTO.getNextUserNickName()) && incomeRecordDTO.getNextUserNickName() != null)
                {
                    String nickNameW = incomeRecordDTO.getNextUserNickName().replaceAll("%", "%25");
                    while(true){
                        if(nickNameW!=null&&nickNameW!=""){
                            if(nickNameW.contains("%25")){
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                            }else{
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                break;
                            }
                        }else{
                            break;
                        }

                    }
                    incomeRecordDTO.setNextUserNickName(nickNameW);
                }
            } catch (UnsupportedEncodingException e) {
                logger.info("service -- 根据条件查询佣金奖励getIncomeRecordByPageParam方法转换nickName失败" );
                e.printStackTrace();
                throw new ServiceException("转换nickName失败");
            }
            if (StringUtils.isBlank(incomeRecordDTO.getTransactionId())) {
                throw new ServiceException("incomeRecordDTO里TransactionId为空");
            }
            List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(incomeRecordDTO.getTransactionId());
            //判断是否有数据
            if(businessOrderDTOS.size() != 0){
                //判断是否只有一笔订单
                if(businessOrderDTOS.size() > 1){
                    for (BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
                        //若有未完成订单则把订单状态返回
                        if(!"2".equals(businessOrderDTO.getStatus())){
                            orderId = businessOrderDTO.getBusinessOrderId();
                            orderAmount = businessOrderDTO.getAmount();
                            orderStatus = businessOrderDTO.getStatus();
                        }
                     }
                }else {
                    orderId = businessOrderDTOS.get(0).getBusinessOrderId();
                    orderStatus = businessOrderDTOS.get(0).getStatus();
                    orderAmount = businessOrderDTOS.get(0).getAmount();
                }
                incomeRecordDTO.setPayDate(businessOrderDTOS.get(0).getPayDate());
            }
            //根据用户income表中的受益人userId查询受益人信息
            UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecordDTO.getSysUserId());
            UserInfoDTO nextUserInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecordDTO.getNextUserId());
            incomeRecordDTO.setOrderStatus(orderId);
            incomeRecordDTO.setOrderAmount(orderAmount);
            incomeRecordDTO.setOrderStatus(orderStatus);
            if(null != nextUserInfoDTO){
                incomeRecordDTO.setUserTypeNow(userInfoDTO.getUserType());
            }
            if(null != nextUserInfoDTO){
                incomeRecordDTO.setNextUserTypeNow(nextUserInfoDTO.getUserType());
            }
        }
        return incomeRecordDTOS;
    }

    public int getIncomeRecordCountByPageParam(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        return incomeMapper.getIncomeRecordCountByPageParam(pageParamVoDTO);
    }


    public List<IncomeRecordDTO> queryIncomeByParameters(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        List<IncomeRecordDTO> incomeRecordDTOS = incomeMapper.getIncomeRecordByPageParam(pageParamVoDTO);
        for (IncomeRecordDTO incomeRecordDTO: incomeRecordDTOS) {
                if (StringUtils.isNotBlank(incomeRecordDTO.getNickName())) {
                    try {
                        if(incomeRecordDTO.getNickName() != null && incomeRecordDTO.getNickName() != ""){
                            String nickNameW = incomeRecordDTO.getNickName().replaceAll("%", "%25");
                            while(true){
                                if(nickNameW!=null&&nickNameW!=""){
                                    if(nickNameW.contains("%25")){
                                        nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                    }else{
                                        nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                        break;
                                    }
                                }else{
                                    break;
                                }
                            }
                            incomeRecordDTO.setNickName(nickNameW);
                        }else{
                            incomeRecordDTO.setNickName("未知用户");
                        }
                    } catch(Throwable e){
                        logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
                        incomeRecordDTO.setNickName("特殊符号用户");
                    }
                }
                if (StringUtils.isNotBlank(incomeRecordDTO.getNextUserNickName())) {
                    try {
                        if(incomeRecordDTO.getNextUserNickName() != null && incomeRecordDTO.getNextUserNickName() != ""){
                            String nickNameW = incomeRecordDTO.getNextUserNickName().replaceAll("%", "%25");
                            while(true){
                                if(nickNameW!=null&&nickNameW!=""){
                                    if(nickNameW.contains("%25")){
                                        nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                    }else{
                                        nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                        break;
                                    }
                                }else{
                                    break;
                                }

                            }
                            incomeRecordDTO.setNextUserNickName(nickNameW);
                        }else{
                            incomeRecordDTO.setNextUserNickName("未知用户");
                        }
                    } catch(Throwable e){
                        logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
                        incomeRecordDTO.setNextUserNickName("特殊符号用户");
                    }
                }
            //判断该交易购买者是否升级
            PromotionTransactionRelation promotionTransactionRelation = promotionTransactionRelationMapper.getIsImportLevel(incomeRecordDTO.getTransactionId());
            if(promotionTransactionRelation!=null){
                if(promotionTransactionRelation.getPromotionLevel()!=null){
                    if((promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_A))||(promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_B))){
                        incomeRecordDTO.setIsImportLevel("是");
                    }else{
                        incomeRecordDTO.setIsImportLevel("否");
                    }
                }
            }

            //获取购买者信息
            UserInfoDTO selfUserInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecordDTO.getSysUserId());
            UserInfoDTO nextUserInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecordDTO.getNextUserId());
            incomeRecordDTO.setUserTypeNow(selfUserInfoDTO.getUserType());
            incomeRecordDTO.setNextUserTypeNow(nextUserInfoDTO.getUserType());
        }
        return incomeRecordDTOS;

    }

    public List<MonthTransactionRecordDTO> queryMonthRecordByParentRelation(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        logger.info("service -- 月度奖励详情queryMonthRecordByParentRelation方法" );
        List<MonthTransactionRecordDTO> monthTransactionRecordDTOS = incomeMapper.queryMonthRecordByParentRelation(pageParamVoDTO);
        for (MonthTransactionRecordDTO monthTransactionRecordDTO: monthTransactionRecordDTOS) {
            try {
                if (StringUtils.isNotBlank(monthTransactionRecordDTO.getNickName())) {
                    monthTransactionRecordDTO.setNickName(URLDecoder.decode(monthTransactionRecordDTO.getNickName(),"utf-8"));
                }
                if (StringUtils.isNotBlank(monthTransactionRecordDTO.getNextUserNickName())) {
                    monthTransactionRecordDTO.setNextUserNickName(URLDecoder.decode(monthTransactionRecordDTO.getNextUserNickName(),"utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                logger.info("service -- 月度奖励详情queryMonthRecordByParentRelation方法转换nickName失败" );
                e.printStackTrace();
            }
            IncomeRecordDTO incomeRecord = new IncomeRecordDTO();
            incomeRecord.setTransactionId(monthTransactionRecordDTO.getTransactionId());
            incomeRecord.setSysUserId(monthTransactionRecordDTO.getUserId());
            List<IncomeRecordDTO> incomeRecordDTOS = incomeMapper.getUserIncomeInfo(incomeRecord);
            float amountMoney = 0;
            for(IncomeRecordDTO incomeRecordDTO:incomeRecordDTOS){
                amountMoney = amountMoney+incomeRecordDTO.getAmount();
            }
            monthTransactionRecordDTO.setAmountMoney(amountMoney);
            UserInfoDTO selfUserInfoDTO = userServiceClient.getUserInfoFromUserId(monthTransactionRecordDTO.getUserId());
            UserInfoDTO nextUserInfoDTO = userServiceClient.getUserInfoFromUserId(monthTransactionRecordDTO.getNextUserId());
            monthTransactionRecordDTO.setUserTypeNow(selfUserInfoDTO.getUserType());
            monthTransactionRecordDTO.setNextUserTypeNow(nextUserInfoDTO.getUserType());
        }
        return monthTransactionRecordDTOS;
    }


    public int getIncomeRecordCountByIncomeManagement(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {

        int count = incomeMapper.getIncomeForUnauditedCount(pageParamVoDTO);
        return count;
    }

    public int queryMonthRecordCountByParentRelation(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        return incomeMapper.queryMonthRecordCountByParentRelation(pageParamVoDTO);
    }

    public List<ExportIncomeRecordExcelDTO> exportExcelIncomeRecord(PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
        logger.info("service -- 导出佣金数据 exportExcelIncomeRecord,方法执行" );
        List<ExportIncomeRecordExcelDTO> exportIncomeRecordExcelDTOS = incomeMapper.exportExcelIncomeRecord(pageParamVoDTO);
        for (ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO : exportIncomeRecordExcelDTOS){
            //判断该交易购买者是否升级
            PromotionTransactionRelation promotionTransactionRelation = promotionTransactionRelationMapper.getIsImportLevel(exportIncomeRecordExcelDTO.getTransactionId());
            if(promotionTransactionRelation!=null){
                if(promotionTransactionRelation.getPromotionLevel()!=null){
                    if((promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_A))||(promotionTransactionRelation.getPromotionLevel().equals(ConfigConstant.LEVE_IMPORT_B))){
                        exportIncomeRecordExcelDTO.setIsImportLevel("是");
                    }else{
                        exportIncomeRecordExcelDTO.setIsImportLevel("否");
                    }
                }
            }

            if (StringUtils.isNotBlank(exportIncomeRecordExcelDTO.getNickName())) {
                try {
                    if(exportIncomeRecordExcelDTO.getNickName() != null && exportIncomeRecordExcelDTO.getNickName() != ""){
                        String nickNameW = exportIncomeRecordExcelDTO.getNickName().replaceAll("%", "%25");
                        while(true){
                            if(nickNameW!=null&&nickNameW!=""){
                                if(nickNameW.contains("%25")){
                                    nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                }else{
                                    nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                    break;
                                }
                            }else{
                                break;
                            }

                        }
                        exportIncomeRecordExcelDTO.setNickName(nickNameW);
                    }else{
                        exportIncomeRecordExcelDTO.setNickName("未知用户");
                    }
                } catch(Throwable e){
                    logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
                    logger.info("异常用户————————————————————————{}",exportIncomeRecordExcelDTO.getNickName());
                    exportIncomeRecordExcelDTO.setNickName("特殊符号用户");
                    continue;
                }
              //  exportIncomeRecordExcelDTO.setNickName(URLDecoder.decode(exportIncomeRecordExcelDTO.getNickName(),"utf-8"));
            }
            if (StringUtils.isNotBlank(exportIncomeRecordExcelDTO.getNextUserNickName())) {

                try {
                    if(exportIncomeRecordExcelDTO.getNextUserNickName() != null && exportIncomeRecordExcelDTO.getNextUserNickName() != ""){
                        String nickNameW1 = exportIncomeRecordExcelDTO.getNextUserNickName().replaceAll("%", "%25");
                        while(true){
                            if(nickNameW1!=null&&nickNameW1!=""){
                                if(nickNameW1.contains("%25")){
                                    nickNameW1 = URLDecoder.decode(nickNameW1,"utf-8");
                                }else{
                                    nickNameW1 = URLDecoder.decode(nickNameW1,"utf-8");
                                    break;
                                }
                            }else{
                                break;
                            }

                        }
                        exportIncomeRecordExcelDTO.setNextUserNickName(nickNameW1);
                    }else{
                        exportIncomeRecordExcelDTO.setNextUserNickName("未知用户");
                    }
                } catch(Throwable e){
                    logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
                    exportIncomeRecordExcelDTO.setNextUserNickName("特殊符号用户");
                }
               // exportIncomeRecordExcelDTO.setNextUserNickName(URLDecoder.decode(exportIncomeRecordExcelDTO.getNextUserNickName(),"utf-8"));
            }


        }
        return exportIncomeRecordExcelDTOS;
    }



    /**
     * 店铺信息
     *
     *
     *
     * */
    public PageParamDTO<List<IncomeRecordDTOExt>> findNextUserInfo(PageParamVoDTO<UserInfoDTO> pageParamVoDTO){
        logger.info("service -- 店铺信息 findNextUserInfo,方法执行" );
        PageParamDTO<List<IncomeRecordDTOExt>> pageResult = new PageParamDTO<List<IncomeRecordDTOExt>>();

        //获取父类是该用户的所有用户
        List<UserInfoDTO> userInfoDTOS = (ArrayList)userServiceClient.getUserInfo(pageParamVoDTO.getRequestData());
        logger.info("service -- 获取父类是该用户的所有用户List={}", userInfoDTOS.size());
        List<IncomeRecordDTOExt> incomeRecordDTOExts = new ArrayList<>();
        if(userInfoDTOS != null && userInfoDTOS.size()>0){
            for(UserInfoDTO userInfo : userInfoDTOS){
                IncomeRecordDTOExt incomeRecordDTOExt = new IncomeRecordDTOExt();
                if(userInfo.getUserType()!=null){
                    if(userInfo.getUserType().equals(ConfigConstant.businessB1)||userInfo.getUserType().equals(ConfigConstant.businessA1)){
                        PayRecordDTO pay  = new PayRecordDTO();
                        pay.setSysUserId(userInfo.getId());
                        //支付成功
                        pay.setStatus("1");
                        List<String> transactionIdS  =  payRecordService.findTransactionIdList(pay);
                        if(transactionIdS!=null&& transactionIdS.size()>0){
                            for(String transactionId :transactionIdS){
                                Float amount = (float)0;
                                StringBuilder orderId = new StringBuilder();
                                PayRecordDTO payRecord  = new PayRecordDTO();
                                payRecord.setTransactionId(transactionId);
                                payRecord.setStatus("1");
                                List<PayRecordDTO> payRecordDTOList  =  payRecordService.getUserPayRecordList(payRecord);
                                if(payRecordDTOList!=null&&payRecordDTOList.size()>0){
                                    if(payRecordDTOList.size()>1){
                                        for(PayRecordDTO payRecordDTO1:payRecordDTOList){
                                            amount = amount + payRecordDTO1.getAmount();
                                            orderId.append(",").append(payRecordDTO1.getOrderId());
                                        }
                                    }else{
                                        amount = payRecordDTOList.get(0).getAmount();
                                        orderId = orderId.append(payRecordDTOList.get(0).getOrderId());
                                    }
                                }
                                logger.info("service -- 判断此单={}是否大于498",amount);
                                //判断此单是否大于498
                                if(amount>=ConfigConstant.PROMOTE_B1_LEVEL_MIN_EXPENSE){
                                    incomeRecordDTOExt.setUserInfoDTO(userInfo);
                                    incomeRecordDTOExt.setAmount(amount);
                                    incomeRecordDTOExt.setOrderId(orderId.toString());
                                    incomeRecordDTOExt.setPayDate(payRecordDTOList.get(0).getPayDate());
                                    incomeRecordDTOExt.setTransactionId(transactionId);
                                    incomeRecordDTOExts.add(incomeRecordDTOExt);
                                    break;
                                }
                            }
                        }

                    }
                }
            }

        }
        if(pageParamVoDTO.getIsExportExcel().equals("N")){
            List<IncomeRecordDTOExt> incomeRecordDTOResult = new ArrayList<>();
            incomeRecordDTOResult = accountService.pagerUtil(incomeRecordDTOExts,incomeRecordDTOResult,pageParamVoDTO.getPageNo(),pageParamVoDTO.getPageSize());
            pageResult.setResponseData(incomeRecordDTOResult);
        }else{
            pageResult.setResponseData(incomeRecordDTOExts);
        }
        pageResult.setTotalCount(incomeRecordDTOExts.size());
        pageResult.setPageNo(pageParamVoDTO.getPageNo());
        pageResult.setPageSize(pageParamVoDTO.getPageSize());
        return pageResult;

    }

    public   String decodeNickName(String nickNameW){
        String nickName = "";
        try {
            while(true){
                if(nickNameW!=null&&nickNameW!=""){
                    if(nickNameW.contains("%25")){
                        nickName = URLDecoder.decode(nickNameW,"utf-8");
                    }else{
                        nickName = URLDecoder.decode(nickNameW,"utf-8");
                        break;
                    }
                }else{
                    break;
                }

            }
        }catch(Throwable e){
            logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
            nickName="特殊符号用户";
        }
        return nickName;
    }



}
