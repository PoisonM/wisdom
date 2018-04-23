package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordCriteria;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.core.mapper.ShopUserConsumeRecordMapper;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName: ShopUerConsumeRecordServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 18:23
 * @since JDK 1.8
 */
@Service("shopUerConsumeRecordService")
public class ShopUerConsumeRecordServiceImpl implements ShopUerConsumeRecordService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserConsumeRecordMapper shopUserConsumeRecordMapper;

    @Override
    public List<UserConsumeRecordResponseDTO> getShopCustomerConsumeRecordList(PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO) {
        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = pageParamVoDTO.getRequestData();
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null) {
            throw new ServiceException("从redis中获取sysClerkDTO对象为空");
        }
        logger.info("getShopCustomerConsumeRecordList方法传入的参数,SysShopId={},ShopUserId={},consumeType={}", sysClerkDTO.getSysShopId(), shopUserConsumeRecordDTO.getSysUserId(), shopUserConsumeRecordDTO.getConsumeType());
        if (StringUtils.isBlank(sysClerkDTO.getSysShopId()) ||
                StringUtils.isBlank(shopUserConsumeRecordDTO.getConsumeType())) {
            throw new ServiceException("getShopCustomerConsumeRecordList方法传入的参数为空");
        }
        ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
        ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
        // 排序
        criteria.setOrderByClause("create_date");
        // 分页
        if(pageParamVoDTO.getPageSize()!=0) {
            criteria.setLimitStart(pageParamVoDTO.getPageNo());
            criteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime()) && StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
            //Date currentDate = DateUtils.parseDate(pageParamVoDTO.getStartTime());
            logger.info("传入的开始时间，结束时间是,StartTime={}，EndTime={}",pageParamVoDTO.getStartTime(),pageParamVoDTO.getEndTime());
            Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
            Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
            c.andCreateDateBetween(startTime, endTime);
        }
        //设置查询条件
        c.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
        c.andConsumeTypeEqualTo(shopUserConsumeRecordDTO.getConsumeType());

        String sysClerkId = shopUserConsumeRecordDTO.getSysClerkId();
        //根据是否有店员id，设置查询条件，如果费类型不是划卡则需要通过goodType来区分,如果sysClerkId不为空则需要根据goodType来区分
        if (StringUtils.isNotBlank(sysClerkId)) {
            logger.info("此时加入店员消费记录查询条件,店员sysClerkId={}", sysClerkId);
            c.andSysClerkIdEqualTo(sysClerkId);
            if (!ConsumeTypeEnum.PUNCH_CARD.getCode().equals(shopUserConsumeRecordDTO.getConsumeType())) {
                if (GoodsTypeEnum.RECHARGE_CARD.getCode().equals(shopUserConsumeRecordDTO.getGoodsType()) ||
                        GoodsTypeEnum.PRODUCT.getCode().equals(shopUserConsumeRecordDTO.getGoodsType())) {
                    //如果是充值卡或者是产品领取
                    c.andGoodsTypeEqualTo(shopUserConsumeRecordDTO.getGoodsType());
                } else {
                    //如果不是充值卡，则查询非充值卡的type
                    c.andGoodsTypeNotEqualTo(GoodsTypeEnum.RECHARGE_CARD.getCode());
                }
            }
        } else {
            if (StringUtils.isNotBlank(shopUserConsumeRecordDTO.getSysUserId())) {
                c.andSysUserIdEqualTo(shopUserConsumeRecordDTO.getSysUserId());
            }
        }
        List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);
        Map<String, UserConsumeRecordResponseDTO> map = new HashMap<>(16);
        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = null;
        for (ShopUserConsumeRecordDTO shopUserConsumeRecord : list) {
            userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
            if (map.get(shopUserConsumeRecord.getFlowNo()) == null) {
                userConsumeRecordResponseDTO.setSumAmount(shopUserConsumeRecord.getPrice());
                userConsumeRecordResponseDTO.setCreateDate(shopUserConsumeRecord.getCreateDate());
                userConsumeRecordResponseDTO.setFlowNo(shopUserConsumeRecord.getFlowNo());
                if (ConsumeTypeEnum.RECHARGE.getCode().equals(shopUserConsumeRecord.getConsumeType())) {
                    userConsumeRecordResponseDTO.setTitle(shopUserConsumeRecord.getConsumeType());
                } else {
                    userConsumeRecordResponseDTO.setTitle(shopUserConsumeRecord.getFlowName());
                }
                map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseDTO);
            } else {
                UserConsumeRecordResponseDTO userConsumeRecordResponseMap = map.get(shopUserConsumeRecord.getFlowNo());
                BigDecimal prices = shopUserConsumeRecord.getPrice().add(userConsumeRecordResponseMap.getSumAmount());
                userConsumeRecordResponseMap.setSumAmount(prices);
                map.put(shopUserConsumeRecord.getFlowNo(), userConsumeRecordResponseMap);
            }
        }
        logger.info("getShopCustomerConsumeRecordList执行完成");
        List values = Arrays.asList(map.values().toArray());
        return values;
    }

    @Override
    public UserConsumeRecordResponseDTO getShopCustomerConsumeRecord(String consumeFlowNo) {
        logger.info("getShopCustomerConsumeRecord方法传入的参数,consumeFlowNo={}}", consumeFlowNo);
        if (StringUtils.isBlank(consumeFlowNo)) {
            throw new ServiceException("getShopCustomerConsumeRecord方法传入的参数为空");
        }
        ShopUserConsumeRecordCriteria criteria = new ShopUserConsumeRecordCriteria();
        ShopUserConsumeRecordCriteria.Criteria c = criteria.createCriteria();
        c.andFlowNoEqualTo(consumeFlowNo);

        List<ShopUserConsumeRecordDTO> list = shopUserConsumeRecordMapper.selectByCriteria(criteria);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("getShopCustomerConsumeRecord方法获取list集合为空");
            return null;
        }
        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = new UserConsumeRecordResponseDTO();
        for (ShopUserConsumeRecordDTO userConsumeRecord : list) {
            userConsumeRecordResponseDTO.setCreateDate(userConsumeRecord.getCreateDate());
            userConsumeRecordResponseDTO.setShopUserName(userConsumeRecord.getSysUserName());
            userConsumeRecordResponseDTO.setSysShopClerkName(userConsumeRecord.getSysClerkName());
            userConsumeRecordResponseDTO.setSysShopName(userConsumeRecord.getSysShopName());
            userConsumeRecordResponseDTO.setType(userConsumeRecord.getConsumeType());
        }
        userConsumeRecordResponseDTO.setUserConsumeRecordList(list);
        return userConsumeRecordResponseDTO;
    }

    /**
     * 保存用户消费或充值记录
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    @Override
    public int saveCustomerConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {

        logger.info("保存用户消费或充值记录传入参数={}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");

        int insert = shopUserConsumeRecordMapper.insert(shopUserConsumeRecordDTO);

        return insert;
    }


}
