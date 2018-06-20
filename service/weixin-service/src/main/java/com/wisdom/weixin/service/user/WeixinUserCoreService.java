package com.wisdom.weixin.service.user;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.TextMessage;
import com.wisdom.common.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class WeixinUserCoreService {
    Logger logger = LoggerFactory.getLogger(WeixinUserCoreService.class);

    @Autowired
    ProcessUserViewEventService processUserClickViewEventService;

    @Autowired
    ProcessUserScanEventService processUserScanEventService;

    @Autowired
    ProcessUserSubscribeEventService processUserSubscribeEventService;

    @Autowired
    ProcessUserClickEventService processUserClickEventService;

    @Autowired
    private MongoTemplate mongoTemplate;

    public String processCustomerWeixinRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        logger.info("调用核心业务类接收消息、处理消息==={}开始" , startTime);
        String respMessage = null;

        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(WeixinUtil.getXmlDataFromWeixin(request));
        String msgType = xmlEntity.getMsgType();
        logger.info("解析xml数据msgType={}" ,msgType);

        // xml请求解析
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
        {
            // 事件类型
            String eventType = xmlEntity.getEvent();
            logger.info("事件类型eventType={}" ,eventType);
            if(eventType.equals(MessageUtil.SCAN))
            {
                //已关注公众号的情况下扫描
                logger.info("已关注公众号的情况下扫描");
                respMessage = processUserScanEventService.processUserScanEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
            {
                //扫描关注公众号或者搜索关注公众号都在其中
                logger.info("扫描关注公众号或者搜索关注公众号都在其中");
                processUserSubscribeEventService.processUserSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
            {
                // 取消订阅
                logger.info("取消订阅");
                processUserSubscribeEventService.processUserUnSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK))
            {
                // 自定义菜单点击事件
                logger.info("自定义菜单点击事件");
                respMessage = processUserClickEventService.processUserClickEvent(xmlEntity,request,response);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW))
            {
                //点击带URL菜单事件
                logger.info("点击带URL菜单事件");
                processUserClickViewEventService.processUserClickViewEvent(xmlEntity);
            }
        }
        else
        {
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(xmlEntity.getFromUserName());
            textMessage.setFromUserName(xmlEntity.getToUserName());
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.TRANSFER_USER_SERVICE);
            textMessage.setFuncFlag(0);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        logger.info("调用核心业务类接收消息、处理消息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return respMessage;
    }

    public void updateUserWeixinToken(){
        try {
            logger.info("用户端微信参数更新==={}开始");
            System.out.print("用户端微信参数更新");
            String token = WeixinUtil.getUserTokenFromTX(ConfigConstant.USER_CORPID, ConfigConstant.USER_SECRET);
            if(StringUtils.isNotNull(token)) {
                String ticket = WeixinUtil.getJsapiTicket(token);
                Query query = new Query().addCriteria(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
                Update update = new Update();
                update.set("token",token);
                update.set("ticket",ticket);
                update.set("updateDate",new Date());
                mongoTemplate.updateFirst(query,update,"weixinParameter");
            }
        } catch (Exception e) {
            logger.info("用户端微信参数更新异常,异常信息为={}"+e.getMessage(),e);
            e.printStackTrace();
        }
    }

    public WeixinShareDTO getWeixinShareInfo(UserInfoDTO userInfoDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("getWeixinShareInfo方法==={}开始" , startTime);
        WeixinShareDTO weixinShareDTO = new WeixinShareDTO();

        weixinShareDTO.setSysUserId(userInfoDTO.getId());
        weixinShareDTO.setUserPhone(userInfoDTO.getMobile());
        weixinShareDTO.setUserImage(userInfoDTO.getPhoto());
        try {
            if(StringUtils.isNotBlank(userInfoDTO.getNickname())){
                String nickNameW = userInfoDTO.getNickname().replaceAll("%", "%25");
                while(true){
                    System.out.println("用户进行编码操作");
                    if(StringUtils.isNotBlank(nickNameW)){
                        if(nickNameW.contains("%25")){
                            nickNameW =  CommonUtils.nameDecoder(nickNameW);
                        }else{
                            nickNameW =  CommonUtils.nameDecoder(nickNameW);
                            break;
                        }
                    }else{
                        break;
                    }
                }
                weixinShareDTO.setNickName(nickNameW);
            }else{
                weixinShareDTO.setNickName("亲爱的");
            }
        } catch(Throwable e){
            logger.error("获取昵称异常，异常信息为，{}"+e.getMessage(),e);
            weixinShareDTO.setNickName("特殊符号用户");
        }
        //获取shareCode
        String shareCode = ConfigConstant.SHARE_CODE_VALUE + userInfoDTO.getMobile() + "_" + RandomNumberUtil.getFourRandom();
        weixinShareDTO.setShareCode(shareCode);
        logger.info("获取shareCode={}",shareCode);

        //获取qrCodeUrl
        String qrCodeUrl = this.getUserQRCode(shareCode);
        weixinShareDTO.setQrCodeURL(qrCodeUrl);
        logger.info("获取qrCodeUrl={}",qrCodeUrl);

        mongoTemplate.insert(weixinShareDTO, "weixinShare");
        logger.info("getWeixinShareInfo方法,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return weixinShareDTO;
    }

    public String getSpecialShopQRCode(String specialShopId)
    {
        logger.info("getSpecialShopQRCode方法参数specialShopId==={}开始" , specialShopId);
        String specialShopCode = ConfigConstant.SPECIAL_SHOP_VALUE + specialShopId + "_" + RandomNumberUtil.getFourRandom();
        String qrCodeUrl = this.getSpecialShopQRURL(specialShopCode);
        return qrCodeUrl;
    }

    /**
     * 根据信息生成二维码
     * @param info
     * @return
     */
    public String getUserQRCode(String info) {
        long startTime = System.currentTimeMillis();
        logger.info("根据信息生成二维码方法开始==={}开始" , startTime);
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        String url= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
        //有效期改为2592000即30天
        String jsonData = "{\"expire_seconds\": 2591000, \"action_name\": \"QR_STR_SCENE\",\"action_info\": {\"scene\": {\"scene_str\"" + ":\"" + info + "\"}}}";
        logger.info("根据信息生成二维码有效期为30天,url={}jsonData={}",url,jsonData);

        String reJson= WeixinUtil.post(url, jsonData,"POST");
        JSONObject jb=JSONObject.fromObject(reJson);
        String qrTicket = jb.getString("ticket");
        String QRCodeURI="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrTicket;
        logger.info("根据信息生成二维码,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return QRCodeURI;
    }

    private String getSpecialShopQRURL(String info) {
        logger.info("getSpecialShopQRURL方法参数info==={}开始" , info);

        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        String url= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
        String jsonData="{\"action_name\": \"QR_LIMIT_STR_SCENE\",\"action_info\": {\"scene\": {\"scene_str\"" + ":\"" + info + "\"}}}";
        String reJson= WeixinUtil.post(url, jsonData,"POST");
        JSONObject jb=JSONObject.fromObject(reJson);
        String qrTicket = jb.getString("ticket");
        String QRCodeURI="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrTicket;
        logger.info("getSpecialShopQRURL方法返回QRCodeURI==={}结束" , QRCodeURI);
        return QRCodeURI;
    }
}
