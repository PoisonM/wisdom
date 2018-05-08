package com.wisdom.beauty.controller.work;

import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.ShopWorkService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopMemberAttendacneDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //获取门店某天的业绩
    @RequestMapping(value = "shopMemberAttendanceAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
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
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getBossExpenditureAndIncome() {
        SysBossDTO sysBossDTO=UserUtils.getBossInfo();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
        userConsumeRequest.setSysBossId(sysBossDTO.getId());
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
    ResponseDTO<Map<String, String>> getShopConsumeAndRecharge(@RequestParam String shopId,
                                                               @RequestParam String startTime,
                                                               @RequestParam String endTime) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        UserConsumeRequestDTO userConsumeRequest = new UserConsumeRequestDTO();
        userConsumeRequest.setSysShopId(shopId);

        pageParamVoDTO.setRequestData(userConsumeRequest);
        Map<String, String> map = shopStatisticsAnalysisService.getShopConsumeAndRecharge(pageParamVoDTO);
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        response.setResponseData(map);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取店员成绩
     * @Date:2018/4/27 18:26
     */
    @RequestMapping(value = "/getClerkAchievement", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Map<String, String>> getClerkAchievement(@RequestParam String sysClerkId) {

        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null) {
            return null;
        }
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
        UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
        userConsumeRequestDTO.setSysClerkId(sysClerkId);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        String startTime = DateUtils.getStartTime();
        String endTime = DateUtils.getEndTime();
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        BigDecimal income = shopStatisticsAnalysisService.getPerformance(pageParamVoDTO);
        BigDecimal expenditure = shopStatisticsAnalysisService.getExpenditure(pageParamVoDTO);
        Integer consumeNumber = shopStatisticsAnalysisService.getUserConsumeNumber(sysClerkId, startTime, endTime);
        Integer shopNewUserNumber = shopStatisticsAnalysisService.getShopNewUserNumber(sysClerkDTO.getSysShopId(), startTime, endTime);

        Map<String, String> map = new HashMap<>(16);
        map.put("income", income == null ? "0" : income.toString());
        map.put("expenditure", expenditure == null ? "0" : income.toString());
        map.put("consumeNumber", consumeNumber.toString());
        map.put("shopNewUserNumber", shopNewUserNumber.toString());
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        response.setResponseData(map);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取当前美容院当前boss的当前家人列表
     * @Date:2018/5/2 9:40
     */
    @RequestMapping(value = "/getFamilyList", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getFamilyList(@RequestParam String startTime,
                                                                     @RequestParam String endTime,
                                                                     @RequestParam int pageSize) {

        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null) {
            logger.info("redis获取clerk对象sysClerkDTO为空");
            return null;
        }
        SysBossDTO sysBossDTO = UserUtils.getBossInfo();
        if (sysBossDTO == null) {
            logger.info("redis获取boos对象sysBossDTO为空");
            return null;
        }
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
        UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
        userConsumeRequestDTO.setSysBossId(sysBossDTO.getId());
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        pageParamVoDTO.setPageSize(pageSize);
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        List<ExpenditureAndIncomeResponseDTO> expenditureAndIncomeResponse = shopStatisticsAnalysisService.getClerkExpenditureAndIncomeList(pageParamVoDTO);

        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
        response.setResponseData(expenditureAndIncomeResponse);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:  业绩明细
    *@Date:2018/5/7 15:35
    */
    @RequestMapping(value = "/getBossPerformance ", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findMineConsume(@RequestBody UserConsumeRequestDTO userConsumeRequest) {
        long startTime = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        SysBossDTO sysBossDTO=UserUtils.getBossInfo();

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        userConsumeRequest.setSysShopId(sysClerkDTO.getSysShopId());
        userConsumeRequest.setSysBossId(sysBossDTO.getId());
        userConsumeRequest.setGoodsTypeRequire(true);
        pageParamVoDTO.setRequestData(userConsumeRequest);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(userConsumeRequest.getPageSize());

        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findMineConsume方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
}
