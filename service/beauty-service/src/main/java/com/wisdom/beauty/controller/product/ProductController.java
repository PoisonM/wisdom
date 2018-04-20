package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * FileName: productController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "productInfo")
public class ProductController {

    @Resource
    private ShopProductInfoService shopProductInfoService;
    @Autowired
    private ShopCustomerProductRelationService shopCustomerProductRelationService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个用户的产品列表信息
     *
     * @param sysUserId
     * @param sysShopId
     * @return
     */
    @RequestMapping(value = "getUserProductList", method = {RequestMethod.POST, RequestMethod.GET})
//    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopUserProductRelationDTO>> getUserProductList(@RequestParam String sysUserId,
                                                                     @RequestParam String sysShopId) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("查询某个用户的产品列表信息传入参数={}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
        ResponseDTO<List<ShopUserProductRelationDTO>> responseDTO = new ResponseDTO<>();
        if (StringUtils.isBlank(sysShopId) || StringUtils.isBlank(sysUserId)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo(BusinessErrorCode.NULL_PROPERTIES.getCode());
            return responseDTO;
        }

        ShopUserProductRelationDTO shopUserProductRelationDTO = new ShopUserProductRelationDTO();
        shopUserProductRelationDTO.setSysShopId(sysShopId);
        shopUserProductRelationDTO.setSysUserId(sysUserId);
        List<ShopUserProductRelationDTO> userProductInfoList = shopProductInfoService.getUserProductInfoList(shopUserProductRelationDTO);

        responseDTO.setResponseData(userProductInfoList);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("查询某个用户的产品列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据产品id获取产品的详细信息
     * @Date:2018/4/10 14:22
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopProductInfoDTO> getProduct(@PathVariable String productId) {
        long currentTimeMillis = System.currentTimeMillis();
        //查询数据
        ShopProductInfoDTO shopProductInfoDTO = shopProductInfoService.getProductDetail(productId);

        ResponseDTO<ShopProductInfoDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopProductInfoDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("getProduct方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;

    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取一级列表
     * @Date:2018/4/10 17:29
     */
    @RequestMapping(value = "/oneLevelProduct", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProductTypeDTO>> findOneLevelProduct(@RequestParam String sysShopId) {
        long currentTimeMillis = System.currentTimeMillis();
        ResponseDTO<List<ShopProductTypeDTO>> responseDTO = new ResponseDTO<>();
        List<ShopProductTypeDTO> list = shopProductInfoService.getOneLevelProductList(sysShopId);
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findOneLevelProduct方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级项目的id
     * @Return:
     * @Description: 获取二级列表
     * @Date:2018/4/10 17:36
     */
    @RequestMapping(value = "/twoLevelProduct", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProductTypeDTO>> findTwoLevelProduct(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopProductTypeDTO shopProductTypeDTO = new ShopProductTypeDTO();
        shopProductTypeDTO.setId(id);
        //查询数据
        List<ShopProductTypeDTO> list = shopProductInfoService.getTwoLevelProductList(shopProductTypeDTO);

        ResponseDTO<List<ShopProductTypeDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findTwoLevelProduct方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级项目的id
     * @Return:
     * @Description: 获取三级列表
     * @Date:2018/4/10 17:36
     */
    @RequestMapping(value = "/threeLevelProduct", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProductInfoDTO>> findThreeLevelProject(@RequestParam String sysShopId,
                                                                @RequestParam String productTypeOneId,
                                                                @RequestParam String productTypeTwoId,
                                                                @RequestParam(required =false) String productName,
                                                                @RequestParam int pageSize) {
        long currentTimeMillis = System.currentTimeMillis();
        PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();

        shopProductInfoDTO.setSysShopId(sysShopId);
        shopProductInfoDTO.setProductTypeOneId(productTypeOneId);
        shopProductInfoDTO.setProductTypeTwoId(productTypeTwoId);
        shopProductInfoDTO.setProductName(productName);

        pageParamVoDTO.setRequestData(shopProductInfoDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopProductInfoDTO> list = shopProductInfoService.getThreeLevelProductList(pageParamVoDTO);

        ResponseDTO<List<ShopProductInfoDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findThreeLevelProject方法耗时={}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


}
