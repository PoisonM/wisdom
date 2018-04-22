package com.wisdom.user.mapper;

import com.wisdom.common.dto.customer.SysBoss;
import com.wisdom.common.dto.customer.SysBossCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysBossMapper extends BaseDao<SysBoss, SysBossCriteria, String> {
}