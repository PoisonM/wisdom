package com.wisdom.user.service.impl;

import com.wisdom.common.dto.customer.SysUserClerkDTO;
import com.wisdom.common.dto.customer.SysUserClerkCriteria;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.mapper.SysUserClerkMapper;
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
    private SysUserClerkMapper sysUserClerkMapper;

    /**
     * 获取店员列表
     * @param sysUserClerk
     * @return
     */
    @Override
    public List<SysUserClerkDTO> getClerkInfo(SysUserClerkDTO sysUserClerk) {
        SysUserClerkCriteria sysUserClerkCriteria = new SysUserClerkCriteria();
        SysUserClerkCriteria.Criteria criteria = sysUserClerkCriteria.createCriteria();

        if(null == sysUserClerk){
            logger.error("获取某个店的店员列表传入参数为空{}", sysUserClerk);
            return null;
        }

        if(StringUtils.isNotBlank(sysUserClerk.getSysShopId())){
            criteria.andSysShopIdEqualTo(sysUserClerk.getSysShopId());
        }

        List<SysUserClerkDTO> sysUserClerks = sysUserClerkMapper.selectByCriteria(sysUserClerkCriteria);

        return sysUserClerks;
    }
}
