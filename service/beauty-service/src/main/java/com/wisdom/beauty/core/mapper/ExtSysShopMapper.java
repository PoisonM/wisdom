package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.SysShopCriteria;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface ExtSysShopMapper extends BaseDao<ExtSysShopDTO, SysShopCriteria, String> {
    List<ExtSysShopDTO> selectBossShopInfo(ExtSysShopDTO extSysShopDTO);
}