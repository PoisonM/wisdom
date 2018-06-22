package com.wisdom.beauty.controller.card;

import com.wisdom.beauty.api.dto.ShopProjectGroupDTO;
import com.wisdom.beauty.api.dto.ShopRechargeCardDTO;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.dto.ShopUserRechargeCardDTO;
import com.wisdom.beauty.api.enums.OrderStatusEnum;
import com.wisdom.beauty.api.errorcode.BusinessErrorCode;
import com.wisdom.beauty.api.extDto.ExtShopRechargeCardDTO;
import com.wisdom.beauty.api.extDto.ShopRechargeCardOrderDTO;
import com.wisdom.beauty.api.responseDto.ProjectInfoGroupResponseDTO;
import com.wisdom.beauty.api.responseDto.ShopRechargeCardResponseDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.*;
import com.wisdom.beauty.interceptor.LoginAnnotations;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.DateUtils;
import com.wisdom.common.util.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * FileName: card
 *
 * @author: 赵得良 Date: 2018/4/3 0003 15:06 Description: 预约相关
 */
@Controller
@LoginAnnotations
@RequestMapping(value = "cardInfo")
public class CardController {

	@Resource
	private ShopCardService cardService;

	@Resource
	private ShopRechargeCardService shopRechargeCardService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ShopCustomerArchivesService shopCustomerArchivesService;

	@Autowired
	private ShopUserConsumeService shopUserConsumeService;

    @Autowired
    private RedisUtils redisUtils;

	@Value("${test.msg}")
	private String msg;

