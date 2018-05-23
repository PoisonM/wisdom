package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.user.*;
import com.wisdom.common.util.*;
import com.wisdom.user.mapper.BeautyUserInfoMapper;
import com.wisdom.user.mapper.SysBossMapper;
import com.wisdom.user.mapper.extMapper.ExtSysClerkMapper;
import com.wisdom.user.service.BeautyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class BeautyLoginServiceImpl implements BeautyLoginService {

    @Autowired
    private BeautyUserInfoMapper beautyUserMapper;

    @Autowired
    private SysBossMapper sysBossMapper;

    @Autowired
    private ExtSysClerkMapper extSysClerkMapper;

    @Autowired
    protected MongoTemplate mongoTemplate;

    private Gson gson = new Gson();

    @Override
    public String beautyUserLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception {

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
            List<UserInfoDTO> userInfoDTOList = beautyUserMapper.getBeautyUserByInfo(userInfoDTO);

            if(userInfoDTOList.size()>0)
            {
                userInfoDTO = userInfoDTOList.get(0);
                if(userInfoDTO.getMobile()==null)
                {
                    UserInfoDTO userInfoDTO1 = new UserInfoDTO();
                    userInfoDTO1.setMobile(loginDTO.getUserPhone());
                    List<UserInfoDTO> userInfoDTOList1 = beautyUserMapper.getBeautyUserByInfo(userInfoDTO1);
                    if(userInfoDTOList1!=null&&userInfoDTOList1.size()>0){
                        for(UserInfoDTO user : userInfoDTOList1){
                            if(!user.getUserType().equals("finance-1") && !"operation-1".equals(user.getUserType()) && !"manager-1".equals(user.getUserType())){
                                return "phoneNotUse";
                            }
                        }
                    }
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(loginDTO.getUserPhone());
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    beautyUserMapper.updateBeautyUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    beautyUserMapper.updateBeautyUserInfo(userInfoDTO);
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
    public String beautyUserLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String openId = WeixinUtil.getBeautyOpenId(session,request);
        JedisUtils.del(logintoken);
        session.removeAttribute(ConfigConstant.USER_OPEN_ID);
        CookieUtils.setCookie(response, ConfigConstant.USER_OPEN_ID, openId==null?"":openId,0,ConfigConstant.DOMAIN_VALUE);
        return StatusConstant.LOGIN_OUT;
    }

    @Override
    public String bossMobileLogin(LoginDTO loginDTO, String loginIP, String openId)
    {
        //判断validateCode是否还有效
        if(LoginUtil.processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setUserOpenid(loginDTO.getUserPhone());
        SysBossCriteria sysBossCriteria = new SysBossCriteria();
        SysBossCriteria.Criteria c = sysBossCriteria.createCriteria();
        c.andUserOpenidEqualTo(loginDTO.getUserPhone());
        List<SysBossDTO> sysBossDTOList = sysBossMapper.selectByCriteria(sysBossCriteria);

        RedisLock redisLock = new RedisLock("bossInfo"+loginDTO.getUserPhone());
        try {
            redisLock.lock();

            if(sysBossDTOList.size()>0)
            {
                sysBossDTO = sysBossDTOList.get(0);
                sysBossDTO.setLoginDate(new Date());
                sysBossDTO.setLoginIp(loginIP);
                sysBossDTO.setUserOpenid(openId);
                sysBossMapper.updateByCriteriaSelective(sysBossDTO, sysBossCriteria);
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
        if(LoginUtil.processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setMobile(loginDTO.getUserPhone());
        SysBossCriteria sysBossCriteria = new SysBossCriteria();
        SysBossCriteria.Criteria c = sysBossCriteria.createCriteria();
        c.andUserOpenidEqualTo(loginDTO.getUserPhone());
        List<SysBossDTO> sysBossDTOList = sysBossMapper.selectByCriteria(sysBossCriteria);
        RedisLock redisLock = new RedisLock("bossInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();
            if(sysBossDTOList.size()>0)
            {
                sysBossDTO = sysBossDTOList.get(0);
                //用户曾经绑定过手机号，更新用户登录信息
                sysBossDTO.setMobile(loginDTO.getUserPhone());
                sysBossDTO.setLoginDate(new Date());
                sysBossDTO.setLoginIp(loginIP);
                sysBossMapper.updateByCriteriaSelective(sysBossDTO, sysBossCriteria);
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
        if(LoginUtil.processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        sysClerkDTO.setMobile(loginDTO.getUserPhone());

        SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
        SysClerkCriteria.Criteria c = sysClerkCriteria.createCriteria();
        c.andMobileEqualTo(loginDTO.getUserPhone());
        List<SysClerkDTO> sysClerkDTOList = extSysClerkMapper.selectByCriteria(sysClerkCriteria);
        RedisLock redisLock = new RedisLock("clerkInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();

            if(sysClerkDTOList.size()>0)
            {
                sysClerkDTO = sysClerkDTOList.get(0);

                //用户曾经绑定过手机号，更新用户登录信息
                sysClerkDTO.setMobile(loginDTO.getUserPhone());
                sysClerkDTO.setLoginDate(new Date());
                sysClerkDTO.setLoginIp(loginIP);
                sysClerkDTO.setUserOpenid(openId);
                extSysClerkMapper.updateByPrimaryKeySelective(sysClerkDTO);
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

        if(LoginUtil.processValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }

        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        sysClerkDTO.setMobile(loginDTO.getUserPhone());
        SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
        SysClerkCriteria.Criteria c = sysClerkCriteria.createCriteria();
        c.andMobileEqualTo(loginDTO.getUserPhone());
        List<SysClerkDTO> sysClerkDTOList = extSysClerkMapper.selectByCriteria(sysClerkCriteria);
        RedisLock redisLock = new RedisLock("clerkInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();
            if(sysClerkDTOList.size()>0)
            {
                //用户曾经绑定过手机号，更新用户登录信息
                sysClerkDTO.setMobile(loginDTO.getUserPhone());
                sysClerkDTO.setLoginDate(new Date());
                sysClerkDTO.setLoginIp(loginIP);
                extSysClerkMapper.updateByCriteriaSelective(sysClerkDTO, sysClerkCriteria);
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
}
