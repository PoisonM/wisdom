package com.wisdom.beauty.core.service.stock;

import java.util.List;

import com.wisdom.beauty.api.dto.ShopStockRecordDTO;
import com.wisdom.beauty.api.dto.ShopStoreDTO;
import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.common.dto.system.PageParamDTO;

/**
 * FileName: ShopStockService
 *
 * @author: 张超
 * Date:     2018/4/23  14:06
 * Description: 仓库和库存相关
 */
public interface ShopStockService {

   /**
     * 查询仓库列表
     * @param pageParamDTO 分页对象
     * @return
     */

    PageParamDTO<List<ExtShopStoreDTO>> findStoreListS(PageParamDTO<ExtShopStoreDTO> pageParamDTO);


    /**
     * 插入一条出/入库记录
     * @param shopStockRecordDTO 出入库表实体对象
     * @return
     */
    void insertStockRecord(ShopStockRecordDTO shopStockRecordDTO);

    /**
     *
     *
     *
     *
     * */

}
