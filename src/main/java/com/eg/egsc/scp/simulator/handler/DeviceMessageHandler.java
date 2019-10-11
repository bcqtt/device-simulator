package com.eg.egsc.scp.simulator.handler;

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

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

//@Component
public class DeviceMessageHandler extends ChannelHandlerAdapter  {
	
	private static final Log log = LogFactory.getLog(DeviceRegisterHandler.class);
	
	private volatile ScheduledFuture<?> uploadRealtimeDataScheduled;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//如果收到设备注册成功的消息，这进行事件透传
		GatewayDeviceDataDto dataDto = (GatewayDeviceDataDto)msg;
		String jsonDataStr = JSON.toJSONString(dataDto.getData());
		String command = dataDto.getCommand();
		EventTypeEnum eventTypeEnum = EventTypeEnum.getEnumByCommand(command);
		JSONArray jsonArray = JSONArray.parseArray(jsonDataStr);
		if(eventTypeEnum==null) {
			log.info("eventTypeEnum=null时，jsonData为：" + jsonDataStr);
		}
		switch (eventTypeEnum) {
		case COM_START_CHARGE: //开始充电
			StartChargeRequestDto startRequestDto = JSONObject.toJavaObject(jsonArray.getJSONObject(0), StartChargeRequestDto.class);
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
			//响应启动充电请求
			RequestMessageManager.writeAndFlush(command, ctx, "1",startRequestDto.getOrderNumber());
			log.info("上报启动充电结果...");
			RequestMessageManager.writeAndFlush(EventTypeEnum.COM_UPLOAD_START_RESULT.getCommand(), ctx, "0", startRequestDto.getOrderNumber()); 
			//订单号保存到缓存
			LocalStore.getInstance().getMap().remove(EventTypeEnum.COM_UPLOAD_START_RESULT.getCommand() + ":ORDERNUMBER", startRequestDto.getOrderNumber());
			uploadRealtimeDataScheduled = ctx.executor().scheduleAtFixedRate( new UploadMeterDataTask(ctx),0, 10,TimeUnit.SECONDS);  //5s发一次，单位毫秒
			break;
		case COM_STOP_CHARGE: //停止充电
			StopChargeRequestDto stopRequestDto = JSONObject.toJavaObject(jsonArray.getJSONObject(0), StopChargeRequestDto.class);
			int unlock = stopRequestDto.getUnlock();
			if(unlock==0) {
				log.info("不解锁电子锁...");
			}else {
				log.info("解锁电子锁...");
			}
			//响应停止充电请求
			RequestMessageManager.writeAndFlush(command, ctx, "1",stopRequestDto.getOrderNumber());
			log.info("上报停止充电结果...");
			RequestMessageManager.writeAndFlush(EventTypeEnum.COM_UPLOAD_STOP_RESULT.getCommand(), ctx, "0", stopRequestDto.getOrderNumber());
			uploadRealtimeDataScheduled.cancel(true);
			
			//删除缓存中的订单号
			LocalStore.getInstance().getMap().remove(EventTypeEnum.COM_UPLOAD_START_RESULT.getCommand() + ":ORDERNUMBER", stopRequestDto.getOrderNumber());
			break;
		case COM_POWER_CONTROL: //功率控制
			PowerControlDto powerControlDto = JSONObject.toJavaObject(jsonArray.getJSONObject(0), PowerControlDto.class);
			int targetPow = powerControlDto.getTargetPower();
			if(targetPow>0 && targetPow<=Constant.RATEDPOWER) {
				log.info("以目标功率开始充电...");
			}else if(targetPow>Constant.RATEDPOWER){
				log.info("大于额定功率，非法,充电失败...");
				//上报失败事件
			}
			RequestMessageManager.writeAndFlush(command, ctx, "1", powerControlDto.getOrderNumber());
			break;
		case COM_SET_LOCK:  //电子锁开关
			log.info("电子锁设置...");
			RequestMessageManager.writeAndFlush(command, ctx, "1", null);
			break;
		case COM_SET_QR_CODE:  //设置序列号
			log.info("设置序列号...");
			//将序列号保存
			log.info("网关下发的设置序列号(命令字:COM_SET_QR_CODE)参数为：" + jsonArray);
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