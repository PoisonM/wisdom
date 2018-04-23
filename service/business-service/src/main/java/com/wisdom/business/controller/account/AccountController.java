package com.wisdom.business.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.business.constant.OrderStatus;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.dto.product.ProductDTO;
import com.wisdom.common.dto.system.*;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.excel.ExportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "account")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private TransactionService transactionService;

	/**
	 * 获取用户的账户信息
	 */
	@RequestMapping(value = "getUserAccountInfo", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<AccountDTO> getUserAccountInfo() {

		logger.info("用户获取账户信息===" + new Date());

		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		ResponseDTO<AccountDTO> result = new ResponseDTO<>();

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setSysUserId(userInfoDTO.getId());
		List<AccountDTO> accountDTOS = accountService.getUserAccountInfo(accountDTO);

		//如果用户没有账户，则为用户创建一个账户
		if(accountDTOS.size()==0)
		{
			logger.info("用户之前没有账户，创建一个新账户==" + userInfoDTO.getMobile());

			accountDTO = new AccountDTO();

			//为用户新建一个账户
			accountDTO.setId(UUID.randomUUID().toString());
			accountDTO.setSysUserId(userInfoDTO.getId());
			accountDTO.setUserOpenId(userInfoDTO.getUserOpenid());
			accountDTO.setBalance((float)0.00);
			accountDTO.setScore((float)0.00);
			accountDTO.setStatus("normal");
			accountDTO.setUpdateDate(new Date());
			accountDTO.setBalanceDeny((float)0.00);
			accountService.createUserAccount(accountDTO);
		}
		else
		{
			accountDTO = accountDTOS.get(0);
			logger.info("用户已经有账户，获取当前账户信息==" + accountDTO);
		}

		IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
		incomeRecordDTO.setSysUserId(userInfoDTO.getId());
		incomeRecordDTO.setUpdateDate(new Date());
		List<IncomeRecordDTO> incomeRecordDTOS = incomeService.getUserIncomeInfoByDate(userInfoDTO.getId(), new Date());
		float todayIncome = 0;
		for(IncomeRecordDTO incomeRecord:incomeRecordDTOS)
		{
			todayIncome = todayIncome + incomeRecord.getAmount();
		}
		accountDTO.setTodayIncome(todayIncome);
		accountDTO.setIdentifyNumber(userInfoDTO.getIdentifyNumber());

		logger.info(userInfoDTO.getMobile()+"用户获取到今天的收益==="+todayIncome);

		Query query = new Query(Criteria.where("sysUserId").is(userInfoDTO.getId()));
		List<UserBankCardInfoDTO> userBankCardInfoDTOS = mongoTemplate.find(query,UserBankCardInfoDTO.class,"userBankCardInfo");
		if(userBankCardInfoDTOS.size()>0)
		{
			accountDTO.setBankCardInfo(userBankCardInfoDTOS.get(0));
		}

		List<Integer> OrderStatusCountList = new ArrayList<>();
		for (OrderStatus status : OrderStatus.values()) {
			List<BusinessOrderDTO> businessOrderDTOList =  transactionService.getBusinessOrderListByUserIdAndStatus(userInfoDTO.getId(),status.getValue());
			OrderStatusCountList.add(businessOrderDTOList.size());
		}
		accountDTO.setOrderStatusCountList(OrderStatusCountList);
		logger.info(userInfoDTO.getMobile()+"用户获取所有订单的状态=="+JSONObject.toJSONString(OrderStatusCountList));

		result.setResponseData(accountDTO);
		result.setResult(StatusConstant.SUCCESS);
		return result;
	}

	/**
	 * 查询所有用户及余额
	 * @param pageParamDTO
	 * @return
	 */
	@RequestMapping(value = "queryAllUserBalance", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<AccountDTO>>> queryAllUserBalance(@RequestBody PageParamDTO pageParamDTO){
		ResponseDTO<PageParamDTO<List<AccountDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<AccountDTO>> page = accountService.queryAllUserBalance(pageParamDTO);
		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 根据条件查询余额信息
	 * @param phoneAndIdentify 手机号或者身份证号
	 * @return
	 */
	@RequestMapping(value = "queryUserBalanceByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<AccountDTO>>>  queryUserBalanceByParameters(@RequestParam String phoneAndIdentify,
																			  @RequestParam String isExportExcel,
																			  @RequestParam Integer pageNo,
																			  @RequestParam Integer pageSize) {
		ResponseDTO<PageParamDTO<List<AccountDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<AccountDTO>> page = accountService.queryUserBalanceByParameters(phoneAndIdentify,isExportExcel,pageNo,pageSize);
		if("Y".equals(isExportExcel)){
			try{
				String[] orderHeaders = {"用户ID","用户名","用户等级","手机号", "账户余额"};
				ExportExcel<ExportAccountExcelDTO> ex =new ExportExcel<>();
				List<ExportAccountExcelDTO> excelList= new ArrayList<>();
				for(AccountDTO accountDTO : page.getResponseData()){
					ExportAccountExcelDTO exportAccountExcelDTO = new ExportAccountExcelDTO();
					exportAccountExcelDTO.setBalance(accountDTO.getBalance());
					exportAccountExcelDTO.setMobile(accountDTO.getMobile());
					exportAccountExcelDTO.setNickName(accountDTO.getNickName());
					exportAccountExcelDTO.setSysUserId(accountDTO.getSysUserId());
					exportAccountExcelDTO.setUserType(accountDTO.getUserType());
					excelList.add(exportAccountExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("账户余额EXCEL文档",orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			}catch (Exception e){
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
			return responseDTO;
		}
		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 根据条件查询账单信息
	 * @return
	 */
	@RequestMapping(value = "queryPayRecordsByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<PayRecordDTO>>>  queryPayRecordsByParameters(@RequestBody PageParamVoDTO<ProductDTO> pageParamVoDTO) {
		ResponseDTO<PageParamDTO<List<PayRecordDTO>>> responseDTO = new ResponseDTO<>();
		String startDate = "1990-01-01";//设定起始时间
		if (!"0".equals(pageParamVoDTO.getTimeType())){
			pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
			pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
		}
		PageParamDTO<List<PayRecordDTO>> page = transactionService.queryPayRecordsByParameters(pageParamVoDTO);
		if("Y".equals(pageParamVoDTO.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"用户id", "用户名", "手机号", "付款金额", "付款时间", "完成时间", "账单编号", "订单编号"};
				ExportExcel<ExportPayRecordExcelDTO> ex = new ExportExcel<>();
				List<ExportPayRecordExcelDTO> excelList = new ArrayList<>();
				for (PayRecordDTO payRecordDTO : page.getResponseData()) {
					ExportPayRecordExcelDTO exportPayRecordExcelDTO = new ExportPayRecordExcelDTO();
					exportPayRecordExcelDTO.setAmount(payRecordDTO.getAmount());
					exportPayRecordExcelDTO.setMobile(payRecordDTO.getMobile());
					exportPayRecordExcelDTO.setNickName(payRecordDTO.getNickName());
					exportPayRecordExcelDTO.setOrderId(payRecordDTO.getOrderId());
					exportPayRecordExcelDTO.setPayDate(DateUtils.formatDate(payRecordDTO.getPayDate(),"yyyy-MM-dd HH:mm:ss"));
					exportPayRecordExcelDTO.setSysUserId(payRecordDTO.getSysUserId());
					exportPayRecordExcelDTO.setTransactionId(payRecordDTO.getTransactionId());
					exportPayRecordExcelDTO.setUpdateDate(DateUtils.formatDate(payRecordDTO.getUpdateDate(),"yyyy-MM-dd HH:mm:ss"));
					excelList.add(exportPayRecordExcelDTO);

				}
				ByteArrayInputStream in = ex.getWorkbookIn("账单EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			} catch (Exception e) {
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
		}
		responseDTO.setResponseData(page);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		return responseDTO;
	}
}
