package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserProductRelationMapper extends BaseDao<ShopUserProductRelationDTO, ShopUserProductRelationCriteria, String> {
}