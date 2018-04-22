package com.wisdom.beauty.core.service;

import com.wisdom.beauty.api.dto.ShopAppointService;
import com.wisdom.beauty.api.extDto.ExtShopAppointServiceDTO;

import java.util.List;

/**
 * FileName: AppointmentService
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
public interface AppointmentService {

    /**
     * 根据时间查询查询某个店的有预约号源的美容师列表
     * @param extShopAppointServiceDTO
     * @return
     */
    List<ShopAppointService> getShopAppointClerkInfoByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO);

    /**
     * 根据时间查询某个店下的某个美容师的预约列表
     * @param extShopAppointServiceDTO
     * @return
     */
    List<ShopAppointService> getShopClerkAppointListByCriteria(ExtShopAppointServiceDTO extShopAppointServiceDTO);

    /**
     * 更新预约信息
     *
     * @param shopAppointServiceDTO
     * @return
     */
    int updateAppointmentInfo(ShopAppointService shopAppointServiceDTO);
    /**
     *@Author:huan
     *@Param:
     *@Return:
     *@Description: 根据用户ID查询预约信息
     *@Date:2018/4/8 14:26
     */
    ShopAppointService getShopAppointService(String userId);

}
