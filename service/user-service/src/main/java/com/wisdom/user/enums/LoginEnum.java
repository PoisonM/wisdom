package com.wisdom.user.enums;

public enum LoginEnum {

    USER("beautyUser", "beautyuserlogintoken"),
    CLERK("beautyClerk", "beautyclerklogintoken"),
    BOSS("beautyBoss", "beautybosslogintoken");

    LoginEnum(String userType, String loginToken) {
        this.userType = userType;
        this.loginToken = loginToken;
    }

    private String userType;
    private String loginToken;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
