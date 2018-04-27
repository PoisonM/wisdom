package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserRechargeCardMapper extends BaseDao<ShopUserRechargeCardDTO, ShopUserRechargeCardCriteria, String> {
}