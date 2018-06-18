package com.wisdom.beauty.controller.card;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "cardHelper")
public class CardHelperController {

    @Resource
    private ShopCardService shopCardService;

    @Value("${test.msg}")
    private String msg;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询虚拟商品的适用范围
     *
     * @return
     */
    @RequestMapping(value = "/getGoodsUseScope", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getGoodsUseScope(@RequestParam String shopRechargeCardId) {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        List<ShopProjectProductCardRelationDTO> timesList = new ArrayList<>();
        List<ShopProjectProductCardRelationDTO> periodList = new ArrayList<>();
        List<ShopProjectProductCardRelationDTO> groupCardList = new ArrayList<>();
        List<ShopProjectProductCardRelationDTO> productList = new ArrayList<>();
        HashMap<Object, Object> responseMap = new HashMap<>(5);

        ShopProjectProductCardRelationDTO relationDTO = new ShopProjectProductCardRelationDTO();
        relationDTO.setShopRechargeCardId(shopRechargeCardId);
        List<ShopProjectProductCardRelationDTO> relationList = shopCardService.getShopProjectProductCardRelationList(relationDTO);
        if (CommonUtils.objectIsNotEmpty(relationList)) {
            for (ShopProjectProductCardRelationDTO dto : relationList) {
                if (GoodsTypeEnum.TIME_CARD.getCode().equals(dto.getGoodsType())) {
                    timesList.add(dto);
                } else if (GoodsTypeEnum.TREATMENT_CARD.getCode().equals(dto.getGoodsType())) {
                    periodList.add(dto);
                } else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(dto.getGoodsType())) {
                    groupCardList.add(dto);
                } else if (GoodsTypeEnum.PRODUCT.getCode().equals(dto.getGoodsType())) {
                    productList.add(dto);
                }
            }
            responseMap.put("timesList", timesList);
            responseMap.put("periodList", periodList);
            responseMap.put("groupCardList", groupCardList);
            responseMap.put("productList", productList);
        }

        responseDTO.setResponseData(responseMap);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }


}
