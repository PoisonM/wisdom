package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.dto.SysUserAccountCriteria;
import com.wisdom.beauty.core.mapper.ShopUserRelationMapper;
import com.wisdom.beauty.core.service.ShopUserRelationService;
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
        logger.info("isMember方法传入的参数userId={}",userId);
        ShopUserRelationCriteria criteria = new ShopUserRelationCriteria();
        ShopUserRelationCriteria.Criteria c = criteria.createCriteria();
        c.andSysUserIdEqualTo(userId);
        List<ShopUserRelationDTO> list= shopUserRelationMapper.selectByCriteria(criteria);
        ShopUserRelationDTO shopUserRelationDTO=null;
        if(CollectionUtils.isNotEmpty(list)){
             shopUserRelationDTO=list.get(0);
        }
        return shopUserRelationDTO.getStatus();
    }
}
