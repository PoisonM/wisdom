package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
       Query query=new Query(Criteria.where("_id").is("1"));
        mongoTemplate.find(query, Object.class);
    }
}
