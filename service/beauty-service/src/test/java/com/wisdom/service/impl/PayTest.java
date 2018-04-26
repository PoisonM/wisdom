package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
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
public class PayTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 查询用户最近一次订单信息
     */
    /**
     * 删除档案信息
     *
     * @throws Exception
     */
    @Test
    public void getShopUserRecentlyOrderInfo() throws Exception {

        MvcResult result = mvc.perform(get("/projectInfo/getShopCourseProjectList").param("shopUserArchivesId", "1"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 更新用户订单信息
     *
     * @throws Exception
     */
    @Test
    public void userPayOpe() throws Exception {

        ShopUserPayDTO shopUserPayDTO = new ShopUserPayDTO();
        shopUserPayDTO.setOrderId("20180424200819402");
        shopUserPayDTO.setPayType(PayTypeEnum.ALI_PAY.getCode());
        shopUserPayDTO.setShopUserArchivesId("1");
        shopUserPayDTO.setCashPayPrice("1000");
        shopUserPayDTO.setSurplusPayPrice("1000");
        ArrayList<ShopUserRechargeCardDTO> rechargeCardDTOS = new ArrayList<>();
        ShopUserRechargeCardDTO userRechargeCardDTO = new ShopUserRechargeCardDTO();
        //消费的金额
        userRechargeCardDTO.setSurplusAmount(new BigDecimal(100));
        userRechargeCardDTO.setId("110");
        rechargeCardDTOS.add(userRechargeCardDTO);

        String toJSONString = JSONObject.toJSONString(shopUserPayDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/userPay/userPayOpe").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 用户签字确认接口
     *
     * @throws Exception
     */
    @Test
    public void paySignConfirm() throws Exception {

        ShopUserPayDTO shopUserPayDTO = new ShopUserPayDTO();
        shopUserPayDTO.setOrderId("20180424200819402");
        shopUserPayDTO.setSignUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524733023679&di=1f09aa7db917eadb7e86e880bdc67f36&imgtype=0&src=http%3A%2F%2Fd9.yihaodianimg.com%2FN04%2FM06%2F31%2F37%2FCgQDr1OpPFKAAmDlAAEftCRsH7g04201_600x600.jpg");

        String toJSONString = JSONObject.toJSONString(shopUserPayDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/userPay/paySignConfirm").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }


}
