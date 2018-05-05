package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.extDto.ImageUrl;
import com.wisdom.beauty.api.responseDto.ProductTypeResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.core.mapper.ShopProductInfoMapper;
import com.wisdom.beauty.core.mapper.ShopProductTypeMapper;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * FileName: ShopProductInfoServiceImpl
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 产品相关
 */
@Service("shopProductInfoService")
public class ShopProductInfoServiceImpl implements ShopProductInfoService {

	@Autowired
	private ShopUserProductRelationMapper shopUserProductRelationMapper;

	@Autowired
	private ShopProductTypeMapper shopProductTypeMapper;

	@Autowired
	private ShopProductInfoMapper shopProductInfoMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 根据条件查询产品列表
	 *
	 * @param shopProductInfoDTO
	 * @return
	 */
	@Override
	public List<ShopProductInfoDTO> getShopProductInfo(ShopProductInfoDTO shopProductInfoDTO) {
		if (CommonUtils.objectIsEmpty(shopProductInfoDTO)) {
			logger.info("根据条件查询产品列表传入参数为空");
			return null;
		}
		ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
		ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();

		if (StringUtils.isNotBlank(shopProductInfoDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopProductInfoDTO.getSysShopId());
		}

		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductName())) {
			criteria.andProductNameLike("%" + shopProductInfoDTO.getProductName() + "%");
		}

		List<ShopProductInfoDTO> shopProductInfoDTOS = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
		return shopProductInfoDTOS;
	}

	/**
	 * 获取用户的产品信息
	 *
	 * @param shopUserProductRelationDTO
	 * @return
	 */
	@Override
	public List<ShopUserProductRelationDTO> getUserProductInfoList(
			ShopUserProductRelationDTO shopUserProductRelationDTO) {

		if (shopUserProductRelationDTO == null) {
			logger.error("获取用户的产品信息为空");
			return null;
		}
		ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
		ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopUserProductRelationDTO.getSysShopId());
		}

		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysUserId())) {
			criteria.andSysUserIdEqualTo(shopUserProductRelationDTO.getSysUserId());
		}

		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getId())) {
			criteria.andIdEqualTo(shopUserProductRelationDTO.getId());
		}

		List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper
				.selectByCriteria(shopUserProductRelationCriteria);

		logger.debug("获取用户的产品信息大小为， {}", shopUserProductRelationDTOS != null ? shopUserProductRelationDTOS.size() : 0);
		return shopUserProductRelationDTOS;
	}

	/**
	 * 更新用户与产品的关系
	 *
	 * @param shopUserProductRelationDTO
	 * @return
	 */
	@Override
	public int updateShopUserProductRelation(ShopUserProductRelationDTO shopUserProductRelationDTO) {
		logger.info("更新用户与产品的关系传入参数={}", "shopUserProductRelationDTO = [" + shopUserProductRelationDTO + "]");
		int update = shopUserProductRelationMapper.updateByPrimaryKey(shopUserProductRelationDTO);
		logger.debug("更新用户与产品的关系,执行结果 {}", update > 0 ? "成功" : "失败");
		return update;
	}

	/**
	 * @Author:huan
	 * @Param: sysShopId
	 * @Return:
	 * @Description: 获取一级产品列表
	 * @Date:2018/4/10 15:59
	 */
	@Override
	public List<ShopProductTypeDTO> getOneLevelProductList(ShopProductTypeDTO shopProductTypeDTO) {
		logger.info("getOneLevelProjectList传入的参数,sysShopId={},productType={}", shopProductTypeDTO.getSysShopId(),shopProductTypeDTO.getProductType());
		if (shopProductTypeDTO==null||StringUtils.isBlank(shopProductTypeDTO.getSysShopId())) {
			logger.info("getOneLevelProjectList传入的参数sysShopId为空");
			return null;
		}
		ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
		ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
		criteria.andSysShopIdEqualTo(shopProductTypeDTO.getSysShopId());
		criteria.andParentIdIsNull();
		if(StringUtils.isNotBlank(shopProductTypeDTO.getProductType())){
			criteria.andProductTypeEqualTo(shopProductTypeDTO.getProductType());
		}
		List<ShopProductTypeDTO> list = shopProductTypeMapper.selectByCriteria(shopProductTypeCriteria);
		return list;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取二级产品列表
	 * @Date:2018/4/10 16:15
	 */
	@Override
	public List<ShopProductTypeDTO> getTwoLevelProductList(ShopProductTypeDTO shopProductTypeDTO) {
		logger.info("getTwoLevelProductList传入的参数,id={}", shopProductTypeDTO.getId());

		if (StringUtils.isBlank(shopProductTypeDTO.getId())) {
			logger.info("getTwoLevelProductList传入的参数id为空");
			return null;
		}
		ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
		ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
		criteria.andParentIdEqualTo(shopProductTypeDTO.getId());
		if(StringUtils.isNotBlank(shopProductTypeDTO.getProductType())){
			criteria.andProductTypeEqualTo(shopProductTypeDTO.getProductType());
		}
		List<ShopProductTypeDTO> list = shopProductTypeMapper.selectByCriteria(shopProductTypeCriteria);
		return list;
	}

	@Override
	public List<ShopProductInfoResponseDTO> getThreeLevelProductList(
			PageParamVoDTO<ShopProductInfoDTO> pageParamVoDTO) {
		ShopProductInfoDTO shopProductInfoDTO = pageParamVoDTO.getRequestData();
		logger.info("getThreeLevelProjectList传入的参数,sysShopId={},productTypeOneId={},productTypeTwoId={}",
				shopProductInfoDTO.getSysShopId(), shopProductInfoDTO.getProductTypeOneId(),
				shopProductInfoDTO.getProductTypeTwoId());

		if (StringUtils.isBlank(shopProductInfoDTO.getSysShopId())) {
			logger.info("getThreeLevelProductList方法的传入的参数sysShopId为空");
			return null;
		}

		ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
		ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();
		// 排序
		shopProductInfoCriteria.setOrderByClause("create_date");
		// 分页
		shopProductInfoCriteria.setLimitStart(pageParamVoDTO.getPageNo());
		shopProductInfoCriteria.setPageSize(pageParamVoDTO.getPageSize());
		criteria.andSysShopIdEqualTo(shopProductInfoDTO.getSysShopId());
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeOneId())) {
			criteria.andProductTypeOneIdEqualTo(shopProductInfoDTO.getProductTypeOneId());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeTwoId())) {
			criteria.andProductTypeTwoIdEqualTo(shopProductInfoDTO.getProductTypeTwoId());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductName())) {
			criteria.andProductNameLike("%" + shopProductInfoDTO.getProductName() + "%");
		}
		List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
		List<String> ids = new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			ids.add(shopProductInfo.getId());
		}
		List<ImageUrl> imageUrls = null;
		if (CollectionUtils.isNotEmpty(ids)) {
			Query query = new Query(Criteria.where("imageId").in(ids));
			imageUrls = mongoTemplate.find(query, ImageUrl.class, "imageUrl");
		}
		Map<String, String> map = null;
		if (CollectionUtils.isNotEmpty(imageUrls)) {
			map = new HashMap<>(16);
			for (ImageUrl imageUrl : imageUrls) {
				map.put(imageUrl.getImageId(), imageUrl.getUrl());
			}
		}
		ShopProductInfoResponseDTO shopProductInfoResponseDTO=null;
		List<ShopProductInfoResponseDTO> respon = new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			shopProductInfoResponseDTO= new ShopProductInfoResponseDTO();
			shopProductInfoResponseDTO.setId(shopProductInfo.getId());
			shopProductInfoResponseDTO.setDiscountPrice(shopProductInfo.getDiscountPrice());
			shopProductInfoResponseDTO.setProductName(shopProductInfo.getProductName());
			shopProductInfoResponseDTO.setProductTypeOneName(shopProductInfo.getProductTypeOneName());
			shopProductInfoResponseDTO.setProductTypeTwoName(shopProductInfo.getProductTypeTwoName());
			String[] urls = null;
			if (map != null && StringUtils.isNotBlank(map.get(shopProductInfo.getId()))) {
				urls = map.get(shopProductInfo.getId()).split("\\|");
			}
			if (urls != null) {
				shopProductInfoResponseDTO.setImageUrl(urls);
			}
			respon.add(shopProductInfoResponseDTO);
		}
		return respon;
	}

	@Override
	public ShopProductInfoResponseDTO getProductDetail(String id) {
		logger.info("getProductDetail传入的参数,id={}", id);

		if (StringUtils.isBlank(id)) {
			return null;
		}
		ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
		ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();

		criteria.andIdEqualTo(id);
		List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getProductDetail返回的结果为空");
			return null;
		}

		ShopProductInfoDTO shopProductInfo = list.get(0);

		Query query = new Query(Criteria.where("imageId").is(shopProductInfo.getId()));
		List<ImageUrl> imageUrls = mongoTemplate.find(query, ImageUrl.class, "imageUrl");

		ShopProductInfoResponseDTO shopProductInfoResponseDTO = new ShopProductInfoResponseDTO();
		shopProductInfoResponseDTO.setDiscountPrice(shopProductInfo.getDiscountPrice());
		shopProductInfoResponseDTO.setMarketPrice(shopProductInfo.getMarketPrice());
		shopProductInfoResponseDTO.setProductName(shopProductInfo.getProductName());
		shopProductInfoResponseDTO.setProductTypeOneName(shopProductInfo.getProductTypeOneName());
		shopProductInfoResponseDTO.setProductTypeTwoName(shopProductInfo.getProductTypeTwoName());
		shopProductInfoResponseDTO.setProductPosition(shopProductInfo.getProductPosition());
		shopProductInfoResponseDTO.setProductSpec(shopProductInfo.getProductSpec());
		shopProductInfoResponseDTO.setProductFunction(shopProductInfo.getProductFunction());
		shopProductInfoResponseDTO.setIntroduce(shopProductInfo.getIntroduce());
		if(	CollectionUtils.isNotEmpty(imageUrls)){
			ImageUrl imageUrl=imageUrls.get(0);
			String url=imageUrl.getUrl();
			if(StringUtils.isNotBlank(url)){
				shopProductInfoResponseDTO.setImageUrl(url.split("\\|"));
			}
		}
		return shopProductInfoResponseDTO;

	}

	@Override
	public List<ShopProductInfoResponseDTO> getProductInfoList(List<String> ids) {
		logger.info("getProductInfoList传入的参数,ids={}", ids);

		if (CollectionUtils.isEmpty(ids)) {
			return null;
		}
		ShopProductInfoCriteria shopProductInfoCriteria = new ShopProductInfoCriteria();
		ShopProductInfoCriteria.Criteria criteria = shopProductInfoCriteria.createCriteria();

		criteria.andIdIn(ids);
		List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);
		List<String> idList = new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			idList.add(shopProductInfo.getId());
		}
		List<ImageUrl> imageUrls = null;
		if (CollectionUtils.isNotEmpty(idList)) {
			Query query = new Query(Criteria.where("imageId").in(idList));
			imageUrls = mongoTemplate.find(query, ImageUrl.class, "imageUrl");
		}
		Map<String, String> map = null;
		if (CollectionUtils.isNotEmpty(imageUrls)) {
			map = new HashMap<>(16);
			for (ImageUrl imageUrl : imageUrls) {
				map.put(imageUrl.getImageId(), imageUrl.getUrl());
			}
		}
		ShopProductInfoResponseDTO shopProductInfoResponseDTO =null;
		List<ShopProductInfoResponseDTO> respon = new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			shopProductInfoResponseDTO= new ShopProductInfoResponseDTO();
			BeanUtils.copyProperties(shopProductInfoResponseDTO,shopProductInfo);
			String[] urls = null;
			if (map != null && StringUtils.isNotBlank(map.get(shopProductInfo.getId()))) {
				urls = map.get(shopProductInfo.getId()).split("\\|");
			}
			if (urls != null) {
				shopProductInfoResponseDTO.setImageUrl(urls);
			}
			respon.add(shopProductInfoResponseDTO);
		}
		return respon;
	}

	@Override
	public List<ProductTypeResponseDTO> getAllProducts(String sysShopId) {
		logger.info("getAllProducts方法传入参数sysShopId={}", sysShopId);
		ShopProductTypeDTO shopProductType=new ShopProductTypeDTO();
		shopProductType.setSysShopId(sysShopId);
		List<ShopProductTypeDTO> oneLevelProductList = this.getOneLevelProductList(shopProductType);

		// 查询ParentId不为空的产品类型，即二级产品
		ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
		ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
		criteria.andParentIdIsNotNull();
		List<ShopProductTypeDTO> twoLevelProductList = shopProductTypeMapper.selectByCriteria(shopProductTypeCriteria);
		Map<String, List<ProductTypeResponseDTO>> map = new HashMap<>(16);

		List<ProductTypeResponseDTO> list = null;
		ProductTypeResponseDTO productTypeResponse = null;
		for (ShopProductTypeDTO shopProductTypeDTO : twoLevelProductList) {
			productTypeResponse = new ProductTypeResponseDTO();
			if (map.get(shopProductTypeDTO.getParentId()) == null) {
				list = new ArrayList<>();
				productTypeResponse.setProductTypeName(shopProductTypeDTO.getProductTypeName());
				list.add(productTypeResponse);
				map.put(shopProductTypeDTO.getParentId(), list);
			} else {
				productTypeResponse.setProductTypeName(shopProductTypeDTO.getProductTypeName());
				map.get(shopProductTypeDTO.getParentId()).add(productTypeResponse);
			}

		}
		ProductTypeResponseDTO productTypeResponseDTO = null;
		List<ProductTypeResponseDTO> productTypeRespons = new ArrayList<>();
		for (ShopProductTypeDTO shopProductTypeDTO : oneLevelProductList) {
			productTypeResponseDTO = new ProductTypeResponseDTO();
			productTypeResponseDTO.setProductTypeName(shopProductTypeDTO.getProductTypeName());
			productTypeResponseDTO.setProductTypeResponses(map.get(shopProductTypeDTO.getId()));
			productTypeRespons.add(productTypeResponseDTO);
		}
		return productTypeRespons;
	}

	/**
	 * 保存用户与产品的关系
	 *
	 * @param shopUserProductRelationDTO
	 * @return
	 */
	@Override
	public int saveShopUserProductRelation(ShopUserProductRelationDTO shopUserProductRelationDTO) {

		if (CommonUtils.objectIsEmpty(shopUserProductRelationDTO)) {
			logger.debug("保存用户与产品的关系传入参数为空 {}", "shopUserProductRelationDTO = [" + shopUserProductRelationDTO + "]");
			return 0;
		}

		int insertFlag = shopUserProductRelationMapper.insertSelective(shopUserProductRelationDTO);

		return insertFlag;
	}

}
