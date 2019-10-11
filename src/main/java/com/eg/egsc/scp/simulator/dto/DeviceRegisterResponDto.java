package com.eg.egsc.scp.simulator.dto;

import com.eg.egsc.scp.simulator.common.ErrorCodeType;

import lombok.Data;

@Data
public class DeviceRegisterResponDto {
	
	private String Command;                 //命令字
	private int Result;                     //结果编码 0:成功，1失败
	private ErrorCodeType ErrorCode;        //错误代码
	private String ErrorMessage;            //返回结果描述
	private Object[] Data;                  //注册成功后携带网关分配的新密钥，失败时该字段为空.
	
}
