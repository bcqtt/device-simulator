package com.eg.egsc.scp.simulator.lora;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eg.egsc.scp.simulator.dto.lora.FRMPayload;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	
	private static final Log log = LogFactory.getLog(UdpClientHandler.class);

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		FRMPayload payload = LoraUtil.changeToFRMPayload(msg);
		if(payload.getData().length>0 && payload.getData()[0]==(byte)0x01) {
			log.info("注册成功...");
		}
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
