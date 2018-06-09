package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopAppointServiceCriteria;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.AppointStatusEnum;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ShopUserLoginDTO;
import com.wisdom.beauty.api.responseDto.ShopProjectInfoResponseDTO;
import com.wisdom.beauty.core.mapper.ExtShopAppointServiceMapper;
import com.wisdom.beauty.core.mapper.ShopAppointServiceMapper;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
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

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShopProjectService shopProjectService;

    /**
     * 根据时间查询查询某个店的有预约号源的美容师列表
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public List<ShopAppointServiceDTO> getShopAppointClerkInfoByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if (null == extShopAppointServiceDTO) {
            logger.info("根据时间查询查询某个店的有预约号源的美容师列表,查询参数为空");
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();

        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        }

        if (null != extShopAppointServiceDTO.getSearchStartTime()) {
            criteria.andCreateDateBetween(extShopAppointServiceDTO.getSearchStartTime(), extShopAppointServiceDTO.getSearchEndTime());
        }

        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysClerkId())) {
            criteria.andSysClerkIdEqualTo(extShopAppointServiceDTO.getSysClerkId());
        }

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
            logger.info("根据时间查询某个店下的某个美容师的预约列表,查询参数为空");
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();
        String status = extShopAppointServiceDTO.getStatus();

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
        if (null != extShopAppointServiceDTO.getAppointStartTimeS() && null != extShopAppointServiceDTO.getAppointEndTimeE()) {
            criteria.andAppointStartTimeBetween(DateUtils.StrToDate(extShopAppointServiceDTO.getAppointStartTimeS(), "datetime"), DateUtils.StrToDate(extShopAppointServiceDTO.getAppointEndTimeE(), "datetime"));
        }
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysBossCode())) {
            criteria.andSysBossCodeEqualTo(extShopAppointServiceDTO.getSysBossCode());
        }
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getId())) {
            List<String> values = new ArrayList<>();
            values.add(extShopAppointServiceDTO.getId());
            criteria.andIdNotIn(values);
        }


        if (StringUtils.isNotBlank(status)) {
            //如果是进行中
            if (AppointStatusEnum.ONGOING.getCode().equals(status)) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(AppointStatusEnum.NOT_STARTED.getCode());
                arrayList.add(AppointStatusEnum.CONFIRM.getCode());
                arrayList.add(AppointStatusEnum.ON_SERVICE.getCode());
                criteria.andStatusIn(arrayList);
            }
            //如果是已结束
            else if (AppointStatusEnum.ENDED.getCode().equals(status)) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(AppointStatusEnum.OVER.getCode());
                arrayList.add(AppointStatusEnum.CANCEL.getCode());
                criteria.andStatusIn(arrayList);
            } else {
                criteria.andStatusEqualTo(status);
            }
        }
        shopAppointServiceCriteria.setOrderByClause("appoint_start_time desc");

        List<ShopAppointServiceDTO> appointServiceDTOS = shopAppointServiceMapper.selectByCriteria(shopAppointServiceCriteria);
        return appointServiceDTOS;
    }

    /**
     * 根据时间查询某个店下的某个美容师的预约个数
     *
     * @param extShopAppointServiceDTO
     * @return
     */
    @Override
    public Integer getShopClerkAppointNumberByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO) {

        if (null == extShopAppointServiceDTO) {
            logger.info("根据时间查询某个店下的某个美容师的预约列表,查询参数为空");
            return null;
        }

        ShopAppointServiceCriteria shopAppointServiceCriteria = new ShopAppointServiceCriteria();
        ShopAppointServiceCriteria.Criteria criteria = shopAppointServiceCriteria.createCriteria();

        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysShopId())) {
            criteria.andSysShopIdEqualTo(extShopAppointServiceDTO.getSysShopId());
        }
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysClerkId())) {
            criteria.andSysClerkIdEqualTo(extShopAppointServiceDTO.getSysClerkId());
        }
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysUserId())) {
            criteria.andSysUserIdEqualTo(extShopAppointServiceDTO.getSysUserId());
        }
        if (null != extShopAppointServiceDTO.getSearchStartTime() && null != extShopAppointServiceDTO.getSearchEndTime()) {
            criteria.andAppointStartTimeBetween(extShopAppointServiceDTO.getSearchStartTime(), extShopAppointServiceDTO.getSearchEndTime());
        }
        if (StringUtils.isNotBlank(extShopAppointServiceDTO.getSysBossCode())) {
            criteria.andSysBossCodeEqualTo(extShopAppointServiceDTO.getSysBossCode());
        }

        int appointServiceDTOS = shopAppointServiceMapper.countByCriteria(shopAppointServiceCriteria);
        logger.info("根据时间查询某个店下的某个美容师的预约个数={}", appointServiceDTOS);
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
            logger.debug(" 根据用户ID查询预约信息为空");
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<Map> updateUserAppointInfo(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
        ResponseDTO<Map> responseDTO = new ResponseDTO<>();

        if (org.apache.commons.lang3.StringUtils.isNotBlank(shopAppointServiceDTO.getAppointStartTimeS())) {
            shopAppointServiceDTO.setAppointStartTime(DateUtils.StrToDate(shopAppointServiceDTO.getAppointStartTimeS(), "hour"));
            Date afterDate = new Date(shopAppointServiceDTO.getAppointStartTime().getTime() + shopAppointServiceDTO.getAppointPeriod() * 60 * 1000L);
            shopAppointServiceDTO.setAppointEndTime(afterDate);
        }
        //根据预约时间查询当前美容师有没有被占用
        shopAppointServiceDTO.setSearchStartTime(shopAppointServiceDTO.getAppointStartTime());
        shopAppointServiceDTO.setSearchEndTime(shopAppointServiceDTO.getAppointEndTime());
        String status = shopAppointServiceDTO.getStatus();
        shopAppointServiceDTO.setStatus("");
        List<ShopAppointServiceDTO> appointListByCriteria = getShopClerkAppointListByCriteria(shopAppointServiceDTO);
        shopAppointServiceDTO.setStatus(status);
        if (CommonUtils.objectIsNotEmpty(appointListByCriteria)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("当前时间段已被预约，请您重新选择(-_-)");
            return responseDTO;
        }

        logger.info("修改用户的预约信息={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
        shopAppointServiceDTO.setUpdateDate(new Date());
        int info = updateAppointmentInfo(shopAppointServiceDTO);
        logger.debug("修改用户的预约信息执行结果， {}", info > 0 ? "成功" : "失败");

        redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);

        ShopUserProjectRelationDTO deleteRelationDTO = new ShopUserProjectRelationDTO();
        deleteRelationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
        //删除用户与项目的关系
        shopProjectService.deleteUserAndProjectRelation(deleteRelationDTO);

        HashMap<Object, Object> hashMap = new HashMap<>(1);
        hashMap.put("appointmentId", shopAppointServiceDTO.getId());
        responseDTO.setResponseData(hashMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }


    /**
     * 保存用户的预约信息
     * @param shopAppointServiceDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveUserShopAppointInfo(ExtShopAppointServiceDTO shopAppointServiceDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        shopAppointServiceDTO.setId(IdGen.uuid());
        UserInfoDTO userInfo = UserUtils.getUserInfo();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        //如果userInfo为空，说明是pad端用户
        if (null != clerkInfo) {
            shopAppointServiceDTO.setCreateBy(clerkInfo.getSysUserId());
            shopAppointServiceDTO.setSysBossCode(clerkInfo.getSysBossCode());
            if (org.apache.commons.lang3.StringUtils.isBlank(shopAppointServiceDTO.getSysClerkId())) {
                shopAppointServiceDTO.setSysClerkId(clerkInfo.getId());
            }
            shopAppointServiceDTO.setSysClerkName(clerkInfo.getName());
            shopAppointServiceDTO.setSysShopId(clerkInfo.getSysShopId());
            shopAppointServiceDTO.setSysShopName(clerkInfo.getSysShopName());
        }
        //如果clerkInfo为空说明是用户端用户
        else if (null != userInfo) {
            shopAppointServiceDTO.setSysUserId(userInfo.getId());
            shopAppointServiceDTO.setSysUserName(userInfo.getNickname());
            shopAppointServiceDTO.setCreateBy(userInfo.getId());
            shopAppointServiceDTO.setSysUserPhone(userInfo.getMobile());
            ShopUserLoginDTO userLoginShop = redisUtils.getUserLoginShop(UserUtils.getUserInfo().getId());
            shopAppointServiceDTO.setSysShopId(userLoginShop.getSysShopId());
            shopAppointServiceDTO.setSysShopName(userLoginShop.getSysShopName());
            shopAppointServiceDTO.setSysUserPhone(userInfo.getMobile());
            shopAppointServiceDTO.setSysUserName(userInfo.getNickname());
            shopAppointServiceDTO.setSysBossCode(userLoginShop.getSysBossCode());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(shopAppointServiceDTO.getAppointStartTimeS())) {
            shopAppointServiceDTO.setAppointStartTime(DateUtils.StrToDate(shopAppointServiceDTO.getAppointStartTimeS(), "hour"));
            Date afterDate = new Date(shopAppointServiceDTO.getAppointStartTime().getTime() + shopAppointServiceDTO.getAppointPeriod() * 60 * 1000l);
            shopAppointServiceDTO.setAppointEndTime(afterDate);
        }
        //根据预约时间查询当前美容师有没有被占用
        shopAppointServiceDTO.setSearchStartTime(shopAppointServiceDTO.getAppointStartTime());
        shopAppointServiceDTO.setSearchEndTime(shopAppointServiceDTO.getAppointEndTime());
        String status = shopAppointServiceDTO.getStatus();
        shopAppointServiceDTO.setStatus("");
        List<ShopAppointServiceDTO> appointListByCriteria = getShopClerkAppointListByCriteria(shopAppointServiceDTO);
        shopAppointServiceDTO.setStatus(status);
        if (CommonUtils.objectIsNotEmpty(appointListByCriteria)) {
            logger.info("根据预约时间查询当前美容师有没有被占用查询结果大小为={}", appointListByCriteria.size());
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("当前时间段已被预约，请您重新选择(-_-)");
            return responseDTO;
        }

        logger.info("保存用户的预约信息={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");
        shopAppointServiceDTO.setCreateDate(new Date());
        SysClerkDTO sysClerkDTO = redisUtils.getSysClerkDTO(shopAppointServiceDTO.getSysClerkId());
        shopAppointServiceDTO.setSysClerkName(sysClerkDTO.getName());
        shopAppointServiceDTO.setSysBossCode(sysClerkDTO.getSysBossCode());

        logger.info("保存用户的预约信息传入参数={}", "shopAppointServiceDTO = [" + shopAppointServiceDTO + "]");

        int insert = shopAppointServiceMapper.insertSelective(shopAppointServiceDTO);
        redisUtils.saveShopAppointInfoToRedis(shopAppointServiceDTO);
        logger.debug("保存用户的预约信息执行结果， {}", insert > 0 ? "成功" : "失败");

        HashMap<Object, Object> hashMap = new HashMap<>(1);
        hashMap.put("appointmentId", shopAppointServiceDTO.getId());
        responseDTO.setResponseData(hashMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 构建用户与项目的关系
     *
     * @param shopAppointServiceDTO
     */
    @Override
    public void buildUserProjectRelation(@RequestBody ExtShopAppointServiceDTO shopAppointServiceDTO) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(shopAppointServiceDTO.getShopProjectId())) {
            String[] projectStr = shopAppointServiceDTO.getShopProjectId().split(";");
            for (String project : projectStr) {
                //先查询，如果用户与项目已经建立关系，并且没有使用完，不需要重新创建用户与项目的关系
                ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
                relationDTO.setSysUserId(shopAppointServiceDTO.getSysUserId());
                relationDTO.setSysShopProjectId(project);
                relationDTO.setSysShopProjectSurplusTimes(0);
                List<ShopUserProjectRelationDTO> userProjectList = shopProjectService.getUserProjectList(relationDTO);
                //需要重新创建用户与项目的关系
                if (CommonUtils.objectIsEmpty(userProjectList)) {
                    relationDTO.setSysShopId(shopAppointServiceDTO.getSysShopId());
                    relationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
                    //根据项目主键查询项目详细信息
                    ShopProjectInfoResponseDTO projectDetail = shopProjectService.getProjectDetail(project);
                    if (null != projectDetail) {
                        relationDTO.setUseStyle(projectDetail.getUseStyle());
                        relationDTO.setCreateBy(shopAppointServiceDTO.getCreateBy());
                        relationDTO.setSysShopProjectName(projectDetail.getProjectName());
                        relationDTO.setId(IdGen.uuid());
                        relationDTO.setSysShopProjectInitTimes(0);
                        relationDTO.setSysShopProjectInitAmount(new BigDecimal(0));
                        relationDTO.setSysClerkId(shopAppointServiceDTO.getSysClerkId());
                        relationDTO.setSysShopName(shopAppointServiceDTO.getSysShopName());
                        relationDTO.setSysShopProjectSurplusAmount(new BigDecimal(0));
                        relationDTO.setSysUserId(shopAppointServiceDTO.getSysUserId());
                        relationDTO.setSysShopProjectId(project);
                        relationDTO.setSysClerkName(shopAppointServiceDTO.getSysClerkName());
                        relationDTO.setSysShopProjectSurplusTimes(0);
                        relationDTO.setShopAppointmentId(shopAppointServiceDTO.getId());
                        relationDTO.setSysShopId(projectDetail.getSysShopId());
                        int num = shopProjectService.saveUserProjectRelation(relationDTO);
                        logger.debug("建立项目与用户的关系， {}", num > 0 ? "成功" : "失败");
                    }
                }
            }
        }
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
    public PageParamDTO<List<ExtShopAppointServiceDTO>> findUserInfoForShopByTimeService(PageParamDTO<ExtShopAppointServiceDTO> pageParamDTO) {


        HashMap<String,String> shopAppointMap = new HashMap<>();
        shopAppointMap.put("sysShopId",pageParamDTO.getRequestData().getSysShopId());
        shopAppointMap.put("appointStartTimeS",pageParamDTO.getRequestData().getAppointStartTimeS());
        shopAppointMap.put("appointStartTimeE", pageParamDTO.getRequestData().getAppointEndTimeE());
        shopAppointMap.put("sysClerkId",pageParamDTO.getRequestData().getSysClerkId());

        //预约用户列表
        List<ExtShopAppointServiceDTO> shopAppointUserInfoList = new ArrayList<>();
        int sum = 0;

        try {

            shopAppointUserInfoList= extShopAppointServiceMapper.findUserInfoForShopAppointByTime(pageParamDTO);
            sum = extShopAppointServiceMapper.findNumForShopAppointByTime(shopAppointMap);

        }catch(Exception e) {
            logger.info(e.getMessage());
        }

        PageParamDTO<List<ExtShopAppointServiceDTO>> page = new PageParamDTO<>();
        page.setResponseData(shopAppointUserInfoList);
        page.setTotalCount(sum);
        logger.info("店铺" + pageParamDTO.getRequestData().getSysShopId() + "下面店员" + pageParamDTO.getRequestData().getSysClerkId() + "在" + pageParamDTO.getRequestData().getAppointStartTimeS() + "到" + pageParamDTO.getRequestData().getAppointEndTimeE() + "的预约用户列表是" + shopAppointUserInfoList);

        return page;
    }


    /**
     * 获取用户的最后一次预约时间
     * @param appointServiceDTO
     * @return
     */
    @Override
    public List<ExtShopAppointServiceDTO> selectShopUserLastAppointInfo(ShopAppointServiceDTO appointServiceDTO){
        if (appointServiceDTO == null) {
            logger.error("查询对象为空");
        }
        return extShopAppointServiceMapper.selectShopUserLastAppointInfo(appointServiceDTO);
    }
}
