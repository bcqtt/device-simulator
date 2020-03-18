package com.eg.egsc.scp.simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.eg.egsc.scp.simulator.util.FileUtils;

public class LogsFilterTest {
	
	
	final String IP_REGEX = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
	
	@Test
	public void logsFilterTest() throws IOException {
		
		List<File> fileList = FileUtils.getFileList("C:\\Users\\122879520\\Desktop\\logs",new ArrayList<File>());
		Set<String> set = new HashSet<>();
		for(File file : fileList) {
			System.out.println("--------------[" + file.getAbsolutePath() + "]---------------------");
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String line;
			while ((line = reader.readLine()) != null) {
				ipFilter(line,set);
			}
			reader.close();
		}
		
		StringBuilder sb = new StringBuilder();
		for(String ip : set) {
			if(ip.contains("100.")) {
				System.out.println("ip: " + ip);
				sb.append(ip + ",");
			}
		}
		System.out.println(sb.toString());
	}

	private void ipFilter(String line,Set<String> set) {
		if(line.contains("与网关建立tcp通讯成功")) {
			Pattern pattern = Pattern.compile(IP_REGEX);
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String ip = matcher.group(0);
				set.add(ip);
			}
		}
		
	}

}
