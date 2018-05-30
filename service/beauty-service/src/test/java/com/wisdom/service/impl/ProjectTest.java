package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.enums.CardTypeEnum;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectGroupDTO;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.extDto.RelationIds;
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
public class ProjectTest {

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

        MvcResult result = mvc.perform(get("/projectInfo/getUserClientShopProjectList").param("pageNo", "1").param("pageSize", "1").param("filterStr", ""))
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

        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");

        RelationIds<String> relationIds = new RelationIds<String>();
        relationIds.setRelationIds(arrayList);

        String toJSONString = JSONObject.toJSONString(relationIds);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/projectInfo/getUserClientShopProjectList").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void userShopProjectType() throws Exception {

        ExtShopProjectInfoDTO extShopProjectInfoDTO = new ExtShopProjectInfoDTO();
        List<String> imageList = new ArrayList<>();
        imageList.add("https://mx-beauty.oss-cn-beijing.aliyuncs.com/timg.jpg?Expires=1526640873&OSSAccessKeyId=TMP.AQFGtLjtekwFvWligMxLOOBWqNvlmeSb3ver52q_BjluZJM4xMg_1NnGWhSmAAAwLAIUS50EPBng4P3pNrjwAifi_WVt4-wCFH1tK76rGREOdeMlM3Ld-uWxlCz4&Signature=O7gUDNP0CNSeMb4t%2FAuQdzKajHo%3D");
        imageList.add("https://mx-beauty.oss-cn-beijing.aliyuncs.com/timg.jpg?Expires=1526640873&OSSAccessKeyId=TMP.AQFGtLjtekwFvWligMxLOOBWqNvlmeSb3ver52q_BjluZJM4xMg_1NnGWhSmAAAwLAIUS50EPBng4P3pNrjwAifi_WVt4-wCFH1tK76rGREOdeMlM3Ld-uWxlCz4&Signature=O7gUDNP0CNSeMb4t%2FAuQdzKajHo%3D");
        extShopProjectInfoDTO.setImageList(imageList);
        extShopProjectInfoDTO.setProjectName("足疗项目");
        extShopProjectInfoDTO.setProjectTypeOneId("1");
        extShopProjectInfoDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
        extShopProjectInfoDTO.setServiceTimes(10);
        extShopProjectInfoDTO.setProjectTypeTwoName("足疗系列");
        extShopProjectInfoDTO.setProjectTypeOneName("足疗类型");
        extShopProjectInfoDTO.setProjectTypeTwoId("2");
        extShopProjectInfoDTO.setProjectDuration(60);
        extShopProjectInfoDTO.setMarketPrice(new BigDecimal(1000));
        extShopProjectInfoDTO.setFunctionIntr("对足部很有好处");
        extShopProjectInfoDTO.setDiscountPrice(new BigDecimal(800));
        extShopProjectInfoDTO.setCardType(CardTypeEnum.HALF_YEAR_CARD.getCode());
        extShopProjectInfoDTO.setUseStyle(GoodsTypeEnum.TREATMENT_CARD.getCode());
        extShopProjectInfoDTO.setOncePrice(new BigDecimal(100));
        extShopProjectInfoDTO.setVisitDateTime(12);
        extShopProjectInfoDTO.setDiscountPrice(new BigDecimal(1000));
        extShopProjectInfoDTO.setId("1");
        String toJSONString = JSONObject.toJSONString(extShopProjectInfoDTO);
        System.out.println(toJSONString);

//        MvcResult result = mvc.perform(post("/projectInfo/saveProjectInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        MvcResult result = mvc.perform(post("/projectInfo/updateProjectInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateOneLevelProjectType() throws Exception {

        ShopProjectTypeDTO shopProjectTypeDTO = new ShopProjectTypeDTO();
        shopProjectTypeDTO.setProjectTypeName("美容啊");
        shopProjectTypeDTO.setId("1");
        shopProjectTypeDTO.setStatus("1");
        String toJSONString = JSONObject.toJSONString(shopProjectTypeDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/projectType/updateOneLevelProjectType").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 保存用户套卡信息
     *
     * @throws Exception
     */
    @Test
    public void saveProjectGroupInfo() throws Exception {

        ExtShopProjectGroupDTO extShopProjectGroupDTO = new ExtShopProjectGroupDTO();
        extShopProjectGroupDTO.setProjectGroupName("自测套卡名称");
        extShopProjectGroupDTO.setId("95e49805840e4dc2ba5ed308a3eb944d");
        List<String> arrays = new ArrayList<>();
        arrays.add("1");
//        arrays.add("2");
//        arrays.add("3");
//        extShopProjectGroupDTO.setShopProjectInfoDTOS(arrays);
        List<String> images = new ArrayList<>();
        images.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        images.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        images.add("https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E5%A5%97%E5%8D%A1.png");
        extShopProjectGroupDTO.setImages(images);
        extShopProjectGroupDTO.setMarketPrice(new BigDecimal(1000));
        extShopProjectGroupDTO.setDiscountPrice(new BigDecimal(900));
        extShopProjectGroupDTO.setEffectiveDate("2018-05-10");
        //expirationDate 不限制传1
        extShopProjectGroupDTO.setExpirationDate("2018-05-10");
        extShopProjectGroupDTO.setDetail("套卡价格很便宜哦~");


        String toJSONString = JSONObject.toJSONString(extShopProjectGroupDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/projectGroupInfo/updateProjectGroupInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果
//        MvcResult result = mvc.perform(post("/projectGroupInfo/saveProjectGroupInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void updateTwoLevelProjectType() throws Exception {

        ShopProjectTypeDTO shopProjectTypeDTO = new ShopProjectTypeDTO();
        shopProjectTypeDTO.setProjectTypeName("美容啊");
        shopProjectTypeDTO.setId("1");
        shopProjectTypeDTO.setStatus("1");
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.andList(shopProjectTypeDTO);
        String toJSONString = JSONObject.toJSONString(requestDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/projectType/updateTwoLevelProjectType").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }


    /**
     * 查询用户套卡下的子卡的详细信息
     */
    @Test
    public void getShopUserProjectGroupRelRelationInfo() throws Exception {

        MvcResult result = mvc.perform(get("/projectInfo/getShopUserProjectGroupRelRelationInfo").param("shopUserProjectGroupRelRelationId", "1"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }


}
