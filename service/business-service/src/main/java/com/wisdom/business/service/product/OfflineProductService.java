package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.ProductMapper;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class OfflineProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductMapper productMapper;

    public ProductDTO<OfflineProductDTO> getOfflineProductDetailById(String productId) {

        ProductDTO<OfflineProductDTO> productDTO = productMapper.getBusinessProductInfo(productId);

        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");

        if(productDTO!=null)
        {
            productDTO.setProductDetail(offlineProductDTO);
        }

        return productDTO;
    }

    public List<ProductDTO> findOfflineProductList(PageParamDTO pageParamDTO) {
        List<ProductDTO> productDTOList = productMapper.findOfflineProductList(pageParamDTO);
        return productDTOList;
    }

    public List<ProductDTO> findSpecialOfflineProductList(PageParamDTO pageParamDTO) {
        List<ProductDTO> productDTOList = productMapper.findSpecialOfflineProductList(pageParamDTO);
        return productDTOList;
    }
}
