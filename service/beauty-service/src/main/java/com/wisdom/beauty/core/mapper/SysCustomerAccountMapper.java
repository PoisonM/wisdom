package com.wisdom.beauty.core.mapper;


import com.wisdom.beauty.api.dto.SysCustomerAccountCriteria;
import com.wisdom.beauty.api.dto.SysCustomerAccountDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysCustomerAccountMapper extends BaseDao<SysCustomerAccountDTO, SysCustomerAccountCriteria, String> {
}