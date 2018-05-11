package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/10.
 * 收入支出分析
 */
public interface IncomeExpenditureAnalysisService {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取老板下的收支情况
     * @Date:2018/5/11 10:04
     */
    Map<String, Object> getBossIncomeExpenditure(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

}
