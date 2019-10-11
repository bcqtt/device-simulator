package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

@Data
public class SetLockDto {
	private int operateType;  //0- 关闭，1- 打开
	private int channel;      //通道号，Bit0表示3孔插座，Bit1表示7孔插座
}
