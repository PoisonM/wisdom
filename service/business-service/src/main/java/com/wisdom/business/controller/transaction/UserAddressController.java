package com.wisdom.business.controller.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.transaction.UserOrderAddressService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 微信页面参数获取相关的控制类
 * Created by baoweiw on 2015/7/27.
 */

@Controller
@RequestMapping(value = "transaction")
public class UserAddressController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserOrderAddressService userOrderAddressService;

    @Autowired
    private UserServiceClient userServiceClient;

    /**
     * 根据用户的所有收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "userAddressList", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<UserOrderAddressDTO>> userAddressList() {
        long startTime = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("获取用户=={}的所有收货地址==={}开始",userInfoDTO.getId(),startTime);
        ResponseDTO<List<UserOrderAddressDTO>> responseDTO = new ResponseDTO<>();
        List<UserOrderAddressDTO> userOrderAddressDTOList =  userOrderAddressService.getUserAddressList(userInfoDTO.getId());
        logger.info("获取用户的所有收货地址List==={}",userOrderAddressDTOList.size());
        responseDTO.setResponseData(userOrderAddressDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("获取用户的所有收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 根据用户id查询收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "findUserAddressById", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> findUserAddressById(@RequestParam String addressId) {
        long startTime = System.currentTimeMillis();
        logger.info("根据用户id查询收货地址id={},时间==={}开始",addressId,startTime);
        ResponseDTO<UserOrderAddressDTO> responseDTO = new ResponseDTO<>();
        UserOrderAddressDTO userOrderAddressDTO =  userOrderAddressService.findUserAddressById(addressId);
        responseDTO.setResponseData(userOrderAddressDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("根据用户id查询收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 添加用户收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO<List<ProductDTO>>
     *
     */
    @RequestMapping(value = "addUserAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO addUserAddress(@RequestBody UserOrderAddressDTO userOrderAddressDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("添加用户收货地址,时间==={}开始",startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressDTO.setId(UUID.randomUUID().toString());
            userOrderAddressService.addUserAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("添加用户地址成功");
        }catch (Exception e)
        {
            logger.error("添加用户收货地址异常,异常信息为{}"+e.getMessage(),e);
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("添加用户地址失败");
        }
        logger.info("添加用户收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 修改用户的收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO
     *
     */
    @RequestMapping(value = "updateUserAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO updateUserAddress(@RequestBody UserOrderAddressDTO userOrderAddressDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("更新用户的收货地址,时间==={}开始",startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressService.updateUserAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("更新用户收货地址成功");
        }catch (Exception e){
            logger.error("更新用户收货地址失败,异常信息为{}"+e.getMessage(),e);
            responseDTO.setErrorInfo("更新用户收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("更新用户的收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }


    /**
     * 根据用户Id删除收货地址
     *
     * input PageParamDto
     *
     * output ResponseDTO
     *
     */
    @RequestMapping(value = "deleteUserAddress", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO deleteUserAddress(@RequestParam String addressId) {
        long startTime = System.currentTimeMillis();
        logger.info("根据用户Id={}删除收货地址,时间==={}开始",addressId,startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressService.deleteUserAddress(addressId);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("删除收货地址成功");
        }catch (Exception e){
            logger.error("根据用户Id删除收货地址失败,异常信息为{}"+e.getMessage(),e);
            responseDTO.setErrorInfo("删除收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        logger.info("根据用户Id删除收货地址,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 获取用户默认收货地址信息
     */
    @RequestMapping(value = "userAddressUsedInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> userAddressUsedInfo() {
        long startTime = System.currentTimeMillis();
        logger.info("获取用户默认收货地址信息,时间==={}开始",startTime);
        ResponseDTO<UserOrderAddressDTO> result = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        UserOrderAddressDTO userOrderAddress = new UserOrderAddressDTO();
        List<UserOrderAddressDTO> userOrderAddressDTOList = userOrderAddressService.getUserAddressList(userInfoDTO.getId());
        logger.info("获取用户默认收货地址信息List={}",userOrderAddressDTOList.size());
        if(userOrderAddressDTOList.size()==0)
        {
            result.setResult(StatusConstant.NO_USER_ADDRESS);
            return result;
        }
        for(UserOrderAddressDTO userOrderAddressDTO:userOrderAddressDTOList)
        {
            if(userOrderAddressDTO.getStatus().equals("1"))
            {
                userOrderAddress = userOrderAddressDTO;
                break;
            }
        }
        result.setResponseData(userOrderAddress);
        result.setResult(StatusConstant.SUCCESS);
        logger.info("获取用户默认收货地址信息,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return result;
    }

    /**
     * 根据地址id获取用户收货地址信息
     */
    @RequestMapping(value = "getUserAddressUsedInfoByAddressId", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> getUserAddressUsedInfoByAddressId(@RequestParam String addressId) {
        long startTime = System.currentTimeMillis();
        logger.info("根据地址id={}获取用户收货地址信息,时间==={}开始",addressId,startTime);
        ResponseDTO<UserOrderAddressDTO> result = new ResponseDTO<>();
        UserOrderAddressDTO userOrderAddressDTO = userOrderAddressService.getUserAddressUsedInfoByAddressId(addressId);
        result.setResponseData(userOrderAddressDTO);
        result.setResult(StatusConstant.SUCCESS);
        logger.info("根据地址id获取用户收货地址信息,耗时{}毫秒",(System.currentTimeMillis() - startTime));
        return result;
    }

}
