package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.extDto.ExtShopProjectGroupDTO;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.beauty.core.mapper.ShopProjectGroupMapper;
import com.wisdom.beauty.core.mapper.ShopProjectInfoGroupRelationMapper;
import com.wisdom.beauty.core.mapper.ShopUserProjectGroupRelRelationMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private MongoUtils mongoUtils;

    @Override
    public List<ProjectInfoGroupResponseDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO) {
        ShopProjectGroupDTO shopProjectGroupDTO = pageParamVoDTO.getRequestData();
        logger.info("getShopProjectGroupList方法传入的参数,sysShopId={},projectGroupName={}", shopProjectGroupDTO.getSysShopId(),
                shopProjectGroupDTO.getProjectGroupName());
        if (StringUtils.isBlank(shopProjectGroupDTO.getSysShopId())) {
            logger.info("getShopProjectGroupList方法传入的参数sysShopId为空");
            throw new ServiceException("SysShopId为空");
        }
        ShopProjectGroupCriteria criteria = new ShopProjectGroupCriteria();
        ShopProjectGroupCriteria.Criteria c = criteria.createCriteria();

        // 排序
        criteria.setOrderByClause("create_date");
        // 分页
        if(pageParamVoDTO.getPaging()){
            criteria.setLimitStart(pageParamVoDTO.getPageNo());
            criteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        // 参数
        c.andSysShopIdEqualTo(shopProjectGroupDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopProjectGroupDTO.getProjectGroupName())) {
            c.andProjectGroupNameLike("%" + shopProjectGroupDTO.getProjectGroupName() + "%");
        }

        List<ShopProjectGroupDTO> groupDTOS = shopProjectGroupMapper.selectByCriteria(criteria);

        List<ProjectInfoGroupResponseDTO> response = new ArrayList();
        for (ShopProjectGroupDTO s : groupDTOS) {
            ProjectInfoGroupResponseDTO projectInfoGroupResponseDTO = new ProjectInfoGroupResponseDTO();
            BeanUtils.copyProperties(s, projectInfoGroupResponseDTO);
            if(StringUtils.isNotBlank(projectInfoGroupResponseDTO.getExpirationDate())&&"0".equals(projectInfoGroupResponseDTO.getExpirationDate())){
                projectInfoGroupResponseDTO.setOverdue(false);
            }else {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                //产品有效期
                Date expirationDate=null;
                try {
                     expirationDate = df.parse(projectInfoGroupResponseDTO.getExpirationDate());
                } catch (Exception e) {
                    logger.info("时间转换异常,异常信息"+e.getMessage(),e);
                }
                if(expirationDate!=null) {
                    if (expirationDate.getTime() > System.currentTimeMillis()) {
                        projectInfoGroupResponseDTO.setOverdue(true);

                    } else {
                        projectInfoGroupResponseDTO.setOverdue(false);
                    }
                }
            }
            projectInfoGroupResponseDTO.setImageUrl(mongoUtils.getImageUrl(s.getId()));
            response.add(projectInfoGroupResponseDTO);
        }
        return response;
    }

    /**
     * 保存用户与套卡的关系的关系
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    @Override
    public int saveShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {
        logger.info("保存用户与套卡的关系的关系传入参数={}",
                "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
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
    public List<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelation(
            ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {

        if (CommonUtils.objectIsEmpty(shopUserProjectGroupRelRelation)) {
            logger.error("根据条件查询用户与套卡与项目关系的关系表传入参数为空，{}",
                    "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
            return null;
        }

        ShopUserProjectGroupRelRelationCriteria relationCriteria = new ShopUserProjectGroupRelRelationCriteria();
        ShopUserProjectGroupRelRelationCriteria.Criteria criteria = relationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserProjectGroupRelRelation.getId())) {
            criteria.andIdEqualTo(shopUserProjectGroupRelRelation.getId());
        }
        List<ShopUserProjectGroupRelRelationDTO> dtos = shopUserProjectGroupRelRelationMapper
                .selectByCriteria(relationCriteria);
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
    public int updateShopUserProjectGroupRelRelation(
            ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation) {

        if (CommonUtils.objectIsNotEmpty(shopUserProjectGroupRelRelation)) {
            logger.error("根据条件查询用户与套卡与项目关系的关系表传入参数为空，{}",
                    "shopUserProjectGroupRelRelation = [" + shopUserProjectGroupRelRelation + "]");
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

        for (ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO : shopProjectInfoGroupRelations) {
            shopProjectInfoIds.add(shopProjectInfoGroupRelationDTO.getShopProjectInfoId());
        }
        // 根据项目id(可能多个)查询,项目的详细信息
        List<ShopProjectInfoDTO> shopProjectInfos = shopProjectService.getProjectDetails(shopProjectInfoIds);
        if (CollectionUtils.isEmpty(shopProjectInfos)) {
            logger.info("接口shopProjectService#getProjectDetails()方法调用结果为空");
        }
        // 获取套卡信息
        ShopProjectGroupDTO shopProjectGroupDTO = this.getShopProjectGroupDTO(id);
        ProjectInfoGroupResponseDTO projectInfoGroupResponseDTO = new ProjectInfoGroupResponseDTO();
        if (shopProjectGroupDTO != null) {
            BeanUtils.copyProperties(shopProjectGroupDTO, projectInfoGroupResponseDTO);
            projectInfoGroupResponseDTO.setImageUrl(mongoUtils.getImageUrl(shopProjectGroupDTO.getId()));
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
        // ShopProjectGroupDTO
        List<ShopProjectGroupDTO> list = shopProjectGroupMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("getShopProjectGroupDTO方法中的List集合返回结果为空");
            return null;
        }
        ShopProjectGroupDTO shopProjectGroupDTO = list.get(0);
        return shopProjectGroupDTO;
    }

    @Override
    public List<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelation(List<String> ids) {
        logger.info("getShopUserProjectGroupRelRelation方法出入的参数ids={}",ids );
        if(CollectionUtils.isEmpty(ids)){
            return  null;
        }
        ShopUserProjectGroupRelRelationCriteria relationCriteria = new ShopUserProjectGroupRelRelationCriteria();
        ShopUserProjectGroupRelRelationCriteria.Criteria criteria = relationCriteria.createCriteria();
        criteria.andIdIn(ids);

        return shopUserProjectGroupRelRelationMapper.selectByCriteria(relationCriteria);

    }

    @Override
    public List<ShopProjectInfoGroupRelationDTO> getShopProjectInfoGroupRelation(List<String> ids) {
        logger.info("getShopProjectInfoGroupRelation方法出入的参数ids={}",ids );
        if(CollectionUtils.isEmpty(ids)){
            return  null;
        }
        ShopProjectInfoGroupRelationCriteria relationCriteria = new ShopProjectInfoGroupRelationCriteria();
        ShopProjectInfoGroupRelationCriteria.Criteria criteria = relationCriteria.createCriteria();
        criteria.andShopProjectGroupIdIn(ids);

        return shopProjectInfoGroupRelationMapper.selectByCriteria(relationCriteria);
    }

    /**
     * 添加套卡
     *
     * @param extShopProjectGroupDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveProjectGroupInfo(ExtShopProjectGroupDTO extShopProjectGroupDTO) {

        List<ShopProjectInfoDTO> shopProjectIds = extShopProjectGroupDTO.getShopProjectIds();
        //保存套卡
        String groupId = IdGen.uuid();
        String shopId = UserUtils.getBossInfo().getCurrentShopId();
        extShopProjectGroupDTO.setCreateDate(new Date());
        extShopProjectGroupDTO.setId(groupId);
        extShopProjectGroupDTO.setSysShopId(shopId);
        extShopProjectGroupDTO.setStatus(CommonCodeEnum.SUCCESS.getCode());
        //保存图片信息
        mongoUtils.saveImageUrl(extShopProjectGroupDTO.getImages(), groupId);
        if (CommonUtils.objectIsNotEmpty(extShopProjectGroupDTO.getImages())) {
            extShopProjectGroupDTO.setProjectGroupUrl(extShopProjectGroupDTO.getImages().get(0));
        }
        extShopProjectGroupDTO.setCreateBy(UserUtils.getBossInfo().getId());
        int insertSelective = shopProjectGroupMapper.insertSelective(extShopProjectGroupDTO);
        logger.error("添加套卡执行结果，{}", "insertSelective = [" + (insertSelective > 0 ? "成功" : "失败") + "]");

        if (CommonUtils.objectIsNotEmpty(shopProjectIds)) {
            saveGroupProjectRelationInfo(extShopProjectGroupDTO, shopProjectIds, groupId, shopId);
        }

        return insertSelective;
    }

    /**
     * 修改套卡
     *
     * @param extShopProjectGroupDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProjectGroupInfo(ExtShopProjectGroupDTO extShopProjectGroupDTO) {
        logger.info("修改套卡传入参数={}", "extShopProjectGroupDTO = [" + extShopProjectGroupDTO + "]");
        String groupDTOId = extShopProjectGroupDTO.getId();
        if (CommonUtils.objectIsEmpty(extShopProjectGroupDTO) || StringUtils.isBlank(groupDTOId)) {
            logger.error("修改套卡传入参数异常={}", "extShopProjectGroupDTO = [" + extShopProjectGroupDTO + "]");
            return 0;
        }
        List<String> images = extShopProjectGroupDTO.getImages();
        mongoUtils.saveImageUrl(images, groupDTOId);
        int update = shopProjectGroupMapper.updateByPrimaryKeySelective(extShopProjectGroupDTO);
        logger.info("修改套卡执行结果={}", update > 0 ? "成功" : "失败");

        List<ShopProjectInfoDTO> shopProjectIds = extShopProjectGroupDTO.getShopProjectIds();
        //查询此套卡与项目的关系
        ShopProjectInfoGroupRelationCriteria criteria = new ShopProjectInfoGroupRelationCriteria();
        ShopProjectInfoGroupRelationCriteria.Criteria c = criteria.createCriteria();
        c.andShopProjectGroupIdEqualTo(groupDTOId);
        shopProjectInfoGroupRelationMapper.deleteByCriteria(criteria);
        //不为空重新构建项目与套卡的关系
        if (CommonUtils.objectIsNotEmpty(shopProjectIds)) {
            String currentShopId = UserUtils.getBossInfo().getCurrentShopId();
            saveGroupProjectRelationInfo(extShopProjectGroupDTO, shopProjectIds, groupDTOId, currentShopId);
        }
        return update;
    }

    private void saveGroupProjectRelationInfo(ExtShopProjectGroupDTO extShopProjectGroupDTO, List<ShopProjectInfoDTO> shopProjectIds, String groupId, String shopId) {
        for (ShopProjectInfoDTO shopProjectInfoDTO : shopProjectIds) {
            //保存项目与套卡的关系
            ShopProjectInfoGroupRelationDTO relationDTO = new ShopProjectInfoGroupRelationDTO();
            relationDTO.setSysShopId(shopId);
            relationDTO.setCreateDate(new Date());
            relationDTO.setId(IdGen.uuid());
            relationDTO.setProjectGroupName(extShopProjectGroupDTO.getProjectGroupName());
            relationDTO.setShopProjectGroupId(groupId);
            relationDTO.setShopProjectInfoId(shopProjectInfoDTO.getId());
            relationDTO.setShopProjectInfoName(shopProjectInfoDTO.getProjectName());
            relationDTO.setShopProjectPrice(shopProjectInfoDTO.getMarketPrice());
            relationDTO.setShopProjectServiceTimes(shopProjectInfoDTO.getServiceTimes());
            shopProjectInfoGroupRelationMapper.insertSelective(relationDTO);
        }
    }

}
