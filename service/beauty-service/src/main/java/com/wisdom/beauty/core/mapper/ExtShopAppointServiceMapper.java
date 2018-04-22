package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopAppointService;
import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface ExtShopAppointServiceMapper extends BaseDao<ShopAppointService, ShopAppointServiceCriteria, String> {

    List<ShopAppointService> selectShopAppointClerkInfoByCriteria(ShopAppointServiceCriteria shopAppointServiceCriteria);
}