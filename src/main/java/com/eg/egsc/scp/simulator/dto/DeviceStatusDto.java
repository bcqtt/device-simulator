package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

@Data
public class DeviceStatusDto {
	private int type;  //应答时该值为0
	private int isCharging;  //是否正在充电，1表示正在充电，0表示未充电
	private int onlineStatus;
	private int urgentStatus;
	private int devStatus;
	private int switch3Status;
	private int switch7Status;
	private int lock3Status;
	private int lock7Status;
	private int switchStatus;
	private String ruleId;
}
