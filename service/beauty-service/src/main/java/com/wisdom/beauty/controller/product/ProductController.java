package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private ShopCustomerProductRelationService shopCustomerProductRelationService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个用户的产品列表信息
     *
     * @param sysUserId
     * @return
     */
    @RequestMapping(value = "getUserProductList", method = {RequestMethod.POST, RequestMethod.GET})
//    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopUserProductRelationDTO>> getUserProductList(@RequestParam String sysUserId) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();

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
    ResponseDTO<ShopProductInfoResponseDTO> getProduct(@PathVariable String productId) {
        long currentTimeMillis = System.currentTimeMillis();
        //查询数据
        ShopProductInfoResponseDTO shopProductInfoResponseDTO = shopProductInfoService.getProductDetail(productId);

        ResponseDTO<ShopProductInfoResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopProductInfoResponseDTO);
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
    ResponseDTO<List<ShopProductTypeDTO>> findOneLevelProduct() {
        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        ResponseDTO<List<ShopProductTypeDTO>> responseDTO = new ResponseDTO<>();
        ShopProductTypeDTO shopProductTypeDTO=new ShopProductTypeDTO();
        shopProductTypeDTO.setSysShopId(sysClerkDTO.getSysShopId());
        List<ShopProductTypeDTO> list = shopProductInfoService.getOneLevelProductList(shopProductTypeDTO);
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findOneLevelProduct方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级产品的id
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
     * 获取产品详情
     *
     * @param userProductInfoId
     * @return
     */
    @RequestMapping(value = "/getUserProductInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopUserProductRelationDTO> getUserProductInfo(@RequestParam String userProductInfoId) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<ShopUserProductRelationDTO> responseDTO = new ResponseDTO<>();
        //查询数据
        ShopUserProductRelationDTO shopUserProductRelationDTO = new ShopUserProductRelationDTO();
        shopUserProductRelationDTO.setId(userProductInfoId);
        List<ShopUserProductRelationDTO> productInfoList = shopProductInfoService.getUserProductInfoList(shopUserProductRelationDTO);
        if (CommonUtils.objectIsEmpty(productInfoList)) {
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }

        responseDTO.setResponseData(productInfoList.get(0));
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取产品详情方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 更新产品信息
     *
     * @param shopProductInfoDTO
     * @return
     */
    @RequestMapping(value = "/updateProductInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateProductInfo(@RequestBody ShopProductInfoDTO shopProductInfoDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        int info = shopProductInfoService.updateProductInfo(shopProductInfoDTO);

        responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        logger.info("获取产品详情方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级产品的id
     * @Return:
     * @Description: 获取三级列表
     * @Date:2018/4/10 17:36
     */
    @RequestMapping(value = "/threeLevelProduct", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProductInfoResponseDTO>> findThreeLevelProduct(@RequestParam String productTypeOneId,
                                                                @RequestParam String productTypeTwoId,
                                                                @RequestParam(required = false) String productName,
                                                                @RequestParam int pageSize) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();

        shopProductInfoDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopProductInfoDTO.setProductTypeOneId(productTypeOneId);
        shopProductInfoDTO.setProductTypeTwoId(productTypeTwoId);
        shopProductInfoDTO.setProductName(productName);

        pageParamVoDTO.setRequestData(shopProductInfoDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopProductInfoResponseDTO> list = shopProductInfoService.getThreeLevelProductList(pageParamVoDTO);

        ResponseDTO<List<ShopProductInfoResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findThreeLevelProduct方法耗时={}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某个店的产品
     *
     * @return
     */
    @RequestMapping(value = "searchShopProductList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<HashMap<String, Object>> searchShopProductList(@RequestParam String filterStr) {

        long currentTimeMillis = System.currentTimeMillis();
        String sysShopId = null;
        if (StringUtils.isBlank(sysShopId)) {
            logger.info("pad端查询某个店的产品传入参数={}", "filterStr = [" + filterStr + "]");
            SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
            sysShopId = clerkInfo.getSysShopId();
        }
        if (StringUtils.isBlank(sysShopId)) {
            logger.info("老板端查询某个店的产品传入参数={}", "filterStr = [" + filterStr + "]");
            SysBossDTO bossInfo = UserUtils.getBossInfo();
            sysShopId = bossInfo.getCurrentShopId();
        }
        logger.info("查询某个店的产品列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
        ResponseDTO<HashMap<String, Object>> responseDTO = new ResponseDTO<>();

        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();
        shopProductInfoDTO.setSysShopId(sysShopId);
        shopProductInfoDTO.setProductName(filterStr);

        HashMap<String, Object> returnMap = new HashMap<>(16);
        List<ShopProductInfoDTO> shopProductInfo = shopProductInfoService.getShopProductInfo(shopProductInfoDTO);

        if (CommonUtils.objectIsEmpty(shopProductInfo)) {
            logger.debug("查询某个店的产品列表信息查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
        }

        //查询某个店的一级产品
        ShopProductTypeDTO typeDTO = new ShopProductTypeDTO();
        typeDTO.setSysShopId(sysShopId);
        List<ShopProductTypeDTO> oneLevelProductList = shopProductInfoService.getOneLevelProductList(typeDTO);
        if (CommonUtils.objectIsEmpty(oneLevelProductList)) {
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }

        ArrayList<Object> levelList = new ArrayList<>();
        //遍历缓存的一级产品
        for (ShopProductTypeDTO shopProductTypeDTO : oneLevelProductList) {
            HashMap<Object, Object> helperMap = new HashMap<>(16);
            //承接二级产品
            HashMap<Object, Object> twoLevelMap = new HashMap<>(16);
            for (ShopProductInfoDTO dto : shopProductInfo) {
                if (shopProductTypeDTO.getId().equals(dto.getProductTypeOneId())) {
                    twoLevelMap.put(dto.getProductTypeTwoName(), dto);
                }
            }

            helperMap.put("levelTwoDetail", twoLevelMap);
            helperMap.put("levelOneDetail", shopProductTypeDTO);
            levelList.add(helperMap);
        }
        //detailLevel集合中包含了一级二级的关联信息，detailProduct集合是所有产品的列表
        returnMap.put("detailLevel", levelList);
        returnMap.put("detailProduct", shopProductInfo);
        responseDTO.setResponseData(returnMap);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("查询某个店的产品列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

}
