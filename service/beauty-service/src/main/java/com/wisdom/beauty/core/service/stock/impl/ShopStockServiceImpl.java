package com.wisdom.beauty.core.service.stock.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.StockStyleEnum;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.mapper.*;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.core.mapper.stock.ExtStockServiceMapper;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.dto.system.PageParamDTO;

/**
 * FileName: ShopStockServiceImpl
 *
 * @author: 张超 Date: 2018/4/23 0003 14:49 Description: 仓库和库存相关
 */
@Service("shopStockService")
public class ShopStockServiceImpl implements ShopStockService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExtStockServiceMapper extStockServiceMapper;

	@Autowired
	private ShopStockRecordMapper shopStockRecordMapper;

	@Autowired
	private ShopStockMapper shopStockMapper;

	@Autowired
	private ShopProductInfoService shopProductInfoService;

	@Autowired
	private ShopStockBossRelationMapper shopStockBossRelationMapper;
	@Autowired
	private ExtShopStockMapper extShopStockMapper;

	@Autowired
	private ShopStockNumberMapper shopStockNumberMapper;

	/**
	 * 查询仓库列表
	 *
	 * @param pageParamDTO
	 *            分页对象
	 * @return
	 */
	@Override
	public PageParamDTO<List<ExtShopStoreDTO>> findStoreListS(PageParamDTO<ExtShopStoreDTO> pageParamDTO) {

		PageParamDTO<List<ExtShopStoreDTO>> page = new PageParamDTO<>();
		List<ExtShopStoreDTO> shopStoreList = null;
		try {

			shopStoreList = extStockServiceMapper.findStoreList(pageParamDTO);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		page.setResponseData(shopStoreList);
		page.setTotalCount(shopStoreList.get(0).getSum());
		logger.info("查询仓库列表" + shopStoreList);
		return page;
	}

	/**
	 * 插入一条出入库记录
	 *
	 * @param shopStockRecordDTO
	 *            出入库表实体对象
	 * @return
	 */
	@Override
	public int insertStockRecord(ShopStockRecordDTO shopStockRecordDTO) {
		int result = 0;
		try {

			result = shopStockRecordMapper.insert(shopStockRecordDTO);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		logger.info("插入出入库记录成功" + shopStockRecordDTO.getId());
		return result;
	}

	@Override
	public ShopStockResponseDTO getShopStock(ShopStockRecordDTO shopStockRecordDTO) {
		logger.info("getShopStock方法传入的参数shopStockRecordDTO={}", shopStockRecordDTO);
		ShopStockRecordCriteria criteria = new ShopStockRecordCriteria();
		ShopStockRecordCriteria.Criteria c = criteria.createCriteria();

		if (StringUtils.isNotBlank(shopStockRecordDTO.getId())) {
			c.andIdEqualTo(shopStockRecordDTO.getId());
		}
		List<ShopStockRecordDTO> list = shopStockRecordMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("返回的集合为空");
			return null;
		}
		ShopStockRecordDTO shopStockRecord = list.get(0);
		String id = shopStockRecord.getId();
		// 根据id查询，shop_stock的入库，出库产品记录
		ShopProductInfoResponseDTO shopProductInfoResponseDTO = null;
		if (StringUtils.isBlank(id)) {
			logger.info("库存记录为空");
			return null;
		}
		ShopStockDTO shopStockDTO = new ShopStockDTO();
		shopStockDTO.setShopStockRecordId(id);
		List<ShopStockDTO> shopStockList = this.getShopStockList(shopStockDTO);
		if (CollectionUtils.isEmpty(shopStockList)) {
			return null;
		}
		List<String> shopProcIds = new ArrayList<>();
		for (ShopStockDTO shopStock : shopStockList) {
			shopProcIds.add(shopStock.getShopProcId());
		}
		List<ShopProductInfoResponseDTO> shopProductInfos = null;
		if (CollectionUtils.isNotEmpty(shopProcIds)) {
			// 查询产品信息
			shopProductInfos = shopProductInfoService.getProductInfoList(shopProcIds);
		}
		// 存放key是产品主键，value是产品对象
		Map<String, ShopProductInfoResponseDTO> map = new HashedMap();
		for (ShopProductInfoResponseDTO shopProductInfoResponse : shopProductInfos) {
			map.put(shopProductInfoResponse.getId(), shopProductInfoResponse);
		}
		List<ShopStockResponseDTO> shopStockResponses = new ArrayList<>();
		ShopStockResponseDTO shopStockResponseDTO = null;
		for (ShopStockDTO shopStock : shopStockList) {
			shopStockResponseDTO = new ShopStockResponseDTO();
			shopStockResponseDTO.setProductDate(shopStock.getProductDate());
			shopStockResponseDTO.setStockNumber(shopStock.getStockNumber());
			shopStockResponseDTO.setStockPrice(shopStock.getStockPrice());
			if (map.get(shopStock.getShopProcId()) != null) {
				shopStockResponseDTO.setProductCode(map.get(shopStock.getShopProcId()).getProductCode());
			}
			if ((map.get(shopStock.getShopProcId()) != null)) {
				shopStockResponseDTO.setProductSpec(map.get(shopStock.getShopProcId()).getProductSpec());
			}
			if (map.get(shopStock.getShopProcId()) != null) {
				shopStockResponseDTO.setProductUnit(map.get(shopStock.getShopProcId()).getProductUnit());
			}
			if (map.get(shopStock.getShopProcId()) != null) {
				shopStockResponseDTO.setImageUrl(map.get(shopStock.getShopProcId()).getImageUrl());
			}

			shopStockResponses.add(shopStockResponseDTO);
		}

		shopStockResponseDTO = new ShopStockResponseDTO();
		shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
		shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
		shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
		shopStockResponseDTO.setName(shopStockRecord.getName());
		shopStockResponseDTO.setStockType(shopStockRecord.getStockType());
		shopStockResponseDTO.setApplayUser(shopStockRecord.getManagerId());
		shopStockResponseDTO.setDetail(shopStockRecord.getDetail());
		shopStockResponseDTO.setShopStockResponseDTO(shopStockResponses);

		return shopStockResponseDTO;
	}

	@Override
	public List<ShopStockRecordDTO> getShopStockRecordList(PageParamVoDTO<ShopStockRecordDTO> pageParamVoDTO) {
		ShopStockRecordDTO shopStockRecord = pageParamVoDTO.getRequestData();
		if (shopStockRecord == null) {
			logger.info("getShopStockRecordList方法传入的参数shopStockRecord为空");
			return null;
		}

		ShopStockRecordCriteria criteria = new ShopStockRecordCriteria();
		ShopStockRecordCriteria.Criteria c = criteria.createCriteria();

		if (StringUtils.isNotBlank(shopStockRecord.getShopBossId())) {
			c.andShopBossIdEqualTo(shopStockRecord.getShopBossId());
		}
		if (StringUtils.isNotBlank(shopStockRecord.getShopStoreId())) {
			c.andShopStoreIdEqualTo(shopStockRecord.getShopStoreId());
		}
		if (StringUtils.isNotBlank(shopStockRecord.getStockStyle())) {
			if (StockStyleEnum.IN_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
				List<String> styles = new ArrayList<>();
				styles.add(StockStyleEnum.IN_STORAGE.getCode());
				styles.add(StockStyleEnum.SCAN_IN_STORAGE.getCode());
				c.andStockStyleIn(styles);
			}
			if (StockStyleEnum.OUT_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
				List<String> styles = new ArrayList<>();
				styles.add(StockStyleEnum.MANUAL_OUT_STORAGE.getCode());
				styles.add(StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode());
				c.andStockStyleIn(styles);
			}
			c.andStockStyleEqualTo(shopStockRecord.getStockStyle());
		}
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			c.andCreateDateBetween(startDate, endDate);
		}

		criteria.setPageSize(pageParamVoDTO.getPageSize());

		return shopStockRecordMapper.selectByCriteria(criteria);

	}

	@Override
	public List<ShopStockBossRelationDTO> getShopStockBossRelationList(ShopStockBossRelationDTO shopStockBossRelation) {
		logger.info("getShopStockBossRelationList方法传入的参数shopStockRecordDTO为shopStockRecordDTO={}",
				shopStockBossRelation);
		if (shopStockBossRelation == null) {
			logger.info("getShopStockBossRelationList方法传入的参数shopStockRecordDTO为空");
			return null;
		}
		ShopStockBossRelationCriteria criteria = new ShopStockBossRelationCriteria();
		ShopStockBossRelationCriteria.Criteria c = criteria.createCriteria();

		if (StringUtils.isNotBlank(shopStockBossRelation.getShopBossId())) {
			c.andShopBossIdEqualTo(shopStockBossRelation.getShopBossId());
		}
		if (StringUtils.isNotBlank(shopStockBossRelation.getShopStoreId())) {
			c.andShopStoreIdEqualTo(shopStockBossRelation.getShopStoreId());
		}
		List<ShopStockBossRelationDTO> list = shopStockBossRelationMapper.selectByCriteria(criteria);
		return list;
	}

	@Override
	public List<ShopStockDTO> getShopStockList(ShopStockDTO shopStockDTO) {
		logger.info("getShopStockList方法传入的参数shopStockDTO={}", shopStockDTO);
		if (shopStockDTO == null) {
			logger.info("getShopStockList方法传入的参数ShopStockDTO为空");
			return null;
		}

		ShopStockCriteria criteria = new ShopStockCriteria();
		ShopStockCriteria.Criteria c = criteria.createCriteria();
		if (StringUtils.isNotBlank(shopStockDTO.getShopStockRecordId())) {
			c.andShopStockRecordIdEqualTo(shopStockDTO.getShopStockRecordId());
		}
		List<ShopStockDTO> lis = shopStockMapper.selectByCriteria(criteria);
		return lis;
	}

	@Override
	public int insertShopStockDTO(String shopStockRequest) {
		List<ShopStockRequestDTO> shopStockRequestDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			shopStockRequestDTO = mapper.readValue(shopStockRequest, new TypeReference<List<ShopStockRequestDTO>>() {
			});
		} catch (IOException e) {
			logger.error("对象转换异常,异常信息是" + e.getMessage(), e);
		}
		if (CollectionUtils.isEmpty(shopStockRequestDTO)) {
			logger.info("转换出来的集合shopStocks为空");
			return 0;
		}
		// 记录插入
		ShopStockRequestDTO shopStockDto = shopStockRequestDTO.get(0);

		ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
		shopStockRecordDTO.setShopBossId(shopStockDto.getShopBossId());
		shopStockRecordDTO.setShopStoreId(shopStockDto.getShopStoreId());
		shopStockRecordDTO.setId(IdGen.uuid());
		shopStockRecordDTO.setCreateDate(new Date());
		shopStockRecordDTO.setFlowNo(shopStockDto.getFlowNo());
		shopStockRecordDTO.setStockType(shopStockDto.getStockType());
		shopStockRecordDTO.setManagerId(shopStockDto.getApplayUser());

		this.insertStockRecord(shopStockRecordDTO);
		// 记录插入结束
		List<ShopStockDTO> shopStockDTOList = new ArrayList<>();
		ShopStockDTO shopStockDTO = null;
		for (ShopStockRequestDTO shopStock : shopStockRequestDTO) {
			shopStockDTO = new ShopStockDTO();
			BeanUtils.copyProperties(shopStock, shopStockDTO);
			shopStockDTOList.add(shopStockDTO);
		}
		return extShopStockMapper.insertBatchShopStock(shopStockDTOList);

	}

	@Override
	public int updateStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
		ShopStockNumberDTO shopStockNumber = this.getStockNumber(shopStockNumberDTO);
		if (shopStockNumber == null) {
			return this.saveStockNumber(shopStockNumberDTO);
		}
		Integer stockNumber = shopStockNumberDTO.getStockNumber();
		Integer actualStockNumber = shopStockNumberDTO.getActualStockNumber();
		shopStockNumberDTO.setStockNumber(shopStockNumber.getStockNumber() + stockNumber);
		return shopStockNumberMapper.updateByPrimaryKeySelective(shopStockNumberDTO);

	}

	@Override
	public ShopStockNumberDTO getStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
		logger.info("getShopStock方法传入的参数shopStockNumberDTO={}", shopStockNumberDTO);
		ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
		if (StringUtils.isNotBlank(shopStockNumberDTO.getShopProcId())) {
			c.andShopProcIdEqualTo(shopStockNumberDTO.getShopProcId());
		}
		if (StringUtils.isNotBlank(shopStockNumberDTO.getShopStoreId())) {
			c.andShopStoreIdEqualTo(shopStockNumberDTO.getShopStoreId());
		}
		List<ShopStockNumberDTO> list = shopStockNumberMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public int saveStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
		return shopStockNumberMapper.insert(shopStockNumberDTO);
	}
}
