package com.wisdom.beauty.api.extDto;

import java.util.Date;

public class ExtShopProductInfoDTO {
    //返回码
    private String showapi_res_code;
    //返回错误信息
    private String showapi_res_error;
    //产品唯一编码
    private String code;

    private Date createDate;
    //扫码后的对象
    private ExtShopScanProductInfoDTO showapi_res_body;

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getCode() {
        return code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExtShopScanProductInfoDTO getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ExtShopScanProductInfoDTO showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
}