package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.common.util.IdGen;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
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
        consumeRecordDTO.setSysBossId(uuid);
        consumeRecordDTO.setPrice(new BigDecimal(5728));
        consumeRecordDTO.setSignUrl("www.baidu.com");
        consumeRecordDTO.setPayType(PayTypeEnum.ALI_PAY.getCode());
        consumeRecordDTO.setDetail("这是一个备注");
        consumeRecordDTO.setConsumeNumber(2);
        consumeRecordDTO.setDiscount(new BigDecimal("0.75"));
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
        projectRelationDTO.setInvalidDays(3);
//        consumeRecordDTO.setShopUserProjectRelationDTO(projectRelationDTO);

        extShopUserConsumeRecordDTOS.add(consumeRecordDTO);

    }

    /**
     * 查询某个用户的充值卡信息
     */
    @Test
    public void getUserRechargeInfo() throws Exception {

    }


}
