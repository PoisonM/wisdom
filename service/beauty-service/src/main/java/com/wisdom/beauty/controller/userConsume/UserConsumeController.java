package com.wisdom.beauty.controller.userConsume;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UserConsumeController
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/9 18:55
 * @since JDK 1.8
 */
@Controller
public class UserConsumeController {
    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;


    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/4/9 19:05
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findUserConsume(@RequestParam String sysShopId, @RequestParam String shopUserId, @RequestParam String type, int pageSize) {
        PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO = new PageParamVoDTO<>();

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setSysUserId(shopUserId);
        shopUserConsumeRecordDTO.setSysShopId(sysShopId);
        shopUserConsumeRecordDTO.setConsumeType(type);
        pageParamVoDTO.setRequestData(shopUserConsumeRecordDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据业务流水查询具体某个消费信息记录
     * @Date:2018/4/10 11:20
     */
    @RequestMapping(value = "/consume/{consumeFlowNo}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> findUserConsumeDetail(@PathVariable String consumeFlowNo) {

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecord(consumeFlowNo);

        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }
}
