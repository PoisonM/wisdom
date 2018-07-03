package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.BeautyLoginResultDTO;
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
    public BeautyLoginResultDTO beautyLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception {

        BeautyLoginResultDTO beautyLoginResultDTO = new BeautyLoginResultDTO();
        beautyLoginResultDTO.setBeautyClerkLoginToken(StatusConstant.TOKEN_ERROR);
        beautyLoginResultDTO.setBeautyBossLoginToken(StatusConstant.TOKEN_ERROR);
        beautyLoginResultDTO.setBeautyUserLoginToken(StatusConstant.TOKEN_ERROR);

        //判断validateCode是否还有效
        if(LoginUtil.processBeautyUserValidateCode(loginDTO).equals(StatusConstant.VALIDATECODE_ERROR))
        {
            beautyLoginResultDTO.setResult(StatusConstant.VALIDATECODE_ERROR);
            return beautyLoginResultDTO;
        }

        String logintoken = null;
        //validateCode有效后，判断sys_user表中，是否存在此用户，如果存在，则成功返回登录，如果不存在，则创建用户后，返回登录成功
        RedisLock redisLock = new RedisLock("userInfo" + loginDTO.getUserPhone());
        try {
            redisLock.lock();

            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setMobile(loginDTO.getUserPhone());

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
                                beautyLoginResultDTO.setResult("phoneNotUse");
                                return beautyLoginResultDTO;
                            }
                        }
                    }
                    //用户曾经绑定过手机号，更新用户登录信息
                    userInfoDTO.setMobile(loginDTO.getUserPhone());
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    if(StringUtils.isNotNull(openId))
                    {
                        userInfoDTO.setUserOpenid(openId);
                    }
                    beautyUserMapper.updateBeautyUserInfo(userInfoDTO);
                }
                else if(userInfoDTO.getMobile().equals(loginDTO.getUserPhone()))
                {
                    userInfoDTO.setLoginDate(new Date());
                    userInfoDTO.setLoginIp(loginIP);
                    if(StringUtils.isNotNull(openId))
                    {
                        userInfoDTO.setUserOpenid(openId);
                    }
                    beautyUserMapper.updateBeautyUserInfo(userInfoDTO);
                }
                else if(!userInfoDTO.getMobile().equals(loginDTO.getUserPhone())){
                    beautyLoginResultDTO.setResult("phoneIsError");
                    return beautyLoginResultDTO;
                }

                userInfoDTO.setNickname(URLEncoder.encode(userInfoDTO.getNickname(),"utf-8"));
                //登录成功后，将用户信息放置到redis中，生成logintoken供前端使用
                logintoken = UUID.randomUUID().toString();
                String userInfoStr = gson.toJson(userInfoDTO);
                JedisUtils.set(logintoken,userInfoStr, ConfigConstant.logintokenPeriod);
                beautyLoginResultDTO.setBeautyUserLoginToken(logintoken);
            }

            SysBossDTO sysBossDTO = new SysBossDTO();
            sysBossDTO.setMobile(loginDTO.getUserPhone());
            SysBossCriteria sysBossCriteria = new SysBossCriteria();
            SysBossCriteria.Criteria b = sysBossCriteria.createCriteria();
            b.andMobileEqualTo(loginDTO.getUserPhone());
            List<SysBossDTO> bossDTOList = sysBossMapper.selectByCriteria(sysBossCriteria);
            if(bossDTOList.size()>0)
            {
                sysBossDTO =  bossDTOList.get(0);
                sysBossDTO.setMobile(loginDTO.getUserPhone());
                sysBossDTO.setLoginDate(new Date());
                if(StringUtils.isNotNull(openId))
                {
                    sysBossDTO.setUserOpenid(openId);
                }
                sysBossMapper.updateByPrimaryKey(sysBossDTO);

                logintoken = UUID.randomUUID().toString();
                String bossInfoStr = gson.toJson(sysBossDTO);
                JedisUtils.set(logintoken,bossInfoStr, ConfigConstant.logintokenPeriod);
                beautyLoginResultDTO.setBeautyBossLoginToken(logintoken);
            }

            SysClerkDTO sysClerkDTO = new SysClerkDTO();
            sysClerkDTO.setMobile(loginDTO.getUserPhone());
            SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
            SysClerkCriteria.Criteria c = sysClerkCriteria.createCriteria();
            c.andMobileEqualTo(loginDTO.getUserPhone());
            List<SysClerkDTO> clerkDTOList = extSysClerkMapper.selectByCriteria(sysClerkCriteria);
            if(clerkDTOList.size()>0)
            {
                sysClerkDTO = clerkDTOList.get(0);
                sysClerkDTO.setMobile(loginDTO.getUserPhone());
                sysClerkDTO.setLoginDate(new Date());
                if(StringUtils.isNotNull(openId))
                {
                    sysClerkDTO.setUserOpenid(openId);
                }
                extSysClerkMapper.updateByPrimaryKey(sysClerkDTO);

                logintoken = UUID.randomUUID().toString();
                String clerkInfoStr = gson.toJson(sysClerkDTO);
                JedisUtils.set(logintoken,clerkInfoStr, ConfigConstant.logintokenPeriod);
                beautyLoginResultDTO.setBeautyClerkLoginToken(logintoken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            redisLock.unlock();
        }

        beautyLoginResultDTO.setResult(StatusConstant.SUCCESS);
        return beautyLoginResultDTO;
    }

    @Override
    public String beautyLoginOut(BeautyLoginResultDTO beautyLoginResultDTO, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String openId = WeixinUtil.getBeautyOpenId(session,request);
        JedisUtils.del(beautyLoginResultDTO.getBeautyBossLoginToken());
        JedisUtils.del(beautyLoginResultDTO.getBeautyClerkLoginToken());
        JedisUtils.del(beautyLoginResultDTO.getBeautyUserLoginToken());
        session.removeAttribute(ConfigConstant.USER_OPEN_ID);
        CookieUtils.setCookie(response, ConfigConstant.USER_OPEN_ID, openId==null?"":openId,0,ConfigConstant.DOMAIN_VALUE);
        return StatusConstant.LOGIN_OUT;
    }


}
