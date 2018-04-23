/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.user.service.ClerkInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@RestController
public class ClerkServiceController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ClerkInfoService clerkInfoService;

    /**
     * 获取店员列表信息
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getClerkInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    List<SysClerkDTO> getClerkInfo(@RequestParam(value = "shopId") String shopId) {

        long startTime = System.currentTimeMillis();
        ResponseDTO<List<SysClerkDTO>> listResponseDTO = new ResponseDTO<>();

        logger.info("获取店员列表信息传入参数shopId = {}",shopId );
        SysClerkDTO SysClerkDTO = new SysClerkDTO();
        SysClerkDTO.setSysShopId(shopId);
        List<SysClerkDTO> clerkInfo = clerkInfoService.getClerkInfo(SysClerkDTO);
        if(CommonUtils.objectIsEmpty(clerkInfo)){
            logger.info("获取的店员列表信息为空！");
            listResponseDTO.setResult(StatusConstant.SUCCESS);
             return null;
        }
        listResponseDTO.setResponseData(clerkInfo);
        listResponseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("获取店员列表信息耗时{}毫秒",(System.currentTimeMillis()-startTime));
        return clerkInfo;
    }


}
