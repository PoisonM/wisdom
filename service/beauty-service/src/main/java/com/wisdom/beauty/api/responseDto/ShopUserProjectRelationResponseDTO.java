package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;

/**
 * Created by zhanghuan on 2018/5/29.
 */
public class ShopUserProjectRelationResponseDTO extends ShopUserProjectRelationDTO {
    /** 是否过期  0未过期 1过期*/
    private  String  overdue;


    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }
}
