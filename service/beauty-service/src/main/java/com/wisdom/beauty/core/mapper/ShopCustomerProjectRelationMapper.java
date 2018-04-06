package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCustomerProjectRelationMapper extends BaseDao<ShopCustomerProjectRelationDTO, ShopCustomerProjectRelationCriteria, String> {
}