package com.wisdom.system.service;

import com.wisdom.common.dto.system.BannerDTO;
import com.wisdom.system.controller.FileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class BannerService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BannerDTO> getHomeBannerList() {
        logger.info("service == 获取banner,getHomeBannerList方法执行");

        Query query = new Query().addCriteria(Criteria.where("status").is("1"))
                .addCriteria(Criteria.where("place").is("home"));
        List<BannerDTO> bannerDTOList = mongoTemplate.find(query, BannerDTO.class,"bannerList");
        return bannerDTOList;
    }
}
