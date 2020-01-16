package com.eg.egsc.scp.simulator.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.util.DeviceInitUtil;

/**
 * Springboot启动之后执行
 * Order(1)：按序号先后执行
 */
@Component
@Order(1)
public class SimulatorInitializer implements ApplicationRunner {
	
	private static final Logger log = LoggerFactory.getLogger(SimulatorInitializer.class);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("SpringBoot启动之后，初始化设备信息...");
//		DeviceInitUtil.deviceInit("D:/template5-DEV.xlsm");
//		DeviceInitUtil.deviceInit("D:/template5-TEST.xlsm");
//		DeviceInitUtil.deviceInit("D:/template5-UAT-20200115(1-1000).xlsm");
		DeviceInitUtil.deviceInit("/home/appdeploy/test/template5-UAT-20200115(1-1000).xlsm");
//		DeviceInitUtil.deviceInit("/home/appdeploy/test/template5-UAT-20200115(1001-2000).xlsm");
		log.info("初始化设备信息完毕,一共模拟{}个设备",LocalStore.getInstance().getDeviceMap().size());
	}

}
