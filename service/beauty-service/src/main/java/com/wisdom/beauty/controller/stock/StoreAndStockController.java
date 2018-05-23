package com.wisdom.beauty.controller.stock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.requestDto.ShopClosePositionRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRecordRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopCheckRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoCheckResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 张超 Date: 2018/4/23 0003 15:06 Description: 仓库和库存相关
 */
@Controller
@RequestMapping(value = "stock")
public class StoreAndStockController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopStockService shopStockService;

    @Autowired
    private ShopCheckService shopCheckService;

    /**
     * 查询仓库列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "findStoreList", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseDTO<List<ShopStoreDTO>> findStoreList() {

        long startTime = System.currentTimeMillis();
        SysBossDTO sysBossDTO=UserUtils.getBossInfo();
        // 执行查询
        List<ShopStoreDTO>  list = shopStockService.findStoreList(sysBossDTO.getSysBossCode());

        ResponseDTO<List<ShopStoreDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);

        logger.info("根据条件查询仓库列表" + "耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 入库
     * @Date:2018/5/7 16:59
     */
    @RequestMapping(value = "/addStock", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> addStock(@RequestBody String shopStock) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        shopStockService.insertShopStockDTO(shopStock);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("addStock方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 更新库存实际量和价格
     * @Date:2018/5/9 21:21
     */
    @RequestMapping(value = "/updateStockNumber", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateStockNumber(@RequestBody ShopStockNumberDTO shopStockNumberDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();

        shopStockService.updateStockNumber(shopStockNumberDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("updateStockNumber方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: 产品id 和仓库id
     * @Return:
     * @Description: 获取库存数量和价格
     * @Date:2018/5/9 21:21
     */
    @RequestMapping(value = "/getStockNumber", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, Object>> getStockNumber(@RequestParam String shopStoreId, @RequestParam String shopProcId) {
        long currentTimeMillis = System.currentTimeMillis();

        ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
        shopStockNumberDTO.setShopStoreId(shopStoreId);
        shopStockNumberDTO.setShopProcId(shopProcId);
        ShopStockNumberDTO shopStockNumber = shopStockService.getStockNumber(shopStockNumberDTO);
        Map<String, Object> map = new HashedMap();
        Integer stockNumber = null;
        BigDecimal stockPrice = null;
        if (shopStockNumber != null) {
            stockNumber = shopStockNumber.getStockNumber();
            stockPrice = shopStockNumber.getStockPrice();
        }
        map.put("stockNumber", stockNumber);
        map.put("stockPrice", stockPrice);
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(map);
        logger.info("addStock方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 入库/出库记录
     * @Date:2018/5/7 16:59
     */
    @RequestMapping(value = "/shopStockRecordList", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<ShopStockRecordDTO>> getShopStockRecordList(
            @RequestBody ShopStockRecordRequestDTO shopStockRecordRequestDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        PageParamVoDTO<ShopStockRecordDTO> pageParamVoDTO = new PageParamVoDTO<>();
        SysBossDTO sysBossDTO = UserUtils.getBossInfo();
        ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
        shopStockRecordDTO.setSysBossCode(sysBossDTO.getId());
        shopStockRecordDTO.setShopStoreId(shopStockRecordRequestDTO.getShopStoreId());
        shopStockRecordDTO.setStockStyle(shopStockRecordRequestDTO.getStockStyle());

        pageParamVoDTO.setRequestData(shopStockRecordDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageSize(shopStockRecordRequestDTO.getPageSize());
        pageParamVoDTO.setStartTime(shopStockRecordRequestDTO.getStartTime());
        pageParamVoDTO.setStartTime(shopStockRecordRequestDTO.getEndTime());
        List<ShopStockRecordDTO> list = shopStockService.getShopStockRecordList(pageParamVoDTO);
        ResponseDTO<List<ShopStockRecordDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getShopStockRecordList方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 入库/出库详情
     * @Date:2018/5/7 16:59
     */
    @RequestMapping(value = "/shopStockRecordDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopStockResponseDTO> getShopStockRecordDetail(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
        shopStockRecordDTO.setId(id);
        ShopStockResponseDTO shopStockResponseDTO = shopStockService.getShopStock(shopStockRecordDTO);
        ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();

        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopStockResponseDTO);
        logger.info("getShopStockRecordDetail方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: 产品id 和仓库id
     * @Return:
     * @Description: 获取产品信息以及库存信息
     * @Date:2018/5/9 21:21
     */
    @RequestMapping(value = "/getProductInfoAndStock", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopStockResponseDTO> getProductInfoAndStock(@RequestParam String shopStoreId, @RequestParam String shopProcId) {
        long currentTimeMillis = System.currentTimeMillis();

        ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
        shopStockNumberDTO.setShopStoreId(shopStoreId);
        shopStockNumberDTO.setShopProcId(shopProcId);
        ShopStockResponseDTO shopStockResponseDTO = shopStockService.getProductInfoAndStock(shopStoreId, shopProcId);

        ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopStockResponseDTO);
        logger.info("getProductInfoAndStock方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: 产品id 和仓库id
     * @Return:
     * @Description: 获取库存详情
     * @Date:2018/5/9 21:21
     */
    @RequestMapping(value = "/getStockDetailList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopStockResponseDTO>> getStockDetailList(@RequestParam String shopStoreId, @RequestParam String productTypeTwoId, int pageSize) {
        long currentTimeMillis = System.currentTimeMillis();

        ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
        shopStockNumberDTO.setShopStoreId(shopStoreId);
        shopStockNumberDTO.setProductTypeTwoId(productTypeTwoId);
        PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO = new PageParamVoDTO();
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageSize(pageSize);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setRequestData(shopStockNumberDTO);
        List<ShopStockResponseDTO> list = shopStockService.getStockDetailList(pageParamVoDTO);

        ResponseDTO<List<ShopStockResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getStockDetailList方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取库存产品的详情
     * @Date:2018/5/19 10:45
     */
    @RequestMapping(value = "/getProductStockDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopStockResponseDTO> getProductStockDetail(@RequestParam String shopProcId) {
        long currentTimeMillis = System.currentTimeMillis();

        ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
        shopStockNumberDTO.setShopProcId(shopProcId);
        ShopStockResponseDTO shopStockResponseDTO = shopStockService.getProductStockDetail(shopStockNumberDTO);

        ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopStockResponseDTO);
        logger.info("getProductStockDetail方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: 产品id   仓库id    实际数量
     * @Return:
     * @Description: 产品盘点
     * @Date:2018/5/19 15:53
     */
    @RequestMapping(value = "/checkProduct", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Integer> checkProduct(@RequestParam String shopCheckRecordDTO) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopCheckRecordDTO[] shopCheckRecordDTOArry = (ShopCheckRecordDTO[]) JSONArray.toArray(JSONArray.fromObject(shopCheckRecordDTO), ShopCheckRecordDTO.class);
        List<ShopCheckRecordDTO> list = Arrays.asList(shopCheckRecordDTOArry);
        Integer result = shopStockService.checkProduct(list);
        ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(result);
        logger.info("checkProduct方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取盘点记录列表
     * @Date:2018/5/21 9:49
     */
    @RequestMapping(value = "/getProductCheckRecord", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopCheckRecordResponseDTO>> getProductCheckRecord(@RequestParam String shopStoreId) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopCheckRecordDTO shopCheckRecordDTO = new ShopCheckRecordDTO();
        shopCheckRecordDTO.setShopStoreId(shopStoreId);
        List<ShopCheckRecordResponseDTO> list = shopCheckService.getProductCheckRecordList(shopCheckRecordDTO);
        ResponseDTO<List<ShopCheckRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getProductCheckRecord方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取产品的盘点记录详情
     * @Date:2018/5/21 10:59
     */
    @RequestMapping(value = "/getProductCheckRecordDeatil", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopCheckRecordResponseDTO>> getProductCheckRecordDeatil(@RequestParam String flowNo) {
        long currentTimeMillis = System.currentTimeMillis();
        List<ShopCheckRecordResponseDTO> list = shopCheckService.getProductCheckRecordDeatil(flowNo);
        ResponseDTO<List<ShopCheckRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getProductCheckRecordDeatil方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 平仓
     * @Date:2018/5/21 15:24
     */
    @RequestMapping(value = "/doClosePosition", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Integer> doClosePosition(@RequestBody ShopClosePositionRequestDTO shopClosePositionRequestDTO) {
        long currentTimeMillis = System.currentTimeMillis();
        Integer result = shopCheckService.doClosePosition(shopClosePositionRequestDTO);
        ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(result);
        logger.info("doClosePositio方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 平仓详情
     * @Date:2018/5/21 16:32
     */
    @RequestMapping(value = "/getShopClosePositionDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopClosePositionRecordDTO> getShopClosePositionDetail(@RequestParam String shopClosePositionId,
                                                                       @RequestParam String productName,
                                                                       @RequestParam String productTypeName) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopClosePositionRecordDTO shopClosePositionRecordDTO = shopCheckService.getShopClosePositionDetail(shopClosePositionId, productName, productTypeName);
        ResponseDTO<ShopClosePositionRecordDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopClosePositionRecordDTO);
        logger.info("getShopClosePositionDetail方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 跳转到产品盘点页面
     * @Date:2018/5/22 14:39
     */
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> getProducts(@RequestBody String shopStoreId,@RequestBody List<String> products) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        List<ShopProductInfoCheckResponseDTO> list = shopCheckService.getProductsCheckLit(shopStoreId,products);
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("getProducts方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}