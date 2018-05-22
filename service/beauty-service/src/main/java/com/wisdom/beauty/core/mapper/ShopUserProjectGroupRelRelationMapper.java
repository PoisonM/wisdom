package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserProjectGroupRelRelationMapper extends BaseDao<ShopUserProjectGroupRelRelationDTO, ShopUserProjectGroupRelRelationCriteria, String> {
}