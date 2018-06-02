package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCashFlowDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;

import java.util.List;

/**
 * FileName: CashService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 资金流水相关
 */
public interface CashService {

    /**
     * 查询某笔资金流水
     *
     * @param shopCashFlowDTO
     * @return
     */
    ShopCashFlowDTO getShopCashFlow(ShopCashFlowDTO shopCashFlowDTO);

    /**
     * 查询某笔资金流水列表
     *
     * @param shopCashFlowDTO
     * @return
     */
    List<ShopCashFlowDTO> getShopCashFlowList(ShopCashFlowDTO shopCashFlowDTO);

    /**
     * 保存某笔资金流水
     *
     * @param shopCashFlowDTO
     * @return
     */
    int saveShopCashFlow(ShopCashFlowDTO shopCashFlowDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/6/2 15:38
    */
    List<ShopCashFlowDTO> getShopCashFlowList(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO);
}
