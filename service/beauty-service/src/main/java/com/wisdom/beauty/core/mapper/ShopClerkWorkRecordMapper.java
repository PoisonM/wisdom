package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.beauty.api.dto.ShopClerkWorkRecordCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopClerkWorkRecordMapper extends BaseDao<ShopClerkWorkRecordDTO, ShopClerkWorkRecordCriteria, String> {
}