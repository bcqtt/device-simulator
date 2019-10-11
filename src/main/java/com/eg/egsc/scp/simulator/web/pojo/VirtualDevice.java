package com.eg.egsc.scp.simulator.web.pojo;

import java.util.Date;

import lombok.Data;

@Data
public class VirtualDevice {
	private String id;
	private String deviceId;
	private int deviceType;
	private String deviceName;
	private String deviceIp;
	private int devicePort;
	private String deviceMac;
	private String deviceMark;
	private String version;
	private Date createTime;
	private int deviceStatus;//设备状态 0:离线   1:在线
	private String environment;
	private int gatewayPort;
	private String gatewayIp;
}
