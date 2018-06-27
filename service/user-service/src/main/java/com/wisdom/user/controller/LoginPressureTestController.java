/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.RealNameResultEnum;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.TransactionCodeDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.dto.PressureLoginDTO;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.RealNameAuthService;
import com.wisdom.user.service.UserInfoService;
import org.apache.catalina.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Controller
@RequestMapping(value = "pressure")
public class LoginPressureTestController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static ExecutorService executor = Executors.newCachedThreadPool();

    @RequestMapping(value = "loginTestPressure", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> beautyUserLogin(HttpServletRequest request,
                                        HttpSession session) throws Exception {

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        Query query = new Query();
        List<PressureLoginDTO> pressureLoginDTOS = mongoTemplate.find(query, PressureLoginDTO.class,"pressureLoginTest");

        for(PressureLoginDTO pressureLoginDTO:pressureLoginDTOS)
        {
            if(!pressureLoginDTO.getInputUserMobile().equals(pressureLoginDTO.getLoginUserMobile()))
            {
                System.out.print("不同=="+pressureLoginDTO.getInputUserMobile());
            }

        }

//        UserInfoDTO userInfoDTO = new UserInfoDTO();
//        userInfoDTO.setUserType("business-B-1");
//        List<UserInfoDTO> userInfoDTOList = userInfoService.getUserInfo(userInfoDTO);
//
//        CountDownLatch latch = new CountDownLatch(userInfoDTOList.size()-650);
//
//        //两次线程去操作逻辑
//        for (int i = 0; i < userInfoDTOList.size()-650; i++) {
//            executor.execute(new SimpleRunnAble(userInfoDTOList, i, latch,mongoTemplate));
//        }
//
//        //保证所有线程执行完毕，再执行下面程序
//        latch.await();
//        executor.shutdown();

        return  responseDTO;
    }

    public static class SimpleRunnAble implements Runnable {
        private final List<UserInfoDTO> userInfoDTOList;
        private final int index;
        private final CountDownLatch latch;
        private Gson gson = new Gson();
        private MongoTemplate mongoTemplate;

        SimpleRunnAble(List<UserInfoDTO> userInfoDTOList, int index, CountDownLatch latch,MongoTemplate mongoTemplate) {
            this.userInfoDTOList = userInfoDTOList;
            this.index = index;
            this.latch = latch;
            this.mongoTemplate = mongoTemplate;
        }

        @Override
        public void run() {
            UserInfoDTO userInfoDTOTest = userInfoDTOList.get(index);
            System.out.println(index);
            System.out.println(userInfoDTOTest.getMobile());
            ResponseDTO<String> responseDTO = new ResponseDTO<>();
            PressureLoginDTO pressureLoginDTO = new PressureLoginDTO();
            LoginDTO loginDTO = new LoginDTO();

            if(!userInfoDTOTest.getMobile().equals(""))
            {
                loginDTO.setUserPhone(userInfoDTOTest.getMobile());
                loginDTO.setOpenId(userInfoDTOTest.getUserOpenid());

                pressureLoginDTO.setInputUserMobile(userInfoDTOTest.getMobile());
                pressureLoginDTO.setInputUserOpenId(userInfoDTOTest.getUserOpenid());

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost post = null;

                // 设置超时时间
                httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
                httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

                post = new HttpPost("http://47.100.102.37/user/userLogin");
                // 构造消息头
                post.setHeader("Content-type", "application/json;charset=UTF-8");

                // 构建消息实体
                String test = gson.toJson(loginDTO);
                StringEntity entity = new StringEntity(test, Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                // 发送Json格式的数据请求
                entity.setContentType("application/json");
                post.setEntity(entity);

                HttpResponse response = null;
                try {
                    response = httpClient.execute(post);
                    HttpEntity httpentity=response.getEntity();

                    responseDTO = gson.fromJson(EntityUtils.toString(httpentity, "utf-8"),ResponseDTO.class);
                    System.out.println("获取到了用户信息==="+responseDTO.getResponseData());
                    if(responseDTO.getResult().equals(StatusConstant.SUCCESS))
                    {
                        pressureLoginDTO.setLoginToken(responseDTO.getResponseData());
                        UserInfoDTO userInfoDTO1 = gson.fromJson(JedisUtils.get(responseDTO.getResponseData()),UserInfoDTO.class);
                        pressureLoginDTO.setLoginUserMobile(userInfoDTO1.getMobile());
                        pressureLoginDTO.setLoginUserOpenId(userInfoDTO1.getUserOpenid());
                        mongoTemplate.insert(pressureLoginDTO,"pressureLoginTest");
                        System.out.println("获取到了用户信息==="+pressureLoginDTO);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    latch.countDown();
                }
            }
        }
    }

}
