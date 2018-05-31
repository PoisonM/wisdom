package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.extDto.ImageUrl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Language;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    @Test
   public void  testMongoing(){
        //条件查询1，多条件is("值")后面可以加and("字段2").is("值2")
/*       Query query=new Query(Criteria.where("_id").is("1"));
        mongoTemplate.find(query, Object.class);*/
            List<String> list=new ArrayList<>();
            list.add("dfsfdffad");

          Query query = new Query();
          query.addCriteria(Criteria.where("imageId").is("dc7e6373c8264fc1a5833ed5ac39e4c39999999"));
          Update update = Update.update("url", list);
          mongoTemplate.updateFirst(query, update, ImageUrl.class);
        }
}
