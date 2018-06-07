/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.system.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.SuggestionDto;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.system.interceptor.LoginRequired;
import com.wisdom.system.service.FeedbackService;
import com.wisdom.system.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工具 Controller
 *
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "feedback")
public class FeedBackController {
    Logger logger = LoggerFactory.getLogger(BannerController.class);
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 提交建议
     */
    @RequestMapping(value = "suggestionDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO suggestion(@RequestParam String suggestion) {
        long startTime = System.currentTimeMillis();
        logger.info("提交建议 ==={}开始" , startTime);
        ResponseDTO<SuggestionDto> result = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        String userId=userInfoDTO.getId();
        logger.info("用户={}提交建议==={}" ,userId,suggestion);
        result.setResponseData(feedbackService.addSuggestion(userId,suggestion));
        result.setResult(StatusConstant.SUCCESS);
        logger.info("获取 Banner 图,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return result;
    }
}
