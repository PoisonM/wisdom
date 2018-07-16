package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 导出佣金DTO
 */
public class ExportMonthExcelDTO {
   /*"昵称", "手机号", "用户当前等级", "佣金金额", "佣金生成时间", "类型",
           "状态" */


    @JSONField(name = "nickName")
    private String nickName;

    @JSONField(name = "mobile")
    private String mobile;



    @JSONField(name = "userTypeNow")
    private String userTypeNow;

    @JSONField(name = "amount")
    private float amount;//收入金额

    @JSONField(name = "createDate")
    private String createDate;

    @JSONField(name = "incomeType")
    private String incomeType;

    @JSONField(name = "incomeStatus")
    private String incomeStatus;




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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIncomeType() {
        if("month".equals(this.incomeType)){
            return "推荐有礼";
        }
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    public String getIncomeStatus() {
        if("0".equals(this.incomeStatus)){
            return "不可提现";
        }else if ("1".equals(this.incomeStatus)){
            return "可提现";
        }
        return incomeStatus;
    }

    public void setIncomeStatus(String incomeStatus) {
        this.incomeStatus = incomeStatus;
    }


//订单状态，0表示未支付；1表示已支付，未收货；
// 2表示已经支付，已收货; del表示订单已经删除；
// 3表示货品放入了购物车中;4表示已经发货，但是用户没收到货;5已取消

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
