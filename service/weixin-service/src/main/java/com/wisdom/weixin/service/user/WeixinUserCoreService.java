package com.wisdom.weixin.service.user;

import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.wexin.WeixinShareDTO;
import com.wisdom.common.dto.wexin.WeixinTokenDTO;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.TextMessage;
import com.wisdom.common.util.*;
import net.sf.json.JSONObject;
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

        String respMessage = null;

        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(WeixinUtil.getXmlDataFromWeixin(request));
        String msgType = xmlEntity.getMsgType();

        // xml请求解析
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
        {
            // 事件类型
            String eventType = xmlEntity.getEvent();
            if(eventType.equals(MessageUtil.SCAN))
            {
                //已关注公众号的情况下扫描
                processUserScanEventService.processUserScanEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
            {
                //扫描关注公众号或者搜索关注公众号都在其中
                processUserSubscribeEventService.processUserSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
            {
                // 取消订阅
                processUserSubscribeEventService.processUserUnSubscribeEvent(xmlEntity);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK))
            {
                // 自定义菜单点击事件
                respMessage = processUserClickEventService.processUserClickEvent(xmlEntity,request,response);
            }
            else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW))
            {
                //点击带URL菜单事件
                processUserClickViewEventService.processUserClickViewEvent(xmlEntity);
            }
        }
        else
        {
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(xmlEntity.getFromUserName());
            textMessage.setFromUserName(xmlEntity.getToUserName());
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.TRANSFER_USER_SERVICE);
            textMessage.setFuncFlag(0);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        return respMessage;
    }

    public void updateUserWeixinToken(){
        try {
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
            e.printStackTrace();
        }
    }

    public WeixinShareDTO getWeixinShareInfo(UserInfoDTO userInfoDTO) {
        WeixinShareDTO weixinShareDTO = new WeixinShareDTO();

        weixinShareDTO.setSysUserId(userInfoDTO.getId());
        weixinShareDTO.setUserPhone(userInfoDTO.getMobile());
        weixinShareDTO.setUserImage(userInfoDTO.getPhoto());
        weixinShareDTO.setNickName(CommonUtils.nameDecoder(userInfoDTO.getNickname()));

        //获取shareCode
        String shareCode = ConfigConstant.SHARE_CODE_VALUE + userInfoDTO.getMobile() + "_" + RandomNumberUtil.getFourRandom();
        weixinShareDTO.setShareCode(shareCode);

        //获取qrCodeUrl
        String qrCodeUrl = this.getUserQRCode(shareCode);
        weixinShareDTO.setQrCodeURL(qrCodeUrl);

        mongoTemplate.insert(weixinShareDTO, "weixinShare");

        return weixinShareDTO;
    }

    public String getSpecialShopQRCode(String specialShopId)
    {
        String specialShopCode = ConfigConstant.SPECIAL_SHOP_VALUE + specialShopId + "_" + RandomNumberUtil.getFourRandom();
        String qrCodeUrl = this.getSpecialShopQRURL(specialShopCode);
        return qrCodeUrl;
    }

    /**
     * 根据信息生成二维码
     * @param info
     * @return
     */
    private String getUserQRCode(String info) {
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        String url= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
        String jsonData="{\"expire_seconds\": 626400, \"action_name\": \"QR_STR_SCENE\",\"action_info\": {\"scene\": {\"scene_str\"" + ":\"" + info + "\"}}}";
        String reJson= WeixinUtil.post(url, jsonData,"POST");
        JSONObject jb=JSONObject.fromObject(reJson);
        String qrTicket = jb.getString("ticket");
        String QRCodeURI="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrTicket;
        return QRCodeURI;
    }

    private String getSpecialShopQRURL(String info) {
        Query query = new Query(Criteria.where("weixinFlag").is(ConfigConstant.weixinUserFlag));
        WeixinTokenDTO weixinTokenDTO = this.mongoTemplate.findOne(query,WeixinTokenDTO.class,"weixinParameter");
        String token = weixinTokenDTO.getToken();
        String url= "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
        String jsonData="{\"action_name\": \"QR_LIMIT_STR_SCENE\",\"action_info\": {\"scene\": {\"scene_str\"" + ":\"" + info + "\"}}}";
        String reJson= WeixinUtil.post(url, jsonData,"POST");
        JSONObject jb=JSONObject.fromObject(reJson);
        String qrTicket = jb.getString("ticket");
        String QRCodeURI="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrTicket;
        return QRCodeURI;
    }
}
