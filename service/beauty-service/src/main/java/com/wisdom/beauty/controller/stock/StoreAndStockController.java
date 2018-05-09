package com.wisdom.beauty.controller.stock;

import java.util.List;

import javax.annotation.Resource;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.dto.ShopStockRecordDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	/**
	 * 根据条件查询仓库列表
	 * 
	 * @param pageParamDTO
	 *            分页对象
	 * @return
	 */
	@RequestMapping(value = "findStoreList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseDTO<PageParamDTO<List<ExtShopStoreDTO>>> findStoreList(
			@RequestBody PageParamDTO<ExtShopStoreDTO> pageParamDTO) {

		long startTime = System.currentTimeMillis();
		logger.info("开始查询仓库列表..........查询条件为：{}", pageParamDTO.getRequestData());

		ResponseDTO<PageParamDTO<List<ExtShopStoreDTO>>> responseDTO = new ResponseDTO<>();

		// 执行查询
		PageParamDTO<List<ExtShopStoreDTO>> page = shopStockService.findStoreListS(pageParamDTO);

		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);

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
	ResponseDTO<Object> addStock(String shopStock) {
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
		shopStockRecordDTO.setShopBossId(sysBossDTO.getId());
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
		ShopStockRecordDTO shopStockRecordDTO=new ShopStockRecordDTO();
		shopStockRecordDTO.setId(id);
		ShopStockResponseDTO shopStockResponseDTO = shopStockService.getShopStock(shopStockRecordDTO);
		ResponseDTO<ShopStockResponseDTO> responseDTO = new ResponseDTO<>();


		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopStockResponseDTO);
		logger.info("getShopStockRecordList方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
		return responseDTO;
	}

}