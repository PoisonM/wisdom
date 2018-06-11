package com.wisdom.beauty.api.extDto;/**
 * Created by 赵得良 on 2018/6/10 0010.
 */

import com.wisdom.common.dto.user.SysClerkDTO;

/**
 * FileName: ExtSysClerkDTO
 *
 * @author: 赵得良
 * Date:     2018/6/10 0010 14:31
 * Description:
 */
public class ExtSysClerkDTO extends SysClerkDTO{
    // 美容院
    private String currentBeautyShopName;

    public String getCurrentBeautyShopName() {
        return currentBeautyShopName;
    }

    public void setCurrentBeautyShopName(String currentBeautyShopName) {
        this.currentBeautyShopName = currentBeautyShopName;
    }
}
