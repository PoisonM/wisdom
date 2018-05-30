package com.wisdom.beauty.controller.shop;

import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.api.enums.CommonCodeEnum;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.beauty.core.service.ShopService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ArchivesController
 *
 * @Author： zhaodeliang
 * @Description: 店控制层
 * @Date:Created in 2018/4/4 9:26
 * @since JDK 1.8
 */
@Controller
@LoginAnnotations
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
            Object object = JedisUtils.getStringObject(string);
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

    /**
     * 查询某个老板的美容院
     */
    @RequestMapping(value = "/getBossShopInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getBossShopInfo() {
        long currentTimeMillis = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        ExtSysShopDTO extSysShopDTO = new ExtSysShopDTO();
        extSysShopDTO.setSysBossCode(bossInfo.getId());
        extSysShopDTO.setType(CommonCodeEnum.SUCCESS.getCode());
        List<ExtSysShopDTO> bossShopInfo = shopUserRelationService.getBossShopInfo(extSysShopDTO);
        responseDTO.setResponseData(CommonUtils.objectIsEmpty(bossShopInfo) ? new ExtSysShopDTO() : bossShopInfo.get(0));
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询某个老板的美容院耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某个老板的店面
     */
    @RequestMapping(value = "/getBossAllShopList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getBossAllShopList() {
        long currentTimeMillis = System.currentTimeMillis();
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        ExtSysShopDTO extSysShopDTO = new ExtSysShopDTO();
        extSysShopDTO.setSysBossCode(bossInfo.getId());
        List<ExtSysShopDTO> bossShopInfo = shopUserRelationService.getBossShopInfo(extSysShopDTO);
        if (CommonUtils.objectIsEmpty(bossShopInfo)) {
            logger.info("老板{}切换店铺", bossInfo.getId());
            bossInfo.setCurrentShopId(bossShopInfo.get(0).getShopId());
            UserUtils.bossSwitchShops(bossInfo);
        }
        responseDTO.setResponseData(bossShopInfo);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询某个老板的美容院耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 老板切换店铺
     *
     * @param sysShopId 为店铺的id
     * @return
     */
    @RequestMapping(value = "/bossSwitchShops", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> bossSwitchShops(@RequestParam String sysShopId) {
        long currentTimeMillis = System.currentTimeMillis();
        ResponseDTO responseDTO = new ResponseDTO();
        if (StringUtils.isBlank(sysShopId)) {
            responseDTO.setResponseData("传入的sysShopId为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        bossInfo.setCurrentShopId(sysShopId);
        UserUtils.bossSwitchShops(bossInfo);

        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("查询某个老板的美容院耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 修改门店
     */
    @RequestMapping(value = "/updateShopInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateShopInfo(@RequestBody ExtSysShopDTO extSysShopDTO) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("修改门店传入参数={}", "sysShopDTO = [" + extSysShopDTO + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        if (StringUtils.isBlank(extSysShopDTO.getId())) {
            responseDTO.setResponseData("传入id为空");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        int info = shopUserRelationService.updateShopInfo(extSysShopDTO);
        responseDTO.setResponseData(info>0?"成功":"失败");
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("修改门店耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}
