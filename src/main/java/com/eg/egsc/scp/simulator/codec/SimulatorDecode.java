package com.eg.egsc.scp.simulator.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class SimulatorDecode extends LengthFieldBasedFrameDecoder {

	/**
    *
    * @param maxFrameLength  帧的最大长度
    * @param lengthFieldOffset length字段偏移的地址
    * @param lengthFieldLength length字段所占的字节长
    */
	public SimulatorDecode( int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
    }

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		in = (ByteBuf) super.decode(ctx,in); 
		
		return super.decode(ctx, in);
	}
	

}
