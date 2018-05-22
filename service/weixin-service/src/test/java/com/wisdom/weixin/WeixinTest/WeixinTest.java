package com.wisdom.weixin.WeixinTest;


import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BonusFlagDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinAttentionDTO;
import com.wisdom.common.entity.TemplateData;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.entity.WxTemplate;
import com.wisdom.common.util.HttpRequestUtil;
import com.wisdom.common.util.SpringUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.weixin.WeixinServiceApplication;
import com.wisdom.weixin.client.BeautyServiceClient;
import com.wisdom.weixin.client.BusinessServiceClient;
import com.wisdom.weixin.client.UserServiceClient;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by 赵得良 on 21/09/2016.
 */
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeixinServiceApplication.class)
@WebAppConfiguration
public class WeixinTest {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    private BeautyServiceClient beautyServiceClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before
    public void beforeTest() {
        ApplicationContext app = SpringApplication.run(WeixinServiceApplication.class, "");
        SpringUtil.setApplicationContext(app);
    }

    @Test
    public void getUserBindingInfo() {
        beautyServiceClient.getUserBindingInfo("oP0k_0S-a-U1z64qcVVCIaLWM52s", "20180514102629597");
    }

    @Test
    public void test() {

        //判断用户是否是扫码微商用户的二维码关注，
        String businessParentPhone = "15635388112";
        String openId = "o6fNC0svt-TfeBDfHL_P84CsVX_8";
        String token = "8_nI1NupLSC2V4PRQ6rZWgWs_tv6H5UCmHlw3Z4zkL7fdP91kzpY0atgz0k2PMstPFyMIB1YC0jk8BymjTkapQn4nk23gOuT0xfuQsYcaGcOYnZvAALlYvXbQHRlEFOReAEALUX";
        //为关注公众号的用户创建新的或修订之前的账户
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);

