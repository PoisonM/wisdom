package com.wisdom.weixin.service.beauty;


import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class ProcessBeautyScanEventService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    public void processEvent(ReceiveXmlEntity xmlEntity)
    {
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinBossFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();

        //开启线程，给关注的用户推送微信消息
        Runnable sendSubscribeMessageThread = new SendScanMessageThread(token, xmlEntity);
        threadExecutorCached.execute(sendSubscribeMessageThread);
    }

    private class SendScanMessageThread extends Thread {
        private String token;
        private ReceiveXmlEntity xmlEntity;

        public SendScanMessageThread(String token, ReceiveXmlEntity xmlEntity) {
            this.token = token;
            this.xmlEntity = xmlEntity;
        }

        @Override
        public void run() {

            String openId = xmlEntity.getFromUserName();

            String shopId = "";
            if(StringUtils.isNotNull(xmlEntity.getEventKey())){
                shopId = xmlEntity.getEventKey().replace("beautyShop", "");
                String codeArray[] = shopId.split("_");
                shopId = codeArray[1];
            }

            List<Article> articleList = new ArrayList<>();
            Article article = new Article();
            article.setTitle("欢迎再次光临! \n");
            article.setDescription("我们是美享商城，在这里，将会为您实时传递最好的美享服务。");
            article.setPicUrl("");
            article.setUrl("");
            articleList.add(article);
            WeixinUtil.senImgMsgToWeixin(token,xmlEntity.getFromUserName(),articleList);

            //根据shopId和openId查询用户是否绑定了此美容院
            JedisUtils.set(shopId+openId,"alreadyBind",ConfigConstant.logintokenPeriod);
            JedisUtils.set(shopId+openId,"notBind",ConfigConstant.logintokenPeriod);


        }
    }

}
