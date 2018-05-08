package com.wisdom.beauty.controller.boss;

import com.wisdom.beauty.api.dto.ShopBossRelationDTO;
import com.wisdom.beauty.core.service.ShopBossService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@Controller
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
    // @LoginRequired
    public @ResponseBody
    ResponseDTO<Object> getBossShopList() {

        long currentTimeMillis = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ShopBossRelationDTO bossRelationDTO = new ShopBossRelationDTO();
        bossRelationDTO.setSysBossId(bossInfo.getId());
        List<ShopBossRelationDTO> shopBossRelationDTOS = shopBossService.ShopBossRelationList(bossRelationDTO);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopBossRelationDTOS);

        logger.info("获取某个老板的店铺信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


}