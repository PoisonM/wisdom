package com.wisdom.beauty.controller.product;

import com.wisdom.beauty.api.dto.ShopUserProductRelationDTO;
import com.wisdom.beauty.interceptor.LoginRequired;
import com.wisdom.common.dto.system.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: productController
 *
 * @author: 赵得良
 * Date:     2018/4/3 0003 15:06
 * Description: 预约相关
 */
@Controller
@RequestMapping(value = "productInfo")
public class ProductController {

	/**
     * 查询某个用户的产品列表信息
	 * @param sysCustomerId
     * @param sysShopId
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "getUserProductList", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
    ResponseDTO<List<ShopUserProductRelationDTO>> getUserProductList(@RequestParam String sysCustomerId,
                                                                     @RequestParam String sysShopId,
                                                                     @RequestParam String startDate,
                                                                     @RequestParam String endDate) {
		ResponseDTO<List<ShopUserProductRelationDTO>> responseDTO = new ResponseDTO<>();

		return  responseDTO;
	}



}
