package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

@Data
public class UploadStartResult {
	private int result;  //是否正在充电，0表示成功、1表示失败
	private int reason;
	private int switchStatus; //1表示3孔、7孔通同时有枪插入，2表示3孔、7孔都未插枪、3表示7孔枪未插好，4表示当前仅插入3孔，5表示当前仅插入7孔
	private String startTime; //本次充电开始时间
	private int power; //输出功率
	private String orderNumber; //订单号
}