        if (userInfoDTOList.size() > 0) {
            //用户之前关注过
            userInfoDTO = userInfoDTOList.get(0);
            if (userInfoDTO.getWeixinAttentionStatus().equals("0")) {
                userInfoDTO.setWeixinAttentionStatus("1");
            }
            //userInfoDTO.setParentUserId(parentUserInfoDTO.getId());
            userInfoDTO.setLoginIp("");
            userServiceClient.updateUserInfo(userInfoDTO);
        } else {
            //获取用户的微信信息
            WeixinUserBean weixinUserBean = WeixinUtil.getWeixinUserBean
                    (token, openId);

            UserInfoDTO parentUserInfoDTO = new UserInfoDTO();
            if (!businessParentPhone.equals("")) {
                parentUserInfoDTO.setMobile(businessParentPhone);
                //从sys_user表中，查询父一级用户信息
                List<UserInfoDTO> parentUserInfoDTOList = userServiceClient.getUserInfo(parentUserInfoDTO);
                parentUserInfoDTO = parentUserInfoDTOList.get(0);
                if (null != parentUserInfoDTO) {
                    //向父节点推送消息
                    WeixinTemplateMessageUtil.sendLowLevelBusinessTemplateWXMessage(weixinUserBean.getNickname(), "c级代理商", "0元", token,
                            "", parentUserInfoDTO.getUserOpenid());
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
            accountDTO.setBalance((float) 0.00);
            accountDTO.setScore((float) 0.00);
            accountDTO.setStatus("normal");
            accountDTO.setUpdateDate(new Date());
            accountDTO.setBalanceDeny((float) 0.00);
            businessServiceClient.createUserAccount(accountDTO);

            //查询用户当前user_business_type中是否有记录，没有则创建用户为C用户
            UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
            userBusinessTypeDTO.setStatus("1");
            List<UserBusinessTypeDTO> userBusinessTypeDTOS = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
            if (userBusinessTypeDTOS.size() == 0) {
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

    /**
     * 推送商品下架提醒
     */
    @Test
    public void sendMessage() {
        String token = "9_rPDvyUgMHL6I5DYMFRV1NL4Hi4o7NwP8GNtRsKnvGtR_sAP7VBR7bGNhXs0NaarubkE-Xlodwm9Ry_ni-LV6pIhtn9M0dlNF6Ynx4RRurETCqSNrQcKj8Rz0OBFcHYUg6RBImUBjVhzXeAfUQTFcAFAGJV";
        String tempId = "dz4_XwRKTzYietZlSsr_BWhGhQBJwv1J25ILuVdf7tY";
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setWeixinAttentionStatus("1");
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        for (UserInfoDTO dto : userInfoDTOS) {
            withdrawalsSuccess2Weixin(tempId, dto.getUserOpenid(), token);
        }

    }

    @Test
    public void testSendImag() {
        String token = "9_rPDvyUgMHL6I5DYMFRV1NL4Hi4o7NwP8GNtRsKnvGtR_sAP7VBR7bGNhXs0NaarubkE-Xlodwm9Ry_ni-LV6pIhtn9M0dlNF6Ynx4RRurETCqSNrQcKj8Rz0OBFcHYUg6RBImUBjVhzXeAfUQTFcAFAGJV";
        String mediaId = "H7b3rZfbpwHiJQGNap3aVu6G8TUdDRJXhEquSeTEAxMsQOd5XSXZkjcBkrIW03Af";
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setWeixinAttentionStatus("1");
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);
        for (UserInfoDTO dto : userInfoDTOS) {
            sendTextMeg(token, dto.getUserOpenid(), mediaId);
        }
    }

    public int withdrawalsSuccess2Weixin(String tempId, String openid, String token) {

        WxTemplate t = new WxTemplate();
        t.setTouser(openid);
        t.setTopcolor("#000000");
        t.setTemplate_id(tempId);
        Map<String, TemplateData> m = new HashMap<>();

        TemplateData templateData = new TemplateData();
        templateData.setColor("#000000");
        templateData.setValue("原199元臻致宠肤四件套，特惠价结束仅剩15天");
        m.put("first", templateData);

        templateData = new TemplateData();
        templateData.setColor("#000000");
        templateData.setValue("臻致宠肤四件套");
        m.put("keyword1", templateData);

        templateData = new TemplateData();
        templateData.setColor("#000000");
        templateData.setValue("2018年5月2日24:00");
        m.put("keyword2", templateData);

        templateData = new TemplateData();
        templateData.setColor("#000000");
        templateData.setValue("原四件套即将升级为全新六件套组，价格调整为399元。\n" +
                "请店主们相互转告，感谢支持！");
        m.put("keyword3", templateData);

        t.setData(m);
        String jsonobj = HttpRequestUtil.httpsRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" +
                token + "", "POST", JSONObject.fromObject(t).toString());
        JSONObject jsonObject = JSONObject.fromObject(jsonobj.replaceAll("200>>>>", ""));
        if (jsonobj != null) {
            if ("0".equals(jsonObject.getString("errcode"))) {
                logger.info("发送模板消息成功={}");
                return 1;
            } else {
                logger.error("发送模板消息失败，失败原因={},失败用户openid为，{}", jsonObject.getString("errcode"), openid);
                return 0;
            }
        }
        logger.error("jsonObject返回空");
        return 0;
    }


    public void sendTextMeg(String token, String openId, String mediaId) {
        WeixinUtil.sendImagToWeixin(token, openId, mediaId);
    }

    public class MyThread1 extends Thread {
        private String token;
        private String openId;
        private String mediaId;

        void MyThread1(String token, String openId, String mediaId) {
            this.token = token;
            this.openId = openId;
            this.mediaId = mediaId;
        }

        public void run() {
            WeixinUtil.sendImagToWeixin(token, openId, mediaId);
        }
    }

    public class MyThread2 extends Thread {
        private String token;
        private String openId;
        private String mediaId;

        void MyThread2(String token, String openId, String mediaId) {
            this.token = token;
            this.openId = openId;
            this.mediaId = mediaId;
        }

        public void run() {
            WeixinUtil.sendImagToWeixin(token, openId, mediaId);
        }
    }

    public class MyThread3 extends Thread {
        private String token;
        private String openId;
        private String mediaId;

        void MyThread3(String token, String openId, String mediaId) {
            this.token = token;
            this.openId = openId;
            this.mediaId = mediaId;
        }

        public void run() {
            WeixinUtil.sendImagToWeixin(token, openId, mediaId);
        }
    }

    public class MyThread4 extends Thread {
        private String token;
        private String openId;
        private String mediaId;

        void MyThread4(String token, String openId, String mediaId) {
            this.token = token;
            this.openId = openId;
            this.mediaId = mediaId;
        }

        public void run() {
            WeixinUtil.sendImagToWeixin(token, openId, mediaId);
        }
    }

    public class MyThread5 extends Thread {
        private String token;
        private String openId;
        private String mediaId;

        void MyThread5(String token, String openId, String mediaId) {
            this.token = token;
            this.openId = openId;
            this.mediaId = mediaId;
        }

        public void run() {
            WeixinUtil.sendImagToWeixin(token, openId, mediaId);
        }
    }

}
