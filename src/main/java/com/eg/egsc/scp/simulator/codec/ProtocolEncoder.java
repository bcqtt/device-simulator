package com.eg.egsc.scp.simulator.codec;

import org.springframework.stereotype.Component;

import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.util.Int2ByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

//@Component
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolBody> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ProtocolBody msg, ByteBuf out) throws Exception {
		 // 将Message转换成二进制数据
	    ProtocolHeader header = msg.getProtocolHeader();
	    // 这里写入的顺序就是协议的顺序.
	    // 写入Header信息
	    out.writeBytes(header.getVersion().getBytes());
	    out.writeBytes(header.getSrcID().getBytes());
	    out.writeBytes(header.getDestId().getBytes());
	    out.writeBytes(Int2ByteUtil.intTo1Bytes(Integer.parseInt(header.getRequestFlag())));
	    out.writeBytes(Int2ByteUtil.intTo4Bytes(Integer.parseInt(header.getPackageNo())));
	    out.writeBytes(Int2ByteUtil.intTo4Bytes(header.getDataLength()));
	    out.writeShort(header.getHold());
	    out.writeBytes(Int2ByteUtil.intTo2Bytes(Integer.parseInt(header.getCrc16())));
	    // 写入消息主体信息
	    out.writeBytes(msg.getProtocolDataBytes());
	}

	
}
