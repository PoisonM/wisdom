package com.wisdom.business.mqsender;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.business.service.transaction.PayCoreService;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.InstanceReturnMoneySignalDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class BusinessMessageQueueSender {

    Logger logger = LoggerFactory.getLogger(BusinessMessageQueueSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    private static Gson gson = new Gson();

    public void sendNotifySpecialShopBossCustomerTransaction(BusinessOrderDTO businessOrderDTO) {
        String businessOrderStr = gson.toJson(businessOrderDTO);
        logger.info("Sender : " + businessOrderStr);
        this.rabbitTemplate.convertAndSend("notifySpecialShopBossCustomerTransaction", businessOrderStr);
    }

    public void sendHandleInstanceReturnMoney(UserInfoDTO userInfoDTO,InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO) {
        logger.info("根据支付交易，处理及时返利=="+instanceReturnMoneySignalDTO);
        String userInfoStr = gson.toJson(userInfoDTO);
        String instanceReturnMoneySignalDTOStr = gson.toJson(instanceReturnMoneySignalDTO);
        String handleInstanceReturnMoneyMessage = userInfoStr +"########" +instanceReturnMoneySignalDTOStr;
        logger.info("send handleInstanceReturnMoneyMessage message==="+handleInstanceReturnMoneyMessage);
        this.rabbitTemplate.convertAndSend("handleInstanceReturnMoney", handleInstanceReturnMoneyMessage);
    }

    public void sendRecordMonthTransaction(UserInfoDTO userInfoDTO,InstanceReturnMoneySignalDTO instanceReturnMoneySignalDTO,float expenseMoney) {
        logger.info("记录月度流水=="+instanceReturnMoneySignalDTO.getSysUserId());
        String userInfoStr = gson.toJson(userInfoDTO);
        String instanceReturnMoneySignalDTOStr = gson.toJson(instanceReturnMoneySignalDTO);
        String sendRecordMonthTransactionMessage = userInfoStr + "########" + instanceReturnMoneySignalDTOStr + "########" + String.valueOf(expenseMoney);
        logger.info("send recordMonthTransaction message==="+sendRecordMonthTransactionMessage);
        this.rabbitTemplate.convertAndSend("recordMonthTransaction", sendRecordMonthTransactionMessage);
    }

    public void sendHandleUserLevelPromotion(UserInfoDTO userInfoDTO, float expenseMoney) {
        logger.info("处理用户等级提升===="+userInfoDTO.getId());
        String userInfoStr = gson.toJson(userInfoDTO);
        String sendHandleUserLevelPromotionMessage = userInfoStr + "########" +  String.valueOf(expenseMoney);
        logger.info("send handleUserLevelPromotion message==="+sendHandleUserLevelPromotionMessage);
        this.rabbitTemplate.convertAndSend("handleUserLevelPromotion", sendHandleUserLevelPromotionMessage);
    }



}
