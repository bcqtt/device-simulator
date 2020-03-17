package com.eg.egsc.scp.simulator.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.dto.ChargeOutletUploadEventDto;
import com.eg.egsc.scp.simulator.dto.DeviceMessageDataDto;
import com.eg.egsc.scp.simulator.dto.DeviceRegisterDto;
import com.eg.egsc.scp.simulator.dto.DeviceStatusDto;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.dto.StartChargeResponseDto;
import com.eg.egsc.scp.simulator.dto.StopChargeResponseDto;
import com.eg.egsc.scp.simulator.dto.UploadStartResult;
import com.eg.egsc.scp.simulator.dto.UploadStopResult;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.ByteUtils;
import com.eg.egsc.scp.simulator.util.CRCUtil;
import com.eg.egsc.scp.simulator.util.DateUtils;
import com.eg.egsc.scp.simulator.util.Keys;
import com.eg.egsc.scp.simulator.util.PackageNumSingleton;
import com.eg.egsc.scp.simulator.util.RSAUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.netty.channel.ChannelHandlerContext;

public class RequestMessageManager {
	
	private static final Log log = LogFactory.getLog(RequestMessageManager.class);
	
	static int quantity = 1;

	/**
	 * 
	 * @param command  命令字
	 * @param ctx   ChannelHandlerContext
	 * @param requestFlag 网关对设备   0 ：请求    1：应答
	 * @param obj  预留参数，根据实际情况转换为需要的目标类型,可为null
	 */
	public static void writeAndFlush(String command,ChannelHandlerContext ctx,String requestFlag,Object obj) {
		sendMessage(command,ctx,requestFlag ,obj);
	}
	
	public static void writeAndFlushWithDeviceId(String command,ChannelHandlerContext ctx,String requestFlag,String deviceId) {
		sendMessage(command,ctx,requestFlag ,deviceId);
	}
	
	public static void writeAndFlushChargeData(String command, ChannelHandlerContext ctx, String requestFlag, Object deviceId) {
		sendMessage(command,ctx,requestFlag ,deviceId);
	}
	
	public static void writeAndFlushEncrypt(String command, ChannelHandlerContext ctx, String requestFlag, String obj) throws Exception {
		sendMessageWithEncrypt(command,ctx,requestFlag ,obj);
	}
	
	private static void sendMessage(String command,ChannelHandlerContext ctx,String requestFlag,Object obj) {
		String deviceId = LocalStore.getInstance().getDeviceId(ctx.channel().localAddress().toString());
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setVersion("HDXM");
	    protocolHeader.setSrcID(deviceId); 
	    protocolHeader.setDestId("00000000000000000000");
	    protocolHeader.setRequestFlag(requestFlag);  //0 ：请求    1：应答
	    protocolHeader.setPackageNo("" + PackageNumSingleton.getInstance().incrementPackgeNum());
	    
	    DeviceMessageDataDto msgDto = new DeviceMessageDataDto();
	    msgDto.setCommand(command);
	    msgDto.setData(buildDataList(command, obj));
	    String msgContent = JSON.toJSONString(msgDto);
		log.info("创建消息：" + msgContent);
	    
	    int crc16New = CRCUtil.crc16CCITTFalse(msgContent.getBytes(), msgContent.getBytes().length);  //获得crc16
	    protocolHeader.setDataLength(msgContent.getBytes().length);
	    protocolHeader.setCrc16("" + crc16New);
	    protocolHeader.setHold((short)00);
		
	    ProtocolBody protocolBody = new ProtocolBody(protocolHeader, msgContent);
	    protocolBody.setCommand(command);
	    ctx.writeAndFlush(protocolBody);   //连接成功时发起设备注册
		log.info("客户端(" + deviceId + ")向网关发送(" + command + ")消息 ..." + protocolBody.toString());  //传对象
	}
	
