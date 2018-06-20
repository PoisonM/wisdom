package com.wisdom.timer.service.business;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.transaction.MonthlyIncomeSignalDTO;
import com.wisdom.common.dto.transaction.MonthlyIncomeErrorDTO;
import com.wisdom.common.dto.transaction.IncomeMonthDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.timer.client.BusinessServiceClient;
import com.wisdom.timer.client.UserServiceClient;
import com.wisdom.timer.mqsender.TimerMessageQueueSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

@Service
public class BusinessRunTimeService {
    Logger logger = LoggerFactory.getLogger(BusinessRunTimeService.class);

    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TimerMessageQueueSender timerMessageQueueSender;

    @Transactional(rollbackFor = Exception.class)
    public void autoConfirmReceiveProduct() {
        long startTime = System.currentTimeMillis();
        logger.info("用户15天后，自动转为收到货物==={}开始" , startTime);
        //先获取所有用户已经完成支付，但是没有确认收货的订单
        BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
        businessOrderDTO.setStatus("1");
        businessOrderDTO.setType("offline");
        List<BusinessOrderDTO> businessOrderDTOList = businessServiceClient.getBusinessOrderList(businessOrderDTO);
        logger.info("先获取所有用户已经完成支付，但是没有确认收货的订单List={}" ,businessOrderDTOList.size());

        //已经发货，但是用户没有确认收货的订单
        businessOrderDTO.setStatus("4");
        List<BusinessOrderDTO> businessOrderDTOList1 = businessServiceClient.getBusinessOrderList(businessOrderDTO);
        logger.info("已经发货，但是用户没有确认收货的订单List={}" ,businessOrderDTOList1.size());

        businessOrderDTOList.addAll(businessOrderDTOList1);

        //遍历所有用户的订单支付时间，todo 考虑加一个锁，避免用户凌晨3点下单的时候，造成business_order表死锁
        for(BusinessOrderDTO businessOrder:businessOrderDTOList)
        {
            timerMessageQueueSender.sendConfirmReceiveProduct(businessOrder);
        }
        logger.info("用户15天后，自动转为收到货物,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoProcessUserAccount() throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("用户即时返现解冻和用户等级提升的批量处理==={}开始" , startTime);
        //查询用户消费的不可提现金额
        IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
        incomeRecordDTO.setStatus("0");
        List<IncomeRecordDTO> incomeRecordDTOList = businessServiceClient.getUserIncomeRecordInfo(incomeRecordDTO);
        List<String> transactionIds = new ArrayList<>();

        //用户返现解冻
        this.deFrozenUserReturnMoney(incomeRecordDTOList,transactionIds);

        //同级推荐提升逻辑
        this.promoteUserBusinessTypeForRecommend();

        logger.info("用户即时返现解冻和用户等级提升的批量处理,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoMonthlyIncomeCalc() throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("月度提成计算==={}开始" , startTime);
        //加入开关量，证明本月已经完成过月度提成了，不用再次计算
        Query query = new Query(Criteria.where("year").is(DateUtils.getYear())).addCriteria(Criteria.where("month").is(DateUtils.getMonth()));
        MonthlyIncomeSignalDTO monthlyIncomeSignalDTO = mongoTemplate.findOne(query,MonthlyIncomeSignalDTO.class,"monthlyIncomeSignal");
        if(monthlyIncomeSignalDTO==null)
        {
            monthlyIncomeSignalDTO = new MonthlyIncomeSignalDTO();
            monthlyIncomeSignalDTO.setYear(DateUtils.getYear());
            monthlyIncomeSignalDTO.setMonth(DateUtils.getMonth());
            monthlyIncomeSignalDTO.setOnTimeFinish("false");
            mongoTemplate.insert(monthlyIncomeSignalDTO,"monthlyIncomeSignal");

            this.monthlyIncomeCalc(ConfigConstant.businessA1);

            this.monthlyIncomeCalc(ConfigConstant.businessB1);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish","true");
            mongoTemplate.updateFirst(query,update,"monthlyIncomeSignal");
        }
        else if(monthlyIncomeSignalDTO.getOnTimeFinish().equals("false"))
        {
            this.monthlyIncomeCalc(ConfigConstant.businessA1);

            this.monthlyIncomeCalc(ConfigConstant.businessB1);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish","true");
            mongoTemplate.updateFirst(query,update,"monthlyIncomeSignal");
        }
        logger.info("月度提成计算,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    @Transactional(rollbackFor = Exception.class)
    public void autoCalculateUserType() throws UnsupportedEncodingException {
        this.frozenUserType(ConfigConstant.businessA1);
        this.frozenUserType(ConfigConstant.businessB1);
    }

    public void autoProcessNoPayRecordData() {
        long startTime = System.currentTimeMillis();
        logger.info("每隔1分钟，将payRecord表中，状态为0的订单，进行状态调整处理,开始" , startTime);
        long autoNotifyProductPay = (long) ConfigConstant.AUTO_NOTIFY_PRODUCT_PAY * 60 * 1000;
        long autoDeleteBusinessOrder = (long) ConfigConstant.AUTO_DELETE_BUSINESS_ORDER * 60 * 1000;
        long nowTime = System.currentTimeMillis();
        long MaxTime = 60 * 1000;
        long MinTime = 0;

        String token = WeixinUtil.getUserToken();

        //查询系统中所有未支付的订单
        BusinessOrderDTO businessOrderDTO = new BusinessOrderDTO();
        businessOrderDTO.setStatus("0");
        List<BusinessOrderDTO> businessOrderDTOList = businessServiceClient.getBusinessOrderList(businessOrderDTO);
        if(businessOrderDTOList.size()>0)
        {
            for(BusinessOrderDTO businessOrder : businessOrderDTOList)
            {
                //订单已下时间
                long outTime = nowTime - (long)businessOrder.getCreateDate().getTime();
                long time = outTime - autoNotifyProductPay;
                //待付款超过10分钟
                if(MinTime < time && time < MaxTime)
                {
                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setId(businessOrder.getSysUserId());
                    userInfoDTO.setDelFlag("0");
                    List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
                    if(userInfoDTOList.size()>0)
                    {
                        String url = ConfigConstant.USER_WEB_URL + "orderManagement/0";
                        WeixinTemplateMessageUtil.sendOrderNotPayTemplateWXMessage(DateUtils.DateToStr(businessOrder.getCreateDate()),
                                businessOrder.getBusinessOrderId(),token,url,userInfoDTOList.get(0).getUserOpenid());
                        logger.info("待付款订单={}超过10分钟={}给用户={}发送提醒消息",businessOrder.getBusinessOrderId(),time,userInfoDTOList.get(0).getMobile());
                    }
                }
                //超过20分钟
                else if(outTime > autoDeleteBusinessOrder)
                {
                    //超时取消
                    businessOrder.setStatus("6");
                    businessServiceClient.updateBusinessOrder(businessOrder);
                    logger.info("待付款订单={}超过20分钟={}超时取消",businessOrder.getBusinessOrderId(),time);
                    PayRecordDTO payRecordDTO = new PayRecordDTO();
                    payRecordDTO.setSysUserId(businessOrder.getSysUserId());
                    payRecordDTO.setOrderId(businessOrder.getBusinessOrderId());
                    List<PayRecordDTO> payRecordDTOList = businessServiceClient.getPayRecordInfo(payRecordDTO);
                    if(payRecordDTOList.size()>0)
                    {
                        payRecordDTO = payRecordDTOList.get(0);
                        payRecordDTO.setStatus("2");
                        businessServiceClient.updatePayRecordStatus(payRecordDTO);
                    }
                }
            }
        }
        logger.info("每隔1分钟，将payRecord表中，状态为0的订单，进行状态调整处理,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    public void frozenUserType(String userType) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("完成用户的状态冻结的自动化操作==={}开始" , startTime);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(userType);
        userInfoDTO.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        for(UserInfoDTO userInfo : userInfoDTOList)
        {
            UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setSysUserId(userInfo.getId());
            userBusinessTypeDTO.setStatus("1");
            List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);

            if(userBusinessTypeDTOS.size()==0)
            {
                //为用户创建一个记录
                userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setId(UUID.randomUUID().toString());
                userBusinessTypeDTO.setSysUserId(userInfo.getId());
                userBusinessTypeDTO.setStatus("1");
                userBusinessTypeDTO.setParentUserId(userInfo.getParentUserId());
                userBusinessTypeDTO.setUserType(userInfo.getUserType());
                userBusinessTypeDTO.setCreateDate(new Date());
                businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);
            }
            else
            {
                userBusinessTypeDTO = userBusinessTypeDTOS.get(0);
            }

            Date dt1 = userBusinessTypeDTO.getCreateDate();
            Date dt2 = new Date((new Date()).getTime() - (long) userBusinessTypeDTO.getLivingPeriod() * 24 * 60 * 60 * 1000);
            Date dt3 = new Date((new Date()).getTime() - (long) (userBusinessTypeDTO.getLivingPeriod()-3) * 24 * 60 * 60 * 1000);

            //用户在365天前已经是目前的等级了
            if (dt2.getTime() > dt1.getTime())
            {
                logger.info("用户在365天前已经是目前的等级了,冻结此账户==={}开始",userBusinessTypeDTO.getSysUserId());
                userBusinessTypeDTO.setStatus("2");//2表示为冻结状态
                businessServiceClient.updateUserBusinessType(userBusinessTypeDTO);
            }

            if(dt3.getTime()> dt1.getTime())
            {
                String name = URLDecoder.decode(userInfo.getNickname(),"utf-8");
                String expDate = DateUtils.DateToStr(dt2);
                String token = WeixinUtil.getUserToken();
                String openid = userInfo.getUserOpenid();
                String url = ConfigConstant.USER_WEB_URL + "myselfCenter";
                WeixinTemplateMessageUtil.sendBusinessMemberDeadlineTemplateWXMessage(name,expDate,token,url,openid);
                logger.info("发送用户会员快到期提醒模板");
            }
        }
        logger.info("完成用户的状态冻结的自动化操作,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    public void monthlyIncomeCalc(String businessType) throws UnsupportedEncodingException {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(businessType);
        userInfoDTO.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        String token = WeixinUtil.getUserToken();
        for(UserInfoDTO userInfo:userInfoDTOList)
        {
            float returnMonthlyMoney = 0;
            float returnMonthlyMoney_A = 0;
            float returnMonthlyMoney_B = 0;

           /* String startDate = "";
            String endDate = DateUtils.getYear()+"-" + DateUtils.getMonth()+"-"+"15";
            if(DateUtils.getMonth().equals("01"))
            {
                int month = 11;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-15";
            }
            else if(DateUtils.getMonth().equals("02"))
            {
                int month = 12;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-15";
            }
            else{
                int month = Integer.parseInt(DateUtils.getMonth()) - 1;
                startDate = DateUtils.getYear() + "-" + month + "-15";
            }
*/

            String startDate ="2018-05-15 00:00:00";
            String endDate ="2018-06-09 23:59:59";

            List<MonthTransactionRecordDTO> monthTransactionRecordDTOList =  businessServiceClient.getMonthTransactionRecordByUserId(userInfo.getId(),startDate,endDate);

            for(MonthTransactionRecordDTO monthTransactionRecordDTO:monthTransactionRecordDTOList)
            {
                if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessA1))
                {
                    returnMonthlyMoney_A = returnMonthlyMoney_A + monthTransactionRecordDTO.getAmount();
                }
                else if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessB1))
                {
                    returnMonthlyMoney_B = returnMonthlyMoney_B + monthTransactionRecordDTO.getAmount();
                }
            }

            //计算当前用户本月的消费金额
            if(returnMonthlyMoney_A>0||returnMonthlyMoney_B>0)
            {
                returnMonthlyMoney = returnMonthlyMoney_A*ConfigConstant.MONTH_A_INCOME_PERCENTAGE/100 + returnMonthlyMoney_B*ConfigConstant.MONTH_B1_INCOME_PERCENTAGE/100;

                AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(userInfo.getId());
                float balance = accountDTO.getBalance() + returnMonthlyMoney;
                float balanceDeny = accountDTO.getBalanceDeny() + returnMonthlyMoney;
                accountDTO.setBalance(balance);
                accountDTO.setBalanceDeny(balanceDeny);
                accountDTO.setUpdateDate(new Date());
                businessServiceClient.updateUserAccountInfo(accountDTO);

                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                incomeRecordDTO.setId(UUID.randomUUID().toString());
                incomeRecordDTO.setSysUserId(userInfo.getId());
                incomeRecordDTO.setUserType(userInfo.getUserType());
                incomeRecordDTO.setNextUserId("");
                incomeRecordDTO.setNextUserType("");
                incomeRecordDTO.setAmount(returnMonthlyMoney);
                incomeRecordDTO.setTransactionAmount(0);
                incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
                incomeRecordDTO.setUpdateDate(new Date());
                incomeRecordDTO.setCreateDate(new Date());
                incomeRecordDTO.setStatus("0");
                incomeRecordDTO.setIdentifyNumber(userInfo.getIdentifyNumber());
                incomeRecordDTO.setNextUserIdentifyNumber("");
                incomeRecordDTO.setNickName(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
                incomeRecordDTO.setNextUserNickName("");
                incomeRecordDTO.setIncomeType("month");
                incomeRecordDTO.setMobile(userInfo.getMobile());
                incomeRecordDTO.setNextUserMobile("");
                incomeRecordDTO.setParentRelation("");
                businessServiceClient.insertUserIncomeInfo(incomeRecordDTO);
                WeixinTemplateMessageUtil.sendMonthIncomeTemplateWXMessage(userInfo.getId(),returnMonthlyMoney+"",DateUtils.DateToStr(new Date()),token,"",userInfo.getUserOpenid());
            }
        }
    }

    public void promoteUserBusinessTypeForRecommend() throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("同级推荐提升逻辑==={}开始" , startTime);
        //根据B用户推荐20个B的逻辑，来实现用户等级提升
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setDelFlag("0");
        userInfoDTO.setUserType(ConfigConstant.businessB1);
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        for(UserInfoDTO userInfo : userInfoDTOS)
        {
            timerMessageQueueSender.sendPromoteUserBusinessTypeForRecommend(userInfo);
        }
        logger.info("同级推荐提升逻辑,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    public void deFrozenUserReturnMoney(List<IncomeRecordDTO> incomeRecordDTOList, List<String> transactionIds) {
        long startTime = System.currentTimeMillis();
        logger.info("用户即时返现解冻==={}开始" , startTime);
        for(IncomeRecordDTO incomeRecord : incomeRecordDTOList)
        {
            timerMessageQueueSender.sendDeFrozenUserReturnMoney(incomeRecord);
        }
        logger.info("用户即时返现解冻,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    /**
     *手动跑月度
     *
     * */
    public void monthlyIncomeCalcM(String businessType,Date startDateM ,Date endDateM,Date startRangeM ,Date endRangeM)throws Exception{
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(businessType);
        userInfoDTO.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        for(UserInfoDTO userInfo:userInfoDTOList) {
            float returnMoney = this.getOneDayMonthMoney(userInfo,startDateM,endDateM);
            if(returnMoney>0){
                try{
                    this.insertIncome(returnMoney,userInfo,startRangeM,endRangeM);
                }catch (Exception e){
                    logger.info("用户"+userInfo.getMobile()+"startDateM"+"-"+"endDateM"+"插入月度返利表出问题");
                    this.insertMonthlyIncomeError(startDateM,userInfo,businessType);
                }
            }
        }
    }

    /**
     * 手动生成月度结算
     * */
    public void MTMonthlyIncomeCalc(String businessType,Date startDateM ,Date endDateM,String isPullMessage,String key) throws Exception{

        logger.info("准备处理"+startDateM+"到"+endDateM+"时间段"+businessType+"的月度");

        //将时间分成年月日
        SimpleDateFormat sdfYear  = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMon  = new SimpleDateFormat("MM");
        SimpleDateFormat sdfDay  = new SimpleDateFormat("dd");
        int startM = Integer.parseInt(sdfMon.format(startDateM));
        int endM = Integer.parseInt(sdfMon.format(endDateM));
        int startD = Integer.parseInt(sdfDay.format(startDateM));
        int endD = Integer.parseInt(sdfDay.format(endDateM));
        int startY = Integer.parseInt(sdfYear.format(startDateM));
        int EndY = Integer.parseInt(sdfYear.format(endDateM));

        //判断是否跨年
        if(startY==EndY){
            //判断是否 跨月份
            if(startM == endM){
                if(startD <= endD){
                    for(int i =startD;i<=endD;i++){

                        StringBuilder sbM = new StringBuilder();
                        StringBuilder sbD = new StringBuilder();

                        if(startM<10){
                            sbM.append("0").append(startM);
                        }else{
                            sbM.append(startM);
                        }
                        if(i<10){
                            sbD.append("0").append(i);
                        }else{
                            sbD.append(i);
                        }

                        //0代表着A类型和B类型都进行月度处理 1代表着A类型2代表着B类型
                        if(businessType.equals("0")){
                            this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                            this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                        }else if(businessType.equals("1")){
                            this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                        }else if(businessType.equals("2")){
                            this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                        }

                    }
                }
            }else if(startM < endM){
                for(int i =startM;i<=endM;i++){
                    StringBuilder sbM = new StringBuilder();
                    if(i<10){
                        sbM.append("0").append(i);
                    }else{
                        sbM.append(i);
                    }
                    int days = this.getDays(String.valueOf(startY),String.valueOf(i));
                    if(i == startM){
                        for(int j = startD;j<=days;j++){
                            StringBuilder sbD = new StringBuilder();
                            if(j<10){
                                sbD.append("0").append(j);
                            }else{
                                sbD.append(j);
                            }
                            if(businessType.equals("0")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }else if(businessType.equals("1")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                            }else if(businessType.equals("2")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }
                        }
                    }else if(i == endM){
                        for(int j = 1;j<=endD;j++){
                            StringBuilder sbD = new StringBuilder();
                            if(j<10){
                                sbD.append("0").append(j);
                            }else{
                                sbD.append(j);
                            }
                            if(businessType.equals("0")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }else if(businessType.equals("1")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                            }else if(businessType.equals("2")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }
                        }
                    }else{
                        for(int j=1;j<=days;j++){
                            StringBuilder sbD = new StringBuilder();
                            if(j<10){
                                sbD.append("0").append(j);
                            }else{
                                sbD.append(j);
                            }
                            if(businessType.equals("0")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }else if(businessType.equals("1")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                            }else if(businessType.equals("2")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }
                        }
                    }
                }
            }
        }else if(startY < EndY){
            for(int i=startY;i<=EndY;i++){
                if(startY == i){
                    for(int j = startM;j<=12;j++){
                        StringBuilder sbM = new StringBuilder();
                        if(j<10){
                            sbM.append("0").append(j);
                        }else{
                            sbM.append(j);
                        }
                        int days = this.getDays(String.valueOf(startY),String.valueOf(i));
                        if(j == startM){
                            for(int m = startD;m<=days;m++){
                                StringBuilder sbD = new StringBuilder();
                                if(m<10){
                                    sbD.append("0").append(m);
                                }else{
                                    sbD.append(m);
                                }
                                if(businessType.equals("0")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }else if(businessType.equals("1")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                }else if(businessType.equals("2")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }
                            }
                        }else{
                            for(int m=1;m<=days;m++){
                                StringBuilder sbD = new StringBuilder();
                                if(m<10){
                                    sbD.append("0").append(m);
                                }else{
                                    sbD.append(m);
                                }
                                if(businessType.equals("0")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }else if(businessType.equals("1")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                }else if(businessType.equals("2")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }
                            }
                        }
                    }
                }else if(i==EndY){
                    for(int j= 1;j <= endM;j++){
                        StringBuilder sbM = new StringBuilder();
                        if(j<10){
                            sbM.append("0").append(j);
                        }else{
                            sbM.append(j);
                        }
                        int days = this.getDays(String.valueOf(startY),String.valueOf(i));
                        if(i == endM){
                            for(int m = 1;m<=days;m++){
                                StringBuilder sbD = new StringBuilder();
                                if(m<10){
                                    sbD.append("0").append(m);
                                }else{
                                    sbD.append(m);
                                }
                                if(businessType.equals("0")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }else if(businessType.equals("1")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                }else if(businessType.equals("2")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }
                            }
                        }else{
                            for(int m=1;m<=endD;m++){
                                StringBuilder sbD = new StringBuilder();
                                if(m<10){
                                    sbD.append("0").append(m);
                                }else{
                                    sbD.append(m);
                                }
                                if(businessType.equals("0")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }else if(businessType.equals("1")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                }else if(businessType.equals("2")){
                                    this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                                }
                            }
                        }
                    }
                }else{
                    for(int j=1; j<=12;j++){
                        StringBuilder sbM = new StringBuilder();
                        if(j<10){
                            sbM.append("0").append(j);
                        }else{
                            sbM.append(j);
                        }
                        int days = this.getDays(String.valueOf(startY),String.valueOf(i));
                        for(int m=1;m<=days;m++){
                            StringBuilder sbD = new StringBuilder();
                            if(m<10){
                                sbD.append("0").append(m);
                            }else{
                                sbD.append(m);
                            }
                            if(businessType.equals("0")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }else if(businessType.equals("1")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessA1,startDateM ,endDateM);
                            }else if(businessType.equals("2")){
                                this.isProcessed(Integer.parseInt(sdfYear.format(startDateM)),sbM.toString(),sbD.toString(),ConfigConstant.businessB1,startDateM ,endDateM);
                            }
                        }
                    }
                }
            }
        }
        this.sendWeixinMessage(businessType,startDateM,endDateM,isPullMessage);
        JedisUtils.set(key,"true",3600);
        logger.info("生成月度完成！！！！");
    }

    /**
     * 获取当前月份有多少天
     *
     * */
    public Integer getDays(String years,String months){
        Integer year = Integer.parseInt(years);
        Integer mon = Integer.parseInt(months);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,mon-1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        return maxDate;
    }

    /**
     * 处理月度（判断是否已经处理过了）
     *
     *
     * */
    public float isProcessed(Integer year ,String month,String day,String businessType, Date startRangeM ,Date endRangeM)throws Exception{

        float returnMonthlyMoney =0;

        //按照类型和时间（具体到天加限制）
        Query query = new Query(Criteria.where("year").is(year.toString())).addCriteria(Criteria.where("month").is(month.toString())).addCriteria(Criteria.where("day").is(day.toString())).addCriteria(Criteria.where("businessType").is(businessType));
        MonthlyIncomeSignalDTO monthlyIncomeSignalDTO = mongoTemplate.findOne(query, MonthlyIncomeSignalDTO.class, "monthlyIncomeSignal");

        //将范围处理成时间一天的范围
        SimpleDateFormat sfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sfe = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sbs  = new StringBuilder();
        sbs.append(year).append("-").append(month).append("-").append(day).append(" ").append("00:00:00");
        StringBuilder sbe  = new StringBuilder();
        sbe.append(year).append("-").append(month).append("-").append(day).append(" ").append("23:59:59");

        if (monthlyIncomeSignalDTO == null) {

            monthlyIncomeSignalDTO = new MonthlyIncomeSignalDTO();
            monthlyIncomeSignalDTO.setYear(year.toString());
            monthlyIncomeSignalDTO.setMonth(month.toString());
            monthlyIncomeSignalDTO.setDay(day.toString());
            monthlyIncomeSignalDTO.setBusinessType(businessType);
            monthlyIncomeSignalDTO.setOnTimeFinish("false");
            mongoTemplate.insert(monthlyIncomeSignalDTO, "monthlyIncomeSignal");

            Date startDateM = sfs.parse(sbs.toString());
            Date endDateM = sfe.parse(sbe.toString());
            this.monthlyIncomeCalcM(businessType,startDateM,endDateM,startRangeM,endRangeM);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish", "true");
            mongoTemplate.updateFirst(query, update, "monthlyIncomeSignal");
        } else if (monthlyIncomeSignalDTO.getOnTimeFinish().equals("false")) {

            Date startDateM =sfs.parse(sbs.toString());
            Date endDateM = sfe.parse(sbe.toString());
            this.monthlyIncomeCalcM(businessType,startDateM,endDateM,startRangeM,endRangeM);

            //操作完毕后，关闭信号量
            Update update = new Update();
            update.set("onTimeFinish", "true");
            mongoTemplate.updateFirst(query, update, "monthlyIncomeSignal");
        }

        return returnMonthlyMoney;
    }

    /**
     * 计算一天的月度消费返利
     *
     * */
    public float getOneDayMonthMoney(UserInfoDTO userInfo,Date startDateM,Date endDateM){

        float returnMonthlyMoney = 0;
        float returnMonthlyMoney_A = 0;
        float returnMonthlyMoney_B = 0;


        String startDate =  DateUtils.formatDate(startDateM,"yyyy-MM-dd HH-mm-ss");
        String endDate = DateUtils.formatDate(endDateM,"yyyy-MM-dd HH-mm-ss");

        List<MonthTransactionRecordDTO> monthTransactionRecordDTOList =  businessServiceClient.getMonthTransactionRecordByUserId(userInfo.getId(),startDate,endDate);

        for(MonthTransactionRecordDTO monthTransactionRecordDTO:monthTransactionRecordDTOList)
        {
            if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessA1))
            {
                returnMonthlyMoney_A = returnMonthlyMoney_A + monthTransactionRecordDTO.getAmount();
            }
            else if(monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessB1))
            {
                returnMonthlyMoney_B = returnMonthlyMoney_B + monthTransactionRecordDTO.getAmount();
            }
        }
        if(returnMonthlyMoney_B>0||returnMonthlyMoney_A>0){
            returnMonthlyMoney = returnMonthlyMoney_A*ConfigConstant.MONTH_A_INCOME_PERCENTAGE/100 + returnMonthlyMoney_B*ConfigConstant.MONTH_B1_INCOME_PERCENTAGE/100;
        }

