package com.wisdom.bussiness.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.business.BusinessServiceApplication;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.SpringUtil;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.JedisUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 赵得良 on 21/09/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessServiceApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class ArchivesTest {

    private MockMvc mvc;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private IncomeService incomeService;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AccountMapper accountMapper;


    @Before
    public void setupMockMvc() throws Exception {
       // mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);


    }
    @Test
    public void monthRun() {


        //查询从"2018-04-15 00:00:00" 到 "2018-06-13 23:59:59" 时间段   用户第一笔消费大于497的记录
        List<PayRecordDTO> payRecordDTOS = transactionService.queryPayFirstOrder();


        try {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            for (PayRecordDTO payRecordDTO : payRecordDTOS) {
                userInfoDTO.setId(payRecordDTO.getSysUserId());
                List<UserInfoDTO> userInfoSelf = userServiceClient.getUserInfo(userInfoDTO);

                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                incomeRecordDTO.setId(UUID.randomUUID().toString());
                incomeRecordDTO.setSysUserId(payRecordDTO.getSysUserId());
                incomeRecordDTO.setUserType(userInfoSelf.get(0).getUserType());
                if(9900 > payRecordDTO.getAmount() && 497 < payRecordDTO.getAmount() ){
                    incomeRecordDTO.setAmount(payRecordDTO.getAmount()* (float) 0.1);
                    System.out.println("BBBBBBBBBBB");
                }else if(9900 <= payRecordDTO.getAmount()){
                    System.out.println("AAAAAAAAAAA");
                    incomeRecordDTO.setAmount(payRecordDTO.getAmount() * (float) 0.3);
                }else {
                    System.out.println("0000000000");
                    incomeRecordDTO.setAmount((float)0.00);
                }
                incomeRecordDTO.setTransactionAmount(payRecordDTO.getAmount());
                incomeRecordDTO.setTransactionId(payRecordDTO.getTransactionId());
                incomeRecordDTO.setUpdateDate(new Date());
                incomeRecordDTO.setCreateDate(new Date());
                incomeRecordDTO.setStatus("0");

                incomeRecordDTO.setNextUserId("");
                incomeRecordDTO.setNextUserType("");
                incomeRecordDTO.setNextUserIdentifyNumber("");
                incomeRecordDTO.setNextUserNickName("");

                incomeRecordDTO.setIdentifyNumber(userInfoSelf.get(0).getIdentifyNumber());
                incomeRecordDTO.setNickName(URLEncoder.encode(userInfoSelf.get(0).getNickname(), "utf-8"));
                incomeRecordDTO.setIncomeType("month");
                incomeRecordDTO.setMobile(userInfoSelf.get(0).getMobile());
                incomeRecordDTO.setParentRelation("");


                System.out.println(incomeRecordDTO.getSysUserId()+"==="+incomeRecordDTO.getAmount());
                incomeService.insertUserIncomeInfo(incomeRecordDTO);


                AccountDTO accountDTO1 = new AccountDTO();
                accountDTO1.setSysUserId(incomeRecordDTO.getSysUserId());
                List<AccountDTO> accountDTOS = accountMapper.getUserAccountInfo(accountDTO1);
                AccountDTO accountDTO = accountDTOS.get(0);
                float balance = accountDTO.getBalance() + incomeRecordDTO.getAmount();
                float balanceDeny = accountDTO.getBalanceDeny() + incomeRecordDTO.getAmount();
                accountDTO.setBalance(balance);
                accountDTO.setBalanceDeny(balanceDeny);
                accountDTO.setUpdateDate(new Date());
                accountMapper.updateUserAccountInfo(accountDTO);
                System.out.println("balance"+balance+"balanceDeny"+balanceDeny);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getIncomeRe(){
        PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO = new PageParamVoDTO<>();
        pageParamVoDTO.setPageSize(10);
        List<IncomeRecordDTO> incomeRecordDTOS = incomeService.getIncomeRanking(pageParamVoDTO);

    }

}
