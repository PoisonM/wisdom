package com.wisdom.common.util;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.system.LoginDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.AccessToken;
import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.JsApiTicket;
import com.wisdom.common.entity.WeixinUserBean;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by baoweiw on 2015/7/27.
 */
public class WeixinUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) SpringUtil.getBean(MongoTemplate.class);

    public static String getUserTokenFromTX(String corpid, String sectet) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + corpid + "&secret=" + sectet + "";
        String content = HttpRequestUtil.get(url);
        AccessToken token = JsonUtil.getObjFromJsonStr(content, AccessToken.class);
        return token.getaccess_token();
    }

    public static String getUserToken()
    {
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        return token;
    }
    /**
     * 获取jsp页面验证用的jsapi-ticket
     *
     * @return
     * @throws IOException
     */
    public static String getJsapiTicket(String token) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        String content = HttpRequestUtil.get(url);
        System.out.println("ticket:" + content);
        JsApiTicket ticket = JsonUtil.getObjFromJsonStr(content, JsApiTicket.class);
        return ticket.getTicket();
    }

    /**
     * 获取jsp页面验证用的jsapi-ticket
     *
     * @return
     * @throws IOException
     */
    public static String getUserOauth2Url(String backUrl) {
        backUrl = urlEncodeUTF8(backUrl);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                ConfigConstant.USER_CORPID + "&redirect_uri=" +
                backUrl + "&response_type=code&scope=snsapi_base&connect_redirect=1#wechat_redirect";
    }

    public static String getBossOauth2Url(String backUrl) {
        backUrl = urlEncodeUTF8(backUrl);
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                ConfigConstant.BOSS_CORPID + "&redirect_uri=" +
                backUrl + "&response_type=code&scope=snsapi_base&connect_redirect=1#wechat_redirect";
    }


    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取微信用户基本信息
     *
     * @param token  access_token
     * @param openid 用户唯一标示
     * @return WeixinUserBean 微信实体
     */
    public static WeixinUserBean getWeixinUserBean(String token, String openid) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?" +
                "access_token=" + token +
                "&openid=" + openid + "&lang=zh_CN";
        String json = HttpRequestUtil.getConnectionResult(url, "GET", "");
        return JsonUtil.getObjFromJsonStr(json, WeixinUserBean.class);
    }

    /**
     * 调用多客服接口指定发送消息
     *
     * @param token   唯一票据
     * @param openId  用户的唯一标示
     * @param content 发送内容
     */
    public static String sendMsgToWeixin(String token, String openId, String content) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
        String result = "failure";
        try {
            String json = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":" +
                    "{\"content\":\"CONTENT\"}" + "}";
            json = json.replace("CONTENT", content);
            String re = HttpRequestUtil.getConnectionResult(url, "POST", json);
            System.out.print(json + "--" + re);
            if(re.contains("access_token is invalid")){
                //token已经失效，重新获取新的token
                result = "tokenIsInvalid";
            }
            JSONObject obj = new JSONObject(re);
            Integer resultStatus = (Integer)obj.get("errcode");
            if(resultStatus != null && resultStatus == 0){
                result = "messageOk";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return result ;
    }

    /**
     * 调用多客服接口指定发送消息
     *
     * @param token   唯一票据
     * @param openId  用户的唯一标示
     * @param mediaId mediaId
     */
    public static String sendImagToWeixin(String token, String openId, String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
        String result = "failure";
        try {
            String json = "{\n" +
                    "    \"touser\":\"" + openId + "\",\n" +
                    "    \"msgtype\":\"image\",\n" +
                    "    \"image\":\n" +
                    "    {\n" +
                    "      \"media_id\":\"" + mediaId + "\"\n" +
                    "    }\n" +
                    "}";
            String re = HttpRequestUtil.getConnectionResult(url, "POST", json);
            System.out.print(json + "--" + re);
            if (re.contains("access_token is invalid")) {
                //token已经失效，重新获取新的token
                result = "tokenIsInvalid";
            }
            JSONObject obj = new JSONObject(re);
            Integer resultStatus = (Integer) obj.get("errcode");
            if (resultStatus != null && resultStatus == 0) {
                result = "messageOk";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */

    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    public static String getUserOpenId(HttpSession session, HttpServletRequest request) {
        String openId = (String) session.getAttribute(ConfigConstant.USER_OPEN_ID);
        return openId;
    }

    public static String post(String strURL, String params, String type) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(type); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 自定义错误信息
    }

    public static String getXmlDataFromWeixin(HttpServletRequest request)
    {
        /** 读取接收到的xml消息 */
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        try {
            is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String getBossOpenId(HttpSession session, HttpServletRequest request) {

        return null;
    }

    /**
     * 调用多客服接口指定发送消息
     *
     * @param token       唯一票据
     * @param openId      用户的唯一标示
     * @param articleList 图文集合
     */
    public static void senImgMsgToWeixin(String token, String openId, List<Article> articleList) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
        try {
            String newStr =   "";
            for(Article article:articleList){
                newStr +=  "{\"title\":\"" + article.getTitle() + "\",\"description\":\"" + article.getDescription() + "\",\"url\":\"" + article.getUrl()+ "\",\"picurl\":\"" + article.getPicUrl() + "\"}," ;
            }
            String json = "{\"touser\":\"" + openId + "\",\"msgtype\":\"news\",\"news\":" +
                    "{\"articles\":[" +newStr+"]" + "}";
            String s = HttpRequestUtil.getConnectionResult(url, "POST", json.substring(0,json.length()-1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 获取openid
     *
     *
     * */
    public static  String getOpenId(String code){
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid="+ ConfigConstant.USER_CORPID +
                "&secret=" + ConfigConstant.USER_SECRET +
                "&code="+ code +
                "&grant_type=authorization_code";
        WeixinUserBean weixinUserBean;
        int countNum = 0;
        do {
            String json = HttpRequestUtil.getConnectionResult(get_access_token_url, "GET", "");
            weixinUserBean = JsonUtil.getObjFromJsonStr(json, WeixinUserBean.class);
            if (countNum++ > 3) {
                break;
            }
        } while (weixinUserBean.getOpenid() == null);

        String openId = weixinUserBean.getOpenid();
        return openId;
    }
}
