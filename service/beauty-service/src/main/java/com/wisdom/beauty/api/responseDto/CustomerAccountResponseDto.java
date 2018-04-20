package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;

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
/*
    //手机
    private String phone;
    //电话
    //性别
    private String sex;
    //生日
    private String birthday;
    //星座
    private String constellation;
    //血型
    private String bloodType;
    // 身高
    private String height;
    //体重
    private String weight;
    //类型
    private String sysUserType;
*/


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

/*    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSysUserType() {
        return sysUserType;
    }

    public void setSysUserType(String sysUserType) {
        this.sysUserType = sysUserType;
    }*/
}
