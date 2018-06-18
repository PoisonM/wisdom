package com.wisdom.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopClerkScheduleDTO;
import com.wisdom.beauty.api.requestDto.*;
import com.wisdom.beauty.api.responseDto.ShopProductInfoCheckResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopCheckRecordMapper;
import com.wisdom.beauty.core.mapper.ExtShopStockNumberMapper;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.SpringUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2018/5/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class StockTest  {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ShopStockService shopStockService;
    @Autowired
    private ExtShopStockNumberMapper extShopStockNumberMapper;

    @Autowired
    private ExtShopUserConsumeRecordMapper extShopUserConsumeRecordMapper;
    @Autowired
    private ShopCheckService shopCheckService;

    @Autowired
    private ExtShopCheckRecordMapper extShopCheckRecordMapper;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }


    @Test
   public void  testAadd() throws Exception {


        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockRequestDTO = new ShopStockRequestDTO();

        shopStockRequestDTO.setShopStoreId("18610414171");
        shopStockRequestDTO.setStockStyle("0");//入库
        shopStockRequestDTO.setShopProcId("100");
        shopStockRequestDTO.setFlowNo("flowNo18610414171");
        shopStockRequestDTO.setDetail("入库的备注");
        shopStockRequestDTO.setStockNumber(10);
        shopStockRequestDTO.setProductDate(new Date());
        shopStockRequestDTO.setStockPrice(new BigDecimal("88.88"));

        ShopStockRequestDTO shopStockRequestDTO2 = new ShopStockRequestDTO();

        shopStockRequestDTO2.setShopStoreId("18610414171");
        shopStockRequestDTO2.setStockStyle("0");//入库
        shopStockRequestDTO2.setShopProcId("101");
        shopStockRequestDTO2.setFlowNo("flowNo18610414171-copy");
        shopStockRequestDTO2.setDetail("入库的备注-copy");
        shopStockRequestDTO2.setStockNumber(33);
        shopStockRequestDTO2.setProductDate(new Date());
        shopStockRequestDTO2.setStockPrice(new BigDecimal("33.88"));
        //shopStockRequestDTO.setReceiver("张欢");
        //如果是入库则不需要出这个
        //shopStockRequestDTO.setStockType("0");
       // shopStockRequestDTO.setStockStyle("0");

        list.add(shopStockRequestDTO);
        list.add(shopStockRequestDTO2);

        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String

        MvcResult result = mvc.perform(post("/stock/addStock").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    public void  testChuku() throws Exception {


        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockRequestDTO = new ShopStockRequestDTO();

        shopStockRequestDTO.setShopStoreId("18610414171");
        shopStockRequestDTO.setShopProcId("100");
        shopStockRequestDTO.setFlowNo("flowNo18610414171");
        shopStockRequestDTO.setDetail("出库的备注");
        shopStockRequestDTO.setStockNumber(11);
        shopStockRequestDTO.setProductDate(new Date());
        shopStockRequestDTO.setStockPrice(new BigDecimal("88.88"));
        shopStockRequestDTO.setReceiver("张欢");
        shopStockRequestDTO.setStockType("1");
        shopStockRequestDTO.setStockStyle("2");
        ShopStockRequestDTO shopStockRequestDTO2 = new ShopStockRequestDTO();

        shopStockRequestDTO2.setShopStoreId("18610414171");
        shopStockRequestDTO2.setShopProcId("101");
        shopStockRequestDTO2.setFlowNo("flowNo18610414171-copy");
        shopStockRequestDTO2.setDetail("出库的备注-copy");
        shopStockRequestDTO2.setStockNumber(12);
        shopStockRequestDTO2.setProductDate(new Date());
        shopStockRequestDTO2.setStockPrice(new BigDecimal("33.88"));
        shopStockRequestDTO2.setReceiver("张欢");
        shopStockRequestDTO2.setStockType("1");
        shopStockRequestDTO2.setStockStyle("2");

        list.add(shopStockRequestDTO);
        list.add(shopStockRequestDTO2);

        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String

        MvcResult result = mvc.perform(post("/stock/addStock").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    public  void test(){
        /*String json =  "[{\"id\":\"133223\",\"sysBossCode\":\"20\"},{\"id\":\"3232\",\"sysBossCode\":\"20\"}]";
        shopStockService.insertShopStockDTO(json);*/
        List<ShopStockRequestDTO> list = new ArrayList<>();
        ShopStockRequestDTO shopStockDTO = new ShopStockRequestDTO();
        shopStockDTO.setShopBossId("11");
        shopStockDTO.setShopStoreId("123");
        shopStockDTO.setShopStockRecordId("3333");
        shopStockDTO.setStockStatus("0");

        shopStockDTO.setShopProcId("00");
        shopStockDTO.setFlowNo("999999999");
        shopStockDTO.setDetail("尹朝阳八佰伴");
        shopStockDTO.setStockNumber(99);
        //shopStockDTO.setProductDate(new Date());
        shopStockDTO.setStockPrice(new BigDecimal("44"));
        list.add(shopStockDTO);


        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String
        shopStockService.insertShopStockDTO(toJSONString);
    }
    @Test
    public void  testUpdateorsaveStockNumber() throws Exception {
        ShopStockNumberDTO shopStockNumberDTO=new ShopStockNumberDTO();
        shopStockNumberDTO.setStockNumber(7587854);
        shopStockNumberDTO.setShopProcId("7878");
        shopStockNumberDTO.setShopStoreId("651742081");
        ShopStockNumberDTO shopStockNumberDTO2=new ShopStockNumberDTO();
        shopStockNumberDTO2.setStockNumber(787878);
        shopStockNumberDTO2.setShopProcId("6");
        shopStockNumberDTO2.setShopStoreId("98548");
        List<ShopStockNumberDTO> list=new ArrayList<>();
        list.add(shopStockNumberDTO);
        list.add(shopStockNumberDTO2);
        extShopStockNumberMapper.updateBatchShopStockNumberCondition(list);

  /*      JSONArray json = JSONArray.fromObject(list);
        String shopStockNumberDTOs = json.toString();//把json转换为String
       // extShopStockNumberMapper.saveBatchShopStockNumber(list);
        MvcResult result = mvc.perform(post("/stock/products").contentType(MediaType.APPLICATION_JSON).content(shopStockNumberDTOs))
                .andExpect(status().isOk())// 模拟向testRest发送get请求
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());*/
    }

    @Test
    public  void  tesst(){
        ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
        ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
        // 设置查询条件
        criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
        criteria.andSysClerkIdEqualTo("11");
        Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
    }
    @Test
    public void testRedis(){
        //JedisUtils.setObject("zhtest3", new Date(), 0);
        //Object object=JedisUtils.getObject("zhtest3");
        String DATE1="1995-11-12 15:21:00";
        String DATE2="1996-01-01 15:21:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");

            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
            } else {

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public  void  pandian() throws Exception {
        ShopCheckRecordDTO shopStockNumberDTO=new ShopCheckRecordDTO();
        shopStockNumberDTO.setProductTypeOneId("23");
        shopStockNumberDTO.setProductTypeOneName("品牌名字");
        shopStockNumberDTO.setStockNumber(111111);
        shopStockNumberDTO.setActualStockNumber(55555);
        shopStockNumberDTO.setShopProcId("66");
        shopStockNumberDTO.setShopProcName("产品名字");
        shopStockNumberDTO.setShopStoreId("8989876665f5");
        ShopCheckRecordDTO shopStockNumberDTO2=new ShopCheckRecordDTO();
        shopStockNumberDTO2.setProductTypeOneId("2322");
        shopStockNumberDTO2.setProductTypeOneName("品牌名字");
        shopStockNumberDTO2.setStockNumber(111111222);
        shopStockNumberDTO2.setActualStockNumber(55555222);
        shopStockNumberDTO2.setShopProcId("77");
        shopStockNumberDTO2.setShopProcName("产品名字222");
        shopStockNumberDTO2.setShopStoreId("8989876665f5222");
        List<ShopCheckRecordDTO> list=new ArrayList<>();
        list.add(shopStockNumberDTO);
        list.add(shopStockNumberDTO2);
        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String
        MvcResult result = mvc.perform(post("/stock/checkProduct").contentType(MediaType.APPLICATION_JSON).content(toJSONString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());

    }
    @Test
    public  void  pingcang(){
        ShopClosePositionRequestDTO shopClosePositionRequestDTO=new ShopClosePositionRequestDTO();
        shopClosePositionRequestDTO.setShopProcId("6");
        shopClosePositionRequestDTO.setShopStoreId("651742081");
        shopClosePositionRequestDTO.setActualStockNumber(8585);
        shopClosePositionRequestDTO.setOriginalFlowNo("d");
        shopClosePositionRequestDTO.setShopCheckRecorId("222");
        shopCheckService.doClosePosition(shopClosePositionRequestDTO);
    }
    @Test
    public  void  testExtShopCheckRecordMapper(){
        List<ShopCheckRecordDTO> list=new ArrayList<>();
        ShopCheckRecordDTO shopCheckRecordDTO=new ShopCheckRecordDTO();
        ShopCheckRecordDTO shopCheckRecordDTO2=new ShopCheckRecordDTO();
        shopCheckRecordDTO.setId("0909");
        shopCheckRecordDTO2.setId("06565909");
        shopCheckRecordDTO.setProductTypeOneName("06565909");
        shopCheckRecordDTO2.setProductTypeOneName("06565909");
        list.add(shopCheckRecordDTO2);
        list.add(shopCheckRecordDTO);
        extShopCheckRecordMapper.insertBatchCheckRecord(list);
    }
    @Test
    public  void  testcheckproduct(){
        String shopStoreId="651742081";
        List<String> products=new ArrayList<>();

        products.add("3");
        products.add("5");
        products.add("6");
        List<ShopProductInfoCheckResponseDTO> list = shopCheckService.getProductsCheckLit(shopStoreId,products);
        JSONArray json = JSONArray.fromObject(list);
        String toJSONString = json.toString();//把json转换为String
        System.out.print(list);
    }
    @Test
    public  void  testSetStorekeeper() throws Exception {
         String[] storeManagerIdsq={"1","2"};
         String[] storeManagerNamesq={"TEST1","TEST2"};
         String id="1";
        SetStorekeeperRequestDTO setStorekeeperRequestDTO=new SetStorekeeperRequestDTO();
        setStorekeeperRequestDTO.setShopStoreId(id);
        setStorekeeperRequestDTO.setStoreManagerIds(storeManagerIdsq);
        setStorekeeperRequestDTO.setStoreManagerNames(storeManagerNamesq);
        String shopClosePositionReques= JSONObject.toJSONString(setStorekeeperRequestDTO);
        /*对象转json字符串
        ShopClosePositionRequestDTO shopClosePositionRequestDTO=new ShopClosePositionRequestDTO();
        shopClosePositionRequestDTO.setShopCheckRecorId("1");
        shopClosePositionRequestDTO.setStockNumber(33);
        ShopClosePositionRequestDTO shopClosePositionRequestDTO2=new ShopClosePositionRequestDTO();
        shopClosePositionRequestDTO2.setShopCheckRecorId("2");
        shopClosePositionRequestDTO2.setStockNumber(33);
        String shopClosePositionReques= JSONObject.toJSONString(shopClosePositionRequestDTO);
        String shopClosePositionReques2= JSONObject.toJSONString(shopClosePositionRequestDTO2);*/
        MvcResult result = mvc.perform(post("/stock/setStorekeeper").contentType(MediaType.APPLICATION_JSON).content(shopClosePositionReques))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
    }
    //测试跳转产品页面
    @Test
    public  void  testtiaozhuan() throws Exception {
        ShopStockRecordRequestDTO shopStockRecordRequestDTO=new ShopStockRecordRequestDTO();
        shopStockRecordRequestDTO.setShopStoreId("11");
        shopStockRecordRequestDTO.setStockStyle("5");
        shopStockRecordRequestDTO.setPageSize(8);
        String shopClosePositionReques= JSONObject.toJSONString(shopStockRecordRequestDTO);

        MvcResult result = mvc.perform(post("/stock/shopStockRecordList").contentType(MediaType.APPLICATION_JSON).content(shopClosePositionReques))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();// 返回执行请求的结果


        System.out.println(result.getResponse().getContentAsString());
    }
}
