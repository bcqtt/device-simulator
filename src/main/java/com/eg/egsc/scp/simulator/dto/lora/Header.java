package com.eg.egsc.scp.simulator.dto.lora;

import lombok.Data;

@Data
public class Header {
	
	private byte vision = (byte)0xAA;     //1byte,协议版本,固定值0xAA
	private byte[] srcId = new byte[6];   //6byte,源ID
	private byte isReply;                 //1byte,请求/应答.0 请求，1应答
	private byte[] packNo = new byte[4];  //4byte,包号
	private byte dataLength;              //1byte,包号
	private byte packFlag;                //1byte,分包标志
	private byte[] crc16 = new byte[2];   //2byte,CRC16

}
