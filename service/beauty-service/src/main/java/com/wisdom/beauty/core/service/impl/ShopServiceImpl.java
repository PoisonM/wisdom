package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.SysShopCriteria;
import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.beauty.core.mapper.SysShopMapper;
import com.wisdom.beauty.core.redis.MongoUtils;
import com.wisdom.beauty.core.service.ShopService;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private MongoUtils mongoUtils;

    /**
     * 根据主键查询shop相关信息
     *
     * @param id
     * @return
     */
    @Override
    public ExtSysShopDTO getShopInfoByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            logger.error("根据条件查询shop相关信息{}", "id = [" + id + "]");
            return null;
        }
        SysShopDTO sysShopDTOS = sysShopMapper.selectByPrimaryKey(id);
        ExtSysShopDTO extSysShopDTO = new ExtSysShopDTO();
        if(null != sysShopDTOS){
            BeanUtils.copyProperties(sysShopDTOS, extSysShopDTO);
            extSysShopDTO.setImageList(mongoUtils.getImageUrl(extSysShopDTO.getId()));
        }
        return extSysShopDTO;
    }

    /**
     * 根据条件查询shop相关信息
     *
     * @return
     */
    @Override
    public List<ExtSysShopDTO> getShopInfo(SysShopDTO sysShopDTO) {
        if (null == sysShopDTO) {
            logger.error("根据条件查询shop相关信息传入参数为空");
            return null;
        }

        SysShopCriteria sysShopCriteria = new SysShopCriteria();
        SysShopCriteria.Criteria criteria = sysShopCriteria.createCriteria();
        if (StringUtils.isNotBlank(sysShopDTO.getParentsId())) {
            criteria.andParentsIdEqualTo(sysShopDTO.getParentsId());
        }

        List<SysShopDTO> sysShopDTOS = sysShopMapper.selectByCriteria(sysShopCriteria);
        List<ExtSysShopDTO> extSysShopDTOS = null;
        if (CommonUtils.objectIsNotEmpty(sysShopDTOS)) {
            extSysShopDTOS = new ArrayList<>();
            ExtSysShopDTO extSysShopDTO = null;
            for (SysShopDTO shopDTO : sysShopDTOS) {
                extSysShopDTO = new ExtSysShopDTO();
                BeanUtils.copyProperties(shopDTO, extSysShopDTO);
                extSysShopDTO.setImageList(mongoUtils.getImageUrl(shopDTO.getId()));
                extSysShopDTOS.add(extSysShopDTO);
            }
        }
        return extSysShopDTOS;
    }
}
