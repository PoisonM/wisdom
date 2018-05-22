package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 导出佣金DTO
 */
public class ExportNextUserInfoExcelDTO {
//"用户id","用户名","用户手机号","用户获益时等级","用户现在等级","佣金金额",
// "下级用户id","下级用户名","下级用户手机号","下级用户等级","下级用户现在等级",
// "交易id","支付时间","订单id","订单金额","订单状态",

    @JSONField(name = "nickName")
    private String nickName;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "userType")
    private String userType;//

    @JSONField(name = "parentNickName")
    private String parentNickName;

    @JSONField(name = "parentMobile")
    private String parentMobile;

    @JSONField(name = "parentUserType")
    private String parentUserType;//


    @JSONField(name = "nextNickName")
    private String nextNickName;


    @JSONField(name = "nextMobile")
    private String nextMobile;


    @JSONField(name = "nextUserType")
    private String nextUserType;//

    @JSONField(name = "payDate")
    private String payDate;

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "transactionId")
    private String transactionId;//交易id

    @JSONField(name = "amount")
    private float amount;//


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNextNickName() {
        return nextNickName;
    }

    public void setNextNickName(String nextNickName) {
        this.nextNickName = nextNickName;
    }

    public String getParentNickName() {
        return parentNickName;
    }

    public void setParentNickName(String parentNickName) {
        this.parentNickName = parentNickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userTypeUtil(userType);
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getNextMobile() {
        return nextMobile;
    }

    public void setNextMobile(String nextMobile) {
        this.nextMobile = nextMobile;
    }


    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public String getNextUserType() {
        return userTypeUtil(nextUserType);
    }

    public void setNextUserType(String nextUserType) {
        this.nextUserType = nextUserType;
    }

    public void setParentUserType(String parentUserType) {
        this.parentUserType = parentUserType;
    }

    public String getParentUserType() {
        return userTypeUtil(parentUserType);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String userTypeUtil(String userType){
        if("business-B-1".equals(userType)){
            return "B";
        }else if("business-A-1".equals(userType)){
            return "A";
        }else if("business-C-1".equals(userType)){
            return "C";
        }
        return userType;
    }

}
