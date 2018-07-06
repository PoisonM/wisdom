package com.wisdom.beauty.core.redis;

import com.fasterxml.jackson.core.JsonParseException;
import com.wisdom.beauty.api.extDto.ExtShopProductInfoDTO;
import com.wisdom.beauty.api.extDto.ImageUrl;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.HttpUtils;
import com.wisdom.common.util.JacksonUtil;
import com.wisdom.common.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: redisUtils
 *
 * @author: 赵得良
 * Date:     2018/4/4 0004 11:03
 * Description: B端redis帮助类
 */
@Service("mongoUtils")
public class MongoUtils {

    @Autowired
    private MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存图片信息
     *
     * @param list
     * @param imageId
     */
    public void saveImageUrl(List<String> list, String imageId) {
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setImageId(imageId);
        imageUrl.setUrl(list);
        mongoTemplate.save(imageUrl, "imageUrl");
    }
    public void updateImageUrl(List<String> list, String imageId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("imageId").is(imageId));
        Update update = Update.update("url", list);
        mongoTemplate.updateFirst(query, update, ImageUrl.class);
    }


    /**
     * 查询图片信息
     */
    public List<String> getImageUrl(String imageId) {
        if (StringUtils.isBlank(imageId)) {
            return  null;
        }
        Query query = new Query(Criteria.where("imageId").is(imageId));
        ImageUrl imageUrl=mongoTemplate.findOne(query, ImageUrl.class, "imageUrl");
        if(imageUrl==null){
            return  null;
        }
        List<String> url = imageUrl.getUrl();
        if(CommonUtils.objectIsNotEmpty(url)&&url.size()==1){
            url.add(url.get(0));
            url.add(url.get(0));
        }
        return url;
    }

    /**
     * 根据产品编号查询产品信息
     *
     * @param code
     * @return
     */
    public ExtShopProductInfoDTO getScanShopProductInfo(String code) {
        //先查询本地
        if (StringUtils.isBlank(code)) {
            logger.info("根据产品编号查询产品信息传入参数={}", "code = [" + code + "]");
            return null;
        }
        Query query = new Query(Criteria.where("code").is(code)).addCriteria(Criteria.where("showapi_res_code").is(CommonCodeEnum.SUCCESS.getCode()));
        ExtShopProductInfoDTO localDTO = mongoTemplate.findOne(query, ExtShopProductInfoDTO.class, "extShopProductInfoDTO");
        logger.error("根据产品编号查询本地产品信息={},查询结果={}", "code = [" + code + "]", localDTO);
        //本地查不到查远程,或者查询远程不成功
        if (null == localDTO) {
            String infoFromAli = getProductInfoFromAli(code);
            try {
                ExtShopProductInfoDTO extShopProductInfoDTO = (ExtShopProductInfoDTO) JacksonUtil.jsonToBean(infoFromAli, ExtShopProductInfoDTO.class);
                extShopProductInfoDTO.setCode(code);
                mongoTemplate.save(extShopProductInfoDTO, "extShopProductInfoDTO");
                return extShopProductInfoDTO;
            } catch (JsonParseException e) {
                logger.error("根据产品编号查询产品信息json转换异常，异常信息" + e.getMessage(), e);
            }
        }
        return localDTO;
    }

    /**
     * 根据商品条形码获取商品信息
     */
    public String getProductInfoFromAli(String code) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("根据商品条形码获取商品信息传入参数={}", "code = [" + code + "]");

        String host = "https://ali-barcode.showapi.com";
        String path = "/barcode";
        String method = "GET";
        String appcode = "d44aff14ca8142efb196889d29c2896d";
        String productInfo = "";
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>(1);
        querys.put("code", code);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            productInfo = EntityUtils.toString(response.getEntity());
            logger.info("远程查询阿里云产品信息，code={},结果为={}", code, productInfo);
        } catch (Exception e) {
            logger.error("远程查询阿里云产品信息,查询异常，异常信息为" + e.getMessage(), e);
        }
        logger.info("根据商品条形码获取商品信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return productInfo;
    }
}
