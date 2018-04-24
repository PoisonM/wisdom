package com.wisdom.beauty.core.service.stock.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.api.dto.ShopStockRecordDTO;
import com.wisdom.beauty.core.mapper.stock.ExtStockServiceMapper;
import com.wisdom.beauty.core.mapper.ShopStockRecordMapper;
import com.wisdom.beauty.core.service.stock.ShopStockService;
import com.wisdom.common.dto.system.PageParamDTO;


/**
 * FileName: ShopStockServiceImpl
 *
 * @author:  张超
 * Date:     2018/4/23 0003 14:49
 * Description: 仓库和库存相关
 */
@Service("ShopStockService")
public class ShopStockServiceImpl implements ShopStockService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExtStockServiceMapper extStockServiceMapper;

    @Autowired
    private  ShopStockRecordMapper shopStockRecordMapper;


    /**
     * 查询仓库列表
     * @param pageParamDTO 分页对象
     * @return
     */
    @Override
    public  PageParamDTO<List<ExtShopStoreDTO>> findStoreListS(PageParamDTO<ExtShopStoreDTO> pageParamDTO){


        PageParamDTO<List<ExtShopStoreDTO>> page = new PageParamDTO<>();
        List<ExtShopStoreDTO> shopStoreList = null;
        try {

             shopStoreList = extStockServiceMapper.findStoreList(pageParamDTO);

        }catch(Exception e){
            logger.info(e.getMessage());
        }

        page.setResponseData(shopStoreList);
        page.setTotalCount(shopStoreList.get(0).getSum());
        logger.info("查询仓库列表" + shopStoreList);
        return page;
    }


    /**
     * 插入一条出入库记录
     * @param shopStockRecordDTO 出入库表实体对象
     * @return
     */
    @Override
    public  void insertStockRecord(ShopStockRecordDTO shopStockRecordDTO){

        try {

            shopStockRecordMapper.insert(shopStockRecordDTO);

        }catch(Exception e){
            logger.info(e.getMessage());
        }

        logger.info("插入出入库记录成功"+shopStockRecordDTO.getId());
    }


}
