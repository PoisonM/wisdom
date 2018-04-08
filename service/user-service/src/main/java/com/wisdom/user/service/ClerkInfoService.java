package com.wisdom.user.service;

import com.wisdom.common.dto.user.SysClerkDTO;

import java.util.List;

public interface ClerkInfoService {

    List<SysClerkDTO> getClerkInfo(SysClerkDTO SysClerk);
}
