package com.wisdom.weixin.WeixinTest;


import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BonusFlagDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinAttentionDTO;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.util.SpringUtil;
import com.wisdom.common.util.WeixinTemplateMessageUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.weixin.WeixinServiceApplication;
import com.wisdom.weixin.client.BusinessServiceClient;
import com.wisdom.weixin.client.UserServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private MongoTemplate mongoTemplate;

    @Before
    public void beforeTest() {
        ApplicationContext app = SpringApplication.run(WeixinServiceApplication.class, "");
        SpringUtil.setApplicationContext(app);
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


}
