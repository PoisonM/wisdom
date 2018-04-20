package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopAppointServiceCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopAppointServiceCriteria() {
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

        public Criteria andShopProjectIdIsNull() {
            addCriterion("shop_project_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdIsNotNull() {
            addCriterion("shop_project_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdEqualTo(String value) {
            addCriterion("shop_project_id =", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdNotEqualTo(String value) {
            addCriterion("shop_project_id <>", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdGreaterThan(String value) {
            addCriterion("shop_project_id >", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_id >=", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdLessThan(String value) {
            addCriterion("shop_project_id <", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdLessThanOrEqualTo(String value) {
            addCriterion("shop_project_id <=", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdLike(String value) {
            addCriterion("shop_project_id like", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdNotLike(String value) {
            addCriterion("shop_project_id not like", value, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdIn(List<String> values) {
            addCriterion("shop_project_id in", values, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdNotIn(List<String> values) {
            addCriterion("shop_project_id not in", values, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdBetween(String value1, String value2) {
            addCriterion("shop_project_id between", value1, value2, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectIdNotBetween(String value1, String value2) {
            addCriterion("shop_project_id not between", value1, value2, "shopProjectId");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameIsNull() {
            addCriterion("shop_project_name is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameIsNotNull() {
            addCriterion("shop_project_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameEqualTo(String value) {
            addCriterion("shop_project_name =", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameNotEqualTo(String value) {
            addCriterion("shop_project_name <>", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameGreaterThan(String value) {
            addCriterion("shop_project_name >", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_name >=", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameLessThan(String value) {
            addCriterion("shop_project_name <", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameLessThanOrEqualTo(String value) {
            addCriterion("shop_project_name <=", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameLike(String value) {
            addCriterion("shop_project_name like", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameNotLike(String value) {
            addCriterion("shop_project_name not like", value, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameIn(List<String> values) {
            addCriterion("shop_project_name in", values, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameNotIn(List<String> values) {
            addCriterion("shop_project_name not in", values, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameBetween(String value1, String value2) {
            addCriterion("shop_project_name between", value1, value2, "shopProjectName");
            return (Criteria) this;
        }

        public Criteria andShopProjectNameNotBetween(String value1, String value2) {
            addCriterion("shop_project_name not between", value1, value2, "shopProjectName");
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

        public Criteria andAppointStartTimeIsNull() {
            addCriterion("appoint_start_time is null");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeIsNotNull() {
            addCriterion("appoint_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeEqualTo(Date value) {
            addCriterion("appoint_start_time =", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeNotEqualTo(Date value) {
            addCriterion("appoint_start_time <>", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeGreaterThan(Date value) {
            addCriterion("appoint_start_time >", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("appoint_start_time >=", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeLessThan(Date value) {
            addCriterion("appoint_start_time <", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("appoint_start_time <=", value, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeIn(List<Date> values) {
            addCriterion("appoint_start_time in", values, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeNotIn(List<Date> values) {
            addCriterion("appoint_start_time not in", values, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeBetween(Date value1, Date value2) {
            addCriterion("appoint_start_time between", value1, value2, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("appoint_start_time not between", value1, value2, "appointStartTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeIsNull() {
            addCriterion("appoint_end_time is null");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeIsNotNull() {
            addCriterion("appoint_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeEqualTo(Date value) {
            addCriterion("appoint_end_time =", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeNotEqualTo(Date value) {
            addCriterion("appoint_end_time <>", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeGreaterThan(Date value) {
            addCriterion("appoint_end_time >", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("appoint_end_time >=", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeLessThan(Date value) {
            addCriterion("appoint_end_time <", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("appoint_end_time <=", value, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeIn(List<Date> values) {
            addCriterion("appoint_end_time in", values, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeNotIn(List<Date> values) {
            addCriterion("appoint_end_time not in", values, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeBetween(Date value1, Date value2) {
            addCriterion("appoint_end_time between", value1, value2, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("appoint_end_time not between", value1, value2, "appointEndTime");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodIsNull() {
            addCriterion("appoint_period is null");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodIsNotNull() {
            addCriterion("appoint_period is not null");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodEqualTo(Integer value) {
            addCriterion("appoint_period =", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodNotEqualTo(Integer value) {
            addCriterion("appoint_period <>", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodGreaterThan(Integer value) {
            addCriterion("appoint_period >", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("appoint_period >=", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodLessThan(Integer value) {
            addCriterion("appoint_period <", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("appoint_period <=", value, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodIn(List<Integer> values) {
            addCriterion("appoint_period in", values, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodNotIn(List<Integer> values) {
            addCriterion("appoint_period not in", values, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodBetween(Integer value1, Integer value2) {
            addCriterion("appoint_period between", value1, value2, "appointPeriod");
            return (Criteria) this;
        }

        public Criteria andAppointPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("appoint_period not between", value1, value2, "appointPeriod");
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

        public Criteria andSysUserPhoneIsNull() {
            addCriterion("sys_user_phone is null");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneIsNotNull() {
            addCriterion("sys_user_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneEqualTo(String value) {
            addCriterion("sys_user_phone =", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneNotEqualTo(String value) {
            addCriterion("sys_user_phone <>", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneGreaterThan(String value) {
            addCriterion("sys_user_phone >", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("sys_user_phone >=", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneLessThan(String value) {
            addCriterion("sys_user_phone <", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneLessThanOrEqualTo(String value) {
            addCriterion("sys_user_phone <=", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneLike(String value) {
            addCriterion("sys_user_phone like", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneNotLike(String value) {
            addCriterion("sys_user_phone not like", value, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneIn(List<String> values) {
            addCriterion("sys_user_phone in", values, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneNotIn(List<String> values) {
            addCriterion("sys_user_phone not in", values, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneBetween(String value1, String value2) {
            addCriterion("sys_user_phone between", value1, value2, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andSysUserPhoneNotBetween(String value1, String value2) {
            addCriterion("sys_user_phone not between", value1, value2, "sysUserPhone");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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