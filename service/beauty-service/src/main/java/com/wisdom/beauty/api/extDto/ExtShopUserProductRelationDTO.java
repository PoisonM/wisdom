package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;

/**
 * FileName: ExtShopUserProductRelationDTO
 *
 * @author: 赵得良
 * Date:     2018/6/30 0030 13:58
 * Description:
 */
public class ExtShopUserProductRelationDTO extends ShopUserProductRelationDTO {

    //产品二级类别id
    private String productTypeTwoId;

    public String getProductTypeTwoId() {
        return productTypeTwoId;
    }

    public void setProductTypeTwoId(String productTypeTwoId) {
        this.productTypeTwoId = productTypeTwoId;
    }
}
