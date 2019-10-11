/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 协议头实体类.
 * 
 * @Author wangguojun
 * @Create In 2018年1月4日
 */
@Data
public class ProtocolHeader implements Serializable {
  private static final long serialVersionUID = 1L;
  //染色ID
  private String traceId;
  // 协议版本
  private String version;
  // 目标ID
  private String destId;
  // 源ID
  private String srcID;
  // 请求或应答
  private String requestFlag;
  // 包号
  private String packageNo;
  // 数据长度
  private Integer dataLength;
  // 预留
  private short hold;
  // crc校验
  private String crc16;

}
