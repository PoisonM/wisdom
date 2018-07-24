package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.extDto.ImageUrl;
import com.wisdom.beauty.core.redis.MongoUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@WebAppConfiguration
public class MongonTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoUtils mongoUtils;

    @Test
   public void  mongoUtilsTest(){
        String _id="5b02627fa34ec30da4988d8c";
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("4");
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUrl(list);

        imageUrl.set_id(new ObjectId(_id));
        mongoTemplate.save(imageUrl, "imageUrl");
    }
}
