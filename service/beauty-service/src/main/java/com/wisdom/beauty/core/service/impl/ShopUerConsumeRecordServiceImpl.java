package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.mapper.ShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: ShopUerConsumeRecordServiceImpl
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/8 18:23
 * @since JDK 1.8
 */
@Service("shopUerConsumeRecordService")
public class ShopUerConsumeRecordServiceImpl implements ShopUerConsumeRecordService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopUserConsumeRecordMapper shopUserConsumeRecordMapper;
	@Autowired
	private ShopClerkWorkService shopClerkWorkService;

	@Autowired
	private ShopProjectGroupService shopProjectGroupService;

	@Autowired
	private ShopProjectService shopProjectService;

	@Autowired
	private CashService cashService;
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public List<UserConsumeRecordResponseDTO> getShopCustomerConsumeRecordList(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequest = pageParamVoDTO.getRequestData();
		String sysShopId = redisUtils.getShopId();

		logger.info(
				"getShopCustomerConsumeRecordList方法传入的参数,SysShopId={},ShopUserId={},consumeType={},goodsType={}startTime={}，endTime={}",
				sysShopId, userConsumeRequest.getSysUserId(), userConsumeRequest.getConsumeType(),
				userConsumeRequest.getGoodsType(), pageParamVoDTO.getStartTime(), pageParamVoDTO.getEndTime());

		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
		// 排序
		criteria.setOrderByClause("create_date");
		// 分页
		if (pageParamVoDTO.getPaging()) {
			criteria.setLimitStart(pageParamVoDTO.getPageNo());
			criteria.setPageSize(pageParamVoDTO.getPageSize());
		}
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			c.andCreateDateBetween(startTime, endTime);
		}
		// 设置查询条件
		if (StringUtils.isNotBlank(sysShopId)) {
			c.andSysShopIdEqualTo(sysShopId);
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysClerkId())) {
			c.andSysClerkIdEqualTo(userConsumeRequest.getSysClerkId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysBossCode())) {
			c.andSysBossCodeEqualTo(userConsumeRequest.getSysBossCode());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getSysUserId())) {
			c.andSysUserIdEqualTo(userConsumeRequest.getSysUserId());
		}
		if (StringUtils.isNotBlank(userConsumeRequest.getConsumeType())) {
			c.andConsumeTypeEqualTo(userConsumeRequest.getConsumeType());
		}

		// 根据goodsTypeRequire设置查询条件，如果费类型不是划卡则需要通过goodType来区分,如果goodsTypeRequire为false则需要根据goodType来区分
		if (userConsumeRequest.getGoodsTypeRequire()) {
			if (ConsumeTypeEnum.CONSUME.getCode().equals(userConsumeRequest.getConsumeType())) {
				if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRequest.getGoodsType())
						|| GoodsTypeEnum.PRODUCT.getCode().equals(userConsumeRequest.getGoodsType())
						|| GoodsTypeEnum.TIME_CARD.getCode().equals(userConsumeRequest.getGoodsType())) {
					// 如果是充值卡或者是产品领取， 06-06查看改代码感觉有问题，想不起来为啥要写TIME_CARD和RECHARGE_CARD，暂时标记
					c.andGoodsTypeEqualTo(userConsumeRequest.getGoodsType());
				}
				if (GoodsTypeEnum.PUNCH_CARD.getCode().equals(userConsumeRequest.getGoodsType())) {
					// 如果查询的划卡记录
					List goodType = new ArrayList();
					goodType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
					goodType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
					c.andGoodsTypeIn(goodType);
				}
			}
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRequest.getConsumeType())) {
				// 充值记录或者疗程卡记录
				if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRequest.getGoodsType())
						|| GoodsTypeEnum.TREATMENT_CARD.getCode().equals(userConsumeRequest.getGoodsType())) {
					c.andGoodsTypeEqualTo(userConsumeRequest.getGoodsType());
				}
				// 收银记录
				if (GoodsTypeEnum.CASHIER.getCode().equals(userConsumeRequest.getGoodsType())) {
					List goodType = new ArrayList();
					goodType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
					goodType.add(GoodsTypeEnum.TIME_CARD.getCode());
					goodType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
					goodType.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
					goodType.add(GoodsTypeEnum.PRODUCT.getCode());
					c.andGoodsTypeIn(goodType);
				}
				// 消费记录
				if (GoodsTypeEnum.CONSUM.getCode().equals(userConsumeRequest.getGoodsType())) {
					List goodType = new ArrayList();
					goodType.add(GoodsTypeEnum.TIME_CARD.getCode());
					goodType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
					goodType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
					goodType.add(GoodsTypeEnum.PRODUCT.getCode());
					c.andGoodsTypeIn(goodType);
				} else {
					List goodType = new ArrayList();
					goodType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
					goodType.add(GoodsTypeEnum.TIME_CARD.getCode());
					goodType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
					c.andGoodsTypeIn(goodType);
				}
			}
		}
		List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("查询用户的消费记录list返回为空");
			return null;
		}
		Map<String, UserConsumeRecordResponseDTO> map = new HashMap<>(16);
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = null;
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
			if (map.get(shopUserConsumeRecord.getFlowNo()) == null) {
				userConsumeRecordResponseDTO.setSumAmount(shopUserConsumeRecord.getPrice());
				userConsumeRecordResponseDTO.setCreateDate(shopUserConsumeRecord.getCreateDate());
				userConsumeRecordResponseDTO.setFlowNo(shopUserConsumeRecord.getFlowNo());
				userConsumeRecordResponseDTO.setSysShopClerkId(shopUserConsumeRecord.getSysClerkId());
				userConsumeRecordResponseDTO.setGoodsType(shopUserConsumeRecord.getGoodsType());
				userConsumeRecordResponseDTO.setConsumeType(shopUserConsumeRecord.getConsumeType());
				userConsumeRecordResponseDTO.setConsumeNumber(shopUserConsumeRecord.getConsumeNumber());
				userConsumeRecordResponseDTO.setCreateBy(shopUserConsumeRecord.getCreateBy());
				userConsumeRecordResponseDTO.setSysShopName(shopUserConsumeRecord.getSysShopName());
				userConsumeRecordResponseDTO.setFlowName(shopUserConsumeRecord.getFlowName());
				// 前台页面显示如果划卡title是项目名称，消费收银为消费类型
				if (ConsumeTypeEnum.CONSUME.getCode().equals(shopUserConsumeRecord.getConsumeType())) {
					userConsumeRecordResponseDTO.setTitle(shopUserConsumeRecord.getFlowName());
				} else {
					userConsumeRecordResponseDTO.setTitle(shopUserConsumeRecord.getConsumeType());
				}
				map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseDTO);
			} else {
				UserConsumeRecordResponseDTO userConsumeRecordResponseMap = map.get(shopUserConsumeRecord.getFlowNo());
				BigDecimal prices = shopUserConsumeRecord.getPrice().add(userConsumeRecordResponseMap.getSumAmount());
				userConsumeRecordResponseMap.setSumAmount(prices);
				map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseMap);
			}
		}
		logger.info("getShopCustomerConsumeRecordList执行完成");
		List values = Arrays.asList(map.values().toArray());
		return values;
	}

	@Override
	public UserConsumeRecordResponseDTO getShopCustomerConsumeRecord(String consumeFlowNo) {
		logger.info("getShopCustomerConsumeRecord方法传入的参数,consumeFlowNo={}", consumeFlowNo);
		if (StringUtils.isBlank(consumeFlowNo)) {
			logger.info("getShopCustomerConsumeRecord方法传入的参数consumeFlowNo为空");
			return null;
		}
		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
		c.andFlowNoEqualTo(consumeFlowNo);

		List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getShopCustomerConsumeRecord方法获取list集合为空");
			return null;
		}
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
		// 获取第一条记录，为了获取到基础信息,因为同一个流水的所有记录的基础信息都是一样的
		BeanUtils.copyProperties(list.get(0), userConsumeRecordResponseDTO);

		BigDecimal totalAmount = null;
		Set<String> consumeTypes = new HashSet<>();
		Set<String> goodsTypes = new HashSet<>();
		List<String> flowIds = new ArrayList<>();
		List<ShopUserConsumeRecordDTO> collectionCardList = new ArrayList<>();
		List<ShopUserConsumeRecordDTO> treatmentCardList = new ArrayList<>();
		for (ShopUserConsumeRecordDTO shopUserConsumeRecordDTO : list) {
			flowIds.add(shopUserConsumeRecordDTO.getFlowId());
			consumeTypes.add(shopUserConsumeRecordDTO.getConsumeType());
			goodsTypes.add(shopUserConsumeRecordDTO.getGoodsType());
			if (null != shopUserConsumeRecordDTO.getPrice()) {
				if (totalAmount == null) {
					totalAmount = shopUserConsumeRecordDTO.getPrice();
				} else {
					totalAmount = totalAmount.add(shopUserConsumeRecordDTO.getPrice());
				}
			}
			// 获取套卡集合
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.COLLECTION_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodsType())) {
				collectionCardList.add(shopUserConsumeRecordDTO);
			}
			// 获取疗程卡
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.TREATMENT_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodsType())) {
				treatmentCardList.add(shopUserConsumeRecordDTO);
			}
		}
		// 设置前台展示类型type
		if (consumeTypes.contains(ConsumeTypeEnum.RECHARGE.getCode())) {
			if (goodsTypes.contains(GoodsTypeEnum.RECHARGE_CARD.getCode())) {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.RECHARGE.getCode());
			} else {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.CONSUME.getCode());
			}
		}
		if (consumeTypes.contains(ConsumeTypeEnum.CONSUME.getCode())) {
			userConsumeRecordResponseDTO.setType(GoodsTypeEnum.PUNCH_CARD.getCode());
		}
		// 存放所有的消费项目的
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponses = new ArrayList<>();
		// 判断套卡,拿出套卡的集合
		if (CollectionUtils.isNotEmpty(collectionCardList)) {
			// 遍历collectionCardList获取flowId,flowId此时是套卡id
			List<String> flowId = new ArrayList<>();
			Map<String, ShopUserConsumeRecordDTO> map2 = new HashMap<>();
			for (ShopUserConsumeRecordDTO dto : collectionCardList) {
				flowId.add(dto.getFlowId());
				map2.put(dto.getFlowId(), dto);
			}

			// 根据多个id查询套卡
			List<ShopUserProjectGroupRelRelationDTO> shopUserProjectGroupRelRelationDTOs = shopProjectGroupService
					.getShopUserProjectGroupRelRelation(flowIds);
			// 遍历shopUserProjectGroupRelRelations将id和遍历出来的对象放入map,key是关系id，value是遍历出来的每个对象
			// map key 存放套卡id, value该套卡的信息
			Map<String, UserConsumeRecordResponseDTO> map = new HashMap<>();
			for (ShopUserProjectGroupRelRelationDTO dto : shopUserProjectGroupRelRelationDTOs) {
				// 获取套卡中的每个项目
				if (map.containsKey(dto.getShopProjectGroupId())) {
					// 需要计算包含次数，和添加包含的项目
					UserConsumeRecordResponseDTO devDto = map.get(dto.getShopProjectGroupId());
					devDto.setIncludeTimes(devDto.getIncludeTimes() + dto.getProjectInitTimes());
					List<ShopProjectInfoDTO> shopProjectInfos = devDto.getShopProjectInfoDTOList();
					ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
					shopProjectInfoDTO.setProjectName(dto.getShopProjectInfoName());
					shopProjectInfoDTO.setServiceTimes(dto.getProjectInitTimes());
					shopProjectInfoDTO.setDiscountPrice(
							dto.getProjectInitAmount().divide(new BigDecimal(dto.getProjectInitTimes())));
					shopProjectInfos.add(shopProjectInfoDTO);
					devDto.setShopProjectInfoDTOList(shopProjectInfos);
					map.put(dto.getShopProjectGroupId(), devDto);
				} else {
					UserConsumeRecordResponseDTO devDto = new UserConsumeRecordResponseDTO();
					devDto.setFlowName(dto.getShopProjectGroupName());
					devDto.setIncludeTimes(dto.getProjectInitTimes());
					devDto.setPrice(dto.getProjectInitAmount());
					devDto.setDiscount(dto.getDiscount());
					devDto.setConsumeNumber(map2.get(dto.getShopProjectGroupId()).getConsumeNumber());
					devDto.setSumAmount(map2.get(dto.getShopProjectGroupId()).getPrice());
					devDto.setGoodsType(map2.get(dto.getShopProjectGroupId()).getGoodsType());
					//
					List<ShopProjectInfoDTO> shopProjectInfos = new ArrayList<>();
					ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
					shopProjectInfoDTO.setProjectName(dto.getShopProjectInfoName());
					shopProjectInfoDTO.setServiceTimes(dto.getProjectInitTimes());
					if(dto.getProjectInitAmount()!=null && dto.getProjectInitTimes()!=null){
						shopProjectInfoDTO.setDiscountPrice(
								dto.getProjectInitAmount().divide(new BigDecimal(dto.getProjectInitTimes())));
					}
					shopProjectInfos.add(shopProjectInfoDTO);
					devDto.setShopProjectInfoDTOList(shopProjectInfos);
					map.put(dto.getShopProjectGroupId(), devDto);
				}

			}
			// 获取map的value集合
			userConsumeRecordResponses.addAll(map.values());
			list.removeAll(collectionCardList);
		}
		// 判断是疗程卡，拿出疗程卡集合
		if (CollectionUtils.isNotEmpty(treatmentCardList)) {
			List<String> flowId = new ArrayList<>();
			for (ShopUserConsumeRecordDTO dto : treatmentCardList) {
				flowId.add(dto.getFlowId());
			}
			// 根据多个id查询用户和项目的关系表
			List<ShopUserProjectRelationDTO> shopUserProjectRelations = shopProjectService
					.getUserShopProjectList(flowId);
			// map key存放用户和项目关系的id value包含次数
			Map<String, Integer> map = new HashedMap();
			for (ShopUserProjectRelationDTO shopUserProjectRelationDTO : shopUserProjectRelations) {
				map.put(shopUserProjectRelationDTO.getId(), shopUserProjectRelationDTO.getServiceTime());
			}
			// 遍历疗程卡集合，设置包含次数等属性
			UserConsumeRecordResponseDTO userConsumeRecordResponse = null;
			for (ShopUserConsumeRecordDTO dto : treatmentCardList) {
				userConsumeRecordResponse = new UserConsumeRecordResponseDTO();
				userConsumeRecordResponse.setIncludeTimes(map.get(dto.getFlowId()));
				if(dto.getPrice()!=null&&dto.getConsumeNumber()!=null){
					userConsumeRecordResponse.setPrice(dto.getPrice().divide(new BigDecimal(dto.getConsumeNumber())));
				}
				userConsumeRecordResponse.setSumAmount(dto.getPrice());
				userConsumeRecordResponse.setPeriodDiscount(dto.getPeriodDiscount());
				userConsumeRecordResponse.setConsumeNumber(dto.getConsumeNumber());
				userConsumeRecordResponse.setFlowName(dto.getFlowName());
				userConsumeRecordResponse.setGoodsType(dto.getGoodsType());
				userConsumeRecordResponses.add(userConsumeRecordResponse);
			}
			list.removeAll(treatmentCardList);
		}
		// 遍历剩余的其他类型 单次和产品,充值卡
		UserConsumeRecordResponseDTO userConsumeRecordResponse = null;
		for (ShopUserConsumeRecordDTO shopUserConsumeRecordDTO : list) {
			userConsumeRecordResponse = new UserConsumeRecordResponseDTO();
			BeanUtils.copyProperties(shopUserConsumeRecordDTO, userConsumeRecordResponse);
			userConsumeRecordResponses.add(userConsumeRecordResponse);
		}
		userConsumeRecordResponseDTO.setSumAmount(totalAmount);
		userConsumeRecordResponseDTO.setUserConsumeRecordResponseList(userConsumeRecordResponses);
		userConsumeRecordResponseDTO.setPayMap(this.getPayMap(userConsumeRecordResponseDTO.getFlowNo()));
		return userConsumeRecordResponseDTO;
	}

	@Override
	public UserConsumeRecordResponseDTO getUserConsumeRecord(String id) {
		logger.info("getUserConsumeRecord方法出入的参数id={}", id);
		if (StringUtils.isBlank(id)) {
			return null;
		}
		ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
		shopUserConsumeRecordDTO.setId(id);
		List<ShopUserConsumeRecordDTO> shopUserConsumeRecordDTOs = this
				.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
		if (CollectionUtils.isEmpty(shopUserConsumeRecordDTOs)) {
			logger.info("返回的shopUserConsumeRecordDTOs为空");
			return null;
		}
		ShopUserConsumeRecordDTO shopUserConsumeRecord = shopUserConsumeRecordDTOs.get(0);

		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
		BeanUtils.copyProperties(shopUserConsumeRecord, userConsumeRecordResponseDTO);

		if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())) {
			if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodsType())) {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.RECHARGE.getCode());
			} else {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.CONSUME.getCode());
			}
		}
		// 根据多个id查询套
		ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation = new ShopUserProjectGroupRelRelationDTO();
		shopUserProjectGroupRelRelation.setConsumeRecordId(id);
		List<ShopUserProjectGroupRelRelationDTO> shopUserProjectGroupRelRelationDTO = shopProjectGroupService
				.getShopUserProjectGroupRelRelation(shopUserProjectGroupRelRelation);

		Integer includeTimes = null;
		// shopProjectInfos集合存储套卡中的项目信息
		List<ShopProjectInfoDTO> shopProjectInfos = null;
		if (CollectionUtils.isNotEmpty(shopUserProjectGroupRelRelationDTO)) {
			shopProjectInfos = new ArrayList<>();
			ShopProjectInfoDTO shopProjectInfoDTO = null;
			for (ShopUserProjectGroupRelRelationDTO dto : shopUserProjectGroupRelRelationDTO) {
				// 计算包含次数，每个项目的次数之和
				if (includeTimes == null) {
					includeTimes = dto.getProjectInitTimes();
				} else {
					includeTimes = includeTimes + dto.getProjectInitTimes();
				}
				shopProjectInfoDTO = new ShopProjectInfoDTO();
				shopProjectInfoDTO.setProjectName(dto.getShopProjectInfoName());
				shopProjectInfoDTO.setServiceTimes(dto.getProjectInitTimes());
				shopProjectInfoDTO
						.setDiscountPrice(dto.getProjectInitAmount().divide(new BigDecimal(dto.getProjectInitTimes())));
				shopProjectInfos.add(shopProjectInfoDTO);
			}
		}
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponseList = new ArrayList<>();
		// 创建套卡中内容对象
		UserConsumeRecordResponseDTO dto = new UserConsumeRecordResponseDTO();
		dto.setFlowName(shopUserConsumeRecord.getFlowName());
		dto.setIncludeTimes(includeTimes);
		dto.setPrice(shopUserConsumeRecord.getPrice().divide(new BigDecimal(shopUserConsumeRecord.getConsumeNumber())));
		dto.setDiscount(shopUserConsumeRecord.getDiscount());
		dto.setConsumeNumber(shopUserConsumeRecord.getConsumeNumber());
		dto.setSumAmount(shopUserConsumeRecord.getPrice());
		dto.setShopProjectInfoDTOList(shopProjectInfos);
		userConsumeRecordResponseList.add(dto);
		userConsumeRecordResponseDTO.setUserConsumeRecordResponseList(userConsumeRecordResponseList);
		userConsumeRecordResponseDTO.setSumAmount(shopUserConsumeRecord.getPrice());
		userConsumeRecordResponseDTO.setPayMap(this.getPayMap(shopUserConsumeRecord.getFlowNo()));
		userConsumeRecordResponseDTO.setSignUrl(shopUserConsumeRecord.getSignUrl());
		userConsumeRecordResponseDTO.setDetail(shopUserConsumeRecord.getDetail());

		return userConsumeRecordResponseDTO;
	}

	/**
	 * 根据条件查询消费记录
	 *
	 * @param shopUserConsumeRecordDTO
	 * @return
	 */
	@Override
	public List<ShopUserConsumeRecordDTO> getShopCustomerConsumeRecord(
			ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {
		logger.info("根据条件查询消费记录方法传入的参数,shopUserConsumeRecordDTO={}}", shopUserConsumeRecordDTO);

		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();

		if (StringUtils.isNotBlank(shopUserConsumeRecordDTO.getFlowId())) {
			c.andFlowIdEqualTo(shopUserConsumeRecordDTO.getFlowId());
		}
		if (StringUtils.isNotBlank(shopUserConsumeRecordDTO.getId())) {
			c.andIdEqualTo(shopUserConsumeRecordDTO.getId());
		}

		if (StringUtils.isNotBlank(shopUserConsumeRecordDTO.getConsumeType())) {
			c.andConsumeTypeEqualTo(shopUserConsumeRecordDTO.getConsumeType());
		}
		if (StringUtils.isNotBlank(shopUserConsumeRecordDTO.getFlowId())) {
			c.andFlowIdEqualTo(shopUserConsumeRecordDTO.getFlowId());
		}
		List<ShopUserConsumeRecordDTO> shopUserConsumeRecordDTOS = shopUserConsumeRecordMapper
				.selectByCriteria(criteria);

		return shopUserConsumeRecordDTOS;
	}

	/**
	 * 保存用户消费或充值记录
	 *
	 * @param shopUserConsumeRecordDTO
	 * @return
	 */
	@Override
	public int saveCustomerConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {

		logger.info("保存用户消费或充值记录传入参数={}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");

		int insert = shopUserConsumeRecordMapper.insert(shopUserConsumeRecordDTO);
		saveShopClerkWorkRecord(shopUserConsumeRecordDTO.getSysClerkId(), shopUserConsumeRecordDTO);
		return insert;
	}

	/**
	 * 生成店员流水记录
	 *
	 * @param sysClerkId
	 * @param consumeRecordDTO
	 */
	private void saveShopClerkWorkRecord(String sysClerkId, ShopUserConsumeRecordDTO consumeRecordDTO) {
		if (com.wisdom.common.util.StringUtils.isNotBlank(sysClerkId)) {
			String[] clerks = sysClerkId.split(";");
			List<ShopClerkWorkRecordDTO> recordDTOS = new ArrayList<>();
			for (String clerk : clerks) {
				ShopClerkWorkRecordDTO recordDTO = new ShopClerkWorkRecordDTO();
				recordDTO.setPrice(consumeRecordDTO.getPrice());
				recordDTO.setId(IdGen.uuid());
				recordDTO.setPayType(consumeRecordDTO.getPayType());
				recordDTO.setFlowNo(consumeRecordDTO.getFlowNo());
				recordDTO.setSysClerkId(clerk);
				recordDTO.setGoodsType(consumeRecordDTO.getGoodsType());
				recordDTO.setSysShopId(consumeRecordDTO.getSysShopId());
				recordDTO.setCreateDate(new Date());
				recordDTO.setConsumeRecordId(consumeRecordDTO.getId());
				recordDTOS.add(recordDTO);
			}
			shopClerkWorkService.saveClerkWorkRecord(recordDTOS);
		}
	}

	/**
	 * 更新用户的消费记录
	 *
	 * @param shopUserConsumeRecordDTO
	 * @return
	 */
	@Override
	public int updateConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {
		logger.info("更新用户的消费记录传入参数={}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");
		return shopUserConsumeRecordMapper.updateByPrimaryKeySelective(shopUserConsumeRecordDTO);
	}

	@Override
	public List<UserConsumeRecordResponseDTO> getShopCustomerConsumeRecordList(String consumeType,
			List<String> sysClerkIds) {

		logger.info("getShopCustomerConsumeRecordList方法传入sysClerkIds的参数,sysClerkIds={}", sysClerkIds);
		if (StringUtils.isBlank(consumeType) || CollectionUtils.isEmpty(sysClerkIds)) {
			logger.info("传入的参数为空");
			return null;
		}
		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();

		// 设置查询条件
		c.andConsumeTypeEqualTo(consumeType);
		c.andSysClerkIdIn(sysClerkIds);

		List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);

		Map<String, UserConsumeRecordResponseDTO> map = new HashMap<>(16);
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = null;
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
			if (map.get(shopUserConsumeRecord.getFlowNo()) == null) {
				userConsumeRecordResponseDTO.setSumAmount(shopUserConsumeRecord.getPrice());
				userConsumeRecordResponseDTO.setCreateDate(shopUserConsumeRecord.getCreateDate());
				userConsumeRecordResponseDTO.setFlowNo(shopUserConsumeRecord.getFlowNo());
				userConsumeRecordResponseDTO.setSysShopClerkId(shopUserConsumeRecord.getSysClerkId());
				userConsumeRecordResponseDTO.setGoodsType(shopUserConsumeRecord.getGoodsType());
				map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseDTO);
			} else {
				UserConsumeRecordResponseDTO userConsumeRecordResponseMap = map.get(shopUserConsumeRecord.getFlowNo());
				BigDecimal prices = shopUserConsumeRecord.getPrice().add(userConsumeRecordResponseMap.getSumAmount());
				userConsumeRecordResponseMap.setSumAmount(prices);
				map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseMap);
			}
		}
		logger.info("getShopCustomerConsumeRecordList执行完成");
		List values = Arrays.asList(map.values().toArray());
		return values;
	}

	@Override
	public List<UserConsumeRecordResponseDTO> getTreatmentAndGroupCardRecord(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
		if (userConsumeRequestDTO == null) {
			logger.info("getTreatmentCardRecord方法出入参数userConsumeRequestDTO为空");
			return null;
		}
		logger.info("getTreatmentCardRecord传入的参数FlowId={}", userConsumeRequestDTO.getFlowId());

		List<ShopUserConsumeRecordDTO> list = null;
		// 判断如果是套卡
		if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(userConsumeRequestDTO.getGoodsType())) {
			list = this.getShopCustomerConsumeRecord(userConsumeRequestDTO.getFlowIds());
		}
		// 判断是疗程卡
		if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(userConsumeRequestDTO.getGoodsType())) {
			ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
			shopUserConsumeRecordDTO.setFlowId(userConsumeRequestDTO.getFlowId());
			list = this.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
		}
		if (CollectionUtils.isEmpty(list)) {
			logger.info("list结果返回为空");
			return null;
		}
		List<String> flowIds = new ArrayList<>();
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			flowIds.add(shopUserConsumeRecord.getFlowId());

		}
		// 根据flowIds集合查询店员list
		List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs = shopClerkWorkService.getShopClerkList(flowIds);
		Map<String, List<String>> map = null;
		if (CollectionUtils.isEmpty(shopClerkWorkRecordDTOs)) {
			logger.info("查询shopClerkWorkRecordDTO结果为空");
		} else {
			// 如果获取到店员list，则做存入map中，key使用消费记录id,value使用员工姓名集合
			map = new HashMap<>();
			for (ShopClerkWorkRecordDTO shopClerkWorkRecordDTO : shopClerkWorkRecordDTOs) {
				if (map.containsKey(shopClerkWorkRecordDTO.getConsumeRecordId())) {
					List<String> devClerkList = map.get(shopClerkWorkRecordDTO.getConsumeRecordId());
					devClerkList.add(shopClerkWorkRecordDTO.getSysClerkName());
					map.put(shopClerkWorkRecordDTO.getConsumeRecordId(), devClerkList);
				} else {
					List<String> devClerkList = new ArrayList<>();
					devClerkList.add(shopClerkWorkRecordDTO.getSysClerkName());
					map.put(shopClerkWorkRecordDTO.getConsumeRecordId(), devClerkList);
				}
			}
		}
		// 遍历list
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTOs = new ArrayList<>();
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = null;
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopUserConsumeRecord.getConsumeType())) {
				if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(shopUserConsumeRecord.getGoodsType())
						|| GoodsTypeEnum.COLLECTION_CARD.getCode().equals(shopUserConsumeRecord.getGoodsType())) {
					userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
					userConsumeRecordResponseDTO.setCreateDate(shopUserConsumeRecord.getCreateDate());
					userConsumeRecordResponseDTO.setFlowName(shopUserConsumeRecord.getFlowName());
					userConsumeRecordResponseDTO.setConsumeNumber(shopUserConsumeRecord.getConsumeNumber());
					userConsumeRecordResponseDTO.setSignUrl(shopUserConsumeRecord.getSignUrl());
					userConsumeRecordResponseDTO.setSysShopName(shopUserConsumeRecord.getSysShopName());
					userConsumeRecordResponseDTO.setFlowNo(shopUserConsumeRecord.getFlowNo());
					if (map != null) {
						userConsumeRecordResponseDTO.setSysClerkNameList(map.get(shopUserConsumeRecord.getId()));
					}
					userConsumeRecordResponseDTOs.add(userConsumeRecordResponseDTO);

				}
			}

		}
		return userConsumeRecordResponseDTOs;
	}

	@Override
	public List<UserConsumeRecordResponseDTO> getUserStampCardRecord(
			PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO) {
		UserConsumeRequestDTO userConsumeRequestDTO = pageParamVoDTO.getRequestData();
		if (userConsumeRequestDTO == null) {
			logger.info("getUserStampCardRecord方法出入参数userConsumeRequestDTO为空");
			return null;
		}
		logger.info("getUserStampCardRecord传入的参数sysUserId={}", userConsumeRequestDTO.getSysUserId());

		ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
		shopUserConsumeRecordDTO.setSysUserId(userConsumeRequestDTO.getSysUserId());
		List<ShopUserConsumeRecordDTO> list = this.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("list结果返回为空");
			return null;
		}
		List<String> consumeRecordIds = new ArrayList<>();
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			consumeRecordIds.add(shopUserConsumeRecord.getId());

		}
		// 根据consumeRecordIds集合查询店员list
		List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs = shopClerkWorkService
				.getShopClerkByConsumeRecordId(consumeRecordIds);
		Map<String, List<String>> map = null;
		if (CollectionUtils.isEmpty(shopClerkWorkRecordDTOs)) {
			logger.info("查询shopClerkWorkRecordDTO结果为空");
		} else {
			// 如果获取到店员list，则做存入map中，key使用消费记录id,value使用员工姓名集合
			map = new HashMap<>();
			for (ShopClerkWorkRecordDTO shopClerkWorkRecordDTO : shopClerkWorkRecordDTOs) {
				if (map.containsKey(shopClerkWorkRecordDTO.getConsumeRecordId())) {
					List<String> devClerkList = map.get(shopClerkWorkRecordDTO.getConsumeRecordId());
					devClerkList.add(shopClerkWorkRecordDTO.getSysClerkName());
					map.put(shopClerkWorkRecordDTO.getConsumeRecordId(), devClerkList);
				} else {
					List<String> devClerkList = new ArrayList<>();
					devClerkList.add(shopClerkWorkRecordDTO.getSysClerkName());
					map.put(shopClerkWorkRecordDTO.getConsumeRecordId(), devClerkList);
				}
			}
		}
		// 遍历list
		List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTOs = new ArrayList<>();
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = null;
		for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopUserConsumeRecord.getConsumeType())) {
				if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(shopUserConsumeRecord.getGoodsType())
						|| GoodsTypeEnum.COLLECTION_CARD.getCode().equals(shopUserConsumeRecord.getGoodsType())) {
					userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
					userConsumeRecordResponseDTO.setCreateDate(shopUserConsumeRecord.getCreateDate());
					userConsumeRecordResponseDTO.setFlowName(shopUserConsumeRecord.getFlowName());
					userConsumeRecordResponseDTO.setConsumeNumber(shopUserConsumeRecord.getConsumeNumber());
					userConsumeRecordResponseDTO.setSignUrl(shopUserConsumeRecord.getSignUrl());
					userConsumeRecordResponseDTO.setSysShopName(shopUserConsumeRecord.getSysShopName());
					userConsumeRecordResponseDTO.setFlowNo(shopUserConsumeRecord.getFlowNo());
					if (map != null) {
						userConsumeRecordResponseDTO.setSysClerkNameList(map.get(shopUserConsumeRecord.getId()));
					}
					userConsumeRecordResponseDTOs.add(userConsumeRecordResponseDTO);

				}
			}

		}
		return userConsumeRecordResponseDTOs;
	}

	@Override
	public List<ShopUserConsumeRecordDTO> getShopCustomerConsumeRecord(List<String> flowIds) {
		logger.info("getShopCustomerConsumeRecord方法传入的参数flowIds={}", flowIds);
		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
		if (CollectionUtils.isEmpty(flowIds)) {
			return null;
		}
		c.andFlowIdIn(flowIds);
		return shopUserConsumeRecordMapper.selectByCriteria(criteria);

	}

	@Override
	public UserConsumeRecordResponseDTO getTreatmentCardConsumeDetail(String flowId) {
		logger.info("getTreatmentCardConsumeDetail方法传入的参数,flowId={}", flowId);
		if (StringUtils.isBlank(flowId)) {
			logger.info("getTreatmentCardConsumeDetail方法传入的参数为空");
			return null;
		}
		ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
		ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
		c.andFlowIdEqualTo(flowId);
		c.andConsumeTypeEqualTo(ConsumeTypeEnum.RECHARGE.getCode());
		c.andGoodsTypeEqualTo(GoodsTypeEnum.TREATMENT_CARD.getCode());

		// 查询消费记录表
		List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getShopCustomerConsumeRecord方法获取list集合为空");
			return null;
		}
		ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = list.get(0);
		// 获取一条消费记录,因为根据flowId查询的疗程卡只有一个
		UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
		if (CommonUtils.objectIsNotEmpty(list)) {
			BeanUtils.copyProperties(shopUserConsumeRecordDTO, userConsumeRecordResponseDTO);
		}

		if (ConsumeTypeEnum.RECHARGE.getCode().equals(userConsumeRecordResponseDTO.getConsumeType())) {
			if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(userConsumeRecordResponseDTO.getGoodsType())) {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.RECHARGE.getCode());
			} else {
				userConsumeRecordResponseDTO.setType(ConsumeTypeEnum.CONSUME.getCode());
			}
		}
		// 根据flowId查询用户和项目的关系,获取ShopUserProjectRelationDTO信息
		ShopUserProjectRelationDTO shopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		shopUserProjectRelationDTO.setId(shopUserConsumeRecordDTO.getFlowId());
		List<ShopUserProjectRelationDTO> shopUserProjectRelations = shopProjectService
				.getUserProjectList(shopUserProjectRelationDTO);
		logger.info("getUserProjectList获取的结果shopUserProjectRelations={}", shopUserProjectRelations);

		List<UserConsumeRecordResponseDTO> userConsumeRecordResponses = null;
		if (CollectionUtils.isEmpty(shopUserProjectRelations)) {
			logger.info("shopUserProjectRelations结果为空");
		} else {
			userConsumeRecordResponses = new ArrayList<>();
			ShopUserProjectRelationDTO shopUserProjectRelation = shopUserProjectRelations.get(0);

			UserConsumeRecordResponseDTO dto = new UserConsumeRecordResponseDTO();
			dto.setIncludeTimes(shopUserProjectRelation.getServiceTime());
			dto.setPrice(userConsumeRecordResponseDTO.getPrice()
					.divide(new BigDecimal(userConsumeRecordResponseDTO.getConsumeNumber())));
			dto.setSumAmount(userConsumeRecordResponseDTO.getPrice());
			dto.setPeriodDiscount(userConsumeRecordResponseDTO.getPeriodDiscount());
			dto.setConsumeNumber(userConsumeRecordResponseDTO.getConsumeNumber());
			dto.setFlowName(userConsumeRecordResponseDTO.getFlowName());
			userConsumeRecordResponses.add(dto);
		}

		userConsumeRecordResponseDTO.setSumAmount(userConsumeRecordResponseDTO.getPrice());
		// 支付明细
		userConsumeRecordResponseDTO.setPayMap(this.getPayMap(userConsumeRecordResponseDTO.getFlowNo()));
		userConsumeRecordResponseDTO.setUserConsumeRecordResponseList(userConsumeRecordResponses);

		return userConsumeRecordResponseDTO;
	}

	private Map<String, Object> getPayMap(String flowNo) {
		logger.info("getPayMap方法传入的参数flowNo={}", flowNo);
		// 计算支付明细,根据消费记录id查询支付明细
		ShopCashFlowDTO shopCashFlowDTO = new ShopCashFlowDTO();
		shopCashFlowDTO.setFlowNo(flowNo);
		ShopCashFlowDTO shopCashFlow = cashService.getShopCashFlow(shopCashFlowDTO);
		if (shopCashFlow == null) {
			logger.info("getShopCashFlow方法获取的结果shopCashFlow为空");
			return null;
		}
		// 余额支付
		BigDecimal balanceAmount = null;
		// 充值卡支付
		BigDecimal rechargeCardAmount = null;
		// 现金支付
		BigDecimal cashAmount = null;
		Map<String, Object> payMap = new HashedMap();

		balanceAmount = shopCashFlow.getBalanceAmount();
		rechargeCardAmount = shopCashFlow.getRechargeCardAmount();
		cashAmount = shopCashFlow.getCashAmount();
		payMap.put("payType", shopCashFlow.getPayType());
		payMap.put("payTypeAmount", shopCashFlow.getPayTypeAmount());
		payMap.put("balanceAmount", balanceAmount);
		payMap.put("rechargeCardAmount", rechargeCardAmount);
		payMap.put("cashAmount", cashAmount);
		return payMap;
	}
}
