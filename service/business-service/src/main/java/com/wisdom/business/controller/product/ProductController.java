package com.wisdom.business.controller.product;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.product.ProductService;
import com.wisdom.business.service.product.TrainingProductService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductClassDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.system.BannerDTO;
import com.wisdom.common.dto.system.ExportProductExcelDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.UUIDUtil;
import com.wisdom.common.util.excel.ExportExcel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.*;

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
	 * 根据id查询商品
	 * @param productId  商品id
	 * @return
	 */
	@RequestMapping(value = "findProductDetail", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ProductDTO>> findProductDetail(@RequestParam String productId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id查询商品==={}开始" , startTime);
		ResponseDTO<List<ProductDTO>> responseDTO = new ResponseDTO<>();
		logger.info("根据id查询商品==={}" , productId);
		ProductDTO productDTO = productService.findProductDetail(productId);
		if(productDTO !=null){
			List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
			productDTOList.add(productDTO);
			responseDTO.setResponseData(productDTOList);
			responseDTO.setResult(StatusConstant.SUCCESS);
			logger.info("根据id查询商品,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		}else{
			responseDTO.setResult(StatusConstant.FAILURE);
		}

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
			if("4".equals(productDTO.getProductPrefecture())){
				productDTO.setType("seckill");
			}else if("seckill".equals(productDTO.getType())){
				if(!"4".equals(productDTO.getProductPrefecture())){
					productDTO.setType("offline");
				}
			}
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
			if("4".equals(productDTO.getProductPrefecture())){
				productDTO.setType("seckill");
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

	/**
	 * 根据id类目信息
	 * @return
	 */
	@RequestMapping(value = "getProductClassListById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ProductClassDTO> getProductClassListById(@RequestParam String id) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id类目信息==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			ProductClassDTO productClassDTO = new ProductClassDTO();
			productClassDTO.setId(id);
			List<ProductClassDTO> productClassDTOS = productService.getProductClassList(productClassDTO);
			if(productClassDTOS.size()>0){
				productClassDTO = productClassDTOS.get(0);
				responseDTO.setResponseData(productClassDTO);
				responseDTO.setResult(StatusConstant.SUCCESS);
			}else{
				responseDTO.setErrorInfo("未查出结果");
				responseDTO.setResult(StatusConstant.FAILURE);
			}
		} catch (Exception e) {
			logger.info("根据id类目信息,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setErrorInfo("查询类目信息失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("根据id类目信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 获取一级类目
	 * @return
	 */
	@RequestMapping(value = "getOneProductClassList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO getOneProductClassList() {
		long startTime = System.currentTimeMillis();
		logger.info("获取商品分类==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {

			List<ProductClassDTO> productClassDTOS = productService.getOneProductClassList();
			if(productClassDTOS.size()>0){
				Collections.sort(productClassDTOS, new Comparator<ProductClassDTO>() {
					@Override
					public int compare(ProductClassDTO o1, ProductClassDTO o2) {
						int i = o1.getSort() - o2.getSort();
						return i;
					}
				});
				responseDTO.setResponseData(productClassDTOS);
				responseDTO.setResult(StatusConstant.SUCCESS);
			}else{
				responseDTO.setErrorInfo("未查出结果");
				responseDTO.setResult(StatusConstant.FAILURE);
			}

		} catch (Exception e) {
			logger.info("获取商品分类,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("获取商品分类,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 获取二级类目
	 * @param productClassId
	 * @return
	 */
	@RequestMapping(value = "getTwoProductClassList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO getTwoProductClassList(@RequestParam String productClassId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id={}获取下级类目开始=={}",productClassId ,startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			List<ProductClassDTO> productClassDTOS = productService.getTwoProductClassList(productClassId);
			if(productClassDTOS.size()>0){
				Collections.sort(productClassDTOS, new Comparator<ProductClassDTO>() {
					@Override
					public int compare(ProductClassDTO o1, ProductClassDTO o2) {
						int i = o1.getSort() - o2.getSort();
						return i;
					}
				});
				responseDTO.setResponseData(productClassDTOS);
				responseDTO.setResult(StatusConstant.SUCCESS);
			}else{
				responseDTO.setErrorInfo("未查出结果");
				responseDTO.setResult(StatusConstant.FAILURE);
			}
		} catch (Exception e) {
			logger.info("根据id获取下级类目,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("根据id获取下级类目,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 添加一级分类
	 * @param className
	 * @return
	 */
	@RequestMapping(value = "addOneProductClass", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO addProductClass(@RequestParam String className) {
		long startTime = System.currentTimeMillis();
		logger.info("添加商品分类==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		List<ProductClassDTO> productClassDTOS = productService.getOneProductClassList();
		try {
			String classId = CodeGenUtil.getProductCodeNumber();
			String uuid = UUIDUtil.getUUID();
			ProductClassDTO productClassDTO = new ProductClassDTO();
			productClassDTO.setId(uuid);
			productClassDTO.setProductClassId(classId);
			productClassDTO.setProductClassName(className);
			productClassDTO.setRank("1");
			productClassDTO.setStatus("1");
			productClassDTO.setSort(productClassDTOS.size()+1);
			productClassDTO.setCreateDate(DateUtils.formatDateTime(new Date()));

			productService.addProductClass(productClassDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("添加商品分类,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setErrorInfo("一级类目添加失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("添加商品分类,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 添加二级分类
	 * @param className
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "addTwoProductClass", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO addTwoProductClass(@RequestParam String className,@RequestParam String parentId,@RequestParam String url) {
		long startTime = System.currentTimeMillis();
		logger.info("添加二级分类==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			if(StringUtils.isBlank(className) || StringUtils.isBlank(parentId) || StringUtils.isBlank(url)){
				responseDTO.setErrorInfo("类目名或者图片为空");
				responseDTO.setResult(StatusConstant.FAILURE);
				return responseDTO;
			}
			List<ProductClassDTO> productClassDTOS = productService.getTwoProductClassList(parentId);
			String classId = CodeGenUtil.getProductCodeNumber();
			String uuid = UUIDUtil.getUUID();
			ProductClassDTO productClassDTO = new ProductClassDTO();
			productClassDTO.setId(uuid);
			productClassDTO.setProductClassId(classId);
			productClassDTO.setProductClassName(className);
			productClassDTO.setUrl(url);
			productClassDTO.setRank("2");
			productClassDTO.setStatus("1");
			productClassDTO.setSort(productClassDTOS.size()+1);
			productClassDTO.setCreateDate(DateUtils.formatDateTime(new Date()));
			productClassDTO.setParentId(parentId);

			productService.addProductClass(productClassDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("添加二级分类,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setErrorInfo("添加二级分类失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("添加二级分类,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 修改分类
	 * @param className 分类名
	 * @param id 分类id
	 * @return
	 */
	@RequestMapping(value = "updateProductClass", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO updateProductClass(@RequestParam String className,@RequestParam String id) {
		long startTime = System.currentTimeMillis();
		logger.info("修改分类==={}开始" , startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			if(StringUtils.isBlank(className) || StringUtils.isBlank(id)){
				responseDTO.setErrorInfo("类目名或者图片为空");
				responseDTO.setResult(StatusConstant.FAILURE);
				return responseDTO;
			}
			ProductClassDTO productClassDTO = new ProductClassDTO();
			productClassDTO.setId(id);
			productClassDTO.setProductClassName(className);

			productService.updateProductClass(productClassDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("修改分类,异常信息为==={}" +e.getMessage(), e);
			e.printStackTrace();
			responseDTO.setErrorInfo("修改分类失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("修改分类,耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑商品类目-上移下移
	 * @param upAndDown 上移up,down下移
	 * @param id id
	 * @return
	 */
	@RequestMapping(value = "upOrDownProductClass", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO upOrDownProductClass(@RequestParam("id") String id,@RequestParam("upAndDown") String upAndDown) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑商品类目=={}-上移下移=={},开始={}", id,upAndDown,startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			ProductClassDTO productClassDTOId = new ProductClassDTO();
			productClassDTOId.setId(id);
			List<ProductClassDTO> productClassDTOs = productService.getProductClassList(productClassDTOId);
			if(0 == productClassDTOs.size()){
				responseDTO.setErrorInfo("未查到此类目,请刷新重试");
				responseDTO.setResult(StatusConstant.FAILURE);
				return responseDTO;
			}
			ProductClassDTO productClassDTO =productClassDTOs.get(0);
			ProductClassDTO sort = new ProductClassDTO();
			sort.setStatus("1");
			//判断有没有父级,有则是二级分类
			if(!"".equals(productClassDTO.getParentId()) && null != productClassDTO.getParentId()){
				//查询条件,放入父级id
				sort.setParentId(productClassDTO.getParentId());sort.setRank("2");
			}else {
				sort.setRank("1");
			}

			int bannerRank = productClassDTO.getSort();

			if("up".equals(upAndDown)){
				if(bannerRank-1 < 1){
					logger.info("编辑商品类目-上移,出错此id={}已为最上层无法再上移",id);
					responseDTO.setErrorInfo("此类目已为最上层");
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				//查出此banner上一层banner
				sort.setSort(bannerRank-1);
				List<ProductClassDTO> bannerDTODowns = productService.getProductClassList(sort);
				if(0 == bannerDTODowns.size()){
					logger.info("编辑商品类目-上移,出错此bannerRank={}查出banner为null",bannerRank-1);
					responseDTO.setErrorInfo("未查出此类目");
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				ProductClassDTO bannerDTODown = bannerDTODowns.get(0);
				//下移,上层banner
				bannerDTODown.setSort(bannerRank);
				//上移,此banner图
				productClassDTO.setSort(bannerRank-1);
				productService.updateProductClass(productClassDTO);
				productService.updateProductClass(bannerDTODown);

			}else if("down".equals(upAndDown)){
				sort.setSort(bannerRank+1);
				List<ProductClassDTO> bannerDTODowns = productService.getProductClassList(sort);
				if(0 == bannerDTODowns.size()){
					logger.info("编辑商品类目-下移,出错此sort={}查出类目为null",bannerRank+1);
					responseDTO.setErrorInfo("此类目已为最下层");
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				ProductClassDTO bannerDTODown = bannerDTODowns.get(0);
				//上移,下层banner
				bannerDTODown.setSort(bannerRank);
				//下移,此banner图
				productClassDTO.setSort(bannerRank+1);
				productService.updateProductClass(productClassDTO);
				productService.updateProductClass(bannerDTODown);
			}
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("编辑类目-上移下移,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setErrorInfo("移动失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑homeBanner-上移下移{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 删除分类
	 * @param id  分类id
	 * @return
	 */
	@RequestMapping(value = "delProductClassById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<ProductClassDTO> delProductClassById(@RequestParam String id) {
		long startTime = System.currentTimeMillis();
		logger.info("删除分类,开始={}", id);
		ResponseDTO responseDTO = new ResponseDTO<>();
		ProductClassDTO bannerDTODown = null;
		boolean nextBanner = true;
		try {
			//根据id查询此分类
			ProductClassDTO productClassDTO2 = new ProductClassDTO();
			productClassDTO2.setId(id);
			List<ProductClassDTO> productClassDTOS = productService.getProductClassList(productClassDTO2);
			ProductClassDTO productClassDTO = productClassDTOS.get(0);
			//把状态值改为0
			productClassDTO.setStatus("0");
			productService.updateProductClass(productClassDTO);
			logger.info("删除分类=={},楼层为={}",id, productClassDTO.getSort()-1);

			//查询下一层,
			int classSort = productClassDTO.getSort()+1;
			ProductClassDTO productClassDTO1 = new ProductClassDTO();
			//判断是否有父级id,如有则说明当前分类是二级分类,查询的时候就要查父级id下的分类
			if(!"".equals(productClassDTO.getParentId()) && null != productClassDTO.getParentId()){
				//查询条件,放入父级id
				productClassDTO1.setParentId(productClassDTO.getParentId());
				productClassDTO1.setRank("2");
			}else {
				productClassDTO1.setRank("1");
			}
			productClassDTO1.setStatus("1");
			//后面的banner全都向前排序
			while (nextBanner) {
				productClassDTO1.setSort(classSort);
				List<ProductClassDTO> bannerDTODowns = productService.getProductClassList(productClassDTO1);
				if(0 < bannerDTODowns.size()){
					logger.info("删除分类,下层楼层为={}需要前移到={}", classSort,classSort-1);
					bannerDTODown = bannerDTODowns.get(0);
					bannerDTODown.setSort(classSort-1);
					productService.updateProductClass(bannerDTODown);
					classSort++;
				}else {
					logger.info("删除分类,下层banner已全部前移,没有下层banner了={}", classSort);
					nextBanner = false;
				}
			}
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("删除分类,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setErrorInfo("删除分类失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("删除分类{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
}

