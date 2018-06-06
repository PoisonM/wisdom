package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopRechargeCardOrderDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopProjectProductCardRelationMapper;
import com.wisdom.beauty.core.mapper.ShopRechargeCardMapper;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	private ShopUserRechargeCardMapper shopUserRechargeCardMapper;

	@Autowired
	private ShopProjectProductCardRelationMapper shopProjectProductCardRelationMapper;

	@Autowired
	private ExtShopProjectProductCardRelationMapper extShopProjectProductCardRelationMapper;

	@Autowired
	private MongoUtils mongoUtils;

	@Override
	public List<ShopRechargeCardOrderDTO> getShopRechargeCardList(PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO) {
		ShopRechargeCardDTO shopRechargeCardDTO = pageParamVoDTO.getRequestData();
		logger.info("getThreeLevelProjectList传入的参数,sysShopId={},name={}", shopRechargeCardDTO.getSysShopId(),
				shopRechargeCardDTO.getName());

		if (StringUtils.isBlank(shopRechargeCardDTO.getSysShopId())) {
			return null;
		}

		ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
		ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();
		// 排序
		shopRechargeCardCriteria.setOrderByClause("recharge_card_type asc");
		// 分页
		shopRechargeCardCriteria.setLimitStart(pageParamVoDTO.getPageNo());
		shopRechargeCardCriteria.setPageSize(pageParamVoDTO.getPageSize());
		criteria.andSysShopIdEqualTo(shopRechargeCardDTO.getSysShopId());

		if (StringUtils.isNotBlank(shopRechargeCardDTO.getName())) {
			criteria.andNameLike("%" + shopRechargeCardDTO.getName() + "%");
		}

		List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
		// 遍历list,将折扣信息和充值卡信息放入到新的list中
		List<ShopRechargeCardOrderDTO> shopRechargeCardResponseDTOs = new ArrayList<>();
		for (ShopRechargeCardDTO shopRechargeCard : list) {
			ShopRechargeCardOrderDTO shopRechargeCardOrderDTO = new ShopRechargeCardOrderDTO();
			BeanUtils.copyProperties(shopRechargeCard, shopRechargeCardOrderDTO);
			shopRechargeCardResponseDTOs.add(shopRechargeCardOrderDTO);
		}

		return shopRechargeCardResponseDTOs;
	}

	@Override
	public ShopRechargeCardResponseDTO getShopRechargeCard(ShopRechargeCardDTO shopRechargeCardDTO) {
		logger.info("getThreeLevelProjectList传入的参数,id={}", shopRechargeCardDTO);

		if (null == shopRechargeCardDTO) {
			return null;
		}

		ShopRechargeCardCriteria shopRechargeCardCriteria = new ShopRechargeCardCriteria();
		ShopRechargeCardCriteria.Criteria criteria = shopRechargeCardCriteria.createCriteria();

		if (StringUtils.isNotBlank(shopRechargeCardDTO.getId())) {
			criteria.andIdEqualTo(shopRechargeCardDTO.getId());
		}

		if (StringUtils.isNotBlank(shopRechargeCardDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopRechargeCardDTO.getSysShopId());
		}

		List<ShopRechargeCardDTO> list = shopRechargeCardMapper.selectByCriteria(shopRechargeCardCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("shopRechargeCardMapper.selectByCriteria()方法查询结果为空");
			return null;
		}

		shopRechargeCardDTO = list.get(0);

		ShopRechargeCardResponseDTO shopRechargeCardResponseDTO = new ShopRechargeCardResponseDTO();
		BeanUtils.copyProperties(shopRechargeCardDTO, shopRechargeCardResponseDTO);
		List<String> imageUrl = mongoUtils.getImageUrl(shopRechargeCardDTO.getId());
		if (StringUtils.isNotBlank(shopRechargeCardDTO.getImageUrl()) && CommonUtils.objectIsEmpty(imageUrl)) {
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add(shopRechargeCardDTO.getImageUrl());
			shopRechargeCardResponseDTO.setImageUrls(arrayList);
			shopRechargeCardResponseDTO.setImageUrls(arrayList);
		} else {
			shopRechargeCardResponseDTO.setImageUrls(imageUrl);
		}

		return shopRechargeCardResponseDTO;
	}

	/**
	 * 更新用户的充值卡信息
	 */
	@Override
	public int updateRechargeCard(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
		logger.info("更新用户的充值卡信息传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");

		if (null == shopUserRechargeCardDTO || StringUtils.isBlank(shopUserRechargeCardDTO.getId())) {
			logger.error("更新用户的充值卡信息传入参数为空");
			return 0;
		}
		int flag = shopUserRechargeCardMapper.updateByPrimaryKeySelective(shopUserRechargeCardDTO);
		return flag;
	}

	/**
	 * 查询用户的充值卡
	 *
	 * @param shopUserRechargeCardDTO
	 * @return
	 */
	@Override
	public ShopUserRechargeCardDTO getShopUserRechargeInfo(ShopUserRechargeCardDTO shopUserRechargeCardDTO) {
		logger.info("查询用户的充值卡信息传入参数={}", "shopUserRechargeCardDTO = [" + shopUserRechargeCardDTO + "]");

		ShopUserRechargeCardCriteria criteria = new ShopUserRechargeCardCriteria();
		ShopUserRechargeCardCriteria.Criteria c = criteria.createCriteria();
		if (null == shopUserRechargeCardDTO) {
			logger.error("查询用户的充值卡传入参数为空");
			return null;
		}

		if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getSysUserId())) {
			c.andSysUserIdEqualTo(shopUserRechargeCardDTO.getSysUserId());
		}

		if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getId())) {
			c.andIdEqualTo(shopUserRechargeCardDTO.getId());
		}

		if (StringUtils.isNotBlank(shopUserRechargeCardDTO.getShopRechargeCardId())) {
			c.andShopRechargeCardIdEqualTo(shopUserRechargeCardDTO.getShopRechargeCardId());
		}

		List<ShopUserRechargeCardDTO> rechargeCardDTOS = shopUserRechargeCardMapper.selectByCriteria(criteria);

		ShopUserRechargeCardDTO cardDTO = new ShopUserRechargeCardDTO();
		if (CommonUtils.objectIsNotEmpty(rechargeCardDTOS)) {
			cardDTO = rechargeCardDTOS.get(0);
		}
		return cardDTO;
	}

	/**
	 * 生产用户的充值卡
	 *
	 * @param userRechargeCardDTO
	 * @return
	 */
	@Override
	public int saveShopUserRechargeCardInfo(ShopUserRechargeCardDTO userRechargeCardDTO) {
		return shopUserRechargeCardMapper.insert(userRechargeCardDTO);
	}
}
