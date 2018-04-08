package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.core.mapper.ShopUserProjectRelationMapper;
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
    public ShopUserProjectRelationMapper shopUserProjectRelationMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取用户预约的项目
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    @Override
    public List<ShopUserProjectRelationDTO> getUserAppointmentProjectList(ShopUserProjectRelationDTO shopUserProjectRelationDTO) {

        if (CommonUtils.objectIsNotEmpty(shopUserProjectRelationDTO)) {
            logger.info("获取用户预约的项目传入参数={}", "shopUserProductRelationDTO = [" + shopUserProjectRelationDTO + "]");
            return null;
        }

        //根据预约主键查询用户的预约项目
        ShopUserProjectRelationCriteria shopUserProjectRelationCriteria = new ShopUserProjectRelationCriteria();
        ShopUserProjectRelationCriteria.Criteria criteria = shopUserProjectRelationCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopUserProjectRelationDTO.getShopAppointmentId())) {
            criteria.andShopAppointmentIdEqualTo(shopUserProjectRelationDTO.getShopAppointmentId());
        }

        List<ShopUserProjectRelationDTO> projectRelationDTOS = shopUserProjectRelationMapper.selectByCriteria(shopUserProjectRelationCriteria);

        return projectRelationDTOS;
    }


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
}
