package com.eg.egsc.scp.simulator.lora;

import java.net.InetSocketAddress;

import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.common.LoraMessageManager;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class UdpClient {
	
	public void run(int port) throws Exception{

        EventLoopGroup group  = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
            .option(ChannelOption.SO_BROADCAST,true)
            .handler(new UdpClientHandler());//设置消息处理器
            Channel ch = b.bind(0).sync().channel();
            //向网段内的所有机器广播UDP消息。
            ByteBuf buf = LoraMessageManager.buildRegisterDatagram();
            ch.writeAndFlush(new DatagramPacket(buf, new InetSocketAddress("255.255.255.255",port))).sync();
            if(!ch.closeFuture().await(15000)){
                System.out.println("请求超时！");
            }
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
    public static void main(String [] args) throws Exception{
        new UdpClient().run(Constant.UDP_PORT);
    }

}
