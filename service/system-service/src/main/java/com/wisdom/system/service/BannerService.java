package com.wisdom.system.service;

import com.wisdom.common.dto.system.BannerDTO;
import com.wisdom.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class BannerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BannerDTO> getHomeBannerList() {
        Query query = new Query().addCriteria(Criteria.where("status").is("1"))
                .addCriteria(Criteria.where("place").is("home"));
        List<BannerDTO> bannerDTOList = mongoTemplate.find(query, BannerDTO.class,"bannerList");
        return bannerDTOList;
    }

    public void addHomeBanner(BannerDTO bannerDTO) {
        mongoTemplate.insert(bannerDTO,"bannerList");
    }

    public void updateHomeBanner(BannerDTO bannerDTO) {
        Query query = new Query().addCriteria(Criteria.where("bannerId").is(bannerDTO.getBannerId()));
        Update update = new Update();
        update.set("bannerId",bannerDTO.getBannerId());
        update.set("uri",bannerDTO.getUri());
        update.set("forward",bannerDTO.getForward());
//        update.set("place",bannerDTO.getPlace());
        update.set("bannerType",bannerDTO.getBannerType());
//        update.set("status",bannerDTO.getStatus());
        update.set("bannerRank",bannerDTO.getBannerRank());
        update.set("createDate", DateUtils.getDate());
        mongoTemplate.updateFirst(query,update,"bannerList");
    }

    public BannerDTO findHomeBannerInfoById(String bannerId) {
        Query query = new Query().addCriteria(Criteria.where("bannerId").is(bannerId));
        BannerDTO bannerDTO = mongoTemplate.findOne(query, BannerDTO.class,"bannerList");
        return bannerDTO;
    }

    public BannerDTO findHomeBannerInfoByBannerRank(int bannerRank) {
        Query query = new Query().addCriteria(Criteria.where("bannerRank").is(bannerRank)).addCriteria(Criteria.where("status").is("1"));
        BannerDTO bannerDTO = mongoTemplate.findOne(query, BannerDTO.class,"bannerList");
        return bannerDTO;
    }

    public void delHomeBannerById(String bannerId) {
        Query query = new Query().addCriteria(Criteria.where("bannerId").is(bannerId));
        Update update = new Update();
        update.set("status","0");
        mongoTemplate.updateFirst(query,update,"bannerList");
    }

    public BannerDTO findHomeBannerInfoByBannerType(String bannerType) {
        Query query = new Query().addCriteria(Criteria.where("bannerType").is(bannerType)).addCriteria(Criteria.where("status").is("1"));
        BannerDTO bannerDTO = mongoTemplate.findOne(query, BannerDTO.class,"bannerList");
        return bannerDTO;
    }
}
