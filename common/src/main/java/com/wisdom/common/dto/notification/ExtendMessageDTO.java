package com.wisdom.common.dto.notification;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ExtendMessageDTO {

    //扩展消息的ID号
    @JSONField(name = "extendMessageId")
    private String extendMessageId;

    //扩展消息的logo图片URL
    @JSONField(name = "extendMessageLogo")
    private String extendMessageLogo;

    //扩展消息的名称
    @JSONField(name = "extendMessageName")
    private String extendMessageName;

    //扩展消息的生成时间
    @JSONField(name = "extendMessageDate")
    private Date extendMessageDate;

    //扩展消息的内容
    @JSONField(name = "extendMessageContent")
    private String extendMessageContent;

    public String getExtendMessageId() {
        return extendMessageId;
    }

    public void setExtendMessageId(String extendMessageId) {
        this.extendMessageId = extendMessageId;
    }

    public String getExtendMessageLogo() {
        return extendMessageLogo;
    }

    public void setExtendMessageLogo(String extendMessageLogo) {
        this.extendMessageLogo = extendMessageLogo;
    }

    public String getExtendMessageName() {
        return extendMessageName;
    }

    public void setExtendMessageName(String extendMessageName) {
        this.extendMessageName = extendMessageName;
    }

    public Date getExtendMessageDate() {
        return extendMessageDate;
    }

    public void setExtendMessageDate(Date extendMessageDate) {
        this.extendMessageDate = extendMessageDate;
    }

    public String getExtendMessageContent() {
        return extendMessageContent;
    }

    public void setExtendMessageContent(String extendMessageContent) {
        this.extendMessageContent = extendMessageContent;
    }
}