package com.wisdom.beauty.service;

import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeautyServiceController {

    @Autowired
    private ShopUserRelationService shopUserRelationService;

    /**
     * 绑定关系
     *
     * @param openId
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getUserBindingInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<String> getUserBindingInfo(@RequestParam(value = "openId") String openId, @RequestParam(value = "shopId") String shopId,
                                           @RequestParam(value = "userId") String userId) {
        return shopUserRelationService.userBinding(openId, shopId,userId);
    }

}
