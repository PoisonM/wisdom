package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopRemindSettingCriteria;
import com.wisdom.beauty.api.dto.ShopRemindSettingDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopRemindSettingMapper extends BaseDao<ShopRemindSettingDTO, ShopRemindSettingCriteria, String> {
}