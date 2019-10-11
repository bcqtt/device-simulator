package com.eg.egsc.scp.simulator.lora;

import com.eg.egsc.scp.simulator.common.Constant;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {
	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		// 由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
				.handler(new UdpServerHandler());
		b.bind(port).sync().channel().closeFuture().await();
	}

	public static void main(String[] args) throws Exception {
		new UdpServer().run(Constant.UDP_PORT);
	}

}
