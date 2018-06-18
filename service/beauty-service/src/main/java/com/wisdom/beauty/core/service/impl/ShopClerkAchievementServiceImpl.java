package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopClerkAchievementService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("shopClerkAchievementService")
public class ShopClerkAchievementServiceImpl implements ShopClerkAchievementService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;
    @Override
    public ExpenditureAndIncomeResponseDTO getExpenditureAndIncomeList(String startTime,String endTime) {
            PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<UserConsumeRequestDTO>();
            SysClerkDTO clerkDTO = UserUtils.getClerkInfo();
            UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
            userConsumeRequest.setSysClerkId(clerkDTO.getSysUserId());
            pageParamVoDTO.setStartTime(startTime);
            pageParamVoDTO.setEndTime(endTime);
            pageParamVoDTO.setRequestData(userConsumeRequest);
            // 查询数据,获取业绩
            List<ExpenditureAndIncomeResponseDTO> incomeList = shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
            if (CollectionUtils.isEmpty(incomeList)) {
                logger.info("incomeList结果为空");
                return null;
            }
            Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>(16);
            ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
            // 遍历incomeList，此集合是消费记录，经过流水号去重后计算price和
            for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : incomeList) {
                expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
                if (map.get(expenditureAndIncomeResponse.getFormateDate()) == null) {
                    // 如果map中的key没有shopId,则直接将业绩值放入value
                    map.put(expenditureAndIncomeResponse.getFormateDate(), expenditureAndIncomeResponse);
                } else {
                    //取出key是ship的值，计算value中的值
                    if(map.get(expenditureAndIncomeResponse.getFormateDate()).getTotalPrice()!=null){
                        expenditureAndIncomeResponseDTO.setTotalPrice(map.get(expenditureAndIncomeResponse.getFormateDate()).getTotalPrice().add(expenditureAndIncomeResponse.getTotalPrice()));
                        map.put(expenditureAndIncomeResponse.getFormateDate(),expenditureAndIncomeResponseDTO);
                    }

                }
                expenditureAndIncomeResponseDTO = null;
            }
            // 获取耗卡
            List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = shopStatisticsAnalysisService.getExpenditureList(pageParamVoDTO);
            if (CollectionUtils.isEmpty(expenditureAndIncomeResponses)) {
                logger.info("list结果为空");
                return null;
            }
            Map<String, ExpenditureAndIncomeResponseDTO> map2 = new HashMap<>(16);
            for (ExpenditureAndIncomeResponseDTO expenditure : expenditureAndIncomeResponses) {
                expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
                if (map2.get(expenditure.getFormateDate()) == null) {
                    // 如果map中的key没有shopId,则直接将业绩值放入value
                    map2.put(expenditure.getFormateDate(), expenditure);
                } else {
                    // 取出key是ship的值，计算value中的值
                    if (map2.get(expenditure.getFormateDate()) != null
                            && map2.get(expenditure.getFormateDate()).getExpenditure() != null
                            && expenditure.getExpenditure() != null) {
                        expenditureAndIncomeResponseDTO.setTotalPrice(map2.get(expenditure.getFormateDate()).getTotalPrice().add(expenditure.getTotalPrice()));
                        map2.put(expenditure.getFormateDate(),expenditureAndIncomeResponseDTO);
                    }
                }
            }
            ExpenditureAndIncomeResponseDTO response = null;
                response = new ExpenditureAndIncomeResponseDTO();
                    response.setIncome(map.get(incomeList.get(0).getFormateDate()).getTotalPrice());
                    response.setExpenditure(map2.get(expenditureAndIncomeResponses.get(0).getFormateDate()).getTotalPrice());
                response.setFormateDate(incomeList.get(0).getFormateDate());
            return response;
    }

}
