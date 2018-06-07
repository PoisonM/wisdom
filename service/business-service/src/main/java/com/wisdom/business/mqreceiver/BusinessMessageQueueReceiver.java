package com.wisdom.business.mqreceiver;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BusinessMessageQueueReceiver {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PayRecordService payRecordService;

    private static Gson gson = new Gson();

    @RabbitListener(queues = "notifySpecialShopBossCustomerTransaction")
    @RabbitHandler
    public void processNotifySpecialShopBossCustomerTransaction(String notifySpecialShopBossCustomerTransaction) throws ClientException {

        System.out.println("Receiver  : " + notifySpecialShopBossCustomerTransaction);
        BusinessOrderDTO businessOrderDTO = gson.fromJson(notifySpecialShopBossCustomerTransaction,BusinessOrderDTO.class);

        String token = WeixinUtil.getUserToken();

        PayRecordDTO payRecordDTO = new PayRecordDTO();
        payRecordDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
        List<PayRecordDTO> payRecordDTOS = payRecordService.getUserPayRecordList(payRecordDTO);

        if(payRecordDTOS.size()>0)
        {
            Query query = new Query(Criteria.where("order").is(businessOrderDTO.getBusinessOrderId()));
            SpecialShopBusinessOrderDTO specialShopBusinessOrderDTO = mongoTemplate.findOne(query,SpecialShopBusinessOrderDTO.class,"specialShopBusinessOrder");
            if(specialShopBusinessOrderDTO!=null)
            {
                String shopId = specialShopBusinessOrderDTO.getShopId();
                query = new Query(Criteria.where("shopId").is(shopId));
                SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");
                UserInfoDTO userInfoDTO = new UserInfoDTO();
                userInfoDTO.setMobile(specialShopInfoDTO.getShopBossMobile());
                List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
                if(userInfoDTOList.size()>0)
                {
                    WeixinTemplateMessageUtil.sendSpecialShopBossUserBuyTemplateWXMessage(token,payRecordDTOS.get(0).getAmount()+"元",businessOrderDTO,userInfoDTOList.get(0).getUserOpenid(),specialShopInfoDTO);
                }
                else
                {
                    //直接给店主发送短信
                    SMSUtil.sendSpecialShopBossTransactionInfo(specialShopInfoDTO.getShopBossMobile(),payRecordDTO.getAmount()+"元",businessOrderDTO,specialShopInfoDTO);
                }
            }
        }
    }


}
