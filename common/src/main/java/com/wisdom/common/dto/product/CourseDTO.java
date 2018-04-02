package com.wisdom.common.dto.product;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class CourseDTO {

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "list")
    private List<SecondCourseDTO> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SecondCourseDTO> getList() {
        return list;
    }

    public void setList(List<SecondCourseDTO> list) {
        this.list = list;
    }
}