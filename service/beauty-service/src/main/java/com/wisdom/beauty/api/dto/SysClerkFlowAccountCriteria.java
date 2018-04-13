package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysClerkFlowAccountCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public SysClerkFlowAccountCriteria() {
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

        public Criteria andShopUserConsumeRecordIdIsNull() {
            addCriterion("shop_user_consume_record_id is null");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdIsNotNull() {
            addCriterion("shop_user_consume_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdEqualTo(String value) {
            addCriterion("shop_user_consume_record_id =", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdNotEqualTo(String value) {
            addCriterion("shop_user_consume_record_id <>", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdGreaterThan(String value) {
            addCriterion("shop_user_consume_record_id >", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_user_consume_record_id >=", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdLessThan(String value) {
            addCriterion("shop_user_consume_record_id <", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdLessThanOrEqualTo(String value) {
            addCriterion("shop_user_consume_record_id <=", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdLike(String value) {
            addCriterion("shop_user_consume_record_id like", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdNotLike(String value) {
            addCriterion("shop_user_consume_record_id not like", value, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdIn(List<String> values) {
            addCriterion("shop_user_consume_record_id in", values, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdNotIn(List<String> values) {
            addCriterion("shop_user_consume_record_id not in", values, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdBetween(String value1, String value2) {
            addCriterion("shop_user_consume_record_id between", value1, value2, "shopUserConsumeRecordId");
            return (Criteria) this;
        }

        public Criteria andShopUserConsumeRecordIdNotBetween(String value1, String value2) {
            addCriterion("shop_user_consume_record_id not between", value1, value2, "shopUserConsumeRecordId");
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

        public Criteria andSysUserNameIsNull() {
            addCriterion("sys_user_name is null");
            return (Criteria) this;
        }

        public Criteria andSysUserNameIsNotNull() {
            addCriterion("sys_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysUserNameEqualTo(String value) {
            addCriterion("sys_user_name =", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameNotEqualTo(String value) {
            addCriterion("sys_user_name <>", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameGreaterThan(String value) {
            addCriterion("sys_user_name >", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_user_name >=", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameLessThan(String value) {
            addCriterion("sys_user_name <", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameLessThanOrEqualTo(String value) {
            addCriterion("sys_user_name <=", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameLike(String value) {
            addCriterion("sys_user_name like", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameNotLike(String value) {
            addCriterion("sys_user_name not like", value, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameIn(List<String> values) {
            addCriterion("sys_user_name in", values, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameNotIn(List<String> values) {
            addCriterion("sys_user_name not in", values, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameBetween(String value1, String value2) {
            addCriterion("sys_user_name between", value1, value2, "sysUserName");
            return (Criteria) this;
        }

        public Criteria andSysUserNameNotBetween(String value1, String value2) {
            addCriterion("sys_user_name not between", value1, value2, "sysUserName");
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

        public Criteria andSysShopNameIsNull() {
            addCriterion("sys_shop_name is null");
            return (Criteria) this;
        }

        public Criteria andSysShopNameIsNotNull() {
            addCriterion("sys_shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopNameEqualTo(String value) {
            addCriterion("sys_shop_name =", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameNotEqualTo(String value) {
            addCriterion("sys_shop_name <>", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameGreaterThan(String value) {
            addCriterion("sys_shop_name >", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_name >=", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameLessThan(String value) {
            addCriterion("sys_shop_name <", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_name <=", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameLike(String value) {
            addCriterion("sys_shop_name like", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameNotLike(String value) {
            addCriterion("sys_shop_name not like", value, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameIn(List<String> values) {
            addCriterion("sys_shop_name in", values, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameNotIn(List<String> values) {
            addCriterion("sys_shop_name not in", values, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameBetween(String value1, String value2) {
            addCriterion("sys_shop_name between", value1, value2, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysShopNameNotBetween(String value1, String value2) {
            addCriterion("sys_shop_name not between", value1, value2, "sysShopName");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdIsNull() {
            addCriterion("sys_clerk_id is null");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdIsNotNull() {
            addCriterion("sys_clerk_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdEqualTo(String value) {
            addCriterion("sys_clerk_id =", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdNotEqualTo(String value) {
            addCriterion("sys_clerk_id <>", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdGreaterThan(String value) {
            addCriterion("sys_clerk_id >", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_clerk_id >=", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdLessThan(String value) {
            addCriterion("sys_clerk_id <", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdLessThanOrEqualTo(String value) {
            addCriterion("sys_clerk_id <=", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdLike(String value) {
            addCriterion("sys_clerk_id like", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdNotLike(String value) {
            addCriterion("sys_clerk_id not like", value, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdIn(List<String> values) {
            addCriterion("sys_clerk_id in", values, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdNotIn(List<String> values) {
            addCriterion("sys_clerk_id not in", values, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdBetween(String value1, String value2) {
            addCriterion("sys_clerk_id between", value1, value2, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysClerkIdNotBetween(String value1, String value2) {
            addCriterion("sys_clerk_id not between", value1, value2, "sysClerkId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdIsNull() {
            addCriterion("sys_boss_id is null");
            return (Criteria) this;
        }

        public Criteria andSysBossIdIsNotNull() {
            addCriterion("sys_boss_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysBossIdEqualTo(String value) {
            addCriterion("sys_boss_id =", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdNotEqualTo(String value) {
            addCriterion("sys_boss_id <>", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdGreaterThan(String value) {
            addCriterion("sys_boss_id >", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_boss_id >=", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdLessThan(String value) {
            addCriterion("sys_boss_id <", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdLessThanOrEqualTo(String value) {
            addCriterion("sys_boss_id <=", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdLike(String value) {
            addCriterion("sys_boss_id like", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdNotLike(String value) {
            addCriterion("sys_boss_id not like", value, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdIn(List<String> values) {
            addCriterion("sys_boss_id in", values, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdNotIn(List<String> values) {
            addCriterion("sys_boss_id not in", values, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdBetween(String value1, String value2) {
            addCriterion("sys_boss_id between", value1, value2, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andSysBossIdNotBetween(String value1, String value2) {
            addCriterion("sys_boss_id not between", value1, value2, "sysBossId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIsNull() {
            addCriterion("flow_amount is null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIsNotNull() {
            addCriterion("flow_amount is not null");
            return (Criteria) this;
        }

        public Criteria andFlowAmountEqualTo(BigDecimal value) {
            addCriterion("flow_amount =", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotEqualTo(BigDecimal value) {
            addCriterion("flow_amount <>", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountGreaterThan(BigDecimal value) {
            addCriterion("flow_amount >", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("flow_amount >=", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountLessThan(BigDecimal value) {
            addCriterion("flow_amount <", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("flow_amount <=", value, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountIn(List<BigDecimal> values) {
            addCriterion("flow_amount in", values, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotIn(List<BigDecimal> values) {
            addCriterion("flow_amount not in", values, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flow_amount between", value1, value2, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andFlowAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flow_amount not between", value1, value2, "flowAmount");
            return (Criteria) this;
        }

        public Criteria andOperDateIsNull() {
            addCriterion("oper_date is null");
            return (Criteria) this;
        }

        public Criteria andOperDateIsNotNull() {
            addCriterion("oper_date is not null");
            return (Criteria) this;
        }

        public Criteria andOperDateEqualTo(Date value) {
            addCriterion("oper_date =", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateNotEqualTo(Date value) {
            addCriterion("oper_date <>", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateGreaterThan(Date value) {
            addCriterion("oper_date >", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateGreaterThanOrEqualTo(Date value) {
            addCriterion("oper_date >=", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateLessThan(Date value) {
            addCriterion("oper_date <", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateLessThanOrEqualTo(Date value) {
            addCriterion("oper_date <=", value, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateIn(List<Date> values) {
            addCriterion("oper_date in", values, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateNotIn(List<Date> values) {
            addCriterion("oper_date not in", values, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateBetween(Date value1, Date value2) {
            addCriterion("oper_date between", value1, value2, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperDateNotBetween(Date value1, Date value2) {
            addCriterion("oper_date not between", value1, value2, "operDate");
            return (Criteria) this;
        }

        public Criteria andOperInfoIsNull() {
            addCriterion("oper_info is null");
            return (Criteria) this;
        }

        public Criteria andOperInfoIsNotNull() {
            addCriterion("oper_info is not null");
            return (Criteria) this;
        }

        public Criteria andOperInfoEqualTo(String value) {
            addCriterion("oper_info =", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoNotEqualTo(String value) {
            addCriterion("oper_info <>", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoGreaterThan(String value) {
            addCriterion("oper_info >", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoGreaterThanOrEqualTo(String value) {
            addCriterion("oper_info >=", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoLessThan(String value) {
            addCriterion("oper_info <", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoLessThanOrEqualTo(String value) {
            addCriterion("oper_info <=", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoLike(String value) {
            addCriterion("oper_info like", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoNotLike(String value) {
            addCriterion("oper_info not like", value, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoIn(List<String> values) {
            addCriterion("oper_info in", values, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoNotIn(List<String> values) {
            addCriterion("oper_info not in", values, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoBetween(String value1, String value2) {
            addCriterion("oper_info between", value1, value2, "operInfo");
            return (Criteria) this;
        }

        public Criteria andOperInfoNotBetween(String value1, String value2) {
            addCriterion("oper_info not between", value1, value2, "operInfo");
            return (Criteria) this;
        }

        public Criteria andDetailIsNull() {
            addCriterion("detail is null");
            return (Criteria) this;
        }

        public Criteria andDetailIsNotNull() {
            addCriterion("detail is not null");
            return (Criteria) this;
        }

        public Criteria andDetailEqualTo(String value) {
            addCriterion("detail =", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotEqualTo(String value) {
            addCriterion("detail <>", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThan(String value) {
            addCriterion("detail >", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThanOrEqualTo(String value) {
            addCriterion("detail >=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThan(String value) {
            addCriterion("detail <", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThanOrEqualTo(String value) {
            addCriterion("detail <=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLike(String value) {
            addCriterion("detail like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotLike(String value) {
            addCriterion("detail not like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailIn(List<String> values) {
            addCriterion("detail in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotIn(List<String> values) {
            addCriterion("detail not in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailBetween(String value1, String value2) {
            addCriterion("detail between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotBetween(String value1, String value2) {
            addCriterion("detail not between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andSignUrlIsNull() {
            addCriterion("sign_url is null");
            return (Criteria) this;
        }

        public Criteria andSignUrlIsNotNull() {
            addCriterion("sign_url is not null");
            return (Criteria) this;
        }

        public Criteria andSignUrlEqualTo(String value) {
            addCriterion("sign_url =", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlNotEqualTo(String value) {
            addCriterion("sign_url <>", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlGreaterThan(String value) {
            addCriterion("sign_url >", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlGreaterThanOrEqualTo(String value) {
            addCriterion("sign_url >=", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlLessThan(String value) {
            addCriterion("sign_url <", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlLessThanOrEqualTo(String value) {
            addCriterion("sign_url <=", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlLike(String value) {
            addCriterion("sign_url like", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlNotLike(String value) {
            addCriterion("sign_url not like", value, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlIn(List<String> values) {
            addCriterion("sign_url in", values, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlNotIn(List<String> values) {
            addCriterion("sign_url not in", values, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlBetween(String value1, String value2) {
            addCriterion("sign_url between", value1, value2, "signUrl");
            return (Criteria) this;
        }

        public Criteria andSignUrlNotBetween(String value1, String value2) {
            addCriterion("sign_url not between", value1, value2, "signUrl");
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

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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