package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopCustomerProductRelationDTO;
import com.wisdom.beauty.api.dto.ShopCustomerProjectGroupRelRelationDTO;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: ProjectController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "projectInfo")
public class ProjectController {

	/**
     * 查询某个用户的疗程卡列表信息
	 * @param sysCustomerId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProductRelationDTO>> getCustomerCourseProjectList(@RequestParam String sysCustomerId,
																			   @RequestParam String sysShopId,
																			   @RequestParam String startDate,
																			   @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
     * 查询某个用户的单次卡列表信息
	 * @param sysCustomerId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
	@RequestMapping(value = "getCustomerSingleProjectList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProductRelationDTO>> getCustomerSingleProjectList(@RequestParam String sysCustomerId,
																			   @RequestParam String sysShopId,
																			   @RequestParam String startDate,
																			   @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}

	/**
	 * 查询某个用户的套卡列表信息
	 * @param sysCustomerId
	 * @param sysShopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "getCustomerProjectGroupList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopCustomerProjectGroupRelRelationDTO>> getCustomerProjectGroupList(@RequestParam String sysCustomerId,
																						  @RequestParam String sysShopId,
																						  @RequestParam String startDate,
																						  @RequestParam String endDate) {
		ResponseDTO<List<ShopCustomerProjectGroupRelRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}




}
