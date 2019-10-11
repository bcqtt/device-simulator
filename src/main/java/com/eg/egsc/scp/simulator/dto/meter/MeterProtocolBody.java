package com.eg.egsc.scp.simulator.dto.meter;

import java.io.Serializable;

import javax.xml.bind.DatatypeConverter;

/**
 * 协议体实体类.
 * 5.3.2  传输次序  所有数据项均先传送低位字节，后传送高位字节。
 * 在主站发送帧信息之前，先发送4个字节FEH，以唤醒接收方。
 * 每次通信都是由主站向按信息帧地址域选择的从站发出请求命令帧开始，被请求的从站接收到命令后作出响应。
 * 字节校验为偶校验，帧校验为纵向信息校验和，接收方无论检测到偶校验出错或纵向信息校验和出错，均放弃该信息帧，不予响应。
 * @Author wangguojun
 * @Create In 2018年1月4日
 */
public class MeterProtocolBody implements Serializable {

  
  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;
  /** 染色ID */
  private String traceId;
  
  /** 
   * 帧起始符 68H 
   * 标识一帧信息的开始，其值为 68H=01101000B。
   */
  private byte frameStartFlag1;
  
  /** 地址域A0-A5 */
  private byte[] addr = new byte[6];
  
  /** 帧起始符 68H  */
  private byte frameStartFlag2;
  
  /** 控制码C 
   * 控制码：C=11H ,功能：请求读电能表数据 
   * ...
   */
  private byte ctrlCode;
  
  /** 数据长度L */
  private byte dataLen;
  
  /** 
   * 数据域
   * 数据域包括数据标识、密码、操作者代码、数据、帧序号等，其结构随控制码的功能而改变。
   * 传输时发送方按字节进行加33H处理，接收方按字节进行减33H处理。
   */
  private byte[] dataBytes;
  private String dataStr;
  
  /** 
   * 校验码 
   * 从第一个帧起始符开始到校验码之前的所有各字节的模 256 的和，即各字节二进制算术和，不计超过 256 的溢出值。
   */
  private byte checksum;
  
  /** 
   * 帧结束符 16H 
   * 标识一帧信息的结束，其值为 16H=00010110B。
   */
  private byte frameEndFlag;

  /** 是否为心跳包标志， ture: 心跳包, false: 非心跳包 */
  private boolean heartbeatFlag;

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public byte getFrameStartFlag1() {
    return frameStartFlag1;
  }

  public void setFrameStartFlag1(byte frameStartFlag1) {
    this.frameStartFlag1 = frameStartFlag1;
  }

  public byte[] getAddr() {
    return addr;
  }

  public void setAddr(byte[] addr) {
    this.addr = addr;
  }

  public byte getFrameStartFlag2() {
    return frameStartFlag2;
  }

  public void setFrameStartFlag2(byte frameStartFlag2) {
    this.frameStartFlag2 = frameStartFlag2;
  }

  public byte getCtrlCode() {
    return ctrlCode;
  }

  public void setCtrlCode(byte ctrlCode) {
    this.ctrlCode = ctrlCode;
  }

  public byte getDataLen() {
    return dataLen;
  }

  public void setDataLen(byte dataLen) {
    this.dataLen = dataLen;
  }

  public byte[] getDataBytes() {
    return dataBytes;
  }

  public void setDataBytes(byte[] dataBytes) {
    this.dataBytes = dataBytes;
  }

  public String getDataStr() {
    return dataStr;
  }

  public void setDataStr(String dataStr) {
    this.dataStr = dataStr;
  }

  public byte getChecksum() {
    checksum = calcChecksum();
    return checksum;
  }

  public void setChecksum(byte checksum) {
    this.checksum = checksum;
  }

  public byte getFrameEndFlag() {
    return frameEndFlag;
  }

  public void setFrameEndFlag(byte frameEndFlag) {
    this.frameEndFlag = frameEndFlag;
  }

  public boolean isHeartbeatFlag() {
    return heartbeatFlag;
  }

  public void setHeartbeatFlag(boolean heartbeatFlag) {
    this.heartbeatFlag = heartbeatFlag;
  }

  @Override
  public String toString() {
    String addrHex = DatatypeConverter.printHexBinary(addr);
    String dataBytesHex = null;
    if (dataBytes != null) {
      dataBytesHex = DatatypeConverter.printHexBinary(dataBytes);
    }
    return String.format("MeterProtocolBody [heartbeatFlag=%b, traceId=%s, frameStartFlag1=%xH, addr=%sH, frameStartFlag2=%xH, ctrlCode=%xH, dataLen=%d, dataBytes=%s, dataStr=%s, checksum=%xH, frameEndFlag=%xH]", 
        heartbeatFlag, traceId, frameStartFlag1, addrHex, frameStartFlag2, ctrlCode, dataLen, dataBytesHex, dataStr, getChecksum(), frameEndFlag);
  }

  /**
   * 计算校验码
   * @Methods Name calcCheckCode
   * @Create In 2018年10月21日 By 070656190
   * @return byte
   */
//  static byte cs = 0;
  private byte calcChecksum() {
//    if (cs==1) cs=2;
//    if(true) return cs++;
    byte code = frameStartFlag1;
    for (int i=0; i<addr.length; i++) {
      code += addr[i];
    }
    code += frameStartFlag2;
    code += ctrlCode;
    code += dataLen;
    if (dataBytes != null) {
      for (int i=0; i<dataBytes.length; i++) {
        code = (byte)((byte)code + (byte)((byte)dataBytes[i]+(byte)0x33) );
      }
    }
    return code;
  }

  
  public static void main(String[]a ) {
//    MeterProtocolBody protocol = new MeterProtocolBody();
//    protocol.setCtrlCode((byte)0x11);
//    byte dataLen = 4;
//    protocol.setDataLen((byte)dataLen);
//    byte[] dataBytes = new byte[dataLen];
//    protocol.setDataBytes(dataBytes);
//    System.out.print(protocol);

    MeterProtocolBody body = new MeterProtocolBody();
    body.setHeartbeatFlag(false);
    body.setFrameStartFlag1((byte)0x68); // 读 帧起始符 68H 
    body.setFrameStartFlag2((byte)0x68); // 读 帧起始符 68H 
    body.setCtrlCode((byte)0x11); // 读 控制码C
    body.setDataLen((byte)0x7); // 读 数据域长度L
    System.out.print( body);
  }
}
