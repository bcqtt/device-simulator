package com.eg.egsc.scp.simulator.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eg.egsc.scp.simulator.dto.lora.DeviceRegisterDto;
import com.eg.egsc.scp.simulator.dto.lora.FRMPayload;
import com.eg.egsc.scp.simulator.dto.lora.Header;
import com.eg.egsc.scp.simulator.dto.meter.MeterProtocolBody;
import com.eg.egsc.scp.simulator.util.ByteUtils;
import com.eg.egsc.scp.simulator.util.CRCUtil;
import com.eg.egsc.scp.simulator.util.Int2ByteUtil;
import com.eg.egsc.scp.simulator.util.PackageNumSingleton;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class LoraMessageManager {

	private static final Log log = LogFactory.getLog(LoraMessageManager.class);
	
	private static FRMPayload buildFRMPayload() {
		Header header = new Header();
		byte[] srcId = {(byte)0x8C,(byte)0xEC,(byte)0x4B,(byte)0x41,(byte)0x9D,(byte)0xB3};  //8C-EC-4B-41-9D-B3
		header.setSrcId(srcId);
		header.setIsReply((byte)0x00);
		
		int packNo = PackageNumSingleton.getInstance().incrementPackgeNum();
		header.setPackNo(Int2ByteUtil.intTo4Bytes(packNo));
		header.setPackFlag((byte)0x00);
		
		FRMPayload frmPyload = new FRMPayload();
		frmPyload.setHeader(header);
		
		return frmPyload;
	}
	
	/**
	 * 设备注册的Lora消息
	 * @return
	 */
	public static ByteBuf buildRegisterDatagram() {
		DeviceRegisterDto dto = new DeviceRegisterDto();
		dto.setCommand(Constant.LORA_REGISTER);
		dto.setIp(ByteUtils.strNumberToByte("172.25.83.231"));
		dto.setLocationAddr(ByteUtils.strNumberToByte("192.168.33.10"));
		dto.setMask(ByteUtils.strNumberToByte("255.255.254.0"));
		dto.setVersion(ByteUtils.strNumberToByte("0.0.1"));
		
		ByteBuf dtoBuf = dto.getLoraDatagram();
		FRMPayload payload = buildFRMPayload();
		Header header = payload.getHeader();
		byte[] data = new byte[dtoBuf.readableBytes()];
		dtoBuf.readBytes(data);
		header.setCrc16(CRCUtil.getParamCRC(data));
		header.setDataLength((byte)data.length);
		payload.setData(data);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(header.getVision());
		buf.writeBytes(header.getSrcId());
		buf.writeByte(header.getIsReply());
		buf.writeBytes(header.getPackNo());
		buf.writeByte(header.getDataLength());
		buf.writeByte(header.getPackFlag());
		buf.writeBytes(header.getCrc16());
		buf.writeBytes(data);
		
		return buf;
	}
	
	public static ByteBuf buildRegisterDatagramResp(byte isREply) {
		ByteBuf dtoBuf = Unpooled.buffer();
		dtoBuf.writeByte((byte)0x01);  //命令字
		dtoBuf.writeByte((byte)0x00);  //成功
		byte[] aseKey = "123456789".getBytes();
		dtoBuf.writeBytes(aseKey);
		
		FRMPayload payload = buildFRMPayload();
		Header header = payload.getHeader();
		header.setIsReply(isREply);
		byte[] data = new byte[dtoBuf.readableBytes()];
		dtoBuf.readBytes(data);
		header.setCrc16(CRCUtil.getParamCRC(data));
		header.setDataLength((byte)data.length);
		payload.setData(data);
		
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(header.getVision());
		buf.writeBytes(header.getSrcId());
		buf.writeByte(header.getIsReply());
		buf.writeBytes(header.getPackNo());
		buf.writeByte(header.getDataLength());
		buf.writeByte(header.getPackFlag());
		buf.writeBytes(header.getCrc16());
		buf.writeBytes(data);
		System.out.println(buf.array().toString());
		return buf;
	}


}
