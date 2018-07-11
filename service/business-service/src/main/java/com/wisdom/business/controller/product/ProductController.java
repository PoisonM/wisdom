package com.wisdom.business.controller.product;

import com.mongodb.Mongo;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.product.TrainingProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.product.TrainingProductDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.ExportProductExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.UUIDUtil;
import com.wisdom.common.util.excel.ExportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 关于账户管理
 * @author frank
 * @date 2015-10-14
 */
@Controller
@RequestMapping(value = "product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private TrainingProductService trainingProductService;

	@Autowired
	private MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取某个商品的基本信息
	 *
	 * input PageParamDto
	 *
	 * output ResponseDTO<List<ProductDTO>>
	 *
	 */
	@RequestMapping(value = "getBusinessProductInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ProductDTO> getBusinessProductInfo(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("获取某个商品的基本信息==={}开始" , startTime);
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<>();
		logger.info("获取某个商品的基本信息==={}" , productId);
		ProductDTO productDTO = productService.getBusinessProductInfo(productId);
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("获取某个商品的基本信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	
	/**
	 * 分页查询所有商品
	 * @param pageParamVoDTO
	 * @return
	 */
	/*@RequestMapping(value = "queryAllProducts", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamVoDTO<List<ProductDTO>>> queryAllProducts(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("分页查询所有商品==={}开始" , startTime);
		ResponseDTO<PageParamVoDTO<List<ProductDTO>>> responseDTO = new ResponseDTO<>();
		PageParamVoDTO<List<ProductDTO>> page = productService.queryAllProducts(pageParamVoDTO);
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("分页查询所有商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}*/

	/**
	 * 条件查询视频
	 * @return
	 */
	@RequestMapping(value = "queryTrainingProductsByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamVoDTO<List<ProductDTO>>> queryTrainingProductsByParameters(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("条件查询视频==={}开始" , startTime);
		logger.info("条件查询视频是否导出==={}" , pageParamVoDTO.getIsExportExcel());
		ResponseDTO<PageParamVoDTO<List<ProductDTO>>> responseDTO = new ResponseDTO<>();
		PageParamVoDTO<List<ProductDTO>> page = trainingProductService.queryTrainingProductsByParameters(pageParamVoDTO);
		if("Y".equals(pageParamVoDTO.getIsExportExcel())){
			try{
				String[] orderHeaders = {"产品编号","产品名称","产品品牌","产品分类", "产品价格", "产品状态","产品库存", "产品销量"};
				ExportExcel<ExportProductExcelDTO> ex =new ExportExcel<>();
				List<ExportProductExcelDTO> excelList= new ArrayList<>();
				for (ProductDTO productDTO : page.getResponseData()) {
					ExportProductExcelDTO exportProductExcelDTO = new ExportProductExcelDTO();
					exportProductExcelDTO.setBrand(productDTO.getBrand());
					exportProductExcelDTO.setPrice(productDTO.getPrice());
					exportProductExcelDTO.setProductId(productDTO.getProductId());
					exportProductExcelDTO.setProductName(productDTO.getProductName());
					if(("training").equals(productDTO.getType())){
						exportProductExcelDTO.setSecondType(productDTO.getSecondTypeName());
					}else{
						exportProductExcelDTO.setSecondType(productDTO.getSecondType());
					}
					exportProductExcelDTO.setStatus(productDTO.getStatus());
					exportProductExcelDTO.setSellNum(productDTO.getSellNum());
					excelList.add(exportProductExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("视频EXCEL文档",orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				logger.info("条件查询视频导出Url==={}" , url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			}catch (Exception e){
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
			return responseDTO;
		}
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("条件查询视频,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 根据id查询商品
	 * @param productId  商品id
	 * @return
	 */
	@RequestMapping(value = "findProductById", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ProductDTO> findProductById(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id查询商品==={}开始" , startTime);
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<>();
		logger.info("根据id查询商品==={}" , productId);
		ProductDTO productDTO = productService.findProductById(productId);
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("根据id查询商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 根据id查询商品剩余特价时间
	 * @param productId  商品id
	 * @return
	 */
	@RequestMapping(value = "findProductBargainPriceTimeById", method = {RequestMethod.POST, RequestMethod.GET})
	//@LoginRequired
	public
	@ResponseBody
	ResponseDTO<OfflineProductDTO> findProductBargainPriceTimeById(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id查询商品剩余特价时间==={}开始" , startTime);
		logger.info("根据id查询商品剩余特价时间==={}" , productId);
		Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
		OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
		offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
		ResponseDTO<OfflineProductDTO> responseDTO = new ResponseDTO();
		responseDTO.setResponseData(offlineProductDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("根据id查询商品剩余特价时间,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 条件查询商品
	 * @param pageParamVoDTO
	 * @return
	 */
	@RequestMapping(value = "queryProductsByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamVoDTO<List<ProductDTO>>> queryProductsByParameters(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		ResponseDTO<PageParamVoDTO<List<ProductDTO>>> responseDTO = new ResponseDTO<>();
		PageParamVoDTO<List<ProductDTO>> page = productService.queryProductsByParameters(pageParamVoDTO);
		if("Y".equals(pageParamVoDTO.getIsExportExcel())){
			try{
				String[] orderHeaders =
                        /*{"id","用户id","订单编号ID","订单地址id","订单商品id","订单类型","订单状态","创建时间","变更时间",
                                "商品数量", "商品名字","商品图片url", "商品价格","此订单价格","收货人姓名",  "收货人联系方式", "收货地址",
                                "商品型号","用户名","用户手机号","身份证号","付款时间", "senderAddress"};*/
						{"产品编号","产品名称","产品品牌","产品分类", "产品价格", "产品状态","产品库存", "产品销量"};
				ExportExcel<ExportProductExcelDTO> ex =new ExportExcel<>();
				List<ExportProductExcelDTO> excelList= new ArrayList<>();
				for (ProductDTO productDTO : page.getResponseData()) {
					ExportProductExcelDTO exportProductExcelDTO = new ExportProductExcelDTO();
					exportProductExcelDTO.setBrand(productDTO.getBrand());
					exportProductExcelDTO.setPrice(productDTO.getPrice());
					exportProductExcelDTO.setProductId(productDTO.getProductId());
					exportProductExcelDTO.setProductName(productDTO.getProductName());
					exportProductExcelDTO.setSecondType(productDTO.getSecondType());
					exportProductExcelDTO.setStatus(productDTO.getStatus());
					exportProductExcelDTO.setSellNum(productDTO.getSellNum());
					exportProductExcelDTO.setProductAmount(Integer.parseInt(productDTO.getProductAmount()));
					excelList.add(exportProductExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("产品EXCEL文档",orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				logger.info("条件查询商品数据Url==={}",url);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				logger.info("条件导出商品Excel耗时{}毫秒", (System.currentTimeMillis() - startTime));
				return responseDTO;
			}catch (Exception e){
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
			return responseDTO;
		}
		if( 0 == page.getResponseData().size()){
			responseDTO.setResponseData(page);
			responseDTO.setResult("未查出结果");
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			logger.info("条件查询商品未查出数据");
			logger.info("条件查询商品耗时{}毫秒", (System.currentTimeMillis() - startTime));
			return responseDTO;
		}
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("条件查询商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑商品
	 * @param productDTO
	 * @return
	 */
	@RequestMapping(value = "updateProductByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ProductDTO> updateProductByParameters(@RequestBody ProductDTO<OfflineProductDTO> productDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑商品==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.updateProductByParameters(productDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setErrorInfo("更新用户收货地址成功");
		}catch (Exception e){
			logger.error("编辑商品异常，异常信息为，{}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setErrorInfo("更新用户收货地址失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 添加商品
	 * @param productDTO
	 * @return
	 */
	@RequestMapping(value = "addOfflineProduct", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<ProductDTO> addOfflineProduct(@RequestBody ProductDTO<OfflineProductDTO> productDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("添加商品==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		String productId = CodeGenUtil.getProductCodeNumber();
		String uuid = UUIDUtil.getUUID();
		try {
			productDTO.setId(uuid);
			if("".equals(productDTO.getProductId()) || null == productDTO.getProductId()){
				productDTO.setProductId(productId);
				productDTO.getProductDetail().setProductId(productId);
			}
			productDTO.getProductDetail().setProductId(productDTO.getProductId());
			logger.info("添加商品==={}开始" , productDTO.getProductId());
			productService.addOfflineProduct(productDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("添加商品异常,异常信息为,{}" +e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("添加商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑商品-上架
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "putAwayProductById", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO putAwayProductById(@RequestParam String id) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑商品-上架==={}开始" , startTime);
		logger.info("上架商品id==={}" , id);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.putAwayProductById(id);

			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("上架商品异常==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑商品-上架,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑商品-下架
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delProductById", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO delProductById(@RequestParam String id) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑商品-下架==={}开始" , startTime);
		logger.info("下架商品id==={}" , id);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.delProductById(id);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("下架商品异常,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑商品-下架,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	@RequestMapping(value = "getSpecialShopInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO getSpecialShopInfo(@RequestParam String specialShopId) {
		long startTime = System.currentTimeMillis();
		logger.info("getSpecialShopInfo==={}开始" , startTime);
		logger.info("getSpecialShopInfo 参数specialShopId==={}" , specialShopId);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			Query query = new Query(Criteria.where("shopId").is(specialShopId));
			SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");
			responseDTO.setResponseData(specialShopInfoDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("getSpecialShopInfo异常,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("getSpecialShopInfo,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
}

