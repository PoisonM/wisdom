package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;

/**
 * FileName: ExtShopUserProjectRelationDTO
 *
 * @author: 赵得良
 * Date:     2018/6/30 0030 13:57
 * Description:
 */
public class ExtShopUserProjectRelationDTO extends ShopUserProjectRelationDTO {
    //项目二级类别id
    private String projectTypeTwoId;

    public String getProjectTypeTwoId() {
        return projectTypeTwoId;
    }

    public void setProjectTypeTwoId(String projectTypeTwoId) {
        this.projectTypeTwoId = projectTypeTwoId;
    }
}
