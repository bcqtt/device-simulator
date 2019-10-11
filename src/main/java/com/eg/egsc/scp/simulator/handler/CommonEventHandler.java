package com.eg.egsc.scp.simulator.handler;

import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.dto.ProtocolBody;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

//@Component
//@Sharable
public class CommonEventHandler extends ChannelHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(CommonEventHandler.class);

	private volatile ScheduledFuture<?> heartBeatScheduled;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ProtocolBody body = (ProtocolBody)msg;
		String command = body.getCommand();
		
//		int result = null;
//		// 握手成功,收到返回的握手应答消息 ，启动定时循环任务主动发送心跳消息
//		if(result!=null && result==0) {
//			//处理网关应答的消息
//			if(command.equals(EventTypeEnum.COM_DEV_REGISTER.getCommand()) ) {  //设备注册
////				heartBeatScheduled = ctx.executor().scheduleAtFixedRate(
////						new CommonEventHandler.HeartbeatTask(ctx),0, 60,TimeUnit.SECONDS);
////				DeviceDataManage.getInstance().getMap().put(ctx.channel().localAddress().toString(), heartBeatScheduled);
//			}else if (command.equals(EventTypeEnum.COM_HEARTBEAT.getCommand()) ) {  //心跳
//				log.info("收到网关的心跳应答消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlushWithDeviceId(EventType.COM_HEARTBEAT.getCommand(), ctx, "0", Constant.SIMULATOR_DEVICE_ID);
//			}else if (command.equals(EventTypeEnum.COM_READ_TIME.getCommand()) ){  //读取时间
//				log.info("收到网关的请求[读取设备的时间]消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlush(command,ctx, "1",null); 
//			}else if(command.equals(EventTypeEnum.COM_SET_TIME.getCommand()) ) {  //设置时间
//				log.info("收到网关的请求[设置设备的时间]消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlush(command,ctx, "1",null); 
//			}else if(command.equals(EventTypeEnum.COM_SETTING_PARAMETERS.getCommand()) ) {  //设置设备参数
//				log.info("收到网关的请求[设备参数]消息：" + msg.toString());
//				log.info("马上调整设备参数...");
//			}
//		}else {
//			//处理网关请求的消息
//			if(command.equals(EventTypeEnum.COM_SETTING_PARAMETERS.getCommand()) ) {
//				log.error("收到网关的请求[设备参数下发]消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlush(command,ctx, "1",null); 
//				//RequestMessageManager.writeAndFlushEncrypt(command,ctx, "1",null); 
//			}else if(command.equals(EventTypeEnum.COM_DEV_STATUS.getCommand()) ) {
//				log.error("收到网关的请求[查询设备状态]消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlush(command,ctx, "1",null); 
//			}else if(command.equals(EventTypeEnum.COM_HEARTBEAT.getCommand()) ) {
//				log.error("收到网关的请求[心跳检测]消息：" + msg.toString());
//				//RequestMessageManager.writeAndFlushEncrypt(command,ctx, "1",null); 
//			}
//			
//		}
		ctx.fireChannelRead(msg); //传给下一个Handler
			
	}

	private class HeartbeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartbeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@SuppressWarnings("static-access")
		public void run() {
//			ProtocolHeader protocolHeader = new ProtocolHeader();
//			protocolHeader.setVersion("HDXM");
//		    protocolHeader.setSrcID("100820258CEC4B419DB3"); 
//		    protocolHeader.setDestId("00000000000000000000");
//		    protocolHeader.setRequestFlag("0");  //0 ：请求    1：应答
//		    protocolHeader.setPackageNo("1");
//		    
//		    DeviceMessageDataDto msgDto = new DeviceMessageDataDto();
//		    msgDto.setCommand(EventType.COM_HEARTBEAT.getCommand());
//		    
//		    String msgContent = JSON.toJSONString(msgDto);
//		    
//		    int crc16New = CRCUtil.crc16CCITTFalse(msgContent.getBytes(), msgContent.getBytes().length);  //获得crc16
//		    protocolHeader.setDataLength(msgContent.getBytes().length);
//		    protocolHeader.setCrc16(String.valueOf(crc16New));
//		    protocolHeader.setHold("00");
//			
//		    ProtocolBody protocolBody = new ProtocolBody(protocolHeader, msgContent);
//		    protocolBody.setCommand(EventType.COM_HEARTBEAT.getCommand());
//			ctx.writeAndFlush(protocolBody);   //连接成功时发起设备注册
//			log.info("客户端(100820258CEC4B419DB3)向服务端发送心跳消息 ...");  //传对象
			
//			List<String> deviceIds = LocalStore.getInstance().getInstance().getDeviceIdList();
//			deviceIds.forEach(deviceId -> {
//				int counter = LocalStore.getInstance().getCounter(deviceId);
//				if(counter%5==0) {
//					try {
//						Thread.sleep(2*60*1000);
//						log.info("counter={},设备{}暂停发送消息",counter,deviceId);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				RequestMessageManager.writeAndFlushWithDeviceId(EventTypeEnum.COM_HEARTBEAT.getCommand(), ctx, "0", deviceId);
//			});
			
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (heartBeatScheduled != null) {
			heartBeatScheduled.cancel(true);
			heartBeatScheduled = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		log.info("客户端关闭 ..." + ctx.channel().localAddress()); 
		super.close(ctx, promise);
	}
	
	
}
