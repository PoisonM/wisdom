package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
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
import java.util.Date;
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
public class OrderTest {

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

        MvcResult result = mvc.perform(get("/orderInfo/getShopUserRecentlyOrderInfo").param("shopUserArchivesId", "1"))
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
    public void updateShopUserOrderInfo() throws Exception {

        ShopUserOrderDTO shopUserOrderDTO = getShopUserOrderDTO();

        String toJSONString = JSONObject.toJSONString(shopUserOrderDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/userPay/updateShopUserOrderPayInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    private ShopUserOrderDTO getShopUserOrderDTO() {
        ShopUserOrderDTO shopUserOrderDTO = new ShopUserOrderDTO();
        shopUserOrderDTO.setOrderId("20180510175636866");

        //用户与项目关系
//        List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS = new ArrayList<>();
//        buildShopUserProjectRelationDTO(shopUserProjectRelationDTOS);
//        shopUserOrderDTO.setShopUserProjectRelationDTOS(shopUserProjectRelationDTOS);

//
//        //用户与产品
//        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = new ArrayList<>();
//        ShopUserProductRelationDTO productRelationDTO = getShopUserProductRelationDTO();
//        shopUserProductRelationDTOS.add(productRelationDTO);
//        shopUserOrderDTO.setShopUserProductRelationDTOS(shopUserProductRelationDTOS);


        //套卡
//        List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS = new ArrayList<>();
//        ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = getShopUserProjectGroupRelRelationDTO();
//        projectGroupRelRelationDTOS.add(groupRelRelationDTO);
//        shopUserOrderDTO.setProjectGroupRelRelationDTOS(projectGroupRelRelationDTOS);

        ShopUserRechargeCardDTO userRechargeCardDTO = new ShopUserRechargeCardDTO();
        userRechargeCardDTO.setId("用户的充值卡id");
        userRechargeCardDTO.setInitAmount(new BigDecimal(1000));
        userRechargeCardDTO.setShopRechargeCardName("开门红");
        userRechargeCardDTO.setSurplusAmount(new BigDecimal(10));

        ShopUserRechargeCardDTO userRechargeCardDTO2 = new ShopUserRechargeCardDTO();
        userRechargeCardDTO2.setId("用户的充值卡id");
        userRechargeCardDTO2.setInitAmount(new BigDecimal(1002));
        userRechargeCardDTO2.setShopRechargeCardName("至尊");
        userRechargeCardDTO2.setSurplusAmount(new BigDecimal(10));


        List<ShopUserRechargeCardDTO> objects = new ArrayList<>();
        objects.add(userRechargeCardDTO);
        objects.add(userRechargeCardDTO2);
        shopUserOrderDTO.setUserPayRechargeCardList(objects);
        //充值卡
        return shopUserOrderDTO;
    }


    /**
     * 保存用户订单信息
     *
     * @throws Exception
     */
    @Test
    public void testSaveShopUserOrderInfo() throws Exception {

        ShopUserOrderDTO shopUserOrderDTO = getShopUserOrderDTO();
        shopUserOrderDTO.setOrderId("20180424200819402");
        shopUserOrderDTO.setStatus(OrderStatusEnum.WAIT_PAY.getCode());
        String toJSONString = JSONObject.toJSONString(shopUserOrderDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/orderInfo/updateShopUserOrderInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    private ShopUserProjectGroupRelRelationDTO getShopUserProjectGroupRelRelationDTO() {
        ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
        groupRelRelationDTO.setProjectSurplusTimes(1000);
        groupRelRelationDTO.setProjectSurplusAmount(new BigDecimal(1000));
        groupRelRelationDTO.setSysClerkId("110");
        groupRelRelationDTO.setShopProjectGroupName("综合啦");
        groupRelRelationDTO.setId("201804242008s9402");
        groupRelRelationDTO.setShopProjectGroupId("101");
        groupRelRelationDTO.setDiscount(0.23f);
        groupRelRelationDTO.setProjectInitTimes(10);
        groupRelRelationDTO.setProjectInitAmount(new BigDecimal(1000));
        return groupRelRelationDTO;
    }

    private ShopUserProductRelationDTO getShopUserProductRelationDTO() {
        ShopUserProductRelationDTO productRelationDTO = new ShopUserProductRelationDTO();
        productRelationDTO.setSysUserId("110");
        productRelationDTO.setDiscount(0.75f);
        productRelationDTO.setInitAmount(new BigDecimal("100"));
        productRelationDTO.setInitTimes(20);
        productRelationDTO.setId("20180424200829402");
        productRelationDTO.setSysClerkId("101");
        productRelationDTO.setShopProductId("101");
        productRelationDTO.setShopProductName("迪奥");
        return productRelationDTO;
    }

    /**
     * 构建用户与项目的关系
     *
     * @param shopUserProjectRelationDTOS
     */
    private void buildShopUserProjectRelationDTO(List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS) {
        ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
        relationDTO.setSysShopProjectInitTimes(20);
        relationDTO.setSysShopProjectInitAmount(new BigDecimal(100));
        relationDTO.setSysShopProjectId("1");
        relationDTO.setSysUserId("1");
        relationDTO.setSysShopProjectName("美白");
        relationDTO.setId("20180424200819402");
        relationDTO.setEffectiveDate(new Date());
        relationDTO.setInvalidDays(new Date());
        relationDTO.setUseStyle(GoodsTypeEnum.TREATMENT_CARD.getCode());
        relationDTO.setDiscount(0.75f);
        relationDTO.setEffectiveDate(new Date());
        relationDTO.setSysClerkId("110");
        shopUserProjectRelationDTOS.add(relationDTO);
    }

}
