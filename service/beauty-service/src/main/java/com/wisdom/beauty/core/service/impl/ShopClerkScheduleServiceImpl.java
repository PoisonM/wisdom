package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopClerkScheduleCriteria;
import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardCriteria;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.core.mapper.ExtShopClerkScheduleMapper;
import com.wisdom.beauty.core.mapper.ShopClerkScheduleMapper;
import com.wisdom.beauty.core.mapper.ShopUserRechargeCardMapper;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopClerkScheduleService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("shopClerkScheduleService")
public class ShopClerkScheduleServiceImpl implements ShopClerkScheduleService {

    @Autowired
    public ShopClerkScheduleMapper shopClerkScheduleMapper;

    @Autowired
    public ExtShopClerkScheduleMapper extShopClerkScheduleMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据条件查询排班信息
     * @param shopClerkScheduleDTO
     * @return
     */
    @Override
    public List<ShopClerkScheduleDTO> getShopClerkScheduleList(ShopClerkScheduleDTO shopClerkScheduleDTO) {

        if(CommonUtils.objectIsEmpty(shopClerkScheduleDTO)){
            logger.info("根据条件查询排班信息传入参数为空");
            return null;
        }

        ShopClerkScheduleCriteria scheduleCriteria = new ShopClerkScheduleCriteria();
        ShopClerkScheduleCriteria.Criteria criteria = scheduleCriteria.createCriteria();

        if(StringUtils.isNotBlank(shopClerkScheduleDTO.getSysShopId())){
            criteria.andSysShopIdEqualTo(shopClerkScheduleDTO.getSysShopId());
        }
        //默认查询shopClerkScheduleDTO.getScheduleDate()所在的整月份
        if(null != shopClerkScheduleDTO.getScheduleDate()){
            Date firstDate = DateUtils.StrToDate(DateUtils.getFirstDate(shopClerkScheduleDTO.getScheduleDate()),"datetime");
            Date lastDay = DateUtils.StrToDate(DateUtils.getLastDate(shopClerkScheduleDTO.getScheduleDate()),"datetime");
            criteria.andScheduleDateBetween(firstDate,lastDay);
        }

        List<ShopClerkScheduleDTO> shopClerkScheduleDTOS = shopClerkScheduleMapper.selectByCriteria(scheduleCriteria);
        return shopClerkScheduleDTOS;
    }

    /**
     * 批量生成店员的排班信息
     * @param scheduleDTOS
     */
    @Override
    public int saveShopClerkScheduleList(List<ShopClerkScheduleDTO> scheduleDTOS) {
        return extShopClerkScheduleMapper.insertBatch(scheduleDTOS);
    }

    /**
     * 批量更新店员的排班信息
     * @param scheduleDTOS
     * @return
     */
    @Override
    public int updateShopClerkScheduleList(List<ShopClerkScheduleDTO> scheduleDTOS) {
        return extShopClerkScheduleMapper.batchUpdate(scheduleDTOS);
    }


}
