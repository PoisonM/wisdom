package com.wisdom.bussiness.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.business.BusinessServiceApplication;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 赵得良 on 21/09/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ArchivesTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }



    /**
     * 修改档案信息
     *
     * @throws Exception
     */
    @Test
    public void testUpdateArchiveInfo() throws Exception {


     /*   MvcResult result = mvc.perform(post("/account/findShopKeeperOrderS")).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        */

        PageParamVoDTO<SpecialShopBusinessOrderDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setPageNo(1);
        pageParamVoDTO.setPageSize(2);

        String toJSONString = JSONObject.toJSONString(pageParamVoDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/account/findShopKeeperOrderS").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();

        System.out.print(result);


    }


}
