package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ImageEnum;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.responseDto.ProductTypeResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopProductInfoMapper;
import com.wisdom.beauty.core.mapper.ShopProductInfoMapper;
import com.wisdom.beauty.core.mapper.ShopProductTypeMapper;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private ExtShopProductInfoMapper extShopProductInfoMapper;

    @Autowired
    private MongoUtils mongoUtils;

	@Autowired
	private ShopStockService shopStockService;

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

		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductCode())) {
			criteria.andProductCodeEqualTo(shopProductInfoDTO.getProductCode());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopProductInfoDTO.getSysShopId());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeOneId())) {
			criteria.andProductTypeOneIdEqualTo(shopProductInfoDTO.getProductTypeOneId());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductTypeTwoId())) {
			criteria.andProductTypeTwoIdEqualTo(shopProductInfoDTO.getProductTypeTwoId());
		}
		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductType())) {
			criteria.andProductTypeEqualTo(shopProductInfoDTO.getProductType());
		}

		if (StringUtils.isNotBlank(shopProductInfoDTO.getProductName())) {
			criteria.andProductNameLike("%" + shopProductInfoDTO.getProductName() + "%");
		}

		
		if(StringUtils.isNotBlank(shopProductInfoDTO.getStatus())){
		    criteria.andStatusEqualTo(shopProductInfoDTO.getStatus());
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
        //根据创建时间排序
		shopUserProductRelationCriteria.setOrderByClause("create_date desc");
		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopUserProductRelationDTO.getSysShopId());
		}

		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getSysUserId())) {
			criteria.andSysUserIdEqualTo(shopUserProductRelationDTO.getSysUserId());
		}

		if (StringUtils.isNotBlank(shopUserProductRelationDTO.getId())) {
			criteria.andIdEqualTo(shopUserProductRelationDTO.getId());
		}
		if(null != shopUserProductRelationDTO.getSurplusTimes() && shopUserProductRelationDTO.getSurplusTimes() > 0){
			criteria.andSurplusTimesGreaterThanOrEqualTo(shopUserProductRelationDTO.getSurplusTimes());
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
		int update = shopUserProductRelationMapper.updateByPrimaryKeySelective(shopUserProductRelationDTO);
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
		if(StringUtils.isNotBlank(shopProductTypeDTO.getProductTypeName())){
			criteria.andProductTypeNameEqualTo(shopProductTypeDTO.getProductTypeName());
		}
		if(StringUtils.isNotBlank(shopProductTypeDTO.getStatus())){
			criteria.andStatusEqualTo(shopProductTypeDTO.getStatus());
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
		}
		ShopProductTypeCriteria shopProductTypeCriteria = new ShopProductTypeCriteria();
		ShopProductTypeCriteria.Criteria criteria = shopProductTypeCriteria.createCriteria();
		if(StringUtils.isNotBlank(shopProductTypeDTO.getId())){
			criteria.andParentIdEqualTo(shopProductTypeDTO.getId());
		}
		if(StringUtils.isNotBlank(shopProductTypeDTO.getProductType())){
			criteria.andProductTypeEqualTo(shopProductTypeDTO.getProductType());
		}
		if(StringUtils.isNotBlank(shopProductTypeDTO.getSysShopId())){
			criteria.andSysShopIdEqualTo(shopProductTypeDTO.getSysShopId());
		}
		if(StringUtils.isNotBlank(shopProductTypeDTO.getStatus())){
			criteria.andStatusEqualTo(shopProductTypeDTO.getStatus());
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
		if (StringUtils.isNotBlank(shopProductInfoDTO.getStatus())) {
			criteria.andStatusEqualTo(shopProductInfoDTO.getStatus());
		}
		List<ShopProductInfoDTO> list = shopProductInfoMapper.selectByCriteria(shopProductInfoCriteria);

		List<ShopProductInfoResponseDTO> respon = new ArrayList<>();
		List<String> shopProcIds=new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			shopProcIds.add(shopProductInfo.getId());
		}
		//获取产品的库存量
		List<ShopStockNumberDTO> shopStockNumberList= shopStockService.getStockNumberList(shopProductInfoDTO.getSysShopId(), shopProcIds);
		Map<String,Integer> shopStockNumberMap=new HashMap<>(16);
		if(CollectionUtils.isNotEmpty(shopStockNumberList)){
			for (ShopStockNumberDTO dto:shopStockNumberList){
				shopStockNumberMap.put(dto.getShopProcId(),dto.getStockNumber());
			}
		}
		for (ShopProductInfoDTO shopProductInfo : list) {
			shopProcIds.add(shopProductInfo.getId());
			ShopProductInfoResponseDTO shopProductInfoResponseDTO = new ShopProductInfoResponseDTO();
			BeanUtils.copyProperties(shopProductInfo, shopProductInfoResponseDTO);
			shopProductInfoResponseDTO.setImageList(mongoUtils.getImageUrl(shopProductInfo.getId()));
			shopProductInfoResponseDTO.setStockNumber(shopStockNumberMap.get(shopProductInfo.getId())==null?0:shopStockNumberMap.get(shopProductInfo.getId()));
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
		ShopProductInfoResponseDTO shopProductInfoResponseDTO = new ShopProductInfoResponseDTO();
        BeanUtils.copyProperties(shopProductInfo, shopProductInfoResponseDTO);
        shopProductInfoResponseDTO.setImageList(mongoUtils.getImageUrl(shopProductInfo.getId()));
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

		ShopProductInfoResponseDTO shopProductInfoResponseDTO =null;
		List<ShopProductInfoResponseDTO> respon = new ArrayList<>();
		for (ShopProductInfoDTO shopProductInfo : list) {
			shopProductInfoResponseDTO= new ShopProductInfoResponseDTO();
			BeanUtils.copyProperties(shopProductInfo,shopProductInfoResponseDTO);
            shopProductInfoResponseDTO.setImageList(mongoUtils.getImageUrl(shopProductInfo.getId()));
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

	/**
	 * 更新产品类别列表
	 *
	 * @param shopProductTypeDTOS
	 * @return
	 */
	@Override
	public int updateProductTypeListInfo(List<ShopProductTypeDTO> shopProductTypeDTOS) {
		int returnFlag = 0;
		logger.info("更新产品类别列表传入参数={}", "shopProductTypeDTOS = [" + shopProductTypeDTOS + "]");
		if (CommonUtils.objectIsNotEmpty(shopProductTypeDTOS)) {
			for (ShopProductTypeDTO dto : shopProductTypeDTOS) {
                dto.setSysShopId(UserUtils.getBossInfo().getCurrentShopId());
				//id为空则是新增
				if (StringUtils.isBlank(dto.getId())) {
					logger.info("主键为空，说明是新增记录={}", dto.getProductTypeName());
					returnFlag = saveProductTypeInfo(dto);
				} else {
					logger.info("主键不为空，说明是修改记录={}", dto.getProductTypeName());
					returnFlag = updateProductTypeInfo(dto);
					//修改产品的二级名称
					ShopProductInfoDTO shopProjectInfoDTO = new ShopProductInfoDTO();
					shopProjectInfoDTO.setProductTypeTwoName(dto.getProductTypeName());
					ShopProductInfoCriteria productInfoCriteria = new ShopProductInfoCriteria();
					ShopProductInfoCriteria.Criteria c = productInfoCriteria.createCriteria();
					c.andProductTypeTwoIdEqualTo(dto.getId());
					shopProductInfoMapper.updateByCriteriaSelective(shopProjectInfoDTO,productInfoCriteria);
				}
			}
		}
		return returnFlag;
	}

	/**
	 * 更新产品类别
	 *
	 * @param shopProductTypeDTOS
	 * @return
	 */
	@Override
	public int updateProductTypeInfo(ShopProductTypeDTO shopProductTypeDTOS) {
		if (CommonUtils.objectIsEmpty(shopProductTypeDTOS) || StringUtils.isBlank(shopProductTypeDTOS.getId())) {
			logger.error("{}", "shopProductTypeDTOS = [" + shopProductTypeDTOS + "]");
			return 0;
		}
		String status = shopProductTypeDTOS.getStatus();
		String oneId = shopProductTypeDTOS.getId();
		//修改二级类别和三级产品的状态
		if(StringUtils.isBlank(shopProductTypeDTOS.getParentId()) && StringUtils.isNotBlank(status)){
			//产品
			ShopProductInfoDTO shopProjectInfoDTO = new ShopProductInfoDTO();
			shopProjectInfoDTO.setStatus(status);
			shopProjectInfoDTO.setProductTypeOneName(shopProductTypeDTOS.getProductTypeName());
			ShopProductInfoCriteria productInfoCriteria = new ShopProductInfoCriteria();
			ShopProductInfoCriteria.Criteria c = productInfoCriteria.createCriteria();
			c.andProductTypeOneIdEqualTo(oneId);
			shopProductInfoMapper.updateByCriteriaSelective(shopProjectInfoDTO,productInfoCriteria);
			//二级类别
			ShopProductTypeDTO twoLevel = new ShopProductTypeDTO();
			twoLevel.setStatus(status);
			ShopProductTypeCriteria criteria = new ShopProductTypeCriteria();
			ShopProductTypeCriteria.Criteria cc = criteria.createCriteria();
			cc.andParentIdEqualTo(oneId);
			shopProductTypeMapper.updateByCriteriaSelective(twoLevel,criteria);
		}
		shopProductTypeDTOS.setUpdateDate(new Date());
		return shopProductTypeMapper.updateByPrimaryKeySelective(shopProductTypeDTOS);
	}

	/**
	 * 更新产品信息
	 *
	 * @param extShopProductInfoDTO
	 * @return
	 */
	@Override
	public int updateProductInfo(ExtShopProductInfoDTO extShopProductInfoDTO) {
		if (CommonUtils.objectIsEmpty(extShopProductInfoDTO) || StringUtils.isBlank(extShopProductInfoDTO.getId())) {
			logger.error("{}", "extShopProductInfoDTO = [" + extShopProductInfoDTO + "]");
			return 0;
		}
		//保存图片信息
		if (CommonUtils.objectIsNotEmpty(extShopProductInfoDTO.getImageList())) {
			mongoUtils.updateImageUrl(extShopProductInfoDTO.getImageList(), extShopProductInfoDTO.getId());
			extShopProductInfoDTO.setProductUrl(extShopProductInfoDTO.getImageList().get(0));
		}
		extShopProductInfoDTO.setUpdateDate(new Date());
		return shopProductInfoMapper.updateByPrimaryKeySelective(extShopProductInfoDTO);
	}

	/**
	 * 添加某个店的某个产品类别
	 *
	 * @param shopProductTypeDTOS
	 */
	@Override
	public int saveProductTypeInfo(ShopProductTypeDTO shopProductTypeDTOS) {

		logger.info("添加某个店的某个产品类别传入参数={}", "shopProductTypeDTOS = [" + shopProductTypeDTOS + "]");
		if (CommonUtils.objectIsEmpty(shopProductTypeDTOS)) {
			logger.error("添加某个店的某个产品类别{}", "shopProductTypeDTOS = [" + shopProductTypeDTOS + "]");
			return 0;
		}
		shopProductTypeDTOS.setCreateDate(new Date());
		shopProductTypeDTOS.setId(IdGen.uuid());
		return shopProductTypeMapper.insertSelective(shopProductTypeDTOS);
	}

	/**
	 * 添加产品信息
	 *
	 * @param shopProductInfoDTO
	 * @return
	 */
	@Override

	public int saveProductInfo(ExtShopProductInfoDTO shopProductInfoDTO) {
		shopProductInfoDTO.setSysShopId(UserUtils.getBossInfo().getCurrentShopId());
		shopProductInfoDTO.setId(IdGen.uuid());
		if (StringUtils.isNotBlank(shopProductInfoDTO.getEffectDate()) && shopProductInfoDTO.getQualityPeriod() > 0) {
			shopProductInfoDTO.setInvalidDate(DateUtils.dateSubMonth(shopProductInfoDTO.getEffectDate(), shopProductInfoDTO.getQualityPeriod()));
		}

		//图片保存 到mongodb
		if (CommonUtils.objectIsNotEmpty(shopProductInfoDTO.getImageList())) {
			shopProductInfoDTO.setProductUrl(shopProductInfoDTO.getImageList().get(0));
		}else{
			shopProductInfoDTO.setProductUrl(ImageEnum.GOODS_CARD.getDesc());
			ArrayList<String> str = new ArrayList<>();
			str.add(ImageEnum.GOODS_CARD.getDesc());
			shopProductInfoDTO.setImageList(str);
		}
		int insertSelective = shopProductInfoMapper.insertSelective(shopProductInfoDTO);
		mongoUtils.saveImageUrl(shopProductInfoDTO.getImageList(), shopProductInfoDTO.getId());
		logger.info("添加产品信息保存={}", insertSelective > 0 ? "成功" : "失败");
		return insertSelective;
	}


	/**
	 * 根据条件查询产品列表
	 *
	 * @param shopProductInfoDTO
	 * @return
	 */
	public List<ShopProductInfoDTO> ShopProductInfofindList(ShopProductInfoDTO shopProductInfoDTO) {
		if (CommonUtils.objectIsEmpty(shopProductInfoDTO)) {
			logger.info("根据条件查询产品列表传入参数为空");
			return null;
		}
		List<ShopProductInfoDTO> shopProductInfoDTOS = extShopProductInfoMapper.findEarlyWarningProductLevelInfo(shopProductInfoDTO);
		return shopProductInfoDTOS;
	}
}
