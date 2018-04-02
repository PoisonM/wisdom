package com.wisdom.weixin.service.beauty;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.TextMessage;
import com.wisdom.common.util.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class WeixinBossCoreService {

    @Autowired
    ProcessBossViewEventService processBossClickViewEvent;

    @Autowired
    ProcessBossScanEventService processBossScanEventService;

    @Autowired
    ProcessBossSubscribeEventService processBossSubscribeEventService;

    @Autowired
    ProcessBossClickEventService processBossClickEventService;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public String processBossWeixinRequest(HttpServletRequest request, HttpServletResponse response) {

        String respMessage = null;

        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(WeixinUtil.getXmlDataFromWeixin(request));
        String msgType = xmlEntity.getMsgType();

        // xml请求解析
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
        {
            // 事件类型
            String eventType = xmlEntity.getEvent();
            if(eventType.equals(MessageUtil.SCAN))
            {
                //已关注公众号的情况下扫描
                processBossScanEventService.processEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
            {
                //扫描关注公众号或者搜索关注公众号都在其中
                processBossSubscribeEventService.processSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
            {
                // 取消订阅
                processBossSubscribeEventService.processUnSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK))
            {
                // 自定义菜单点击事件
                respMessage = processBossClickEventService.processEvent(xmlEntity,request,response);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW))
            {
                //点击带URL菜单事件
                processBossClickViewEvent.processEvent(xmlEntity);
            }
        }
        else
        {
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(xmlEntity.getFromUserName());
            textMessage.setFromUserName(xmlEntity.getToUserName());
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.TRANSFER_CUSTOMER_SERVICE);
            textMessage.setFuncFlag(0);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        return respMessage;
    }
}
