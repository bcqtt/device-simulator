package com.eg.egsc.scp.simulator.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class DeviceMessageDataDto {

	@JSONField(name = "Command")
	private String Command;

//	@JSONField(name = "Result")
	@JsonIgnore
	private Integer result; // 结果编码 0:成功，1失败（必须项）

	@JSONField(name = "Data")
	private Object Data;

	// 错误码类型
	@JSONField(name = "ErrorCode")
	private Integer errorCode;

	// 返回结果描述
	@JSONField(name = "ErrorMessage")
	private String errorMessage;

	// 事件类型
	@JSONField(name = "EventCode")
	private Integer eventCode;

	@JSONField(name = "ProductVersion")
	private String productVersion;

}
