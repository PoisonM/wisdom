package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.SysUserAccountCriteria;
import com.wisdom.beauty.api.dto.SysUserAccountDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.core.mapper.SysUserAccountMapper;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.core.service.SysUserAccountService;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * ClassName: SysCustomerAccountServiceImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 11:03
 * @since JDK 1.8
 */
@Service("sysUserAccountService")
public class SysCustomerAccountServiceImpl implements SysUserAccountService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserAccountMapper sysCustomerAccountMapper;

    @Autowired
    private ShopUserRelationService shopUserRelationService;

    @Autowired
    private ShopAppointmentService appointmentService;

    @Resource
    private UserServiceClient userServiceClient;

    @Override
    public CustomerAccountResponseDto getSysAccountListByUserId(String userId) {
        logger.info("getSysAccountListByUserId方法传入的参数userId={}", userId);
        if (StringUtils.isBlank(userId)) {
            throw new ServiceException("userId为空");
        }
        //查询用户信息，获取到账户的信息
        CustomerAccountResponseDto customerAccountResponseDto = new CustomerAccountResponseDto();
        UserInfoDTO userInfoDTO = null;
        try {
            userInfoDTO = userServiceClient.getUserInfoFromUserId(userId);
        } catch (Exception e) {
            logger.info("userServiceClient.getUserInfoFromUserId()方法调用失败，失败信息是:" + e.getMessage(), e);
        }

        if (userInfoDTO != null) {
            customerAccountResponseDto.setPhoto(userInfoDTO.getPhoto());
            customerAccountResponseDto.setUserName(userInfoDTO.getNickname());
        }
        SysUserAccountDTO sysUserAccountDTO=new SysUserAccountDTO();
        sysUserAccountDTO.setSysUserId(userId);
        SysUserAccountDTO sysUserAccount = getSysUserAccountDTO(sysUserAccountDTO);
        //获取资金信息
        if (sysUserAccount != null) {
            logger.info("sysCustomerAccountMapper查询的资金信息是:arrears={},sumAmount={}", sysUserAccount.getArrears(), sysUserAccount.getSumAmount());
            customerAccountResponseDto.setArrears(sysUserAccount.getArrears());
            customerAccountResponseDto.setSumAmount(sysUserAccount.getSumAmount());
        }
        //获取会员绑定关系
        String state = null;
        try {
            state = shopUserRelationService.isMember(userId);
        } catch (Exception e) {
            logger.error("shopUserRelationService.isMember()方法调用异常,异常信息是:" + e.getMessage(), e);
        }

        customerAccountResponseDto.setMember(state);
        //获取美容师
        ShopAppointServiceDTO shopAppointServiceDTO = null;
        try {
            shopAppointServiceDTO = appointmentService.getShopAppointService(userId);
        } catch (Exception e) {
            logger.info("appointmentService.getShopAppointService()方法调用异常,异常信息是:" + e.getMessage(), e);
        }

        if (shopAppointServiceDTO != null) {
            customerAccountResponseDto.setSysClerkName(shopAppointServiceDTO.getSysClerkName());
        }
        return customerAccountResponseDto;

    }

    /**
     * 获取用户的账户信息
     *
     * @param sysUserAccountDTO
     * @return
     */
    @Override
    public SysUserAccountDTO getSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO) {

        if (CommonUtils.objectIsEmpty(sysUserAccountDTO)) {
            logger.info("获取用户的账户信息传入参数={}", "sysUserAccountDTO = [" + sysUserAccountDTO + "]");
            return null;
        }

        SysUserAccountCriteria criteria = new SysUserAccountCriteria();
        SysUserAccountCriteria.Criteria c = criteria.createCriteria();

        //参数
        if (StringUtils.isNotBlank(sysUserAccountDTO.getSysUserId())) {
            c.andSysUserIdEqualTo(sysUserAccountDTO.getSysUserId());
        }
        if (StringUtils.isNotBlank(sysUserAccountDTO.getSysShopId())) {
            c.andSysShopIdEqualTo(sysUserAccountDTO.getSysShopId());
        }

        List<SysUserAccountDTO> list = sysCustomerAccountMapper.selectByCriteria(criteria);
        SysUserAccountDTO sysUserAccount = null;
        if (CollectionUtils.isNotEmpty(list)) {
            sysUserAccount = list.get(0);
        }
        return sysUserAccount;
    }

    /**
     * 更新用户的账户信息
     *
     * @param sysUserAccountDTO
     * @return
     */
    @Override
    public int updateSysUserAccountDTO(SysUserAccountDTO sysUserAccountDTO) {

        logger.info("更新用户的账户信息传入参数={}", "sysUserAccountDTO = [" + sysUserAccountDTO + "]");

        int sysUserAccount = sysCustomerAccountMapper.updateByPrimaryKey(sysUserAccountDTO);

        logger.debug("更新用户的账户信息执行结果 {}", sysUserAccount > 0 ? "成功" : "失败");

        return sysUserAccount;
    }
}
