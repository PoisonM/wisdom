package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.IncomeExpenditureAnalysisService;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    private ShopBossService shopBossService;

    @Override
    public Map<String, BigDecimal> getBossIncomeExpenditure(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
        UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
        if (userConsumeRequestDTO == null) {
            logger.info("getBossIncomeExpenditure传入的参数UserConsumeRequestDTO为空");
            return null;
        }
        logger.info("getBossIncomeExpenditure方法传入的参数,boosId={},startTime={},endTime={}",
                userConsumeRequestDTO.getSysBossId(), pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime());
        // 获取的List是经过流水号去重后的结果
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("查询出list的结果为空");
            return null;
        }
        // 遍历list, 以payType为维度分组，然后计算各个组的金额值,并且将值放入map中
        Map<String, BigDecimal> map = new HashedMap();
        BigDecimal totalInCome = null;
        for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : list) {
            if (totalInCome == null) {
                totalInCome = expenditureAndIncomeResponseDTO.getTotalPrice();
            } else {
                totalInCome = totalInCome.add(expenditureAndIncomeResponseDTO.getTotalPrice());
            }
            if (!map.containsKey(expenditureAndIncomeResponseDTO.getPayType())) {
                map.put(expenditureAndIncomeResponseDTO.getPayType(), expenditureAndIncomeResponseDTO.getTotalPrice());
            } else {
                BigDecimal price = map.get(expenditureAndIncomeResponseDTO.getPayType());
                if (price != null) {
                    BigDecimal totalPrice = price.add(expenditureAndIncomeResponseDTO.getTotalPrice());
                    map.put(expenditureAndIncomeResponseDTO.getPayType(), totalPrice);
                }

            }
        }
        map.put(PayTypeEnum.ALL.getCode(), totalInCome);
        return map;
    }

    @Override
    public List<ExpenditureAndIncomeResponseDTO> getAllShopIncomeExpenditure(
            PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
        UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
        if (userConsumeRequestDTO == null) {
            logger.info("getAllShopIncomeExpenditure方法出入的参数为空");
            return null;
        }
        logger.info("getAllShopIncomeExpenditure方法出入的参数 sysBossId={}", userConsumeRequestDTO.getSysBossId());
        //查询boos下所有美容院
        ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
        shopBossRelationDTO.setSysBossId(userConsumeRequestDTO.getSysBossId());
        List<ShopBossRelationDTO> shopBossRelationDTOList = shopBossService.ShopBossRelationList(shopBossRelationDTO);
        if (CollectionUtils.isEmpty(shopBossRelationDTOList)) {
            logger.info("shopBossRelationDTOList为空");
            return null;
        }
        //查询
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
        Map<String, ExpenditureAndIncomeResponseDTO> map = new HashedMap();
        //循环list,将shopId放入map作为key,expenditureAndIncomeResponseDTO作为value,
        BigDecimal allEarnings = null;
        for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : list) {
            if (allEarnings == null) {
                allEarnings = expenditureAndIncomeResponseDTO.getTotalPrice();
            } else {
                allEarnings = allEarnings.add(expenditureAndIncomeResponseDTO.getTotalPrice());
            }
            //以上是获取总收入
            if (PayTypeEnum.CASH_PAY.getCode().equals(expenditureAndIncomeResponseDTO.getPayType())) {
                if (!map.containsKey(expenditureAndIncomeResponseDTO.getSysShopId())) {
                    map.put(expenditureAndIncomeResponseDTO.getSysShopId(), expenditureAndIncomeResponseDTO);
                } else {
                    ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = map.get(expenditureAndIncomeResponseDTO.getSysShopId());
                    if (expenditureAndIncomeResponse != null) {
                        if (expenditureAndIncomeResponse.getTotalPrice() != null) {
                            BigDecimal totalPrice = expenditureAndIncomeResponse.getTotalPrice().add(expenditureAndIncomeResponseDTO.getTotalPrice());
                            expenditureAndIncomeResponse.setTotalPrice(totalPrice);
                            map.put(expenditureAndIncomeResponseDTO.getSysShopId(), expenditureAndIncomeResponse);
                        }
                    } else {
                        map.put(expenditureAndIncomeResponseDTO.getSysShopId(), expenditureAndIncomeResponseDTO);
                    }

                }
            }
            expenditureAndIncomeResponseDTO.setAllEarnings(allEarnings);
            map.put(expenditureAndIncomeResponseDTO.getSysShopId(), expenditureAndIncomeResponseDTO);
        }
        //循环美容院shopBossRelationDTOList
        List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = new ArrayList<>();
        ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
        for (ShopBossRelationDTO shopBossRelation : shopBossRelationDTOList) {
            expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
            if (map.get(shopBossRelation.getSysShopId()) != null) {
                expenditureAndIncomeResponseDTO.setCashEarnings(map.get(shopBossRelation.getSysShopId()).getTotalPrice());
                expenditureAndIncomeResponseDTO.setAllEarnings(map.get(shopBossRelation.getSysShopId()).getAllEarnings());
            }
            expenditureAndIncomeResponseDTO.setSysShopName(shopBossRelation.getSysShopName());
            expenditureAndIncomeResponses.add(expenditureAndIncomeResponseDTO);
        }
        return expenditureAndIncomeResponses;
    }

    @Override
    public List<ExpenditureAndIncomeResponseDTO> getCashEarningsTendency(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
        UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
        if (userConsumeRequestDTO == null) {
            logger.info("getCashEarningsTendency方法出入的参数对象userConsumeRequestDTO为空");
            return null;
        }
        //获取近7的时间放入list中
        List<String> sevenDayList = new ArrayList<>();
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = null;
        for (int i = 0; i < 7; i++) {
            lastDate = Calendar.getInstance();
            lastDate.roll(Calendar.DATE, i-7);//日期回滚7天
            str = sdf.format(lastDate.getTime());
            sevenDayList.add(str);
        }
        //查询
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
        Map<String, ExpenditureAndIncomeResponseDTO> map = new HashedMap();
        //循环list,将shopId放入map作为key,expenditureAndIncomeResponseDTO作为value,
        for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : list) {
            if (PayTypeEnum.CASH_PAY.getCode().equals(expenditureAndIncomeResponseDTO.getPayType())) {
                if (!map.containsKey(expenditureAndIncomeResponseDTO.getFormateDate())) {
                    map.put(expenditureAndIncomeResponseDTO.getFormateDate(), expenditureAndIncomeResponseDTO);
                } else {
                    ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = map.get(expenditureAndIncomeResponseDTO.getFormateDate());
                    if (expenditureAndIncomeResponse != null) {
                        if (expenditureAndIncomeResponse.getTotalPrice() != null) {
                            BigDecimal totalPrice = expenditureAndIncomeResponse.getTotalPrice().add(expenditureAndIncomeResponseDTO.getTotalPrice());
                            expenditureAndIncomeResponse.setTotalPrice(totalPrice);
                            map.put(expenditureAndIncomeResponseDTO.getFormateDate(), expenditureAndIncomeResponse);
                        }
                    } else {
                        map.put(expenditureAndIncomeResponseDTO.getFormateDate(), expenditureAndIncomeResponseDTO);
                    }

                }
            }
        }
        //遍历日期，将sevenDayList中的所有日期对应的现金收入放到新的list中
        List<ExpenditureAndIncomeResponseDTO> responseList = new ArrayList<>();
        ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
        for (String day : sevenDayList) {
            expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
            if (!map.containsKey(day)) {
                expenditureAndIncomeResponseDTO.setFormateDate(day);
            } else {
                expenditureAndIncomeResponseDTO.setFormateDate(day);
                expenditureAndIncomeResponseDTO.setCashEarnings(map.get(day).getTotalPrice());
            }
            responseList.add(expenditureAndIncomeResponseDTO);
        }
        return responseList;
    }

    @Override
    public List<ExpenditureAndIncomeResponseDTO> getIncomeExpenditureAnalysisDetail(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getIncomeList(pageParamVoDTO);
        return  list;
    }
}
