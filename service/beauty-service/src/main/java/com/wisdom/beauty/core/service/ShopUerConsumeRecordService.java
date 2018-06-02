package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;


/**
 * ClassName: ShopUerConsumeRecordService
 *
 * @Author： huan
 * @Description: 消费记录下相关功能
 * @Date:Created in 2018/4/8 18:14
 * @since JDK 1.8
 */
public interface ShopUerConsumeRecordService {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据条件查询某个美容院某个用户的账户记录，包括收银记录和划卡记录
     * @Date:2018/4/3 18:57
     */
    List<UserConsumeRecordResponseDTO> getShopCustomerConsumeRecordList(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 根据流水号查询用户的消费记录
    *@Date:2018/4/9 19:06
    */
    UserConsumeRecordResponseDTO getShopCustomerConsumeRecord(String consumeFlowNo);

    /**
     * 根据条件查询消费记录
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    List<ShopUserConsumeRecordDTO> getShopCustomerConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO);

    /**
     * 保存用户消费或充值记录
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    int saveCustomerConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO);


    /**
     * 更新用户的消费记录
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    int updateConsumeRecord(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 根据消费类型和多个sysClerkId查询消费记录集合
    *@Date:2018/4/26 9:51
    */
    List<UserConsumeRecordResponseDTO> getShopCustomerConsumeRecordList(String consumeType,List<String> sysClerkIds);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 根据疗程卡和套卡Id获取疗程的划卡记录
    *@Date:2018/6/1 18:10
    */
    List<UserConsumeRecordResponseDTO> getTreatmentAndGroupCardRecord(PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO);
}
