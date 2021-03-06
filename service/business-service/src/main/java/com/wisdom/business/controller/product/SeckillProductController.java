package com.wisdom.business.controller.product;

import com.wisdom.business.service.product.SeckillProductService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.product.SeckillActivityDTO;
import com.wisdom.common.dto.product.SeckillActivityFieldDTO;
import com.wisdom.common.dto.product.SeckillProductDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 微商城秒杀控制端
 * <p>
 * Created by wangbaowei on 2018/7/25.
 */

@Controller
@RequestMapping(value = "seckillProduct")
public class SeckillProductController {

    Logger logger = LoggerFactory.getLogger(SeckillProductController.class);

    @Autowired
    private SeckillProductService seckillProductService;


    /**
     * 获取秒杀商品活动列表
     */
    @RequestMapping(value = "getSeckillProductList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<PageParamVoDTO> getSeckillProductList(@RequestBody PageParamVoDTO<SeckillProductDTO> pageParamVoDTO) {
        long startTime = System.currentTimeMillis();
        logger.info("获取某个商品的基本信息==={}开始", startTime);
        ResponseDTO<PageParamVoDTO> responseDTO = new ResponseDTO<>();
        PageParamVoDTO<List<SeckillProductDTO>> page = seckillProductService.queryAllProducts(pageParamVoDTO);
        responseDTO.setResponseData(page);
        logger.info("获取某个商品的基本信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 获取秒杀商品活动列表
     */
    @RequestMapping(value = "getseckillProductDetailById", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    SeckillProductDTO getseckillProductDetailById(@RequestParam String activtyId) {
        return seckillProductService.getseckillProductDetailById(activtyId);
    }

    /**
     * 获取活动列表
     */
    @RequestMapping(value = "findSeckillActivityList", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<PageParamVoDTO<List<SeckillActivityDTO>>> findSeckillActivityList(@RequestBody PageParamVoDTO<SeckillActivityDTO> pageParamVoDTO) {
        long startTime = System.currentTimeMillis();
        ResponseDTO<PageParamVoDTO<List<SeckillActivityDTO>>> responseDTO = new ResponseDTO<>();
        SeckillActivityDTO seckillActivityDTO = new SeckillActivityDTO();
        seckillActivityDTO = pageParamVoDTO.getRequestData();
        int pageNo = (pageParamVoDTO.getPageNo()-1)*pageParamVoDTO.getPageSize();
        int pageSize = pageParamVoDTO.getPageSize();
        seckillActivityDTO.setPageNo(pageNo);
        seckillActivityDTO.setPageSize(pageSize);
        logger.info("获取某个商品的基本信息==={}开始", startTime);
        PageParamVoDTO<List<SeckillActivityDTO>> page = seckillProductService.findSeckillActivitylist(seckillActivityDTO);
        logger.info("获取某个商品的基本信息,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        page.setPageNo(pageParamVoDTO.getPageNo());
        responseDTO.setResponseData(page);
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }

    /**
     * 修改活动状态
     */
    @RequestMapping(value = "changeSecKillActivityStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> changeSecKillActivityStatus(@RequestParam Integer id, @RequestParam Integer status) {

        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (status != null) {
            if (status == 1) {
                status = 0;
            } else {
                status = 1;
            }
            long startTime = System.currentTimeMillis();
            logger.info("更新秒杀活动状态==={}开始", startTime);
            String result = seckillProductService.changeSeckillActivityStatus(id, status);
            logger.info("更新秒杀活动状态,耗时{}毫秒", (System.currentTimeMillis() - startTime));
            responseDTO.setResponseData(result);
            responseDTO.setResult(StatusConstant.SUCCESS);
        } else {
            responseDTO.setResult(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 新增秒杀活动
     */
    @RequestMapping(value = "addSecKillActivity", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<String> addSecKillActivity(@RequestBody SeckillActivityDTO seckillActivityDTO) {

        //获取当前登录用户信息
        UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
        seckillActivityDTO.setCreateBy(userInfoDTO.getId());
        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfA = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        try {
            if(seckillActivityDTO.getEndTime().getTime()<seckillActivityDTO.getStartTime().getTime()){
                responseDTO.setErrorInfo("活动结束日期不能小于开始日期！");
                responseDTO.setResult(StatusConstant.FAILURE);
                return responseDTO;
            }
            for (SeckillActivityFieldDTO seckillActivityField : seckillActivityDTO.getSessionList()) {
                if (sdf.parse(seckillActivityField.getEndTimeString()).getTime()<sdf.parse(seckillActivityField.getStartTimeString()).getTime()) {
                    responseDTO.setErrorInfo("活动场次结束日期不能小于开始日期！");
                    responseDTO.setResult(StatusConstant.FAILURE);
                    return responseDTO;
                }
            }

        }catch (Exception e){
            logger.info(e.getMessage());
            responseDTO.setErrorInfo("新增异常！");
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }

        long startTime = System.currentTimeMillis();
        logger.info("新增秒杀活动状态==={}开始", startTime);
        try {
            String result = seckillProductService.addSeckillActivity(seckillActivityDTO);
            responseDTO.setResponseData(result);
        } catch (java.lang.Exception e) {
            logger.info(e.getMessage());
            responseDTO.setResponseData("failure");
        }
        logger.info("新增秒杀活动状态,耗时{}毫秒", (System.currentTimeMillis() - startTime));


        responseDTO.setResult(StatusConstant.SUCCESS);

        return responseDTO;
    }

    /**
     * 查询秒杀活动详情
     */
    @RequestMapping(value = "getSecKillActivityDetail", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO<SeckillActivityDTO> getSecKillActivityDetail(@RequestParam Integer id) {

        ResponseDTO<SeckillActivityDTO> responseDTO = new ResponseDTO<>();

        long startTime = System.currentTimeMillis();
        logger.info("更新秒杀活动状态==={}开始", startTime);
        SeckillActivityDTO seckillActivityDTO = seckillProductService.getSecKillActivity(id);
        logger.info("更新秒杀活动状态,耗时{}毫秒", (System.currentTimeMillis() - startTime));

        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(seckillActivityDTO);

        return responseDTO;
    }
}
