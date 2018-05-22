package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectInfoDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.core.mapper.*;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopProjectService;
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
			criteria.andSysShopProjectSurplusTimesGreaterThan(
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
	 * 查询某个店的项目列表信息
	 *
	 * @return
	 */
	@Override
	public List<ShopProjectInfoDTO> getShopCourseProjectList(ShopProjectInfoDTO shopProjectInfoDTO) {

		logger.info("查询某个店的疗程卡列表信息传入参数={}", "shopProjectInfoDTO = [" + shopProjectInfoDTO + "]");

		if (shopProjectInfoDTO == null) {
			return null;
		}

		ShopProjectInfoCriteria shopProjectInfoCriteria = new ShopProjectInfoCriteria();
		ShopProjectInfoCriteria.Criteria criteria = shopProjectInfoCriteria.createCriteria();

		if (StringUtils.isNotBlank(shopProjectInfoDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(shopProjectInfoDTO.getSysShopId());
		}

		// if (StringUtils.isNotBlank(shopProjectInfoDTO.getStatus())) { 默认查询有效的
		criteria.andStatusEqualTo(CommonCodeEnum.SUCCESS.getCode());
		// }

		if (StringUtils.isNotBlank(shopProjectInfoDTO.getProjectName())) {
			criteria.andProjectNameLike(shopProjectInfoDTO.getProjectName());
		}

		if (StringUtils.isNotBlank(shopProjectInfoDTO.getUseStyle())) {
			criteria.andUseStyleEqualTo(shopProjectInfoDTO.getUseStyle());
		}

		if (StringUtils.isNotBlank(shopProjectInfoDTO.getId())) {
			criteria.andIdEqualTo(shopProjectInfoDTO.getId());
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
			logger.error("查询某个用户的所有套卡项目列表传入参数,{}",
					"shopUserProjectGroupRelRelationDTO = [" + shopUserProjectGroupRelRelationDTO + "]");
			return null;
		}

		ShopUserProjectGroupRelRelationCriteria shopUserProjectGroupRelRelationCriteria = new ShopUserProjectGroupRelRelationCriteria();
		ShopUserProjectGroupRelRelationCriteria.Criteria criteria = shopUserProjectGroupRelRelationCriteria
				.createCriteria();

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
	public List<ShopProjectTypeDTO> getOneLevelProjectList(String sysShopId) {
		logger.info("getOneLevelProjectList传入的参数,sysShopId={}", sysShopId);
		if (StringUtils.isBlank(sysShopId)) {
			logger.info("getOneLevelProjectList传入的参数sysShopId为空");
			return null;
		}
		ShopProjectTypeCriteria shopProjectTypeCriteria = new ShopProjectTypeCriteria();
		ShopProjectTypeCriteria.Criteria criteria = shopProjectTypeCriteria.createCriteria();
		criteria.andSysShopIdEqualTo(sysShopId);
		criteria.andStatusEqualTo(CommonCodeEnum.SUCCESS.getCode());
		criteria.andParentIdIsNull();

		ShopProjectTypeCriteria.Criteria or = shopProjectTypeCriteria.createCriteria();
		or.andParentIdEqualTo("");
		shopProjectTypeCriteria.or(or);
		List<ShopProjectTypeDTO> list = shopProjectTypeMapper.selectByCriteria(shopProjectTypeCriteria);
		return list;
	}

	@Override
	public List<ShopProjectTypeDTO> getTwoLevelProjectList(ShopProjectTypeDTO shopProjectTypeDTO) {
		logger.info("getTwoLevelProjectList传入的参数,id={}", shopProjectTypeDTO.getId());

		if (StringUtils.isBlank(shopProjectTypeDTO.getId())) {
			logger.info("getTwoLevelProjectList传入的参数id为空");
			return null;
		}
		ShopProjectTypeCriteria shopProjectTypeCriteria = new ShopProjectTypeCriteria();
		ShopProjectTypeCriteria.Criteria criteria = shopProjectTypeCriteria.createCriteria();
		criteria.andParentIdEqualTo(shopProjectTypeDTO.getId());
		criteria.andStatusEqualTo(CommonCodeEnum.SUCCESS.getCode());
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
		//项目为启用状态
		criteria.andStatusEqualTo(CommonCodeEnum.SUCCESS.getCode());
		List<ShopProjectInfoDTO> list = shopProjectInfoMapper.selectByCriteria(shopProjectInfoCriteria);

        List<ShopProjectInfoResponseDTO> responseDTOS = new ArrayList<>();
        //查询图片信息
        if (CommonUtils.objectIsEmpty(list)) {
            for (ShopProjectInfoDTO dto : list) {
                ShopProjectInfoResponseDTO shopProjectInfoResponseDTO = new ShopProjectInfoResponseDTO();
                BeanUtils.copyProperties(dto, shopProjectInfoResponseDTO);
                shopProjectInfoResponseDTO.setImageUrl(mongoUtils.getImageUrl(dto.getId()));
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

        shopProjectInfoResponseDTO.setImageUrl(mongoUtils.getImageUrl(shopProjectInfoDTO.getId()));
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
			logger.error("根据条件查询套卡与项目的关系列表传入参数为空，{}",
					"shopProjectInfoGroupRelationDTO = [" + shopProjectInfoGroupRelationDTO + "]");
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
			logger.error("添加项目类别传入参数异常={}", "shopProjectTypeDTO = [" + shopProjectTypeDTO + "]");
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
		ShopProjectTypeCriteria criteria = new ShopProjectTypeCriteria();
		ShopProjectTypeCriteria.Criteria c = criteria.createCriteria();
		c.andIdEqualTo(shopProjectTypeDTO.getId());
		return shopProjectTypeMapper.updateByPrimaryKeySelective(shopProjectTypeDTO);
	}

	/**
	 * 更新项目信息
	 *
	 * @param shopProjectInfoDTO
	 * @return
	 */
	@Override
	public int updateProjectInfo(ShopProjectInfoDTO shopProjectInfoDTO) {
		if (CommonUtils.objectIsEmpty(shopProjectInfoDTO)) {
			logger.error("更新项目信息传入参数有误={}", "shopProjectInfoDTO = [" + shopProjectInfoDTO + "]");
			return 0;
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
            logger.error("保存项目={}", "extShopProjectInfoDTO = [" + extShopProjectInfoDTO + "]");
            return 0;
        }
        if (CommonUtils.objectIsNotEmpty(extShopProjectInfoDTO.getImageList())) {
            extShopProjectInfoDTO.setProjectUrl(extShopProjectInfoDTO.getImageList().get(0));
        }
        String uuid = IdGen.uuid();
        extShopProjectInfoDTO.setId(uuid);
        //保存图片信息
        mongoUtils.saveImageUrl(extShopProjectInfoDTO.getImageList(), uuid);
        return shopProjectInfoMapper.insertSelective(extShopProjectInfoDTO);
    }



}
