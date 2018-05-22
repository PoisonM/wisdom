package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationCriteria;
import com.wisdom.beauty.api.dto.ShopProjectProductCardRelationDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ${CLASS_NAME}
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/16 17:51
 * @since JDK 1.8
 */

@MyBatisDao
@Repository
public interface ExtShopProjectProductCardRelationMapper extends BaseDao<ShopProjectProductCardRelationDTO, ShopProjectProductCardRelationCriteria, String> {
    List<ShopProjectProductCardRelationDTO> selectdiscountInfoByCriteria(ShopProjectProductCardRelationCriteria shopProjectProductCardRelationCriteria);
}
