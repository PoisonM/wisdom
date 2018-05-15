package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: ExpenditureAndIncomeResponseDTO
 *
 * @Author： huan
 * 
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
	private String role;
	/**
	 * 金额总计，去重flowNo后计算消费记录算出的金额
	 */
	private BigDecimal totalPrice;
	/**
	 * 支付方式 0:微信 1：支付宝 2:现金
	 */
	private String payType;
	/**
	 * 总收益
	 */
	private BigDecimal allEarnings;
	/**
	 * 现金收益
	 */
	private BigDecimal cashEarnings;
	/**
	 *卡耗
	 */
	private BigDecimal kahao;
	/**
	 *人次数
	 */
	private Integer consumeTime;
	/**
	 * 服务次数
	 */
	private Integer serviceNumber;


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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getAllEarnings() {
		return allEarnings;
	}

	public void setAllEarnings(BigDecimal allEarnings) {
		this.allEarnings = allEarnings;
	}

	public BigDecimal getCashEarnings() {
		return cashEarnings;
	}

	public void setCashEarnings(BigDecimal cashEarnings) {
		this.cashEarnings = cashEarnings;
	}

	public BigDecimal getKahao() {
		return kahao;
	}

	public void setKahao(BigDecimal kahao) {
		this.kahao = kahao;
	}

	public Integer getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Integer consumeTime) {
		this.consumeTime = consumeTime;
	}

	public Integer getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(Integer serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
}
