package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 员工端 业绩、卡耗相关
 * add by 盛小龙
 *
 *
 */
public  interface ShopClerkAchievementService {
    ExpenditureAndIncomeResponseDTO getExpenditureAndIncomeList(String startTime,String endTime);
}
