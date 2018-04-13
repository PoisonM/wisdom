package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ShopCustomerArchivesServcie
 *
 * @Author： huan
 * @Description: 档案相关的接口
 * @Date:Created in 2018/4/3 16:38
 * @since JDK 1.8
 */
public interface ShopCustomerArchivesServcie {
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
     * @param shopId
     * @param startDate
     * @param endDate
     * @return
     */
    int getShopBuildArchivesNumbers(String shopId, Date startDate, Date endDate);

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
     * 删除用户的档案信息
     *
     * @param archivesId
     * @return
     */
    int deleteShopUserArchivesInfo(String archivesId);
}
