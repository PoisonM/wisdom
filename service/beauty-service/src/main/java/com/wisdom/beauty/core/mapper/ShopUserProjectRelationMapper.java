package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserProjectRelationMapper extends BaseDao<ShopUserProjectRelationDTO, ShopUserProjectRelationCriteria, String> {
}