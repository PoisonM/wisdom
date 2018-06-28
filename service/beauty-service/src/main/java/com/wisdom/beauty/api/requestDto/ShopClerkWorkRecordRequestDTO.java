package com.wisdom.beauty.api.requestDto;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/31.
 *
 * 员工工作业绩请求参数
 */
public class ShopClerkWorkRecordRequestDTO extends ShopClerkWorkRecordDTO {
    /**
     * 请求来源，用于区分是否需要设置goodsType和consumeType条件
     */
    private Boolean typeRequire=false;
    /**
     * goodsType集合
     */
    private List<String> goodsTypeList;
    /**
     * consumeType集合
     */
    private List<String> consumeTypeList;
    /**
     * 查询类型
     */
    private String searchFile;

    public Boolean getTypeRequire() {
        return typeRequire;
    }

    public void setTypeRequire(Boolean typeRequire) {
        this.typeRequire = typeRequire;
    }

    public List<String> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<String> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    public List<String> getConsumeTypeList() {
        return consumeTypeList;
    }

    public void setConsumeTypeList(List<String> consumeTypeList) {
        this.consumeTypeList = consumeTypeList;
    }

    public String getSearchFile() {
        return searchFile;
    }

    public void setSearchFile(String searchFile) {
        this.searchFile = searchFile;
    }
}
