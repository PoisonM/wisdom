package com.wisdom.beauty.core.mapper;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordCriteria;
import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhanghuan on 2018/6/1.
 */
@Repository
@MyBatisDao
public interface ExtShopClerkWorkRecordMapper extends BaseDao<ShopClerkWorkRecordDTO, ShopClerkWorkRecordCriteria, String> {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 批量插入员工工作记录
     * @Date:2018/6/1 10:15
     */
    int insertBatchClerkRecord(List<ShopClerkWorkRecordDTO> list);
}
