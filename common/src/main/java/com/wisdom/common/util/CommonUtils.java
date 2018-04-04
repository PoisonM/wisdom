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


	/**
	 * 根据开始时间、结束时间返回数组编号
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getArrayNo(String startDate,String endDate){

		String[][] dateArray = {{"0","00:00"},{"1","00:30"},{"2","01:00"},{"3","01:30"},{"4","02:00"},{"5","02:30"}
		,{"6","03:00"},{"7","03:30"},{"8","04:00"},{"9","04:30"},{"10","05:00"},{"11","05:30"},{"12","06:00"},{"13","06:30"},
				{"14","07:00"},{"15","07:30"},{"16","08:00"},{"17","08:30"},{"18","09:00"},{"19","09:30"},{"20","10:00"},
				{"21","10:30"},{"22","11:00"},{"23","11:30"},{"24","12:00"},{"25","12:30"},{"26","13:00"}
				,{"27","13:30"},{"28","14:00"},{"29","14:30"},{"30","15:00"},{"31","15:30"},{"32","16:00"},{"33","16:30"},{"34","17:00"},
				{"35","17:30"},{"36","18:00"},{"37","18:30"},{"38","19:00"},{"39","19:30"},{"40","20:00"},{"41","20:30"},
				{"42","21:00"},{"43","21:30"},{"44","22:00"},{"45","22:30"},{"46","23:00"},{"47","23:30"}};

		//获取开始时间、结束时间的数据编号
		String startNo = "";
		String endNo = "";
		for (int i = 0; i < dateArray.length; i++) {
			if (startDate.equals(dateArray[i][1])){
				startNo = dateArray[i][0];
			}
			if(endDate.equals(dateArray[i][1])){
				endNo = dateArray[i][0];
			}
			if(StringUtils.isNotBlank(startNo) && StringUtils.isNotBlank(endNo)){
				break;
			}
		}

		//根据开始时间编号计算结束时间编号
		StringBuffer responseStr = new StringBuffer("{");
		for (int i = Integer.parseInt(startNo); i < Integer.parseInt(endNo) ; i++) {
			responseStr.append("\"");
			responseStr.append(i);
			responseStr.append("\",");
		}

		responseStr.deleteCharAt(responseStr.length() - 1);
		responseStr.append("}");
		return responseStr.toString();
	}

	public static void main(String[] args) {
		System.out.println(getArrayNo("01:00","10:00") );
	}
}
