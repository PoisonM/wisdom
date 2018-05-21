package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopCheckRecordCriteria;
import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.beauty.core.mapper.ShopCheckRecordMapper;
import com.wisdom.beauty.core.service.ShopCheckService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/21.
 */
@Service("shopCheckService")
public class ShopCheckServiceImpl implements ShopCheckService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopCheckRecordMapper shopCheckRecordMapper;
    @Override
    public List<ShopCheckRecordDTO> getProductCheckRecord(ShopCheckRecordDTO shopCheckRecordDTO) {
        if(shopCheckRecordDTO==null){
            logger.info("getProductCheckRecord方法传入的参数shopCheckRecordDTO为空");
            return null;
        }
        logger.info("getProductCheckRecord方法传入的参数shopStoreId={}",shopCheckRecordDTO.getShopStoreId());

        ShopCheckRecordCriteria shopCheckRecordCriteria = new ShopCheckRecordCriteria();
        ShopCheckRecordCriteria.Criteria criteria = shopCheckRecordCriteria.createCriteria();
        shopCheckRecordCriteria.setOrderByClause("create_date desc");
        if(StringUtils.isNotBlank(shopCheckRecordDTO.getShopStoreId())){
            criteria.andShopStoreIdEqualTo(shopCheckRecordDTO.getShopStoreId());
        }
        return  shopCheckRecordMapper.selectByCriteria(shopCheckRecordCriteria);
    }
}
