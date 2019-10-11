package com.eg.egsc.scp.simulator.component;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 本地计算机环境数据辅助类
 * 
 * @author 122879520
 *
 */
public class DeviceEnv {

	private static final Log log = LogFactory.getLog(DeviceEnv.class);

	@SuppressWarnings("static-access")
	private static InetAddress getIa() {
		InetAddress ia = null;
		try {
			ia = ia.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ia;
	}

	/**
	 * 获取本机IP
	 * 
	 * @return
	 */
	public static String getLocalIp() {
		InetAddress ia = getIa();
		String localname = ia.getHostName();
		String localip = ia.getHostAddress();
		//log.info("本机名称是：" + localname + "	本机的ip是 ：" + localip);
		return localip;
	}

	/**
	 * 获取本机MAC地址
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalMac() {
		InetAddress ia = getIa();
		// 获取网卡，获取地址
		byte[] mac;
		StringBuffer sb = null;
		try {
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			//log.info("mac数组长度：" + mac.length);
			sb = new StringBuffer("");
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append(":");
				}
				// 字节转换为整数
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		String localMac = sb.toString().toUpperCase();
		//log.info("本机MAC地址:" + localMac);
		return localMac;
	}

	/**
	 * 获取子网掩码
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getSubnetMask() {
		InetAddress ia = getIa();
		NetworkInterface ni = null;
		try {
			ni = NetworkInterface.getByInetAddress(getIa());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		// 搜索绑定了指定IP地址的网络接口
		List<InterfaceAddress> list = ni.getInterfaceAddresses();// 获取此网络接口的全部或部分 InterfaceAddresses 所组成的列表
		String submask = "";
		if (list.size() > 0) {
			short prflen = list.get(0).getNetworkPrefixLength();// 子网掩码的二进制1的个数
			int shft = 0xffffffff << (32 - prflen);
			int oct1 = ((byte) ((shft & 0xff000000) >> 24)) & 0xff;
			int oct2 = ((byte) ((shft & 0x00ff0000) >> 16)) & 0xff;
			int oct3 = ((byte) ((shft & 0x0000ff00) >> 8)) & 0xff;
			int oct4 = ((byte) (shft & 0x000000ff)) & 0xff;
			submask = oct1 + "." + oct2 + "." + oct3 + "." + oct4;
			//log.info("子网掩码:" + submask);
		}
		return submask;
	}

	/**
	 * 获取系统版本号
	 * 
	 * @return
	 */
	public static String getSystemVersion() {
		Properties props = System.getProperties();
		String version = props.getProperty("os.name") + "_V" + props.getProperty("os.version");
		//log.info("系统版本号:" + version);
		return version;
	}

	/**
	 * 随机生成MAC
	 * 
	 * @return
	 */
	public static String createMac() {
		Random random = new Random();
		String[] mac = { String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
				String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
				String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)) };
		return String.join(":", mac);
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

	public static void main(String[] args) throws SocketException {
		System.out.println(getLocalMac());
		System.out.println(getLocalIp());
	}

}
