package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;

import java.util.List;

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

    List<ShopCheckRecordDTO> getProductCheckRecord(ShopCheckRecordDTO shopCheckRecordDTO);
}
