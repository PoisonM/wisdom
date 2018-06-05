/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.beauty.util;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    public static final String beautyUserLoginToken = "beautyuserlogintoken";
    public static final String beautyBossLoginToken = "beautybosslogintoken";
    public static final String beautyClerkLoginToken = "beautyclerklogintoken";

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoDTO getUserInfo() {
        String token = getUserToken(beautyUserLoginToken);
        String userInfoStr = JedisUtils.get(token);
        UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfoStr, UserInfoDTO.class);
        return userInfoDTO;
    }

    /**
     * 获取店员信息
     *
     * @return
     */
    public static SysClerkDTO getClerkInfo() {
        String token = getUserToken(beautyClerkLoginToken);
        String sysClerkDTO = JedisUtils.get(token);
        SysClerkDTO clerkDTO = (new Gson()).fromJson(sysClerkDTO, SysClerkDTO.class);
        return clerkDTO;
    }

    /**
     * 获取老板信息
     *
     * @return
     */
    public static SysBossDTO getBossInfo() {
        String token = getUserToken(beautyBossLoginToken);
        String sysBossDTO = JedisUtils.get(token);
        SysBossDTO bossDTO = (new Gson()).fromJson(sysBossDTO, SysBossDTO.class);
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
        JedisUtils.set(getUserToken(beautyBossLoginToken), bossInfoStr, ConfigConstant.logintokenPeriod);
    }

    /**
     * 获取登陆的token信息
     * @return
     */
    private static String getUserToken(String loginToken) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);
        String token = tokenValue.get(loginToken);
        if (StringUtils.isBlank(loginToken)) {
            System.out.println("商户平台登陆获取userTyp或token为空");
            return null;
        }
        return token;
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
