package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.core.service.DiscountService;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopOrderService;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * FileName: ShopOrderServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 订单相关
 */
@Service("shopOrderService")
public class ShopOrderServiceImpl implements ShopOrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DiscountService discountService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ShopCardService cardService;
    /**
     * 保存用户的订单信息
     *
     * @param extShopUserConsumeRecordDTO
     * @return
     */
    @Override
    public int saveShopUserOrderInfo(ShopUserOrderDTO extShopUserConsumeRecordDTO) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO updateShopUserOrderInfo(ShopUserOrderDTO shopUserOrderDTO) {

        ResponseDTO responseDTO = new ResponseDTO();

        String orderId = shopUserOrderDTO.getOrderId();
        //0添加  1删除
        String operation = shopUserOrderDTO.getOperation();

        if (StringUtils.isBlank(orderId)) {
            logger.info("更新订单虚拟商品的信息传入订单号为空");
            throw new ServiceException("更新订单虚拟商品的信息传入订单号为空");
        }
        //根据订单编号查询订单信息
        Query query = new Query(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        ShopUserOrderDTO alreadyOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");
        logger.info("根据订单号，{}，查询订单信息为，{}", orderId, alreadyOrderDTO);
        if (CommonUtils.objectIsEmpty(alreadyOrderDTO)) {
            logger.info("根据订单号，{},mongo查询订单信息为空", orderId);
            throw new ServiceException("mongo查询订单信息为空");
        }

        String goodsType = shopUserOrderDTO.getGoodsType();

        //次卡或者是疗程卡
        if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {
            updateProjectInfo(shopUserOrderDTO, responseDTO, operation, alreadyOrderDTO);
        }
        //产品
        else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
            updateProductInfo(shopUserOrderDTO, responseDTO, operation, alreadyOrderDTO);
        }
        //套卡
        else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
            responseDTO = updateProjectGroupInfo(shopUserOrderDTO, responseDTO, operation, alreadyOrderDTO);
        }
        //充值卡
        updateRechargeCardInfo(shopUserOrderDTO, orderId, alreadyOrderDTO);

        return responseDTO;
    }

    /**
     * 更新订单的充值卡操作
     *
     * @param shopUserOrderDTO
     * @param orderId
     * @param alreadyOrderDTO
     */
    private void updateRechargeCardInfo(ShopUserOrderDTO shopUserOrderDTO, String orderId, ShopUserOrderDTO alreadyOrderDTO) {
        //充值卡主键
        String userRechargeId="";
        //再次查询保证取到最新的订单
        Query query = new Query(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        alreadyOrderDTO = mongoTemplate.findOne(query, ShopUserOrderDTO.class, "shopUserOrderDTO");

        //消费界面用户选择不同的充值卡，也就是用户更新充值卡操作，先获取新的充值卡信息
        if (null != shopUserOrderDTO.getShopUserRechargeCardDTO() && StringUtils.isNotBlank(shopUserOrderDTO.getShopUserRechargeCardDTO().getShopRechargeCardId())) {
            userRechargeId = shopUserOrderDTO.getShopUserRechargeCardDTO().getShopRechargeCardId();
        }
        //消费界面添加更多，比如添加了一个信息的产品，那么新的产品折扣也要逆向更新，新的充值卡信息为空，获取老的充值卡信息
        else if(null != alreadyOrderDTO.getShopUserRechargeCardDTO()){
            userRechargeId = alreadyOrderDTO.getShopUserRechargeCardDTO().getShopRechargeCardId();
        }
        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        //根据主键查询用户与充值卡关系信息
        if(StringUtils.isNotBlank(userRechargeId)){
            shopUserRechargeCardDTO.setShopRechargeCardId(userRechargeId);
            shopUserRechargeCardDTO.setSysUserId(alreadyOrderDTO.getUserId());
            shopUserRechargeCardDTO.setSysShopId(alreadyOrderDTO.getShopId());
            List<ShopUserRechargeCardDTO> userRechargeCardList = cardService.getUserRechargeCardList(shopUserRechargeCardDTO);
            //用户没有选择任何的充值卡
            if (CommonUtils.objectIsNotEmpty(userRechargeCardList)) {
                logger.info("用户的充值卡不为空");
                shopUserRechargeCardDTO = userRechargeCardList.get(0);
                userRechargeId = shopUserRechargeCardDTO.getId();
            }
        }
        //断定用户没有选择任何的充值卡
        else{
            logger.error("订单号={}，根据主键查询用户与充值卡关系信息为空", orderId);
            shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
            shopUserRechargeCardDTO.setProductDiscount(1f);
            shopUserRechargeCardDTO.setTimeDiscount(1f);
            shopUserRechargeCardDTO.setPeriodDiscount(1f);
        }

        logger.info("订单号={}，充值卡主键为，{}", orderId, userRechargeId);
        boolean updateFlag = false;
        //遍历用户的产品，更新产品的初始金额
        List<ShopUserProductRelationDTO> shopUserProductRelationDTOS = alreadyOrderDTO.getShopUserProductRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(shopUserProductRelationDTOS)) {
            logger.info("订单号={}，更新用户产品信息", orderId);
            for (ShopUserProductRelationDTO userProductRelationDTO : shopUserProductRelationDTOS) {
                if (null != userProductRelationDTO && null != userProductRelationDTO.getPurchasePrice() && null != userProductRelationDTO.getInitTimes()) {
                    logger.info("订单号={}，对应产品折扣价格信息为，{}", orderId, shopUserRechargeCardDTO);
                    //折扣价格，也就是单个产品的价格
                    BigDecimal discountPrice = userProductRelationDTO.getPurchasePrice().multiply(new BigDecimal(shopUserRechargeCardDTO.getProductDiscount()));
                    BigDecimal initAmount = discountPrice.multiply(new BigDecimal(userProductRelationDTO.getInitTimes()));
                    userProductRelationDTO.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                    userProductRelationDTO.setInitAmount(initAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
                    userProductRelationDTO.setDiscount(shopUserRechargeCardDTO.getProductDiscount());
                    updateFlag = true;
                }
            }
        }

        //遍历用户的次卡和疗程卡，更新次卡的初始金额
        List<ShopUserProjectRelationDTO> shopUserProjectRelationDTOS = alreadyOrderDTO.getShopUserProjectRelationDTOS();
        if (CommonUtils.objectIsNotEmpty(shopUserProjectRelationDTOS)) {
            logger.info("订单号={}，更新用户单次或疗程卡信息", orderId);
            for (ShopUserProjectRelationDTO userProjectRelationDTO : shopUserProjectRelationDTOS) {
                logger.info("订单号={}，对应单次或疗程卡折扣价格信息为，{}", orderId, shopUserRechargeCardDTO);
                if (null != userProjectRelationDTO && null != userProjectRelationDTO.getSysShopProjectPurchasePrice() && null != userProjectRelationDTO.getSysShopProjectInitTimes()) {
                    //如果是次卡的话
                    if (GoodsTypeEnum.TIME_CARD.getCode().equals(userProjectRelationDTO.getUseStyle())) {
                        //折扣价格，也是单个价格
                        BigDecimal discountPrice = userProjectRelationDTO.getSysShopProjectPurchasePrice().multiply(new BigDecimal(shopUserRechargeCardDTO.getTimeDiscount()));
                        BigDecimal initAmount = discountPrice.multiply(new BigDecimal(userProjectRelationDTO.getSysShopProjectInitTimes()));
                        userProjectRelationDTO.setSysShopProjectInitAmount(initAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
                        userProjectRelationDTO.setDiscount(shopUserRechargeCardDTO.getTimeDiscount());
                        userProjectRelationDTO.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                        updateFlag = true;
                    } else {
                        BigDecimal discountPrice = userProjectRelationDTO.getSysShopProjectPurchasePrice().multiply(new BigDecimal(shopUserRechargeCardDTO.getPeriodDiscount()));
                        BigDecimal initAmount = discountPrice.multiply(new BigDecimal(userProjectRelationDTO.getSysShopProjectInitTimes()));
                        userProjectRelationDTO.setSysShopProjectInitAmount(initAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
                        userProjectRelationDTO.setDiscount(shopUserRechargeCardDTO.getPeriodDiscount());
                        userProjectRelationDTO.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                        updateFlag = true;
                    }
                }
            }
        }
        List<ShopUserProjectGroupRelRelationDTO> projectGroupRelRelationDTOS = alreadyOrderDTO.getProjectGroupRelRelationDTOS();
        if(CommonUtils.objectIsNotEmpty(projectGroupRelRelationDTOS)){
            for(ShopUserProjectGroupRelRelationDTO dto:projectGroupRelRelationDTOS){
                dto.setDiscountPrice(dto.getShopGroupPuchasePrice());
            }
        }

        Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
        Update update = new Update();
        if (null != shopUserOrderDTO.getShopUserRechargeCardDTO()) {
            update.set("shopUserRechargeCardDTO", shopUserOrderDTO.getShopUserRechargeCardDTO());
        }
        if (updateFlag) {
            update.set("shopUserProjectRelationDTOS", alreadyOrderDTO.getShopUserProjectRelationDTOS());
            update.set("shopUserProductRelationDTOS", alreadyOrderDTO.getShopUserProductRelationDTOS());
        }
        if(CommonUtils.objectIsNotEmpty(projectGroupRelRelationDTOS)){
            update.set("projectGroupRelRelationDTOS", projectGroupRelRelationDTOS);
        }
        update.set("updateDate",new Date());
        mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
    }

    /**
     * 更新订单的项目
     *
     * @param shopUserOrderDTO
     * @param responseDTO
     * @param operation
     * @param alreadyOrderDTO
     */
    private ResponseDTO updateProjectGroupInfo(ShopUserOrderDTO shopUserOrderDTO, ResponseDTO responseDTO, String operation, ShopUserOrderDTO alreadyOrderDTO) {
        logger.info("获取已存在的套卡信息");
        List<ShopUserProjectGroupRelRelationDTO> alreadyProjectGroupRelRelationDTOS = alreadyOrderDTO.getProjectGroupRelRelationDTOS();
        if (CommonUtils.objectIsEmpty(alreadyProjectGroupRelRelationDTOS)) {
            alreadyProjectGroupRelRelationDTOS = new ArrayList<>();
        }
        int updateFlag = alreadyProjectGroupRelRelationDTOS.size();
        ShopUserProjectGroupRelRelationDTO newProjectGroupRelationDTOS = shopUserOrderDTO.getProjectGroupRelRelationDTOS().get(0);
        if (CommonUtils.objectIsEmpty(newProjectGroupRelationDTOS)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("传入的产品信息为空");
            return responseDTO;
        }
        List<ShopUserProjectGroupRelRelationDTO> helperList = new ArrayList<>();
        Iterator<ShopUserProjectGroupRelRelationDTO> alreadyIterator = alreadyProjectGroupRelRelationDTOS.iterator();
        if (!alreadyIterator.hasNext()) {
            alreadyProjectGroupRelRelationDTOS.add(newProjectGroupRelationDTOS);
        } else {
            while (alreadyIterator.hasNext()) {
                ShopUserProjectGroupRelRelationDTO alreadyRelation = alreadyIterator.next();
                //如果是原订单中有记录则将要处理的新数据缓存到helperList中
                if (newProjectGroupRelationDTOS.getShopProjectGroupId().equals(alreadyRelation.getShopProjectGroupId())) {
                    helperList.add(newProjectGroupRelationDTOS);
                    if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                        alreadyIterator.remove();
                    }
                }
            }
            //原定单中没有,并且是添加操作
            if (helperList.size() == 0 && CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyProjectGroupRelRelationDTOS.add(newProjectGroupRelationDTOS);
            }
        }

        //更新操作
        if (alreadyProjectGroupRelRelationDTOS.size() != updateFlag) {
            Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("projectGroupRelRelationDTOS", alreadyProjectGroupRelRelationDTOS);
            mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
            responseDTO.setResponseData(StatusConstant.SUCCESS);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        return responseDTO;
    }

    /**
     * 更新订单的产品信息
     *
     * @param shopUserOrderDTO
     * @param responseDTO
     * @param operation
     * @param alreadyOrderDTO
     * @return
     */
    private ResponseDTO updateProductInfo(ShopUserOrderDTO shopUserOrderDTO, ResponseDTO responseDTO, String operation, ShopUserOrderDTO alreadyOrderDTO) {
        logger.info("获取已存在的产品信息");
        List<ShopUserProductRelationDTO> alreadyProductRelationDTOS = alreadyOrderDTO.getShopUserProductRelationDTOS();
        if (CommonUtils.objectIsEmpty(alreadyProductRelationDTOS)) {
            alreadyProductRelationDTOS = new ArrayList<>();
        }
        int updateFlag = alreadyProductRelationDTOS.size();
        ShopUserProductRelationDTO newUserProductRelationDTO = shopUserOrderDTO.getShopUserProductRelationDTOS().get(0);
        if (CommonUtils.objectIsEmpty(newUserProductRelationDTO)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("传入的产品信息为空");
            return responseDTO;
        }
        List<ShopUserProductRelationDTO> helperList = new ArrayList<>();
        Iterator<ShopUserProductRelationDTO> alreadyIterator = alreadyProductRelationDTOS.iterator();
        if (!alreadyIterator.hasNext()) {
            alreadyProductRelationDTOS.add(newUserProductRelationDTO);
        } else {
            while (alreadyIterator.hasNext()) {
                ShopUserProductRelationDTO alreadyRelation = alreadyIterator.next();
                //如果是原订单中有记录则将要处理的新数据缓存到helperList中
                if (newUserProductRelationDTO.getShopProductId().equals(alreadyRelation.getShopProductId())) {
                    helperList.add(newUserProductRelationDTO);
                    if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                        alreadyIterator.remove();
                    }
                }
            }
            //原定单中没有,并且是添加操作
            if (helperList.size() == 0 && CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyProductRelationDTOS.add(newUserProductRelationDTO);
            }
        }

        //更新操作
        if (alreadyProductRelationDTOS.size() != updateFlag) {
            Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("shopUserProductRelationDTOS", alreadyProductRelationDTOS);
            mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
            responseDTO.setResponseData(StatusConstant.SUCCESS);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        return responseDTO;
    }

    /**
     * 更新项目信息
     *
     * @param shopUserOrderDTO
     * @param responseDTO
     * @param operation
     * @param alreadyOrderDTO
     */
    private ResponseDTO updateProjectInfo(ShopUserOrderDTO shopUserOrderDTO, ResponseDTO responseDTO, String operation, ShopUserOrderDTO alreadyOrderDTO) {
        //获取已经保存的项目信息
        List<ShopUserProjectRelationDTO> alreadyExistProjectRelationDTOS = alreadyOrderDTO.getShopUserProjectRelationDTOS();
        logger.info("查询项目信息为，{}", "shopUserProjectRelationDTOS = [" + alreadyExistProjectRelationDTOS + "]");
        if (null == alreadyExistProjectRelationDTOS) {
            alreadyExistProjectRelationDTOS = new ArrayList<>();
        }
        int updateFlag = alreadyExistProjectRelationDTOS.size();
        //获取需要添加的项目信息
        ShopUserProjectRelationDTO newUserProjectRelationDTO = shopUserOrderDTO.getShopUserProjectRelationDTOS().get(0);
        if (CommonUtils.objectIsEmpty(newUserProjectRelationDTO)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("传入的项目信息为空");
            return responseDTO;
        }

        List<ShopUserProjectRelationDTO> helperList = new ArrayList<>();
        Iterator<ShopUserProjectRelationDTO> alreadyIterator = alreadyExistProjectRelationDTOS.iterator();
        //如果原订单中为空，直接添加
        if (!alreadyIterator.hasNext()) {
            alreadyExistProjectRelationDTOS.add(newUserProjectRelationDTO);
        } else {
            while (alreadyIterator.hasNext()) {
                ShopUserProjectRelationDTO alreadyRelation = alreadyIterator.next();
                //如果是原订单中有记录则将要处理的新数据缓存到helperList中
                if (newUserProjectRelationDTO.getSysShopProjectId().equals(alreadyRelation.getSysShopProjectId())) {
                    helperList.add(newUserProjectRelationDTO);
                    if (CommonCodeEnum.DELETE.getCode().equals(operation)) {
                        alreadyIterator.remove();
                    }
                }
            }
            //原定单中没有,并且是添加操作
            if (helperList.size() == 0 && CommonCodeEnum.ADD.getCode().equals(operation)) {
                alreadyExistProjectRelationDTOS.add(newUserProjectRelationDTO);
            }
        }

        //更新操作
        if (alreadyExistProjectRelationDTOS.size() != updateFlag) {
            Query updateQuery = new Query().addCriteria(Criteria.where("orderId").is(shopUserOrderDTO.getOrderId()));
            Update update = new Update();
            update.set("shopUserProjectRelationDTOS", alreadyExistProjectRelationDTOS);
            mongoTemplate.upsert(updateQuery, update, "shopUserOrderDTO");
            responseDTO.setResponseData(StatusConstant.SUCCESS);
            responseDTO.setResult(StatusConstant.SUCCESS);
        }
        return responseDTO;
    }
}
