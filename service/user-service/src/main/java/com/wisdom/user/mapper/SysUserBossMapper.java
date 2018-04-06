package com.wisdom.user.mapper;

import com.wisdom.common.dto.customer.SysUserBossDTO;
import com.wisdom.common.dto.customer.SysUserBossCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysUserBossMapper extends BaseDao<SysUserBossDTO, SysUserBossCriteria, String> {
}