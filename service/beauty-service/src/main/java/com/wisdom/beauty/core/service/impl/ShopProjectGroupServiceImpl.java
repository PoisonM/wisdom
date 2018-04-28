package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.beauty.core.mapper.ShopProjectGroupMapper;
import com.wisdom.beauty.core.mapper.ShopProjectInfoGroupRelationMapper;
import com.wisdom.beauty.core.mapper.ShopUserProjectGroupRelRelationMapper;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ShopProjectGroupServiceImpl
 *
 * @Author： huan
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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ShopProjectGroupDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO) {
        ShopProjectGroupDTO shopProjectGroupDTO = pageParamVoDTO.getRequestData();
        logger.info("getArchivesList方法传入的参数,sysShopId={},projectGroupName={}", shopProjectGroupDTO.getSysShopId(),
                shopProjectGroupDTO.getProjectGroupName());
        if (StringUtils.isBlank(shopProjectGroupDTO.getSysShopId())) {
            logger.info("getArchivesList方法传入的参数sysShopId为空");
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

    /**
     * 根据条件查询用户与套卡与项目关系的关系表
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    @Override
    public List<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {

        if (CommonUtils.objectIsEmpty(shopUserProjectGroupRelRelation)) {
            logger.error("根据条件查询用户与套卡与项目关系的关系表传入参数为空，{}", "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
            return null;
        }

        ShopUserProjectGroupRelRelationCriteria relationCriteria = new ShopUserProjectGroupRelRelationCriteria();
        ShopUserProjectGroupRelRelationCriteria.Criteria criteria = relationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserProjectGroupRelRelation.getId())) {
            criteria.andIdEqualTo(shopUserProjectGroupRelRelation.getId());
        }
        List<ShopUserProjectGroupRelRelationDTO> dtos = shopUserProjectGroupRelRelationMapper.selectByCriteria(relationCriteria);
        logger.debug("根据条件查询用户与套卡与项目关系的关系表查出来的数量为， {}", dtos != null ? dtos.size() : "0");
        return dtos;
    }

    /**
     * 更新用户与套卡与项目关系的关系表
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    @Override
    public int updateShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {

        if (CommonUtils.objectIsNotEmpty(shopUserProjectGroupRelRelation)) {
            logger.error("根据条件查询用户与套卡与项目关系的关系表传入参数为空，{}", "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
            return 0;
        }

        int update = shopUserProjectGroupRelRelationMapper.updateByPrimaryKey(shopUserProjectGroupRelRelation);

        return update;
    }

    @Override
    public ProjectInfoGroupResponseDTO getShopProjectInfoGroupRelation(String id) {
        logger.info("getShopProjectInfoGroupRelation方法传入的参数,id={}", id);
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("Id为空");
        }
        ShopProjectInfoGroupRelationCriteria criteria = new ShopProjectInfoGroupRelationCriteria();
        ShopProjectInfoGroupRelationCriteria.Criteria c = criteria.createCriteria();
        // 参数
        c.andShopProjectGroupIdEqualTo(id);
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
        //获取套卡信息
        ShopProjectGroupDTO shopProjectGroupDTO = this.getShopProjectGroupDTO(id);
        if (shopProjectGroupDTO != null) {
            projectInfoGroupResponseDTO.setProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
            projectInfoGroupResponseDTO.setDetail(shopProjectGroupDTO.getDetail());
            projectInfoGroupResponseDTO.setDiscountPrice(shopProjectGroupDTO.getDiscountPrice());
            projectInfoGroupResponseDTO.setMarketPrice(shopProjectGroupDTO.getMarketPrice());
            projectInfoGroupResponseDTO.setProjectGroupUrl(shopProjectGroupDTO.getProjectGroupUrl());
            projectInfoGroupResponseDTO.setValidDate(shopProjectGroupDTO.getValidDate());
        }
        projectInfoGroupResponseDTO.setList(shopProjectInfos);
        return projectInfoGroupResponseDTO;
    }

    @Override
    public ShopProjectGroupDTO getShopProjectGroupDTO(String id) {
        logger.info("getShopProjectGroupDTO方法传入的参数,id={}", id);
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("Id为空");
        }
        ShopProjectGroupCriteria criteria = new ShopProjectGroupCriteria();
        ShopProjectGroupCriteria.Criteria c = criteria.createCriteria();
        c.andIdEqualTo(id);
        //ShopProjectGroupDTO
        List<ShopProjectGroupDTO> list = shopProjectGroupMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("getShopProjectGroupDTO方法中的List集合返回结果为空");
            return null;
        }
        ShopProjectGroupDTO shopProjectGroupDTO = list.get(0);
        return shopProjectGroupDTO;
    }
}
