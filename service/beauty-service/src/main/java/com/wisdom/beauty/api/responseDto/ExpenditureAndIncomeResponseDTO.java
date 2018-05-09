package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: ExpenditureAndIncomeResponseDTO
 *
 * @Author： huan
 * @Description: 收入和支出类
 * @Date:Created in 2018/4/8 11:00
 * @since JDK 1.8
 */
public class ExpenditureAndIncomeResponseDTO extends BaseEntity {
    /**
     * 照片
     */
    private String photo;
    /**
     * 店员名字
     */
    private String sysShopClerkName;

    private String sysShopClerkId;
    /**
     * 美容院id
     */
    private String sysShopId;
    /**
     * 美容院名称
     */
    private String sysShopName;
    /**
     * 流水号
     */
    private String flowNo;
    /**
     * 耗卡
     */
    private BigDecimal expenditure;
    /**
     * 业绩
     */
    private BigDecimal income;
    /**
     * 日期
     */
    private Date date;
    /**
     * 格式化后的日期,例如2018-04-04
     */
    private String formateDate;
    private  String role;

    public BigDecimal getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(BigDecimal expenditure) {
        this.expenditure = expenditure;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getSysShopId() {
        return sysShopId;
    }

    public void setSysShopId(String sysShopId) {
        this.sysShopId = sysShopId;
    }

    public String getSysShopName() {
        return sysShopName;
    }

    public void setSysShopName(String sysShopName) {
        this.sysShopName = sysShopName;
    }

    public String getFormateDate() {
        return formateDate;
    }

    public void setFormateDate(String formateDate) {
        this.formateDate = formateDate;
    }

    public String getSysShopClerkId() {
        return sysShopClerkId;
    }

    public void setSysShopClerkId(String sysShopClerkId) {
        this.sysShopClerkId = sysShopClerkId;
    }

    public String getSysShopClerkName() {
        return sysShopClerkName;
    }

    public void setSysShopClerkName(String sysShopClerkName) {
        this.sysShopClerkName = sysShopClerkName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


