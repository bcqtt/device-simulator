package com.eg.egsc.scp.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DeviceSimulatorApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DeviceSimulatorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DeviceSimulatorApplication.class, args);
	}
}
