package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@MyBatisDao
@Repository
public interface ExtShopUserConsumeRecordMapper extends ShopUserConsumeRecordMapper {
    BigDecimal selectSumPriceByCriteria(ShopUserConsumeRecordCriteria shopUserConsumeRecordCriteria);
    Integer selectUserConsumeNumber(ShopUserConsumeRecordCriteria shopUserConsumeRecordCriteria);
    /**
    *@Author:  zhanghuan
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/4/23 11:30
    */
    List<ExpenditureAndIncomeResponseDTO> selectPriceListByCriteria(ShopUserConsumeRecordCriteria shopUserConsumeRecordCriteria);
}