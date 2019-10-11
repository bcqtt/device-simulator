package com.eg.egsc.scp.simulator.channel;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eg.egsc.scp.simulator.codec.MeterProtocolDecoder;
import com.eg.egsc.scp.simulator.codec.MeterProtocolEncoder;
import com.eg.egsc.scp.simulator.handler.meter.Meter645EventHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MeterChannel {

	private static final Log log = LogFactory.getLog(MeterChannel.class);

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private EventLoopGroup group = new NioEventLoopGroup();

	private ChannelFuture channelFuture = null;

	/**
	 * 设备注册
	 * 
	 * @param registerDto
	 * @return
	 * @throws InterruptedException
	 */
	public ChannelFuture connect(String localIp, int localPort, String gateWayIp, int gatewayPort)
			throws InterruptedException {
		channelFuture = null;

		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("meterProtocolDecoder", new MeterProtocolDecoder());
						ch.pipeline().addLast("meterProtocolEncoder", new MeterProtocolEncoder());
						ch.pipeline().addLast("meterEventHandler", new Meter645EventHandler());
					}
				});
		// 发起异步连接操作
		channelFuture = b.connect(new InetSocketAddress(gateWayIp, gatewayPort), 
				new InetSocketAddress(localIp, localPort)).sync();
		if (channelFuture.channel().isActive()) {
			log.info(String.format("TCP连接成功 :%s:%d --> %s:%d ", localIp, localPort, gateWayIp, gatewayPort));
		} else {
			log.info(String.format("TCP连接失败 :%s:%d --> %s:%d ", localIp, localPort, gateWayIp, gatewayPort));
		}

		// 当对应的channel关闭的时候，就会返回对应的channel。
		cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					channelFuture.channel().closeFuture().sync(); // 用线程池来执行等待
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// 所有资源释放完成之后，清空资源，再次发起重连操作
					executor.execute(new Runnable() {
						public void run() {
							try {
								TimeUnit.SECONDS.sleep(1);
								try {
									channelFuture = connect(localIp, localPort, gateWayIp, gatewayPort);// 发起重连操作
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		return channelFuture;
	}
	

}
