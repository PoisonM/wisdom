package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.ProductMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class OfflineProductService {
    Logger logger = LoggerFactory.getLogger(OfflineProductService.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PayRecordService payRecordService;

    public ProductDTO<OfflineProductDTO> getOfflineProductDetailById(String productId) {
        logger.info("service -- 根据商品id={}查询商品信息 getOfflineProductDetailById,方法执行",productId);
        ProductDTO<OfflineProductDTO> productDTO = productMapper.getBusinessProductInfo(productId);

        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
        offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
        if(productDTO!=null)
        {
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            productDTO.setProductDetail(offlineProductDTO);
            if(offlineProductDTO!=null)
            {
                int sell = (Integer.parseInt(sellNum) * 8) + Integer.parseInt(null == offlineProductDTO.getProductSalesVolume()?"0":offlineProductDTO.getProductSalesVolume());
                offlineProductDTO.setProductSalesVolume(sell+"");
            }
        }


        return productDTO;
    }

    public List<ProductDTO> findOfflineProductList(PageParamDTO<ProductDTO> pageParamDTO) {
        List<ProductDTO> productDTOList = productMapper.findOfflineProductList(pageParamDTO);
        for (ProductDTO productDTO : productDTOList){
            String sellNum = payRecordService.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            if(null != offlineProductDTO){
                offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
                //sellNum真是销量乘一个基数
                int sell = (Integer.parseInt(sellNum) * 8) + Integer.parseInt(null ==offlineProductDTO.getProductSalesVolume()?"0":offlineProductDTO.getProductSalesVolume());
                offlineProductDTO.setProductSalesVolume(sell+"");
                productDTO.setSellNum(sell+"");
            }
            productDTO.setProductDetail(offlineProductDTO);
        }
        return productDTOList;
    }

    public List<ProductDTO> findSpecialOfflineProductList(PageParamDTO pageParamDTO) {
        List<ProductDTO> productDTOList = productMapper.findSpecialOfflineProductList(pageParamDTO);
        return productDTOList;
    }
}
