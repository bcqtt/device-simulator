package com.eg.egsc.scp.simulator.dto.lora;

import lombok.Data;

@Data
public class FRMPayload {
	private Header header;
	private byte[] data;
}
