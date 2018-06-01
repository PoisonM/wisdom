package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopProjectInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopProjectInfoCriteria() {
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

        public Criteria andParentShopIdIsNull() {
            addCriterion("parent_shop_id is null");
            return (Criteria) this;
        }

        public Criteria andParentShopIdIsNotNull() {
            addCriterion("parent_shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentShopIdEqualTo(String value) {
            addCriterion("parent_shop_id =", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdNotEqualTo(String value) {
            addCriterion("parent_shop_id <>", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdGreaterThan(String value) {
            addCriterion("parent_shop_id >", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdGreaterThanOrEqualTo(String value) {
            addCriterion("parent_shop_id >=", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdLessThan(String value) {
            addCriterion("parent_shop_id <", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdLessThanOrEqualTo(String value) {
            addCriterion("parent_shop_id <=", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdLike(String value) {
            addCriterion("parent_shop_id like", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdNotLike(String value) {
            addCriterion("parent_shop_id not like", value, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdIn(List<String> values) {
            addCriterion("parent_shop_id in", values, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdNotIn(List<String> values) {
            addCriterion("parent_shop_id not in", values, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdBetween(String value1, String value2) {
            addCriterion("parent_shop_id between", value1, value2, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andParentShopIdNotBetween(String value1, String value2) {
            addCriterion("parent_shop_id not between", value1, value2, "parentShopId");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameIsNull() {
            addCriterion("project_type_one_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameIsNotNull() {
            addCriterion("project_type_one_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameEqualTo(String value) {
            addCriterion("project_type_one_name =", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameNotEqualTo(String value) {
            addCriterion("project_type_one_name <>", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameGreaterThan(String value) {
            addCriterion("project_type_one_name >", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_one_name >=", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameLessThan(String value) {
            addCriterion("project_type_one_name <", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameLessThanOrEqualTo(String value) {
            addCriterion("project_type_one_name <=", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameLike(String value) {
            addCriterion("project_type_one_name like", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameNotLike(String value) {
            addCriterion("project_type_one_name not like", value, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameIn(List<String> values) {
            addCriterion("project_type_one_name in", values, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameNotIn(List<String> values) {
            addCriterion("project_type_one_name not in", values, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameBetween(String value1, String value2) {
            addCriterion("project_type_one_name between", value1, value2, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneNameNotBetween(String value1, String value2) {
            addCriterion("project_type_one_name not between", value1, value2, "projectTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameIsNull() {
            addCriterion("project_type_two_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameIsNotNull() {
            addCriterion("project_type_two_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameEqualTo(String value) {
            addCriterion("project_type_two_name =", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameNotEqualTo(String value) {
            addCriterion("project_type_two_name <>", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameGreaterThan(String value) {
            addCriterion("project_type_two_name >", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_two_name >=", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameLessThan(String value) {
            addCriterion("project_type_two_name <", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameLessThanOrEqualTo(String value) {
            addCriterion("project_type_two_name <=", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameLike(String value) {
            addCriterion("project_type_two_name like", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameNotLike(String value) {
            addCriterion("project_type_two_name not like", value, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameIn(List<String> values) {
            addCriterion("project_type_two_name in", values, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameNotIn(List<String> values) {
            addCriterion("project_type_two_name not in", values, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameBetween(String value1, String value2) {
            addCriterion("project_type_two_name between", value1, value2, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoNameNotBetween(String value1, String value2) {
            addCriterion("project_type_two_name not between", value1, value2, "projectTypeTwoName");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdIsNull() {
            addCriterion("project_type_one_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdIsNotNull() {
            addCriterion("project_type_one_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdEqualTo(String value) {
            addCriterion("project_type_one_id =", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdNotEqualTo(String value) {
            addCriterion("project_type_one_id <>", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdGreaterThan(String value) {
            addCriterion("project_type_one_id >", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_one_id >=", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdLessThan(String value) {
            addCriterion("project_type_one_id <", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdLessThanOrEqualTo(String value) {
            addCriterion("project_type_one_id <=", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdLike(String value) {
            addCriterion("project_type_one_id like", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdNotLike(String value) {
            addCriterion("project_type_one_id not like", value, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdIn(List<String> values) {
            addCriterion("project_type_one_id in", values, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdNotIn(List<String> values) {
            addCriterion("project_type_one_id not in", values, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdBetween(String value1, String value2) {
            addCriterion("project_type_one_id between", value1, value2, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeOneIdNotBetween(String value1, String value2) {
            addCriterion("project_type_one_id not between", value1, value2, "projectTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdIsNull() {
            addCriterion("project_type_two_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdIsNotNull() {
            addCriterion("project_type_two_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdEqualTo(String value) {
            addCriterion("project_type_two_id =", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdNotEqualTo(String value) {
            addCriterion("project_type_two_id <>", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdGreaterThan(String value) {
            addCriterion("project_type_two_id >", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_type_two_id >=", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdLessThan(String value) {
            addCriterion("project_type_two_id <", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdLessThanOrEqualTo(String value) {
            addCriterion("project_type_two_id <=", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdLike(String value) {
            addCriterion("project_type_two_id like", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdNotLike(String value) {
            addCriterion("project_type_two_id not like", value, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdIn(List<String> values) {
            addCriterion("project_type_two_id in", values, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdNotIn(List<String> values) {
            addCriterion("project_type_two_id not in", values, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdBetween(String value1, String value2) {
            addCriterion("project_type_two_id between", value1, value2, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProjectTypeTwoIdNotBetween(String value1, String value2) {
            addCriterion("project_type_two_id not between", value1, value2, "projectTypeTwoId");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNull() {
            addCriterion("product_type is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNotNull() {
            addCriterion("product_type is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeEqualTo(String value) {
            addCriterion("product_type =", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotEqualTo(String value) {
            addCriterion("product_type <>", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThan(String value) {
            addCriterion("product_type >", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThanOrEqualTo(String value) {
            addCriterion("product_type >=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThan(String value) {
            addCriterion("product_type <", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThanOrEqualTo(String value) {
            addCriterion("product_type <=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLike(String value) {
            addCriterion("product_type like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotLike(String value) {
            addCriterion("product_type not like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIn(List<String> values) {
            addCriterion("product_type in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotIn(List<String> values) {
            addCriterion("product_type not in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeBetween(String value1, String value2) {
            addCriterion("product_type between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotBetween(String value1, String value2) {
            addCriterion("product_type not between", value1, value2, "productType");
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

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("card_type like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("card_type not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthIsNull() {
            addCriterion("effective_number_month is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthIsNotNull() {
            addCriterion("effective_number_month is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthEqualTo(Integer value) {
            addCriterion("effective_number_month =", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthNotEqualTo(Integer value) {
            addCriterion("effective_number_month <>", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthGreaterThan(Integer value) {
            addCriterion("effective_number_month >", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("effective_number_month >=", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthLessThan(Integer value) {
            addCriterion("effective_number_month <", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthLessThanOrEqualTo(Integer value) {
            addCriterion("effective_number_month <=", value, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthIn(List<Integer> values) {
            addCriterion("effective_number_month in", values, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthNotIn(List<Integer> values) {
            addCriterion("effective_number_month not in", values, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthBetween(Integer value1, Integer value2) {
            addCriterion("effective_number_month between", value1, value2, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andEffectiveNumberMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("effective_number_month not between", value1, value2, "effectiveNumberMonth");
            return (Criteria) this;
        }

        public Criteria andProjectUrlIsNull() {
            addCriterion("project_url is null");
            return (Criteria) this;
        }

        public Criteria andProjectUrlIsNotNull() {
            addCriterion("project_url is not null");
            return (Criteria) this;
        }

        public Criteria andProjectUrlEqualTo(String value) {
            addCriterion("project_url =", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlNotEqualTo(String value) {
            addCriterion("project_url <>", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlGreaterThan(String value) {
            addCriterion("project_url >", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlGreaterThanOrEqualTo(String value) {
            addCriterion("project_url >=", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlLessThan(String value) {
            addCriterion("project_url <", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlLessThanOrEqualTo(String value) {
            addCriterion("project_url <=", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlLike(String value) {
            addCriterion("project_url like", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlNotLike(String value) {
            addCriterion("project_url not like", value, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlIn(List<String> values) {
            addCriterion("project_url in", values, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlNotIn(List<String> values) {
            addCriterion("project_url not in", values, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlBetween(String value1, String value2) {
            addCriterion("project_url between", value1, value2, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectUrlNotBetween(String value1, String value2) {
            addCriterion("project_url not between", value1, value2, "projectUrl");
            return (Criteria) this;
        }

        public Criteria andProjectDurationIsNull() {
            addCriterion("project_duration is null");
            return (Criteria) this;
        }

        public Criteria andProjectDurationIsNotNull() {
            addCriterion("project_duration is not null");
            return (Criteria) this;
        }

        public Criteria andProjectDurationEqualTo(Integer value) {
            addCriterion("project_duration =", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationNotEqualTo(Integer value) {
            addCriterion("project_duration <>", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationGreaterThan(Integer value) {
            addCriterion("project_duration >", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_duration >=", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationLessThan(Integer value) {
            addCriterion("project_duration <", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationLessThanOrEqualTo(Integer value) {
            addCriterion("project_duration <=", value, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationIn(List<Integer> values) {
            addCriterion("project_duration in", values, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationNotIn(List<Integer> values) {
            addCriterion("project_duration not in", values, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationBetween(Integer value1, Integer value2) {
            addCriterion("project_duration between", value1, value2, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andProjectDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("project_duration not between", value1, value2, "projectDuration");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIsNull() {
            addCriterion("market_price is null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIsNotNull() {
            addCriterion("market_price is not null");
            return (Criteria) this;
        }

        public Criteria andMarketPriceEqualTo(BigDecimal value) {
            addCriterion("market_price =", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotEqualTo(BigDecimal value) {
            addCriterion("market_price <>", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThan(BigDecimal value) {
            addCriterion("market_price >", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("market_price >=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThan(BigDecimal value) {
            addCriterion("market_price <", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("market_price <=", value, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceIn(List<BigDecimal> values) {
            addCriterion("market_price in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotIn(List<BigDecimal> values) {
            addCriterion("market_price not in", values, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_price between", value1, value2, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andMarketPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("market_price not between", value1, value2, "marketPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIsNull() {
            addCriterion("discount_price is null");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIsNotNull() {
            addCriterion("discount_price is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceEqualTo(BigDecimal value) {
            addCriterion("discount_price =", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotEqualTo(BigDecimal value) {
            addCriterion("discount_price <>", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceGreaterThan(BigDecimal value) {
            addCriterion("discount_price >", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_price >=", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceLessThan(BigDecimal value) {
            addCriterion("discount_price <", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("discount_price <=", value, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceIn(List<BigDecimal> values) {
            addCriterion("discount_price in", values, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotIn(List<BigDecimal> values) {
            addCriterion("discount_price not in", values, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_price between", value1, value2, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andDiscountPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("discount_price not between", value1, value2, "discountPrice");
            return (Criteria) this;
        }

        public Criteria andServiceTimesIsNull() {
            addCriterion("service_times is null");
            return (Criteria) this;
        }

        public Criteria andServiceTimesIsNotNull() {
            addCriterion("service_times is not null");
            return (Criteria) this;
        }

        public Criteria andServiceTimesEqualTo(Integer value) {
            addCriterion("service_times =", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesNotEqualTo(Integer value) {
            addCriterion("service_times <>", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesGreaterThan(Integer value) {
            addCriterion("service_times >", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_times >=", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesLessThan(Integer value) {
            addCriterion("service_times <", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesLessThanOrEqualTo(Integer value) {
            addCriterion("service_times <=", value, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesIn(List<Integer> values) {
            addCriterion("service_times in", values, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesNotIn(List<Integer> values) {
            addCriterion("service_times not in", values, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesBetween(Integer value1, Integer value2) {
            addCriterion("service_times between", value1, value2, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andServiceTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("service_times not between", value1, value2, "serviceTimes");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeIsNull() {
            addCriterion("visit_date_time is null");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeIsNotNull() {
            addCriterion("visit_date_time is not null");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeEqualTo(Integer value) {
            addCriterion("visit_date_time =", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeNotEqualTo(Integer value) {
            addCriterion("visit_date_time <>", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeGreaterThan(Integer value) {
            addCriterion("visit_date_time >", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("visit_date_time >=", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeLessThan(Integer value) {
            addCriterion("visit_date_time <", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeLessThanOrEqualTo(Integer value) {
            addCriterion("visit_date_time <=", value, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeIn(List<Integer> values) {
            addCriterion("visit_date_time in", values, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeNotIn(List<Integer> values) {
            addCriterion("visit_date_time not in", values, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeBetween(Integer value1, Integer value2) {
            addCriterion("visit_date_time between", value1, value2, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andVisitDateTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("visit_date_time not between", value1, value2, "visitDateTime");
            return (Criteria) this;
        }

        public Criteria andOncePriceIsNull() {
            addCriterion("once_price is null");
            return (Criteria) this;
        }

        public Criteria andOncePriceIsNotNull() {
            addCriterion("once_price is not null");
            return (Criteria) this;
        }

        public Criteria andOncePriceEqualTo(BigDecimal value) {
            addCriterion("once_price =", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceNotEqualTo(BigDecimal value) {
            addCriterion("once_price <>", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceGreaterThan(BigDecimal value) {
            addCriterion("once_price >", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("once_price >=", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceLessThan(BigDecimal value) {
            addCriterion("once_price <", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("once_price <=", value, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceIn(List<BigDecimal> values) {
            addCriterion("once_price in", values, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceNotIn(List<BigDecimal> values) {
            addCriterion("once_price not in", values, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("once_price between", value1, value2, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andOncePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("once_price not between", value1, value2, "oncePrice");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrIsNull() {
            addCriterion("function_intr is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrIsNotNull() {
            addCriterion("function_intr is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrEqualTo(String value) {
            addCriterion("function_intr =", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrNotEqualTo(String value) {
            addCriterion("function_intr <>", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrGreaterThan(String value) {
            addCriterion("function_intr >", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrGreaterThanOrEqualTo(String value) {
            addCriterion("function_intr >=", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrLessThan(String value) {
            addCriterion("function_intr <", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrLessThanOrEqualTo(String value) {
            addCriterion("function_intr <=", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrLike(String value) {
            addCriterion("function_intr like", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrNotLike(String value) {
            addCriterion("function_intr not like", value, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrIn(List<String> values) {
            addCriterion("function_intr in", values, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrNotIn(List<String> values) {
            addCriterion("function_intr not in", values, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrBetween(String value1, String value2) {
            addCriterion("function_intr between", value1, value2, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andFunctionIntrNotBetween(String value1, String value2) {
            addCriterion("function_intr not between", value1, value2, "functionIntr");
            return (Criteria) this;
        }

        public Criteria andIsDisplayIsNull() {
            addCriterion("is_display is null");
            return (Criteria) this;
        }

        public Criteria andIsDisplayIsNotNull() {
            addCriterion("is_display is not null");
            return (Criteria) this;
        }

        public Criteria andIsDisplayEqualTo(String value) {
            addCriterion("is_display =", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotEqualTo(String value) {
            addCriterion("is_display <>", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayGreaterThan(String value) {
            addCriterion("is_display >", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayGreaterThanOrEqualTo(String value) {
            addCriterion("is_display >=", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayLessThan(String value) {
            addCriterion("is_display <", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayLessThanOrEqualTo(String value) {
            addCriterion("is_display <=", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayLike(String value) {
            addCriterion("is_display like", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotLike(String value) {
            addCriterion("is_display not like", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayIn(List<String> values) {
            addCriterion("is_display in", values, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotIn(List<String> values) {
            addCriterion("is_display not in", values, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayBetween(String value1, String value2) {
            addCriterion("is_display between", value1, value2, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotBetween(String value1, String value2) {
            addCriterion("is_display not between", value1, value2, "isDisplay");
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