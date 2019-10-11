package com.eg.egsc.scp.simulator.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;

public class EncryptedMessage {
	
	private static final Logger log = LoggerFactory.getLogger(EncryptedMessage.class);

	public ProtocolBody createMessage(String deviceId,EventTypeEnum eventType) {
		ProtocolBody body = null ;
		switch (eventType) {
		case COM_DEV_REGISTER:
			body = MessageUtil.createRegisterMsgEncrypted(deviceId, eventType);
			break;
		case COM_HEARTBEAT:
			body = MessageUtil.createHeartbeatMsgEncrypted(deviceId, eventType);
			break;
		case COM_CHARGE_UPLOAD_EVENT:
			body = MessageUtil.createChargeDataUploadEncrypted(deviceId, eventType);
			break;
		default:
			break;
		}
		
		return body;
	}
	
	
	

}
