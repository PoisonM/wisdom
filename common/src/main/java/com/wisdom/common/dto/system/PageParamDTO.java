package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

public class PageParamDTO<T> {

    @JSONField(name = "pageNo")
    private int pageNo; //当前第几页

    @JSONField(name = "pageSize")
    private int pageSize; //每页的条目数

    @JSONField(name = "pageStartNo")
    private int pageStartNo; //起始行

    @JSONField(name = "totalCount")
    private int totalCount;// 总记录数，设置为“-1”表示不查询总数

    //orderType为date表示按日期
    @JSONField(name = "orderType")
    private String orderType;

    @JSONField(name = "orderBy")
    private String orderBy;  //按参数排序，0位由高到底，1为由低到高

    @JSONField(name = "requestData")
    private T requestData;

    @JSONField(name = "responseData")
    private T responseData;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public T getRequestData() {
        return requestData;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageStartNo() {
        return (pageNo-1)*pageSize;
    }

    public void setPageStartNo(int pageStartNo) {
        this.pageStartNo = pageStartNo;
    }
}