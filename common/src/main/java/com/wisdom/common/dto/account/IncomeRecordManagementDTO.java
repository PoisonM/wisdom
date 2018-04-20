package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class IncomeRecordManagementDTO {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "incomeRecordId")
    private String incomeRecordId;

    @JSONField(name = "sysUserId")
    private String sysUserId;

    @JSONField(name = "userName")
    private String userName;

    @JSONField(name = "createDate")
    private Date createDate;

    @JSONField(name = "userType")
    private String userType;

    @JSONField(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncomeRecordId() {
        return incomeRecordId;
    }

    public void setIncomeRecordId(String incomeRecordId) {
        this.incomeRecordId = incomeRecordId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
