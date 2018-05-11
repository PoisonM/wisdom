package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.IncomeExpenditureAnalysisService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/10.
 */
@Service("incomeExpenditureAnalysisService")
public class IncomeExpenditureAnalysisServiceImpl implements IncomeExpenditureAnalysisService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

    @Override
    public Map<String, Object> getBossIncomeExpenditure(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
        UserConsumeRequestDTO userConsumeRequestDTO=pageParamVoDTO.getRequestData();
        if(userConsumeRequestDTO==null){
            logger.info("getBossIncomeExpenditure传入的参数UserConsumeRequestDTO为空");
            return  null;
        }
        logger.info("getBossIncomeExpenditure方法传入的参数,boosId={},startTime={},endTime={}",userConsumeRequestDTO.getSysBossId(),pageParamVoDTO.getStartTime(),pageParamVoDTO.getEndTime());
        //获取的List是经过流水号去重后的结果
        List<ExpenditureAndIncomeResponseDTO> list=shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
        if(CollectionUtils.isEmpty(list)){
            logger.info("查询出list的结果为空");
            return  null;
        }
        //遍历list, 以payType为维度分组，然后计算各个组的金额值,并且将值放入map中
        Map<String,Object> map=new HashedMap();
        for(ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO:list){
            if(!map.containsKey(expenditureAndIncomeResponseDTO.getPayType())){
                map.put(expenditureAndIncomeResponseDTO.getPayType(),expenditureAndIncomeResponseDTO.getTotalPrice());
            }else {
                Object object=expenditureAndIncomeResponseDTO.getPayType();
                if(object!=null){
                   // BigDecimal totalPrice=map.get(expenditureAndIncomeResponseDTO.getPayType());
                }

            }
        }
        return null;
    }
}
