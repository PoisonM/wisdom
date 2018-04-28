package com.wisdom.business.controller.account;

import com.wisdom.business.client.UserServiceClient;
import com.wisdom.business.interceptor.LoginRequired;
import com.wisdom.business.mapper.account.IncomeMapper;
import com.wisdom.business.mapper.level.UserTypeMapper;
import com.wisdom.business.service.account.IncomeService;
import com.wisdom.business.service.transaction.PayRecordService;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.IncomeRecordDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.system.*;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.excel.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 直播板块
 * @author frank
 * @date 2015-10-14
 */

@Controller
@RequestMapping(value = "income")
public class IncomeManagementController {

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private UserTypeMapper userTypeMapper;

	@Autowired
	private IncomeMapper incomeMapper;

	@Autowired
	private PayRecordService payRecordService;

	@Autowired
	private UserServiceClient userServiceClient;

	@RequestMapping(value = "path", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO path() throws FileNotFoundException {
		String i = System.getProperty("user.dir");
		System.out.print(i);
		FileInputStream instream = new FileInputStream(new File(i+"\\service\\business-service\\target\\classes\\application.properties"));
		return null;
	}

	/**
	 * 根据用户id查询这个月都消费了哪些订单
	 * @return
	 */
	@RequestMapping(value = "management", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO management() {
		ResponseDTO responseDTO = new ResponseDTO();

		UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
		userBusinessTypeDTO.setUserType(ConfigConstant.businessC1);
		List<UserBusinessTypeDTO> userBusinessTypeDTOList = userTypeMapper.getUserBusinessTypeSpecial(userBusinessTypeDTO);

		for(UserBusinessTypeDTO userBusinessTypeDTO1:userBusinessTypeDTOList)
		{
			if(userBusinessTypeDTO1.getParentUserId().equals(""))
			{
				UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(userBusinessTypeDTO1.getSysUserId());
				if(userInfoDTO.getParentUserId()!=null)
				{
					userBusinessTypeDTO1.setParentUserId(userInfoDTO.getParentUserId());
					userTypeMapper.updateUserBusinessType(userBusinessTypeDTO1);
				}
			}
		}

		IncomeRecordDTO incomeRecordDTO = new IncomeRecordDTO();
		incomeRecordDTO.setIncomeType("instance");
		List<IncomeRecordDTO> incomeRecordDTOList = incomeService.getUserIncomeRecordInfo(incomeRecordDTO);

		for(IncomeRecordDTO incomeRecord:incomeRecordDTOList)
		{
			if(incomeRecord.getUserType().equals(""))
			{
				String userType = getUserType(incomeRecord.getCreateDate(),incomeRecord.getSysUserId());

				UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecord.getSysUserId());

				incomeRecord.setUserType(userType);
				incomeRecord.setMobile(userInfoDTO.getMobile());
				incomeRecord.setNickName(userInfoDTO.getNickname());
				incomeRecord.setIdentifyNumber(userInfoDTO.getIdentifyNumber());

				//通过transactionID获取消费者的ID
				String transactionId = incomeRecord.getTransactionId();
				PayRecordDTO payRecordDTO = new PayRecordDTO();
				payRecordDTO.setTransactionId(transactionId);
				List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);
				if(payRecordDTOList.size()==0)
				{
					continue;
				}
				float transactionAmount = 0;
				for(PayRecordDTO payRecordDTO1:payRecordDTOList)
				{
					transactionAmount = transactionAmount + payRecordDTO1.getAmount();
				}
				incomeRecord.setTransactionAmount(transactionAmount);

				String nextUserId = payRecordDTOList.get(0).getSysUserId();

				String nextUserType = getUserType(incomeRecord.getCreateDate(),nextUserId);

				UserInfoDTO nextUserInfoDTO = userServiceClient.getUserInfoFromUserId(nextUserId);
				incomeRecord.setNextUserId(nextUserId);
				incomeRecord.setNextUserType(nextUserType);
				incomeRecord.setNextUserMobile(nextUserInfoDTO.getMobile());
				incomeRecord.setNextUserNickName(nextUserInfoDTO.getNickname());
				incomeRecord.setNextUserIdentifyNumber(nextUserInfoDTO.getIdentifyNumber());

				if(nextUserInfoDTO.getParentUserId().equals(userInfoDTO.getId()))
				{
					incomeRecord.setParentRelation(userType);
				}
				else
				{
					incomeRecord.setParentRelation("A1B1");
				}

				incomeService.updateIncomeInfo(incomeRecord);
			}
		}

		IncomeRecordDTO incomeRecordDTO1 = new IncomeRecordDTO();
		incomeRecordDTO1.setIncomeType("month");
		List<IncomeRecordDTO> incomeRecordDTOList1 = incomeService.getUserIncomeRecordInfo(incomeRecordDTO1);

		for(IncomeRecordDTO incomeRecord:incomeRecordDTOList1) {
			if (incomeRecord.getUserType().equals("")) {
				String userType = getUserType(incomeRecord.getCreateDate(),incomeRecord.getSysUserId());

				UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(incomeRecord.getSysUserId());

				incomeRecord.setUserType(userType);
				incomeRecord.setMobile(userInfoDTO.getMobile());
				incomeRecord.setNickName(userInfoDTO.getNickname());
				incomeRecord.setIdentifyNumber(userInfoDTO.getIdentifyNumber());
				incomeService.updateIncomeInfo(incomeRecord);
			}
		}

		List<MonthTransactionRecordDTO> monthTransactionRecordDTOList = incomeMapper.queryAllMonthTransactionRecord();
		for(MonthTransactionRecordDTO monthTransactionRecordDTO:monthTransactionRecordDTOList)
		{
			if(monthTransactionRecordDTO.getUserType().equals(""))
			{
				String userType = getUserType(monthTransactionRecordDTO.getCreateDate(),monthTransactionRecordDTO.getUserId());

				UserInfoDTO userInfoDTO = userServiceClient.getUserInfoFromUserId(monthTransactionRecordDTO.getUserId());

				monthTransactionRecordDTO.setNickName(userInfoDTO.getNickname());
				monthTransactionRecordDTO.setMobile(userInfoDTO.getMobile());
				monthTransactionRecordDTO.setIdentifyNumber(userInfoDTO.getIdentifyNumber());
				monthTransactionRecordDTO.setUserType(userType);

				//通过transactionID获取消费者的ID
				String transactionId = monthTransactionRecordDTO.getTransactionId();
				PayRecordDTO payRecordDTO = new PayRecordDTO();
				payRecordDTO.setTransactionId(transactionId);
				List<PayRecordDTO> payRecordDTOList = payRecordService.getUserPayRecordList(payRecordDTO);
				if(payRecordDTOList.size()==0)
				{
					continue;
				}
				String nextUserId = payRecordDTOList.get(0).getSysUserId();

				String nextUserType = getUserType(monthTransactionRecordDTO.getCreateDate(),nextUserId);

				UserInfoDTO nextUserInfoDTO = userServiceClient.getUserInfoFromUserId(nextUserId);
				monthTransactionRecordDTO.setNextUserId(nextUserId);
				monthTransactionRecordDTO.setNextUserType(nextUserType);
				monthTransactionRecordDTO.setNextUserMobile(nextUserInfoDTO.getMobile());
				monthTransactionRecordDTO.setNextUserNickName(nextUserInfoDTO.getNickname());
				monthTransactionRecordDTO.setNextUserIdentifyNumber(nextUserInfoDTO.getIdentifyNumber());

				if(nextUserInfoDTO.getParentUserId().equals(userInfoDTO.getId()))
				{
					monthTransactionRecordDTO.setParentRelation(userType);
				}
				else
				{
					monthTransactionRecordDTO.setParentRelation("A1B1");
				}

				incomeMapper.updateMonthTransactionRecord(monthTransactionRecordDTO);
			}
		}
		return  responseDTO;
	}

