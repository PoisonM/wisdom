package com.wisdom.beauty.core.service.impl;

import com.wisdom.beauty.api.dto.ShopNameListCriteria;
import com.wisdom.beauty.api.dto.ShopNameListDTO;
import com.wisdom.beauty.core.mapper.ShopNameListMapper;
import com.wisdom.beauty.core.service.ShopNameListService;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FileName: ShopNameListService
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 名单相关
 */
@Service("shopNameListService")
public class ShopNameListServiceImpl implements ShopNameListService {

	@Autowired
	private ShopNameListMapper shopNameListMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询名单
	 *
	 * @param shopNameListDTO
	 * @return
	 */
	@Override
	public ShopNameListDTO getShopNameListDTO(ShopNameListDTO shopNameListDTO) {

		if(null == shopNameListDTO){
			logger.error("{}","shopNameListDTO = [" + shopNameListDTO + "]");
		}
		ShopNameListCriteria nameListCriteria = new ShopNameListCriteria();
		ShopNameListCriteria.Criteria c = nameListCriteria.createCriteria();
		
		if(StringUtils.isNotBlank(shopNameListDTO.getPhone())){
		    c.andPhoneEqualTo(shopNameListDTO.getPhone());
		}
		if(StringUtils.isNotBlank(shopNameListDTO.getType())){
			c.andTypeEqualTo(shopNameListDTO.getType());
		}
		if(StringUtils.isNotBlank(shopNameListDTO.getStatus())){
		    c.andStatusEqualTo(shopNameListDTO.getStatus());
		}
		List<ShopNameListDTO> shopNameListDTOS = shopNameListMapper.selectByCriteria(nameListCriteria);
		if(CommonUtils.objectIsNotEmpty(shopNameListDTOS)){
			return shopNameListDTOS.get(0);
		}
		return null;
	}


}
