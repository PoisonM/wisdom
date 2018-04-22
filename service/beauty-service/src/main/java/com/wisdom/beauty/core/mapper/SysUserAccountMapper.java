package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.SysUserAccount;
import com.wisdom.beauty.api.dto.SysUserAccountCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysUserAccountMapper extends BaseDao<SysUserAccount, SysUserAccountCriteria, String> {
}