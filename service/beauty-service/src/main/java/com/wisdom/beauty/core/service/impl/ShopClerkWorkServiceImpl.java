package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopClerkWorkRecordCriteria;
import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClerkWorkRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopClerkWorkRecordResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopClerkWorkRecordMapper;
import com.wisdom.beauty.core.mapper.ShopClerkWorkRecordMapper;
import com.wisdom.beauty.core.service.ShopClerkWorkService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
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
 * Created by zhanghuan on 2018/5/31.
 *
 * 统计员工业绩相关接口的
 */
@Service("shopClerkWorkService")
public class ShopClerkWorkServiceImpl implements ShopClerkWorkService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopClerkWorkRecordMapper shopClerkWorkRecordMapper;

	@Autowired
	private ExtShopClerkWorkRecordMapper extShopClerkWorkRecordMapper;

	@Override
	public List<ShopClerkWorkRecordResponseDTO> getShopCustomerConsumeRecordList(
			PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO) {
		ShopClerkWorkRecordRequestDTO shopClerkWorkRecordRequestDTO = pageParamVoDTO.getRequestData();
		SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
		if (sysClerkDTO == null) {
			throw new ServiceException("从redis中获取sysClerkDTO对象为空");
		}
		logger.info(
				"getShopCustomerConsumeRecordList方法传入的参数,SysShopId={},sysClerkId={},consumeType={},startTime={}，endTime={}",
				sysClerkDTO.getSysShopId(), shopClerkWorkRecordRequestDTO.getSysClerkId(),
				shopClerkWorkRecordRequestDTO.getConsumeType(), pageParamVoDTO.getStartTime(),
				pageParamVoDTO.getEndTime());
		ShopClerkWorkRecordCriteria criteria = new ShopClerkWorkRecordCriteria();
		ShopClerkWorkRecordCriteria.Criteria c = criteria.createCriteria();
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
		if (StringUtils.isNotBlank(sysClerkDTO.getSysShopId())) {
			c.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
		}
		if (StringUtils.isNotBlank(shopClerkWorkRecordRequestDTO.getSysClerkId())) {
			c.andSysClerkIdEqualTo(shopClerkWorkRecordRequestDTO.getSysClerkId());
		}
		if (shopClerkWorkRecordRequestDTO.getTypeRequire()) {
			c.andConsumeTypeIn(shopClerkWorkRecordRequestDTO.getConsumeTypeList());
			c.andGoodsTypeIn(shopClerkWorkRecordRequestDTO.getGoodsTypeList());
		}
		List<ShopClerkWorkRecordDTO> list = shopClerkWorkRecordMapper.selectByCriteria(criteria);
		Map<String, ShopClerkWorkRecordResponseDTO> map = new HashMap<>(16);
		ShopClerkWorkRecordResponseDTO shopClerkWorkRecordResponseDTO = null;
		for (ShopClerkWorkRecordDTO shopClerkWorkRecord : list) {
			shopClerkWorkRecordResponseDTO = new ShopClerkWorkRecordResponseDTO();
			BeanUtils.copyProperties(shopClerkWorkRecord, shopClerkWorkRecordResponseDTO);
			// 业绩 ---充值金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				// shopClerkWorkRecordResponseDTO.setType("业绩充值");
				shopClerkWorkRecordResponseDTO.setType("0");
			}
			// 业绩 ---消费金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& !GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				// shopClerkWorkRecordResponseDTO.setType("业绩消费金额");
				shopClerkWorkRecordResponseDTO.setType("1");
			}
			// 耗卡 --- 划卡金额(疗程卡和套卡)
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())) {
				if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())
						|| GoodsTypeEnum.COLLECTION_CARD.getCode()
								.equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
					// shopClerkWorkRecordResponseDTO.setType("耗卡划卡金额");
					shopClerkWorkRecordResponseDTO.setType("2");
				}
			}
			// 耗卡 --- 单次消费
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.TIME_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				// shopClerkWorkRecordResponseDTO.setType("耗卡单次消费");
				shopClerkWorkRecordResponseDTO.setType("3");
			}
			// 卡耗 --- 单次消费
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				// shopClerkWorkRecordResponseDTO.setType("卡耗单次消费");
				shopClerkWorkRecordResponseDTO.setType("4");
			}
			if (map.get(shopClerkWorkRecord.getFlowNo()) == null) {
				shopClerkWorkRecordResponseDTO.setSumAmount(shopClerkWorkRecordResponseDTO.getPrice());
				map.put(shopClerkWorkRecord.getFlowNo(), shopClerkWorkRecordResponseDTO);
			} else {
				if (map.get(shopClerkWorkRecord.getFlowNo()).getPrice() != null) {
					BigDecimal prices = map.get(shopClerkWorkRecord.getFlowNo()).getPrice()
							.add(shopClerkWorkRecord.getPrice());
					shopClerkWorkRecordResponseDTO.setSumAmount(prices);
				}
				map.put(shopClerkWorkRecord.getFlowNo(), shopClerkWorkRecordResponseDTO);
			}
		}
		List values = Arrays.asList(map.values().toArray());
		return values;
	}

	@Override
	public Map<String, String> getShopConsumeAndRecharge(PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO) {
		// 根据条件获取员工消费记录集合
		List<ShopClerkWorkRecordResponseDTO> shopClerkWorkRecordResponseDTOs = this
				.getShopCustomerConsumeRecordList(pageParamVoDTO);
		if (CollectionUtils.isEmpty(shopClerkWorkRecordResponseDTOs)) {
			logger.info("查询shopClerkWorkRecordResponseDTOs的结果为空");
			return null;
		}
		// 业绩
		BigDecimal recharge = null;
		BigDecimal consume = null;
		BigDecimal scratchCard = null;
		BigDecimal oneConsume = null;
		BigDecimal cardConsume = null;
		Map<String, String> map = null;
		for (ShopClerkWorkRecordResponseDTO shopClerkWorkRecordResponseDTO : shopClerkWorkRecordResponseDTOs) {
			// 业绩 ---充值金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				if (recharge != null) {
					recharge = recharge.add(shopClerkWorkRecordResponseDTO.getSumAmount());
				} else {
					recharge = shopClerkWorkRecordResponseDTO.getSumAmount();
				}
			}
			// 业绩 ---消费金额
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& !GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				if (consume != null) {
					consume = consume.add(shopClerkWorkRecordResponseDTO.getSumAmount());
				} else {
					consume = shopClerkWorkRecordResponseDTO.getSumAmount();
				}
			}
			// 耗卡 --- 划卡金额(疗程卡和套卡)
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())) {
				if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())
						|| GoodsTypeEnum.COLLECTION_CARD.getCode()
								.equals(shopClerkWorkRecordResponseDTO.getGoodsType()))
					if (scratchCard != null) {
						scratchCard = scratchCard.add(shopClerkWorkRecordResponseDTO.getSumAmount());
					} else {
						scratchCard = shopClerkWorkRecordResponseDTO.getSumAmount();
					}
			}
			// 耗卡 --- 单次消费
			if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.TIME_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				if (oneConsume != null) {
					oneConsume = oneConsume.add(shopClerkWorkRecordResponseDTO.getSumAmount());
				} else {
					oneConsume = shopClerkWorkRecordResponseDTO.getSumAmount();
				}
			}
			// 卡耗 --- 单次消费
			if (ConsumeTypeEnum.CONSUME.getCode().equals(shopClerkWorkRecordResponseDTO.getConsumeType())
					&& GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopClerkWorkRecordResponseDTO.getGoodsType())) {
				if (cardConsume != null) {
					cardConsume = cardConsume.add(shopClerkWorkRecordResponseDTO.getSumAmount());
				} else {
					cardConsume = shopClerkWorkRecordResponseDTO.getSumAmount();
				}
			}
		}
		map = new HashedMap();
		// 充值
		map.put("recharge", recharge == null ? "0" : recharge.toString());
		map.put("consume", consume == null ? "0" : consume.toString());
		map.put("scratchCard", scratchCard == null ? "0" : scratchCard.toString());
		map.put("oneConsume", oneConsume == null ? "0" : oneConsume.toString());
		map.put("cardConsume", cardConsume == null ? "0" : cardConsume.toString());
		return map;
	}

	@Override
	public int saveClerkWorkRecord(List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs) {
		return extShopClerkWorkRecordMapper.insertBatchClerkRecord(shopClerkWorkRecordDTOs);
	}

	@Override
	public List<ShopClerkWorkRecordDTO> getShopClerkList(List<String> flowIds) {
		logger.info("getShopClerkList方法传入的参数flowIds={}",flowIds);
		if(CollectionUtils.isEmpty(flowIds)){
			logger.info("getShopClerkList方法传入的参数flowIds为空");
			return  null;
		}
		ShopClerkWorkRecordCriteria criteria = new ShopClerkWorkRecordCriteria();
		ShopClerkWorkRecordCriteria.Criteria c = criteria.createCriteria();
		c.andFlowIdIn(flowIds);
		return  shopClerkWorkRecordMapper.selectByCriteria(criteria);
	}
}
