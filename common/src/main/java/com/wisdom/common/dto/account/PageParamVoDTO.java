package com.wisdom.common.dto.account;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 后台管理传参封装DTO
 * @param <T>
 */
public class PageParamVoDTO<T> {
    @JSONField(name = "pageNo")
    private int pageNo; //当前第几页

    @JSONField(name = "pageSize")
    private int pageSize; //每页的条目数

    @JSONField(name = "pageStartNo")
    private int pageStartNo; //起始行

    @JSONField(name = "totalCount")
    private int totalCount;// 总记录数，设置为“-1”表示不查询总数

    @JSONField(name = "startTime")
    private String startTime; //

    @JSONField(name = "endTime")
    private String endTime; //

    @JSONField(name = "timeType")
    private String timeType; //时间类型 按时间查询的时候具体按哪种时间

    @JSONField(name = "param")
    private String param; //搜索所需参数

    @JSONField(name = "requestData")
    private T requestData;

    @JSONField(name = "responseData")
    private T responseData;

    //是否导出Excel   Y: 是  N: 否
    @JSONField(name = "isExportExcel")
    private String isExportExcel;

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

    public int getPageStartNo() {
        return (this.pageNo-1)*this.pageSize;
    }

    public void setPageStartNo(int pageStartNo) {
        this.pageStartNo = pageStartNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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

    public String getIsExportExcel() {
        return isExportExcel;
    }

    public void setIsExportExcel(String isExportExcel) {
        this.isExportExcel = isExportExcel;
    }
}
