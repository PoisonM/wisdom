package com.wisdom.business.controller.account;

import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.ExportIncomeRecordExcelDTO;
import com.wisdom.common.dto.system.ExportMonthTransactionExcelDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.excel.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "income")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

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
					exportIncomeRecordExcelDTO.setCreateDate(DateUtils.formatDate(incomeRecordDTO.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
					exportIncomeRecordExcelDTO.setIncomeType(incomeRecordDTO.getIncomeType());
					exportIncomeRecordExcelDTO.setMobile(incomeRecordDTO.getMobile());
					exportIncomeRecordExcelDTO.setNickName(incomeRecordDTO.getNickName());
					exportIncomeRecordExcelDTO.setStatus(incomeRecordDTO.getStatus());
					exportIncomeRecordExcelDTO.setSysUserId(incomeRecordDTO.getSysUserId());
					exportIncomeRecordExcelDTO.setUserType(incomeRecordDTO.getUserType());
					exportIncomeRecordExcelDTO.setAmount(incomeRecordDTO.getAmount());
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
