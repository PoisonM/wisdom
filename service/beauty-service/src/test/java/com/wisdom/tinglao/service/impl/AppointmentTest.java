package com.wisdom.tinglao.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.service.AppointmentService;
import com.wisdom.common.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 赵得良 on 21/09/2016.
 */
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@WebAppConfiguration
public class AppointmentTest {

    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Resource
    private AppointmentService appointmentService;

    @Test
    public void initOrderShouldReturnInitializedOrder() throws Exception {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setAppointEndTime(new Date());
        shopAppointServiceDTO.setId("24234");
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
}
