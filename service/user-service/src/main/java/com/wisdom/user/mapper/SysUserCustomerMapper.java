package com.wisdom.user.mapper;

import com.wisdom.common.dto.customer.SysUserCustomerDTO;
import com.wisdom.common.dto.customer.SysUserCustomerCriteria;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysUserCustomerMapper extends BaseDao<SysUserCustomerDTO, SysUserCustomerCriteria, String> {
}