/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.business.mapper.product;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.ProductClassDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface ProductMapper {

	ProductDTO getBusinessProductInfo(@Param("productId") String productId);

	List<ProductDTO> findOfflineProductList(PageParamDTO productDTO);

    List<ProductDTO> findTrainingProductList(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("price") float price);

	List<ProductDTO> findTrainingProductListNew(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("secondType") String secondType);

	//查询所有商品
	Page<ProductDTO> queryAllProducts(Page<ProductDTO> page);

	//条件查询商品Count
	int queryProductsCountByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);

	//条件查询商品
	List<ProductDTO> queryProductsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);

	//根据id查询商品
	ProductDTO findProductById(String id);

	//编辑商品
	void updateProductByParameters(ProductDTO pageParamVoDTO);

	//编辑商品-上架
	void putAwayProductById(String id);

	//编辑商品-下架
	void delProductById(String id);

	//新增商品
	void addOfflineProduct(ProductDTO productDTO);

    List<ProductDTO> findSpecialOfflineProductList(PageParamDTO pageParamDTO);

    List<String> getBorderSpecialProductBrandList();

    List<ProductClassDTO> getOneProductClassList();

	List<ProductClassDTO> getTwoProductClassList(@Param("productClassId") String productClassId);

    void addProductClass(ProductClassDTO productClassDTO);

    void updateProductClass(ProductClassDTO productClassDTO);

	List<ProductClassDTO> getProductClassList(ProductClassDTO productClassDTO);
}
