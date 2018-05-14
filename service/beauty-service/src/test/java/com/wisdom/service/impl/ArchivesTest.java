package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.common.util.IdGen;
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
public class ArchivesTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 删除档案信息
     *
     * @throws Exception
     */
    @Test
    public void testDeleteArchiveInfo() throws Exception {

        MvcResult result = mvc.perform(get("/archives/detail/8ed0e6aa6ed246d8b788c90492364f6b"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 保存档案信息
     *
     * @throws Exception
     */
    @Test
    public void testSaveArchiveInfo() throws Exception {

        ShopUserArchivesDTO shopUserArchivesDTO = getShopUserArchivesDTO();

        String toJSONString = JSONObject.toJSONString(shopUserArchivesDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/archives/saveArchiveInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());

    }

    /**
     * 修改档案信息
     *
     * @throws Exception
     */
    @Test
    public void testUpdateArchiveInfo() throws Exception {

        ShopUserArchivesDTO shopUserArchivesDTO = getShopUserArchivesDTO();

        String toJSONString = JSONObject.toJSONString(shopUserArchivesDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/archives/updateArchiveInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());

    }

    /**
     * 构建getShopUserArchivesDTO
     *
     * @return
     */
    private ShopUserArchivesDTO getShopUserArchivesDTO() {
        String uuid = IdGen.uuid();
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setId("02bb6e2d8d0e44c5abd8690266bdc1a8");
        shopUserArchivesDTO.setPhone("18810142926");
        shopUserArchivesDTO.setSysShopId(uuid);
        shopUserArchivesDTO.setAge(12);
        shopUserArchivesDTO.setBirthday("1990-02-17");
        shopUserArchivesDTO.setBloodType("A");
        shopUserArchivesDTO.setChannel("美团网");
        shopUserArchivesDTO.setConstellation("狮子座");
        shopUserArchivesDTO.setDetail("这是个好用户");
        shopUserArchivesDTO.setWeight(41.1f);
        shopUserArchivesDTO.setSysShopName("汉方美容院");
        shopUserArchivesDTO.setSysClerkName("小王");
        shopUserArchivesDTO.setSysClerkId(uuid);
        shopUserArchivesDTO.setSex("男");
        shopUserArchivesDTO.setImageRul("www.baidu.com");
        shopUserArchivesDTO.setHeight(179f);
        return shopUserArchivesDTO;
    }
//

    @Test
    public void testFindNumForShopByTimeControl() throws Exception {

        MvcResult result = mvc.perform(get("/appointmentInfo/findNumForShopByTimeControl").param("sysShopId", "3").param("sysClerkId","1").param("appointStartTimeS","2018-02-22").param("appointStartTimeE","2018-04-20"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testFindUserInfoForShopByTimeControl() throws Exception {

        MvcResult result = mvc.perform(get("/appointmentInfo/findUserInfoForShopByTimeControl").param("pn","1").param("sysShopId", "3").param("sysClerkId","1").param("appointStartTimeS","2018-02-22").param("appointStartTimeE","2018-04-20"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }
}
