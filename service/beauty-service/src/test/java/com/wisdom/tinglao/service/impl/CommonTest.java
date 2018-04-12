package com.wisdom.tinglao.service.impl;


import com.wisdom.common.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * FileName: CommonTest
 *
 * @author: 赵得良
 * Date:     2018/4/12 0012 11:25
 * Description: 公共测试类
 */
public class CommonTest {
    public static void main(String[] args) {
        String host = "http://idcard.market.alicloudapi.com";
        String path = "/lianzhuo/idcard";
        String method = "GET";
        String appcode = "d44aff14ca8142efb196889d29c2896d";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardno", "370703198111300338");
        querys.put("name", "赵得良");


        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
