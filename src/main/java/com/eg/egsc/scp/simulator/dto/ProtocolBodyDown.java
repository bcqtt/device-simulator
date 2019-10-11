/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.dto;

import java.io.Serializable;

/**
 * 协议体实体类.
 * 
 * @Author wangguojun
 * @Create In 2018年1月4日
 */
public class ProtocolBodyDown implements Serializable {
  private static final long serialVersionUID = -1569883613531008043L;

  private ProtocolHeader protocolHeader;

  /**
   * 消息的内容.
   */
  private byte[] content;

  /**
   * 协议命令ID, 需要从content中解析出来.
   */
  private String command;

  public ProtocolBodyDown() {
    super();
  }
  
  /**
   * 用于初始化，SmartCarProtocol.
   * 
   * @param protocolHeader 协议里面，消息数据的长度
   * @param content 协议里面，消息的数据
   */
  public ProtocolBodyDown(ProtocolHeader protocolHeader, byte[] content) {
    this.protocolHeader = protocolHeader;
    this.content = content;
  }


  public ProtocolHeader getProtocolHeader() {
    return protocolHeader;
  }

  public void setProtocolHeader(ProtocolHeader protocolHeader) {
    this.protocolHeader = protocolHeader;
  }

  public byte[] getContent() {
    return content;
  }


  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getCommand() {
    return command;
  }


  public void setCommand(String command) {
    this.command = command;
  }

  @Override
  public String toString() {
    return "DeviceInfoDto [command=" + command + ", protocolHeader=" + protocolHeader.toString() + ", content="
        + content +  "]";
  }

}
