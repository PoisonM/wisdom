package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCustomerProjectGroupRelRelationCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerProjectGroupRelRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopCustomerProjectGroupRelRelationMapper extends BaseDao<ShopCustomerProjectGroupRelRelationDTO, ShopCustomerProjectGroupRelRelationCriteria, String> {
}