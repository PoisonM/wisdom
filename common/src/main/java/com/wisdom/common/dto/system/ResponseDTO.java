package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseDTO<T> {

    @JSONField(name = "result")
    private String result;

    @JSONField(name = "errorInfo")
    private String errorInfo;

    @JSONField(name = "responseData")
    private T responseData;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}