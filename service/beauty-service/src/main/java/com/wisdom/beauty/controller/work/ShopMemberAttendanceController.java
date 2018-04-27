package com.wisdom.beauty.controller.work;

import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.ShopWorkService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopMemberAttendacneDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "work")
public class ShopMemberAttendanceController {

    @Resource
    private ShopWorkService workService;

    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    //获取门店某天的业绩
    @RequestMapping(value = "shopMemberAttendanceAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopMemberAttendacneDTO>> shopMemberAttendanceAnalyzeByDate(@RequestParam String shopId,
                                                                                 @RequestParam String date) {
        ResponseDTO<List<ShopMemberAttendacneDTO>> responseDTO = new ResponseDTO<>();

        return responseDTO;
    }

    /**
     * @Author: zhanghuan
     * @Param: 美容院id ，startTime(2018-04-10 00:00:00)，endTime(2018-04-10 00:00:00)
     * @Return:
     * @Description: 根据时间查询某个美容院的耗卡和业绩
     * @Date:2018/4/23 16:07
     */
    @RequestMapping(value = "/getExpenditureAndIncome", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getExpenditureAndIncome(@RequestParam String sysShopId,
                                                                               @RequestParam String startTime,
                                                                               @RequestParam String endTime) {
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
        userConsumeRequest.setSysShopId(sysShopId);

        pageParamVoDTO.setRequestData(userConsumeRequest);
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getExpenditureAndIncomeList(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
        response.setResponseData(list);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * @Author:zhanghuan
     * @Param: bossId，consumeType 消费类型
     * @Return:
     * @Description: 获取老板下所有美容院的列表
     * @Date:2018/4/24 11:08
     */
    @RequestMapping(value = "/getBossExpenditureAndIncome", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getBossExpenditureAndIncome(@RequestParam String sysBossId) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
        userConsumeRequest.setSysBossId(sysBossId);
        userConsumeRequest.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());

        pageParamVoDTO.setRequestData(userConsumeRequest);
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getExpenditureAndIncomeList(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
        response.setResponseData(list);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return: map
     * key：recharge=充值
     * key: consume=消费
     * @Description: 获取某个美容院的业绩信息(充值和消费)
     * 充值  goodsType=2
     * 消费  goodsType!=2
     * @Date:2018/4/24 11:08
     */
    @RequestMapping(value = "/getShopConsumeAndRecharge", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Map<String, BigDecimal>> getShopConsumeAndRecharge(@RequestParam String shopId,
                                                                     @RequestParam String startTime,
                                                                     @RequestParam String consumeType,
                                                                     @RequestParam String endTime) {

        Date startDate = DateUtils.StrToDate(startTime, "datetime");
        Date endDate = DateUtils.StrToDate(endTime, "datetime");
        Boolean bool=false;
        BigDecimal recharge = shopStatisticsAnalysisService.getShopConsumeAndRecharge(shopId, GoodsTypeEnum.RECHARGE_CARD.getCode(),consumeType ,bool,startDate, endDate);
        BigDecimal consume = shopStatisticsAnalysisService.getShopConsumeAndRecharge(shopId, GoodsTypeEnum.TIME_CARD.getCode(),consumeType, bool, startDate,endDate);
        Map<String, BigDecimal> map = new HashMap<>(16);
        map.put("recharge", recharge);
        map.put("consume", consume);
        ResponseDTO<Map<String, BigDecimal>> response = new ResponseDTO<>();
        response.setResponseData(map);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取店员成绩
    *@Date:2018/4/27 18:26
    */
    @RequestMapping(value = "/getClerkAchievement", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Map<String, String>> getClerkAchievement(@RequestParam String sysClerkId) {

        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        if(sysClerkDTO==null){
            return  null;
        }
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO=new PageParamVoDTO();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
        userConsumeRequestDTO.setSysClerkId(sysClerkId);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        //String startTime= DateUtils.formatDateTime(new Date());
        String startTime= "2018-04-27 00:00:00";
        String endTime= "2018-04-27 00:00:00";
        pageParamVoDTO.setStartTime("2018-04-27 00:00:00");
        pageParamVoDTO.setEndTime("2018-04-27 00:00:00");
        BigDecimal income = shopStatisticsAnalysisService.getPerformance(pageParamVoDTO);
        BigDecimal expenditure = shopStatisticsAnalysisService.getExpenditure(pageParamVoDTO);
        Integer consumeNumber = shopStatisticsAnalysisService.getUserConsumeNumber(sysClerkId,startTime,endTime);
        Integer shopNewUserNumber = shopStatisticsAnalysisService.getShopNewUserNumber(sysClerkDTO.getSysShopId(),startTime,endTime);

        Map<String, String> map = new HashMap<>(16);
        map.put("income", income==null?"0":income.toString());
        map.put("expenditure", expenditure==null?"0":income.toString());
        map.put("consumeNumber", consumeNumber.toString());
        map.put("shopNewUserNumber", shopNewUserNumber.toString());
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        response.setResponseData(map);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

}
