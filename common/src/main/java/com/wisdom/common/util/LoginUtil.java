package com.wisdom.common.util;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class LoginUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) SpringUtil.getBean(MongoTemplate.class);

    public static String processBeautyUserValidateCode(LoginDTO loginDTO)
    {
        return StatusConstant.SUCCESS;
    }

    public static String processValidateCode(LoginDTO loginDTO)
    {
        //判断validateCode是否还有效
        Query query = new Query().addCriteria(Criteria.where("mobile").is(loginDTO.getUserPhone()));
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
        List<ValidateCodeDTO> data = mongoTemplate.find(query, ValidateCodeDTO.class,"validateCode");
        if(data==null)
        {
            return StatusConstant.VALIDATECODE_ERROR;
        }
        else
        {
            ValidateCodeDTO validateCodeDTO = data.get(0);
            Date dateStr = validateCodeDTO.getCreateDate();
            //判断验证码是否是最新的
            if(!validateCodeDTO.getCode().equals(loginDTO.getCode())){
                return StatusConstant.VALIDATECODE_ERROR;
            }
            long period =  (new Date()).getTime() - dateStr.getTime();

            //验证码过了5分钟了
            if(period>300000)
            {
                return  StatusConstant.VALIDATECODE_ERROR;
            }
        }
        return StatusConstant.SUCCESS;
    }

    public static ResponseDTO processLoginInterceptor(HttpServletRequest request)
    {
        // 判断该方法是否加了@LoginRequired 注解
        Map<String, String> tokenValue = getHeadersInfo(request);
        String userType = tokenValue.get("usertype");

        ResponseDTO<String> responseDto=new ResponseDTO<>();

        if(userType==null||userType.equals(""))
        {
            String token = tokenValue.get("logintoken");
            if(token==null||token.equals("")){
                responseDto.setResult(StatusConstant.FAILURE);
                responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
                return responseDto;
            }

            //验证token有效性
            int loginTokenPeriod = ConfigConstant.logintokenPeriod;
            String userInfo = JedisUtils.get(token);
            if(userInfo==null)
            {
                responseDto.setResult(StatusConstant.FAILURE);
                responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
                return responseDto;
            }
            else
            {
                responseDto.setResult(StatusConstant.SUCCESS);
                JedisUtils.set(token,userInfo,loginTokenPeriod);
                return responseDto;
            }
        }
        else
        {
            String beautyUserLoginToken = "";
            String beautyBossLoginToken = "";
            String beautyClerkLoginToken = "";
            if(userType.equals("beautyUser"))
            {
                beautyUserLoginToken = tokenValue.get("beautyuserlogintoken");

                if(StatusConstant.TOKEN_ERROR.equals(checkLoginToken(beautyUserLoginToken).getErrorInfo()))
                {
                    return checkLoginToken(beautyUserLoginToken);
                }

                //验证token有效性
                String beautyUserInfo = JedisUtils.get(beautyUserLoginToken);
                return checkLoginInfo(beautyUserInfo,beautyUserLoginToken,responseDto);

            }
            else if(userType.equals("beautyBoss"))
            {
                beautyBossLoginToken = tokenValue.get("beautybosslogintoken");

                if(StatusConstant.TOKEN_ERROR.equals(checkLoginToken(beautyBossLoginToken).getErrorInfo()))
                {
                    return checkLoginToken(beautyBossLoginToken);
                }

                //验证token有效性
                String bossInfo = JedisUtils.get(beautyBossLoginToken);
                return checkLoginInfo(bossInfo,beautyBossLoginToken,responseDto);
            }
            else if(userType.equals("beautyClerk"))
            {
                beautyClerkLoginToken = tokenValue.get("beautyclerklogintoken");

                if(StatusConstant.TOKEN_ERROR.equals(checkLoginToken(beautyClerkLoginToken).getErrorInfo()))
                {
                    return checkLoginToken(beautyClerkLoginToken);
                }

                //验证token有效性
                String clerkInfo = JedisUtils.get(beautyClerkLoginToken);
                return checkLoginInfo(clerkInfo,beautyClerkLoginToken,responseDto);
            }
            else
            {
                responseDto.setResult(StatusConstant.FAILURE);
                return responseDto;
            }
        }
    }

    private static ResponseDTO checkLoginInfo(String userLoginInfo, String userLoginToken, ResponseDTO<String> responseDto) {
        if(userLoginInfo==null)
        {
            responseDto.setResult(StatusConstant.FAILURE);
            responseDto.setErrorInfo(StatusConstant.TOKEN_ERROR);
            return responseDto;
        }
        else
        {
            responseDto.setResult(StatusConstant.SUCCESS);
            JedisUtils.set(userLoginToken,userLoginInfo,ConfigConstant.logintokenPeriod);
            return responseDto;
        }
    }

    private static ResponseDTO<String> checkLoginToken(String loginToken)
    {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if(loginToken==null||loginToken.equals("")){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo(StatusConstant.TOKEN_ERROR);
        }
        return responseDTO;
    }

    //get request headers
    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
