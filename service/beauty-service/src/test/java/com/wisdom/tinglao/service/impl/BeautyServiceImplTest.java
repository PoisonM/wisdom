package com.wisdom.tinglao.service.impl;

import com.wisdom.beauty.BeautyServiceApplication;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by 赵得良 on 21/09/2016.
 */
//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeautyServiceApplication.class)
@WebAppConfiguration
public class BeautyServiceImplTest {

    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Resource
    private ShopAppointmentService appointmentService;
    @Autowired
    private ShopRechargeCardService shopRechargeCardService;

    @Test
    public void initOrderShouldReturnInitializedOrder() throws Exception {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setAppointEndTime(new Date());
        shopAppointServiceDTO.setId("24234");
        shopAppointServiceMapper.insert(shopAppointServiceDTO);

    }
    @Test
    public  void  te(){
        String a="33";
        String b="11";
        String c="1";
       Float s= shopRechargeCardService.getDiscount(a,b,c);
        System.out.print(s);
    }

}
