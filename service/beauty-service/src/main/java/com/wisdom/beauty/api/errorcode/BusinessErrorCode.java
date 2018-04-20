package com.wisdom.beauty.api.errorcode;

public enum BusinessErrorCode  {

	/**
	 * 前3位固定为系统码：BEAUTY 第4,5位为子系统码：IF 第6位表示日志级别：W/E 第7位系统错误类型：B业务类型/S系统类型
	 * 最后三位：A公共错误码/B批量错误码/数字则为普通错误码+序号
	 */

	/***
	 * 公用错误码
	 */
	SUCCESS("BEAUTYIF00000"), // 处理成功
	UNKHOWN_ERROR("BEAUTYIF99999"), // 未知错误
	ERROR_NULL_RECORD("BEAUTYIFWB888"), // 查无此记录
	ERROR_CALL_OUTER_INTERFACE("BEAUTYIF00001"), // 调用【{0}】接口异常

	WARN_SYS_ERROR("BEAUTYWB001"), // 系统错误

	/***
	 * WB--业务WARN--公用错误码--BEAUTYIFWBA01-BEAUTYIFWBA99
	 */

	NULL_PROPERTIES("BEAUTYIFWBA01"), // 【{0}】为空
	ERROR_FORMAT("BEAUTYIFWBA02"), // 参数【{0}】格式错误
	ERROR_PARAM("BEAUTYIFWBA03"), // 参数【{0}】错误
	ERROR_LENGTH("BEAUTYIFWBA04"), // 参数【{0}】长度错误
	ERROR_NOT_MATCH("BEAUTYIFWBA05"), // {0}不匹配
	ERROR_NOT_SEARCH_RECORD("BEAUTYIFWBA06"), // 未查询到{0}
	ERROR_REPORT_TRANS("BEAUTYIFWBA07"), // {0}数据转换异常
	ERROR_REPORT_TYPE("BEAUTYIFWBA08"), // 返回{0}类型错误
	ERROR_GET_NOT_DATA("BEAUTYIFWBA09"), // {0}未返回数据
	ERROR_QUERY_NOT_FINISHED("BEAUTYIFWBA10"), // 查询{0}未完成
	ERROR_DATA_TRANS("BEAUTYIFWBA11"), // 数据转换异常
	ERROR_INTERFACE_CALL_FAILED("BEAUTYIFWBA12"), // {0}接口调用失败{1}
	ERROR_HAS_BEEN_CALLED("BEAUTYIFWBA13"), // {0}已被加载

	/***
	 * EB--业务ERROR--公用错误码--BEAUTYIFEBA01-BEAUTYIFEBA99
	 */

	/***
	 * ES--系统ERROR--公用错误码--BEAUTYIFESA01-BEAUTYIFESA99
	 */
	ERROR_UPDATE_FAIL("BEAUTYIFESA01"), // {0}更新失败
	ERROR_QUERY_FAIL("BEAUTYIFESA02"), // {0}查询失败
	ERROR_INSERT_FAIL("BEAUTYIFESA03"), // {0}新增失败
	ERROR_DATA_ACCESS("BEAUTYIFESA04"), // 数据库访问异常
	ERROR_LINK_FAIL("BEAUTYIFESA05"), // 连接{0}异常
	ERROR_CONNECT_TIMEOUT("BEAUTYIFESA06"), // 链接{0}超时
	ERROR_JSON_PARSE_ERROR("BEAUTYIFESA07"), // JSON解析异常
	ERROR_ENCODE_ERROR("BEAUTYIFESA08"), // {0}URL编码有误
	ERROR_FETCH_FILE_FAIL("BEAUTYIFESA09"), // 无法获取{0}文件
	ERROR_REPORT_FORMAT_ERROR("BEAUTYIFESA10"), // {0}报文格式有误

	ERROR_LF_QUERY_ERROR("BEAUTYLFES001"), // linkface查询异常
	ERROR_LF_UNKNOWN_ERROR("BEAUTYLFES002"); // linkface出现未知错误


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String code;

	private BusinessErrorCode(String code) {
		this.code = code;
	}
}
