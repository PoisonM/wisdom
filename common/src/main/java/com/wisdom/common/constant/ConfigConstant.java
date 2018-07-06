package com.wisdom.common.constant;

import com.wisdom.common.config.Global;

public class ConfigConstant {

	public static final String DOMAIN_VALUE = Global.getConfig("DOMAIN_VALUE");

	public static final String USER_WEB_URL = Global.getConfig("USER_WEB_URL");

	public static final String BOSS_WEB_URL = Global.getConfig("BOSS_WEB_URL");

	public static final String OFFLINE_PRODUCT_BUY_NOTIFY_URL = Global.getConfig("OFFLINE_PRODUCT_BUY_NOTIFY_URL");

	public static final String TRAINING_PRODUCT_BUY_NOTIFY_URL = Global.getConfig("TRAINING_PRODUCT_BUY_NOTIFY_URL");

	public static final String SPECIAL_PRODUCT_BUY_NOTIFY_URL = Global.getConfig("SPECIAL_PRODUCT_BUY_NOTIFY_URL");

	public static final String SPECIAL_SHOP_URL = Global.getConfig("SPECIAL_SHOP_URL");

	//redis中存储的登陆后的token的有效期，目前是30天
	public static final int logintokenPeriod = Integer.parseInt(Global.getConfig("logintokenPeriod"));

	public static final String businessC1 = Global.getConfig("businessC1");

	public static final String businessB1 = Global.getConfig("businessB1");

	public static final String businessA1 = Global.getConfig("businessA1");

	public static final String promote_businessB1_ProductId_No1 = Global.getConfig("promote_businessB1_ProductId_No1");

	public static final int livingPeriodYear = Integer.parseInt(Global.getConfig("livingPeriodYear"));

	public static final int livingPeriodTripleMonth  = Integer.parseInt(Global.getConfig("livingPeriodTripleMonth"));

	public static final float nextB1TripleMonthReward  = Integer.parseInt(Global.getConfig("nextB1TripleMonthReward"));

	public static final int livingPeriodTripleMonth_B1  = Integer.parseInt(Global.getConfig("livingPeriodTripleMonth_B1"));

	public static final String weixinUserFlag = Global.getConfig("weixinUserFlag");

	public static final String weixinBossFlag = Global.getConfig("weixinBossFlag");

	public static final String USER_CORPID = Global.getConfig("USER_CORPID");

	public static final String USER_SECRET = Global.getConfig("USER_SECRET");

	public static final String BOSS_CORPID = Global.getConfig("BOSS_CORPID");

	public static final String BOSS_SECRET = Global.getConfig("BOSS_SECRET");

	public static final String APP_ID = Global.getConfig("APP_ID");

	public static final String MCH_ID = Global.getConfig("MCH_ID");

	public static final String DEVICE_INFO = Global.getConfig("DEVICE_INFO");

	public static final String FEE_TYPE = Global.getConfig("FEE_TYPE");

	public static final String GATE_URL = Global.getConfig("GATE_URL");

	public static final String transfer = Global.getConfig("transfer");

	public static final String SIGN_METHOD = Global.getConfig("SIGN_METHOD");

	public static final String SHARE_CODE_VALUE = Global.getConfig("SHARE_CODE_VALUE");

	public static final String SPECIAL_SHOP_VALUE = Global.getConfig("SPECIAL_SHOP_VALUE");

	public static final int AUTO_CONFIRM_RECEIVE_PRODUCT_DAY = Integer.parseInt(Global.getConfig("AUTO_CONFIRM_RECEIVE_PRODUCT_DAY"));

	public static final int AUTO_NOTIFY_PRODUCT_PAY = Integer.parseInt(Global.getConfig("AUTO_NOTIFY_PRODUCT_PAY"));

	public static final int AUTO_DELETE_BUSINESS_ORDER = Integer.parseInt(Global.getConfig("AUTO_DELETE_BUSINESS_ORDER"));

