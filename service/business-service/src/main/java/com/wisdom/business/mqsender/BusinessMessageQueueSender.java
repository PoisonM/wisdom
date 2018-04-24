package com.wisdom.business.mqsender;

import com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import org.springframework.amqp.core.AmqpTemplate;
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

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    private static Gson gson = new Gson();

    public void sendNotifySpecialShopBossCustomerTransaction(BusinessOrderDTO businessOrderDTO) {
        String businessOrderStr = gson.toJson(businessOrderDTO);
        System.out.println("Sender : " + businessOrderStr);
        this.rabbitTemplate.convertAndSend("notifySpecialShopBossCustomerTransaction", businessOrderStr);
    }

}
