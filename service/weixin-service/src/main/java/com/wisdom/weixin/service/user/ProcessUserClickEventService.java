package com.wisdom.weixin.service.user;

import com.wisdom.common.entity.Article;
import com.wisdom.common.entity.NewsMessage;
import com.wisdom.common.entity.ReceiveXmlEntity;
import com.wisdom.common.entity.TextMessage;
import com.wisdom.common.util.MessageUtil;
import com.wisdom.common.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenjiake on 2017/9/11.
 */
@Service
@Transactional(readOnly = false)
public class ProcessUserClickEventService {
    Logger logger = LoggerFactory.getLogger(ProcessUserClickEventService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static ExecutorService threadExecutorSingle = Executors.newSingleThreadExecutor();

    public String processUserClickEvent(ReceiveXmlEntity xmlEntity, HttpServletRequest request, HttpServletResponse response) {
        logger.info("自定义菜单点击事件,开始");

        String respMessage = "";
        if("10".equals(xmlEntity.getEventKey())){
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(xmlEntity.getFromUserName());
            textMessage.setFromUserName(xmlEntity.getToUserName());
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);
            String st = "亲，即将上线，尽情期待";
            textMessage.setContent(st);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        else if("38".equals(xmlEntity.getEventKey()))
        {
            HttpSession session = request.getSession();
            session.setAttribute("openId",xmlEntity.getFromUserName());
            Map parameter = null;//wechatInfoDao.getWeixinParameter();
            String token = (String) parameter.get("token");
            List<Article> articleList = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle("");
            article.setDescription("");
            article.setPicUrl(" ");
            article.setUrl("https://mp.weixin.qq.com/s?__biz=MzI2MDAxOTY3OQ==&mid=504236660&idx=1&sn=10d923526047a5276dd9452b7ed1e302&scene=1&srcid=0612OCo7d5ASBoGRr2TDgjfR&key=f5c31ae61525f82ed83c573369e70b8f9b853c238066190fb5eb7b8640946e0a090bbdb47e79b6d2e57b615c44bd82c5&ascene=0&uin=MzM2NjEyMzM1&devicetype=iMac+MacBookPro11%2C4+OSX+OSX+10.11.4+build(15E65)&version=11020201&pass_ticket=dG5W6eOP3JU1%2Fo3JXw19SFBAh1DgpSlQrAXTyirZuj970HMU7TYojM4D%2B2LdJI9n");
            articleList.add(article);
            WeixinUtil.senImgMsgToWeixin(token,xmlEntity.getFromUserName(),articleList);
        }else if("36".equals(xmlEntity.getEventKey()))
        {
            List<Article> articleList = new ArrayList<Article>();
            // 创建图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(xmlEntity.getFromUserName());
            newsMessage.setFromUserName(xmlEntity.getToUserName());
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            newsMessage.setFuncFlag(0);
            Article article = new Article();
            article.setTitle("");
            article.setDescription("");
            article.setPicUrl("");
            article.setUrl("http://mp.weixin.qq.com/s?__biz=MzI0MjAwNjY0Nw==&mid=208340985&idx=1&sn=2beb0d78fc9097f10d073e182f1cb6c1&scene=0#rd");
            articleList.add(article);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }

        return respMessage;
    }
}
