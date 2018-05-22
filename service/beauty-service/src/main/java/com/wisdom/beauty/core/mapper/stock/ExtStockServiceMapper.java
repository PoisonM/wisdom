package com.wisdom.beauty.core.mapper.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wisdom.beauty.api.dto.ShopStoreCriteria;
import com.wisdom.beauty.api.dto.ShopStoreDTO;
import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.entity.BaseDao;
import com.wisdom.common.persistence.annotation.MyBatisDao;

@MyBatisDao
@Repository
public interface ExtStockServiceMapper extends BaseDao<ShopStoreDTO, ShopStoreCriteria, String> {



    /**
     *  查询仓库列表
     *  @param  pageParamDTO  仓库的dto分页对象
     *  @return 仓库列表
     *  @autur  zhangchao
     * */
    List<ExtShopStoreDTO> findStoreList(PageParamDTO<ExtShopStoreDTO> pageParamDTO);


}