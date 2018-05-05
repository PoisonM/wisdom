package com.wisdom.business.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.wisdom.business.constant.OrderStatus;
import com.wisdom.business.service.account.AccountService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.TransactionService;
import com.wisdom.business.service.transaction.PayRecordService;
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
import com.wisdom.common.dto.specialShop.SpecialShopInfoDTO;
import com.wisdom.common.dto.specialShop.SpecialShopBusinessOrderDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.excel.ExportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.*;

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

	@Autowired
	private PayRecordService payRecordService;

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

	/**
	 * 判断当前用户是否是店主
	 *
	 * */
	@RequestMapping(value = "isShopKeeper", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Integer> isShopKeeper(){
		logger.info("用户获取账户信息===" + new Date());

		//获取当前登录用户信息
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		String phone = userInfoDTO.getMobile();
		SpecialShopInfoDTO specialShopInfoDTO = new SpecialShopInfoDTO();
		specialShopInfoDTO.setShopBossMobile(phone);

		Query query = new Query(Criteria.where("shopBossMobile").is(specialShopInfoDTO.getShopBossMobile()));
		List<SpecialShopInfoDTO> specialShopInfoDTOS = mongoTemplate.find(query,SpecialShopInfoDTO.class,"specialShopInfo");

		ResponseDTO<Integer> responseDTO = new ResponseDTO<>();
		responseDTO.setResult(StatusConstant.SUCCESS);
		if(!specialShopInfoDTOS.isEmpty()){
			responseDTO.setResponseData(specialShopInfoDTOS.size());
		}else{
			responseDTO.setResponseData(0);
		}
		return responseDTO;
	}

	/**
	 * 查询当前登录用户店铺的交易明细
	 *
	 * */
	@RequestMapping(value = "findShopKeeperOrderS", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<SpecialShopBusinessOrderDTO>> findShopKeeperOrderS(@RequestBody PageParamVoDTO<SpecialShopBusinessOrderDTO> pageParamVoDTO){
		logger.info("用户获取账户信息===" + new Date());

		long startTime = System.currentTimeMillis();
		//获取当前登录用户信息
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		String phone = userInfoDTO.getMobile();

		SpecialShopInfoDTO specialShopInfoDTO = new SpecialShopInfoDTO();
		specialShopInfoDTO.setShopBossMobile(phone);

		//获取用户下店铺信息
		Query query = new Query(Criteria.where("shopBossMobile").is(specialShopInfoDTO.getShopBossMobile()));
		List<SpecialShopInfoDTO> specialShopInfoDTOS = mongoTemplate.find(query,SpecialShopInfoDTO.class,"specialShopInfo");

		//声明一个该用户下所有订单list
		List<SpecialShopBusinessOrderDTO>  specialShopBusinessOrderDTOSZ = new ArrayList();

		//判断该用户是否店铺
		if(!specialShopInfoDTOS.isEmpty()){

			for(int i=0 ;i<specialShopInfoDTOS.size();i++){

				//根据店铺查询店铺下的订单
				SpecialShopBusinessOrderDTO  specialShopBusinessOrderDTO = new SpecialShopBusinessOrderDTO();
				specialShopBusinessOrderDTO.setShopId(specialShopInfoDTOS.get(i).getShopId());
				Query queryOrder = new Query(Criteria.where("shopId").is(specialShopInfoDTOS.get(i).getShopId()));
				queryOrder.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createDate")));
				List<SpecialShopBusinessOrderDTO> specialShopBusinessOrderDTOS = mongoTemplate.find(queryOrder,SpecialShopBusinessOrderDTO.class,"specialShopBusinessOrder");

				//将所有订单放入一个list里面
				for(int j=0;j<specialShopBusinessOrderDTOS.size();j++){

					List<PayRecordDTO>  payRecordDTOS = payRecordService.findOrderInfoForSpecial(specialShopBusinessOrderDTOS.get(j).getOrderId());

					logger.info("交易订单号为："+specialShopBusinessOrderDTOS.get(j).getOrderId());
					SpecialShopBusinessOrderDTO specialShopBusinessOrderDTO1 = new SpecialShopBusinessOrderDTO();
					specialShopBusinessOrderDTO1 = specialShopBusinessOrderDTOS.get(j);

					if(payRecordDTOS != null&&payRecordDTOS.size()>0){
						Float account=(float)0 ;
						for (int m=0;m<payRecordDTOS.size();m++){
							account = account+payRecordDTOS.get(m).getAmount();
						}
						specialShopBusinessOrderDTO1.setAccount(String.valueOf(account));

						logger.info("交易金额为："+account);
					}

					specialShopBusinessOrderDTOSZ.add(specialShopBusinessOrderDTO1);

				}
			}
		}

		List<SpecialShopBusinessOrderDTO> responeDateList = new ArrayList<>();

		//将获取的list进行分页
		List<SpecialShopBusinessOrderDTO> responeList = accountService.pagerUtil(specialShopBusinessOrderDTOSZ,responeDateList,pageParamVoDTO.getPageNo(),pageParamVoDTO.getPageSize());

		logger.info("返回结果为：{}",responeList);

		logger.info("获取该店铺订单列表耗时{}毫秒", (System.currentTimeMillis() - startTime));

		ResponseDTO<List<SpecialShopBusinessOrderDTO>> responseDTO = new ResponseDTO<>();
		if(responeList!=null&&responeList.size()>0){

			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setResponseData(responeList);
		}else{

			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("无更多交易记录");
		}

		return responseDTO;
	}

	/**
	 *
	 *
	 * 根据交易流水查询所有订单
	 *
	 * */
	@RequestMapping(value = "findOrderByTransactionId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<PayRecordDTO>> findOrderByTransactionId(@RequestParam String orderId){

		logger.info("交易订单号为={}",orderId);
		long startTime = System.currentTimeMillis();

		List<PayRecordDTO>  payRecordDTOS = payRecordService.findOrderInfoForSpecial(orderId);

		logger.info("获取该交易流水号下订单列表耗时{}毫秒", (System.currentTimeMillis() - startTime));

		Float paySum=(float)0;
		if(payRecordDTOS!=null&&payRecordDTOS.size()>0){
			for(int i =0;i<payRecordDTOS.size();i++){
				paySum = paySum + payRecordDTOS.get(i).getAmount();

			}
		}

		ResponseDTO<List<PayRecordDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(payRecordDTOS);
		return responseDTO;
	}


	/***
	 * 查询用户是否登录
	 *
	 * */
	@RequestMapping(value = "isLogin", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<String> isLogin(){

		ResponseDTO<String> responseDTO = new ResponseDTO<>();
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		if(userInfoDTO !=null) {
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setResponseData("success");
		}else{
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setResponseData("failure");
		}

		return responseDTO;
	}


}
