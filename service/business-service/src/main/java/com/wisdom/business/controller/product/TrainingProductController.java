package com.wisdom.business.controller.product;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.TrainingProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.product.TrainingProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class TrainingProductController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TrainingProductService trainingProductService;


	/**
	 * 获取培训商品的详细信息
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<ProductDTO>>
	 *
	 */
	@RequestMapping(value = "getTrainingProductListNeedPay", method = {RequestMethod.POST, RequestMethod.GET})
/*	@LoginRequired*/
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> getTrainingProductListNeedPay(@RequestBody PageParamDTO<ProductDTO> pageParamDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("获取培训商品的详细信息==={}开始" , startTime);
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();

		//获取免费产品列表
		List<ProductDTO> freeProductDTOList = trainingProductService.findTrainingProductList(pageParamDTO,"0");

		//获取会员产品列表
		List<ProductDTO> memberProductDTOList = trainingProductService.findTrainingProductList(pageParamDTO,"1");

		//获取收费产品列表
		List<ProductDTO> chargeProductDTOList = trainingProductService.findTrainingProductList(pageParamDTO,"2");


		Map<String,Object> productList = new HashMap<>();
		productList.put("freeProductDTOList",freeProductDTOList);
		productList.put("memberProductDTOList",memberProductDTOList);
		productList.put("chargeProductDTOList",chargeProductDTOList);

		responseDTO.setResponseData(productList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取培训商品的详细信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	@RequestMapping(value = "getTrainingProductListNoNeedPay", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> getTrainingProductListNoNeedPay(@RequestBody PageParamDTO<ProductDTO> pageParamDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("获取培训商品getTrainingProductListNoNeedPay==={}开始" , startTime);
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		List<ProductDTO> productDTOList = trainingProductService.getTrainingProductList(pageParamDTO,0.00f);
		logger.info("获取培训商品getTrainingProductListNoNeedPay结果Size==={}" , productDTOList.size());
		responseDTO.setResponseData(productDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取培训商品的getTrainingProductListNoNeedPay,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}


	/**
	 * 获取培训商品的详细信息
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<ProductDTO>>
	 *
	 */
	@RequestMapping(value = "getTrainingProductDetail", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ProductDTO<TrainingProductDTO>> getTrainingProductDetail(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id获取培训商品的详细信息==={}开始" , startTime);
		logger.info("根据id{}获取培训商品的详细信息" , productId);
		ResponseDTO<ProductDTO<TrainingProductDTO>> responseDTO = new ResponseDTO<>();
		ProductDTO<TrainingProductDTO> productDTO = trainingProductService.getTrainingProductDetailById(productId);
		productDTO.setPrice(productDTO.getPrice());
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("根据id获取培训商品的详细信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 获取某个培训商品的播放次数
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<ProductDTO>>
	 *
	 */
	@RequestMapping(value = "getTrainingProductPlayNum", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<Integer> getTrainingProductPlayNum(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("获取某个培训商品的播放次数==={}开始" , startTime);
		ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
		int playNum = trainingProductService.getTrainingProductPlayNum(productId);
		logger.info("获取某个培训商品{}的播放次数{}" , productId,playNum);
		responseDTO.setResponseData(playNum);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取某个培训商品的播放次数,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	@RequestMapping(value = "AddTrainingProductPlayNum", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO AddTrainingProductPlayNum(@RequestParam String productId, @RequestParam String playURL) {
		long startTime = System.currentTimeMillis();
		logger.info("添加商品点击次数==={}开始" , startTime);
		logger.info("添加商品{}点击次数playURL{}" , productId,playURL);
		ResponseDTO responseDTO = new ResponseDTO<>();
		trainingProductService.AddTrainingProductPlayNum(productId,playURL);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("添加商品点击次数,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 新增视频
	 * @param productDTO
	 * @return
	 */
	@RequestMapping(value = "addTrainingProduct", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO addTrainingProduct(@RequestBody ProductDTO<TrainingProductDTO> productDTO) {
		if(productDTO.getPrice()==""||productDTO.getPrice()==null){
			productDTO.setPrice("0");
		}
		long startTime = System.currentTimeMillis();
		logger.info("新增视频==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		String productId = CodeGenUtil.getProductCodeNumber();
		logger.info("新增视频{}" , productId);
		String uuid = UUIDUtil.getUUID();
		try {
			productDTO.setId(uuid);
			productDTO.setProductId(productId);
			productDTO.setCreateDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			productDTO.getProductDetail().setProductId(productId);
			productDTO.getProductDetail().setBrand(productDTO.getBrand());
			productDTO.getProductDetail().setTrainingProductName(productDTO.getProductName());
			productDTO.getProductDetail().setDescription(productDTO.getDescription());
			productDTO.getProductDetail().setTrainingProductType(productDTO.getSecondType());
			trainingProductService.addTrainingProduct(productDTO);
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("新增视频异常,异常信息为,{}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
		}
		logger.info("新增视频,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑视频
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "updateTrainingProduct", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO updateTrainingProduct(@RequestBody ProductDTO<TrainingProductDTO> product) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑视频==={}开始" , startTime);
		logger.info("编辑视频==={}" , product.getProductId());
		ResponseDTO responseDTO = new ResponseDTO<>();

		try {
			trainingProductService.updateTrainingProduct(product);
			responseDTO.setResult(StatusConstant.SUCCESS);
		}catch (Exception e){
			logger.info("编辑视频异常,异常信息为==={}" +e.getMessage(), e);
			responseDTO.setResult(StatusConstant.FAILURE);
			e.printStackTrace();
		}
		logger.info("编辑视频,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

}

