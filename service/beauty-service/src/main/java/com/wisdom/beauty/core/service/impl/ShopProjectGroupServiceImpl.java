package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopProjectGroupCriteria;
import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.beauty.core.mapper.ShopProjectGroupMapper;
import com.wisdom.beauty.core.mapper.ShopUserProjectGroupRelRelationMapper;
import com.wisdom.beauty.core.mapper.ShopProjectInfoGroupRelationMapper;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ShopProjectGroupServiceImpl
 *
 * @Author： huan
 * 
 * @Description: 套卡相关的接口
 * @Date:Created in 2018/4/11 15:15
 * @since JDK 1.8
 */
@Service("shopProjectGroupService")
public class ShopProjectGroupServiceImpl implements ShopProjectGroupService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopProjectGroupMapper shopProjectGroupMapper;

    @Autowired
    private ShopUserProjectGroupRelRelationMapper shopUserProjectGroupRelRelationMapper;

	@Autowired
	private ShopProjectInfoGroupRelationMapper shopProjectInfoGroupRelationMapper;

	@Autowired
	private ShopProjectService shopProjectService;

	@Override
	public List<ShopProjectGroupDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO) {
		ShopProjectGroupDTO shopProjectGroupDTO = pageParamVoDTO.getRequestData();
		logger.info("getArchivesList方法传入的参数,sysShopId={},projectGroupName={}", shopProjectGroupDTO.getSysShopId(),
				shopProjectGroupDTO.getProjectGroupName());
		if (StringUtils.isBlank(shopProjectGroupDTO.getSysShopId())) {
			throw new ServiceException("SysShopId为空");
		}
		ShopProjectGroupCriteria criteria = new ShopProjectGroupCriteria();
		ShopProjectGroupCriteria.Criteria c = criteria.createCriteria();

		// 排序
		criteria.setOrderByClause("create_date");
		// 分页
		criteria.setLimitStart(pageParamVoDTO.getPageNo());
		criteria.setPageSize(pageParamVoDTO.getPageSize());
		// 参数
		c.andSysShopIdEqualTo(shopProjectGroupDTO.getSysShopId());
		if (StringUtils.isNotBlank(shopProjectGroupDTO.getProjectGroupName())) {
			c.andProjectGroupNameLike("%" + shopProjectGroupDTO.getProjectGroupName() + "%");
		}

		List<ShopProjectGroupDTO> shopCustomerArchiveslist = shopProjectGroupMapper.selectByCriteria(criteria);

		return shopCustomerArchiveslist;
	}

    /**
     * 保存用户与套卡的关系的关系
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    @Override
    public int saveShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {
        logger.info("保存用户与套卡的关系的关系传入参数={}", "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
        int insert = shopUserProjectGroupRelRelationMapper.insert(shopUserProjectGroupRelRelation);
        return insert;
    }

	@Override
	public ProjectInfoGroupResponseDTO getShopProjectInfoGroupRelation(ShopProjectGroupDTO shopProjectGroupDTO) {
		logger.info("getShopProjectInfoGroupRelation方法传入的参数,shopProjectGroupDTO={}", shopProjectGroupDTO);
		if (shopProjectGroupDTO == null || StringUtils.isBlank(shopProjectGroupDTO.getId())) {
			throw new ServiceException("Id为空");
		}
		ShopProjectInfoGroupRelationCriteria criteria = new ShopProjectInfoGroupRelationCriteria();
		ShopProjectInfoGroupRelationCriteria.Criteria c = criteria.createCriteria();
		// 参数
		c.andShopProjectGroupIdEqualTo(shopProjectGroupDTO.getId());
		List<ShopProjectInfoGroupRelationDTO> shopProjectInfoGroupRelations = shopProjectInfoGroupRelationMapper
				.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(shopProjectInfoGroupRelations)) {
			logger.info("shopProjectInfoGroupRelationMapper查询的结果shopProjectInfoGroupRelations为空");
		}
		List<String> shopProjectInfoIds = new ArrayList<>();
		ProjectInfoGroupResponseDTO projectInfoGroupResponseDTO = new ProjectInfoGroupResponseDTO();
		for (ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO : shopProjectInfoGroupRelations) {
			shopProjectInfoIds.add(shopProjectInfoGroupRelationDTO.getShopProjectInfoId());
		}
		// 根据项目id(可能多个)查询,项目的详细信息
		List<ShopProjectInfoDTO> shopProjectInfos = shopProjectService.getProjectDetails(shopProjectInfoIds);
		if (CollectionUtils.isEmpty(shopProjectInfos)) {
			logger.info("接口shopProjectService#getProjectDetails()方法调用结果为空");
		}
		projectInfoGroupResponseDTO.setList(shopProjectInfos);
		projectInfoGroupResponseDTO.setProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
		projectInfoGroupResponseDTO.setDetail(shopProjectGroupDTO.getDetail());
		projectInfoGroupResponseDTO.setDiscountPrice(shopProjectGroupDTO.getDiscountPrice());
		projectInfoGroupResponseDTO.setMarketPrice(shopProjectGroupDTO.getMarketPrice());
		projectInfoGroupResponseDTO.setProjectGroupUrl(shopProjectGroupDTO.getProjectGroupUrl());
		projectInfoGroupResponseDTO.setValidDate(shopProjectGroupDTO.getValidDate());
		return projectInfoGroupResponseDTO;
	}
}
