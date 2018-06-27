/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.system.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.Base64Utils;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.OSSObjectTool;
import com.wisdom.system.interceptor.LoginRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 工具 Controller
 *
 * @author ThinkGem
 * @version 2013-10-17
 */
@Controller
@RequestMapping(value = "file")
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);

    /**
     * 上传文件
     * @param file
     * @param
     * @return {"status","success"}
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="/uploadMediaFile",method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public ResponseDTO UploadFile(@RequestParam("file") MultipartFile file, String fileName) throws UnsupportedEncodingException {
        long startTime = System.currentTimeMillis();
        logger.info("上传文件uploadMediaFile ==={}开始" , startTime);
        ResponseDTO response = new ResponseDTO();
        String path = fileName;
        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        try {
            file.transferTo(newFile);
            OSSObjectTool.uploadFileInputStream(path,newFile.length(),new FileInputStream(newFile),OSSObjectTool.BUCKET_MX_PIC);
            response.setErrorInfo("上传成功");
            response.setResult(StatusConstant.SUCCESS);
            response.setResult(path);
        } catch (IOException e) {
            logger.error("上传文件异常,异常信息为" +e.getMessage(),e);
            e.printStackTrace();
            response.setErrorInfo("上传失败");
            response.setResult(StatusConstant.FAILURE);
        }
        logger.info("上传文件uploadMediaFile,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return response;
    }

    /**
     * 图片上传
     * @param
     * @return
     */
    @RequestMapping(value = "imageUploadToOSS", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO imageUploadToOSS(@RequestParam MultipartFile[] listFile, @RequestParam String folder) {
        long startTime = System.currentTimeMillis();
        logger.info("图片上传imageUploadToOSS ==={}开始" , startTime);

        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            List<String> urlList = CommonUtils.imageUploadToOSS(listFile,folder);
            responseDTO.setResponseData(urlList);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        }catch (Exception e){
            logger.error("图片上传imageUploadToOSS,异常信息为" +e.getMessage(),e);
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
        }
        logger.info("图片上传imageUploadToOSS,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 图片上传
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/imageBase64UploadToOSS", method = {RequestMethod.POST, RequestMethod.GET})
//    @LoginRequired
    public
    @ResponseBody
    ResponseDTO imageBase64UploadToOSS(@RequestBody String imageStr) throws IOException {
        long startTime = System.currentTimeMillis();
        logger.info("图片上传imageBase64UploadToOSS ==={}开始" , startTime);

        ResponseDTO responseDTO = new ResponseDTO<>();
        InputStream inputStream = null;
        try {
            inputStream = Base64Utils.getInputStream(imageStr);
            try {
                String fileName = DateUtils.DateToStr(new Date(), "dateMillisecond");
                MultipartFile multipartFile = new MockMultipartFile(DateUtils.DateToStr(new Date(), "dateMillisecond"), inputStream);
                String url = OSSObjectTool.imageUploadToOSS(multipartFile, "mx-beauty", fileName, ".png");
                responseDTO.setResponseData(url);
            } catch (Exception e) {
                logger.info("图片上传imageBase64UploadToOSS异常,异常信息为{}"+e.getMessage(),e);
                e.printStackTrace();
            }
            responseDTO.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            logger.info("图片上传imageBase64UploadToOSS异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
            responseDTO.setResult(StatusConstant.FAILURE);
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        logger.info("图片上传imageBase64UploadToOSS,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;
    }

    /**
     * 上传视频
     * @param
     * @return
     */
    @RequestMapping(value = "aviUploadToOSS", method = {RequestMethod.POST, RequestMethod.GET})
    @LoginRequired
    public
    @ResponseBody
    ResponseDTO aviUploadToOSS(@RequestParam MultipartFile[] listFile) {
        long startTime = System.currentTimeMillis();
        logger.info("上传视频aviUploadToOSS ==={}开始" , startTime);
        ResponseDTO responseDTO = new ResponseDTO<>();

        try{
            String url = CommonUtils.aviUploadToSSO(listFile);
            logger.info("上传视频Url={}aviUploadToOSS" , url);
            responseDTO.setResult(url);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        }catch (Exception e){
            logger.error("上传视频aviUploadToOSS 异常,异常信息为{}"+e.getMessage(),e);
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
        }
        logger.info("上传视频aviUploadToOSS,耗时{}毫秒", (System.currentTimeMillis() - startTime));
        return responseDTO;

    }
}
