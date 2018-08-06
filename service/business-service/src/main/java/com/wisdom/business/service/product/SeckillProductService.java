package com.wisdom.business.service.product;

import com.wisdom.business.mapper.product.SeckillProductMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.product.SeckillActivityDTO;
import com.wisdom.common.dto.product.SeckillActivityFieldDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.OrderProductRelationDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.FrontUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微商城秒杀服务层
 * Created by wangbaowei on 2018/7/25.
 */

@Service
@Transactional(readOnly = false)
public class SeckillProductService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 预约详情缓存时常，20分钟
     */
    private int productInfoCacheSeconds = 12000;

    private int productAmountCacheSeconds = 86400000;


    @Autowired
    private SeckillProductMapper seckillProductMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TransactionMapper transactionMapper;

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
//            0马上抢 1即将开始 2活动已结束 3已抢光
            if(productDTO.getProductAmount()>=productDTO.getActivityNum()){
                productDTO.setStatus(3);
            }else if(nowTime.getTime() >= productDTO.getActivityEndTime().getTime()){
                productDTO.setStatus(2);
            }else if(productDTO.getActivityStartTime().getTime()>=nowTime.getTime()){
                if(null !=productDTO.getFieldStartTime()){
                    productDTO.setStatus(1);
                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(productDTO.getActivityStartTime());
                    endCalendar.set(Calendar.HOUR_OF_DAY, productDTO.getFieldEndTime().getHours());//时
                    endCalendar.set(Calendar.MINUTE, productDTO.getFieldStartTime().getMinutes());//分
                    endCalendar.set(Calendar.SECOND, productDTO.getFieldStartTime().getSeconds());//秒
                    productDTO.setCountdown((endCalendar.getTime().getTime()-new Date().getTime())/1000);
                }else{
                    productDTO.setStatus(2);
                }
            }else{
                if(null != productDTO.getFieldStartTime()){
                    if(DateUtils.compTime(nowTime,productDTO.getFieldStartTime()) && DateUtils.compTime(productDTO.getFieldEndTime(),nowTime)){
                        productDTO.setStatus(0);
                        if(null != productDTO.getFieldEndTime()){
                            Calendar endCalendar = Calendar.getInstance();
                            endCalendar.setTime(nowTime);
                            endCalendar.set(Calendar.HOUR_OF_DAY, productDTO.getFieldEndTime().getHours());//时
                            endCalendar.set(Calendar.MINUTE, productDTO.getFieldEndTime().getMinutes());//分
                            endCalendar.set(Calendar.SECOND, productDTO.getFieldEndTime().getSeconds());//秒
                            productDTO.setCountdown((endCalendar.getTime().getTime() - new Date().getTime())/1000);
                        }
                    }else {
                        productDTO.setStatus(1);
                        Calendar endCalendar = Calendar.getInstance();
                        endCalendar.setTime(nowTime);
                        endCalendar.set(Calendar.HOUR_OF_DAY, productDTO.getFieldStartTime().getHours());//时
                        endCalendar.set(Calendar.MINUTE, productDTO.getFieldStartTime().getMinutes());//分
                        endCalendar.set(Calendar.SECOND, productDTO.getFieldStartTime().getSeconds());//秒
                        productDTO.setCountdown((endCalendar.getTime().getTime()-new Date().getTime())/1000);
                    }
                }else {
                    productDTO.setStatus(2);
                }
            }

        }
        pageResult.setTotalCount((int)resultPage.getCount());
        pageResult.setResponseData(resultPage.getList());
        return pageResult;
    }

    /**
     * 获取某个活动的基本信息
     * @param fieldId
     * @return
     */
    public SeckillProductDTO getseckillProductDetailById(String fieldId) {
        logger.info("service -- 根据活动id={}查询商品信息 getseckillProductDetailById,方法执行",fieldId);
        SeckillProductDTO<OfflineProductDTO> seckillproductDTO = (SeckillProductDTO<OfflineProductDTO>) JedisUtils.getObject("seckillProductInfo:"+fieldId);
        if(null == seckillproductDTO){
            seckillproductDTO  = seckillProductMapper.findSeckillProductInfoById(fieldId);
            if(null != seckillproductDTO){
                Query query = new Query().addCriteria(Criteria.where("productId").is(seckillproductDTO.getProductId()));
                OfflineProductDTO offlineProductDTO = mongoTemplate.findOne(query, OfflineProductDTO.class,"offlineProduct");
                if(seckillproductDTO!=null)
                {
                    offlineProductDTO.setNowTime(DateUtils.formatDateTime(new Date()));
                    seckillproductDTO.setProductDetail(offlineProductDTO);
                }
                int validityTime = (int)(seckillproductDTO.getActivityEndTime().getTime()-seckillproductDTO.getActivityStartTime().getTime());
                JedisUtils.setObject("seckillProductInfo:"+fieldId,seckillproductDTO,validityTime/1000);
            }else{
                return null;
            }
        }
        //这里有个弊端带付款后更新缓存信息
        seckillproductDTO.setStockNum( getProductAmout(fieldId));
        seckillproductDTO.setCountdown(-1);
        if(null != seckillproductDTO.getFieldEndTime()){
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(new Date());
            endCalendar.set(Calendar.HOUR_OF_DAY, seckillproductDTO.getFieldEndTime().getHours());//时
            endCalendar.set(Calendar.MINUTE, seckillproductDTO.getFieldEndTime().getMinutes());//分
            endCalendar.set(Calendar.SECOND, seckillproductDTO.getFieldEndTime().getSeconds());//秒
            seckillproductDTO.setCountdown((endCalendar.getTime().getTime() - new Date().getTime())/1000);
        }
        return seckillproductDTO;
    }


    /**
     * 秒杀 查询缓存中的库存量
     * */
    public int getProductAmout(String fieldId){
        int orderNumrds = 0;
        Set<String> ordeNumStr = JedisUtils.vagueSearch("seckillproductOrderNum:" + fieldId+":");
        for(String orderNum : ordeNumStr){
            orderNum = JedisUtils.get(orderNum);
            if(StringUtils.isNotNull(orderNum)){
                orderNumrds += Integer.parseInt(orderNum);
            }
        }
        String productAmountStr = JedisUtils.get("seckillproductAmount:" + fieldId);
        if (StringUtils.isNotNull(productAmountStr) && (Integer.parseInt(productAmountStr)-orderNumrds)>0) {
            return Integer.parseInt(productAmountStr)-orderNumrds;
        }
        return 0;
    }


    /**
     * 获取活动列表
     * */
    public PageParamVoDTO<List<SeckillActivityDTO>> findSeckillActivitylist(SeckillActivityDTO seckillActivityDTO){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        PageParamVoDTO<List<SeckillActivityDTO>> page = new PageParamVoDTO();

        Date activityDate = new Date();
        seckillActivityDTO.setActivityStatusTimeString(sdf.format(activityDate));
        if(seckillActivityDTO.getStartTime()!=null){
            seckillActivityDTO.setStartTimeString(sdf.format(seckillActivityDTO.getStartTime()));
        }
        if(seckillActivityDTO.getEndTime()!=null){
            seckillActivityDTO.setEndTimeString(sdf.format(seckillActivityDTO.getEndTime()));
        }
        List<SeckillActivityDTO> seckillActivityList = seckillProductMapper.findSeckillActivityList(seckillActivityDTO);
        Date now = new Date();

        for(SeckillActivityDTO seckillActivity : seckillActivityList){

            seckillActivity.setStartTimeString(sdf1.format(seckillActivity.getStartTime()));
            seckillActivity.setEndTimeString(sdf1.format(seckillActivity.getEndTime()));

            //判断活动状态是否为启用
            if(seckillActivity.getIsEnable()!=0){

                //根据时间判断活动状态
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("距离活动").append(betweenDays).append("天");
                    seckillActivity.setActivityDays(sb.toString());
                }
            }else{
                seckillActivity.setActivityStatus("已结束");
            }

        }

        int count = seckillProductMapper.findSeckillActivityCount(seckillActivityDTO);

        page.setResponseData(seckillActivityList);
        page.setTotalCount(count);
        page.setPageNo(seckillActivityDTO.getPageNo()+1);
        page.setPageSize(seckillActivityDTO.getPageSize());
        return page;
    }

    /**
     * 修改活动状态
     *
     * */
    public String changeSeckillActivityStatus(Integer id ,Integer isEnable){
        try{
            seckillProductMapper.changeSecKillProductStatus(id,isEnable);
            return "success";
        }catch (Exception e){
            logger.info("秒杀活动Id:{}状态，改变失败",id);
            return "failure";
        }
    }

    /**
     * 新增秒杀活动
     *
     *
     * */
    @Transactional(rollbackFor = Exception.class)
    public String  addSeckillActivity(SeckillActivityDTO seckillActivityDTO) throws Exception{

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder sbStart = new StringBuilder();
        sbStart.append(sdf1.format(seckillActivityDTO.getStartTime())).append(" ").append("00:00:00");
        StringBuilder sbEnd = new StringBuilder();
        sbEnd.append(sdf1.format(seckillActivityDTO.getEndTime())).append(" ").append("23:59:59");

        seckillActivityDTO.setStartTime(sdfEnd.parse(sbStart.toString()));
        seckillActivityDTO.setEndTime(sdfEnd.parse(sbEnd.toString()));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        seckillActivityDTO.setCreateTime(new Date());
        StringBuilder activityNo = new StringBuilder();
        Date activityNoDate = new Date();
        activityNo.append(sdf1.format(activityNoDate).replaceAll("-","")).append(activityNoDate.getTime()/1000);
        seckillActivityDTO.setActivityNo(activityNo.toString());
        int activityId = seckillProductMapper.addSeckillActivity(seckillActivityDTO);

        for(SeckillActivityFieldDTO seckillActivityField : seckillActivityDTO.getSessionList()){
            SeckillActivityFieldDTO seckillActivityFieldDTO = new SeckillActivityFieldDTO();
            seckillActivityFieldDTO.setActivityId(seckillActivityDTO.getId());
            seckillActivityFieldDTO.setCreateTime(new Date());
            long msc = 2592000;
            try {
                seckillActivityFieldDTO.setStartTime(sdf.parse(seckillActivityField.getStartTimeString()));
                seckillActivityFieldDTO.setEndTime(sdf.parse(seckillActivityField.getEndTimeString()));
                msc = (sdfEnd.parse(sbStart.toString()).getTime()-(new Date()).getTime())/1000;
            }catch (Exception e){

            }
            int ms = (int)Integer.parseInt(String.valueOf(msc));
            seckillActivityFieldDTO.setProductAmount(0);
            seckillProductMapper.addSeckillActivityField(seckillActivityFieldDTO);
            StringBuilder sb = new StringBuilder();
            sb.append("seckillproductAmount:").append(String.valueOf(seckillActivityFieldDTO.getId()));
            JedisUtils.set(sb.toString(),String.valueOf(seckillActivityDTO.getActivityNum()),ms);

            logger.info("key值={}",sb.toString());
            logger.info("缓存值={}",sb.toString());
        }

        return "success";
    }

    /**
     * 获取秒杀活动详情
     *
     * */
    public SeckillActivityDTO getSecKillActivity(Integer id){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfH = new SimpleDateFormat("HH:mm:ss");
        SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
        seckillActivityDTO = seckillProductMapper.getSecKillActivity(id);

        seckillActivityDTO.setStartTimeString(sdf.format(seckillActivityDTO.getStartTime()));
        seckillActivityDTO.setEndTimeString(sdf.format(seckillActivityDTO.getEndTime()));

        Date nowDate = new Date();
        List<SeckillActivityFieldDTO> seckillActivityFieldList = seckillProductMapper.findSecKillActivityField(seckillActivityDTO.getId());
        for(SeckillActivityFieldDTO seckillActivityField:seckillActivityFieldList){

            seckillActivityField.setStartTimeString(sdfH.format(seckillActivityField.getStartTime()));
            seckillActivityField.setEndTimeString(sdfH.format(seckillActivityField.getEndTime()));


            if(seckillActivityDTO.getStartTime().getTime()>nowDate.getTime()){

                seckillActivityField.setActivitySessionStatus("未开始");
            }else if(nowDate.getTime()>seckillActivityDTO.getEndTime().getTime()){

                seckillActivityField.setActivitySessionStatus("已结束");
            }else{

                StringBuilder sbStartTime = new StringBuilder();
                sbStartTime.append(sdf.format(nowDate)).append(" ").append(seckillActivityField.getStartTimeString());

                StringBuilder sbEndTime = new StringBuilder();
                sbEndTime.append(sdf.format(nowDate)).append(" ").append(seckillActivityField.getEndTimeString());

                try {
                    if (nowDate.getTime() < sdfNow.parse(sbStartTime.toString()).getTime()){

                        seckillActivityField.setActivitySessionStatus("未开始");
                    }else if(nowDate.getTime() > sdfNow.parse(sbEndTime.toString()).getTime()){

                        seckillActivityField.setActivitySessionStatus("已结束");
                    }else{

                        seckillActivityField.setActivitySessionStatus("进行中");
                    }
                }catch (Exception e){

                    logger.info("异常信息：{}",e.getMessage());
                    seckillActivityField.setActivitySessionStatus("状态异常");
                }finally {
                    continue;
                }
            }
        }

        seckillActivityDTO.setSessionList(seckillActivityFieldList);
        return seckillActivityDTO;
    }

    /**
     * 获取场次详情
     * @param id
     * */
    public SeckillActivityFieldDTO findSecKillActivityFieldById(Integer id){
        return seckillProductMapper.findSecKillActivityFieldById(id);
    };

    /**
     * 支付完成后处理更新缓存等问题
     * */
    public void handlePayNotify(String orderId){
        String fieldId = JedisUtils.get("seckillproductOrder:"+orderId);
        if(StringUtils.isNotNull(fieldId)){
            JedisUtils.del("seckillproductOrder:"+orderId);
            JedisUtils.del("seckillProductInfo:"+fieldId);
            String ordeNumStr = JedisUtils.get("seckillproductOrderNum:" + fieldId+":"+orderId);
            int ordeNum = StringUtils.isNotNull(ordeNumStr)?Integer.parseInt(ordeNumStr):0;
            JedisUtils.del("seckillproductOrderNum:" + fieldId+":"+orderId);
            SeckillProductDTO seckillProductDTO = seckillProductMapper.findSeckillProductInfoById(fieldId);
            int saleNum = seckillProductDTO.getProductAmount()+ordeNum;
            SeckillActivityFieldDTO seckillActivityFieldDTO = new SeckillActivityFieldDTO();
            seckillActivityFieldDTO.setId(Integer.parseInt(fieldId));
            seckillActivityFieldDTO.setProductAmount(saleNum);
            seckillActivityFieldDTO.setUpdateTime(new Date());
            String proAmountStr = JedisUtils.get("seckillproductAmount:" + fieldId);
            int proAmount = StringUtils.isNotNull(proAmountStr)?Integer.parseInt(proAmountStr):0;
            int validityTime = (int)(seckillProductDTO.getActivityEndTime().getTime()-new Date().getTime());
            JedisUtils.set("seckillproductAmount:" + fieldId,(proAmount-ordeNum)+"",validityTime/1000);
            seckillProductMapper.updateSeckillActivityFieldById(seckillActivityFieldDTO);
        }
    }
}
