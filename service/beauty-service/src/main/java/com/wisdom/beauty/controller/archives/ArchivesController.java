package com.wisdom.beauty.controller.archives;


import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.service.ShopCustomerArchivesServcie;
import com.wisdom.beauty.core.service.SysUserAccountService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
@RequestMapping(value = "archives")
public class ArchivesController {

    @Autowired
    private ShopCustomerArchivesServcie shopCustomerArchivesServcie;

    @Autowired
    private SysUserAccountService sysUserAccountService;

    @Autowired
    private UserServiceClient userServiceClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description:  获取档案列表
    *@Date:2018/4/8 10:21
    */
    @RequestMapping(value = "/archives", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String,Object>> findArchives(@RequestParam String queryField,@RequestParam String sysShopId,int pageSize ) {
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO =new PageParamVoDTO<> ();

        ShopUserArchivesDTO shopUserArchivesDTO=new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysShopId(sysShopId);
        shopUserArchivesDTO.setPhone(queryField);
        shopUserArchivesDTO.setSysUserName(queryField);


        pageParamVoDTO.setRequestData(shopUserArchivesDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopUserArchivesDTO> list=shopCustomerArchivesServcie.getArchivesList(pageParamVoDTO);
        //查询个数
        int count=shopCustomerArchivesServcie.getArchivesCount(shopUserArchivesDTO);
        Map<String,Object> map=new HashMap<>(16);
        map.put("info",list);
        map.put("data",count);

        ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
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

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        shopUserArchivesDTO.setId(IdGen.uuid());
        //查询用户
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setMobile(shopUserArchivesDTO.getPhone());
        List<UserInfoDTO> userInfoDTOS = userServiceClient.getUserInfo(userInfoDTO);

        logger.debug("保存用户档案接口，查询的用户信息为，{}", "userInfoDTOS = [" + userInfoDTOS + "]");

        if (CommonUtils.objectIsEmpty(userInfoDTOS)) {
            responseDTO.setResponseData(BusinessErrorCode.NULL_PROPERTIES.getCode());
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        userInfoDTO = userInfoDTOS.get(0);
        shopUserArchivesDTO.setSysUserId(userInfoDTO.getId());
        shopUserArchivesDTO.setSysUserName(userInfoDTO.getNickname());
        shopUserArchivesDTO.setSysUserType(userInfoDTO.getUserType());
        shopUserArchivesDTO.setCreateDate(new Date());
        shopUserArchivesDTO.setSysUserId(shopUserArchivesDTO.getSysClerkId());
        shopCustomerArchivesServcie.saveShopUserArchivesInfo(shopUserArchivesDTO);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        shopCustomerArchivesServcie.updateShopUserArchivesInfo(shopUserArchivesDTO);
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
        shopCustomerArchivesServcie.deleteShopUserArchivesInfo(archivesId);
        responseDTO.setResponseData(BusinessErrorCode.SUCCESS.getCode());
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 获取用户id查询档案信息
     *@Date:2018/4/8
    */
    @RequestMapping(value = "/archives/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<CustomerAccountResponseDto> findArchive(@PathVariable String userId) {
        long startTime = System.currentTimeMillis();
        ResponseDTO<CustomerAccountResponseDto> responseDTO = new ResponseDTO<>();
        CustomerAccountResponseDto customerAccountResponseDto = sysUserAccountService.getSysAccountListByUserId(userId);
        if(customerAccountResponseDto!=null){
            responseDTO.setResponseData(customerAccountResponseDto);
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findArchive方法耗时{}毫秒",(System.currentTimeMillis()-startTime));
        return responseDTO;
    }

}
