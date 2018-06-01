package com.wisdom.business.controller.account;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.account.IncomeRecordManagementService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.*;
import com.wisdom.common.dto.system.ExportIncomeRecordExcelDTO;
import com.wisdom.common.dto.system.ExportNextUserInfoExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.StringUtils;
import com.wisdom.common.util.UUIDUtil;
import com.wisdom.common.util.excel.ExportExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "income")
public class IncomeController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private PayRecordService payRecordService;

	@Autowired
	private IncomeRecordManagementService incomeRecordManagementService;

	@Autowired
	private UserServiceClient userServiceClient;

	/**
	 * 根据条件查询用户佣金奖励new
	 * @param pageParamVoDTO
	 * @return
	 */
	@RequestMapping(value = "getIncomeRecordByPageParam", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> getIncomeRecordByPageParam(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("根据状态查询返利数据传入参数={}", "pageParamVoDTO = [" + pageParamVoDTO + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		int count = 0;

		//设定起始时间
		String startDate = "1990-01-01";
		pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
		pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));

		//审核状态,已审核/未审核
		String checkStatus = "";
		if (null != pageParamVoDTO.getRequestData()) {
			checkStatus = pageParamVoDTO.getRequestData().getCheckStatus();
		}

		//如果checkStauts不为空 则说明用户查询已审核或未审核状态
		if (StringUtils.isNotBlank(checkStatus)) {
			UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
			if(userInfoDTO == null){
				logger.info("条件查询用户佣金奖励从Redis获取用户信息失败={}");
				responseDTO.setResult("获取用户信息失败");
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
				return responseDTO;
			}
			//插入操作人的类型,运营人员/财务人员,根据此条件查询相应结果
			pageParamVoDTO.getRequestData().setCheckUserType(userInfoDTO.getUserType());
		}

		List<IncomeRecordDTO> incomeRecordDTOS = incomeService.getIncomeRecordByPageParam(pageParamVoDTO);

		if (CommonUtils.objectIsEmpty(incomeRecordDTOS)) {
			logger.info("佣金数据incomeRecord数据为空");
		}

		if (StringUtils.isNotBlank(checkStatus)) {
			count = incomeService.getIncomeRecordCountByIncomeManagement(pageParamVoDTO);
		}else {
			count = incomeService.getIncomeRecordCountByPageParam(pageParamVoDTO);
		}
		logger.info("根据条件查询用户奖励总数Count" + count);
		Map<String,Object> map=new HashMap<>(16);
		map.put("incomeRecordDTOS",incomeRecordDTOS);
		map.put("count",count);

		responseDTO.setResponseData(map);
		responseDTO.setResult("根据条件查询用户佣金奖励成功");
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 根据id查询用户奖励详情new
	 * @param incomeId
	 * @return
	 */
	@RequestMapping(value = "queryIncomeInfoByIncomeId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> queryIncomeInfoByIncomeId(@RequestParam String incomeId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据id查询用户奖励参数={}", "incomeId = [" + incomeId + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		Map<String,Object> map=new HashMap<>(16);

		PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO = new PageParamVoDTO();
		IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
		incomeRecordDTO.setId(incomeId);
		pageParamVoDTO.setRequestData(incomeRecordDTO);
		List<IncomeRecordDTO> incomeRecordDTOS = incomeService.queryIncomeByParameters(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(incomeRecordDTOS)){
			logger.info("佣金下拉详情上下级关系数据incomeRecord数据为空");
		}
		List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(incomeRecordDTOS.get(0).getTransactionId());
		if(CommonUtils.objectIsEmpty(incomeRecordDTOS)){
			logger.info("佣金下拉详情订单数据businessOrder数据为空");
		}

		map.put("user",incomeRecordDTOS);//收益和消费人
		map.put("IncomeList",businessOrderDTOS);//订单
		responseDTO.setResult("根据id查询用户奖励详情成功");
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 佣金奖励审核new
	 * @param status 状态审核通过或不通过
	 * @param incomeRecordId     审核id
	 * @return
	 */
	@RequestMapping(value = "checkIncomeRecordManagement", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> checkIncomeRecordManagement(@RequestParam String incomeRecordId,String status) {
		long startTime = System.currentTimeMillis();
		//logger.info("佣金奖励审核传入参数={}", "incomeRecordId = [" + incomeRecordId + "],status = [" + status + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		if("".equals(incomeRecordId) || incomeRecordId == null){
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
			responseDTO.setResult("incomeRecordId为空");
			logger.info("佣金审核接口传入参数incomeRecordId为空");
		}
		//获取登录人信息
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		if(userInfoDTO == null) {
			logger.info("佣金奖励审核接口从Redis获取用户信息失败={}");
			responseDTO.setResult("获取用户信息失败");
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
			return responseDTO;
		}
		if("finance-1".equals(userInfoDTO.getUserType()) && "operation-1".equals(userInfoDTO.getUserType())){
			logger.info("当前用户既不是运营人员又不是财务人,所以拒绝审核");
			responseDTO.setResult("当前用户不是相关人员,拒绝审核");
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
			return responseDTO;
		}
		//查询审核记录
		IncomeRecordManagementDTO incomeRecordManagementDTO1 =new IncomeRecordManagementDTO();
		incomeRecordManagementDTO1.setIncomeRecordId(incomeRecordId);
		incomeRecordManagementDTO1.setUserType(userInfoDTO.getUserType());
//		incomeRecordManagementDTO1.setSysUserId(userInfoDTO.getId());
		List<IncomeRecordManagementDTO> incomeRecordManagementDTOS = incomeRecordManagementService.getIncomeRecordManagement(incomeRecordManagementDTO1);

		//如已有审核记录
		if(incomeRecordManagementDTOS.size()>0){
			//已有的审核记录状态与本次相同
			responseDTO.setResult("已有相关人员审核,不能重复修改");
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			return responseDTO;
		}
		//查询此条数据有没有被审核
		/*IncomeRecordManagementDTO incomeRecordManagementDTO2 =new IncomeRecordManagementDTO();
		incomeRecordManagementDTO2.setIncomeRecordId(incomeRecordId);
		List<IncomeRecordManagementDTO> incomeRecordManagementDTOS2 = incomeRecordManagementService.getIncomeRecordManagement(incomeRecordManagementDTO2);

		//如果有数据,则审核状态为通过则说明此次审核为第二次审核,判断审核结果修改数据状态
		if(incomeRecordManagementDTOS2.size()>0 && "1".equals(incomeRecordManagementDTOS2.get(0).getStatus())){
			if("1".equals(status)){
				//审核通过,修改状态
				IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
				incomeRecordDTO.setStatus(status);
				incomeService.updateIncomeRecord(incomeRecordDTO);
			}

		}*/
		//第一次审核,创建审核记录,向incomeRecordManagement表中插入数据
		IncomeRecordManagementDTO incomeRecordManagementDTO = new IncomeRecordManagementDTO();
		incomeRecordManagementDTO.setId(UUIDUtil.getUUID());
		incomeRecordManagementDTO.setSysUserId(userInfoDTO.getId());
		incomeRecordManagementDTO.setIncomeRecordId(incomeRecordId);
		incomeRecordManagementDTO.setUserName(userInfoDTO.getNickname());
		incomeRecordManagementDTO.setUserType(userInfoDTO.getUserType());
		incomeRecordManagementDTO.setCreateDate(new Date());
		incomeRecordManagementDTO.setStatus(status);
		incomeRecordManagementService.insertIncomeRecordManagement(incomeRecordManagementDTO);
		responseDTO.setResult("审核成功");
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 佣金即时导表New
	 * @param
	 * @return
	 */
	@RequestMapping(value = "exportExcelIncomeRecord", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> exportExcelIncomeRecord(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("根据状态查询返利数据传入参数={}", "pageParamVoDTO = [" + pageParamVoDTO + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		//设定起始时间
		String startDate = "1990-01-01";
		pageParamVoDTO.setStartTime("".equals(pageParamVoDTO.getStartTime()) ? startDate : pageParamVoDTO.getStartTime());
		pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
		//审核状态,已审核/未审核
		String checkStatus = "";
		if (null != pageParamVoDTO.getRequestData()) {
			checkStatus = pageParamVoDTO.getRequestData().getCheckStatus();
		}
		//如果checkStauts不为空 则说明用户查询已审核或未审核状态
		if (StringUtils.isNotBlank(checkStatus)) {
			UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
			if(userInfoDTO == null){
				logger.info("佣金导表从Redis获取用户信息失败={}");
				responseDTO.setResult("获取用户信息失败");
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
				return responseDTO;
			}
			//插入操作人的类型,运营人员/财务人员,根据此条件查询相应结果
			pageParamVoDTO.getRequestData().setCheckUserType(userInfoDTO.getUserType());
		}
		List<ExportIncomeRecordExcelDTO> exportIncomeRecordExcelDTOS = incomeService.exportExcelIncomeRecord(pageParamVoDTO);
		try {
			String[] orderHeaders = {"用户id", "用户名", "用户手机号", "用户获益时等级", "用户现在等级", "佣金金额",
					"下级用户id", "下级用户名", "下级用户手机号", "下级用户等级", "下级用户现在等级",
					"交易id", "支付时间", "订单id", "支付金额", "订单状态", "上下级关系","升级消费"};
			ExportExcel<ExportIncomeRecordExcelDTO> ex = new ExportExcel<>();
			ByteArrayInputStream in = ex.getWorkbookIn("佣金奖励EXCEL文档", orderHeaders, exportIncomeRecordExcelDTOS);
			String url = CommonUtils.orderExcelToOSS(in);
			if ("".equals(url) && url == null){
				logger.info("佣金奖励Excel 获取OSSUrl为空");
			}
			responseDTO.setResult(url);
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			//return responseDTO;
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
		}

		logger.info("导表耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 月度奖励详情导出Excel  new
	 * @param pageParamVoDTO 用户id  param self
	 * @return
	 */
	@RequestMapping(value = "exportExcelMonthTransactionRecordByUserId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> exportExcelMonthTransactionRecordByUserId(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		if(pageParamVoDTO.getRequestData() == null){
			logger.info("查询月度奖励详情传入对象为null={}", "RequestData.sysUserId = [" + pageParamVoDTO.getRequestData().getSysUserId() + "]");
		}
		List<MonthTransactionRecordDTO> selfList1 = new ArrayList<>();
		List<MonthTransactionRecordDTO> nextList1 = new ArrayList<>();

		//先查出所有本人的,再查出下级的
		pageParamVoDTO.getRequestData().setParentRelation("self");
		List<MonthTransactionRecordDTO> selfList = incomeService.queryMonthRecordByParentRelation(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(selfList)){
			logger.info("月度本人详情数据selfList数据为空");
		}
		for(MonthTransactionRecordDTO monthTransactionRecordDTO : selfList){
			List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
			for(BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
                MonthTransactionRecordDTO monthDTO = new MonthTransactionRecordDTO();
                BeanUtils.copyProperties(monthTransactionRecordDTO, monthDTO);
                monthDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
                monthDTO.setOrderAmount(businessOrderDTO.getAmount());
                monthDTO.setOrderStatus(businessOrderDTO.getStatus());
                monthDTO.setPayDate(businessOrderDTO.getPayDate());
				selfList1.add(monthDTO);
			}
		}
		pageParamVoDTO.getRequestData().setParentRelation("other");
		List<MonthTransactionRecordDTO> nextList = incomeService.queryMonthRecordByParentRelation(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(nextList)){
			logger.info("月度下级详情数据nextList数据为空");
		}
		for(MonthTransactionRecordDTO monthTransactionRecordDTO : nextList){
			List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
			for(BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
                MonthTransactionRecordDTO monthDTO = new MonthTransactionRecordDTO();
                BeanUtils.copyProperties(monthTransactionRecordDTO, monthDTO);
                monthDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
                monthDTO.setOrderAmount(businessOrderDTO.getAmount());
                monthDTO.setOrderStatus(businessOrderDTO.getStatus());
                monthDTO.setPayDate(businessOrderDTO.getPayDate());
				nextList1.add(monthDTO);
			}
		}
		if("Y".equals(pageParamVoDTO.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"用户id", "用户名", "用户手机号", "用户获益时等级", "用户现在等级", "佣金金额",
						"下级用户id", "下级用户名", "下级用户手机号", "下级生成订单等级", "下级用户现在等级",
						"交易id", "支付时间", "订单id", "订单金额", "订单状态"};
				ExportExcel<ExportIncomeRecordExcelDTO> ex = new ExportExcel<>();
				List<ExportIncomeRecordExcelDTO> excelList = new ArrayList<>();
				for (MonthTransactionRecordDTO monthTransactionRecordDTO : selfList1) {
						ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
						exportIncomeRecordExcelDTO.setUserType(monthTransactionRecordDTO.getUserType());
						exportIncomeRecordExcelDTO.setUserTypeNow(monthTransactionRecordDTO.getUserTypeNow());
						exportIncomeRecordExcelDTO.setSysUserId(monthTransactionRecordDTO.getUserId());
						exportIncomeRecordExcelDTO.setNickName(monthTransactionRecordDTO.getNickName());
						exportIncomeRecordExcelDTO.setMobile(monthTransactionRecordDTO.getMobile());
						exportIncomeRecordExcelDTO.setAmount(monthTransactionRecordDTO.getAmountMoney());
						exportIncomeRecordExcelDTO.setNextUserId(monthTransactionRecordDTO.getNextUserId());
						exportIncomeRecordExcelDTO.setNextUserMobile(monthTransactionRecordDTO.getNextUserMobile());
						exportIncomeRecordExcelDTO.setNextUserNickName(monthTransactionRecordDTO.getNextUserNickName());
						exportIncomeRecordExcelDTO.setNextUserType(monthTransactionRecordDTO.getNextUserType());
						exportIncomeRecordExcelDTO.setNextUserTypeNow(monthTransactionRecordDTO.getNextUserTypeNow());
						exportIncomeRecordExcelDTO.setOrderAmount(monthTransactionRecordDTO.getOrderAmount());
						exportIncomeRecordExcelDTO.setOrderId(monthTransactionRecordDTO.getOrderId());
						exportIncomeRecordExcelDTO.setOrderStatus(monthTransactionRecordDTO.getOrderStatus());
						exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(monthTransactionRecordDTO.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
						exportIncomeRecordExcelDTO.setTransactionId(monthTransactionRecordDTO.getTransactionId());
						excelList.add(exportIncomeRecordExcelDTO);
				}
				for (MonthTransactionRecordDTO monthTransactionRecordDTO : nextList1) {
						ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
						exportIncomeRecordExcelDTO.setUserType(monthTransactionRecordDTO.getUserType());
						exportIncomeRecordExcelDTO.setUserTypeNow(monthTransactionRecordDTO.getUserTypeNow());
						exportIncomeRecordExcelDTO.setSysUserId(monthTransactionRecordDTO.getUserId());
						exportIncomeRecordExcelDTO.setNickName(monthTransactionRecordDTO.getNickName());
						exportIncomeRecordExcelDTO.setMobile(monthTransactionRecordDTO.getMobile());
						exportIncomeRecordExcelDTO.setAmount(monthTransactionRecordDTO.getAmountMoney());
						exportIncomeRecordExcelDTO.setNextUserId(monthTransactionRecordDTO.getNextUserId());
						exportIncomeRecordExcelDTO.setNextUserMobile(monthTransactionRecordDTO.getNextUserMobile());
						exportIncomeRecordExcelDTO.setNextUserNickName(monthTransactionRecordDTO.getNextUserNickName());
						exportIncomeRecordExcelDTO.setNextUserType(monthTransactionRecordDTO.getNextUserType());
						exportIncomeRecordExcelDTO.setNextUserTypeNow(monthTransactionRecordDTO.getNextUserTypeNow());
						exportIncomeRecordExcelDTO.setOrderAmount(monthTransactionRecordDTO.getOrderAmount());
						exportIncomeRecordExcelDTO.setOrderId(monthTransactionRecordDTO.getOrderId());
						exportIncomeRecordExcelDTO.setOrderStatus(monthTransactionRecordDTO.getOrderStatus());
						exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(monthTransactionRecordDTO.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
						exportIncomeRecordExcelDTO.setTransactionId(monthTransactionRecordDTO.getTransactionId());
						excelList.add(exportIncomeRecordExcelDTO);
				}

				HashMap<String, String> helperMap = new HashMap<>(16);
				if (CommonUtils.objectIsNotEmpty(excelList)) {
					for (ExportIncomeRecordExcelDTO excelDTO : excelList) {
						if (StringUtils.isBlank(helperMap.get(excelDTO.getTransactionId()))) {
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(1));
						} else {
							int count = Integer.parseInt(helperMap.get(excelDTO.getTransactionId())) + 1;
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(count));
						}
					}

					for (ExportIncomeRecordExcelDTO excelDTO : excelList) {
						Integer count = Integer.parseInt(helperMap.get(excelDTO.getTransactionId()));
						if (count > 1) {
							excelDTO.setAmount(0);
							int number = Integer.parseInt(helperMap.get(excelDTO.getTransactionId())) - 1;
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(number));
						}
					}
				}


				ByteArrayInputStream in = ex.getWorkbookIn("账单EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				//return responseDTO;
			} catch (Exception e) {
				responseDTO.setResult("月度详情导出Excel异常");
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
				logger.error("月度详情导出Excel异常");
				e.printStackTrace();
			}
		}
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 查询月度奖励本人消费详情new
	 * @param pageParamVoDTO 用户id  param self
	 * @return
	 */
	@RequestMapping(value = "selectSelfMonthTransactionRecordByUserId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> selectSelfMonthTransactionRecordByUserId(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		if(pageParamVoDTO.getRequestData() == null){
			logger.info("查询月度奖励详情传入对象为null={}", "RequestData.sysUserId = [" + pageParamVoDTO.getRequestData().getSysUserId() + "]");
		}
		List<MonthTransactionRecordDTO> selfList1 = new ArrayList<>();
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		Map<String,Object> map=new HashMap<>(16);

		pageParamVoDTO.getRequestData().setParentRelation("self");
		int selfCount = incomeService.queryMonthRecordCountByParentRelation(pageParamVoDTO);
		List<MonthTransactionRecordDTO> selfList = incomeService.queryMonthRecordByParentRelation(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(selfList)) logger.info("月度本人详情数据selfList数据为空");
		for(MonthTransactionRecordDTO monthTransactionRecordDTO : selfList){
			List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
			for(BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
                MonthTransactionRecordDTO monthDTO = new MonthTransactionRecordDTO();
                BeanUtils.copyProperties(monthTransactionRecordDTO, monthDTO);
                monthDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
                monthDTO.setOrderAmount(businessOrderDTO.getAmount());
                monthDTO.setOrderStatus(businessOrderDTO.getStatus());
                monthDTO.setPayDate(businessOrderDTO.getPayDate());
				selfList1.add(monthDTO);
			}
		}
		map.put("selfCount",selfCount);
		map.put("selfList",selfList1);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
		responseDTO.setResult(StatusConstant.SUCCESS);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 查询月度奖励下级消费详情new
	 * @param pageParamVoDTO 用户id  param self
	 * @return
	 */
	@RequestMapping(value = "selectNextMonthTransactionRecordByUserId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> selectNextMonthTransactionRecordByUserId(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {
		long startTime = System.currentTimeMillis();
		if(pageParamVoDTO.getRequestData() == null){
			logger.info("查询月度奖励详情传入对象为null={}", "RequestData.sysUserId = [" + pageParamVoDTO.getRequestData().getSysUserId() + "]");
		}
		List<MonthTransactionRecordDTO> nextList1 = new ArrayList<>();
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		Map<String,Object> map=new HashMap<>(16);

		pageParamVoDTO.getRequestData().setParentRelation("other");
		int nextCount = incomeService.queryMonthRecordCountByParentRelation(pageParamVoDTO);
		List<MonthTransactionRecordDTO> nextList = incomeService.queryMonthRecordByParentRelation(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(nextList)){
			logger.info("月度下级详情数据selfList数据为空");
		}
		for(MonthTransactionRecordDTO monthTransactionRecordDTO : nextList){
			List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
			for(BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
				MonthTransactionRecordDTO monthDTO = new MonthTransactionRecordDTO();
				BeanUtils.copyProperties(monthTransactionRecordDTO, monthDTO);
				monthDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
				monthDTO.setOrderAmount(businessOrderDTO.getAmount());
				monthDTO.setOrderStatus(businessOrderDTO.getStatus());
				monthDTO.setPayDate(businessOrderDTO.getPayDate());
				nextList1.add(monthDTO);
			}
		}
		map.put("nextCount",nextCount);
		map.put("nextList",nextList1);
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		responseDTO.setResponseData(map);
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}


	/**
	 * 查询店主推荐下级列表
	 * @param
	 *
	 * */
	@RequestMapping(value = "findNextUserInfoControl", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
    ResponseDTO<PageParamDTO<List<IncomeRecordDTOExt>>>  findNextUserInfoControl(@RequestBody PageParamVoDTO<PayRecordDTO> pageParamVoDTO){
		long startTime = System.currentTimeMillis();

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setParentUserId(pageParamVoDTO.getRequestData().getSysUserId());
		PageParamVoDTO<UserInfoDTO> userPageParamVoDTO = new PageParamVoDTO<UserInfoDTO>();
		userPageParamVoDTO.setPageNo(pageParamVoDTO.getPageNo());
		userPageParamVoDTO.setPageSize(pageParamVoDTO.getPageSize());
		userPageParamVoDTO.setRequestData(userInfoDTO);
		userPageParamVoDTO.setIsExportExcel(pageParamVoDTO.getIsExportExcel());
		PageParamDTO<List<IncomeRecordDTOExt>> page = incomeService.findNextUserInfo(userPageParamVoDTO);

		ResponseDTO<PageParamDTO<List<IncomeRecordDTOExt>>> responseDTO = new ResponseDTO<>();
		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));

		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;

	}

	/**
	 * 查询店主推荐下级列表
	 * @param
	 *
	 * */
	@RequestMapping(value = "exportNextUserInfoControl", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<IncomeRecordDTOExt>>>  exportNextUserInfoControl(@RequestBody PageParamVoDTO<PayRecordDTO> pageParamVoDTO){
		long startTime = System.currentTimeMillis();

		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setParentUserId(pageParamVoDTO.getRequestData().getSysUserId());
		PageParamVoDTO<UserInfoDTO> userPageParamVoDTO = new PageParamVoDTO<UserInfoDTO>();
		userPageParamVoDTO.setPageNo(pageParamVoDTO.getPageNo());
		userPageParamVoDTO.setPageSize(pageParamVoDTO.getPageSize());
		userPageParamVoDTO.setRequestData(userInfoDTO);
		userPageParamVoDTO.setIsExportExcel(pageParamVoDTO.getIsExportExcel());
		PageParamDTO<List<IncomeRecordDTOExt>> page = incomeService.findNextUserInfo(userPageParamVoDTO);

		ResponseDTO<PageParamDTO<List<IncomeRecordDTOExt>>> responseDTO = new ResponseDTO<>();
		List<ExportNextUserInfoExcelDTO>  exportNextUserInfoExcelDTOS = new ArrayList<>();
		List<IncomeRecordDTOExt> result =  page.getResponseData();

		UserInfoDTO userInfoP = new UserInfoDTO();
		UserInfoDTO userInfoG = new UserInfoDTO();
		userInfoP = userServiceClient.getUserInfoFromUserId(pageParamVoDTO.getRequestData().getSysUserId());
		if(userInfoP!=null){
			if(userInfoP.getParentUserId()!=null){
				userInfoG = userServiceClient.getUserInfoFromUserId(userInfoP.getParentUserId());
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		for(IncomeRecordDTOExt IncomeRecordDTO:result){
			ExportNextUserInfoExcelDTO exportNextUserInfoExcelDTO = new  ExportNextUserInfoExcelDTO();
			if(userInfoP!=null){
				exportNextUserInfoExcelDTO.setNickName(incomeService.decodeNickName(userInfoP.getNickname()));
				exportNextUserInfoExcelDTO.setMobile(userInfoP.getMobile());
				exportNextUserInfoExcelDTO.setUserType(userInfoP.getUserType());
			}
			if(userInfoG!=null){
				exportNextUserInfoExcelDTO.setParentNickName(incomeService.decodeNickName(userInfoG.getNickname()));
				exportNextUserInfoExcelDTO.setParentMobile(userInfoG.getMobile());
				exportNextUserInfoExcelDTO.setParentUserType(userInfoG.getUserType());
			}
			if(IncomeRecordDTO!=null){
				if(IncomeRecordDTO.getUserInfoDTO()!=null){
					exportNextUserInfoExcelDTO.setNextNickName(IncomeRecordDTO.getUserInfoDTO().getNickname());
					exportNextUserInfoExcelDTO.setNextMobile(IncomeRecordDTO.getUserInfoDTO().getMobile());
					exportNextUserInfoExcelDTO.setNextUserType(IncomeRecordDTO.getUserInfoDTO().getUserType());
				}
				if(IncomeRecordDTO.getPayDate()!=null){
					exportNextUserInfoExcelDTO.setPayDate(sdf.format(IncomeRecordDTO.getPayDate()));
				}
				exportNextUserInfoExcelDTO.setTransactionId(IncomeRecordDTO.getTransactionId());
				exportNextUserInfoExcelDTO.setOrderId(IncomeRecordDTO.getOrderId());
				exportNextUserInfoExcelDTO.setAmount(IncomeRecordDTO.getAmount());
			}
			exportNextUserInfoExcelDTOS.add(exportNextUserInfoExcelDTO);
		}

		if("Y".equals(pageParamVoDTO.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"昵称","手机号", "用户当前等级","上级级昵称","上级手机号", "上级用户当前等级","下级昵称","下级手机号", "下级用户当前等级","升级支付时间", "升级订单id",
						"升级交易流水号", "升级订单金额"};
				ExportExcel<ExportNextUserInfoExcelDTO> ex = new ExportExcel<>();

				HashMap<String, String> helperMap = new HashMap<>(16);
				if (CommonUtils.objectIsNotEmpty(exportNextUserInfoExcelDTOS)) {
					for (ExportNextUserInfoExcelDTO excelDTO : exportNextUserInfoExcelDTOS) {
						if (StringUtils.isBlank(helperMap.get(excelDTO.getTransactionId()))) {
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(1));
						} else {
							int count = Integer.parseInt(helperMap.get(excelDTO.getTransactionId())) + 1;
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(count));
						}
					}

					for (ExportNextUserInfoExcelDTO excelDTO : exportNextUserInfoExcelDTOS) {
						Integer count = Integer.parseInt(helperMap.get(excelDTO.getTransactionId()));
						if (count > 1) {
							excelDTO.setAmount(0);
							int number = Integer.parseInt(helperMap.get(excelDTO.getTransactionId())) - 1;
							helperMap.put(excelDTO.getTransactionId(), String.valueOf(number));
						}
					}
				}


				ByteArrayInputStream in = ex.getWorkbookIn("账单EXCEL文档", orderHeaders, exportNextUserInfoExcelDTOS);
				String url = CommonUtils.orderExcelToOSS(in);
				if ("".equals(url) && url == null){
					logger.info("佣金奖励Excel 获取OSSUrl为空");
				}
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);

			} catch (Exception e) {
				responseDTO.setResult("月度详情导出Excel异常");
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
				logger.error("月度详情导出Excel异常");
				e.printStackTrace();
			}
		}


		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));

		responseDTO.setResponseData(page);
		return responseDTO;

	}
}
