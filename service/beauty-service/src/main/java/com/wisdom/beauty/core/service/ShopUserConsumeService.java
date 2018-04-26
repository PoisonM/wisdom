package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserOrderDTO;
import com.wisdom.beauty.api.extDto.ShopUserPayDTO;
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
     * 用户消费充值卡信息
     *
     * @param userRechargeCardDTO
     * @return
     */
    int userConsumeRechargeCard(ShopUserRechargeCardDTO userRechargeCardDTO);

    /**
     * 用户充值操作
     */
    int userRechargeOperation(ShopUserOrderDTO shopUserOrderDTO, ShopUserPayDTO shopUserPayDTO, SysClerkDTO clerkInfo);

    /**
     * 用户划疗程卡
     */
    int consumeCourseCard(List<ShopUserConsumeDTO> shopUserConsumeDTOS, SysClerkDTO clerkInfo);
}
