package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;


public class ExportOrderExcelDTO {
    /*"订单编号", "商品编号","用户id","用户名","用户手机号", "付款时间", "商品品牌", "商品名称", "商品规格", "商品数量"
      ,"订单状态", "收货人姓名", "收货人电话",
        "收货人详细地址", "订单金额", "是否要发票", "发票抬头", "纳税人识别号"*/
    /**
     * 订单编号
     */
    @JSONField(name = "orderId")
    private String orderId;

    /**
     * 用户id
     */
    @JSONField(name = "id")
    private String id;
    /**
     * 用户名
     */
    @JSONField(name = "nickName")
    private String nickName;

    /**
     * 用户手机号
     */
    @JSONField(name = "mobile")
    private String mobile;
    /**
     * 付款时间
     */
    @JSONField(name = "payDate")
    private String payDate;
    /**
     * 商品品牌
     */
    @JSONField(name = "productBrand")
    private String productBrand;
    /**
     * 商品编号
     */
    @JSONField(name = "productId")
    private String productId;
    /**
     * 商品名称
     */
    @JSONField(name = "productName")
    private String productName;
    /**
     * 商品规格
     */
    @JSONField(name = "productSpec")
    private String productSpec;
    /**
     * 商品数量
     */
    @JSONField(name = "productNum")
    private String productNum;
    /**
     * 订单状态
     */
    @JSONField(name = "orderStatus")
    private String orderStatus;
    /**
     * 收货人姓名
     */
    @JSONField(name = "userName")
    private String userName;
    /**
     * 收货人电话
     */
    @JSONField(name = "userPhone")
    private String userPhone;

    /**
     * 收货人详细地址
     */
    @JSONField(name = "userAddress")
    private String userAddress;
    /**
     * 订单金额
     */
    @JSONField(name = "amount")
    private String amount;
    /**
     * 是否要发票
     */
    @JSONField(name = "invoice")
    private String invoice;
    /**
     * 发票抬头
     */
    @JSONField(name = "companyName")
    private String companyName;
    /**
     * 纳税人识别号
     */
    @JSONField(name = "taxpayerNumber")
    private String taxpayerNumber;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getOrderStatus() {
        if(("0").equals(orderStatus)){
            return "未支付";
        }else if(("1").equals(orderStatus)){
            return "待发货";
        }else if(("2").equals(orderStatus)){
            return "已支付已收货";
        }else if(("3").equals(orderStatus)){
            return "用户放入购物车中";
        }else if(("4").equals(orderStatus)){
            return "待收货";
        }else if(("5").equals(orderStatus)){
            return "未支付下的取消的订单";
        }else if(("del").equals(orderStatus)){
            return "已删除";
        }else {
            return "未知";
        }
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInvoice() {
        if("1".equals(invoice)){
            return "是";
        }else {
            return "否";
        }
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
