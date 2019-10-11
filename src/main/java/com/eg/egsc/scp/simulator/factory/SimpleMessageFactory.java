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

}
