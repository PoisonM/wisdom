package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockRecordCriteria;
import com.wisdom.beauty.api.dto.ShopStockRecordDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopStockRecordMapper extends BaseDao<ShopStockRecordDTO, ShopStockRecordCriteria, String> {
}