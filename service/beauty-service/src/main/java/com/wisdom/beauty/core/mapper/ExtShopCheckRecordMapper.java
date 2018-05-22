package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopCheckRecordCriteria;
import com.wisdom.beauty.api.dto.ShopCheckRecordDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanghuan on 2018/5/22.
 */
@MyBatisDao
@Repository
public interface ExtShopCheckRecordMapper extends BaseDao<ShopCheckRecordDTO, ShopCheckRecordCriteria, String> {
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:  批量插入
    *@Date:2018/5/22 11:48
    */
    int insertBatchCheckRecord(List<ShopCheckRecordDTO> list);
}
