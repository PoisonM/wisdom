package com.wisdom.user.service.impl;

import com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.client.BusinessServiceClient;
import com.wisdom.user.mapper.UserInfoMapper;
import com.wisdom.user.service.BeautyUserInfoService;
import com.wisdom.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Transactional(readOnly = false)
public class BeautyUserInfoServiceImpl implements BeautyUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    protected MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    public List<UserInfoDTO> getBeautyUserInfo(UserInfoDTO userInfoDTO) {
        List<UserInfoDTO> userInfoDTOS = userInfoMapper.getUserByInfo(userInfoDTO);
        if(CommonUtils.objectIsEmpty(userInfoDTOS)){
            logger.info("查询的用户信息为空");
            return userInfoDTOS;
        }
        for (UserInfoDTO user: userInfoDTOS) {
            if(StringUtils.isNotBlank(user.getNickname())){
                String nickNameW = user.getNickname().replaceAll("%", "%25");
                while(true){
                    logger.info("用户进行编码操作");
                    if(StringUtils.isNotBlank(nickNameW)){
                        if(nickNameW.contains("%25")){
                            nickNameW =  CommonUtils.nameDecoder(nickNameW);
                        }else{
                            nickNameW =  CommonUtils.nameDecoder(nickNameW);
                            break;
                        }
                    }else{
                        break;
                    }
                }
                user.setNickname(nickNameW);
            }
        }
        return  userInfoDTOS;
    }
    
    public void updateBeautyUserInfo(UserInfoDTO userInfoDTO) {
        RedisLock redisLock = new RedisLock("userInfo"+userInfoDTO.getId());
        try{
            redisLock.lock();
            userInfoMapper.updateUserInfo(userInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock();
        }
    }
    
    public void insertBeautyUserInfo(UserInfoDTO userInfoDTO) {
        RedisLock redisLock = new RedisLock("userInfo"+userInfoDTO.getId());
        try{
            redisLock.lock();
            userInfoMapper.insertUserInfo(userInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock();
        }
    }

    public UserInfoDTO getBeautyUserInfoFromRedis() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);
        String token = tokenValue.get("logintoken");
        String userInfoStr = JedisUtils.get(token);
        UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfoStr,UserInfoDTO.class);

        //开启一个线程，主要用来同步userType信息到redis中
        Runnable processUserInfoSynchronize = new processUserInfoSynchronize(token,userInfoDTO);
        threadExecutorCached.execute(processUserInfoSynchronize);
        
        return userInfoDTO;
    }

    private class processUserInfoSynchronize extends Thread {

        private String logintoken;
        private UserInfoDTO userInfoDTO;

        public processUserInfoSynchronize(String logintoken,UserInfoDTO userInfoDTO) {
            this.logintoken = logintoken;
            this.userInfoDTO = userInfoDTO;
        }

        @Override
        public void run() {
            UserInfoDTO userInfoDTO1 = new UserInfoDTO();
            userInfoDTO1.setId(userInfoDTO.getId());
            List<UserInfoDTO> userInfoDTOS = getBeautyUserInfo(userInfoDTO1);
            if(userInfoDTOS.size()>0)
            {
                String userInfoStr = (new com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson()).toJson(userInfoDTOS.get(0));
                JedisUtils.set(logintoken,userInfoStr,ConfigConstant.logintokenPeriod);
            }
        }
    }

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
