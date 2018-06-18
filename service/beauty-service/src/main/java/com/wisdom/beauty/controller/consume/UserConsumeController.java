package com.wisdom.beauty.controller.consume;

import com.wisdom.beauty.api.dto.ShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
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
import com.wisdom.common.util.RedisLock;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/4/9 19:05
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findUserConsume(
            @RequestBody UserConsumeRequestDTO userConsumeRequest) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        userConsumeRequest.setGoodsTypeRequire(true);
        pageParamVoDTO.setRequestData(userConsumeRequest);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(userConsumeRequest.getPageSize());
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getShopCustomerConsumeRecordList(pageParamVoDTO);

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

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getShopCustomerConsumeRecord(consumeFlowNo);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: id 消费记录表中的主键
     * @Return:
     * @Description: 根据消费记录id查询具体某个消费详情,(套卡的消费详情)
     * @Date:2018/6/4 14:52
     */
    @RequestMapping(value = "/consume/id", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> userGroupCardConsumeDetail(@RequestParam String id) {

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getUserGroupCardConsumeDetail(id);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取疗程卡的消费详情
     * @Date:2018/6/4 19:59
     */
    @RequestMapping(value = "/consume/flowId", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> treatmentConsumeDetail(@RequestParam String flowId) {

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getTreatmentCardConsumeDetail(flowId);
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
    ResponseDTO<UserConsumeRecordResponseDTO> updateConsumeRecord(@RequestParam String consumeId,
                                                                  @RequestParam String image) {
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
     *
     * @param consumeId
     * @return
     */
    @RequestMapping(value = "/consume/{consumeId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> findUserConsumeDetailInfo(@PathVariable("consumeId") String consumeId) {

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(consumeId);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService
                .getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
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
    ResponseDTO<Object> getUserConsumeByFlowId(@RequestParam String flowId,
                                               @RequestParam(required = false) String consumeType) {

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setFlowId(flowId);
        shopUserConsumeRecordDTO.setConsumeType(consumeType);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService
                .getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
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
    ResponseDTO<String> consumeCourseCard(
            @RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        ShopUserConsumeDTO consumeDTO = shopUserConsumeDTO.getShopUserConsumeDTO().get(0);
        if(StringUtils.isBlank(consumeDTO.getConsumeId()) || consumeDTO.getConsumeNum() <= 0){
            responseDTO.setErrorInfo("传入数据异常");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();

        RedisLock redisLock = null;
        int cardFlag = 0;
        try {
            redisLock = new RedisLock("consumeCourseCard:"+consumeDTO.getConsumeId());
            redisLock.lock();
            cardFlag = shopUserConsumeService.consumeCourseCard(shopUserConsumeDTO.getShopUserConsumeDTO(), clerkInfo);
        } catch (Exception e) {
            logger.error("用户划疗程卡操作,操作异常，异常信息为={}"+e.getMessage(),e);
        } finally {
            redisLock.unlock();
        }
        // 保存用户的操作记录
        responseDTO.setResult(cardFlag > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setErrorInfo(cardFlag == 2?"无可用卡":"");
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
    ResponseDTO<String> consumesDaughterCard(
            @RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();
        List<ShopUserConsumeDTO> userConsumeDTOT = shopUserConsumeDTO.getShopUserConsumeDTO();
        if(StringUtils.isBlank(userConsumeDTOT.get(0).getConsumeId()) || userConsumeDTOT.get(0).getConsumeNum() <= 0){
            responseDTO.setErrorInfo("传入数据异常");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        String cardFlag = shopUserConsumeService.consumesDaughterCard(userConsumeDTOT, clerkInfo);
        // 保存用户的操作记录
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
    ResponseDTO<String> consumesUserProduct(@RequestBody ShopUserConsumeDTO shopUserConsumeDTO) {

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isBlank(shopUserConsumeDTO.getConsumeId()) || shopUserConsumeDTO.getConsumeNum() <= 0){
            responseDTO.setErrorInfo("传入数据异常！");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        responseDTO = shopUserConsumeService.consumesUserProduct(shopUserConsumeDTO, clerkInfo);
        return responseDTO;
    }

    /**
     * @Param:
     * @Return:
     * @Description: 根据疗程卡Id获取疗程和套卡的划卡记录
     * 套卡查询需要传递flowIds goodsType 疗程卡查询需要传递flowId goodsType
     * @Date:2018/4/10 11:20
     */
    @RequestMapping(value = "/consume/treatmentAndGroupCardRecordList", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> getUserConsumeByFlowId(
            @RequestBody UserConsumeRequestDTO userConsumeRequestDTO) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        List<UserConsumeRecordResponseDTO> list = shopUerConsumeRecordService
                .getTreatmentAndGroupCardRecord(pageParamVoDTO);
        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        return responseDTO;
    }

    /**
     * @Param:
     * @Return:
     * @Description: 获取用户的的划卡记录
     * @Date:2018/4/10 11:20
     */
    @RequestMapping(value = "/consume/userStampCardRecordList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> getUserStampCardRecord(@RequestParam String sysUserId) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
        UserConsumeRequestDTO userConsumeRequestDTO = new UserConsumeRequestDTO();
        userConsumeRequestDTO.setSysUserId(sysUserId);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        List<UserConsumeRecordResponseDTO> list = shopUerConsumeRecordService.getUserStampCardRecord(pageParamVoDTO);
        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param: userConsumeRequestDTO
     * goodsType =4
     * @Return:
     * @Description: flowId查询产品的领取记录。flowId是产品和用户关联主键
     * @Date:2018/6/12 14:33
     */
    @RequestMapping(value = "/consume/getProductDrawRecord", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> getProductDrawRecordByFlowId(@RequestBody UserConsumeRequestDTO userConsumeRequestDTO) {

        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO();
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        List<UserConsumeRecordResponseDTO> list = shopUerConsumeRecordService
                .getTreatmentAndGroupCardRecord(pageParamVoDTO);
        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        return responseDTO;
    }
    /**
    *@Author:zhanghuan
    *@Param:  flowId  goodsType  consumeType
    *@Return:
    *@Description: 获取产品的消费详情和充值卡的订单详情
    *@Date:2018/6/12 16:10
    */
    @RequestMapping(value = "/consume/productAndRechargeCard/getConsumeDetail", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<UserConsumeRecordResponseDTO> getProductConsumeDetailByFlowId(@RequestBody UserConsumeRequestDTO userConsumeRequestDTO) {

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getProductConsumeDetailByFlowId(userConsumeRequestDTO);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }
    /**
    *@Author:zhanghuan
    *@Param:  goodsType=2   consumeType=0   flowId(需要前端传递参数)
    *@Return:
    *@Description: 获取特殊类型充值卡的充值记录
    *@Date:2018/6/13 9:40
    */
    @RequestMapping(value = "/consume/getRechargeRecord", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> getRechargeRecord(@RequestParam String flowId,int pageSize) {
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        UserConsumeRequestDTO userConsumeRequestDTO=new UserConsumeRequestDTO();
        userConsumeRequestDTO.setGoodsTypeRequire(true);
        userConsumeRequestDTO.setConsumeType(ConsumeTypeEnum.RECHARGE.getCode());
        userConsumeRequestDTO.setGoodsType(GoodsTypeEnum.COLLECTION_CARD.getCode());
        userConsumeRequestDTO.setGoodsTypeRequire(true);
        userConsumeRequestDTO.setFlowId(flowId);
        pageParamVoDTO.setRequestData(userConsumeRequestDTO);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);

        List<UserConsumeRecordResponseDTO> list = shopUerConsumeRecordService
                .getShopCustomerConsumeRecordList(pageParamVoDTO);
        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        return responseDTO;
    }
}
