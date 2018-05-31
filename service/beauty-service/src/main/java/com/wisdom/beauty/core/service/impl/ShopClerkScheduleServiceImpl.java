package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopClerkScheduleCriteria;
import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.dto.ShopScheduleSettingCriteria;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.enums.ScheduleTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopClerkScheduleDTO;
import com.wisdom.beauty.core.mapper.ExtShopClerkScheduleMapper;
import com.wisdom.beauty.core.mapper.ShopClerkScheduleMapper;
import com.wisdom.beauty.core.mapper.ShopScheduleSettingMapper;
import com.wisdom.beauty.core.service.ShopClerkScheduleService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    public ShopScheduleSettingMapper shopScheduleSettingMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据条件查询排班信息
     * @param extShopClerkScheduleDTO
     * @return
     */
    @Override
    public List<ShopClerkScheduleDTO> getShopClerkScheduleList(ExtShopClerkScheduleDTO extShopClerkScheduleDTO) {

        if (CommonUtils.objectIsEmpty(extShopClerkScheduleDTO)) {
            logger.info("根据条件查询排班信息传入参数为空");
            return null;
        }

        ShopClerkScheduleCriteria scheduleCriteria = new ShopClerkScheduleCriteria();
        ShopClerkScheduleCriteria.Criteria criteria = scheduleCriteria.createCriteria();

        if (StringUtils.isNotBlank(extShopClerkScheduleDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(extShopClerkScheduleDTO.getSysShopId());
        }

        if (StringUtils.isNotBlank(extShopClerkScheduleDTO.getSysClerkId())) {
            criteria.andSysClerkIdEqualTo(extShopClerkScheduleDTO.getSysClerkId());
        }
        //注意，这里精确到月份，如有其他需要扩展接口
        if (null != extShopClerkScheduleDTO.getScheduleDate()) {
            String startDate = DateUtils.getFirstDate(extShopClerkScheduleDTO.getScheduleDate());
            String endDate = DateUtils.getLastDate(extShopClerkScheduleDTO.getScheduleDate());
            criteria.andScheduleDateBetween(DateUtils.StrToDate(startDate, "datetime"), DateUtils.StrToDate(endDate, "datetime"));
        }

        //根据查询日期查询
        if (null != extShopClerkScheduleDTO.getSearchStartDate()) {
            criteria.andScheduleDateEqualTo(extShopClerkScheduleDTO.getSearchStartDate());
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
        Map<String, Object> map = new HashMap<>(1);
        map.put("list",scheduleDTOS);
        return extShopClerkScheduleMapper.batchUpdate(map);
    }

    /**
     * 获取某个店的排班设置信息
     *
     * @param shopScheduleSettingDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ShopScheduleSettingDTO> getBossShopScheduleSetting(ShopScheduleSettingDTO shopScheduleSettingDTO) {
        logger.info("获取某个店的排班设置信息传入参数={}", "shopScheduleSettingDTO = [" + shopScheduleSettingDTO + "]");
        ShopScheduleSettingCriteria criteria = new ShopScheduleSettingCriteria();
        ShopScheduleSettingCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(shopScheduleSettingDTO.getSysShopId())) {
            c.andSysShopIdEqualTo(shopScheduleSettingDTO.getSysShopId());
        }
        List<ShopScheduleSettingDTO> shopScheduleSettingDTOS = shopScheduleSettingMapper.selectByCriteria(criteria);
        //如果排班为空,则自动生成
        if (CommonUtils.objectIsEmpty(shopScheduleSettingDTOS)) {
            ShopScheduleSettingDTO settingDTO = new ShopScheduleSettingDTO();
            settingDTO.setSysShopId(UserUtils.getBossInfo().getCurrentShopId());
            settingDTO.setCreateBy(UserUtils.getBossInfo().getId());
            settingDTO.setStatus(CommonCodeEnum.ENABLED.getCode());
            settingDTO.setCreateDate(new Date());
            settingDTO.setSysBossCode(UserUtils.getBossInfo().getId());
            for (ScheduleTypeEnum scheduleTypeEnum : ScheduleTypeEnum.values()) {
                settingDTO.setEndTime(scheduleTypeEnum.getDefaultEndTime());
                settingDTO.setId(IdGen.uuid());
                settingDTO.setStartTime(scheduleTypeEnum.getDefaultStartTime());
                settingDTO.setTypeName(scheduleTypeEnum.getCode());
                shopScheduleSettingMapper.insertSelective(settingDTO);
                shopScheduleSettingDTOS.add(settingDTO);
            }
        }
        return shopScheduleSettingDTOS;
    }

    /**
     * 修改老板的排班设置信息
     *
     * @param shopScheduleSettingDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBossShopScheduleSetting(ShopScheduleSettingDTO shopScheduleSettingDTO) {
        return shopScheduleSettingMapper.updateByPrimaryKeySelective(shopScheduleSettingDTO);
    }


}
