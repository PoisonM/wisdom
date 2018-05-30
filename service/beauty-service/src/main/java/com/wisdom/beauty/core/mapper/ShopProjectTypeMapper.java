package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.dto.ShopProjectTypeCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopProjectTypeMapper extends BaseDao<ShopProjectTypeDTO, ShopProjectTypeCriteria, String> {
}