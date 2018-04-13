package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;

import java.util.Date;

/**
 * FileName: ShopArchivesService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 档案相关
 */
public interface ShopArchivesService {

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
}
