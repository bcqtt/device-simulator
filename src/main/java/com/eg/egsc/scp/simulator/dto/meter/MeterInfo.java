package com.eg.egsc.scp.simulator.dto.meter;

import java.io.Serializable;

import io.netty.channel.ChannelHandlerContext;

public class MeterInfo implements Serializable {
  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;
  
  /** MAC地址 */
  private String mac;
  /** IP地址 */
  private String ip;
  /** 与设备建立的通讯通道 */
  private transient ChannelHandlerContext ctx;
  /** 地址域A0-A5 */
  private byte[] addr;
  
  public String getMac() {
    return mac;
  }
  public void setMac(String mac) {
    this.mac = mac;
  }
  public String getIp() {
    return ip;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public ChannelHandlerContext getCtx() {
    return ctx;
  }
  public void setCtx(ChannelHandlerContext ctx) {
    this.ctx = ctx;
  }

  public byte[] getAddr() {
    return addr;
  }
  
  public void setAddr(byte[] addr) {
    this.addr = addr;
  }
}
