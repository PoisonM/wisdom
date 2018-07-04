package com.wisdom.common.dto.activity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by cjk on 2017/5/24.
 */
public class ShareActivityDTO {

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "parentSysUserId")
    private String parentSysUserId;

    @JSONField(name = "createTime",format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getParentSysUserId() {
        return parentSysUserId;
    }

    public void setParentSysUserId(String parentSysUserId) {
        this.parentSysUserId = parentSysUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
