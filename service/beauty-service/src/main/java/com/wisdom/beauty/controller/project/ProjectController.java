package com.wisdom.beauty.controller.project;

import com.wisdom.beauty.api.dto.ShopProjectInfoDTO;
import com.wisdom.beauty.api.dto.ShopProjectTypeDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectGroupRelRelationDTO;
import com.wisdom.beauty.api.dto.ShopUserProjectRelationDTO;
import com.wisdom.beauty.api.enums.CardTypeEnum;
import com.wisdom.beauty.core.service.ShopProjectService;
import com.wisdom.beauty.util.UserUtils;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.user.SysClerkDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ResponseDTO<HashMap<Object, Object>> getUserCardProjectList(@RequestParam String appointmentId) {

		long startTime = System.currentTimeMillis();

		logger.info("查询某个用户的卡片列表信息传入参数={}", "appointment = [" + appointmentId + "]");
        ResponseDTO<HashMap<Object, Object>> responseDTO = new ResponseDTO<>();

		ShopUserProjectRelationDTO ShopUserProjectRelationDTO = new ShopUserProjectRelationDTO();
		ShopUserProjectRelationDTO.setShopAppointmentId(appointmentId);

        List<ShopUserProjectRelationDTO> projectList = projectService.getUserProjectList(ShopUserProjectRelationDTO);
        if (CommonUtils.objectIsEmpty(projectList)) {
            responseDTO.setResult(StatusConstant.FAILURE);
            return responseDTO;
        }
        //分组，需要购买的一组（预约的时候建立关系，可使用次数为0），直接划卡(可使用次数不为0)的一组
        HashMap<Object, Object> returnMap = new HashMap<>();
        ArrayList<Object> payList = new ArrayList<>();
        ArrayList<Object> consumeList = new ArrayList<>();
        for (ShopUserProjectRelationDTO dto : projectList) {
            Integer surplusTimes = dto.getSysShopProjectSurplusTimes();
            if (null != surplusTimes && surplusTimes > 0) {
                payList.add(dto);
            } else {
                consumeList.add(dto);
            }
        }
        returnMap.put("consume", payList);
        returnMap.put("punchCard", consumeList);
        responseDTO.setResult(StatusConstant.SUCCESS);
        responseDTO.setResponseData(returnMap);

		logger.info("查询某个用户的卡片列表信息耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

    /**
     * 查询某个店的疗程卡、单次卡列表信息 :"0", "疗程卡"  "1", "单次" "2","所有")
     *
     * @return
     */
    @RequestMapping(value = "getShopCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopProjectInfoDTO>> getShopCourseProjectList(@RequestParam String useStyle, @RequestParam String filterStr) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();
        logger.info("查询某个店的疗程卡列表信息传入参数={}", "sysShopId = [" + sysShopId + "]");
        ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();

        ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
        shopProjectInfoDTO.setSysShopId(sysShopId);
        shopProjectInfoDTO.setProjectName(filterStr);
        if (StringUtils.isNotBlank(useStyle) && (!CardTypeEnum.ALL.getCode().equals(useStyle))) {
            shopProjectInfoDTO.setUseStyle(useStyle);
        }
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
     * @param cardStyle 0 查询疗程卡  1 查询单次卡
     * @return
     */
    @RequestMapping(value = "getUserCourseProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<ShopUserProjectRelationDTO>> getUserCourseProjectList(@RequestParam String sysUserId, @RequestParam String cardStyle) {
        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();
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
    ResponseDTO<List<ShopProjectTypeDTO>> findOneLevelProject() {
        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        ResponseDTO<List<ShopProjectTypeDTO>> responseDTO = new ResponseDTO<>();
        List<ShopProjectTypeDTO> list = projectService.getOneLevelProjectList(sysClerkDTO.getSysShopId());
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
        logger.info("findTwoLevelProject方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
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
    ResponseDTO<List<ShopProjectInfoDTO>> findThreeLevelProject(@RequestParam String projectTypeOneId,
                                                                @RequestParam String ProjectTypeTwoId,
                                                                @RequestParam(required = false) String projectName,
                                                                @RequestParam int pageSize) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO sysClerkDTO=UserUtils.getClerkInfo();
        PageParamVoDTO<ShopProjectInfoDTO> pageParamVoDTO = new PageParamVoDTO<>();
        ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();

        shopProjectInfoDTO.setSysShopId(sysClerkDTO.getSysShopId());
        shopProjectInfoDTO.setProjectTypeOneId(projectTypeOneId);
        shopProjectInfoDTO.setProjectTypeTwoId(ProjectTypeTwoId);
        shopProjectInfoDTO.setProjectName(projectName);

        pageParamVoDTO.setRequestData(shopProjectInfoDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopProjectInfoDTO> list = projectService.getThreeLevelProjectList(pageParamVoDTO);

        ResponseDTO<List<ShopProjectInfoDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(list);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findThreeLevelProject方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * @Author:huan
     * @Param:
     * @Return:
     * @Description: 获取项目的详细信息
     * @Date:2018/4/10 18:06
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopProjectInfoDTO> findDetailProject(@PathVariable String id) {
        long currentTimeMillis = System.currentTimeMillis();
        //查询数据
        ShopProjectInfoDTO shopProjectInfoDTO = projectService.getProjectDetail(id);

        ResponseDTO<ShopProjectInfoDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(shopProjectInfoDTO);
        responseDTO.setResult(StatusConstant.SUCCESS);
        logger.info("findDetailProject方法耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }

    /**
     * 查询某店的项目列表
     *
     * @return
     */
    @RequestMapping(value = "getShopProjectList", method = {RequestMethod.POST, RequestMethod.GET})
//	@LoginRequired
    public
    @ResponseBody
    ResponseDTO<List<Object>> getShopProjectList(@RequestParam String pageNo, @RequestParam String pageSize, @RequestParam String filterStr) {

        long currentTimeMillis = System.currentTimeMillis();
        SysClerkDTO clerkInfo = UserUtils.getClerkInfo();
        String sysShopId = clerkInfo.getSysShopId();
        logger.info("查询某店的项目列表传入参数={}", "pageNo = [" + pageNo + "], pageSize = [" + pageSize + "], filterStr = [" + filterStr + "]");

        ResponseDTO<List<Object>> responseDTO = new ResponseDTO<>();

        ShopProjectInfoDTO shopProjectInfoDTO = new ShopProjectInfoDTO();
        shopProjectInfoDTO.setSysShopId(sysShopId);
        if (StringUtils.isNotBlank(filterStr)) {
            shopProjectInfoDTO.setProjectName(filterStr);
        }
        List<ShopProjectInfoDTO> projectList = projectService.getShopCourseProjectList(shopProjectInfoDTO);
        if (CommonUtils.objectIsEmpty(projectList)) {
            logger.debug("查询某店的项目列表，个数为空");
            return null;
        }

        //缓存一级项目map
        HashMap<Object, Object> oneTypeMap = new HashMap<>(16);
        for (ShopProjectInfoDTO infoDTO : projectList) {
            oneTypeMap.put(infoDTO.getProjectTypeOneId(), infoDTO.getProjectTypeOneName());
        }

        List<Object> arrayList = new ArrayList<Object>();
        //查询一级项目下的三级项目
        for (Map.Entry entry : oneTypeMap.entrySet()) {
            HashMap<Object, Object> oneMap = new HashMap<>(16);
            ArrayList<Object> threeProjectList = new ArrayList<>();
            for (ShopProjectInfoDTO infoDTO : projectList) {
                if (infoDTO.getProjectTypeOneId().equals(entry.getKey())) {
                    threeProjectList.add(infoDTO);
                }
            }
            oneMap.put(entry.getValue(), threeProjectList);
            arrayList.add(oneMap);
        }

        if (CommonUtils.objectIsEmpty(projectList)) {
            logger.debug("查询某店的项目列表查询结果为空，{}", "sysShopId = [" + sysShopId + "]");
            return null;
        }

        responseDTO.setResponseData(arrayList);
        responseDTO.setResult(StatusConstant.SUCCESS);

        logger.info("查询某店的项目列表耗时{}毫秒", System.currentTimeMillis() - currentTimeMillis);
        return responseDTO;
    }


}
