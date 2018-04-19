/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.beauty.util;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
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

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoDTO getUserInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);
        String token = tokenValue.get("logintoken");
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
        SysClerkDTO sysClerkDTO = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, String> tokenValue = getHeadersInfo(request);
            String token = tokenValue.get("logintoken");
            String userInfoStr = JedisUtils.get(token);
            sysClerkDTO = (new Gson()).fromJson(userInfoStr, SysClerkDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysClerkDTO;
    }

    /**
     * 获取老板信息
     *
     * @return
     */
    public static SysBossDTO getBossInfo() {
        SysBossDTO sysBossDTO = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, String> tokenValue = getHeadersInfo(request);
            String token = tokenValue.get("logintoken");
            String userInfoStr = JedisUtils.get(token);
            sysBossDTO = (new Gson()).fromJson(userInfoStr, SysBossDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysBossDTO;
    }

    /**
     * 获取店员信息
     *
     * @param request
     * @return
     */

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
