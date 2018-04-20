package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStoreCriteria;
import com.wisdom.beauty.api.dto.ShopStoreDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopStoreMapper extends BaseDao<ShopStoreDTO, ShopStoreCriteria, String> {
}