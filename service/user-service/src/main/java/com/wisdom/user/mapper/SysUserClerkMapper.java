package com.wisdom.user.mapper;

import com.wisdom.common.dto.customer.SysUserClerkDTO;
import com.wisdom.common.dto.customer.SysUserClerkCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysUserClerkMapper extends BaseDao<SysUserClerkDTO, SysUserClerkCriteria, String> {
}