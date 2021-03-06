package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserRelationMapper extends BaseDao<ShopUserRelationDTO, ShopUserRelationCriteria, String> {
}