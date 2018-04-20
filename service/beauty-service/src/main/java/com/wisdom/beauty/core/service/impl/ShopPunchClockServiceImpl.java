package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopPunchClockCriteria;
import com.wisdom.beauty.api.dto.ShopPunchClockDTO;
import com.wisdom.beauty.core.mapper.ShopPunchClockMapper;
import com.wisdom.beauty.core.service.ShopPunchClockService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ShopPunchClockServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/12 15:08
 * @since JDK 1.8
 */
@Service("shopPunchClockService")
public class ShopPunchClockServiceImpl implements ShopPunchClockService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopPunchClockMapper shopPunchClockMapper;

    @Override
    public List<ShopPunchClockDTO> findShopPunchClockList(ShopPunchClockDTO shopPunchClockDTO) {
        return null;
    }

    @Override
    public void punchClock(ShopPunchClockDTO shopPunchClockDTO) {
        logger.info("punchClock方法传入的参数shopPunchClockDTO={}", shopPunchClockDTO);
        if (shopPunchClockDTO == null) {
            logger.info("传入的shopPunchClockDTO参数为空");
        }
        ShopPunchClockDTO shopPunchClock = this.getShopPunchClockDTO(shopPunchClockDTO);
        if (shopPunchClock == null) {
            //如果是没有查询到店员的打卡计划则需要插入一条打卡信息
            shopPunchClockDTO.setPunchTime(new Date());
            shopPunchClockDTO.setSchedulingDate(new Date());
            // TODO: 2018/4/12  需要根据排版信息 判断打卡的状态是否正常
            shopPunchClockMapper.insert(shopPunchClockDTO);
        } else {
            //如果查询到了店员的打卡计划则跟新这条打卡信息的时间
            ShopPunchClockCriteria shopPunchClockCriteria = new ShopPunchClockCriteria();
            ShopPunchClockCriteria.Criteria criteria = shopPunchClockCriteria.createCriteria();
            criteria.andIdEqualTo(shopPunchClock.getId());
            shopPunchClock.setPunchTime(new Date());
            shopPunchClock.setUpdateDate(new Date());
            shopPunchClockMapper.updateByCriteria(shopPunchClock, shopPunchClockCriteria);
        }

    }

    @Override
    public ShopPunchClockDTO getShopPunchClockDTO(ShopPunchClockDTO shopPunchClockDTO) {
        logger.info("getShopPunchClockDTO方法传入的参数shopPunchClockDTO={}", shopPunchClockDTO);
        if (shopPunchClockDTO == null) {
            logger.info("getShopPunchClockDTO方法传入的参数shopPunchClockDTO为空");
            return null;
        }
        ShopPunchClockCriteria shopPunchClockCriteria = new ShopPunchClockCriteria();
        ShopPunchClockCriteria.Criteria criteria = shopPunchClockCriteria.createCriteria();
        criteria.andSchedulingDateEqualTo(shopPunchClockDTO.getSchedulingDate());
        criteria.andSysShopIdEqualTo(shopPunchClockDTO.getSysShopId());
        criteria.andSysClerkIdEqualTo(shopPunchClockDTO.getSysClerkId());
        List<ShopPunchClockDTO> list = shopPunchClockMapper.selectByCriteria(shopPunchClockCriteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("shopPunchClockMapper.selectByCriteria()方法查询出的结果为空");
            return null;
        }
        return list.get(0);
    }
}
