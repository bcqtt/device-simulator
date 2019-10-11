package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

@Data
public class UploadStopResult {
	private int result;  //是否停止成功，0表示成功、1表示失败
	private String endTime; //本次充电结束时间
	private String orderNumber; //订单号
}
