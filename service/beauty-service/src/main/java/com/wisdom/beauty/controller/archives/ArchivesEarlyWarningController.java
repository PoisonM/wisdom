package com.wisdom.beauty.controller.archives;


import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.core.service.ShopCustomerArchivesService;
import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * ClassName: ArchivesController
 *
 * @Author： huan
 * @Description: 档案控制层
 * @Date:Created in 2018/4/4 9:26
 * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "earlyWarning")
public class ArchivesEarlyWarningController {

    @Autowired
    private ShopCustomerArchivesService shopCustomerArchivesService;


    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @param queryType 一个月未到店 one ;三个月未到店 three ;六个月未到店 six
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getEarlyWarningList", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String, Object>> findArchives(@RequestParam String queryType, @RequestParam String pageNo, @RequestParam String pageSize) {
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO = new PageParamVoDTO<>();
        //获取当前boss下的档案列表
//        shopCustomerArchivesService.getArchivesList();

        //根据queryType构造查询时间段

        //根据查询时间段查询预约列表

        //遍历档案列表将在预约列表的用户remove掉

        //返回过滤后的档案泪飙

        ShopUserArchivesDTO shopUserArchivesDTO = new ShopUserArchivesDTO();


        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        responseDTO.setResult(StatusConstant.SUCCESS);
        return responseDTO;
    }


}
