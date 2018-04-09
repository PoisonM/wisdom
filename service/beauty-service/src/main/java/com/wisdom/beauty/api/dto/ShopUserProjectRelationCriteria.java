package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopUserProjectRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopUserProjectRelationCriteria() {
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

        public Criteria andShopAppointmentIdIsNull() {
            addCriterion("shop_appointment_id is null");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdIsNotNull() {
            addCriterion("shop_appointment_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdEqualTo(String value) {
            addCriterion("shop_appointment_id =", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdNotEqualTo(String value) {
            addCriterion("shop_appointment_id <>", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdGreaterThan(String value) {
            addCriterion("shop_appointment_id >", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_appointment_id >=", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdLessThan(String value) {
            addCriterion("shop_appointment_id <", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdLessThanOrEqualTo(String value) {
            addCriterion("shop_appointment_id <=", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdLike(String value) {
            addCriterion("shop_appointment_id like", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdNotLike(String value) {
            addCriterion("shop_appointment_id not like", value, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdIn(List<String> values) {
            addCriterion("shop_appointment_id in", values, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdNotIn(List<String> values) {
            addCriterion("shop_appointment_id not in", values, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdBetween(String value1, String value2) {
            addCriterion("shop_appointment_id between", value1, value2, "shopAppointmentId");
            return (Criteria) this;
        }

        public Criteria andShopAppointmentIdNotBetween(String value1, String value2) {
            addCriterion("shop_appointment_id not between", value1, value2, "shopAppointmentId");
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

        public Criteria andUseStyleIsNull() {
            addCriterion("use_style is null");
            return (Criteria) this;
        }

        public Criteria andUseStyleIsNotNull() {
            addCriterion("use_style is not null");
            return (Criteria) this;
        }

        public Criteria andUseStyleEqualTo(String value) {
            addCriterion("use_style =", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleNotEqualTo(String value) {
            addCriterion("use_style <>", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleGreaterThan(String value) {
            addCriterion("use_style >", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleGreaterThanOrEqualTo(String value) {
            addCriterion("use_style >=", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleLessThan(String value) {
            addCriterion("use_style <", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleLessThanOrEqualTo(String value) {
            addCriterion("use_style <=", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleLike(String value) {
            addCriterion("use_style like", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleNotLike(String value) {
            addCriterion("use_style not like", value, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleIn(List<String> values) {
            addCriterion("use_style in", values, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleNotIn(List<String> values) {
            addCriterion("use_style not in", values, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleBetween(String value1, String value2) {
            addCriterion("use_style between", value1, value2, "useStyle");
            return (Criteria) this;
        }

        public Criteria andUseStyleNotBetween(String value1, String value2) {
            addCriterion("use_style not between", value1, value2, "useStyle");
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

        public Criteria andSysShopProjectIdIsNull() {
            addCriterion("sys_shop_project_id is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdIsNotNull() {
            addCriterion("sys_shop_project_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdEqualTo(String value) {
            addCriterion("sys_shop_project_id =", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdNotEqualTo(String value) {
            addCriterion("sys_shop_project_id <>", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdGreaterThan(String value) {
            addCriterion("sys_shop_project_id >", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_project_id >=", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdLessThan(String value) {
            addCriterion("sys_shop_project_id <", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_project_id <=", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdLike(String value) {
            addCriterion("sys_shop_project_id like", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdNotLike(String value) {
            addCriterion("sys_shop_project_id not like", value, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdIn(List<String> values) {
            addCriterion("sys_shop_project_id in", values, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdNotIn(List<String> values) {
            addCriterion("sys_shop_project_id not in", values, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdBetween(String value1, String value2) {
            addCriterion("sys_shop_project_id between", value1, value2, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectIdNotBetween(String value1, String value2) {
            addCriterion("sys_shop_project_id not between", value1, value2, "sysShopProjectId");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameIsNull() {
            addCriterion("sys_shop_project_name is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameIsNotNull() {
            addCriterion("sys_shop_project_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameEqualTo(String value) {
            addCriterion("sys_shop_project_name =", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameNotEqualTo(String value) {
            addCriterion("sys_shop_project_name <>", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameGreaterThan(String value) {
            addCriterion("sys_shop_project_name >", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_project_name >=", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameLessThan(String value) {
            addCriterion("sys_shop_project_name <", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_project_name <=", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameLike(String value) {
            addCriterion("sys_shop_project_name like", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameNotLike(String value) {
            addCriterion("sys_shop_project_name not like", value, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameIn(List<String> values) {
            addCriterion("sys_shop_project_name in", values, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameNotIn(List<String> values) {
            addCriterion("sys_shop_project_name not in", values, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameBetween(String value1, String value2) {
            addCriterion("sys_shop_project_name between", value1, value2, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectNameNotBetween(String value1, String value2) {
            addCriterion("sys_shop_project_name not between", value1, value2, "sysShopProjectName");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountIsNull() {
            addCriterion("sys_shop_project_init_amount is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountIsNotNull() {
            addCriterion("sys_shop_project_init_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountEqualTo(Long value) {
            addCriterion("sys_shop_project_init_amount =", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountNotEqualTo(Long value) {
            addCriterion("sys_shop_project_init_amount <>", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountGreaterThan(Long value) {
            addCriterion("sys_shop_project_init_amount >", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("sys_shop_project_init_amount >=", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountLessThan(Long value) {
            addCriterion("sys_shop_project_init_amount <", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountLessThanOrEqualTo(Long value) {
            addCriterion("sys_shop_project_init_amount <=", value, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountIn(List<Long> values) {
            addCriterion("sys_shop_project_init_amount in", values, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountNotIn(List<Long> values) {
            addCriterion("sys_shop_project_init_amount not in", values, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountBetween(Long value1, Long value2) {
            addCriterion("sys_shop_project_init_amount between", value1, value2, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitAmountNotBetween(Long value1, Long value2) {
            addCriterion("sys_shop_project_init_amount not between", value1, value2, "sysShopProjectInitAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountIsNull() {
            addCriterion("sys_shop_project_surplus_amount is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountIsNotNull() {
            addCriterion("sys_shop_project_surplus_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountEqualTo(Long value) {
            addCriterion("sys_shop_project_surplus_amount =", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountNotEqualTo(Long value) {
            addCriterion("sys_shop_project_surplus_amount <>", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountGreaterThan(Long value) {
            addCriterion("sys_shop_project_surplus_amount >", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("sys_shop_project_surplus_amount >=", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountLessThan(Long value) {
            addCriterion("sys_shop_project_surplus_amount <", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountLessThanOrEqualTo(Long value) {
            addCriterion("sys_shop_project_surplus_amount <=", value, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountIn(List<Long> values) {
            addCriterion("sys_shop_project_surplus_amount in", values, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountNotIn(List<Long> values) {
            addCriterion("sys_shop_project_surplus_amount not in", values, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountBetween(Long value1, Long value2) {
            addCriterion("sys_shop_project_surplus_amount between", value1, value2, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusAmountNotBetween(Long value1, Long value2) {
            addCriterion("sys_shop_project_surplus_amount not between", value1, value2, "sysShopProjectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesIsNull() {
            addCriterion("sys_shop_project_surplus_times is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesIsNotNull() {
            addCriterion("sys_shop_project_surplus_times is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesEqualTo(Integer value) {
            addCriterion("sys_shop_project_surplus_times =", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesNotEqualTo(Integer value) {
            addCriterion("sys_shop_project_surplus_times <>", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesGreaterThan(Integer value) {
            addCriterion("sys_shop_project_surplus_times >", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("sys_shop_project_surplus_times >=", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesLessThan(Integer value) {
            addCriterion("sys_shop_project_surplus_times <", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesLessThanOrEqualTo(Integer value) {
            addCriterion("sys_shop_project_surplus_times <=", value, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesIn(List<Integer> values) {
            addCriterion("sys_shop_project_surplus_times in", values, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesNotIn(List<Integer> values) {
            addCriterion("sys_shop_project_surplus_times not in", values, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesBetween(Integer value1, Integer value2) {
            addCriterion("sys_shop_project_surplus_times between", value1, value2, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectSurplusTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("sys_shop_project_surplus_times not between", value1, value2, "sysShopProjectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesIsNull() {
            addCriterion("sys_shop_project_init_times is null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesIsNotNull() {
            addCriterion("sys_shop_project_init_times is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesEqualTo(Integer value) {
            addCriterion("sys_shop_project_init_times =", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesNotEqualTo(Integer value) {
            addCriterion("sys_shop_project_init_times <>", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesGreaterThan(Integer value) {
            addCriterion("sys_shop_project_init_times >", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("sys_shop_project_init_times >=", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesLessThan(Integer value) {
            addCriterion("sys_shop_project_init_times <", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesLessThanOrEqualTo(Integer value) {
            addCriterion("sys_shop_project_init_times <=", value, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesIn(List<Integer> values) {
            addCriterion("sys_shop_project_init_times in", values, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesNotIn(List<Integer> values) {
            addCriterion("sys_shop_project_init_times not in", values, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesBetween(Integer value1, Integer value2) {
            addCriterion("sys_shop_project_init_times between", value1, value2, "sysShopProjectInitTimes");
            return (Criteria) this;
        }

        public Criteria andSysShopProjectInitTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("sys_shop_project_init_times not between", value1, value2, "sysShopProjectInitTimes");
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