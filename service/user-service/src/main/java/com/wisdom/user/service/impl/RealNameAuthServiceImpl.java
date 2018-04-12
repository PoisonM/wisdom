package com.wisdom.user.service.impl;


import com.fasterxml.jackson.core.JsonParseException;
import com.wisdom.common.constant.RealNameResultEnum;
import com.wisdom.common.dto.user.RealNameAuthHelperDTO;
import com.wisdom.common.dto.user.RealNameInfoDTO;
import com.wisdom.common.util.HttpUtils;
import com.wisdom.common.util.JacksonUtil;
import com.wisdom.user.service.RealNameAuthService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("realNameAuthService")
public class RealNameAuthServiceImpl implements RealNameAuthService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 用户实名认证接口
     *
     * @param idCard
     * @param name
     * @return
     */
    @Override
    public RealNameInfoDTO getRealNameInfoDTO(String idCard, String name) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("用户实名认证接口传入参数={}", "idCard = [" + idCard + "], name = [" + name + "]");

        //根据身份证号到mongo查询RealNameInfoDTO对象
        Query query = new Query().addCriteria(Criteria.where("idCard").is(idCard)).addCriteria(Criteria.where("name").is(name));

        RealNameInfoDTO realNameInfoDTO = mongoTemplate.findOne(query, RealNameInfoDTO.class, "realNameInfoDTO");

        logger.debug("用户实名认证接口mongo中,查询结果 {}", realNameInfoDTO);
        if (null != realNameInfoDTO) {
            return realNameInfoDTO;
        }

        //查远程
        String realNameInfo = getRealNameInfo(idCard, name);
        logger.debug("远程查询实名认证查询结果为空，传入参数为 {}", "idCard = [" + idCard + "], name = [" + name + "]");
        if (StringUtils.isBlank(realNameInfo)) {
            return getRealNameInfoDTO();
        }

        RealNameAuthHelperDTO jsonToBean = null;
        try {
            jsonToBean = (RealNameAuthHelperDTO) JacksonUtil.jsonToBean(realNameInfo, RealNameAuthHelperDTO.class);
        } catch (JsonParseException e) {
            logger.error("查询实名认证json转换异常，异常信息为，{}" + e.getMessage(), e);
            return getRealNameInfoDTO();
        }
        realNameInfoDTO = new RealNameInfoDTO();
        realNameInfoDTO.setAddress(jsonToBean.getData().getAddress());
        realNameInfoDTO.setBirthday(jsonToBean.getData().getBirthday());
        realNameInfoDTO.setSex(jsonToBean.getData().getSex());
        realNameInfoDTO.setCode(jsonToBean.getResp().getCode());
        realNameInfoDTO.setDesc(RealNameResultEnum.judgeValue(jsonToBean.getResp().getCode()).getDesc());
        realNameInfoDTO.setIdCard(idCard);
        realNameInfoDTO.setName(name);
        realNameInfoDTO.setResult(jsonToBean.getResp().getCode().equalsIgnoreCase(RealNameResultEnum.MATCHING.getCode()) ? "匹配" : "不匹配");
        //远程查回来的数据保存到mongo
        mongoTemplate.save(realNameInfoDTO, "realNameInfoDTO");
        logger.info("用户实名认证接口耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return realNameInfoDTO;
    }

    /**
     * 远程查询实名认证信息
     *
     * @param idCard
     * @param name
     * @return
     */
    public String getRealNameInfo(String idCard, String name) {
        String host = "http://idcard.market.alicloudapi.com";
        String path = "/lianzhuo/idcard";
        String method = "GET";
        String appcode = "d44aff14ca8142efb196889d29c2896d";
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardno", idCard);
        querys.put("name", name);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String string = EntityUtils.toString(response.getEntity());
            logger.debug("实名认证远程查询结果为， {}", string);
            return string;
        } catch (Exception e) {
            logger.error("实名认证远程查询失败，异常信息为，{}" + e.getMessage(), e);
            return "";
        }
    }

    private RealNameInfoDTO getRealNameInfoDTO() {
        RealNameInfoDTO realNameInfoDTO;
        realNameInfoDTO = new RealNameInfoDTO();
        realNameInfoDTO.setCode(RealNameResultEnum.NO_RESULT.getCode());
        realNameInfoDTO.setDesc(RealNameResultEnum.NO_RESULT.getDesc());
        return realNameInfoDTO;
    }
}
