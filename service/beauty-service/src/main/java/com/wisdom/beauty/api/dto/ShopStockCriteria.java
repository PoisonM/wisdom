package com.wisdom.beauty.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopStockCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public ShopStockCriteria() {
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

        public Criteria andShopStockRecordIdIsNull() {
            addCriterion("shop_stock_record_id is null");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdIsNotNull() {
            addCriterion("shop_stock_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdEqualTo(String value) {
            addCriterion("shop_stock_record_id =", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdNotEqualTo(String value) {
            addCriterion("shop_stock_record_id <>", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdGreaterThan(String value) {
            addCriterion("shop_stock_record_id >", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_stock_record_id >=", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdLessThan(String value) {
            addCriterion("shop_stock_record_id <", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdLessThanOrEqualTo(String value) {
            addCriterion("shop_stock_record_id <=", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdLike(String value) {
            addCriterion("shop_stock_record_id like", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdNotLike(String value) {
            addCriterion("shop_stock_record_id not like", value, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdIn(List<String> values) {
            addCriterion("shop_stock_record_id in", values, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdNotIn(List<String> values) {
            addCriterion("shop_stock_record_id not in", values, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdBetween(String value1, String value2) {
            addCriterion("shop_stock_record_id between", value1, value2, "shopStockRecordId");
            return (Criteria) this;
        }

        public Criteria andShopStockRecordIdNotBetween(String value1, String value2) {
            addCriterion("shop_stock_record_id not between", value1, value2, "shopStockRecordId");
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

        public Criteria andOutStockNumberIsNull() {
            addCriterion("out_stock_number is null");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberIsNotNull() {
            addCriterion("out_stock_number is not null");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberEqualTo(Integer value) {
            addCriterion("out_stock_number =", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberNotEqualTo(Integer value) {
            addCriterion("out_stock_number <>", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberGreaterThan(Integer value) {
            addCriterion("out_stock_number >", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_stock_number >=", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberLessThan(Integer value) {
            addCriterion("out_stock_number <", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberLessThanOrEqualTo(Integer value) {
            addCriterion("out_stock_number <=", value, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberIn(List<Integer> values) {
            addCriterion("out_stock_number in", values, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberNotIn(List<Integer> values) {
            addCriterion("out_stock_number not in", values, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberBetween(Integer value1, Integer value2) {
            addCriterion("out_stock_number between", value1, value2, "outStockNumber");
            return (Criteria) this;
        }

        public Criteria andOutStockNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("out_stock_number not between", value1, value2, "outStockNumber");
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

        public Criteria andProductDateIsNull() {
            addCriterion("product_date is null");
            return (Criteria) this;
        }

        public Criteria andProductDateIsNotNull() {
            addCriterion("product_date is not null");
            return (Criteria) this;
        }

        public Criteria andProductDateEqualTo(Date value) {
            addCriterion("product_date =", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateNotEqualTo(Date value) {
            addCriterion("product_date <>", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateGreaterThan(Date value) {
            addCriterion("product_date >", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateGreaterThanOrEqualTo(Date value) {
            addCriterion("product_date >=", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateLessThan(Date value) {
            addCriterion("product_date <", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateLessThanOrEqualTo(Date value) {
            addCriterion("product_date <=", value, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateIn(List<Date> values) {
            addCriterion("product_date in", values, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateNotIn(List<Date> values) {
            addCriterion("product_date not in", values, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateBetween(Date value1, Date value2) {
            addCriterion("product_date between", value1, value2, "productDate");
            return (Criteria) this;
        }

        public Criteria andProductDateNotBetween(Date value1, Date value2) {
            addCriterion("product_date not between", value1, value2, "productDate");
            return (Criteria) this;
        }

        public Criteria andStockPriceIsNull() {
            addCriterion("stock_price is null");
            return (Criteria) this;
        }

        public Criteria andStockPriceIsNotNull() {
            addCriterion("stock_price is not null");
            return (Criteria) this;
        }

        public Criteria andStockPriceEqualTo(BigDecimal value) {
            addCriterion("stock_price =", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceNotEqualTo(BigDecimal value) {
            addCriterion("stock_price <>", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceGreaterThan(BigDecimal value) {
            addCriterion("stock_price >", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("stock_price >=", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceLessThan(BigDecimal value) {
            addCriterion("stock_price <", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("stock_price <=", value, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceIn(List<BigDecimal> values) {
            addCriterion("stock_price in", values, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceNotIn(List<BigDecimal> values) {
            addCriterion("stock_price not in", values, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stock_price between", value1, value2, "stockPrice");
            return (Criteria) this;
        }

        public Criteria andStockPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stock_price not between", value1, value2, "stockPrice");
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