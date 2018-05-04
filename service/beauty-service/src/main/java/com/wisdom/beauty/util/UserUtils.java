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
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoDTO getTestUserInfoDTO() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId("1");
        userInfoDTO.setMobile("18810142926");
        userInfoDTO.setNickname("小明");
        userInfoDTO.setPhoto("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%91%98%E5%B7%A5%E5%9B%BE%E7%89%87.png");
        return userInfoDTO;
    }

    /**
     * 获取店员信息
     *
     * @return
     */
    public static SysClerkDTO getClerkInfo() {
        //挡板
        SysClerkDTO clerkDTO = new SysClerkDTO();
        clerkDTO.setWeixinAttentionStatus("1");
        clerkDTO.setSysUserId("3");
        clerkDTO.setSysBossName("王老板");
        clerkDTO.setSysBossId("sys_boss_id");
        clerkDTO.setName("陈莺梦");
        clerkDTO.setId("clerkId1");
        clerkDTO.setSysShopId("11");
        clerkDTO.setNickname("张欢昵称");
        clerkDTO.setMobile("18810142926");
        clerkDTO.setSysShopName("汉方美业");
        clerkDTO.setScore(70f);
        return clerkDTO;

//        SysClerkDTO sysClerkDTO = null;
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            Map<String, String> tokenValue = getHeadersInfo(request);
//            String token = tokenValue.get("logintoken");
//            String userInfoStr = JedisUtils.get(token);
//            sysClerkDTO = (new Gson()).fromJson(userInfoStr, SysClerkDTO.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sysClerkDTO;
    }

    /**
     * 获取老板信息
     *
     * @return
     */
    public static SysBossDTO getBossInfo() {
//        SysBossDTO sysBossDTO = null;
//        try {
//            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            Map<String, String> tokenValue = getHeadersInfo(request);
//            String token = tokenValue.get("logintoken");
//            String userInfoStr = JedisUtils.get(token);
//            sysBossDTO = (new Gson()).fromJson(userInfoStr, SysBossDTO.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sysBossDTO;
        return getTestBossInfo();
    }

    public static SysBossDTO getTestBossInfo() {
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setMobile("18810142926");
        sysBossDTO.setNickname("赵得良");
        sysBossDTO.setId("zdl");
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
