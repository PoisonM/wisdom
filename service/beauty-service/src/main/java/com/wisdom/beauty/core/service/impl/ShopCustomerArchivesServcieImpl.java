package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserArchivesCriteria;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.core.mapper.ShopUserArchivesMapper;
import com.wisdom.beauty.core.service.ShopCustomerArchivesServcie;
import com.wisdom.common.dto.account.PageParamVoDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopCustomerArchivesServcieImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/3 16:41
 * @since JDK 1.8
 */
@Service("shopCustomerArchivesServcie")
public class ShopCustomerArchivesServcieImpl implements ShopCustomerArchivesServcie {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserArchivesMapper shopUserArchivesMapper;

    @Override
    public int getArchivesCount(ShopUserArchivesDTO shopUserArchivesDTO) {
        logger.info("getArchivesCount方法传入的参数={}", shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isBlank(shopUserArchivesDTO.getSysShopId())) {
            throw new ServiceException("SysShopId为空");
        }
        ShopUserArchivesCriteria criteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria c = criteria.createCriteria();
        ShopUserArchivesCriteria.Criteria or = criteria.createCriteria();

        //参数
        c.andSysShopIdEqualTo(shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopUserArchivesDTO.getPhone())) {
            c.andPhoneLike("%" + shopUserArchivesDTO.getPhone() + "%");
        }

        or.andSysShopIdEqualTo(shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopUserArchivesDTO.getSysUserName())) {
            or.andSysUserNameLike("%" + shopUserArchivesDTO.getSysUserName() + "%");
        }
        criteria.or(or);
        int count = shopUserArchivesMapper.countByCriteria(criteria);
        return count;
    }

    @Override
    public List<ShopUserArchivesDTO> getArchivesList(PageParamVoDTO<ShopUserArchivesDTO> shopCustomerArchivesDTO) {
        logger.info("getArchivesList方法传入的参数={}", shopCustomerArchivesDTO);
        if (StringUtils.isBlank(shopCustomerArchivesDTO.getRequestData().getSysShopId())) {
            throw new ServiceException("SysShopId为空");
        }
        ShopUserArchivesCriteria criteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria c = criteria.createCriteria();
        ShopUserArchivesCriteria.Criteria or = criteria.createCriteria();


        // 排序
        criteria.setOrderByClause("sys_user_name");
        // 分页
        criteria.setLimitStart(shopCustomerArchivesDTO.getPageNo());
        criteria.setPageSize(shopCustomerArchivesDTO.getPageSize());
        //参数
        c.andSysShopIdEqualTo(shopCustomerArchivesDTO.getRequestData().getSysShopId());
        if (StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getPhone())) {
            c.andPhoneLike("%" + shopCustomerArchivesDTO.getRequestData().getPhone() + "%");
        }

        or.andSysShopIdEqualTo(shopCustomerArchivesDTO.getRequestData().getSysShopId());
        if (StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getSysUserName())) {
            or.andSysUserNameLike("%" + shopCustomerArchivesDTO.getRequestData().getSysUserName() + "%");
        }
        criteria.or(or);
        List<ShopUserArchivesDTO> shopCustomerArchiveslist = shopUserArchivesMapper.selectByCriteria(criteria);

        return shopCustomerArchiveslist;
    }
}
