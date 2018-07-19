package com.wisdom.weixin.service.beauty;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinAttentionDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.weixin.client.UserBeautyServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class ProcessBeautySubscribeEventService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private UserServiceClient userServiceClient;

    @Autowired
    private UserBeautyServiceClient userBeautyServiceClient;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    //处理用户关注公众号事件
    public void processSubscribeEvent(ReceiveXmlEntity xmlEntity)
    {
        long startTime = System.currentTimeMillis();
        logger.info("处理用户关注公众号事件==={}开始" , startTime);
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinBossFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();

        //开启线程，处理用户是扫描关注的用户，还是搜索关注公众号的用户
        Runnable processSubscribeThread = new ProcessSubscribeThread(token,xmlEntity);
        threadExecutorCached.execute(processSubscribeThread);

        //开启线程，给关注的用户推送微信消息
        logger.info("开启线程，给关注的用户token={}推送微信消息==={}开始" ,token);
        Runnable sendSubscribeMessageThread = new SendSubscribeMessageThread(token, xmlEntity);
        threadExecutorCached.execute(sendSubscribeMessageThread);
        logger.info("处理用户关注公众号事件,耗时{}毫秒", (System.currentTimeMillis() - startTime));
    }

    private class ProcessSubscribeThread extends Thread {

        private String token;
        private ReceiveXmlEntity xmlEntity;

        public ProcessSubscribeThread(String token, ReceiveXmlEntity xmlEntity) {
            this.token = token;
            this.xmlEntity = xmlEntity;
        }

        @Override
        public void run() {

            String openId = xmlEntity.getFromUserName();

            //判断用户是否是扫码微商用户的二维码关注，
            String shopId = "";
            String userId = "";
            if(StringUtils.isNotNull(xmlEntity.getEventKey())){
                shopId = xmlEntity.getEventKey().replace("beautyShop", "");
                String codeArray[] = shopId.split("_");
                shopId = codeArray[1];
                userId = codeArray[2];
            }

            //为关注公众号的用户创建新的或修订之前的账户
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(userId);
            List<UserInfoDTO> userInfoDTOList = userBeautyServiceClient.getUserInfo(userInfoDTO);

            if(userInfoDTOList.size()>0)
            {
                //用户之前关注过
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getWeixinAttentionStatus().equals("0"))
                {
                    userInfoDTO.setWeixinAttentionStatus("1");
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
                userBeautyServiceClient.updateUserInfo(userInfoDTO);

                //根据shopId和openId查询用户是否绑定了此美容院
                ResponseDTO<String> responseDTO = new ResponseDTO<String>();
                if("N".equals(responseDTO.getResponseData()))
                {
                    logger.info("根据shopId和openId查询,用户绑定了此美容院,redis中设置的key为 "+shopId+"_"+userId);
                    JedisUtils.set(shopId+"_"+userInfoDTO.getId(),"notBind",ConfigConstant.logintokenPeriod);
                }
                else if("Y".equals(responseDTO.getResponseData()))
                {
                    logger.info("根据shopId和openId查询,用户未绑定了此美容院,redis中设置已经绑定过的的key为"+shopId+"_"+userId);
                    JedisUtils.set(shopId+"_"+userInfoDTO.getId(),"alreadyBind",ConfigConstant.logintokenPeriod);
                }
            }

            //为用户的此次关注插入到mongodb记录中
            WeixinAttentionDTO weixinAttentionDTO = new WeixinAttentionDTO();
            weixinAttentionDTO.setDate(new Date());
            weixinAttentionDTO.setOpenid(openId);
            weixinAttentionDTO.setParentUserId(userInfoDTO.getParentUserId());
            weixinAttentionDTO.setStatus("1");
            mongoTemplate.insert(weixinAttentionDTO, "weixinAttention");
        }
    }

    private class SendSubscribeMessageThread extends Thread {
        private String token;
        private ReceiveXmlEntity xmlEntity;

        public SendSubscribeMessageThread(String token, ReceiveXmlEntity xmlEntity) {
            this.token = token;
            this.xmlEntity = xmlEntity;
        }

        @Override
        public void run() {
            List<Article> articleList = new ArrayList<>();
            Article article = new Article();
            article.setTitle("嗨!您终于来啦! ~\n");
            article.setDescription(
                    "在这里,可以边赚钱边美美哒 ~  \n" +
                    " \n" +
                    "点击「99课堂」，教你玩转社群营销 ~\n" +
                    "点击「99商城」，分享即赚钱 ~\n" +
                    "更多资讯,直接留言 ~");
            article.setPicUrl("");
            article.setUrl("");
            articleList.add(article);
            WeixinUtil.senImgMsgToWeixin(token,xmlEntity.getFromUserName(),articleList);
        }
    }

    //处理用户取消关注公众号事件
    public void processUnSubscribeEvent(ReceiveXmlEntity xmlEntity)
    {
        //开启线程，处理用户的取消关注事件
        logger.info("开启线程，处理用户的取消关注事件" );
        Runnable processUnSubscribeThread = new ProcessUnSubscribeThread(xmlEntity);
        threadExecutorCached.execute(processUnSubscribeThread);
    }

    private class ProcessUnSubscribeThread extends Thread{

        private ReceiveXmlEntity xmlEntity;

        public ProcessUnSubscribeThread(ReceiveXmlEntity xmlEntity) {
            this.xmlEntity = xmlEntity;
        }

        @Override
        public void run() {
            logger.info("开启线程，处理用户的取消关注事件==={}开始,修改sys_user表中微信关注状态" );
            //修改sys_user表中微信关注状态
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(xmlEntity.getFromUserName());
            List<UserInfoDTO> userInfoDTOList = userBeautyServiceClient.getUserInfo(userInfoDTO);
            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                userInfoDTO.setWeixinAttentionStatus("0");
                userBeautyServiceClient.updateUserInfo(userInfoDTO);
            }

            //为用户的此次取消关注插入到mongodb记录中
            logger.info("为用户的此次取消关注插入到mongodb记录中" );
            WeixinAttentionDTO weixinAttentionDTO = new WeixinAttentionDTO();
            weixinAttentionDTO.setDate(new Date());
            weixinAttentionDTO.setOpenid(xmlEntity.getFromUserName());
            weixinAttentionDTO.setParentUserId(userInfoDTO.getParentUserId());
            weixinAttentionDTO.setStatus("0");
            mongoTemplate.insert(weixinAttentionDTO, "weixinAttention");

        }
    }

}
