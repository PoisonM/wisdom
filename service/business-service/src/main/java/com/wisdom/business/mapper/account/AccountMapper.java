package com.wisdom.business.mapper.account;

import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunxiao on 2017/6/20.
 */
@MyBatisDao
@Repository
public interface AccountMapper {

    AccountDTO getUserAccountInfoByUserId(@Param("userId") String userId);

    void updateUserAccountInfo(AccountDTO accountDTO);

    void insertUserAccountInfo(AccountDTO accountDTO);

    List<AccountDTO> getUserAccountInfo(AccountDTO accountDTO);

    /**
     * 查询条件总条数
     */
    Integer queryUserBalanceCountByParameters(@Param("phoneAndIdentify")String phoneAndIdentify);

    //根据条件查询余额信息
    List<AccountDTO> queryUserBalanceByParameters(@Param("phoneAndIdentify")String phoneAndIdentify,
                                                  @Param("isExportExcel")String isExportExcel,
                                                  @Param("pageStartNo")int pageStartNo,
                                                  @Param("pageSize")int pageSize);
    //分页器冲突暂时不用
    //Page<AccountDTO> queryUserBalanceByParameters(String phoneAndIdentify, Page<AccountDTO> page);

    Page<AccountDTO> queryAllUserBalance(Page<AccountDTO> page);
}
