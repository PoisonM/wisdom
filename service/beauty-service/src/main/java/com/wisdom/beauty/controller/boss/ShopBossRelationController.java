package com.wisdom.beauty.controller.boss;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "shopBossRelation")
public class ShopBossRelationController {

    @Resource
    private ShopBossService shopBossService;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取某个老板的店铺信息
     *
     * @return
     */
    @RequestMapping(value = "/getBossShopList", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO<Object> getBossShopList() {
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ShopBossRelationDTO bossRelationDTO = new ShopBossRelationDTO();
        bossRelationDTO.setSysBossCode(bossInfo.getSysBossCode());
        List<ShopBossRelationDTO> shopBossRelationDTOS = shopBossService.shopBossRelationList(bossRelationDTO);
        //默认老板们只有一个店铺，后期扩展
        ArrayList<ShopBossRelationDTO> relationDTOS = new ArrayList<>();
        if(CommonUtils.objectIsNotEmpty(shopBossRelationDTOS)){
            relationDTOS.add(shopBossRelationDTOS.get(0));
        }
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(relationDTOS);
        return responseDTO;
    }


}
