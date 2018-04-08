package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopScheduleSettingCriteria;
import com.wisdom.beauty.api.dto.ShopScheduleSettingDTO;
import com.wisdom.beauty.core.mapper.ShopScheduleSettingMapper;
import com.wisdom.beauty.core.service.WorkService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("workService")
public class WorkServiceImpl implements WorkService{

    @Autowired
    public ShopScheduleSettingMapper shopScheduleSettingMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查看某个店的上下班时间
     * @param shopScheduleSettingDTO
     * @return
     */
    @Override
    public List<ShopScheduleSettingDTO> getShopScheduleSettingInfo(ShopScheduleSettingDTO shopScheduleSettingDTO){

        logger.info("getShopScheduleSettingInfo传入参数，{}","shopScheduleSettingDTO = [" + shopScheduleSettingDTO + "]");

        if(null == shopScheduleSettingDTO){
            logger.error("getShopScheduleSettingInfo传入参数为空,{}" ,shopScheduleSettingDTO);
            return null;
        }

        ShopScheduleSettingCriteria shopScheduleSettingCriteria = new ShopScheduleSettingCriteria();
        ShopScheduleSettingCriteria.Criteria criteria = shopScheduleSettingCriteria.createCriteria();

        if(StringUtils.isNotBlank(shopScheduleSettingDTO.getSysShopId())){
            criteria.andSysShopIdEqualTo(shopScheduleSettingDTO.getSysShopId());
        }
        List<ShopScheduleSettingDTO> shopScheduleSettingDTOS = shopScheduleSettingMapper.selectByCriteria(shopScheduleSettingCriteria);

        if(CommonUtils.objectIsNotEmpty(shopScheduleSettingDTOS)){
            return shopScheduleSettingDTOS;
        }
        logger.debug("查看某个店的上下班查询结果为空，{}" , "shopScheduleSettingDTOS = [" + shopScheduleSettingDTOS + "]");
        return null;
    }

}
