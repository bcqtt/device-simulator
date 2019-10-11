package com.eg.egsc.scp.simulator.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.eg.egsc.scp.simulator.common.MeterMessageManager;
import io.netty.channel.ChannelHandlerContext;

public class UploadMeterDataTask implements Runnable {
	
	private static final Log log = LogFactory.getLog(UploadMeterDataTask.class);
	
	private ChannelHandlerContext ctx;

	public UploadMeterDataTask(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		log.info("向网关发送数据的定时任务启动...");
		MeterMessageManager.sendInstantaneousTotalActivePower(ctx);   //发送瞬时总有功功率
//		MeterMessageManager.sendInstantaneouseActivePower("A", ctx);  //发送功率
		try {
			MeterMessageManager.sendCurrent("A", ctx);  //发送电流
			Thread.sleep(5);
			MeterMessageManager.sendCurrent("B", ctx);  //发送电流
			Thread.sleep(5);
			MeterMessageManager.sendCurrent("C", ctx);  //发送电流
//			MeterMessageManager.sendVoltage("A", ctx);  //发送电压
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
