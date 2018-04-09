package com.wisdom.user.mapper;

import com.wisdom.common.dto.user.SysBossCriteria;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface SysBossMapper extends BaseDao<SysBossDTO, SysBossCriteria, String> {

    List<SysBossDTO> getBossInfo(SysBossDTO sysBossDTO);

    void updateBossInfo(SysBossDTO sysBossDTO);

    void updateUserInfo(SysBossDTO sysBossDTO);


}