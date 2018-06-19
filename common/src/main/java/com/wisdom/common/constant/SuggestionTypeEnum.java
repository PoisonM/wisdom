package com.wisdom.common.constant;

public enum SuggestionTypeEnum {

    BOSS("0"),
    CUSTOMER("1"),
    MANAGEMENT("2"),
    RECEPTION("3");
    SuggestionTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
