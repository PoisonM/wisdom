package com.wisdom.business.controller.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.transaction.UserOrderAddressService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
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
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        ResponseDTO<List<UserOrderAddressDTO>> responseDTO = new ResponseDTO<>();
        List<UserOrderAddressDTO> userOrderAddressDTOList =  userOrderAddressService.getUserAddressList(userInfoDTO.getId());
        responseDTO.setResponseData(userOrderAddressDTOList);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        ResponseDTO<UserOrderAddressDTO> responseDTO = new ResponseDTO<>();
        UserOrderAddressDTO userOrderAddressDTO =  userOrderAddressService.findUserAddressById(addressId);
        responseDTO.setResponseData(userOrderAddressDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 根据用户的所有收货地址
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressDTO.setId(UUID.randomUUID().toString());
            userOrderAddressService.addUserAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("添加用户地址成功");
        }catch (Exception e)
        {
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("添加用户地址失败");
        }
        return responseDTO;
    }

    /**
     * 根据用户的所有收货地址
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressService.updateUserAddress(userOrderAddressDTO);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("更新用户收货地址成功");
        }catch (Exception e){
            responseDTO.setErrorInfo("更新用户收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }


    /**
     * 根据用户Id删除
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            userOrderAddressService.deleteUserAddress(addressId);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo("删除收货地址成功");
        }catch (Exception e){
            responseDTO.setErrorInfo("删除收货地址失败");
            responseDTO.setResult(StatusConstant.FAILURE);
        }
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
        ResponseDTO<UserOrderAddressDTO> result = new ResponseDTO<>();
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        UserOrderAddressDTO userOrderAddress = new UserOrderAddressDTO();
        List<UserOrderAddressDTO> userOrderAddressDTOList = userOrderAddressService.getUserAddressList(userInfoDTO.getId());
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
        return result;
    }

    /**
     * 获取用户默认收货地址信息
     */
    @RequestMapping(value = "getUserAddressUsedInfoByAddressId", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<UserOrderAddressDTO> getUserAddressUsedInfoByAddressId(@RequestParam String addressId) {
        ResponseDTO<UserOrderAddressDTO> result = new ResponseDTO<>();
        UserOrderAddressDTO userOrderAddressDTO = userOrderAddressService.getUserAddressUsedInfoByAddressId(addressId);
        result.setResponseData(userOrderAddressDTO);
        result.setResult(StatusConstant.SUCCESS);
        return result;
    }

}
