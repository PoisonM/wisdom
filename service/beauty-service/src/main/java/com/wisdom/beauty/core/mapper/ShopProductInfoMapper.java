package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopProductInfoCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopProductInfoMapper extends BaseDao<ShopProductInfoDTO, ShopProductInfoCriteria, String> {
}