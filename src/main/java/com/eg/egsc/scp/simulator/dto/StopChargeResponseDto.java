package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 * 停止充电
 * 
 * 设备响应网关的DTO
 */
@Data
public class StopChargeResponseDto {
	
	private String endTime;
	private String orderNumber;
}


