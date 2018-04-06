package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCustomerConsumeRecordCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerConsumeRecordDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCustomerConsumeRecordMapper extends BaseDao<ShopCustomerConsumeRecordDTO, ShopCustomerConsumeRecordCriteria, String> {
}