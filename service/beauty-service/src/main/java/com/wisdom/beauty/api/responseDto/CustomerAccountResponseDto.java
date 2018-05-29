package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;

import java.math.BigDecimal;

/**
 * ClassName: CustomerAccountResponseDto
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 11:00
 * @since JDK 1.8
 */
public class CustomerAccountResponseDto extends ShopUserArchivesDTO {
    //总金额
    private BigDecimal sumAmount;

    //总欠款
    private BigDecimal arrears;
    //用户名
    private String userName;
    //头像
    private String photo;
    //美容师
    private String sysClerkName;
    //会员状态
    private String member;

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getArrears() {
        return arrears;
    }

    public void setArrears(BigDecimal arrears) {
        this.arrears = arrears;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSysClerkName() {
        return sysClerkName;
    }

    public void setSysClerkName(String sysClerkName) {
        this.sysClerkName = sysClerkName;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

}
