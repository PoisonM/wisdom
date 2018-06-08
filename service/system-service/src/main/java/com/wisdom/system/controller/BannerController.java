package com.wisdom.system.controller;

import com.wisdom.common.constant.StatusConstant;
import com.wisdom.common.dto.product.OfflineProductDTO;
import com.wisdom.common.dto.system.BannerDTO;
import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.util.CodeGenUtil;
import com.wisdom.common.util.DateUtils;
import com.wisdom.system.interceptor.LoginRequired;
import com.wisdom.system.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "banner")
public class BannerController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BannerService bannerService;
	/**
	 * 获取 Banner 图
	 *
			 */
	@RequestMapping(value = "getHomeBannerList", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<List<BannerDTO>> getHomeBannerList() {
		ResponseDTO responseDto=new ResponseDTO<>();
		List<BannerDTO> list = bannerService.getHomeBannerList();
		if(list.size()>0){
			responseDto.setResponseData(list);
			responseDto.setResult(StatusConstant.SUCCESS);
		}else{
			responseDto.setResult(StatusConstant.FAILURE);
		}
		return responseDto;
	}

	/**
	 * 新增homeBanner
	 * @param bannerDTO
	 * @return
	 */
	@RequestMapping(value = "addHomeBanner", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO addHomeBanner(@RequestBody BannerDTO bannerDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("新增banner开始={}", bannerDTO);
		ResponseDTO responseDTO = new ResponseDTO<>();
		BannerDTO bannerDTODown = null;
		boolean nextBanner = true;
		String bannerId = CodeGenUtil.getProductCodeNumber();
		int bannerRank = bannerDTO.getBannerRank();
		if(null == bannerDTO.getUri()){
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		BannerDTO bannerDTOOld = bannerService.findHomeBannerInfoByBannerRank(bannerRank);
		if(null != bannerDTOOld){
			logger.info("新增banner,此banner图层={}已有banner图",bannerDTO.getBannerRank());
			//后面的banner全都向后排序
			while (nextBanner) {
				bannerDTODown = bannerService.findHomeBannerInfoByBannerRank(bannerRank);
				if(null != bannerDTODown){
					logger.info("新增Banner,该层bannerId为={}楼层为={}需要下移到={}", bannerDTODown.getBannerId(),bannerRank,bannerRank+1);
					bannerDTODown.setBannerRank(bannerRank+1);
					bannerService.updateHomeBanner(bannerDTODown);
					bannerRank++;
				}else {
					logger.info("新增Banner方法,各层banner已全部下移,没有下层banner了此楼层={}", bannerRank);
					nextBanner = false;
				}
			}
		}else {
			//新增默认为最后一个
			List<BannerDTO> list = bannerService.getHomeBannerList();
			if(0 == list.size()){
				bannerDTO.setBannerRank(list.size());
			}else {
				bannerDTO.setBannerRank(list.size()+1);
			}
		}
		try {
			bannerDTO.setBannerId(bannerId);
			bannerDTO.setPlace("home");
			bannerDTO.setStatus("1");
			bannerDTO.setCreateDate( DateUtils.getDate());
			bannerService.addHomeBanner(bannerDTO);
			logger.info("新增bannerId={}楼层={}", bannerId,bannerDTO.getBannerRank());
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("新增banner异常,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("新增banner结束耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑banner
	 * @param bannerDTO
	 * @return
	 */
	@RequestMapping(value = "updateHomeBanner", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO updateHomeBanner(@RequestBody BannerDTO bannerDTO) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑homeBanner开始={}", bannerDTO);
		ResponseDTO responseDTO = new ResponseDTO<>();
		if(null == bannerDTO.getUri()){
			logger.info("编辑homeBanner没有url");
			responseDTO.setResult(StatusConstant.FAILURE);
			return responseDTO;
		}
		if(null == bannerDTO.getBannerId() || "".equals(bannerDTO.getBannerId())){
			String bannerId = CodeGenUtil.getProductCodeNumber();
			bannerDTO.setBannerId(bannerId);
		}
		logger.info("编辑homeBannerId={}", bannerDTO.getBannerId());
		try {
			bannerService.updateHomeBanner(bannerDTO);
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("编辑homeBanner异常,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑homeBanner结束耗时{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 编辑homeBanner-上移下移
	 * @param status 上移up,down下移
	 * @param bannerId homeBanenrId
	 * @return
	 */
	@RequestMapping(value = "updateHomeBannerRank", method = {RequestMethod.POST, RequestMethod.GET})
	@LoginRequired
	public
	@ResponseBody
	ResponseDTO updateHomeBannerRank(@RequestParam String status,@RequestParam String bannerId) {
		long startTime = System.currentTimeMillis();
		logger.info("编辑homeBannerId=={}-上移下移=={},开始={}", bannerId,status,startTime);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			BannerDTO bannerDTO = bannerService.findHomeBannerInfoById(bannerId);
			if(null == bannerDTO){
				logger.info("编辑homeBannerId-上移下移,出错此bannerId={}查出banner为null",bannerId);
				responseDTO.setResult(StatusConstant.FAILURE);
				return responseDTO;
			}
			if("up".equals(status)){
				int bannerRank = bannerDTO.getBannerRank();
				if(bannerDTO.getBannerRank()-1 < 0){
					logger.info("编辑homeBannerId-上移,出错此bannerId={}已为最上层无法再上移",bannerId);
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				//查出此banner上一层banner
				BannerDTO bannerDTODown = bannerService.findHomeBannerInfoByBannerRank(bannerRank-1);
				if(null == bannerDTO){
					logger.info("编辑homeBannerId-上移,出错此bannerRank={}查出banner为null",bannerRank-1);
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				//下移,上层banner
				bannerDTODown.setBannerRank(bannerRank);
				//上移,此banner图
				bannerDTO.setBannerRank(bannerRank-1);
				bannerService.updateHomeBanner(bannerDTO);
				bannerService.updateHomeBanner(bannerDTODown);
			}else if("down".equals(status)){
				int bannerRank = bannerDTO.getBannerRank();
				BannerDTO bannerDTODown = bannerService.findHomeBannerInfoByBannerRank(bannerRank+1);
				if(null == bannerDTO){
					logger.info("编辑homeBannerId-下移,出错此bannerRank={}查出banner为null",bannerRank+1);
					responseDTO.setResult(StatusConstant.FAILURE);
					return responseDTO;
				}
				//上移,下层banner
				bannerDTODown.setBannerRank(bannerRank-1);
				//下移,此banner图
				bannerDTO.setBannerRank(bannerRank+1);
				bannerService.updateHomeBanner(bannerDTO);
				bannerService.updateHomeBanner(bannerDTODown);
			}
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("编辑homeBanner-上移下移,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("编辑homeBanner-上移下移{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}

	/**
	 * 根据bannerId查询banner信息
	 * @param bannerId
	 * @return
	 */
	@RequestMapping(value = "findHomeBannerInfoById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<BannerDTO> findHomeBannerInfoById(@RequestParam String bannerId) {
		long startTime = System.currentTimeMillis();
		logger.info("根据bannerId查询banner信息,开始={}", bannerId);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			BannerDTO bannerDTO = bannerService.findHomeBannerInfoById(bannerId);
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setResponseData(bannerDTO);
		} catch (Exception e) {
			logger.info("根据bannerId查询banner信息,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("根据bannerId查询banner信息{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 根据banner楼层bannerRank查询banner信息
	 * @param bannerRank
	 * @return
	 */
	@RequestMapping(value = "findHomeBannerInfoByBannerRank", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<BannerDTO> findHomeBannerInfoByBannerRank(@RequestParam int bannerRank) {
		long startTime = System.currentTimeMillis();
		logger.info("根据banner楼层bannerRank查询banner信息,开始={}", bannerRank);
		ResponseDTO responseDTO = new ResponseDTO<>();
		try {
			BannerDTO bannerDTO = bannerService.findHomeBannerInfoByBannerRank(bannerRank);
			responseDTO.setResult(StatusConstant.SUCCESS);
			responseDTO.setResponseData(bannerDTO);
		} catch (Exception e) {
			logger.info("根据banner楼层bannerRank查询banner信息,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("根据banner楼层bannerRank查询banner信息{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
	/**
	 * 删除Banner
	 * @param bannerId
	 * @return
	 */
	@RequestMapping(value = "delHomeBannerById", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseDTO<BannerDTO> delHomeBannerById(@RequestParam String bannerId) {
		long startTime = System.currentTimeMillis();
		logger.info("删除Banner,开始={}", bannerId);
		ResponseDTO responseDTO = new ResponseDTO<>();
		BannerDTO bannerDTODown = null;
		boolean nextBanner = true;
		try {
			BannerDTO bannerDTO = bannerService.findHomeBannerInfoById(bannerId);
			int bannerRank = bannerDTO.getBannerRank()+1;
			logger.info("删除Banner,删除的banner楼层为={}", bannerRank-1);
			bannerService.delHomeBannerById(bannerId);
			//后面的banner全都向前排序
			while (nextBanner) {
				bannerDTODown = bannerService.findHomeBannerInfoByBannerRank(bannerRank);
				if(null != bannerDTODown){
					logger.info("删除Banner,下层bannerId为={}楼层为={}需要前移到={}", bannerDTODown.getBannerId(),bannerRank,bannerRank-1);
					bannerDTODown.setBannerRank(bannerRank-1);
					bannerService.updateHomeBanner(bannerDTODown);
					bannerRank++;
				}else {
					logger.info("删除Banner方法,下层banner已全部前移,没有下层banner了={}", bannerRank);
					nextBanner = false;
				}
			}
			responseDTO.setResult(StatusConstant.SUCCESS);
		} catch (Exception e) {
			logger.info("删除Banner,异常信息为={}"+e.getMessage(),e);
			e.printStackTrace();
			responseDTO.setResult(StatusConstant.FAILURE);
		}
		logger.info("删除Banner{}毫秒", (System.currentTimeMillis() - startTime));
		return responseDTO;
	}
}
