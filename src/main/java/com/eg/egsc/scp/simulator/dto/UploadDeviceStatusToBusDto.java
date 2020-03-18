package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

import java.util.List;

/**
 * 设备状态上报
 */
@Data
public class UploadDeviceStatusToBusDto {

	/**
	 * 会话ID（每次启动充电都重新生成）
	 */
	private String sessionId;

	/**
	 * 设备编码
	 */
	private String deviceCode;

	/**
	 * 在线状态，0:离线； 1:在线
	 */
	private Integer onlineStatus;

	/**
	 * 是否正在充电，1表示正在充电，0表示未充电
	 */
	private Integer isCharging;

	/**
	 * 3孔插座连接状态
	 */
	private int switch3Status;

	/**
	 * 7孔插座连接状态
	 */
	private int switch7Status;

	/**
	 * 3孔插座的锁状态
	 */
	private int lock3Status;

	/**
	 * 7孔插座的锁状态
	 */
	private int lock7Status;

	/**
	 * 急停状态
	 */
	private int urgentStatus;

	/**
	 * 插座设备故障状态
	 */
	private int devStatus;

	// 二代充电盒专有字段
	/**
	 * 计费规则id
	 */
	private String ruleId;

	/**
	 * 枪连接状态 0：充电枪故障，1：未连接，2：已连接未供电，3：启动中，4：充电中
	 */
	private int switchStatus;
	
	private int type;

	// 二代充电盒专有字段结束

	// 三代桩专有字段
	private List<Integer> alarmCode; // 如 [102,104]等，表示当前存在的告警信息，若正常则为空[]，告警码参考协议文档9.1节定义
	private List<Integer> faultCode; // 如 [102,104]等，表示当前存在的故障信息，若正常则为空[]，告警码参考协议文档9.1节定义
	// 三代桩专有字段结束
}