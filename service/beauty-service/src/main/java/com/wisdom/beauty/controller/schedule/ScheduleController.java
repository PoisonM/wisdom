package com.wisdom.beauty.controller.schedule;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopClerkScheduleDTO;
import com.wisdom.beauty.api.enums.ScheduleTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopClerkScheduleDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopClerkScheduleService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * FileName: ScheduleController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 排班相关
 */
@Controller
@RequestMapping(value = "clerkSchedule")
public class ScheduleController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopClerkScheduleService shopClerkScheduleService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShopAppointmentService appointmentService;

    /**
     * 获取某个店的排班信息
     *
     * @param searchDate
     * @return
     */
    @RequestMapping(value = "/getShopClerkScheduleList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getShopClerkScheduleList(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date searchDate) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("获取某个店的排班信息传入参数={}", "searchDate = [" + searchDate + "]");

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ArrayList<Object> helperList = new ArrayList<>();
        ShopClerkScheduleDTO shopClerkScheduleDTO = new ShopClerkScheduleDTO();
        shopClerkScheduleDTO.setSysShopId(clerkInfo.getSysShopId());
        shopClerkScheduleDTO.setScheduleDate(searchDate);
        //查询某个店的排班信息
        List<ShopClerkScheduleDTO> clerkScheduleList = shopClerkScheduleService.getShopClerkScheduleList(shopClerkScheduleDTO);
        //获取某个月的所有天的集合
        List<String> monthFullDay = DateUtils.getMonthFullDay(Integer.parseInt(DateUtils.getYear(searchDate)), Integer.parseInt(DateUtils.getMonth(searchDate)), 0);
        //查询店员信息
        List<SysClerkDTO> clerkDTOS = userServiceClient.getClerkInfo(clerkInfo.getSysShopId());

        if (null == clerkDTOS) {
            logger.info("查询某个店的店员信息为空");
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("店员信息为空");
            return responseDTO;
        }

        //如果某个店的店员信息为空，则批量初始化
        if (CommonUtils.objectIsEmpty(clerkScheduleList)) {
            logger.info("获取某个店的排班信息为空");
            clerkScheduleList = new ArrayList<>();
            for (SysClerkDTO sysClerkDTO : clerkDTOS) {
                for (String string : monthFullDay) {
                    ShopClerkScheduleDTO scheduleDTO = new ShopClerkScheduleDTO();
                    scheduleDTO.setId(IdGen.uuid());
                    scheduleDTO.setScheduleDate(DateUtils.StrToDate(string, "date"));
                    scheduleDTO.setSysShopId(clerkInfo.getSysShopId());
                    scheduleDTO.setCreateDate(new Date());
                    scheduleDTO.setScheduleType(ScheduleTypeEnum.ALL.getCode());
                    scheduleDTO.setSysClerkId(sysClerkDTO.getId());
                    scheduleDTO.setSysBossId(sysClerkDTO.getSysBossId());
                    scheduleDTO.setSysClerkName(sysClerkDTO.getName());
                    clerkScheduleList.add(scheduleDTO);
                }
            }
            //批量插入
            int number = shopClerkScheduleService.saveShopClerkScheduleList(clerkScheduleList);
            logger.info("批量插入{}条数据",number);
        }


        for (SysClerkDTO sysClerkDTO : clerkDTOS) {
            HashMap<Object, Object> helperMap = new HashMap<>(16);
            //clerkSchInfo存储某个美容师的所有排班信息
            List<ShopClerkScheduleDTO> clerkSchInfo = new ArrayList<>();
            for (ShopClerkScheduleDTO scheduleDTO : clerkScheduleList) {
                if (sysClerkDTO.getId().equals(scheduleDTO.getSysClerkId())) {
                    clerkSchInfo.add(scheduleDTO);
                }
            }
            Collections.sort(clerkSchInfo, new Comparator<ShopClerkScheduleDTO>() {
                @Override
                public int compare(ShopClerkScheduleDTO o1, ShopClerkScheduleDTO o2) {
                    Long i = o1.getScheduleDate().getTime() - o2.getScheduleDate().getTime();
                    return i.intValue();
                }
            });
            helperMap.put("clerkSchInfo", clerkSchInfo);
            helperMap.put("clerkInfo", sysClerkDTO);
            helperList.add(helperMap);
        }


        HashMap<Object, Object> returnMap = new HashMap<>(16);
        //界面顶部日期显示
        ArrayList<Object> dateDetail = new ArrayList<>();
        for (String string : monthFullDay) {
            StringBuffer sb = new StringBuffer(string);
            sb.append("||");
            sb.append(DateUtils.getWeek(DateUtils.StrToDate(string, "date")));
            dateDetail.add(sb.toString());
        }

        returnMap.put("dateDetail", dateDetail);
        returnMap.put("responseList", helperList);
        responseDTO.setResponseData(returnMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取某个店的排班信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


    /**
     * 批量更新某个店的排班信息
     *
     * @param shopClerkSchedule
     * @return
     */
    @RequestMapping(value = "/updateShopClerkScheduleList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Object> updateShopClerkScheduleList(@RequestBody ExtShopClerkScheduleDTO<List<ShopClerkScheduleDTO>> shopClerkSchedule) {

        long currentTimeMillis = System.currentTimeMillis();
        List<ShopClerkScheduleDTO> scheduleDTO = shopClerkSchedule.getShopClerkSchedule();
        int scheduleList = shopClerkScheduleService.updateShopClerkScheduleList(scheduleDTO);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(scheduleList>0?StatusConstant.SUCCESS:StatusConstant.FAILURE);
        logger.info("批量更新某个店的排班信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 获取某个店某个美容师某天的排班信息
     */
    @RequestMapping(value = "/getClerkScheduleInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Object> getClerkScheduleInfo(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchDate, @RequestParam String clerkId) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("获取某个店某个美容师某天的排班信息传入参数={}", "searchDate = [" + searchDate + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        ShopClerkScheduleDTO shopClerkScheduleDTO = new ShopClerkScheduleDTO();
        //登陆相关-待补充
//        String sysShopId = redisUtils.getUserLoginShop(UserUtils.getUserInfo().getId()).getSysShopId();
        String sysShopId = "11";
        shopClerkScheduleDTO.setSysShopId(sysShopId);
        shopClerkScheduleDTO.setSysClerkId(clerkId);
        shopClerkScheduleDTO.setScheduleDate(searchDate);
        //查询美容师的排班信息
        List<ShopClerkScheduleDTO> clerkScheduleList = shopClerkScheduleService.getShopClerkScheduleList(shopClerkScheduleDTO);
        if (CommonUtils.objectIsEmpty(clerkScheduleList)) {
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData("美容师的排班信息为空，没有初始化！");
            return responseDTO;
        }
        shopClerkScheduleDTO = clerkScheduleList.get(0);

        String startTime = ScheduleTypeEnum.judgeValue(shopClerkScheduleDTO.getScheduleType()).getDefaultStartTime();
        String endTime = ScheduleTypeEnum.judgeValue(shopClerkScheduleDTO.getScheduleType()).getDefaultEndTime();
        //存储当前美容师的排班时间段
        String responseStr = CommonUtils.getArrayNo(startTime, endTime);

        //查询某个美容师某天的预约详情
        ExtShopAppointServiceDTO extShopAppointServiceDTO = new ExtShopAppointServiceDTO();
        extShopAppointServiceDTO.setSearchStartTime(DateUtils.StrToDate(DateUtils.DateToStr(searchDate, "date") + " 00:00:00", "datetime"));
        extShopAppointServiceDTO.setSearchEndTime(DateUtils.StrToDate(DateUtils.DateToStr(searchDate, "date") + " 23:59:59", "datetime"));
        extShopAppointServiceDTO.setSysClerkId(clerkId);
        extShopAppointServiceDTO.setSysShopId(sysShopId);
        List<ShopAppointServiceDTO> shopAppointServiceDTOS = appointmentService.getShopClerkAppointListByCriteria(extShopAppointServiceDTO);
        //缓存预约过的时间
        StringBuffer filterStr = new StringBuffer();

        //可预约时间 = 当前美容师的排班时间段 - 预约过的时间
        if (CommonUtils.objectIsNotEmpty(shopAppointServiceDTOS)) {
            for (int i = 0; i < shopAppointServiceDTOS.size(); i++) {
                filterStr.append(CommonUtils.getArrayNo(DateUtils.DateToStr(shopAppointServiceDTOS.get(i).getAppointStartTime(), "time"),
                        DateUtils.DateToStr(shopAppointServiceDTOS.get(i).getAppointEndTime(), "time")));
                if (i != (shopAppointServiceDTOS.size() - 1)) {
                    filterStr.append(",");
                }
            }
            //转为字符数组，方便过滤
            String[] filter = filterStr.toString().split(",");
            //转为list方便过滤
            List<String> list = Arrays.asList(responseStr.split(","));
            list = new ArrayList(list);
            //list中过滤掉冲突时间段
            for (int j = 0; j < filter.length; j++) {
                Iterator<String> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (next.equals(filter[j])) {
                        iterator.remove();
                    }
                }
            }
            //转为string
            responseStr = list.toString();
        }

        responseDTO.setResponseData(responseStr);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取某个店某个美容师某天的排班信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);

        return responseDTO;
    }

}