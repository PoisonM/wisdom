package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
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
import java.util.*;

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
                                                @RequestParam(required = false) String pageSize) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("根据产品类型查询一级二级产品传入参数={}", "productType = [" + productType + "], levelOneId = [" + levelOneId + "], levelTwoId = [" + levelTwoId + "]");

        ResponseDTO responseDTO = new ResponseDTO();
        ShopProductInfoDTO shopProductInfoDTO = new ShopProductInfoDTO();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (judgeBossCurrentShop(responseDTO, bossInfo)) {
            return responseDTO;
        }
        String currentShopId = bossInfo.getCurrentShopId();

        if (StringUtils.isNotBlank(productType)) {
            shopProductInfoDTO.setProductType(productType);
        }
        if (StringUtils.isNotBlank(levelOneId)) {
            shopProductInfoDTO.setProductTypeOneId(levelOneId);
        }
        if (StringUtils.isNotBlank(levelTwoId)) {
            shopProductInfoDTO.setProductTypeTwoId(levelTwoId);
        }

        shopProductInfoDTO.setSysShopId(currentShopId);
        List<ShopProductInfoDTO> detailProductList = shopProductInfoService.getShopProductInfo(shopProductInfoDTO);

        HashMap<Object, Object> responseMap = new HashMap<>(8);
        if (CommonUtils.objectIsNotEmpty(detailProductList)) {
            //缓存一级产品名牌
            LinkedHashMap<String, ShopProductInfoDTO> oneMap = new LinkedHashMap<>(16);
            logger.info("开始缓存一级产品品牌");
            for (ShopProductInfoDTO dto : detailProductList) {
                oneMap.put(dto.getProductTypeOneName(), dto);
            }

            List<Object> oneLevelList = new ArrayList<>();
            for (Map.Entry entry : oneMap.entrySet()) {
                oneLevelList.add(entry.getValue());
            }
            responseMap.put("oneLevelList", oneLevelList);
            //缓存选中的二级产品品牌，如果levelTwo，默认取oneMap中的第一条作为查询结果
            logger.info("开始缓存二级产品品牌,levelOneId={}", levelOneId);
            HashMap<Object, Object> twoMap = new HashMap<>(16);
            if (StringUtils.isBlank(levelOneId)) {
                levelOneId = ((ShopProductInfoDTO) oneLevelList.get(0)).getProductTypeOneId();
            }
            List<Object> twoLevelList = new ArrayList<>();
            for (ShopProductInfoDTO dto : detailProductList) {
                if (dto.getProductTypeOneId().equals(levelOneId)) {
                    twoLevelList.add(dto);
                }
            }
            responseMap.put("twoLevelList", twoLevelList);
        }

        responseMap.put("detailProductList", detailProductList);
        responseDTO.setResponseData(responseMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据产品类型查询一级二级产品耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
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
