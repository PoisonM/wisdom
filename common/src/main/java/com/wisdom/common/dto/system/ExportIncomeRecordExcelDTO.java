package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 导出佣金DTO
 */
public class ExportIncomeRecordExcelDTO {
//"用户id","用户名","用户手机号","用户获益时等级","用户现在等级","佣金金额",
// "下级用户id","下级用户名","下级用户手机号","下级用户等级","下级用户现在等级",
// "交易id","支付时间","订单id","订单金额","订单状态",
    @JSONField(name = "sysUserId")
    private String sysUserId;//上级用户id

    @JSONField(name = "nickName")
    private String nickName;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "userType")
    private String userType;//上级用户获益时等级

    @JSONField(name = "userTypeNow")
    private String userTypeNow;

    @JSONField(name = "amount")
    private float amount;//收入金额

    @JSONField(name = "nextUserId")
    private String nextUserId;//下级用户id

    @JSONField(name = "nextUserNickName")
    private String nextUserNickName;

    @JSONField(name = "nextUserMobile")
    private String nextUserMobile;

    @JSONField(name = "nextUserType")
    private String nextUserType;//下级用户等级

    @JSONField(name = "nextUserTypeNow")
    private String nextUserTypeNow;//下级用户现在的等级

    @JSONField(name = "transactionId")
    private String transactionId;//交易id

    @JSONField(name = "payDate")
    private String payDate;

    @JSONField(name = "orderId")
    private String orderId;

    @JSONField(name = "orderAmount")
    private String orderAmount;

    @JSONField(name = "orderStatus")
    private String orderStatus;

    @JSONField(name = "parentRelation")
    private String parentRelation;

    //该交易流水与用户等级关系
    private  String isImportLevel;

    public String getIsImportLevel() {
        return isImportLevel;
    }

    public void setIsImportLevel(String isImportLevel) {
        this.isImportLevel = isImportLevel;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getUserTypeNow() {
        return userTypeUtil(userTypeNow);
    }

    public void setUserTypeNow(String userTypeNow) {
        this.userTypeNow = userTypeNow;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(String nextUserId) {
        this.nextUserId = nextUserId;
    }

    public String getNextUserNickName() {
        return nextUserNickName;
    }

    public void setNextUserNickName(String nextUserNickName) {
        this.nextUserNickName = nextUserNickName;
    }

    public String getNextUserMobile() {
        return nextUserMobile;
    }

    public void setNextUserMobile(String nextUserMobile) {
        this.nextUserMobile = nextUserMobile;
    }

    public String getNextUserType() {
        return userTypeUtil(nextUserType);
    }

    public void setNextUserType(String nextUserType) {
        this.nextUserType = nextUserType;
    }

    public String getNextUserTypeNow() {
        return userTypeUtil(nextUserTypeNow);
    }

    public void setNextUserTypeNow(String nextUserTypeNow) {
        this.nextUserTypeNow = nextUserTypeNow;
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

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
//订单状态，0表示未支付；1表示已支付，未收货；
// 2表示已经支付，已收货; del表示订单已经删除；
// 3表示货品放入了购物车中;4表示已经发货，但是用户没收到货;5已取消
    public String getOrderStatus() {
        if("1".equals(orderStatus)){
            return "待发货";
        }else if("2".equals(orderStatus)){
            return "已完成";
        }else if("3".equals(orderStatus)){
            return "放入购物车中";
        }else if("4".equals(orderStatus)){
            return "待收货";
        }else if("5".equals(orderStatus)){
            return "已取消";
        }else if("0".equals(orderStatus)){
            return "未支付";
        }
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getParentRelation() {
        if("business-B-1".equals(parentRelation)){
            return "直B关系";
        }else if("business-A-1".equals(parentRelation)){
            return "直A关系";
        }else if("A1B1".equals(parentRelation)){
            return "间接关系";
        }
        return parentRelation;
    }

    public void setParentRelation(String parentRelation) {
        this.parentRelation = parentRelation;
    }
}
