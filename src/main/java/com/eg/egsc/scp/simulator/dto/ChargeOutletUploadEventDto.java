package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 * 用于充电插座事件上报
 * @author 122879520
 *
 */
@Data
public class ChargeOutletUploadEventDto extends UploadEventDto {
	private int isCharging; //是否正在充电，1表示正在充电，0表示未充电
	private String startTime;  //本次充电开始时间，若非充电状态该字段为空
	private String currentTime; //本次充电当前时间
	private int duarationTime; //累计充电时间，单位（秒）
	private int startQuantity; //充电前的电量值（精度0.01度）
	private int usedQuantity;  //已充电量（精度0.01度）
	private int voltageOut;  //输出电压（精度0.1V）
	private int current;    //电流（精度0.001A）
	private int power;      //输出功率（精度0.1瓦）
	private int switch3Status;      //0表示未连接，1表示连接但是未供电，2表示正在供电中，当状态发送变化时立即上报
	private int switch7Status;      //0表示未连接，1表示插座、枪已连接好，2表示插座、枪、车已经连接好但是未供电，3表示插座、枪、车已经连接好且已经给车发送开始充电指令，4表示插座、枪、车已经连接好且正在充电，当状态发送变化时立即上报
	private int lock3Status;      //3孔插座的锁状态，0表示未上锁，1表示已上锁，当状态发送变化时立即上报
	private int lock7Status;      //7孔插座的锁状态，0表示未上锁，1表示已上锁，当状态发送变化时立即上报
	private int urgentStatus;   //0：非急停状态，1，急停状态，系统产生急停事件后向平台上报，平台收到急停事件后会禁用插座，当有工作人员介入弹出急停按钮后，平台会启动插座；
	                            //注：急停按钮没弹出来之前不允许使用
	private int devStatus;  //智能插座设备状态：设备故障（正常、过压、欠压、过流、欠流、漏电）
	private int switchStatus;
}
