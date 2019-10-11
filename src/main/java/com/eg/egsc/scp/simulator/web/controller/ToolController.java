package com.eg.egsc.scp.simulator.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.component.DeviceEnv;
import com.eg.egsc.scp.simulator.web.service.IDeviceService;

@Controller
public class ToolController {
	
	@Autowired
	private IDeviceService deviceService;
	
	@RequestMapping(value="/getRandomMac",method = RequestMethod.GET)
	@ResponseBody
	public String getRandomMac() {
		return JSON.toJSONString(DeviceEnv.createMac());
	}
	
	
	@RequestMapping(value="/createDevicePort",method = RequestMethod.GET)
	@ResponseBody
	public Integer createDevicePort() {
		return deviceService.createDevicePort();
	}
	
	

}
