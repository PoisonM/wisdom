package com.wisdom.business.controller;

import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.mapper.transaction.TransactionMapper;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.UserBusinessTypeDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class BusinessServiceController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private PayRecordService payRecordService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserTypeMapper userTypeMapper;

	@RequestMapping(value = "/getBusinessOrderList",method=RequestMethod.POST)
	@ResponseBody
	List<BusinessOrderDTO> getBusinessOrderList(@RequestBody BusinessOrderDTO businessOrderDTO){
		List<BusinessOrderDTO> businessOrderDTOList = transactionMapper.getBusinessOrderList(businessOrderDTO);
		return businessOrderDTOList;
	};

	@RequestMapping(value = "/updateBusinessOrder",method=RequestMethod.POST)
	@ResponseBody
	void updateBusinessOrder(@RequestBody BusinessOrderDTO businessOrderDTO){
		transactionService.updateBusinessOrder(businessOrderDTO);
	};

	@RequestMapping(value = "/getUserIncomeRecordInfo",method=RequestMethod.POST)
	@ResponseBody
	List<IncomeRecordDTO> getUserIncomeRecordInfo(@RequestBody IncomeRecordDTO incomeRecordDTO){
		return incomeService.getUserIncomeRecordInfo(incomeRecordDTO);
	};

	@RequestMapping(value = "/getPayRecordInfo",method=RequestMethod.POST)
	@ResponseBody
	List<PayRecordDTO> getPayRecordInfo(@RequestBody PayRecordDTO payRecordDTO){
		return payRecordService.getUserPayRecordList(payRecordDTO);
	};

	@RequestMapping(value = "/updatePayRecordStatus",method=RequestMethod.POST)
	@ResponseBody
	void updatePayRecordStatus(@RequestBody PayRecordDTO payRecordDTO){
		payRecordService.updatePayRecordStatus(payRecordDTO);
	};

	@RequestMapping(value = "/getUserBusinessType",method=RequestMethod.POST)
	@ResponseBody
	List<UserBusinessTypeDTO> getUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO)
	{
		return userTypeMapper.getUserBusinessType(userBusinessTypeDTO);
	}

	@RequestMapping(value = "/insertUserBusinessType",method=RequestMethod.POST)
	@ResponseBody
	void insertUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO)
	{
		userTypeMapper.insertUserBusinessType(userBusinessTypeDTO);
	}

	@RequestMapping(value = "/updateUserBusinessType",method=RequestMethod.POST)
	@ResponseBody
	void updateUserBusinessType(@RequestBody UserBusinessTypeDTO userBusinessTypeDTO)
	{
		userTypeMapper.updateUserBusinessType(userBusinessTypeDTO);
	}

	@RequestMapping(value = "/getMonthTransactionRecordByUserId",method=RequestMethod.GET)
	@ResponseBody
	List<MonthTransactionRecordDTO> getMonthTransactionRecordByUserId(@RequestParam String userId,
																	  @RequestParam String startDate,
																	  @RequestParam String endDate){
		return transactionMapper.getMonthTransactionRecordByUserId(userId, startDate,endDate);
	};

	@RequestMapping(value = "/getUserPayRecordListByDate",method=RequestMethod.GET)
	@ResponseBody
	List<PayRecordDTO> getUserPayRecordListByDate(@RequestParam String userId,
												  @RequestParam String startDate,
												  @RequestParam String endDate){
		return payRecordService.getUserPayRecordListByDate(userId,startDate,endDate);
	};

	@RequestMapping(value = "/insertUserIncomeInfo",method=RequestMethod.POST)
	@ResponseBody
	void insertUserIncomeInfo(@RequestBody IncomeRecordDTO incomeRecordDTO){
		incomeService.insertUserIncomeInfo(incomeRecordDTO);
	};

	@RequestMapping(value = "/getUserAccountInfo",method=RequestMethod.GET)
	@ResponseBody
	AccountDTO getUserAccountInfo(@RequestParam String userId){
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setSysUserId(userId);
		return accountService.getUserAccountInfo(accountDTO).get(0);
	};

	@RequestMapping(value = "/updateUserAccountInfo",method=RequestMethod.POST)
	@ResponseBody
	void updateUserAccountInfo(@RequestBody AccountDTO accountDTO){
		accountService.updateUserAccountInfo(accountDTO);
	};

	@RequestMapping(value = "/getUserPayRecordList",method=RequestMethod.POST)
	@ResponseBody
	List<PayRecordDTO> getUserPayRecordList(@RequestBody PayRecordDTO payRecordDTO){
		return payRecordService.getUserPayRecordList(payRecordDTO);
	};

	@RequestMapping(value = "/getBusinessOrderByOrderId",method=RequestMethod.GET)
	@ResponseBody
	BusinessOrderDTO getBusinessOrderByOrderId(@RequestParam String orderId){
		return transactionMapper.getBusinessOrderByOrderId(orderId);
	};

	@RequestMapping(value = "/queryOrderDetailsById",method=RequestMethod.GET)
	@ResponseBody
	BusinessOrderDTO queryOrderDetailsById(@RequestParam String orderId){
		return transactionMapper.queryOrderDetailsById(orderId);
	};

	@RequestMapping(value = "/updateIncomeInfo",method=RequestMethod.POST)
	@ResponseBody
	void updateIncomeInfo(@RequestBody IncomeRecordDTO incomeRecord){
		incomeService.updateIncomeInfo(incomeRecord);
	};

	@RequestMapping(value = "/createUserAccount",method=RequestMethod.POST)
	void createUserAccount(@RequestBody AccountDTO accountDTO)
	{
		accountService.createUserAccount(accountDTO);
	}

	@RequestMapping(value = "/selectIncomeInstanceByUserId",method=RequestMethod.GET)
	@ResponseBody
	String selectIncomeInstanceByUserId(@RequestParam String userId)
	{
		return incomeService.selectIncomeInstanceByUserId(userId);
	}

	@RequestMapping(value = "/getIncomeRecordManagement",method=RequestMethod.POST)
	@ResponseBody
	List<IncomeRecordManagementDTO> getIncomeRecordManagement(@RequestBody IncomeRecordManagementDTO incomeRecordManagementDTO)
	{
		return incomeService.getIncomeRecordManagement(incomeRecordManagementDTO);
	}

}
