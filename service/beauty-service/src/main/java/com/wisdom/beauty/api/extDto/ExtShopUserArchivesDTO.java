package com.wisdom.beauty.api.extDto;

import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;

public class ExtShopUserArchivesDTO extends ShopUserArchivesDTO {
    //账户总余额
    private String totalBalance;

    //最近一次预约时间
    private String lastAppointTimes;

    private String isMember;

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public String getLastAppointTimes() {
        return lastAppointTimes;
    }

    public void setLastAppointTimes(String lastAppointTimes) {
        this.lastAppointTimes = lastAppointTimes;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }
}