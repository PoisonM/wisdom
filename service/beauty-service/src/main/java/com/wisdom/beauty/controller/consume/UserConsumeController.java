package com.wisdom.beauty.controller.consume;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.extDto.ShopConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.ShopUserConsumeService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@LoginAnnotations
public class UserConsumeController {
    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Resource
    private ShopUserConsumeService shopUserConsumeService;

    @Value("${test.msg}")
    private String msg;

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/4/9 19:05
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findUserConsume(@RequestBody UserConsumeRequestDTO userConsumeRequest) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        userConsumeRequest.setGoodsTypeRequire(true);
        pageParamVoDTO.setRequestData(userConsumeRequest);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(userConsumeRequest.getPageSize());
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
    @RequestMapping(value = "/consume/consumeFlowNo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> findUserConsumeDetail(@RequestParam String consumeFlowNo) {

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecord(consumeFlowNo);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 更新消费记录，签字确认
     * @Date:2018/4/10 11:20
     */
    @RequestMapping(value = "/consume/updateConsumeRecord", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> updateConsumeRecord(@RequestParam String consumeId, @RequestParam String image) {
        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(consumeId);
        shopUserConsumeRecordDTO.setSignUrl(image);
        int record = shopUerConsumeRecordService.updateConsumeRecord(shopUserConsumeRecordDTO);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(record > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        return responseDTO;
    }

    /**
     * 根据消费主键查询消费详情
     * @param consumeId
     * @return
     */
    @RequestMapping(value = "/consume/{consumeId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> findUserConsumeDetailInfo(@PathVariable("consumeId") String consumeId) {

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(consumeId);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
        if (CommonUtils.objectIsNotEmpty(shopCustomerConsumeRecord)) {
            shopUserConsumeRecordDTO = shopCustomerConsumeRecord.get(0);
        }
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopUserConsumeRecordDTO);
        return responseDTO;
    }

    /**
     * @Param:
     * @Return:
     * @Description: 根据flowId查询具体某个消费信息记录
     * @Date:2018/4/10 11:20
     */
    @RequestMapping(value = "/consume/getUserConsumeByFlowId", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getUserConsumeByFlowId(@RequestParam String flowId, @RequestParam(required = false) String consumeType) {

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        if (msg.equals(CommonCodeEnum.TRUE.getCode())) {
            flowId = "10b939362aca4680b1634718106cf840";
        }
        shopUserConsumeRecordDTO.setFlowId(flowId);
        shopUserConsumeRecordDTO.setConsumeType(consumeType);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopCustomerConsumeRecord);
        return responseDTO;
    }

    /**
     * 用户划疗程卡操作
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumeCourseCard", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> consumeCourseCard(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();
        int cardFlag = shopUserConsumeService.consumeCourseCard(shopUserConsumeDTO.getShopUserConsumeDTO(), clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData("success");
        return responseDTO;
    }


    /**
     * 用户划套卡下的子卡操作
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumesDaughterCard", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> consumesDaughterCard(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();
        List<ShopUserConsumeDTO> userConsumeDTOT = shopUserConsumeDTO.getShopUserConsumeDTO();
        String cardFlag = shopUserConsumeService.consumesDaughterCard(userConsumeDTOT, clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag != null ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData(cardFlag);
        return responseDTO;
    }

    /**
     * 用户领取产品
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumesUserProduct", method = {RequestMethod.POST, RequestMethod.GET})

    public
    @ResponseBody
    ResponseDTO<String> consumesUserProduct(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();
        String cardFlag = shopUserConsumeService.consumesUserProduct(shopUserConsumeDTO.getShopUserConsumeDTO(), clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag != null ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData(cardFlag);
        return responseDTO;
    }


}
