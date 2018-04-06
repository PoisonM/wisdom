package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCustomerRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerRechargeCardDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCustomerRechargeCardMapper extends BaseDao<ShopCustomerRechargeCardDTO, ShopCustomerRechargeCardCriteria, String> {
}