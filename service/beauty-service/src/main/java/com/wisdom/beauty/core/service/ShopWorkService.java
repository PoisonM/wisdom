package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;

/**
 * FileName: ShopClerkService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ShopWorkService {

    /**
     * 查看某个店的上下班时间
     *
     * @param shopScheduleSettingDTO
     * @return
     */

    List<ShopScheduleSettingDTO> getShopScheduleSettingInfo(ShopScheduleSettingDTO shopScheduleSettingDTO);

    /**
     * 查看某个点的店员列表
     */
//    public List<>

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据bossId查询所有美容院列表业绩每个美容院的业绩和耗卡
     * @Date:2018/4/23 17:10
     */
    List<ExpenditureAndIncomeResponseDTO> getShopExpenditureAndIncomeList(PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO);

}
