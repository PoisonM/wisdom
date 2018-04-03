package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopStockBossRelationCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopStockBossRelationCriteria() {
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

        public Criteria andShopBossIdIsNull() {
            addCriterion("shop_boss_id is null");
            return (Criteria) this;
        }

        public Criteria andShopBossIdIsNotNull() {
            addCriterion("shop_boss_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopBossIdEqualTo(String value) {
            addCriterion("shop_boss_id =", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdNotEqualTo(String value) {
            addCriterion("shop_boss_id <>", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdGreaterThan(String value) {
            addCriterion("shop_boss_id >", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_boss_id >=", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdLessThan(String value) {
            addCriterion("shop_boss_id <", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdLessThanOrEqualTo(String value) {
            addCriterion("shop_boss_id <=", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdLike(String value) {
            addCriterion("shop_boss_id like", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdNotLike(String value) {
            addCriterion("shop_boss_id not like", value, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdIn(List<String> values) {
            addCriterion("shop_boss_id in", values, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdNotIn(List<String> values) {
            addCriterion("shop_boss_id not in", values, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdBetween(String value1, String value2) {
            addCriterion("shop_boss_id between", value1, value2, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopBossIdNotBetween(String value1, String value2) {
            addCriterion("shop_boss_id not between", value1, value2, "shopBossId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdIsNull() {
            addCriterion("shop_store_id is null");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdIsNotNull() {
            addCriterion("shop_store_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdEqualTo(String value) {
            addCriterion("shop_store_id =", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdNotEqualTo(String value) {
            addCriterion("shop_store_id <>", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdGreaterThan(String value) {
            addCriterion("shop_store_id >", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_store_id >=", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdLessThan(String value) {
            addCriterion("shop_store_id <", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdLessThanOrEqualTo(String value) {
            addCriterion("shop_store_id <=", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdLike(String value) {
            addCriterion("shop_store_id like", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdNotLike(String value) {
            addCriterion("shop_store_id not like", value, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdIn(List<String> values) {
            addCriterion("shop_store_id in", values, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdNotIn(List<String> values) {
            addCriterion("shop_store_id not in", values, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdBetween(String value1, String value2) {
            addCriterion("shop_store_id between", value1, value2, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStoreIdNotBetween(String value1, String value2) {
            addCriterion("shop_store_id not between", value1, value2, "shopStoreId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdIsNull() {
            addCriterion("shop_stock_id is null");
            return (Criteria) this;
        }

        public Criteria andShopStockIdIsNotNull() {
            addCriterion("shop_stock_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopStockIdEqualTo(String value) {
            addCriterion("shop_stock_id =", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdNotEqualTo(String value) {
            addCriterion("shop_stock_id <>", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdGreaterThan(String value) {
            addCriterion("shop_stock_id >", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_stock_id >=", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdLessThan(String value) {
            addCriterion("shop_stock_id <", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdLessThanOrEqualTo(String value) {
            addCriterion("shop_stock_id <=", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdLike(String value) {
            addCriterion("shop_stock_id like", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdNotLike(String value) {
            addCriterion("shop_stock_id not like", value, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdIn(List<String> values) {
            addCriterion("shop_stock_id in", values, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdNotIn(List<String> values) {
            addCriterion("shop_stock_id not in", values, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdBetween(String value1, String value2) {
            addCriterion("shop_stock_id between", value1, value2, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopStockIdNotBetween(String value1, String value2) {
            addCriterion("shop_stock_id not between", value1, value2, "shopStockId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdIsNull() {
            addCriterion("shop_proc_id is null");
            return (Criteria) this;
        }

        public Criteria andShopProcIdIsNotNull() {
            addCriterion("shop_proc_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopProcIdEqualTo(String value) {
            addCriterion("shop_proc_id =", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdNotEqualTo(String value) {
            addCriterion("shop_proc_id <>", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdGreaterThan(String value) {
            addCriterion("shop_proc_id >", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_proc_id >=", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdLessThan(String value) {
            addCriterion("shop_proc_id <", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdLessThanOrEqualTo(String value) {
            addCriterion("shop_proc_id <=", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdLike(String value) {
            addCriterion("shop_proc_id like", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdNotLike(String value) {
            addCriterion("shop_proc_id not like", value, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdIn(List<String> values) {
            addCriterion("shop_proc_id in", values, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdNotIn(List<String> values) {
            addCriterion("shop_proc_id not in", values, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdBetween(String value1, String value2) {
            addCriterion("shop_proc_id between", value1, value2, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopProcIdNotBetween(String value1, String value2) {
            addCriterion("shop_proc_id not between", value1, value2, "shopProcId");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameIsNull() {
            addCriterion("shop_store_name is null");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameIsNotNull() {
            addCriterion("shop_store_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameEqualTo(String value) {
            addCriterion("shop_store_name =", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameNotEqualTo(String value) {
            addCriterion("shop_store_name <>", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameGreaterThan(String value) {
            addCriterion("shop_store_name >", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_store_name >=", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameLessThan(String value) {
            addCriterion("shop_store_name <", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameLessThanOrEqualTo(String value) {
            addCriterion("shop_store_name <=", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameLike(String value) {
            addCriterion("shop_store_name like", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameNotLike(String value) {
            addCriterion("shop_store_name not like", value, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameIn(List<String> values) {
            addCriterion("shop_store_name in", values, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameNotIn(List<String> values) {
            addCriterion("shop_store_name not in", values, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameBetween(String value1, String value2) {
            addCriterion("shop_store_name between", value1, value2, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopStoreNameNotBetween(String value1, String value2) {
            addCriterion("shop_store_name not between", value1, value2, "shopStoreName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameIsNull() {
            addCriterion("shop_boss_name is null");
            return (Criteria) this;
        }

        public Criteria andShopBossNameIsNotNull() {
            addCriterion("shop_boss_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopBossNameEqualTo(String value) {
            addCriterion("shop_boss_name =", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameNotEqualTo(String value) {
            addCriterion("shop_boss_name <>", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameGreaterThan(String value) {
            addCriterion("shop_boss_name >", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_boss_name >=", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameLessThan(String value) {
            addCriterion("shop_boss_name <", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameLessThanOrEqualTo(String value) {
            addCriterion("shop_boss_name <=", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameLike(String value) {
            addCriterion("shop_boss_name like", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameNotLike(String value) {
            addCriterion("shop_boss_name not like", value, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameIn(List<String> values) {
            addCriterion("shop_boss_name in", values, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameNotIn(List<String> values) {
            addCriterion("shop_boss_name not in", values, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameBetween(String value1, String value2) {
            addCriterion("shop_boss_name between", value1, value2, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopBossNameNotBetween(String value1, String value2) {
            addCriterion("shop_boss_name not between", value1, value2, "shopBossName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameIsNull() {
            addCriterion("shop_proc_name is null");
            return (Criteria) this;
        }

        public Criteria andShopProcNameIsNotNull() {
            addCriterion("shop_proc_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopProcNameEqualTo(String value) {
            addCriterion("shop_proc_name =", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameNotEqualTo(String value) {
            addCriterion("shop_proc_name <>", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameGreaterThan(String value) {
            addCriterion("shop_proc_name >", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_proc_name >=", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameLessThan(String value) {
            addCriterion("shop_proc_name <", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameLessThanOrEqualTo(String value) {
            addCriterion("shop_proc_name <=", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameLike(String value) {
            addCriterion("shop_proc_name like", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameNotLike(String value) {
            addCriterion("shop_proc_name not like", value, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameIn(List<String> values) {
            addCriterion("shop_proc_name in", values, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameNotIn(List<String> values) {
            addCriterion("shop_proc_name not in", values, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameBetween(String value1, String value2) {
            addCriterion("shop_proc_name between", value1, value2, "shopProcName");
            return (Criteria) this;
        }

        public Criteria andShopProcNameNotBetween(String value1, String value2) {
            addCriterion("shop_proc_name not between", value1, value2, "shopProcName");
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