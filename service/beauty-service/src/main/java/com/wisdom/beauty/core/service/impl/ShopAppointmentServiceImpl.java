package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ExtShopAppointServiceMapper;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Service("appointmentService")
public class ShopAppointmentServiceImpl implements ShopAppointmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Autowired
    private ExtShopAppointServiceMapper extShopAppointServiceMapper;

    /**
     * 根据时间查询查询某个店的有预约号源的美容师列表
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public List<ShopAppointServiceDTO> getShopAppointClerkInfoByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if(null == extShopAppointServiceDTO || null == extShopAppointServiceDTO.getSearchStartTime() || null == extShopAppointServiceDTO.getSearchEndTime()){
            logger.info("根据时间查询查询某个店的有预约号源的美容师列表,查询参数为空{}",extShopAppointServiceDTO);
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        criteria.andCreateDateBetween(extShopAppointServiceDTO.getSearchStartTime(),extShopAppointServiceDTO.getSearchEndTime());
        List<ShopAppointServiceDTO> appointServiceDTOS = extShopAppointServiceMapper.selectShopAppointClerkInfoByCriteria(shopAppointServiceCriteria);
        return appointServiceDTOS;
    }

    /**
     * 根据时间查询某个店下的某个美容师的预约列表
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public List<ShopAppointServiceDTO> getShopClerkAppointListByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if(null == extShopAppointServiceDTO || null == extShopAppointServiceDTO.getSearchStartTime() || null == extShopAppointServiceDTO.getSearchEndTime()){
            logger.info("根据时间查询某个店下的某个美容师的预约列表,查询参数为空{}",extShopAppointServiceDTO);
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        if(StringUtils.isNotBlank(extShopAppointServiceDTO.getSysShopId())){
            criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        }
        if(StringUtils.isNotBlank(extShopAppointServiceDTO.getSysClerkId())){
            criteria.andSysClerkIdEqualTo(extShopAppointServiceDTO.getSysClerkId());
        }
        if(null != extShopAppointServiceDTO.getSearchStartTime() && null != extShopAppointServiceDTO.getSearchEndTime()){
            criteria.andCreateDateBetween(extShopAppointServiceDTO.getSearchStartTime(),extShopAppointServiceDTO.getSearchEndTime());
        }

        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);
        return appointServiceDTOS;
    }

    /**
     * 更新预约信息
     *
     * @param shopAppointServiceDTO
     * @return
     */
    @Override
    public int updateAppointmentInfo(ShopAppointServiceDTO shopAppointServiceDTO) {
        if (shopAppointServiceDTO == null || StringUtils.isBlank(shopAppointServiceDTO.getId())) {
            return 0;
        }
        return shopAppointServiceMapper.updateByPrimaryKeySelective(shopAppointServiceDTO);
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据用户ID查询预约信息
     * @Date:2018/4/8 14:26
     */
    @Override
    public ShopAppointServiceDTO getShopAppointService(String userId) {

        logger.info("getShopAppointServiceDTO传入参数userId={}", userId);
        if (StringUtils.isBlank(userId)) {
            logger.debug(" 根据用户ID查询预约信息,{}", "userId = [" + userId + "]");
            return null;
        }
        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        criteria.andSysUserIdEqualTo(userId);
        shopAppointServiceCriteria.setOrderByClause("create_date");
        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);
        ShopAppointServiceDTO shopAppointServiceDTO = null;

        if (CollectionUtils.isNotEmpty(appointServiceDTOS)) {
            shopAppointServiceDTO = appointServiceDTOS.get(0);
        }
        return shopAppointServiceDTO;
    }

    /**
     * 保存用户的预约信息
     */
    @Override
    public int saveUserShopAppointInfo(ShopAppointServiceDTO shopAppointServiceDTO) {

        logger.info("保存用户的预约信息传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");

        int insert = shopAppointServiceMapper.insertSelective(shopAppointServiceDTO);

        return insert;
    }


}
