package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopCustomerProjectGroupRelRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopCustomerProjectGroupRelRelationCriteria() {
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

        public Criteria andSysCustomerIdIsNull() {
            addCriterion("sys_customer_id is null");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdIsNotNull() {
            addCriterion("sys_customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdEqualTo(String value) {
            addCriterion("sys_customer_id =", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdNotEqualTo(String value) {
            addCriterion("sys_customer_id <>", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdGreaterThan(String value) {
            addCriterion("sys_customer_id >", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_customer_id >=", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdLessThan(String value) {
            addCriterion("sys_customer_id <", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("sys_customer_id <=", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdLike(String value) {
            addCriterion("sys_customer_id like", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdNotLike(String value) {
            addCriterion("sys_customer_id not like", value, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdIn(List<String> values) {
            addCriterion("sys_customer_id in", values, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdNotIn(List<String> values) {
            addCriterion("sys_customer_id not in", values, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdBetween(String value1, String value2) {
            addCriterion("sys_customer_id between", value1, value2, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andSysCustomerIdNotBetween(String value1, String value2) {
            addCriterion("sys_customer_id not between", value1, value2, "sysCustomerId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdIsNull() {
            addCriterion("shop_project_info_group_relation_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdIsNotNull() {
            addCriterion("shop_project_info_group_relation_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdEqualTo(String value) {
            addCriterion("shop_project_info_group_relation_id =", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdNotEqualTo(String value) {
            addCriterion("shop_project_info_group_relation_id <>", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdGreaterThan(String value) {
            addCriterion("shop_project_info_group_relation_id >", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_info_group_relation_id >=", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdLessThan(String value) {
            addCriterion("shop_project_info_group_relation_id <", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdLessThanOrEqualTo(String value) {
            addCriterion("shop_project_info_group_relation_id <=", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdLike(String value) {
            addCriterion("shop_project_info_group_relation_id like", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdNotLike(String value) {
            addCriterion("shop_project_info_group_relation_id not like", value, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdIn(List<String> values) {
            addCriterion("shop_project_info_group_relation_id in", values, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdNotIn(List<String> values) {
            addCriterion("shop_project_info_group_relation_id not in", values, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdBetween(String value1, String value2) {
            addCriterion("shop_project_info_group_relation_id between", value1, value2, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoGroupRelationIdNotBetween(String value1, String value2) {
            addCriterion("shop_project_info_group_relation_id not between", value1, value2, "shopProjectInfoGroupRelationId");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesIsNull() {
            addCriterion("project_init_times is null");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesIsNotNull() {
            addCriterion("project_init_times is not null");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesEqualTo(Integer value) {
            addCriterion("project_init_times =", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesNotEqualTo(Integer value) {
            addCriterion("project_init_times <>", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesGreaterThan(Integer value) {
            addCriterion("project_init_times >", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_init_times >=", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesLessThan(Integer value) {
            addCriterion("project_init_times <", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesLessThanOrEqualTo(Integer value) {
            addCriterion("project_init_times <=", value, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesIn(List<Integer> values) {
            addCriterion("project_init_times in", values, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesNotIn(List<Integer> values) {
            addCriterion("project_init_times not in", values, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesBetween(Integer value1, Integer value2) {
            addCriterion("project_init_times between", value1, value2, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("project_init_times not between", value1, value2, "projectInitTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesIsNull() {
            addCriterion("project_surplus_times is null");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesIsNotNull() {
            addCriterion("project_surplus_times is not null");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesEqualTo(Integer value) {
            addCriterion("project_surplus_times =", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesNotEqualTo(Integer value) {
            addCriterion("project_surplus_times <>", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesGreaterThan(Integer value) {
            addCriterion("project_surplus_times >", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_surplus_times >=", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesLessThan(Integer value) {
            addCriterion("project_surplus_times <", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesLessThanOrEqualTo(Integer value) {
            addCriterion("project_surplus_times <=", value, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesIn(List<Integer> values) {
            addCriterion("project_surplus_times in", values, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesNotIn(List<Integer> values) {
            addCriterion("project_surplus_times not in", values, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesBetween(Integer value1, Integer value2) {
            addCriterion("project_surplus_times between", value1, value2, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("project_surplus_times not between", value1, value2, "projectSurplusTimes");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountIsNull() {
            addCriterion("project_init_amount is null");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountIsNotNull() {
            addCriterion("project_init_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountEqualTo(Long value) {
            addCriterion("project_init_amount =", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountNotEqualTo(Long value) {
            addCriterion("project_init_amount <>", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountGreaterThan(Long value) {
            addCriterion("project_init_amount >", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("project_init_amount >=", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountLessThan(Long value) {
            addCriterion("project_init_amount <", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountLessThanOrEqualTo(Long value) {
            addCriterion("project_init_amount <=", value, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountIn(List<Long> values) {
            addCriterion("project_init_amount in", values, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountNotIn(List<Long> values) {
            addCriterion("project_init_amount not in", values, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountBetween(Long value1, Long value2) {
            addCriterion("project_init_amount between", value1, value2, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectInitAmountNotBetween(Long value1, Long value2) {
            addCriterion("project_init_amount not between", value1, value2, "projectInitAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountIsNull() {
            addCriterion("project_surplus_amount is null");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountIsNotNull() {
            addCriterion("project_surplus_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountEqualTo(Long value) {
            addCriterion("project_surplus_amount =", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountNotEqualTo(Long value) {
            addCriterion("project_surplus_amount <>", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountGreaterThan(Long value) {
            addCriterion("project_surplus_amount >", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("project_surplus_amount >=", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountLessThan(Long value) {
            addCriterion("project_surplus_amount <", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountLessThanOrEqualTo(Long value) {
            addCriterion("project_surplus_amount <=", value, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountIn(List<Long> values) {
            addCriterion("project_surplus_amount in", values, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountNotIn(List<Long> values) {
            addCriterion("project_surplus_amount not in", values, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountBetween(Long value1, Long value2) {
            addCriterion("project_surplus_amount between", value1, value2, "projectSurplusAmount");
            return (Criteria) this;
        }

        public Criteria andProjectSurplusAmountNotBetween(Long value1, Long value2) {
            addCriterion("project_surplus_amount not between", value1, value2, "projectSurplusAmount");
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