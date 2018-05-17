package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopClerkScheduleDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRecordRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.core.mapper.ExtShopStockNumberMapper;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.SpringUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ExtShopStockNumberMapper extShopStockNumberMapper;

    @Autowired
    private ExtShopUserConsumeRecordMapper extShopUserConsumeRecordMapper;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }


    @Test
   public void  testAadd() throws Exception {


        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockRequestDTO = new ShopStockRequestDTO();

        shopStockRequestDTO.setShopStoreId("651742081");
        shopStockRequestDTO.setStockStyle("1");
        shopStockRequestDTO.setShopProcId("7878");
        shopStockRequestDTO.setFlowNo("785489636598741258");
        shopStockRequestDTO.setDetail("我写的备注啦啦啦啦");
        shopStockRequestDTO.setStockNumber(99);
        shopStockRequestDTO.setProductDate(new Date());
        shopStockRequestDTO.setStockPrice(new BigDecimal("88.88"));
        //如果是入库则不需要出这个
        //shopStockRequestDTO.setStockType("0");
        shopStockRequestDTO.setStockStyle("0");

        list.add(shopStockRequestDTO);

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
        /*String json =  "[{\"id\":\"133223\",\"shopBossId\":\"20\"},{\"id\":\"3232\",\"shopBossId\":\"20\"}]";
        shopStockService.insertShopStockDTO(json);*/
        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockDTO = new ShopStockRequestDTO();
        shopStockDTO.setShopBossId("11");
        shopStockDTO.setShopStoreId("123");
        shopStockDTO.setShopStockRecordId("3333");
        shopStockDTO.setStockStatus("0");

        shopStockDTO.setShopProcId("00");
        shopStockDTO.setFlowNo("999999999");
        shopStockDTO.setDetail("尹朝阳八佰伴");
        shopStockDTO.setStockNumber(99);
        //shopStockDTO.setProductDate(new Date());
        shopStockDTO.setStockPrice(new BigDecimal("44"));
        list.add(shopStockDTO);


        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String
        shopStockService.insertShopStockDTO(toJSONString);
    }
    @Test
    public void  testUpdate(){
        Map<String,Object> param=new HashedMap();
        ShopStockNumberDTO shopStockNumberDTO=new ShopStockNumberDTO();
        shopStockNumberDTO.setId("000000");
        shopStockNumberDTO.setId("882");
        shopStockNumberDTO.setStockNumber(999);
        ShopStockNumberDTO shopStockNumberDTO2=new ShopStockNumberDTO();
        shopStockNumberDTO2.setId("883");
        shopStockNumberDTO2.setId("00000220");
        shopStockNumberDTO2.setStockNumber(999);
        List<ShopStockNumberDTO> list=new ArrayList<>();
        list.add(shopStockNumberDTO);
        list.add(shopStockNumberDTO2);
        param.put("list",list);
       // extShopStockNumberMapper.updateBatchShopStockNumber(param);
        extShopStockNumberMapper.saveBatchShopStockNumber(list);
    }

    @Test
    public  void  tesst(){
        ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
        ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
        // 设置查询条件
        criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
        criteria.andSysClerkIdEqualTo("11");
        Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
    }
    @Test
    public void testRedis(){
        //JedisUtils.setObject("zhtest3", new Date(), 0);
        //Object object=JedisUtils.getObject("zhtest3");
        String DATE1="1995-11-12 15:21:00";
        String DATE2="1996-01-01 15:21:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");

            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
            } else {

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
