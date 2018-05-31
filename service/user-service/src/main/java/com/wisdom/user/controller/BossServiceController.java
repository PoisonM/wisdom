/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.user.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.user.service.BossInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * FileName: BossServiceController
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 老板相关
 */
@RestController
public class BossServiceController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BossInfoService bossInfoService;


    /**
     * 更新老板信息
     *
     * @param sysBossDTO
     * @return
     */
    @RequestMapping(value = "updateBossInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Object> updateBossInfo(@RequestBody SysBossDTO sysBossDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        logger.info("更新老板信息传入参数={}", "sysBossDTO = [" + sysBossDTO + "]");
        int bossInfo = bossInfoService.updateBossInfo(sysBossDTO);
        responseDTO.setResponseData(bossInfo);
        responseDTO.setResult(bossInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        return responseDTO;
    }


}
