package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

/**
 * 网关请求设备的DTO
 * @author 122879520
 *
 */
@Data
public class PowerControlDto {
	private int targetPower;
	private String orderNumber;
}
