package com.wisdom.user.service;

import com.wisdom.common.dto.system.BeautyLoginResultDTO;
import com.wisdom.common.dto.system.LoginDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = false)
public interface BeautyLoginService {

    BeautyLoginResultDTO beautyLogin(LoginDTO loginDTO, String loginIP, String openId) throws Exception;
    
    String beautyLoginOut(BeautyLoginResultDTO beautyLoginResultDTO, HttpServletRequest request, HttpServletResponse response, HttpSession session);

}
