package com.wisdom.beauty.controller.analyze;

import com.wisdom.beauty.api.responseDto.ExpenditureAndIncomeResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopStatisticsAnalysisService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.beauty.CustomerContributionDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "analyze")
public class CustomerContributionStatisticController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopStatisticsAnalysisService shopStatisticsAnalysisService;

    //获取门店某天的业绩
    @RequestMapping(value = "shopCustomerContributionByDate", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<CustomerContributionDTO>> shopCustomerContributionByDate(@RequestParam String shopId, @RequestParam String date) {

        ResponseDTO<List<CustomerContributionDTO>> responseDTO = new ResponseDTO<>();

        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/5/15 14:12
     */
    @RequestMapping(value = "/getClerkAchievementList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> getClerkAchievementList(@RequestParam(required = false) String sysShopId,
                                                                               @RequestParam String startTime,
                                                                               @RequestParam String endTime) {

        long start = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
        if (StringUtils.isNotBlank(sysShopId)) {
            userConsumeRequestDTO.setSysShopId(sysShopId);
        }
        userConsumeRequestDTO.setSysBossId(bossInfo.getId());
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        List<ExpenditureAndIncomeResponseDTO> list = shopStatisticsAnalysisService.getClerkAchievementList(pageParamVoDTO);
        ResponseDTO<List<ExpenditureAndIncomeResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getCashEarningsTendency方法耗时{}毫秒", (System.currentTimeMillis() - start));
        return responseDTO;
    }
}
