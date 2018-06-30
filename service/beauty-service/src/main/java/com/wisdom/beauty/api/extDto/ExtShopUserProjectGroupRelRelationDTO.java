package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;

import java.util.List;

public class ExtShopUserProjectGroupRelRelationDTO extends ShopUserProjectGroupRelRelationDTO {
    //
    private List projectList;

    public List getProjectList() {
        return projectList;
    }

    public void setProjectList(List projectList) {
        this.projectList = projectList;
    }
}