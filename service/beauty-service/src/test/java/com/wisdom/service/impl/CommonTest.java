package com.wisdom.service.impl;


import com.fasterxml.jackson.core.JsonParseException;
import com.wisdom.common.constant.RealNameResultEnum;
import com.wisdom.common.dto.user.RealNameAuthHelperDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.util.HttpUtils;
import com.wisdom.common.util.JacksonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public static void main1(String[] args) {
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

    public static void main2(String[] args) throws Exception {
        String string = "{\"data\":{\"sex\":\"男\",\"address\":\"山东省-青岛市-莱西市\",\"birthday\":\"1992-02-17\"},\"resp\":{\"code\":0,\"desc\":\"匹配\"}}";
        RealNameAuthHelperDTO jsonToBean = (RealNameAuthHelperDTO) JacksonUtil.jsonToBean(string, RealNameAuthHelperDTO.class);
        System.out.println("string = [" + jsonToBean + "]");
        RealNameInfoDTO realNameInfoDTO = new RealNameInfoDTO();
        realNameInfoDTO.setAddress(jsonToBean.getData().getAddress());
        realNameInfoDTO.setBirthday(jsonToBean.getData().getBirthday());
        realNameInfoDTO.setSex(jsonToBean.getData().getSex());

        realNameInfoDTO.setCode(jsonToBean.getResp().getCode());
        realNameInfoDTO.setDesc(jsonToBean.getData().getDesc());
        System.out.println("realNameInfoDTO = [" + realNameInfoDTO + "]");
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws JsonParseException {
        RealNameInfoDTO realNameInfoDTO = new RealNameInfoDTO();
        String string = "{\"data\":{\"sex\":\"男\",\"address\":\"山东省-青岛市-莱西市\",\"birthday\":\"1992-02-17\"},\"resp\":{\"code\":0,\"desc\":\"匹配\"}}";

        RealNameAuthHelperDTO jsonToBean = (RealNameAuthHelperDTO) JacksonUtil.jsonToBean(string, RealNameAuthHelperDTO.class);

        realNameInfoDTO = new RealNameInfoDTO();
        realNameInfoDTO.setAddress(jsonToBean.getData().getAddress());
        realNameInfoDTO.setBirthday(jsonToBean.getData().getBirthday());
        realNameInfoDTO.setSex(jsonToBean.getData().getSex());
        realNameInfoDTO.setCode(jsonToBean.getResp().getCode());
        realNameInfoDTO.setDesc(RealNameResultEnum.judgeValue(jsonToBean.getResp().getCode()).getDesc());
        realNameInfoDTO.setIdCard("用户身份证号");
        realNameInfoDTO.setName("用户姓名");
        realNameInfoDTO.setResult(jsonToBean.getResp().getCode().equalsIgnoreCase(RealNameResultEnum.MATCHING.getCode()) ? "匹配" : "不匹配");
        System.out.println("args = [" + JacksonUtil.beanToJson(realNameInfoDTO) + "]");
    }















}
