package com.wisdom.beauty.controller.consume;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ShopConsumeDTO;
import com.wisdom.beauty.api.extDto.ShopUserConsumeDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.wisdom.common.util.CodeGenUtil.getTransactionCodeNumber;

/**
 * ClassName: UserConsumeController
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/9 18:55
 * @since JDK 1.8
 */
@Controller
@LoginRequired
public class UserConsumeController {
    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Resource
    private SysUserAccountService sysUserAccountService;

    @Resource
    private ShopProjectService shopProjectService;

    @Resource
    private ShopUserConsumeService shopUserConsumeService;

    @Resource
    private ShopClerkService shopClerkService;

    @Resource
    private ShopProductInfoService shopProductInfoService;

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    @Resource
    private RedisUtils redisUtils;

    Logger logger = LoggerFactory.getLogger(this.getClass());

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

        long startTime = System.currentTimeMillis();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();

        userConsumeRequest.setGoodsTypeRequire(true);

        pageParamVoDTO.setRequestData(userConsumeRequest);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(userConsumeRequest.getPageSize());
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findUserConsume方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
        long startTime = System.currentTimeMillis();

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecord(consumeFlowNo);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findUserConsumeDetail方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
        long startTime = System.currentTimeMillis();

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setId(consumeId);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);

        if (CommonUtils.objectIsNotEmpty(shopCustomerConsumeRecord)) {
            shopUserConsumeRecordDTO = shopCustomerConsumeRecord.get(0);
        }
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopUserConsumeRecordDTO);

        logger.info("findUserConsumeDetail方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
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
    ResponseDTO<Object> getUserConsumeByFlowId(@RequestParam String flowId) {
        long startTime = System.currentTimeMillis();

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        if (msg.equals(CommonCodeEnum.TRUE.getCode())) {
            flowId = "10b939362aca4680b1634718106cf840";
        }
        shopUserConsumeRecordDTO.setFlowId(flowId);
        List<ShopUserConsumeRecordDTO> shopCustomerConsumeRecord = shopUerConsumeRecordService.getShopCustomerConsumeRecord(shopUserConsumeRecordDTO);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(shopCustomerConsumeRecord);

        logger.info("findUserConsumeDetail方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 用户消费操作，适用范围为消费单次卡、疗程卡、套卡、产品
     *
     * @param shopUserConsumeRecordDTOS
     * @return
     */
    @RequestMapping(value = "userConsumeOperation", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    @Transactional
    ResponseDTO<String> userConsumeOperation(@RequestBody List<ShopUserConsumeRecordDTO> shopUserConsumeRecordDTOS) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("用户消费操作传入参数={}", "shopUserConsumeRecordDTOS = [" + shopUserConsumeRecordDTOS + "]");

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        if (CommonUtils.objectIsEmpty(shopUserConsumeRecordDTOS)) {
            logger.debug("用户消费操作传入参数为空, {}", "shopUserConsumeRecordDTOS = [" + shopUserConsumeRecordDTOS + "]");
            return null;
        }
        //生成唯一的交易流水号
        String transactionCodeNumber = getTransactionCodeNumber();

        try {
            //遍历记录
            for (ShopUserConsumeRecordDTO dto : shopUserConsumeRecordDTOS) {

                if (dto.getConsumeNumber() == null) {
                    logger.error("用户消费数量为空 {}", "consumeNumber = [" + dto.getConsumeNumber() + "]");
                    throw new RuntimeException("用户的消费数量为空！");
                }
                if (StringUtils.isBlank(dto.getFlowId())) {
                    logger.error("用户与消费项关联id为空，{}", "flowId = [" + dto.getFlowId() + "]");
                    throw new RuntimeException("用户与消费项关联id为空");
                }

                //虚拟商品类型
                String goodsType = dto.getGoodsType();

                //获取用户的账户信息
                SysUserAccountDTO sysUserAccountDTO = new SysUserAccountDTO();
                sysUserAccountDTO.setSysUserId(dto.getSysUserId());
                sysUserAccountDTO.setSysShopId(dto.getSysShopId());
                sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(sysUserAccountDTO);

                //如果是次卡、疗程卡相关操作
                if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {
                    //根据主键查询用户与项目的关系
                    ShopUserProjectRelationDTO shopUserRelationDTO = new ShopUserProjectRelationDTO();
                    shopUserRelationDTO.setId(dto.getFlowId());
                    shopUserRelationDTO = shopProjectService.getUserProjectList(shopUserRelationDTO).get(0);
                    //剩余价格=剩余价格-消费价格
                    BigDecimal surplusAmount = shopUserRelationDTO.getSysShopProjectSurplusAmount();
                    shopUserRelationDTO.setSysShopProjectSurplusAmount(surplusAmount.subtract(dto.getPrice()));
                    //剩余次数=剩余次数-消费次数
                    Integer surplusTimes = shopUserRelationDTO.getSysShopProjectSurplusTimes();
                    shopUserRelationDTO.setSysShopProjectSurplusTimes(surplusTimes - dto.getConsumeNumber());
                    //更新用户与项目的关系表
                    shopUserRelationDTO.setUpdateUser(dto.getSysClerkId());
                    shopProjectService.updateUserAndProjectRelation(shopUserRelationDTO);
                }
                //如果是套卡相关操作
                else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
                    //根据flowId查询用户与套卡项目表关系的关系
                    ShopUserProjectGroupRelRelationDTO relRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                    relRelationDTO.setId(dto.getFlowId());
                    ShopUserProjectGroupRelRelationDTO groupRelRelation = shopProjectGroupService.getShopUserProjectGroupRelRelation(relRelationDTO).get(0);
                    //剩余价格=剩余价格-消费价格
                    BigDecimal surplusAmount = groupRelRelation.getProjectSurplusAmount().subtract(dto.getPrice());
                    groupRelRelation.setProjectSurplusAmount(surplusAmount);
                    //剩余次数=剩余次数-消费次数
                    int surplusTimes = groupRelRelation.getProjectSurplusTimes() - dto.getConsumeNumber();
                    groupRelRelation.setProjectSurplusTimes(surplusTimes);
                    //更新用户与项目的关系表
                    groupRelRelation.setUpdateUser(dto.getSysClerkId());
                    shopProjectGroupService.updateShopUserProjectGroupRelRelation(groupRelRelation);
                }
                //如果是产品相关
                else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
                    //根据flowId查询用户与套卡项目表关系的关系
                    ShopUserProductRelationDTO productRelationDTO = new ShopUserProductRelationDTO();
                    productRelationDTO.setId(dto.getFlowId());
                    productRelationDTO = shopProductInfoService.getUserProductInfoList(productRelationDTO).get(0);
                    //剩余价格=剩余价格-消费价格
                    BigDecimal surplusAmount = productRelationDTO.getSurplusAmount().subtract(dto.getPrice());
                    productRelationDTO.setSurplusAmount(surplusAmount);
                    //剩余次数=剩余次数-消费次数
                    int surplusTimes = productRelationDTO.getSurplusTimes() - dto.getConsumeNumber();
                    productRelationDTO.setSurplusTimes(surplusTimes);
                    //更新用户与产品的关系表
                    shopProductInfoService.updateShopUserProductRelation(productRelationDTO);
                }
                //dto.getPrice()为此次交易的总价格
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().subtract(dto.getPrice()));

                //更新用户的账户信息
                try {
                    sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
                } catch (Exception e) {
                    logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
                    throw new RuntimeException();
                }

                //记录店员的流水信息
                shopClerkService.saveSysClerkFlowAccountInfo(dto);

                dto.setFlowNo(transactionCodeNumber);
                int record = shopUerConsumeRecordService.saveCustomerConsumeRecord(dto);
                logger.debug("保存用户充值或消费操作返回结果 {}", record > 0 ? "成功" : "失败");
            }
        } catch (Exception e) {
            logger.error("充值失败，失败原因为，{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
        //保存用户的操作记录
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData("success");
        logger.info("用户充值操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 用户划疗程卡操作
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumeCourseCard", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> consumeCourseCard(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("用户划疗程卡操作传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTO + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();

        int cardFlag = shopUserConsumeService.consumeCourseCard(shopUserConsumeDTO.getShopUserConsumeDTO(), clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData("success");
        logger.info("用户充值操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


    /**
     * 用户划套卡下的子卡操作
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumesDaughterCard", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> consumesDaughterCard(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("用户划套卡下的子卡操作传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTO + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();

        List<ShopUserConsumeDTO> userConsumeDTOT = shopUserConsumeDTO.getShopUserConsumeDTO();
        int cardFlag = shopUserConsumeService.consumesDaughterCard(userConsumeDTOT, clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData("success");
        logger.info("用户划套卡下的子卡操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 用户领取产品
     *
     * @param shopUserConsumeDTO
     * @return
     */
    @RequestMapping(value = "/consumes/consumesUserProduct", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<String> consumesUserProduct(@RequestBody ShopConsumeDTO<List<ShopUserConsumeDTO>> shopUserConsumeDTO) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("用户领取产品操作传入参数={}", "shopUserConsumeDTO = [" + shopUserConsumeDTO + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO responseDTO = new ResponseDTO();

        int cardFlag = shopUserConsumeService.consumesUserProduct(shopUserConsumeDTO.getShopUserConsumeDTO(), clerkInfo);
        //保存用户的操作记录
        responseDTO.setResult(cardFlag > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
        responseDTO.setResponseData("success");
        logger.info("用户领取产品操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


}
