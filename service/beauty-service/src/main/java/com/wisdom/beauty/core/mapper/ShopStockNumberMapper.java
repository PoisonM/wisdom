package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockNumberDTO;
import com.wisdom.beauty.api.dto.ShopStockNumberCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopStockNumberMapper extends BaseDao<ShopStockNumberDTO, ShopStockNumberCriteria, String> {
}