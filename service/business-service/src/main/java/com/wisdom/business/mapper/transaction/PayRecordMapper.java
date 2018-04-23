package com.wisdom.business.mapper.transaction;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by sunxiao on 2017/6/20.
 */
@MyBatisDao
@Repository
public interface PayRecordMapper {

    void insertPayRecord(PayRecordDTO payRecordDTO);

    List<PayRecordDTO> getUserPayRecordList(PayRecordDTO payRecordDTO);

    void updatePayRecordStatus(PayRecordDTO payRecord);

    int queryPayRecordCountByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);

    List<PayRecordDTO> queryPayRecordsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);

    List<PayRecordDTO> getUserPayRecordListByDate(@Param("userId") String userId,
                                                  @Param("startDate") String startDate,
                                                  @Param("endDate") String endDate);

    String getSellNumByProductId(@Param("productId") String productId);

    List<PayRecordDTO> queryUserInfoByTransactionId(@Param("transactionId")String transactionId);

    List<BusinessOrderDTO> queryOrderInfoByTransactionId(@Param("transactionId")String transactionId);
}
