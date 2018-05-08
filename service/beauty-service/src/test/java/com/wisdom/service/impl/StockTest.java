package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.dto.ShopStockDTO;
import com.wisdom.beauty.api.dto.ShopStockRecordDTO;
import com.wisdom.beauty.api.extDto.ExtShopClerkScheduleDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRecordRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.util.SpringUtil;
import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2018/5/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class StockTest  {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ShopStockService shopStockService;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }


    @Test
   public void  testAadd() throws Exception {


        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockDTO = new ShopStockRequestDTO();
        shopStockDTO.setShopBossId("11");
        shopStockDTO.setShopStoreId("123");
        shopStockDTO.setShopStockRecordId("3333");
        shopStockDTO.setStockStatus("0");

        shopStockDTO.setShopProcId("123");
        shopStockDTO.setFlowNo("123456789");
        shopStockDTO.setDetail("beizhu");
        shopStockDTO.setStockNumber(99);
        shopStockDTO.setProductDate(new Date());
        shopStockDTO.setStockPrice(new BigDecimal("44"));
        list.add(shopStockDTO);


        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String



        MvcResult result = mvc.perform(post("/stock/addStock").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public  void test(){
      /*  String json =  "[{\"id\":\"133223\",\"shopBossId\":\"20\"},{\"id\":\"3232\",\"shopBossId\":\"20\"}]";
        shopStockService.insertShopStockDTO(json);*/
        ShopStockRecordRequestDTO shopStockRecordRequestDTO = new ShopStockRecordRequestDTO();

        shopStockRecordRequestDTO.setPageSize(9);
        shopStockRecordRequestDTO.setStartTime("2018-04-10 00:00:00");
        shopStockRecordRequestDTO.setEndTime("2019-04-10 00:00:00");
        shopStockRecordRequestDTO.setShopStoreId("5");
        shopStockRecordRequestDTO.setStockStyle("0");

        String toJSONString = JSONObject.toJSONString(shopStockRecordRequestDTO);
    }

}
