package com.wisdom.user.mapper.extMapper;

import com.wisdom.common.dto.user.SysClerkCriteria;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ExtSysClerkMapper extends BaseDao<SysClerkDTO, SysClerkCriteria, String> {

}