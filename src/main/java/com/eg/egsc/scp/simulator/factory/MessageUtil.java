package com.eg.egsc.scp.simulator.factory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ChargeOutletUploadEventDto;
import com.eg.egsc.scp.simulator.dto.DeviceMessageDataDto;
import com.eg.egsc.scp.simulator.dto.DeviceRegisterDto;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.CRCUtil;
import com.eg.egsc.scp.simulator.util.DateUtils;
import com.eg.egsc.scp.simulator.util.Keys;
import com.eg.egsc.scp.simulator.util.RSAUtils;
import com.google.common.collect.Lists;

public class MessageUtil {
	
	private final static String AESKEY = "2222222222222222";
	
	/**
	 * 创建注册消息(非加密)
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createRegisterMsg(String deviceId,EventTypeEnum eventType) {
		String command = eventType.getCommand();
		ProtocolBody pbody = ProtocolUtil.createNormalProtocolBody();
		pbody.setCommand(command);
		pbody.getProtocolHeader().setSrcID(deviceId);
		
		List<Object> dataList = Lists.newArrayList();
		DeviceRegisterDto registerDto = new DeviceRegisterDto();
		registerDto.setDeviceID(deviceId);
		registerDto.setManufacturer("恒大");
		registerDto.setIp(Constant.SIMULATOR_IP);
		registerDto.setMac("90:f0:52:07:d8:f3");
		registerDto.setMask("255.255.255.0");
		registerDto.setVersion("Windos7");
//		registerDto.setAesKey(StringUtils.getUUID16());
		registerDto.setAesKey(AESKEY);
		registerDto.setIccid("6666666666666666666");
		dataList.add(registerDto);
		
		DeviceMessageDataDto msgDto = new DeviceMessageDataDto();
		msgDto.setProductVersion("2.0");
	    msgDto.setCommand(command);
	    msgDto.setData(dataList);
	    String msgContent = JSON.toJSONString(msgDto);
		
		int crc16 = CRCUtil.crc16CCITTFalse(msgContent.getBytes(), msgContent.getBytes().length);  //获得crc16
		pbody.getProtocolHeader().setDataLength(msgContent.getBytes().length);
		pbody.getProtocolHeader().setCrc16("" + crc16);
		pbody.setData(msgContent);
		
		return pbody;
	}
	
	/**
	 * 创建注册消息(加密：RSA+AES)
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createRegisterMsgEncrypted(String deviceId,EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		encryptedBody.getProtocolHeader().setHold((short)1280);
		byte[] encryptedByteMsg = null;
		try {
			encryptedByteMsg = RSAUtils.encryptByPublicKey(encryptedBody.getProtocolDataBytes(), Keys.SERVER_PUBLIC_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int dataLength = encryptedByteMsg.length;
		int crc16New = CRCUtil.crc16CCITTFalse(encryptedByteMsg, dataLength);  //获得crc16
		encryptedBody.getProtocolHeader().setDataLength(dataLength);
		encryptedBody.getProtocolHeader().setCrc16("" + crc16New);
		encryptedBody.setDataByte(encryptedByteMsg);
		encryptedBody.setData(null);
		return encryptedBody;
	}

	/**
	 * 创建注册消息(加密：AES公钥+AES私钥)
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createRegisterMsgEncryptedAES(String deviceId,EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		encryptedBody.getProtocolHeader().setHold((short)1280);
		byte[] encryptedByteMsg = null;
		try {
			encryptedByteMsg = RSAUtils.encryptByPublicKey(encryptedBody.getProtocolDataBytes(), Keys.SERVER_PUBLIC_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int dataLength = encryptedByteMsg.length;
		int crc16New = CRCUtil.crc16CCITTFalse(encryptedByteMsg, dataLength);  //获得crc16
		encryptedBody.getProtocolHeader().setDataLength(dataLength);
		encryptedBody.getProtocolHeader().setCrc16("" + crc16New);
		encryptedBody.setDataByte(encryptedByteMsg);
		encryptedBody.setData(null);
		return encryptedBody;
	}

	/**
	 * 创建数据字段的内容
	 * @return
	 */
	private static DeviceMessageDataDto buildDataDto(String command) {
		List<Object> dataList = Lists.newArrayList();
		DeviceMessageDataDto msgDto = new DeviceMessageDataDto();
		msgDto.setProductVersion("2.x");
	    msgDto.setCommand(command);
	    msgDto.setResult(0);
	    msgDto.setData(dataList);
		return msgDto;
	}
	
