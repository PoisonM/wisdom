package com.wisdom.business.controller.product;

import com.wisdom.business.service.product.OfflineProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class OfflineProductController {

	Logger logger = LoggerFactory.getLogger(OfflineProductController.class);

	@Autowired
	private OfflineProductService offlineProductService;

	/**
	 * 获取商城首页的线下产品的列表，每次获取6条产品记录，前端下拉时记载更多的6条，此处不包括产品详情
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<ProductDTO<OfflineProductDTO>>>
	 *
	 */
	@RequestMapping(value = "getOfflineProductList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> getOfflineProductList(@RequestBody PageParamDTO pageParamDTO,
														HttpSession session,
														HttpServletRequest request) {
		long startTime = System.currentTimeMillis();
		logger.info("获取微商城的产品列表==={}开始" , startTime);
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		logger.info("获取微商城的产品列表===" + pageParamDTO);
		List<ProductDTO> productDTOList = offlineProductService.findOfflineProductList(pageParamDTO);
		logger.info("查询到的微商城商品列表===" + productDTOList);
		responseDTO.setResponseData(productDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取微商城的产品列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	@RequestMapping(value = "getSpecialProductList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> GetSpecialProductList(@RequestBody PageParamDTO pageParamDTO,
														HttpSession session,
														HttpServletRequest request) {
		long startTime = System.currentTimeMillis();
		logger.info("获取微商城的Special产品列表==={}开始" , startTime);
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		List<ProductDTO> productDTOList = offlineProductService.findSpecialOfflineProductList(pageParamDTO);
		logger.info("获取微商城的Special产品列表Size==={}" , productDTOList.size());
		responseDTO.setResponseData(productDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取微商城的Special产品列表,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}


	/**
	 * 获取线下商品的详细信息
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<OfflineProductDTO>>
	 *
	 */
	@RequestMapping(value = "getOfflineProductDetail", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ProductDTO<OfflineProductDTO>> getOfflineProductDetail(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("获取微商城的某个商品=={}详情==={}开始" ,productId,startTime);
		ResponseDTO<ProductDTO<OfflineProductDTO>> responseDTO = new ResponseDTO<>();
		ProductDTO<OfflineProductDTO> offlineProductDTO = offlineProductService.getOfflineProductDetailById(productId);
		logger.info("得到微商城的某个商品详情===" + offlineProductDTO);
		responseDTO.setResponseData(offlineProductDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取微商城的某个商品详情,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
}

