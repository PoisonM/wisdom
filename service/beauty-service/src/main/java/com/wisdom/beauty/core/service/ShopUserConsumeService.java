package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;

import java.util.List;

/**
 * FileName: ShopUserConsumService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 用户消费相关
 */
public interface ShopUserConsumeService {

    /**
     * 用户充值操作
     */
    ResponseDTO userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO);

    /**
     * 用户划疗程卡
     */
    int consumeCourseCard(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo);

    /**
     * 用户划套卡下的子卡操作
     *
     * @param shopUserConsumeDTOS
     * @param clerkInfo
     * @return
     */
    String consumesDaughterCard(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo);

    /**
     * 用户领取产品
     *
     * @param shopUserConsumeDTOS
     * @param clerkInfo
     * @return
     */
    ResponseDTO consumesUserProduct(ShopUserConsumeDTO shopUserConsumeDTOS, SysClerkDTO clerkInfo);

    /**
     * 充值卡充值操作
     *
     * @param transactionId
     * @param imageUrl
     * @return
     */
    ResponseDTO rechargeRechargeCard(String transactionId, String imageUrl);
}
