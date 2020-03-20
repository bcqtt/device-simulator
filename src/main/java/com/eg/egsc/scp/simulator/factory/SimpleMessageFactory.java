package com.eg.egsc.scp.simulator.factory;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;

/**
 * 抽象的消息工厂
 * 
 * @author 122879520
 *
 */
public class SimpleMessageFactory {
	
	private static boolean encryptEnable = true;

	public static ProtocolBody createMessage(String deviceId, EventTypeEnum eventType) {
		
		if(encryptEnable) {
			return new EncryptedMessage().createMessage(deviceId, eventType);
		}

		return new NormalMessage().createMessage(deviceId, eventType);
	}

	//针对COM_UPLOAD_EVENT事件，充电中的周期性上报
	public static ProtocolBody createEventDataMessage(String deviceId) {
		if(encryptEnable) {
			return new EncryptedMessage().createEventMessage(deviceId);
		}

		return new NormalMessage().createEventMessage(deviceId);
	}
}
