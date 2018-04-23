package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.ExtShopUserProductRelationMapper;
import com.wisdom.beauty.core.mapper.ShopUserProductRelationMapper;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopProductInfoService;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ExtShopUserProductRelationMapper extShopUserProductRelationMapper;

    @Autowired
    private ShopProductInfoService shopProductInfoService;

    @Override
    public ShopUserProductRelationDTO getShopProductInfo(String shopProductId) {
        logger.info("getShopProductInfo方法传入的参数,shopProductId={}", shopProductId);
        if (StringUtils.isBlank(shopProductId)) {
            throw new ServiceException("getShopProductInfo方法传入的参数为空");
        }
        ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
        ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

        criteria.andIdEqualTo(shopProductId);

        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = shopUserProductRelationMapper.selectByCriteria(shopUserProductRelationCriteria);
        if (CollectionUtils.isEmpty(shopUserProductRelationDTOS)) {
            logger.info("未获取到信息,getShopProductInfo查询的结果为空");
            return null;
        }
        ShopUserProductRelationDTO shopUserProductRelationDTO = shopUserProductRelationDTOS.get(0);
        return shopUserProductRelationDTO;
    }

    @Override
    public Map<String, Object> getShopUserProductRelations(String sysClerkId, String sysShopId, String searchFile) {
        logger.info("getShopProductInfo方法传入的参数,sysClerkId={},searchFile={}", sysClerkId, searchFile);
        if (StringUtils.isBlank(sysClerkId)) {
            throw new ServiceException("getShopUserProductRelations方法传入的参数sysClerkId为空");
        }
        Map<String,String> mapFile=new HashMap();
        mapFile.put("sysClerkId",sysClerkId);
        mapFile.put("sysShopId",sysShopId);
        List<UserProductRelationResponseDTO> list = extShopUserProductRelationMapper.getWaitReceiveNumber(mapFile);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("getWaitReceiveNumber方法查询的结果为空");
            return null;
        }
        //待领取数量
        Integer totalWaitReceiveNumber = 0;
        //待领取数量
        Integer totalWaitReceivePeople = list.size();
        List<String> idList = new ArrayList<>();
        //用于存储用户和剩余领取的数量的对应关系
        Map<String, Integer> map = new HashMap<>(16);
        //遍历用户信息，并且将该用户对应的产品为领取数量放到一起
        for (UserProductRelationResponseDTO userProductRelationResponse : list) {
            totalWaitReceiveNumber = totalWaitReceiveNumber + userProductRelationResponse.getWaitReceiveNumber();
            idList.add(userProductRelationResponse.getSysUserId());
            map.put(userProductRelationResponse.getSysUserId(), userProductRelationResponse.getWaitReceiveNumber());
        }

        String[] strings = new String[idList.size()];
        String[] strs = idList.toArray(strings);
        //查询用户的信息
//        List<UserInfoDTO> userInfoList = userServiceClient.getUserInfoListFromUserId(strs, searchFile);
//        if (CollectionUtils.isEmpty(userInfoList)) {
//
//        }
//        List<UserProductRelationResponseDTO> userProductRelationResponses = new ArrayList<>();
//        UserProductRelationResponseDTO userProductRelationResponseDTO = null;
//        for (UserInfoDTO userInfoDTO : userInfoList) {
//            userProductRelationResponseDTO = new UserProductRelationResponseDTO();
//            userProductRelationResponseDTO.setWaitReceiveNumber(map.get(userInfoDTO.getId()));
//            userProductRelationResponseDTO.setMobile(userInfoDTO.getMobile());
//            userProductRelationResponseDTO.setNickname(userInfoDTO.getNickname());
//            userProductRelationResponses.add(userProductRelationResponseDTO);
//        }
        Map<String, Object> mapResponse = new HashMap<>();
//        mapResponse.put("data", userProductRelationResponses);
//        mapResponse.put("totalWaitReceiveNumber", totalWaitReceiveNumber);
//        mapResponse.put("totalWaitReceivePeople", totalWaitReceivePeople);

        return mapResponse;
    }

    @Override
    public List<UserProductRelationResponseDTO> getShopUserProductRelationList(String sysUserId, String sysShopId) {
        logger.info("getShopUserProductRelationList方法传入的参数,sysUserId={},sysShopId={}", sysUserId, sysShopId);
        if (StringUtils.isBlank(sysUserId) || StringUtils.isBlank(sysShopId)) {
            throw new ServiceException("getShopProductInfo方法传入的参数为空");
        }
        ShopUserProductRelationCriteria shopUserProductRelationCriteria = new ShopUserProductRelationCriteria();
        ShopUserProductRelationCriteria.Criteria criteria = shopUserProductRelationCriteria.createCriteria();

        criteria.andSysShopIdEqualTo(sysShopId);
        criteria.andSysUserIdEqualTo(sysUserId);

        List<ShopUserProductRelationDTO> shopUserProductRelations = shopUserProductRelationMapper.selectByCriteria(shopUserProductRelationCriteria);
        if (CollectionUtils.isEmpty(shopUserProductRelations)) {
            logger.info("shopUserProductRelations为空,shopUserProductRelationMapper未查询到结果");
            return null;
        }
        List<String> list = new ArrayList<>();
        //key是产品id，value是产品信息
        Map<String, ShopProductInfoDTO> map = new HashMap<>(16);
        for (ShopUserProductRelationDTO shopUserProductRelation : shopUserProductRelations) {
            list.add(shopUserProductRelation.getShopProductId());
        }

        List<ShopProductInfoDTO> shopProductInfos = shopProductInfoService.getProductInfoList(list);
        for (ShopProductInfoDTO productInfo : shopProductInfos) {
            map.put(productInfo.getId(), productInfo);
        }
        List<UserProductRelationResponseDTO> responseList = new ArrayList<>();
        UserProductRelationResponseDTO userProductRelationResponseDTO = null;
        for (ShopUserProductRelationDTO shopUserProductRelation : shopUserProductRelations) {
            userProductRelationResponseDTO = new UserProductRelationResponseDTO();
            ShopProductInfoDTO shopProductInfo = map.get(shopUserProductRelation.getId());
            if(shopProductInfo!=null) {
                userProductRelationResponseDTO.setProductName(shopProductInfo.getProductName());
                userProductRelationResponseDTO.setProductSpec(shopProductInfo.getProductSpec());
                userProductRelationResponseDTO.setProductTypeOneName(shopProductInfo.getProductTypeOneName());
                userProductRelationResponseDTO.setProductTypeTwoName(shopProductInfo.getProductTypeTwoName());
                userProductRelationResponseDTO.setWaitReceiveNumber(shopUserProductRelation.getWaitReceiveNumber());
                responseList.add(userProductRelationResponseDTO);
            }
        }

        return responseList;
    }

}