	/**
	 *协议体装箱
	 *@param 协议体
	 *@param 数据字段的内容
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	private static void encasementBody(ProtocolBody body, DeviceMessageDataDto dataDto)
			throws InvalidKeyException, UnsupportedEncodingException, 	NoSuchAlgorithmException, 
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String msgContent = JSON.toJSONString(dataDto);
		body.setData(msgContent);
		byte[] contentBytes = msgContent.getBytes();
		if(body.getProtocolHeader().getHold() == (short)768) {
			contentBytes = AESUtils.encrypt(msgContent,AESKEY);
			body.setData(null);
		}
		int crc16 = CRCUtil.crc16CCITTFalse(contentBytes, contentBytes.length);
		body.getProtocolHeader().setDataLength(contentBytes.length);
		body.getProtocolHeader().setCrc16("" + crc16);
		body.setDataByte(contentBytes);
	}
	
	/**
	 * 创建心跳消息
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createHeartbeatMsg(String deviceId, EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		DeviceMessageDataDto dataDto = buildDataDto(eventType.getCommand());
		encryptedBody.getProtocolHeader().setHold((short)00);
		try {
			encasementBody(encryptedBody, dataDto);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return encryptedBody;
	}
	
	/**
	 * 创建心跳消息(加密)
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createHeartbeatMsgEncrypted(String deviceId, EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		DeviceMessageDataDto dataDto = buildDataDto(eventType.getCommand());
		encryptedBody.getProtocolHeader().setHold((short)768);
		try {
			encasementBody(encryptedBody, dataDto);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return encryptedBody;
	}
	
	/**
	 * 创建充电数据上报消息
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createChargeDataUpload(String deviceId, EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		DeviceMessageDataDto dataDto = buildDataDto(eventType.getCommand());
		encryptedBody.getProtocolHeader().setHold((short)00);
		try {
			//封装数据体
			String startTime = "2019-05-20 10:45:45";
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
			outletDto.setUsedQuantity(50);   //已充电量（精度0.01度）
			outletDto.setVoltageOut(220);          //输出电压（精度0.1V）
			outletDto.setCurrent(32);               //电流（精度0.001A）
			outletDto.setPower(outPower);              //输出功率（精度0.1瓦）
			outletDto.setSwitch3Status(2);
			outletDto.setSwitch7Status(0);
			outletDto.setLock3Status(0);
			outletDto.setLock7Status(0);
			outletDto.setUrgentStatus(0);
			outletDto.setDevStatus(0);
			List<Object> dataObj = Lists.newArrayList();
			dataObj.add(outletDto);
			dataDto.setData(dataObj);
			encasementBody(encryptedBody, dataDto);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return encryptedBody;
	}

	/**
	 * 创建充电数据上报消息（加密）
	 * @param deviceId
	 * @param eventType
	 * @return
	 */
	public static ProtocolBody createChargeDataUploadEncrypted(String deviceId, EventTypeEnum eventType) {
		ProtocolBody encryptedBody = createRegisterMsg(deviceId,eventType);
		DeviceMessageDataDto dataDto = buildDataDto(eventType.getCommand());
		encryptedBody.getProtocolHeader().setHold((short)768);
		try {
			//封装数据体
			String startTime = "2019-05-20 10:45:45";
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
			outletDto.setUsedQuantity(50);   //已充电量（精度0.01度）
			outletDto.setVoltageOut(220);          //输出电压（精度0.1V）
			outletDto.setCurrent(30);               //电流（精度0.001A）
			outletDto.setPower(outPower);              //输出功率（精度0.1瓦）
			outletDto.setSwitch3Status(2);
			outletDto.setSwitch7Status(0);
			outletDto.setLock3Status(0);
			outletDto.setLock7Status(0);
			outletDto.setUrgentStatus(0);
			outletDto.setDevStatus(0);
			List<Object> dataObj = Lists.newArrayList();
			dataObj.add(outletDto);
			dataDto.setData(dataObj);
			encasementBody(encryptedBody, dataDto);
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}

		return encryptedBody;
	}

}
