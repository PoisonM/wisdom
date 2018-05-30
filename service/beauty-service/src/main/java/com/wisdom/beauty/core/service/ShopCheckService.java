package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.beauty.api.dto.ShopClosePositionRecordDTO;
import com.wisdom.beauty.api.requestDto.ShopClosePositionRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopCheckRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoCheckResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

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

    List<ShopCheckRecordResponseDTO> getProductCheckRecordList(PageParamVoDTO<ShopCheckRecordDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 盘点记录详情
    *@Date:2018/5/21 10:10
    */
    List<ShopCheckRecordResponseDTO> getProductCheckRecordDeatil(String flowNo);
    /**
    *@Author:平仓
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/5/21 14:48
    */
    int doClosePosition(ShopClosePositionRequestDTO shopClosePositionRequestDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:   平仓详情
    *@Date:2018/5/21 16:00
    */
    ShopClosePositionRecordDTO getShopClosePositionDetail(String id,String productName,String productTypeName);
    /**
    *@Author:zhanmghuan
    *@Param:
    *@Return:
    *@Description: 跳转盘点页面使用接口
    *@Date:2018/5/22 14:37
    */
    List<ShopProductInfoCheckResponseDTO> getProductsCheckLit(String shopStoreId , List<String> productIds);
}
