package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.SysClerkFlowAccountDTO;
import com.wisdom.beauty.core.mapper.SysClerkFlowAccountMapper;
import com.wisdom.beauty.core.service.ShopClerkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
