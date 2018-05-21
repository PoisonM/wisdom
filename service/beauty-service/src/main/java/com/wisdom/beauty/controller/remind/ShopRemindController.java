package com.wisdom.beauty.controller.remind;

import com.wisdom.beauty.core.service.ShopRemindService;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.beauty.ShopMemberAttendacneDTO;
import com.wisdom.common.dto.system.ResponseDTO;
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
@RequestMapping(value = "remind")
public class ShopRemindController {

    @Autowired
    private ShopRemindService shopRemindService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // 获取门店某天的业绩
    @RequestMapping(value = "shopMemberAttendanceAnalyzeByDate", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public @ResponseBody
    ResponseDTO<List<ShopMemberAttendacneDTO>> shopMemberAttendanceAnalyzeByDate(
            @RequestParam String shopId, @RequestParam String date) {
        ResponseDTO<List<ShopMemberAttendacneDTO>> responseDTO = new ResponseDTO<>();

        return responseDTO;
    }

}
