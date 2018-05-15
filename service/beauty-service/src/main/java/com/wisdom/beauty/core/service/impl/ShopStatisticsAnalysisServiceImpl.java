package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.*;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
	@Resource
	private UserServiceClient userServiceClient;
	@Autowired
	private ShopBossService shopBossService;

	@Override
	public BigDecimal getPerformance(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		// 计算充值金额
		BigDecimal totalAmount = null;
		// 查询数据
		List<ExpenditureAndIncomeResponseDTO> userConsumeRecordResponses = this.getIncomeList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(userConsumeRecordResponses)) {
			logger.info("userConsumeRecordResponses集合为空");
			return null;
		}
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : userConsumeRecordResponses) {
			if (totalAmount == null) {
				totalAmount = expenditureAndIncomeResponseDTO.getTotalPrice();
			}
			totalAmount.add(expenditureAndIncomeResponseDTO.getTotalPrice());
		}
		return totalAmount;
	}

	@Override
	public BigDecimal getExpenditure(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		// 计算耗卡金额
		BigDecimal totalAmount = null;
		// 查询数据
		List<ExpenditureAndIncomeResponseDTO> userConsumeRecordResponses = this.getExpenditureList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(userConsumeRecordResponses)) {
			logger.info("userConsumeRecordResponses为空");
			return null;
		}
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : userConsumeRecordResponses) {
			if (totalAmount == null) {
				totalAmount = expenditureAndIncomeResponseDTO.getTotalPrice();
			}
			totalAmount.add(expenditureAndIncomeResponseDTO.getTotalPrice());
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
	public int getShopNewUserNumber(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		return shopCustomerArchivesService.getShopBuildArchivesNumbers(pageParamVoDTO);
	}

	@Override
	public Integer getUserConsumeNumber(String sysClerkId, String startDate, String endDate) {
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 设置查询条件
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		criteria.andSysClerkIdEqualTo(sysClerkId);
		criteria.andCreateDateBetween(DateUtils.StrToDate(startDate, "datetime"),
				DateUtils.StrToDate(endDate, "endDate"));
		Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
		return consumeNumber;
	}

	@Override
	public Integer getUserConsumeNumber(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();

		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 设置查询条件
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		criteria.andSysBossIdEqualTo(userConsumeRequestDTO.getSysBossId());
		String startDate = pageParamVoDTO.getStartTime();
		String endDate = pageParamVoDTO.getEndTime();
		criteria.andCreateDateBetween(DateUtils.StrToDate(startDate, "datetime"),
				DateUtils.StrToDate(endDate, "endDate"));
		if (userConsumeRequestDTO.getDisticRequire()) {
			recordCriteria.setDistinct(true);
		}
		Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
		return consumeNumber;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getExpenditureAndIncomeList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		logger.info("getPerformanceList方法传入的参数,sysShopId={}", userConsumeRequest.getSysShopId());
		// 获取近7的时间放入list中
		List<String> sevenDayList = new ArrayList<>();
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = null;
		for (int i = 0; i < 7; i++) {
			lastDate = Calendar.getInstance();
			lastDate.roll(Calendar.DATE, i - 7);// 日期回滚7天
			str = sdf.format(lastDate.getTime());
			sevenDayList.add(str);
		}
		// 查询七日数据
		String startTime = sevenDayList.get(0) + " 00:00:00";
		String endTime = sevenDayList.get(sevenDayList.size() - 1) + " 23:59:59";
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		// 查询数据,获取业绩
		List<ExpenditureAndIncomeResponseDTO> incomeList = this.getIncomeList(pageParamVoDTO);
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
				// 取出key是ship的值，计算value中的值
				map.get(expenditureAndIncomeResponse.getFormateDate()).getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
			}
			expenditureAndIncomeResponseDTO = null;
		}
		// 获取耗卡
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = this.getExpenditureList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(expenditureAndIncomeResponses)) {
			logger.info("list结果为空");
			return null;
		}
		Map<String, ExpenditureAndIncomeResponseDTO> map2 = new HashMap<>(16);
		List<ExpenditureAndIncomeResponseDTO> responsesList = new ArrayList<>();
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
					map2.get(expenditure.getFormateDate()).getTotalPrice().add(expenditure.getTotalPrice());
				}
			}
		}
		ExpenditureAndIncomeResponseDTO response = null;
		for (String sevenDay : sevenDayList) {
			response = new ExpenditureAndIncomeResponseDTO();
			if (map.get(sevenDay) != null) {
				response.setIncome(map.get(sevenDay).getTotalPrice());
				response.setSysShopId(map.get(sevenDay).getSysShopId());
				response.setSysShopName(map.get(sevenDay).getSysShopName());
			}
			if (map2.get(sevenDay) != null) {
				response.setExpenditure(map2.get(sevenDay).getTotalPrice());
				response.setSysShopId(map2.get(sevenDay).getSysShopId());
				response.setSysShopName(map2.get(sevenDay).getSysShopName());
			}
			response.setFormateDate(sevenDay);
			responsesList.add(response);
		}
		return responsesList;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getShopExpenditureAndIncomeList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		logger.info("getShopExpenditureAndIncomeList方法传入的参数,sysShopId={},startTime={},endTime={}",
				userConsumeRequest.getSysShopId(), pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime());
		// 查询数据,获取业绩
		List<ExpenditureAndIncomeResponseDTO> incomeList = this.getIncomeList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(incomeList)) {
			logger.info("incomeList结果为空");
			return null;
		}
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>(16);
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		// 遍历incomeList，此集合是消费记录，经过流水号去重后计算price和
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : incomeList) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			if (map.get(expenditureAndIncomeResponse.getSysShopId()) == null) {
				// 如果map中的key没有shopId,则直接将业绩值放入value
				map.put(expenditureAndIncomeResponse.getSysShopId(), expenditureAndIncomeResponse);
			} else {
				// 取出key是ship的值，计算value中的值
				map.get(expenditureAndIncomeResponse.getSysShopId()).getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
			}
			expenditureAndIncomeResponseDTO = null;
		}
		// 获取耗卡
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = this.getExpenditureList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(expenditureAndIncomeResponses)) {
			logger.info("list结果为空");
			return null;
		}
		Map<String, ExpenditureAndIncomeResponseDTO> map2 = new HashMap<>(16);
		List<ExpenditureAndIncomeResponseDTO> responsesList = new ArrayList<>();
		for (ExpenditureAndIncomeResponseDTO expenditure : expenditureAndIncomeResponses) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			if (map2.get(expenditure.getSysShopId()) == null) {
				// 如果map中的key没有shopId,则直接将业绩值放入value
				map2.put(expenditure.getSysShopId(), expenditure);
			} else {
				// 取出key是ship的值，计算value中的值
				if (map2.get(expenditure.getSysShopId()) != null
						&& map2.get(expenditure.getSysShopId()).getTotalPrice() != null
						&& expenditure.getTotalPrice() != null) {
					map2.get(expenditure.getSysShopId()).getTotalPrice().add(expenditure.getTotalPrice());
				}
			}
		}
		ExpenditureAndIncomeResponseDTO response = null;
		// 获取bossid下的所有美容店
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
		shopBossRelationDTO.setSysBossId(userConsumeRequest.getSysBossId());
		List<ShopBossRelationDTO> shopBossRelationList = shopBossService.ShopBossRelationList(shopBossRelationDTO);
		for (ShopBossRelationDTO shopBossRelation : shopBossRelationList) {
			response = new ExpenditureAndIncomeResponseDTO();
			if (map.get(shopBossRelation.getSysShopId()) != null) {
				response.setIncome(map.get(shopBossRelation.getSysShopId()).getExpenditure());
			}
			if (map2.get(shopBossRelation.getSysShopId()) != null) {
				response.setExpenditure(map2.get(shopBossRelation.getSysShopId()).getExpenditure());
			}
			response.setSysShopId(shopBossRelation.getSysShopId());
			response.setSysShopName(shopBossRelation.getSysShopName());
			responsesList.add(response);
		}
		return responsesList;
	}

	@Override
	public BigDecimal getShopConsumeAndRecharge(String shopId, String goodType, String consumeType,
			Boolean isCardConsume, Date startDate, Date endDate) {
		logger.info("getShopConsumeAndRecharge方法传入的参数shopId={},goodType={},startDate={},endDate={}", shopId, goodType,
				startDate, endDate);
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 业绩归为充值
		criteria.andConsumeTypeEqualTo(consumeType);
		criteria.andSysShopIdEqualTo(shopId);
		// 充值
		if (ConsumeTypeEnum.RECHARGE.getCode().equals(consumeType)) {
			if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(goodType)) {
				// 充值卡
				criteria.andGoodsTypeEqualTo(goodType);
			} else {
				List<String> goods = new ArrayList<>();
				// 套卡
				goods.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
				// 次卡
				goods.add(GoodsTypeEnum.TIME_CARD.getCode());
				// 疗程卡
				goods.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
				if (isCardConsume) {
					// 产品
					goods.add(GoodsTypeEnum.PRODUCT.getCode());
				}
				criteria.andGoodsTypeIn(goods);
			}
		}
		// 消费
		if (ConsumeTypeEnum.CONSUME.getCode().equals(consumeType)) {
			if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodType)) {
				// 单次
				criteria.andGoodsTypeEqualTo(goodType);
			} else {
				List<String> goods = new ArrayList<>();
				// 套卡
				goods.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
				// 疗程卡
				goods.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
				criteria.andGoodsTypeIn(goods);
			}
		}
		criteria.andCreateDateBetween(startDate, endDate);

		BigDecimal bigDecimal = extShopUserConsumeRecordMapper.selectSumPriceByCriteria(recordCriteria);

		return bigDecimal;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getClerkExpenditureAndIncomeList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		List<SysClerkDTO> sysClerkList = userServiceClient.getClerkInfoList(userConsumeRequest.getSysBossId(),
				pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime(), pageParamVoDTO.getPageSize());
		if (CollectionUtils.isEmpty(sysClerkList)) {
			logger.info("查询店员的结果为空");
			return null;
		}
		// 通过用户美容院id查询出来消费记录
		List<ExpenditureAndIncomeResponseDTO> incomeResponse = this.getIncomeList(pageParamVoDTO);
		// 计算业绩
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>(16);
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		// 循环消费记录，将店员id作为key值
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : incomeResponse) {
			if (map.get(expenditureAndIncomeResponse.getSysShopClerkId()) == null) {
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncomeResponseDTO);
			} else {
				ExpenditureAndIncomeResponseDTO expenditureAndIncome = map
						.get(expenditureAndIncomeResponse.getSysShopClerkId());
				BigDecimal prices = expenditureAndIncome.getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
				expenditureAndIncome.setIncome(prices);
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncome);
			}
		}
		// 耗卡
		List<ExpenditureAndIncomeResponseDTO> list2 = this.getExpenditureList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(list2)) {
			logger.info("list结果为空");
			return null;
		}

		Map<String, ExpenditureAndIncomeResponseDTO> map2 = new HashMap<>(16);
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : list2) {
			if (map2.get(expenditureAndIncomeResponse.getSysShopClerkId()) == null) {
				map2.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncomeResponse);
			} else {
				ExpenditureAndIncomeResponseDTO expenditureAndIncome = map2
						.get(expenditureAndIncomeResponse.getSysShopClerkId());
				BigDecimal prices = expenditureAndIncome.getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
				expenditureAndIncome.setExpenditure(prices);
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncome);
			}

		}
		// 循环店员集合
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = new ArrayList<>();
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = null;
		for (SysClerkDTO sysClerkDTO : sysClerkList) {
			expenditureAndIncomeResponse = new ExpenditureAndIncomeResponseDTO();
			if (map.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setIncome(map.get(sysClerkDTO.getId()).getIncome());
			}
			if (map2.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setExpenditure(map2.get(sysClerkDTO.getId()).getExpenditure());
			}
			expenditureAndIncomeResponse.setPhoto(sysClerkDTO.getPhoto());
			expenditureAndIncomeResponse.setSysShopClerkName(sysClerkDTO.getName());
			expenditureAndIncomeResponse.setRole(sysClerkDTO.getRole());
			expenditureAndIncomeResponses.add(expenditureAndIncomeResponse);
		}
		return expenditureAndIncomeResponses;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 获取产生耗卡的消费记录列表
	 * @Date:2018/4/26 14:03
	 */
	@Override
	public List<ExpenditureAndIncomeResponseDTO> getExpenditureList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();

		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		ShopUserConsumeRecordCriteria.Criteria or = recordCriteria.createCriteria();
		// 耗卡归为消费类
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());

		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossId())) {
			criteria.andSysBossIdEqualTo(userConsumeRequest.getSysBossId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysShopId())) {
			criteria.andSysShopIdEqualTo(userConsumeRequest.getSysShopId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysClerkId())) {
			criteria.andSysShopIdEqualTo(userConsumeRequest.getSysClerkId());
		}
		Date startDate = null;
		Date endDate = null;
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			criteria.andCreateDateBetween(startDate, endDate);
		}

		// 或操作
		or.andConsumeTypeEqualTo(ConsumeTypeEnum.RECHARGE.getCode());
		or.andGoodsTypeEqualTo(GoodsTypeEnum.TIME_CARD.getCode());
		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossId())) {
			or.andSysBossIdEqualTo(userConsumeRequest.getSysBossId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysShopId())) {
			or.andSysShopIdEqualTo(userConsumeRequest.getSysShopId());
		}
		if (startDate != null && endDate != null) {
			or.andCreateDateBetween(startDate, endDate);
			recordCriteria.or(or);
		}
		List<ExpenditureAndIncomeResponseDTO> list = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(recordCriteria);
		return list;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 获取业绩的消费记录列表
	 * @Date:2018/4/26 14:52
	 */
	@Override
	public List<ExpenditureAndIncomeResponseDTO> getIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();

		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 业绩为充值类型
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.RECHARGE.getCode());

		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossId())) {
			criteria.andSysBossIdEqualTo(userConsumeRequest.getSysBossId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysShopId())) {
			criteria.andSysShopIdEqualTo(userConsumeRequest.getSysShopId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysClerkId())) {
			criteria.andSysShopIdEqualTo(userConsumeRequest.getSysClerkId());
		}
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			criteria.andCreateDateBetween(startDate, endDate);
		}

		List<ExpenditureAndIncomeResponseDTO> list = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(recordCriteria);
		return list;
	}

	@Override
	public Map<String, String> getShopConsumeAndRecharge(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		// 根据条件获取消费记录集合
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponse = shopUerConsumeRecordService
				.getShopCustomerConsumeRecordList(pageParamVoDTO);
		// 业绩
		BigDecimal recharge = null;
		BigDecimal consume = null;
		BigDecimal scratchCard = null;
		BigDecimal oneConsume = null;
		BigDecimal cardConsume = null;
		Map<String, String> map = null;
		for (UserConsumeRecordResponseDTO userConsumeRecordResponseDTO : userConsumeRecordResponse) {
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodType())) {
				if (recharge != null) {
					recharge = recharge.add(userConsumeRecordResponseDTO.getSumAmount());
				} else {
					recharge = userConsumeRecordResponseDTO.getSumAmount();
				}
			}
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& !GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodType())) {
				if (consume != null) {
					consume = consume.add(userConsumeRecordResponseDTO.getSumAmount());
				} else {
					consume = userConsumeRecordResponseDTO.getSumAmount();
				}
			}
			if (ConsumeTypeEnum.CONSUME.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.TIME_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodType())) {
				if (scratchCard != null) {
					scratchCard = scratchCard.add(userConsumeRecordResponseDTO.getSumAmount());
				} else {
					scratchCard = userConsumeRecordResponseDTO.getSumAmount();
				}
			}
			if (ConsumeTypeEnum.CONSUME.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.TIME_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodType())) {
				if (oneConsume != null) {
					oneConsume = oneConsume.add(userConsumeRecordResponseDTO.getSumAmount());
				} else {
					oneConsume = userConsumeRecordResponseDTO.getSumAmount();
				}
			}
			if (ConsumeTypeEnum.CONSUME.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodType())) {
				if (cardConsume != null) {
					cardConsume = cardConsume.add(userConsumeRecordResponseDTO.getSumAmount());
				} else {
					cardConsume = userConsumeRecordResponseDTO.getSumAmount();
				}
			}
		}
		map = new HashedMap();
		map.put("recharge", recharge == null ? "0" : recharge.toString());
		map.put("consume", consume == null ? "0" : consume.toString());
		map.put("scratchCard", scratchCard == null ? "0" : scratchCard.toString());
		map.put("oneConsume", oneConsume == null ? "0" : oneConsume.toString());
		map.put("cardConsume", cardConsume == null ? "0" : cardConsume.toString());
		return map;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getClerkAchievementList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		List<SysClerkDTO> sysClerkList = userServiceClient.getClerkInfoList(userConsumeRequest.getSysBossId(),
				pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime(), pageParamVoDTO.getPageSize());
		if (CollectionUtils.isEmpty(sysClerkList)) {
			logger.info("查询店员的结果为空");
			return null;
		}
		// 通过用户美容院id查询出来消费记录
		List<ExpenditureAndIncomeResponseDTO> incomeResponse = this.getIncomeList(pageParamVoDTO);
		// 计算业绩
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>(16);

		// 循环消费记录，将店员id作为key值
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : incomeResponse) {
			if (map.get(expenditureAndIncomeResponse.getSysShopClerkId()) == null) {
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncomeResponse);
			} else {
				ExpenditureAndIncomeResponseDTO expenditureAndIncome = map
						.get(expenditureAndIncomeResponse.getSysShopClerkId());
				BigDecimal prices = expenditureAndIncome.getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
				expenditureAndIncome.setTotalPrice(prices);
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncome);
			}
		}
		// 耗卡和服务次数
		List<ExpenditureAndIncomeResponseDTO> list2 = this.getExpenditureList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(list2)) {
			logger.info("list结果为空");
			return null;
		}

		Map<String, ExpenditureAndIncomeResponseDTO> map2 = new HashMap<>(16);
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse : list2) {
			if (map2.get(expenditureAndIncomeResponse.getSysShopClerkId()) == null) {
				expenditureAndIncomeResponse.setServiceNumber(1);
				map2.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncomeResponse);
			} else {
				ExpenditureAndIncomeResponseDTO expenditureAndIncome = map2
						.get(expenditureAndIncomeResponse.getSysShopClerkId());
				BigDecimal prices = expenditureAndIncome.getTotalPrice()
						.add(expenditureAndIncomeResponse.getTotalPrice());
				expenditureAndIncome.setTotalPrice(prices);
				expenditureAndIncome.setServiceNumber(expenditureAndIncome.getServiceNumber() + 1);
				map.put(expenditureAndIncomeResponse.getSysShopClerkId(), expenditureAndIncome);
			}

		}
		// 卡耗 的查询条件
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		criteria.andGoodsTypeEqualTo(GoodsTypeEnum.RECHARGE_CARD.getCode());
		List<ExpenditureAndIncomeResponseDTO> kahaoList = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(recordCriteria);
		Map<String, ExpenditureAndIncomeResponseDTO> map3 = new HashMap<>(16);
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : kahaoList) {
			if (map3.get(expenditureAndIncomeResponseDTO.getSysShopClerkId()) == null) {
				map3.put(expenditureAndIncomeResponseDTO.getSysShopClerkId(), expenditureAndIncomeResponseDTO);
			} else {
				ExpenditureAndIncomeResponseDTO expenditureAndIncome = map3
						.get(expenditureAndIncomeResponseDTO.getSysShopClerkId());
				BigDecimal prices = expenditureAndIncome.getTotalPrice()
						.add(expenditureAndIncomeResponseDTO.getTotalPrice());
				expenditureAndIncome.setTotalPrice(prices);
				map3.put(expenditureAndIncomeResponseDTO.getSysShopClerkId(), expenditureAndIncome);
			}
		}
		// 人次数
		ShopUserConsumeRecordCriteria timeCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria timeC = recordCriteria.createCriteria();
		List<ExpenditureAndIncomeResponseDTO> timeList = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(timeCriteria);
		Map<String, Integer> map4 = new HashMap<>(16);
		for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : timeList) {
			if (map4.get(expenditureAndIncomeResponseDTO.getSysShopClerkId()) == null) {
				map4.put(expenditureAndIncomeResponseDTO.getSysShopClerkId(), 1);
			} else {
				Integer consumeTime = map4.get(expenditureAndIncomeResponseDTO.getSysShopClerkId());
				map4.put(expenditureAndIncomeResponseDTO.getSysShopClerkId(), consumeTime + 1);
			}
		}
		// 循环店员集合
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponses = new ArrayList<>();
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = null;
		for (SysClerkDTO sysClerkDTO : sysClerkList) {
			expenditureAndIncomeResponse = new ExpenditureAndIncomeResponseDTO();
			if (map.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setIncome(map.get(sysClerkDTO.getId()).getTotalPrice());
			}
			if (map2.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setExpenditure(map2.get(sysClerkDTO.getId()).getTotalPrice());
				expenditureAndIncomeResponse.setServiceNumber(map2.get(sysClerkDTO.getId()).getServiceNumber());
			}
			if (map3.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setKahao(map3.get(sysClerkDTO.getId()).getTotalPrice());
			}
			if (map4.get(sysClerkDTO.getId()) != null) {
				expenditureAndIncomeResponse.setConsumeTime(map4.get(sysClerkDTO.getId()));
			}
			expenditureAndIncomeResponse.setSysShopClerkName(sysClerkDTO.getName());
			expenditureAndIncomeResponses.add(expenditureAndIncomeResponse);
		}
		return expenditureAndIncomeResponses;
	}

}
