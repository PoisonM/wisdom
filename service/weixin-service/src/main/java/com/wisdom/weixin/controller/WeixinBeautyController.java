package com.wisdom.weixin.controller;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinConfigDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.util.*;
import com.wisdom.weixin.interceptor.LoginRequired;
import com.wisdom.weixin.service.beauty.WeixinBeautyCoreService;
import com.wisdom.weixin.util.UserUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by chenjiake on 17/11/4.
 * 负责响应微商平台用户端微信公众平台的请求
 *
 */

@Controller
@RequestMapping(value = "beauty")
public class WeixinBeautyController {
    Logger logger = LoggerFactory.getLogger(WeixinBeautyController.class);

    @Autowired
    private WeixinBeautyCoreService weixinBeautyCoreService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * *用户校验是否是微信服务器发送的请求
     * */
    @RequestMapping(value = "requestFromServer", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String requestFromServer(HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        logger.info("用户校验是否是微信服务器发送的请求==={}开始" , startTime);
          String method = request.getMethod().toUpperCase();
          if ("GET".equals(method)) {
              // 微信加密签名
              String signature = request.getParameter("signature");
              // 时间戳
              String timestamp = request.getParameter("timestamp");
              // 随机数
              String nonce = request.getParameter("nonce");
              // 随机字符串
              String echostr = request.getParameter("echostr");

              // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
              if (SignUtil.checkBeautySignature(signature, timestamp, nonce)) {
                  logger.info("通过检验signature对请求进行校验，若校验成功则原样返回echostr，接入成功,耗时{}毫秒", (System.currentTimeMillis() - startTime));
                  return echostr;
              }
              logger.info("通过检验signature对请求进行校验，若校验成功则原样返回echostr，接入失败,耗时{}毫秒", (System.currentTimeMillis() - startTime));
              return "";
          } else {
              // 调用核心业务类接收消息、处理消息
              String respMessage = null;
              respMessage = weixinBeautyCoreService.processBeautyWeixinRequest(request,response);
              return respMessage;
          }
    }

    /**
     * 公众号菜单引导页 081dazSU0Zf1iU1fGISU0q5ASU0dazSd 0815XmM70lSlvH1UnyN70OwBM705XmM9
     */
    @RequestMapping(value = "getBeautyWeixinMenuId", method = {RequestMethod.POST, RequestMethod.GET})
    public String getBeautyWeixinMenuId(HttpServletRequest request,
                                    HttpServletResponse response,
                                    HttpSession session) throws Exception
    {
        long startTime = System.currentTimeMillis();
        logger.info("公众号菜单引导页 081dazSU0Zf1iU1fGISU0q5ASU0dazSd 0815XmM70lSlvH1UnyN70OwBM705XmM9==={}开始" , startTime);
        String url = java.net.URLDecoder.decode(request.getParameter("url"), "utf-8");

        if ("beautyUser".equals(url)) {
            url = ConfigConstant.USER_BEAUTY_WEB_URL + "beautyAppoint";
        }
        else if ("beautyBoss".equals(url)) {
            url = ConfigConstant.BOSS_WEB_URL + "workHome";
        }
        else if ("beautyClerk".equals(url)) {
            url = ConfigConstant.BOSS_WEB_URL + "employeeIndex";
        }
        
        String code = request.getParameter("code");
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid="+ ConfigConstant.BOSS_CORPID +
                "&secret=" + ConfigConstant.BOSS_SECRET +
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
        } while (weixinUserBean == null);

        String openId = weixinUserBean.getOpenid();
        session.setAttribute(ConfigConstant.BOSS_OPEN_ID, openId);
        CookieUtils.setCookie(response, ConfigConstant.BOSS_OPEN_ID, openId==null?"":openId,60*60*24*30,ConfigConstant.DOMAIN_VALUE);

        logger.info("公众号菜单引导页 081dazSU0Zf1iU1fGISU0q5ASU0dazSd 0815XmM70lSlvH1UnyN70OwBM705XmM9,耗时={}", (System.currentTimeMillis() - startTime));
        return "redirect:" + url;
    }

    @RequestMapping(value = "getBeautyConfig", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseDTO<WeixinConfigDTO> getBeautyConfig(HttpServletRequest request) throws Exception
    {
        long startTime = System.currentTimeMillis();
        logger.info("getBeautyConfig==={}开始" , startTime);
        ResponseDTO<WeixinConfigDTO> responseDTO = new ResponseDTO<>();
        String u = request.getParameter("url");
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinBossFlag));
        WeixinTokenDTO weixinTokenDTO = mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String ticket = weixinTokenDTO.getTicket();
        WeixinConfigDTO WeixinConfigDTO = JsApiTicketUtil.bossSign(ticket, u);
        responseDTO.setResponseData(WeixinConfigDTO);
        logger.info("getBeautyConfig,耗时={}", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 验证主入口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/fieldwork/author", method = RequestMethod.GET)
    public String Oauth2API(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("验证主入口==={}开始" , startTime);
        String backUrl = request.getParameter("url");
        String oauth2Url = WeixinUtil.getBeautyOauth2Url(backUrl);
        logger.info("验证主入口,耗时={}", (System.currentTimeMillis() - startTime));
        return "redirect:" + oauth2Url;
    }

    /**
     * 获取美容院固定二维码
     */
    @RequestMapping(value = "getBeautyQRCode", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> getBeautyQRCode(@RequestParam String shopId,@RequestParam String userId) {
        long startTime = System.currentTimeMillis();
        logger.info("获取美容院固定二维码参数shopId={},userId={}开始={}" ,shopId,userId,startTime);
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinBossFlag));
        WeixinTokenDTO weixinTokenDTO = mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        String url= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
        String shareCode = "beautyShop_" + shopId + "_" + userId;
        String jsonData="{\"expire_seconds\": 2591000, \"action_name\": \"QR_STR_SCENE\",\"action_info\": {\"scene\": {\"scene_str\"" + ":\"" + shareCode + "\"}}}";
        String reJson= WeixinUtil.post(url, jsonData,"POST");
        JSONObject jb = JSONObject.fromObject(reJson);
        String qrTicket = jb.getString("ticket");
        String QRCodeURI="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrTicket;
        responseDTO.setResponseData(QRCodeURI);
        logger.info("验证主入口,耗时={}", (System.currentTimeMillis() - startTime));
        return  responseDTO;
    }
}
