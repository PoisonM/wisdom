package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopCheckRecordCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopCheckRecordCriteria() {
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

        public Criteria andFlowNoIsNull() {
            addCriterion("flow_no is null");
            return (Criteria) this;
        }

        public Criteria andFlowNoIsNotNull() {
            addCriterion("flow_no is not null");
            return (Criteria) this;
        }

        public Criteria andFlowNoEqualTo(String value) {
            addCriterion("flow_no =", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotEqualTo(String value) {
            addCriterion("flow_no <>", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThan(String value) {
            addCriterion("flow_no >", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoGreaterThanOrEqualTo(String value) {
            addCriterion("flow_no >=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThan(String value) {
            addCriterion("flow_no <", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLessThanOrEqualTo(String value) {
            addCriterion("flow_no <=", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoLike(String value) {
            addCriterion("flow_no like", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotLike(String value) {
            addCriterion("flow_no not like", value, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoIn(List<String> values) {
            addCriterion("flow_no in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotIn(List<String> values) {
            addCriterion("flow_no not in", values, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoBetween(String value1, String value2) {
            addCriterion("flow_no between", value1, value2, "flowNo");
            return (Criteria) this;
        }

        public Criteria andFlowNoNotBetween(String value1, String value2) {
            addCriterion("flow_no not between", value1, value2, "flowNo");
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

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
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

        public Criteria andShopClosePositionIdIsNull() {
            addCriterion("shop_close_position_id is null");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdIsNotNull() {
            addCriterion("shop_close_position_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdEqualTo(String value) {
            addCriterion("shop_close_position_id =", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdNotEqualTo(String value) {
            addCriterion("shop_close_position_id <>", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdGreaterThan(String value) {
            addCriterion("shop_close_position_id >", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_close_position_id >=", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdLessThan(String value) {
            addCriterion("shop_close_position_id <", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdLessThanOrEqualTo(String value) {
            addCriterion("shop_close_position_id <=", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdLike(String value) {
            addCriterion("shop_close_position_id like", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdNotLike(String value) {
            addCriterion("shop_close_position_id not like", value, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdIn(List<String> values) {
            addCriterion("shop_close_position_id in", values, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdNotIn(List<String> values) {
            addCriterion("shop_close_position_id not in", values, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdBetween(String value1, String value2) {
            addCriterion("shop_close_position_id between", value1, value2, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andShopClosePositionIdNotBetween(String value1, String value2) {
            addCriterion("shop_close_position_id not between", value1, value2, "shopClosePositionId");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberIsNull() {
            addCriterion("actual_stock_number is null");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberIsNotNull() {
            addCriterion("actual_stock_number is not null");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberEqualTo(Integer value) {
            addCriterion("actual_stock_number =", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberNotEqualTo(Integer value) {
            addCriterion("actual_stock_number <>", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberGreaterThan(Integer value) {
            addCriterion("actual_stock_number >", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_stock_number >=", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberLessThan(Integer value) {
            addCriterion("actual_stock_number <", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberLessThanOrEqualTo(Integer value) {
            addCriterion("actual_stock_number <=", value, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberIn(List<Integer> values) {
            addCriterion("actual_stock_number in", values, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberNotIn(List<Integer> values) {
            addCriterion("actual_stock_number not in", values, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberBetween(Integer value1, Integer value2) {
            addCriterion("actual_stock_number between", value1, value2, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andActualStockNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_stock_number not between", value1, value2, "actualStockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberIsNull() {
            addCriterion("stock_number is null");
            return (Criteria) this;
        }

        public Criteria andStockNumberIsNotNull() {
            addCriterion("stock_number is not null");
            return (Criteria) this;
        }

        public Criteria andStockNumberEqualTo(Integer value) {
            addCriterion("stock_number =", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberNotEqualTo(Integer value) {
            addCriterion("stock_number <>", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberGreaterThan(Integer value) {
            addCriterion("stock_number >", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("stock_number >=", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberLessThan(Integer value) {
            addCriterion("stock_number <", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberLessThanOrEqualTo(Integer value) {
            addCriterion("stock_number <=", value, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberIn(List<Integer> values) {
            addCriterion("stock_number in", values, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberNotIn(List<Integer> values) {
            addCriterion("stock_number not in", values, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberBetween(Integer value1, Integer value2) {
            addCriterion("stock_number between", value1, value2, "stockNumber");
            return (Criteria) this;
        }

        public Criteria andStockNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("stock_number not between", value1, value2, "stockNumber");
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

        public Criteria andProductTypeOneNameIsNull() {
            addCriterion("product_type_one_name is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameIsNotNull() {
            addCriterion("product_type_one_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameEqualTo(String value) {
            addCriterion("product_type_one_name =", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameNotEqualTo(String value) {
            addCriterion("product_type_one_name <>", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameGreaterThan(String value) {
            addCriterion("product_type_one_name >", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_type_one_name >=", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameLessThan(String value) {
            addCriterion("product_type_one_name <", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameLessThanOrEqualTo(String value) {
            addCriterion("product_type_one_name <=", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameLike(String value) {
            addCriterion("product_type_one_name like", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameNotLike(String value) {
            addCriterion("product_type_one_name not like", value, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameIn(List<String> values) {
            addCriterion("product_type_one_name in", values, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameNotIn(List<String> values) {
            addCriterion("product_type_one_name not in", values, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameBetween(String value1, String value2) {
            addCriterion("product_type_one_name between", value1, value2, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneNameNotBetween(String value1, String value2) {
            addCriterion("product_type_one_name not between", value1, value2, "productTypeOneName");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdIsNull() {
            addCriterion("product_type_one_Id is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdIsNotNull() {
            addCriterion("product_type_one_Id is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdEqualTo(String value) {
            addCriterion("product_type_one_Id =", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdNotEqualTo(String value) {
            addCriterion("product_type_one_Id <>", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdGreaterThan(String value) {
            addCriterion("product_type_one_Id >", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdGreaterThanOrEqualTo(String value) {
            addCriterion("product_type_one_Id >=", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdLessThan(String value) {
            addCriterion("product_type_one_Id <", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdLessThanOrEqualTo(String value) {
            addCriterion("product_type_one_Id <=", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdLike(String value) {
            addCriterion("product_type_one_Id like", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdNotLike(String value) {
            addCriterion("product_type_one_Id not like", value, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdIn(List<String> values) {
            addCriterion("product_type_one_Id in", values, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdNotIn(List<String> values) {
            addCriterion("product_type_one_Id not in", values, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdBetween(String value1, String value2) {
            addCriterion("product_type_one_Id between", value1, value2, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andProductTypeOneIdNotBetween(String value1, String value2) {
            addCriterion("product_type_one_Id not between", value1, value2, "productTypeOneId");
            return (Criteria) this;
        }

        public Criteria andManagerIdIsNull() {
            addCriterion("manager_id is null");
            return (Criteria) this;
        }

        public Criteria andManagerIdIsNotNull() {
            addCriterion("manager_id is not null");
            return (Criteria) this;
        }

        public Criteria andManagerIdEqualTo(String value) {
            addCriterion("manager_id =", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotEqualTo(String value) {
            addCriterion("manager_id <>", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThan(String value) {
            addCriterion("manager_id >", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdGreaterThanOrEqualTo(String value) {
            addCriterion("manager_id >=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThan(String value) {
            addCriterion("manager_id <", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLessThanOrEqualTo(String value) {
            addCriterion("manager_id <=", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdLike(String value) {
            addCriterion("manager_id like", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotLike(String value) {
            addCriterion("manager_id not like", value, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdIn(List<String> values) {
            addCriterion("manager_id in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotIn(List<String> values) {
            addCriterion("manager_id not in", values, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdBetween(String value1, String value2) {
            addCriterion("manager_id between", value1, value2, "managerId");
            return (Criteria) this;
        }

        public Criteria andManagerIdNotBetween(String value1, String value2) {
            addCriterion("manager_id not between", value1, value2, "managerId");
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