package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.extDto.RequestDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: ProjectTypeController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 项目类别
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "projectType")
public class ProjectTypeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopProjectService projectService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    /**
     * 添加项目一级类别
     */
    @RequestMapping(value = "saveShopProjectType", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> saveShopProjectType(@RequestBody ShopProjectTypeDTO shopProjectTypeDTO) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        int info = projectService.saveProjectTypeInfo(shopProjectTypeDTO, bossInfo);
        responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        return responseDTO;
    }


    /**
     * 修改一级项目类别
     */
    @RequestMapping(value = "updateOneLevelProjectType", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> updateOneLevelProjectType(@RequestBody ShopProjectTypeDTO shopProjectTypeDTO) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (null == shopProjectTypeDTO || StringUtils.isBlank(shopProjectTypeDTO.getId())) {
            responseDTO.setResponseData("传入参数为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        int info = projectService.updateProjectTypeInfo(shopProjectTypeDTO);
        logger.info("修改一级项目类别={}", info > 0 ? "成功" : "失败");
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }


    /**
     * 批量修改二级项目类别
     */
    @RequestMapping(value = "updateTwoLevelProjectType", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> updateTwoLevelProjectType(@RequestBody RequestDTO<ShopProjectTypeDTO> requestDTO) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (null == requestDTO || CommonUtils.objectIsEmpty(requestDTO.getRequestList())) {
            responseDTO.setResponseData("修改二级项目类别传入参数有问题");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        List<ShopProjectTypeDTO> shopProjectTypeDTOS = requestDTO.getRequestList();
        for (ShopProjectTypeDTO shopProjectTypeDTO : shopProjectTypeDTOS) {
            //主键为空说明是新增操作
            if (StringUtils.isBlank(shopProjectTypeDTO.getId())) {
                logger.info("批量修改二级项目类别新增操作={}", shopProjectTypeDTO.getProjectTypeName());
                SysBossDTO bossInfo = UserUtils.getBossInfo();
                projectService.saveProjectTypeInfo(shopProjectTypeDTO, bossInfo);
            } else {
                logger.info("批量修改二级项目类别修改操作={}", shopProjectTypeDTO.getProjectTypeName());
                projectService.updateProjectTypeInfo(shopProjectTypeDTO);
            }
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }


    /**
     * 判断老板有当前店铺信息
     *
     * @param responseDTO
     * @param bossInfo
     * @return
     */
    private boolean judgeBossCurrentShop(ResponseDTO<Object> responseDTO, SysBossDTO bossInfo) {
        if (null == bossInfo || StringUtils.isBlank(bossInfo.getCurrentShopId())) {
            logger.error("获取老板信息异常，{}", "bossInfo = [" + bossInfo + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("获取老板信息异常");
            return true;
        }
        return false;
    }
}
