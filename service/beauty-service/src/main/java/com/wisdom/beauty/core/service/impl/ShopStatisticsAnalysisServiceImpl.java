package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.core.mapper.ExtShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.ShopArchivesService;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FileName: ShopStatisticsAnalysisServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店铺分析相关
 */
@Service("shopStatisticsAnalysisService")
public class ShopStatisticsAnalysisServiceImpl implements ShopStatisticsAnalysisService {

    @Autowired
    private ShopArchivesService shopArchivesService;

    @Autowired
    private ExtShopUserConsumeRecordMapper extShopUserConsumeRecordMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询美容店某个时间段的耗卡金额
     */
    @Override
    public BigDecimal getShopCardConsumeAmount(String shopId, Date startDate, Date endDate) {

        logger.info("查询美容店某个时间段的耗卡金额传入参数={}", "shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");

        if (StringUtils.isBlank(shopId) || null == startDate || null == endDate) {
            logger.error("查询美容店某个时间段的耗卡金额传入参数为空，{}", "shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");
            ;
            return null;
        }

        //获取用户的划卡金额金额
        ShopUserConsumeRecordCriteria recordCriteria = new ShopUserConsumeRecordCriteria();
        ShopUserConsumeRecordCriteria.Criteria criteria = recordCriteria.createCriteria();
        //卡耗归为消费类
        criteria.andConsumeTypeEqualTo(ConsumeTypeEnum.CONSUME.getCode());
        List<String> goods = new ArrayList<>();
        goods.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
        goods.add(GoodsTypeEnum.TIME_CARD.getCode());
        goods.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
        goods.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
        criteria.andGoodsTypeIn(goods);
        criteria.andCreateDateBetween(startDate, endDate);

        BigDecimal bigDecimal = extShopUserConsumeRecordMapper.selectSumPriceByCriteria(recordCriteria);

        return bigDecimal;
    }

    /**
     * 查询新客个数
     */
    @Override
    public int getShopNewUserNumber(String shopId, Date startDate, Date endDate) {
        logger.info("查询新客个数传入参数={}", "shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");
        return shopArchivesService.getShopBuildArchivesNumbers(shopId, startDate, endDate);
    }
}
