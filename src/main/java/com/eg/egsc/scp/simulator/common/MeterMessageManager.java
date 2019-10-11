package com.eg.egsc.scp.simulator.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.eg.egsc.scp.simulator.dto.meter.MeterProtocolBody;
import com.eg.egsc.scp.simulator.util.ByteUtils;

import io.netty.channel.ChannelHandlerContext;

public class MeterMessageManager {

	private static final Log log = LogFactory.getLog(MeterMessageManager.class);

	static int quantity = 1;

	/**
	 * 获取电压。可根据A、B、C相进行发送，all标识全部相都有
	 * @param line
	 * @param ctx
	 */
	public static void sendVoltage(String line, ChannelHandlerContext ctx) {
		byte DI0 = 00;
		byte[] DI1 = {01, 02, 03, (byte) 0xFF };
		byte DI2 = 01;
		byte DI3 = 02;
		
		byte[] dataBytes = { DI0, DI1[0], DI2, DI3, 0, 0, 0 }; // 数据标识位，默认 line = total,后三位为数据位的BCD码
		String dataStr = "332211";
		if ("A".equals(line)) {
			dataBytes[2] = DI1[1];// A相
			dataStr = "112233";
		} else if ("B".equals(line)) {
			dataBytes[2] = DI1[2];// B相
			dataStr = "112233";
		} else if ("C".equals(line)) {
			dataBytes[2] = DI1[3];// C相
			dataStr = "112233";
		} else if ("all".equals(line)) {
			dataBytes[2] = DI1[4];// 全部相
			// TODO
		}
		byte[] dataBcb = ByteUtils.str2Bcd(dataStr);
		dataBytes[4] = dataBcb[2];
		dataBytes[5] = dataBcb[1];
		dataBytes[6] = dataBcb[0];
		
		MeterProtocolBody protocol = buildProtocolBody((byte) 0x91, dataBytes);
		ctx.writeAndFlush(protocol);
		log.info("向网关发送【" + line + "】相电压数据：" + protocol);
	}
	
	/**
	 * 获取电流。可根据A、B、C相进行发送，all标识全部相都有
	 * @param line
	 * @param ctx
	 */
	public static void sendCurrent(String line, ChannelHandlerContext ctx) {
		byte DI0 = 00;
		byte[] DI1 = {01, 02, 03, (byte) 0xFF };
		byte DI2 = 02;
		byte DI3 = 02;
		
		byte[] dataBytes = { DI0, DI1[0], DI2, DI3, 0, 0, 0 }; // 数据标识位，默认 line = total,后三位为数据位的BCD码
		String dataStr = "332211";
		if ("A".equals(line)) {
			dataBytes[2] = DI1[1];// A相
			dataStr = "112233";
		} else if ("B".equals(line)) {
			dataBytes[2] = DI1[2];// B相
			dataStr = "112233";
		} else if ("C".equals(line)) {
			dataBytes[2] = DI1[3];// C相
			dataStr = "112233";
		} else if ("all".equals(line)) {
			dataBytes[2] = DI1[4];// 全部相
			// TODO
		}
		byte[] dataBcb = ByteUtils.str2Bcd(dataStr);
		dataBytes[4] = dataBcb[2];
		dataBytes[5] = dataBcb[1];
		dataBytes[6] = dataBcb[0];
		
		MeterProtocolBody protocol = buildProtocolBody((byte) 0x91, dataBytes);
		ctx.writeAndFlush(protocol);
		log.info("向网关发送【" + line + "】相电流数据：" + protocol);
	}
	
	/**
	 * 获取瞬时有功功率。可根据A、B、C相进行发送，all标识全部都有，total标识总有功功率
	 * @param line
	 * @param ctx
	 */
	public static void sendInstantaneouseActivePower(String line, ChannelHandlerContext ctx) {
		byte DI0 = 00;
		byte[] DI1 = { 00, 01, 02, 03, (byte) 0xFF };
		byte DI2 = 03;
		byte DI3 = 02;

		byte[] dataBytes = { DI0, DI1[0], DI2, DI3, 0, 0, 0 }; // 数据标识位，默认 line = total,后三位为数据位的BCD码
		String dataStr = "000257";
		if ("A".equals(line)) {
			dataBytes[2] = DI1[1];// A相
			dataStr = "000257";
		} else if ("B".equals(line)) {
			dataBytes[2] = DI1[2];// B相
			dataStr = "000257";
		} else if ("C".equals(line)) {
			dataBytes[2] = DI1[3];// C相
			dataStr = "000257";
		} else if ("all".equals(line)) {
			dataBytes[2] = DI1[4];// 全部相
			// TODO
		}
		byte[] dataBcb = ByteUtils.str2Bcd(dataStr);
		dataBytes[4] = dataBcb[2];
		dataBytes[5] = dataBcb[1];
		dataBytes[6] = dataBcb[0];
		
		MeterProtocolBody protocol = buildProtocolBody((byte) 0x91, dataBytes);
		ctx.writeAndFlush(protocol);
		log.info("向网关发送【" + line + "】相功率数据：" + protocol);

	}

	/**
	 * 发送瞬时总有功功率 数据标识：02030000 数据格式：XXX.XXX
	 * 
	 * @param ctrlcode
	 * @param ctx
	 */
	public static void sendInstantaneousTotalActivePower(ChannelHandlerContext ctx) {
		byte[] dataBytes = { 00, 00, 03, 02, 87, 02, 00}; // 数据项00000302： 12.3456 kw
		MeterProtocolBody protocol = buildProtocolBody((byte) 0x91, dataBytes);   //控制码 0x91无后续数据帧
		ctx.writeAndFlush(protocol);
		log.info("向网关发送【瞬时总有功功率】数据：" + protocol);
	}

	private static MeterProtocolBody buildProtocolBody(byte ctrlcode, byte[] dataBytes) {
		byte[] addrByte = ByteUtils.str2Bcd("AAAAAAAAAAAA");
		MeterProtocolBody protocol = new MeterProtocolBody();
		protocol.setFrameStartFlag1(Constant.MeterFrameStartFlag); // 帧起始符：0x68
		protocol.setAddr(addrByte); // 地址域: A0~A5
		protocol.setFrameStartFlag2(Constant.MeterFrameStartFlag); // 帧起始符：0x68
		protocol.setCtrlCode(ctrlcode); // 控制码：从站发出应答，正确应答，无后续数据帧(0x91)
		protocol.setDataLen((byte) dataBytes.length); // 读度数，数据长度7
		protocol.setDataBytes(dataBytes);
		protocol.setChecksum(protocol.getChecksum());
		protocol.setFrameEndFlag(Constant.MeterFrameEndFlag); // 结束符 0x16
		return protocol;
	}

}
