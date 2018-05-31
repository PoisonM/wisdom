package com.wisdom.beauty.controller.remind;

import com.wisdom.beauty.api.dto.ShopRemindSettingDTO;
import com.wisdom.beauty.core.service.ShopRemindService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@LoginAnnotations
@RequestMapping(value = "remind")
public class ShopRemindController {

    @Autowired
    private ShopRemindService shopRemindService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个店的设置信息
     *
     * @return
     */
    @RequestMapping(value = "/getShopRemindSetting", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> getShopRemindSetting() {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        ShopRemindSettingDTO shopRemindSettingDTO = new ShopRemindSettingDTO();
        shopRemindSettingDTO.setSysBossCode(UserUtils.getBossInfo().getId());
        ShopRemindSettingDTO shopRemindSetting = shopRemindService.getShopRemindSetting(shopRemindSettingDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopRemindSetting);
        return responseDTO;
    }

    /**
     * 修改某个店的设置信息
     *
     * @return
     */
    @RequestMapping(value = "/updateShopRemindSetting", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<Object> updateShopRemindSetting(@RequestBody ShopRemindSettingDTO shopRemindSettingDTO) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        if (StringUtils.isBlank(shopRemindSettingDTO.getId())) {
            responseDTO.setResult(StatusConstant.SUCCESS);
            return responseDTO;
        }
        int setting = shopRemindService.updateShopRemindSetting(shopRemindSettingDTO);
        responseDTO.setResult(setting > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        return responseDTO;
    }

}
