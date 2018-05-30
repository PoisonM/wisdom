package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.extDto.ExtShopRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopRechargeCardOrderDTO;
import com.wisdom.common.util.SpringUtil;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 赵得良 on 21/09/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CardTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 更新充值卡
     *
     * @throws Exception
     */
    @Test
    public void getShopClerkScheduleList() throws Exception {

        ShopRechargeCardOrderDTO userRechargeCardDTO = new ShopRechargeCardOrderDTO();
        //充值卡id
        userRechargeCardDTO.setId("1");
        userRechargeCardDTO.setTimeDiscount(0.25f);
        userRechargeCardDTO.setPeriodDiscount(0.26f);
        userRechargeCardDTO.setProductDiscount(0.27f);
        userRechargeCardDTO.setName("梅大");
        userRechargeCardDTO.setId("11");
        userRechargeCardDTO.setRechargeAmount("1000");
        userRechargeCardDTO.setAmount(new BigDecimal(5000));
        userRechargeCardDTO.setCashPay("1000");
        userRechargeCardDTO.setPayType("1");
        userRechargeCardDTO.setSurplusPayPrice("100");
        userRechargeCardDTO.setTransactionId("20180509211744970");
        userRechargeCardDTO.setSysUserId("11");
        userRechargeCardDTO.setUserName("小明");

        String toJSONString = JSONObject.toJSONString(userRechargeCardDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/cardInfo/userRechargeConfirm").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 保存充值卡信息
     *
     * @throws Exception
     */
    @Test
    public void saveRechargeCardInfo() throws Exception {

        ExtShopRechargeCardDTO extShopRechargeCardDTO = new ExtShopRechargeCardDTO();
        extShopRechargeCardDTO.setName("测试充值卡名称");
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        imageUrls.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        imageUrls.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        extShopRechargeCardDTO.setImageUrls(imageUrls);
        extShopRechargeCardDTO.setTimeDiscount(0.75f);
        extShopRechargeCardDTO.setPeriodDiscount(0.95f);
        extShopRechargeCardDTO.setProductDiscount(0.85f);
        extShopRechargeCardDTO.setIntroduce("这是一张测试用的充值卡~");
        extShopRechargeCardDTO.setAmount(new BigDecimal(10000));

        extShopRechargeCardDTO.setId("4e3a924187b44bc5a156262f690902e2");
        List<ShopProjectProductCardRelationDTO> relationDTOS = new ArrayList<>();
        ShopProjectProductCardRelationDTO relationDTO = new ShopProjectProductCardRelationDTO();
        relationDTO.setShopGoodsTypeId("1");
        relationDTO.setGoodsType("1");
        relationDTOS.add(relationDTO);
        relationDTO.setShopGoodsTypeId("2");
        relationDTO.setGoodsType("2");
        relationDTOS.add(relationDTO);
        extShopRechargeCardDTO.setTimesList(relationDTOS);
        String toJSONString = JSONObject.toJSONString(extShopRechargeCardDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/cardInfo/saveRechargeCardInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果
//        MvcResult result = mvc.perform(post("/cardInfo/updateRechargeCardInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的

//        MvcResult result = mvc.perform(post("/cardHelper/getGoodsUseScope").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }


    /**
     * 查询最近充值卡信息
     */
    /**
     * 删除档案信息
     *
     * @throws Exception
     */
    @Test
    public void getShopUserRecentlyOrderInfo() throws Exception {

//        MvcResult result = mvc.perform(get("/cardInfo/rechargeCardSignConfirm").param("transactionId", "20180510170143457").param("imageUrl", "1"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        MvcResult result = mvc.perform(get("/cardHelper/getGoodsUseScope").param("shopRechargeCardId", "2"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

}
