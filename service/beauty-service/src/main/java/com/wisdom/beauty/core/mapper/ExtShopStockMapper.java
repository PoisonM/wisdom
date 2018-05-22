package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockCriteria;
import com.wisdom.beauty.api.dto.ShopStockDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/3.
 */
@Repository
@MyBatisDao
public interface ExtShopStockMapper extends BaseDao<ShopStockDTO, ShopStockCriteria, String> {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 批量插入
     * @Date:2018/5/3 18:10
     */
    int insertBatchShopStock(List<ShopStockDTO> shopStockDTO);

}
