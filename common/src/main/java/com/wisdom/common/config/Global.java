/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.common.config;

import com.google.common.collect.Maps;
import com.wisdom.common.util.StringUtils;
import java.util.Map;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {

	public static String[] resourcePaths = {"http://localhost:8888/conf/constant-config-dev.properties",
			"http://localhost:8888/conf/config-ali.properties"};

	public static String[] resourcePaths = {"http://localhost:8888/conf/constant-config-pro.properties",
			"http://localhost:8888/conf/config-ali.properties"};

//	public static String[] resourcePaths = {"http://172.19.41.70:8888/conf/constant-config-pro.properties",
//		"http://172.19.41.70:8888/conf/config-ali.properties"};

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader(resourcePaths);
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/////////////////////////////////////////////////////////
	
	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	// 对/错
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 页面获取常量
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = "../webapps/backend";
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}
	
}
