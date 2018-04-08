package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

/**
 * ClassName: CustomerAccountResponseDto
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/8 11:00
 * @since JDK 1.8
 */
public class CustomerAccountResponseDto extends BaseEntity {
    //总金额
    private Long sumAmount;

    //总欠款
    private Long arrears;
    //用户名
    private String userName;
    //头像
    private String photo;
    //美容师
    private String sysClerkName;
    //会员状态
    private String member;

    public Long getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Long sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Long getArrears() {
        return arrears;
    }

    public void setArrears(Long arrears) {
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
