package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.api.dto.ShopCashFlowDTO;
import com.wisdom.beauty.api.enums.PayTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.CashService;
import com.wisdom.beauty.core.service.IncomeExpenditureAnalysisService;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

	@Autowired
	private CashService cashService;

	@Override
	public Map<String, BigDecimal> getBossIncomeExpenditure(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO) {
		ShopCashFlowDTO shopCashFlowDTO = pageParamVoDTO.getRequestData();
		if (shopCashFlowDTO == null) {
			logger.info("getBossIncomeExpenditure传入的参数shopCashFlowDTO为空");
			return null;
		}
		logger.info("getBossIncomeExpenditure方法传入的参数,sysBossCode={},startTime={},endTime={}",
				shopCashFlowDTO.getSysBossCode(), pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime());
		List<ShopCashFlowDTO> shopCashFlowDTOs = cashService.getShopCashFlowList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(shopCashFlowDTOs)) {
			return null;
		}
		// 遍历shopCashFlowDTOs, 以payType为维度分组，然后计算各个组的金额值,并且将值放入map中,计算支付方式是银行卡，支付宝，微信
		Map<String, BigDecimal> map = new HashedMap();
		// 现金支付
		BigDecimal cashAmount = null;
		for (ShopCashFlowDTO shopCashFlow : shopCashFlowDTOs) {
			// 计算现金收入
			if (cashAmount == null) {
				cashAmount = shopCashFlow.getCashAmount();
			} else {
				if (shopCashFlow.getCashAmount() != null) {
					cashAmount = cashAmount.add(shopCashFlow.getCashAmount());
				}
			}
			if (!map.containsKey(shopCashFlow.getPayType())) {
				if (StringUtils.isNotBlank(shopCashFlow.getPayType())) {
					map.put(shopCashFlow.getPayType(), shopCashFlow.getPayTypeAmount());
				}
			} else {
				if (StringUtils.isNotBlank(shopCashFlow.getPayType())) {
					BigDecimal price = map.get(shopCashFlow.getPayType());
					if (price != null && shopCashFlow.getPayTypeAmount() != null) {
						map.put(shopCashFlow.getPayType(), price.add(shopCashFlow.getPayTypeAmount()));
					}
				}
			}
		}
		logger.info("map的返回值map={}", map);
		BigDecimal bankIncome = map.get(PayTypeEnum.BANK_PAY.getCode()) == null ? new BigDecimal("0")
				: map.get(PayTypeEnum.BANK_PAY.getCode());
		BigDecimal wechatInCome = map.get(PayTypeEnum.WECHAT_PAY.getCode()) == null ? new BigDecimal("0")
				: map.get(PayTypeEnum.WECHAT_PAY.getCode());
		BigDecimal aliInCome = map.get(PayTypeEnum.ALI_PAY.getCode()) == null ? new BigDecimal("0")
				: map.get(PayTypeEnum.ALI_PAY.getCode());
		cashAmount = cashAmount == null ? new BigDecimal("0") : cashAmount;
		// 总收入
		BigDecimal totalInCome = bankIncome.add(wechatInCome).add(aliInCome).add(cashAmount);
		map.put(PayTypeEnum.CASH_PAY.getCode(), cashAmount);
		map.put(PayTypeEnum.ALL.getCode(), totalInCome);
		return map;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getAllShopIncomeExpenditure(
			PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO) {
		ShopCashFlowDTO shopCashFlowDTO = pageParamVoDTO.getRequestData();
		if (shopCashFlowDTO == null) {
			logger.info("getAllShopIncomeExpenditure方法出入的参数为空");
			return null;
		}
		logger.info("getAllShopIncomeExpenditure方法出入的参数 sysBossCode={}", shopCashFlowDTO.getSysBossCode());
		// 查询boos下所有美容院
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
		shopBossRelationDTO.setSysBossCode(shopCashFlowDTO.getSysBossCode());
		List<ShopBossRelationDTO> shopBossRelationDTOList = shopBossService.shopBossRelationList(shopBossRelationDTO);
		if (CollectionUtils.isEmpty(shopBossRelationDTOList)) {
			logger.info("shopBossRelationDTOList为空");
			return null;
		}
		// 查询
		List<ShopCashFlowDTO> shopCashFlowDTOs = cashService.getShopCashFlowList(pageParamVoDTO);
		Map<String, BigDecimal> cashAmountMap = null;
		Map<String, BigDecimal> payTypeAmountMap = null;
		if (CollectionUtils.isNotEmpty(shopCashFlowDTOs)) {
			cashAmountMap = new HashedMap();
			payTypeAmountMap = new HashedMap();
			// 循环list,将shopId放入map作为key,金额作为value,
			for (ShopCashFlowDTO shopCashFlow : shopCashFlowDTOs) {
				// 计算现金收入
				if (shopCashFlow.getCashAmount() != null) {
					if (cashAmountMap.containsKey(shopCashFlow.getSysShopId())) {
						BigDecimal cashEarnings = cashAmountMap.get(shopCashFlow.getSysShopId());
						cashAmountMap.put(shopCashFlow.getSysShopId(), cashEarnings.add(shopCashFlow.getCashAmount()));

					} else {
						cashAmountMap.put(shopCashFlow.getSysShopId(), shopCashFlow.getCashAmount());
					}
				}
				// 计算总收入
				if (StringUtils.isNotBlank(shopCashFlow.getPayType())) {
					if (payTypeAmountMap.containsKey(shopCashFlow.getSysShopId())) {
						BigDecimal totalInCome = payTypeAmountMap.get(shopCashFlow.getSysShopId());
						if (cashAmountMap.get(shopCashFlow.getSysShopId()) != null) {
							totalInCome = totalInCome.add(cashAmountMap.get(shopCashFlow.getSysShopId()));
						}
						payTypeAmountMap.put(shopCashFlow.getSysShopId(),
								totalInCome.add(shopCashFlow.getPayTypeAmount()));

					} else {
						payTypeAmountMap.put(shopCashFlow.getSysShopId(), shopCashFlow.getPayTypeAmount());
					}
				}

			}
		}
		// 循环美容院shopBossRelationDTOList
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = new ArrayList<>();
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		for (ShopBossRelationDTO shopBossRelation : shopBossRelationDTOList) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			if(cashAmountMap!=null && cashAmountMap.get(shopBossRelation.getSysShopId()) != null){
					expenditureAndIncomeResponseDTO.setCashEarnings(cashAmountMap.get(shopBossRelation.getSysShopId()));
					expenditureAndIncomeResponseDTO.setAllEarnings(cashAmountMap.get(shopBossRelation.getSysShopId()));
			}
			expenditureAndIncomeResponseDTO.setSysShopName(shopBossRelation.getSysShopName());
			expenditureAndIncomeResponseDTO.setSysShopId(shopBossRelation.getSysShopId());
			expenditureAndIncomeResponses.add(expenditureAndIncomeResponseDTO);
		}
		return expenditureAndIncomeResponses;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getCashEarningsTendency(
			PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO) {
		ShopCashFlowDTO shopCashFlowDTO = pageParamVoDTO.getRequestData();
		if (shopCashFlowDTO == null) {
			logger.info("getCashEarningsTendency方法出入的参数对象userConsumeRequestDTO为空");
			return null;
		}
		// 获取近7的时间放入list中
		List<String> sevenDayList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 7; i++) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(calendar.DATE, i - 7);// 把日期往后增加一天.整数往后推,负数往前移动
			String putDate = sdf.format(calendar.getTime()); // 增加一天后的日期
			sevenDayList.add(putDate);
		}
		// 查询七日数据
		String startTime = sevenDayList.get(0) + " 00:00:00";
		String endTime = sevenDayList.get(sevenDayList.size() - 1) + " 23:59:59";
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		List<ShopCashFlowDTO> shopCashFlowDTOs = cashService.getShopCashFlowList(pageParamVoDTO);
		Map<String, BigDecimal> map = new HashedMap();
		// 循环list,将shopId放入map作为key,expenditureAndIncomeResponseDTO作为value,
		for (ShopCashFlowDTO shopCashFlow : shopCashFlowDTOs) {
			if (shopCashFlow.getCashAmount() != null) {
				String formateDate = DateUtils.DateToStr(shopCashFlow.getCreateDate(), "date");
				if (map.containsKey(formateDate)) {
					BigDecimal cashEarnings = map.get(formateDate);
					map.put(formateDate, cashEarnings.add(shopCashFlow.getCashAmount()));

				} else {
					map.put(formateDate, shopCashFlow.getCashAmount());
				}
			}
		}
		// 遍历日期，将sevenDayList中的所有日期对应的现金收入放到新的list中
		List<ExpenditureAndIncomeResponseDTO> responseList = new ArrayList<>();
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		for (String day : sevenDayList) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			if (!map.containsKey(day)) {
				expenditureAndIncomeResponseDTO.setFormateDate(day);
			} else {
				expenditureAndIncomeResponseDTO.setFormateDate(day);
				expenditureAndIncomeResponseDTO.setCashEarnings(map.get(day));
			}
			responseList.add(expenditureAndIncomeResponseDTO);
		}
		return responseList;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getIncomeExpenditureAnalysisDetail(
			PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO) {
		List<ShopCashFlowDTO> shopCashFlowDTOs = cashService.getShopCashFlowList(pageParamVoDTO);
		Map<String, ExpenditureAndIncomeResponseDTO> map=null;
		if(CollectionUtils.isNotEmpty(shopCashFlowDTOs)) {
			//过滤出现金，银行卡，微信，支付宝支付的数据，即现金支付=0 支付方式为空
			 map = new HashMap<>();
			for (ShopCashFlowDTO shopCashFlowDTO : shopCashFlowDTOs) {
				if (StringUtils.isBlank(shopCashFlowDTO.getPayType()) && shopCashFlowDTO.getCashAmount().compareTo(new BigDecimal("0")) == 0) {
					continue;
				}
				if (map.containsKey(shopCashFlowDTO.getFlowNo())) {
					ExpenditureAndIncomeResponseDTO expenditureAndIncome = map.get(shopCashFlowDTO.getFlowNo());
					expenditureAndIncome.setTotalPrice(shopCashFlowDTO.getPayTypeAmount().add(shopCashFlowDTO.getCashAmount()));
					map.put(shopCashFlowDTO.getFlowNo(), expenditureAndIncome);
				} else {
					ExpenditureAndIncomeResponseDTO expenditureAndIncome = new ExpenditureAndIncomeResponseDTO();
					expenditureAndIncome.setTotalPrice(shopCashFlowDTO.getPayTypeAmount().add(shopCashFlowDTO.getCashAmount()));
					expenditureAndIncome.setCreateDate(shopCashFlowDTO.getCreateDate());
					expenditureAndIncome.setFlowNo(shopCashFlowDTO.getFlowNo());
					map.put(shopCashFlowDTO.getFlowNo(), expenditureAndIncome);
				}
			}
		}
		if(map==null){
			return null;
		}else {
			List<ExpenditureAndIncomeResponseDTO> list = new ArrayList<>();
			for (Map.Entry<String, ExpenditureAndIncomeResponseDTO> entry:map.entrySet()){
				list.add(entry.getValue());
			}
			return list;
		}
	}

}
