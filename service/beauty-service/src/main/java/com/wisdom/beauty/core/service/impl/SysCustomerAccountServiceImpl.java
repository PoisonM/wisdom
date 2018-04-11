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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * ClassName: SysCustomerAccountServcieImpl
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
        UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(userId);
        if(userInfoDTO!=null){
            customerAccountResponseDto.setPhoto(userInfoDTO.getPhoto());
            customerAccountResponseDto.setUserName(userInfoDTO.getNickname());
        }
        SysUserAccountDTO sysUserAccount = getSysUserAccountDTO(userId);
        //获取金信息
        if (sysUserAccount != null) {
            logger.info("sysCustomerAccountMapper查询的资金信息是:arrears={},sumAmount={}", sysUserAccount.getArrears(), sysUserAccount.getSumAmount());
            customerAccountResponseDto.setArrears(sysUserAccount.getArrears());
            customerAccountResponseDto.setSumAmount(sysUserAccount.getSumAmount());
        }
        //获取会员
        String state = shopUserRelationService.isMember(userId);
        customerAccountResponseDto.setMember(state);
        //获取美容师
        ShopAppointServiceDTO shopAppointServiceDTO = appointmentService.getShopAppointService(userId);
        if (shopAppointServiceDTO != null) {
            customerAccountResponseDto.setSysClerkName(shopAppointServiceDTO.getSysClerkName());
        }
        return customerAccountResponseDto;

    }

    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public SysUserAccountDTO getSysUserAccountDTO(String userId) {
        SysUserAccountCriteria criteria = new SysUserAccountCriteria();
        SysUserAccountCriteria.Criteria c = criteria.createCriteria();

        //参数
        c.andSysUserIdEqualTo(userId);
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
