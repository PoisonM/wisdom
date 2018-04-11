package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.enums.GoodsTypeEnum;
import com.wisdom.beauty.api.extDto.ExtShopUserConsumeRecordDTO;
import com.wisdom.beauty.core.redis.RedisUtils;
import com.wisdom.beauty.core.service.ShopProjectGroupService;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.core.service.ShopUerConsumeRecordService;
import com.wisdom.beauty.core.service.SysUserAccountService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CommonUtils;
import com.wisdom.common.util.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.wisdom.common.util.CodeGenUtil.getTransactionCodeNumber;

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

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopProjectService projectService;

	@Resource
	private ShopUerConsumeRecordService shopUerConsumeRecordService;

	@Resource
	private SysUserAccountService sysUserAccountService;

	@Resource
	private ShopProjectService shopProjectService;

    @Resource
    private ShopProjectGroupService shopProjectGroupService;

    @Resource
    private RedisUtils redisUtils;


	/**
	 * 查询某个用户预约项目列表信息
	 *
	 * @param appointmentId
	 * @return
	 */
	@RequestMapping(value = "getUserCardProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
	ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCardProjectList(@RequestParam String appointmentId) {

		long startTime = System.currentTimeMillis();

		logger.info("查询某个用户的卡片列表信息传入参数={}", "appointment = [" + appointmentId + "]");
		ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO ShopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		ShopUserProjectRelationDTO.setShopAppointmentId(appointmentId);

        List<ShopUserProjectRelationDTO> projectList = projectService.getUserProjectList(ShopUserProjectRelationDTO);
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData(projectList);

		logger.info("查询某个用户的卡片列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
     * 用户充值操作，适用范围为消费单次卡、疗程卡、套卡、产品
	 *
     * @param extShopUserConsumeRecordDTOS
	 * @return
	 */
	@RequestMapping(value = "userRechargeOrConsumeOperation", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
	public
	@ResponseBody
    @Transactional
	ResponseDTO<String> userRechargeOrConsumeOperation(@RequestBody List<ExtShopUserConsumeRecordDTO> extShopUserConsumeRecordDTOS) {

		long currentTimeMillis = System.currentTimeMillis();
		logger.info("传入参数={}", "shopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");

		ResponseDTO<String> responseDTO = new ResponseDTO<>();

		if (CommonUtils.objectIsNotEmpty(extShopUserConsumeRecordDTOS)) {
			logger.debug("用户充值或消费操作传入参数为空, {}", "shopUserConsumeRecordDTOS = [" + extShopUserConsumeRecordDTOS + "]");
			return null;
		}
        //生成唯一的交易流水号
        String transactionCodeNumber = getTransactionCodeNumber();

        try {

            //遍历记录
            for (ExtShopUserConsumeRecordDTO dto : extShopUserConsumeRecordDTOS) {
                //虚拟商品类型
                String goodsType = dto.getGoodsType();

                //获取用户的账户信息
                SysUserAccountDTO sysUserAccountDTO = sysUserAccountService.getSysUserAccountDTO(dto.getSysUserId());

                //如果是次卡、疗程卡相关操作
                if (GoodsTypeEnum.TIME_CARD.getCode().equals(goodsType) || GoodsTypeEnum.TREATMENT_CARD.getCode().equals(goodsType)) {

                    if (dto.getShopUserProjectRelationDTO() == null) {
                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
                        throw new RuntimeException();
                    }
                    //生成用户与项目的关系
                    ShopUserProjectRelationDTO shopUserRelationDTO = dto.getShopUserProjectRelationDTO();
                    shopUserRelationDTO.setCreateDate(new Date());
                    String uuid = IdGen.uuid();
                    shopUserRelationDTO.setId(uuid);
                    dto.setFlowId(uuid);
                    shopProjectService.saveUserProjectRelation(shopUserRelationDTO);
                }
                //如果是套卡相关操作
                else if (GoodsTypeEnum.COLLECTION_CARD.getCode().equals(goodsType)) {
                    if (dto.getShopProjectGroupDTO() == null) {
                        logger.error("用户充值操作用户与卡的关系为空,{}", "ShopUserProjectRelationDTO = [" + dto.getShopUserProjectRelationDTO() + "]");
                        throw new RuntimeException();
                    }
                    ShopProjectGroupDTO shopProjectGroupDTO = dto.getShopProjectGroupDTO();

                    //根据套卡id查询项目列表
                    ShopProjectInfoGroupRelationDTO shopProjectInfoGroupRelationDTO = new ShopProjectInfoGroupRelationDTO();
                    shopProjectInfoGroupRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
                    shopProjectInfoGroupRelationDTO.setSysShopId(shopProjectInfoGroupRelationDTO.getSysShopId());
                    List<ShopProjectInfoGroupRelationDTO> groupRelations = shopProjectService.getShopProjectInfoGroupRelations(shopProjectInfoGroupRelationDTO);

                    if (null == groupRelations) {
                        logger.error("根据项目套卡主键查询出来的项目列表为空，{}", groupRelations);
                        throw new RuntimeException();
                    }
                    //如果套卡能买多套
                    for (int i = 0; i < dto.getConsumeNumber(); i++) {
                        //生成用户跟套卡与项目的关系的关系
                        for (ShopProjectInfoGroupRelationDTO dt : groupRelations) {
                            //查询项目信息
                            ShopProjectInfoDTO shopProjectInfoDTO = redisUtils.getShopProjectInfoFromRedis(dt.getShopProjectInfoId());
                            ShopUserProjectGroupRelRelationDTO groupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
                            groupRelRelationDTO.setSysShopId(dto.getSysShopId());
                            groupRelRelationDTO.setSysUserId(dto.getSysUserId());
                            groupRelRelationDTO.setId(IdGen.uuid());
                            groupRelRelationDTO.setProjectInitAmount(shopProjectInfoDTO.getMarketPrice());
                            groupRelRelationDTO.setProjectInitTimes(shopProjectInfoDTO.getMaxContainTimes());
                            groupRelRelationDTO.setProjectSurplusAmount(shopProjectInfoDTO.getMarketPrice());
                            groupRelRelationDTO.setProjectSurplusTimes(shopProjectInfoDTO.getMaxContainTimes());
                            groupRelRelationDTO.setShopProjectGroupId(shopProjectGroupDTO.getId());
                            groupRelRelationDTO.setShopProjectGroupName(shopProjectGroupDTO.getProjectGroupName());
                            groupRelRelationDTO.setShopProjectInfoGroupRelationId(dt.getId());
                            groupRelRelationDTO.setSysBossId(shopProjectInfoDTO.getSysBossId());
                            shopProjectGroupService.saveShopUserProjectGroupRelRelation(groupRelRelationDTO);
                        }
                    }
                    dto.setFlowId(shopProjectGroupDTO.getId());
                }
                //如果是产品相关
                else if (GoodsTypeEnum.PRODUCT.getCode().equals(goodsType)) {
                    //生成用户跟产品的关系
                    ShopUserProductRelationDTO userProductRelationDTO = dto.getShopUserProductRelationDTO();
                    String uuid = IdGen.uuid();
                    userProductRelationDTO.setId(uuid);
                    dto.setFlowId(uuid);
                }
                //dto.getPrice()为此次交易的总价格
                sysUserAccountDTO.setSumAmount(sysUserAccountDTO.getSumAmount().add(dto.getPrice()));

                //更新用户的账户信息
                try {
                    sysUserAccountService.updateSysUserAccountDTO(sysUserAccountDTO);
                } catch (Exception e) {
                    logger.error("更新账户信息失败，失败信息为{}" + e.getMessage(), e);
                    throw new RuntimeException();
                }

                dto.setFlowNo(transactionCodeNumber);
                int record = shopUerConsumeRecordService.saveCustomerConsumeRecord(dto);
                logger.debug("保存用户充值或消费操作返回结果 {}", record > 0 ? "成功" : "失败");
            }
        } catch (Exception e) {
            logger.error("充值失败，失败原因为，{}" + e.getMessage(), e);
            throw new RuntimeException();
        }
		//保存用户的操作记录
		responseDTO.setResult(StatusConstant.SUCCESS);
		responseDTO.setResponseData("success");
        logger.info("用户充值操作耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
	}

    /**
     * 查询某个店的疗程卡、单次卡列表信息 "0", "疗程卡"  "1", "单次")
     *
     * @return
     */
    @RequestMapping(value = "getShopCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopProjectInfoDTO>> getShopCourseProjectList(@RequestParam String sysShopId, @RequestParam String useStyle) {

        long currentTimeMillis = System.currentTimeMillis();

        logger.info("查询某个店的疗程卡列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
        ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();

        ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
        shopProjectInfoDTO.setSysShopId(sysShopId);
        shopProjectInfoDTO.setUseStyle(useStyle);
        List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);

        if (CommonUtils.objectIsEmpty(projectList)) {
            logger.debug("查询某个店的疗程卡列表信息查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
            return null;
        }

        responseDTO.setResponseData(projectList);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("查询某个店的疗程卡列表信息耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


    /**
     * 查询某个用户的卡片列表信息
     *
     * @param sysUserId
     * @param sysShopId
     * @param cardStyle 0 查询疗程卡  1 查询单次卡
     * @return
     */
    @RequestMapping(value = "getUserCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCourseProjectList(@RequestParam String sysUserId,
                                                                           @RequestParam String sysShopId, @RequestParam String cardStyle) {
        long currentTimeMillis = System.currentTimeMillis();
        logger.info("传入参数={}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "], cardStyle = [" + cardStyle + "]");
        ResponseDTO<List<ShopUserProjectRelationDTO>> responseDTO = new ResponseDTO<>();

        ShopUserProjectRelationDTO relationDTO = new ShopUserProjectRelationDTO();
        relationDTO.setSysUserId(sysUserId);
        relationDTO.setSysShopId(sysShopId);
        relationDTO.setUseStyle(cardStyle);
        List<ShopUserProjectRelationDTO> userProjectList = projectService.getUserProjectList(relationDTO);
        if (CommonUtils.objectIsEmpty(userProjectList)) {
            logger.debug("查询某个用户的卡片列表信息为空, {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "], cardStyle = [" + cardStyle + "]");
            return null;
        }

        responseDTO.setResponseData(userProjectList);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某个用户的套卡列表信息
     *
     * @param sysUserId
     * @param sysShopId
     * @return
     */
    @RequestMapping(value = "getUserProjectGroupList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<HashMap<String, Object>>> getUserProjectGroupList(@RequestParam String sysUserId,
                                                                       @RequestParam String sysShopId) {
        ResponseDTO<List<HashMap<String, Object>>> responseDTO = new ResponseDTO<>();

        if (StringUtils.isBlank(sysShopId) || StringUtils.isBlank(sysUserId)) {
            logger.debug("查询某个用户的套卡列表信息传入参数为空， {}", "sysUserId = [" + sysUserId + "], sysShopId = [" + sysShopId + "]");
            return null;
        }

        //查询用户的套卡信息
        ShopUserProjectGroupRelRelationDTO shopUserProjectGroupRelRelationDTO = new ShopUserProjectGroupRelRelationDTO();
        shopUserProjectGroupRelRelationDTO.setSysUserId(sysUserId);
        shopUserProjectGroupRelRelationDTO.setSysShopId(sysShopId);
        List<ShopUserProjectGroupRelRelationDTO> userCollectionCardProjectList = projectService.getUserCollectionCardProjectList(shopUserProjectGroupRelRelationDTO);

        Map<String, String> helperMap = new HashMap<>(16);

        //套卡主键保存到helperMap中
        if (CommonUtils.objectIsNotEmpty(userCollectionCardProjectList)) {
            for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
                helperMap.put(dto.getShopProjectGroupId(), dto.getShopProjectGroupId());
            }
        }

        List<HashMap<String, Object>> returnList = new ArrayList<>();
        if (CommonUtils.objectIsNotEmpty(helperMap)) {
            //遍历每个项目组
            for (Map.Entry entry : helperMap.entrySet()) {
                //套卡map
                HashMap<String, Object> map = new HashMap<>(6);
                //套卡总金额
                BigDecimal bigDecimal = new BigDecimal(0);
                //套卡名称
                String projectGroupName = null;
                ArrayList<Object> arrayList = new ArrayList<>();
                for (ShopUserProjectGroupRelRelationDTO dto : userCollectionCardProjectList) {
                    if (entry.getKey().equals(dto.getShopProjectGroupId())) {
                        arrayList.add(dto);
                        bigDecimal = bigDecimal.add(dto.getProjectInitAmount());
                        projectGroupName = dto.getShopProjectGroupName();
                    }
                }
                map.put("projectList", arrayList);
                map.put("totalAmount", bigDecimal);
                map.put("projectGroupName", projectGroupName);
                returnList.add(map);
            }
        }
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(returnList);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取一级列表
     * @Date:2018/4/10 17:29
     */
    @RequestMapping(value = "/oneLevelProject", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProjectTypeDTO>> findOneLevelProject(@RequestParam String sysShopId) {
        long currentTimeMillis = System.currentTimeMillis();
        ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
        List<ShopProjectTypeDTO> list = projectService.getOneLevelProjectList(sysShopId);
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级项目的id
     * @Return:
     * @Description: 获取二级列表
     * @Date:2018/4/10 17:36
     */
    @RequestMapping(value = "/twoLevelProject", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProjectTypeDTO>> findTwoLevelProject(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();
        ShopProjectTypeDTO shopProjectTypeDTO = new ShopProjectTypeDTO();
        shopProjectTypeDTO.setId(id);
        //查询数据
        List<ShopProjectTypeDTO> list = projectService.getTwoLevelProjectList(shopProjectTypeDTO);

        ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param: id是一级项目的id
     * @Return:
     * @Description: 获取三级列表
     * @Date:2018/4/10 17:36
     */
    @RequestMapping(value = "/threeLevelProject", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<List<ShopProjectInfoDTO>> findThreeLevelProject(@RequestParam String sysShopId, @RequestParam String projectTypeOneId,
                                                                @RequestParam String ProjectTypeTwoId, @RequestParam int pageSize) {
        long currentTimeMillis = System.currentTimeMillis();
        PageParamVoDTO<ShopProjectInfoDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();

        shopProjectInfoDTO.setSysShopId(sysShopId);
        shopProjectInfoDTO.setProjectTypeOneId(projectTypeOneId);
        shopProjectInfoDTO.setProjectTypeTwoId(ProjectTypeTwoId);

        pageParamVoDTO.setRequestData(shopProjectInfoDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopProjectInfoDTO> list = projectService.getThreeLevelProjectList(pageParamVoDTO);

        ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description:
     * @Date:2018/4/10 18:06
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopProjectInfoDTO> findDetailProject(@RequestParam String id) {
        long currentTimeMillis = System.currentTimeMillis();
        //查询数据
        ShopProjectInfoDTO shopProjectInfoDTO = projectService.getProjectDetail(id);

        ResponseDTO<ShopProjectInfoDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopProjectInfoDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }
}
