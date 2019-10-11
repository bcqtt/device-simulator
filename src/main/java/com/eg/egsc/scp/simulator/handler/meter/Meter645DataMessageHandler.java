package com.eg.egsc.scp.simulator.handler.meter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.component.UploadMeterDataTask;
import com.eg.egsc.scp.simulator.dto.GatewayDeviceDataDto;
import com.eg.egsc.scp.simulator.dto.PowerControlDto;
import com.eg.egsc.scp.simulator.dto.StartChargeRequestDto;
import com.eg.egsc.scp.simulator.dto.StopChargeRequestDto;
import com.eg.egsc.scp.simulator.handler.DeviceRegisterHandler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

//@Component
public class Meter645DataMessageHandler extends ChannelHandlerAdapter  {
	
	private static final Log log = LogFactory.getLog(DeviceRegisterHandler.class);
	
	private volatile ScheduledFuture<?> uploadRealtimeDataScheduled;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//如果收到设备注册成功的消息，这进行事件透传
		GatewayDeviceDataDto dataDto = (GatewayDeviceDataDto)msg;
		String jsonDataStr = JSON.toJSONString(dataDto.getData());
		String command = dataDto.getCommand();
		EventTypeEnum eventTypeEnum = EventTypeEnum.getEnumByCommand(command);
		if(eventTypeEnum==null) {
			log.info("eventTypeEnum=null时，jsonData为：" + jsonDataStr);
		}
		
		Object obj = LocalStore.getInstance().getMap().get(ctx.channel().localAddress().toString());
		if(obj != null) {
			RequestMessageManager.writeAndFlush(EventTypeEnum.CHARGE_UPLOAD_VOLTAGE_CURRENT.getCommand(), ctx, "0", null);
		}
		
		ctx.fireChannelRead(msg); 
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
