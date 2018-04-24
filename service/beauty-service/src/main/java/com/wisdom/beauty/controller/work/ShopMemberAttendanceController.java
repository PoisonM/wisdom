package com.wisdom.beauty.controller.work;

import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.core.service.ShopWorkService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.ShopMemberAttendacneDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "work")
public class ShopMemberAttendanceController {

    @Resource
    private ShopWorkService workService;

    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

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
     * @Description: 根据时间，美容院id查询该美容院的耗卡和业绩
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
        userConsumeRequest.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());

        pageParamVoDTO.setRequestData(userConsumeRequest);
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getExpenditureAndIncomeList(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> response = new ResponseDTO<>();
        response.setResponseData(list);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }


}
