package com.wisdom.beauty.controller.card;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopRechargeCardService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "cardInfo")
public class CardController {

    @Resource
    private ShopCardService cardService;

    @Autowired
    private ShopRechargeCardService shopRechargeCardService;

    @Autowired
    private ShopProjectGroupService shopProjectGroupService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个用户的充值卡列表信息
     *
     * @param sysUserId
     * @param sysShopId
     * @return
     */
    @RequestMapping(value = "/getUserRechargeCardList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopUserRechargeCardDTO>> getUserRechargeCardList(@RequestParam String sysUserId,
                                                                       @RequestParam String sysShopId) {
        long currentTimeMillis = System.currentTimeMillis();

        logger.info("查询某个用户的充值卡列表信息传入参数={}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
        ResponseDTO<List<ShopUserRechargeCardDTO>> responseDTO = new ResponseDTO<>();

        if (StringUtils.isBlank(sysUserId) || StringUtils.isBlank(sysShopId)) {
            logger.debug("传入参数为空， {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
            return null;
        }

        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        shopUserRechargeCardDTO.setSysUserId(sysUserId);
        shopUserRechargeCardDTO.setSysShopId(sysShopId);
        List<ShopUserRechargeCardDTO> userRechargeCardList = cardService.getUserRechargeCardList(shopUserRechargeCardDTO);
        if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
            logger.debug("查询某个用户的充值卡列表信息查询结果为空，参数 {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo(BusinessErrorCode.ERROR_NULL_RECORD.getCode());
            return responseDTO;
        }

        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userRechargeCardList);

        logger.info("查询某个用户的充值卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 查询充值卡列表
     * @Date:2018/4/11 13:58
     */
    @RequestMapping(value = "/getRechargeCardList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopRechargeCardResponseDTO>> findRechargeCardList(@RequestParam(required = false) String name,
                                                                                                        int pageSize) {
        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopRechargeCardDTO shopRechargeCardDTO = new ShopRechargeCardDTO();
        shopRechargeCardDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopRechargeCardDTO.setName(name);

        pageParamVoDTO.setRequestData(shopRechargeCardDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopRechargeCardResponseDTO> list = shopRechargeCardService.getShopRechargeCardList(pageParamVoDTO);
        ResponseDTO<List<ShopRechargeCardResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询充值卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取具体充值卡的信息
     * @Date:2018/4/11 14:16
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopRechargeCardResponseDTO> findRechargeCard(@PathVariable String id) {
        long currentTimeMillis = System.currentTimeMillis();
        //查询数据
        ShopRechargeCardResponseDTO shopRechargeCardResponseDTO = shopRechargeCardService.getShopRechargeCard(id);

        ResponseDTO<ShopRechargeCardResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopRechargeCardResponseDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询某个充值卡信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取某个店里的套卡列表
     * @Date:2018/4/11 15:40
     */
    @RequestMapping(value = "/getShopProjectGroups", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProjectGroupDTO>> findShopProjectGroupList(@RequestParam(required = false) String projectGroupName,int pageSize) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopProjectGroupDTO shopProjectGroupDTO = new ShopProjectGroupDTO();
        shopProjectGroupDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopProjectGroupDTO.setProjectGroupName(projectGroupName);

        pageParamVoDTO.setRequestData(shopProjectGroupDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopProjectGroupDTO> list = shopProjectGroupService.getShopProjectGroupList(pageParamVoDTO);

        ResponseDTO<List<ShopProjectGroupDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询套卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取某个店里的具体套卡的信息
     * @Date:2018/4/11 15:40
     */
    @RequestMapping(value = "/getShopProjectGroup/detail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ProjectInfoGroupResponseDTO> findShopProjectGroupListe(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();

        ProjectInfoGroupResponseDTO projectInfoGroupResponseDTO = shopProjectGroupService.getShopProjectInfoGroupRelation(id);

        ResponseDTO<ProjectInfoGroupResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(projectInfoGroupResponseDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询套卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}
