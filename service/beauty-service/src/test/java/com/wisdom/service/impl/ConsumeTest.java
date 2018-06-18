package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.extDto.ShopConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.common.dto.account.PageParamVoDTO;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ConsumeTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;
    @Autowired
    private ExtShopUserConsumeRecordMapper extShopUserConsumeRecordMapper;

    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

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
    public void getUserRechargeSumAmount() throws Exception {

        MvcResult result = mvc.perform(get("/consume/6c5c51c2ce3b43aa819030b62b756814"))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }
    /**
     * 用户划卡
     *
     * @throws Exception
     */
    @Test
    public void consumeCourseCard() throws Exception {
        List<ShopUserConsumeDTO> shopUserConsumeDTO = new ArrayList<>();
        ShopUserConsumeDTO consumeDTO = new ShopUserConsumeDTO();
        consumeDTO.setClerkId("1");
        consumeDTO.setConsumeId("6a06eb1c040447ea8c33617f0111468b");
        consumeDTO.setConsumePrice(new BigDecimal(100));
        consumeDTO.setConsumeNum(12);
        shopUserConsumeDTO.add(consumeDTO);

        String toJSONString = JSONObject.toJSONString(shopUserConsumeDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/consumes/consumeCourseCard").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 用户消费套卡
     *
     * @throws Exception
     */
    @Test
    public void consumesDaughterCard() throws Exception {
        ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO = new ShopConsumeDTO<List<ShopUserConsumeDTO>>();

        List<ShopUserConsumeDTO> arrayList = new ArrayList<>();
        ShopUserConsumeDTO consumeDTO = new ShopUserConsumeDTO();
        consumeDTO.setClerkId("1");
        consumeDTO.setConsumeId("5b080f1a39634d4eb3b9bc82130402e5");
        consumeDTO.setConsumePrice(new BigDecimal(100));
        consumeDTO.setConsumeNum(12);
        consumeDTO.setSysUserId("110");
        arrayList.add(consumeDTO);
        shopUserConsumeDTO.setShopUserConsumeDTO(arrayList);



        String toJSONString = JSONObject.toJSONString(shopUserConsumeDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/consumes/consumesDaughterCard").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 用户消费产品
     *
     * @throws Exception
     */
    @Test
    public void consumesUserProduct() throws Exception {
        List<ShopUserConsumeDTO> shopUserConsumeDTO = new ArrayList<>();
        ShopUserConsumeDTO consumeDTO = new ShopUserConsumeDTO();
        consumeDTO.setClerkId("1");
        consumeDTO.setConsumeId("1");
        consumeDTO.setConsumePrice(new BigDecimal(100));
        consumeDTO.setConsumeNum(12);
        consumeDTO.setSysUserId("110");
        shopUserConsumeDTO.add(consumeDTO);

        String toJSONString = JSONObject.toJSONString(shopUserConsumeDTO);

        System.out.println(toJSONString);

        MvcResult result = mvc.perform(post("/consumes/consumesUserProduct").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果

        System.out.println(result.getResponse().getContentAsString());
    }

    /**
     * 用户消费单次卡
     *
     * @throws Exception
     */
    @Test
    public void userOneCardOperation() throws Exception {

        List<ShopUserConsumeRecordDTO> extShopUserConsumeRecordDTOS = new ArrayList<>();
        String uuid = IdGen.uuid();

        //构建用户消费信息
        ShopUserConsumeRecordDTO consumeRecordDTO = new ShopUserConsumeRecordDTO();
        consumeRecordDTO.setId(uuid);
        consumeRecordDTO.setSysUserId(uuid);
        consumeRecordDTO.setSysUserName("安迪苏瓦");
        consumeRecordDTO.setSysShopName("汉方美业");
        consumeRecordDTO.setSysClerkName("张小芳");
        consumeRecordDTO.setSysBossCode(uuid);
        consumeRecordDTO.setPrice(new BigDecimal(5728));
        consumeRecordDTO.setSignUrl("www.baidu.com");
        consumeRecordDTO.setPayType(PayTypeEnum.ALI_PAY.getCode());
        consumeRecordDTO.setDetail("这是一个备注");
        consumeRecordDTO.setConsumeNumber(2);
        consumeRecordDTO.setDiscount(0.75f);
        consumeRecordDTO.setGoodsType(GoodsTypeEnum.TIME_CARD.getCode());
        consumeRecordDTO.setSysShopId(uuid);
        consumeRecordDTO.setSysClerkId(uuid);

        //构建用户与项目的关系
        ShopUserProjectRelationDTO projectRelationDTO = new ShopUserProjectRelationDTO();
        projectRelationDTO.setSysShopProjectSurplusTimes(10);
        projectRelationDTO.setSysShopProjectName("黄金换肤");
        //消费确认要提交的初始次数
        projectRelationDTO.setSysShopProjectInitTimes(10);
        projectRelationDTO.setSysShopProjectInitAmount(new BigDecimal(1411));
        projectRelationDTO.setSysClerkId(uuid);
        projectRelationDTO.setSysShopProjectId(uuid);
        projectRelationDTO.setSysShopName(consumeRecordDTO.getSysShopName());
        projectRelationDTO.setIsSend("1");//1 赠送 0不赠送
        projectRelationDTO.setSysShopProjectInitAmount(new BigDecimal(5728));
        projectRelationDTO.setSysShopProjectSurplusAmount(new BigDecimal(5728));
       // projectRelationDTO.setInvalidDays(3);
//        consumeRecordDTO.setShopUserProjectRelationDTO(projectRelationDTO);

        extShopUserConsumeRecordDTOS.add(consumeRecordDTO);

    }

    /**
     * 查询某个用户的充值卡信息
     */
    @Test
    public void getUserRechargeInfo() throws Exception {

    }

     @Test
    public  void  testUserConsume(){
             PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO=new PageParamVoDTO<>();
             pageParamVoDTO.setStartTime("2018-04-10 00:00:00");
             pageParamVoDTO.setEndTime("2018-04-10 23:59:59");
         UserConsumeRequestDTO shopUserConsumeRecordDTO=new UserConsumeRequestDTO();
             shopUserConsumeRecordDTO.setSysShopId("11");
             shopUserConsumeRecordDTO.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
             pageParamVoDTO.setRequestData(shopUserConsumeRecordDTO);
             List<ExpenditureAndIncomeResponseDTO>  s=shopStatisticsAnalysisService.getExpenditureAndIncomeList(pageParamVoDTO);
         }
    @Test
    public  void   te(){
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i <7; i++) {
            pastDaysList.add(ve(i));
        }
        System.out.print(pastDaysList);
    }
    public  String ve(int past){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse("2016-03-01");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-past);
        Date voucherDate = calendar.getTime();
        return dateFormat.format(voucherDate);
    }
    @Test
    public  void  ee() throws java.text.ParseException {
        // 获取当月的天数（需完善）
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 定义当前期间的1号的date对象
        Date date = null;
        date = dateFormat.parse("201602");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-7);//日期倒数一日,既得到本月最后一天
        Date voucherDate = calendar.getTime();
        System.out.println(dateFormat.format(voucherDate));
    }
   @Test
    public  void  testdd(){
       shopUerConsumeRecordService
               .getShopCustomerConsumeRecord("20180607144058285");
   }
}
