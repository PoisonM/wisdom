package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.SpringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 赵得良 on 21/09/2016.
 */
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class AppointmentTest {


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Resource
    private ShopAppointmentService appointmentService;

    @Resource
    private RedisUtils redisUtils;

    @Before
    public void beforeTest(){
        ApplicationContext app = SpringApplication.run(BeautyServiceApplication.class, "");
        SpringUtil.setApplicationContext(app);
    }

    @Test
    public void initOrderShouldReturnInitializedOrder() throws Exception {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setAppointEndTime(new Date());
        shopAppointServiceDTO.setId("242dfsfdfg34");
        shopAppointServiceMapper.insert(shopAppointServiceDTO);

    }

    @Test
    public void getShopAppointClerkInfoByCriteria() throws Exception {

        ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
        extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate("2018-04-04 11:16:48","datetime"));
        extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate("2018-04-04 14:16:48","datetime"));
        extShopAppointServiceDTO.setSysShopId("sys_shop_id");
        List<ShopAppointServiceDTO> ls = appointmentService.getShopAppointClerkInfoByCriteria(extShopAppointServiceDTO);
        System.out.println(ls);
    }

    @Test
    public void saveAppointmentService() {

        String a = UUID.randomUUID().toString();
        ShopAppointServiceDTO shopAppointServiceDTO = getShopAppointServiceDTO();
        redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);
        System.out.println("测试完毕");
    }

    /**
     * 保存用户的预约信息
     *
     * @throws Exception
     */
    @Test
    public void testSaveAppointmentService() throws Exception {

        ShopAppointServiceDTO shopAppointServiceDTO = getShopAppointServiceDTO();

        String toJSONString = JSONObject.toJSONString(shopAppointServiceDTO);

        System.out.println(toJSONString);

//        MvcResult result = mvc.perform(get("/appointmentInfo/getShopClerkAppointmentInfo").param("searchDate", "2018-04-27").param("sysShopId", "11").param("sysClerkId", "cc03a01d060e4bb09e051788e8d9801b").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
//                .andExpect(status().isOk())// 模拟向testRest发送post请求
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
//                .andReturn();// 返回执行请求的结果

        MvcResult result = mvc.perform(post("/appointmentInfo/updateUserAppointInfo").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());

    }


    @Test
    public void getAppointmentService() {
        redisUtils.getAppointmentIdByShopClerk("48940be00e634bae86006c4519263906_8f4bbff4c1404136a521350c08e31483","20180000000000","20190000000000");
    }

    @Test
    public void getShopAppointInfoFromRedis() {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setId("fcdda9aa39c14f39b08e687ceccf18a4");
        ShopAppointServiceDTO infoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceDTO.getId());
        System.out.println("infoFromRedis");
    }

    @Test
    public void findAppointNumByTime(){

    }

    private ShopAppointServiceDTO getShopAppointServiceDTO() {
        ExtShopAppointServiceDTO shopAppointServiceDTO = new ExtShopAppointServiceDTO();
        shopAppointServiceDTO.setAppointPeriod(60);
        shopAppointServiceDTO.setAppointStartTimeS("2018-09-20 19:00");
        shopAppointServiceDTO.setShopProjectId(IdGen.uuid());
        shopAppointServiceDTO.setShopProjectName("面部保洁");
        shopAppointServiceDTO.setStatus("0");
        shopAppointServiceDTO.setSysClerkId("6ce974e11feb4deab74b553d3b3c5509");
        shopAppointServiceDTO.setId("a4874cd4754748788e90f4a69ba5c382");
        return shopAppointServiceDTO;
    }

//    private ShopAppointServiceDTO getShopAppointServiceDTO() {
//        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
//        shopAppointServiceDTO.setId("id_8");
//        shopAppointServiceDTO.setAppointEndTime(DateUtils.StrToDate("2018-04-08 11:00:00", "datetime"));
//        shopAppointServiceDTO.setAppointPeriod(60);
//        shopAppointServiceDTO.setAppointStartTime(new Date());
//        shopAppointServiceDTO.setCreateBy(IdGen.uuid());
//        shopAppointServiceDTO.setCreateDate(DateUtils.StrToDate("2018-04-08 12:00:00", "datetime"));
//        shopAppointServiceDTO.setDetail("测试");
//        shopAppointServiceDTO.setShopProjectId(IdGen.uuid());
//        shopAppointServiceDTO.setShopProjectName("面部保洁");
//        shopAppointServiceDTO.setStatus("0");
//        shopAppointServiceDTO.setSysBossId(IdGen.uuid());
//        shopAppointServiceDTO.setSysClerkId("1");
//        shopAppointServiceDTO.setSysUserId(IdGen.uuid());
//        shopAppointServiceDTO.setSysClerkName("王五");
//        shopAppointServiceDTO.setUpdateUser(IdGen.uuid());
//        shopAppointServiceDTO.setSysShopName("汉方美容院");
//        shopAppointServiceDTO.setSysUserName("张欢");
//        shopAppointServiceDTO.setSysUserPhone("181812839893");
//        shopAppointServiceDTO.setSysShopId("3");
//        return shopAppointServiceDTO;
//    }


    /**
     * 删除档案信息
     *
     * @throws Exception
     */
    @Test
    public void testDeleteArchiveInfo() throws Exception {

        MvcResult result = mvc.perform(get("/appointmentInfo/getAppointmentInfoById").param("shopAppointServiceId", "f83d378c34a747588570d1546b35469b"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    public static void main(String[] args) {
        System.out.println("args = [" + args + "]");
    }



}
