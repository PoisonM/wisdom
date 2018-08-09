package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.ProductMapper;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductClassDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.FrontUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PayRecordService payRecordService;

    @Autowired
    private MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductDTO getBusinessProductInfo(String productId) {
        ProductDTO productDTO = productMapper.getBusinessProductInfo(productId);
        return productDTO;
    }

    /**
     * 查询所有商品
     * @param pageParamVoDTO
     * @return
     */
    public PageParamVoDTO<List<ProductDTO>> queryAllProducts(PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        logger.info("service -- 查询所有商品 queryAllProducts,方法执行");
        PageParamVoDTO<List<ProductDTO>> pageResult = new  PageParamVoDTO<>();
        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());
        String pageSize = String.valueOf(pageParamVoDTO.getPageSize());
        Page<ProductDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<ProductDTO> resultPage = productMapper.queryAllProducts(page);
        for (ProductDTO productDTO : resultPage.getList()){
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
            productDTO.setProductDetail(offlineProductDTO);
            productDTO.setSellNum(sellNum);
        }
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }

    /**
     * 条件查询商品
     * @param pageParamVoDTO
     * @return
     */
    public PageParamVoDTO<List<ProductDTO>> queryProductsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        logger.info("service -- 条件查询商品 queryProductsByParameters,方法执行");
        PageParamVoDTO<List<ProductDTO>> page = new  PageParamVoDTO<>();
        int count = productMapper.queryProductsCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<ProductDTO> productDTOList = productMapper.queryProductsByParameters(pageParamVoDTO);
        logger.info("service -- 条件查询商品Count={},List={}",count,productDTOList.size());
        for (ProductDTO productDTO : productDTOList){
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            if(null != offlineProductDTO){
                offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
                if(null == offlineProductDTO.getProductSalesVolume() || "".equals(offlineProductDTO.getProductSalesVolume())){
                    offlineProductDTO.setProductSalesVolume("0");
                }
                offlineProductDTO.setProductSalesVolume((Integer.parseInt(sellNum)*8+Integer.parseInt(offlineProductDTO.getProductSalesVolume())+""));
            }
            productDTO.setSellNum(sellNum);
            productDTO.setProductDetail(offlineProductDTO);
        }
        page.setResponseData(productDTOList);
        return page;
    }

    /**
     * 根据id查询商品
     * @param productId
     * @return
     */
    public ProductDTO findProductById(String productId) {
        logger.info("service -- 根据商品id={}查询商品信息 findProductById,方法执行",productId);
        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
        offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
        ProductDTO productDTO = productMapper.findProductById(productId);
        productDTO.setProductDetail(offlineProductDTO);
        return productDTO;
    }

    /**
     * 编辑商品
     * @param productDTO
     */
    public void updateProductByParameters(ProductDTO<OfflineProductDTO> productDTO) {
        logger.info("service -- 编辑商品={} updateProductByParameters,方法执行",productDTO.getProductId());
        Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
        Update update = new Update();
        update.set("tag",productDTO.getProductDetail().getTag());
        update.set("listPic",productDTO.getProductDetail().getListPic());
        update.set("services",productDTO.getProductDetail().getServices());
        update.set("spec",productDTO.getProductDetail().getSpec());
        update.set("detailPic",productDTO.getProductDetail().getDetailPic());
        update.set("productMarketPrice",productDTO.getProductDetail().getProductMarketPrice());
        update.set("productSalesVolume",productDTO.getProductDetail().getProductSalesVolume());
        update.set("senderAddress",productDTO.getProductDetail().getSenderAddress());
        update.set("productAmount",productDTO.getProductDetail().getProductAmount());
        mongoTemplate.updateFirst(query,update,"offlineProduct");
        productMapper.updateProductByParameters(productDTO);
    }

    /**
     * 编辑商品-上架
     * @param id
     */
    public void putAwayProductById(String id) {
        productMapper.putAwayProductById(id);
    }
    /**
     * 编辑商品-下架
     * @param id
     */
    public void delProductById(String id) {
        productMapper.delProductById(id);
    }

    public void addOfflineProduct(ProductDTO<OfflineProductDTO> productDTO) {
        logger.info("service -- 新增商品={} addOfflineProduct,方法执行",productDTO.getProductId());
        mongoTemplate.insert(productDTO.getProductDetail(),"offlineProduct");
        productMapper.addOfflineProduct(productDTO);
    }

    public List<String> getBorderSpecialProductBrandList() {
        return productMapper.getBorderSpecialProductBrandList();
    }

    /**
     * 根据产品id获取产品详情
     *
     * */
    public ProductDTO findProductDetail(String id){

        ProductDTO productDTO = productMapper.findProductById(id);
        return productDTO;
    }

    /**
     * 获取商品一级类目
     * @return
     */
    public List<ProductClassDTO> getOneProductClassList() {
        return productMapper.getOneProductClassList();
    }

    public List<ProductClassDTO> getTwoProductClassList(String productClassId) {
        return productMapper.getTwoProductClassList(productClassId);
    }

    public void addProductClass(ProductClassDTO productClassDTO) {
        productMapper.addProductClass(productClassDTO);
    }

    public void updateProductClass(ProductClassDTO productClassDTO) {
        productMapper.updateProductClass(productClassDTO);
    }

    public List<ProductClassDTO> getProductClassList(ProductClassDTO productClassDTO) {
        return productMapper.getProductClassList(productClassDTO);
    }
}
