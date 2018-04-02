package com.wisdom.common.dto.transaction;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class NeedPayOrderListDTO {

    @JSONField(name = "needPayOrderList")
    private List<NeedPayOrderDTO> needPayOrderList;

    public List<NeedPayOrderDTO> getNeedPayOrderList() {
        return needPayOrderList;
    }

    public void setNeedPayOrderList(List<NeedPayOrderDTO> needPayOrderList) {
        this.needPayOrderList = needPayOrderList;
    }
}
