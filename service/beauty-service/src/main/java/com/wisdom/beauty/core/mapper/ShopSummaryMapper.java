package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopSummaryCriteria;
import com.wisdom.beauty.api.dto.ShopSummaryDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopSummaryMapper extends BaseDao<ShopSummaryDTO, ShopSummaryCriteria, String> {
}