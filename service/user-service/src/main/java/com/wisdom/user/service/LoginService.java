package com.wisdom.user.service;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.mapper.CustomerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public interface LoginService {

    String userLogin(String phone, String code, String loginIP, String openId) throws Exception;
    
    String userLoginOut(String logintoken, HttpServletRequest request, HttpServletResponse response, HttpSession session);

    String managerLogin(String userPhone, String code);

}
