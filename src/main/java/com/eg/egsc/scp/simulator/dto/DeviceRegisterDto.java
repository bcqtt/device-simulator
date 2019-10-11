package com.eg.egsc.scp.simulator.dto;

import com.eg.egsc.scp.simulator.common.DeviceType;

import lombok.Data;

@Data
public class DeviceRegisterDto {
	
	private DeviceType Type;          //设备类型
	private String deviceID;          //设备ID  
	private String manufacturer;      //厂商
	private int macNO;                //机号
	private String locationAddr;      //逻辑地址
	private String name;              //设备名称
	private String gateWay;           //网关
	private String ip;                //设备ip
	private String mac;               //Mac地址（设备出厂设定）
	private String mask;              //子网掩码
	private String version;           //系统版本号
	private String aesKey;            //AES秘钥
	private String iccid;             //4G卡sim卡号，20字节，未使用4G不用携带字段


}
