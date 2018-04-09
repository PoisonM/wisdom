package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

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

}
