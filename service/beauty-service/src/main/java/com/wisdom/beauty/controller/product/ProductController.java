package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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


}
