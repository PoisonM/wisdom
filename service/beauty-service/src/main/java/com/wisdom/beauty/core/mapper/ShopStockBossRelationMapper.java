package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockBossRelationCriteria;
import com.wisdom.beauty.api.dto.ShopStockBossRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopStockBossRelationMapper extends BaseDao<ShopStockBossRelationDTO, ShopStockBossRelationCriteria, String> {
}