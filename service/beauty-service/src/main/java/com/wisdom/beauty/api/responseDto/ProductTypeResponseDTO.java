package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

import java.util.List;

/**
 * ClassName: ProductTypeResponseDTO
 *
 * @Author： huan
 * 
 * @Description:
 * @Date:Created in 2018/4/20 12:04
 * @since JDK 1.8
 */
public class ProductTypeResponseDTO extends BaseEntity {

	/** 主键 */
	private String id;
	/** 产品品牌类别名称 */
	private String productTypeName;
	/** 产品品牌类别父品牌id */
	private String parentId;
	/** 0：客装 1：院装 2：易耗品 */
	private String productType;
    /**用于存放二级产品*/
    private List<ProductTypeResponseDTO> productTypeResponses;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

    public List<ProductTypeResponseDTO> getProductTypeResponses() {
        return productTypeResponses;
    }

    public void setProductTypeResponses(List<ProductTypeResponseDTO> productTypeResponses) {
        this.productTypeResponses = productTypeResponses;
    }
}
