package com.wisdom.business.controller.crossBorder;

import com.wisdom.business.service.product.OfflineProductService;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 跨境商品
 */
@Controller
@RequestMapping(value = "crossBorder/order")
public class BorderProductController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;
    @Autowired
    private OfflineProductService offlineProductService;
    /**
     * 获取商城首页的跨境商品列表
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO<OfflineProductDTO>>>
     *
     */
    @RequestMapping(value = "getBorderSpecialProductList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<PageParamVoDTO<List<ProductDTO>>> getBorderSpecialProductList(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("获取跨境商品列表==={}开始" , startTime);
        ResponseDTO<PageParamVoDTO<List<ProductDTO>>> responseDTO = new ResponseDTO<>();
        PageParamVoDTO<List<ProductDTO>> page = productService.queryProductsByParameters(pageParamVoDTO);
        logger.info("查询到的跨境商品个数===" + page.getResponseData().size());
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取微商城的产品列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
    /**
     * 获取跨境商品的详细信息
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<OfflineProductDTO>>
     *
     */
    @RequestMapping(value = "getBorderSpecialProductDetail", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<ProductDTO<OfflineProductDTO>> getBorderSpecialProductDetail(@RequestParam String productId) {
        long startTime = System.currentTimeMillis();
        logger.info("获取跨境某个商品=={}详情==={}开始" ,productId,startTime);
        ResponseDTO<ProductDTO<OfflineProductDTO>> responseDTO = new ResponseDTO<>();
        ProductDTO<OfflineProductDTO> offlineProductDTO = offlineProductService.getOfflineProductDetailById(productId);
        logger.info("得到跨境某个商品详情===" + offlineProductDTO);
        responseDTO.setResponseData(offlineProductDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取跨境某个商品详情,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
    /**
     * 获取跨境商品品牌列表
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO<OfflineProductDTO>>>
     *
     */
    @RequestMapping(value = "getBorderSpecialProductBrandList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<List<String>> getBorderSpecialProductBrandList() {
        long startTime = System.currentTimeMillis();
        logger.info("获取跨境商品品牌列表==={}开始" , startTime);
        ResponseDTO<List<String>> responseDTO = new ResponseDTO<>();
        List<String> brandList = productService.getBorderSpecialProductBrandList();
        logger.info("获取跨境商品品牌列表===" + brandList.size());
        responseDTO.setResponseData(brandList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取跨境商品品牌列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
}
