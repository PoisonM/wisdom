package com.wisdom.beauty.core.mapper;


import com.wisdom.beauty.api.dto.ShopBossScheduleSettingRelationCriteria;
import com.wisdom.beauty.api.dto.ShopBossScheduleSettingRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ShopBossScheduleSettingRelationMapper extends BaseDao<ShopBossScheduleSettingRelationDTO, ShopBossScheduleSettingRelationCriteria, String> {
}