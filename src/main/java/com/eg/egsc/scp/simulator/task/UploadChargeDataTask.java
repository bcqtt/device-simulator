package com.eg.egsc.scp.simulator.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.factory.SimpleMessageFactory;

import io.netty.channel.ChannelHandlerContext;

public class UploadChargeDataTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(UploadChargeDataTask.class);
	
	private final ChannelHandlerContext ctx;
	
	private final String deviceId;

	public UploadChargeDataTask(final ChannelHandlerContext ctx,final String deviceId) {
		this.ctx = ctx;
		this.deviceId = deviceId;
	}

	/*public void run() {
		int count = 0;
		while(true) {
			try {
				//短时间内批量发送消息
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(count == 100000) {
				break;
			}
			log.info("模拟器{}发送消息的序号：{}",deviceId, count);
			ProtocolBody protocolBody = SimpleMessageFactory.createMessage(deviceId, EventTypeEnum.COM_CHARGE_UPLOAD_EVENT);
			log.info("模拟器{}发送【充电数据上报】消息：{}",deviceId,protocolBody);
			ctx.writeAndFlush(protocolBody);
			count++;
		}
	}*/

	public void run() {
		ProtocolBody protocolBody = SimpleMessageFactory.createMessage(deviceId, EventTypeEnum.CHARGE_UPLOAD_EVENT);
		log.info("模拟器{}发送【充电数据上报】消息：{}",deviceId,protocolBody);
		ctx.writeAndFlush(protocolBody);
	}

}