	private static void sendMessageWithEncrypt(String command,ChannelHandlerContext ctx,String requestFlag,Object obj) throws Exception {
		String deviceId = LocalStore.getInstance().getDeviceId(ctx.channel().localAddress().toString());
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setVersion("HDXM");
		protocolHeader.setSrcID(deviceId); 
		protocolHeader.setDestId("00000000000000000000");
		protocolHeader.setRequestFlag(requestFlag);  //0 ：请求    1：应答
		protocolHeader.setHold((short)1280);  //1280:RSA加密，768：AES加密，0：不加密
		protocolHeader.setPackageNo("" + PackageNumSingleton.getInstance().incrementPackgeNum());
		
		DeviceMessageDataDto msgDto = new DeviceMessageDataDto();
		msgDto.setCommand(command);
		msgDto.setData(buildDataList(command, obj));
		String msgContent = JSON.toJSONString(msgDto);
		log.info("创建消息：" + msgContent);
		
		String encryptedMsg = null;
		byte[] aesMsgByte = null ;
		int crc16New = 0;
		int dataLength = 0;
		if("COM_DEV_REGISTER".equals(command)) {
			encryptedMsg = RSAUtils.encryptedDataOnJava(msgContent, Keys.SERVER_PUBLIC_KEY);
			dataLength = encryptedMsg.getBytes().length;
			crc16New = CRCUtil.crc16CCITTFalse(encryptedMsg.getBytes(), dataLength);  //获得crc16
		}else {
			aesMsgByte = AESUtils.encrypt(msgContent, Constant.AESKEY_VALUE);
			dataLength = aesMsgByte.length;
		    crc16New = CRCUtil.crc16CCITTFalse(aesMsgByte, dataLength);  //获得crc1
		}
		
		protocolHeader.setDataLength(dataLength);
		protocolHeader.setCrc16("" + crc16New);
		
		ProtocolBody protocolBody = new ProtocolBody(protocolHeader, encryptedMsg);
		protocolBody.setCommand(command);
		if(encryptedMsg==null) {
			protocolBody.setDataByte(aesMsgByte);
		}
			
		ctx.writeAndFlush(protocolBody);   //连接成功时发起设备注册
		log.info("客户端(" + deviceId + ")向网关发送AES加密(" + command + ")消息 ..." + protocolBody.toString());  //传对象
	}
	
