package com.wisdom.business.service.transaction;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.transaction.UserOrderAddressMapper;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.dto.transaction.OrderAddressRelationDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.system.UserOrderAddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class UserOrderAddressService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserOrderAddressMapper userOrderAddressMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    
    public List<UserOrderAddressDTO> getUserAddressList(String userId) {
        List<UserOrderAddressDTO> userOrderAddressDTOList = userOrderAddressMapper.getUserOrderAddress(userId);
        return userOrderAddressDTOList;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void addUserAddress(UserOrderAddressDTO userOrderAddressDTO) {
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        userOrderAddressDTO.setDelFlag("0");
        userOrderAddressDTO.setSysUserId(userInfoDTO.getId());
        logger.info("添加用户={}收货地址",userInfoDTO.getId());
        //先找出用户所有的未删除的地址列表
        this.cancelDefaultUserAddress(userOrderAddressDTO,userInfoDTO.getId());
        userOrderAddressMapper.addUserAddress(userOrderAddressDTO);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAddress(UserOrderAddressDTO userOrderAddressDTO) throws Exception{
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        logger.info("修改用户={}的收货地址",userInfoDTO.getId());
        //先找出用户所有的未删除的地址列表
        this.cancelDefaultUserAddress(userOrderAddressDTO,userInfoDTO.getId());
        userOrderAddressMapper.updateUserAddress(userOrderAddressDTO);
    }
    
    public void deleteUserAddress(String addressId) throws Exception{
        userOrderAddressMapper.deleteUserAddress(addressId);
    }
    
    public UserOrderAddressDTO findUserAddressById(String addressId) {
        UserOrderAddressDTO userOrderAddressDTO=userOrderAddressMapper.findUserAddressById(addressId);
        return userOrderAddressDTO;
    }
    
    public UserOrderAddressDTO getUserAddressUsedInfoByAddressId(String addressId) {
        return userOrderAddressMapper.findUserAddressById(addressId);
    }
    
    public List<UserOrderAddressDTO> getUserOrderAddress(String id, String s) {
        return userOrderAddressMapper.getUserOrderAddress(id,s);
    }

    private void cancelDefaultUserAddress(UserOrderAddressDTO userOrderAddressDTO,String userId)
    {
        logger.info("先找出用户={}所有的未删除的地址列表",userId);
        //先找出用户所有的未删除的地址列表
        if(userOrderAddressDTO.getStatus().equals("1"))
        {
            List<UserOrderAddressDTO> userOrderAddressDTOList =  userOrderAddressMapper.getUserOrderAddress(userId);
            if(userOrderAddressDTOList.size()>0)
            {
                for(UserOrderAddressDTO userOrderAddressDTO1:userOrderAddressDTOList)
                {
                    if(userOrderAddressDTO1.getStatus().equals("1"))
                    {
                        userOrderAddressDTO1.setStatus("0");
                        userOrderAddressMapper.updateUserAddress(userOrderAddressDTO1);
                    }

                }
            }
        }
    }

    public UserOrderAddressDTO getUserOrderAddressByOrderId(String orderId) {
        return userOrderAddressMapper.getUserOrderAddressByOrderId(orderId);
    }
    public void addOrderAddressRelation(OrderAddressRelationDTO orderAddressRelationDTO){
        userOrderAddressMapper.addOrderAddressRelation(orderAddressRelationDTO);
    }
    public List<OrderAddressRelationDTO> getOrderAddressRelationByOrderId(String orderId){
        return userOrderAddressMapper.getOrderAddressRelationByOrderId(orderId);
    }

    public void updateOrderAddressRelationByOrderId(OrderAddressRelationDTO orderAddressRelationDTO) {
        userOrderAddressMapper.updateOrderAddressRelationByOrderId(orderAddressRelationDTO);
    }
}
