package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 * 停止充电
 * 
 * 网关请求设备的DTO
 */
@Data
public class StopChargeRequestDto {
	
	private int unlock;
	private String orderNumber;
}


