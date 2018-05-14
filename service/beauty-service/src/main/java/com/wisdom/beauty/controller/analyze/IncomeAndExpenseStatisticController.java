package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.IncomeExpenditureAnalysisService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopAchievementDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "analyze")
public class IncomeAndExpenseStatisticController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IncomeExpenditureAnalysisService incomeExpenditureAnalysisService;

    //获取某个boss下的所有门店的收支分析
    @RequestMapping(value = "shopIncomeAndOutcomeAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopAchievementDTO>> shopIncomeAnalyzeByDate(@RequestParam String date) {
        ResponseDTO<List<ShopAchievementDTO>> responseDTO = new ResponseDTO<>();

        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 如果有美容院店的id则查询某一个美容院店的收支情况及具体支付方式支付金额，
     *                如果没有则查询boss下所有的美容院收支情况以及具体支付方式支付金额
     * @Date:2018/5/11 15:02
     */
    @RequestMapping(value = "/getInComeExpenditureDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, BigDecimal>> getInComeExpenditureDetail(@RequestParam(required = false) String sysShopId,
                                                          @RequestParam String startTime,
                                                          @RequestParam String endTime) {

        long start = System.currentTimeMillis();

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        userConsumeRequestDTO.setSysBossId(bossInfo.getId());
        if(StringUtils.isNotBlank(sysShopId)){
            userConsumeRequestDTO.setSysShopId(sysShopId);
        }
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        Map<String, BigDecimal> map=incomeExpenditureAnalysisService.getBossIncomeExpenditure(pageParamVoDTO);
        ResponseDTO<Map<String, BigDecimal>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(map);
        logger.info("getInComeExpenditureDetail方法耗时{}毫秒", (System.currentTimeMillis() - start));
        return responseDTO;
    }
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取所有美容的总收入和现金收入
    *@Date:2018/5/11 17:19
    */
    @RequestMapping(value = "/getAllShopIncomeExpenditure", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getAllShopIncomeExpenditure(@RequestParam String startTime,
                                                                     @RequestParam String endTime) {

        long start = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysBossId(bossInfo.getId());
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        List<ExpenditureAndIncomeResponseDTO> list=incomeExpenditureAnalysisService.getAllShopIncomeExpenditure(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getAllShopIncomeExpenditure方法耗时{}毫秒", (System.currentTimeMillis() - start));
        return responseDTO;
    }
    /**
    *@Author:zhanghuan
    *@Param: sysShopId为空则查询boss下所有美容店的现金趋势图，不为空则查询某个美容店的现金趋势图吗
    *@Return:
    *@Description: 获取7天现金收入趋势图
    *@Date:2018/5/14 10:01
        */
    @RequestMapping(value = "/getCashEarningsTendency", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getCashEarningsTendency(@RequestParam(required = false) String sysShopId,
                                                                               @RequestParam String startTime,
                                                                               @RequestParam String endTime){

        long start = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        if(StringUtils.isNotBlank(sysShopId)){
          userConsumeRequestDTO.setSysShopId(sysShopId);
        }
        userConsumeRequestDTO.setSysBossId(bossInfo.getId());
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        List<ExpenditureAndIncomeResponseDTO> list=incomeExpenditureAnalysisService.getCashEarningsTendency(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getCashEarningsTendency方法耗时{}毫秒", (System.currentTimeMillis() - start));
        return responseDTO;
    }
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return: totalPrice 总金额
    *@Description: 查询收入明细
    *@Date:2018/5/14 11:08
    */
    @RequestMapping(value = "/getIncomeExpenditureAnalysisDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getIncomeExpenditureAnalysisDetail(@RequestParam String sysShopId,int pageSize){

        long start = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysShopId(sysShopId);
        userConsumeRequestDTO.setSysBossId(bossInfo.getId());
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        List<ExpenditureAndIncomeResponseDTO> list=incomeExpenditureAnalysisService.getIncomeExpenditureAnalysisDetail(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getIncomeExpenditureAnalysisDetail方法耗时{}毫秒", (System.currentTimeMillis() - start));
        return responseDTO;
    }
}
