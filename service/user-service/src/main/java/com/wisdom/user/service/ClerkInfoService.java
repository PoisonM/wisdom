package com.wisdom.user.service;

import com.wisdom.common.dto.customer.SysUserClerkDTO;

import java.util.List;


public interface ClerkInfoService {

    List<SysUserClerkDTO> getClerkInfo(SysUserClerkDTO sysUserClerk);
}
