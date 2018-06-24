package com.wisdom.beauty.controller.work;

import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClerkWorkRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopClerkWorkRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopClerkWorkService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopMemberAttendacneDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@LoginAnnotations
@RequestMapping(value = "work")
public class ShopMemberAttendanceController {

	@Resource
	private RedisUtils redisUtils;

	@Autowired
	private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

	@Autowired
	private ShopClerkWorkService shopClerkWorkService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	// 获取门店某天的业绩
	@RequestMapping(value = "shopMemberAttendanceAnalyzeByDate", method = { RequestMethod.POST, RequestMethod.GET })
	@LoginRequired
	public @ResponseBody ResponseDTO<List<ShopMemberAttendacneDTO>> shopMemberAttendanceAnalyzeByDate(
			@RequestParam String shopId, @RequestParam String date) {
		ResponseDTO<List<ShopMemberAttendacneDTO>> responseDTO = new ResponseDTO<>();

		return responseDTO;
	}

	/**
	 * @Author: zhanghuan
	 * @Param: 美容院id ，startTime(2018-04-10 00:00:00)，endTime(2018-04-10 00:00:00)
	 * @Return:
	 * @Description: 根据时间查询某个美容院的耗卡和业绩
	 * @Date:2018/4/23 16:07
	 */
	@RequestMapping(value = "/getExpenditureAndIncome", method = { RequestMethod.GET })
	@ResponseBody
	ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getExpenditureAndIncome(
			@RequestParam(required = false) String sysShopId) {
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
		sysShopId = redisUtils.getShopId();
		userConsumeRequest.setSysShopId(sysShopId);

		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		userConsumeRequest.setSysBossCode(sysBossDTO.getId());
		pageParamVoDTO.setRequestData(userConsumeRequest);
		List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService
				.getExpenditureAndIncomeList(pageParamVoDTO);
		ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
		response.setResponseData(list);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}

