package com.wisdom.beauty.controller.stock;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.requestDto.SetStorekeeperRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopClosePositionRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRecordRequestDTO;
import com.wisdom.beauty.api.requestDto.StoreProductIdRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopCheckRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoCheckResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 张超 Date: 2018/4/23 0003 15:06 Description: 仓库和库存相关
 */
@Controller
@LoginAnnotations
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
	public @ResponseBody ResponseDTO<List<ShopStoreDTO>> findStoreList() {

		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		// 执行查询
		List<ShopStoreDTO> list = shopStockService.findStoreList(sysBossDTO.getSysBossCode());

		ResponseDTO<List<ShopStoreDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(list);
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
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		String id=shopStockService.insertShopStockDTO(shopStock);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(id);
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
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		shopStockService.updateStockNumber(shopStockNumberDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
		ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
		shopStockRecordDTO.setId(id);
		ShopStockResponseDTO shopStockResponseDTO = shopStockService.getShopStock(shopStockRecordDTO);
		ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();

		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopStockResponseDTO);
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
	ResponseDTO<ShopStockResponseDTO> getProductInfoAndStock(@RequestParam String shopStoreId,
			@RequestParam String shopProcId) {

		ShopStockResponseDTO shopStockResponseDTO = shopStockService.getProductInfoAndStock(shopStoreId, shopProcId);

		ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopStockResponseDTO);
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
	ResponseDTO<Map<String,Object>> getStockDetailList(@RequestParam String shopStoreId,
			                                                   @RequestParam(required = false) String productTypeTwoId,
                                                               int pageSize) {
		ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
		shopStockNumberDTO.setShopStoreId(shopStoreId);
		shopStockNumberDTO.setProductTypeTwoId(productTypeTwoId);
		PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO = new PageParamVoDTO();
		pageParamVoDTO.setPaging(true);
		pageParamVoDTO.setPageSize(pageSize);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setRequestData(shopStockNumberDTO);
        Map<String, Object> map = shopStockService.getStockDetailList(pageParamVoDTO, new ArrayList<>());

		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
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
		ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
		shopStockNumberDTO.setShopProcId(shopProcId);
		ShopStockResponseDTO shopStockResponseDTO = shopStockService.getProductStockDetail(shopStockNumberDTO);

		ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopStockResponseDTO);
		return responseDTO;
	}

	/**
	 * @Author:zhanghuan
	 * @Param: 产品id 仓库id 实际数量
	 * @Return:  盘点记录流水号
	 * @Description: 产品盘点
	 * @Date:2018/5/19 15:53
	 */
	@RequestMapping(value = "/checkProduct", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<String> checkProduct(@RequestBody String shopCheckRecordDTO) {
		ShopCheckRecordDTO[] shopCheckRecordDTOArry = (ShopCheckRecordDTO[]) JSONArray
				.toArray(JSONArray.fromObject(shopCheckRecordDTO), ShopCheckRecordDTO.class);
		List<ShopCheckRecordDTO> list = Arrays.asList(shopCheckRecordDTOArry);
		String result = shopStockService.checkProduct(list);
		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(result);
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
	ResponseDTO<List<ShopCheckRecordResponseDTO>> getProductCheckRecord(@RequestParam String shopStoreId,
																		int pageSize) {
		ShopCheckRecordDTO shopCheckRecordDTO = new ShopCheckRecordDTO();
		shopCheckRecordDTO.setShopStoreId(shopStoreId);
		PageParamVoDTO<ShopCheckRecordDTO> pageParamVoDTO=new PageParamVoDTO();
		pageParamVoDTO.setPaging(true);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setPageSize(pageSize);
		pageParamVoDTO.setRequestData(shopCheckRecordDTO);
		List<ShopCheckRecordResponseDTO> list = shopCheckService.getProductCheckRecordList(pageParamVoDTO);
		ResponseDTO<List<ShopCheckRecordResponseDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(list);
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
		List<ShopCheckRecordResponseDTO> list = shopCheckService.getProductCheckRecordDeatil(flowNo);
		ResponseDTO<List<ShopCheckRecordResponseDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(list);
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
		Integer result = shopCheckService.doClosePosition(shopClosePositionRequestDTO);
		ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(result);
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
			@RequestParam String productName, @RequestParam String productTypeName) {
		ShopClosePositionRecordDTO shopClosePositionRecordDTO = shopCheckService
				.getShopClosePositionDetail(shopClosePositionId, productName, productTypeName);
		ResponseDTO<ShopClosePositionRecordDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopClosePositionRecordDTO);
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
	ResponseDTO<Object> getProducts(@RequestBody StoreProductIdRequestDTO storeProductIdRequestDTO) {
		String shopStoreId = storeProductIdRequestDTO.getShopStoreId();
		List<String> productIds = storeProductIdRequestDTO.getProductIds();

		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		List<ShopProductInfoCheckResponseDTO> list = shopCheckService.getProductsCheckLit(shopStoreId, productIds);
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:zhanghuan
	 * @Param: 仓库id 仓库管理员Id
	 * @Return:
	 * @Description: 仓库管理员设置
	 * @Date:2018/5/23 14:28
	 */
	@RequestMapping(value = "/setStorekeeper", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<Object> setStorekeeper(@RequestBody SetStorekeeperRequestDTO setStorekeeperRequestDTO) {

		int result = shopStockService.setStorekeeper(setStorekeeperRequestDTO);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(result);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	@RequestMapping(value = "/getStoreManager", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> getStoreManager(@RequestParam String id) {
		Map<String,String> result = shopStockService.getStoreManager(id);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(result);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}
}