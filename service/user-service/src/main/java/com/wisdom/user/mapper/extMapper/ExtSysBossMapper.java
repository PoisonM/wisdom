package com.wisdom.user.mapper.extMapper;

import com.wisdom.common.dto.user.SysBossCriteria;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

@MyBatisDao
@Repository
public interface ExtSysBossMapper extends BaseDao<SysBossDTO, SysBossCriteria, String> {

}