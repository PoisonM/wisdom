package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopUserRechargeCardCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopUserRechargeCardCriteria() {
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

        public Criteria andShopRechargeCardIdIsNull() {
            addCriterion("shop_recharge_card_id is null");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdIsNotNull() {
            addCriterion("shop_recharge_card_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdEqualTo(String value) {
            addCriterion("shop_recharge_card_id =", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdNotEqualTo(String value) {
            addCriterion("shop_recharge_card_id <>", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdGreaterThan(String value) {
            addCriterion("shop_recharge_card_id >", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_recharge_card_id >=", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdLessThan(String value) {
            addCriterion("shop_recharge_card_id <", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdLessThanOrEqualTo(String value) {
            addCriterion("shop_recharge_card_id <=", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdLike(String value) {
            addCriterion("shop_recharge_card_id like", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdNotLike(String value) {
            addCriterion("shop_recharge_card_id not like", value, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdIn(List<String> values) {
            addCriterion("shop_recharge_card_id in", values, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdNotIn(List<String> values) {
            addCriterion("shop_recharge_card_id not in", values, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdBetween(String value1, String value2) {
            addCriterion("shop_recharge_card_id between", value1, value2, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardIdNotBetween(String value1, String value2) {
            addCriterion("shop_recharge_card_id not between", value1, value2, "shopRechargeCardId");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameIsNull() {
            addCriterion("shop_recharge_card_name is null");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameIsNotNull() {
            addCriterion("shop_recharge_card_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameEqualTo(String value) {
            addCriterion("shop_recharge_card_name =", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameNotEqualTo(String value) {
            addCriterion("shop_recharge_card_name <>", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameGreaterThan(String value) {
            addCriterion("shop_recharge_card_name >", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_recharge_card_name >=", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameLessThan(String value) {
            addCriterion("shop_recharge_card_name <", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameLessThanOrEqualTo(String value) {
            addCriterion("shop_recharge_card_name <=", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameLike(String value) {
            addCriterion("shop_recharge_card_name like", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameNotLike(String value) {
            addCriterion("shop_recharge_card_name not like", value, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameIn(List<String> values) {
            addCriterion("shop_recharge_card_name in", values, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameNotIn(List<String> values) {
            addCriterion("shop_recharge_card_name not in", values, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameBetween(String value1, String value2) {
            addCriterion("shop_recharge_card_name between", value1, value2, "shopRechargeCardName");
            return (Criteria) this;
        }

        public Criteria andShopRechargeCardNameNotBetween(String value1, String value2) {
            addCriterion("shop_recharge_card_name not between", value1, value2, "shopRechargeCardName");
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

        public Criteria andSysClerkNameIsNull() {
            addCriterion("sys_clerk_name is null");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameIsNotNull() {
            addCriterion("sys_clerk_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameEqualTo(String value) {
            addCriterion("sys_clerk_name =", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameNotEqualTo(String value) {
            addCriterion("sys_clerk_name <>", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameGreaterThan(String value) {
            addCriterion("sys_clerk_name >", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_clerk_name >=", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameLessThan(String value) {
            addCriterion("sys_clerk_name <", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameLessThanOrEqualTo(String value) {
            addCriterion("sys_clerk_name <=", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameLike(String value) {
            addCriterion("sys_clerk_name like", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameNotLike(String value) {
            addCriterion("sys_clerk_name not like", value, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameIn(List<String> values) {
            addCriterion("sys_clerk_name in", values, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameNotIn(List<String> values) {
            addCriterion("sys_clerk_name not in", values, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameBetween(String value1, String value2) {
            addCriterion("sys_clerk_name between", value1, value2, "sysClerkName");
            return (Criteria) this;
        }

        public Criteria andSysClerkNameNotBetween(String value1, String value2) {
            addCriterion("sys_clerk_name not between", value1, value2, "sysClerkName");
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

        public Criteria andTimeDiscountIsNull() {
            addCriterion("time_discount is null");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountIsNotNull() {
            addCriterion("time_discount is not null");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountEqualTo(Float value) {
            addCriterion("time_discount =", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountNotEqualTo(Float value) {
            addCriterion("time_discount <>", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountGreaterThan(Float value) {
            addCriterion("time_discount >", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountGreaterThanOrEqualTo(Float value) {
            addCriterion("time_discount >=", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountLessThan(Float value) {
            addCriterion("time_discount <", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountLessThanOrEqualTo(Float value) {
            addCriterion("time_discount <=", value, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountIn(List<Float> values) {
            addCriterion("time_discount in", values, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountNotIn(List<Float> values) {
            addCriterion("time_discount not in", values, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountBetween(Float value1, Float value2) {
            addCriterion("time_discount between", value1, value2, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andTimeDiscountNotBetween(Float value1, Float value2) {
            addCriterion("time_discount not between", value1, value2, "timeDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountIsNull() {
            addCriterion("period_discount is null");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountIsNotNull() {
            addCriterion("period_discount is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountEqualTo(Float value) {
            addCriterion("period_discount =", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountNotEqualTo(Float value) {
            addCriterion("period_discount <>", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountGreaterThan(Float value) {
            addCriterion("period_discount >", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountGreaterThanOrEqualTo(Float value) {
            addCriterion("period_discount >=", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountLessThan(Float value) {
            addCriterion("period_discount <", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountLessThanOrEqualTo(Float value) {
            addCriterion("period_discount <=", value, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountIn(List<Float> values) {
            addCriterion("period_discount in", values, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountNotIn(List<Float> values) {
            addCriterion("period_discount not in", values, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountBetween(Float value1, Float value2) {
            addCriterion("period_discount between", value1, value2, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andPeriodDiscountNotBetween(Float value1, Float value2) {
            addCriterion("period_discount not between", value1, value2, "periodDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIsNull() {
            addCriterion("product_discount is null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIsNotNull() {
            addCriterion("product_discount is not null");
            return (Criteria) this;
        }

        public Criteria andProductDiscountEqualTo(Float value) {
            addCriterion("product_discount =", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotEqualTo(Float value) {
            addCriterion("product_discount <>", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountGreaterThan(Float value) {
            addCriterion("product_discount >", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountGreaterThanOrEqualTo(Float value) {
            addCriterion("product_discount >=", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountLessThan(Float value) {
            addCriterion("product_discount <", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountLessThanOrEqualTo(Float value) {
            addCriterion("product_discount <=", value, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountIn(List<Float> values) {
            addCriterion("product_discount in", values, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotIn(List<Float> values) {
            addCriterion("product_discount not in", values, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountBetween(Float value1, Float value2) {
            addCriterion("product_discount between", value1, value2, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andProductDiscountNotBetween(Float value1, Float value2) {
            addCriterion("product_discount not between", value1, value2, "productDiscount");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeIsNull() {
            addCriterion("recharge_card_type is null");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeIsNotNull() {
            addCriterion("recharge_card_type is not null");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeEqualTo(String value) {
            addCriterion("recharge_card_type =", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeNotEqualTo(String value) {
            addCriterion("recharge_card_type <>", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeGreaterThan(String value) {
            addCriterion("recharge_card_type >", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("recharge_card_type >=", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeLessThan(String value) {
            addCriterion("recharge_card_type <", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeLessThanOrEqualTo(String value) {
            addCriterion("recharge_card_type <=", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeLike(String value) {
            addCriterion("recharge_card_type like", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeNotLike(String value) {
            addCriterion("recharge_card_type not like", value, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeIn(List<String> values) {
            addCriterion("recharge_card_type in", values, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeNotIn(List<String> values) {
            addCriterion("recharge_card_type not in", values, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeBetween(String value1, String value2) {
            addCriterion("recharge_card_type between", value1, value2, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andRechargeCardTypeNotBetween(String value1, String value2) {
            addCriterion("recharge_card_type not between", value1, value2, "rechargeCardType");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNull() {
            addCriterion("image_url is null");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNotNull() {
            addCriterion("image_url is not null");
            return (Criteria) this;
        }

        public Criteria andImageUrlEqualTo(String value) {
            addCriterion("image_url =", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotEqualTo(String value) {
            addCriterion("image_url <>", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThan(String value) {
            addCriterion("image_url >", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("image_url >=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThan(String value) {
            addCriterion("image_url <", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThanOrEqualTo(String value) {
            addCriterion("image_url <=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLike(String value) {
            addCriterion("image_url like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotLike(String value) {
            addCriterion("image_url not like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlIn(List<String> values) {
            addCriterion("image_url in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotIn(List<String> values) {
            addCriterion("image_url not in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlBetween(String value1, String value2) {
            addCriterion("image_url between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotBetween(String value1, String value2) {
            addCriterion("image_url not between", value1, value2, "imageUrl");
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

        public Criteria andDiscountDescIsNull() {
            addCriterion("discount_desc is null");
            return (Criteria) this;
        }

        public Criteria andDiscountDescIsNotNull() {
            addCriterion("discount_desc is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountDescEqualTo(String value) {
            addCriterion("discount_desc =", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescNotEqualTo(String value) {
            addCriterion("discount_desc <>", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescGreaterThan(String value) {
            addCriterion("discount_desc >", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescGreaterThanOrEqualTo(String value) {
            addCriterion("discount_desc >=", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescLessThan(String value) {
            addCriterion("discount_desc <", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescLessThanOrEqualTo(String value) {
            addCriterion("discount_desc <=", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescLike(String value) {
            addCriterion("discount_desc like", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescNotLike(String value) {
            addCriterion("discount_desc not like", value, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescIn(List<String> values) {
            addCriterion("discount_desc in", values, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescNotIn(List<String> values) {
            addCriterion("discount_desc not in", values, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescBetween(String value1, String value2) {
            addCriterion("discount_desc between", value1, value2, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andDiscountDescNotBetween(String value1, String value2) {
            addCriterion("discount_desc not between", value1, value2, "discountDesc");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountIsNull() {
            addCriterion("surplus_amount is null");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountIsNotNull() {
            addCriterion("surplus_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountEqualTo(BigDecimal value) {
            addCriterion("surplus_amount =", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountNotEqualTo(BigDecimal value) {
            addCriterion("surplus_amount <>", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountGreaterThan(BigDecimal value) {
            addCriterion("surplus_amount >", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("surplus_amount >=", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountLessThan(BigDecimal value) {
            addCriterion("surplus_amount <", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("surplus_amount <=", value, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountIn(List<BigDecimal> values) {
            addCriterion("surplus_amount in", values, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountNotIn(List<BigDecimal> values) {
            addCriterion("surplus_amount not in", values, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("surplus_amount between", value1, value2, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andSurplusAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("surplus_amount not between", value1, value2, "surplusAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNull() {
            addCriterion("init_amount is null");
            return (Criteria) this;
        }

        public Criteria andInitAmountIsNotNull() {
            addCriterion("init_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInitAmountEqualTo(BigDecimal value) {
            addCriterion("init_amount =", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotEqualTo(BigDecimal value) {
            addCriterion("init_amount <>", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThan(BigDecimal value) {
            addCriterion("init_amount >", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("init_amount >=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThan(BigDecimal value) {
            addCriterion("init_amount <", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("init_amount <=", value, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountIn(List<BigDecimal> values) {
            addCriterion("init_amount in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotIn(List<BigDecimal> values) {
            addCriterion("init_amount not in", values, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("init_amount between", value1, value2, "initAmount");
            return (Criteria) this;
        }

        public Criteria andInitAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("init_amount not between", value1, value2, "initAmount");
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