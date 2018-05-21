package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/21.
 */
public interface ShopCheckService {
    /**
    *@Author:zhanghuan
    *@Param: 仓库id
    *@Return:
    *@Description:  获取产品的盘点记录
    *@Date:2018/5/21 9:50
    */

    List<ShopCheckRecordDTO> getProductCheckRecordList(ShopCheckRecordDTO shopCheckRecordDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 盘点记录详情
    *@Date:2018/5/21 10:10
    */
    Map<String ,Object> getProductCheckRecordDeatil(ShopCheckRecordDTO shopCheckRecordDTO);
}
