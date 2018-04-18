package com.wisdom.beauty.api.responseDto;

import com.wisdom.common.entity.BaseEntity;

/**
 * ClassName: UserProductRelationResponseDTO
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/18 11:22
 * @since JDK 1.8
 */
public class UserProductRelationResponseDTO extends BaseEntity {

    /**
     * 未领取数量
     */
    private Integer waitReceiveNumber;
    /**
     * 用户id
     */
    private String sysUserId;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 手机号
     */
    private String mobile;

    public UserProductRelationResponseDTO() {
    }

    public Integer getWaitReceiveNumber() {
        return waitReceiveNumber;
    }

    public void setWaitReceiveNumber(Integer waitReceiveNumber) {
        this.waitReceiveNumber = waitReceiveNumber;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

}
