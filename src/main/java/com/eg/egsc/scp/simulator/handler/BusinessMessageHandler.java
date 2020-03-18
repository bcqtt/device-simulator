package com.eg.egsc.scp.simulator.handler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.eg.egsc.scp.simulator.component.CommondUtil;
import com.eg.egsc.scp.simulator.dto.*;
import com.eg.egsc.scp.simulator.task.UploadChargeDataTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.task.UploadMeterDataTask;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import static com.eg.egsc.scp.simulator.common.EventTypeEnum.*;

@Component
@Sharable
public class BusinessMessageHandler extends ChannelHandlerAdapter  {
	
	private static final Log log = LogFactory.getLog(DeviceRegisterHandler.class);
	
	private volatile ScheduledFuture<?> uploadRealtimeDataScheduled;
	private volatile ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ProtocolBody body = (ProtocolBody)msg;
		String jsonDataStr = body.getData();
		String command = body.getCommand();
		String deviceCode = body.getProtocolHeader().getDestId();
		EventTypeEnum eventTypeEnum = EventTypeEnum.getEnumByCommand(command);
		JSONObject jsonObject = JSONObject.parseObject(jsonDataStr);
		if(eventTypeEnum==null) {
			log.info("eventTypeEnum=null时，jsonData为：" + jsonDataStr);
		}
		switch (eventTypeEnum) {
		case CHARGE_COM_START_CHARGE: //开始充电
			JSONObject data = (JSONObject) jsonObject.getJSONArray("Data").get(0);
			StartChargeRequestDto startRequestDto = JSONObject.toJavaObject(data, StartChargeRequestDto.class);
			int targetPower = startRequestDto.getTargetPower();
			if(targetPower == 0) {//开始充电且目标功率为0时表示预约充电
				log.info("开始充电，目标功率为0，进入预约充电...");
			}else if(targetPower > 1 && targetPower<= Constant.RATEDPOWER) {//给插座供电
				log.info("给插座供电");
			}else if(targetPower > Constant.RATEDPOWER) {
				log.error("充电插座功率非法。。。");
				// TODO 上报非法
			}else if(targetPower == -1) {
				log.info("充电插座使用最小功率，使用6A充电电流...");
				LocalStore.getInstance().getMap().put("MIN_POWER", Constant.MIN_POWER);
				LocalStore.getInstance().getMap().put("CURRENT_6A", Constant.CURRENT_6A);
			}else if(targetPower == -2) {
				log.info("充电插座使用最大功率...");
				LocalStore.getInstance().getMap().put("MAX_POWER", Constant.MAX_POWER);
			}
			//响应启动充电请求、上报启动充电结果
			CommondUtil.responseStartCharge(deviceCode,ctx, startRequestDto.getOrderNumber());

			//uploadRealtimeDataScheduled = ctx.executor().scheduleAtFixedRate( new UploadMeterDataTask(ctx),0, 10,TimeUnit.SECONDS);  //5s发一次，单位毫秒
			ScheduledFuture chargeSchedule = ctx.executor().scheduleAtFixedRate(new UploadChargeDataTask(ctx, deviceCode), 0, 10, TimeUnit.SECONDS);
			LocalStore.getInstance().getScheduledMap().put(deviceCode, chargeSchedule);
			break;
		case CHARGE_UPLOAD_START_RESULT:
			log.info("收到网关应答[启动充电结果上报]");
			break;
		case CHARGE_UPLOAD_STOP_RESULT:
			log.info("收到网关应答[停止充电结果上报]");
			break;
		case CHARGE_COM_STOP_CHARGE: //停止充电
			JSONObject stopData = (JSONObject) jsonObject.getJSONArray("Data").get(0);
			StopChargeRequestDto stopRequestDto =JSONObject.toJavaObject(stopData, StopChargeRequestDto.class);
			int unlock = stopRequestDto.getUnlock();
			if(unlock==0) {
				log.info("不解锁电子锁...");
			}else {
				log.info("解锁电子锁...");
			}
			//响应停止充电请求、上报停止充电结果
			CommondUtil.responseStopCharge(deviceCode,ctx);
			//uploadRealtimeDataScheduled.cancel(true);
			break;
		case CHARGE_COM_DEV_STATUS:  //查询设备状态
			CommondUtil.responseDevStatus(deviceCode, CHARGE_COM_DEV_STATUS,ctx);
			break;
		case CHARGE_COM_PAY_RULE:  //计费规则
			CommondUtil.responsePayRule(deviceCode,ctx);
			break;
		case CHARGE_COM_CLOUD_STATUS_SYNC:  //计费规则
			log.info("收到网关充电状态同步[CHARGE_COM_CLOUD_STATUS_SYNC]");
			break;
		case CHARGE_COM_POWER_CONTROL: //功率控制
			JSONObject ctrlData = (JSONObject) jsonObject.getJSONArray("Data").get(0);
			PowerControlDto powerControlDto = JSONObject.toJavaObject(ctrlData, PowerControlDto.class);
			int targetPow = powerControlDto.getTargetPower();
			if(targetPow>0 && targetPow<=Constant.RATEDPOWER) {
				log.info("以目标功率开始充电...");
			}else if(targetPow>Constant.RATEDPOWER){
				log.info("大于额定功率，非法,充电失败...");
				//上报失败事件
			}
			RequestMessageManager.writeAndFlush(command, ctx, "1", powerControlDto.getOrderNumber());
			break;
		case CHARGE_COM_SET_LOCK:  //电子锁开关
			log.info("电子锁设置...");
			RequestMessageManager.writeAndFlush(command, ctx, "1", null);
			break;
		case CHARGE_COM_SET_QR_CODE:  //设置序列号
			log.info("设置序列号...");
			//将序列号保存
			log.info("网关下发的设置序列号(命令字:COM_SET_QR_CODE)参数为：" + jsonObject);
			break;
		case CHARGE_COM_REQ_REAL_DATA:  //请求实时数据
			CommondUtil.responseRealData(deviceCode, CHARGE_COM_REQ_REAL_DATA,ctx);
			break;
		default:
			break;
		}
		
		ctx.fireChannelRead(msg); 
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
