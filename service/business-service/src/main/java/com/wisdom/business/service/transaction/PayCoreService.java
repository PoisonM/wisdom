package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.mapper.transaction.PromotionTransactionRelationMapper;
import com.wisdom.business.mqsender.BusinessMessageQueueSender;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.transaction.*;
import com.wisdom.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wisdom.common.constant.ConfigConstant.RECOMMEND_PROMOTE_A1_REWARD;

/**
 * Created by sunxiao on 2017/6/26.
 */

@Service
public class PayCoreService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private PayFunction payFunction;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private BusinessMessageQueueSender businessMessageQueueSender;

    Logger logger = LoggerFactory.getLogger(PayCoreService.class);

    private static ExecutorService threadExecutorSingle = Executors.newSingleThreadExecutor();

    @Transactional(rollbackFor = Exception.class)
    public void handleProductPayNotifyInfo(PayRecordDTO payRecordDTO,String notifyType) {

        logger.info("处理微信平台推送的支付成功消息=="+payRecordDTO);

        RedisLock redisLock = new RedisLock("userPay" + payRecordDTO.getOutTradeNo());

        try {
            redisLock.lock();
            PayRecordDTO payRecordDTO2 = new PayRecordDTO();
            payRecordDTO2.setOutTradeNo(payRecordDTO.getOutTradeNo());
            List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO2);

            payFunction.processPayStatus(payRecordDTOList);

            if(notifyType.equals("offline"))
            {
                //实时返现不跟交易放在一起，在此设置一个信号灯
                payRecordDTO = payRecordDTOList.get(0);
                InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO = new InstanceReturnMoneySignalDTO();
                instanceReturnMoneySignalDTO.setSysUserId(payRecordDTO.getSysUserId());
                instanceReturnMoneySignalDTO.setOutTradeNo(payRecordDTO.getOutTradeNo());
                instanceReturnMoneySignalDTO.setTransactionId(payRecordDTO.getTransactionId());
                instanceReturnMoneySignalDTO.setStatus("1");
                mongoTemplate.insert(instanceReturnMoneySignalDTO,"instanceReturnMoneySignal");

                //开启一个线程，进行即时返现和提升处理
                Runnable processInstancePayThread = new ProcessInstancePayThread(instanceReturnMoneySignalDTO);
                threadExecutorSingle.execute(processInstancePayThread);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisLock.unlock();
        }
    }

    //进入即时返现和提升处理线程
    private class ProcessInstancePayThread extends Thread {
        private InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO;

        public ProcessInstancePayThread(InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
            this.instanceReturnMoneySignalDTO = instanceReturnMoneySignalDTO;
        }

        @Override
        public void run() {
            try
            {
                //todo 开启A、B店的规则，给不同的用户给与不同的金额入账，此处涉及到修改用户的account表和income表
                UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(instanceReturnMoneySignalDTO.getSysUserId());

                businessMessageQueueSender.sendHandleInstanceReturnMoney(userInfoDTO,instanceReturnMoneySignalDTO);

                float expenseMoney = payFunction.calculateUserExpenseMoney(instanceReturnMoneySignalDTO);

                businessMessageQueueSender.sendRecordMonthTransaction(userInfoDTO,instanceReturnMoneySignalDTO,expenseMoney);

                logger.info("处理用户消推荐返利的活动=="+userInfoDTO.getMobile());
                payFunction.shareRebate(userInfoDTO,instanceReturnMoneySignalDTO);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
