package com.wisdom.beauty.api.responseDto;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;

import java.math.BigDecimal;

/**
 * Created by zhanghuan on 2018/5/31.
 * 员工工作业绩返回对象
 */
public class ShopClerkWorkRecordResponseDTO extends ShopClerkWorkRecordDTO  implements Comparable<ShopClerkWorkRecordResponseDTO>{
    /**
     * 总金额
     */
    private BigDecimal sumAmount;
    private  String type;
    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(ShopClerkWorkRecordResponseDTO o) {
        if(this.getCreateDate().getTime()-o.getCreateDate().getTime()>0){
            return -1;
        }else {
            return  1;
        }
    }
}
