package com.eg.egsc.scp.simulator.web.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eg.egsc.scp.simulator.channel.SimulatorChannel;
import com.eg.egsc.scp.simulator.channel.MeterChannel;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.DeviceType;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.util.StringUtils;
import com.eg.egsc.scp.simulator.web.mapper.VirtualDeviceMapper;
import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;
import com.eg.egsc.scp.simulator.web.service.IDeviceService;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

@Service
public class DeviceServiceImpl implements IDeviceService {
	
	private static final Log log = LogFactory.getLog(DeviceServiceImpl.class);
	
	@Autowired
	private VirtualDeviceMapper virtualDeviceMapper;

	@Override
	public int save(VirtualDevice device) {
		int n = 0;
		if(device.getId()==null) {
			device.setId(StringUtils.getUUID());
			device.setCreateTime(new Date());
			device.setDeviceStatus(0);
			
			if(virtualDeviceMapper.queryCount()==0) {
				device.setDevicePort(30000);
			}else {
				device.setDevicePort(virtualDeviceMapper.queryNewPort());
			}
			log.info("新增一个设备并保持入库：" + device.toString());
			virtualDeviceMapper.insert(device);
		}else {
			virtualDeviceMapper.update(device);
			log.info("更新设备信息，设备id：" + device.getDeviceIp());
		}
		
		return n;
	}

	@Override
	public List<VirtualDevice> queryAllDevice() {
		return virtualDeviceMapper.queryAll();
	}

	@Override
	public VirtualDevice queryDeviceById(String deviceId) {
		return virtualDeviceMapper.queryDeviceById(deviceId);
	}

	@Override
	public int createDevicePort() {
		return virtualDeviceMapper.queryNewPort();
	}

	@Override
	public String goOnline(String deviceId) {
		return null;
	}
	
	@Override
	public String doOffline(String deviceId) {
		
		return null;
	}

	@Override
	public String meterOnline(String deviceId) {
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public String requestQrCode(String localIp, int localPort) {
		ChannelFuture channelFuture = null;
		try {
			channelFuture = new SimulatorChannel().connect(localIp, localPort,Constant.GATEWAY_IP,Constant.GATEWAY_PORT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String resultMsg = "创建了一个 " + DeviceType.SMART_CHARGE.getName() + " 设备!";
		log.info(resultMsg);
		
		LocalStore.getInstance();
		ChannelHandlerContext ctx = LocalStore.getInstance().getCtxMap().get(channelFuture.channel().localAddress()); 
		RequestMessageManager.writeAndFlush(EventTypeEnum.COM_REQUEST_QR_CODE_AGAIN.getCommand(), ctx, "0", null);
		
		return "success";
	}

	@SuppressWarnings("static-access")
	@Override
	public String deviceRegister(String gatewayIp, Integer gatewayPort, String deviceId) {
		ChannelHandlerContext ctx = LocalStore.getInstance().getInstance().getCtxMap().get(Constant.LOCAL_IP + "->" + gatewayIp);
		if(ctx==null) {
			ChannelFuture channelFuture = null;
			int port = LocalStore.getInstance().getInstance().getLocalPort();
			try {
				channelFuture = new SimulatorChannel().connect(Constant.LOCAL_IP, port,gatewayIp,gatewayPort);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LocalStore.getInstance().getInstance().setLocalPort(port++);
			LocalStore.getInstance().getInstance().getDeviceIdList().add(deviceId);
			ctx = LocalStore.getInstance().getInstance().getCtxMap().get(Constant.LOCAL_IP + "->" + gatewayIp);
		}
		RequestMessageManager.writeAndFlushWithDeviceId(EventTypeEnum.COM_DEV_REGISTER.getCommand(), ctx, "0", deviceId);
		return "success";
	}
	
	@Override
	public String sendMessage(String gatewayIp, Integer gatewayPort, String deviceId, String replyFlag, String jsonData) {
		ChannelHandlerContext ctx = LocalStore.getInstance().getInstance().getCtxMap().get(Constant.LOCAL_IP+"->" + gatewayIp);
		
		RequestMessageManager.writeAndFlushMessage(jsonData, ctx, deviceId,replyFlag);
		if(ctx==null) {
			return "虚拟设备尚未注册";
		}
		
		return "success";
	}


}
