package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopSummaryCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopSummaryCriteria() {
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

        public Criteria andSysBossNameIsNull() {
            addCriterion("sys_boss_name is null");
            return (Criteria) this;
        }

        public Criteria andSysBossNameIsNotNull() {
            addCriterion("sys_boss_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysBossNameEqualTo(String value) {
            addCriterion("sys_boss_name =", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameNotEqualTo(String value) {
            addCriterion("sys_boss_name <>", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameGreaterThan(String value) {
            addCriterion("sys_boss_name >", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_boss_name >=", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameLessThan(String value) {
            addCriterion("sys_boss_name <", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameLessThanOrEqualTo(String value) {
            addCriterion("sys_boss_name <=", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameLike(String value) {
            addCriterion("sys_boss_name like", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameNotLike(String value) {
            addCriterion("sys_boss_name not like", value, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameIn(List<String> values) {
            addCriterion("sys_boss_name in", values, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameNotIn(List<String> values) {
            addCriterion("sys_boss_name not in", values, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameBetween(String value1, String value2) {
            addCriterion("sys_boss_name between", value1, value2, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andSysBossNameNotBetween(String value1, String value2) {
            addCriterion("sys_boss_name not between", value1, value2, "sysBossName");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryIsNull() {
            addCriterion("today_summary is null");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryIsNotNull() {
            addCriterion("today_summary is not null");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryEqualTo(String value) {
            addCriterion("today_summary =", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryNotEqualTo(String value) {
            addCriterion("today_summary <>", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryGreaterThan(String value) {
            addCriterion("today_summary >", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryGreaterThanOrEqualTo(String value) {
            addCriterion("today_summary >=", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryLessThan(String value) {
            addCriterion("today_summary <", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryLessThanOrEqualTo(String value) {
            addCriterion("today_summary <=", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryLike(String value) {
            addCriterion("today_summary like", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryNotLike(String value) {
            addCriterion("today_summary not like", value, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryIn(List<String> values) {
            addCriterion("today_summary in", values, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryNotIn(List<String> values) {
            addCriterion("today_summary not in", values, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryBetween(String value1, String value2) {
            addCriterion("today_summary between", value1, value2, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTodaySummaryNotBetween(String value1, String value2) {
            addCriterion("today_summary not between", value1, value2, "todaySummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryIsNull() {
            addCriterion("tomorrow_summary is null");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryIsNotNull() {
            addCriterion("tomorrow_summary is not null");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryEqualTo(String value) {
            addCriterion("tomorrow_summary =", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryNotEqualTo(String value) {
            addCriterion("tomorrow_summary <>", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryGreaterThan(String value) {
            addCriterion("tomorrow_summary >", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("tomorrow_summary >=", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryLessThan(String value) {
            addCriterion("tomorrow_summary <", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryLessThanOrEqualTo(String value) {
            addCriterion("tomorrow_summary <=", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryLike(String value) {
            addCriterion("tomorrow_summary like", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryNotLike(String value) {
            addCriterion("tomorrow_summary not like", value, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryIn(List<String> values) {
            addCriterion("tomorrow_summary in", values, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryNotIn(List<String> values) {
            addCriterion("tomorrow_summary not in", values, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryBetween(String value1, String value2) {
            addCriterion("tomorrow_summary between", value1, value2, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andTomorrowSummaryNotBetween(String value1, String value2) {
            addCriterion("tomorrow_summary not between", value1, value2, "tomorrowSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryIsNull() {
            addCriterion("content_summary is null");
            return (Criteria) this;
        }

        public Criteria andContentSummaryIsNotNull() {
            addCriterion("content_summary is not null");
            return (Criteria) this;
        }

        public Criteria andContentSummaryEqualTo(String value) {
            addCriterion("content_summary =", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryNotEqualTo(String value) {
            addCriterion("content_summary <>", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryGreaterThan(String value) {
            addCriterion("content_summary >", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryGreaterThanOrEqualTo(String value) {
            addCriterion("content_summary >=", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryLessThan(String value) {
            addCriterion("content_summary <", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryLessThanOrEqualTo(String value) {
            addCriterion("content_summary <=", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryLike(String value) {
            addCriterion("content_summary like", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryNotLike(String value) {
            addCriterion("content_summary not like", value, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryIn(List<String> values) {
            addCriterion("content_summary in", values, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryNotIn(List<String> values) {
            addCriterion("content_summary not in", values, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryBetween(String value1, String value2) {
            addCriterion("content_summary between", value1, value2, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andContentSummaryNotBetween(String value1, String value2) {
            addCriterion("content_summary not between", value1, value2, "contentSummary");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeIsNull() {
            addCriterion("summary_type is null");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeIsNotNull() {
            addCriterion("summary_type is not null");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeEqualTo(String value) {
            addCriterion("summary_type =", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeNotEqualTo(String value) {
            addCriterion("summary_type <>", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeGreaterThan(String value) {
            addCriterion("summary_type >", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeGreaterThanOrEqualTo(String value) {
            addCriterion("summary_type >=", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeLessThan(String value) {
            addCriterion("summary_type <", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeLessThanOrEqualTo(String value) {
            addCriterion("summary_type <=", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeLike(String value) {
            addCriterion("summary_type like", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeNotLike(String value) {
            addCriterion("summary_type not like", value, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeIn(List<String> values) {
            addCriterion("summary_type in", values, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeNotIn(List<String> values) {
            addCriterion("summary_type not in", values, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeBetween(String value1, String value2) {
            addCriterion("summary_type between", value1, value2, "summaryType");
            return (Criteria) this;
        }

        public Criteria andSummaryTypeNotBetween(String value1, String value2) {
            addCriterion("summary_type not between", value1, value2, "summaryType");
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