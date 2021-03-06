package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopUserProductRelationMapper;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShopCustomerProductRelationServiceImpl
 *
 * @Author： huan
 * 
 * @Description: 用户和产品相关的接口
 * @Date:Created in 2018/4/10 14:31
 * @since JDK 1.8
 */
@Service("shopCustomerProductRelationService")
public class ShopCustomerProductRelationServiceImpl implements ShopCustomerProductRelationService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopUserProductRelationMapper shopUserProductRelationMapper;

	@Autowired
	private ExtShopUserProductRelationMapper extShopUserProductRelationMapper;

	@Autowired
	private ShopProductInfoService shopProductInfoService;

	@Override
	public ShopUserProductRelationDTO getShopProductInfo(String shopProductId) {
		logger.info("getShopProductInfo方法传入的参数,shopProductId={}", shopProductId);
		if (StringUtils.isBlank(shopProductId)) {
			throw new ServiceException("getShopProductInfo方法传入的参数为空");
		}
		ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
		ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

		criteria.andIdEqualTo(shopProductId);

		List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper
				.selectByCriteria(shopUserProductRelationCriteria);
		if (CollectionUtils.isEmpty(shopUserProductRelationDTOS)) {
			logger.info("未获取到信息,getShopProductInfo查询的结果为空");
			return null;
		}
		ShopUserProductRelationDTO shopUserProductRelationDTO = shopUserProductRelationDTOS.get(0);
		return shopUserProductRelationDTO;
	}

	@Override
	public List<UserProductRelationResponseDTO> getShopUserProductRelations(String sysShopId, String searchFile) {
		logger.info("getShopProductInfo方法传入的参数,searchFile={}", searchFile);
		Map<String, String> mapFile = new HashMap(16);
		mapFile.put("sysShopId", sysShopId);
		if (StringUtils.isNotBlank(searchFile)) {
			mapFile.put("searchFile", "%" + searchFile + "%");
		} else {
			mapFile.put("searchFile", null);
		}
		List<UserProductRelationResponseDTO> list = extShopUserProductRelationMapper.getWaitReceiveNumber(mapFile);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getWaitReceiveNumber方法查询的结果为空");
			return null;
		}

		return list;
	}

	@Override
	public Map<String, Object> getWaitReceivePeopleAndNumber(String sysShopId) {
		logger.info("getWaitReceivePeopleAndNumber方法传入的参数,sysShopId={}", sysShopId);
		Map<String, String> mapFile = new HashMap(16);
		mapFile.put("sysShopId", sysShopId);
		mapFile.put("searchFile", null);
		List<UserProductRelationResponseDTO> list = extShopUserProductRelationMapper.getWaitReceiveNumber(mapFile);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getWaitReceivePeopleAndNumber方法查询的结果为空");
			return null;
		}
		// 待领取数量
		Integer totalWaitReceiveNumber = 0;
		// 待领取数量
		Integer totalWaitReceivePeople = list.size();
		// 遍历用户信息，并且将该用户对应的产品为领取数量放到一起
		for (UserProductRelationResponseDTO userProductRelationResponse : list) {
			totalWaitReceiveNumber = totalWaitReceiveNumber + userProductRelationResponse.getWaitReceiveNumber();
		}

		Map<String, Object> mapResponse = new HashMap<>(16);
		mapResponse.put("totalWaitReceiveNumber", totalWaitReceiveNumber);
		mapResponse.put("totalWaitReceivePeople", totalWaitReceivePeople);

		return mapResponse;
	}

	@Override
	public List<UserProductRelationResponseDTO> getShopUserProductRelationList(String sysUserId, String sysShopId) {
		logger.info("getShopUserProductRelationList方法传入的参数,sysUserId={},sysShopId={}", sysUserId, sysShopId);
		if (StringUtils.isBlank(sysUserId) || StringUtils.isBlank(sysShopId)) {
			throw new ServiceException("getShopProductInfo方法传入的参数为空");
		}
		ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
		ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

		criteria.andSysShopIdEqualTo(sysShopId);
		criteria.andSysUserIdEqualTo(sysUserId);

		List<ShopUserProductRelationDTO> shopUserProductRelations = shopUserProductRelationMapper
				.selectByCriteria(shopUserProductRelationCriteria);
		if (CollectionUtils.isEmpty(shopUserProductRelations)) {
			logger.info("shopUserProductRelations为空,shopUserProductRelationMapper未查询到结果");
			return null;
		}
		List<String> list = new ArrayList<>();
		// key是产品id，value是产品信息
		Map<String, ShopProductInfoResponseDTO> map = new HashMap<>(16);
		for (ShopUserProductRelationDTO shopUserProductRelation : shopUserProductRelations) {
			list.add(shopUserProductRelation.getShopProductId());
		}

		List<ShopProductInfoResponseDTO> shopProductInfos = shopProductInfoService.getProductInfoList(list);
		for (ShopProductInfoResponseDTO productInfo : shopProductInfos) {
			map.put(productInfo.getId(), productInfo);
		}
		List<UserProductRelationResponseDTO> responseList = new ArrayList<>();
		UserProductRelationResponseDTO userProductRelationResponseDTO = null;
		for (ShopUserProductRelationDTO shopUserProductRelation : shopUserProductRelations) {
			userProductRelationResponseDTO = new UserProductRelationResponseDTO();
			ShopProductInfoResponseDTO shopProductInfo = shopProductInfoService.getProductDetail(shopUserProductRelation.getShopProductId());
			if (shopProductInfo != null) {
				userProductRelationResponseDTO.setProductName(shopProductInfo.getProductName());
				userProductRelationResponseDTO.setProductSpec(shopProductInfo.getProductSpec());
				userProductRelationResponseDTO.setProductTypeOneName(shopProductInfo.getProductTypeOneName());
				userProductRelationResponseDTO.setProductTypeTwoName(shopProductInfo.getProductTypeTwoName());
				userProductRelationResponseDTO.setWaitReceiveNumber(shopUserProductRelation.getSurplusTimes());
				responseList.add(userProductRelationResponseDTO);
			}
		}

		return responseList;
	}

}
