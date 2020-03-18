package com.eg.egsc.scp.simulator.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

import com.eg.egsc.scp.simulator.common.Constant;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

public class LocalStore {
	
	private static volatile LocalStore instance;
	
	@Setter @Getter
	private Map<String,Object> map = new HashMap<>();
	
	@Setter @Getter
	private Map<String,ChannelHandlerContext> ctxMap = new ConcurrentHashMap<>();
	
	@Setter @Getter
	private List<String> deviceIdList = new ArrayList<>();
	
	@Setter @Getter
	private Map<String,AtomicInteger> counterMap = new HashMap<>();	
	
	@Setter @Getter
	private int localPort = Constant.LOCAL_PORT;
	
	@Setter @Getter
	private Map<String,Integer> deviceMap = new HashMap<>(); //	<deviceId,port>
	
	@Setter @Getter
	private Map<String,Integer> meterMap = new HashMap<>(); //	<deviceId,port>

	@Setter @Getter
	private Map<String,String> orderMap = new HashMap<>(); //	<deviceId,orderNo>
	@Setter @Getter
	private Map<String,String> startTimeMap = new HashMap<>(); //	<deviceId,startTime>

	@Setter @Getter
	private volatile Map<String,ScheduledFuture<?>> scheduledMap = new HashMap<>();;

	public void initDevice() {
//		deviceMap.put("10082025100000000349", 29080);  //测试环境
//		deviceMap.put("10082025100000000107", 29081);  //测试环境
//		deviceMap.put("10082025D0B60AB0005C", 29091);
//		deviceMap.put("10082025D0B60AB0001A", 29092);
//		deviceMap.put("10082026D0B60AB0005E", 29093);
//		deviceMap.put("10082026D0B60AB0005D", 29094);
//		deviceMap.put("10082026080000007896", 29095);
//		deviceMap.put("10082025D0B60AB00008", 29096);
//		deviceMap.put("10082025080021222301", 29097);
//		deviceMap.put("10082025080000007802", 29098);
		deviceMap.put("10082025D0B60AB0097C", 29099);
		deviceMap.put("10082025D0B60AB0097B", 29100);
		deviceMap.put("10082025D0B60AB00000", 29101);
		
//		meterMap.put("100820268CEC4B419DB3", 29102);
//		meterMap.put("1008202648EDA938A582", 29103);
//		meterMap.put("1001202648EDAA373D2E", 29104);
//		meterMap.put("10082026000102030405", 29105);
//		meterMap.put("10082026000102030303", 29106);
	}
	
	private LocalStore() {}
	
	public static LocalStore getInstance() {
		if (instance == null) {
            synchronized (LocalStore.class) {
                if (instance == null) {
                	instance = new LocalStore();
                	instance.initDevice();
                }
            }
        }
        return instance;
	}

	public void addCtx(String key, ChannelHandlerContext ctx) {
		ctxMap.put(key, ctx);
	}

	public void addDeviceId(String deviceId) {
		deviceIdList.add(deviceId);
	}

	public int getCounter(String deviceId) {
		if(counterMap.containsKey(deviceId)) {
			return counterMap.get(deviceId).get();
		}else {
			counterMap.put(deviceId, new AtomicInteger(1));
		}
		return 1;
	}

	public String getDeviceId(String localAddr) {
		int port = Integer.parseInt(localAddr.substring(localAddr.indexOf(":")+1));
		for(Map.Entry<String,Integer> entry : deviceMap.entrySet()) {
			if(entry.getValue() == port) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public String getDeviceIdOfMeter(String localAddr) {
		int port = Integer.parseInt(localAddr.substring(localAddr.indexOf(":")+1));
		for(Map.Entry<String,Integer> entry : meterMap.entrySet()) {
			if(entry.getValue() == port) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 结束充电，并且移除schedule
	 * @param deviceCode
	 */
	public void stopCharge(String deviceCode) {
		this.getScheduledMap().get(deviceCode).cancel(true);
		this.getScheduledMap().remove(deviceCode);
	}


}
