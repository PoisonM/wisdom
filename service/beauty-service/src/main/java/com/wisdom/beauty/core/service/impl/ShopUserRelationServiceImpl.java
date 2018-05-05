package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.core.mapper.ShopUserRelationMapper;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopUserRelationServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 13:54
 * @since JDK 1.8
 */
@Service("shopUserRelationService")
public class ShopUserRelationServiceImpl implements ShopUserRelationService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserRelationMapper shopUserRelationMapper;

    @Override
    public String isMember(String userId) {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null || StringUtils.isBlank(userId)) {
            throw new ServiceException("isMember方法从redis获取sysClerkDTO为空");
        }
        logger.info("isMember方法传入的参数userId={}，sysShopId={}", userId, sysClerkDTO.getSysShopId());

        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();
        c.andSysUserIdEqualTo(userId);
        c.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
        List<ShopUserRelationDTO> list = shopUserRelationMapper.selectByCriteria(criteria);
        ShopUserRelationDTO shopUserRelationDTO = null;
        if (CollectionUtils.isEmpty(list)) {
            logger.info("list集合为空");
            return null;
        }

        shopUserRelationDTO = list.get(0);
        return shopUserRelationDTO.getStatus();
    }

    @Override
    public List<ShopUserRelationDTO> getShopListByCondition(ShopUserRelationDTO shopUserRelationDTO) {

        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();

        SysBossDTO sysBossDTO = UserUtils.getBossInfo();
        if (sysBossDTO != null && StringUtils.isNotNull(sysBossDTO.getId())) {
            logger.info("getShopListByCondition方法传入的参数bossId={}", sysBossDTO.getId());
            c.andSysBossIdEqualTo(sysBossDTO.getId());
        }

        if (StringUtils.isNotBlank(shopUserRelationDTO.getSysUserId())) {
            c.andSysUserIdEqualTo(shopUserRelationDTO.getSysUserId());
        }


        List<ShopUserRelationDTO> shopUserRelations = shopUserRelationMapper.selectByCriteria(criteria);
        return shopUserRelations;
    }
}
