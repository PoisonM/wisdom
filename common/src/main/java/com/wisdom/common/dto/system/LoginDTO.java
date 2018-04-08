package com.wisdom.common.dto.system;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by cjk on 2017/5/24.
 */
public class LoginDTO {

    @JSONField(name = "userPhone")
    private String userPhone;

    @JSONField(name = "source")
    private String source;

    @JSONField(name = "code")
    private String code;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
