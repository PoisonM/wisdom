package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@MyBatisDao
@Repository
public interface ExtShopAppointServiceMapper extends BaseDao<ShopAppointServiceDTO, ShopAppointServiceCriteria, String> {

    List<ShopAppointServiceDTO> selectShopAppointClerkInfoByCriteria(ShopAppointServiceCriteria shopAppointServiceCriteria);


    /**
     *  查询某个美容院某个时间预约个数
     *  @param  shopAppointMap 预约时间和店铺id及店员id
     *  @return  某一个时间段的数量
     *  @autur zhangchao
     * */
    Integer findNumForShopAppointByTime(HashMap  shopAppointMap);


    /**
     *  查询某个美容院下某个店员的某个时间预约用户列表
     *  @param  shopAppointMap 预约时间和店铺id及店员id
     *  @return  店员下某一个时间段的用户列表
     *  @autur zhangchao
     * */
    List<ShopAppointServiceDTO>  findUserInfoForShopAppointByTime(HashMap  shopAppointMap);
}