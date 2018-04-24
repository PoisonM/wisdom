package com.wisdom.business.controller.account;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.account.IncomeRecordManagementService;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.business.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.IncomeRecordManagementDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.ExportIncomeRecordExcelDTO;
import com.wisdom.common.dto.system.ExportMonthTransactionExcelDTO;
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

	/**
	 * 查询所有提成信息
	 * @return
	 */
	@RequestMapping(value = "queryAllUserIncome", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<IncomeRecordDTO>>> queryAllUserIncome(@RequestBody PageParamDTO pageParamDTO) {
		ResponseDTO<PageParamDTO<List<IncomeRecordDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<IncomeRecordDTO>> page = incomeService.queryAllUserIncome(pageParamDTO);
		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 审核月结  是否可提现
	 * @return
	 */
	@RequestMapping(value = "updateIncomeRecord", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<String> updateIncomeRecord(@RequestBody IncomeRecordDTO incomeRecordDTO) {
		ResponseDTO responseDTO = new ResponseDTO<>();
		try{
			incomeService.updateIncomeRecord(incomeRecordDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setErrorInfo("更新月结是否可提现成功");
		}catch (Exception e){
			responseDTO.setErrorInfo("更新月结是否可提现失败");
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		return responseDTO;

	}

	/**
	 * 查询即时返现和统计推荐下的订单详情
	 * @return
	 */
	@RequestMapping(value = "queryInstanceInfoByTransactionId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<PayRecordDTO>>> queryInstanceInfoByTransactionId(@RequestBody PageParamVoDTO<IncomeRecordDTO> pageParamVoDTO) {

		pageParamVoDTO.setEndTime(CommonUtils.getEndDate(pageParamVoDTO.getEndTime()));
		ResponseDTO<PageParamDTO<List<PayRecordDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<PayRecordDTO>> page = incomeService.queryInstanceInfoByTransactionId(pageParamVoDTO);
		responseDTO.setResponseData(page);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 查询月度结算下级消费的详情
	 * @return
	 */
	@RequestMapping(value = "queryMonthTransactionRecordByIncomeRecord", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<MonthTransactionRecordDTO>>> queryMonthTransactionRecordByIncomeRecord(@RequestBody PageParamVoDTO<IncomeRecordDTO> page) {
		page.setEndTime(CommonUtils.getEndDate(page.getEndTime()));
		ResponseDTO<PageParamDTO<List<MonthTransactionRecordDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<MonthTransactionRecordDTO>> monthTransactionRecordDTOList = incomeService.queryMonthTransactionRecordByIncomeRecord(page);
		if("Y".equals(page.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"用户id", "用户名","用户等级", "手机号", "交易流水号","付款时间", "消费流水"};
				ExportExcel<ExportMonthTransactionExcelDTO> ex = new ExportExcel<>();
				List<ExportMonthTransactionExcelDTO> excelList = new ArrayList<>();
				for (MonthTransactionRecordDTO monthTransactionRecordDTO : monthTransactionRecordDTOList.getResponseData()) {
					ExportMonthTransactionExcelDTO exportIncomeRecordExcelDTO = new ExportMonthTransactionExcelDTO();
					exportIncomeRecordExcelDTO.setSysUserId(monthTransactionRecordDTO.getUserId());
					exportIncomeRecordExcelDTO.setNickName(monthTransactionRecordDTO.getNickName());
					exportIncomeRecordExcelDTO.setMobile(monthTransactionRecordDTO.getMobile());
					exportIncomeRecordExcelDTO.setUserType(monthTransactionRecordDTO.getUserType());
					exportIncomeRecordExcelDTO.setMonthAmount(monthTransactionRecordDTO.getAmount());
					exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(monthTransactionRecordDTO.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
					exportIncomeRecordExcelDTO.setTransactionId(monthTransactionRecordDTO.getTransactionId());
					excelList.add(exportIncomeRecordExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("月度下级消费记录EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			} catch (Exception e) {
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
		}

		responseDTO.setResponseData(monthTransactionRecordDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 根据条件查询提成信息
	 * @param phoneAndIdentify 手机号或者身份证号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@RequestMapping(value = "queryUserIncomeByParameters", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<IncomeRecordDTO>>> queryUserIncomeByParameters(@RequestParam String phoneAndIdentify,
																				 @RequestParam String incomeType,
																				 @RequestParam String startTime,
																				 @RequestParam String endTime,
																				 @RequestParam String isExportExcel,
																				 @RequestParam Integer pageNo,
																				 @RequestParam Integer pageSize) {

		ResponseDTO<PageParamDTO<List<IncomeRecordDTO>>> responseDTO = new ResponseDTO<>();
		String applyStartTime ="";
		String applyEndTime = "";
		String startDate = "1990-01-01";//设定起始时间
		applyStartTime = "".equals(startTime) ? startDate : startTime;
		applyEndTime = CommonUtils.getEndDate(endTime);
		PageParamDTO<List<IncomeRecordDTO>> page =
				incomeService.queryUserIncomeByParameters(phoneAndIdentify,incomeType,applyStartTime,applyEndTime,isExportExcel,pageNo,pageSize);
		if("Y".equals(isExportExcel)) {
			try {
				String[] orderHeaders = {"用户id", "用户名","用户等级", "手机号", "提成金额","提成类型", "提成时间", "提成状态"};
				ExportExcel<ExportIncomeRecordExcelDTO> ex = new ExportExcel<>();
				List<ExportIncomeRecordExcelDTO> excelList = new ArrayList<>();
				for (IncomeRecordDTO incomeRecordDTO : page.getResponseData()) {
					ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
					/*exportIncomeRecordExcelDTO.setCreateDate(DateUtils.formatDate(incomeRecordDTO.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
					exportIncomeRecordExcelDTO.setIncomeType(incomeRecordDTO.getIncomeType());
					exportIncomeRecordExcelDTO.setMobile(incomeRecordDTO.getMobile());
					exportIncomeRecordExcelDTO.setNickName(incomeRecordDTO.getNickName());
					exportIncomeRecordExcelDTO.setStatus(incomeRecordDTO.getStatus());
					exportIncomeRecordExcelDTO.setSysUserId(incomeRecordDTO.getSysUserId());
					exportIncomeRecordExcelDTO.setUserType(incomeRecordDTO.getUserType());
					exportIncomeRecordExcelDTO.setAmount(incomeRecordDTO.getAmount());*/
					excelList.add(exportIncomeRecordExcelDTO);
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
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

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
				logger.info("条件查询用户佣金奖励从Redis获取用户信息失败={}", "userInfoDTO = [" + userInfoDTO + "]");
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
			return null;
		}

		if("Y".equals(pageParamVoDTO.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"用户id","用户名","用户手机号","用户获益时等级","用户现在等级","佣金金额",
						"下级用户id","下级用户名","下级用户手机号","下级用户等级","下级用户现在等级",
						"交易id","支付时间","订单id","订单金额","订单状态"};
				ExportExcel<ExportIncomeRecordExcelDTO> ex = new ExportExcel<>();
				List<ExportIncomeRecordExcelDTO> excelList = new ArrayList<>();
				for (IncomeRecordDTO incomeRecordDTO : incomeRecordDTOS) {
					//根据TransactionId查询订单
					List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(incomeRecordDTO.getTransactionId());
					if(CommonUtils.objectIsEmpty(businessOrderDTOS)) logger.info("导表-佣金订单数据businessOrder数据为空");
					for(BusinessOrderDTO businessOrderDTO : businessOrderDTOS){
						ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
						exportIncomeRecordExcelDTO.setUserType(incomeRecordDTO.getUserType());
						exportIncomeRecordExcelDTO.setUserTypeNow(incomeRecordDTO.getUserTypeNow());
						exportIncomeRecordExcelDTO.setSysUserId(incomeRecordDTO.getSysUserId());
						exportIncomeRecordExcelDTO.setNickName(incomeRecordDTO.getNickName());
						exportIncomeRecordExcelDTO.setMobile(incomeRecordDTO.getMobile());
						exportIncomeRecordExcelDTO.setAmount(incomeRecordDTO.getAmount());
						exportIncomeRecordExcelDTO.setNextUserId(incomeRecordDTO.getNextUserId());
						exportIncomeRecordExcelDTO.setNextUserMobile(incomeRecordDTO.getNextUserMobile());
						exportIncomeRecordExcelDTO.setNextUserNickName(incomeRecordDTO.getNextUserNickName());
						exportIncomeRecordExcelDTO.setNextUserType(incomeRecordDTO.getNextUserType());
						exportIncomeRecordExcelDTO.setNextUserTypeNow(incomeRecordDTO.getNextUserTypeNow());
						exportIncomeRecordExcelDTO.setOrderAmount(businessOrderDTO.getAmount());
						exportIncomeRecordExcelDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
						exportIncomeRecordExcelDTO.setOrderStatus(businessOrderDTO.getStatus());
						exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(businessOrderDTO.getPayDate(),"yyyy-MM-dd HH:mm:ss"));
						exportIncomeRecordExcelDTO.setTransactionId(incomeRecordDTO.getTransactionId());
						excelList.add(exportIncomeRecordExcelDTO);
					}
				}
				ByteArrayInputStream in = ex.getWorkbookIn("佣金奖励EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				if("".equals(url) && url == null) logger.info("佣金奖励Excel 获取OSSUrl为空");
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			} catch (Exception e) {
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
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
		if(CommonUtils.objectIsEmpty(incomeRecordDTOS)) logger.info("佣金下拉详情上下级关系数据incomeRecord数据为空");
		List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(incomeRecordDTOS.get(0).getTransactionId());
		if(CommonUtils.objectIsEmpty(incomeRecordDTOS)) logger.info("佣金下拉详情订单数据businessOrder数据为空");

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
		logger.info("佣金奖励审核传入参数={}", "incomeRecordId = [" + incomeRecordId + "],status = [" + status + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		if("".equals(incomeRecordId) || incomeRecordId == null){
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
			responseDTO.setResult("incomeRecordId为空");
			logger.info("佣金审核接口传入参数incomeRecordId为空", "incomeRecordId = [" + incomeRecordId + "]");
		}
		//获取登录人信息
		UserInfoDTO userInfoDTO = UserUtils.getUserInfoFromRedis();
		if(userInfoDTO == null) {
			logger.info("佣金奖励审核接口从Redis获取用户信息失败={}", "userInfoDTO = [" + userInfoDTO + "]");
			responseDTO.setResult("获取用户信息失败");
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
			/*if(status.equals(incomeRecordManagementDTOS.get(0).getStatus())){
				responseDTO.setResult("审核成功,与上次相同");
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			}else {
				//已有的审核记录状态与本次不同,则修改审核记录,为本次审核结果
				incomeRecordManagementDTOS.get(0).setStatus(status);
				incomeRecordManagementService.updateIncomeRecordManagement(incomeRecordManagementDTOS.get(0));
				responseDTO.setResult("审核修改成功");
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			}*/
			responseDTO.setResult("已有相关人员审核,不能重复修改");
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
			return responseDTO;
		}
		//查询此条数据有没有被审核
		IncomeRecordManagementDTO incomeRecordManagementDTO2 =new IncomeRecordManagementDTO();
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

		}
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
	 * 修改佣金奖励审核new
	 * @param status 状态审核通过或不通过
	 * @param IncomeRecordManagementId
	 * @return
	 */
	@RequestMapping(value = "updateIncomeRecordManagement", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> updateIncomeRecordManagement(@RequestParam String IncomeRecordManagementId,String status) {
		/*long startTime = System.currentTimeMillis();
		logger.info("修改佣金审核传入参数={}", "incomeRecordId = [" + IncomeRecordManagementId + "],status = [" + status + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
		if("".equals(IncomeRecordManagementId) && IncomeRecordManagementId != null){
			IncomeRecordManagementDTO incomeRecordManagementDTO = new IncomeRecordManagementDTO();
			incomeRecordManagementDTO.setId(IncomeRecordManagementId);
			incomeRecordManagementDTO.setStatus(status);
			incomeRecordManagementService.updateIncomeRecordManagement(incomeRecordManagementDTO);
			responseDTO.setErrorInfo(StatusConstant.SUCCESS);
		}else {
			responseDTO.setErrorInfo(StatusConstant.FAILURE);
			responseDTO.setResult("IncomeRecordManagementId为空");
			logger.info("修改佣金审核incomeRecordId为空", "incomeRecordId = [" + IncomeRecordManagementId + "]");
		}

		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;*/
		return null;
	}

	/**
	 * 月度导表New
	 * @param sysUserId 当前用户id,即查当前人的月度
	 * @param
	 * @return
	 */
	@RequestMapping(value = "exportExcelIncomeRecord", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<Map<String,Object>> exportExcelIncomeRecord(@RequestParam String sysUserId) {
		/*long startTime = System.currentTimeMillis();
		logger.info("修改佣金审核传入参数={}", "incomeRecordId = [" + sysUserId + "]");
		ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();





		logger.info("查询返利数据耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;*/
		return null;
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
		//Map<String,Object> map=new HashMap<>(16);

		//先查出所有本人的,再查出下级的
		pageParamVoDTO.getRequestData().setParentRelation("self");
		//int selfCount = incomeService.queryMonthRecordCountByParentRelation(pageParamVoDTO);
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
		pageParamVoDTO.getRequestData().setParentRelation("other");
		//int nextCount = incomeService.queryMonthRecordCountByParentRelation(pageParamVoDTO);
		List<MonthTransactionRecordDTO> nextList = incomeService.queryMonthRecordByParentRelation(pageParamVoDTO);
		if(CommonUtils.objectIsEmpty(nextList)) logger.info("月度下级详情数据nextList数据为空");
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
						"下级用户id", "下级用户名", "下级用户手机号", "下级用户等级", "下级用户现在等级",
						"交易id", "支付时间", "订单id", "订单金额", "订单状态"};
				ExportExcel<ExportIncomeRecordExcelDTO> ex = new ExportExcel<>();
				List<ExportIncomeRecordExcelDTO> excelList = new ArrayList<>();
				for (MonthTransactionRecordDTO monthTransactionRecordDTO : selfList) {
					List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
					if (CommonUtils.objectIsEmpty(businessOrderDTOS)) logger.info("导表-月度本人订单数据businessOrder数据为空");
					for (BusinessOrderDTO businessOrderDTO : businessOrderDTOS) {
						ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
						exportIncomeRecordExcelDTO.setUserType(monthTransactionRecordDTO.getUserType());
						exportIncomeRecordExcelDTO.setUserTypeNow(monthTransactionRecordDTO.getUserTypeNow());
						exportIncomeRecordExcelDTO.setSysUserId(monthTransactionRecordDTO.getUserId());
						exportIncomeRecordExcelDTO.setNickName(monthTransactionRecordDTO.getNickName());
						exportIncomeRecordExcelDTO.setMobile(monthTransactionRecordDTO.getMobile());
						exportIncomeRecordExcelDTO.setAmount(monthTransactionRecordDTO.getAmount());
						exportIncomeRecordExcelDTO.setNextUserId(monthTransactionRecordDTO.getNextUserId());
						exportIncomeRecordExcelDTO.setNextUserMobile(monthTransactionRecordDTO.getNextUserMobile());
						exportIncomeRecordExcelDTO.setNextUserNickName(monthTransactionRecordDTO.getNextUserNickName());
						exportIncomeRecordExcelDTO.setNextUserType(monthTransactionRecordDTO.getNextUserType());
						exportIncomeRecordExcelDTO.setNextUserTypeNow(monthTransactionRecordDTO.getNextUserTypeNow());
						exportIncomeRecordExcelDTO.setOrderAmount(businessOrderDTO.getAmount());
						exportIncomeRecordExcelDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
						exportIncomeRecordExcelDTO.setOrderStatus(businessOrderDTO.getStatus());
						exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(businessOrderDTO.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
						exportIncomeRecordExcelDTO.setTransactionId(monthTransactionRecordDTO.getTransactionId());
						excelList.add(exportIncomeRecordExcelDTO);
					}
				}
				for (MonthTransactionRecordDTO monthTransactionRecordDTO : nextList) {
					List<BusinessOrderDTO> businessOrderDTOS = payRecordService.queryOrderInfoByTransactionId(monthTransactionRecordDTO.getTransactionId());
					if (CommonUtils.objectIsEmpty(businessOrderDTOS)) logger.info("导表-月度下级订单数据businessOrder数据为空");
					for (BusinessOrderDTO businessOrderDTO : businessOrderDTOS) {
						ExportIncomeRecordExcelDTO exportIncomeRecordExcelDTO = new ExportIncomeRecordExcelDTO();
						exportIncomeRecordExcelDTO.setUserType(monthTransactionRecordDTO.getUserType());
						exportIncomeRecordExcelDTO.setUserTypeNow(monthTransactionRecordDTO.getUserTypeNow());
						exportIncomeRecordExcelDTO.setSysUserId(monthTransactionRecordDTO.getUserId());
						exportIncomeRecordExcelDTO.setNickName(monthTransactionRecordDTO.getNickName());
						exportIncomeRecordExcelDTO.setMobile(monthTransactionRecordDTO.getMobile());
						exportIncomeRecordExcelDTO.setAmount(monthTransactionRecordDTO.getAmount());
						exportIncomeRecordExcelDTO.setNextUserId(monthTransactionRecordDTO.getNextUserId());
						exportIncomeRecordExcelDTO.setNextUserMobile(monthTransactionRecordDTO.getNextUserMobile());
						exportIncomeRecordExcelDTO.setNextUserNickName(monthTransactionRecordDTO.getNextUserNickName());
						exportIncomeRecordExcelDTO.setNextUserType(monthTransactionRecordDTO.getNextUserType());
						exportIncomeRecordExcelDTO.setNextUserTypeNow(monthTransactionRecordDTO.getNextUserTypeNow());
						exportIncomeRecordExcelDTO.setOrderAmount(businessOrderDTO.getAmount());
						exportIncomeRecordExcelDTO.setOrderId(businessOrderDTO.getBusinessOrderId());
						exportIncomeRecordExcelDTO.setOrderStatus(businessOrderDTO.getStatus());
						exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(businessOrderDTO.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
						exportIncomeRecordExcelDTO.setTransactionId(monthTransactionRecordDTO.getTransactionId());
						excelList.add(exportIncomeRecordExcelDTO);
					}
				}

				ByteArrayInputStream in = ex.getWorkbookIn("账单EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			} catch (Exception e) {
				responseDTO.setResult("月度详情导出Excel异常");
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
				logger.error("月度详情导出Excel异常");
				e.printStackTrace();
			}
		}
		/*map.put("selfCount",selfCount);
		map.put("nextCount",nextCount);
		map.put("selfList",selfList1);
		map.put("nextList",nextList1);*/
		responseDTO.setErrorInfo(StatusConstant.SUCCESS);

		//responseDTO.setResponseData(map);
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
		if(CommonUtils.objectIsEmpty(nextList)) logger.info("月度本人详情数据selfList数据为空");
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
	 * 根据用户id查询这个月都消费了哪些订单
	 * @return
	 */
	@RequestMapping(value = "queryMonthPayRecordByUserId", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<PageParamDTO<List<PayRecordDTO>>> queryMonthPayRecordByUserId(@RequestBody PageParamVoDTO<IncomeRecordDTO> page) {
		page.setEndTime(CommonUtils.getEndDate(page.getEndTime()));
		ResponseDTO<PageParamDTO<List<PayRecordDTO>>> responseDTO = new ResponseDTO<>();
		PageParamDTO<List<PayRecordDTO>> payRecordDTOList = incomeService.queryMonthPayRecordByUserId(page);
		if("Y".equals(page.getIsExportExcel())) {
			try {
				String[] orderHeaders = {"用户id", "用户名", "手机号", "交易流水号","付款时间", "消费流水"};
				ExportExcel<ExportMonthTransactionExcelDTO> ex = new ExportExcel<>();
				List<ExportMonthTransactionExcelDTO> excelList = new ArrayList<>();
				for (PayRecordDTO payRecordDTO : payRecordDTOList.getResponseData()) {
					ExportMonthTransactionExcelDTO exportIncomeRecordExcelDTO = new ExportMonthTransactionExcelDTO();
					exportIncomeRecordExcelDTO.setSysUserId(payRecordDTO.getSysUserId());
					exportIncomeRecordExcelDTO.setNickName(payRecordDTO.getNickName());
					exportIncomeRecordExcelDTO.setMobile(payRecordDTO.getMobile());
					exportIncomeRecordExcelDTO.setUserType(payRecordDTO.getUserType());
					exportIncomeRecordExcelDTO.setMonthAmount(payRecordDTO.getAmount());
					exportIncomeRecordExcelDTO.setPayDate(DateUtils.formatDate(payRecordDTO.getPayDate(),"yyyy-MM-dd HH:mm:ss"));
					exportIncomeRecordExcelDTO.setTransactionId(payRecordDTO.getTransactionId());
					excelList.add(exportIncomeRecordExcelDTO);
				}
				ByteArrayInputStream in = ex.getWorkbookIn("月度本人消费记录EXCEL文档", orderHeaders, excelList);
				String url = CommonUtils.orderExcelToOSS(in);
				responseDTO.setResult(url);
				responseDTO.setErrorInfo(StatusConstant.SUCCESS);
				return responseDTO;
			} catch (Exception e) {
				e.printStackTrace();
				responseDTO.setErrorInfo(StatusConstant.FAILURE);
			}
		}
		responseDTO.setResponseData(payRecordDTOList);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}
}
