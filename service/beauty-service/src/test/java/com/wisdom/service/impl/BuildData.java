package com.wisdom.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.RandomValue;
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

import java.util.Date;

/**
 * Created by 赵得良 on 21/09/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class BuildData {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 批量创建预约信息
     */
    @Test
    public void buildAppointmentData() {
        String sysClerkId = "048a01346cd84935b309bfe22f076bdc";
        String sysClerkName = "融祥琛";
        for (int i = 0; i < 20; i++) {
            ShopAppointServiceDTO serviceDTO = new ShopAppointServiceDTO();
            String uuid = IdGen.uuid();
            serviceDTO.setId(uuid);
            serviceDTO.setSysUserId(uuid);
            serviceDTO.setSysUserPhone(RandomValue.getTel());
            serviceDTO.setSysShopId("11");
            serviceDTO.setCreateDate(new Date());
            serviceDTO.setSysUserName(RandomValue.getChineseName());
            serviceDTO.setShopProjectName("项目" + i + "名称");
            serviceDTO.setSysShopName("汉方美容店");
            serviceDTO.setSysClerkName(sysClerkName);
            serviceDTO.setAppointStartTime(DateUtils.StrToDate("2018-04-04 15:00:00", "datetime"));
            serviceDTO.setAppointEndTime(DateUtils.StrToDate("2018-04-04 16:00:00", "datetime"));
            serviceDTO.setSysClerkId(sysClerkId);
            shopAppointServiceMapper.insertSelective(serviceDTO);
        }

    }

}
