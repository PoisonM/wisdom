package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.util.*;
import com.wisdom.user.mapper.SysBossMapper;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.mapper.UserInfoMapper;
import com.wisdom.user.mapper.extMapper.ExtSysClerkMapper;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private ExtSysClerkMapper extSysClerkMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    private Gson gson = new Gson();

    @Override
    public String userLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception {

        //判断validateCode是否还有效
        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
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
            //userInfoDTO.setMobile(phone);
            List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);
            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
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
                }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }

                userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));

                //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
                logintoken = UUID.randomUUID().toString();
                String userInfoStr = gson.toJson(userInfoDTO);
                JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
            }
            else
            {
                WeixinUserBean weixinUserBean = WeixinUtil.getWeixinUserBean(WeixinUtil.getUserToken(),openId);

                userInfoDTO.setId(UUID.randomUUID().toString());
                userInfoDTO.setMobile(loginDTO.getUserPhone());
                String nickname = null;
                try {
                    nickname = URLEncoder.encode(weixinUserBean.getNickname(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userInfoDTO.setNickname(nickname);
                userInfoDTO.setUserType(ConfigConstant.businessC1);
                userInfoDTO.setParentUserId("");
                userInfoDTO.setWeixinAttentionStatus("1");
                userInfoDTO.setPhoto(weixinUserBean.getHeadimgurl());
                userInfoDTO.setDelFlag("0");
                userInfoDTO.setLoginIp(loginIP);
                userInfoDTO.setCreateDate(new Date());
                userMapper.insertUserInfo(userInfoDTO);
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
    public String bossMobileLogin(LoginDTO loginDTO, String loginIP, String openId)
    {
        //判断validateCode是否还有效
        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setUserOpenid(openId);
        List<SysBossDTO> sysBossDTOList = sysBossMapper.getBossInfo(sysBossDTO);
        RedisLock redisLock = new RedisLock("bossInfo"+loginDTO.getUserPhone());
        try {
            redisLock.lock();

            if(sysBossDTOList.size()>0)
            {
                sysBossDTO = sysBossDTOList.get(0);
                if(sysBossDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    sysBossDTO.setMobile(loginDTO.getUserPhone());
                    sysBossDTO.setLoginDate(new Date());
                    sysBossDTO.setLoginIp(loginIP);
                    sysBossMapper.updateBossInfo(sysBossDTO);
                }
                else if(sysBossDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    sysBossDTO.setLoginDate(new Date());
                    sysBossDTO.setLoginIp(loginIP);
                    sysBossMapper.updateUserInfo(sysBossDTO);
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

        sysBossDTO.setNickname(CommonUtils.nameDecoder(sysBossDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String bossInfoStr = gson.toJson(sysBossDTO);
        JedisUtils.set(logintoken,bossInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String bossWebLogin(LoginDTO loginDTO, String loginIP)
    {
        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setMobile(loginDTO.getUserPhone());
        List<SysBossDTO> sysBossDTOList = sysBossMapper.getBossInfo(sysBossDTO);
        RedisLock redisLock = new RedisLock("bossInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();
            if(sysBossDTOList.size()>0)
            {
                sysBossDTO = sysBossDTOList.get(0);
                if(sysBossDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    sysBossDTO.setMobile(loginDTO.getUserPhone());
                    sysBossDTO.setLoginDate(new Date());
                    sysBossDTO.setLoginIp(loginIP);
                    sysBossMapper.updateBossInfo(sysBossDTO);
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

        sysBossDTO.setNickname(CommonUtils.nameDecoder(sysBossDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String bossInfoStr = gson.toJson(sysBossDTO);
        JedisUtils.set(logintoken,bossInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String ClerkMobileLogin(LoginDTO loginDTO, String loginIP, String openId) {
        //判断validateCode是否还有效
        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        sysClerkDTO.setUserOpenid(openId);
        List<SysClerkDTO> sysClerkDTOList = extSysClerkMapper.getClerkInfo(sysClerkDTO);
        RedisLock redisLock = new RedisLock("clerkInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();

            if(sysClerkDTOList.size()>0)
            {
                sysClerkDTO = sysClerkDTOList.get(0);
                if(sysClerkDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    sysClerkDTO.setMobile(loginDTO.getUserPhone());
                    sysClerkDTO.setLoginDate(new Date());
                    sysClerkDTO.setLoginIp(loginIP);
                    extSysClerkMapper.updateClerkInfo(sysClerkDTO);
                }
                else if(sysClerkDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    sysClerkDTO.setLoginDate(new Date());
                    sysClerkDTO.setLoginIp(loginIP);
                    extSysClerkMapper.updateClerkInfo(sysClerkDTO);
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

        sysClerkDTO.setNickname(CommonUtils.nameDecoder(sysClerkDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String clerkInfoStr = gson.toJson(sysClerkDTO);
        JedisUtils.set(logintoken,clerkInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String ClerkWebLogin(LoginDTO loginDTO, String loginIP) {

        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        sysClerkDTO.setMobile(loginDTO.getUserPhone());
        List<SysClerkDTO> sysClerkDTOList = extSysClerkMapper.getClerkInfo(sysClerkDTO);
        RedisLock redisLock = new RedisLock("clerkInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();
            if(sysClerkDTOList.size()>0)
            {
                sysClerkDTO = sysClerkDTOList.get(0);
                if(sysClerkDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    sysClerkDTO.setMobile(loginDTO.getUserPhone());
                    sysClerkDTO.setLoginDate(new Date());
                    sysClerkDTO.setLoginIp(loginIP);
                    extSysClerkMapper.updateClerkInfo(sysClerkDTO);
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

        sysClerkDTO.setNickname(CommonUtils.nameDecoder(sysClerkDTO.getNickname()));

        //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
        String logintoken = UUID.randomUUID().toString();
        String clerkInfoStr = gson.toJson(sysClerkDTO);
        JedisUtils.set(logintoken,clerkInfoStr, ConfigConstant.logintokenPeriod);
        return logintoken;
    }

    @Override
    public String beautyUserLogin(LoginDTO loginDTO, String s, String openid) {

        //判断validateCode是否还有效
        if(processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        String beautyLoginToken = null;
        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        RedisLock redisLock = new RedisLock("userInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserOpenid(openid);
            List<UserInfoDTO> userInfoDTOList = userMapper.getUserByInfo(userInfoDTO);
            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(loginDTO.getUserPhone());
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(s);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(s);
                    userMapper.updateUserInfo(userInfoDTO);
                }
                else
                {
                    return StatusConstant.WEIXIN_ATTENTION_ERROR;
                }

                userInfoDTO.setNickname(CommonUtils.nameDecoder(userInfoDTO.getNickname()));
            }
            else
            {
                WeixinUserBean weixinUserBean = WeixinUtil.getWeixinUserBean(WeixinUtil.getBeautyToken(),openid);

                userInfoDTO.setId(UUID.randomUUID().toString());
                userInfoDTO.setMobile(loginDTO.getUserPhone());
                String nickname = null;
                try {
                    nickname = URLEncoder.encode(weixinUserBean.getNickname(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                userInfoDTO.setNickname(nickname);
                userInfoDTO.setUserType("beautyUser");
                userInfoDTO.setParentUserId("");
                userInfoDTO.setWeixinAttentionStatus("1");
                userInfoDTO.setPhoto(weixinUserBean.getHeadimgurl());
                userInfoDTO.setDelFlag("0");
                userInfoDTO.setLoginIp(s);
                userInfoDTO.setCreateDate(new Date());
                userMapper.insertUserInfo(userInfoDTO);
            }

            //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
            beautyLoginToken = UUID.randomUUID().toString();
            String userInfoStr = gson.toJson(userInfoDTO);
            JedisUtils.set(beautyLoginToken,userInfoStr, ConfigConstant.logintokenPeriod);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        return beautyLoginToken;
    }

    private String processValidateCode(LoginDTO loginDTO)
    {
        //判断validateCode是否还有效
        Query query = new Query().addCriteria(Criteria.where("mobile").is(loginDTO.getUserPhone()));
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
              //判断验证码是否是最新的
            if(!validateCodeDTO.getCode().equals(loginDTO.getCode())){
                return StatusConstant.VALIDATECODE_ERROR;
            }
            long period =  (new Date()).getTime() - dateStr.getTime();

            //验证码过了5分钟了
            if(period>300000)
            {
                return  StatusConstant.VALIDATECODE_ERROR;
            }
        }
        return StatusConstant.SUCCESS;
    }
}
