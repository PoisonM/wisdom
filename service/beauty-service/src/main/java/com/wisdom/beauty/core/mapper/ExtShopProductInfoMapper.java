package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProductInfoCriteria;
import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface ExtShopProductInfoMapper extends BaseDao<ShopProductInfoDTO, ShopProductInfoCriteria, String> {


    List<ShopProductInfoDTO> findEarlyWarningProductLevelInfo(ShopProductInfoDTO shopProductInfoDTO);
}