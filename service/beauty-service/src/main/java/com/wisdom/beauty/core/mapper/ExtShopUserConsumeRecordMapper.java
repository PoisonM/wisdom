package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@MyBatisDao
@Repository
public interface ExtShopUserConsumeRecordMapper extends ShopUserConsumeRecordMapper {
    BigDecimal selectSumPriceByCriteria(ShopUserConsumeRecordCriteria shopUserConsumeRecordCriteria);
}