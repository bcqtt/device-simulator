package com.eg.egsc.scp.simulator.handler.meter;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.task.UploadMeterDataTask;
import com.eg.egsc.scp.simulator.dto.meter.MeterProtocolBody;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@Component
//@Sharable
public class Meter645EventHandler extends ChannelHandlerAdapter {

	private static final Log log = LogFactory.getLog(Meter645EventHandler.class);

	private volatile ScheduledFuture<?> scheduledFuture;
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("TCP链接创建成功，发出心跳包...");
		scheduledFuture = ctx.executor().scheduleAtFixedRate(new Meter645EventHandler.HeartbeatTask(ctx),0, 15,TimeUnit.SECONDS);
	}
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MeterProtocolBody body = (MeterProtocolBody)msg;
		byte ctrlcode = body.getCtrlCode();
		if(ctrlcode==0x13) {  //7.4	读通信地址0x13
			// 握手成功,收到返回的握手应答消息 ，启动定时循环任务主动发送心跳消息
			if(!LocalStore.getInstance().getMap().containsKey("UPLOAD_METER_DATA")) {
				scheduledFuture = ctx.executor().scheduleAtFixedRate(new UploadMeterDataTask(ctx),0, 5,TimeUnit.SECONDS);
				LocalStore.getInstance().getMap().put("UPLOAD_METER_DATA", scheduledFuture);
			}
			log.info("响应网关【读通信地址】的指令：" + msg);
			MeterProtocolBody protocol = new MeterProtocolBody();
			protocol.setDataStr(Constant.CONNECT_ADDR);
			protocol.setHeartbeatFlag(true);
			ctx.writeAndFlush(protocol);
		}else if(ctrlcode==0x91) {  //C=91H 网关正常应答，无后续数据帧
			
		}
		
		ctx.fireChannelRead(msg); //传给下一个Handler
	}

	private class HeartbeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		public HeartbeatTask(final ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		public void run() {
			String deviceId = LocalStore.getInstance().getDeviceIdOfMeter(ctx.channel().localAddress().toString());
			String heartbeatMsg = "H" + deviceId.substring(8);
			
			log.info("发送心跳包:" + heartbeatMsg);
			MeterProtocolBody protocol = new MeterProtocolBody();
			protocol.setDataStr(heartbeatMsg);
			protocol.setHeartbeatFlag(true);
//			try {
//				Thread.sleep(80000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			ctx.writeAndFlush(protocol);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
			scheduledFuture = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		log.info("客户端关闭 ..." + ctx.channel().localAddress()); 
		super.close(ctx, promise);
	}
	
	
}
