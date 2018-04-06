/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.customer.SysUserClerkDTO;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.ObjectUtils;
import com.wisdom.common.util.SMSUtil;
import com.wisdom.common.util.WeixinUtil;
import com.wisdom.user.interceptor.LoginRequired;
import com.wisdom.user.service.ClerkInfoService;
import com.wisdom.user.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@RestController
public class ClerkInfoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ClerkInfoService clerkInfoService;

    /**
     * 获取店员列表信息
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getClerkInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    List<SysUserClerkDTO> getClerkInfo(@RequestBody String shopId) {

        long startTime = System.currentTimeMillis();
        ResponseDTO<List<SysUserClerkDTO>> listResponseDTO = new ResponseDTO<>();

        logger.info("获取店员列表信息传入参数shopId = {}",shopId );
        SysUserClerkDTO sysUserClerkDTO = new SysUserClerkDTO();
        sysUserClerkDTO.setSysShopId(shopId);
        List<SysUserClerkDTO> clerkInfo = clerkInfoService.getClerkInfo(sysUserClerkDTO);
        if(CommonUtils.objectIsEmpty(clerkInfo)){
            logger.info("获取的店员列表信息为空！");
             return null;
        }

        logger.info("获取店员列表信息耗时{}毫秒",(System.currentTimeMillis()-startTime));
        return clerkInfo;
    }


}
