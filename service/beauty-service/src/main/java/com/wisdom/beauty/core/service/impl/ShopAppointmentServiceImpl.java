package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ExtShopAppointServiceMapper;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * FileName: AppointmentServiceImpl
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Service("appointmentService")
public class ShopAppointmentServiceImpl implements ShopAppointmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private ShopAppointServiceMapper shopAppointServiceMapper;

    @Autowired
    private ExtShopAppointServiceMapper extShopAppointServiceMapper;

    /**
     * 根据时间查询查询某个店的有预约号源的美容师列表
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public List<ShopAppointServiceDTO> getShopAppointClerkInfoByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if(null == extShopAppointServiceDTO || null == extShopAppointServiceDTO.getSearchStartTime() || null == extShopAppointServiceDTO.getSearchEndTime()){
            logger.info("根据时间查询查询某个店的有预约号源的美容师列表,查询参数为空{}",extShopAppointServiceDTO);
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        criteria.andCreateDateBetween(extShopAppointServiceDTO.getSearchStartTime(),extShopAppointServiceDTO.getSearchEndTime());
        List<ShopAppointServiceDTO> appointServiceDTOS = extShopAppointServiceMapper.selectShopAppointClerkInfoByCriteria(shopAppointServiceCriteria);
        return appointServiceDTOS;
    }

    /**
     * 根据时间查询某个店下的某个美容师的预约列表
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public List<ShopAppointServiceDTO> getShopClerkAppointListByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if(null == extShopAppointServiceDTO || null == extShopAppointServiceDTO.getSearchStartTime() || null == extShopAppointServiceDTO.getSearchEndTime()){
            logger.info("根据时间查询某个店下的某个美容师的预约列表,查询参数为空{}",extShopAppointServiceDTO);
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        if(StringUtils.isNotBlank(extShopAppointServiceDTO.getSysShopId())){
            criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        }
        if(StringUtils.isNotBlank(extShopAppointServiceDTO.getSysClerkId())){
            criteria.andSysClerkIdEqualTo(extShopAppointServiceDTO.getSysClerkId());
        }
        if(null != extShopAppointServiceDTO.getSearchStartTime() && null != extShopAppointServiceDTO.getSearchEndTime()){
            criteria.andCreateDateBetween(extShopAppointServiceDTO.getSearchStartTime(),extShopAppointServiceDTO.getSearchEndTime());
        }

        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);
        return appointServiceDTOS;
    }

    /**
     * 更新预约信息
     *
     * @param shopAppointServiceDTO
     * @return
     */
    @Override
    public int updateAppointmentInfo(ShopAppointServiceDTO shopAppointServiceDTO) {
        if (shopAppointServiceDTO == null || StringUtils.isBlank(shopAppointServiceDTO.getId())) {
            return 0;
        }
        return shopAppointServiceMapper.updateByPrimaryKeySelective(shopAppointServiceDTO);
    }

    @Override
    public ShopAppointServiceDTO getShopAppointService(String userId) {

            logger.info("getShopAppointServiceDTO传入参数userId={}",userId);
            if(StringUtils.isBlank(userId)){
                return  null;
            }
            ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
            ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
            criteria.andSysUserIdEqualTo(userId);
            shopAppointServiceCriteria.setOrderByClause("create_date");
        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);
        ShopAppointServiceDTO shopAppointServiceDTO = null;
            if(CollectionUtils.isNotEmpty(appointServiceDTOS)){
                shopAppointServiceDTO=appointServiceDTOS.get(0);
            }
            return shopAppointServiceDTO;
        }

    /**
     *  查询某个美容院某个时间预约个数
     *  @param  sysShopId 店铺id
     *  @param  sysClerkId 店员id
     *  @param  appointStartTimeS 预约开始时间（范围起始值）
     *  @param  appointStartTimeE  appointStartTimeE（范围结束值）
     *  @return  resultCode 代表执行成功或者失败 fail和success
     *  @return  resultMessage 成功的话代表预约个数，失败的话代表错误信息
     *  @autur zhangchao
     * */
    @Override
    public HashMap<String,String> findNumForShopByTimeService(String sysShopId, String sysClerkId,String appointStartTimeS, String appointStartTimeE){

        HashMap<String,String> shopAppointMap = new HashMap<>();
        shopAppointMap.put("sysShopId",sysShopId);
        shopAppointMap.put("appointStartTimeS",appointStartTimeS);
        shopAppointMap.put("appointStartTimeE",appointStartTimeE);
        shopAppointMap.put("sysClerkId",sysClerkId);

        HashMap<String,String> resultMap = new HashMap<>();
        Integer shopAppointNum;

        try {
             shopAppointNum = extShopAppointServiceMapper.findNumForShopAppointByTime(shopAppointMap);
        }catch(Exception e) {
            logger.info(e.getMessage());
            resultMap.put("resultCode", "fail");
            resultMap.put("resultMessage", "查询存在问题，请联系支撑人员");
            return resultMap;
        }
        //判断是具体到店员还是店铺
        if(sysClerkId!=null&&sysClerkId!=""){
            logger.info("店铺"+sysShopId+"下面店员"+sysClerkId+"在"+appointStartTimeS+"到"+appointStartTimeE+"的预约数量是"+shopAppointNum);
        }else{
            logger.info("店铺"+sysShopId+"在"+appointStartTimeS+"到"+appointStartTimeE+"的预约数量是"+shopAppointNum);
        }

        String resultMessage  = String.valueOf(shopAppointNum);
        resultMap.put("resultCode","success");
        resultMap.put("resultMessage",resultMessage);
        return resultMap;
    }


    /**
     *  查询某个美容院某个时间预约个数
     *  @param  sysShopId 店铺id
     *  @param  sysClerkId 店员id
     *  @param  appointStartTimeS 预约开始时间（范围起始值）
     *  @param  appointStartTimeE  appointStartTimeE（范围结束值）
     *  @return  shopAppointUserInfoList 预约用户列表
     *  @autuor zhangchao
     * */
    @Override
    public List<ShopAppointServiceDTO> findUserInfoForShopByTimeService(String sysShopId, String sysClerkId,String appointStartTimeS, String appointStartTimeE){

        HashMap<String,String> shopAppointMap = new HashMap<>();
        shopAppointMap.put("sysShopId",sysShopId);
        shopAppointMap.put("appointStartTimeS",appointStartTimeS);
        shopAppointMap.put("appointStartTimeE",appointStartTimeE);
        shopAppointMap.put("sysClerkId",sysClerkId);

        List<ShopAppointServiceDTO> shopAppointUserInfoList = new ArrayList<>();
        try {
            shopAppointUserInfoList= extShopAppointServiceMapper.findUserInfoForShopAppointByTime(shopAppointMap);
        }catch(Exception e) {
            logger.info(e.getMessage());
        }

        logger.info("店铺"+sysShopId+"下面店员"+sysClerkId+"在"+appointStartTimeS+"到"+appointStartTimeE+"的预约用户列表是"+shopAppointUserInfoList);
        return shopAppointUserInfoList;
    }

}
