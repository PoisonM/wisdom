package com.wisdom.common.util;

import com.wisdom.common.dto.product.ProductCodeDTO;
import com.wisdom.common.dto.transaction.OrderCodeDTO;
import com.wisdom.common.dto.transaction.TransactionCodeDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.wisdom.common.util.OrderNumberTool.generateString;

/**
 * Created by jiangzhongge on 2016-5-12.
 */
public class CodeGenUtil {

    private static MongoTemplate mongoTemplate = (MongoTemplate) SpringUtil.getBean(MongoTemplate.class);

    /**
     * 生成订单号 {年(后两位)月日(171213)+ XXXXXX(随机数)
     * @return
     */
    public static String getOrderCodeNumber(){
        String orderNumber = "";
        do
        {
            //获取当前年限的后两位
            String yearLast = new SimpleDateFormat("yyMMdd", Locale.CHINESE).format(Calendar.getInstance().getTime());

            //订单号 新生成的
            orderNumber = yearLast + generateString(6);

        }while (removeRepeatOrderNumber(orderNumber));
        return orderNumber;
    }

    public static String getTransactionCodeNumber(){
        String transactionNumber = "";

        do
        {
            Date date=new Date();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");

            //新生成的支付流水号 transactionNumber
            transactionNumber=sdf.format(date)+"01"+generateString(10);

        }while (removeRepeatTransactionNumber(transactionNumber));

        return transactionNumber;
    }

    public static String getProductCodeNumber(){
        String productNumber = "";

        do
        {
            Date date=new Date();
            SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
            //新生成的产品号 productNumber
            productNumber = sdf.format(date)+"01"+generateString(10);

        }while (removeRepeatProductNumber(productNumber));

        return productNumber;
    }

    /**
     * 插入新的订单号
     * @param orderNumber
     * @return
     */
    private static Boolean removeRepeatOrderNumber(String orderNumber) {

        Query query = new Query().addCriteria(Criteria.where("orderCode").is(orderNumber));
        OrderCodeDTO orderCodeDTO = mongoTemplate.findOne(query, OrderCodeDTO.class,"orderCode");
        if(orderCodeDTO!=null)
        {
            return true;
        }
        else
        {
            orderCodeDTO = new OrderCodeDTO();
            orderCodeDTO.setOrderCode(orderNumber);
            mongoTemplate.insert(orderCodeDTO, "orderCode");
            return false;
        }
    }

    /**
     * 插入新的订单号
     * @param productCodeNumber
     * @return
     */
    private static Boolean removeRepeatProductNumber(String productCodeNumber) {

        Query query = new Query().addCriteria(Criteria.where("productCode").is(productCodeNumber));
        ProductCodeDTO productCodeDTO = mongoTemplate.findOne(query, ProductCodeDTO.class,"productCode");
        if(productCodeDTO!=null)
        {
            return true;
        }
        else
        {
            productCodeDTO = new ProductCodeDTO();
            productCodeDTO.setProductCode(productCodeNumber);
            mongoTemplate.insert(productCodeDTO, "productCode");
            return false;
        }
    }

    /**
     * 插入新的订单号
     * @param transactionCodeNumber
     * @return
     */
    private static Boolean removeRepeatTransactionNumber(String transactionCodeNumber) {

        Query query = new Query().addCriteria(Criteria.where("transactionCode").is(transactionCodeNumber));
        TransactionCodeDTO transactionCodeDTO = mongoTemplate.findOne(query, TransactionCodeDTO.class,"transactionCode");
        if(transactionCodeDTO!=null)
        {
            return true;
        }
        else
        {
            transactionCodeDTO = new TransactionCodeDTO();
            transactionCodeDTO.setTransactionCode(transactionCodeNumber);
            mongoTemplate.insert(transactionCodeDTO, "transactionCode");
            return false;
        }
    }

}
