/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取两个日期之间差值（单位，毫秒）
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceMillisecondOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime);
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	public static String getDay(Date date) {
		return formatDate(date, "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	public static String getWeek(Date date) {
		return formatDate(date, "E");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }


	/**
	 * 毫秒转换成日期
	 * @param timeMillis
	 * @return
	 */
	public static String timeMillisToDate(long timeMillis){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		return formatter.format(calendar.getTime());
	}



		/**
         * 获取两个日期之间的天数
         *
         * @param before
         * @param after
         * @return
         */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 获取两个日期之间的分钟数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getMinuteOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 );
	}
	

	/**
	 * 获取周几
	 */
	public static String getDayWeek(int week) {
		if (week == 1) {
			return "周一";
		} else if (week == 2) {
			return "周二";
		} else if (week == 3) {
			return "周三";
		} else if (week == 4) {
			return "周四";
		} else if (week == 5) {
			return "周五";
		} else if (week == 6) {
			return "周六";
		} else if (week == 7) {
			return "周日";
		} else {
			return "";
		}
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 日期转换成字符串
	 *@author zdl
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}


	/**
	 * @author zdl
	 * 格式化日期
	 * @param params
	 * @return
	 */
	public static Date formatDate(@RequestBody Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = sdf.parse((String) params.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}


		return date;
	}


		/**
         * @author zdl
         * String to date
         * @param StringDate
         * @return
         */
	public static Date formatDateString(String StringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(StringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @author zdl
	 * Date to Date
	 */
	public static Date formatDateToDate(Date date, Object... pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd hh:mm:ss");
		}
		try {
			date = sdf.parse(formatDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
	public static Date StrToDate(String str,String flag) {
		SimpleDateFormat format = null;
		if("time".equals(flag)){
			format = new SimpleDateFormat("HH:mm");
		}else if("date".equals(flag)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
		}else if("datetime".equals(flag)){
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else if("xiangang".equals(flag)){
			format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}else if("datetimesec".equals(flag)){
			format = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if ("dateMillisecond".equals(flag)) {
			format = new SimpleDateFormat("yyyyMMddHHmmss");
		}else{
			format = new SimpleDateFormat("yyyyMMddHHmmssS");
		}
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
    
    /**
     * 日期转换成字符串
     *
     * @return date
     */
    public static String DateToStr(Date date,String flag) {
    	SimpleDateFormat format = null;
    	if("time".equals(flag)){
    		format = new SimpleDateFormat("HH:mm");
    	}else if("date".equals(flag)) {
    		format = new SimpleDateFormat("yyyy-MM-dd");
    	}else if("datetime".equals(flag)){
    		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}else if("datetimesec".equals(flag)){
			format = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if ("dateMillisecond".equals(flag)) {
			format = new SimpleDateFormat("yyyyMMddHHmmssS");
		}
        String dateStr = null;
    	dateStr = format.format(date);
        return dateStr;
    }

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String formatDateToStr(Date date,String formatStr) {
		SimpleDateFormat format = null;
		format = new SimpleDateFormat(formatStr);
		String dateStr = null;
		dateStr = format.format(date);
		return dateStr;
	}

	/**
	 * 判断两个时间之间相差的天数 张博
	 * @param date1
	 * @param date2
	 * @return
	 *
	 */
	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}

	/**
	 * 获取某一天开始时间
	 */
	public static String getDateStartTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println("开始时间：" + calendar.getTime());
        return DateToStr(calendar.getTime(), "datetimesec");
	}

	/**
	 * 获取某一天结束时间
	 */
	public static String getDateEndTime(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		System.out.println("结束时间：" + calendar.getTime());
        return DateToStr(calendar.getTime(), "datetimesec");
	}

	/**
	 * date自增一天
	 *
	 * @param date
	 * @return
	 */
	public static Date dateInc(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	public static int compareDate(Date date1, Date date2)
	{
		if (date1.after(date2)){
			return 1;
		}else{
			return 0;
		}
	}
}
