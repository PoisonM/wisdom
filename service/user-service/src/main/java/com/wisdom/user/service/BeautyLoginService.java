package com.wisdom.user.service;

import com.wisdom.common.dto.system.LoginDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = false)
public interface BeautyLoginService {

    String beautyUserLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception;
    
    String beautyUserLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session);

    String bossMobileLogin(LoginDTO loginDTO, String loginIp, String openid);

    String bossWebLogin(LoginDTO loginDTO, String loginIp);

    String ClerkMobileLogin(LoginDTO loginDTO, String loginIp, String openid);

    String ClerkWebLogin(LoginDTO loginDTO, String loginIp);
}
