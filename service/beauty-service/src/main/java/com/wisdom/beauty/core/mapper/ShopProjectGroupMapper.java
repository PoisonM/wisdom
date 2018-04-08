package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProjectGroupCriteria;
import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopProjectGroupMapper extends BaseDao<ShopProjectGroupDTO, ShopProjectGroupCriteria, String> {
}