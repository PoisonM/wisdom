package com.wisdom.beauty.controller.clerk;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.requestDto.ShopClerkWorkRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopClerkWorkRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopClerkWorkService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/31.
 * <p>
 * 员工工作业绩相关控制层
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "clerkWork")
public class ClerkWorkRecordController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopClerkWorkService shopClerkWorkService;

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取具体某个店员的业绩和耗卡（包含来源分析）
     * @Date:2018/5/31 15:32
     */
    @RequestMapping(value = "/getClerkWorkDetail", method = {RequestMethod.GET})
    @ResponseBody
    ResponseDTO<Map<String, String>> getClerkWorkDetail(@RequestParam String startTime, @RequestParam String endTime) {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        if (sysClerkDTO == null) {
            logger.info("redis获取sysClerkDTO为空");
            return null;
        }
        PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setStartTime(startTime);
        pageParamVoDTO.setEndTime(endTime);
        ShopClerkWorkRecordRequestDTO shopClerkWorkRecordRequestDTO = new ShopClerkWorkRecordRequestDTO();
        shopClerkWorkRecordRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopClerkWorkRecordRequestDTO.setSysClerkId(sysClerkDTO.getId());

        pageParamVoDTO.setRequestData(shopClerkWorkRecordRequestDTO);

        Map<String, String> map = shopClerkWorkService.getShopConsumeAndRecharge(pageParamVoDTO);
        if (map != null) {
            BigDecimal income = new BigDecimal(map.get("consume")).add(new BigDecimal(map.get("recharge")));
            BigDecimal expenditure = new BigDecimal(map.get("oneConsume")).add(new BigDecimal(map.get("scratchCard")));
            BigDecimal kahao = new BigDecimal(map.get("cardConsume"));

            map.put("income", income.toString());
            map.put("expenditure", expenditure.toString());
            map.put("kahao", kahao.toString());
        }

        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        response.setResponseData(map);
        response.setResult(StatusConstant.SUCCESS);
        return response;
    }

    /**
     * @Author:zhanghuan
     * @Param:   searchFile 1   查业绩明细
     *           searchFile 2   耗卡明细
     *           searchFile 3  卡耗明细
     * @Return:
     * @Description: 获取员工的业绩,耗卡,卡耗明细
     *                  业绩明细: consumeType 0 goodsType 2
     *                         : consumeType 0 goodsType 0 1 3 4
     *                  耗卡明细:consumeType 1 goodsType 1
     *                          consumeType 1 goodsType 3
     *                          consumeType 0 goodsType 0
         *                  卡耗明细:consumeType 1 goodsType2
     * @Date:2018/5/31 15:32
     */
    @RequestMapping(value = "/getClerkPerformanceList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopClerkWorkRecordResponseDTO>> getClerkPerformanceList(@RequestParam String searchFile, int pageSize) {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        ShopClerkWorkRecordRequestDTO shopClerkWorkRecordRequestDTO = new ShopClerkWorkRecordRequestDTO();
        shopClerkWorkRecordRequestDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopClerkWorkRecordRequestDTO.setSysClerkId(sysClerkDTO.getId());
        //设置为true 这样需要通过consumeType和goodType做为条件来查询
        shopClerkWorkRecordRequestDTO.setTypeRequire(true);
        if("1".equals(searchFile)){
            List<String> consumeType=new ArrayList<>();
            consumeType.add(ConsumeTypeEnum.RECHARGE.getCode());
            shopClerkWorkRecordRequestDTO.setConsumeTypeList(consumeType);
            List<String> goodsType=new ArrayList<>();
            goodsType.add(GoodsTypeEnum.TIME_CARD.getCode());
            goodsType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
            goodsType.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
            goodsType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
            goodsType.add(GoodsTypeEnum.PRODUCT.getCode());
            shopClerkWorkRecordRequestDTO.setGoodsTypeList(goodsType);
        }
        if("2".equals(searchFile)){
            shopClerkWorkRecordRequestDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
            List<String> goodsType=new ArrayList<>();
            goodsType.add(GoodsTypeEnum.TIME_CARD.getCode());
            goodsType.add(GoodsTypeEnum.TREATMENT_CARD.getCode());
            goodsType.add(GoodsTypeEnum.COLLECTION_CARD.getCode());
            shopClerkWorkRecordRequestDTO.setGoodsTypeList(goodsType);
            List<String> consumeType=new ArrayList<>();
            consumeType.add(ConsumeTypeEnum.RECHARGE.getCode());
            consumeType.add(ConsumeTypeEnum.CONSUME.getCode());
            shopClerkWorkRecordRequestDTO.setConsumeTypeList(consumeType);
        }
        if("3".equals(searchFile)){
            shopClerkWorkRecordRequestDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
            List<String> goodsType=new ArrayList<>();
            goodsType.add(GoodsTypeEnum.RECHARGE_CARD.getCode());
            shopClerkWorkRecordRequestDTO.setGoodsTypeList(goodsType);
            List<String> consumeType=new ArrayList<>();
            consumeType.add(ConsumeTypeEnum.CONSUME.getCode());
            shopClerkWorkRecordRequestDTO.setConsumeTypeList(consumeType);
        }
        PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setRequestData(shopClerkWorkRecordRequestDTO);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);

        List<ShopClerkWorkRecordResponseDTO> shopClerkWorkRecordResponseDTOs = shopClerkWorkService
                .getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<ShopClerkWorkRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopClerkWorkRecordResponseDTOs);
        return responseDTO;
    }
}
