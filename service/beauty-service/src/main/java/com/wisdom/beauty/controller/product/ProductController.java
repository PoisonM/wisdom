package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.constant.StatusConstant;
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
    ResponseDTO<ShopUserProductRelationDTO> getProduct(@PathVariable String productId) {
        long currentTimeMillis = System.currentTimeMillis();
        ResponseDTO<ShopUserProductRelationDTO> responseDTO = new ResponseDTO<>();
        ShopUserProductRelationDTO shopUserProductRelationDTO = shopCustomerProductRelationService.getShopProductInfo(productId);
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopUserProductRelationDTO);
        logger.info("查询某个用户的产品列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;

    }


}
