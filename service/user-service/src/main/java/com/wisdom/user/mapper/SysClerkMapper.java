package com.wisdom.user.mapper;


import com.wisdom.common.dto.customer.SysClerkCriteria;
import com.wisdom.common.dto.customer.SysClerkDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface SysClerkMapper extends BaseDao<SysClerkDTO, SysClerkCriteria, String> {
}