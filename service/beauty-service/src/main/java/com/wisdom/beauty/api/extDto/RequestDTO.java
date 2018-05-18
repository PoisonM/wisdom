package com.wisdom.beauty.api.extDto;

import com.wisdom.common.entity.BaseEntity;
import com.wisdom.common.util.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestDTO<T> extends BaseEntity implements Serializable {


    private List<T> requestList;

    public void andList(T t) {
        if (CommonUtils.objectIsEmpty(requestList)) {
            requestList = new ArrayList<>();
        }
        requestList.add(t);
    }

    public List<T> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<T> requestList) {
        this.requestList = requestList;
    }
}