package com.wisdom.beauty.core.service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FileName: ShopStatisticsAnalysisService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 店员相关
 */
public interface ShopStatisticsAnalysisService {

    /**
     * 查询美容店某个时间段的耗卡金额
     */
    BigDecimal getShopCardConsumeAmount(String shopId, Date startDate, Date endDate);

    /**
     * 查询新客个数
     */
    int getShopNewUserNumber(String shopId, Date startDate, Date endDate);

}
