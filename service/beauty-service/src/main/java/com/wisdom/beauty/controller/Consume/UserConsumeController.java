package com.wisdom.beauty.controller.Consume;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.ConsumeTypeEnum;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopUserConsumeRecordDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/4/9 19:05
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findUserConsume(@RequestParam String sysShopId,
                                                                    @RequestParam String shopUserId,
                                                                    @RequestParam String consumeType, int pageSize) {

        long startTime = System.currentTimeMillis();
        PageParamVoDTO<ShopUserConsumeRecordDTO> pageParamVoDTO = new PageParamVoDTO<>();

        ShopUserConsumeRecordDTO shopUserConsumeRecordDTO = new ShopUserConsumeRecordDTO();
        shopUserConsumeRecordDTO.setSysUserId(shopUserId);
        shopUserConsumeRecordDTO.setSysShopId(sysShopId);
        shopUserConsumeRecordDTO.setConsumeType(consumeType);

        pageParamVoDTO.setRequestData(shopUserConsumeRecordDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findUserConsume方法耗时{}毫秒",(System.currentTimeMillis()-startTime));
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
        long startTime = System.currentTimeMillis();

        UserConsumeRecordResponseDTO userConsumeRecordResponseDTO = shopUerConsumeRecordService.getShopCustomerConsumeRecord(consumeFlowNo);
        ResponseDTO<UserConsumeRecordResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        logger.info("findUserConsumeDetail方法耗时{}毫秒",(System.currentTimeMillis()-startTime));
        return responseDTO;
    }


    /**
     * 用户充值操作，适用范围为消费单次卡、疗程卡、套卡、产品
     *
     * @param extShopUserConsumeRecordDTOS
     * @return
     */
    @RequestMapping(value = "userRechargeOperation", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    @Transactional
    ResponseDTO<String> userRechargeOperation(@RequestBody List<ExtShopUserConsumeRecordDTO> extShopUserConsumeRecordDTOS) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("传入参数={}", "shopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        if (CommonUtils.objectIsEmpty(extShopUserConsumeRecordDTOS)) {
            logger.debug("用户充值操作传入参数为空, {}", "shopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");
            return null;
        }
        //生成唯一的交易流水号
        String transactionCodeNumber = getTransactionCodeNumber();

        try {
            //遍历记录
            for (ExtShopUserConsumeRecordDTO dto : extShopUserConsumeRecordDTOS) {
                //虚拟商品类型
                String goodsType = dto.getGoodsType();

                //获取用户的账户信息
                SysUserAccountDTO sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(dto.getSysUserId());

                //如果是次卡、疗程卡相关操作
                if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {

                    if (dto.getShopUserProjectRelationDTO() == null) {
                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
                        throw new RuntimeException();
                    }
                    //生成用户与项目的关系
                    ShopUserProjectRelationDTO shopUserRelationDTO = dto.getShopUserProjectRelationDTO();
                    shopUserRelationDTO.setCreateDate(new Date());
                    String uuid = IdGen.uuid();
                    shopUserRelationDTO.setId(uuid);
                    shopUserRelationDTO.setSysShopName(dto.getSysShopName());
                    //流水id
                    dto.setFlowId(uuid);
                    dto.setFlowName(GoodsTypeEnum.TIME_CARD.getDesc());
                    //如果用充值卡抵扣的话
                    if (null != dto.getShopUserRechargeCardDTO()) {
                        ShopUserRechargeCardDTO shopUserRechargeCardDTO = dto.getShopUserRechargeCardDTO();
                        if (StringUtils.isBlank(shopUserRechargeCardDTO.getId())) {
                            logger.error("用户充值操作,充值卡参数异常{}", "extShopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");
                            throw new RuntimeException();
                        }
                        ShopUserConsumeRecordDTO userConsumeRecordDTO = new ShopUserConsumeRecordDTO();
                        userConsumeRecordDTO.setFlowId(dto.getShopUserRechargeCardDTO().getId());
                        shopUserConsumeService.userConsumeRechargeCard(userConsumeRecordDTO);
                    }
                    shopProjectService.saveUserProjectRelation(shopUserRelationDTO);
                }
                //如果是套卡相关操作
                else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
                    if (dto.getShopProjectGroupDTO() == null) {
                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
                        throw new RuntimeException();
                    }
                    ShopProjectGroupDTO shopProjectGroupDTO = dto.getShopProjectGroupDTO();

                    //根据套卡id查询项目列表
                    ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                    shopProjectInfoGroupRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
                    shopProjectInfoGroupRelationDTO.setSysShopId(shopProjectInfoGroupRelationDTO.getSysShopId());
                    List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);

                    if (null == groupRelations) {
                        logger.error("根据项目套卡主键查询出来的项目列表为空，{}", groupRelations);
                        throw new RuntimeException();
                    }
                    //如果套卡能买多套
                    for (int i = 0; i < dto.getConsumeNumber(); i++) {
                        //生成用户跟套卡与项目的关系的关系
                        for (ShopProjectInfoGroupRelationDTO dt : groupRelations) {
                            //查询项目信息
                            ShopProjectInfoDTO shopProjectInfoDTO = redisUtils.getShopProjectInfoFromRedis(dt.getShopProjectInfoId());
                            ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                            groupRelRelationDTO.setSysShopId(dto.getSysShopId());
                            groupRelRelationDTO.setSysUserId(dto.getSysUserId());
                            groupRelRelationDTO.setId(IdGen.uuid());
                            groupRelRelationDTO.setProjectInitAmount(shopProjectInfoDTO.getMarketPrice());
                            groupRelRelationDTO.setProjectInitTimes(shopProjectInfoDTO.getMaxContainTimes());
                            groupRelRelationDTO.setProjectSurplusAmount(shopProjectInfoDTO.getMarketPrice());
                            groupRelRelationDTO.setProjectSurplusTimes(shopProjectInfoDTO.getMaxContainTimes());
                            groupRelRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
                            groupRelRelationDTO.setShopProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
                            groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
                            groupRelRelationDTO.setSysBossId(shopProjectInfoDTO.getSysBossId());
                            shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                        }
                    }
                    dto.setFlowId(shopProjectGroupDTO.getId());
                    dto.setFlowName(GoodsTypeEnum.COLLECTION_CARD.getDesc());
                }
                //如果是产品相关
                else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
                    //生成用户跟产品的关系
                    ShopUserProductRelationDTO userProductRelationDTO = dto.getShopUserProductRelationDTO();
                    String uuid = IdGen.uuid();
                    userProductRelationDTO.setId(uuid);
                    dto.setFlowId(uuid);
                    dto.setFlowName(GoodsTypeEnum.PRODUCT.getCode());
                }
                //dto.getPrice()为此次交易的总价格
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(dto.getPrice()));

                //更新用户的账户信息
                try {
                    sysUserAccountDTO.setFlowNo(transactionCodeNumber);
                    sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
                } catch (Exception e) {
                    logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
                    throw new RuntimeException();
                }
                //记录店员的流水信息
                shopClerkService.saveSysClerkFlowAccountInfo(dto);

                dto.setFlowNo(transactionCodeNumber);
                dto.setOperDate(new Date());
                dto.setConsumeType(ConsumeTypeEnum.CONSUME.getCode());
                int record = shopUerConsumeRecordService.saveCustomerConsumeRecord(dto);
                logger.debug("保存用户充值或消费操作返回结果 {}", record > 0 ? "成功" : "失败");
            }
        } catch (Exception e) {
            logger.error("充值失败，失败原因为，{}" + e.getMessage(), e);
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData("failure");
        }
        //保存用户的操作记录
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData("success");
        logger.info("用户充值操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
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
                SysUserAccountDTO sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(dto.getSysUserId());

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


}
