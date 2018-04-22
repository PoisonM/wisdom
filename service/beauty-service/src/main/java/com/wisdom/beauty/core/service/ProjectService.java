package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopCustomerProjectRelationDTO;

import java.util.List;

/**
 * FileName: ProjectService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ProjectService {

    /**
     * 查询某个用户预约的项目列表
     *
     * @param shopCustomerProjectRelationDTO
     * @return
     */
    List<ShopCustomerProjectRelationDTO> getCustomerAppointmentProjectList(ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO);


    /**
     * 更新用户与项目的关系
     *
     * @param shopCustomerProjectRelationDTO
     * @return
     */
    int updateCustomerCardProject(ShopCustomerProjectRelationDTO shopCustomerProjectRelationDTO);

}
