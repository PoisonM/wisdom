package com.wisdom.user.service;

import com.wisdom.common.dto.system.LoginDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = false)
public interface LoginService {

    String userLogin(String phone, String code, String loginIP, String openId) throws Exception;
    
    String userLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session);

    String managerLogin(String userPhone, String code);

    String clerkLogin(LoginDTO loginDTO, String s, String openId);

    String bossMobileLogin(LoginDTO loginDTO, String s, String openid);

    String bossWebLogin(LoginDTO loginDTO, String s);
}
