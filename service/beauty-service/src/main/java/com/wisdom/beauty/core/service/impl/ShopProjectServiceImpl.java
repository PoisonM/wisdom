package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CardType;
import com.wisdom.beauty.api.enums.CommonCode;
import com.wisdom.beauty.core.mapper.ShopProjectInfoMapper;
import com.wisdom.beauty.core.mapper.ShopUserProjectGroupRelRelationMapper;
import com.wisdom.beauty.core.mapper.ShopUserProjectRelationMapper;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("projectService")
public class ShopProjectServiceImpl implements ShopProjectService {

    @Autowired
    public ShopUserProjectRelationMapper shopUserProjectRelationMapper;

    @Autowired
    public ShopProjectInfoMapper shopProjectInfoMapper;

    @Autowired
    public ShopUserProjectGroupRelRelationMapper shopUserProjectGroupRelRelationMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取用户的项目信息
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    @Override
    public List<ShopUserProjectRelationDTO> getUserProjectList(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

        if (CommonUtils.objectIsNotEmpty(shopUserProjectRelationDTO)) {
            logger.info("获取用户预约的项目传入参数={}", "shopUserProductRelationDTO = [" + shopUserProjectRelationDTO + "]");
            return null;
        }

        ShopUserProjectRelationCriteria shopUserProjectRelationCriteria = new ShopUserProjectRelationCriteria();
        ShopUserProjectRelationCriteria.Criteria criteria = shopUserProjectRelationCriteria.createCriteria();

        //根据预约主键查询用户的预约项目
        if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getShopAppointmentId())) {
            criteria.andShopAppointmentIdEqualTo(shopUserProjectRelationDTO.getShopAppointmentId());
        }

        //查询用户的疗程卡或者是单卡信息
        if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getUseStyle())) {
            criteria.andUseStyleEqualTo(shopUserProjectRelationDTO.getUseStyle());
        }

        List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserProjectRelationMapper.selectByCriteria(shopUserProjectRelationCriteria);

        return projectRelationDTOS;
    }

    /**
     * 更新用户与项目的关系
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    @Override
    public int updateUserCardProject(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

        if (shopUserProjectRelationDTO == null) {
            logger.info("更新用户与项目的关系传入参数={}", "shopUserProjectRelationDTO = [" + shopUserProjectRelationDTO + "]");
            return 0;
        }

        if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getId())) {
            logger.error("更新用户与项目的关系的主键为空", shopUserProjectRelationDTO.getId());
            return 0;
        }

        int update = shopUserProjectRelationMapper.updateByPrimaryKey(shopUserProjectRelationDTO);
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

        if (StringUtils.isNotBlank(shopProjectInfoDTO.getStatus())) {
            criteria.andStatusNotEqualTo(CommonCode.SUCCESS.getCode());
        }

        if (StringUtils.isNotBlank(shopProjectInfoDTO.getUseStyle())) {
            criteria.andUseStyleEqualTo(CardType.TREATMENT_CARD.getCode());
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
    public List<ShopUserProjectGroupRelRelationDTO> getUserCollectionCardProjectList(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO) {

        if (shopUserProjectGroupRelRelationDTO == null) {
            logger.error("查询某个用户的所有套卡项目列表传入参数,{}", "shopUserProjectGroupRelRelationDTO = [" + shopUserProjectGroupRelRelationDTO + "]");
            return null;
        }

        ShopUserProjectGroupRelRelationCriteria shopUserProjectGroupRelRelationCriteria = new ShopUserProjectGroupRelRelationCriteria();
        ShopUserProjectGroupRelRelationCriteria.Criteria criteria = shopUserProjectGroupRelRelationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserProjectGroupRelRelationDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(shopUserProjectGroupRelRelationDTO.getSysShopId());
        }

        if (StringUtils.isNotBlank(shopUserProjectGroupRelRelationDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopUserProjectGroupRelRelationDTO.getSysUserId());
        }

        List<ShopUserProjectGroupRelRelationDTO> relationDTOS = shopUserProjectGroupRelRelationMapper.selectByCriteria(shopUserProjectGroupRelRelationCriteria);

        logger.debug("查询某个用户的所有套卡项目列表的大小为, {}", CommonUtils.objectIsNotEmpty(relationDTOS) ? relationDTOS.size() : "0");

        return relationDTOS;
    }
}
