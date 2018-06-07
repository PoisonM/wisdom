package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.ProductMapper;
import com.wisdom.business.mapper.transaction.PayRecordMapper;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.*;
import com.wisdom.common.dto.system.PageParamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TrainingProductService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PayRecordMapper payRecordMapper;

    public ProductDTO<TrainingProductDTO> getTrainingProductDetailById(String productId) {
        logger.info("service -- 根据id查询视频信息={} getTrainingProductDetailById,方法执行",productId);
        ProductDTO<TrainingProductDTO> productDTO = productMapper.getBusinessProductInfo(productId);

        //从mongodb库中获取培训课程详尽信息
        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        TrainingProductDTO trainingProductDTO = mongoTemplate.findOne(query, TrainingProductDTO.class,"trainingProduct");
        productDTO.setProductDetail(trainingProductDTO);
        return productDTO;

    }

    public List<ProductDTO> getTrainingProductList(PageParamDTO pageParamDTO,float price) {
        List<ProductDTO> trainingProductDTOList = productMapper.findTrainingProductList(pageParamDTO.getPageNo(),pageParamDTO.getPageSize(),price);
        return trainingProductDTOList;
    }

    public int getTrainingProductPlayNum(String productId) {
        Query query = new Query().addCriteria(Criteria.where("productId").is(productId));
        List<TrainingProductPlayStatisticDTO> trainingProductPlayStatisticDTOS = mongoTemplate.find(query, TrainingProductPlayStatisticDTO.class,"trainingProductPlayStatistic");

        //从mongodb库中获取培训课程详尽信息
        query = new Query().addCriteria(Criteria.where("productId").is(productId));
        TrainingProductDTO trainingProductDTO = mongoTemplate.findOne(query, TrainingProductDTO.class,"trainingProduct");

        int num = 0;
        for(CourseDTO courseDTO:trainingProductDTO.getListCourse())
        {
            for(SecondCourseDTO secondCourseDTO:courseDTO.getList())
            {
                for(TrainingProductPlayStatisticDTO trainingProductPlayStatisticDTO:trainingProductPlayStatisticDTOS)
                {
                    if(secondCourseDTO.getUrl().equals(trainingProductPlayStatisticDTO.getUrl()))
                    {
                        num = num + trainingProductPlayStatisticDTO.getPlayNum();
                    }
                }
            }
        }
        logger.info("service -- 根据id={}查询视频点击数={} getTrainingProductPlayNum,方法执行",productId,num);
        return num;
    }

    public void AddTrainingProductPlayNum(String productId, String playURL) {
        logger.info("service -- 根据id={}增加视频点击数={} AddTrainingProductPlayNum,方法执行",productId,playURL);
        Query query = new Query().addCriteria(Criteria.where("productId").is(productId)).addCriteria(Criteria.where("url").is(playURL));
        TrainingProductPlayStatisticDTO trainingProductPlayStatisticDTO = mongoTemplate.findOne(query, TrainingProductPlayStatisticDTO.class,"trainingProductPlayStatistic");

        if(trainingProductPlayStatisticDTO==null)
        {
            trainingProductPlayStatisticDTO = new  TrainingProductPlayStatisticDTO();
            trainingProductPlayStatisticDTO.setPlayNum(1);
            trainingProductPlayStatisticDTO.setProductId(productId);
            trainingProductPlayStatisticDTO.setUrl(playURL);
            mongoTemplate.insert(trainingProductPlayStatisticDTO,"trainingProductPlayStatistic");
        }
        else
        {
            Update update = new Update();
            update.set("playNum",trainingProductPlayStatisticDTO.getPlayNum()+1);
            mongoTemplate.updateFirst(query,update,"trainingProductPlayStatistic");
        }
    }

    /**
     * 条件查询视频
     * @param pageParamVoDTO
     * @return
     */
    public PageParamVoDTO<List<ProductDTO>> queryTrainingProductsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO) {
        logger.info("service -- 条件查询视频 queryTrainingProductsByParameters,方法执行");
        PageParamVoDTO<List<ProductDTO>> page = new  PageParamVoDTO<>();
        int count = productMapper.queryProductsCountByParameters(pageParamVoDTO);
        page.setTotalCount(count);
        List<ProductDTO> productDTOList = productMapper.queryProductsByParameters(pageParamVoDTO);
        logger.info("service -- 条件查询视频Count={},List={}",count,productDTOList.size());
        for (ProductDTO productDTO : productDTOList){
            String sellNum = payRecordMapper.getSellNumByProductId(productDTO.getProductId());
            Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
            TrainingProductDTO trainingProductDTO = mongoTemplate.findOne(query, TrainingProductDTO.class,"trainingProduct");
            productDTO.setSellNum(sellNum);
            productDTO.setProductDetail(trainingProductDTO);
        }
        page.setResponseData(productDTOList);
        return page;
    }

    public void addTrainingProduct(ProductDTO<TrainingProductDTO> productDTO){
        logger.info("service -- 新增视频={} addTrainingProduct,方法执行",productDTO.getProductId());
        mongoTemplate.insert(productDTO.getProductDetail(),"trainingProduct");
        productMapper.addOfflineProduct(productDTO);
    }
    public void updateTrainingProduct(ProductDTO<TrainingProductDTO> productDTO){
        logger.info("service -- 编辑视频={} updateTrainingProduct,方法执行",productDTO.getProductId());
        Query query = new Query().addCriteria(Criteria.where("productId").is(productDTO.getProductId()));
        Update update = new Update();
        update.set("trainingProductName",productDTO.getProductDetail().getTrainingProductName());
        update.set("brand",productDTO.getProductDetail().getBrand());
        update.set("trainingProductType",productDTO.getProductDetail().getTrainingProductType());
        update.set("description",productDTO.getProductDetail().getDescription());
        update.set("detailList",productDTO.getProductDetail().getDetailList());
        update.set("listCourse",productDTO.getProductDetail().getListCourse());
        mongoTemplate.updateFirst(query,update,"trainingProduct");
        productMapper.updateProductByParameters(productDTO);
    }
}
