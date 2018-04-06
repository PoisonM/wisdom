package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.SysClerkFlowAccountCriteria;
import com.wisdom.beauty.api.dto.SysClerkFlowAccountDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;


@MyBatisDao
@Repository
public interface SysClerkFlowAccountMapper extends BaseDao<SysClerkFlowAccountDTO, SysClerkFlowAccountCriteria, String> {
}