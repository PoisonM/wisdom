package com.wisdom.timer.client;

import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("business-service")
public interface BusinessServiceClient {

    @RequestMapping(value = "/getBusinessOrderList",method=RequestMethod.POST)
    List<BusinessOrderDTO> getBusinessOrderList(@RequestBody BusinessOrderDTO businessOrderDTO);

    @RequestMapping(value = "/getUserIncomeRecordInfo",method=RequestMethod.POST)
    List<IncomeRecordDTO> getUserIncomeRecordInfo(@RequestBody IncomeRecordDTO incomeRecordDTO);

    @RequestMapping(value = "/updateBusinessOrder",method=RequestMethod.POST)
    void updateBusinessOrder(@RequestBody BusinessOrderDTO businessOrder);

    @RequestMapping(value = "/getPayRecordInfo",method=RequestMethod.POST)
    List<PayRecordDTO> getPayRecordInfo(@RequestBody PayRecordDTO payRecordDTO);

    @RequestMapping(value = "/updatePayRecordStatus",method=RequestMethod.POST)
    void updatePayRecordStatus(@RequestBody PayRecordDTO payRecordDTO);

    @RequestMapping(value = "/getUserBusinessType",method=RequestMethod.POST)
    List<UserBusinessTypeDTO> getUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/insertUserBusinessType",method=RequestMethod.POST)
    void insertUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/updateUserBusinessType",method=RequestMethod.POST)
    void updateUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/getMonthTransactionRecordByUserId",method=RequestMethod.GET)
    List<MonthTransactionRecordDTO> getMonthTransactionRecordByUserId(@RequestParam(value="userId") String userId,
                                                                      @RequestParam(value="startDate") String startDate,
                                                                      @RequestParam(value="endDate") String endDate);

    @RequestMapping(value = "/getUserPayRecordListByDate",method=RequestMethod.GET)
    List<PayRecordDTO> getUserPayRecordListByDate(@RequestParam(value="userId") String userId,
                                                  @RequestParam(value="startDate") String startDate,
                                                  @RequestParam(value="endDate") String endDate);

    @RequestMapping(value = "/insertUserIncomeInfo",method=RequestMethod.POST)
    void insertUserIncomeInfo(@RequestBody IncomeRecordDTO incomeRecordDTO);

    @RequestMapping(value = "/getUserAccountInfo",method=RequestMethod.GET)
    AccountDTO getUserAccountInfo(@RequestParam(value="userId") String userId);

    @RequestMapping(value = "/updateUserAccountInfo",method=RequestMethod.POST)
    void updateUserAccountInfo(@RequestBody AccountDTO accountDTO);

    @RequestMapping(value = "/getUserPayRecordList",method=RequestMethod.POST)
    List<PayRecordDTO> getUserPayRecordList(@RequestBody PayRecordDTO payRecordDTO);

    @RequestMapping(value = "/getBusinessOrderByOrderId",method=RequestMethod.GET)
    BusinessOrderDTO getBusinessOrderByOrderId(@RequestParam(value="orderId") String orderId);

    @RequestMapping(value = "/updateIncomeInfo",method=RequestMethod.POST)
    void updateIncomeInfo(@RequestBody IncomeRecordDTO incomeRecord);
}
