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
		case CHARGE_UPLOAD_EVENT:
			body = MessageUtil.createChargeDataUploadEncrypted(deviceId, eventType);
			break;
		case CHARGE_COM_DEV_STATUS:
			body = MessageUtil.createDevStatusDataUploadEncrypted(deviceId, eventType);
			break;
		case CHARGE_COM_START_CHARGE:
			body = MessageUtil.createStartResponseEncrypted(deviceId, eventType);
			break;
		case CHARGE_UPLOAD_START_RESULT:
			body = MessageUtil.createStartResultEncrypted(deviceId, eventType);
			break;
		case CHARGE_COM_STOP_CHARGE:
			body = MessageUtil.createStopResponseEncrypted(deviceId, eventType);
			break;
		case CHARGE_UPLOAD_STOP_RESULT:
			body = MessageUtil.createStopResultEncrypted(deviceId, eventType);
			break;
		case CHARGE_COM_PAY_RULE:
			body = MessageUtil.createRuleResponseEncrypted(deviceId, eventType);
			break;
		case CHARGE_COM_REQ_REAL_DATA:
			body = MessageUtil.createRealDataEncrypted(deviceId, eventType);
			break;
		default:
			break;
		}
		
		return body;
	}


	public ProtocolBody createEventMessage(String deviceId) {
		return MessageUtil.createEventDataEncrypted(deviceId);
	}
}
