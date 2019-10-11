package com.eg.egsc.scp.simulator.lora;

import com.eg.egsc.scp.simulator.dto.lora.FRMPayload;
import com.eg.egsc.scp.simulator.dto.lora.Header;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

public class LoraUtil {
	
	public static FRMPayload changeToFRMPayload(DatagramPacket msg) {
		ByteBuf byteBuf = msg.content();
		byte version = byteBuf.readByte();
		byte[] srcID = new byte[6];
		byteBuf.readBytes(srcID);
		byte isReply = byteBuf.readByte();
		byte[] packNo = new byte[4];
		byteBuf.readBytes(packNo);
		byte dataLength = byteBuf.readByte();
		byte packFlag = byteBuf.readByte();
		byte[] crc16 = new byte[2];
		byteBuf.readBytes(crc16);
		byte[] data = new byte[(int)dataLength];
		byteBuf.readBytes(data);
		
		Header header = new Header();
		header.setVision(version);
		header.setSrcId(srcID);
		header.setIsReply(isReply);
		header.setPackNo(packNo);
		header.setDataLength(dataLength);
		header.setPackFlag(packFlag);
		header.setCrc16(crc16);
		
		FRMPayload payload = new FRMPayload();
		payload.setHeader(header);
		payload.setData(data);
		
		return payload;
	}

}
