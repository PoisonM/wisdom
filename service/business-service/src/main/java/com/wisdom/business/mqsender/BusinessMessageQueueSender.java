package com.wisdom.business.mqsender;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class BusinessMessageQueueSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    private static Gson gson = new Gson();

    public void sendNotifySpecialShopBossCustomerTransaction(BusinessOrderDTO businessOrderDTO) {
        String businessOrderStr = gson.toJson(businessOrderDTO);
        System.out.println("Sender : " + businessOrderStr);
        this.rabbitTemplate.convertAndSend("notifySpecialShopBossCustomerTransaction", businessOrderStr);
    }

}
