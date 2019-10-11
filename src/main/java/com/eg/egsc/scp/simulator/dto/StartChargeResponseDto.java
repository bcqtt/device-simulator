package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 *设备对网关的响应DTO 
 *
 */
@Data
public class StartChargeResponseDto {
	private String startTime;
	private String orderNumber;
}
