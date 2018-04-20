package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopProjectProductCardRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopProjectProductCardRelationCriteria() {
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

        public Criteria andShopProductIdIsNull() {
            addCriterion("shop_product_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProductIdIsNotNull() {
            addCriterion("shop_product_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProductIdEqualTo(String value) {
            addCriterion("shop_product_id =", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdNotEqualTo(String value) {
            addCriterion("shop_product_id <>", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdGreaterThan(String value) {
            addCriterion("shop_product_id >", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_product_id >=", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdLessThan(String value) {
            addCriterion("shop_product_id <", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdLessThanOrEqualTo(String value) {
            addCriterion("shop_product_id <=", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdLike(String value) {
            addCriterion("shop_product_id like", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdNotLike(String value) {
            addCriterion("shop_product_id not like", value, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdIn(List<String> values) {
            addCriterion("shop_product_id in", values, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdNotIn(List<String> values) {
            addCriterion("shop_product_id not in", values, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdBetween(String value1, String value2) {
            addCriterion("shop_product_id between", value1, value2, "shopProductId");
            return (Criteria) this;
        }

        public Criteria andShopProductIdNotBetween(String value1, String value2) {
            addCriterion("shop_product_id not between", value1, value2, "shopProductId");
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

        public Criteria andDiscountIsNull() {
            addCriterion("discount is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIsNotNull() {
            addCriterion("discount is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountEqualTo(Float value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Float value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Float value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Float value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Float value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Float value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Float> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Float> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Float value1, Float value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Float value1, Float value2) {
            addCriterion("discount not between", value1, value2, "discount");
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