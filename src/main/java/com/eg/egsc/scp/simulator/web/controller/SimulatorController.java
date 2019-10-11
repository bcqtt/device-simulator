package com.eg.egsc.scp.simulator.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.channel.SimulatorChannel;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.DeviceType;
import com.eg.egsc.scp.simulator.component.DeviceEnv;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;
import com.eg.egsc.scp.simulator.web.service.IDeviceService;

import io.netty.channel.ChannelFuture;

@Controller
public class SimulatorController {

	private static final Log log = LogFactory.getLog(SimulatorController.class);
	
	@Autowired
	private IDeviceService deviceService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		List<VirtualDevice> list = deviceService.queryAllDevice();
		ModelAndView mav = new ModelAndView();
		mav.addObject("localip",DeviceEnv.getClientIp(request));
		mav.addObject("localMac",DeviceEnv.getLocalMac());
		mav.addObject("localMask",DeviceEnv.getSubnetMask());
		mav.addObject("localVersion",DeviceEnv.getSystemVersion());
		mav.addObject("deviceList",list);
		mav.setViewName("home");
		return mav;
	}
	
	@RequestMapping(value = "/websocket", method = RequestMethod.GET)
	public ModelAndView websocket() {
		List<VirtualDevice> list = deviceService.queryAllDevice();
		ModelAndView mav = new ModelAndView();
		mav.addObject("localip",DeviceEnv.getLocalIp());
		mav.addObject("localMac",DeviceEnv.getLocalMac());
		mav.addObject("localMask",DeviceEnv.getSubnetMask());
		mav.addObject("localVersion",DeviceEnv.getSystemVersion());
		mav.addObject("deviceList",list);
		mav.setViewName("websocket");
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createDevice", method = RequestMethod.POST)
	@ResponseBody
	public String createDevice(String localIp, int localPort) {
		ChannelFuture channelFuture = null;
		try {
			channelFuture = new SimulatorChannel().connect(localIp, localPort,Constant.GATEWAY_IP,Constant.CHARGE_GATEWAY_PORT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Object> list = (List<Object>) LocalStore.getInstance().getMap().get(DeviceType.SMART_METER.getName());
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(channelFuture);
		String resultMsg = "创建了一个 " + DeviceType.SMART_METER.getName() + " 设备!";
		log.info(resultMsg);
		return JSON.toJSONString(resultMsg);
	}
	
	@RequestMapping(value = "/requestQrCode", method = RequestMethod.POST)
	@ResponseBody
	public String requestQrCode(String localIp, int localPort) {
		String result = deviceService.requestQrCode(localIp,localPort);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/deviceRegister", method = RequestMethod.POST)
	@ResponseBody
	public String deviceRegister(String gatewayIp,Integer gatewayPort,String deviceId) {
		String result = deviceService.deviceRegister(gatewayIp,gatewayPort,deviceId);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public String sendMessage(String gatewayIp,Integer gatewayPort,String deviceId,String replyFlag,String jsonData) {
		String result = deviceService.sendMessage(gatewayIp,gatewayPort,deviceId,replyFlag,jsonData);
		return JSON.toJSONString(result);
	}
	
}
