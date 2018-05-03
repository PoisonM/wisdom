package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopAppointServiceDTO;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;
import com.wisdom.common.dto.system.PageParamDTO;

import java.util.HashMap;
import java.util.List;

/**
 * FileName: AppointmentService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
public interface ShopAppointmentService {

    /**
     * 根据时间查询查询某个店的有预约号源的美容师列表
     * @param extShopAppointServiceDTO
     * @return
     */
    List<ShopAppointServiceDTO> getShopAppointClerkInfoByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO);

    /**
     * 根据时间查询某个店下的某个美容师的预约列表
     * @param extShopAppointServiceDTO
     * @return
     */
    List<ShopAppointServiceDTO> getShopClerkAppointListByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO);

    /**
     * 更新预约信息
     *
     * @param shopAppointServiceDTO
     * @return
     */
    int updateAppointmentInfo(ShopAppointServiceDTO shopAppointServiceDTO);
    /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 根据用户ID查询预约信息
     *@Date:2018/4/8 14:26
     */
    ShopAppointServiceDTO getShopAppointService(ShopAppointServiceDTO shopAppointServiceDTO);


    /**
     *  查询某个美容院某个时间预约个数或某个店员的预约总数
     *  @param  sysShopId 店铺id
     *  @param  appointStartTimeS 预约开始时间（范围起始值）
     *  @param  appointStartTimeE  appointStartTimeE（范围结束值）
     *  @return  某一个时间段的数量数量
     *  @autur zhangchao
     * */
    HashMap<String,String> findNumForShopByTimeService(String sysShopId, String sysClerkId, String appointStartTimeS, String appointStartTimeE);


    /**
     *  查询某个美容院某个时间预约个数
     *  @param  pageParamDTO 分页对象
     *  @return  shopAppointUserInfoList 预约用户列表
     *  @autur zhangchao
     * */
    PageParamDTO<List<ExtShopAppointServiceDTO>> findUserInfoForShopByTimeService(PageParamDTO<ExtShopAppointServiceDTO> pageParamDTO);
    /**
     * 保存用户的预约信息
     */
    int saveUserShopAppointInfo(ShopAppointServiceDTO shopAppointServiceDTO);

}
