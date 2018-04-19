package com.wisdom.beauty.controller.mine;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;
import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MineController
 *
 * @Author： huan
 * @Description: 我的相关控制层
 * @Date:Created in 2018/4/17 14:19
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "mine")
public class MineController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Autowired
    private ShopCustomerProductRelationService shopCustomerProductRelationService;

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 划卡记录对应后台的消费---状态是1,此时不需要goodType
     * 充值记录，消费记录对应后台的充值---状态是0，并且充值记录的goodType--2
     * @Date:2018/4/17 14:45
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findMineConsume(@RequestParam String sysShopId,
                                                                    @RequestParam String sysClerkId,
                                                                    @RequestParam(required = false) String goodType,
                                                                    @RequestParam String consumeType, int pageSize) {

        long startTime = System.currentTimeMillis();
        PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO = new PageParamVoDTO<>();

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setSysShopId(sysShopId);
        shopUserConsumeRecordDTO.setSysClerkId(sysClerkId);
        shopUserConsumeRecordDTO.setConsumeType(consumeType);
        shopUserConsumeRecordDTO.setGoodsType(goodType);

        pageParamVoDTO.setRequestData(shopUserConsumeRecordDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findMineConsume方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
    @RequestMapping(value = "/getProductRecord", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String,Object>> getProductRecord(@RequestParam String sysClerkId,
                                                     @RequestParam String sysShopId,
                                                     @RequestParam(required = false) String searchFile) {

        long startTime = System.currentTimeMillis();
        Map<String,Object> map=shopCustomerProductRelationService.getShopUserProductRelations(sysClerkId,sysShopId,searchFile);
        ResponseDTO<Map<String,Object>>  responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(map);
        logger.info("getProductRecord方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 根据用户查询该用户待领取产品结果
    *@Date:2018/4/19 9:46
    */
    @RequestMapping(value = "/getWaitReceiveDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserProductRelationResponseDTO>> getProductRecord(@RequestParam String sysUserId,
                                                                       @RequestParam String sysShopId) {

        long startTime = System.currentTimeMillis();
        List<UserProductRelationResponseDTO> list=shopCustomerProductRelationService.getShopUserProductRelationList(sysUserId,sysShopId);
        ResponseDTO<List<UserProductRelationResponseDTO>>  responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        logger.info("getProductRecord方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }
}
