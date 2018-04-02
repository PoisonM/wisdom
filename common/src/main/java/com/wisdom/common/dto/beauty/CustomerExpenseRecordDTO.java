package com.wisdom.common.dto.beauty;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by cjk on 2017/5/24.
 */
public class CustomerExpenseRecordDTO {

    @JSONField(name = "shopId")
    private String shopId;

    @JSONField(name = "customerId")
    private String customerId;



}
