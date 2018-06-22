package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.extDto.ExtShopScanProductInfoDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: productController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "productInfo")
public class ProductController {

    @Resource
    private ShopProductInfoService shopProductInfoService;

    @Autowired
    private MongoUtils mongoUtils;
    @Autowired
    private RedisUtils redisUtils;

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
    ResponseDTO<List<ShopUserProductRelationDTO>> getUserProductList(@RequestParam String sysUserId,@RequestParam(required = false) String surplusTimes) {
        String sysShopId=null;
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        if(clerkInfo!=null){
             sysShopId = clerkInfo.getSysShopId();
        }
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if(bossInfo!=null){
            sysShopId = bossInfo.getCurrentShopId();
        }
        ResponseDTO<List<ShopUserProductRelationDTO>> responseDTO = new ResponseDTO<>();
        if (StringUtils.isBlank(sysShopId) || StringUtils.isBlank(sysUserId)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo(BusinessErrorCode.NULL_PROPERTIES.getCode());
            return responseDTO;
        }
        ShopUserProductRelationDTO shopUserProductRelationDTO = new ShopUserProductRelationDTO();
        shopUserProductRelationDTO.setSysShopId(sysShopId);
        shopUserProductRelationDTO.setSysUserId(sysUserId);
        if(StringUtils.isNotBlank(surplusTimes)){
            shopUserProductRelationDTO.setSurplusTimes(Integer.parseInt(surplusTimes));
        }
        List<ShopUserProductRelationDTO> userProductInfoList = shopProductInfoService.getUserProductInfoList(shopUserProductRelationDTO);

        responseDTO.setResponseData(userProductInfoList);
        responseDTO.setResult(StatusConstant.SUCCESS);

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
        //查询数据
        ShopProductInfoResponseDTO shopProductInfoResponseDTO = shopProductInfoService.getProductDetail(productId);
        ResponseDTO<ShopProductInfoResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopProductInfoResponseDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        ResponseDTO<List<ShopProductTypeDTO>> responseDTO = new ResponseDTO<>();
        ShopProductTypeDTO shopProductTypeDTO=new ShopProductTypeDTO();
        shopProductTypeDTO.setSysShopId(sysClerkDTO.getSysShopId());
        List<ShopProductTypeDTO> list = shopProductInfoService.getOneLevelProductList(shopProductTypeDTO);
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ShopProductTypeDTO shopProductTypeDTO = new ShopProductTypeDTO();
        shopProductTypeDTO.setId(id);
        shopProductTypeDTO.setSysShopId(redisUtils.getShopId());
        //查询数据
        List<ShopProductTypeDTO> list = shopProductInfoService.getTwoLevelProductList(shopProductTypeDTO);
        ResponseDTO<List<ShopProductTypeDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        return responseDTO;
    }

    /**
     * 更新产品信息
     *
     * @param extShopProductInfoDTO
     * @return
     */
    @RequestMapping(value = "/updateProductInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateProductInfo(@RequestBody ExtShopProductInfoDTO extShopProductInfoDTO) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        int info = shopProductInfoService.updateProductInfo(extShopProductInfoDTO);

        responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
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

        return responseDTO;
    }

