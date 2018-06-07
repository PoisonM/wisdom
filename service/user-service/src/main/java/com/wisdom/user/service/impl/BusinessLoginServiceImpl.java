package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.dto.user.SysBossCriteria;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.mapper.SysBossMapper;
import com.wisdom.user.mapper.UserInfoMapper;
import com.wisdom.user.mapper.extMapper.ExtSysClerkMapper;
import com.wisdom.user.service.BusinessLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class BusinessLoginServiceImpl implements BusinessLoginService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoMapper userMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    private Gson gson = new Gson();

    @Override
    public String businessUserLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception {

        //判断validateCode是否还有效
        if(LoginUtil.processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        String logintoken = null;
        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        RedisLock redisLock = new RedisLock("userInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openId);
            List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);

            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
                    UserInfoDTO userInfoDTO1 = new UserInfoDTO();
                    userInfoDTO1.setMobile(loginDTO.getUserPhone());
                    List<UserInfoDTO> userInfoDTOList1 = userMapper.getUserByInfo(userInfoDTO1);
                    if(userInfoDTOList1!=null&&userInfoDTOList1.size()>0){
                        for(UserInfoDTO user : userInfoDTOList1){
                            if(!user.getUserType().equals("finance-1")){
                                return "phoneNotUse";
                            }
                        }
                    }
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(loginDTO.getUserPhone());
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }else if(!userInfoDTO.getMobile().equals(loginDTO.getUserPhone())){
                        return "phoneIsError";
                    }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }

                userInfoDTO.setNickname(URLEncoder.encode(userInfoDTO.getNickname(),"utf-8"));
                //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
                logintoken = UUID.randomUUID().toString();
                String userInfoStr = gson.toJson(userInfoDTO);
                JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        return logintoken;
    }

    @Override
    public String businessUserLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String openId = WeixinUtil.getUserOpenId(session,request);
        logger.info("Service == businessUserLoginOut,openId={}方法执行" ,openId);
        JedisUtils.del(logintoken);
        session.removeAttribute(ConfigConstant.USER_OPEN_ID);
        CookieUtils.setCookie(response, ConfigConstant.USER_OPEN_ID, openId==null?"":openId,0,ConfigConstant.DOMAIN_VALUE);
        return StatusConstant.LOGIN_OUT;
    }

    @Override
    public String managerLogin(String userPhone, String code) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setMobile(userPhone);
        userInfoDTO.setPassword(code);
        List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);
        if(userInfoDTOList.size()>0)
        {
            //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
            String logintoken = UUID.randomUUID().toString();
            String userInfoStr = gson.toJson(userInfoDTOList.get(0));
            JedisUtils.set(logintoken,userInfoStr,ConfigConstant.logintokenPeriod);
            return logintoken;
        }else{
            return StatusConstant.FAILURE;
        }
    }
}
