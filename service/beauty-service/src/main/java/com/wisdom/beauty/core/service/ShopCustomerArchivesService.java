package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * ClassName: ShopCustomerArchivesService
 *
 * @Author： huan
 * @Description: 档案相关的接口
 * @Date:Created in 2018/4/3 16:38
 * @since JDK 1.8
 */
public interface ShopCustomerArchivesService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据美容院条件查询档案数量
     * @Date:2018/4/3 16:44
     */
    int getArchivesCount(ShopUserArchivesDTO shopUserArchivesDTO);

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据条件获取档案列表
     * @Date:2018/4/3 16:59
     */
    List<ShopUserArchivesDTO> getArchivesList(PageParamVoDTO<ShopUserArchivesDTO> shopCustomerArchivesDTO);

    /**
     * 查询某个店某一时间段建档的个数
     *
     * @param pageParamVoDTO
     * @return
     */
    int getShopBuildArchivesNumbers(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);

    /**
     * 保存用户的建档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    int saveShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO);

    /**
     * 更新用户的档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    int updateShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO);

    /**
     * 更新用户的档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    int updateByCriteriaSelective(ShopUserArchivesDTO shopUserArchivesDTO);

    /**
     * 删除用户的档案信息
     *
     * @param archivesId
     * @return
     */
    int deleteShopUserArchivesInfo(String archivesId);

    /**
     * 查询某个用户的档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    List<ShopUserArchivesDTO> getShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO);


    /**
     * 保存用户档案接口
     *
     * @param shopUserArchivesDTO
     * @return
     */
    ResponseDTO<Object> saveArchiveInfo(@RequestBody ShopUserArchivesDTO shopUserArchivesDTO);
    /**
    *@Author:zhanghuan
    *@Param: userIds
    *@Return:
    *@Description:根据多个userId获取档案列表
    *@Date:2018/5/18 11:48
    */
    List<ShopUserArchivesDTO> getArchivesList(List<String>  userIds);

}
