package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopCheckRecordCriteria;
import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.beauty.api.dto.ShopClosePositionRecordDTO;
import com.wisdom.beauty.api.dto.ShopStockNumberDTO;
import com.wisdom.beauty.api.enums.ClosePositionTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClosePositionRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopCheckRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopClosePositionResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoCheckResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.core.mapper.ShopCheckRecordMapper;
import com.wisdom.beauty.core.mapper.ShopClosePositionRecordMapper;
import com.wisdom.beauty.core.mapper.ShopStockNumberMapper;
import com.wisdom.beauty.core.service.ShopCheckService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.dto.account.PageParamVoDTO;
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

import java.util.*;

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
    public List<ShopCheckRecordResponseDTO> getProductCheckRecordList(PageParamVoDTO<ShopCheckRecordDTO> pageParamVoDTO) {
        ShopCheckRecordDTO shopCheckRecordDTO=pageParamVoDTO.getRequestData();
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
        if(pageParamVoDTO.getPaging()){
            shopCheckRecordCriteria.setLimitStart(pageParamVoDTO.getPageNo());
            shopCheckRecordCriteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        List<ShopCheckRecordDTO> list= shopCheckRecordMapper.selectByCriteria(shopCheckRecordCriteria);

        Map<String,ShopCheckRecordResponseDTO> map=new HashMap<>();
        // todo 待修复
        List<ShopCheckRecordResponseDTO> shopCheckRecordResponseDTOs=new ArrayList<>();
        ShopCheckRecordResponseDTO shopCheckRecordResponseDTO=null;
        for(ShopCheckRecordDTO shopCheckRecord:list){
            shopCheckRecordResponseDTO=new ShopCheckRecordResponseDTO();
            if(map.containsKey(shopCheckRecord.getFlowNo())){
                shopCheckRecordResponseDTO=map.get(shopCheckRecord.getFlowNo());
                if(ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecordResponseDTO.getState())||
                        ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecord.getState())){
                    //如果有一个盘点记录是未平仓的则设置为未处理
                    shopCheckRecordResponseDTO.setState(ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode());
                }
                //计算异常数
                if (ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecord.getState())) {
                    shopCheckRecordResponseDTO.setExceptionNumber(shopCheckRecordResponseDTO.getExceptionNumber()+1);
                }
                map.put(shopCheckRecord.getFlowNo(),shopCheckRecordResponseDTO);

            }else {

                shopCheckRecordResponseDTO.setState(shopCheckRecord.getState());
                shopCheckRecordResponseDTO.setCreateDate(shopCheckRecord.getCreateDate());
                shopCheckRecordResponseDTO.setShopProcId(shopCheckRecord.getShopProcId());
                if(ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecord.getState())){
                    shopCheckRecordResponseDTO.setExceptionNumber(1);
                }else {
                    shopCheckRecordResponseDTO.setExceptionNumber(0);
                }
                map.put(shopCheckRecord.getFlowNo(),shopCheckRecordResponseDTO);
            }
            shopCheckRecordResponseDTOs.add(shopCheckRecordResponseDTO);

        }
        List values = Arrays.asList(map.values().toArray());
        return values;
    }

    @Override
    public List<ShopCheckRecordResponseDTO> getProductCheckRecordDeatil(String flowNo) {
        if(StringUtils.isBlank(flowNo)){
            logger.info("getProductCheckRecordDeatil方法传入的参数flowNo为空");
            return null;
        }
        //根据流水号查询盘点记录
        ShopCheckRecordCriteria shopCheckRecordCriteria = new ShopCheckRecordCriteria();
        ShopCheckRecordCriteria.Criteria c1 = shopCheckRecordCriteria.createCriteria();
        c1.andFlowNoEqualTo(flowNo);
        // todo 待修复
        List<ShopCheckRecordDTO> shopCheckRecordDTOList= shopCheckRecordMapper.selectByCriteria(shopCheckRecordCriteria);
        Map<String,ShopCheckRecordResponseDTO> map=new HashMap<>();
        ShopCheckRecordResponseDTO shopCheckRecordResponseDTO=null;
        for(ShopCheckRecordDTO shopCheckRecord:shopCheckRecordDTOList){
            if(map.containsKey(shopCheckRecord.getProductTypeOneId())){
                ShopCheckRecordResponseDTO devShopCheckRecordResponseDTO=map.get(shopCheckRecord.getProductTypeOneId());
                List<ShopCheckRecordResponseDTO> devList=devShopCheckRecordResponseDTO.getShopCheckRecordResponseList();
                shopCheckRecordResponseDTO=new ShopCheckRecordResponseDTO();
                shopCheckRecordResponseDTO.setProductName(shopCheckRecord.getShopProcName());
                shopCheckRecordResponseDTO.setStockNumber(shopCheckRecord.getStockNumber());
                shopCheckRecordResponseDTO.setActualStockNumber(shopCheckRecord.getActualStockNumber());
                shopCheckRecordResponseDTO.setId(shopCheckRecord.getId());
                shopCheckRecordResponseDTO.setShopProcId(shopCheckRecord.getShopProcId());
                devList.add(shopCheckRecordResponseDTO);
                devShopCheckRecordResponseDTO.setShopCheckRecordResponseList(devList);
                if(ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecord.getState())){
                    devShopCheckRecordResponseDTO.setExceptionNumber(devShopCheckRecordResponseDTO.getExceptionNumber()+1);
                }
                devShopCheckRecordResponseDTO.setProductTypeNumber(devShopCheckRecordResponseDTO.getProductTypeNumber()+1);
                map.put(shopCheckRecord.getProductTypeOneId(),devShopCheckRecordResponseDTO);
            }else {
                shopCheckRecordResponseDTO=new ShopCheckRecordResponseDTO();
                ShopCheckRecordResponseDTO shopCheckRecordResponseDTO2=new ShopCheckRecordResponseDTO();
                shopCheckRecordResponseDTO.setProductName(shopCheckRecord.getShopProcName());
                shopCheckRecordResponseDTO.setStockNumber(shopCheckRecord.getStockNumber());
                shopCheckRecordResponseDTO.setActualStockNumber(shopCheckRecord.getActualStockNumber());
                shopCheckRecordResponseDTO.setId(shopCheckRecord.getId());
                shopCheckRecordResponseDTO.setShopProcId(shopCheckRecord.getShopProcId());
                List<ShopCheckRecordResponseDTO> shopCheckRecordResponseList=new ArrayList<>();
                shopCheckRecordResponseList.add(shopCheckRecordResponseDTO);
                shopCheckRecordResponseDTO2.setShopCheckRecordResponseList(shopCheckRecordResponseList);
                shopCheckRecordResponseDTO2.setProductTypeOneName(shopCheckRecord.getProductTypeOneName());
                if(ClosePositionTypeEnum.CLOSE_POSITION_NO.getCode().equals(shopCheckRecord.getState())){
                    shopCheckRecordResponseDTO2.setExceptionNumber(1);
                }else {
                    shopCheckRecordResponseDTO2.setExceptionNumber(0);
                }
                shopCheckRecordResponseDTO2.setProductTypeNumber(1);
                map.put(shopCheckRecord.getProductTypeOneId(),shopCheckRecordResponseDTO2);
            }
        }
        List values = Arrays.asList(map.values().toArray());
        return values;
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

    @Override
    public List<ShopProductInfoCheckResponseDTO> getProductsCheckLit(String shopStoreId , List<String> productIds) {
        //获取库存量
        List<ShopStockNumberDTO> shopStockNumberDTOList= shopStockService.getStockNumberList(shopStoreId,productIds);
        Map<String,Integer> map=new HashMap<>(16);
        for(ShopStockNumberDTO shopStockNumberDTO:shopStockNumberDTOList){
            map.put(shopStockNumberDTO.getShopProcId(),shopStockNumberDTO.getStockNumber());
        }
        List<ShopProductInfoResponseDTO> list = shopProductInfoService.getProductInfoList(productIds);
        if(CollectionUtils.isEmpty(list)){
            return  null;
        }
        List<ShopProductInfoCheckResponseDTO>  shopCheckRecordDTOList=new ArrayList<>();
        ShopProductInfoCheckResponseDTO shopProductInfoCheckResponseDTO=new ShopProductInfoCheckResponseDTO();
        for(ShopProductInfoResponseDTO shopProductInfoResponseDTO:list){
            shopProductInfoCheckResponseDTO.setProductName(shopProductInfoResponseDTO.getProductName());
            shopProductInfoCheckResponseDTO.setProductUrl(shopProductInfoResponseDTO.getProductUrl());
            shopProductInfoCheckResponseDTO.setProductCode(shopProductInfoResponseDTO.getProductCode());
            shopProductInfoCheckResponseDTO.setProductUnit(shopProductInfoResponseDTO.getProductUnit());
            shopProductInfoCheckResponseDTO.setProductSpec(shopProductInfoResponseDTO.getProductSpec());
            shopProductInfoCheckResponseDTO.setProductTypeOneId(shopProductInfoResponseDTO.getProductTypeOneId());
            shopProductInfoCheckResponseDTO.setProductTypeOneName(shopProductInfoResponseDTO.getProductTypeOneName());
            if(map.get(shopProductInfoResponseDTO.getId())!=null){
                shopProductInfoCheckResponseDTO.setStockNumber(map.get(shopProductInfoResponseDTO.getId()));
            }
            //产品id
            shopProductInfoCheckResponseDTO.setId(shopProductInfoResponseDTO.getId());
            shopProductInfoCheckResponseDTO.setShopStoreId(shopStoreId);
            shopCheckRecordDTOList.add(shopProductInfoCheckResponseDTO);
        }
        return shopCheckRecordDTOList;
    }
}
