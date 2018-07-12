package com.wisdom.beauty.controller.archives;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopAppointmentService;
import com.wisdom.beauty.core.service.ShopCustomerArchivesService;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.PinYinSort;
import com.wisdom.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: ArchivesController
 *
 * @Author： huan
 * 
 * @Description: 档案控制层
 * @Date:Created in 2018/4/4 9:26
 * @since JDK 1.8
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "earlyWarning")
public class ArchivesEarlyWarningController {

	@Autowired
	private ShopCustomerArchivesService shopCustomerArchivesService;

	@Autowired
	private ShopAppointmentService shopAppointmentService;

	@Autowired
	private RedisUtils redisUtils;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 档案预警接口
	 * 
	 * @param queryType
	 *            一个月未到店 one ;三个月未到店 three ;六个月未到店 six
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getEarlyWarningList", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> getEarlyWarningList(@RequestParam String queryType, @RequestParam(required = false) String pageNo,
                                            @RequestParam(required = false) String pageSize,@RequestParam(required = false) String queryScope,
											@RequestParam(required = false) String sysShopId) {

		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		String bossCode = redisUtils.getBossCode();

		if (StringUtils.isBlank(bossCode)) {
			logger.error("获取到的店铺信息为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			responseDTO.setErrorInfo("获取到的店铺信息为空！");
			return responseDTO;
		}

		// 获取当前boss下的档案列表
		ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
		shopUserArchivesDTO.setSysBossCode(bossCode);
		shopUserArchivesDTO.setSysShopId(redisUtils.getShopId());
		//查询老板下所有店铺信息
		if("all".equals(queryScope)){
			shopUserArchivesDTO.setSysShopId("");
		}

		// 根据queryType构造查询时间段
		Calendar calendar = Calendar.getInstance();
		Date currentDate = null;
		if ("one".equals(queryType)) {
			calendar.add(Calendar.MONTH, -1);
			currentDate=calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
		} else if ("three".equals(queryType)) {
			calendar.add(Calendar.MONTH, -1);
			currentDate=calendar.getTime();
			calendar.add(Calendar.MONTH, -3);
		} else if ("six".equals(queryType)) {
			calendar.add(Calendar.MONTH, -1);
			currentDate=calendar.getTime();
			calendar.add(Calendar.MONTH, -6);
		}
		PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO=new PageParamVoDTO<>();
		pageParamVoDTO.setRequestData(shopUserArchivesDTO);
		pageParamVoDTO.setStartTime(DateUtils.DateToStr(calendar.getTime(),"datetime"));
		pageParamVoDTO.setEndTime(DateUtils.DateToStr(currentDate,"datetime"));

		List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService
				.getShopBuildArchives(pageParamVoDTO);

		ArrayList<Object> lastList = new ArrayList<>();
		for (char a : PinYinSort.getSortType()) {
			HashMap<Object, Object> hashMap = new HashMap<>(16);
			ArrayList<Object> arrayList = new ArrayList<>();
			for (ShopUserArchivesDTO dto : shopUserArchivesInfo) {
				if(dto.getSysUserName().matches("\\b[A-Za-z][^ ]{0,}")){
					if (a ==dto.getSysUserName().toLowerCase().charAt(0) ) {
						arrayList.add(dto);
					}
				}else {
					if (a == PinYinSort.ToPinYinString(dto.getSysUserName()).toLowerCase().charAt(0)) {
						arrayList.add(dto);
					}
				}
			}
			if (arrayList.size() > 0) {
				hashMap.put(String.valueOf(a).toUpperCase(), arrayList);
				lastList.add(hashMap);
			}
		}

		// 查询个数
		Map<String, Object> map = new HashMap<>(16);
		map.put("info", lastList);
		map.put("count", shopUserArchivesInfo.size());

		// 返回过滤后的档案列表
		responseDTO.setResponseData(map);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 获取成功的返回结果
	 *
	 * @param responseDTO
	 * @param shopUserArchivesInfo
	 * @return
	 */
	private ResponseDTO<Object> getSuccessResponseDTO(ResponseDTO<Object> responseDTO,
			List<ShopUserArchivesDTO> shopUserArchivesInfo) {
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(shopUserArchivesInfo);
		responseDTO.setErrorInfo("获取的boss信息为空！");
		return responseDTO;
	}

}
