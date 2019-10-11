package com.eg.egsc.scp.simulator.component;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eg.egsc.scp.simulator.channel.MeterChannel;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;
import com.eg.egsc.scp.simulator.web.service.impl.DeviceServiceImpl;

import io.netty.channel.ChannelFuture;

@Component
@Order(1)
public class Meter645Initialize implements ApplicationRunner{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("初始化645电表...");
		VirtualDevice device = new VirtualDevice();
		device.setDeviceIp(Constant.SIMULATOR_IP);
		device.setDevicePort(Constant.SIMULATO_METER_PORT);
		device.setDeviceMac(Constant.SIMULATOR_MAC);
		device.setGatewayPort(Constant.CHARGE_METER_PORT);
		device.setEnvironment(Constant.GATEWAY_IP);
		
		//批量注册
		Map<String,Integer> map = LocalStore.getInstance().getMeterMap();
		for(Map.Entry<String,Integer> entry : map.entrySet()) {
			ChannelFuture channelFuture = new MeterChannel().connect(device.getDeviceIp(), entry.getValue(), 
					device.getEnvironment(),device.getGatewayPort());
			if(channelFuture.channel().isActive()) {
				LocalStore.getInstance().addDeviceId(entry.getKey());
			}
		}
		
		//单个设备调试
//		ChannelFuture channelFuture = new MeterChannel().connect(device.getDeviceIp(), device.getDevicePort(), 
//				device.getEnvironment(),device.getGatewayPort());
//		if(channelFuture.channel().isActive()) {
//			String mapKey = device.getDeviceIp() + ":" + device.getDevicePort();
//			LocalStore.getInstance().addChannel(mapKey, channelFuture);
//		}
		
	}

}
