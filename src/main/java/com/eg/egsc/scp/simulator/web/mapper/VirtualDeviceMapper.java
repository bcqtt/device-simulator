package com.eg.egsc.scp.simulator.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.eg.egsc.scp.simulator.web.mapper.sql.DynamicSqlProvider;
import com.eg.egsc.scp.simulator.web.pojo.VirtualDevice;

@Mapper
public interface VirtualDeviceMapper {
	
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
	
	@Select("select count(1) from virtual_device")
	public int queryCount();
	
	@Select("select max(device_port)+1 as new_device_port from virtual_device")
	public int queryNewPort();
	
	@Insert("insert into virtual_device values(#{id},#{deviceId},#{deviceType},"
			+ "#{deviceName},#{deviceIp}, #{devicePort},#{deviceMac},#{deviceMark},#{version},#{createTime},"
			+ "#{deviceStatus},#{environment},#{gatewayPort})")
	public int insert(VirtualDevice device);
	
	@Select("select * from virtual_device order by create_time desc")
	public List<VirtualDevice> queryAll();

	@Select("select * from virtual_device where id=#{deviceId}")
	public VirtualDevice queryDeviceById(String deviceId);

	@UpdateProvider(type=DynamicSqlProvider.class,method="updateDeiceById")
	public Integer update(VirtualDevice device);
}
