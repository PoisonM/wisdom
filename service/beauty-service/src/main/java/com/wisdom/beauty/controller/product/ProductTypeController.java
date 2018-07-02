package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.dto.ShopStockNumberDTO;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.extDto.RequestDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * FileName: ProductTypeController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 产品类别相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "productTypeInfo")
public class ProductTypeController {

    @Resource
    private ShopProductInfoService shopProductInfoService;
    @Resource
    private ShopStockService shopStockService;

    @Resource
    private RedisUtils redisUtils;

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
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        ShopProductTypeDTO shopProductTypeDTO = new ShopProductTypeDTO();
        shopProductTypeDTO.setStatus(status);
        shopProductTypeDTO.setProductTypeName(productTypeName);
        shopProductTypeDTO.setSysShopId(bossInfo.getCurrentShopId());
        int updateInfo = shopProductInfoService.saveProductTypeInfo(shopProductTypeDTO);
        responseDTO.setResult(updateInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
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
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (null == shopProductTypeDTO || StringUtils.isBlank(shopProductTypeDTO.getId())) {
            logger.error("更新产品一级类别信息传入参数异常");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("主键为空");
            return responseDTO;
        }

        int updateInfo = shopProductInfoService.updateProductTypeInfo(shopProductTypeDTO);
        responseDTO.setResult(updateInfo > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
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
        return responseDTO;
    }

    /**
     * 根据产品类型、产品等级，查询一级二级产品
     *
     * @param productType "0", "客装产品";"1", "院装产品";"2", "易耗品"
     * @param levelOneId
     * @param levelTwoId
     * @return 1、什么都不传，表示查询当前店铺下所有产品
     * 2、可以根据产品类型productType 查询一级产品，二级产品（一级产品没有传的话默认取第一个一级产品的二级产品，一级产品传值查询选中的一级产品的二级产品），以及产品列表
     * 3、productType、levelOne、levelTwo 可以随机匹配查询
     */
    @RequestMapping(value = "/getShopProductLevelInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getShopProductLevelInfo(@RequestParam(required = false) String productType, @RequestParam(required = false) String levelOneId,
                                                @RequestParam(required = false) String levelTwoId, @RequestParam(required = false) String pageNo,
                                                @RequestParam(required = false) String pageSize, @RequestParam(required = false) String shopStoreId) {

        ResponseDTO responseDTO = new ResponseDTO();
        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        String sysShopId = redisUtils.getShopId();
        if (StringUtils.isNotBlank(productType)) {
            shopProductInfoDTO.setProductType(productType);
        }
        if (StringUtils.isNotBlank(levelOneId)) {
            shopProductInfoDTO.setProductTypeOneId(levelOneId);
        }
        if (StringUtils.isNotBlank(levelTwoId)) {
            shopProductInfoDTO.setProductTypeTwoId(levelTwoId);
        }

        shopProductInfoDTO.setSysShopId(sysShopId);
        List<ShopProductInfoDTO> detailProductList = shopProductInfoService.getShopProductInfo(shopProductInfoDTO);

        HashMap<Object, Object> responseMap = new HashMap<>(8);
        if (CommonUtils.objectIsNotEmpty(detailProductList)) {
            //缓存一级产品名牌
            HashMap<String, ShopProductInfoDTO> oneMap = new LinkedHashMap<>(16);
            logger.info("开始缓存一级产品品牌");
            for (ShopProductInfoDTO dto : detailProductList) {
                oneMap.put(dto.getProductTypeOneId(), dto);
            }

            List<Object> oneLevelList = new ArrayList<Object>();
            for (Map.Entry entry : oneMap.entrySet()) {
                oneLevelList.add(entry.getValue());
            }
            responseMap.put("oneLevelList", oneLevelList);


            //缓存选中的二级产品品牌，如果levelTwo，默认取oneMap中的第一条作为查询结果
            logger.info("开始缓存二级产品品牌,levelOneId={}", levelOneId);
            HashMap<Object, ShopProductInfoDTO> twoMap = new HashMap<>(16);
            HashMap<Object, HashMap<Object, Integer>> oneProductNumber = new HashMap<>(16);
            for (ShopProductInfoDTO dto : detailProductList) {
               twoMap.put(dto.getProductTypeTwoId(), dto);
               if(dto.getProductTypeOneId()!=null&&oneProductNumber.get(dto.getProductTypeOneId())==null){
                   HashMap<Object, Integer> twoProductNumber = new HashMap<>(16);
                   for (ShopProductInfoDTO dto2 : detailProductList){
                       if(dto2.getProductTypeOneId()!=null&&dto2.getProductTypeOneId().equals(dto.getProductTypeOneId())){
                           if(dto2.getProductTypeTwoId()!=null&&twoProductNumber.get(dto2.getProductTypeTwoId())!=null&&twoProductNumber.get(dto2.getProductTypeTwoId())!=0){

                               int sum = twoProductNumber.get(dto2.getProductTypeTwoId())+1;
                               twoProductNumber.put(dto2.getProductTypeTwoId(),sum);

                           }else if(dto2.getProductTypeTwoId()!=null&&twoProductNumber.get(dto2.getProductTypeTwoId())==null){
                               twoProductNumber.put(dto2.getProductTypeTwoId(),1);
                           }
                       }
                   }
                   oneProductNumber.put(dto.getProductTypeOneId(),twoProductNumber);
               }

            }
            List<Object> twoLevelList = new ArrayList<>();
            for (Map.Entry<Object,ShopProductInfoDTO> entry : twoMap.entrySet()) {
                ShopProductInfoDTO productInfoDTO = entry.getValue();
                ExtShopProductInfoDTO extShopProductInfoDTO = new ExtShopProductInfoDTO();
                BeanUtils.copyProperties(productInfoDTO,extShopProductInfoDTO);
                for(Map.Entry entrySum : oneProductNumber.entrySet()){
                    if(extShopProductInfoDTO.getProductTypeOneId()!=null){
                        if(entrySum.getKey().equals(extShopProductInfoDTO.getProductTypeOneId())){
                            HashMap<Object, Integer> sumMap = (HashMap<Object, Integer>)entrySum.getValue();
                            for(Map.Entry entrySumVal : sumMap.entrySet()){
                                if(entry.getKey()!=null){
                                    if(entry.getKey().equals(entrySumVal.getKey())){
                                        extShopProductInfoDTO.setNumber((Integer) entrySumVal.getValue());
                                    }
                                }
                            }

                        }
                    }
                    entry.setValue(extShopProductInfoDTO);
                }
                twoLevelList.add(entry.getValue());

            }
            responseMap.put("twoLevelList", twoLevelList);
            responseMap.put("oneProductNumber",oneProductNumber);
        }


        responseMap.put("detailProductList", detailProductList);
        //查询产品的库存信息
        if (StringUtils.isNotBlank(shopStoreId)) {
            ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
            shopStockNumberDTO.setShopStoreId(shopStoreId);
            shopStockNumberDTO.setProductTypeTwoId(levelTwoId);
            PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO = new PageParamVoDTO();
            pageParamVoDTO.setPaging(true);
            pageParamVoDTO.setPageNo(0);
            pageParamVoDTO.setRequestData(shopStockNumberDTO);
            //查询产品库存详情
            Map<String, Object> map = shopStockService.getStockDetailList(pageParamVoDTO, detailProductList);
            responseMap.put("allUseCost", map.get("allUseCost"));
            responseMap.put("useCost", map.get("useCost"));
            responseMap.put("detailProductList", map.get("extShopProductInfoDTOs"));
        }

        responseDTO.setResponseData(responseMap);
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
        if (null == bossInfo || com.wisdom.common.util.StringUtils.isBlank(bossInfo.getCurrentShopId())) {
            logger.error("获取老板信息异常，{}", "bossInfo = [" + bossInfo + "]");
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("获取老板信息异常");
            return true;
        }
        return false;
    }
}
