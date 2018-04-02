package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by zbm84 on 2017/8/3.
 */
public class ValidateCodeDTO {

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "code")
    private String code;

    @JSONField(name = "createDate")
    private Date createDate;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
