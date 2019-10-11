/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.codec;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.dto.meter.MeterProtocolBody;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 轻量级网关编码类.
 * 
 * @Author yunweihang
 * @since 2018年10月21日
 */
public class MeterProtocolEncoder extends MessageToByteEncoder<MeterProtocolBody> {
  private static final Logger logger = LoggerFactory.getLogger(MeterProtocolEncoder.class);

  /**
   * 重写编码方法.
   * 
   * @Methods encode
   * @Create In 2017年12月20日 By zhangweixian
   * @param tcx required
   * @param msg required
   * @param out required
   */
  static boolean bIsSend = false;
  @Override
  protected void encode(ChannelHandlerContext tcx, MeterProtocolBody body, ByteBuf out) throws Exception {
	  if(body.isHeartbeatFlag()) {
		  ByteBuf byteBuf = Unpooled.copiedBuffer(body.getDataStr().getBytes());
		  out.writeBytes(byteBuf);
	  }else {
		  // 5.3.2  传输次序  所有数据项均先传送低位字节，后传送高位字节。
		  //在主站发送帧信息之前，先发送4个字节FEH，以唤醒接收方。
//    if (! bIsSend) {
		  out.writeByte(0xFE);
		  out.writeByte(0xFE);
		  out.writeByte(0xFE);
		  out.writeByte(0xFE);
//      bIsSend = true;
//    }
		  out.writeByte(body.getFrameStartFlag1());
		  for (int i=0; i<body.getAddr().length; i++) {
			  out.writeByte(body.getAddr()[i]);
		  }
		  out.writeByte(body.getFrameStartFlag2());
		  out.writeByte(body.getCtrlCode());
		  byte dataLen = body.getDataLen();
		  out.writeByte(dataLen);
		  // 数据域：传输时发送方按字节进行加33H处理，接收方按字节进行减33H处理。
		  if (body.getDataLen() > 0) {
			  byte[] dataX = new byte[dataLen];
			  for (int i=0; i<dataLen; i++) {
				  dataX[i] = (byte)((byte)body.getDataBytes()[i] + (byte)0x33);
				  out.writeByte(dataX[i]);
			  }
			  String addrXHex = DatatypeConverter.printHexBinary(dataX);
			  logger.debug("发送的数据域加33H后的十六进制数据: {}", addrXHex);
		  }
		  else {
			  logger.debug("将要发送的电表协议数据域长度为0");
		  }
		  out.writeByte(body.getChecksum());
		  out.writeByte(body.getFrameEndFlag());
	  }
   }

}
