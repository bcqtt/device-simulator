package com.eg.egsc.scp.simulator.component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eg.egsc.scp.simulator.channel.MeterChannel;
import com.eg.egsc.scp.simulator.channel.SimulatorChannel;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.task.UploadChargeDataTask;
import com.eg.egsc.scp.simulator.util.DeviceInitUtil;
import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;
import com.eg.egsc.scp.simulator.web.service.impl.DeviceServiceImpl;
import com.google.common.collect.Lists;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

@Component
@Order(2)
public class SimulatorInitialize implements ApplicationRunner{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);
	
	@Autowired
	private SimulatorChannel simulatorChannel;
	
	private volatile ScheduledFuture<?> deviceSheduled;
	private volatile ScheduledExecutorService executor = Executors.newScheduledThreadPool(16);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("开始初始化模拟设备...");
		VirtualDevice device = new VirtualDevice();
		device.setDeviceId(Constant.SIMULATOR_DEVICE_ID);
		device.setDeviceIp(DeviceEnv.getLocalIp());
		device.setDevicePort(Constant.SIMULATOR_PORT);
		device.setDeviceMac(ComputerInfo.getMacAddress());
		device.setGatewayPort(Constant.CHARGE_GATEWAY_PORT);
		device.setEnvironment(Constant.GATEWAY_IP);
		
		//批量注册
		Map<String,Integer> map = LocalStore.getInstance().getDeviceMap();
		for(Map.Entry<String,Integer> entry : map.entrySet()) {
			ChannelFuture channelFuture = simulatorChannel.connect(device.getDeviceIp(), entry.getValue(), 
					device.getEnvironment(),device.getGatewayPort());
			if(channelFuture.channel().isActive()) {
				LocalStore.getInstance().addDeviceId(entry.getKey());
			}
			
			Thread.sleep(1000);
		}
		log.info("============[全部设备注册完毕]=====================");
			
		//发送电量的消息
		/*Map<String,ChannelHandlerContext> ctxMap = LocalStore.getInstance().getCtxMap();
		for(Map.Entry<String,ChannelHandlerContext> entry : ctxMap.entrySet()) {
			ChannelHandlerContext ctx = entry.getValue();
			String deviceId = entry.getKey();
			//deviceSheduled = ctx.executor().scheduleAtFixedRate(new UploadChargeDataTask(ctx,deviceId),0, 5, TimeUnit.SECONDS);
			executor.scheduleAtFixedRate(new UploadChargeDataTask(ctx,deviceId),0, 5, TimeUnit.SECONDS);
			Thread.sleep(1000);
		}*/
		
	}

}