	private String getUserType(Date createDate, String sysUserId)
	{
		UserBusinessTypeDTO userBusinessTypeDTO = new UserBusinessTypeDTO();
		userBusinessTypeDTO.setSysUserId(sysUserId);
		List<UserBusinessTypeDTO> userBusinessTypeDTOList = userTypeMapper.getUserBusinessType(userBusinessTypeDTO);

		String tempType = null;

		if(userBusinessTypeDTOList.size()==1)
		{
			tempType = ConfigConstant.businessC1;
		}
		else if(userBusinessTypeDTOList.size()==2)
		{
			if(DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(0).getCreateDate())==1)
			{
				tempType = userBusinessTypeDTOList.get(0).getUserType();
			}
			else if(DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(0).getCreateDate())==0
					&&DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(1).getCreateDate())==1)
			{
				tempType = userBusinessTypeDTOList.get(1).getUserType();
			}
		}
		else if(userBusinessTypeDTOList.size()==3)
		{
			if(DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(0).getCreateDate())==1)
			{
				tempType = userBusinessTypeDTOList.get(0).getUserType();

			}
			else if(DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(0).getCreateDate())==0
					&&DateUtils.compareDate(createDate,userBusinessTypeDTOList.get(1).getCreateDate())==1)
			{
				tempType = userBusinessTypeDTOList.get(1).getUserType();
			}
			else
			{
				tempType = userBusinessTypeDTOList.get(2).getUserType();
			}
		}

		return  tempType;
	}
}
