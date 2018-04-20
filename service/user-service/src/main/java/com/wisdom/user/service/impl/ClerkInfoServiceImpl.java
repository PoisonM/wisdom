package com.wisdom.user.service.impl;

import com.wisdom.common.dto.user.SysClerkCriteria;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.service.ClerkInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clerkInfoService")
public class ClerkInfoServiceImpl implements ClerkInfoService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysClerkMapper sysClerkMapper;

    /**
     * 获取店员列表
     * @param SysClerk
     * @return
     */
    @Override
    public List<SysClerkDTO> getClerkInfo(SysClerkDTO SysClerk) {
        SysClerkCriteria SysClerkCriteria = new SysClerkCriteria();
        SysClerkCriteria.Criteria criteria = SysClerkCriteria.createCriteria();

        if (null == SysClerk) {
            logger.error("获取某个店的店员列表传入参数为空{}", SysClerk);
            return null;
        }

        if (StringUtils.isNotBlank(SysClerk.getSysShopId())) {
            criteria.andSysShopIdEqualTo(SysClerk.getSysShopId());
        }

        List<SysClerkDTO> SysClerks = sysClerkMapper.selectByCriteria(SysClerkCriteria);

        return SysClerks;
    }
}
