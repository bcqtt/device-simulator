package com.eg.egsc.scp.simulator.factory;

import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.util.PackageNumSingleton;

public class ProtocolUtil {
	
	/**
	 * 初始化消息体对象(非加密)，方便后续赋值
	 * @return
	 */
	public static ProtocolBody createNormalProtocolBody() {
		ProtocolHeader protocolHeader = new ProtocolHeader();
		protocolHeader.setVersion("HDXM");
	    protocolHeader.setSrcID("");          //需要补充
	    protocolHeader.setDestId("00000000000000000000");
	    protocolHeader.setRequestFlag("0");  //0 ：请求    1：应答
	    protocolHeader.setPackageNo("" + PackageNumSingleton.getInstance().incrementPackgeNum());
	    protocolHeader.setDataLength(0);      //需要补充
	    protocolHeader.setCrc16("0");         //需要补充
	    protocolHeader.setHold((short)00);    //需要补充
	    ProtocolBody protocolBody = new ProtocolBody();
	    protocolBody.setCommand(null);
	    protocolBody.setProtocolHeader(protocolHeader);
		return protocolBody;
	}
	
	/**
	 * 初始化消息体对象(加密)，方便后续赋值
	 * @return
	 */
	public static ProtocolBody createEncryptedProtocolBody() {
	    ProtocolBody protocolBody = createNormalProtocolBody();
	    protocolBody.getProtocolHeader().setHold((short)768);
		return protocolBody;
	}

}
