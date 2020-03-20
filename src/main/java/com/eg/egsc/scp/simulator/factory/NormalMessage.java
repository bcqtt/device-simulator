package com.eg.egsc.scp.simulator.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;

@Service("normalMessageFactory")
public class NormalMessage {
	
	private static final Log log = LogFactory.getLog(NormalMessage.class);

	public ProtocolBody createMessage(String deviceId,EventTypeEnum eventType) {
		ProtocolBody body = null ;
		switch (eventType) {
		case COM_DEV_REGISTER:
			body = MessageUtil.createRegisterMsg(deviceId,eventType);
			break;
		case COM_HEARTBEAT:
			body = MessageUtil.createHeartbeatMsg(deviceId, eventType);
			break;
		case CHARGE_UPLOAD_EVENT:
			body = MessageUtil.createChargeDataUpload(deviceId, eventType);
			break;
		default:
			break;
		}
		
		return body;
	}

    public ProtocolBody createEventMessage(String deviceId) {
		return null;
    }
}
