package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopCustomerProductRelationServiceImpl
 *
 * @Author： huan
 * @Description: 用户和产品相关的接口
 * @Date:Created in 2018/4/10 14:31
 * @since JDK 1.8
 */
@Service("shopCustomerProductRelationService")
public class ShopCustomerProductRelationServiceImpl implements ShopCustomerProductRelationService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserProductRelationMapper shopUserProductRelationMapper;
    @Override
    public ShopUserProductRelationDTO getShopProductInfo(String shopProductId) {
        logger.info("getShopProductInfo方法传入的参数,shopProductId={}",shopProductId);
        if(StringUtils.isBlank(shopProductId)){
            throw new ServiceException("getShopCustomerConsumeRecordList方法传入的参数为空");
        }
        ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
        ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

        criteria.andIdEqualTo(shopProductId);

        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper.selectByCriteria(shopUserProductRelationCriteria);
        if(CollectionUtils.isEmpty(shopUserProductRelationDTOS)){
            logger.info("未获取到信息,getShopProductInfo查询的结果为空");
            return  null;
        }
        ShopUserProductRelationDTO shopUserProductRelationDTO=shopUserProductRelationDTOS.get(0);
        return  shopUserProductRelationDTO;
    }
}
