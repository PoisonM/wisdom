package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopCashFlowCriteria;
import com.wisdom.beauty.api.dto.ShopCashFlowDTO;
import com.wisdom.beauty.core.mapper.ShopCashFlowMapper;
import com.wisdom.beauty.core.service.CashService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * FileName: CashService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 资金流水相关
 */
@Service("cashService")
public class CashServiceImpl implements CashService {

    @Autowired
    private ShopCashFlowMapper shopCashFlowMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某笔资金流水
     *
     * @param shopCashFlowDTO
     * @return
     */
    @Override
    public ShopCashFlowDTO getShopCashFlow(ShopCashFlowDTO shopCashFlowDTO) {

        ShopCashFlowCriteria criteria = getShopCashFlowCriteria(shopCashFlowDTO);

        List<ShopCashFlowDTO> shopCashFlowDTOS = shopCashFlowMapper.selectByCriteria(criteria);
        return CommonUtils.objectIsNotEmpty(shopCashFlowDTOS) ? shopCashFlowDTOS.get(0) : null;
    }


    /**
     * 查询某笔资金流水列表
     *
     * @param shopCashFlowDTO
     * @return
     */
    @Override
    public List<ShopCashFlowDTO> getShopCashFlowList(ShopCashFlowDTO shopCashFlowDTO) {

        ShopCashFlowCriteria criteria = getShopCashFlowCriteria(shopCashFlowDTO);

        List<ShopCashFlowDTO> shopCashFlowDTOS = shopCashFlowMapper.selectByCriteria(criteria);
        return shopCashFlowDTOS;
    }

    /**
     * 拼装查询条件接口
     *
     * @param shopCashFlowDTO
     * @return
     */
    public ShopCashFlowCriteria getShopCashFlowCriteria(ShopCashFlowDTO shopCashFlowDTO) {
        if (null == shopCashFlowDTO) {
            logger.error("查询某笔资金流水传入参数为空");
            return null;
        }

        ShopCashFlowCriteria criteria = new ShopCashFlowCriteria();
        ShopCashFlowCriteria.Criteria c = criteria.createCriteria();
        //根据主键查询
        if (StringUtils.isNotBlank(shopCashFlowDTO.getId())) {
            c.andIdEqualTo(shopCashFlowDTO.getId());
        }
        if (StringUtils.isNotBlank(shopCashFlowDTO.getFlowNo())) {
            c.andFlowNoEqualTo(shopCashFlowDTO.getFlowNo());
        }
        if(StringUtils.isNotBlank(shopCashFlowDTO.getSysBossCode())){
            c.andSysBossCodeEqualTo(shopCashFlowDTO.getSysBossCode());
        }
        if(StringUtils.isNotBlank(shopCashFlowDTO.getSysShopId())){
            c.andSysShopIdEqualTo(shopCashFlowDTO.getSysShopId());
        }
        return criteria;
    }

    /**
     * 保存某笔资金流水
     *
     * @param shopCashFlowDTO
     * @return
     */
    @Override
    public int saveShopCashFlow(ShopCashFlowDTO shopCashFlowDTO) {
        return shopCashFlowMapper.insertSelective(shopCashFlowDTO);
    }

    @Override
    public List<ShopCashFlowDTO> getShopCashFlowList(PageParamVoDTO<ShopCashFlowDTO> pageParamVoDTO) {
        ShopCashFlowDTO shopCashFlowDTO=pageParamVoDTO.getRequestData();
        if(shopCashFlowDTO==null){
            return  null;
        }
        ShopCashFlowCriteria criteria = new ShopCashFlowCriteria();
        ShopCashFlowCriteria.Criteria c = criteria.createCriteria();
        if(StringUtils.isNotBlank(shopCashFlowDTO.getSysBossCode())){
            c.andSysBossCodeEqualTo(shopCashFlowDTO.getSysBossCode());
        }
        if(StringUtils.isNotBlank(shopCashFlowDTO.getSysShopId())){
            c.andSysShopIdEqualTo(shopCashFlowDTO.getSysShopId());
        }
        // 分页
        if (pageParamVoDTO.getPaging()) {
            criteria.setLimitStart(pageParamVoDTO.getPageNo());
            criteria.setPageSize(pageParamVoDTO.getPageSize());
        }
        if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
                && StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
            Date startTime = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
            Date endTime = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
            c.andCreateDateBetween(startTime, endTime);
        }
        return  shopCashFlowMapper.selectByCriteria(criteria);
    }
}