	@Autowired
	private ShopProjectGroupService shopProjectGroupService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 查询某个用户的充值卡列表信息
	 *
	 * @param sysUserId
	 * @return
	 */
	@RequestMapping(value = "/getUserRechargeCardList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	ResponseDTO<Object> getUserRechargeCardList(
			@RequestParam String sysUserId, @RequestParam(required = false) String sysShopId) {
        sysShopId = redisUtils.getShopId();
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();

        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        shopUserRechargeCardDTO.setSysUserId(sysUserId);
        shopUserRechargeCardDTO.setSysShopId(sysShopId);
        List<ShopUserRechargeCardDTO> userRechargeCardList = cardService
                .getUserRechargeCardList(shopUserRechargeCardDTO);
        if (CommonUtils.objectIsEmpty(userRechargeCardList)) {
            logger.debug("查询某个用户的充值卡列表信息查询结果为空，参数 {}",
                    "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
            responseDTO.setResult(StatusConstant.SUCCESS);
            responseDTO.setErrorInfo(BusinessErrorCode.ERROR_NULL_RECORD.getCode());
            return responseDTO;
        }
		HashMap<Object, Object> hashMap = new HashMap<>(2);
		hashMap.put("userRechargeCardList", userRechargeCardList);
		//查询用户账户总余额
		String sumAmount = cardService.getUserRechargeCardSumAmount(shopUserRechargeCardDTO).toString();
		hashMap.put("totalBalance", sumAmount);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(hashMap);

        return responseDTO;
    }

    /**
     * 查询某个用户的充值卡总金额
     *
     * @param sysUserId
     * @return
     */
    @RequestMapping(value = "/getUserRechargeSumAmount", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody
    ResponseDTO<BigDecimal> getUserRechargeSumAmount(@RequestParam String sysUserId) {

        String sysShopId = redisUtils.getShopId();
        ResponseDTO<BigDecimal> responseDTO = new ResponseDTO<>();

        if (StringUtils.isBlank(sysUserId)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        ShopUserRechargeCardDTO shopUserRechargeCardDTO = new ShopUserRechargeCardDTO();
        shopUserRechargeCardDTO.setSysUserId(sysUserId);
        shopUserRechargeCardDTO.setSysShopId(sysShopId);
        BigDecimal sumAmount = cardService.getUserRechargeCardSumAmount(shopUserRechargeCardDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(sumAmount);

        return responseDTO;
    }

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 查询充值卡列表
	 * @Date:2018/4/11 13:58
	 */
	@RequestMapping(value = "/getRechargeCardList", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> findRechargeCardList(@RequestParam(required = false) String name,
											 int pageSize) {
        String sysShopId = redisUtils.getShopId();
		PageParamVoDTO<ShopRechargeCardDTO> pageParamVoDTO = new PageParamVoDTO<>();
		ShopRechargeCardDTO shopRechargeCardDTO = new ShopRechargeCardDTO();
        shopRechargeCardDTO.setSysShopId(sysShopId);
		shopRechargeCardDTO.setName(name);

		pageParamVoDTO.setRequestData(shopRechargeCardDTO);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setPageSize(pageSize);
		// 查询数据
		List<ShopRechargeCardOrderDTO> list = shopRechargeCardService.getShopRechargeCardList(pageParamVoDTO);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取具体充值卡的信息
	 * @Date:2018/4/11 14:16
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> findRechargeCard(@PathVariable String id) {
		// 查询数据
		ShopRechargeCardDTO shopRechargeCardDTO = new ShopRechargeCardDTO();
		shopRechargeCardDTO.setId(id);
		ShopRechargeCardResponseDTO shopRechargeCardResponseDTO = shopRechargeCardService.getShopRechargeCard(shopRechargeCardDTO);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		if(shopRechargeCardResponseDTO!=null){
			ShopRechargeCardOrderDTO shopRechargeCardOrderDTO = new ShopRechargeCardOrderDTO();
			BeanUtils.copyProperties(shopRechargeCardResponseDTO, shopRechargeCardOrderDTO);
			responseDTO.setResponseData(shopRechargeCardOrderDTO);
		}
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取某个店里的套卡列表
	 * @Date:2018/4/11 15:40
	 */
	@RequestMapping(value = "/getShopProjectGroups", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<List<ProjectInfoGroupResponseDTO>> findShopProjectGroupList(
			@RequestParam(required = false) String projectGroupName, int pageSize) {

		ShopProjectGroupDTO shopProjectGroupDTO = new ShopProjectGroupDTO();
        String sysShopId = redisUtils.getShopId();
        shopProjectGroupDTO.setSysShopId(sysShopId);

		PageParamVoDTO<ShopProjectGroupDTO> pageParamVoDTO = new PageParamVoDTO<>();
		if (StringUtils.isNotBlank(projectGroupName)) {
			shopProjectGroupDTO.setProjectGroupName(projectGroupName);
		}
		pageParamVoDTO.setRequestData(shopProjectGroupDTO);
		pageParamVoDTO.setPaging(true);
		pageParamVoDTO.setPageNo(0);
		pageParamVoDTO.setPageSize(pageSize);
		// 查询数据
		List<ProjectInfoGroupResponseDTO> list = shopProjectGroupService.getShopProjectGroupList(pageParamVoDTO);

		ResponseDTO<List<ProjectInfoGroupResponseDTO>> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(list);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * @Author:huan
	 * @Param:
	 * @Return:
	 * @Description: 获取某个店里的具体套卡的信息
	 * @Date:2018/4/11 15:40
	 */
	@RequestMapping(value = "/getShopProjectGroup/detail", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<ProjectInfoGroupResponseDTO> findShopProjectGroupListe(@RequestParam String id) {
		ProjectInfoGroupResponseDTO projectInfoGroupResponseDTO = shopProjectGroupService
				.getShopProjectInfoGroupRelation(id);

		ResponseDTO<ProjectInfoGroupResponseDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(projectInfoGroupResponseDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}


	/**
	 * 查询某个用户的某个充值卡信息
	 *
	 * @param id 用户的某个充值卡id
	 * @return
	 */
	@RequestMapping(value = "/getShopUserRechargeInfo", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> getShopUserRechargeInfo(@RequestParam String id) {
		ShopUserRechargeCardDTO userRechargeCardDTO = new ShopUserRechargeCardDTO();
		userRechargeCardDTO.setId(id);
		ShopUserRechargeCardDTO shopUserRechargeInfo = shopRechargeCardService.getShopUserRechargeInfo(userRechargeCardDTO);
		ShopRechargeCardOrderDTO extShopUserRechargeCardDTO = new ShopRechargeCardOrderDTO();
		BeanUtils.copyProperties(shopUserRechargeInfo, extShopUserRechargeCardDTO);
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(extShopUserRechargeCardDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}


	/**
	 * 充值卡充值确认接口
	 *
	 * @param shopRechargeCardOrderDTO
	 * @return
	 */
	@RequestMapping(value = "/userRechargeConfirm", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<Object> userRechargeConfirm(@RequestBody ShopRechargeCardOrderDTO shopRechargeCardOrderDTO) {
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		String shopId = redisUtils.getShopId();
		shopRechargeCardOrderDTO.setTransactionId(DateUtils.DateToStr(new Date(), "dateMillisecond"));
		shopRechargeCardOrderDTO.setOrderStatus(OrderStatusEnum.WAIT_SIGN.getCode());
		shopRechargeCardOrderDTO.setOrderStatusDesc(OrderStatusEnum.WAIT_SIGN.getDesc());
		if(StringUtils.isBlank(shopRechargeCardOrderDTO.getSysUserId())){
			logger.error("用户主键为空");
			responseDTO.setErrorInfo("用户主键为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		//查询用户档案表信息
		ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();
		shopUserArchivesDTO.setSysUserId(shopRechargeCardOrderDTO.getSysUserId());
		shopUserArchivesDTO.setSysShopId(shopId);
		List<ShopUserArchivesDTO> shopUserArchivesInfo = shopCustomerArchivesService.getShopUserArchivesInfo(shopUserArchivesDTO);
		if(CommonUtils.objectIsEmpty(shopUserArchivesInfo)){
			logger.error("用户档案信息为空");
			responseDTO.setErrorInfo("用户档案信息为空");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		shopRechargeCardOrderDTO.setUserName(shopUserArchivesInfo.get(0).getSysUserName());
		shopRechargeCardOrderDTO.setSysShopId(shopId);
		mongoTemplate.save(shopRechargeCardOrderDTO, "shopRechargeCardOrderDTO");
		responseDTO.setResponseData(shopRechargeCardOrderDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 充值卡充值签字确认查询接口
	 *
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "/searchRechargeConfirm", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> searchRechargeConfirm(@RequestParam String transactionId) {
		Query query = new Query(Criteria.where("transactionId").is(transactionId));
		ShopRechargeCardOrderDTO shopUserRechargeCardDTO = mongoTemplate.findOne(query, ShopRechargeCardOrderDTO.class, "shopRechargeCardOrderDTO");
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		responseDTO.setResponseData(shopUserRechargeCardDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 充值卡充值签字确认接口
	 *
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "/rechargeCardSignConfirm", method = RequestMethod.GET)
	@ResponseBody
	ResponseDTO<Object> rechargeCardSignConfirm(@RequestParam String transactionId, @RequestParam String imageUrl) {

		ResponseDTO<Object> responseDTO = new ResponseDTO();
		RedisLock lock = null;
		try {
			lock = new RedisLock("recharge_" + transactionId);
			responseDTO = shopUserConsumeService.rechargeRechargeCard(transactionId, imageUrl);
		} catch (Exception e) {
			logger.error("异常信息为={}"+e.getMessage(),e);
			responseDTO.setErrorInfo("异常数据");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}finally {
			lock.unlock();
		}
		responseDTO.setResult(StatusConstant.SUCCESS);
		return responseDTO;
	}

	/**
	 * 新增充值卡信息
	 *
	 * @param extShopRechargeCardDTO
	 * @return
	 */
	@RequestMapping(value = "/saveRechargeCardInfo", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<Object> saveRechargeCardInfo(@RequestBody ExtShopRechargeCardDTO extShopRechargeCardDTO) {
		ResponseDTO<Object> responseDTO = new ResponseDTO<>();
		int info = cardService.saveRechargeCardInfo(extShopRechargeCardDTO);
		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
		return responseDTO;
	}

	/**
	 * 更新充值卡信息
	 *
	 * @param extShopRechargeCardDTO
	 * @return
	 */
	@RequestMapping(value = "/updateRechargeCardInfo", method = RequestMethod.POST)
	@ResponseBody
	ResponseDTO<Object> updateRechargeCardInfo(@RequestBody ExtShopRechargeCardDTO extShopRechargeCardDTO) {
		ResponseDTO<Object> responseDTO = new ResponseDTO();
		if (StringUtils.isBlank(extShopRechargeCardDTO.getId())) {
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		int info = cardService.updateRechargeCardInfo(extShopRechargeCardDTO);
		responseDTO.setResult(info > 0 ? StatusConstant.SUCCESS : StatusConstant.FAILURE);
		return responseDTO;
	}
}