	/**
	 * @Author:zhanghuan
	 * @Param: bossId，consumeType 消费类型
	 * @Return:
	 * @Description: 获取老板下所有美容院的列表的业绩和耗卡以及所有美容院业绩和耗卡的总和
	 * @Date:2018/4/24 11:08
	 */
	@RequestMapping(value = "/getBossExpenditureAndIncome", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Map<String, Object>> getBossExpenditureAndIncome(@RequestParam String startTime,
			@RequestParam String endTime) {
		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
		userConsumeRequest.setSysBossCode(sysBossDTO.getSysBossCode());

		pageParamVoDTO.setRequestData(userConsumeRequest);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		Map<String, Object> map = shopStatisticsAnalysisService.getShopExpenditureAndIncomeList(pageParamVoDTO);
		ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
		response.setResponseData(map);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return: map key：recharge=充值 key: consume=消费
	 * @Description: 获取某个美容院的业绩信息(充值和消费) 充值 goodsType=2 消费 goodsType!=2
	 * @Date:2018/4/24 11:08
	 */
	@RequestMapping(value = "/getShopConsumeAndRecharge", method = { RequestMethod.GET })
	@ResponseBody
	ResponseDTO<Map<String, String>> getShopConsumeAndRecharge(@RequestParam String shopId,
			@RequestParam String startTime, @RequestParam String endTime) {

		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
		userConsumeRequest.setSysShopId(shopId);

		pageParamVoDTO.setRequestData(userConsumeRequest);
		Map<String, String> map = shopStatisticsAnalysisService.getShopConsumeAndRecharge(pageParamVoDTO);
		BigDecimal income = new BigDecimal(map.get("consume")).add(new BigDecimal(map.get("recharge")));
		BigDecimal expenditure = new BigDecimal(map.get("oneConsume")).add(new BigDecimal(map.get("scratchCard")));
		BigDecimal kahao = new BigDecimal(map.get("cardConsume"));

		map.put("income", income.toString());
		map.put("expenditure", expenditure.toString());
		map.put("kahao", kahao.toString());
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		response.setResponseData(map);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 获取店员成绩
	 * @Date:2018/4/27 18:26
	 */
	@RequestMapping(value = "/getClerkAchievement", method = { RequestMethod.GET })
	@ResponseBody
	@LoginRequired
	ResponseDTO<Map<String, String>> getClerkAchievement() {

		SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
		if (sysClerkDTO == null) {
			return null;
		}
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
		userConsumeRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
		userConsumeRequestDTO.setSysClerkId(sysClerkDTO.getId());
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		String startTime = DateUtils.getStartTime();
		String endTime = DateUtils.getEndTime();
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		BigDecimal income = shopStatisticsAnalysisService.getPerformance(pageParamVoDTO);
		BigDecimal expenditure = shopStatisticsAnalysisService.getExpenditure(pageParamVoDTO);
		Integer consumeNumber = shopStatisticsAnalysisService.getUserConsumeNumber(sysClerkDTO.getId(), startTime,
				endTime);
		Integer shopNewUserNumber = shopStatisticsAnalysisService.getShopNewUserNumber(pageParamVoDTO);

		Map<String, String> map = new HashMap<>(16);
		map.put("income", income == null ? "0" : income.toString());
		map.put("expenditure", expenditure == null ? "0" : expenditure.toString());
		map.put("consumeNumber", consumeNumber.toString());
		map.put("shopNewUserNumber", shopNewUserNumber.toString());
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		response.setResponseData(map);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 获取当前美容院当前boss的当前家人列表
	 * @Date:2018/5/2 9:40
	 */
	@RequestMapping(value = "/getFamilyList", method = { RequestMethod.GET })
	@ResponseBody
	ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getFamilyList(@RequestParam String startTime,
																	 @RequestParam String endTime,
																	 @RequestParam String sysShopId,
																	 @RequestParam int pageSize) {

		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		if (sysBossDTO == null) {
			logger.info("redis获取boos对象sysBossDTO为空");
			return null;
		}
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
		userConsumeRequestDTO.setSysBossCode(sysBossDTO.getId());
		userConsumeRequestDTO.setSysShopId(sysShopId);
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		pageParamVoDTO.setPaging(true);
		pageParamVoDTO.setPageSize(pageSize);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponse = shopStatisticsAnalysisService.getClerkAchievementList(pageParamVoDTO);

		ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
		response.setResponseData(expenditureAndIncomeResponse);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}

	/**
	 * @Author:zhanghuan
	 * @Return:
	 * @Param: searchFile 1 查业绩明细 searchFile 2 耗卡明细 searchFile 3 卡耗明细
	 * @Description: 获取boss的业绩,耗卡,卡耗明细 业绩明细: consumeType 0 goodsType 2 : consumeType
	 *               0 goodsType 0 1 3 4 耗卡明细:consumeType 1 goodsType 1 consumeType
	 *               1 goodsType 3 consumeType 0 goodsType 0 卡耗明细:consumeType 1
	 *               goodsType2
	 * @Date:2018/5/7 15:35
	 */
	@RequestMapping(value = "/getBossPerformanceList", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<List<ShopClerkWorkRecordResponseDTO>> getBossPerformanceList(@RequestParam String startTime,
																			 @RequestParam String endTime,
																			 @RequestParam String searchFile,
																			 @RequestParam String sysShopId, int pageSize) {
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();

		userConsumeRequestDTO.setSysShopId(sysShopId);
		// 设置为true 这样需要通过consumeType和goodType做为条件来查询
		//userConsumeRequestDTO.setGoodsTypeRequire(true);
/*		if ("1".equals(searchFile)) {
			List<String> consumeType = new ArrayList<>();
			consumeType.add(ConsumeTypeEnum.RECHARGE.getCode());
			userConsumeRequestDTO.setConsumeTypeList(consumeType);
			List<String> goodsType = new ArrayList<>();
			goodsType.add(GoodsTypeEnum.TIME_CARD.getCode());
			goodsType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
			goodsType.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
			goodsType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
			goodsType.add(GoodsTypeEnum.PRODUCT.getCode());
			userConsumeRequestDTO.setGoodsTypeList(goodsType);
		}
		if ("2".equals(searchFile)) {
			userConsumeRequestDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
			List<String> goodsType = new ArrayList<>();
			goodsType.add(GoodsTypeEnum.TIME_CARD.getCode());
			goodsType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
			goodsType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
			userConsumeRequestDTO.setGoodsTypeList(goodsType);
			List<String> consumeType = new ArrayList<>();
			consumeType.add(ConsumeTypeEnum.RECHARGE.getCode());
			consumeType.add(ConsumeTypeEnum.CONSUME.getCode());
			userConsumeRequestDTO.setConsumeTypeList(consumeType);
		}
		if ("3".equals(searchFile)) {
			userConsumeRequestDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
			List<String> goodsType = new ArrayList<>();
			goodsType.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
			userConsumeRequestDTO.setGoodsTypeList(goodsType);
			List<String> consumeType = new ArrayList<>();
			consumeType.add(ConsumeTypeEnum.CONSUME.getCode());
			userConsumeRequestDTO.setConsumeTypeList(consumeType);
		}*/
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
		userConsumeRequestDTO.setSearchFile(searchFile);
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		pageParamVoDTO.setPaging(true);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setPageSize(pageSize);
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);
		List<ShopClerkWorkRecordResponseDTO> shopClerkWorkRecordResponseDTOs = shopStatisticsAnalysisService
				.getShopMoneyConsumeDetail(pageParamVoDTO);

		ResponseDTO<List<ShopClerkWorkRecordResponseDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopClerkWorkRecordResponseDTOs);
		return responseDTO;
	}

	/**
	 * @Author:zhanghuan
	 * @Param:
	 * @Return:
	 * @Description: 获取boss成绩
	 * @Date:2018/4/27 18:26
	 */
	@RequestMapping(value = "/getBossAchievement", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Map<String, String>> getBossAchievement() {

		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		if (sysBossDTO == null) {
			logger.info("redis获取sysBossDTO信息为空");
			return null;
		}
		PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
		UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
		userConsumeRequestDTO.setSysBossCode(sysBossDTO.getSysBossCode());
		pageParamVoDTO.setRequestData(userConsumeRequestDTO);
		String startTime = DateUtils.getStartTime();
		String endTime = DateUtils.getEndTime();
		pageParamVoDTO.setStartTime(startTime);
		pageParamVoDTO.setEndTime(endTime);

		Integer shopNewUserNumber = shopStatisticsAnalysisService.getShopNewUserNumber(pageParamVoDTO);
		Map<String, Integer> consumeNumberAndTimeMap = shopStatisticsAnalysisService
				.getShopsConsumeNumberAndTime(pageParamVoDTO);
		// 服务次数 划卡消费+单次的次数
		List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getExpenditureList(pageParamVoDTO);
		BigDecimal income = shopStatisticsAnalysisService.getPerformance(pageParamVoDTO);
		BigDecimal expenditure = shopStatisticsAnalysisService.getExpenditure(pageParamVoDTO);
		Map<String, String> map = new HashMap<>(16);
		// 业绩
		map.put("income", income == null ? "0" : income.toString());
		// 耗卡
		map.put("expenditure", expenditure == null ? "0" : expenditure.toString());
		// 消费次数(人次数)
		map.put("consumeTime", consumeNumberAndTimeMap==null||consumeNumberAndTimeMap.get("consumeTime") == null ? "0"
				: consumeNumberAndTimeMap.get("consumeTime").toString());
		// 服务次数
		map.put("serviceNumber", CollectionUtils.isEmpty(list) ? "0" : String.valueOf(list.size()));
		// 消费人数(人头数)
		map.put("consumeNumber", consumeNumberAndTimeMap==null||consumeNumberAndTimeMap.get("consumeNumber") == null ? "0"
				: consumeNumberAndTimeMap.get("consumeNumber").toString());
		// 新客
		map.put("shopNewUserNumber", shopNewUserNumber.toString());
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		response.setResponseData(map);
		response.setResult(StatusConstant.SUCCESS);
		return response;
	}
}
