package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * FileName: ProjectTypeController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 项目类别
 */
@Controller
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
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("添加项目一级类别传入参数={}", "shopProjectTypeDTO = [" + shopProjectTypeDTO + "]");

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        shopProjectTypeDTO.setId(IdGen.uuid());
        shopProjectTypeDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
        shopProjectTypeDTO.setCreateDate(new Date());
        int info = projectService.saveProjectTypeInfo(shopProjectTypeDTO);

        responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);

        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 获取一二级项目列表
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOneTwoLevelProject", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProjectTypeDTO>> getOneTwoLevelProject(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopProjectTypeDTO shopProjectTypeDTO = new ShopProjectTypeDTO();
        shopProjectTypeDTO.setId(id);
        //查询数据
        List<ShopProjectTypeDTO> list = projectService.getTwoLevelProjectList(shopProjectTypeDTO);

        ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findTwoLevelProject方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

}
