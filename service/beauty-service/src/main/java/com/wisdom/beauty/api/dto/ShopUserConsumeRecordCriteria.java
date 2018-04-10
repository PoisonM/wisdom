package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopUserConsumeRecordCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    protected String groupBy;


    public ShopUserConsumeRecordCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getGroupBy() {
        return groupBy;
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

        public Criteria andShopProductNameIsNull() {
            addCriterion("shop_product_name is null");
            return (Criteria) this;
        }

        public Criteria andShopProductNameIsNotNull() {
            addCriterion("shop_product_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopProductNameEqualTo(String value) {
            addCriterion("shop_product_name =", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameNotEqualTo(String value) {
            addCriterion("shop_product_name <>", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameGreaterThan(String value) {
            addCriterion("shop_product_name >", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_product_name >=", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameLessThan(String value) {
            addCriterion("shop_product_name <", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameLessThanOrEqualTo(String value) {
            addCriterion("shop_product_name <=", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameLike(String value) {
            addCriterion("shop_product_name like", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameNotLike(String value) {
            addCriterion("shop_product_name not like", value, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameIn(List<String> values) {
            addCriterion("shop_product_name in", values, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameNotIn(List<String> values) {
            addCriterion("shop_product_name not in", values, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameBetween(String value1, String value2) {
            addCriterion("shop_product_name between", value1, value2, "shopProductName");
            return (Criteria) this;
        }

        public Criteria andShopProductNameNotBetween(String value1, String value2) {
            addCriterion("shop_product_name not between", value1, value2, "shopProductName");
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

        public Criteria andShopProjectGroupNameIsNull() {
            addCriterion("shop_project_group_name is null");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameIsNotNull() {
            addCriterion("shop_project_group_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameEqualTo(String value) {
            addCriterion("shop_project_group_name =", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameNotEqualTo(String value) {
            addCriterion("shop_project_group_name <>", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameGreaterThan(String value) {
            addCriterion("shop_project_group_name >", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_project_group_name >=", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameLessThan(String value) {
            addCriterion("shop_project_group_name <", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameLessThanOrEqualTo(String value) {
            addCriterion("shop_project_group_name <=", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameLike(String value) {
            addCriterion("shop_project_group_name like", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameNotLike(String value) {
            addCriterion("shop_project_group_name not like", value, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameIn(List<String> values) {
            addCriterion("shop_project_group_name in", values, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameNotIn(List<String> values) {
            addCriterion("shop_project_group_name not in", values, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameBetween(String value1, String value2) {
            addCriterion("shop_project_group_name between", value1, value2, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopProjectGroupNameNotBetween(String value1, String value2) {
            addCriterion("shop_project_group_name not between", value1, value2, "shopProjectGroupName");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdIsNull() {
            addCriterion("shop_coupon_id is null");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdIsNotNull() {
            addCriterion("shop_coupon_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdEqualTo(String value) {
            addCriterion("shop_coupon_id =", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdNotEqualTo(String value) {
            addCriterion("shop_coupon_id <>", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdGreaterThan(String value) {
            addCriterion("shop_coupon_id >", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_coupon_id >=", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdLessThan(String value) {
            addCriterion("shop_coupon_id <", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdLessThanOrEqualTo(String value) {
            addCriterion("shop_coupon_id <=", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdLike(String value) {
            addCriterion("shop_coupon_id like", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdNotLike(String value) {
            addCriterion("shop_coupon_id not like", value, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdIn(List<String> values) {
            addCriterion("shop_coupon_id in", values, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdNotIn(List<String> values) {
            addCriterion("shop_coupon_id not in", values, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdBetween(String value1, String value2) {
            addCriterion("shop_coupon_id between", value1, value2, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponIdNotBetween(String value1, String value2) {
            addCriterion("shop_coupon_id not between", value1, value2, "shopCouponId");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameIsNull() {
            addCriterion("shop_coupon_name is null");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameIsNotNull() {
            addCriterion("shop_coupon_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameEqualTo(String value) {
            addCriterion("shop_coupon_name =", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameNotEqualTo(String value) {
            addCriterion("shop_coupon_name <>", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameGreaterThan(String value) {
            addCriterion("shop_coupon_name >", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_coupon_name >=", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameLessThan(String value) {
            addCriterion("shop_coupon_name <", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameLessThanOrEqualTo(String value) {
            addCriterion("shop_coupon_name <=", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameLike(String value) {
            addCriterion("shop_coupon_name like", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameNotLike(String value) {
            addCriterion("shop_coupon_name not like", value, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameIn(List<String> values) {
            addCriterion("shop_coupon_name in", values, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameNotIn(List<String> values) {
            addCriterion("shop_coupon_name not in", values, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameBetween(String value1, String value2) {
            addCriterion("shop_coupon_name between", value1, value2, "shopCouponName");
            return (Criteria) this;
        }

        public Criteria andShopCouponNameNotBetween(String value1, String value2) {
            addCriterion("shop_coupon_name not between", value1, value2, "shopCouponName");
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

        public Criteria andConsumeNumberIsNull() {
            addCriterion("consume_number is null");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberIsNotNull() {
            addCriterion("consume_number is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberEqualTo(Integer value) {
            addCriterion("consume_number =", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberNotEqualTo(Integer value) {
            addCriterion("consume_number <>", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberGreaterThan(Integer value) {
            addCriterion("consume_number >", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("consume_number >=", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberLessThan(Integer value) {
            addCriterion("consume_number <", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberLessThanOrEqualTo(Integer value) {
            addCriterion("consume_number <=", value, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberIn(List<Integer> values) {
            addCriterion("consume_number in", values, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberNotIn(List<Integer> values) {
            addCriterion("consume_number not in", values, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberBetween(Integer value1, Integer value2) {
            addCriterion("consume_number between", value1, value2, "consumeNumber");
            return (Criteria) this;
        }

        public Criteria andConsumeNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("consume_number not between", value1, value2, "consumeNumber");
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

        public Criteria andSysShopClerkIdIsNull() {
            addCriterion("sys_shop_clerk_id is null");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdIsNotNull() {
            addCriterion("sys_shop_clerk_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdEqualTo(String value) {
            addCriterion("sys_shop_clerk_id =", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdNotEqualTo(String value) {
            addCriterion("sys_shop_clerk_id <>", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdGreaterThan(String value) {
            addCriterion("sys_shop_clerk_id >", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_clerk_id >=", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdLessThan(String value) {
            addCriterion("sys_shop_clerk_id <", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_clerk_id <=", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdLike(String value) {
            addCriterion("sys_shop_clerk_id like", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdNotLike(String value) {
            addCriterion("sys_shop_clerk_id not like", value, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdIn(List<String> values) {
            addCriterion("sys_shop_clerk_id in", values, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdNotIn(List<String> values) {
            addCriterion("sys_shop_clerk_id not in", values, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdBetween(String value1, String value2) {
            addCriterion("sys_shop_clerk_id between", value1, value2, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkIdNotBetween(String value1, String value2) {
            addCriterion("sys_shop_clerk_id not between", value1, value2, "sysShopClerkId");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameIsNull() {
            addCriterion("sys_shop_clerk_name is null");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameIsNotNull() {
            addCriterion("sys_shop_clerk_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameEqualTo(String value) {
            addCriterion("sys_shop_clerk_name =", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameNotEqualTo(String value) {
            addCriterion("sys_shop_clerk_name <>", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameGreaterThan(String value) {
            addCriterion("sys_shop_clerk_name >", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_shop_clerk_name >=", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameLessThan(String value) {
            addCriterion("sys_shop_clerk_name <", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameLessThanOrEqualTo(String value) {
            addCriterion("sys_shop_clerk_name <=", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameLike(String value) {
            addCriterion("sys_shop_clerk_name like", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameNotLike(String value) {
            addCriterion("sys_shop_clerk_name not like", value, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameIn(List<String> values) {
            addCriterion("sys_shop_clerk_name in", values, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameNotIn(List<String> values) {
            addCriterion("sys_shop_clerk_name not in", values, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameBetween(String value1, String value2) {
            addCriterion("sys_shop_clerk_name between", value1, value2, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andSysShopClerkNameNotBetween(String value1, String value2) {
            addCriterion("sys_shop_clerk_name not between", value1, value2, "sysShopClerkName");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Long value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Long value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Long value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Long value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Long value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Long value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Long> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Long> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Long value1, Long value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Long value1, Long value2) {
            addCriterion("price not between", value1, value2, "price");
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

        public Criteria andDiscountEqualTo(Long value) {
            addCriterion("discount =", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotEqualTo(Long value) {
            addCriterion("discount <>", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThan(Long value) {
            addCriterion("discount >", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountGreaterThanOrEqualTo(Long value) {
            addCriterion("discount >=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThan(Long value) {
            addCriterion("discount <", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountLessThanOrEqualTo(Long value) {
            addCriterion("discount <=", value, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountIn(List<Long> values) {
            addCriterion("discount in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotIn(List<Long> values) {
            addCriterion("discount not in", values, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountBetween(Long value1, Long value2) {
            addCriterion("discount between", value1, value2, "discount");
            return (Criteria) this;
        }

        public Criteria andDiscountNotBetween(Long value1, Long value2) {
            addCriterion("discount not between", value1, value2, "discount");
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

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("pay_type like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("pay_type not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
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

        public Criteria andShopUserNameIsNull() {
            addCriterion("shop_user_name is null");
            return (Criteria) this;
        }

        public Criteria andShopUserNameIsNotNull() {
            addCriterion("shop_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopUserNameEqualTo(String value) {
            addCriterion("shop_user_name =", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameNotEqualTo(String value) {
            addCriterion("shop_user_name <>", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameGreaterThan(String value) {
            addCriterion("shop_user_name >", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_user_name >=", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameLessThan(String value) {
            addCriterion("shop_user_name <", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameLessThanOrEqualTo(String value) {
            addCriterion("shop_user_name <=", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameLike(String value) {
            addCriterion("shop_user_name like", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameNotLike(String value) {
            addCriterion("shop_user_name not like", value, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameIn(List<String> values) {
            addCriterion("shop_user_name in", values, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameNotIn(List<String> values) {
            addCriterion("shop_user_name not in", values, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameBetween(String value1, String value2) {
            addCriterion("shop_user_name between", value1, value2, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserNameNotBetween(String value1, String value2) {
            addCriterion("shop_user_name not between", value1, value2, "shopUserName");
            return (Criteria) this;
        }

        public Criteria andShopUserIdIsNull() {
            addCriterion("shop_user_id is null");
            return (Criteria) this;
        }

        public Criteria andShopUserIdIsNotNull() {
            addCriterion("shop_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopUserIdEqualTo(String value) {
            addCriterion("shop_user_id =", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdNotEqualTo(String value) {
            addCriterion("shop_user_id <>", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdGreaterThan(String value) {
            addCriterion("shop_user_id >", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_user_id >=", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdLessThan(String value) {
            addCriterion("shop_user_id <", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdLessThanOrEqualTo(String value) {
            addCriterion("shop_user_id <=", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdLike(String value) {
            addCriterion("shop_user_id like", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdNotLike(String value) {
            addCriterion("shop_user_id not like", value, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdIn(List<String> values) {
            addCriterion("shop_user_id in", values, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdNotIn(List<String> values) {
            addCriterion("shop_user_id not in", values, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdBetween(String value1, String value2) {
            addCriterion("shop_user_id between", value1, value2, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andShopUserIdNotBetween(String value1, String value2) {
            addCriterion("shop_user_id not between", value1, value2, "shopUserId");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoIsNull() {
            addCriterion("consume_flow_no is null");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoIsNotNull() {
            addCriterion("consume_flow_no is not null");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoEqualTo(String value) {
            addCriterion("consume_flow_no =", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoNotEqualTo(String value) {
            addCriterion("consume_flow_no <>", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoGreaterThan(String value) {
            addCriterion("consume_flow_no >", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoGreaterThanOrEqualTo(String value) {
            addCriterion("consume_flow_no >=", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoLessThan(String value) {
            addCriterion("consume_flow_no <", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoLessThanOrEqualTo(String value) {
            addCriterion("consume_flow_no <=", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoLike(String value) {
            addCriterion("consume_flow_no like", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoNotLike(String value) {
            addCriterion("consume_flow_no not like", value, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoIn(List<String> values) {
            addCriterion("consume_flow_no in", values, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoNotIn(List<String> values) {
            addCriterion("consume_flow_no not in", values, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoBetween(String value1, String value2) {
            addCriterion("consume_flow_no between", value1, value2, "consumeFlowNo");
            return (Criteria) this;
        }

        public Criteria andConsumeFlowNoNotBetween(String value1, String value2) {
            addCriterion("consume_flow_no not between", value1, value2, "consumeFlowNo");
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