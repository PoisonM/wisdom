package com.wisdom.timer.mqsender;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.InstanceReturnMoneySignalDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class TimerMessageQueueSender {

    Logger logger = LoggerFactory.getLogger(TimerMessageQueueSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private static Gson gson = new Gson();


    public void sendConfirmReceiveProduct(BusinessOrderDTO businessOrder) {
        logger.info("sendConfirmReceiveProduct==="+businessOrder.getOrderId());
        String businessOrderStr = gson.toJson(businessOrder);
        this.rabbitTemplate.convertAndSend("confirmReceiveProduct", businessOrderStr);
    }

    public void sendDeFrozenUserReturnMoney(IncomeRecordDTO incomeRecord) {
        logger.info("sendDeFrozenUserReturnMoney==="+incomeRecord.getId());
        String incomeRecordStr = gson.toJson(incomeRecord);
        this.rabbitTemplate.convertAndSend("deFrozenUserReturnMoney", incomeRecordStr);
    }

    public void sendFrozenUserType(UserInfoDTO userInfoDTO) {
        logger.info("sendFrozenUserType===" + userInfoDTO.getId());
        WeixinUtil.getUserToken();
        String userInfoDTOStr = gson.toJson(userInfoDTO);
        this.rabbitTemplate.convertAndSend("frozenUserType", userInfoDTOStr);
    }

    public void sendPromoteUserBusinessTypeForRecommend(UserInfoDTO userInfo) {
        logger.info("sendPromoteUserBusinessTypeForRecommend==="+userInfo.getId());
        String userInfoStr = gson.toJson(userInfo);
        this.rabbitTemplate.convertAndSend("promoteUserBusinessTypeForRecommend", userInfoStr);
    }
}
