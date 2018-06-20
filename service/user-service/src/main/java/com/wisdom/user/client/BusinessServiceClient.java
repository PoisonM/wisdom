package com.wisdom.user.client;

import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("business-service")
public interface BusinessServiceClient {

    @RequestMapping(value = "/getUserBusinessType",method= RequestMethod.POST)
    List<UserBusinessTypeDTO> getUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/getBusinessOrderByOrderId",method=RequestMethod.GET)
    BusinessOrderDTO getBusinessOrderByOrderId(@RequestParam(value="orderId") String orderId);

    @RequestMapping(value = "/queryOrderDetailsById",method=RequestMethod.GET)
    BusinessOrderDTO queryOrderDetailsById(@RequestParam(value="orderId") String orderId);

    @RequestMapping(value = "/createUserAccount",method=RequestMethod.POST)
    void createUserAccount(@RequestBody AccountDTO accountDTO);

    @RequestMapping(value = "/insertUserBusinessType",method=RequestMethod.POST)
    void insertUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

}
