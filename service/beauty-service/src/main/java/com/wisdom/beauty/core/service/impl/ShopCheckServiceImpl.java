package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ClosePositionTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClosePositionRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopClosePositionResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.mapper.ShopCheckRecordMapper;
import com.wisdom.beauty.core.mapper.ShopClosePositionRecordMapper;
import com.wisdom.beauty.core.mapper.ShopStockNumberMapper;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.util.IdGen;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private ShopStockService shopStockService;

    @Autowired
    private ShopClosePositionRecordMapper shopClosePositionRecordMapper;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doClosePosition(ShopClosePositionRequestDTO shopClosePositionRequestDTO) {
        if(shopClosePositionRequestDTO==null){
            logger.info("doClosePosition方法传入的参数shopClosePositionRequestDTO为空");
            return 0;
        }
        //更新库存信息
        ShopStockNumberDTO shopStockNumberDTO=new ShopStockNumberDTO();
        shopStockNumberDTO.setActualStockNumber(shopClosePositionRequestDTO.getActualStockNumber());
        shopStockNumberDTO.setShopProcId(shopClosePositionRequestDTO.getShopProcId());
        shopStockNumberDTO.setShopStoreId(shopClosePositionRequestDTO.getShopStoreId());
        shopStockNumberDTO.setUpdateDate(new Date());
        shopStockService.updateStockNumber(shopStockNumberDTO);
        //插入平仓记录
        ShopClosePositionRecordDTO shopClosePositionRecordDTO=new ShopClosePositionRecordDTO();
        shopClosePositionRecordDTO.setActualStockNumber(shopClosePositionRequestDTO.getActualStockNumber());
        shopClosePositionRecordDTO.setStockNumber(shopClosePositionRequestDTO.getStockNumber());
        shopClosePositionRecordDTO.setShopProcId(shopClosePositionRequestDTO.getShopProcId());
        shopClosePositionRecordDTO.setOriginalFlowNo(shopClosePositionRequestDTO.getOriginalFlowNo());
        //暂时先随机生成一个数，需要根据需求修改到底生成什么样子的数据
        shopClosePositionRecordDTO.setFlowNo(IdGen.uuid());
        shopClosePositionRecordDTO.setId(IdGen.uuid());
        shopClosePositionRecordDTO.setCreateDate(new Date());
        shopClosePositionRecordDTO.setUpdateDate(new Date());
        shopClosePositionRecordMapper.insertSelective(shopClosePositionRecordDTO);
        //更新盘点记录表，1.更新盘点记录字段shopClosePositionId  2.更新平仓状态 state
        ShopCheckRecordDTO shopCheckRecordDTO=new ShopCheckRecordDTO();
        shopCheckRecordDTO.setId(shopClosePositionRequestDTO.getShopCheckRecorId());
        shopCheckRecordDTO.setState(ClosePositionTypeEnum.CLOSE_POSITION_YES.getCode());
        shopCheckRecordDTO.setShopClosePositionId(shopClosePositionRecordDTO.getId());
        return shopCheckRecordMapper.updateByPrimaryKeySelective(shopCheckRecordDTO);
    }

    @Override
    public ShopClosePositionRecordDTO getShopClosePositionDetail(String id,String productName,String productTypeName) {
         logger.info("getshopClosePositionRecordDTO方法传入的参数id={},productName={},productTypeName={}",id,productName,productTypeName);
         if(StringUtils.isBlank(id)){
             return  null;
         }
        ShopClosePositionRecordDTO shopClosePositionRecordDTO= shopClosePositionRecordMapper.selectByPrimaryKey(id);
        ShopClosePositionResponseDTO shopClosePositionResponseDTO=new ShopClosePositionResponseDTO();
        BeanUtils.copyProperties(shopClosePositionRecordDTO,shopClosePositionResponseDTO);
        Integer differenceNumber=shopClosePositionRecordDTO.getActualStockNumber()-shopClosePositionRecordDTO.getStockNumber();
        shopClosePositionResponseDTO.setDifferenceNumber(Math.abs(differenceNumber));
        if(differenceNumber>0){
            //实际库存大于库存， 盘盈
            shopClosePositionResponseDTO.setState(ClosePositionTypeEnum.INVENTORY_PROFIT.getCode());

        }
        if(differenceNumber>0){
            //实际库存小于库存 盘亏
            shopClosePositionResponseDTO.setState(ClosePositionTypeEnum.INVENTORY_LOSS.getCode());
        }
        shopClosePositionResponseDTO.setProductName(productName);
        shopClosePositionResponseDTO.setProductTypeName(productTypeName);
        return shopClosePositionResponseDTO;
    }
}
