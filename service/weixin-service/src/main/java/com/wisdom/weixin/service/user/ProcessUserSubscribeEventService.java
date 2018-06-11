package com.wisdom.weixin.service.user;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.transaction.BonusFlagDTO;
import com.wisdom.common.dto.wexin.WeixinAttentionDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.weixin.client.BusinessServiceClient;
import com.wisdom.weixin.client.UserServiceClient;
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
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class ProcessUserSubscribeEventService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private BusinessServiceClient businessServiceClient;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    //处理用户关注公众号事件
    public void processUserSubscribeEvent(ReceiveXmlEntity xmlEntity)
    {
        long startTime = System.currentTimeMillis();
        logger.info("处理用户关注公众号事件==={}开始" , startTime);
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();

        //开启线程，处理用户是扫描关注的用户，还是搜索关注公众号的用户
        logger.info("开启线程，处理用户token={}是扫描关注的用户，还是搜索关注公众号的用户==={}开始" ,token);

        Runnable processSubscribeThread = new ProcessSubscribeThread(token,xmlEntity);
        threadExecutorCached.execute(processSubscribeThread);

        //开启线程，给关注的用户推送微信消息
        logger.info("开启线程，给关注的用户token={}推送微信消息==={}开始" ,token);
        Runnable sendSubscribeMessageThread = new SendSubscribeMessageThread(token, xmlEntity);
        threadExecutorCached.execute(sendSubscribeMessageThread);
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
            logger.info("判断用户openId={}是否是扫码微商用户的二维码关注",openId);
            String businessParentPhone = "";
            if(StringUtils.isNotNull(xmlEntity.getEventKey())&&xmlEntity.getEventKey().indexOf("mxbusinessshare_")!=-1){
                businessParentPhone = xmlEntity.getEventKey().replace("mxbusinessshare_", "");
                String codeArray[] = businessParentPhone.split("_");
                businessParentPhone = codeArray[1];
            }
            else if(StringUtils.isNotNull(xmlEntity.getEventKey())&&xmlEntity.getEventKey().indexOf("mxForeignPurchase")!=-1)
            {
                String weishiyiShop = xmlEntity.getEventKey().replace("mxForeignPurchase_", "");
                String codeArray[] = weishiyiShop.split("_");
                String specialShopId = codeArray[1];

                //通过shopId查询出店铺名称
                logger.info("通过shopId={}查询出店铺名称",specialShopId);
                Query query = new Query(Criteria.where("shopId").is(specialShopId));
                SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");

                businessParentPhone = specialShopInfoDTO.getShopBossMobile();
            }

            //为关注公众号的用户创建新的或修订之前的账户
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);

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
                //userInfoDTO.setParentUserId(parentUserInfoDTO.getId());
                userInfoDTO.setLoginIp("");
                userServiceClient.updateUserInfo(userInfoDTO);
            }
            else
            {
                //获取用户的微信信息
                WeixinUserBean weixinUserBean = WeixinUtil.getWeixinUserBean(token,openId);

                UserInfoDTO parentUserInfoDTO = new UserInfoDTO();
                if(!businessParentPhone.equals(""))
                {
                    parentUserInfoDTO.setMobile(businessParentPhone);
                    //从sys_user表中，查询父一级用户信息
                    List<UserInfoDTO> parentUserInfoDTOList = userServiceClient.getUserInfo(parentUserInfoDTO);
                    if(parentUserInfoDTOList!=null&&parentUserInfoDTOList.size()>0){
                        for(int i=0; i<parentUserInfoDTOList.size();i++){
                            if(!parentUserInfoDTOList.get(i).getUserType().equals("finance-1")){
                                parentUserInfoDTO = parentUserInfoDTOList.get(i);
                            }
                        }
                        if(null != parentUserInfoDTO){
                            //向父节点推送消息
                            WeixinTemplateMessageUtil.sendLowLevelBusinessTemplateWXMessage(weixinUserBean.getNickname(),"c级代理商","0元",token,
                                    "",parentUserInfoDTO.getUserOpenid());
                        }
                    }
                }

                //用户第一次关注
                userInfoDTO.setId(UUID.randomUUID().toString());
                String nickname = null;
                try {
                    nickname = URLEncoder.encode(weixinUserBean.getNickname(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userInfoDTO.setNickname(nickname);
                userInfoDTO.setUserType(ConfigConstant.businessC1);
                userInfoDTO.setParentUserId(parentUserInfoDTO.getId());
                userInfoDTO.setWeixinAttentionStatus("1");
                userInfoDTO.setPhoto(weixinUserBean.getHeadimgurl());
                userInfoDTO.setDelFlag("0");
                userInfoDTO.setLoginIp("");
                userInfoDTO.setCreateDate(new Date());
                userServiceClient.insertUserInfo(userInfoDTO);

                //为用户新建一个账户
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(UUID.randomUUID().toString());
                accountDTO.setSysUserId(userInfoDTO.getId());
                accountDTO.setUserOpenId(userInfoDTO.getUserOpenid());
                accountDTO.setBalance((float)0.00);
                accountDTO.setScore((float)0.00);
                accountDTO.setStatus("normal");
                accountDTO.setUpdateDate(new Date());
                accountDTO.setBalanceDeny((float)0.00);
                businessServiceClient.createUserAccount(accountDTO);

                //查询用户当前user_business_type中是否有记录，没有则创建用户为C用户
                UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
                userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
                userBusinessTypeDTO.setStatus("1");
                List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
                if(userBusinessTypeDTOS.size()==0)
                {
                    userBusinessTypeDTO.setId(UUID.randomUUID().toString());
                    userBusinessTypeDTO.setParentUserId("");
                    userBusinessTypeDTO.setCreateDate(new Date());
                    userBusinessTypeDTO.setUserType(ConfigConstant.businessC1);
                    userBusinessTypeDTO.setStatus("1");
                    businessServiceClient.insertUserBusinessType(userBusinessTypeDTO);
                }

                BonusFlagDTO bonusFlagDTO = new BonusFlagDTO();
                bonusFlagDTO.setUserId(userInfoDTO.getId());
                bonusFlagDTO.setProductId(ConfigConstant.promote_businessB1_ProductId_No1);
                bonusFlagDTO.setMessageFlag("true");
                bonusFlagDTO.setBonusFlag("false");
                mongoTemplate.insert(bonusFlagDTO, "bonusFlag");
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

            if(StringUtils.isNotNull(xmlEntity.getEventKey())&&xmlEntity.getEventKey().indexOf("mxForeignPurchase")!=-1)
            {
                String weishiyiShop = xmlEntity.getEventKey().replace("mxForeignPurchase_", "");
                String codeArray[] = weishiyiShop.split("_");
                String specialShopId = codeArray[1];

                //通过shopId查询出店铺名称
                Query query = new Query(Criteria.where("shopId").is(specialShopId));
                SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");

                //处理境外购的流程
                List<Article> articleList = new ArrayList<>();
                Article article = new Article();
                article.setTitle("嗨!您终于来啦! ~\n");
                article.setDescription(
                        "请点击我进入"+specialShopInfoDTO.getShopName()+"吧");
                article.setPicUrl(specialShopInfoDTO.getShopURL());
                article.setUrl(ConfigConstant.SPECIAL_SHOP_URL+specialShopInfoDTO.getShopId());
                articleList.add(article);
                WeixinUtil.senImgMsgToWeixin(token,xmlEntity.getFromUserName(),articleList);
            }
            else
            {
                String content = "【美享99快时尚】一个分享赚钱的美妆商城，自用省钱，分享更赚钱。\n" +
                        " 仅需，一次性消费满498元，成为美享店主☟☟☟   \n" +
                        " \n" +
                        " 享 25-70%的现金返利                 \n" +
                        " 送 新人三重礼，价值664元         \n" +
                        " 送 快速拓客课程，价值1920元 \n" +
                        " \n" +"猛戳<a href=\\\"http://mx99.kpbeauty.com.cn/customer#/shopHome/\\\">做店主～(￣▽￣～)</a>~ \n"+ " \n" +
                        "点击【我要赚钱】☞ <a href=\\\"http://mx99.kpbeauty.com.cn/customer#/shareHome\\\">分享赚钱</a> ~\n" +
                        "点击【用户指南】☞ <a href=\\\"https://mp.weixin.qq.com/s/B8f8hmCWz3T2xUwpRvz-iQ\\\">了解赚钱</a> ~\n" +
                        "学习课程请点击【微课堂】，并加导师微信：mx99xx001";
                WeixinUtil.sendMsgToWeixin(token,xmlEntity.getFromUserName(),content);
            }
        }
    }

    //处理用户取消关注公众号事件
    public void processUserUnSubscribeEvent(ReceiveXmlEntity xmlEntity)
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
            List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                userInfoDTO.setWeixinAttentionStatus("0");
                String nickname = null;
                try {
                    nickname = URLEncoder.encode(userInfoDTO.getNickname(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userInfoDTO.setNickname(nickname);
                userServiceClient.updateUserInfo(userInfoDTO);
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
