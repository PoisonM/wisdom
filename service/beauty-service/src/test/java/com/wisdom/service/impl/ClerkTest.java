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

}
