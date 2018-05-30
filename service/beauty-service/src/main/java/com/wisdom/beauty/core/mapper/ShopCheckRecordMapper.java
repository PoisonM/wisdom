package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.beauty.api.dto.ShopCheckRecordCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCheckRecordMapper extends BaseDao<ShopCheckRecordDTO, ShopCheckRecordCriteria, String> {
}