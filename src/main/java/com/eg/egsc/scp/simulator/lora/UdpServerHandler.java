package com.eg.egsc.scp.simulator.lora;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eg.egsc.scp.simulator.common.LoraMessageManager;
import com.eg.egsc.scp.simulator.dto.lora.FRMPayload;
import com.eg.egsc.scp.simulator.util.ByteUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	private static final Log log = LogFactory.getLog(UdpServerHandler.class);
	
    /**
     * 在这个方法中，形参packet客户端发过来的DatagramPacket对象
     * DatagramPacket 类解释
     * 1.官网是这么说的：
     * The message container that is used for {@link DatagramChannel} to communicate with the remote peer.
     * 翻译：DatagramPacket 是消息容器，这个消息容器被 DatagramChannel使用，作用是用来和远程设备交流
     * 2.看它的源码我们发现DatagramPacket是final类不能被继承，只能被使用。我们还发现DatagramChannel最终实现了AddressedEnvelope接口，接下来我们看一下AddressedEnvelope接口。
     * AddressedEnvelope接口官网解释如下：
     * A message that wraps another message with a sender address and a recipient address.
     * 翻译：这是一个消息,这个消息包含发送者和接受者消息
     * 3.那我们知道了DatagramPacket它包含了发送者和接受者的消息，
     * 通过content()来获取消息内容
     * 通过sender();来获取发送者的消息
     * 通过recipient();来获取接收者的消息。
     * 
     * 4.public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {}
     *  这个DatagramPacket其中的一个构造方法，data 是发送内容;是发送都信息。
     */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		FRMPayload payload = LoraUtil.changeToFRMPayload(msg);
		
		if(payload.getData().length>0 && payload.getData()[0]==(byte)0x01) {
			log.info("客户端注册成功，应答客户端...");
			DatagramPacket dp = new DatagramPacket(LoraMessageManager.buildRegisterDatagramResp((byte)0x01),msg.sender());
			ctx.writeAndFlush(dp);
		}
		
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

}
