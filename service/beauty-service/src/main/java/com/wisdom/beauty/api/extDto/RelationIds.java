package com.wisdom.beauty.api.extDto;

import java.util.List;

/**
 * FileName: RelationIds
 *
 * @author: 赵得良
 * Date:     2018/4/27 0027 18:22
 * Description:
 */
public class RelationIds<T> {


    List<T> relationIds;

    public List<T> getRelationIds() {
        return relationIds;
    }

    public void setRelationIds(List<T> relationIds) {
        this.relationIds = relationIds;
    }
}
