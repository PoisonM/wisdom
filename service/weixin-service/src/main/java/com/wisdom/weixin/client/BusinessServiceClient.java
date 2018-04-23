package com.wisdom.weixin.client;

import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("business-service")
public interface BusinessServiceClient {

    @RequestMapping(value = "/getUserBusinessType",method=RequestMethod.POST)
    List<UserBusinessTypeDTO> getUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/insertUserBusinessType",method=RequestMethod.POST)
    void insertUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO);

    @RequestMapping(value = "/getUserAccountInfo",method=RequestMethod.GET)
    AccountDTO getUserAccountInfo(@RequestParam(value="userId") String userId);

    @RequestMapping(value = "/createUserAccount",method=RequestMethod.POST)
    void createUserAccount(@RequestBody AccountDTO accountDTO);

    @RequestMapping(value = "/selectIncomeInstanceByUserId",method=RequestMethod.GET)
    String selectIncomeInstanceByUserId(@RequestParam(value = "userId") String userId);

}
