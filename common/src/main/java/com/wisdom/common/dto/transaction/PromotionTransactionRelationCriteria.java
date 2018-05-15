package com.wisdom.common.dto.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PromotionTransactionRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public PromotionTransactionRelationCriteria() {
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
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
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

        public Criteria andPromotionLevelIdIsNull() {
            addCriterion("promotion_level_id is null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdIsNotNull() {
            addCriterion("promotion_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdEqualTo(String value) {
            addCriterion("promotion_level_id =", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdNotEqualTo(String value) {
            addCriterion("promotion_level_id <>", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdGreaterThan(String value) {
            addCriterion("promotion_level_id >", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdGreaterThanOrEqualTo(String value) {
            addCriterion("promotion_level_id >=", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdLessThan(String value) {
            addCriterion("promotion_level_id <", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdLessThanOrEqualTo(String value) {
            addCriterion("promotion_level_id <=", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdLike(String value) {
            addCriterion("promotion_level_id like", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdNotLike(String value) {
            addCriterion("promotion_level_id not like", value, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdIn(List<String> values) {
            addCriterion("promotion_level_id in", values, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdNotIn(List<String> values) {
            addCriterion("promotion_level_id not in", values, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdBetween(String value1, String value2) {
            addCriterion("promotion_level_id between", value1, value2, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIdNotBetween(String value1, String value2) {
            addCriterion("promotion_level_id not between", value1, value2, "promotionLevelId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNull() {
            addCriterion("transaction_id is null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIsNotNull() {
            addCriterion("transaction_id is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionIdEqualTo(String value) {
            addCriterion("transaction_id =", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotEqualTo(String value) {
            addCriterion("transaction_id <>", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThan(String value) {
            addCriterion("transaction_id >", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdGreaterThanOrEqualTo(String value) {
            addCriterion("transaction_id >=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThan(String value) {
            addCriterion("transaction_id <", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLessThanOrEqualTo(String value) {
            addCriterion("transaction_id <=", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdLike(String value) {
            addCriterion("transaction_id like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotLike(String value) {
            addCriterion("transaction_id not like", value, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdIn(List<String> values) {
            addCriterion("transaction_id in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotIn(List<String> values) {
            addCriterion("transaction_id not in", values, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdBetween(String value1, String value2) {
            addCriterion("transaction_id between", value1, value2, "transactionId");
            return (Criteria) this;
        }

        public Criteria andTransactionIdNotBetween(String value1, String value2) {
            addCriterion("transaction_id not between", value1, value2, "transactionId");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIsNull() {
            addCriterion("promotion_level is null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIsNotNull() {
            addCriterion("promotion_level is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelEqualTo(String value) {
            addCriterion("promotion_level =", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelNotEqualTo(String value) {
            addCriterion("promotion_level <>", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelGreaterThan(String value) {
            addCriterion("promotion_level >", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelGreaterThanOrEqualTo(String value) {
            addCriterion("promotion_level >=", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelLessThan(String value) {
            addCriterion("promotion_level <", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelLessThanOrEqualTo(String value) {
            addCriterion("promotion_level <=", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelLike(String value) {
            addCriterion("promotion_level like", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelNotLike(String value) {
            addCriterion("promotion_level not like", value, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelIn(List<String> values) {
            addCriterion("promotion_level in", values, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelNotIn(List<String> values) {
            addCriterion("promotion_level not in", values, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelBetween(String value1, String value2) {
            addCriterion("promotion_level between", value1, value2, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelNotBetween(String value1, String value2) {
            addCriterion("promotion_level not between", value1, value2, "promotionLevel");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeIsNull() {
            addCriterion("promotion_level_time is null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeIsNotNull() {
            addCriterion("promotion_level_time is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeEqualTo(Date value) {
            addCriterion("promotion_level_time =", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeNotEqualTo(Date value) {
            addCriterion("promotion_level_time <>", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeGreaterThan(Date value) {
            addCriterion("promotion_level_time >", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("promotion_level_time >=", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeLessThan(Date value) {
            addCriterion("promotion_level_time <", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeLessThanOrEqualTo(Date value) {
            addCriterion("promotion_level_time <=", value, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeIn(List<Date> values) {
            addCriterion("promotion_level_time in", values, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeNotIn(List<Date> values) {
            addCriterion("promotion_level_time not in", values, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeBetween(Date value1, Date value2) {
            addCriterion("promotion_level_time between", value1, value2, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andPromotionLevelTimeNotBetween(Date value1, Date value2) {
            addCriterion("promotion_level_time not between", value1, value2, "promotionLevelTime");
            return (Criteria) this;
        }

        public Criteria andSysUserIdIsNull() {
            addCriterion("sys_user_id is null");
            return (Criteria) this;
        }

        public Criteria andSysUserIdIsNotNull() {
            addCriterion("sys_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysUserIdEqualTo(String value) {
            addCriterion("sys_user_id =", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdNotEqualTo(String value) {
            addCriterion("sys_user_id <>", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdGreaterThan(String value) {
            addCriterion("sys_user_id >", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_user_id >=", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdLessThan(String value) {
            addCriterion("sys_user_id <", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdLessThanOrEqualTo(String value) {
            addCriterion("sys_user_id <=", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdLike(String value) {
            addCriterion("sys_user_id like", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdNotLike(String value) {
            addCriterion("sys_user_id not like", value, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdIn(List<String> values) {
            addCriterion("sys_user_id in", values, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdNotIn(List<String> values) {
            addCriterion("sys_user_id not in", values, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdBetween(String value1, String value2) {
            addCriterion("sys_user_id between", value1, value2, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andSysUserIdNotBetween(String value1, String value2) {
            addCriterion("sys_user_id not between", value1, value2, "sysUserId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdIsNull() {
            addCriterion("income_id is null");
            return (Criteria) this;
        }

        public Criteria andIncomeIdIsNotNull() {
            addCriterion("income_id is not null");
            return (Criteria) this;
        }

        public Criteria andIncomeIdEqualTo(String value) {
            addCriterion("income_id =", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdNotEqualTo(String value) {
            addCriterion("income_id <>", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdGreaterThan(String value) {
            addCriterion("income_id >", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdGreaterThanOrEqualTo(String value) {
            addCriterion("income_id >=", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdLessThan(String value) {
            addCriterion("income_id <", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdLessThanOrEqualTo(String value) {
            addCriterion("income_id <=", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdLike(String value) {
            addCriterion("income_id like", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdNotLike(String value) {
            addCriterion("income_id not like", value, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdIn(List<String> values) {
            addCriterion("income_id in", values, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdNotIn(List<String> values) {
            addCriterion("income_id not in", values, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdBetween(String value1, String value2) {
            addCriterion("income_id between", value1, value2, "incomeId");
            return (Criteria) this;
        }

        public Criteria andIncomeIdNotBetween(String value1, String value2) {
            addCriterion("income_id not between", value1, value2, "incomeId");
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