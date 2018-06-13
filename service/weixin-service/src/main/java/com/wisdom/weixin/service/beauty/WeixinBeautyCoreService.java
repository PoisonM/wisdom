package com.wisdom.weixin.service.beauty;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.TextMessage;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class WeixinBeautyCoreService {
    Logger logger = LoggerFactory.getLogger(WeixinBeautyCoreService.class);

    @Autowired
    ProcessBeautyViewEventService processBeautyClickViewEvent;

    @Autowired
    ProcessBeautyScanEventService processBeautyScanEventService;

    @Autowired
    ProcessBeautySubscribeEventService processBeautySubscribeEventService;

    @Autowired
    ProcessBeautyClickEventService processBeautyClickEventService;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public String processBeautyWeixinRequest(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        logger.info("调用核心业务类接收消息、处理消息==={}开始" , startTime);
        String respMessage = null;

        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(WeixinUtil.getXmlDataFromWeixin(request));
        String msgType = xmlEntity.getMsgType();
        logger.info("解析xml数据msgType={}" ,msgType);

        // xml请求解析
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
        {
            // 事件类型
            String eventType = xmlEntity.getEvent();
            logger.info("事件类型eventType={}" ,eventType);
            if(eventType.equals(MessageUtil.SCAN))
            {
                //已关注公众号的情况下扫描
                logger.info("已关注公众号的情况下扫描");
                processBeautyScanEventService.processEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
            {
                //扫描关注公众号或者搜索关注公众号都在其中
                logger.info("扫描关注公众号或者搜索关注公众号都在其中");
                processBeautySubscribeEventService.processSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
            {
                // 取消订阅
                logger.info("取消订阅");
                processBeautySubscribeEventService.processUnSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK))
            {
                // 自定义菜单点击事件
                logger.info("自定义菜单点击事件");
                respMessage = processBeautyClickEventService.processEvent(xmlEntity,request,response);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW))
            {
                //点击带URL菜单事件
                logger.info("点击带URL菜单事件");
                processBeautyClickViewEvent.processEvent(xmlEntity);
            }
        }
        else
        {
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(xmlEntity.getFromUserName());
            textMessage.setFromUserName(xmlEntity.getToUserName());
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.TRANSFER_USER_SERVICE);
            textMessage.setFuncFlag(0);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        logger.info("调用核心业务类接收消息、处理消息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return respMessage;
    }

    public void updateBeautyWeixinToken() {
        try {
            logger.info("用户端微信参数更新==={}开始");
            System.out.print("用户端微信参数更新");
            String token = WeixinUtil.getUserTokenFromTX(ConfigConstant.BOSS_CORPID, ConfigConstant.BOSS_SECRET);
            if(StringUtils.isNotNull(token)) {
                String ticket = WeixinUtil.getJsapiTicket(token);
                Query query = new Query().addCriteria(Criteria.where("weixinFlag").is(ConfigConstant.weixinBossFlag));
                Update update = new Update();
                update.set("token",token);
                update.set("ticket",ticket);
                update.set("updateDate",new Date());
                mongoTemplate.updateFirst(query,update,"weixinParameter");
            }
        } catch (Exception e) {
            logger.info("用户端微信参数更新异常,异常信息为={}"+e.getMessage(),e);
            e.printStackTrace();
        }
    }

}
