package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopProjectInfoGroupRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.extDto.ExtShopProjectGroupDTO;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;

/**
 * ClassName: ShopProjectGroupService
 *
 * @Author： huan
 * @Description: 套卡相关的接口
 * @Date:Created in 2018/4/11 15:01
 * @since JDK 1.8
 */
public interface ShopProjectGroupService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取套卡列表
     * @Date:2018/4/11 15:14
     */
    List<ProjectInfoGroupResponseDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO);


    /**
     * 保存用户与套卡的关系的关系
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    int saveShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation);

    /**
     * 根据条件查询用户与套卡与项目关系的关系表
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    List<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation);

    /**
     * 更新用户与套卡与项目关系的关系表
     *
     * @param shopUserProjectGroupRelRelation
     * @return
     */
    int updateShopUserProjectGroupRelRelation(ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelation);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取套卡的详细信息
     * @Date:2018/4/11 16:57
     */
    ProjectInfoGroupResponseDTO getShopProjectInfoGroupRelation(String id);
    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 根据ID查询套卡信息
    *@Date:2018/4/17 11:32
    */
    ShopProjectGroupDTO getShopProjectGroupDTO(String id);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 根据多个主键id查询信息
    *@Date:2018/5/17 9:22
    */
    List<ShopUserProjectGroupRelRelationDTO> getShopUserProjectGroupRelRelation(List<String> ids);
    /**
     *@Author:zhanghuan
     *@Param:
     *@Return:
     *@Description: 根据多个套卡id查询信息
     *@Date:2018/5/17 9:22
     */
    List<ShopProjectInfoGroupRelationDTO> getShopProjectInfoGroupRelation(List<String> ids);

    /**
     * 添加套卡
     *
     * @param extShopProjectGroupDTO
     * @return
     */
    int saveProjectGroupInfo(ExtShopProjectGroupDTO extShopProjectGroupDTO);

    /**
     * 修改套卡
     *
     * @param extShopProjectGroupDTO
     * @return
     */
    int updateProjectGroupInfo(ExtShopProjectGroupDTO extShopProjectGroupDTO);
}
