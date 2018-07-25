package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.SeckillProductMapper;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 微商城秒杀服务层
 * Created by wangbaowei on 2018/7/25.
 */

@Service
@Transactional(readOnly = false)
public class SeckillProductService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    /**
     * 查询所有商品
     * @param pageParamVoDTO
     * @return
     */
    public PageParamVoDTO<List<SeckillProductDTO>> queryAllProducts(PageParamVoDTO<SeckillProductDTO> pageParamVoDTO) {
        logger.info("service -- 查询所有商品 queryAllProducts,方法执行");
        PageParamVoDTO<List<SeckillProductDTO>> pageResult = new  PageParamVoDTO<>();
        String currentPage = String.valueOf(pageParamVoDTO.getPageNo());
        String pageSize = String.valueOf(pageParamVoDTO.getPageSize());
        Page<SeckillProductDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<SeckillProductDTO> resultPage = seckillProductMapper.queryAllProducts(page);
        Date nowTime = new Date();
        for (SeckillProductDTO productDTO : resultPage.getList()){
            int productAmount = productDTO.getProductAmount();
            if(productAmount<=0){
                productDTO.setStatus(3);
            }else if(productDTO.getStartTime().getTime()>=nowTime.getTime()){
                productDTO.setStatus(1);
            }else if(productDTO.getEndTime().getTime()>=nowTime.getTime()){
                productDTO.setStatus(2);
            }else{
                productDTO.setStatus(0);
            }
        }
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }

    /**
     * 秒杀 查询缓存中的库存量
     * */
    public int getProductAmout(String fieldId){
        List<Object> orderMap = JedisUtils.getObjectList("seckillproductOrder:" + fieldId);
        int ordeNum = null == orderMap? 0:orderMap.size();
        String productAmountStr = JedisUtils.get("seckillproductAmount:" + fieldId);
        if (StringUtils.isNotNull(productAmountStr) && Integer.parseInt(productAmountStr)-ordeNum>0) {
            return Integer.parseInt(productAmountStr)-ordeNum;
        }
        return 0;
    }

}