        return returnMonthlyMoney;
    }

    /***
     * 记录月度返利详情
     *
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public void insertIncome(float returnMonthlyMoney,UserInfoDTO userInfo,Date startRangeM ,Date endRangeM)throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(startRangeM)).append("-").append(sdf.format(endRangeM));
        String timeRangeId = sb.toString();

        //更新用户余额和不可提现余额
        AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(userInfo.getId());
        float balance = accountDTO.getBalance() + returnMonthlyMoney;
        float balanceDeny = accountDTO.getBalanceDeny() + returnMonthlyMoney;
        accountDTO.setBalance(balance);
        accountDTO.setBalanceDeny(balanceDeny);
        accountDTO.setUpdateDate(new Date());
        businessServiceClient.updateUserAccountInfo(accountDTO);

        //查询当前用户的当前时间范围的月度是否开始计算
        Query query = new Query(Criteria.where("timeRangeId").is(timeRangeId)).addCriteria(Criteria.where("sysUserId").is(userInfo.getId()));
        IncomeMonthDTO incomeMonthDTO = mongoTemplate.findOne(query, IncomeMonthDTO.class, "incomeMonth");
        if(incomeMonthDTO==null){
            IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
            incomeRecordDTO.setId(UUID.randomUUID().toString());
            incomeRecordDTO.setSysUserId(userInfo.getId());
            incomeRecordDTO.setUserType(userInfo.getUserType());
            incomeRecordDTO.setNextUserId("");
            incomeRecordDTO.setNextUserType("");
            incomeRecordDTO.setAmount(returnMonthlyMoney);
            incomeRecordDTO.setTransactionAmount(0);
            incomeRecordDTO.setTransactionId(CodeGenUtil.getTransactionCodeNumber());
            incomeRecordDTO.setUpdateDate(new Date());
            incomeRecordDTO.setCreateDate(new Date());
            incomeRecordDTO.setStatus("0");
            incomeRecordDTO.setIdentifyNumber(userInfo.getIdentifyNumber());
            incomeRecordDTO.setNextUserIdentifyNumber("");
            incomeRecordDTO.setNickName(userInfo.getNickname());
            incomeRecordDTO.setNextUserNickName("");
            incomeRecordDTO.setIncomeType("month");
            incomeRecordDTO.setMobile(userInfo.getMobile());
            incomeRecordDTO.setNextUserMobile("");
            incomeRecordDTO.setParentRelation("");
            businessServiceClient.insertUserIncomeInfo(incomeRecordDTO);

            IncomeMonthDTO incomeMonth = new IncomeMonthDTO();
            incomeMonth.setIncomeId(incomeRecordDTO.getId());
            incomeMonth.setTimeRangeId(timeRangeId);
            incomeMonth.setSysUserId(userInfo.getId());
            mongoTemplate.insert(incomeMonthDTO, "incomeMonth");
        }else{
            IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
            incomeRecordDTO.setId(incomeMonthDTO.getIncomeId());
            List<IncomeRecordDTO> incomeRecordDTOList = businessServiceClient.getUserIncomeRecordInfo(incomeRecordDTO);
            float amount = incomeRecordDTOList.get(0).getAmount()+returnMonthlyMoney;
            incomeRecordDTO.setAmount(amount);
            businessServiceClient.updateIncomeInfo(incomeRecordDTO);
        }

    }

    /**
     * 插入出错信息
     *
     * */

    public void insertMonthlyIncomeError(Date date,UserInfoDTO userInfo ,String businessType){

        SimpleDateFormat sdfYear  = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMon  = new SimpleDateFormat("MM");
        SimpleDateFormat sdfDay  = new SimpleDateFormat("dd");

        MonthlyIncomeErrorDTO monthlyIncomeError = new MonthlyIncomeErrorDTO();
        monthlyIncomeError.setYear(sdfYear.format(date).toString());
        monthlyIncomeError.setMonth(sdfMon.format(date).toString());
        monthlyIncomeError.setDay(sdfDay.format(date).toString());
        monthlyIncomeError.setBusinessType(businessType);
        monthlyIncomeError.setStatus("0");
        monthlyIncomeError.setSysUserId(userInfo.getId());

        mongoTemplate.insert(monthlyIncomeError, "MonthlyIncomeError");

    }


    /**
     * 发送微信消息
     * */
    public void sendWeixinMessage(String businessType,Date startRangeM ,Date endRangeM,String isPullMessage){

        String token = WeixinUtil.getUserToken();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(sdf.format(startRangeM)).append("-").append(sdf.format(endRangeM));
        String timeRangeId = sb.toString();

        UserInfoDTO userInfoDTOA = new UserInfoDTO();
        userInfoDTOA.setUserType(businessType);
        userInfoDTOA.setDelFlag("0");
        List<UserInfoDTO> userInfoDTOListA = userServiceClient.getUserInfo(userInfoDTOA);
        for(UserInfoDTO userInfo:userInfoDTOListA) {

            Query query = new Query(Criteria.where("timeRangeId").is(timeRangeId)).addCriteria(Criteria.where("sysUserId").is(userInfo.getId()));
            IncomeMonthDTO incomeMonthDTO = mongoTemplate.findOne(query, IncomeMonthDTO.class, "incomeMonth");
            IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
            incomeRecordDTO.setId(incomeMonthDTO.getIncomeId());
            if(incomeMonthDTO!=null){
                List<IncomeRecordDTO> incomeRecordDTOList = businessServiceClient.getUserIncomeRecordInfo(incomeRecordDTO);
                if(incomeRecordDTOList!=null){

                    logger.info("用户"+userInfo.getMobile()+"在时间"+timeRangeId+"的月度返利为"+incomeRecordDTOList.get(0).getAmount());
                    if(("1").equals(isPullMessage)&&incomeRecordDTOList.get(0).getAmount()>0){
                       // WeixinTemplateMessageUtil.sendMonthIncomeTemplateWXMessage(userInfo.getId(),incomeRecordDTOList.get(0).getAmount()+"",DateUtils.DateToStr(new Date()),token,"",userInfo.getUserOpenid());
                    }
                }
            }

        }


    }

}
