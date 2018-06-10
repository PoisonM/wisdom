package com.wisdom.beauty.controller.mine;

import com.wisdom.beauty.api.dto.ShopUserRelationDTO;
import com.wisdom.beauty.api.dto.SysShopDTO;
import com.wisdom.beauty.api.extDto.ExtShopBossDTO;
import com.wisdom.beauty.api.extDto.ExtSysClerkDTO;
import com.wisdom.beauty.api.extDto.ShopUserLoginDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.beauty.api.responseDto.UserProductRelationResponseDTO;
import com.wisdom.beauty.client.UserServiceClient;
import com.wisdom.beauty.client.WeixinServiceClient;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopCustomerProductRelationService;
import com.wisdom.beauty.core.service.ShopService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.ShopUserRelationService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysBossDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MineController
 *
 * @Author： huan
 * @Description: 我的相关控制层
 * @Date:Created in 2018/4/17 14:19
 * @since JDK 1.8
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "mine")
public class MineController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUerConsumeRecordService shopUerConsumeRecordService;

    @Autowired
    private ShopCustomerProductRelationService shopCustomerProductRelationService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WeixinServiceClient weixinServiceClient;

    @Resource
    private ShopUserRelationService shopUserRelationService;

    @Resource
    private UserServiceClient userServiceClient;

    @Value("${test.msg}")
    private String msg;

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 今日收银记录 划卡记录对应后台的消费---状态是1,此时不需要goodType
     * 充值记录，消费记录对应后台的充值---状态是0，并且充值记录的goodType--2
     * @Date:2018/4/17 14:45
     */
    @RequestMapping(value = "/consumes", method = RequestMethod.POST)
    @ResponseBody
    ResponseDTO<List<UserConsumeRecordResponseDTO>> findMineConsume(
            @RequestBody UserConsumeRequestDTO userConsumeRequest) {

        String sysShopId = redisUtils.getShopId();
        PageParamVoDTO<UserConsumeRequestDTO> pageParamVoDTO = new PageParamVoDTO<>();
        userConsumeRequest.setSysShopId(sysShopId);
        userConsumeRequest.setGoodsTypeRequire(true);
        pageParamVoDTO.setRequestData(userConsumeRequest);
        pageParamVoDTO.setPaging(true);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(userConsumeRequest.getPageSize());

        // 设置当天的开始时间和结束时间
        pageParamVoDTO.setStartTime(DateUtils.getStartTime());
        pageParamVoDTO.setEndTime(DateUtils.getEndTime());
        List<UserConsumeRecordResponseDTO> userConsumeRecordResponseDTO = shopUerConsumeRecordService
                .getShopCustomerConsumeRecordList(pageParamVoDTO);

        ResponseDTO<List<UserConsumeRecordResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(userConsumeRecordResponseDTO);
        return responseDTO;
    }

    @RequestMapping(value = "/getProductRecord", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, Object>> getProductRecord(@RequestParam(required = false) String searchFile) {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        Map<String, Object> map = shopCustomerProductRelationService
                .getShopUserProductRelations(sysClerkDTO.getSysShopId(), searchFile);
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(map);
        return responseDTO;
    }

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/6/6 19:27
     */
    @RequestMapping(value = "/getWaitReceivePeopleAndNumber", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, Object>> getWaitReceivePeopleAndNumber() {
        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        Map<String, Object> map = shopCustomerProductRelationService
                .getWaitReceivePeopleAndNumber(sysClerkDTO.getSysShopId());
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(map);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 根据用户查询该用户待领取产品结果
     * @Date:2018/4/19 9:46
     */
    @RequestMapping(value = "/getWaitReceiveDetail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<UserProductRelationResponseDTO>> getProductRecordDetail(@RequestParam String sysUserId) {

        SysClerkDTO sysClerkDTO = UserUtils.getClerkInfo();
        List<UserProductRelationResponseDTO> list = shopCustomerProductRelationService
                .getShopUserProductRelationList(sysUserId, sysClerkDTO.getSysShopId());
        ResponseDTO<List<UserProductRelationResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(list);
        return responseDTO;
    }

    /**
     * @Param:
     * @Return:
     * @Description: 查询用户的店铺信息
     * @Date:2018/4/19 9:46
     */
    @RequestMapping(value = "/getUserClientInfo", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> getUserClientInfo() {

        UserInfoDTO userInfo = UserUtils.getUserInfo();

        HashMap<Object, Object> responseMap = new HashMap<>(2);
        ShopUserLoginDTO userLoginShop = redisUtils.getUserLoginShop(userInfo.getId());
        responseMap.put("currentShop", userLoginShop);

        ShopUserRelationDTO shopUserRelationDTO = new ShopUserRelationDTO();
        shopUserRelationDTO.setSysUserId(userInfo.getId());
        List<ShopUserRelationDTO> shopListByCondition = shopUserRelationService
                .getShopListByCondition(shopUserRelationDTO);
        if (CommonUtils.objectIsNotEmpty(shopListByCondition) && shopListByCondition.size() > 1) {
            Iterator it = shopListByCondition.iterator();
            while (it.hasNext()) {
                ShopUserRelationDTO next = (ShopUserRelationDTO) it.next();
                if (userLoginShop.getSysShopId().equals(next.getSysShopId())) {
                    it.remove();
                }
            }
            responseMap.put("otherShop", shopListByCondition);
        }

        ResponseDTO<Object> responseDTO = new ResponseDTO<Object>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(responseMap);
        return responseDTO;
    }

    /**
     * 切换店铺
     *
     * @param sysShopId
     * @return
     */
    @RequestMapping(value = "/changeUserShop", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Object> changeUserShop(@RequestParam String sysShopId) {

        UserInfoDTO userInfo = UserUtils.getUserInfo();
        redisUtils.updateUserLoginShop(userInfo.getId(), sysShopId);

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 获取我的二维码
     */
    @RequestMapping(value = "/getUserQrCode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<Object> getUserQrCode() {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        UserInfoDTO userInfo = UserUtils.getUserInfo();
        if (null != userInfo) {
            String temporaryQrCode = weixinServiceClient.getTemporaryQrCode(userInfo.getMobile());
            logger.info("调用微信服务获取到的二维码为,{}", temporaryQrCode);
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setResponseData(temporaryQrCode);
        } else {
            logger.error("获取我的二维码userInfo为空2");
            String temporaryQrCode = "https://mxavi.oss-cn-beijing.aliyuncs.com/jmcpavi/%E4%BA%8C%E7%BB%B4%E7%A0%81.png";
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setResponseData(temporaryQrCode);
        }

        return responseDTO;
    }

    /**
     * 获取我的个人信息
     */
    @RequestMapping(value = "/getCurrentLoginUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<Object> getCurrentLoginUserInfo() {

        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        SysBossDTO bossInfo = UserUtils.getBossInfo();
        if (null != bossInfo) {
            logger.info("老板端获取我的个人信息");
            ExtShopBossDTO extShopBossDTO = new ExtShopBossDTO();
            BeanUtils.copyProperties(bossInfo, extShopBossDTO);
            // 查询当前店铺名称
            SysShopDTO beauty = shopService.getShopInfoByPrimaryKey(bossInfo.getParentShopId());
            // 查询当前美容院名称
            SysShopDTO shop = shopService.getShopInfoByPrimaryKey(bossInfo.getCurrentShopId());
            extShopBossDTO.setCurrentBeautyShopName(null != beauty ? beauty.getName() : "");
            extShopBossDTO.setCurrentShopName(null != shop ? shop.getName() : "");
            responseDTO.setResponseData(extShopBossDTO);
            return responseDTO;
        }
        if (null != UserUtils.getUserInfo()) {
            logger.info("用户端获取我的个人信息");
            ShopUserLoginDTO userLoginShop = redisUtils.getUserLoginShop(UserUtils.getUserInfo().getId());
            responseDTO.setResponseData(userLoginShop);
            return responseDTO;
        }
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        if (null != clerkInfo) {
            ExtSysClerkDTO extSysClerkDTO = new ExtSysClerkDTO();
            BeanUtils.copyProperties(clerkInfo,extSysClerkDTO);
            //查询美容店
            SysShopDTO beauty = shopService.getShopInfoByPrimaryKey(extSysClerkDTO.getSysShopId());
            extSysClerkDTO.setSysShopName(beauty.getName());
            //查询美容院
            beauty = shopService.getShopInfoByPrimaryKey(beauty.getParentsId());
            extSysClerkDTO.setCurrentBeautyShopName(null != beauty ? beauty.getName() : "");
            responseDTO.setResponseData(clerkInfo);
            return responseDTO;
        }
        responseDTO.setResult(StatusConstant.FAILURE);
        return responseDTO;
    }

    /**
     * 修改老板个人信息
     */
    @RequestMapping(value = "/updateBossInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDTO<Object> updateBossInfo(@RequestBody ExtShopBossDTO extShopBossDTO) {
        ResponseDTO<Object> responseDTO = new ResponseDTO<>();
        userServiceClient.updateBossInfo(extShopBossDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

}
