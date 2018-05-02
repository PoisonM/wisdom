package com.wisdom.user.service.impl;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.user.SysClerkCriteria;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.service.ClerkInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("clerkInfoService")
public class ClerkInfoServiceImpl implements ClerkInfoService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysClerkMapper sysClerkMapper;

    /**
     * 获取店员列表
     *
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
        if (StringUtils.isNotBlank(SysClerk.getId())) {
            criteria.andIdEqualTo(SysClerk.getId());
        }
        List<SysClerkDTO> SysClerks = sysClerkMapper.selectByCriteria(SysClerkCriteria);

        return SysClerks;
    }

    @Override
    public List<SysClerkDTO> getClerkInfoList(PageParamVoDTO<SysClerkDTO> pageParamVoDTO) {
        SysClerkDTO sysClerkDTO = pageParamVoDTO.getRequestData();

        SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
        SysClerkCriteria.Criteria criteria = sysClerkCriteria.createCriteria();

        if (null == sysClerkDTO) {
            logger.error("获取某个店的店员列表传入参数为空{}", sysClerkDTO);
            return null;
        }

        if (StringUtils.isNotBlank(sysClerkDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
        }
        if (StringUtils.isNotBlank(sysClerkDTO.getSysBossId())) {
            criteria.andSysBossIdEqualTo(sysClerkDTO.getSysBossId());
        }
        // 排序
        //sysClerkCriteria.setOrderByClause("create_date");
        // 分页
        if (pageParamVoDTO.getPageSize() != 0) {
            sysClerkCriteria.setLimitStart(pageParamVoDTO.getPageNo());
            sysClerkCriteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        //时间
        if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime()) && StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
            Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
            Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
            // TODO: 2018/4/25  
        }
        List<SysClerkDTO> SysClerks = sysClerkMapper.selectByCriteria(sysClerkCriteria);

        return SysClerks;
    }

    @Override
    public int  updateSysClerk(SysClerkDTO sysClerkDTO) {
       return sysClerkMapper.updateByPrimaryKey(sysClerkDTO);
    }
}
