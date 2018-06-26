package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopProductInfoDTO;

import java.util.List;

public class ExtShopProductInfoDTO extends ShopProductInfoDTO {
    //返回码
    private String showapi_res_code;
    //返回错误信息
    private String showapi_res_error;
    //产品唯一编码
    private String code;
    //库存总量
    private Integer allStoreNumber;
    //本仓库库存量
    private Integer storeNumberSelf;
    /**
     * 图片详情
     */
    private List<String> imageList;


    private int number;
    //扫码后的对象
    private ExtShopScanProductInfoDTO showapi_res_body;

    public Integer getAllStoreNumber() {
        return allStoreNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAllStoreNumber(Integer allStoreNumber) {
        this.allStoreNumber = allStoreNumber;
    }

    public Integer getStoreNumberSelf() {
        return storeNumberSelf;
    }

    public void setStoreNumberSelf(Integer storeNumberSelf) {
        this.storeNumberSelf = storeNumberSelf;
    }

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

    public void setCode(String code) {
        this.code = code;
    }

    public ExtShopScanProductInfoDTO getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ExtShopScanProductInfoDTO showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}