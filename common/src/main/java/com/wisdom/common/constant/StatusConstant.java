package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public enum StatusConstant {

	instance;

	public static final String LOGIN_ERROR="0x00000";

	public static final String SUCCESS = "0x00001";

	public static final String FAILURE = "0x00002";

	public static final String ONLINE = "0x00003";

	public static final String OFFLINE = "0x00004";

	public static final String LOGIN_OUT = "0x00005";

	public static final String TOKEN_ERROR = "0x00006";

	public static final String PARAM_ERROR = "0x00007";

	public static final String VALIDATECODE_ERROR = "0x00010";

	public static final String NO_USER_ADDRESS = "0x00011";

	public static final String WEIXIN_ATTENTION_ERROR = "0x00012";

}
