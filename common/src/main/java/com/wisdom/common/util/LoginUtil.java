package com.wisdom.common.util;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.AccessToken;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.JsApiTicket;
import com.wisdom.common.entity.WeixinUserBean;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by baoweiw on 2015/7/27.
 */
public class LoginUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) SpringUtil.getBean(MongoTemplate.class);

    public static String processValidateCode(LoginDTO loginDTO)
    {
        //判断validateCode是否还有效
//        Query query = new Query().addCriteria(Criteria.where("mobile").is(loginDTO.getUserPhone()))
//                .addCriteria(Criteria.where("code").is(loginDTO.getCode()));
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

}
