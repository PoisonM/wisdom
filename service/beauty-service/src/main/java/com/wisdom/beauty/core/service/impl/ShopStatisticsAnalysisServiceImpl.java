package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopCustomerArchivesService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * FileName: ShopStatisticsAnalysisServiceImpl
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 店铺分析相关
 */
@Service("shopStatisticsAnalysisService")
public class ShopStatisticsAnalysisServiceImpl implements ShopStatisticsAnalysisService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ShopCustomerArchivesService shopCustomerArchivesService;

	@Autowired
	private ExtShopUserConsumeRecordMapper extShopUserConsumeRecordMapper;
	@Autowired
	private ShopUerConsumeRecordService shopUerConsumeRecordService;
	@Autowired
	private ShopCardService shopCardService;

	@Override
	public BigDecimal getPerformance(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		// 计算充值金额
		BigDecimal totalAmount = null;
		// ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new
		// ShopUserConsumeRecordDTO();
		// shopUserConsumeRecordDTO.getSysClerkId();
		// shopUserConsumeRecordDTO.setStatus(ConsumeTypeEnum.CONSUME.getCode());
		// pageParamVoDTO.setRequestData(shopUserConsumeRecordDTO);
		// 查询数据
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponses = shopUerConsumeRecordService
				.getShopCustomerConsumeRecordList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(userConsumeRecordResponses)) {
			return null;
		}
		for (UserConsumeRecordResponseDTO userConsumeRecordResponse : userConsumeRecordResponses) {
			if (totalAmount == null) {
				totalAmount = userConsumeRecordResponse.getSumAmount();
			}
			totalAmount.add(userConsumeRecordResponse.getSumAmount());
		}
		return totalAmount;
	}

	/**
	 * 查询美容店某个时间段的耗卡金额
	 */
	@Override
	public BigDecimal getShopCardConsumeAmount(String shopId, Date startDate, Date endDate) {

		logger.info("查询美容店某个时间段的耗卡金额传入参数={}",
				"shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");

		if (StringUtils.isBlank(shopId) || null == startDate || null == endDate) {
			logger.error("查询美容店某个时间段的耗卡金额传入参数为空，{}",
					"shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");
			;
			return null;
		}

		// 获取用户的划卡金额金额
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 卡耗归为消费类
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		List<String> goods = new ArrayList<>();
		goods.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
		goods.add(GoodsTypeEnum.TIME_CARD.getCode());
		goods.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
		goods.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
		criteria.andGoodsTypeIn(goods);
		criteria.andCreateDateBetween(startDate, endDate);

		BigDecimal bigDecimal = extShopUserConsumeRecordMapper.selectSumPriceByCriteria(recordCriteria);

		return bigDecimal;
	}

	/**
	 * 查询新客个数
	 */
	@Override
	public int getShopNewUserNumber(String shopId, Date startDate, Date endDate) {
		logger.info("查询新客个数传入参数={}",
				"shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");
		return shopCustomerArchivesService.getShopBuildArchivesNumbers(shopId, startDate, endDate);
	}

	@Override
	public Integer getUserConsumeNumber(String sysClerkId, Date startDate, Date endDate) {
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 设置查询条件
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		criteria.andSysClerkIdEqualTo(sysClerkId);
		criteria.andCreateDateBetween(startDate, endDate);
		Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
		return consumeNumber;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getExpenditureAndIncomeList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest=pageParamVoDTO.getRequestData();
        logger.info("getPerformanceList方法传入的参数,sysShopId={},startTime={},endTime={}",userConsumeRequest.getSysShopId(),pageParamVoDTO.getStartTime(),pageParamVoDTO.getEndTime());
		// 查询数据,获取业绩
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponses = shopUerConsumeRecordService
				.getShopCustomerConsumeRecordList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(userConsumeRecordResponses)) {
            logger.info("userConsumeRecordResponses结果为空");
			return null;
		}
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>(16);
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		for (UserConsumeRecordResponseDTO userConsumeRecordResponse : userConsumeRecordResponses) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			expenditureAndIncomeResponseDTO.setDate(userConsumeRecordResponse.getCreateDate());
			expenditureAndIncomeResponseDTO.setIncome(userConsumeRecordResponse.getSumAmount());
			map.put(userConsumeRecordResponse.getFlowNo(), expenditureAndIncomeResponseDTO);
			expenditureAndIncomeResponseDTO = null;
		}
		// 获取耗卡
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 卡耗归为消费类
		criteria.andConsumeTypeEqualTo(pageParamVoDTO.getRequestData().getConsumeType());
		List<String> goods = new ArrayList<>();
		goods.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
		goods.add(GoodsTypeEnum.TIME_CARD.getCode());
		goods.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
		goods.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
		criteria.andGoodsTypeIn(goods);
		Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
		Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
		criteria.andCreateDateBetween(startDate, endDate);

		List<ExpenditureAndIncomeResponseDTO> list = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(recordCriteria);
        if(CollectionUtils.isEmpty(list)){
            logger.info("list结果为空");
            return  null;
        }
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = new ArrayList<>();
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : list) {
			expenditureAndIncomeResponse.setIncome(map.get(expenditureAndIncomeResponse.getFlowNo()).getIncome());
			expenditureAndIncomeResponse.setExpenditure(expenditureAndIncomeResponse.getExpenditure());
			expenditureAndIncomeResponse.setDate(map.get(expenditureAndIncomeResponse.getFlowNo()).getDate());
			expenditureAndIncomeResponse.setSysShopId(expenditureAndIncomeResponse.getSysShopId());
			expenditureAndIncomeResponse.setSysShopName(expenditureAndIncomeResponse.getSysShopName());
			expenditureAndIncomeResponses.add(expenditureAndIncomeResponse);

		}
		return expenditureAndIncomeResponses;
	}
}
