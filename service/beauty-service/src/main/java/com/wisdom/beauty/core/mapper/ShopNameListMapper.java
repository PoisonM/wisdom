package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopNameListCriteria;
import com.wisdom.beauty.api.dto.ShopNameListDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopNameListMapper extends BaseDao<ShopNameListDTO, ShopNameListCriteria, String> {
}