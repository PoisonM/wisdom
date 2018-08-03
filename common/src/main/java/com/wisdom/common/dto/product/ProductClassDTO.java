package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductClassDTO {

    /**
     * 类目id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 产品的ID编号
     */
    @JSONField(name = "productClassId")
    private String productClassId;

    /**
     * 类目名称
     */
    @JSONField(name = "productClassName")
    private String productClassName;

    /**
     * 类目父级ID编号
     */
    @JSONField(name = "parentId")
    private String parentId;

    /**
     * 二级类目图片url
     */
    @JSONField(name = "url")
    private String url;

    @JSONField(name = "createDate")
    private String createDate;

    /**
     * 1:一级类目2:二级类目
     */
    @JSONField(name = "rank")
    private String rank;
    /**
     * 一级类目排序
     */
    @JSONField(name = "sort")
    private int sort;
    /**
     * 1:有效0:无效
     */
    @JSONField(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(String productClassId) {
        this.productClassId = productClassId;
    }

    public String getProductClassName() {
        return productClassName;
    }

    public void setProductClassName(String productClassName) {
        this.productClassName = productClassName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}