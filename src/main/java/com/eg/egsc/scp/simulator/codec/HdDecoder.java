package com.eg.egsc.scp.simulator.codec;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.dto.GatewayDeviceDataDto;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.ByteUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 自动处理分包问题
 * @author 122879520
 *
 */
public class HdDecoder extends LengthFieldBasedFrameDecoder {

	public HdDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf buffer = (ByteBuf) super.decode(ctx, in);
		if (buffer == null) {
			return null;
		}
		ProtocolBody body = new ProtocolBody();
		ProtocolHeader header = new ProtocolHeader();
	    String version = ByteUtils.parseString(buffer, 4);
	    String srcID = ByteUtils.parseString(buffer, 20);
	    String destID = ByteUtils.parseString(buffer, 20);
	    String request = ByteUtils.parseNumber(buffer, 1);
	    String packNo = ByteUtils.parseNumber(buffer, 4);
	    String contentLength = ByteUtils.parseNumber(buffer, 4);
	    short hold = Short.parseShort(ByteUtils.parseNumber(buffer, 2));
	    String crc = ByteUtils.parseNumber(buffer, 2);
	    header.setVersion(version);
	    header.setSrcID(srcID);
	    header.setDestId(destID);
	    header.setDataLength(Integer.parseInt(contentLength));
	    header.setCrc16(crc);
	    header.setHold(hold);
	    header.setRequestFlag(request);
	    header.setPackageNo(packNo);
	    
	    byte[] encryptedData = new byte[buffer.readableBytes()];
	    buffer.readBytes(encryptedData);
	    
	    byte[] dataByte = null;
	    String msgFromGw = null;
	    //hold = 768;
	    if(hold == (short)768) {
	    	String dataEncrypted = AESUtils.parseByte2HexStr(encryptedData);
			dataByte = AESUtils.decrypt(dataEncrypted, Constant.AESKEY_VALUE);
			msgFromGw = new String(dataByte,"UTF-8");
	    }else {
			msgFromGw = new String(encryptedData,"UTF-8");
	    }
	    GatewayDeviceDataDto dataObj = JSON.parseObject(msgFromGw, GatewayDeviceDataDto.class);
	    body.setCommand(dataObj.getCommand());
	    body.setData(msgFromGw);
		body.setProtocolHeader(header);
		body.setDataByte(msgFromGw.getBytes());
		
		return body;
	}
	
	

}