	/**
	 * 设置消息中的Data参数
	 * @param command
	 * @param obj 根据实际情况转换为需要的目标类型
	 * @return
	 */
	private static List<Object> buildDataList(String command,Object obj){
		List<Object> dataList = Lists.newArrayList();
	    Map<String,Object> map = Maps.newHashMap();
	    
	    if(command.equals(EventTypeEnum.COM_DEV_REGISTER.getCommand()) ) {
	    	//构造注册消息
			DeviceRegisterDto registerDto = new DeviceRegisterDto();
			registerDto.setDeviceID((String)obj);
			registerDto.setManufacturer("恒大");
			registerDto.setIp(Constant.SIMULATOR_IP);
			registerDto.setMac("90:f0:52:07:d8:f3");
			registerDto.setMask("255.255.255.0");
			registerDto.setVersion("Windos7");
			registerDto.setAesKey("2222222222222222");
			dataList.add(registerDto);
	    	
			//模拟门禁
//	    	Map<String,Object> data = Maps.newHashMap();
//	    	data.put("Type", 2009);
//	    	data.put("deviceID", "10012009159357159357");
//	    	data.put("manufacturer", "海康");
//	    	data.put("macNO", 102);
//	    	data.put("ip", "192.168.135.128");
//	    	data.put("mac", "00:0c:29:c2:b0:27");
//	    	data.put("mask", "255.255.255.0");
//	    	data.put("deviceDetailType", "AAAAA");
//	    	data.put("name", "人行门禁");
//	    	data.put("version", "V1.0.2_20181108001");
//	    	dataList.add(data);
	    }else if( command.equals(EventTypeEnum.COM_HEARTBEAT.getCommand()) ) {  //心跳
	    	dataList.add(null);
	    }else if(command.equals(EventTypeEnum.COM_READ_TIME.getCommand()) ) {
	    	map.put("time",DateUtils.formatDate2(new Date()));
			dataList.add(map);
	    }else if(command.equals(EventTypeEnum.COM_SET_TIME.getCommand()) ) {
			map.put("time",DateUtils.getHMS(new Date()));
			dataList.add(map);
		}else if(command.equals(EventTypeEnum.COM_SETTING_PARAMETERS.getCommand()) ) {
			//do nothing
		}else if(command.equals(EventTypeEnum.COM_DEV_STATUS.getCommand()) ) {  //实时数据查询
			DeviceStatusDto deviceDto = new DeviceStatusDto();
			deviceDto.setType(0);
			deviceDto.setIsCharging(0);
			deviceDto.setSwitch3Status(1);
			deviceDto.setSwitch7Status(0);
			deviceDto.setLock3Status(0);
			deviceDto.setLock7Status(0);
			deviceDto.setUrgentStatus(0);
			deviceDto.setDevStatus(0);
			dataList.add(deviceDto);
		} else if(command.equals(EventTypeEnum.COM_START_CHARGE.getCommand()) ) {  //开始充电
			String startTime = DateUtils.formatDate2(new Date());
			StartChargeResponseDto responseDto = new StartChargeResponseDto();
			responseDto.setStartTime(startTime);
			responseDto.setOrderNumber(obj.toString());
			dataList.add(responseDto);
			LocalStore.getInstance().getMap().put("START_TIME", startTime);
		}else if(command.equals(EventTypeEnum.COM_STOP_CHARGE.getCommand()) ) {    //结束充电
			String endTime = DateUtils.formatDate2(new Date());
			StopChargeResponseDto responseDto = new StopChargeResponseDto();
			responseDto.setEndTime(endTime);
			responseDto.setOrderNumber(obj.toString());
			dataList.add(responseDto);
			LocalStore.getInstance().getMap().put("END_TIME", endTime);
		}else if(command.equals(EventTypeEnum.COM_UPLOAD_START_RESULT.getCommand()) ) {  //上报开始充电结果
			UploadStartResult result = new UploadStartResult();
			result.setResult(0);
			result.setSwitchStatus(4);
			result.setStartTime((String)LocalStore.getInstance().getMap().get("START_TIME"));
			result.setPower(3000);
			result.setOrderNumber(obj.toString());
			dataList.add(result);
		}else if(command.equals(EventTypeEnum.COM_UPLOAD_STOP_RESULT.getCommand()) ) {  //上报停止充电结果
			UploadStopResult result = new UploadStopResult();
			result.setResult(0);
			result.setEndTime((String)LocalStore.getInstance().getMap().get("END_TIME"));
			result.setOrderNumber(obj.toString());
			dataList.add(result);
		}else if(command.equals(EventTypeEnum.COM_SET_LOCK.getCommand()) ) {   //设置电子锁开/关
			map.put("result", 0);
			dataList.add(map);
		}else if(command.equals(EventTypeEnum.COM_CHARGE_UPLOAD_EVENT.getCommand()) ) {   //上报插座实时数据
			String startTime = (String) LocalStore.getInstance().getMap().get("START_TIME");
			String currentTime = DateUtils.formatDate2(new Date());
			Object maxPower = 5000;
			Integer outPower = 3000;
			if(maxPower!=null) {
				outPower = (Integer) maxPower;
			}
			ChargeOutletUploadEventDto outletDto = new ChargeOutletUploadEventDto();
			outletDto.setType(95121);
			outletDto.setIsCharging(1);
			outletDto.setStartTime(startTime);     //订单开始时间
			outletDto.setCurrentTime(currentTime); //本次充电仓前时间
			outletDto.setDuarationTime(500);  //累计充电时间，单位（秒）
			outletDto.setStartQuantity(0);     //充电前的电量值（精度0.01度）
			outletDto.setUsedQuantity(quantity);   //已充电量（精度0.01度）
			outletDto.setVoltageOut(220);          //输出电压（精度0.1V）
			outletDto.setCurrent(6);               //电流（精度0.001A）
			outletDto.setPower(outPower);              //输出功率（精度0.1瓦）
			outletDto.setSwitch3Status(2);
			outletDto.setSwitch7Status(0);
			outletDto.setLock3Status(0);
			outletDto.setLock7Status(0);
			outletDto.setUrgentStatus(0);
			outletDto.setDevStatus(0);
			dataList.add(outletDto);
			quantity+=1;
		}else if(command.equals(EventTypeEnum.COM_REQUEST_QR_CODE_AGAIN.getCommand()) ) {
//	    	Map<String,Object> data = Maps.newHashMap();
////	    	data.put("deviceCode", "1008202590f05207d8f3");
//	    	dataList.add(data);
		}else if(command.equals(EventTypeEnum.CHARGE_COM_PAY_RULE.getCommand()) ) {
			map.put("result", 0);
			dataList.add(map);
		}
		return dataList;
	}

	public static void writeAndFlushMessage(String jsonData, ChannelHandlerContext ctx, String deviceId,String replyFlag) {
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setVersion("HDXM");
	    protocolHeader.setSrcID(deviceId); 
	    protocolHeader.setDestId("00000000000000000000");
	    protocolHeader.setRequestFlag(replyFlag);  //0 ：请求    1：应答
	    protocolHeader.setPackageNo("1");
	    
	    JSONObject jsonObject = JSON.parseObject(jsonData);
	    String command = jsonObject.getString("Command");
	    
	    String msgContent = JSON.toJSONString(jsonObject);
		log.info("创建消息：" + msgContent);
	    
	    int crc16New = CRCUtil.crc16CCITTFalse(msgContent.getBytes(), msgContent.getBytes().length);  //获得crc16
	    protocolHeader.setDataLength(msgContent.getBytes().length);
	    protocolHeader.setCrc16("" + crc16New);
	    protocolHeader.setHold((short)00);
		
	    ProtocolBody protocolBody = new ProtocolBody(protocolHeader, msgContent);
	    protocolBody.setCommand(command);
	    ctx.writeAndFlush(protocolBody);   //连接成功时发起设备注册
		log.info("客户端(" + deviceId + ")向网关发送(" + command + ")消息 ..." + protocolBody.toString());  //传对象
		
	}



}
