package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.ProductMapper;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PageParamVoDTO<List<ProductDTO>> pageResult = new  PageParamVoDTO<>();
        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());
        String pageSize = String.valueOf(pageParamVoDTO.getPageSize());
        Page<ProductDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<ProductDTO> resultPage = productMapper.queryAllProducts(page);
        for (ProductDTO productDTO : resultPage.getList()){
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            productDTO.setProductAmount(offlineProductDTO.getProductAmount());
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
        PageParamVoDTO<List<ProductDTO>> page = new  PageParamVoDTO<>();
        int count = productMapper.queryProductsCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<ProductDTO> productDTOList = productMapper.queryProductsByParameters(pageParamVoDTO);
        for (ProductDTO productDTO : productDTOList){
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            productDTO.setSellNum(sellNum);
            if(offlineProductDTO != null){
                productDTO.setProductAmount(offlineProductDTO.getProductAmount());
            }
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
        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
        ProductDTO productDTO = productMapper.findProductById(productId);
        productDTO.setProductDetail(offlineProductDTO);
        return productDTO;
    }

    /**
     * 编辑商品
     * @param productDTO
     */
    public void updateProductByParameters(ProductDTO<OfflineProductDTO> productDTO) {

        Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
        Update update = new Update();
        update.set("tag",productDTO.getProductDetail().getTag());
        update.set("listPic",productDTO.getProductDetail().getListPic());
        update.set("services",productDTO.getProductDetail().getServices());
        update.set("spec",productDTO.getProductDetail().getSpec());
        update.set("detailPic",productDTO.getProductDetail().getDetailPic());
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
        mongoTemplate.insert(productDTO.getProductDetail(),"offlineProduct");
        productMapper.addOfflineProduct(productDTO);
    }
}
