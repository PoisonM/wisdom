package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
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
public class ScheduleTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 查询某个点的排班信息
     *
     * @throws Exception
     */
    @Test
    public void getShopClerkScheduleList() throws Exception {

//        MvcResult result = mvc.perform(get("/clerkSchedule/getShopClerkScheduleList").param("searchDate", "2018-04-28").param("clerkId", "6ce974e11feb4deab74b553d3b3c5509"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        MvcResult result = mvc.perform(get("/clerkSchedule/getClerkScheduleOneDayInfo").param("searchDate", "2018-03-08"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果
//
//        MvcResult result = mvc.perform(get("/clerkSchedule/getBossShopScheduleSetting"))
//                .andExpect(status().isOk())// 模拟向testRest发送get请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 批量更新排班信息
     *
     * @throws Exception
     */
    @Test
    public void updateShopClerkScheduleList() throws Exception {

        RequestDTO<ShopScheduleSettingDTO> requestDTO = new RequestDTO<ShopScheduleSettingDTO>();
        List<ShopScheduleSettingDTO> scheduleDTOS = new ArrayList<>();

        ShopScheduleSettingDTO scheduleDTO = new ShopScheduleSettingDTO();
        scheduleDTO.setStatus("1");
        scheduleDTO.setId("320697b6007a4d76a64fac92562e5a68");
        scheduleDTO.setStartTime("11:11");
        scheduleDTO.setEndTime("14:00");
        scheduleDTOS.add(scheduleDTO);
        ShopScheduleSettingDTO scheduleDTO2 = new ShopScheduleSettingDTO();
        scheduleDTO2.setStatus("1");
        scheduleDTO2.setStartTime("13:11");
        scheduleDTO2.setEndTime("12:00");
        scheduleDTO2.setId("733baf711fa04cfe9f5f5e0f5b054ed5");
        scheduleDTOS.add(scheduleDTO2);


        requestDTO.setRequestList(scheduleDTOS);

        String toJSONString = JSONObject.toJSONString(requestDTO);
        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/clerkSchedule/updateBossShopScheduleSetting").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }
}
