package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopCashFlowCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopCashFlowCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFlowNoIsNull() {
            addCriterion("flow_no is null");
            return (Criteria) this;
        }

        public Criteria andFlowNoIsNotNull() {
            addCriterion("flow_no is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNoEqualTo(String value) {
            addCriterion("flow_no =", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotEqualTo(String value) {
            addCriterion("flow_no <>", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThan(String value) {
            addCriterion("flow_no >", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThanOrEqualTo(String value) {
            addCriterion("flow_no >=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThan(String value) {
            addCriterion("flow_no <", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThanOrEqualTo(String value) {
            addCriterion("flow_no <=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLike(String value) {
            addCriterion("flow_no like", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotLike(String value) {
            addCriterion("flow_no not like", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoIn(List<String> values) {
            addCriterion("flow_no in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotIn(List<String> values) {
            addCriterion("flow_no not in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoBetween(String value1, String value2) {
            addCriterion("flow_no between", value1, value2, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotBetween(String value1, String value2) {
            addCriterion("flow_no not between", value1, value2, "flowNo");
            return (Criteria) this;
        }

        public Criteria andSysShopIdIsNull() {
            addCriterion("sys_shop_id is null");
            return (Criteria) this;
        }

        public Criteria andSysShopIdIsNotNull() {
            addCriterion("sys_shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopIdEqualTo(String value) {
            addCriterion("sys_shop_id =", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdNotEqualTo(String value) {
            addCriterion("sys_shop_id <>", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdGreaterThan(String value) {
            addCriterion("sys_shop_id >", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_id >=", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdLessThan(String value) {
            addCriterion("sys_shop_id <", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_id <=", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdLike(String value) {
            addCriterion("sys_shop_id like", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdNotLike(String value) {
            addCriterion("sys_shop_id not like", value, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdIn(List<String> values) {
            addCriterion("sys_shop_id in", values, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdNotIn(List<String> values) {
            addCriterion("sys_shop_id not in", values, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdBetween(String value1, String value2) {
            addCriterion("sys_shop_id between", value1, value2, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysShopIdNotBetween(String value1, String value2) {
            addCriterion("sys_shop_id not between", value1, value2, "sysShopId");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeIsNull() {
            addCriterion("sys_boss_code is null");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeIsNotNull() {
            addCriterion("sys_boss_code is not null");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeEqualTo(String value) {
            addCriterion("sys_boss_code =", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeNotEqualTo(String value) {
            addCriterion("sys_boss_code <>", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeGreaterThan(String value) {
            addCriterion("sys_boss_code >", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeGreaterThanOrEqualTo(String value) {
            addCriterion("sys_boss_code >=", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeLessThan(String value) {
            addCriterion("sys_boss_code <", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeLessThanOrEqualTo(String value) {
            addCriterion("sys_boss_code <=", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeLike(String value) {
            addCriterion("sys_boss_code like", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeNotLike(String value) {
            addCriterion("sys_boss_code not like", value, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeIn(List<String> values) {
            addCriterion("sys_boss_code in", values, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeNotIn(List<String> values) {
            addCriterion("sys_boss_code not in", values, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeBetween(String value1, String value2) {
            addCriterion("sys_boss_code between", value1, value2, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andSysBossCodeNotBetween(String value1, String value2) {
            addCriterion("sys_boss_code not between", value1, value2, "sysBossCode");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("pay_type like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("pay_type not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountIsNull() {
            addCriterion("pay_type_amount is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountIsNotNull() {
            addCriterion("pay_type_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountEqualTo(BigDecimal value) {
            addCriterion("pay_type_amount =", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountNotEqualTo(BigDecimal value) {
            addCriterion("pay_type_amount <>", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountGreaterThan(BigDecimal value) {
            addCriterion("pay_type_amount >", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_type_amount >=", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountLessThan(BigDecimal value) {
            addCriterion("pay_type_amount <", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_type_amount <=", value, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountIn(List<BigDecimal> values) {
            addCriterion("pay_type_amount in", values, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountNotIn(List<BigDecimal> values) {
            addCriterion("pay_type_amount not in", values, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_type_amount between", value1, value2, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_type_amount not between", value1, value2, "payTypeAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountIsNull() {
            addCriterion("balance_amount is null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountIsNotNull() {
            addCriterion("balance_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountEqualTo(BigDecimal value) {
            addCriterion("balance_amount =", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountNotEqualTo(BigDecimal value) {
            addCriterion("balance_amount <>", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountGreaterThan(BigDecimal value) {
            addCriterion("balance_amount >", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_amount >=", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountLessThan(BigDecimal value) {
            addCriterion("balance_amount <", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("balance_amount <=", value, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountIn(List<BigDecimal> values) {
            addCriterion("balance_amount in", values, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountNotIn(List<BigDecimal> values) {
            addCriterion("balance_amount not in", values, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_amount between", value1, value2, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andBalanceAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("balance_amount not between", value1, value2, "balanceAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountIsNull() {
            addCriterion("recharge_card_amount is null");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountIsNotNull() {
            addCriterion("recharge_card_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountEqualTo(BigDecimal value) {
            addCriterion("recharge_card_amount =", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountNotEqualTo(BigDecimal value) {
            addCriterion("recharge_card_amount <>", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountGreaterThan(BigDecimal value) {
            addCriterion("recharge_card_amount >", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_card_amount >=", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountLessThan(BigDecimal value) {
            addCriterion("recharge_card_amount <", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("recharge_card_amount <=", value, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountIn(List<BigDecimal> values) {
            addCriterion("recharge_card_amount in", values, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountNotIn(List<BigDecimal> values) {
            addCriterion("recharge_card_amount not in", values, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_card_amount between", value1, value2, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("recharge_card_amount not between", value1, value2, "rechargeCardAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountIsNull() {
            addCriterion("cash_amount is null");
            return (Criteria) this;
        }

        public Criteria andCashAmountIsNotNull() {
            addCriterion("cash_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCashAmountEqualTo(BigDecimal value) {
            addCriterion("cash_amount =", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotEqualTo(BigDecimal value) {
            addCriterion("cash_amount <>", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountGreaterThan(BigDecimal value) {
            addCriterion("cash_amount >", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_amount >=", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountLessThan(BigDecimal value) {
            addCriterion("cash_amount <", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cash_amount <=", value, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountIn(List<BigDecimal> values) {
            addCriterion("cash_amount in", values, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotIn(List<BigDecimal> values) {
            addCriterion("cash_amount not in", values, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_amount between", value1, value2, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andCashAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cash_amount not between", value1, value2, "cashAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountIsNull() {
            addCriterion("owe_amount is null");
            return (Criteria) this;
        }

        public Criteria andOweAmountIsNotNull() {
            addCriterion("owe_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOweAmountEqualTo(BigDecimal value) {
            addCriterion("owe_amount =", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountNotEqualTo(BigDecimal value) {
            addCriterion("owe_amount <>", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountGreaterThan(BigDecimal value) {
            addCriterion("owe_amount >", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("owe_amount >=", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountLessThan(BigDecimal value) {
            addCriterion("owe_amount <", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("owe_amount <=", value, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountIn(List<BigDecimal> values) {
            addCriterion("owe_amount in", values, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountNotIn(List<BigDecimal> values) {
            addCriterion("owe_amount not in", values, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("owe_amount between", value1, value2, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andOweAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("owe_amount not between", value1, value2, "oweAmount");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}