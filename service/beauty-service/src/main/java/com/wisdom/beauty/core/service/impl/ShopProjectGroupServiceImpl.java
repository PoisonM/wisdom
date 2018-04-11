package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopProjectGroupCriteria;
import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.core.mapper.ShopProjectGroupMapper;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.common.dto.account.PageParamVoDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ShopProjectGroupServiceImpl
 *
 * @Author： huan
 * 
 * @Description: 套卡相关的接口
 * @Date:Created in 2018/4/11 15:15
 * @since JDK 1.8
 */
@Service("shopProjectGroupService")
public class ShopProjectGroupServiceImpl implements ShopProjectGroupService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShopProjectGroupMapper shopProjectGroupMapper;

	@Override
	public List<ShopProjectGroupDTO> getShopProjectGroupList(PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO) {
		ShopProjectGroupDTO shopProjectGroupDTO = pageParamVoDTO.getRequestData();
		logger.info("getArchivesList方法传入的参数,sysShopId={},projectGroupName={}", shopProjectGroupDTO.getSysShopId(),
				shopProjectGroupDTO.getProjectGroupName());
		if (StringUtils.isBlank(shopProjectGroupDTO.getSysShopId())) {
			throw new ServiceException("SysShopId为空");
		}
		ShopProjectGroupCriteria criteria = new ShopProjectGroupCriteria();
		ShopProjectGroupCriteria.Criteria c = criteria.createCriteria();

		// 排序
		criteria.setOrderByClause("create_date");
		// 分页
		criteria.setLimitStart(pageParamVoDTO.getPageNo());
		criteria.setPageSize(pageParamVoDTO.getPageSize());
		// 参数
		c.andSysShopIdEqualTo(shopProjectGroupDTO.getSysShopId());
		if (StringUtils.isNotBlank(shopProjectGroupDTO.getProjectGroupName())) {
			c.andProjectGroupNameLike("%" + shopProjectGroupDTO.getProjectGroupName() + "%");
		}

		List<ShopProjectGroupDTO> shopCustomerArchiveslist = shopProjectGroupMapper.selectByCriteria(criteria);

		return shopCustomerArchiveslist;
	}

}
