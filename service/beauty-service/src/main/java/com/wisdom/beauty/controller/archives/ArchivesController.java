package com.wisdom.beauty.controller.archives;


import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.api.responseDto.CustomerAccountResponseDto;
import com.wisdom.beauty.core.service.ShopCustomerArchivesServcie;


import com.wisdom.beauty.core.service.SysUserAccountServcie;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.ResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
public class ArchivesController {
    @Autowired
    private ShopCustomerArchivesServcie shopCustomerArchivesServcie;
    @Autowired
    private SysUserAccountServcie sysUserAccountServcie;


    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description:  获取档案列表
    *@Date:2018/4/8 10:21
    */
    @RequestMapping(value = "/archives", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<Map<String,Object>> findArchives(@RequestParam String queryField,@RequestParam String sysShopId,int pageSize ) {
        PageParamVoDTO<ShopUserArchivesDTO> pageParamVoDTO =new PageParamVoDTO<> ();

        ShopUserArchivesDTO shopUserArchivesDTO=new ShopUserArchivesDTO();
        shopUserArchivesDTO.setSysShopId(sysShopId);
        shopUserArchivesDTO.setPhone(queryField);
        shopUserArchivesDTO.setSysUserName(queryField);


        pageParamVoDTO.setRequestData(shopUserArchivesDTO);
        pageParamVoDTO.setPageNo(0);
        pageParamVoDTO.setPageSize(pageSize);
        //查询数据
        List<ShopUserArchivesDTO> list=shopCustomerArchivesServcie.getArchivesList(pageParamVoDTO);
        //查询个数
        int count=shopCustomerArchivesServcie.getArchivesCount(sysShopId);
        Map<String,Object> map=new HashMap<>(16);
        map.put("info",list);
        map.put("count",count);

        ResponseDTO<Map<String,Object>> responseDTO = new ResponseDTO<>();
        responseDTO.setResponseData(map);
        return responseDTO;
    }

    /**
    *@Author:huan
    *@Param:
    *@Return:
    *@Description: 获取用户id查询档案信息
    *@Date:2018/4/8 15:11
    */
    @RequestMapping(value = "/archives/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<CustomerAccountResponseDto> findArchive(@PathVariable String userId) {

        ResponseDTO<CustomerAccountResponseDto> responseDTO = new ResponseDTO<>();
        CustomerAccountResponseDto customerAccountResponseDto = sysUserAccountServcie.getSysAccountListByUserId(userId);
        if(customerAccountResponseDto!=null){
            responseDTO.setResponseData(customerAccountResponseDto);
        }
        return responseDTO;
    }

    //查询会员情况（有无绑定关系)
    @RequestMapping(value = "/archives/isMember/{userId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseDTO<ShopUserArchivesDTO> isMember(@PathVariable String userId) {

        ResponseDTO<ShopUserArchivesDTO> responseDTO = new ResponseDTO<>();

        return responseDTO;
    }
}
