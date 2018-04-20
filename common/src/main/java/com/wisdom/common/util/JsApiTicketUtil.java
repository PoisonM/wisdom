package com.wisdom.common.util;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.wexin.WeixinConfigDTO;
import org.apache.log4j.Logger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class JsApiTicketUtil {
    static Logger logger = Logger.getLogger(JsApiTicketUtil.class);

    /**
     * 用于给定的jsticket和url按照SHA-1签名
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static WeixinConfigDTO customerSign(String jsapi_ticket, String url) {
        WeixinConfigDTO ret = new WeixinConfigDTO();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();

        //注意这里参数名必须全部小写，且必须有序
        String configUrl = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;;
        String signature = "";

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(configUrl.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        ret.setUrl(url);
        ret.setJsapiTicket(jsapi_ticket);
        ret.setNonceStr(nonce_str);
        ret.setTimestamp(timestamp);
        ret.setSignature(signature);
        ret.setAppid(ConfigConstant.USER_CORPID);
        return ret;
    }

    public static WeixinConfigDTO bossSign(String jsapi_ticket, String url) {
        WeixinConfigDTO ret = new WeixinConfigDTO();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();

        //注意这里参数名必须全部小写，且必须有序
        String configUrl = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;;
        String signature = "";

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(configUrl.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        ret.setUrl(url);
        ret.setJsapiTicket(jsapi_ticket);
        ret.setNonceStr(nonce_str);
        ret.setTimestamp(timestamp);
        ret.setSignature(signature);
        ret.setAppid(ConfigConstant.BOSS_CORPID);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 获取uuid
     * */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    /**
     * 获取系统当前时间
     * */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 对参数进行签名
     * */
    public static String createSign(String characterEncoding, SortedMap<Object,Object> prePayInfoMap){
        StringBuffer sb = new StringBuffer();
        Set es = prePayInfoMap.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + ConfigConstant.PARTNER_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 组装xml信息
     * */
    public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

}