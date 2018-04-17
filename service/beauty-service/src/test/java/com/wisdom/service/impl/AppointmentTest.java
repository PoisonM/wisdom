package com.wisdom.service.impl;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
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

        ShopAppointServiceDTO shopAppointServiceDTO = getShopAppointServiceDTO();
        redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);
        System.out.println("测试完毕");
    }

    @Test
    public void getAppointmentService() {
        redisUtils.getAppointmentIdByShopClerk("48940be00e634bae86006c4519263906_8f4bbff4c1404136a521350c08e31483","20180000000000","20190000000000");
    }

    @Test
    public void getShopAppointInfoFromRedis() {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setId("6951d1561c5b4cae84fd72283e52a081");
        ShopAppointServiceDTO infoFromRedis = redisUtils.getShopAppointInfoFromRedis(shopAppointServiceDTO.getId());
        System.out.println("infoFromRedis");
    }

    private ShopAppointServiceDTO getShopAppointServiceDTO() {
        ShopAppointServiceDTO shopAppointServiceDTO = new ShopAppointServiceDTO();
        shopAppointServiceDTO.setId("id_8");
        shopAppointServiceDTO.setAppointEndTime(DateUtils.StrToDate("2018-04-08 11:00:00", "datetime"));
        shopAppointServiceDTO.setAppointPeriod(60);
        shopAppointServiceDTO.setAppointStartTime(new Date());
        shopAppointServiceDTO.setCreateBy(IdGen.uuid());
        shopAppointServiceDTO.setCreateDate(DateUtils.StrToDate("2018-04-08 12:00:00", "datetime"));
        shopAppointServiceDTO.setDetail("测试");
        shopAppointServiceDTO.setShopProjectId(IdGen.uuid());
        shopAppointServiceDTO.setShopProjectName("面部保洁");
        shopAppointServiceDTO.setStatus("0");
        shopAppointServiceDTO.setSysBossId(IdGen.uuid());
        shopAppointServiceDTO.setSysClerkId("1");
        shopAppointServiceDTO.setSysUserId(IdGen.uuid());
        shopAppointServiceDTO.setSysClerkName("王五");
        shopAppointServiceDTO.setUpdateUser(IdGen.uuid());
        shopAppointServiceDTO.setSysShopName("汉方美容院");
        shopAppointServiceDTO.setSysUserName("张欢");
        shopAppointServiceDTO.setSysUserPhone("181812839893");
        shopAppointServiceDTO.setSysShopId("3");
        return shopAppointServiceDTO;
    }


}
