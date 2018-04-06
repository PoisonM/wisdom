package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCustomerProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerProductRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCustomerProductRelationMapper extends BaseDao<ShopCustomerProductRelationDTO, ShopCustomerProductRelationCriteria, String> {
}