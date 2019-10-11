package com.eg.egsc.scp.simulator;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.net.telnet.TelnetClient;
import org.junit.Test;

public class SocketTest {

	@Test
    public void test1() {
        try {
            TelnetClient telnetClient = new TelnetClient();  //指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(2000); //socket延迟时间：5000ms
            telnetClient.connect("112.95.214.226",19211);  //建立一个连接,默认端口是23   ,119.23.185.48
//            telnetClient.connect("119.23.185.48",20011);  //建立一个连接,默认端口是23
//            InputStream inputStream = telnetClient.getInputStream(); //读取命令的流
//            PrintStream pStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
//            byte[] b = new byte[1024];
//            int size;
//            StringBuffer sBuffer = new StringBuffer(300);
//            while(true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
//                size = inputStream.read(b);
//                if(-1 != size) {
//                    sBuffer.append(new String(b,0,size));
//                    if(sBuffer.toString().trim().endsWith("login:")) {
//                        break;
//                    }
//                }
//            }
//            System.out.println(sBuffer.toString());
//            pStream.println("exit"); //写命令
//            pStream.flush(); //将命令发送到telnet Server
//            if(null != pStream) {
//                pStream.close();
//            }
            telnetClient.disconnect();
        } catch (SocketException e) {
            System.out.println("异常");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("异常");
            e.printStackTrace();
        }
    }
	
	@Test
	public void test2() {
		String remoteAddress = "/112.95.214.226:12020";
		String deviceIp = remoteAddress.substring(1,remoteAddress.indexOf(":"));
		int port = Integer.parseInt(remoteAddress.substring(remoteAddress.indexOf(":")+1));
		System.out.println(isEnableConnect(deviceIp, port));
	}
	
	
	
	public static boolean isEnableConnect(String deviceIp, int port) {
			InetAddress ad = null;
			try {
				ad = InetAddress.getByName(deviceIp);
			} catch (UnknownHostException e) {
				return false;
			}
			
			boolean state = false;
			try {
				state = ad.isReachable(1000);// 测试是否可以达到该地址 ,判断ip是否可以连接 1000ms是超时时间
			} catch (IOException e) {
				System.out.println("错误：IP连接异常 " + deviceIp + "不可达");
				return false;
			} 
			if (state) {
				@SuppressWarnings("resource")
				Socket socket = new Socket();
				try {
					socket.setSoTimeout(1000);
					SocketAddress address = new InetSocketAddress(deviceIp, port);
					try {
						socket.connect(address,1000);  //1.判断ip、端口是否可连接
						return true;
					} catch (IOException e) {
						System.out.println("新建socket连接失败,IP:"+ deviceIp+",端口：" + port);
						return false;
					}
				} catch (SocketException e) {
					return false;
				}
			}
			System.out.println("错误：IP " + deviceIp + " 不可达");
			return false;
		}
}