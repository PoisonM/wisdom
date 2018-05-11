/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.beauty.util;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.extDto.ExtUserDTO;
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

    public static ExtUserDTO ExtUserDTO() {
        return null;
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoDTO getUserInfo() {
        String token = getUserToken();
        String userInfoStr = JedisUtils.get(token);
        UserInfoDTO userInfoDTO = (new Gson()).fromJson(userInfoStr, UserInfoDTO.class);
        return getTestUserInfoDTO();
    }

    /**
     * 获取店员信息
     *
     * @return
     */
    public static SysClerkDTO getClerkInfo() {
        String token = getUserToken();
        String sysClerkDTO = JedisUtils.get(token);
        SysClerkDTO clerkDTO = (new Gson()).fromJson(sysClerkDTO, SysClerkDTO.class);
        return getTestClerkInfo();
    }

    /**
     * 获取老板信息
     *
     * @return
     */
    public static SysBossDTO getBossInfo() {
        String token = getUserToken();
        String sysBossDTO = JedisUtils.get(token);
        SysBossDTO bossDTO = (new Gson()).fromJson(sysBossDTO, SysBossDTO.class);
        return getTestBossInfo();
    }

    /**
     * 获取登陆的token信息
     * @return
     */
    private static String getUserToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);

        String userType = tokenValue.get("usertype");
        String token = tokenValue.get("beautylogintoken");

        if(StringUtils.isBlank(userType) || StringUtils.isBlank(token)){
            System.out.println("商户平台登陆获取userTyp或token为空"+userType+token);
            return null;
        }
        return token;
    }

    /**
     * 获取登陆人的角色信息
     *
     * @return
     */
    private static String getUserType() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> tokenValue = getHeadersInfo(request);
        String userType = tokenValue.get("usertype");
        if (StringUtils.isNotBlank(userType)) {
            System.out.println("获取当前登录人的角色为空");
            throw new ServiceException("获取当前登录人的角色为空");
        }
        return userType;
    }

    /**
     * 获取请求信息
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

    /**
     * 获取测试老板数据
     * @return
     */
    public static SysBossDTO getTestBossInfo() {
        SysBossDTO sysBossDTO = new SysBossDTO();
        sysBossDTO.setMobile("18810142926");
        sysBossDTO.setNickname("赵得良");
        sysBossDTO.setId("11");
        return sysBossDTO;
    }

    /**
     * 获取测试店员信息
     *
     * @return
     */
    public static SysClerkDTO getTestClerkInfo() {
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
    }

    /**
     * 获取测试用户信息
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

}
