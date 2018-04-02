package com.wisdom.weixin.controller;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinConfigDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.WeixinUserBean;
import com.wisdom.common.util.*;
import com.wisdom.weixin.client.BusinessServiceClient;
import com.wisdom.weixin.client.UserServiceClient;
import com.wisdom.weixin.interceptor.LoginRequired;
import com.wisdom.weixin.service.customer.WeixinCustomerCoreService;
import com.wisdom.weixin.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by chenjiake on 17/11/4.
 * 负责响应微商平台用户端微信公众平台的请求
 *
 */

@Controller
@RequestMapping(value = "customer")
public class WeixinCustomerController {

    @Autowired
    private WeixinCustomerCoreService weixinCustomerCoreService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BusinessServiceClient businessServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;


    /**
     * *用户校验是否是微信服务器发送的请求
     * */
    @RequestMapping(value = "requestFromServer", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    String requestFromServer(HttpServletRequest request, HttpServletResponse response) {
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
              if (SignUtil.checkCustomerSignature(signature, timestamp, nonce)) {
                return echostr;
              }
              return "";
          } else {
              // 调用核心业务类接收消息、处理消息
              String respMessage = null;
              try {
                  respMessage = weixinCustomerCoreService.processCustomerWeixinRequest(request,response);
              } catch (IOException e) {
                  e.printStackTrace();
              }
              return respMessage;
          }
      }

    /**
     * 公众号菜单引导页 081dazSU0Zf1iU1fGISU0q5ASU0dazSd 0815XmM70lSlvH1UnyN70OwBM705XmM9
     */
    @RequestMapping(value = "getCustomerWeixinMenuId", method = {RequestMethod.POST, RequestMethod.GET})
    public String getCustomerWeixinMenuId(HttpServletRequest request,
                                          HttpServletResponse response,
                                          HttpSession session) throws Exception
    {
        String url = java.net.URLDecoder.decode(request.getParameter("url"), "utf-8");

        if ("shopHome".equals(url)) {
            url = ConfigConstant.CUSTOMER_WEB_URL + "shopHome";
        }
        if ("specialProductList".equals(url)) {
            url = ConfigConstant.CUSTOMER_WEB_URL + "specialProductList";
        }
        else if ("shareHome".equals(url)) {
            url = ConfigConstant.CUSTOMER_WEB_URL + "shareHome";
        }
        else if ("trainingHome".equals(url)) {
            url = ConfigConstant.CUSTOMER_WEB_URL + "trainingHome";
        }
        else if ("myselfCenter".equals(url)) {
            url = ConfigConstant.CUSTOMER_WEB_URL + "myselfCenter";
        }

        String openId = WeixinUtil.getOpenId(session,request);
        if (openId==null||openId.equals("")) {
            String code = request.getParameter("code");
            String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid="+ ConfigConstant.CUTOMER_CORPID +
                    "&secret=" + ConfigConstant.CUSTOMER_SECRET +
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

            openId = weixinUserBean.getOpenid();
            session.setAttribute(ConfigConstant.CUSTOMER_OPEN_ID, openId);
            CookieUtils.setCookie(response, ConfigConstant.CUSTOMER_OPEN_ID, openId==null?"":openId,60*60*24*30,ConfigConstant.DOMAIN_VALUE);
        }

        return "redirect:" + url;
    }


    /**
     * 用户端微信JS-SDK获得初始化参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getConfig", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseDTO<WeixinConfigDTO> getConfig(HttpServletRequest request) throws Exception
    {
        ResponseDTO<WeixinConfigDTO> responseDTO = new ResponseDTO<>();
        String u = request.getParameter("url");
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinCustomerFlag));
        WeixinTokenDTO weixinTokenDTO = mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String ticket = weixinTokenDTO.getTicket();
        WeixinConfigDTO WeixinConfigDTO = JsApiTicketUtil.customerSign(ticket, u);
        responseDTO.setResponseData(WeixinConfigDTO);
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
        String backUrl = request.getParameter("url");
        String oauth2Url = WeixinUtil.getCustomerOauth2Url(backUrl);
        return "redirect:" + oauth2Url;
    }

    /**
     * 用户获取推广二维码
     */
    @RequestMapping(value = "getUserQRCode", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO<WeixinShareDTO> getUserQRCode() throws FileNotFoundException {
        ResponseDTO<WeixinShareDTO> responseDTO = new ResponseDTO();

        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        WeixinShareDTO weixinShareDTO = weixinCustomerCoreService.getWeixinShareInfo(userInfoDTO);
        if(weixinShareDTO==null)
        {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        else
        {
            AccountDTO accountDTO = businessServiceClient.getUserAccountInfo(weixinShareDTO.getSysUserId());
            String instanceMoney = businessServiceClient.selectIncomeInstanceByUserId(userInfoDTO.getId());
            List<UserInfoDTO> userInfoDTOList = userServiceClient.queryNextUserByUserId(userInfoDTO.getId());
            float balance = accountDTO.getBalance();
            weixinShareDTO.setIstanceMoney(instanceMoney);
            weixinShareDTO.setPeoperCount(userInfoDTOList.size());
            weixinShareDTO.setBalance(String.valueOf(balance));
            weixinShareDTO.setUserType(userInfoDTO.getUserType());
            weixinShareDTO.setQrCodeURL(saveImageToLocal(weixinShareDTO.getQrCodeURL(),weixinShareDTO.getSysUserId(),"qrCode"));
            weixinShareDTO.setUserImage(saveImageToLocal(weixinShareDTO.getUserImage(),weixinShareDTO.getSysUserId(),"userImage"));
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData(weixinShareDTO);
        }
        return responseDTO;
    }

    private static String saveImageToLocal(String urlToLocal,String userId,String type) throws FileNotFoundException {
        //将图片存入到本地
        String rootPath = getRootPath();
        String newUrl = "static/images/sharePage/" + userId + "_" + type + ".png";
        URL url = null;
        String responseURL = "/weixin/images/sharePage/" + userId + "_" + type + ".png";
        try {
            url = new URL(urlToLocal);
            //打开链接
            HttpURLConnection conn = null;
            conn = (HttpURLConnection)url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = FileUtils.readInputStream(inStream);

            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(rootPath + "/" + newUrl);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return responseURL;
    }

    /**
     * TODO 获取根目录
     * @return
     * @author <a href="mailto:pheh.lin@gmail.com">PHeH</a><br>
     * Created On 2007-5-10 15:16:21
     */
    private static String getRootPath() throws FileNotFoundException {
        //获取跟目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) path = new File("");
        return path.getAbsolutePath();
    }

}
