package com.eg.egsc.scp.simulator.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;

import io.netty.channel.ChannelFuture;

@Controller
public class MeterSimulatorController {

	private static final Log log = LogFactory.getLog(MeterSimulatorController.class);
	
//	@Autowired
//	private DeviceBase deviceBase;
//	
//	@Autowired
//	private ChargeDevice chargeDevice;
	
	@Autowired
	private IDeviceService deviceService;
	
	
	
	

}
