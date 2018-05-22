package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.dto.ShopClerkScheduleCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopClerkScheduleMapper extends BaseDao<ShopClerkScheduleDTO, ShopClerkScheduleCriteria, String> {
}