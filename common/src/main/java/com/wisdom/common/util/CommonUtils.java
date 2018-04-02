/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class CommonUtils {

	/**
	 * 获取向后一天的日期
	 * @return
	 */
	public static String getEndDate(String endTime){
		if( endTime == null || "".equals(endTime)){
			endTime = DateUtils.getDate();//设定当前时间
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = DateUtils.StrToDate(endTime,"date");//string转Date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,+1);//当前日期加一
		String endData = dateFormat.format(calendar.getTime());//结束时间
		return endData;
	}

	/**
	 * 上传Excel到OSS
	 * @param in
	 * @return
	 */
	public static String orderExcelToOSS(ByteArrayInputStream in) {
		int i = in.available();
		Long length = (long) i;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateFormat.format(new Date());
		String random = RandomNumberUtil.getFourRandom();
		String bucketName = "mxexcel";
		String folder = "orderExcel/";
		String fileName = folder + time + random + ".xls";
		//上传OSS
		String url = OSSObjectTool.uploadFileInputStream(fileName, length, in, bucketName);
		return url;
	}

	/**
	 * 上传图片
	 * @param listFile 图片
	 * @param folder 对应OSS上的文件夹
	 * @return
	 */
	public static List<String> imageUploadToOSS(MultipartFile[] listFile, String folder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		List<String> urlList = new ArrayList<>();
		String bucketName = "mximage";
		if(listFile.length >0) {
			for (MultipartFile file : listFile) {
				String time = dateFormat.format(new Date());
				String random = RandomNumberUtil.getFourRandom();
				String originalFileName = file.getOriginalFilename();
				String fileName = time + random + originalFileName;

				try {
					String url = OSSObjectTool.imageUploadToOSS(file, bucketName, folder, fileName);
					urlList.add(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return urlList;
	}

	/**
	 * 上传视频
	 * @param listFile
	 * @return
	 */
	public static String aviUploadToSSO(MultipartFile[] listFile) {
		String bucketName = "mxavi";
		String folder = "jmcpavi/";
		if(listFile.length >0) {
			for (MultipartFile file : listFile) {
				String originalFileName = file.getOriginalFilename();
				String fileName = originalFileName;

				try {
					String url = OSSObjectTool.imageUploadToOSS(file, bucketName, folder, fileName);
					return url;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 用户名解码
	 * @param name
	 * @return
	 */
	public static String nameDecoder(String name){
		if(name != null && name !=""){
			try {
				return URLDecoder.decode(name,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
