package com.wisdom.user.service;

import com.wisdom.common.dto.system.LoginDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = false)
public interface BusinessLoginService {

    String businessUserLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception;
    
    String businessUserLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session);

    String managerLogin(String userPhone, String code);

    String crossBorderLogin(LoginDTO loginDTO,String ip);
}
