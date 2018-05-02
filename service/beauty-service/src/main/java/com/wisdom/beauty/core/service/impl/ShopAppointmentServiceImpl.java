package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.mapper.ExtShopAppointServiceMapper;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        if (null == extShopAppointServiceDTO) {
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
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(extShopAppointServiceDTO.getSysUserId());
        }
        if(null != extShopAppointServiceDTO.getSearchStartTime() && null != extShopAppointServiceDTO.getSearchEndTime()){
            criteria.andAppointStartTimeBetween(extShopAppointServiceDTO.getSearchStartTime(), extShopAppointServiceDTO.getSearchEndTime());
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

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据用户ID查询预约信息
     * @Date:2018/4/8 14:26
     */
    @Override
    public ShopAppointServiceDTO getShopAppointService(ShopAppointServiceDTO shopAppointServiceDTO) {

        logger.info("getShopAppointServiceDTO传入参数userId={}", shopAppointServiceDTO);
        if (null == shopAppointServiceDTO) {
            logger.debug(" 根据用户ID查询预约信息,{}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
            return null;
        }
        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopAppointServiceDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(shopAppointServiceDTO.getSysUserId());
        }

        if (StringUtils.isNotBlank(shopAppointServiceDTO.getId())) {
            criteria.andIdEqualTo(shopAppointServiceDTO.getId());
        }

        shopAppointServiceCriteria.setOrderByClause("create_date");
        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);

        if (CollectionUtils.isNotEmpty(appointServiceDTOS)) {
            shopAppointServiceDTO = appointServiceDTOS.get(0);
        }
        return shopAppointServiceDTO;
    }

    /**
     * 保存用户的预约信息
     */
    @Override
    public int saveUserShopAppointInfo(ShopAppointServiceDTO shopAppointServiceDTO) {

        logger.info("保存用户的预约信息传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");

        int insert = shopAppointServiceMapper.insertSelective(shopAppointServiceDTO);

        return insert;
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

        //返回结果map
        HashMap<String,String> resultMap = new HashMap<>();
        //预约总数
        Integer shopAppointNum;

        try {
             shopAppointNum = extShopAppointServiceMapper.findNumForShopAppointByTime(shopAppointMap);
        }catch(Exception e) {
            logger.info(e.getMessage());
            resultMap.put("resultCode", "fail");
            resultMap.put("resultMessage", "查询存在问题，请联系支撑人员");
            return resultMap;
        }
        //判断是具体到店员还是店铺仅用于打印日志区分
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
     *  @param  pageParamDTO  分页对象
     *  @return  shopAppointUserInfoList 预约用户列表
     *  @autuor zhangchao
     * */
    @Override
    public PageParamDTO<List<ShopAppointServiceDTO>> findUserInfoForShopByTimeService(PageParamDTO<ShopAppointServiceDTO> pageParamDTO){


        HashMap<String,String> shopAppointMap = new HashMap<>();
        shopAppointMap.put("sysShopId",pageParamDTO.getRequestData().getSysShopId());
        shopAppointMap.put("appointStartTimeS",pageParamDTO.getRequestData().getAppointStartTimeS());
        shopAppointMap.put("appointStartTimeE",pageParamDTO.getRequestData().getAppointStartTimeE());
        shopAppointMap.put("sysClerkId",pageParamDTO.getRequestData().getSysClerkId());

        //预约用户列表
        List<ShopAppointServiceDTO> shopAppointUserInfoList = new ArrayList<>();
        int sum = 0;

        try {

            shopAppointUserInfoList= extShopAppointServiceMapper.findUserInfoForShopAppointByTime(pageParamDTO);
            sum = extShopAppointServiceMapper.findNumForShopAppointByTime(shopAppointMap);

        }catch(Exception e) {
            logger.info(e.getMessage());
        }

        PageParamDTO<List<ShopAppointServiceDTO>> page = new  PageParamDTO<>();
        page.setResponseData(shopAppointUserInfoList);
        page.setTotalCount(sum);
        logger.info("店铺"+pageParamDTO.getRequestData().getSysShopId()+"下面店员"+pageParamDTO.getRequestData().getSysClerkId()+"在"+pageParamDTO.getRequestData().getAppointStartTimeS()+"到"+pageParamDTO.getRequestData().getAppointStartTimeE()+"的预约用户列表是"+shopAppointUserInfoList);

        return page;
    }

}
