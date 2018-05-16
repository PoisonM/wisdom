package com.wisdom.beauty.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysShopCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int pageSize = -1;

    public SysShopCriteria() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlIsNull() {
            addCriterion("shop_image_url is null");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlIsNotNull() {
            addCriterion("shop_image_url is not null");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlEqualTo(String value) {
            addCriterion("shop_image_url =", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlNotEqualTo(String value) {
            addCriterion("shop_image_url <>", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlGreaterThan(String value) {
            addCriterion("shop_image_url >", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("shop_image_url >=", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlLessThan(String value) {
            addCriterion("shop_image_url <", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlLessThanOrEqualTo(String value) {
            addCriterion("shop_image_url <=", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlLike(String value) {
            addCriterion("shop_image_url like", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlNotLike(String value) {
            addCriterion("shop_image_url not like", value, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlIn(List<String> values) {
            addCriterion("shop_image_url in", values, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlNotIn(List<String> values) {
            addCriterion("shop_image_url not in", values, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlBetween(String value1, String value2) {
            addCriterion("shop_image_url between", value1, value2, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andShopImageUrlNotBetween(String value1, String value2) {
            addCriterion("shop_image_url not between", value1, value2, "shopImageUrl");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlIsNull() {
            addCriterion("business_license_url is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlIsNotNull() {
            addCriterion("business_license_url is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlEqualTo(String value) {
            addCriterion("business_license_url =", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlNotEqualTo(String value) {
            addCriterion("business_license_url <>", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlGreaterThan(String value) {
            addCriterion("business_license_url >", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlGreaterThanOrEqualTo(String value) {
            addCriterion("business_license_url >=", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlLessThan(String value) {
            addCriterion("business_license_url <", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlLessThanOrEqualTo(String value) {
            addCriterion("business_license_url <=", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlLike(String value) {
            addCriterion("business_license_url like", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlNotLike(String value) {
            addCriterion("business_license_url not like", value, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlIn(List<String> values) {
            addCriterion("business_license_url in", values, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlNotIn(List<String> values) {
            addCriterion("business_license_url not in", values, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlBetween(String value1, String value2) {
            addCriterion("business_license_url between", value1, value2, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseUrlNotBetween(String value1, String value2) {
            addCriterion("business_license_url not between", value1, value2, "businessLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlIsNull() {
            addCriterion("id_card_front_url is null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlIsNotNull() {
            addCriterion("id_card_front_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlEqualTo(String value) {
            addCriterion("id_card_front_url =", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlNotEqualTo(String value) {
            addCriterion("id_card_front_url <>", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlGreaterThan(String value) {
            addCriterion("id_card_front_url >", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_front_url >=", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlLessThan(String value) {
            addCriterion("id_card_front_url <", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_front_url <=", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlLike(String value) {
            addCriterion("id_card_front_url like", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlNotLike(String value) {
            addCriterion("id_card_front_url not like", value, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlIn(List<String> values) {
            addCriterion("id_card_front_url in", values, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlNotIn(List<String> values) {
            addCriterion("id_card_front_url not in", values, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlBetween(String value1, String value2) {
            addCriterion("id_card_front_url between", value1, value2, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardFrontUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_front_url not between", value1, value2, "idCardFrontUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIsNull() {
            addCriterion("id_card_back_url is null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIsNotNull() {
            addCriterion("id_card_back_url is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlEqualTo(String value) {
            addCriterion("id_card_back_url =", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotEqualTo(String value) {
            addCriterion("id_card_back_url <>", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlGreaterThan(String value) {
            addCriterion("id_card_back_url >", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_back_url >=", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLessThan(String value) {
            addCriterion("id_card_back_url <", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_back_url <=", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlLike(String value) {
            addCriterion("id_card_back_url like", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotLike(String value) {
            addCriterion("id_card_back_url not like", value, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlIn(List<String> values) {
            addCriterion("id_card_back_url in", values, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotIn(List<String> values) {
            addCriterion("id_card_back_url not in", values, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlBetween(String value1, String value2) {
            addCriterion("id_card_back_url between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andIdCardBackUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_back_url not between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlIsNull() {
            addCriterion("open_account_license_url is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlIsNotNull() {
            addCriterion("open_account_license_url is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlEqualTo(String value) {
            addCriterion("open_account_license_url =", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlNotEqualTo(String value) {
            addCriterion("open_account_license_url <>", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlGreaterThan(String value) {
            addCriterion("open_account_license_url >", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlGreaterThanOrEqualTo(String value) {
            addCriterion("open_account_license_url >=", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlLessThan(String value) {
            addCriterion("open_account_license_url <", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlLessThanOrEqualTo(String value) {
            addCriterion("open_account_license_url <=", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlLike(String value) {
            addCriterion("open_account_license_url like", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlNotLike(String value) {
            addCriterion("open_account_license_url not like", value, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlIn(List<String> values) {
            addCriterion("open_account_license_url in", values, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlNotIn(List<String> values) {
            addCriterion("open_account_license_url not in", values, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlBetween(String value1, String value2) {
            addCriterion("open_account_license_url between", value1, value2, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOpenAccountLicenseUrlNotBetween(String value1, String value2) {
            addCriterion("open_account_license_url not between", value1, value2, "openAccountLicenseUrl");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusIsNull() {
            addCriterion("on_service_status is null");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusIsNotNull() {
            addCriterion("on_service_status is not null");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusEqualTo(String value) {
            addCriterion("on_service_status =", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusNotEqualTo(String value) {
            addCriterion("on_service_status <>", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusGreaterThan(String value) {
            addCriterion("on_service_status >", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusGreaterThanOrEqualTo(String value) {
            addCriterion("on_service_status >=", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusLessThan(String value) {
            addCriterion("on_service_status <", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusLessThanOrEqualTo(String value) {
            addCriterion("on_service_status <=", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusLike(String value) {
            addCriterion("on_service_status like", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusNotLike(String value) {
            addCriterion("on_service_status not like", value, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusIn(List<String> values) {
            addCriterion("on_service_status in", values, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusNotIn(List<String> values) {
            addCriterion("on_service_status not in", values, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusBetween(String value1, String value2) {
            addCriterion("on_service_status between", value1, value2, "onServiceStatus");
            return (Criteria) this;
        }

        public Criteria andOnServiceStatusNotBetween(String value1, String value2) {
            addCriterion("on_service_status not between", value1, value2, "onServiceStatus");
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

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Float value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Float value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Float value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Float value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Float value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Float value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Float> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Float> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Float value1, Float value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Float value1, Float value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateIsNull() {
            addCriterion("open_door_date is null");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateIsNotNull() {
            addCriterion("open_door_date is not null");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateEqualTo(String value) {
            addCriterion("open_door_date =", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateNotEqualTo(String value) {
            addCriterion("open_door_date <>", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateGreaterThan(String value) {
            addCriterion("open_door_date >", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateGreaterThanOrEqualTo(String value) {
            addCriterion("open_door_date >=", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateLessThan(String value) {
            addCriterion("open_door_date <", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateLessThanOrEqualTo(String value) {
            addCriterion("open_door_date <=", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateLike(String value) {
            addCriterion("open_door_date like", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateNotLike(String value) {
            addCriterion("open_door_date not like", value, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateIn(List<String> values) {
            addCriterion("open_door_date in", values, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateNotIn(List<String> values) {
            addCriterion("open_door_date not in", values, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateBetween(String value1, String value2) {
            addCriterion("open_door_date between", value1, value2, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andOpenDoorDateNotBetween(String value1, String value2) {
            addCriterion("open_door_date not between", value1, value2, "openDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateIsNull() {
            addCriterion("close_door_date is null");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateIsNotNull() {
            addCriterion("close_door_date is not null");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateEqualTo(String value) {
            addCriterion("close_door_date =", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateNotEqualTo(String value) {
            addCriterion("close_door_date <>", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateGreaterThan(String value) {
            addCriterion("close_door_date >", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateGreaterThanOrEqualTo(String value) {
            addCriterion("close_door_date >=", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateLessThan(String value) {
            addCriterion("close_door_date <", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateLessThanOrEqualTo(String value) {
            addCriterion("close_door_date <=", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateLike(String value) {
            addCriterion("close_door_date like", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateNotLike(String value) {
            addCriterion("close_door_date not like", value, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateIn(List<String> values) {
            addCriterion("close_door_date in", values, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateNotIn(List<String> values) {
            addCriterion("close_door_date not in", values, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateBetween(String value1, String value2) {
            addCriterion("close_door_date between", value1, value2, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andCloseDoorDateNotBetween(String value1, String value2) {
            addCriterion("close_door_date not between", value1, value2, "closeDoorDate");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(String value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(String value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(String value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(String value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(String value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLike(String value) {
            addCriterion("shop_id like", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotLike(String value) {
            addCriterion("shop_id not like", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<String> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<String> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(String value1, String value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(String value1, String value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlIsNull() {
            addCriterion("qr_code_url is null");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlIsNotNull() {
            addCriterion("qr_code_url is not null");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlEqualTo(String value) {
            addCriterion("qr_code_url =", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlNotEqualTo(String value) {
            addCriterion("qr_code_url <>", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlGreaterThan(String value) {
            addCriterion("qr_code_url >", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qr_code_url >=", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlLessThan(String value) {
            addCriterion("qr_code_url <", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlLessThanOrEqualTo(String value) {
            addCriterion("qr_code_url <=", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlLike(String value) {
            addCriterion("qr_code_url like", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlNotLike(String value) {
            addCriterion("qr_code_url not like", value, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlIn(List<String> values) {
            addCriterion("qr_code_url in", values, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlNotIn(List<String> values) {
            addCriterion("qr_code_url not in", values, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlBetween(String value1, String value2) {
            addCriterion("qr_code_url between", value1, value2, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andQrCodeUrlNotBetween(String value1, String value2) {
            addCriterion("qr_code_url not between", value1, value2, "qrCodeUrl");
            return (Criteria) this;
        }

        public Criteria andParentsIdIsNull() {
            addCriterion("parents_id is null");
            return (Criteria) this;
        }

        public Criteria andParentsIdIsNotNull() {
            addCriterion("parents_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentsIdEqualTo(String value) {
            addCriterion("parents_id =", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdNotEqualTo(String value) {
            addCriterion("parents_id <>", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdGreaterThan(String value) {
            addCriterion("parents_id >", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdGreaterThanOrEqualTo(String value) {
            addCriterion("parents_id >=", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdLessThan(String value) {
            addCriterion("parents_id <", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdLessThanOrEqualTo(String value) {
            addCriterion("parents_id <=", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdLike(String value) {
            addCriterion("parents_id like", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdNotLike(String value) {
            addCriterion("parents_id not like", value, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdIn(List<String> values) {
            addCriterion("parents_id in", values, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdNotIn(List<String> values) {
            addCriterion("parents_id not in", values, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdBetween(String value1, String value2) {
            addCriterion("parents_id between", value1, value2, "parentsId");
            return (Criteria) this;
        }

        public Criteria andParentsIdNotBetween(String value1, String value2) {
            addCriterion("parents_id not between", value1, value2, "parentsId");
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