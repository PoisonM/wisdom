package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;

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
     * @param shopUserProjectRelationDTO
     * @return
     */
    List<ShopUserProjectRelationDTO> getUserProjectList(ShopUserProjectRelationDTO shopUserProjectRelationDTO);


    /**
     * 更新用户与项目的关系
     *
     * @param shopUserProjectRelationDTO
     * @return
     */
    int updateUserCardProject(ShopUserProjectRelationDTO shopUserProjectRelationDTO);

    /**
     * 查询某个店的项目列表信息
     *
     * @return
     */
    List<ShopProjectInfoDTO> getShopCourseProjectList(ShopProjectInfoDTO shopProjectInfoDTO);
}
