package com.wisdom.user.service;

import com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.client.BusinessServiceClient;
import com.wisdom.user.mapper.CustomerInfoMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface UserInfoService {

    List<UserInfoDTO> getUserInfo(UserInfoDTO userInfoDTO);
    
    void updateUserInfo(UserInfoDTO userInfoDTO);
    
    void insertUserInfo(UserInfoDTO userInfoDTO);

    PageParamDTO<List<UserBusinessTypeDTO>> queryUserBusinessById(String sysUserId);

    PageParamDTO<List<UserInfoDTO>> queryUserInfoDTOByParameters(PageParamVoDTO<UserInfoDTO> pageParamVoDTO);

    //根据用户id查询下级代理
    List<UserInfoDTO> queryNextUserById(String sysUserId);

    UserInfoDTO getUserInfoFromUserId(String sysUserId);

    //根据parentId查询上级信息
    List<UserInfoDTO> queryParentUserById(String parentUserId);

    UserInfoDTO getUserInfoFromRedis();
}
