package com.eg.egsc.scp.simulator.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.factory.SimpleMessageFactory;

import io.netty.channel.ChannelHandlerContext;

public class HeartbeatTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(HeartbeatTask.class);

	private final ChannelHandlerContext ctx;
	
	private final String deviceId;

	public HeartbeatTask(final ChannelHandlerContext ctx,final String deviceId) {
		this.ctx = ctx;
		this.deviceId = deviceId;
	}
	
	@Override
	public void run() {
		ProtocolBody protocolBody = SimpleMessageFactory.createMessage(deviceId, EventTypeEnum.COM_HEARTBEAT);
		log.info("模拟器发送【心跳】消息：{}",protocolBody);
		ctx.writeAndFlush(protocolBody);
	}

}
