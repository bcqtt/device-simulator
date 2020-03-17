package com.eg.egsc.scp.simulator.handler;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.dto.GatewayDeviceDataDto;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.factory.SimpleMessageFactory;
import com.eg.egsc.scp.simulator.task.HeartbeatTask;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 握手安全认证
 * @author 122879520
 *
 */
@Component
@Sharable
public class DeviceBatchRegisterHandler extends ChannelHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(DeviceBatchRegisterHandler.class);
	
	@Value(value = "${gw.data.encrypt.enabled}")
	private Boolean dataEncrypeEnabled; // 是否使用加密通信
	
	private volatile ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String localAddr = ctx.channel().localAddress().toString();
		log.info("TCP链接创建成功，马上发起设备注册...{}",dataEncrypeEnabled);
		
		int port = Integer.parseInt(localAddr.substring(localAddr.indexOf(":")+1));
		Map<String,Integer> map = LocalStore.getInstance().getDeviceMap();
		for(Map.Entry<String,Integer> entry : map.entrySet()) {
			if(entry.getValue() == port) {
				ProtocolBody protocolBody = SimpleMessageFactory.createMessage(entry.getKey(), EventTypeEnum.COM_DEV_REGISTER);
				log.info("模拟器发送【注册】消息：{}",protocolBody);
				ctx.writeAndFlush(protocolBody);

				//解密信息
//				byte[] dataBytes = protocolBody.getDataBytes();
//				byte[] decryptByte = RSAUtils.decryptByPrivateKey2(dataBytes, Keys.SERVER_PRIVATE_KEY);
//				log.info(new String(decryptByte,"UTF-8"));
				log.info("已模拟设备数：" + LocalStore.getInstance().getDeviceIdList().size());

				Thread.sleep(500);
			}
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//如果收到设备注册成功的消息，这进行事件透传
		ProtocolBody protocolBody = (ProtocolBody)msg;
		
		String command = protocolBody.getCommand();
		String deviceId = protocolBody.getProtocolHeader().getDestId();
		GatewayDeviceDataDto dataObj = JSON.parseObject(protocolBody.getData(), GatewayDeviceDataDto.class);
		if(command.equals(EventTypeEnum.COM_DEV_REGISTER.getCommand())) {
			if(dataObj.getResult()==0){
				log.info("收到网关响应设备【注册成功】消息：" + protocolBody.toString());
			}else{
				log.info("收到网关响应设备【注册失败】消息：" + protocolBody.toString());
				return ;
			}

			executor.scheduleAtFixedRate(new HeartbeatTask(ctx, deviceId), 0, 15,TimeUnit.SECONDS);
			
			//保存设备长连接
			LocalStore.getInstance().addCtx(protocolBody.getProtocolHeader().getDestId(), ctx);
			
		}else if(command.equals(EventTypeEnum.COM_QUERY_DIR.getCommand())) {
			log.info("收到网关【查询子目录】的消息：" + protocolBody.toString());
		}else if(command.equals(EventTypeEnum.COM_SETTING_PARAMETERS.getCommand())) {
			log.info("收到网关【设置参数】的消息：" + protocolBody.toString());
		}else if(command.equals(EventTypeEnum.COM_HEARTBEAT.getCommand())) {
			if(dataObj.getResult()==2) {
				log.info("收到网关【心跳检测】的消息：" + protocolBody.toString());
				ProtocolBody heartbeatMsgBody = SimpleMessageFactory.createMessage(deviceId, EventTypeEnum.COM_HEARTBEAT);
				log.info("模拟器发送【心跳】消息，响应网关的心跳检测：{}",heartbeatMsgBody);
				ctx.writeAndFlush(heartbeatMsgBody);
			}else {
				log.info("收到网关【心跳响应】的消息：" + protocolBody.toString());
				//deviceSheduled = ctx.executor().scheduleAtFixedRate(new UploadChargeDataTask(ctx,deviceId),0, 5,TimeUnit.SECONDS);
			}
		}
		ctx.fireChannelRead(protocolBody);  //收到注册成功消息之后，将消息体透传
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
	
}
