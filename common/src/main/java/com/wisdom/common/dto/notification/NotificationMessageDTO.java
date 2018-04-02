package com.wisdom.common.dto.notification;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class NotificationMessageDTO {

    //老友提醒消息的ID号
    @JSONField(name = "notificationMessageId")
    private String notificationMessageId;

    //老友提醒消息的logo图片URL
    @JSONField(name = "notificationMessageLogo")
    private String notificationMessageLogo;

    //老友提醒消息的生成时间
    @JSONField(name = "notificationMessageDate")
    private Date notificationMessageDate;

    //老友提醒消息的标题
    @JSONField(name = "notificationMessageTitle")
    private String notificationMessageTitle;

    //老友提醒消息的内容
    @JSONField(name = "notificationMessageContent")
    private String notificationMessageContent;

    //老友提醒消息的类型
    /**
     * notificationMessageType
     * 0 表示为直播开始的提醒消息；
     * 1 表示为活动开始的提醒消息；
     * */
    @JSONField(name = "notificationMessageType")
    private String notificationMessageType;

    //老友提醒消息的内容的ID
    /**
     * 如果是直播消息，为直播内容的ID
     * 如果是活动消息，则为此活动的ID
     * */
    @JSONField(name = "notificationMessageContentId")
    private String notificationMessageContentId;

    //老友提醒消息的已读或者未度状态
    /**
     * 0 表示没有读
     * 1 表示已读
     * */
    @JSONField(name = "notificationMessageStatus")
    private String notificationMessageStatus;

    public String getNotificationMessageId() {
        return notificationMessageId;
    }

    public void setNotificationMessageId(String notificationMessageId) {
        this.notificationMessageId = notificationMessageId;
    }

    public String getNotificationMessageLogo() {
        return notificationMessageLogo;
    }

    public void setNotificationMessageLogo(String notificationMessageLogo) {
        this.notificationMessageLogo = notificationMessageLogo;
    }

    public String getNotificationMessageTitle() {
        return notificationMessageTitle;
    }

    public void setNotificationMessageTitle(String notificationMessageTitle) {
        this.notificationMessageTitle = notificationMessageTitle;
    }

    public String getNotificationMessageContent() {
        return notificationMessageContent;
    }

    public void setNotificationMessageContent(String notificationMessageContent) {
        this.notificationMessageContent = notificationMessageContent;
    }

    public String getNotificationMessageType() {
        return notificationMessageType;
    }

    public void setNotificationMessageType(String notificationMessageType) {
        this.notificationMessageType = notificationMessageType;
    }

    public String getNotificationMessageContentId() {
        return notificationMessageContentId;
    }

    public void setNotificationMessageContentId(String notificationMessageContentId) {
        this.notificationMessageContentId = notificationMessageContentId;
    }

    public Date getNotificationMessageDate() {
        return notificationMessageDate;
    }

    public void setNotificationMessageDate(Date notificationMessageDate) {
        this.notificationMessageDate = notificationMessageDate;
    }

    public String getNotificationMessageStatus() {
        return notificationMessageStatus;
    }

    public void setNotificationMessageStatus(String notificationMessageStatus) {
        this.notificationMessageStatus = notificationMessageStatus;
    }
}