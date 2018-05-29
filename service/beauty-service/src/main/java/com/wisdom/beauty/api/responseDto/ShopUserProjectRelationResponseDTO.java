package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;

/**
 * Created by zhanghuan on 2018/5/29.
 */
public class ShopUserProjectRelationResponseDTO extends ShopUserProjectRelationDTO {
    /** 是否过期  false未过期*/
    private  Boolean  overdue;

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }
}
