package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopProjectInfoGroupRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopProjectInfoGroupRelationCriteria() {
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

        public Criteria andProjectGroupNameIsNull() {
            addCriterion("project_group_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameIsNotNull() {
            addCriterion("project_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameEqualTo(String value) {
            addCriterion("project_group_name =", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameNotEqualTo(String value) {
            addCriterion("project_group_name <>", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameGreaterThan(String value) {
            addCriterion("project_group_name >", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_group_name >=", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameLessThan(String value) {
            addCriterion("project_group_name <", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameLessThanOrEqualTo(String value) {
            addCriterion("project_group_name <=", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameLike(String value) {
            addCriterion("project_group_name like", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameNotLike(String value) {
            addCriterion("project_group_name not like", value, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameIn(List<String> values) {
            addCriterion("project_group_name in", values, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameNotIn(List<String> values) {
            addCriterion("project_group_name not in", values, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameBetween(String value1, String value2) {
            addCriterion("project_group_name between", value1, value2, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andProjectGroupNameNotBetween(String value1, String value2) {
            addCriterion("project_group_name not between", value1, value2, "projectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameIsNull() {
            addCriterion("shop_project_info_name is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameIsNotNull() {
            addCriterion("shop_project_info_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameEqualTo(String value) {
            addCriterion("shop_project_info_name =", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameNotEqualTo(String value) {
            addCriterion("shop_project_info_name <>", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameGreaterThan(String value) {
            addCriterion("shop_project_info_name >", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_info_name >=", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameLessThan(String value) {
            addCriterion("shop_project_info_name <", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameLessThanOrEqualTo(String value) {
            addCriterion("shop_project_info_name <=", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameLike(String value) {
            addCriterion("shop_project_info_name like", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameNotLike(String value) {
            addCriterion("shop_project_info_name not like", value, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameIn(List<String> values) {
            addCriterion("shop_project_info_name in", values, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameNotIn(List<String> values) {
            addCriterion("shop_project_info_name not in", values, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameBetween(String value1, String value2) {
            addCriterion("shop_project_info_name between", value1, value2, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoNameNotBetween(String value1, String value2) {
            addCriterion("shop_project_info_name not between", value1, value2, "shopProjectInfoName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdIsNull() {
            addCriterion("shop_project_group_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdIsNotNull() {
            addCriterion("shop_project_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdEqualTo(String value) {
            addCriterion("shop_project_group_id =", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdNotEqualTo(String value) {
            addCriterion("shop_project_group_id <>", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdGreaterThan(String value) {
            addCriterion("shop_project_group_id >", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_group_id >=", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdLessThan(String value) {
            addCriterion("shop_project_group_id <", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdLessThanOrEqualTo(String value) {
            addCriterion("shop_project_group_id <=", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdLike(String value) {
            addCriterion("shop_project_group_id like", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdNotLike(String value) {
            addCriterion("shop_project_group_id not like", value, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdIn(List<String> values) {
            addCriterion("shop_project_group_id in", values, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdNotIn(List<String> values) {
            addCriterion("shop_project_group_id not in", values, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdBetween(String value1, String value2) {
            addCriterion("shop_project_group_id between", value1, value2, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupIdNotBetween(String value1, String value2) {
            addCriterion("shop_project_group_id not between", value1, value2, "shopProjectGroupId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdIsNull() {
            addCriterion("shop_project_info_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdIsNotNull() {
            addCriterion("shop_project_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdEqualTo(String value) {
            addCriterion("shop_project_info_id =", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdNotEqualTo(String value) {
            addCriterion("shop_project_info_id <>", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdGreaterThan(String value) {
            addCriterion("shop_project_info_id >", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_info_id >=", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdLessThan(String value) {
            addCriterion("shop_project_info_id <", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdLessThanOrEqualTo(String value) {
            addCriterion("shop_project_info_id <=", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdLike(String value) {
            addCriterion("shop_project_info_id like", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdNotLike(String value) {
            addCriterion("shop_project_info_id not like", value, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdIn(List<String> values) {
            addCriterion("shop_project_info_id in", values, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdNotIn(List<String> values) {
            addCriterion("shop_project_info_id not in", values, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdBetween(String value1, String value2) {
            addCriterion("shop_project_info_id between", value1, value2, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectInfoIdNotBetween(String value1, String value2) {
            addCriterion("shop_project_info_id not between", value1, value2, "shopProjectInfoId");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesIsNull() {
            addCriterion("shop_project_service_times is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesIsNotNull() {
            addCriterion("shop_project_service_times is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesEqualTo(Integer value) {
            addCriterion("shop_project_service_times =", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesNotEqualTo(Integer value) {
            addCriterion("shop_project_service_times <>", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesGreaterThan(Integer value) {
            addCriterion("shop_project_service_times >", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_project_service_times >=", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesLessThan(Integer value) {
            addCriterion("shop_project_service_times <", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesLessThanOrEqualTo(Integer value) {
            addCriterion("shop_project_service_times <=", value, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesIn(List<Integer> values) {
            addCriterion("shop_project_service_times in", values, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesNotIn(List<Integer> values) {
            addCriterion("shop_project_service_times not in", values, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesBetween(Integer value1, Integer value2) {
            addCriterion("shop_project_service_times between", value1, value2, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectServiceTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_project_service_times not between", value1, value2, "shopProjectServiceTimes");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceIsNull() {
            addCriterion("shop_project_price is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceIsNotNull() {
            addCriterion("shop_project_price is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceEqualTo(BigDecimal value) {
            addCriterion("shop_project_price =", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceNotEqualTo(BigDecimal value) {
            addCriterion("shop_project_price <>", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceGreaterThan(BigDecimal value) {
            addCriterion("shop_project_price >", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shop_project_price >=", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceLessThan(BigDecimal value) {
            addCriterion("shop_project_price <", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shop_project_price <=", value, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceIn(List<BigDecimal> values) {
            addCriterion("shop_project_price in", values, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceNotIn(List<BigDecimal> values) {
            addCriterion("shop_project_price not in", values, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shop_project_price between", value1, value2, "shopProjectPrice");
            return (Criteria) this;
        }

        public Criteria andShopProjectPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shop_project_price not between", value1, value2, "shopProjectPrice");
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