package com.wisdom.user.service.impl;

import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.user.mapper.SysBossMapper;
import com.wisdom.user.service.BossInfoService;
import com.wisdom.user.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bossInfoService")
public class BossInfoServiceImpl implements BossInfoService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysBossMapper sysBossMapper;

    /**
     * 更新老板信息
     *
     * @param sysBossDTO
     * @return
     */
    @Override
    public int updateBossInfo(SysBossDTO sysBossDTO) {
        logger.info("更新老板信息传入参数={}", "sysBossDTO = [" + sysBossDTO + "]");
        int update = sysBossMapper.updateByPrimaryKeySelective(sysBossDTO);
        logger.info("更新老板信息更新结果={}", update > 0 ? "成功" : "失败");
        //更新redis中boss的信息
        String token=UserUtils.getToken();
        //JedisUtils.del(token);
        return update;
    }
}
