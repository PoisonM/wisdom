package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public class ConfigConstant {

	public static final String DOMAIN_VALUE = Global.getConfig("DOMAIN_VALUE");

	public static final String USER_WEB_URL = Global.getConfig("USER_WEB_URL");

	public static final String OFFLINE_PRODUCT_BUY_NOTIFY_URL = null;//Global.getConfig("OFFLINE_PRODUCT_BUY_NOTIFY_URL");

	public static final String TRAINING_PRODUCT_BUY_NOTIFY_URL = null;//Global.getConfig("TRAINING_PRODUCT_BUY_NOTIFY_URL");

	public static final String SPECIAL_PRODUCT_BUY_NOTIFY_URL = null;//Global.getConfig("SPECIAL_PRODUCT_BUY_NOTIFY_URL");

	public static final String SPECIAL_SHOP_URL = null;//Global.getConfig("SPECIAL_SHOP_URL");

	//redis中存储的登陆后的token的有效期，目前是30天
	public static final int logintokenPeriod = 0;//Integer.parseInt(Global.getConfig("logintokenPeriod"));

	public static final String businessC1 = null;//Global.getConfig("businessC1");

	public static final String businessB1 = null;//Global.getConfig("businessB1");

	public static final String businessA1 = null;//Global.getConfig("businessA1");

	public static final String promote_businessB1_ProductId_No1 = null;//Global.getConfig("promote_businessB1_ProductId_No1");

	public static final int livingPeriodYear = 0;//Integer.parseInt(Global.getConfig("livingPeriodYear"));

	public static final int livingPeriodTripleMonth  = 0;//Integer.parseInt(Global.getConfig("livingPeriodTripleMonth"));

	public static final float nextB1TripleMonthReward  =0;//Integer.parseInt(Global.getConfig("nextB1TripleMonthReward"));

	public static final int livingPeriodTripleMonth_B1  = 0;//Integer.parseInt(Global.getConfig("livingPeriodTripleMonth_B1"));

	public static final String weixinUserFlag = null;//Global.getConfig("weixinUserFlag");

	public static final String weixinBossFlag = null;//Global.getConfig("weixinBossFlag");

	public static final String USER_CORPID = null;//Global.getConfig("USER_CORPID");

	public static final String USER_SECRET = null;//Global.getConfig("USER_SECRET");

	public static final String BOSS_CORPID = null;//Global.getConfig("BOSS_CORPID");

	public static final String BOSS_SECRET = null;//Global.getConfig("BOSS_SECRET");

	public static final String APP_ID = null;//Global.getConfig("APP_ID");

	public static final String MCH_ID = null;//Global.getConfig("MCH_ID");

	public static final String DEVICE_INFO = null;//Global.getConfig("DEVICE_INFO");

	public static final String FEE_TYPE = null;//Global.getConfig("FEE_TYPE");

	public static final String GATE_URL = null;//Global.getConfig("GATE_URL");

	public static final String transfer = null;//Global.getConfig("transfer");

	public static final String SIGN_METHOD = null;//Global.getConfig("SIGN_METHOD");

	public static final String SHARE_CODE_VALUE = null;//Global.getConfig("SHARE_CODE_VALUE");

	public static final String SPECIAL_SHOP_VALUE = null;//Global.getConfig("SPECIAL_SHOP_VALUE");

	public static final int AUTO_CONFIRM_RECEIVE_PRODUCT_DAY = 0;//Integer.parseInt(Global.getConfig("AUTO_CONFIRM_RECEIVE_PRODUCT_DAY"));

	public static final int AUTO_NOTIFY_PRODUCT_PAY = 0;//Integer.parseInt(Global.getConfig("AUTO_NOTIFY_PRODUCT_PAY"));

	public static final int AUTO_DELETE_BUSINESS_ORDER = 0;//Integer.parseInt(Global.getConfig("AUTO_DELETE_BUSINESS_ORDER"));

	public static final float PROMOTE_B1_LEVEL_MIN_EXPENSE = 0;//Integer.parseInt(Global.getConfig("PROMOTE_B1_LEVEL_MIN_EXPENSE"));

	public static final float PROMOTE_B1_LEVEL_MAX_EXPENSE = 0;//Integer.parseInt(Global.getConfig("PROMOTE_B1_LEVEL_MAX_EXPENSE"));

	public static final float PROMOTE_A_LEVEL_MIN_EXPENSE = 0;//Integer.parseInt(Global.getConfig("PROMOTE_A_LEVEL_MIN_EXPENSE"));

	public static final float MONTH_A_INCOME_PERCENTAGE = 0;//Integer.parseInt(Global.getConfig("MONTH_A_INCOME_PERCENTAGE"));

	public static final float RECOMMEND_PROMOTE_A1_REWARD = 0;//Integer.parseInt(Global.getConfig("RECOMMEND_PROMOTE_A1_REWARD"));

	public static final float RECOMMEND_USER_NUM_REWARD = 0;//Integer.parseInt(Global.getConfig("RECOMMEND_USER_NUM_REWARD"));

	public static final float MONTH_B1_INCOME_PERCENTAGE = 0;//Integer.parseInt(Global.getConfig("MONTH_B1_INCOME_PERCENTAGE"));

	public static final String PARTNER_KEY =  null;//Global.getConfig("PARTNER_KEY");

	public static final String USER_OPEN_ID =  null;//Global.getConfig("USER_OPEN_ID");

	public static final String BOSS_OPEN_ID =  null;//Global.getConfig("BOSS_OPEN_ID");

    public static final String financeMember =  null;//Global.getConfig("financeMember");

    public static final String operationMember =  null;//Global.getConfig("operationMember");

    public static final int MAX_WITHDRAW_NUM = 0;//Integer.parseInt(Global.getConfig("MAX_WITHDRAW_NUM"));

	public static final int MAX_WITHDRAW_AMOUNT = 0;//Integer.parseInt(Global.getConfig("MAX_WITHDRAW_AMOUNT"));

	public static final int MIN_WITHDRAW_AMOUNT = 0;//Integer.parseInt(Global.getConfig("MIN_WITHDRAW_AMOUNT"));

	public static final int CHECK_WITHDRAW_AMOUNT = 0;//Integer.parseInt(Global.getConfig("CHECK_WITHDRAW_AMOUNT"));

}
