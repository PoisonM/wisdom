package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ImageEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.core.mapper.*;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: ShopProjectService
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 项目相关
 */
@Service("shopProjectService")
public class ShopProjectServiceImpl implements ShopProjectService {

	@Autowired
	public ShopUserProjectRelationMapper shopUserProjectRelationMapper;

	@Autowired
	public ShopProjectInfoMapper shopProjectInfoMapper;

	@Autowired
	public ShopProjectInfoGroupRelationMapper shopProjectInfoGroupRelationMapper;

	@Autowired
	public ShopUserProjectGroupRelRelationMapper shopUserProjectGroupRelRelationMapper;

	@Autowired
	private ShopProjectTypeMapper shopProjectTypeMapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
    private MongoUtils mongoUtils;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取用户的项目信息
	 *
	 * @param shopUserProjectRelationDTO
	 * @return
	 */
	@Override
	public List<ShopUserProjectRelationDTO> getUserProjectList(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

		if (CommonUtils.objectIsEmpty(shopUserProjectRelationDTO)) {
			logger.info("获取用户预约的项目传入参数={}", "shopUserProductRelationDTO = [" + shopUserProjectRelationDTO + "]");
			return null;
		}

		ShopUserProjectRelationCriteria shopUserProjectRelationCriteria = new ShopUserProjectRelationCriteria();
		ShopUserProjectRelationCriteria.Criteria criteria = shopUserProjectRelationCriteria.createCriteria();
        //根据创建时间排序
		shopUserProjectRelationCriteria.setOrderByClause("create_date desc");
		// 根据预约主键查询用户的预约项目
		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getShopAppointmentId())) {
			criteria.andShopAppointmentIdEqualTo(shopUserProjectRelationDTO.getShopAppointmentId());
		}

		// 查询用户的疗程卡或者是单卡信息
		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getUseStyle())) {
			criteria.andUseStyleEqualTo(shopUserProjectRelationDTO.getUseStyle());
		}

		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getId())) {
			criteria.andIdEqualTo(shopUserProjectRelationDTO.getId());
		}

		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getSysShopProjectId())) {
			criteria.andSysShopProjectIdEqualTo(shopUserProjectRelationDTO.getSysShopProjectId());
		}

		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getSysUserId())) {
			criteria.andSysUserIdEqualTo(shopUserProjectRelationDTO.getSysUserId());
		}

		if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopUserProjectRelationDTO.getSysShopId());
		}

		if (null != shopUserProjectRelationDTO.getSysShopProjectSurplusTimes()
				&& shopUserProjectRelationDTO.getSysShopProjectSurplusTimes() > 0) {
			criteria.andSysShopProjectSurplusTimesGreaterThanOrEqualTo(
					shopUserProjectRelationDTO.getSysShopProjectSurplusTimes());
		}

		List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserProjectRelationMapper
				.selectByCriteria(shopUserProjectRelationCriteria);

		return projectRelationDTOS;
	}

	/**
	 * 根据用户与项目的关系主键列表查询用户与项目的关系
	 *
	 * @param relationId
	 * @return
	 */
	@Override
	public List<ShopUserProjectRelationDTO> getUserShopProjectList(List<String> relationId) {

		if (CommonUtils.objectIsEmpty(relationId)) {
			logger.info("根据用户与项目的关系主键列表查询用户与项目的关系传入参数={}", "relationId = [" + relationId + "]");
			return null;
		}

		ShopUserProjectRelationCriteria shopUserProjectRelationCriteria = new ShopUserProjectRelationCriteria();
		ShopUserProjectRelationCriteria.Criteria criteria = shopUserProjectRelationCriteria.createCriteria();

		// 根据用户与项目的关系主键列表查询用户与项目的关系
		criteria.andIdIn(relationId);

		List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserProjectRelationMapper
				.selectByCriteria(shopUserProjectRelationCriteria);

		return projectRelationDTOS;
	}

	/**
	 * 更新用户与项目的关系
	 *
	 * @param shopUserProjectRelationDTO
	 * @return
	 */
	@Override
	public int updateUserAndProjectRelation(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

		if (shopUserProjectRelationDTO == null) {
			logger.info("更新用户与项目的关系传入参数为空");
			return 0;
		}

		if (StringUtils.isBlank(shopUserProjectRelationDTO.getId())) {
			logger.error("更新用户与项目的关系的主键为空", shopUserProjectRelationDTO.getId());
			return 0;
		}

		int update = shopUserProjectRelationMapper.updateByPrimaryKeySelective(shopUserProjectRelationDTO);
		return update;
	}

    /**
     * 删除用户与项目的关系
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    @Override
    public int deleteUserAndProjectRelation(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

        if (shopUserProjectRelationDTO == null) {
            logger.info("删除用户与项目的关系传入参数为空");
            return 0;
        }

        if (StringUtils.isBlank(shopUserProjectRelationDTO.getShopAppointmentId())) {
            logger.error("删除用户与项目的关系的主键为空", shopUserProjectRelationDTO.getShopAppointmentId());
            return 0;
        }
        ShopUserProjectRelationCriteria criteria = new ShopUserProjectRelationCriteria();
        ShopUserProjectRelationCriteria.Criteria c = criteria.createCriteria();
        c.andShopAppointmentIdEqualTo(shopUserProjectRelationDTO.getShopAppointmentId());
        int update = shopUserProjectRelationMapper.deleteByCriteria(criteria);
        return update;
    }

	/**
	 * 查询某个店的项目列表信息
	 *
	 * @return
	 */
	@Override
	public List<ShopProjectInfoDTO> getShopCourseProjectList(ExtShopProjectInfoDTO extShopProjectInfoDTO) {

		logger.info("查询某个店的疗程卡列表信息传入参数={}", "shopProjectInfoDTO = [" + extShopProjectInfoDTO + "]");

		if (extShopProjectInfoDTO == null) {
			return null;
		}

		ShopProjectInfoCriteria shopProjectInfoCriteria = new ShopProjectInfoCriteria();
		ShopProjectInfoCriteria.Criteria criteria = shopProjectInfoCriteria.createCriteria();

		if (StringUtils.isNotBlank(extShopProjectInfoDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(extShopProjectInfoDTO.getSysShopId());
		}
		if(StringUtils.isNotBlank(extShopProjectInfoDTO.getFuzzyQuery())&&
				"0".equals(extShopProjectInfoDTO.getFuzzyQuery())){
			if(StringUtils.isNotBlank(extShopProjectInfoDTO.getProjectName())){
				criteria.andProjectNameLike("%"+extShopProjectInfoDTO.getProjectName()+"%");
			}
		}else {
			if (StringUtils.isNotBlank(extShopProjectInfoDTO.getProjectName())) {
				criteria.andProjectNameLike("%"+extShopProjectInfoDTO.getProjectName()+"%");
			}
		}

		if (StringUtils.isNotBlank(extShopProjectInfoDTO.getUseStyle())) {
			criteria.andUseStyleEqualTo(extShopProjectInfoDTO.getUseStyle());
		}

		if (StringUtils.isNotBlank(extShopProjectInfoDTO.getId())) {
			criteria.andIdEqualTo(extShopProjectInfoDTO.getId());
		}
		if (StringUtils.isNotBlank(extShopProjectInfoDTO.getStatus())) {
			criteria.andStatusEqualTo(extShopProjectInfoDTO.getStatus());
		}
		List<ShopProjectInfoDTO> dtos = shopProjectInfoMapper.selectByCriteria(shopProjectInfoCriteria);

		return dtos;
	}

	/**
	 * 查询某个用户的所有套卡项目列表
	 *
	 * @param shopUserProjectGroupRelRelationDTO
	 * @return
	 */
	@Override
	public List<ShopUserProjectGroupRelRelationDTO> getUserCollectionCardProjectList(
			ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO) {

		if (shopUserProjectGroupRelRelationDTO == null) {
			logger.error("查询某个用户的所有套卡项目列表传入参数");
			return null;
		}

		ShopUserProjectGroupRelRelationCriteria shopUserProjectGroupRelRelationCriteria = new ShopUserProjectGroupRelRelationCriteria();
		ShopUserProjectGroupRelRelationCriteria.Criteria criteria = shopUserProjectGroupRelRelationCriteria
				.createCriteria();
		//根据创建时间排序
		shopUserProjectGroupRelRelationCriteria.setOrderByClause("create_date");
		if (StringUtils.isNotBlank(shopUserProjectGroupRelRelationDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopUserProjectGroupRelRelationDTO.getSysShopId());
		}

		if (StringUtils.isNotBlank(shopUserProjectGroupRelRelationDTO.getSysUserId())) {
			criteria.andSysUserIdEqualTo(shopUserProjectGroupRelRelationDTO.getSysUserId());
		}

		List<ShopUserProjectGroupRelRelationDTO> relationDTOS = shopUserProjectGroupRelRelationMapper
				.selectByCriteria(shopUserProjectGroupRelRelationCriteria);

		logger.debug("查询某个用户的所有套卡项目列表的大小为, {}", CommonUtils.objectIsNotEmpty(relationDTOS) ? relationDTOS.size() : "0");

		return relationDTOS;
	}

	@Override
	public List<ShopProjectTypeDTO> getOneLevelProjectList(ShopProjectTypeDTO shopProjectTypeDTO) {

		if (null == shopProjectTypeDTO) {
			logger.info("getOneLevelProjectList传入的参数sysShopId为空");
			return null;
		}
		String sysShopId = shopProjectTypeDTO.getSysShopId();
		String status = shopProjectTypeDTO.getStatus();
		String projectTypeName = shopProjectTypeDTO.getProjectTypeName();

		ShopProjectTypeCriteria shopProjectTypeCriteria = new ShopProjectTypeCriteria();
		ShopProjectTypeCriteria.Criteria criteria = shopProjectTypeCriteria.createCriteria();

		if(StringUtils.isNotBlank(sysShopId)){
			criteria.andSysShopIdEqualTo(sysShopId);
		}
		if(StringUtils.isNotBlank(status)){
			criteria.andStatusEqualTo(status);
		}
		if(StringUtils.isNotBlank(projectTypeName)){
			criteria.andProjectTypeNameEqualTo(projectTypeName);
		}

		criteria.andParentIdIsNull();

		ShopProjectTypeCriteria.Criteria or = shopProjectTypeCriteria.createCriteria();
		or.andParentIdEqualTo("");
		shopProjectTypeCriteria.or(or);
		List<ShopProjectTypeDTO> list = shopProjectTypeMapper.selectByCriteria(shopProjectTypeCriteria);
		return list;
	}

	@Override
	public List<ShopProjectTypeDTO> getTwoLevelProjectList(ShopProjectTypeDTO shopProjectTypeDTO) {
		if(shopProjectTypeDTO==null){
			logger.info("shopProjectTypeDTO参数为空");
			return null;
		}
		logger.info("getTwoLevelProjectList传入的参数,id={},sysShopId={}", shopProjectTypeDTO.getId(),shopProjectTypeDTO.getSysShopId());

		ShopProjectTypeCriteria shopProjectTypeCriteria = new ShopProjectTypeCriteria();
		ShopProjectTypeCriteria.Criteria criteria = shopProjectTypeCriteria.createCriteria();
		if(StringUtils.isNotBlank(shopProjectTypeDTO.getId())){
			criteria.andParentIdEqualTo(shopProjectTypeDTO.getId());
		}
		if(StringUtils.isNotBlank(shopProjectTypeDTO.getSysShopId())){
			criteria.andSysShopIdEqualTo(shopProjectTypeDTO.getSysShopId());
		}
		if(StringUtils.isNotBlank(shopProjectTypeDTO.getStatus())){
			criteria.andStatusEqualTo(shopProjectTypeDTO.getStatus());
		}

		List<ShopProjectTypeDTO> list = shopProjectTypeMapper.selectByCriteria(shopProjectTypeCriteria);
		return list;
	}

	@Override
	public List<ShopProjectInfoResponseDTO> getThreeLevelProjectList(
			PageParamVoDTO<ShopProjectInfoDTO> pageParamVoDTO) {
		ShopProjectInfoDTO shopProjectInfoDTO = pageParamVoDTO.getRequestData();
		logger.info("getThreeLevelProjectList传入的参数,sysShopId={},projectTypeOneId={},projectTypeTwoId={}",
				shopProjectInfoDTO.getSysShopId(), shopProjectInfoDTO.getProjectTypeOneId(),
				shopProjectInfoDTO.getProjectTypeTwoId());

		if (StringUtils.isBlank(shopProjectInfoDTO.getSysShopId())) {
			logger.info("getThreeLevelProjectList传入的参数sysShopId为空");
			return null;
		}

		ShopProjectInfoCriteria shopProjectInfoCriteria = new ShopProjectInfoCriteria();
		ShopProjectInfoCriteria.Criteria criteria = shopProjectInfoCriteria.createCriteria();
		// 排序
		shopProjectInfoCriteria.setOrderByClause("create_date");
		// 分页
		shopProjectInfoCriteria.setLimitStart(pageParamVoDTO.getPageNo());
		shopProjectInfoCriteria.setPageSize(pageParamVoDTO.getPageSize());
		criteria.andSysShopIdEqualTo(shopProjectInfoDTO.getSysShopId());
		if (StringUtils.isNotBlank(shopProjectInfoDTO.getProjectTypeOneId())) {
			criteria.andProjectTypeOneIdEqualTo(shopProjectInfoDTO.getProjectTypeOneId());
		}
		if (StringUtils.isNotBlank(shopProjectInfoDTO.getProjectTypeTwoId())) {
			criteria.andProjectTypeTwoIdEqualTo(shopProjectInfoDTO.getProjectTypeTwoId());
		}
		if (StringUtils.isNotBlank(shopProjectInfoDTO.getProjectName())) {
			criteria.andProjectNameLike("%" + shopProjectInfoDTO.getProjectName() + "%");
		}
		if (StringUtils.isNotBlank(shopProjectInfoDTO.getUseStyle())) {
			criteria.andUseStyleEqualTo(shopProjectInfoDTO.getUseStyle());
		}
		if (StringUtils.isNotBlank(shopProjectInfoDTO.getStatus())) {
			criteria.andStatusEqualTo(shopProjectInfoDTO.getStatus());
		}

		List<ShopProjectInfoDTO> list = shopProjectInfoMapper.selectByCriteria(shopProjectInfoCriteria);

        List<ShopProjectInfoResponseDTO> responseDTOS = new ArrayList<>();
        //查询图片信息
        if (CommonUtils.objectIsNotEmpty(list)) {
            for (ShopProjectInfoDTO dto : list) {
                ShopProjectInfoResponseDTO shopProjectInfoResponseDTO = new ShopProjectInfoResponseDTO();
                BeanUtils.copyProperties(dto, shopProjectInfoResponseDTO);
                shopProjectInfoResponseDTO.setImageList(mongoUtils.getImageUrl(dto.getId()));
                responseDTOS.add(shopProjectInfoResponseDTO);
			}
		}
        return responseDTOS;
	}

	@Override
	public ShopProjectInfoResponseDTO getProjectDetail(String id) {
		logger.info("getProjectDetail传入的参数,id={}", id);

		if (StringUtils.isBlank(id)) {
			return null;
		}
		ShopProjectInfoCriteria shopProjectInfoCriteria = new ShopProjectInfoCriteria();
		ShopProjectInfoCriteria.Criteria criteria = shopProjectInfoCriteria.createCriteria();

		criteria.andIdEqualTo(id);
		List<ShopProjectInfoDTO> list = shopProjectInfoMapper.selectByCriteria(shopProjectInfoCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("getProjectDetail返回的结果为空");
			return null;
		}
		ShopProjectInfoDTO shopProjectInfoDTO = list.get(0);

		ShopProjectInfoResponseDTO shopProjectInfoResponseDTO = new ShopProjectInfoResponseDTO();

		BeanUtils.copyProperties(shopProjectInfoDTO, shopProjectInfoResponseDTO);

        shopProjectInfoResponseDTO.setImageList(mongoUtils.getImageUrl(shopProjectInfoDTO.getId()));

        if (CommonUtils.objectIsEmpty(shopProjectInfoResponseDTO.getImageList()) && org.apache.commons.lang3.StringUtils.isNotBlank(shopProjectInfoResponseDTO.getProjectUrl())) {
            List<String> objects = new ArrayList<>();
            objects.add(shopProjectInfoResponseDTO.getProjectUrl());
            shopProjectInfoResponseDTO.setImageList(objects);
        }
		return shopProjectInfoResponseDTO;
	}

	/**
	 * 保存用户与项目的关系
	 *
	 * @param shopUserRelationDTO
	 * @return
	 */
	@Override
	public int saveUserProjectRelation(ShopUserProjectRelationDTO shopUserRelationDTO) {
		logger.info("保存用户与项目的关系传入参数={}", "shopUserRelationDTO = [" + shopUserRelationDTO + "]");
		int insert = shopUserProjectRelationMapper.insert(shopUserRelationDTO);
		logger.debug("保存用户与项目的关系执行结果 {}", insert > 0 ? "成功" : "失败");
		return insert;
	}

	/**
	 * 根据条件查询套卡与项目的关系列表
	 *
	 * @param shopProjectInfoGroupRelationDTO
	 * @return
	 */
	@Override
	public List<ShopProjectInfoGroupRelationDTO> getShopProjectInfoGroupRelations(
			ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO) {

		logger.info("根据条件查询套卡与项目的关系列表,传入参数={}",
				"shopProjectInfoGroupRelationDTO = [" + shopProjectInfoGroupRelationDTO + "]");
		if (shopProjectInfoGroupRelationDTO == null) {
			logger.error("根据条件查询套卡与项目的关系列表传入参数为空");
			return null;
		}

		ShopProjectInfoGroupRelationCriteria relationCriteria = new ShopProjectInfoGroupRelationCriteria();
		ShopProjectInfoGroupRelationCriteria.Criteria criteria = relationCriteria.createCriteria();

		if (StringUtils.isNotBlank(shopProjectInfoGroupRelationDTO.getShopProjectGroupId())) {
			criteria.andShopProjectGroupIdEqualTo(shopProjectInfoGroupRelationDTO.getShopProjectGroupId());
		}

		if (StringUtils.isNotBlank(shopProjectInfoGroupRelationDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopProjectInfoGroupRelationDTO.getSysShopId());
		}

		List<ShopProjectInfoGroupRelationDTO> dtos = shopProjectInfoGroupRelationMapper
				.selectByCriteria(relationCriteria);

		logger.info("根据条件查询套卡与项目的关系列表查询结果为={}", dtos == null ? "" : dtos.size());
		return dtos;
	}

	@Override
	public List<ShopProjectInfoDTO> getProjectDetails(List<String> ids) {
		logger.info("getProjectDetail传入的参数,id={}", ids);

		if (ids.size() == 0) {
			return null;
		}
		ShopProjectInfoCriteria shopProjectInfoCriteria = new ShopProjectInfoCriteria();
		ShopProjectInfoCriteria.Criteria criteria = shopProjectInfoCriteria.createCriteria();

		criteria.andIdIn(ids);
		List<ShopProjectInfoDTO> list = shopProjectInfoMapper.selectByCriteria(shopProjectInfoCriteria);

		return list;
	}

	/**
	 * 添加项目类别
	 */
	@Override
	public int saveProjectTypeInfo(ShopProjectTypeDTO shopProjectTypeDTO, SysBossDTO bossInfo) {
		if (null == shopProjectTypeDTO) {
			logger.error("添加项目类别传入参数异常，参数为空");
			return 0;
		}
		shopProjectTypeDTO.setId(IdGen.uuid());
		shopProjectTypeDTO.setSysShopId(bossInfo.getCurrentShopId());
        shopProjectTypeDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
		int selective = shopProjectTypeMapper.insertSelective(shopProjectTypeDTO);
		return selective;
	}

	/**
	 * 修改项目类别
	 *
	 * @param shopProjectTypeDTO
	 * @return
	 */
	@Override
	public int updateProjectTypeInfo(ShopProjectTypeDTO shopProjectTypeDTO) {
		if (null == shopProjectTypeDTO || StringUtils.isBlank(shopProjectTypeDTO.getId())) {
			logger.error("修改项目类别传入参数有误={}", shopProjectTypeDTO);
			return 0;
		}
		//如果是一级类别修改状态，则它下面的项目都修改状态
		if(StringUtils.isBlank(shopProjectTypeDTO.getParentId()) && StringUtils.isNotBlank(shopProjectTypeDTO.getStatus())){
			//项目
			ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
			shopProjectInfoDTO.setStatus(shopProjectTypeDTO.getStatus());
			ShopProjectInfoCriteria projectInfoCriteria = new ShopProjectInfoCriteria();
			ShopProjectInfoCriteria.Criteria cr = projectInfoCriteria.createCriteria();
			cr.andProjectTypeOneIdEqualTo(shopProjectTypeDTO.getId());
			shopProjectInfoMapper.updateByCriteriaSelective(shopProjectInfoDTO,projectInfoCriteria);
			//二级类别
			ShopProjectTypeDTO twoLevel = new ShopProjectTypeDTO();
			twoLevel.setStatus(shopProjectTypeDTO.getStatus());
			ShopProjectTypeCriteria criteria = new ShopProjectTypeCriteria();
			ShopProjectTypeCriteria.Criteria c = criteria.createCriteria();
			c.andParentIdEqualTo(shopProjectTypeDTO.getId());
			shopProjectTypeMapper.updateByCriteriaSelective(twoLevel,criteria);
		}
		return shopProjectTypeMapper.updateByPrimaryKeySelective(shopProjectTypeDTO);
	}

	/**
	 * 更新项目信息
	 *
	 * @param shopProjectInfoDTO
	 * @return
	 */
	@Override
	public int updateProjectInfo(ExtShopProjectInfoDTO shopProjectInfoDTO) {
		if (CommonUtils.objectIsEmpty(shopProjectInfoDTO)) {
			logger.error("更新项目信息传入参数有误={}", "shopProjectInfoDTO = [" + shopProjectInfoDTO + "]");
			return 0;
		}
		//保存图片信息
		if (CommonUtils.objectIsNotEmpty(shopProjectInfoDTO.getImageList())) {
			mongoUtils.updateImageUrl(shopProjectInfoDTO.getImageList(), shopProjectInfoDTO.getId());
			shopProjectInfoDTO.setProjectUrl(shopProjectInfoDTO.getImageList().get(0));
		}
		return shopProjectInfoMapper.updateByPrimaryKeySelective(shopProjectInfoDTO);
	}

	/**
     * 保存项目
     *
     * @param extShopProjectInfoDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveProjectInfo(ExtShopProjectInfoDTO extShopProjectInfoDTO) {
        if (null == extShopProjectInfoDTO) {
			logger.error("保存项目传入参数为空");
            return 0;
        }
        if (CommonUtils.objectIsNotEmpty(extShopProjectInfoDTO.getImageList())) {
            extShopProjectInfoDTO.setProjectUrl(extShopProjectInfoDTO.getImageList().get(0));
        }else{
			extShopProjectInfoDTO.setProjectUrl(ImageEnum.GOODS_CARD.getDesc());
			ArrayList<String> objects = new ArrayList<>();
			objects.add(ImageEnum.GOODS_CARD.getDesc());
			extShopProjectInfoDTO.setImageList(objects);
		}
        String uuid = IdGen.uuid();
        extShopProjectInfoDTO.setId(uuid);
        //保存图片信息
        mongoUtils.saveImageUrl(extShopProjectInfoDTO.getImageList(), uuid);
        return shopProjectInfoMapper.insertSelective(extShopProjectInfoDTO);
    }



}
