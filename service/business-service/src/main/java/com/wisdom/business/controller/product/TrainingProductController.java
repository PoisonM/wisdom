package com.wisdom.business.controller.product;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class TrainingProductController {

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
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> getTrainingProductListNeedPay(@RequestBody PageParamDTO<ProductDTO> pageParamDTO) {
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		List<ProductDTO> productDTOList = trainingProductService.getTrainingProductList(pageParamDTO,1.00f);
		responseDTO.setResponseData(productDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	@RequestMapping(value = "getTrainingProductListNoNeedPay", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> getTrainingProductListNoNeedPay(@RequestBody PageParamDTO<ProductDTO> pageParamDTO) {
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		List<ProductDTO> productDTOList = trainingProductService.getTrainingProductList(pageParamDTO,0.00f);
		responseDTO.setResponseData(productDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
		ResponseDTO<ProductDTO<TrainingProductDTO>> responseDTO = new ResponseDTO<>();
		ProductDTO<TrainingProductDTO> productDTO = trainingProductService.getTrainingProductDetailById(productId);
		productDTO.setPrice(productDTO.getPrice());
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
		ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
		int playNum = trainingProductService.getTrainingProductPlayNum(productId);
		responseDTO.setResponseData(playNum);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	@RequestMapping(value = "AddTrainingProductPlayNum", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO AddTrainingProductPlayNum(@RequestParam String productId, @RequestParam String playURL) {
		ResponseDTO responseDTO = new ResponseDTO<>();
		trainingProductService.AddTrainingProductPlayNum(productId,playURL);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO<>();
		String productId = CodeGenUtil.getProductCodeNumber();
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
			e.printStackTrace();
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
		}
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
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			trainingProductService.updateTrainingProduct(product);
			responseDTO.setResult(StatusConstant.SUCCESS);
		}catch (Exception e){
			responseDTO.setResult(StatusConstant.FAILURE);
			e.printStackTrace();
		}
		return responseDTO;
	}

}

