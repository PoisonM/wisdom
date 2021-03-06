package com.wisdom.beauty.controller.shop;

import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.api.extDto.ExtSysShopDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.CommonCodeEnum;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.JedisUtils;
import com.wisdom.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
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
    private RedisUtils redisUtils;
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

        ResponseDTO<Object> responseDTO = new ResponseDTO();
        sysShopId = redisUtils.getShopId();
        SysShopDTO shopInfoByPrimaryKey = shopService.getShopInfoByPrimaryKey(sysShopId);
        responseDTO.setResponseData(shopInfoByPrimaryKey);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 查询某个用户是否扫码绑定
     */
    @RequestMapping(value = "/getUserScanInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getUserScanInfo(@RequestParam String sysUserId, @RequestParam String shopId) {
        logger.info("查询某个用户是否扫码绑定传入参数={}", "sysUserId = [" + sysUserId + "]");
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        String string = new StringBuffer(shopId).append("_").append(sysUserId).toString();
        Object object = JedisUtils.getStringObject(string);
        responseDTO.setResponseData(object);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 查询某个老板的美容院
     */
    @RequestMapping(value = "/getBossShopInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getBossShopInfo() {
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        ExtSysShopDTO extSysShopDTO = new ExtSysShopDTO();
        extSysShopDTO.setSysBossCode(bossInfo.getSysBossCode());
        extSysShopDTO.setType(CommonCodeEnum.SUCCESS.getCode());
        List<ExtSysShopDTO> bossShopInfo = shopUserRelationService.getBossShopInfo(extSysShopDTO);
        responseDTO.setResponseData(CommonUtils.objectIsEmpty(bossShopInfo) ? new ExtSysShopDTO() : bossShopInfo.get(0));
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 查询某个老板的店面
     */
    @RequestMapping(value = "/getBossAllShopList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getBossAllShopList(@RequestParam(required = false) String type){
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        ResponseDTO<Object> responseDTO = new ResponseDTO();
        ExtSysShopDTO extSysShopDTO = new ExtSysShopDTO();
        extSysShopDTO.setSysBossCode(bossInfo.getSysBossCode());
        if(StringUtils.isNotBlank(type)){
            extSysShopDTO.setType(type);
        }
        List<ExtSysShopDTO> bossShopInfo = shopUserRelationService.getBossShopInfo(extSysShopDTO);
        if (CollectionUtils.isNotEmpty(bossShopInfo)) {
            logger.info("老板{}切换店铺", bossInfo.getId());
            bossInfo.setCurrentShopId(bossShopInfo.get(0).getId());
            UserUtils.bossSwitchShops(bossInfo);
        }
        responseDTO.setResponseData(bossShopInfo);
        responseDTO.setResult(StatusConstant.SUCCESS);
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
        return responseDTO;
    }

    /**
     * 修改门店
     */
    @RequestMapping(value = "/updateShopInfo", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<Object> updateShopInfo(@RequestBody ExtSysShopDTO extSysShopDTO) {
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
        return responseDTO;
    }
}
