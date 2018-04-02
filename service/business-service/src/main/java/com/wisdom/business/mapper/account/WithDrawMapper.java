package com.wisdom.business.mapper.account;

import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.WithDrawRecordDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@MyBatisDao
@Repository
public interface WithDrawMapper {
    /**
     * 查询所有提现信息
     * @param
     * @return
     */
    Page<WithDrawRecordDTO> queryAllWithdraw(Page<WithDrawRecordDTO> page);

    void addWithDrawRecord(WithDrawRecordDTO withDrawRecordDTO);

    /**
     * 修改提现状态
     * @param withdrawId
     * @param status
     */
    void updateWithdrawById(@Param("withdrawId") String withdrawId, @Param("status") String status);

    /**
     * 根据条件查询提现信息
     * @return
     */
    List<WithDrawRecordDTO> queryWithdrawsByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);
    /**
     * 查询条件总条数
     */
    Integer queryWithdrawsCountByParameters(PageParamVoDTO<ProductDTO> pageParamVoDTO);

    List<WithDrawRecordDTO> getWithdrawInfoByPage(@Param("userId") String userId, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    WithDrawRecordDTO getWithdrawDetail(@Param("withDrawId") String transactionId);
}
