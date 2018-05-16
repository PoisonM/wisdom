package com.wisdom.business.mapper.transaction;

import com.wisdom.common.dto.transaction.PromotionTransactionRelation;
import com.wisdom.common.dto.transaction.PromotionTransactionRelationCriteria;
import com.wisdom.common.entity.BaseDao;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionTransactionRelationMapper extends BaseDao<PromotionTransactionRelation, PromotionTransactionRelationCriteria, String> {



    @Override
    public int insertSelective(PromotionTransactionRelation promotionTransactionRelationromotionTransactionRelation);


    PromotionTransactionRelation getIsImportLevel(@Param("transactionId") String transactionId);

    int isExistence(@Param("transactionId") String transactionId);
}