package com.wisdom.beauty.core.service.stock.impl;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.StockStyleEnum;
import com.wisdom.beauty.api.enums.StockTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.requestDto.SetStorekeeperRequestDTO;
import com.wisdom.beauty.api.requestDto.ShopStockRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopProductInfoResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.beauty.core.mapper.*;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.wisdom.beauty.client.UserServiceClient;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * FileName: ShopStockServiceImpl
 *
 * @author: 张超 Date: 2018/4/23 0003 14:49 Description: 仓库和库存相关
 */
@Service("shopStockService")
public class ShopStockServiceImpl implements ShopStockService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@Autowired
	private ExtShopCheckRecordMapper extShopCheckRecordMapper;

	@Autowired
	private ShopStoreMapper shopStoreMapper;

	@Autowired
	private  UserServiceClient userServiceClient;

	/**
	 * 查询仓库列表
	 *
	 * @param sysBossCode
	 * @return
	 */
	@Override
	public List<ShopStoreDTO> findStoreList(String sysBossCode) {
		logger.info("findStoreList方法传入的参数sysBossCode={}", sysBossCode);
		if (StringUtils.isBlank(sysBossCode)) {
			return null;
		}
		ShopStoreCriteria shopStoreCriteria = new ShopStoreCriteria();
		ShopStoreCriteria.Criteria c = shopStoreCriteria.createCriteria();
		c.andSysBossCodeEqualTo(sysBossCode);
		return shopStoreMapper.selectByCriteria(shopStoreCriteria);

	}

	@Override
	public int setStorekeeper(SetStorekeeperRequestDTO setStorekeeperRequestDTO) {
		if (setStorekeeperRequestDTO == null) {
			logger.info("参数setStorekeeperRequestDTO为空");
			return 0;
		}
		String[] storeManagerIds = setStorekeeperRequestDTO.getStoreManagerIds();
		String[] storeManagerNames = setStorekeeperRequestDTO.getStoreManagerNames();
		String shopStoreId = setStorekeeperRequestDTO.getShopStoreId();
		StringBuffer storeManagerId = new StringBuffer();

		for (String storeManager : storeManagerIds) {
			storeManagerId.append(storeManager).append(",");
		}
		if (StringUtils.isNotBlank(storeManagerId.toString())) {
			storeManagerId.deleteCharAt(shopStoreId.length() - 1);
		}
		StringBuffer storeManagerName = new StringBuffer();

		for (String storeManager : storeManagerNames) {
			storeManagerName.append(storeManager).append(",");
		}
		if (StringUtils.isNotBlank(storeManagerName.toString())) {
			storeManagerName.deleteCharAt(storeManagerName.length() - 1);
		}
		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		ShopStoreDTO shopStoreDTO = new ShopStoreDTO();
		shopStoreDTO.setId(shopStoreId);
		shopStoreDTO.setSysBossCode(sysBossDTO.getSysBossCode());
		shopStoreDTO.setStoreManagerId(storeManagerId.toString());
		shopStoreDTO.setSysUserName(storeManagerName.toString());
		return shopStoreMapper.updateByPrimaryKeySelective(shopStoreDTO);
	}

	@Override
	public Map<String,String> getStoreManager(String id) {
		logger.info("getStoreManager方法传入的参数id={}");
		ShopStoreCriteria shopStoreCriteria = new ShopStoreCriteria();
		ShopStoreCriteria.Criteria c = shopStoreCriteria.createCriteria();
		c.andIdEqualTo(id);
		List<ShopStoreDTO> list = shopStoreMapper.selectByCriteria(shopStoreCriteria);
		if (CollectionUtils.isEmpty(list)) {
			logger.info("查询结果list为空");
			return null;
		}
		ShopStoreDTO shopStoreDTO = list.get(0);
		String storeManagerName = shopStoreDTO.getSysUserName();
		if (StringUtils.isBlank(storeManagerName)) {
			logger.info("storeManagerName为空");
			return null;
		}
		Map<String, String> map=new HashedMap();
		map.put("storeManagerName",storeManagerName);
		map.put("storeManagerId",shopStoreDTO.getStoreManagerId());
		return map;
	}

	/**
	 * 插入一条出入库记录
	 *
	 * @param shopStockRecordDTO
	 *            出入库表实体对象
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		ShopStockRecordDTO shopStockRecord = list.get(0);

		if(list.get(0).getReceiver()!=null){
			List<SysClerkDTO> sysClerkDTOList = userServiceClient.getClerkInfoByClerkId(list.get(0).getReceiver());
			if(sysClerkDTOList!=null&&sysClerkDTOList.size()>0){
				shopStockRecord.setReceiver(sysClerkDTOList.get(0).getName());
			}
		}

		shopStockRecord.setCreateDateTime(sdf.format(list.get(0).getCreateDate()));
		String id = shopStockRecord.getId();
		// 根据id查询，shop_stock的入库，出库产品记录
		ShopProductInfoResponseDTO shopProductInfoResponseDTO = null;

		List<ShopStoreDTO> StoreList = this.findStoreList(shopStockRecord.getSysBossCode());
		for(ShopStoreDTO store:StoreList){
			if(store.getId().equals(shopStockRecord.getShopStoreId())){
				shopStockRecord.setName(store.getName());
			}
		}

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
		if (CommonUtils.objectIsNotEmpty(shopProductInfos)) {
			for (ShopProductInfoResponseDTO shopProductInfoResponse : shopProductInfos) {
				map.put(shopProductInfoResponse.getId(), shopProductInfoResponse);
			}
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
				shopStockResponseDTO.setImageUrl(map.get(shopStock.getShopProcId()).getImageList());
				shopStockResponseDTO.setShopProcName(map.get(shopStock.getShopProcId()).getProductName());
			}

			shopStockResponses.add(shopStockResponseDTO);
		}
		SysBossDTO sysBoss = new SysBossDTO();
		sysBoss.setId(shopStockRecord.getManagerId());
		SysBossDTO sysBossDTO = userServiceClient.getBossInfo(sysBoss);
		shopStockResponseDTO = new ShopStockResponseDTO();
		shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
		shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
		shopStockResponseDTO.setReceiver(shopStockRecord.getReceiver());
		shopStockResponseDTO.setFlowNo(shopStockRecord.getFlowNo());
		shopStockResponseDTO.setName(shopStockRecord.getName());
		shopStockResponseDTO.setStockType(shopStockRecord.getStockType());
		if(sysBossDTO!=null) {
			shopStockResponseDTO.setApplayUser(sysBossDTO.getName());
		}
		shopStockResponseDTO.setDetail(shopStockRecord.getDetail());
		shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
		shopStockResponseDTO.setOperDate(shopStockRecord.getOperDate());
		shopStockResponseDTO.setCreateDate(shopStockRecord.getCreateDate());
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

		if (StringUtils.isNotBlank(shopStockRecord.getSysBossCode())) {
			c.andSysBossCodeEqualTo(shopStockRecord.getSysBossCode());
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
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			c.andCreateDateBetween(startDate, endDate);
		}
		if (pageParamVoDTO.getPaging()) {
			criteria.setPageSize(pageParamVoDTO.getPageSize());
			criteria.setLimitStart(pageParamVoDTO.getPageNo());
		}

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
	public String insertShopStockDTO(String shopStockRequest) {

		logger.info("insertShopStockDTO方法入参shopStockRequest={}", shopStockRequest);

		if (StringUtils.isBlank(shopStockRequest)) {
			logger.info("insertShopStockDTO方法出入的参数shopStockRequest为空");
		}

		ShopStockRequestDTO[] shopStockRequestDTOArray = (ShopStockRequestDTO[]) JSONArray
				.toArray(JSONArray.fromObject(shopStockRequest), ShopStockRequestDTO.class);
		List<ShopStockRequestDTO> shopStockRequestDTO = Arrays.asList(shopStockRequestDTOArray);

		if (CollectionUtils.isEmpty(shopStockRequestDTO)) {
			logger.info("转换出来的集合shopStocks为空");
			return null;
		}

		//开始执行入库/出库
		SysBossDTO sysBossDTO = UserUtils.getBossInfo();
		ShopStockRequestDTO shopStockDto = shopStockRequestDTO.get(0);
		ShopStockRecordDTO shopStockRecordDTO = new ShopStockRecordDTO();
		String id = IdGen.uuid();
		shopStockRecordDTO.setSysBossCode(sysBossDTO.getSysBossCode());
		shopStockRecordDTO.setShopStoreId(shopStockDto.getShopStoreId());
		shopStockRecordDTO.setId(id);
		shopStockRecordDTO.setCreateDate(new Date());
		shopStockRecordDTO.setStockStyle(shopStockDto.getStockStyle());
		shopStockRecordDTO.setDetail(shopStockDto.getDetail());
		shopStockRecordDTO.setManagerId(sysBossDTO.getSysBossCode());
		shopStockRecordDTO.setCreateDate(new Date());

		//生成单据号
		Random random = new Random();
		String result="";
		for (int i=0;i<6;i++)
		{
			result+=random.nextInt(10);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder flowNo = new StringBuilder();
		// 判断是出库还是入库
		if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {

			shopStockRecordDTO.setStockType(StockTypeEnum.PURCHASE_IN_STORAGE.getCode());
			flowNo.append("RK").append("-").append(sdf.format(new Date())).append("-").append(result);
			shopStockRecordDTO.setFlowNo(flowNo.toString());
		}else if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {

			shopStockRecordDTO.setStockType(shopStockDto.getStockType());
			shopStockRecordDTO.setStockStyle(shopStockDto.getStockStyle());
			shopStockRecordDTO.setReceiver(shopStockDto.getReceiver());
			flowNo.append("CK").append("-").append(sdf.format(new Date())).append("-").append(result);
			shopStockRecordDTO.setFlowNo(flowNo.toString());
		}

		//插入出入库记录
		this.insertStockRecord(shopStockRecordDTO);

		List<ShopStockDTO> shopStockDTOList = new ArrayList<>();
		List<String> productIds = new ArrayList<>();
		ShopStockDTO shopStockDTO = null;
		Map<String, ShopStockRequestDTO> map = new HashedMap();

		// 遍历传入过来的集合参数
		for (ShopStockRequestDTO shopStock : shopStockRequestDTO) {
			shopStockDTO = new ShopStockDTO();
			BeanUtils.copyProperties(shopStock, shopStockDTO);
			shopStockDTO.setId(IdGen.uuid());
			shopStockDTO.setCreateDate(new Date());
			shopStockDTO.setUpdateDate(new Date());
			shopStock.setShopBossId(sysBossDTO.getId());

			// 将record表总的id放入shopStockDTO中
			shopStockDTO.setShopStockRecordId(shopStockRecordDTO.getId());

			// 判断的出库还是入库
			if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
				// 此时是入库
				shopStockDTO.setStockNumber(shopStock.getStockNumber());
			}else if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
				// 此时是出库
				shopStockDTO.setOutStockNumber(shopStock.getStockOutNumber());
			}
			shopStockDTO.setSysBossCode(sysBossDTO.getSysBossCode());
			shopStockDTO.setFlowNo(flowNo.toString());
			shopStockDTOList.add(shopStockDTO);
			productIds.add(shopStock.getShopProcId());
			map.put(shopStock.getShopProcId(), shopStock);
		}
		extShopStockMapper.insertBatchShopStock(shopStockDTOList);
		// 更新数量,如果shop_stock_number中没有数据插入。有则更新


		// 根据产品查询
		List<ShopStockNumberDTO> shopStockNumberDTOs = this.getShopStockNumberBy(productIds);
		// 需要更新的List集合
		List<ShopStockNumberDTO> updateShopStockNumber = new ArrayList<>();

		for (ShopStockNumberDTO shopStockNumber : shopStockNumberDTOs) {
			if (map.get(shopStockNumber.getShopProcId()) != null) {
				// 需要更新的
				// 判断是出库还是入库。入库需要加，出库需要减
				if (StockStyleEnum.MANUAL_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_IN_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
					// 此时是入库
					shopStockNumber.setStockNumber(shopStockNumber.getStockNumber()
							+ map.get(shopStockNumber.getShopProcId()).getStockNumber());
				}else if (StockStyleEnum.MANUAL_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())|| StockStyleEnum.SCAN_CARD_OUT_STORAGE.getCode().equals(shopStockDto.getStockStyle())) {
					// 此时是出库
					shopStockNumber.setStockNumber(shopStockNumber.getStockNumber()- map.get(shopStockNumber.getShopProcId()).getStockOutNumber());
				}
				updateShopStockNumber.add(shopStockNumber);

				// 出去map中需要更新的，剩下的就是需要插入的
				map.remove(shopStockNumber.getShopProcId());
			}
		}

		// 判断updateShopStockNumber是否为空，如果为空则不需要批量更新库存量
		if (CollectionUtils.isNotEmpty(updateShopStockNumber)) {
				extShopStockNumberMapper.updateBatchShopStockNumberCondition(updateShopStockNumber);
		}

		// 获取需要插入的对象，然后遍历
		Collection<ShopStockRequestDTO> values = map.values();

		// 需要插入的List集合
		List<ShopStockNumberDTO> saveShopStockNumber = new ArrayList<>();
		if (CollectionUtils.isEmpty(values)) {
			logger.info("values为空不需要更新");
			return id;
		}

		// 查询产品信息，用户获取到
		ShopStockNumberDTO shopStockNumberDTO = null;
		for (ShopStockRequestDTO addShopStockRequest : values) {
			shopStockNumberDTO = new ShopStockNumberDTO();
			shopStockNumberDTO.setId(IdGen.uuid());
			shopStockNumberDTO.setStockNumber(addShopStockRequest.getStockNumber());
			shopStockNumberDTO.setSysBossCode(sysBossDTO.getId());
			shopStockNumberDTO.setShopProcId(addShopStockRequest.getShopProcId());
			shopStockNumberDTO.setShopStoreId(addShopStockRequest.getShopStoreId());
			shopStockNumberDTO.setStockPrice(addShopStockRequest.getStockPrice());
			shopStockNumberDTO.setProductTypeTwoId(addShopStockRequest.getProductTypeTwoId());
			shopStockNumberDTO.setUpdateDate(new Date());
			shopStockNumberDTO.setCreateDate(new Date());
			saveShopStockNumber.add(shopStockNumberDTO);
		}
		this.batchAddShopStockNumber(saveShopStockNumber);
		return id;
	}

	@Override
	public int updateStockNumber(ShopStockNumberDTO shopStockNumberDTO) {
		ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
		c.andShopProcIdEqualTo(shopStockNumberDTO.getShopProcId());
		c.andShopStoreIdEqualTo(shopStockNumberDTO.getShopStoreId());

		shopStockNumberDTO.setActualStockNumber(shopStockNumberDTO.getActualStockNumber());
		shopStockNumberDTO.setActualStockPrice(shopStockNumberDTO.getActualStockPrice());

		return shopStockNumberMapper.updateByCriteriaSelective(shopStockNumberDTO, criteria);

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
	public List<ShopStockNumberDTO> getStockNumberList(String shopStoreId, List<String> shopProcIds) {
		logger.info("getStockNumberList方法传入的参数shopStoreId={},shopProcIds={}", shopStoreId, shopProcIds);
		ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
		if (CollectionUtils.isNotEmpty(shopProcIds)) {
			c.andShopProcIdIn(shopProcIds);
		}
		if (StringUtils.isNotBlank(shopStoreId)) {
			c.andShopStoreIdEqualTo(shopStoreId);
		}
		return shopStockNumberMapper.selectByCriteria(criteria);
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
			shopStockResponseDTO.setImageUrl(shopProductInfoResponseDTO.getImageList());
			shopStockResponseDTO.setShopProcName(shopProductInfoResponseDTO.getProductName());
		}
		if (shopStockNumber != null) {
			shopStockResponseDTO.setStockNumber(shopStockNumber.getStockNumber());
			shopStockResponseDTO.setStockPrice(shopStockNumber.getStockPrice());
		}
		return shopStockResponseDTO;
	}

	@Override
	public Map<String, Object> getStockDetailList(PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO,
			List<ShopProductInfoDTO> shopProductInfoDTOs) {
		ShopStockNumberDTO shopStockNumberDTO = pageParamVoDTO.getRequestData();
		if (shopStockNumberDTO == null) {
			logger.info("getStockDetailList方法传入的参数shopStockNumberDTO为空");
			return null;
		}
		logger.info("getStockDetailList方法出入的参数:productTypeTwoId={},shopStoreId={}",
				shopStockNumberDTO.getProductTypeTwoId(), shopStockNumberDTO.getShopStoreId());

		ShopStockNumberCriteria criteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = criteria.createCriteria();
		// 查询条件
		if (StringUtils.isNotBlank(shopStockNumberDTO.getShopStoreId())) {
			c.andShopStoreIdEqualTo(shopStockNumberDTO.getShopStoreId());
		}
		List<ShopStockNumberDTO> shopStockNumberDTOs = shopStockNumberMapper.selectByCriteria(criteria);
		if (CollectionUtils.isEmpty(shopStockNumberDTOs)) {
			logger.info("getStockDetailList方法获取的结果shopStockNumberDTOs为空");
			return null;
		}
		// shopStockMap缓存库存列表
		Map<String, ShopStockNumberDTO> shopStockMap = new HashMap<>(16);
		// map用户存储，key=产品ID value=ShopStockNumberDTO
		for (ShopStockNumberDTO shopStockNumber : shopStockNumberDTOs) {
			shopStockMap.put(shopStockNumber.getShopProcId(), shopStockNumber);
		}
		// productInfoMap 缓存产品列表 key=产品ID value=ShopProductInfoDTO
		Map<String, ShopProductInfoDTO> productInfoMap = new HashMap<>(16);
		for (ShopProductInfoDTO dto : shopProductInfoDTOs) {
			productInfoMap.put(dto.getId(), dto);
		}
		// 计算库存总量
		ShopStockNumberDTO stockNumber = new ShopStockNumberDTO();
		List<ShopStockNumberDTO> allStoreNumbers = this.getShopStockNumberDTOList(stockNumber);
		Map<String, Integer> allStoreNumberMap = new HashMap<>(16);
		for (ShopStockNumberDTO shopStockNumber : allStoreNumbers) {
			if (allStoreNumberMap.containsKey(shopStockNumber.getShopProcId())) {
				Integer allStoreNumber = allStoreNumberMap.get(shopStockNumber.getShopProcId());
				if(shopStockNumber.getStockNumber()!=null){
					allStoreNumberMap.put(shopStockNumber.getShopProcId(), allStoreNumber + shopStockNumber.getStockNumber());
				}

			} else {
				if(shopStockNumber.getStockNumber()!=null) {
					allStoreNumberMap.put(shopStockNumber.getShopProcId(), shopStockNumber.getStockNumber());
				}
			}
		}
		// 遍历shopProductInfoDTOs
		List<ExtShopProductInfoDTO> extShopProductInfoDTOs = new ArrayList<>();
		ExtShopProductInfoDTO extShopProductInfoDTO = null;
		for (ShopProductInfoDTO shopProductInfoDTO : shopProductInfoDTOs) {
			extShopProductInfoDTO = new ExtShopProductInfoDTO();
			BeanUtils.copyProperties(productInfoMap.get(shopProductInfoDTO.getId()), extShopProductInfoDTO);
			// 库存总量
			extShopProductInfoDTO.setAllStoreNumber(allStoreNumberMap.get(shopProductInfoDTO.getId()));
			// 本仓库存
			if (shopStockMap.get(shopProductInfoDTO.getId()) != null) {
				extShopProductInfoDTO.setStoreNumberSelf(shopStockMap.get(shopProductInfoDTO.getId()).getStockNumber());
			}
			extShopProductInfoDTOs.add(extShopProductInfoDTO);
		}
		Map<String, Object> responseMap = new HashMap<>(16);
		Map<String, Object> costMap = this.getCost(shopStockNumberDTO.getShopStoreId(), productInfoMap);
		responseMap.put("allUseCost", costMap == null ? 0 : costMap.get("allUseCost"));
		responseMap.put("useCost", costMap == null ? 0 : costMap.get("useCost"));
		responseMap.put("extShopProductInfoDTOs", extShopProductInfoDTOs);
		return responseMap;
	}

	@Override
	public ShopStockResponseDTO getProductStockDetail(ShopStockNumberDTO shopStockNumberDTO) {
		if (shopStockNumberDTO == null) {
			logger.info("getProductStockDetail方法出入的参数shopStockNumberDTO为空");
			return null;
		}
		logger.info("getProductStockDetail方法传入的参数shopProcId={}", shopStockNumberDTO.getShopProcId());
		if (StringUtils.isBlank(shopStockNumberDTO.getShopProcId())) {
			return null;
		}
		// 计算占用成本，库存量*进货价格
		// 根据产品id获取该产品所有的库存信息
		ShopStockNumberCriteria shopStockNumberCriteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = shopStockNumberCriteria.createCriteria();
		c.andShopProcIdEqualTo(shopStockNumberDTO.getShopProcId());
		List<ShopStockNumberDTO> shopStockNumbers = shopStockNumberMapper.selectByCriteria(shopStockNumberCriteria);
		if (CollectionUtils.isEmpty(shopStockNumbers)) {
			logger.info("产品" + shopStockNumberDTO.getShopProcId() + "没有库存信息");
			return null;
		}
		List<ShopStockResponseDTO> shopStockResponses = new ArrayList<>();
		ShopStockResponseDTO shopStockResponse = null;
		Integer allStoreNumber = null;
		BigDecimal allUseCost = null;
		for (ShopStockNumberDTO shopStockNumber : shopStockNumbers) {
			shopStockResponse = new ShopStockResponseDTO();
			shopStockResponse.setStoreNumberSelf(shopStockNumber.getStockNumber());
			shopStockResponse.setShopStoreName(shopStockNumber.getShopStoreName());
			if(shopStockNumber.getStockNumber()!=null){
				BigDecimal useCost = shopStockNumber.getStockPrice()
						.multiply(new BigDecimal(shopStockNumber.getStockNumber()));
				shopStockResponse.setUseCost(useCost);
				if (allStoreNumber == null) {
					allStoreNumber = shopStockNumber.getStockNumber();
				} else {
					allStoreNumber = allStoreNumber + shopStockNumber.getStockNumber();
				}
				if (allUseCost == null) {
					allUseCost = useCost;
				} else {
					allUseCost = allUseCost.add(useCost);
				}
			}
			shopStockResponse.setShopStoreName(shopStockNumber.getShopStoreName());
			shopStockResponses.add(shopStockResponse);
		}

		shopStockResponse = new ShopStockResponseDTO();
		// 根据产品id查询产品信息
		ShopProductInfoResponseDTO shopProductInfoResponses = shopProductInfoService
				.getProductDetail(shopStockNumberDTO.getShopProcId());
		if (shopProductInfoResponses == null) {
			logger.info("getProductDetail方法获取的对象shopProductInfoResponses为空");
			return null;
		}
		shopStockResponse.setProductImage(shopProductInfoResponses.getProductUrl());
		shopStockResponse.setShopProcName(shopProductInfoResponses.getProductName());
		shopStockResponse.setProductCode(shopProductInfoResponses.getProductCode());
		// 单位
		shopStockResponse.setProductUnit(shopProductInfoResponses.getProductUnit());
		// 规格
		shopStockResponse.setProductSpec(shopProductInfoResponses.getProductSpec());
		shopStockResponse.setProductTypeOneName(shopProductInfoResponses.getProductTypeOneName());
		shopStockResponse.setProductTypeTwoName(shopProductInfoResponses.getProductTypeTwoName());
		shopStockResponse.setShopStockResponseDTO(shopStockResponses);
		shopStockResponse.setAllUseCost(allUseCost);
		shopStockResponse.setAllStoreNumber(allStoreNumber);
		return shopStockResponse;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String checkProduct(List<ShopCheckRecordDTO> list) {
		logger.info("checkProduct方法出入的参数list={}", list);
		// 插入盘点记录
		String flowNo = IdGen.uuid();
		List<ShopCheckRecordDTO> shopCheckRecordDTOList = new ArrayList<>();
		// 更新库存使用的list
		List<ShopStockNumberDTO> shopStockNumberDTOs = new ArrayList<>();
		ShopStockNumberDTO shopStockNumberDTO = null;
		for (ShopCheckRecordDTO shopCheckRecordDTO : list) {
			shopStockNumberDTO = new ShopStockNumberDTO();
			shopStockNumberDTO.setStockNumber(shopCheckRecordDTO.getActualStockNumber());
			shopStockNumberDTO.setShopProcId(shopCheckRecordDTO.getShopProcId());
			shopStockNumberDTO.setShopStoreId(shopCheckRecordDTO.getShopStoreId());
			shopStockNumberDTOs.add(shopStockNumberDTO);
			// 拼装更新库存对象结束
			shopCheckRecordDTO.setId(IdGen.uuid());
			shopCheckRecordDTO.setFlowNo(flowNo);
			shopCheckRecordDTO.setCreateDate(new Date());
			shopCheckRecordDTO.setUpdateDate(new Date());
			shopCheckRecordDTOList.add(shopCheckRecordDTO);
		}
		// 插入盘点的记录
		int insertResult = extShopCheckRecordMapper.insertBatchCheckRecord(shopCheckRecordDTOList);
		logger.info("insertBatchCheckRecord方法更新的条数insertResult={}", insertResult);
		// 更新该产品的库存
		int updateResult = extShopStockNumberMapper.updateBatchShopStockNumberCondition(shopStockNumberDTOs);
		logger.info("updateBatchShopStockNumberCondition方法更新的条数updateResult={}", updateResult);
		;
		return flowNo;
	}

	@Override
	public Map<String, Object> getCost(String shopStoreId, Map<String, ShopProductInfoDTO> productInfoMap) {
		logger.info("getAllUseCost方法传入的参数shopStoreId={}", shopStoreId);
		if (StringUtils.isBlank(shopStoreId)) {
			return null;
		}
		ShopStockNumberCriteria shopStockNumberCriteria = new ShopStockNumberCriteria();
		ShopStockNumberCriteria.Criteria c = shopStockNumberCriteria.createCriteria();
		c.andShopStoreIdEqualTo(shopStoreId);
		List<ShopStockNumberDTO> shopStockNumbers = shopStockNumberMapper.selectByCriteria(shopStockNumberCriteria);
		if (CollectionUtils.isEmpty(shopStockNumbers)) {
			logger.info("查询的结果shopStockNumbers为空");
			return null;
		}
		BigDecimal allUseCost = null;
		BigDecimal useCost = null;
		for (ShopStockNumberDTO shopStockNumber : shopStockNumbers) {
			if (shopStockNumber.getStockPrice() == null) {
				// 为空的时候，则继续循环
				continue;
			}
			if (productInfoMap.get(shopStockNumber.getShopProcId()) != null && shopStockNumber.getShopProcId()
					.equals(productInfoMap.get(shopStockNumber.getShopProcId()).getId())) {
				// 计算所选择产品的占用成本
				if (useCost == null) {
					useCost = shopStockNumber.getStockPrice()
							.multiply(new BigDecimal(shopStockNumber.getStockNumber()));
				} else {
					useCost = useCost.add(
							shopStockNumber.getStockPrice().multiply(new BigDecimal(shopStockNumber.getStockNumber())));
				}
			}
			//计算占用总成
			if (allUseCost == null) {
				if(shopStockNumber.getStockNumber()!=null&&shopStockNumber.getStockPrice()!=null){
					allUseCost = shopStockNumber.getStockPrice().multiply(new BigDecimal(shopStockNumber.getStockNumber()));
				}
			} else {
				if(shopStockNumber.getStockNumber()!=null&&shopStockNumber.getStockPrice()!=null){
					allUseCost = allUseCost.add(
							shopStockNumber.getStockPrice().multiply(new BigDecimal(shopStockNumber.getStockNumber())));
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("allUseCost", allUseCost);
		map.put("useCost", useCost);
		return map;
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
