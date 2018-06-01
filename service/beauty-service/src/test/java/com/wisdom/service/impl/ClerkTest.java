package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.beauty.api.responseDto.ShopClerkWorkRecordResponseDTO;
import com.wisdom.beauty.core.service.ShopClerkWorkService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.SpringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuan on 2018/5/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ClerkTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ShopClerkWorkService shopClerkWorkService;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    @Test
    public void  testgetShopCustomerConsumeRecordList() {
        PageParamVoDTO<ShopClerkWorkRecordDTO> pageParamVoDTO =new PageParamVoDTO();
        ShopClerkWorkRecordDTO shopClerkWorkRecordDTO=new ShopClerkWorkRecordDTO();
        shopClerkWorkRecordDTO.setSysShopId("11");
        shopClerkWorkRecordDTO.setSysClerkId("123");
        pageParamVoDTO.setRequestData(shopClerkWorkRecordDTO);
        //List<ShopClerkWorkRecordResponseDTO> w=shopClerkWorkService.getShopCustomerConsumeRecordList(pageParamVoDTO);
    }
    @Test
    public  void  testSave(){
        List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs=new ArrayList<>();
        ShopClerkWorkRecordDTO shopClerkWorkRecordDTO=new ShopClerkWorkRecordDTO();
        shopClerkWorkRecordDTO.setGoodsType("0");
        shopClerkWorkRecordDTO.setGoodsType("1");
        shopClerkWorkRecordDTO.setFlowNo("99999999");
        shopClerkWorkRecordDTO.setSysClerkId("9898");
        shopClerkWorkRecordDTO.setSysShopId("987788");
        ShopClerkWorkRecordDTO shopClerkWorkRecordDTO2=new ShopClerkWorkRecordDTO();
        shopClerkWorkRecordDTO2.setGoodsType("0");
        shopClerkWorkRecordDTO2.setGoodsType("1");
        shopClerkWorkRecordDTO2.setFlowNo("77777777");
        shopClerkWorkRecordDTO2.setSysClerkId("9898");
        shopClerkWorkRecordDTO2.setSysShopId("987788");
        ShopClerkWorkRecordDTO shopClerkWorkRecordDTO3=new ShopClerkWorkRecordDTO();
        shopClerkWorkRecordDTO3.setGoodsType("0");
        shopClerkWorkRecordDTO3.setGoodsType("1");
        shopClerkWorkRecordDTO3.setFlowNo("66666666666");
        shopClerkWorkRecordDTO3.setSysClerkId("9898");
        shopClerkWorkRecordDTO3.setSysShopId("987788");
        shopClerkWorkRecordDTOs.add(shopClerkWorkRecordDTO);
        shopClerkWorkRecordDTOs.add(shopClerkWorkRecordDTO2);
        shopClerkWorkRecordDTOs.add(shopClerkWorkRecordDTO3);
        shopClerkWorkService.saveClerkWorkRecord(shopClerkWorkRecordDTOs);
    }
}