    /**
     * 查询某个店的产品
     *
     * @return
     */
    @RequestMapping(value = "searchShopProductList", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<HashMap<String, Object>> searchShopProductList(@RequestParam String filterStr) {

        String sysShopId = null;
        if (StringUtils.isBlank(sysShopId)) {
            logger.info("pad端查询某个店的产品传入参数={}", "filterStr = [" + filterStr + "]");
            SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
            if(clerkInfo!=null){
                sysShopId = clerkInfo.getSysShopId();
            }
        }
        if (StringUtils.isBlank(sysShopId)) {
            logger.info("老板端查询某个店的产品传入参数={}", "filterStr = [" + filterStr + "]");
            SysBossDTO bossInfo = UserUtils.getBossInfo();
            if(bossInfo!=null){
                sysShopId = bossInfo.getCurrentShopId();
            }
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
        //获取二级和三级
        ShopProductTypeDTO shopProductType=new ShopProductTypeDTO();
        shopProductType.setSysShopId(sysShopId);
        List<ShopProductTypeDTO> twoAndThreeTypeList=shopProductInfoService.getTwoLevelProductList(shopProductType);
        //一个一级对应所有的二级
        Map<String,Map<String,ShopProductTypeDTO>> twoMap=null;
        if(CollectionUtils.isNotEmpty(twoAndThreeTypeList)){
            twoMap=new HashMap<>();
            for (ShopProductTypeDTO dto:twoAndThreeTypeList){
                if(twoMap.containsKey(dto.getParentId())){
                    Map<String,ShopProductTypeDTO> devMap=twoMap.get(dto.getParentId());
                    devMap.put(dto.getProductTypeName(),dto);
                    twoMap.put(dto.getParentId(),devMap);
                }else {
                    if(StringUtils.isNotBlank(dto.getParentId())){
                        Map<String,ShopProductTypeDTO> devMap=new HashMap<>();
                        devMap.put(dto.getProductTypeName(),dto);
                        twoMap.put(dto.getParentId(),devMap);
                    }
                }
            }
        }
        ArrayList<Object> levelList = new ArrayList<>();
        ArrayList<Object> oneAndTwoLevelList = new ArrayList<>();
        //遍历缓存的一级产品
        for (ShopProductTypeDTO shopProductTypeDTO : oneLevelProductList) {
            HashMap<Object, Object> helperMap = new HashMap<>(16);
            HashMap<Object, Object> oneAndTwoHelperMap = new HashMap<>(16);
            //承接二级产品
            HashMap<Object, Object> twoLevelMap = new HashMap<>(16);
            HashMap<Object, Object> oneAndTwoLevelMap = new HashMap<>(16);
            for (ShopProductInfoDTO dto : shopProductInfo) {
                if (shopProductTypeDTO.getId().equals(dto.getProductTypeOneId())) {
                    twoLevelMap.put(dto.getProductTypeTwoName(), dto);
                }
            }
            if(twoMap.get(shopProductTypeDTO.getId())!=null){
                oneAndTwoLevelMap.putAll(twoMap.get(shopProductTypeDTO.getId()));
            }
            helperMap.put("levelTwoDetail", twoLevelMap);
            helperMap.put("levelOneDetail", shopProductTypeDTO);
            if(twoLevelMap.size()>0){
                levelList.add(helperMap);
            }

            oneAndTwoHelperMap.put("levelTwoDetail", oneAndTwoLevelMap);
            oneAndTwoHelperMap.put("levelOneDetail", shopProductTypeDTO);
            oneAndTwoLevelList.add(oneAndTwoHelperMap);
        }
        //detailLevel集合中包含了一级二级的关联信息，detailProduct集合是所有产品的列表
        returnMap.put("detailLevel", levelList);
        returnMap.put("oneAndTwoLevelList", oneAndTwoLevelList);
        returnMap.put("detailProduct", shopProductInfo);
        responseDTO.setResponseData(returnMap);
        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }


    /**
     * 扫码入库
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/getProductInfoByScanCode", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getProductInfoByScanCode(@RequestParam String code) {

        String[] codeArray =code.split(",");
        ResponseDTO responseDTO = new ResponseDTO();
        ExtShopProductInfoDTO scanShopProductInfo = mongoUtils.getScanShopProductInfo(codeArray[1]);
        logger.info("扫码入库查询出来的数据为={}", "scanShopProductInfo = [" + scanShopProductInfo + "]");
        ExtShopScanProductInfoDTO extShopScanProductInfoDTO = scanShopProductInfo.getShowapi_res_body();
        if (null != extShopScanProductInfoDTO) {
            if(extShopScanProductInfoDTO.getFlag().equals("true")){
                //查询出来的信息转换为产品对象
                ExtShopProductInfoDTO productInfoDTO = new ExtShopProductInfoDTO();
                productInfoDTO.setProductName(extShopScanProductInfoDTO.getGoodsName());
                productInfoDTO.setManuName(extShopScanProductInfoDTO.getManuName());
                productInfoDTO.setTradeMark(extShopScanProductInfoDTO.getTrademark());
                productInfoDTO.setProductUrl(extShopScanProductInfoDTO.getImg());
                productInfoDTO.setNote(extShopScanProductInfoDTO.getNote());
                productInfoDTO.setTradeMark(extShopScanProductInfoDTO.getTrademark());
                String price = extShopScanProductInfoDTO.getPrice();
                if (StringUtils.isNotBlank(price)) {
                    productInfoDTO.setMarketPrice(new BigDecimal(price));
                }
                productInfoDTO.setCode(codeArray[1]);
                productInfoDTO.setProductCode(codeArray[1]);
                String spec = extShopScanProductInfoDTO.getSpec();
                if (StringUtils.isNotBlank(spec)) {
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(spec);
                    productInfoDTO.setProductSpec(m.replaceAll("").trim());
                    productInfoDTO.setProductSpecUnit(spec.replaceAll("\\d+", ""));
                }
                List<String> imageList = new ArrayList<>();
                imageList.add(extShopScanProductInfoDTO.getImg());
                productInfoDTO.setImageList(imageList);
                responseDTO.setResponseData(productInfoDTO);
                responseDTO.setResult(StatusConstant.SUCCESS);
            }else{
                responseDTO.setResult(StatusConstant.FAILURE);
            }
        }

        return responseDTO;
    }

    /**
     * 添加产品信息
     *
     * @param shopProductInfoDTO
     * @return
     */
    @RequestMapping(value = "/saveProductInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> saveProductInfo(@RequestBody ExtShopProductInfoDTO shopProductInfoDTO) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        int productInfo = shopProductInfoService.saveProductInfo(shopProductInfoDTO);
        responseDTO.setResult(productInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData(productInfo);
        return responseDTO;
    }

    /**
     * 获取产品详情
     *
     * @param productCode
     *
     * @return
     * */
    @RequestMapping(value = "/getProductInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getProductInfo(@RequestParam String  productCode) {

        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();
        shopProductInfoDTO.setProductCode(productCode);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        List<ShopProductInfoDTO>  shopProductInfos = shopProductInfoService.getShopProductInfo(shopProductInfoDTO);
        responseDTO.setResult(shopProductInfos.size() > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData(shopProductInfos);
        return responseDTO;
    }

}
