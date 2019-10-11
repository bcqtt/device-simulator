package com.eg.egsc.scp.simulator.dto;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class GatewayDeviceDataDto {
  // 下发数据命令（必须项）
  @JSONField(name = "Command")
  private String command;

  // 下发数据（可选项）
  @JSONField(name = "Data")
  private Object[] data;

  // 结果编码 0:成功，1失败（必须项）
  @JSONField(name = "Result")
  private Integer result;

  // 错误码类型（可选项）
  @JSONField(name = "ErrorCode")
  private Integer errorCode;

  // 返回结果描述（可选项）
  @JSONField(name = "ErrorMessage")
  private String errorMessage;

}
