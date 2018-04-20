package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockCriteria;
import com.wisdom.beauty.api.dto.ShopStockDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopStockMapper extends BaseDao<ShopStockDTO, ShopStockCriteria, String> {
}