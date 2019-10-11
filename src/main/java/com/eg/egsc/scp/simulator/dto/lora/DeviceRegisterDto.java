package com.eg.egsc.scp.simulator.dto.lora;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * 设备注册
 * @author 122879520
 *
 */
@Data
public class DeviceRegisterDto {
	private byte command; //1byte
	private byte[] locationAddr = new byte[4];    //1byte,逻辑地址
	private byte[] ip = new byte[4];    //4byte,逻辑地址
	private byte[] mask = new byte[4];    //4byte,子网掩码
	private byte[] version = new byte[3];    //3byte,逻辑地址
	
	public ByteBuf getLoraDatagram() {
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(command);
		buf.writeBytes(locationAddr);
		buf.writeBytes(ip);
		buf.writeBytes(mask);
		buf.writeBytes(version);
		return buf;
	}

}
