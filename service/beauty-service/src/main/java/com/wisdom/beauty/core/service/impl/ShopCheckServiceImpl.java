package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.mapper.ShopCheckRecordMapper;
import com.wisdom.beauty.core.mapper.ShopStockNumberMapper;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/21.
 */
@Service("shopCheckService")
public class ShopCheckServiceImpl implements ShopCheckService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopCheckRecordMapper shopCheckRecordMapper;

    @Autowired
    private ShopStockNumberMapper shopStockNumberMapper;

    @Autowired
    private ShopProductInfoService shopProductInfoService;
    @Override
    public List<ShopCheckRecordDTO> getProductCheckRecordList(ShopCheckRecordDTO shopCheckRecordDTO) {
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

    @Override
    public Map<String ,Object> getProductCheckRecordDeatil(ShopCheckRecordDTO shopCheckRecordDTO) {
        if(shopCheckRecordDTO==null){
            logger.info("getProductCheckRecordDeatil方法传入的参数shopCheckRecordDTO为空");
            return null;
        }
        logger.info("getProductCheckRecord方法传入的参数shopStockNumberId={}，productId={}",shopCheckRecordDTO.getShopStockNumberId(),shopCheckRecordDTO.getShopProcId());
        //查询库存表
        ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
        ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
        if (com.wisdom.common.util.StringUtils.isNotBlank(shopCheckRecordDTO.getShopStockNumberId())) {
            c.andIdEqualTo(shopCheckRecordDTO.getShopStockNumberId());
        }
        List<ShopStockNumberDTO> shopStockNumberDTOList= shopStockNumberMapper.selectByCriteria(criteria);
        if(CollectionUtils.isEmpty(shopStockNumberDTOList)){
            logger.info("shopStockNumberDTOList结果为空");
            return null;
        }
        //根据产品id查询产品信息
        ShopProductInfoResponseDTO shopProductInfoResponseDTO = shopProductInfoService.getProductDetail(shopCheckRecordDTO.getShopProcId());
        if (shopProductInfoResponseDTO==null) {
            logger.info("根据productId查询产品信息的结果shopProductInfoResponseDTO为空");
        }
        //该产品对应的品牌
        String productTypeOneName=shopProductInfoResponseDTO.getProductTypeOneName();
        //查询产品的系列
        ShopProductTypeDTO shopProductTypeDTO=new ShopProductTypeDTO();
        shopProductTypeDTO.setId(shopProductInfoResponseDTO.getProductTypeOneId());
        List<ShopProductTypeDTO> shopProductTypeList=shopProductInfoService.getTwoLevelProductList(shopProductTypeDTO);
        //根据产品id查询产品品牌以及品牌下的类别个数
        ShopStockNumberDTO shopStockNumberDTO=shopStockNumberDTOList.get(0);
        ShopStockResponseDTO shopStockResponseDTO=new ShopStockResponseDTO();
        shopStockResponseDTO.setImageUrl(shopProductInfoResponseDTO.getImageUrl());
        shopStockResponseDTO.setShopProcName(shopProductInfoResponseDTO.getProductName());
        //库存数
        shopStockResponseDTO.setStockNumber(shopStockNumberDTO.getStockNumber());
        //库存实际数
        shopStockResponseDTO.setActualStockNumber(shopStockNumberDTO.getActualStockNumber());
        Map<String ,Object> map=new HashMap<>(16);
        map.put("shopStockResponseDTO",shopStockResponseDTO);
        map.put("productTypeOneName",productTypeOneName);
        map.put("productTypeNumber",shopProductTypeList.size());
        return  map;
    }
}
