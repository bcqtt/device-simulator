package com.eg.egsc.scp.simulator.channel;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.eg.egsc.scp.simulator.handler.BusinessMessageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eg.egsc.scp.simulator.codec.HdDecoder;
import com.eg.egsc.scp.simulator.codec.ProtocolEncoder;
import com.eg.egsc.scp.simulator.handler.DeviceBatchRegisterHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

@Component
public class SimulatorChannel {

	private static final Log log = LogFactory.getLog(SimulatorChannel.class);

	@Autowired
	private DeviceBatchRegisterHandler deviceBatchRegisterHandler;

	@Autowired
	private BusinessMessageHandler deviceMessageHandler;

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(3000);
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private EventLoopGroup group = new NioEventLoopGroup(16);

	private ChannelFuture channelFuture = null;

	/**
	 * 设备注册
	 * 
	 * @throws InterruptedException
	 */
	public ChannelFuture connect(String localIp, int localPort, String gateWayIp, int gatewayPort)
			throws InterruptedException {

		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK,10 * 1024 * 1024)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("protocolDecoder", new HdDecoder(1024, 49, 4, 4, 0));
						ch.pipeline().addLast("simulatorEncode", new ProtocolEncoder());
						ch.pipeline().addLast("deviceRegisterHandler", deviceBatchRegisterHandler);   //多设备批量处理
						ch.pipeline().addLast("deviceMessageHandler", deviceMessageHandler);          //模拟设备的业务消息
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
					TimeUnit.SECONDS.sleep(5);
					if(channelFuture==null) {
						log.error("******channelFuture为null");
						return;
					}
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
