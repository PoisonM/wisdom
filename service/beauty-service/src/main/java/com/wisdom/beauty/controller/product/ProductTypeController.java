package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.extDto.RequestDTO;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: ProductTypeController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 产品类别相关
 */
@Controller
@RequestMapping(value = "productTypeInfo")
public class ProductTypeController {

    @Resource
    private ShopProductInfoService shopProductInfoService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 添加产品一级类别信息
     *
     * @param productTypeName
     * @param status
     * @return
     */
    @RequestMapping(value = "/saveProductTypeInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> saveProductTypeInfo(@RequestParam String productTypeName, @RequestParam String status) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("添加产品一级类别信息传入参数={}", "productTypeName = [" + productTypeName + "], status = [" + status + "]");
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        ShopProductTypeDTO shopProductTypeDTO = new ShopProductTypeDTO();
        shopProductTypeDTO.setStatus(status);
        shopProductTypeDTO.setProductTypeName(productTypeName);
        int updateInfo = shopProductInfoService.saveProductTypeInfo(shopProductTypeDTO);
        responseDTO.setResult(updateInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        logger.info("更新产品一级类别信息方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


    /**
     * 更新产品一级类别信息
     *
     * @param shopProductTypeDTO
     * @return
     */
    @RequestMapping(value = "/updateOneLevelTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateOneLevelTypeInfo(@RequestBody ShopProductTypeDTO shopProductTypeDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        logger.info("更新产品一级类别信息传入参数={}", "shopProductTypeDTO = [" + shopProductTypeDTO + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (null == shopProductTypeDTO || StringUtils.isBlank(shopProductTypeDTO.getId())) {
            logger.error("更新产品一级类别信息传入参数异常");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("主键为空");
            return responseDTO;
        }

        int updateInfo = shopProductInfoService.updateProductTypeInfo(shopProductTypeDTO);
        responseDTO.setResult(updateInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        logger.info("更新产品一级类别信息方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 更新产品二级级类别信息
     *
     * @param dtoRequestDTO
     * @return
     */
    @RequestMapping(value = "/updateTwoLevelTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateTwoLevelTypeInfo(@RequestBody RequestDTO<ShopProductTypeDTO> dtoRequestDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        logger.info("更新产品二级级类别信息传入参数={}", "dtoRequestDTO = [" + dtoRequestDTO + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (null == dtoRequestDTO || CommonUtils.objectIsEmpty(dtoRequestDTO) || CommonUtils.objectIsEmpty(dtoRequestDTO.getRequestList())) {
            logger.error("更新产品二级类别信息传入参数异常");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("主键为空");
            return responseDTO;
        }
        List<ShopProductTypeDTO> shopProductTypeDTOS = dtoRequestDTO.getRequestList();

        int updateInfo = shopProductInfoService.updateProductTypeListInfo(shopProductTypeDTOS);
        responseDTO.setResult(updateInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        logger.info("更新产品二级类别信息方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
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
        if (null == bossInfo || com.wisdom.common.util.StringUtils.isBlank(bossInfo.getCurrentShopId())) {
            logger.error("获取老板信息异常，{}", "bossInfo = [" + bossInfo + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("获取老板信息异常");
            return true;
        }
        return false;
    }
}
