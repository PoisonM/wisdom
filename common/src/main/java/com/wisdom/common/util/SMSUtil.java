package com.wisdom.common.util;

import com.aliyun.oss.ClientException;
import com.wisdom.common.dto.product.ProductCodeDTO;
import com.wisdom.common.dto.system.ValidateCodeDTO;
import com.wisdom.common.dto.transaction.OrderCodeDTO;
import com.wisdom.common.dto.transaction.TransactionCodeDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.wisdom.common.util.OrderNumberTool.generateString;

/**
 * Created by jiangzhongge on 2016-5-12.
 */
public class SMSUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) SpringUtil.getBean(MongoTemplate.class);

    public static void sendUserValidateCode(String mobile) throws com.aliyuncs.exceptions.ClientException {
        //生成4位数字的验证码
        String num = RandomNumberUtil.getFourRandom();

        //将用户手机号和随机数插入库中
        ValidateCodeDTO validateCodeDTO = new ValidateCodeDTO();
        validateCodeDTO.setCode(num);
        validateCodeDTO.setCreateDate(new Date());
        validateCodeDTO.setMobile(mobile);
        mongoTemplate.insert(validateCodeDTO, "validateCode");

        try {
            SMSMessageUtil.sendValidateCodeMessage(mobile,num);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
