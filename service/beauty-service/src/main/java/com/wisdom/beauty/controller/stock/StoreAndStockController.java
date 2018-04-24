package com.wisdom.beauty.controller.stock;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 张超
 * Date:     2018/4/23 0003 15:06
 * Description: 仓库和库存相关
 */
@Controller
@RequestMapping(value = "StoreAndStockController")
public class StoreAndStockController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

 	@Resource
	private ShopStockService shopStockService;

	/**
	 * 根据条件查询仓库列表
	 * @param pageParamDTO 分页对象
	 * @return
	 */
	@RequestMapping(value = "findStoreList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<ExtShopStoreDTO>>> findStoreList(@RequestBody PageParamDTO<ExtShopStoreDTO> pageParamDTO) {

		long startTime = System.currentTimeMillis();
		logger.info("开始查询仓库列表..........查询条件为：{}", pageParamDTO.getRequestData());

		ResponseDTO<PageParamDTO<List<ExtShopStoreDTO>>> responseDTO = new ResponseDTO<>();

		//执行查询
		PageParamDTO<List<ExtShopStoreDTO>> page = shopStockService.findStoreListS(pageParamDTO);


		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);

		logger.info("根据条件查询仓库列表" + "耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}





}