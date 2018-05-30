package com.wisdom.beauty.controller.archives;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopUserArchivesDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.service.ShopCardService;
import com.wisdom.beauty.core.service.ShopCustomerArchivesService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.core.service.SysUserAccountService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.PinYinSort;
import com.wisdom.common.util.RandomValue;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ArchivesController
 *
 * @Author： huan
 * @Description: 档案控制层
 * @Date:Created in 2018/4/4 9:26
 * @since JDK 1.8
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "archives")
public class ArchivesController {

    @Autowired
    private ShopCustomerArchivesService shopCustomerArchivesService;

    @Autowired
    private SysUserAccountService sysUserAccountService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Resource
    private ShopCardService cardService;

    @Autowired
    private ShopUserRelationService shopUserRelationService;

    @Value("${test.msg}")
    private String msg;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取档案列表或某个店的用户列表
     * @Date:2018/4/8 10:21
     */
    @RequestMapping(value = "/findArchives", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, Object>> findArchives(@RequestParam(required = false) String queryField, @RequestParam(required = false) String sysShopId, @RequestParam(required = false) String pageNo, @RequestParam(required = false) int pageSize) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("获取档案列表或某个店的用户列表传入参数={}", "queryField = [" + queryField + "], sysShopId = [" + sysShopId + "], pageNo = [" + pageNo + "]");
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO = new PageParamVoDTO<>();

        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        //pad端用户
        if (null != clerkInfo && StringUtils.isBlank(sysShopId)) {
            sysShopId = clerkInfo.getSysShopId();
        }
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysShopId(sysShopId);
        shopUserArchivesDTO.setPhone(queryField);
        shopUserArchivesDTO.setSysUserName(queryField);
        if (null != UserUtils.getBossInfo()) {
            shopUserArchivesDTO.setSysBossCode(UserUtils.getBossInfo().getId());
        }
        pageParamVoDTO.setRequestData(shopUserArchivesDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopUserArchivesDTO> shopUserArchivesDTOS = shopCustomerArchivesService.getArchivesList(pageParamVoDTO);

        if (CommonUtils.objectIsEmpty(shopUserArchivesDTOS)) {
            logger.debug("获取档案列表或某个店的用户列表查询结果为空");
            responseDTO.setErrorInfo("获取档案列表或某个店的用户列表查询结果为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }

        ArrayList<Object> lastList = new ArrayList<>();
        for (char a : PinYinSort.getSortType()) {
            HashMap<Object, Object> hashMap = new HashMap<>(16);
            ArrayList<Object> arrayList = new ArrayList<>();
            for (ShopUserArchivesDTO archivesDTO : shopUserArchivesDTOS) {
                if (a == PinYinSort.ToPinYinString(archivesDTO.getSysUserName()).charAt(0)) {
                    arrayList.add(archivesDTO);
                }
            }
            if (arrayList.size() > 0) {
                hashMap.put(String.valueOf(a).toUpperCase(), arrayList);
                lastList.add(hashMap);
            }
        }
        //查询个数
        int count = shopCustomerArchivesService.getArchivesCount(shopUserArchivesDTO);
        Map<String, Object> map = new HashMap<>(16);
        map.put("info", lastList);
        map.put("data", count);

        responseDTO.setResponseData(map);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("获取档案列表或某个店的用户列表,耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 保存用户档案接口
     *
     * @param shopUserArchivesDTO
     * @return
     */
    @RequestMapping(value = "/saveArchiveInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<String> saveArchiveInfo(@RequestBody ShopUserArchivesDTO shopUserArchivesDTO) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("保存用户档案接口传入参数={}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");
        ResponseDTO<String> responseDTO = shopCustomerArchivesService.saveArchiveInfo(shopUserArchivesDTO);

        logger.info("保存用户档案接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


    /**
     * 更新用户档案接口
     *
     * @param shopUserArchivesDTO
     * @return
     */
    @RequestMapping(value = "/updateArchiveInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<String> updateArchiveInfo(@RequestBody ShopUserArchivesDTO shopUserArchivesDTO) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        shopCustomerArchivesService.updateShopUserArchivesInfo(shopUserArchivesDTO);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("更新用户档案接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 删除用户档案接口
     *
     * @param archivesId
     * @return
     */
    @RequestMapping(value = "/deleteArchiveInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<String> deleteArchiveInfo(@RequestParam String archivesId) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        shopCustomerArchivesService.deleteShopUserArchivesInfo(archivesId);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("删除用户档案接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某个用户的档案信息
     *
     * @param sysUserId
     * @return
     */
    @RequestMapping(value = "/getShopUserArchivesInfoByUserId", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getShopUserArchivesInfoByUserId(@RequestParam String sysUserId) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("查询某个用户的档案信息传入参数={}", "sysUserId = [" + sysUserId + "]");
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysUserId(sysUserId);
        shopUserArchivesDTO.setSysShopId(clerkInfo.getSysShopId());
        List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
        responseDTO.setResponseData(shopUserArchivesInfo);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("删除用户档案接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取用户id查询档案信息
     * @Date:2018/4/8
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<CustomerAccountResponseDto> findArchive(@PathVariable String userId) {
        long startTime = System.currentTimeMillis();
        ResponseDTO<CustomerAccountResponseDto> responseDTO = new ResponseDTO<>();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();
        CustomerAccountResponseDto customerAccountResponseDto = sysUserAccountService.getSysAccountListByUserId(userId, sysShopId);
        if (customerAccountResponseDto != null) {
            responseDTO.setResponseData(customerAccountResponseDto);
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findArchive方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据档案ID获取档案信息, 详细信息
     * @Date:2018/4/13 19:29
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> findArchiveById(@PathVariable String id) {
        long startTime = System.currentTimeMillis();
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO = new PageParamVoDTO<>();

        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setId(id);
        pageParamVoDTO.setRequestData(shopUserArchivesDTO);
        //查询数据
        pageParamVoDTO.setPageNo(0);
        List<ShopUserArchivesDTO> list = shopCustomerArchivesService.getArchivesList(pageParamVoDTO);
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();

        if (!CollectionUtils.isEmpty(list)) {
            ShopUserArchivesDTO shopUserArchive = list.get(0);
            ExtShopUserArchivesDTO extShopUserArchivesDTO = new ExtShopUserArchivesDTO();
            BeanUtils.copyProperties(shopUserArchive, extShopUserArchivesDTO);
            //查询用户账户总余额
            ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
            shopUserRechargeCardDTO.setSysUserId(shopUserArchive.getSysUserId());
            shopUserRechargeCardDTO.setSysShopId(shopUserArchive.getSysShopId());
            BigDecimal sumAmount = cardService.getUserRechargeCardSumAmount(shopUserRechargeCardDTO);
            extShopUserArchivesDTO.setTotalBalance(sumAmount.toString());
            //测试挡板
            if (msg.equals(CommonCodeEnum.TRUE.getCode())) {
                extShopUserArchivesDTO.setTotalBalance(String.valueOf(RandomValue.getNum(100, 10000)));
            }
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData(extShopUserArchivesDTO);
        }

        logger.info("findArchiveById方法耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 查询某个用户与店的绑定关系
     * @param openId
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/userBinding", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<String> userBinding(@RequestParam String openId, @RequestParam String shopId) {

        long currentTimeMillis = System.currentTimeMillis();
        logger.info("查询某个用户与店的绑定关系传入参数={}", "openId = [" + openId + "], shopId = [" + shopId + "]");

        ResponseDTO<String> responseDTO = shopUserRelationService.userBinding(openId, shopId);

        logger.info("查询某个用户与店的绑定关系耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

}
