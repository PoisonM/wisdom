package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.customer.SysBossDTO;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.mapper.SysBossMapper;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.mapper.UserInfoMapper;
import com.wisdom.user.service.LoginService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserInfoMapper userMapper;

    @Autowired
    private SysBossMapper sysBossMapper;

    @Autowired
    private SysClerkMapper sysClerkMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    private Gson gson = new Gson();

    @Override
    public String userLogin(String phone, String code, String loginIP, String openId) throws Exception {

        //判断validateCode是否还有效
        Query query = new Query().addCriteria(Criteria.where("mobile").is(phone))
                .addCriteria(Criteria.where("code").is(code));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        List<ValidateCodeDTO> data = mongoTemplate.find(query, ValidateCodeDTO.class,"validateCode");
        if(data==null)
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }
        else
        {
            ValidateCodeDTO validateCodeDTO = data.get(0);
            Date dateStr = validateCodeDTO.getCreateDate();
            long period =  (new Date()).getTime() - dateStr.getTime();

            //验证码过了5分钟了
            if(period>300000)
            {
                return  StatusConstant.VALIDATECODE_ERROR;
            }
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        //userInfoDTO.setMobile(phone);
        List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);
        RedisLock redisLock = new RedisLock("userInfo"+phone);
        try {
            redisLock.lock();

            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(phone);
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(phone))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String userInfoStr = gson.toJson(userInfoDTO);
        JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String userLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String openId = WeixinUtil.getUserOpenId(session,request);
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
        userInfoDTO.setUserType("manager-1");
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

    @Override
    public String bossMobileLogin(LoginDTO loginDTO, String s, String openId)
    {
        //判断validateCode是否还有效
        Query query = new Query().addCriteria(Criteria.where("mobile").is(loginDTO.getUserPhone()))
                .addCriteria(Criteria.where("code").is(loginDTO.getCode()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        List<ValidateCodeDTO> data = mongoTemplate.find(query, ValidateCodeDTO.class,"validateCode");
        if(data==null)
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }
        else
        {
            ValidateCodeDTO validateCodeDTO = data.get(0);
            Date dateStr = validateCodeDTO.getCreateDate();
            long period =  (new Date()).getTime() - dateStr.getTime();

            //验证码过了5分钟了
            if(period>300000)
            {
                return  StatusConstant.VALIDATECODE_ERROR;
            }
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setUserOpenid(openId);
        List<SysBossDTO> sysBossDTOList = sysBossMapper.getBossInfo(sysBossDTO);
        RedisLock redisLock = new RedisLock("userInfo"+loginDTO.getUserPhone());
        try {
            redisLock.lock();

            if(sysBossDTOList.size()>0)
            {
                sysBossDTO = sysBossDTOList.get(0);
                if(sysBossDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(loginDTO.getUserPhone());
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(phone))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String userInfoStr = gson.toJson(userInfoDTO);
        JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String bossWebLogin(LoginDTO loginDTO, String s)
    {
        //判断validateCode是否还有效
        Query query = new Query().addCriteria(Criteria.where("mobile").is(loginDTO.getUserPhone()))
                .addCriteria(Criteria.where("code").is(loginDTO.getCode()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        List<ValidateCodeDTO> data = mongoTemplate.find(query, ValidateCodeDTO.class,"validateCode");
        if(data==null)
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }
        else
        {
            ValidateCodeDTO validateCodeDTO = data.get(0);
            Date dateStr = validateCodeDTO.getCreateDate();
            long period =  (new Date()).getTime() - dateStr.getTime();

            //验证码过了5分钟了
            if(period>300000)
            {
                return  StatusConstant.VALIDATECODE_ERROR;
            }
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(openId);
        //userInfoDTO.setMobile(phone);
        List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);
        RedisLock redisLock = new RedisLock("userInfo"+phone);
        try {
            redisLock.lock();

            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(phone);
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(phone))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String userInfoStr = gson.toJson(userInfoDTO);
        JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String clerkLogin(LoginDTO loginDTO, String s, String openid)
    {
        return null;
    }

}
