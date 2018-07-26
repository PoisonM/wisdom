/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.business.mapper.product;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.product.SeckillActivityDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisDao
@Repository
public interface SeckillProductMapper {

	//查询所有商品
	Page<SeckillProductDTO> queryAllProducts(Page<SeckillProductDTO> page);

	//根据场次查询商品
	SeckillProductDTO getSeckillProductInfo(@Param("productId") String productId);

	//获取活动列表
	List<SeckillActivityDTO>  findSeckillActivityList(SeckillActivityDTO seckillActivityDTO);

	//获取活动列表总数
	int findSeckillActivityCount(SeckillActivityDTO seckillActivityDTO);
}
