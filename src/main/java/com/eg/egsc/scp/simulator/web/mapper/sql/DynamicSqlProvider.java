package com.eg.egsc.scp.simulator.web.mapper.sql;

import org.apache.ibatis.jdbc.SQL;

import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;

public class DynamicSqlProvider {
	
	/**
	private String id;
	private String deviceId;
	private int deviceType;
	private String deviceName;
	private String deviceIp;
	private int devicePort;
	private String deviceMac;
	private String deviceMark;
	private String version;
	private Date createTime;
	private int deviceStatus;//设备状态 0:离线   1:在线
	 */
	
	public String updateDeiceById(VirtualDevice device) {
		SQL sql = new SQL() {
			{
				UPDATE("virtual_device");
				StringBuilder setParam = new StringBuilder();
				if(device.getEnvironment()!=null) {
					setParam.append("environment=#{environment},");
				}
				if(device.getGatewayPort()!=0) {
					setParam.append("gateway_port=#{gatewayPort},");
				}
				if(device.getDeviceType()!=0) {
					setParam.append("device_type=#{deviceType},");
				}
				if(device.getDeviceName()!=null) {
					setParam.append("device_name=#{deviceName},");
				}
				if(device.getDeviceIp()!=null) {
					setParam.append("device_ip=#{deviceIp},");
				}
				if(device.getDevicePort()!=0) {
					setParam.append("device_port=#{devicePort},");
				}
				if(device.getDeviceMac()!=null) {
					setParam.append("device_mac=#{deviceMac},");
				}
				if(device.getDeviceMark()!=null) {
					setParam.append("device_mark=#{deviceMark},");
				}
				if(device.getVersion()!=null) {
					setParam.append("version=#{version},");
				}
				setParam.append("device_status=#{deviceStatus}");
				SET(setParam.toString());
				WHERE("id=#{id}");
			}
		};
		return sql.toString();
	}
}
