package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserArchivesCriteria;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.core.mapper.ShopUserArchivesMapper;
import com.wisdom.beauty.core.service.ShopArchivesService;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * FileName: ShopArchivesServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 档案相关
 */
@Service("shopArchivesService")
public class ShopArchivesServiceImpl implements ShopArchivesService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopUserArchivesMapper shopUserArchivesMapper;

    /**
     * 查询某个店某一时间段建档的个数
     *
     * @param shopId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public int getShopBuildArchivesNumbers(String shopId, Date startDate, Date endDate) {

        logger.info("查询某个店某一时间段建档的个数传入参数={}", "shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");

        ShopUserArchivesCriteria archivesCriteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria criteria = archivesCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopId)) {
            criteria.andSysShopIdEqualTo(shopId);
        }
        if (startDate != null && endDate != endDate) {
            criteria.andCreateDateBetween(startDate, endDate);
        }

        int count = shopUserArchivesMapper.countByCriteria(archivesCriteria);
        logger.debug("查询某个店某一时间段建档的个数为， {}", count);
        return count;
    }

    /**
     * 保存用户的建档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    @Override
    public int saveShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO) {

        logger.info("保存用户的建档案信息传入参数={}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");

        if (CommonUtils.objectIsEmpty(shopUserArchivesDTO)) {
            logger.error("保存用户的建档案信息,传入参数为空{}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");
            return 0;
        }

        return shopUserArchivesMapper.insert(shopUserArchivesDTO);
    }
}
