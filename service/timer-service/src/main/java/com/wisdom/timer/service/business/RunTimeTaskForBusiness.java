package com.wisdom.timer.service.business;

import com.wisdom.common.util.WeixinUtil;
import com.wisdom.timer.client.WeixinServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sunxiao on 2017/9/14.
 */
@Configuration
@EnableScheduling
public class RunTimeTaskForBusiness {

    @Autowired
    WeixinServiceClient weixinServiceClient;

    @Autowired
    BusinessRunTimeService transactionRunTimeService;


    //每隔60分钟，更新一次公众号的Token
    @Scheduled(cron="0 */60 * * * ?")
    public void updateUserWeixinToken(){
        weixinServiceClient.updateUserWeixinToken();
    }

    //每隔10分钟，将payRecord表中，状态为0的订单，进行状态调整处理
    @Scheduled(cron="0 */1 * * * ?")
    public void processNoPayRecordData(){
        transactionRunTimeService.autoProcessNoPayRecordData();
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void autoTaskEveryDayInMorning() throws Exception {

        //用户15天后，自动转为收到货物
        transactionRunTimeService.autoConfirmReceiveProduct();

        //完成用户的状态冻结的自动化操作
        transactionRunTimeService.autoCalculateUserType();

        //用户即时返现解冻和用户等级提升的批量处理
        transactionRunTimeService.autoProcessUserAccount();

        //判断是不是本月的25号，若是25号，则进行月度提成计算
        Calendar now = Calendar.getInstance();
        String day = now.get(Calendar.DAY_OF_MONTH) + "";
        if(day.equals("26")){
            transactionRunTimeService.autoMonthlyIncomeCalc();
        }
    }

}
