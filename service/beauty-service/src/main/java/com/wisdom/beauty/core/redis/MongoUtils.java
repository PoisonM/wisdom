package com.wisdom.beauty.core.redis;

import com.wisdom.beauty.api.extDto.ImageUrl;
import com.wisdom.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: redisUtils
 *
 * @author: 赵得良
 * Date:     2018/4/4 0004 11:03
 * Description: B端redis帮助类
 */
@Service("mongoUtils")
public class MongoUtils {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存图片信息
     *
     * @param list
     * @param imageId
     */
    public void saveImageUrl(List<String> list, String imageId) {
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setImageId(imageId);
        imageUrl.setUrl(list);
        mongoTemplate.save(imageUrl, "imageUrl");
    }

    /**
     * 查询图片信息
     */
    public List<String> getImageUrl(String imageId) {
        if(StringUtils.isNull(imageId)){
            return  null;
        }
        Query query = new Query(Criteria.where("imageId").is(imageId));
        ImageUrl imageUrl=mongoTemplate.findOne(query, ImageUrl.class, "imageUrl");
        if(imageUrl==null){
            return  null;
        }
        return imageUrl.getUrl();
    }
}
