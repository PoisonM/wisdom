package com.wisdom.bussiness.service.impl;

import com.wisdom.business.BusinessServiceApplication;
import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.business.mapper.account.IncomeMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.SpringUtil;
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
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
    private WebApplicationContext context;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private IncomeMapper incomeMapper;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        SpringUtil.setApplicationContext(context);
    }

    @Test
    public void testAccount() throws UnsupportedEncodingException {
        monthlyIncomeCalc(ConfigConstant.businessB1);
    }

    @Test
    public void testUserAccount() throws UnsupportedEncodingException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId("045229c6-1de8-4c9f-8196-4e740f1aeda8");
        accountMapper.updateUserAccountInfo(accountDTO);
    }

    public void monthlyIncomeCalc(String businessType) throws UnsupportedEncodingException {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserType(businessType);
        userInfoDTO.setDelFlag("0");
        userInfoDTO.setId("0d2d7826-c85c-42d9-b54c-afb41e55e8b9");
        List<UserInfoDTO> userInfoDTOList = userServiceClient.getUserInfo(userInfoDTO);
        for (UserInfoDTO userInfo : userInfoDTOList) {
            float returnMonthlyMoney = 0;
            float returnMonthlyMoney_A = 0;
            float returnMonthlyMoney_B = 0;

            String startDate = "";
            String endDate = DateUtils.getYear() + "-" + DateUtils.getMonth() + "-" + "26";
            if (DateUtils.getMonth().equals("01")) {
                int month = 11;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-26";
            } else if (DateUtils.getMonth().equals("02")) {
                int month = 12;
                int year = Integer.parseInt(DateUtils.getYear()) - 1;
                startDate = year + "-" + month + "-26";
            } else {
                int month = Integer.parseInt(DateUtils.getMonth()) - 2;
                startDate = DateUtils.getYear() + "-" + month + "-26";
            }

            List<MonthTransactionRecordDTO> monthTransactionRecordDTOList = transactionMapper.getMonthTransactionRecordByUserId(userInfo.getId(), startDate, endDate);

            for (MonthTransactionRecordDTO monthTransactionRecordDTO : monthTransactionRecordDTOList) {
                if (monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessA1)) {
                    returnMonthlyMoney_A = returnMonthlyMoney_A + monthTransactionRecordDTO.getAmount();
                } else if (monthTransactionRecordDTO.getUserType().equals(ConfigConstant.businessB1)) {
                    returnMonthlyMoney_B = returnMonthlyMoney_B + monthTransactionRecordDTO.getAmount();
                }
            }

            //计算当前用户本月的消费金额
            if (returnMonthlyMoney_A > 0 || returnMonthlyMoney_B > 0) {
                returnMonthlyMoney = returnMonthlyMoney_A * ConfigConstant.MONTH_A_INCOME_PERCENTAGE / 100 + returnMonthlyMoney_B * ConfigConstant.MONTH_B1_INCOME_PERCENTAGE / 100;

                AccountDTO accountDTO = accountMapper.getUserAccountInfoByUserId(userInfo.getId());
                float balance = accountDTO.getBalance() + returnMonthlyMoney;
                float balanceDeny = accountDTO.getBalanceDeny() + returnMonthlyMoney;
                accountDTO.setBalance(balance);
                accountDTO.setBalanceDeny(balanceDeny);
                accountDTO.setUpdateDate(new Date());
                accountMapper.updateUserAccountInfo(accountDTO);

                IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
                incomeRecordDTO.setId(UUID.randomUUID().toString());
                incomeRecordDTO.setSysUserId(userInfo.getId());
                incomeRecordDTO.setUserType(userInfo.getUserType());
                incomeRecordDTO.setNextUserId("");
                incomeRecordDTO.setNextUserType("");
                incomeRecordDTO.setAmount(returnMonthlyMoney);
                incomeRecordDTO.setTransactionAmount(0);
                incomeRecordDTO.setTransactionId("test");
                incomeRecordDTO.setUpdateDate(new Date());
                incomeRecordDTO.setCreateDate(new Date());
                incomeRecordDTO.setStatus("0");
                incomeRecordDTO.setIdentifyNumber(userInfo.getIdentifyNumber());
                incomeRecordDTO.setNextUserIdentifyNumber("");
                incomeRecordDTO.setNickName(URLEncoder.encode(userInfo.getNickname(), "utf-8"));
                incomeRecordDTO.setNextUserNickName("");
                incomeRecordDTO.setIncomeType("month");
                incomeRecordDTO.setMobile(userInfo.getMobile());
                incomeRecordDTO.setNextUserMobile("");
                incomeRecordDTO.setParentRelation("");
                incomeMapper.insertUserIncomeInfo(incomeRecordDTO);
            }
        }
    }

    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        float min = 1.5f;
        float max = 3.0f;
        float floatBounded = Float.parseFloat(decimalFormat.format(min + new Random().nextFloat() * (max - min)));
        System.out.println("args = [" + floatBounded + "]");

    }
}
