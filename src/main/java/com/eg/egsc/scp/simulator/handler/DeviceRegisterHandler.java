package com.eg.egsc.scp.simulator.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.component.DeviceEnv;
import com.eg.egsc.scp.simulator.dto.DeviceMessageDataDto;
import com.eg.egsc.scp.simulator.dto.DeviceRegisterDto;
import com.eg.egsc.scp.simulator.dto.GatewayDeviceDataDto;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.factory.NormalMessage;
import com.eg.egsc.scp.simulator.util.CRCUtil;
import com.google.common.collect.Lists;

/**
 * 握手安全认证
 * @author 122879520
 *
 */
//@Component
public class DeviceRegisterHandler extends ChannelHandlerAdapter {

	private static final Log log = LogFactory.getLog(DeviceRegisterHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String localAddr = ctx.channel().remoteAddress().toString();
		String remoteAddr = ctx.channel().remoteAddress().toString();
		String key = localAddr +"->" + remoteAddr;
		LocalStore.getInstance().addCtx(key, ctx);
		log.info("TCP链接创建成功，马上发起设备注册...");
		RequestMessageManager.writeAndFlush(EventTypeEnum.COM_DEV_REGISTER.getCommand(), ctx, "0", Constant.SIMULATOR_DEVICE_ID);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//如果收到设备注册成功的消息，这进行事件透传
		ProtocolBody protocolBody = (ProtocolBody)msg;
		String msgFromGw = new String(protocolBody.getDataBytes(),"UTF-8");
		GatewayDeviceDataDto dataObj = JSON.parseObject(msgFromGw, GatewayDeviceDataDto.class);  //消息体
		
		String command = dataObj.getCommand();
		Integer result = dataObj.getResult();  
		if(command.equals(EventTypeEnum.COM_DEV_REGISTER.getCommand()) && result == 0) {
			log.info("收到设备注册反馈消息：" + msgFromGw);
		}
		ctx.fireChannelRead(dataObj);  //收到注册成功消息之后，将消息体透传
		
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
}
