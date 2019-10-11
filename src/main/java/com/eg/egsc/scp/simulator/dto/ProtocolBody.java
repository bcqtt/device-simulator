package com.eg.egsc.scp.simulator.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.BeanUtils;

import com.eg.egsc.scp.simulator.util.AESUtils;

public class ProtocolBody implements Serializable {
  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1021165174379609585L;

  private ProtocolHeader protocolHeader;

  public ProtocolBody() {
    protocolHeader = new ProtocolHeader();
  }

  /**
   * 消息的内容.
   */
  private String data;

  /**
   * 协议命令ID, 需要从content中解析出来.
   */
  private String command;

  /**
   * 字节内容.
   */
  private byte[] dataBytes;

  /**
   * 用于初始化，SmartCarProtocol.
   *
   * @param protocolHeader 协议里面，消息数据的长度
   * @param content 协议里面，消息的数据
   */
  public ProtocolBody(ProtocolHeader protocolHeader, String data) {
    ProtocolHeader header = new ProtocolHeader();
    BeanUtils.copyProperties(protocolHeader,header);
    this.protocolHeader = header;
    this.data = data;
  }


  public ProtocolHeader getProtocolHeader() {
    return protocolHeader;
  }

  public void setProtocolHeader(ProtocolHeader protocolHeader) {
    this.protocolHeader = protocolHeader;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public byte[] getDataBytes() {
    return dataBytes;
  }

  public void setDataByte(byte[] dataByte) {
    this.dataBytes = dataByte;
  }

  /**
   * 获取协议包里面要发送的数据字段二进制字节流
   * @Methods Name getContentByteEx
   * @Create In 2018年6月12日 By Administrator
   * @return byte[] 要发送的数据字段二进制字节流
   * @throws UnsupportedEncodingException 
   */
  public byte[] getProtocolDataBytes() throws UnsupportedEncodingException {
    if (data != null) {
      return data.getBytes("UTF-8");
    }
    return dataBytes;
  }
  
  @Override
  public String toString() {
    String dataFormat = "data=%s";
    String dataStr = data;
    if (data == null) {
      dataFormat = "dataByte=%s";
      dataStr = AESUtils.parseByte2HexStr(dataBytes);
    }
    return String.format(
        "[version=%s,dataLength=%s,srcId=%s,destId=%s,Hold=%s,RequestFlag=%s,PackageNo=%s"
            + ",Crc16=%s," + dataFormat + "]",
        protocolHeader.getVersion(), protocolHeader.getDataLength(), protocolHeader.getSrcID(),
        protocolHeader.getDestId(), protocolHeader.getHold(), protocolHeader.getRequestFlag(),
        protocolHeader.getPackageNo(), protocolHeader.getCrc16(), dataStr);
  }

}
