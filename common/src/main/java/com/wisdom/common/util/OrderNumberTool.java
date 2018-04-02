package com.wisdom.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Random;

/**
 * Created by Administrator on 2017/12/13.
 */
public class OrderNumberTool {

    //随机数取值范围
    private static final String ALLCHAR = "0123456789";

    /**
     * 随机生成4位数字
     * @param length
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }




}
