package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserConsumeRecordMapper extends BaseDao<ShopUserConsumeRecordDTO, ShopUserConsumeRecordCriteria, String> {
}