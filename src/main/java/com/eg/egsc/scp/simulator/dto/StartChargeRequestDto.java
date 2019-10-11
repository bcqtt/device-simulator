package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 * 充电设备DTO
 * 
 * 网关请求设备的Dto
 */
@Data
public class StartChargeRequestDto {
	
	private int targetPower;
	private String orderNumber;
}


