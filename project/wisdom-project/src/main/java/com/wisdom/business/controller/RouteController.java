package com.wisdom.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RouteController {

	@RequestMapping(value ="boss",method = {RequestMethod.POST, RequestMethod.GET})
	public String boss(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/bossIndex";
	}

	@RequestMapping(value ="management",method = {RequestMethod.POST, RequestMethod.GET})
	public String management(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/managementIndex";
	}

	@RequestMapping(value ="customer",method = {RequestMethod.POST, RequestMethod.GET})
	public String customer(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/customerIndex";
	}

	@RequestMapping(value ="reception",method = {RequestMethod.POST, RequestMethod.GET})
	public String business(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "angular/receptionIndex";
	}

	@RequestMapping(value ="MP_verify_338eDaTfFCfMKzmb.txt",method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String value(HttpServletResponse response) {
		response.addHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		return "338eDaTfFCfMKzmb";
	}
}
