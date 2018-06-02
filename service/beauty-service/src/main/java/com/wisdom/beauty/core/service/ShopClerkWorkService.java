package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopClerkWorkRecordDTO;
import com.wisdom.beauty.api.requestDto.ShopClerkWorkRecordRequestDTO;
import com.wisdom.beauty.api.responseDto.ShopClerkWorkRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRecordResponseDTO;
import com.wisdom.beauty.api.responseDto.UserConsumeRequestDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghuan on 2018/5/31.
 * <p>
 * 统计员工业绩相关接口的
 */
public interface ShopClerkWorkService {
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据条件获取员工的工作业绩记录列表
     * @Date:2018/5/31 11:26
     */
    List<ShopClerkWorkRecordResponseDTO> getShopCustomerConsumeRecordList(PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取充值金额，消费金额 ，划卡金额，单次消费金额，卡耗消费金额
     * @Date:2018/5/31 11:23
     */
    Map<String, String> getShopConsumeAndRecharge(PageParamVoDTO<ShopClerkWorkRecordRequestDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 批量插入员工工作记录
     * @Date:2018/6/1 9:50
     */
    int saveClerkWorkRecord(List<ShopClerkWorkRecordDTO> shopClerkWorkRecordDTOs);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据多个flowId查询员工集合
     * @Date:2018/6/2 9:55
     */
    List<ShopClerkWorkRecordDTO> getShopClerkList(List<String> flowIds);
}
