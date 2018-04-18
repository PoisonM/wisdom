package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.dto.SysUserAccountDTO;
import com.wisdom.beauty.core.service.*;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: ShopUserConsumService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 用户消费相关
 */
@Service("shopUserConsumeService")
public class ShopUserConsumeServiceImpl implements ShopUserConsumeService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ShopClerkService shopClerkService;

    @Resource
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Resource
    private SysUserAccountService sysUserAccountService;

    @Resource
    private ShopCardService shopCardService;

    /**
     * 用户消费充值卡信息
     *
     * @param shopUserConsumeRecordDTO
     * @return
     */
    @Override
    public int userConsumeRechargeCard(ShopUserConsumeRecordDTO shopUserConsumeRecordDTO) {
        if (shopUserConsumeRecordDTO == null || StringUtils.isBlank(shopUserConsumeRecordDTO.getFlowId())) {
            logger.error("用户消费充值卡信息传入参数为空，{}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");
            throw new RuntimeException();
        }
        //更新用户充值卡记录信息
        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        shopUserConsumeRecordDTO.setId(shopUserConsumeRecordDTO.getFlowId());
        List<ShopUserRechargeCardDTO> userRechargeCardList = shopCardService.getUserRechargeCardList(shopUserRechargeCardDTO);
        if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
            logger.error("用户消费充值卡信息,根据主键插叙用户充值卡为空，{}", "userRechargeCardList = [" + userRechargeCardList + "]");
            throw new RuntimeException();
        }
        shopUserRechargeCardDTO = userRechargeCardList.get(0);
        BigDecimal surplus = shopUserRechargeCardDTO.getSurplusAmount().subtract(shopUserConsumeRecordDTO.getPrice());
        shopUserRechargeCardDTO.setSurplusAmount(surplus);
        shopCardService.updateUserRechargeCard(shopUserRechargeCardDTO);
        //生成消费记录
        shopUerConsumeRecordService.saveCustomerConsumeRecord(shopUserConsumeRecordDTO);
        //更新用户的账户信息
        SysUserAccountDTO sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(shopUserConsumeRecordDTO.getSysUserId());
        if (sysUserAccountDTO == null) {
            logger.error("查询用户的账户信息为空，{}", "shopUserConsumeRecordDTO = [" + shopUserConsumeRecordDTO + "]");
        }
        sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(shopUserConsumeRecordDTO.getPrice()));
        try {
            sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
        } catch (Exception e) {
            logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
        //更新店员的账户信息
        shopClerkService.saveSysClerkFlowAccountInfo(shopUserConsumeRecordDTO);
        return 0;
    }


}
