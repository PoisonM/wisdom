package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationCriteria;
import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationDTO;
import com.wisdom.beauty.core.mapper.ShopCustomerProjectRelationMapper;
import com.wisdom.beauty.core.service.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    public ShopCustomerProjectRelationMapper shopCustomerProjectRelationMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取用户预约的项目
     *
     * @param shopCustomerProjectRelationDTO
     * @return
     */
    @Override
    public List<ShopCustomerProjectRelationDTO> getCustomerAppointmentProjectList(ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO) {

        if (CommonUtils.objectIsNotEmpty(shopCustomerProjectRelationDTO)) {
            logger.info("获取用户预约的项目传入参数={}", "shopCustomerProductRelationDTO = [" + shopCustomerProjectRelationDTO + "]");
            return null;
        }

        //根据预约主键查询用户的预约项目
        ShopCustomerProjectRelationCriteria shopCustomerProjectRelationCriteria = new ShopCustomerProjectRelationCriteria();
        ShopCustomerProjectRelationCriteria.Criteria criteria = shopCustomerProjectRelationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopCustomerProjectRelationDTO.getShopAppointmentId())) {
            criteria.andShopAppointmentIdEqualTo(shopCustomerProjectRelationDTO.getShopAppointmentId());
        }

        List<ShopCustomerProjectRelationDTO> projectRelationDTOS = shopCustomerProjectRelationMapper.selectByCriteria(shopCustomerProjectRelationCriteria);

        return projectRelationDTOS;
    }


    @Override
    public int updateCustomerCardProject(ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO) {

        if (shopCustomerProjectRelationDTO == null) {
            logger.info("更新用户与项目的关系传入参数={}", "shopCustomerProjectRelationDTO = [" + shopCustomerProjectRelationDTO + "]");
            return 0;
        }

        if (StringUtils.isNotBlank(shopCustomerProjectRelationDTO.getId())) {
            logger.error("更新用户与项目的关系的主键为空", shopCustomerProjectRelationDTO.getId());
            return 0;
        }

        int update = shopCustomerProjectRelationMapper.updateByPrimaryKey(shopCustomerProjectRelationDTO);
        return update;
    }
}
