package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopRemindSettingCriteria;
import com.wisdom.beauty.api.dto.ShopRemindSettingDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.core.mapper.ShopRemindSettingMapper;
import com.wisdom.beauty.core.service.ShopRemindService;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ShopRemindService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 提醒相关
 */
@Service("shopRemindService")
public class ShopRemindServiceImpl implements ShopRemindService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ShopRemindSettingMapper shopRemindSettingMapper;

    /**
     * 查询老板端推送消息设置
     *
     * @param shopRemindSettingDTO
     * @return
     */
    @Override
    public ShopRemindSettingDTO getShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO) {
        logger.info("保存老板端推送消息设置传入参数={}", "shopRemindSettingDTO = [" + shopRemindSettingDTO + "]");
        ShopRemindSettingCriteria criteria = new ShopRemindSettingCriteria();
        ShopRemindSettingCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopRemindSettingDTO.getSysBossId())) {
            c.andSysBossIdEqualTo(shopRemindSettingDTO.getSysBossId());
        }
        List<ShopRemindSettingDTO> shopRemindSettingDTOS = shopRemindSettingMapper.selectByCriteria(criteria);
        if (CommonUtils.objectIsEmpty(shopRemindSettingDTOS)) {
            return saveShopRemindSetting(shopRemindSettingDTO);
        } else {
            return shopRemindSettingDTOS.get(0);
        }
    }

    /**
     * 保存老板端推送消息设置
     *
     * @param shopRemindSettingDTO
     * @return
     */
    @Override
    public ShopRemindSettingDTO saveShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO) {
        logger.info("保存老板端推送消息设置传入参数={}", "shopRemindSettingDTO = [" + shopRemindSettingDTO + "]");
        shopRemindSettingDTO.setId(IdGen.uuid());
        shopRemindSettingDTO.setStatus(CommonCodeEnum.ENABLED.getCode());
        shopRemindSettingMapper.insertSelective(shopRemindSettingDTO);
        return shopRemindSettingDTO;
    }

    /**
     * 修改老板端推送消息设置
     *
     * @param shopRemindSettingDTO
     * @return
     */
    @Override
    public int updateShopRemindSetting(ShopRemindSettingDTO shopRemindSettingDTO) {
        if (StringUtils.isBlank(shopRemindSettingDTO.getId())) {
            logger.error("修改老板端推送消息设置传入参数异常{}", "shopRemindSettingDTO = [" + shopRemindSettingDTO + "]");
            return 0;
        }
        return shopRemindSettingMapper.updateByPrimaryKeySelective(shopRemindSettingDTO);
    }
}
