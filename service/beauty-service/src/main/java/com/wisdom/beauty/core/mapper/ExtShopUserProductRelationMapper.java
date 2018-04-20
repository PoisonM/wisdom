package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopUserProductRelationCriteria;
import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ${CLASS_NAME}
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/18 10:58
 * @since JDK 1.8
 */
@MyBatisDao
@Repository
public interface ExtShopUserProductRelationMapper extends BaseDao<ShopUserProductRelationDTO, ShopUserProductRelationCriteria, String> {
    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:  根据sysClerkId获取所有用的为领取产品的数量
     * @Date:2018/4/18 11:33
     */
    List<UserProductRelationResponseDTO> getWaitReceiveNumber(String sysClerkId);
}
