package com.wisdom.beauty.core.service.stock.impl;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.StockStyleEnum;
import com.wisdom.beauty.api.enums.StockTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.mapper.*;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.core.mapper.stock.ExtStockServiceMapper;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.dto.system.PageParamDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * FileName: ShopStockServiceImpl
 *
 * @author: 张超 Date: 2018/4/23 0003 14:49 Description: 仓库和库存相关
 */
@Service("shopStockService")
public class ShopStockServiceImpl implements ShopStockService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtStockServiceMapper extStockServiceMapper;

    @Autowired
    private ShopStockRecordMapper shopStockRecordMapper;

    @Autowired
    private ShopStockMapper shopStockMapper;

    @Autowired
    private ShopProductInfoService shopProductInfoService;

    @Autowired
    private ShopStockBossRelationMapper shopStockBossRelationMapper;
    @Autowired
    private ExtShopStockMapper extShopStockMapper;

    @Autowired
    private ShopStockNumberMapper shopStockNumberMapper;
    @Autowired
    private ExtShopStockNumberMapper extShopStockNumberMapper;

    /**
     * 查询仓库列表
     *
     * @param pageParamDTO 分页对象
     * @return
     */
    @Override
    public PageParamDTO<List<ExtShopStoreDTO>> findStoreListS(PageParamDTO<ExtShopStoreDTO> pageParamDTO) {

        PageParamDTO<List<ExtShopStoreDTO>> page = new PageParamDTO<>();
        List<ExtShopStoreDTO> shopStoreList = null;
        try {

            shopStoreList = extStockServiceMapper.findStoreList(pageParamDTO);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        page.setResponseData(shopStoreList);
        page.setTotalCount(shopStoreList.get(0).getSum());
        logger.info("查询仓库列表" + shopStoreList);
        return page;
    }

    /**
     * 插入一条出入库记录
     *
     * @param shopStockRecordDTO 出入库表实体对象
     * @return
     */
    @Override
    public int insertStockRecord(ShopStockRecordDTO shopStockRecordDTO) {
        int result = 0;
        try {

            result = shopStockRecordMapper.insert(shopStockRecordDTO);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        logger.info("插入出入库记录成功" + shopStockRecordDTO.getId());
        return result;
    }

    @Override
    public ShopStockResponseDTO getShopStock(ShopStockRecordDTO shopStockRecordDTO) {
        logger.info("getShopStock方法传入的参数shopStockRecordDTO={}", shopStockRecordDTO);
        ShopStockRecordCriteria criteria = new ShopStockRecordCriteria();
        ShopStockRecordCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopStockRecordDTO.getId())) {
            c.andIdEqualTo(shopStockRecordDTO.getId());
        }
        List<ShopStockRecordDTO> list = shopStockRecordMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("返回的集合为空");
            return null;
        }
        ShopStockRecordDTO shopStockRecord = list.get(0);
        String id = shopStockRecord.getId();
        // 根据id查询，shop_stock的入库，出库产品记录
        ShopProductInfoResponseDTO shopProductInfoResponseDTO = null;
        if (StringUtils.isBlank(id)) {
            logger.info("库存记录为空");
            return null;
        }
        ShopStockDTO shopStockDTO = new ShopStockDTO();
        shopStockDTO.setShopStockRecordId(id);
        List<ShopStockDTO> shopStockList = this.getShopStockList(shopStockDTO);
        if (CollectionUtils.isEmpty(shopStockList)) {
            return null;
        }
        List<String> shopProcIds = new ArrayList<>();
        for (ShopStockDTO shopStock : shopStockList) {
            shopProcIds.add(shopStock.getShopProcId());
        }
        List<ShopProductInfoResponseDTO> shopProductInfos = null;
        if (CollectionUtils.isNotEmpty(shopProcIds)) {
            // 查询产品信息
            shopProductInfos = shopProductInfoService.getProductInfoList(shopProcIds);
        }
        // 存放key是产品主键，value是产品对象
        Map<String, ShopProductInfoResponseDTO> map = new HashedMap();
        for (ShopProductInfoResponseDTO shopProductInfoResponse : shopProductInfos) {
            map.put(shopProductInfoResponse.getId(), shopProductInfoResponse);
        }
        List<ShopStockResponseDTO> shopStockResponses = new ArrayList<>();
        ShopStockResponseDTO shopStockResponseDTO = null;
        for (ShopStockDTO shopStock : shopStockList) {
            shopStockResponseDTO = new ShopStockResponseDTO();
            shopStockResponseDTO.setProductDate(shopStock.getProductDate());
            shopStockResponseDTO.setStockNumber(shopStock.getStockNumber());
            shopStockResponseDTO.setOutStockNumber(shopStock.getOutStockNumber());
            shopStockResponseDTO.setStockPrice(shopStock.getStockPrice());
            shopStockResponseDTO.setProductId(shopStock.getShopProcId());
            if (map.get(shopStock.getShopProcId()) != null) {
                shopStockResponseDTO.setProductCode(map.get(shopStock.getShopProcId()).getProductCode());
                shopStockResponseDTO.setProductSpec(map.get(shopStock.getShopProcId()).getProductSpec());
                shopStockResponseDTO.setProductUnit(map.get(shopStock.getShopProcId()).getProductUnit());
                shopStockResponseDTO.setImageUrl(map.get(shopStock.getShopProcId()).getImageUrl());
                shopStockResponseDTO.setShopProcName(map.get(shopStock.getShopProcId()).getProductName());
            }

            shopStockResponses.add(shopStockResponseDTO);
        }

        shopStockResponseDTO = new ShopStockResponseDTO();
        shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
        shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
        shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
        shopStockResponseDTO.setName(shopStockRecord.getName());
        shopStockResponseDTO.setStockType(shopStockRecord.getStockType());
        shopStockResponseDTO.setApplayUser(shopStockRecord.getManagerId());
        shopStockResponseDTO.setDetail(shopStockRecord.getDetail());
        shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
        shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
        if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockRecord.getStockStyle())
                || StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
            shopStockResponseDTO.setStockStatus(StockStyleEnum.IN_STORAGE.getCode());
        }
        if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockRecord.getStockStyle())
                || StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
            shopStockResponseDTO.setStockStatus(StockStyleEnum.OUT_STORAGE.getCode());
        }
        shopStockResponseDTO.setShopStockResponseDTO(shopStockResponses);

        return shopStockResponseDTO;
    }

    @Override
    public List<ShopStockRecordDTO> getShopStockRecordList(PageParamVoDTO<ShopStockRecordDTO> pageParamVoDTO) {
        ShopStockRecordDTO shopStockRecord = pageParamVoDTO.getRequestData();
        if (shopStockRecord == null) {
            logger.info("getShopStockRecordList方法传入的参数shopStockRecord为空");
            return null;
        }

        ShopStockRecordCriteria criteria = new ShopStockRecordCriteria();
        ShopStockRecordCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopStockRecord.getShopBossId())) {
            c.andShopBossIdEqualTo(shopStockRecord.getShopBossId());
        }
        if (StringUtils.isNotBlank(shopStockRecord.getShopStoreId())) {
            c.andShopStoreIdEqualTo(shopStockRecord.getShopStoreId());
        }
        if (StringUtils.isNotBlank(shopStockRecord.getStockStyle())) {
            if (StockStyleEnum.IN_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
                List<String> styles = new ArrayList<>();
                styles.add(StockStyleEnum.MANUAL_IN_STORAGE.getCode());
                styles.add(StockStyleEnum.SCAN_IN_STORAGE.getCode());
                c.andStockStyleIn(styles);
            }
            if (StockStyleEnum.OUT_STORAGE.getCode().equals(shopStockRecord.getStockStyle())) {
                List<String> styles = new ArrayList<>();
                styles.add(StockStyleEnum.MANUAL_OUT_STORAGE.getCode());
                styles.add(StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode());
                c.andStockStyleIn(styles);
            }
        }
        if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
                && StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
            Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
            Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
            c.andCreateDateBetween(startDate, endDate);
        }

        criteria.setPageSize(pageParamVoDTO.getPageSize());

        return shopStockRecordMapper.selectByCriteria(criteria);

    }

    @Override
    public List<ShopStockBossRelationDTO> getShopStockBossRelationList(ShopStockBossRelationDTO shopStockBossRelation) {
        logger.info("getShopStockBossRelationList方法传入的参数shopStockRecordDTO为shopStockRecordDTO={}",
                shopStockBossRelation);
        if (shopStockBossRelation == null) {
            logger.info("getShopStockBossRelationList方法传入的参数shopStockRecordDTO为空");
            return null;
        }
        ShopStockBossRelationCriteria criteria = new ShopStockBossRelationCriteria();
        ShopStockBossRelationCriteria.Criteria c = criteria.createCriteria();

        if (StringUtils.isNotBlank(shopStockBossRelation.getShopBossId())) {
            c.andShopBossIdEqualTo(shopStockBossRelation.getShopBossId());
        }
        if (StringUtils.isNotBlank(shopStockBossRelation.getShopStoreId())) {
            c.andShopStoreIdEqualTo(shopStockBossRelation.getShopStoreId());
        }
        List<ShopStockBossRelationDTO> list = shopStockBossRelationMapper.selectByCriteria(criteria);
        return list;
    }

    @Override
    public List<ShopStockDTO> getShopStockList(ShopStockDTO shopStockDTO) {
        logger.info("getShopStockList方法传入的参数shopStockDTO={}", shopStockDTO);
        if (shopStockDTO == null) {
            logger.info("getShopStockList方法传入的参数ShopStockDTO为空");
            return null;
        }

        ShopStockCriteria criteria = new ShopStockCriteria();
        ShopStockCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(shopStockDTO.getShopStockRecordId())) {
            c.andShopStockRecordIdEqualTo(shopStockDTO.getShopStockRecordId());
        }
        List<ShopStockDTO> lis = shopStockMapper.selectByCriteria(criteria);
        return lis;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertShopStockDTO(String shopStockRequest) {
        if (StringUtils.isBlank(shopStockRequest)) {
            logger.info("insertShopStockDTO方法出入的参数shopStockRequest为空");
        }
        ShopStockRequestDTO[] ss = (ShopStockRequestDTO[]) JSONArray.toArray(JSONArray.fromObject(shopStockRequest), ShopStockRequestDTO.class);
        List<ShopStockRequestDTO> shopStockRequestDTO = Arrays.asList(ss);
        if (CollectionUtils.isEmpty(shopStockRequestDTO)) {
            logger.info("转换出来的集合shopStocks为空");
            return 0;
        }
        SysBossDTO sysBossDTO = UserUtils.getBossInfo();
        // 记录插入
        ShopStockRequestDTO shopStockDto = shopStockRequestDTO.get(0);

        ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
        shopStockRecordDTO.setShopBossId(sysBossDTO.getId());
        shopStockRecordDTO.setShopStoreId(shopStockDto.getShopStoreId());
        shopStockRecordDTO.setId(IdGen.uuid());
        shopStockRecordDTO.setCreateDate(new Date());
        shopStockRecordDTO.setFlowNo(shopStockDto.getFlowNo());
        shopStockRecordDTO.setStockStyle(shopStockDto.getStockType());
        shopStockRecordDTO.setDetail(shopStockDto.getDetail());
        //待定这个人是谁
        shopStockRecordDTO.setManagerId(shopStockDto.getApplayUser());
        // 插入StockRecord,并且返回id
        //判断是出库还是入库
        if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle()) ||
                StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
            //此时是入库
            shopStockRecordDTO.setStockType(StockTypeEnum.PURCHASE_IN_STORAGE.getCode());
        }
        if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle()) ||
                StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
            //此时是出库
            shopStockRecordDTO.setStockType(shopStockDto.getStockType());
        }
        this.insertStockRecord(shopStockRecordDTO);
        // 记录插入结束
        List<ShopStockDTO> shopStockDTOList = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        ShopStockDTO shopStockDTO = null;
        Map<String, ShopStockRequestDTO> map = new HashedMap();
        for (ShopStockRequestDTO shopStock : shopStockRequestDTO) {
            shopStockDTO = new ShopStockDTO();
            BeanUtils.copyProperties(shopStock, shopStockDTO);
            shopStockDTO.setId(IdGen.uuid());
            shopStock.setShopBossId(sysBossDTO.getId());
            // 将record表总的id放入shopStockDTO中
            shopStockDTO.setShopStockRecordId(shopStockRecordDTO.getId());
            shopStockDTOList.add(shopStockDTO);
            productIds.add(shopStock.getShopProcId());
            map.put(shopStock.getShopProcId(), shopStock);
        }
        extShopStockMapper.insertBatchShopStock(shopStockDTOList);
        // 更新数量,如果shop_stock_number中没有数据插入。有则更新
        // 根据产品查询
        List<ShopStockNumberDTO> shopStockNumberDTOs = this.getShopStockNumberBy(productIds);
        // 计算需要更新的产品
        List<String> requireUpdate = new ArrayList<>();
        // 需要更新的List集合
        List<ShopStockNumberDTO> updateShopStockNumber = new ArrayList<>();

        ShopStockNumberDTO shopStockNumberDTO = null;
        for (ShopStockNumberDTO shopStockNumber : shopStockNumberDTOs) {
            if (map.get(shopStockNumber.getShopProcId()) != null) {
                // 需要更新的
                //判断是出库还是入库。入库需要加，出库需要减
                if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle()) ||
                        StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
                    //此时是入库
                    shopStockNumber.setStockNumber(
                            shopStockNumber.getStockNumber() + map.get(shopStockNumber.getShopProcId()).getStockNumber());
                }
                if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle()) ||
                        StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
                    //此时是出库
                    shopStockNumber.setStockNumber(
                            shopStockNumber.getStockNumber() - map.get(shopStockNumber.getShopProcId()).getStockNumber());
                }
                updateShopStockNumber.add(shopStockNumber);
                // 出去map中需要更新的，剩下的就是需要插入的
                map.remove(shopStockNumber.getShopProcId());
            }
        }
        // 获取需要插入的对象，然后遍历
        Collection<ShopStockRequestDTO> values = map.values();
        // 需要插入的List集合
        List<ShopStockNumberDTO> saveShopStockNumber = new ArrayList<>();
        if (CollectionUtils.isEmpty(values)) {
            logger.info("values为空不需要更新");
            return 1;
        }
        ShopStockNumberDTO shopStockNumber = null;
        for (ShopStockRequestDTO addShopStockRequest : values) {
            shopStockNumberDTO = new ShopStockNumberDTO();
            shopStockNumberDTO.setId(IdGen.uuid());
            shopStockNumberDTO.setStockNumber(addShopStockRequest.getStockNumber());
            shopStockNumberDTO.setShopBossId(sysBossDTO.getId());
            shopStockNumberDTO.setShopProcId(addShopStockRequest.getShopProcId());
            shopStockNumberDTO.setShopStoreId(addShopStockRequest.getShopStoreId());
            shopStockNumberDTO.setUpdateDate(new Date());
            shopStockNumberDTO.setCreateDate(new Date());
            saveShopStockNumber.add(shopStockNumberDTO);
        }
        return this.batchAddShopStockNumber(saveShopStockNumber);
    }

    @Override
    public int updateStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
        shopStockNumberDTO.setActualStockNumber(shopStockNumberDTO.getActualStockNumber());
        shopStockNumberDTO.setActualStockPrice(shopStockNumberDTO.getActualStockPrice());
        return shopStockNumberMapper.updateByPrimaryKeySelective(shopStockNumberDTO);

    }

    @Override
    public ShopStockNumberDTO getStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
        logger.info("getShopStock方法传入的参数shopStockNumberDTO={}", shopStockNumberDTO);
        ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
        ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(shopStockNumberDTO.getShopProcId())) {
            c.andShopProcIdEqualTo(shopStockNumberDTO.getShopProcId());
        }
        if (StringUtils.isNotBlank(shopStockNumberDTO.getShopStoreId())) {
            c.andShopStoreIdEqualTo(shopStockNumberDTO.getShopStoreId());
        }
        List<ShopStockNumberDTO> list = shopStockNumberMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public int saveStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
        return shopStockNumberMapper.insert(shopStockNumberDTO);
    }

    @Override
    public List<ShopStockNumberDTO> getShopStockNumberBy(List<String> productIds) {
        logger.info("getShopStockNumberBy方法传入的参数productIds={}", productIds);
        if (CollectionUtils.isEmpty(productIds)) {
            logger.info("getShopStockNumberBy方法传入的参数productIds为空");
            return null;
        }
        ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
        ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
        c.andShopProcIdIn(productIds);
        return shopStockNumberMapper.selectByCriteria(criteria);
    }

    @Override
    public int batchAddShopStockNumber(List<ShopStockNumberDTO> shopStockNumberDTO) {
        return extShopStockNumberMapper.saveBatchShopStockNumber(shopStockNumberDTO);
    }

    @Override
    public ShopStockResponseDTO getProductInfoAndStock(String shopStoreId, String shopProcId) {
        logger.info("getProductInfoAndStock方法传入的参数shopStoreId={}shopProcId={}", shopStoreId, shopProcId);

        // 查询产品信息
        ShopProductInfoResponseDTO shopProductInfoResponseDTO = shopProductInfoService.getProductDetail(shopProcId);
        ShopStockNumberDTO shopStockNumberDTO = new ShopStockNumberDTO();
        shopStockNumberDTO.setShopStoreId(shopStoreId);
        shopStockNumberDTO.setShopProcId(shopProcId);
        ShopStockNumberDTO shopStockNumber = this.getStockNumber(shopStockNumberDTO);
        ShopStockResponseDTO shopStockResponseDTO = new ShopStockResponseDTO();
        if (shopProductInfoResponseDTO != null) {
            shopStockResponseDTO.setProductCode(shopProductInfoResponseDTO.getProductCode());
            shopStockResponseDTO.setProductSpec(shopProductInfoResponseDTO.getProductSpec());
            shopStockResponseDTO.setProductUnit(shopProductInfoResponseDTO.getProductUnit());
            shopStockResponseDTO.setImageUrl(shopProductInfoResponseDTO.getImageUrl());
            shopStockResponseDTO.setShopProcName(shopProductInfoResponseDTO.getProductName());
        }
        if (shopStockNumber != null) {
            shopStockResponseDTO.setStockNumber(shopStockNumber.getStockNumber());
            shopStockResponseDTO.setStockPrice(shopStockNumber.getStockPrice());
        }
        return shopStockResponseDTO;
    }

    @Override
    public List<ShopStockResponseDTO> getStockDetailList(PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO) {
        ShopStockNumberDTO shopStockNumberDTO=pageParamVoDTO.getRequestData();
        if (shopStockNumberDTO == null) {
            logger.info("getStockDetailList方法传入的参数shopStockNumberDTO为空");
            return null;
        }
        logger.info("getStockDetailList方法出入的参数:productTypeTwoId={},shopStoreId={}", shopStockNumberDTO.getProductTypeTwoId(), shopStockNumberDTO.getShopStoreId());
        ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
        ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(shopStockNumberDTO.getProductTypeTwoId())) {
            c.andProductTypeTwoIdEqualTo(shopStockNumberDTO.getProductTypeTwoId());
        }
        if (StringUtils.isNotBlank(shopStockNumberDTO.getShopStoreId())) {
            c.andShopStoreIdEqualTo(shopStockNumberDTO.getShopStoreId());
        }
        // 分页
        if (pageParamVoDTO.getPaging()) {
            criteria.setLimitStart(pageParamVoDTO.getPageNo());
            criteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        List<ShopStockNumberDTO> shopStockNumberDTOs = shopStockNumberMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(shopStockNumberDTOs)) {
            logger.info("getStockDetailList方法获取的结果shopStockNumberDTOs为空");
            return null;
        }
        //遍历shopStockNumberDTOs
        //map用户存储，key=产品ID value=ShopStockNumberDTO
        Map<String, ShopStockNumberDTO> map = new HashMap<>(16);
        List<String> productIds = new ArrayList<>();
        for (ShopStockNumberDTO shopStockNumber : shopStockNumberDTOs) {
            productIds.add(shopStockNumber.getShopProcId());
            map.put(shopStockNumber.getShopProcId(), shopStockNumber);
        }
        //根据多个productId查询产品信息
        List<ShopProductInfoResponseDTO> shopProductInfoResponses = shopProductInfoService.getProductInfoList(productIds);
        if (CollectionUtils.isEmpty(shopProductInfoResponses)) {
            logger.info("根据多个productId查询产品信息的结果shopProductInfoResponses为空");
            return null;
        }
        //遍历shopProductInfoResponses，并将其存入MAP中，key存放产品的id
        Map<String, ShopProductInfoResponseDTO> productInfoMap = new HashMap<>(16);
        for (ShopProductInfoResponseDTO shopProductInfoResponseDTO : shopProductInfoResponses) {
            productInfoMap.put(shopProductInfoResponseDTO.getId(), shopProductInfoResponseDTO);
        }
        //计算库存总量
        ShopStockNumberDTO stock = new ShopStockNumberDTO();
        stock.setProductTypeTwoId(shopStockNumberDTO.getProductTypeTwoId());
        List<ShopStockNumberDTO> allStoreNumbers = this.getShopStockNumberDTOList(stock);
        Map<String, Integer> allStoreNumberMap = new HashMap<>();
        for (ShopStockNumberDTO shopStockNumber : allStoreNumbers) {
            if (allStoreNumberMap.containsKey(shopStockNumber.getShopProcId())) {
                Integer allStoreNumber = allStoreNumberMap.get(shopStockNumber.getShopProcId());
                allStoreNumberMap.put(shopStockNumber.getShopProcId(), allStoreNumber + shopStockNumber.getStockNumber());
            } else {
                allStoreNumberMap.put(shopStockNumber.getShopProcId(), shopStockNumber.getStockNumber());
            }
        }
        //再次遍历shopStockNumberDTOs
        List<ShopStockResponseDTO> shopStockResponses = new ArrayList<>();
        ShopStockResponseDTO shopStockResponseDTO = null;
        for (ShopStockNumberDTO shopStockNumber : shopStockNumberDTOs) {
            shopStockResponseDTO = new ShopStockResponseDTO();
            if (productInfoMap.get(shopStockNumber.getShopProcId()) != null) {
                shopStockResponseDTO.setImageUrl(productInfoMap.get(shopStockNumber.getShopProcId()).getImageUrl());
                shopStockResponseDTO.setShopProcName(productInfoMap.get(shopStockNumber.getShopProcId()).getProductName());
                shopStockResponseDTO.setProductCode(productInfoMap.get(shopStockNumber.getShopProcId()).getProductCode());
                //单位
                shopStockResponseDTO.setProductUnit(productInfoMap.get(shopStockNumber.getShopProcId()).getProductUnit());
                //规格
                shopStockResponseDTO.setProductSpec(productInfoMap.get(shopStockNumber.getShopProcId()).getProductSpec());
                //库存总量
                shopStockResponseDTO.setAllStoreNumber(allStoreNumberMap.get(shopStockNumber.getShopProcId()));
            }
            //本仓库存
            shopStockResponseDTO.setStoreNumberSelf(shopStockNumber.getStockNumber());

            shopStockResponses.add(shopStockResponseDTO);
        }
        return shopStockResponses;
    }

    private List<ShopStockNumberDTO> getShopStockNumberDTOList(ShopStockNumberDTO shopStockNumberDTO) {
        ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
        ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
        if (StringUtils.isNotBlank(shopStockNumberDTO.getProductTypeTwoId())) {
            c.andProductTypeTwoIdEqualTo(shopStockNumberDTO.getProductTypeTwoId());
        }
        return shopStockNumberMapper.selectByCriteria(criteria);
    }
}
