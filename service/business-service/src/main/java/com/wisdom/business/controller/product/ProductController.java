package com.wisdom.business.controller.product;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.product.TrainingProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.ExportProductExcelDTO;
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
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<>();
		ProductDTO productDTO = productService.getBusinessProductInfo(productId);
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}
	
	/**
	 * 分页查询所有商品
	 * @param pageParamVoDTO
	 * @return
	 */
	@RequestMapping(value = "queryAllProducts", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamVoDTO<List<ProductDTO>>> queryAllProducts(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
		ResponseDTO<PageParamVoDTO<List<ProductDTO>>> responseDTO = new ResponseDTO<>();
		PageParamVoDTO<List<ProductDTO>> page = productService.queryAllProducts(pageParamVoDTO);
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 条件查询视频
	 * @return
	 */
	@RequestMapping(value = "queryTrainingProductsByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamVoDTO<List<ProductDTO>>> queryTrainingProductsByParameters(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
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
					exportProductExcelDTO.setSecondType(productDTO.getSecondType());
					exportProductExcelDTO.setStatus(productDTO.getStatus());
					exportProductExcelDTO.setSellNum(productDTO.getSellNum());
					excelList.add(exportProductExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("视频EXCEL文档",orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			}catch (Exception e){
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
			return responseDTO;
		}
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
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
		ResponseDTO<ProductDTO> responseDTO = new ResponseDTO<>();
		ProductDTO productDTO = productService.findProductById(productId);
		responseDTO.setResponseData(productDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
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

		Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
		OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
		offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
		ResponseDTO<OfflineProductDTO> responseDTO = new ResponseDTO();
		responseDTO.setResponseData(offlineProductDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
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
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
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
			return responseDTO;
		}
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
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
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.updateProductByParameters(productDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setErrorInfo("更新用户收货地址成功");
		}catch (Exception e){
			e.printStackTrace();
			responseDTO.setErrorInfo("更新用户收货地址失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
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
		ResponseDTO responseDTO = new ResponseDTO<>();
		String productId = CodeGenUtil.getProductCodeNumber();
		String uuid = UUIDUtil.getUUID();
		try {
			productDTO.setId(uuid);
			if(null == productDTO.getProductId()){
				productDTO.setProductId(productId);
				productDTO.getProductDetail().setProductId(productId);
			}
			productDTO.getProductDetail().setProductId(productDTO.getProductId());
			productService.addOfflineProduct(productDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}

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
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.putAwayProductById(id);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
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
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			productService.delProductById(id);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		return responseDTO;
	}

	@RequestMapping(value = "getSpecialShopInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO getSpecialShopInfo(@RequestParam String specialShopId) {
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			Query query = new Query(Criteria.where("shopId").is(specialShopId));
			SpecialShopInfoDTO specialShopInfoDTO = mongoTemplate.findOne(query,SpecialShopInfoDTO.class,"specialShopInfo");
			responseDTO.setResponseData(specialShopInfoDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		return responseDTO;
	}
}

