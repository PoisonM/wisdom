package com.wisdom.user.service.impl;

import com.aliyun.opensearch.sdk.dependencies.com.google.gson.Gson;
import com.wisdom.common.constant.ConfigConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkCriteria;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.dto.user.UserInfoDTO;
import com.wisdom.common.util.*;
import com.wisdom.user.client.BeautyServiceClient;
import com.wisdom.user.mapper.SysClerkMapper;
import com.wisdom.user.service.BeautyUserInfoService;
import com.wisdom.user.service.ClerkInfoService;
import com.wisdom.user.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import com.wisdom.user.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("clerkInfoService")
public class ClerkInfoServiceImpl implements ClerkInfoService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysClerkMapper sysClerkMapper;

	@Autowired
	private UserInfoService userInfoService;
    @Autowired
	private BeautyServiceClient beautyServiceClient;
	@Autowired
	private BeautyUserInfoService beautyUserInfoService;

	private Gson gson = new Gson();


	/**
	 * 获取店员列表
	 *
	 * @param SysClerk
	 * @return
	 */
	@Override
	public List<SysClerkDTO> getClerkInfo(SysClerkDTO SysClerk) {
		SysClerkCriteria SysClerkCriteria = new SysClerkCriteria();
		SysClerkCriteria.Criteria criteria = SysClerkCriteria.createCriteria();

		if (null == SysClerk) {
			logger.error("获取某个店的店员列表传入参数为空{}", SysClerk);
			return null;
		}

		if (StringUtils.isNotBlank(SysClerk.getSysShopId())) {
			criteria.andSysShopIdEqualTo(SysClerk.getSysShopId());
		}
		if (StringUtils.isNotBlank(SysClerk.getId())) {
			criteria.andIdEqualTo(SysClerk.getId());
		}
		List<SysClerkDTO> SysClerks = sysClerkMapper.selectByCriteria(SysClerkCriteria);

		return SysClerks;
	}

	@Override
	public List<SysClerkDTO> getClerkInfoList(PageParamVoDTO<SysClerkDTO> pageParamVoDTO) {
		SysClerkDTO sysClerkDTO = pageParamVoDTO.getRequestData();

		SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
		SysClerkCriteria.Criteria criteria = sysClerkCriteria.createCriteria();

		if (null == sysClerkDTO) {
			logger.error("获取某个店的店员列表传入参数为空{}", sysClerkDTO);
			return null;
		}

		if (StringUtils.isNotBlank(sysClerkDTO.getSysShopId())) {
			criteria.andSysShopIdEqualTo(sysClerkDTO.getSysShopId());
		}
		if (StringUtils.isNotBlank(sysClerkDTO.getSysBossCode())) {
			criteria.andSysBossCodeEqualTo(sysClerkDTO.getSysBossCode());
		}
		// 排序
		sysClerkCriteria.setOrderByClause("create_date");
		// 分页
		if (pageParamVoDTO.getPaging()) {
			sysClerkCriteria.setLimitStart(pageParamVoDTO.getPageNo());
			sysClerkCriteria.setPageSize(pageParamVoDTO.getPageSize());
		}
		// 时间
		if (StringUtils.isNotBlank(pageParamVoDTO.getStartTime())
				&& StringUtils.isNotBlank(pageParamVoDTO.getEndTime())) {
			Date startDate = DateUtils.StrToDate(pageParamVoDTO.getStartTime(), "datetime");
			Date endDate = DateUtils.StrToDate(pageParamVoDTO.getEndTime(), "datetime");
			criteria.andCreateDateBetween(startDate, endDate);
		}
		List<SysClerkDTO> SysClerks = sysClerkMapper.selectByCriteria(sysClerkCriteria);

		return SysClerks;
	}

	@Override
	public int updateSysClerk(SysClerkDTO sysClerkDTO) {
		int count = sysClerkMapper.updateByPrimaryKeySelective(sysClerkDTO);
		if(count>0){
			String token = UserUtils.getClerkToken();
			SysClerkDTO sys = sysClerkMapper.selectByPrimaryKey(sysClerkDTO.getId());
			String sysClerk = gson.toJson(sys);
			JedisUtils.set(token,sysClerk, ConfigConstant.logintokenPeriod);
		}
		return count;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int saveSysClerk(SysClerkDTO sysClerkDTO) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setNickname(sysClerkDTO.getName());
		userInfoDTO.setMobile(sysClerkDTO.getMobile());
		userInfoDTO.setId(IdGen.uuid());
		beautyUserInfoService.insertBeautyUserInfo(userInfoDTO);
		sysClerkDTO.setId(IdGen.uuid());
		sysClerkDTO.setSysUserId(userInfoDTO.getId());
		sysClerkDTO.setSysShopId(sysClerkDTO.getSysShopId());
		sysClerkDTO.setMobile(sysClerkDTO.getMobile());
		sysClerkDTO.setName(sysClerkDTO.getName());
		sysClerkDTO.setRole(sysClerkDTO.getRole());
		sysClerkDTO.setCreateDate(new Date());
		sysClerkDTO.setUpdateDate(new Date());
		if(StringUtils.isBlank(sysClerkDTO.getPhoto())){
			sysClerkDTO.setPhoto("https://mx-beauty.oss-cn-beijing.aliyuncs.com/%E5%A4%B4%E5%83%8F.png");
		}
		int reult=sysClerkMapper.insertSelective(sysClerkDTO);
		//设置默认排班
		SysClerkDTO sysClerk =new SysClerkDTO();
		sysClerk.setId(sysClerkDTO.getId());
		sysClerk.setName(sysClerkDTO.getName());
		sysClerk.setSysBossCode(sysClerkDTO.getSysBossCode());
		sysClerk.setSysShopId(sysClerkDTO.getSysShopId());
		beautyServiceClient.saveOneClerkSchedule(sysClerk);
		return reult;
	}

	@Override
	public List<SysClerkDTO> getClerkBySearchFile(SysClerkDTO sysClerkDTO) {
		if (sysClerkDTO == null) {
			logger.info("getClerkBySearchFile方法获取的参数sysClerkDTO为空");
		}
		SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
		SysClerkCriteria.Criteria c = sysClerkCriteria.createCriteria();
		SysClerkCriteria.Criteria or = sysClerkCriteria.createCriteria();

		// 参数
		if (StringUtils.isNotBlank(sysClerkDTO.getSysBossCode())) {
			c.andSysBossCodeEqualTo(sysClerkDTO.getSysBossCode());
		}
		if (StringUtils.isNotBlank(sysClerkDTO.getMobile())) {
			c.andMobileLike("%" + sysClerkDTO.getMobile() + "%");
		}
		if (StringUtils.isNotBlank(sysClerkDTO.getName())) {
			or.andNameLike("%" + sysClerkDTO.getName() + "%");
		}
		if (StringUtils.isNotBlank(sysClerkDTO.getSysBossCode())) {
			or.andSysBossCodeEqualTo(sysClerkDTO.getSysBossCode());
		}
		sysClerkCriteria.or(or);
		return sysClerkMapper.selectByCriteria(sysClerkCriteria);

	}

	@Override
	public List<SysClerkDTO> getClerkInfoListByClerkIds(PageParamVoDTO<SysClerkDTO> pageParamVoDTO, List<String> clerkIds) {

		SysClerkCriteria sysClerkCriteria = new SysClerkCriteria();
		SysClerkCriteria.Criteria criteria = sysClerkCriteria.createCriteria();

		if (CollectionUtils.isNotEmpty(clerkIds)) {
			criteria.andIdIn(clerkIds);
		}
		// 排序
		sysClerkCriteria.setOrderByClause("create_date");
		// 分页
		if (pageParamVoDTO.getPaging()) {
			sysClerkCriteria.setLimitStart(pageParamVoDTO.getPageNo());
			sysClerkCriteria.setPageSize(pageParamVoDTO.getPageSize());
		}
		// 时间
		return sysClerkMapper.selectByCriteria(sysClerkCriteria);
	}
}
