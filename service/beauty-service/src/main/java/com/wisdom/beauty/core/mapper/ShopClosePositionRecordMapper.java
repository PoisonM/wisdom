package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopClosePositionRecordDTO;
import com.wisdom.beauty.api.dto.ShopClosePositionRecordCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopClosePositionRecordMapper extends BaseDao<ShopClosePositionRecordDTO, ShopClosePositionRecordCriteria, String> {
}