/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.util;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.enums.LoginEnum;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户工具类
 *
 * @author cjk
 * @version 2013-12-05
 */
public class UserUtils {

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoDTO getUserInfo() {
        String token = getUserToken(LoginEnum.USER);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String userInfoStr = JedisUtils.get(token);
        UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfoStr, UserInfoDTO.class);
        try {
            userInfoDTO.setNickname(java.net.URLDecoder.decode(userInfoDTO.getNickname(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userInfoDTO;
    }

    /**
     * 获取店员信息
     *
     * @return
     */
    public static SysClerkDTO getClerkInfo() {
        String token = getUserToken(LoginEnum.CLERK);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String sysClerkDTO = JedisUtils.get(token);
        SysClerkDTO clerkDTO = (new Gson()).fromJson(sysClerkDTO, SysClerkDTO.class);
        try {
            if (StringUtils.isNotBlank(clerkDTO.getSysShopName()) && clerkDTO.getSysShopName().contains("%")) {
                clerkDTO.setSysShopName(java.net.URLDecoder.decode(clerkDTO.getSysShopName(), "utf-8"));
                clerkDTO.setSysBossName(java.net.URLDecoder.decode(clerkDTO.getSysBossName(), "utf-8"));
                clerkDTO.setName(java.net.URLDecoder.decode(clerkDTO.getName(), "utf-8"));
                clerkDTO.setNickname(java.net.URLDecoder.decode(clerkDTO.getNickname(), "utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clerkDTO;
    }

    /**
     * 获取老板信息
     *
     * @return
     */
    public static SysBossDTO getBossInfo() {
        String token = getUserToken(LoginEnum.BOSS);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String sysBossDTO = JedisUtils.get(token);
        SysBossDTO bossDTO = (new Gson()).fromJson(sysBossDTO, SysBossDTO.class);
        try {
            bossDTO.setNickname(java.net.URLDecoder.decode(bossDTO.getNickname(), "utf-8"));
            bossDTO.setName(java.net.URLDecoder.decode(bossDTO.getName(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bossDTO;
    }

    /**
     * 老板切换店铺
     *
     * @param sysBossDTO
     */
    public static void bossSwitchShops(SysBossDTO sysBossDTO) {
        Gson gson = new Gson();
        String bossInfoStr = gson.toJson(sysBossDTO);
        JedisUtils.set(getUserToken(LoginEnum.BOSS), bossInfoStr, ConfigConstant.logintokenPeriod);
    }

    /**
     * 获取登陆的token信息
     * @return
     */
    private static String getUserToken(LoginEnum loginEnum) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);
        //只能获取到usertype对应的token
        String userType = tokenValue.get("usertype");
        if (loginEnum.getUserType().equals(userType)) {
            String token = tokenValue.get(loginEnum.getLoginToken());
            return token;
        }
        System.out.println("userType = " + userType + ",loginEnum.userType =" + loginEnum.getUserType());
        return null;
    }

    /**
     * 获取请求信息
     * @param request
     * @return
     */
    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(6);
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


}
