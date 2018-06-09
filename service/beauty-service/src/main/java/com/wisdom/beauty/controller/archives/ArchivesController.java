package com.wisdom.beauty.controller.archives;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopUserArchivesDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.PinYinSort;
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
    private RedisUtils redisUtils;

    @Resource
    private ShopCardService cardService;

    @Autowired
    private ShopUserRelationService shopUserRelationService;

    @Resource
    private ShopAppointmentService appointmentService;

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

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO = new PageParamVoDTO<>();

        sysShopId = redisUtils.getShopId();
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysShopId(sysShopId);
        shopUserArchivesDTO.setPhone(queryField);
        shopUserArchivesDTO.setSysUserName(queryField);
        if (null != UserUtils.getBossInfo()) {
            shopUserArchivesDTO.setSysBossCode(UserUtils.getBossInfo().getId());
        }
        pageParamVoDTO.setRequestData(shopUserArchivesDTO);
        pageParamVoDTO.setPaging(true);
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
        logger.info("查询用户的档案信息个数为={}",shopUserArchivesDTOS.size());
        //获取用户最近一次预约时间
        ShopAppointServiceDTO serviceDTO = new ShopAppointServiceDTO();
        serviceDTO.setSysShopId(sysShopId);
        List<ExtShopAppointServiceDTO> extShopAppointServiceDTOS = appointmentService.selectShopUserLastAppointInfo(serviceDTO);
        List<ExtShopUserArchivesDTO> extShopUserArchivesDTOS = new ArrayList<>();
        for(ShopUserArchivesDTO archivesDTO :shopUserArchivesDTOS){
            ExtShopUserArchivesDTO extShopUserArchivesDTO = new ExtShopUserArchivesDTO();
            BeanUtils.copyProperties(archivesDTO,extShopUserArchivesDTO);
            if(CommonUtils.objectIsNotEmpty(extShopAppointServiceDTOS)){
                logger.info("查询店铺的预约信息个数为={}",extShopAppointServiceDTOS.size());
                for(ExtShopAppointServiceDTO shopAppointServiceDTO : extShopAppointServiceDTOS){
                    if(shopAppointServiceDTO.getSysUserId().equals(archivesDTO.getSysUserId())){
                        extShopUserArchivesDTO.setLastAppointTimes(DateUtils.DateToStr(shopAppointServiceDTO.getAppointStartTime(),"datetime"));
                    }
                }
            }
            extShopUserArchivesDTOS.add(extShopUserArchivesDTO);
        }

        ArrayList<Object> lastList = new ArrayList<>();
        for (char a : PinYinSort.getSortType()) {
            HashMap<Object, Object> hashMap = new HashMap<>(16);
            ArrayList<Object> arrayList = new ArrayList<>();
            for (ExtShopUserArchivesDTO dto : extShopUserArchivesDTOS) {
                if (a == PinYinSort.ToPinYinString(dto.getSysUserName()).toLowerCase().charAt(0)) {
                    arrayList.add(dto);
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

        ResponseDTO<String> responseDTO = shopCustomerArchivesService.saveArchiveInfo(shopUserArchivesDTO);

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
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        shopCustomerArchivesService.updateShopUserArchivesInfo(shopUserArchivesDTO);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        shopCustomerArchivesService.deleteShopUserArchivesInfo(archivesId);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysUserId(sysUserId);
        shopUserArchivesDTO.setSysShopId(clerkInfo.getSysShopId());
        List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
        responseDTO.setResponseData(shopUserArchivesInfo);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ResponseDTO<CustomerAccountResponseDto> responseDTO = new ResponseDTO<>();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();
        CustomerAccountResponseDto customerAccountResponseDto = sysUserAccountService.getSysAccountListByUserId(userId, sysShopId);
        if (customerAccountResponseDto != null) {
            responseDTO.setResponseData(customerAccountResponseDto);
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
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
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData(extShopUserArchivesDTO);
        }
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
    ResponseDTO<String> userBinding(@RequestParam String openId, @RequestParam(required = false) String shopId) {
        ResponseDTO<String> responseDTO = shopUserRelationService.userBinding(openId, shopId);
        return responseDTO;
    }


}
