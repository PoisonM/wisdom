package com.wisdom.business.mapper.transaction;

import com.wisdom.common.dto.transaction.PromotionTransactionRelation;
import com.wisdom.common.dto.transaction.PromotionTransactionRelationCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface PromotionTransactionRelationMapper extends BaseDao<PromotionTransactionRelation, PromotionTransactionRelationCriteria, String> {



    @Override
    public int insertSelective(PromotionTransactionRelation promotionTransactionRelationromotionTransactionRelation);


    PromotionTransactionRelation getIsImportLevel(@Param("transactionId") String transactionId);

    int isExistence(@Param("transactionId") String transactionId);
}