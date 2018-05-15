package com.wisdom.weixin.service.beauty;


import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.weixin.client.BeautyServiceClient;
import com.wisdom.weixin.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @Autowired
    private BeautyServiceClient beautyServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

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
            String userId = "";
            if(StringUtils.isNotNull(xmlEntity.getEventKey())){
                shopId = xmlEntity.getEventKey().replace("beautyShop", "");
                String codeArray[] = shopId.split("_");
                shopId = codeArray[1];
                userId = codeArray[2];
            }

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(userId);
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);

            if(userInfoDTOList.size()>0)
            {
                //用户之前关注过
                userInfoDTO = userInfoDTOList.get(0);
                if(StringUtils.isBlank(userInfoDTO.getWeixinAttentionStatus())){
                    userInfoDTO.setWeixinAttentionStatus("1");
                }else{
                    if(userInfoDTO.getWeixinAttentionStatus().equals("0"))
                    {
                        userInfoDTO.setWeixinAttentionStatus("1");
                    }
                }
                String nickname = null;
                try {
                    nickname = URLEncoder.encode(userInfoDTO.getNickname(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userInfoDTO.setNickname(nickname);
                userInfoDTO.setUserOpenid(openId);
                userInfoDTO.setLoginIp("");
                userServiceClient.updateUserInfo(userInfoDTO);

                //根据shopId和openId查询用户是否绑定了此美容院
                ResponseDTO<String> responseDTO = beautyServiceClient.getUserBindingInfo(openId,shopId);
                if("N".equals(responseDTO.getResponseData()))
                {
                    System.out.println("redis中设置的key为 "+shopId+"_"+userId);
                    JedisUtils.set(shopId+"_"+userId,"notBind",ConfigConstant.logintokenPeriod);
                }
                else if("Y".equals(responseDTO.getResponseData()))
                {
                    JedisUtils.set(shopId+"_"+userId,"alreadyBind",ConfigConstant.logintokenPeriod);
                }
            }

            List<Article> articleList = new ArrayList<>();
            Article article = new Article();
            article.setTitle("欢迎再次光临! \n");
            article.setDescription("我们是美享商城，在这里，将会为您实时传递最好的美享服务。");
            article.setPicUrl("");
            article.setUrl("");
            articleList.add(article);
            WeixinUtil.senImgMsgToWeixin(token,xmlEntity.getFromUserName(),articleList);
        }
    }

}
