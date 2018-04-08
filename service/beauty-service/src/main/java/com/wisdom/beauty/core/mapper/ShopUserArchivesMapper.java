package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserArchivesCriteria;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopUserArchivesMapper extends BaseDao<ShopUserArchivesDTO, ShopUserArchivesCriteria, String> {
}