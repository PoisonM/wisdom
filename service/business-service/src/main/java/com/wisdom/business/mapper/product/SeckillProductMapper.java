/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.business.mapper.product;

import com.wisdom.common.dto.product.SeckillActivityDTO;
import com.wisdom.common.dto.product.SeckillActivityFieldDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
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
	SeckillProductDTO findSeckillProductInfoById(@Param("id") String id);

	//获取活动列表
	List<SeckillActivityDTO>  findSeckillActivityList(SeckillActivityDTO seckillActivityDTO);

	//获取活动列表总数
	int findSeckillActivityCount(SeckillActivityDTO seckillActivityDTO);

	//更改活动状态
	void changeSecKillProductStatus(@Param("id") Integer id,@Param("isEnable") Integer isEnable);

	//新增秒杀活动
	int addSeckillActivity(SeckillActivityDTO seckillActivityDTO);

	//新增活动场次
	void  addSeckillActivityField(SeckillActivityFieldDTO seckillActivityFieldDTO);

	//获取秒杀活动详情
	SeckillActivityDTO getSecKillActivity(@Param("id") Integer id);

	//获取秒杀活动场次详情
	List<SeckillActivityFieldDTO>  findSecKillActivityField(@Param("activityId") Integer activityId);

}
