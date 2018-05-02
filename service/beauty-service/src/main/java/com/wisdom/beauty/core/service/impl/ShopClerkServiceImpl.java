package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.dto.SysClerkFlowAccountDTO;
import com.wisdom.beauty.core.mapper.SysClerkFlowAccountMapper;
import com.wisdom.beauty.core.service.ShopClerkService;
import com.wisdom.common.util.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * FileName: workService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 工作相关
 */
@Service("shopClerkService")
public class ShopClerkServiceImpl implements ShopClerkService {

    @Autowired
    public SysClerkFlowAccountMapper sysClerkFlowAccountMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 记录店员的流水信息
     *
     * @param dto
     */
    @Override
    public void saveSysClerkFlowAccountInfo(ShopUserConsumeRecordDTO dto) {
        logger.info("记录店员的流水信息传入参数={}", "dto = [" + dto + "]");
        try {
            SysClerkFlowAccountDTO clerkFlowAccountDTO = new SysClerkFlowAccountDTO();
            clerkFlowAccountDTO.setCreateDate(new Date());
            clerkFlowAccountDTO.setDetail(dto.getDetail());
            clerkFlowAccountDTO.setFlowAmount(dto.getPrice());
            clerkFlowAccountDTO.setOperDate(new Date());
            clerkFlowAccountDTO.setId(IdGen.uuid());
            clerkFlowAccountDTO.setSignUrl(dto.getSignUrl());
            clerkFlowAccountDTO.setOperInfo(dto.getDetail());
            clerkFlowAccountDTO.setSysBossId(dto.getSysBossId());
            clerkFlowAccountDTO.setSysClerkId(dto.getSysClerkId());
            clerkFlowAccountDTO.setSysShopId(dto.getSysShopId());
            clerkFlowAccountDTO.setSysShopName(dto.getSysShopName());
            clerkFlowAccountDTO.setSysUserId(dto.getSysUserId());
            clerkFlowAccountDTO.setShopUserConsumeRecordId(dto.getId());
            clerkFlowAccountDTO.setType(dto.getConsumeType());
//            saveSysClerkFlowAccountInfo(clerkFlowAccountDTO);
        } catch (Exception e) {
            logger.error("保存店员的流水信息失败，失败信息为{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    /**
     * 保存店员的流水信息
     *
     * @param sysClerkFlowAccountDTO
     * @return
     */
    @Override
    public int saveSysClerkFlowAccountInfo(SysClerkFlowAccountDTO sysClerkFlowAccountDTO) {
        logger.info("保存店员的流水信息传入参数={}", "sysClerkFlowAccountDTO = [" + sysClerkFlowAccountDTO + "]");
        return sysClerkFlowAccountMapper.insert(sysClerkFlowAccountDTO);
    }
}
