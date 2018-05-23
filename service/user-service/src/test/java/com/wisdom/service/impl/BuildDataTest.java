package com.wisdom.service.impl;

import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.RandomValue;
import com.wisdom.common.util.SpringUtil;
import com.wisdom.user.UserServiceApplication;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.service.ClerkInfoService;
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

/**
 * Created by 赵得良 on 21/09/2016.
 */
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class BuildDataTest {


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private SysClerkMapper sysClerkMapper;
    @Autowired
    private ClerkInfoService clerkInfoService;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    /**
     * 批量创建店员信息
     */
    @Test
    public void buildSysClerk() {

        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        for (int i = 0; i < 30; i++) {
            sysClerkDTO.setMobile(RandomValue.getTel());
            sysClerkDTO.setNickname(RandomValue.getChineseName());
            sysClerkDTO.setSysShopId("3");
            sysClerkDTO.setId(IdGen.uuid());
            sysClerkDTO.setName(RandomValue.getChineseName());
            sysClerkDTO.setSysBossCode("11");
            sysClerkDTO.setSysUserId(IdGen.uuid());
            sysClerkDTO.setSysBossName(RandomValue.getChineseName());
            sysClerkDTO.setSysShopName("汉方美容店");
            sysClerkMapper.insertSelective(sysClerkDTO);
        }

    }
    @Test
    public void SysClerk() {

        SysClerkDTO sysClerkDTO = new SysClerkDTO();
        sysClerkDTO.setName("测试名字update２");
        clerkInfoService.saveSysClerk(sysClerkDTO);
    }
}
