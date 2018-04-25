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
import com.wisdom.user.service.UserInfoService;
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
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoMapper customerInfoMapper;

    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    protected MongoTemplate mongoTemplate;

    private static ExecutorService threadExecutorCached = Executors.newCachedThreadPool();

    public List<UserInfoDTO> getUserInfo(UserInfoDTO userInfoDTO) {
        List<UserInfoDTO> userInfoDTOS = customerInfoMapper.getUserByInfo(userInfoDTO);
        for (UserInfoDTO user: userInfoDTOS) {
            user.setNickname(CommonUtils.nameDecoder(user.getNickname()));
        }
        return  userInfoDTOS;
    }
    
    public void updateUserInfo(UserInfoDTO userInfoDTO) {
        RedisLock redisLock = new RedisLock("userInfo"+userInfoDTO.getId());
        try{
            redisLock.lock();
            customerInfoMapper.updateUserInfo(userInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock();
        }
    }
    
    public void insertUserInfo(UserInfoDTO userInfoDTO) {
        RedisLock redisLock = new RedisLock("userInfo"+userInfoDTO.getId());
        try{
            redisLock.lock();

            customerInfoMapper.insertUserInfo(userInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock();
        }
    }

    public PageParamDTO<List<UserBusinessTypeDTO>> queryUserBusinessById(String sysUserId) {
        PageParamDTO<List<UserBusinessTypeDTO>> page = new  PageParamDTO<>();
        List<UserBusinessTypeDTO> userBusinessTypeDTOList = customerInfoMapper.queryUserBusinessById(sysUserId);
        page.setResponseData(userBusinessTypeDTOList);
        return page;
    }

    /**
     * 条件查询用户List分页
     * @return
     */
    public PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO) {
        PageParamDTO<List<UserInfoDTO>> page = new  PageParamDTO<>();
        int count = customerInfoMapper.queryUserInfoDTOCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<UserInfoDTO> userInfoDTOList = customerInfoMapper.queryUserInfoDTOByParameters(pageParamVoDTO);
        for(UserInfoDTO userInfoDTO : userInfoDTOList){
            if(userInfoDTO.getLivingPeriod() != 0){
                int outDay = (int) DateUtils.pastDays(userInfoDTO.getCreateDate());
                int livingPeriod = (userInfoDTO.getLivingPeriod() - outDay);
                if(livingPeriod>0){
                    userInfoDTO.setLivingPeriod(livingPeriod);
                }else {
                    userInfoDTO.setLivingPeriod(-1);
                }
            }
            userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));
        }
        page.setResponseData(userInfoDTOList);
        return page;
    }

    //根据用户id查询下级代理
    public List<UserInfoDTO> queryNextUserById(String sysUserId) {
        List<UserInfoDTO> userInfoDTOList = customerInfoMapper.queryNextUserById(sysUserId);
        for(UserInfoDTO userInfoDTO : userInfoDTOList){
            userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));
        }
        return userInfoDTOList;
    }

    public UserInfoDTO getUserInfoFromUserId(String sysUserId) {
        UserInfoDTO dto = new UserInfoDTO();
        if (StringUtils.isBlank(sysUserId)) {
            return dto;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(sysUserId);
        List<UserInfoDTO> userInfoDTOS = customerInfoMapper.getUserByInfo(userInfoDTO);
        if(userInfoDTOS.size()>0)
        {
            return userInfoDTOS.get(0);
        }else
        {
            return dto;
        }
    }
    //根据parentId查询上级信息
    public List<UserInfoDTO> queryParentUserById(String parentUserId) {
        List<UserInfoDTO> userInfoDTOS= customerInfoMapper.queryParentUserById(parentUserId);
        for (UserInfoDTO userInfoDTO :userInfoDTOS) {
            userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));
        }
        return userInfoDTOS;
    }

    public UserInfoDTO getUserInfoFromRedis() {
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

    @Override
    public List<UserInfoDTO> getUserInfoFromUserId(List<String> sysUserIds,String searchFile) {
        Map<String,Object> map=new HashMap<>(16);
        map.put("list",sysUserIds);
        map.put("searchFile",searchFile);
        List<UserInfoDTO> userInfoDTOS = customerInfoMapper.getUserByInfoList(map);
        return  userInfoDTOS;
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

            UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
            userBusinessTypeDTO.setStatus("1");
            userBusinessTypeDTO.setSysUserId(userInfoDTO.getId());
            List<UserBusinessTypeDTO> userBusinessTypeDTOList = businessServiceClient.getUserBusinessType(userBusinessTypeDTO);
            if(userBusinessTypeDTOList.size()>0)
            {
                if(!userBusinessTypeDTOList.get(0).getUserType().equals(userInfoDTO.getUserType()))
                {
                    userInfoDTO.setUserType(userBusinessTypeDTOList.get(0).getUserType());
                    String userInfoStr = (new com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson()).toJson(userInfoDTO);
                    JedisUtils.set(logintoken,userInfoStr,ConfigConstant.logintokenPeriod);
                }
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
