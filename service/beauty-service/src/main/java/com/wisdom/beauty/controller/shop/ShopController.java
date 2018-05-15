package com.wisdom.beauty.controller.shop;

import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.core.service.ShopService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: ArchivesController
 *
 * @Author： zhaodeliang
 * @Description: 店控制层
 * @Date:Created in 2018/4/4 9:26
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopUserRelationService shopUserRelationService;

    @Value("${test.msg}")
    private String msg;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询某个店的信息
     *
     * @param sysShopId
     * @return
     */
    @RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getShopInfo(@RequestParam(required = false) String sysShopId) {
        long currentTimeMillis = System.currentTimeMillis();

        ResponseDTO<Object> responseDTO = new ResponseDTO();
        logger.info("查询某个店的信息传入参数={}", "sysShopId = [" + sysShopId + "]");
        if (StringUtils.isBlank(sysShopId)) {
            SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
            sysShopId = clerkInfo.getSysShopId();
        }
        SysShopDTO shopInfoByPrimaryKey = shopService.getShopInfoByPrimaryKey(sysShopId);
        responseDTO.setResponseData(shopInfoByPrimaryKey);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询某个店的信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某个用户是否扫码绑定
     */
    @RequestMapping(value = "/getUserScanInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getUserScanInfo(@RequestParam String sysUserId, @RequestParam String shopId) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("查询某个用户是否扫码绑定传入参数={}", "sysUserId = [" + sysUserId + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        if (StringUtils.isNotBlank(shopId) && StringUtils.isNotBlank(sysUserId)) {
            String string = new StringBuffer(shopId).append("_").append(sysUserId).toString();
            Object object = JedisUtils.getObject(string);
            logger.info("redis中查询出来的数据为，{}",object);
            if (null != object) {
                String flag = (String) object;
                if (CommonCodeEnum.NOTBIND.getCode().equals(flag)) {
                    //更新用户与店的绑定关系
                    ShopUserRelationDTO userRelationDTO = new ShopUserRelationDTO();
                    userRelationDTO.setSysUserId(sysUserId);
                    userRelationDTO.setShopId(shopId);
                    userRelationDTO.setStatus(CommonCodeEnum.Y.getCode());
                    logger.info("保存用户的绑定关系");
                    int saveFlag = shopUserRelationService.saveUserShopRelation(userRelationDTO);
                    logger.info("更新用户与店的绑定关系更新结果为={}", saveFlag > 0 ? "成功" : "失败");
                    responseDTO.setResult(StatusConstant.SUCCESS);
                    JedisUtils.set(string, "alreadyBind", ConfigConstant.logintokenPeriod);
                    return responseDTO;
                } else if (CommonCodeEnum.ALREADYBIND.getCode().equals(flag)) {
                    logger.info("用户已经绑定");
                    responseDTO.setResult(CommonCodeEnum.ALREADYBIND.getCode());
                    return responseDTO;
                }
            }
        }
        responseDTO.setResult(StatusConstant.FAILURE);
        logger.info("查询某个用户是否扫码绑定耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}
