package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ChannelEnum;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClerkWorkRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.*;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.*;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.JedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.*;
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

	@Resource
	private UserServiceClient userServiceClient;
	@Autowired
	private ShopBossService shopBossService;

	@Autowired
	private ShopClerkWorkService shopClerkWorkService;

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
			} else {
				if (expenditureAndIncomeResponseDTO.getTotalPrice() != null) {
					totalAmount = totalAmount.add(expenditureAndIncomeResponseDTO.getTotalPrice());
				}
			}

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
			} else {
				if (expenditureAndIncomeResponseDTO.getTotalPrice() != null) {
					totalAmount = totalAmount.add(expenditureAndIncomeResponseDTO.getTotalPrice());
				}
			}
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
				DateUtils.StrToDate(endDate, "datetime"));
		Integer consumeNumber = extShopUserConsumeRecordMapper.selectUserConsumeNumber(recordCriteria);
		return consumeNumber;
	}

	@Override
	public Map<String, Integer> getShopsConsumeNumberAndTime(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();

		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		// 设置查询条件
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		criteria.andSysBossCodeEqualTo(userConsumeRequestDTO.getSysBossCode());
		String startDate = pageParamVoDTO.getStartTime();
		String endDate = pageParamVoDTO.getEndTime();
		criteria.andCreateDateBetween(DateUtils.StrToDate(startDate, "datetime"),
				DateUtils.StrToDate(endDate, "datetime"));
		List<ExpenditureAndIncomeResponseDTO> list = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(recordCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("list结果返回集合为空");
			return null;
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("consumeTime", list.size());
		// 处理获取消费人头数
		Set<String> consumeNumberSet = new HashSet<>();
		for (ExpenditureAndIncomeResponseDTO dto : list) {
			consumeNumberSet.add(dto.getSysUserId());
		}
		map.put("consumeNumber", consumeNumberSet.size());
		return map;
	}

	@Override
	public List<ExpenditureAndIncomeResponseDTO> getExpenditureAndIncomeList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		logger.info("getPerformanceList方法传入的参数,sysShopId={}", userConsumeRequest.getSysShopId());
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
				expenditureAndIncomeResponseDTO.setTotalPrice(map.get(expenditureAndIncomeResponse.getFormateDate())
						.getTotalPrice().add(expenditureAndIncomeResponse.getTotalPrice()));
				map.put(expenditureAndIncomeResponse.getFormateDate(), expenditureAndIncomeResponseDTO);
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
					expenditureAndIncomeResponseDTO.setTotalPrice(
							map2.get(expenditure.getFormateDate()).getTotalPrice().add(expenditure.getTotalPrice()));
					map2.put(expenditure.getFormateDate(), expenditureAndIncomeResponseDTO);
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
	public Map<String, Object> getShopExpenditureAndIncomeList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		logger.info("getShopExpenditureAndIncomeList方法传入的参数,sysBossCode={},sysShopId={},startTime={},endTime={}",
				userConsumeRequest.getSysBossCode(), userConsumeRequest.getSysShopId(), pageParamVoDTO.getStartTime(),
				pageParamVoDTO.getEndTime());
		// 查询数据,获取业绩
		List<ExpenditureAndIncomeResponseDTO> incomeList = this.getIncomeList(pageParamVoDTO);
		Map<String, ExpenditureAndIncomeResponseDTO> incomeMap = null;
		if (CollectionUtils.isNotEmpty(incomeList)) {
			logger.info("incomeList集合不为空,此时开始处理业绩数据");
			incomeMap = new HashMap<>(16);
			ExpenditureAndIncomeResponseDTO incomeDto = null;
			// 遍历incomeList，此集合是消费记录，经过流水号去重后计算price和
			for (ExpenditureAndIncomeResponseDTO dto : incomeList) {
				incomeDto = new ExpenditureAndIncomeResponseDTO();
				if (!incomeMap.containsKey(dto.getSysShopId())) {
					// 如果incomeMap中的key没有shopId,则直接将业绩值放入value
					incomeMap.put(dto.getSysShopId(), dto);
				} else {
					// 取出key是ship的值，计算value中的值,并判断是否为空
					if (incomeMap.get(dto.getSysShopId()).getTotalPrice() != null && dto.getTotalPrice() != null) {
						// 取出key是ship的值，计算value中的值
						incomeDto.setTotalPrice(
								incomeMap.get(dto.getSysShopId()).getTotalPrice().add(dto.getTotalPrice()));
						incomeMap.put(dto.getSysShopId(), incomeDto);
					}
				}
			}
		}

		// 获取耗卡
		List<ExpenditureAndIncomeResponseDTO> expenditureList = this.getExpenditureList(pageParamVoDTO);
		Map<String, ExpenditureAndIncomeResponseDTO> expenditureMap = null;
		if (CollectionUtils.isNotEmpty(expenditureList)) {
			logger.info("expenditureList集合不为空,此时开始处理业绩数据");
			expenditureMap = new HashMap<>(16);
			ExpenditureAndIncomeResponseDTO expenditureDTO = null;
			for (ExpenditureAndIncomeResponseDTO expenditure : expenditureList) {
				expenditureDTO = new ExpenditureAndIncomeResponseDTO();
				if (expenditureMap.get(expenditure.getSysShopId()) == null) {
					// 如果map中的key没有shopId,则直接将业绩值放入value
					expenditureMap.put(expenditure.getSysShopId(), expenditure);
				} else {
					// 取出key是ship的值，计算value中的值
					if (expenditureMap.get(expenditure.getSysShopId()) != null
							&& expenditureMap.get(expenditure.getSysShopId()).getTotalPrice() != null
							&& expenditure.getTotalPrice() != null) {
						expenditureDTO.setTotalPrice(expenditureMap.get(expenditure.getSysShopId()).getTotalPrice()
								.add(expenditure.getTotalPrice()));
						expenditureMap.put(expenditure.getSysShopId(), expenditureDTO);
					}
				}
			}
		}
		ExpenditureAndIncomeResponseDTO response = null;
		// 获取bossid下的所有美容店
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
		shopBossRelationDTO.setSysBossCode(userConsumeRequest.getSysBossCode());
		List<ShopBossRelationDTO> shopBossRelationList = shopBossService.shopBossRelationList(shopBossRelationDTO);
		List<ExpenditureAndIncomeResponseDTO> responsesList = new ArrayList<>();
		// 所有美容院业绩总计
		BigDecimal allIncome = null;
		// 所有美容店耗卡总计
		BigDecimal allExpenditure = null;
		for (ShopBossRelationDTO shopBossRelation : shopBossRelationList) {
			response = new ExpenditureAndIncomeResponseDTO();
			if (incomeMap != null) {
				if (incomeMap.get(shopBossRelation.getSysShopId()) != null) {
					response.setIncome(incomeMap.get(shopBossRelation.getSysShopId()).getTotalPrice());
				}
			}
			if (expenditureMap != null) {
				if (expenditureMap.get(shopBossRelation.getSysShopId()) != null) {
					response.setExpenditure(expenditureMap.get(shopBossRelation.getSysShopId()).getTotalPrice());
				}
			}
			response.setSysShopId(shopBossRelation.getSysShopId());
			response.setSysShopName(shopBossRelation.getSysShopName());
			responsesList.add(response);
			if (allIncome == null) {
				allIncome = response.getIncome();
			} else {
				allIncome = allIncome.add(response.getIncome());
			}
			if (allExpenditure == null) {
				allExpenditure = response.getExpenditure();
			} else {
				allExpenditure = allExpenditure.add(response.getExpenditure());
			}
		}
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("responsesList", responsesList);
		responseMap.put("allIncome", allIncome);
		responseMap.put("allExpenditure", allExpenditure);
		return responseMap;
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
		ResponseDTO<List<SysClerkDTO>> listResponseDTO = userServiceClient.getClerkInfoList(
				userConsumeRequest.getSysBossCode(), pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime(),
				pageParamVoDTO.getPageSize());
		List<SysClerkDTO> sysClerkList = listResponseDTO.getResponseData();
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
			expenditureAndIncomeResponse.setSysShopClerkId(sysClerkDTO.getId());
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
		if (userConsumeRequest == null) {
			logger.info("getExpenditureList方法出入的参数userConsumeRequest对象为空");
			return null;
		}
		ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
		ShopUserConsumeRecordCriteria.Criteria or = recordCriteria.createCriteria();
		// 耗卡归为消费类
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());

		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossCode())) {
			criteria.andSysBossCodeEqualTo(userConsumeRequest.getSysBossCode());
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
		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossCode())) {
			or.andSysBossCodeEqualTo(userConsumeRequest.getSysBossCode());
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

		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossCode())) {
			criteria.andSysBossCodeEqualTo(userConsumeRequest.getSysBossCode());
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
		List<ShopUserConsumeRecordDTO> shopUserConsumeRecordList = shopUerConsumeRecordService
				.getShopCustomerConsumeRecord(pageParamVoDTO);
		// 业绩
		BigDecimal recharge = null;
		BigDecimal consume = null;
		BigDecimal scratchCard = null;
		BigDecimal oneConsume = null;
		BigDecimal cardConsume = null;
		Map<String, String> map = null;
		if (CollectionUtils.isNotEmpty(shopUserConsumeRecordList)) {
			for (ShopUserConsumeRecordDTO dto : shopUserConsumeRecordList) {
				// 业绩 ---充值金额
				if (ConsumeTypeEnum.RECHARGE.getCode().equals(dto.getConsumeType())
						&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(dto.getGoodsType())) {
					if (recharge != null) {
						recharge = recharge.add(dto.getPrice());
					} else {
						recharge = dto.getPrice();
					}
				}
				// 业绩 ---消费金额
				if (ConsumeTypeEnum.RECHARGE.getCode().equals(dto.getConsumeType())
						&& !GoodsTypeEnum.RECHARGE_CARD.getCode().equals(dto.getGoodsType())) {
					if (consume != null) {
						consume = consume.add(dto.getPrice());
					} else {
						consume = dto.getPrice();
					}
				}
				// 耗卡 --- 划卡金额(疗程卡和套卡)
				if (ConsumeTypeEnum.CONSUME.getCode().equals(dto.getConsumeType())) {
					if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(dto.getGoodsType())
							|| GoodsTypeEnum.COLLECTION_CARD.getCode().equals(dto.getGoodsType())) {
						if (scratchCard != null) {
							scratchCard = scratchCard.add(dto.getPrice());
						} else {
							scratchCard = dto.getPrice();
						}
					}
				}
				// 耗卡 --- 单次消费
				if (ConsumeTypeEnum.RECHARGE.getCode().equals(dto.getConsumeType())
						&& GoodsTypeEnum.TIME_CARD.getCode().equals(dto.getGoodsType())) {
					if (oneConsume != null) {
						oneConsume = oneConsume.add(dto.getPrice());
					} else {
						oneConsume = dto.getPrice();
					}
				}
				// 卡耗 --- 单次消费
				if (ConsumeTypeEnum.CONSUME.getCode().equals(dto.getConsumeType())
						&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(dto.getGoodsType())) {
					if (cardConsume != null) {
						cardConsume = cardConsume.add(dto.getPrice());
					} else {
						cardConsume = dto.getPrice();
					}
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
		// 查询时间段的店员
		PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParam = new PageParamVoDTO();
		ShopClerkWorkRecordRequestDTO shopClerkWorkRecordRequestDTO = new ShopClerkWorkRecordRequestDTO();
		shopClerkWorkRecordRequestDTO.setSysShopId(userConsumeRequest.getSysShopId());
		pageParam.setRequestData(shopClerkWorkRecordRequestDTO);
		pageParam.setStartTime(pageParamVoDTO.getStartTime());
		pageParam.setEndTime(pageParamVoDTO.getEndTime());
		List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs = shopClerkWorkService.getClerkWorkRecordList(pageParam);

		if (CollectionUtils.isEmpty(shopClerkWorkRecordDTOs)) {
			logger.info("查询店员的结果为空");
			return null;
		}
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponseDTOs = new ArrayList<>();
		List<String> clerkIds = new ArrayList<>();
		for (ShopClerkWorkRecordDTO shopClerkWorkRecord : shopClerkWorkRecordDTOs) {
			clerkIds.add(shopClerkWorkRecord.getSysClerkId());
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			expenditureAndIncomeResponseDTO.setSysShopClerkId(shopClerkWorkRecord.getSysClerkId());
			// 业绩 ---充值金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecord.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())) {
				if (shopClerkWorkRecord.getPrice() != null) {
					expenditureAndIncomeResponseDTO
							.setIncome(expenditureAndIncomeResponseDTO.getIncome().add(shopClerkWorkRecord.getPrice()));
				}
			}
			// 业绩 ---消费金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecord.getConsumeType())
					&& !GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())) {
				if (shopClerkWorkRecord.getPrice() != null) {
					expenditureAndIncomeResponseDTO
							.setIncome(expenditureAndIncomeResponseDTO.getIncome().add(shopClerkWorkRecord.getPrice()));
				}
			}
			// 耗卡 --- 划卡金额(疗程卡和套卡)
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecord.getConsumeType())) {
				if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())
						|| GoodsTypeEnum.COLLECTION_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())) {
					if (shopClerkWorkRecord.getPrice() != null) {
						expenditureAndIncomeResponseDTO.setExpenditure(
								expenditureAndIncomeResponseDTO.getExpenditure().add(shopClerkWorkRecord.getPrice()));
					}
				}
			}
			// 耗卡 --- 单次消费
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecord.getConsumeType())
					&& GoodsTypeEnum.TIME_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())) {
				if (shopClerkWorkRecord.getPrice() != null) {
					expenditureAndIncomeResponseDTO.setExpenditure(
							expenditureAndIncomeResponseDTO.getExpenditure().add(shopClerkWorkRecord.getPrice()));
				}
			}
			// 卡耗 --- 单次消费
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecord.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecord.getGoodsType())) {
				if (shopClerkWorkRecord.getPrice() != null) {
					expenditureAndIncomeResponseDTO
							.setKahao(expenditureAndIncomeResponseDTO.getKahao().add(shopClerkWorkRecord.getPrice()));
				}
			}
			expenditureAndIncomeResponseDTOs.add(expenditureAndIncomeResponseDTO);
		}
		// 处理下expenditureAndIncomeResponseDTOs，相同的店员
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashMap<>();
		for (ExpenditureAndIncomeResponseDTO dto : expenditureAndIncomeResponseDTOs) {
			if (map.containsKey(dto.getSysShopClerkId())) {
				ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = map.get(dto.getSysShopClerkId());
				expenditureAndIncomeResponse
						.setExpenditure(expenditureAndIncomeResponse.getExpenditure().add(dto.getExpenditure()));
				expenditureAndIncomeResponse.setIncome(expenditureAndIncomeResponse.getIncome().add(dto.getIncome()));
				expenditureAndIncomeResponse.setKahao(expenditureAndIncomeResponse.getKahao().add(dto.getKahao()));
			} else {
				map.put(dto.getSysShopClerkId(), dto);
			}

		}
		// 根据多个clerkId查询
		ResponseDTO<List<SysClerkDTO>> responseDTO = userServiceClient.getClerkInfoListByClerkIds(clerkIds);
		Map<String, String> clerkMap = new HashMap<>();
		if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getResponseData())) {
			for (SysClerkDTO sysClerkDTO : responseDTO.getResponseData()) {
				clerkMap.put(sysClerkDTO.getId(), sysClerkDTO.getName());
			}
		}
		// 遍历map
		List<ExpenditureAndIncomeResponseDTO> reponseList = new ArrayList<>();
		for (Map.Entry<String, ExpenditureAndIncomeResponseDTO> entry : map.entrySet()) {
			ExpenditureAndIncomeResponseDTO dto = entry.getValue();
			dto.setSysShopClerkName(clerkMap.get(dto.getSysShopClerkId()));
			reponseList.add(dto);
		}
		return reponseList;
	}

	@Override
	public Map<String, Object> getCustomerArriveList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
		if (userConsumeRequestDTO == null) {
			logger.info("getCustomerArriveList方法传入的userConsumeRequestDTO对象为空");
		}
		// boss下所有的店
		ShopBossRelationDTO shopBossRelationDTO = new ShopBossRelationDTO();
		if (null != userConsumeRequestDTO && StringUtils.isNotBlank(userConsumeRequestDTO.getSysBossCode())) {
			shopBossRelationDTO.setSysBossCode(userConsumeRequestDTO.getSysBossCode());
		}

		List<ShopBossRelationDTO> shopBossRelationList = shopBossService.shopBossRelationList(shopBossRelationDTO);
		if (CollectionUtils.isEmpty(shopBossRelationList)) {
			return null;
		}
		// 人头数,需要处理去重
		ShopUserConsumeRecordCriteria numberCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria criteria = numberCriteria.createCriteria();
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			criteria.andCreateDateBetween(startTime, endTime);
		}
		criteria.andSysBossCodeEqualTo(userConsumeRequestDTO.getSysBossCode());
		criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		List<ExpenditureAndIncomeResponseDTO> consumeNumberList = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(numberCriteria);
		Map<String, ExpenditureAndIncomeResponseDTO> map = null;
		if (CollectionUtils.isNotEmpty(consumeNumberList)) {
			map = new HashedMap(16);
			for (ExpenditureAndIncomeResponseDTO dto : consumeNumberList) {
				if (map.get(dto.getSysShopId()) == null) {
					ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = new ExpenditureAndIncomeResponseDTO();
					expenditureAndIncomeResponse.setConsumeNumber(1);
					expenditureAndIncomeResponse.setSysUserId(dto.getSysUserId());
					map.put(dto.getSysShopId(), expenditureAndIncomeResponse);
				} else {
					ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponse = map.get(dto.getSysShopId());
					if (!dto.getSysUserId().equals(expenditureAndIncomeResponse.getSysUserId())) {
						expenditureAndIncomeResponse
								.setConsumeNumber(expenditureAndIncomeResponse.getConsumeNumber() + 1);
						map.put(dto.getSysShopId(), expenditureAndIncomeResponse);
					}

				}
			}

		}

		// 人次数
		ShopUserConsumeRecordCriteria timeCriteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = timeCriteria.createCriteria();
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			c.andCreateDateBetween(startTime, endTime);
		}
		c.andSysBossCodeEqualTo(userConsumeRequestDTO.getSysBossCode());
		c.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
		List<ExpenditureAndIncomeResponseDTO> timeList = extShopUserConsumeRecordMapper
				.selectPriceListByCriteria(timeCriteria);
		Map<String, Integer> timeMap = null;
		if (CollectionUtils.isNotEmpty(timeList)) {
			timeMap = new HashedMap(16);
			for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : timeList) {
				if (timeMap.get(expenditureAndIncomeResponseDTO.getSysShopId()) == null) {
					timeMap.put(expenditureAndIncomeResponseDTO.getSysShopId(), 1);
				} else {
					Integer consumeTime = 0;
					if (timeMap != null) {
						consumeTime = timeMap.get(expenditureAndIncomeResponseDTO.getSysShopId());
					}
					timeMap.put(expenditureAndIncomeResponseDTO.getSysShopId(), consumeTime + 1);
				}
			}
		}
		// 新客
		PageParamVoDTO<ShopUserArchivesDTO> shopCustomerArchivesDTO = new PageParamVoDTO();
		ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
		shopUserArchivesDTO.setSysBossCode(userConsumeRequestDTO.getSysBossCode());
		shopCustomerArchivesDTO.setRequestData(shopUserArchivesDTO);
		shopCustomerArchivesDTO.setStartTime(pageParamVoDTO.getStartTime());
		shopCustomerArchivesDTO.setEndTime(pageParamVoDTO.getEndTime());
		List<ShopUserArchivesDTO> list = shopCustomerArchivesService.getArchivesList(shopCustomerArchivesDTO);
		Map<String, Integer> newCustomerMap = new HashedMap(16);
		for (ShopUserArchivesDTO shopUserArchives : list) {
			if (newCustomerMap.get(shopUserArchives.getSysShopId()) == null) {
				newCustomerMap.put(shopUserArchives.getSysShopId(), 1);
			} else {
				Integer consumeTime = newCustomerMap.get(shopUserArchives.getSysShopId());
				newCustomerMap.put(shopUserArchives.getSysShopId(), consumeTime + 1);
			}
		}
		// 遍历shopBossRelationList
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		List<ExpenditureAndIncomeResponseDTO> responseDTOList = new ArrayList<>();
		Integer totalConsumeNumber = null;
		Integer totalShopNewUserNumber = null;
		Integer totalConsumeTime = null;
		for (ShopBossRelationDTO shopBossRelation : shopBossRelationList) {
			expenditureAndIncomeResponseDTO = new ExpenditureAndIncomeResponseDTO();
			expenditureAndIncomeResponseDTO.setSysShopName(shopBossRelation.getSysShopName());// 美容院店名字
			expenditureAndIncomeResponseDTO.setShopNewUserNumber(newCustomerMap.get(shopBossRelation.getSysShopId()));// 新客
			if (map != null && map.get(shopBossRelation.getSysShopId()) != null) {
				expenditureAndIncomeResponseDTO
						.setConsumeNumber(map.get(shopBossRelation.getSysShopId()).getConsumeNumber());// 人头数
			}
			if (timeMap != null) {
				expenditureAndIncomeResponseDTO.setConsumeTime(timeMap.get(shopBossRelation.getSysShopId()));// 次数
			}
			expenditureAndIncomeResponseDTO.setSysShopId(shopBossRelation.getSysShopId());
			if (totalConsumeNumber == null) {
				totalConsumeNumber = map.get(shopBossRelation.getSysShopId()).getConsumeNumber();
			} else {
				if (map.get(shopBossRelation.getSysShopId()) != null) {
					totalConsumeNumber = totalConsumeNumber
							+ map.get(shopBossRelation.getSysShopId()).getConsumeNumber();
				}
			}
			if (totalShopNewUserNumber == null) {
				totalShopNewUserNumber = newCustomerMap.get(shopBossRelation.getSysShopId());
			} else {
				totalShopNewUserNumber = totalShopNewUserNumber + newCustomerMap.get(shopBossRelation.getSysShopId());
			}
			if (totalConsumeTime == null) {
				totalConsumeTime = timeMap.get(shopBossRelation.getSysShopId());
			} else {
				if (timeMap.get(shopBossRelation.getSysShopId()) != null) {
					totalConsumeTime = totalConsumeTime + timeMap.get(shopBossRelation.getSysShopId());
				}
			}
			responseDTOList.add(expenditureAndIncomeResponseDTO);
		}
		Map<String, Object> responseMap = new HashedMap();
		responseMap.put("totalConsumeNumber", totalConsumeNumber);
		responseMap.put("totalShopNewUserNumber", totalShopNewUserNumber);
		responseMap.put("totalConsumeTime", totalConsumeTime);
		responseMap.put("responseDTOList", responseDTOList);
		return responseMap;
	}

	@Override
	public Map<String, Object> getShopCustomerArriveList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO,
			String condition) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
		if (userConsumeRequestDTO == null) {
			logger.info("getCustomerArriveList方法传入的userConsumeRequestDTO对象为空");
		}
		Set<String> userIds = new HashSet<>();
		List<ExpenditureAndIncomeResponseDTO> list = null;
		// 人头数
		Map<String, ExpenditureAndIncomeResponseDTO> map = new HashedMap();
		if ("1".equals(condition)) {
			ShopUserConsumeRecordCriteria numberCriteria = new ShopUserConsumeRecordCriteria();
			ShopUserConsumeRecordCriteria.Criteria numberC = numberCriteria.createCriteria();
			if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
					&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
				Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
				Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
				numberC.andCreateDateBetween(startTime, endTime);
			}
			numberC.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
			// 根据时间排序，降序
			numberCriteria.setOrderByClause("create_date  desc");
			list = extShopUserConsumeRecordMapper.selectPriceListByCriteria(numberCriteria);

			for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : list) {
				userIds.add(expenditureAndIncomeResponseDTO.getSysUserId());
				if (map.get(expenditureAndIncomeResponseDTO.getSysUserId()) == null) {
					map.put(expenditureAndIncomeResponseDTO.getSysUserId(), expenditureAndIncomeResponseDTO);
				}
			}
		}

		// 人次数
		if ("2".equals(condition)) {
			ShopUserConsumeRecordCriteria timeCriteria = new ShopUserConsumeRecordCriteria();
			ShopUserConsumeRecordCriteria.Criteria timeC = timeCriteria.createCriteria();
			// 根据时间排序，降序
			timeCriteria.setOrderByClause("create_date  desc");
			if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
					&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
				Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
				Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
				timeC.andCreateDateBetween(startTime, endTime);
			}
			timeC.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
			list = extShopUserConsumeRecordMapper.selectPriceListByCriteria(timeCriteria);

			for (ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO : list) {
				userIds.add(expenditureAndIncomeResponseDTO.getSysUserId());
				if (map.get(expenditureAndIncomeResponseDTO.getSysUserId()) == null) {
					expenditureAndIncomeResponseDTO.setUseArriveShopTime(1);
					map.put(expenditureAndIncomeResponseDTO.getSysUserId(), expenditureAndIncomeResponseDTO);
				} else {
					Integer useArriveShopTime = map.get(expenditureAndIncomeResponseDTO.getSysUserId())
							.getUseArriveShopTime() + 1;
					ExpenditureAndIncomeResponseDTO expenditureAndIncomeRespons = map
							.get(expenditureAndIncomeResponseDTO.getSysUserId());
					expenditureAndIncomeRespons.setUseArriveShopTime(useArriveShopTime);
					map.put(expenditureAndIncomeResponseDTO.getSysUserId(), expenditureAndIncomeRespons);
				}
			}
		}

		// 新客
		List<ShopUserArchivesDTO> archivesList = null;
		if ("3".equals(condition)) {
			PageParamVoDTO<ShopUserArchivesDTO> shopCustomerArchivesDTO = new PageParamVoDTO();
			ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
			shopUserArchivesDTO.setSysShopId(userConsumeRequestDTO.getSysShopId());
			shopCustomerArchivesDTO.setRequestData(shopUserArchivesDTO);
			shopCustomerArchivesDTO.setStartTime(pageParamVoDTO.getStartTime());
			shopCustomerArchivesDTO.setEndTime(pageParamVoDTO.getEndTime());
			// 查询出来新客列表
			archivesList = shopCustomerArchivesService.getArchivesList(shopCustomerArchivesDTO);
			// 遍历新客列表，将渠道作为key ,计算出每个渠道的人数
			List<ShopChannelResponseDTO> shopChannelResponseList = new ArrayList<>();
			Map<String, ShopChannelResponseDTO> channelMap = new HashMap<>();
			ShopChannelResponseDTO shopChannelResponseDTO = null;
			for (ShopUserArchivesDTO dto : archivesList) {
				if (channelMap.containsKey(dto.getChannel())) {
					shopChannelResponseDTO = channelMap.get(dto.getChannel());
					shopChannelResponseDTO.setChannelPeopleNumber(1 + shopChannelResponseDTO.getChannelPeopleNumber());
					DecimalFormat fnum = new DecimalFormat("##0");
					String dd = fnum.format(Float.valueOf(shopChannelResponseDTO.getChannelPeopleNumber())
							/ Float.valueOf(archivesList.size()) * 100);
					shopChannelResponseDTO.setChannelPeopleProportionr(Integer.valueOf(dd));
					channelMap.put(dto.getChannel(), shopChannelResponseDTO);

				} else {
					shopChannelResponseDTO = new ShopChannelResponseDTO();
					if(ChannelEnum.judgeValue(dto.getChannel())!=null){
						shopChannelResponseDTO.setChannelName(ChannelEnum.judgeValue(dto.getChannel()).getDesc());
					}
					shopChannelResponseDTO.setChannelPeopleNumber(1);
					DecimalFormat fnum = new DecimalFormat("##0");
					String dd = fnum.format(1f / Float.valueOf(archivesList.size()) * 100);
					shopChannelResponseDTO.setChannelPeopleProportionr(Integer.valueOf(dd));
					channelMap.put(dto.getChannel(), shopChannelResponseDTO);
				}
			}
			for (Map.Entry<String, ShopChannelResponseDTO> entry : channelMap.entrySet()) {
				shopChannelResponseList.add(entry.getValue());

			}
			Map<String, Object> responseMap = new HashedMap();
			responseMap.put("shopNewUserNumber", archivesList == null ? 0 : archivesList.size());// 新客
			responseMap.put("shopChannelResponseList", shopChannelResponseList);
			return responseMap;
		}
		// 查询档案列表
		List<String> userIdList = new ArrayList<>(userIds);
		List<ShopUserArchivesDTO> shopUserArchivesDTOs = shopCustomerArchivesService.getArchivesList(userIdList);
		// 遍历userInfoList
		ExpenditureAndIncomeResponseDTO expenditureAndIncomeResponseDTO = null;
		List<UserInfoDTOResponseDTO> responseDTOList = new ArrayList<>();
		UserInfoDTOResponseDTO userInfoDTOResponseDTO = null;
		if (CollectionUtils.isNotEmpty(shopUserArchivesDTOs)) {
			for (ShopUserArchivesDTO shopUserArchivesDTO : shopUserArchivesDTOs) {
				userInfoDTOResponseDTO = new UserInfoDTOResponseDTO();
				userInfoDTOResponseDTO.setPhoto(shopUserArchivesDTO.getImageUrl());
				userInfoDTOResponseDTO.setNickname(shopUserArchivesDTO.getSysUserName());
				userInfoDTOResponseDTO.setLastArriveTime(map.get(shopUserArchivesDTO.getSysUserId()).getCreateDate());
				// 查询redis是否存在记录,key是 "arrive"+shopId+userId
				String key = "arrive" + shopUserArchivesDTO.getSysShopId() + shopUserArchivesDTO.getSysUserId();
				String date1 = (String) JedisUtils.getObject(key);
				if (StringUtils.isBlank(date1)) {
					userInfoDTOResponseDTO.setNewUser(true);
				} else {
					// 判断redis中获取到的时间是否大于当前时间
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						Date dt1 = df.parse(date1);
						Date dt2 = df.parse(pageParamVoDTO.getStartTime());
						if (dt1.getTime() > dt2.getTime()) {
							userInfoDTOResponseDTO.setNewUser(true);
						} else {
							userInfoDTOResponseDTO.setNewUser(false);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				userInfoDTOResponseDTO
						.setUseArriveShopTime(map.get(shopUserArchivesDTO.getSysUserId()).getUseArriveShopTime());
				responseDTOList.add(userInfoDTOResponseDTO);
			}
		}
		Map<String, Object> responseMap = new HashedMap();
		responseMap.put("responseDTOList", responseDTOList);
		responseMap.put("consumeNumber", userIds.size());// 人头数
		responseMap.put("consumeTime", list == null ? 0 : list.size());// 人次数

		return responseMap;
	}

}
