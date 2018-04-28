package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopClerkScheduleCriteria;
import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface ExtShopClerkScheduleMapper extends BaseDao<ShopClerkScheduleDTO, ShopClerkScheduleCriteria, String> {

    /**
     * 批量插入接口
     * @param shopClerkScheduleDTO
     * @return
     */
    int insertBatch(List<ShopClerkScheduleDTO> shopClerkScheduleDTO);

    /**
     * 批量更新接口
     * @param shopClerkScheduleDTO
     * @return
     */
    int batchUpdate(List<ShopClerkScheduleDTO> shopClerkScheduleDTO);
}