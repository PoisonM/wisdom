package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProductTypeDTO;
import com.wisdom.beauty.api.dto.ShopProductTypeCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopProductTypeMapper extends BaseDao<ShopProductTypeDTO, ShopProductTypeCriteria, String> {
}