	public static final float PROMOTE_B1_LEVEL_MIN_EXPENSE = Integer.parseInt(Global.getConfig("PROMOTE_B1_LEVEL_MIN_EXPENSE"));

	public static final float PROMOTE_B1_LEVEL_MAX_EXPENSE = Integer.parseInt(Global.getConfig("PROMOTE_B1_LEVEL_MAX_EXPENSE"));

	public static final float PROMOTE_A_LEVEL_MIN_EXPENSE = Integer.parseInt(Global.getConfig("PROMOTE_A_LEVEL_MIN_EXPENSE"));

	public static final float MONTH_A_INCOME_PERCENTAGE = Integer.parseInt(Global.getConfig("MONTH_A_INCOME_PERCENTAGE"));

	public static final float RECOMMEND_PROMOTE_A1_REWARD = Integer.parseInt(Global.getConfig("RECOMMEND_PROMOTE_A1_REWARD"));

	public static final float RECOMMEND_USER_NUM_REWARD = Integer.parseInt(Global.getConfig("RECOMMEND_USER_NUM_REWARD"));

	public static final float MONTH_B1_INCOME_PERCENTAGE = Integer.parseInt(Global.getConfig("MONTH_B1_INCOME_PERCENTAGE"));

	public static final String PARTNER_KEY =  Global.getConfig("PARTNER_KEY");

	public static final String USER_OPEN_ID =  Global.getConfig("USER_OPEN_ID");

	public static final String BOSS_OPEN_ID =  Global.getConfig("BOSS_OPEN_ID");

    public static final String financeMember =  Global.getConfig("financeMember");

    public static final String operationMember =  Global.getConfig("operationMember");

    public static final int MAX_WITHDRAW_NUM = Integer.parseInt(Global.getConfig("MAX_WITHDRAW_NUM"));

	public static final int MAX_WITHDRAW_AMOUNT = Integer.parseInt(Global.getConfig("MAX_WITHDRAW_AMOUNT"));

	public static final int MIN_WITHDRAW_AMOUNT = Integer.parseInt(Global.getConfig("MIN_WITHDRAW_AMOUNT"));

	public static final int CHECK_WITHDRAW_AMOUNT = Integer.parseInt(Global.getConfig("CHECK_WITHDRAW_AMOUNT"));

	public static final String PAY_TEST_FLAG = Global.getConfig("PAY_TEST_FLAG");

	public static final String shopBusiness = Global.getConfig("shopBusiness");

	public static final String beautySource = "beauty";

	public static final String businessSource = "business";
/*	public static final float PERMANT_REWARD = Integer.parseInt(Global.getConfig("PERMANT_REWARD"));*/

	public static final String LEVE_IMPORT_A = Global.getConfig("LEVE_IMPORT_A");

	public static final String LEVE_IMPORT_B = Global.getConfig("LEVE_IMPORT_B");

	public static final String LEVE_IMPORT = Global.getConfig("LEVE_IMPORT");

	public static final String INCOME_TYPE_P = Global.getConfig("INCOME_TYPE_P");

	public static final String INCOME_TYPE_I = Global.getConfig("INCOME_TYPE_I");

	//即时审核状态
	public static final String INCOME_UNAUDITED = Global.getConfig("INCOME_UNAUDITED");

	public static final String INCOME_OPERATION = Global.getConfig("INCOME_OPERATION");

	public static final String INCOME_FINANCE = Global.getConfig("INCOME_FINANCE");

	public static final String INCOME_AUDITED = Global.getConfig("INCOME_AUDITED");

	public static final String INCOME_AUDIT_REJECTION = Global.getConfig("INCOME_AUDIT_REJECTION");

	public static final String FREE_COURSE = Global.getConfig("FREE_COURSE");

	public static final String MEMBER_SHIP_COURSE = Global.getConfig("MEMBER_SHIP_COURSE");

	public static final String CHARGE_COURSE = Global.getConfig("CHARGE_COURSE");

}
