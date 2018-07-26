package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.SeckillProductMapper;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.SeckillActivityDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.FrontUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
    @Autowired
    private MongoTemplate mongoTemplate;

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
//            0马上抢 1即将开始 2活动已结束 3已抢光
            if(productAmount<=0){
                productDTO.setStatus(3);
            }else if(productDTO.getEndTime().getTime()>=nowTime.getTime()){
                productDTO.setStatus(2);
            }else if(productDTO.getStartTime().getTime()>=nowTime.getTime()){
                productDTO.setStatus(1);
            }else{
                productDTO.setStatus(0);
            }
        }
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }

    /**
     * 获取某个活动的基本信息
     * @param activtyId
     * @return
     */
    public SeckillProductDTO getseckillProductDetailById(String activtyId) {
        logger.info("service -- 根据活动id={}查询商品信息 getseckillProductDetailById,方法执行",activtyId);
        SeckillProductDTO<OfflineProductDTO> seckillproductDTO = (SeckillProductDTO<OfflineProductDTO>) JedisUtils.getObject("seckillProductInfo:"+activtyId);
        if(null == seckillproductDTO){
            seckillproductDTO  = seckillProductMapper.findSeckillProductInfoById(activtyId);
            Query query = new Query().addCriteria(Criteria.where("productId").is(seckillproductDTO.getProductId()));
            OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
            offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
            if(seckillproductDTO!=null)
            {
                seckillproductDTO.setProductDetail(offlineProductDTO);
            }
            JedisUtils.setObject("seckillProductInfo:"+activtyId,offlineProductDTO,1);
        }
        return seckillproductDTO;
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


    /**
     * 获取活动列表
     * */
    public PageParamVoDTO<List<SeckillActivityDTO>> findSeckillActivitylist(SeckillActivityDTO seckillActivityDTO){

        PageParamVoDTO<List<SeckillActivityDTO>> page = new PageParamVoDTO();
        List<SeckillActivityDTO> seckillActivityList = seckillProductMapper.findSeckillActivityList(seckillActivityDTO);
        Date now = new Date();
        for(SeckillActivityDTO seckillActivity : seckillActivityList){
            if(now.getTime()>= seckillActivity.getStartTime().getTime()){
                if(now.getTime()<=seckillActivity.getEndTime().getTime()){
                    seckillActivity.setActivityStatus("进行中");
                }else if(now.getTime()>seckillActivity.getEndTime().getTime()){
                    seckillActivity.setActivityStatus("已结束");
                }
            }else{
                seckillActivity.setActivityStatus("未开始");
                Calendar beginCalendar = Calendar.getInstance();
                beginCalendar.set(seckillActivity.getStartTime().getYear(),seckillActivity.getStartTime().getMonth(),seckillActivity.getStartTime().getDate(),seckillActivity.getStartTime().getHours(),seckillActivity.getStartTime().getMinutes(),seckillActivity.getStartTime().getSeconds());		//设定时间为2017年3月2日20:20:20
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(now.getYear(),now.getMonth(),now.getDay(),now.getHours(),now.getMinutes(),now.getSeconds());		//设定时间为2017年3月3日10:10:10
                long beginTime = beginCalendar.getTime().getTime();
                long endTime = endCalendar.getTime().getTime();
                long betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24));
                seckillActivity.setActivityDays(betweenDays);
            }
        }
        int count = seckillProductMapper.findSeckillActivityCount(seckillActivityDTO);
        page.setRequestData(seckillActivityList);
        page.setTotalCount(count);
        page.setPageNo(seckillActivityDTO.getPageNo());
        page.setPageSize(seckillActivityDTO.getPageSize());
        return page;
    }

}
