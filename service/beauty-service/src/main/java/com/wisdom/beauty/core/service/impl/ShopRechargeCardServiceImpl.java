package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationCriteria;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.dto.ShopRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ImageUrl;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopRechargeCardMapper;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName: ShopRechargeCardServiceImpl
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/11 11:41
 * @since JDK 1.8
 */
@Service("shopRechargeCardService")
public class ShopRechargeCardServiceImpl implements ShopRechargeCardService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopRechargeCardMapper shopRechargeCardMapper;

	@Autowired
	private ShopProjectProductCardRelationMapper shopProjectProductCardRelationMapper;

	@Autowired
	private ExtShopProjectProductCardRelationMapper extShopProjectProductCardRelationMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<ShopRechargeCardResponseDTO> getShopRechargeCardList(
			PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO) {
		ShopRechargeCardDTO shopRechargeCardDTO = pageParamVoDTO.getRequestData();
		logger.info("getThreeLevelProjectList传入的参数,sysShopId={},name={}", shopRechargeCardDTO.getSysShopId(),
				shopRechargeCardDTO.getName());

		if (StringUtils.isBlank(shopRechargeCardDTO.getSysShopId())) {
			return null;
		}

		ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
		ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();
		// 排序
		shopRechargeCardCriteria.setOrderByClause("create_date");
		// 分页
		shopRechargeCardCriteria.setLimitStart(pageParamVoDTO.getPageNo());
		shopRechargeCardCriteria.setPageSize(pageParamVoDTO.getPageSize());
		criteria.andSysShopIdEqualTo(shopRechargeCardDTO.getSysShopId());

		if (StringUtils.isNotBlank(shopRechargeCardDTO.getName())) {
			criteria.andNameLike("%" + shopRechargeCardDTO.getName() + "%");
		}

		List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
		List<String> ids = new ArrayList<>();
		// 遍历list,将折扣信息和充值卡信息放入到新的list中
		List<ShopRechargeCardResponseDTO> shopRechargeCardResponseDTOs = new ArrayList<>();
		ShopRechargeCardResponseDTO shopRechargeCardResponseDTO = null;
		for (ShopRechargeCardDTO shopRechargeCard : list) {
			shopRechargeCardResponseDTO = new ShopRechargeCardResponseDTO();
			shopRechargeCardResponseDTO.setShopRechargeCardId(shopRechargeCard.getId());
			shopRechargeCardResponseDTO.setAmount(shopRechargeCard.getAmount());
			shopRechargeCardResponseDTO.setDiscountDesc(shopRechargeCard.getDiscountDesc());
			shopRechargeCardResponseDTO.setIntroduce(shopRechargeCard.getIntroduce());
			shopRechargeCardResponseDTO.setName(shopRechargeCard.getName());
			shopRechargeCardResponseDTO.setShopRechargeCardId(shopRechargeCard.getId());
			// 将充值卡id放入集合中
			ids.add(shopRechargeCard.getId());
			shopRechargeCardResponseDTOs.add(shopRechargeCardResponseDTO);
			shopRechargeCardResponseDTO = null;
		}
		// 查询mongodb中的图片地址
		List<ImageUrl> imageUrls = null;
		if (CollectionUtils.isNotEmpty(ids)) {
			Query query = new Query(Criteria.where("imageId").in(ids));
			imageUrls = mongoTemplate.find(query, ImageUrl.class, "imageUrl");
		}
		Map<String, String> urlMap = null;
		if (CollectionUtils.isNotEmpty(imageUrls)) {
			urlMap = new HashMap<>(16);
			for (ImageUrl imageUrl : imageUrls) {
				urlMap.put(imageUrl.getImageId(), imageUrl.getUrl());
			}
		}
		Map<String, Map<String, Object>> map = this.getDiscount(ids);

		Iterator<ShopRechargeCardResponseDTO> iterator = shopRechargeCardResponseDTOs.iterator();
		List<ShopRechargeCardResponseDTO> shopRechargeCardResponses = new ArrayList<>();
		while (iterator.hasNext()) {
			ShopRechargeCardResponseDTO rechargeCardResponse = iterator.next();
			if (map != null) {
				rechargeCardResponse.setMap(map.get(rechargeCardResponse.getShopRechargeCardId()));
			}
			String[] urls = null;
			if (urlMap != null && StringUtils.isNotBlank(urlMap.get(rechargeCardResponse.getShopRechargeCardId()))) {
				urls = urlMap.get(rechargeCardResponse.getShopRechargeCardId()).split("\\|");
			}
			if (urls != null) {
				rechargeCardResponse.setImageUrl(urls);
			}
			shopRechargeCardResponses.add(rechargeCardResponse);
		}

		return shopRechargeCardResponseDTOs;
	}

	@Override
	public ShopRechargeCardResponseDTO getShopRechargeCard(String id) {
		logger.info("getThreeLevelProjectList传入的参数,id={}", id);

		if (StringUtils.isBlank(id)) {
			return null;
		}

		ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
		ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();

		criteria.andIdEqualTo(id);
		List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("shopRechargeCardMapper.selectByCriteria()方法查询结果为空");
			return null;
		}

		ShopRechargeCardDTO shopRechargeCardDTO = list.get(0);
		logger.info("充值卡的id={}", shopRechargeCardDTO.getId());
		// 获取折扣信息
		List<String> ids = new ArrayList<>();
		ids.add(shopRechargeCardDTO.getId());
		Map<String, Map<String, Object>> map = this.getDiscount(ids);
		Map<String, Object> discountMap = null;
		if (map != null) {
			discountMap = map.get(shopRechargeCardDTO.getId());
		}
		ShopRechargeCardResponseDTO shopRechargeCardResponseDTO = new ShopRechargeCardResponseDTO();
		shopRechargeCardResponseDTO.setMap(discountMap);
		shopRechargeCardResponseDTO.setName(shopRechargeCardDTO.getName());
		shopRechargeCardResponseDTO.setIntroduce(shopRechargeCardDTO.getIntroduce());
		shopRechargeCardResponseDTO.setAmount(shopRechargeCardDTO.getAmount());
		shopRechargeCardResponseDTO.setDiscountDesc(shopRechargeCardDTO.getDiscountDesc());

		Query query = new Query(Criteria.where("imageId").is(shopRechargeCardDTO.getId()));
		List<ImageUrl> imageUrls = mongoTemplate.find(query, ImageUrl.class, "imageUrl");

		if (CollectionUtils.isNotEmpty(imageUrls)) {
			ImageUrl imageUrl = imageUrls.get(0);
			String url = imageUrl.getUrl();
			if (StringUtils.isNotBlank(url)) {
				shopRechargeCardResponseDTO.setImageUrl(url.split("\\|"));
			}
		}
		return shopRechargeCardResponseDTO;
	}

	@Override
	public Float getDiscount(String queryCriteria, String rechargeCardId, String type) {
		logger.info("getDiscount传入的参数,queryCriteria={},rechargeCardId={}", queryCriteria, rechargeCardId);

		if (StringUtils.isBlank(rechargeCardId)) {
			logger.info("getDiscount传入的参数方法传入的参数为空");
			return null;
		}

		ShopProjectProductCardRelationCriteria shopProjectProductCardRelationCriteria = new ShopProjectProductCardRelationCriteria();
		ShopProjectProductCardRelationCriteria.Criteria criteria = shopProjectProductCardRelationCriteria
				.createCriteria();

		criteria.andShopRechargeCardIdEqualTo(rechargeCardId);
		// 判断type是否为空，为空的时候使用在不需要更具产品Id或者项目id作为查询条件
		if (StringUtils.isNotBlank(type)) {
			// 如果是产品类型的
			if (GoodsTypeEnum.PRODUCT.getCode().equals(type)) {
				criteria.andShopGoodsTypeIdEqualTo(queryCriteria);
			} else {
				// 项目类型
				criteria.andShopGoodsTypeIdEqualTo(queryCriteria);
			}
		}
		List<ShopProjectProductCardRelationDTO> list = shopProjectProductCardRelationMapper
				.selectByCriteria(shopProjectProductCardRelationCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("接口shopRechargeCardMapper#selectByCriteria()查询结果为空");
			return null;
		}
		ShopProjectProductCardRelationDTO shopProjectProductCardRelation = list.get(0);
		Float discount = shopProjectProductCardRelation.getDiscount();
		logger.info("getDiscount接口获取到的折扣信息={}", discount);
		return discount;
	}

	@Override
	public Map<String, Map<String, Object>> getDiscount(List<String> rechargeCardIds) {
		logger.info("getDiscount传入的参数rechargeCardId={}", rechargeCardIds);

		if (CollectionUtils.isEmpty(rechargeCardIds)) {
			logger.info("getDiscount传入的参数方法传入的参数为空");
			return null;
		}

		ShopProjectProductCardRelationCriteria shopProjectProductCardRelationCriteria = new ShopProjectProductCardRelationCriteria();
		ShopProjectProductCardRelationCriteria.Criteria criteria = shopProjectProductCardRelationCriteria
				.createCriteria();

		criteria.andShopRechargeCardIdIn(rechargeCardIds);
		List<ShopProjectProductCardRelationDTO> shopProjectProductCardRelations = extShopProjectProductCardRelationMapper
				.selectdiscountInfoByCriteria(shopProjectProductCardRelationCriteria);
		if (CollectionUtils.isEmpty(shopProjectProductCardRelations)) {
			logger.info("接口shopRechargeCardMapper#selectByCriteria()查询结果为空");
			return null;
		}
		Map<String, Map<String, Object>> map = new HashMap<>(16);
		Map<String, Object> dicountMap = new HashMap<>(16);
		for (ShopProjectProductCardRelationDTO shopProjectProductCardRelation : shopProjectProductCardRelations) {
			if (map.get(shopProjectProductCardRelation.getShopRechargeCardId()) == null) {
				// 将类型和折扣放入一个dicountMap中
				dicountMap.put(shopProjectProductCardRelation.getGoodsType(),
						shopProjectProductCardRelation.getDiscount());
				// 将dicountMap放入map中,key是充值卡id
				map.put(shopProjectProductCardRelation.getShopRechargeCardId(), dicountMap);
			} else {
				Map<String, Object> map1 = map.get(shopProjectProductCardRelation.getShopRechargeCardId());
				map1.put(shopProjectProductCardRelation.getGoodsType(), shopProjectProductCardRelation.getDiscount());
			}

		}
		logger.info("getDiscount接口获取到的折扣信息结果map={}", map);
		return map;
	}
}
