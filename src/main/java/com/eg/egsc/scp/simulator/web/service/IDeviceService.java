package com.eg.egsc.scp.simulator.web.service;

import java.util.List;

import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;

public interface IDeviceService {
	
	public int save(VirtualDevice device);
	
	public List<VirtualDevice> queryAllDevice();

	public VirtualDevice queryDeviceById(String deviceId);

	public int createDevicePort();

	public String goOnline(String deviceId);

	public String doOffline(String deviceId);

	public String meterOnline(String deviceId);

	public String requestQrCode(String localIp, int localPort);

	public String sendMessage(String gatewayIp, Integer gatewayPort, String deviceId, String replyFlag, String jsonData);

	public String deviceRegister(String gatewayIp, Integer gatewayPort, String deviceId);


}
