package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.SysShopCriteria;
import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.core.mapper.SysShopMapper;
import com.wisdom.beauty.core.service.ShopService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ShopBossService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 老板与店相关
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysShopMapper sysShopMapper;

    /**
     * 根据主键查询shop相关信息
     *
     * @param id
     * @return
     */
    @Override
    public SysShopDTO getShopInfoByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            logger.error("根据条件查询shop相关信息{}", "id = [" + id + "]");
            return null;
        }
        SysShopDTO sysShopDTOS = sysShopMapper.selectByPrimaryKey(id);
        return sysShopDTOS;
    }

    /**
     * 根据条件查询shop相关信息
     *
     * @return
     */
    @Override
    public List<SysShopDTO> getShopInfo(SysShopDTO sysShopDTO) {
        if (null == sysShopDTO) {
            logger.error("根据条件查询shop相关信息{}", "sysShopDTO = [" + sysShopDTO + "]");
            return null;
        }

        SysShopCriteria sysShopCriteria = new SysShopCriteria();
        SysShopCriteria.Criteria criteria = sysShopCriteria.createCriteria();
        if (StringUtils.isNotBlank(sysShopDTO.getParentsId())) {
            criteria.andParentsIdEqualTo(sysShopDTO.getParentsId());
        }

        List<SysShopDTO> sysShopDTOS = sysShopMapper.selectByCriteria(sysShopCriteria);
        return sysShopDTOS;
    }
}
