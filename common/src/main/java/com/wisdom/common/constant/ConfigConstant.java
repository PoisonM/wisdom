package com.wisdom.common.constant;

public enum ConfigConstant {

	instance;

	public static final String DOMAIN_VALUE = "kpbeauty.com.cn";

	public static final String USER_WEB_URL = "http://mx99test1.kpbeauty.com.cn/customer#/";

	public static final String OFFLINE_PRODUCT_BUY_NOTIFY_URL = "http://mx99test1.kpbeauty.com.cn/business/transaction/getOfflineProductPayNotifyInfo";

	public static final String TRAINING_PRODUCT_BUY_NOTIFY_URL = "http://mx99test1.kpbeauty.com.cn/business/transaction/getTrainingProductPayNotifyInfo";

	public static final String SPECIAL_PRODUCT_BUY_NOTIFY_URL = "http://mx99test1.kpbeauty.com.cn/business/transaction/getSpecialProductPayNotifyInfo";

	public static final String SPECIAL_SHOP_URL = "http://mx99test1.kpbeauty.com.cn/weixin/customer/fieldwork/author?url=" +
			"http://mx99test1.kpbeauty.com.cn/weixin/customer/getCustomerWeixinMenuId?url=specialProductList88888888";

	//redis中存储的登陆后的token的有效期，目前是30天
	public static final int logintokenPeriod = 60*60*24*30;

	public static final String businessC1 = "business-C-1";

	public static final String businessB1 = "business-B-1";//消费超过498

	public static final String businessA1 = "business-A-1";

	public static final String promote_businessB1_ProductId_No1 = "88888888888";

	public static final int livingPeriodYear = 365; //一年有效期,365天

	public static final int livingPeriodTripleMonth  = 10; //90天有效期

	public static final float nextB1TripleMonthReward  = 20; //特殊商品20元奖励

	public static final int livingPeriodTripleMonth_B1  = 90; //90天有效期

	public static final String weixinUserFlag = "userOnlineOperation";

	public static final String weixinBossFlag = "bossOnlineOperation";

	public static final String USER_CORPID = "wx174f02516e9f9733";//测试号

	public static final String USER_SECRET = "b2c3393bf85fc29d2a1ea5b5dec554b3";//测试号

	public static final String BOSS_CORPID = "wx174f02516e9f9733";//测试号

	public static final String BOSS_SECRET = "b2c3393bf85fc29d2a1ea5b5dec554b3";//测试号

//	public static final String USER_CORPID = "wxa62609dff1842cd8";//商用号
//
//	public static final String USER_SECRET = "e2bf27273f48715d12f16fcafca0bb75";//商用号

	public static final String APP_ID = "wx174f02516e9f9733";

	public static final String MCH_ID = "1495158682";

	public static final String DEVICE_INFO = "WEB";

	public static final String FEE_TYPE = "CNY";

	public static final String GATE_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static final String transfer = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	public static final String SIGN_METHOD = "MD5";

	public static final String SHARE_CODE_VALUE = "mxbusinessshare_";

	public static final String SPECIAL_SHOP_VALUE = "mxForeignPurchase_";

	public static final int AUTO_CONFIRM_RECEIVE_PRODUCT_DAY = 15;

	public static final int AUTO_NOTIFY_PRODUCT_PAY = 3;

	public static final int AUTO_DELETE_BUSINESS_ORDER = 3;

	public static final float PROMOTE_B1_LEVEL_MIN_EXPENSE = 498;

	public static final float PROMOTE_B1_LEVEL_MAX_EXPENSE = 9899;

	public static final float PROMOTE_A_LEVEL_MIN_EXPENSE = 9900;

	public static final float MONTH_A_INCOME_PERCENTAGE = 35;

	public static final float RECOMMEND_PROMOTE_A1_REWARD = 495;

	public static final float RECOMMEND_USER_NUM_REWARD = 2;

//	public static final float MONTH_A_INCOME_MAX_EXPENSE = 10000;
//
//	public static final float MONTH_B1_INCOME_MAX_EXPENSE = 1000;

	public static final float MONTH_A_INCOME_MAX_EXPENSE = -1;

	public static final float MONTH_B1_INCOME_MAX_EXPENSE = -1;

	public static final float MONTH_B1_INCOME_PERCENTAGE = 10;

	public static final String PARTNER_KEY = "FDSKLJjklsjJKLJKLjkl98908789kljl";

	public static final String USER_OPEN_ID = "meixiang99MXtest_user_openid";

	public static final String BOSS_OPEN_ID = "meixiang99MXtest_boss_openid";

    public static final String financeMember = "finance-1";

    public static final String operationMember = "operation-1";

    public static final int MAX_WITHDRAW_NUM = 1;

	public static final int MAX_WITHDRAW_AMOUNT = 5000;

	public static final int MIN_WITHDRAW_AMOUNT = 10;

	public static final int CHECK_WITHDRAW_AMOUNT = 1000;
}
