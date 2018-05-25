package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.extDto.RequestDTO;
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
public class ProductTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() {
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

//        MvcResult result = mvc.perform(get("/productInfo/getUserProductInfo").param("id", "1"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

//        MvcResult result = mvc.perform(get("/productTypeInfo/saveProductTypeInfo").param("productTypeName", "产品类别").param("status", "0"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果 .param("productType", "0").param("levelOneId", "1")

        MvcResult result = mvc.perform(get("/productTypeInfo/getShopProductLevelInfo"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

//        MvcResult result = mvc.perform(get("/productInfo/getProductInfoByScanCode").param("code", "6938166920785"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果
        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 保存档案信息
     *
     * @throws Exception
     */
    @Test
    public void testSaveArchiveInfo() throws Exception {

        ShopProductInfoDTO dto = new ShopProductInfoDTO();
        // 0：客装 1：院装 2：易耗品
        dto.setProductType("0");
        dto.setProductTypeOneId("1");
        dto.setProductTypeTwoId("2");
        dto.setProductTypeOneName("产品品牌");
        dto.setProductTypeTwoName("产品系列");
        dto.setProductName("产品名称");
        dto.setProductUrl("图片url地址");
        dto.setMarketPrice(new BigDecimal(1000));
        dto.setDiscountPrice(new BigDecimal(900));
        dto.setProductCode("产品编号");
        dto.setProductSpec("100");
        dto.setProductSpecUnit("ml");
        dto.setProductUnit("瓶");
        dto.setProductPosition("脚部");
        dto.setProductFunction("对脚部很有好处");
        dto.setQualityPeriod(12);
        dto.setProductWarningDay(12);
        dto.setProductWarningNum(10);
        dto.setStatus("0");
        dto.setIntroduce("产品简介");
        dto.setEffectDate("2017-02-02");

        String toJSONString = JSONObject.toJSONString(dto);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/productInfo/saveProductInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());

    }


    /**
     * 根据用户与项目的关系主键列表查询用户与项目的关系
     */
    @Test
    public void getUserShopProjectList() throws Exception {

        RequestDTO<ShopProductTypeDTO> shopProductTypeDTORequestDTO = new RequestDTO<>();
        ShopProductTypeDTO shopProductTypeDTO1 = new ShopProductTypeDTO();
        shopProductTypeDTO1.setId("1eaf7f33405d4c1c933d5c6d79839e46");
        shopProductTypeDTO1.setProductTypeName("修改操作");
        shopProductTypeDTO1.setParentId("3");

        ShopProductTypeDTO shopProductTypeDTO2 = new ShopProductTypeDTO();
        shopProductTypeDTO2.setId("19d3ef2ef7da4579a16ecca54735dfb4");
        shopProductTypeDTO2.setStatus("1");
        shopProductTypeDTO2.setParentId("3");

        ShopProductTypeDTO shopProductTypeDTO3 = new ShopProductTypeDTO();
        shopProductTypeDTO3.setProductTypeName("添加操作");
        shopProductTypeDTO3.setStatus("1");
        shopProductTypeDTO3.setParentId("3");

        shopProductTypeDTORequestDTO.andList(shopProductTypeDTO1);
        shopProductTypeDTORequestDTO.andList(shopProductTypeDTO2);
        shopProductTypeDTORequestDTO.andList(shopProductTypeDTO3);

//        ShopProductTypeDTO shopProductTypeDTO = new ShopProductTypeDTO();
//        shopProductTypeDTO.setId("1eaf7f33405d4c1c933d5c6d79839e46");
//        shopProductTypeDTO.setProductTypeName("品牌名称");
//        shopProductTypeDTO.setStatus("1");

        String toJSONString = JSONObject.toJSONString(shopProductTypeDTORequestDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/productTypeInfo/updateTwoLevelTypeInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

}
