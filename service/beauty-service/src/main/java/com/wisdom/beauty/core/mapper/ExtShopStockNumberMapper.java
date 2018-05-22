package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopStockDTO;
import com.wisdom.beauty.api.dto.ShopStockNumberCriteria;
import com.wisdom.beauty.api.dto.ShopStockNumberDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/4.
 */
@Repository
@MyBatisDao
public interface ExtShopStockNumberMapper extends BaseDao<ShopStockNumberDTO, ShopStockNumberCriteria, String> {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 批量更新
     * @Date:2018/5/3 18:10
     */
    int updateBatchShopStockNumber(Map<String,Object> param);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:批量插入
    *@Date:2018/5/9 20:47
    */
    int saveBatchShopStockNumber(List<ShopStockNumberDTO> shopStockDTO);
}
