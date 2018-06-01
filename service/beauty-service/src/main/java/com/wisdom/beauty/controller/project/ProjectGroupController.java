package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.extDto.ExtShopProjectGroupDTO;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * FileName: ProjectController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "projectGroupInfo")
public class ProjectGroupController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    /**
     * 保存用户的套卡信息
     *
     * @param shopProjectGroupDTO
     * @return
     */
    @RequestMapping(value = "saveProjectGroupInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<HashMap<Object, Object>> saveProjectGroupInfo(@RequestBody ExtShopProjectGroupDTO shopProjectGroupDTO) {

        ResponseDTO<HashMap<Object, Object>> responseDTO = new ResponseDTO<>();

        int groupInfo = shopProjectGroupService.saveProjectGroupInfo(shopProjectGroupDTO);

        responseDTO.setResult(groupInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

        return responseDTO;
    }

    /**
     * 编辑用户的套卡信息
     *
     * @param shopProjectGroupDTO
     * @return
     */
    @RequestMapping(value = "updateProjectGroupInfo", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<Object> updateProjectGroupInfo(@RequestBody ExtShopProjectGroupDTO shopProjectGroupDTO) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (StringUtils.isBlank(shopProjectGroupDTO.getId())) {
            logger.error("编辑用户的套卡信息主键为空{}", "shopProjectGroupDTO = [" + shopProjectGroupDTO + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("传入主键为空");
            return responseDTO;
        }
        logger.info("编辑用户的套卡信息传入参数={}", "shopProjectGroupDTO = [" + shopProjectGroupDTO + "]");

        int groupInfo = shopProjectGroupService.updateProjectGroupInfo(shopProjectGroupDTO);

        responseDTO.setResult(groupInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

        return responseDTO;
    }

}
