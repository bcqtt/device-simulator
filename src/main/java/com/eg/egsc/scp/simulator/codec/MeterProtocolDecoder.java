/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.codec;

import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.dto.meter.MeterProtocolBody;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.DateUtils;
import com.eg.egsc.scp.simulator.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 轻量级网关解码类.
 *
 * @Author yunweihang
 * @since 2018年10月21日
 */
public class MeterProtocolDecoder extends ByteToMessageDecoder {
  private final Logger logger = LoggerFactory.getLogger(MeterProtocolDecoder.class);
  // 存储分包的后半截内容数据
  private int HEARTBEAT_LEN = 13;
  
  private MeterProtocolBody partBody = null;

  /**
   * 默认头部长度.
   */
  public static final int BASE_LENGTH = 57;
  public static final int READ_LENGTH = 512;

  /**
   * 重写解码方法.
   *
   * @Methods decode
   * @Create In 2017年12月20日 By zhangweixian
   * @param ctx required
   * @param buffer required
   * @param out required
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out)
      throws Exception {
    if (! buffer.isReadable()) {
      logger.error("TCP buffer is not readable! TCP缓冲区不可读 ！");
      return;
    }
    
    int readableBytes = buffer.readableBytes();
    
    if (partBody != null) {
      // 包头已读，被分包，需读 数据长度 + (校验码、结束符)2字节 
      byte dataLen = partBody.getDataLen();
      if (readableBytes > dataLen + 2) {
        logger.info("发现电表协议数据包有分包，正在处理分包...");
        byte[] dataBytes = new byte[dataLen];
        buffer.readBytes(dataBytes); // 读 数据域
        for (int i=0; i<dataLen; i++) {
          dataBytes[i] = (byte)((byte)dataBytes[i]-(byte)0x33);
        }
        partBody.setDataBytes(dataBytes);
        String dataBytesHex = null;
        dataBytesHex = DatatypeConverter.printHexBinary(dataBytes);
        partBody.setDataStr(dataBytesHex);
        partBody.setChecksum(buffer.readByte()); // 读 校验码
        partBody.setFrameEndFlag(buffer.readByte()); // 读 帧结束符 16H 
        out.add(partBody);
        logger.info("处理分包, partBody: {}", partBody);
        partBody = null;
      }
      return;
    }
    
    byte firstByte = buffer.getByte(0);
    if (firstByte == 'H') { // 'H'开头
      if (readableBytes >= HEARTBEAT_LEN) {
        MeterProtocolBody body = new MeterProtocolBody();
        byte[] srcbytes = new byte[HEARTBEAT_LEN - 1];
        @SuppressWarnings("unused")
        byte heartbeatStartFloat = buffer.readByte(); // 读出心跳包起始符(字符'H')
        buffer.readBytes(srcbytes); // 读出心跳包数据(MAC地址)
        body.setDataBytes(srcbytes);
        body.setDataStr(new String(srcbytes));
        body.setHeartbeatFlag(true);
        out.add(body);
        return;
      }
    }
    else if (firstByte == (byte)0xFE && readableBytes>=(4+10)) { 
      // 科陆电表-国家电网智能电能表通信协议-数据帧  从站应答时也有4个字节FE
      // 应答帧也是 68H 开头 标识一帧信息的开始，其值为 68H=01101000B。
      // 以下为临时解决代码，科陆电表：有的电表发4字节FE，有的电表只发两个字节FE
      byte byTest = buffer.getByte(0);
      int feLen = 0;
      while (byTest == (byte)0xFE && feLen < 4) {
        buffer.readByte(); // // 读FE
        feLen++;
        byTest = buffer.getByte(feLen);
      }
      //
//      byte[] feBytes = new byte[4];
//      buffer.readBytes(feBytes); // 读4个字节FE ， 科陆电表 有的电表只有两个字节FE
      if (byTest != (byte)0x68) {
        byte[] errByteArr = new byte[1];
        errByteArr[0] = byTest;
        String errByteStr = DatatypeConverter.printHexBinary(errByteArr);
        logger.error("在电表协议层解析电表分包粘包数据出现错误，无法识别电表数据，预期在FEH后面是68H，现在是: {}", errByteStr);
        return;
      }
      
      MeterProtocolBody body = new MeterProtocolBody();
      body.setHeartbeatFlag(false);
      body.setFrameStartFlag1(buffer.readByte()); // 读 帧起始符 68H 
      buffer.readBytes(body.getAddr()); // 读 地址域A0-A5
      body.setFrameStartFlag2(buffer.readByte()); // 读 帧起始符 68H 
      body.setCtrlCode(buffer.readByte()); // 读 控制码C
      byte dataLen = buffer.readByte();
      body.setDataLen(dataLen); // 读 数据域长度L
      logger.info("已读电表协议头, body: {}", body);
      //
      if (readableBytes >= feLen+10+dataLen+2) { // 4个(有可能2个)字节FE + 包头10字节 + 数据长度 + (校验码、结束符)2字节 
        byte[] dataBytes = new byte[dataLen];
        buffer.readBytes(dataBytes); // 读 数据域
        for (int i=0; i<dataLen; i++) {
          dataBytes[i] = (byte)((byte)dataBytes[i]-(byte)0x33);
        }
        body.setDataBytes(dataBytes);
        String dataBytesHex = null;
        dataBytesHex = DatatypeConverter.printHexBinary(dataBytes);
        body.setDataStr(dataBytesHex);
        body.setChecksum(buffer.readByte()); // 读 校验码
        body.setFrameEndFlag(buffer.readByte()); // 读 帧结束符 16H 
        out.add(body);
      }
      else {
        partBody = body;
      }
    }
    else { // 不是 'H' 开头，也不是0xFE开头
      byte[] moreBytes = new byte[readableBytes];
      byte byTest = buffer.getByte(0);
      int ridx = 0;
      while (byTest != (byte)0xFE && byTest != 'H') {
        moreBytes[ridx] = buffer.readByte(); // // 读FE
        ridx++;
        byTest = buffer.getByte(ridx);
      }
      byte[] readMoreBytes = new byte[ridx];
      System.arraycopy(moreBytes, 0, readMoreBytes, 0, readMoreBytes.length);
      String moreBytesHex = null;
      moreBytesHex = DatatypeConverter.printHexBinary(readMoreBytes);
      logger.error("协议解码读了未在预期的未知数据字节, 十六进制内容为: {}", moreBytesHex);
    }

  }

  private void printDebugMsg(ChannelHandlerContext ctx, MeterProtocolBody body) {
    if (logger.isDebugEnabled()) {
      String allBytesHexStr = AESUtils.parseByte2HexStr(body.getDataBytes());
      logger.info("网关读取客户端({})上传的数据十六进制:{}", ctx.channel().remoteAddress(), allBytesHexStr);
    }
  }

  /**
   * 组装协议头,读取前面57个.
   *
   * @param buffer 字节缓冲
   * @param header void
   */
  private ProtocolHeader assembleHeader(ByteBuf buffer) {
    ProtocolHeader header = new ProtocolHeader();
    String version = parseString(buffer, 4);
    String srcID = parseString(buffer, 20);
    String destID = parseString(buffer, 20);
    String request = parseNumber(buffer, 1);
    String packNo = parseNumber(buffer, 4);
    String contentLength = parseNumber(buffer, 4);
    short hold = Short.parseShort(parseNumber(buffer, 2));
    String crc = parseNumber(buffer, 2);
    header.setVersion(version);
    header.setSrcID(srcID);
    header.setDestId(destID);
    header.setDataLength(Integer.parseInt(contentLength));
    header.setCrc16(crc);
    header.setHold(hold);
    header.setRequestFlag(request);
    header.setPackageNo(packNo);
    return header;
  }

  /**
   * byte数组转成字符串.
   *
   * @Methods Name parseString
   * @Create In 2017年12月20日 By zhangweixian
   * @param buffer required
   * @param length required
   * @return String
   */
  private String parseString(ByteBuf buffer, int length) {
    byte[] bytes = new byte[length];
    String str = null;
    try {
      buffer.readBytes(bytes);
      str = new String(bytes, Constant.ENCODING);
    } catch (Exception e) {
      logger.debug("解码器转换字符串发生异常:" + e.getMessage());
      return null;
    }
    return str;
  }


  /**
   * byte数组转成10进制数字字符串.
   *
   * @Methods Name parseNumber
   * @Create In 2017年12月20日 By zhangweixian
   * @param buffer required
   * @param length required
   * @return String
   */
  private String parseNumber(ByteBuf buffer, int length) {
    byte[] bytes = new byte[length];
    buffer.readBytes(bytes);
    String result = null;
    try {
      result = StringUtils.binary(bytes, 10);
    } catch (Exception e) {
      logger.debug("时间为" + DateUtils.formatDate2(new Date()) + "解码器转换数字异常:"
          + e.getMessage());
      return null;
    }
    return result;
  }

}
