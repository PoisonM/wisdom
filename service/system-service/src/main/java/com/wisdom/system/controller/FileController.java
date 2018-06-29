/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.system.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.*;
import com.wisdom.system.interceptor.LoginRequired;
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
        ResponseDTO response = new ResponseDTO();
        String path=fileName;
        File newFile=new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        try {
            file.transferTo(newFile);
            OSSObjectTool.uploadFileInputStream(path,newFile.length(),new FileInputStream(newFile),OSSObjectTool.BUCKET_MX_PIC);
            response.setErrorInfo("上传成功");
            response.setResult(StatusConstant.SUCCESS);
            response.setResult(path);
        } catch (IOException e) {
            e.printStackTrace();
            response.setErrorInfo("上传失败");
            response.setResult(StatusConstant.FAILURE);
        }
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
        ResponseDTO responseDTO = new ResponseDTO<>();
        try{
            List<String> urlList = CommonUtils.imageUploadToOSS(listFile,folder);
            responseDTO.setResponseData(urlList);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

    /**
     * 图片上传
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/imageBase64UploadToOSS", method = {RequestMethod.POST, RequestMethod.GET})
    public
    @ResponseBody
    ResponseDTO imageBase64UploadToOSS(@RequestBody(required = false) String imageStr) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO<>();
        if(StringUtils.isBlank(imageStr)){
            responseDTO.setResult(StatusConstant.FAILURE);
            responseDTO.setErrorInfo("读取失败，重新上传！");
            return responseDTO;
        }

        InputStream inputStream = null;
        try {
            inputStream = Base64Utils.getInputStream(imageStr);
            try {
                String fileName = DateUtils.DateToStr(new Date(), "dateMillisecond");
                MultipartFile multipartFile = new MockMultipartFile(DateUtils.DateToStr(new Date(), "dateMillisecond"), inputStream);
                String url = OSSObjectTool.imageUploadToOSS(multipartFile, "mx-beauty", fileName, ".png");
                responseDTO.setResponseData(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseDTO.setResult(StatusConstant.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setResult(StatusConstant.FAILURE);
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
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
        ResponseDTO responseDTO = new ResponseDTO<>();

        try{
            String url = CommonUtils.aviUploadToSSO(listFile);
            responseDTO.setResult(url);
            responseDTO.setErrorInfo(StatusConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            responseDTO.setErrorInfo(StatusConstant.FAILURE);
        }
        return responseDTO;
    }

}
