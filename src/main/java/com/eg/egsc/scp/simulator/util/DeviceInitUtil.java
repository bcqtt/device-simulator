package com.eg.egsc.scp.simulator.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eg.egsc.scp.simulator.component.LocalStore;
import com.google.common.collect.Lists;

/**
 * 从文件初始化设备列表
 * @author 122879520
 *
 */
public class DeviceInitUtil {
	public static void deviceInit(String filePath) throws IOException {
		
		InputStream stream = new FileInputStream(filePath);
		@SuppressWarnings("resource")
		XSSFWorkbook excel = new XSSFWorkbook(stream); //工作簿
		XSSFSheet sheet = excel.getSheetAt(0);   //工作表
		List<Integer> portList = getAvailableTcpPort();
		List<String> idList = Lists.newArrayList();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			XSSFRow row = sheet.getRow(rowNum);
			 if (row == null) continue;
			 XSSFCell cell0 = row.getCell(0);
			 if (cell0 == null) continue;
			 String deviceId = cell0.getStringCellValue();
			 LocalStore.getInstance().getDeviceMap().put(deviceId, portList.get(rowNum-1));
			 idList.add(deviceId);
		}
	}

	/**
	 * 写文件
	 * @param filePath
	 * @throws IOException
	 */
	public static void writeDeviceInfo(String filePath, Integer deviceCount) throws IOException {
		Map<String,List<String>> deviceMap = createDevice(deviceCount);

		InputStream inputStream = new FileInputStream(filePath);
		@SuppressWarnings("resource")
		XSSFWorkbook excel = new XSSFWorkbook(inputStream); //工作簿
		XSSFSheet sheet0 = excel.getSheetAt(0);   //工作表
		XSSFSheet sheet1 = excel.getSheetAt(1);   //工作表
		XSSFSheet sheet2 = excel.getSheetAt(2);   //工作表

		int rowNum = 0;
		String[] col2 = {"chargeType","parkingNo","ntpServer","elecLoop","phaseLine"};
		String[] col3 = {"1","7878787878","10.101.70.239:123","回路001","相线01"};

		for(int i=0; i<deviceMap.get("DEVICE_ID_LIST").size(); i++){
			//写工作表1
			XSSFRow sheet0Row = sheet0.createRow(i+1);
			XSSFCell cellA = sheet0Row.createCell(0);
			XSSFCell cellB = sheet0Row.createCell(1);
			XSSFCell cellC = sheet0Row.createCell(2);
			XSSFCell cellD = sheet0Row.createCell(3);
			XSSFCell cellF = sheet0Row.createCell(5);
			XSSFCell cellG = sheet0Row.createCell(6);
			XSSFCell cellI = sheet0Row.createCell(8);
			XSSFCell cellJ = sheet0Row.createCell(9);
			XSSFCell cellK = sheet0Row.createCell(10);
			XSSFCell cellL = sheet0Row.createCell(11);
			XSSFCell cellM = sheet0Row.createCell(12);
			XSSFCell cellN = sheet0Row.createCell(13);
			XSSFCell cellQ = sheet0Row.createCell(16);
			XSSFCell cellT = sheet0Row.createCell(19);
			XSSFCell cellU = sheet0Row.createCell(20);
			cellA.setCellValue(deviceMap.get("DEVICE_ID_LIST").get(i));
			cellB.setCellValue("【模拟】插座数据" + i);
			cellC.setCellValue("测试数据");
			cellD.setCellValue(2025);
			cellF.setCellValue("航天28楼");
			cellG.setCellValue("892a21cc4ab3456188f2532864902c1a");
			cellI.setCellValue("DM01");
			cellJ.setCellValue("1008");
			cellK.setCellValue("255.255.255.0");
			cellL.setCellValue(deviceMap.get("DEVICE_MAC_LIST").get(i));
			cellM.setCellValue(deviceMap.get("DEVICE_IP_LIST").get(i));
			cellN.setCellValue(2011);
			cellQ.setCellValue(4);
			cellT.setCellValue("2.0");
			cellU.setCellValue("V1.0");

			//写工作表2
			for(int r=0; r<5; r++) {
				rowNum++;
				XSSFRow sheet1Row = sheet1.createRow(rowNum);
				XSSFCell cell1 = sheet1Row.createCell(0);
				XSSFCell cell2 = sheet1Row.createCell(1);
				XSSFCell cell3 = sheet1Row.createCell(2);
				cell1.setCellValue(deviceMap.get("DEVICE_MAC_LIST").get(i));
				cell2.setCellValue(col2[r]);
				cell3.setCellValue(col3[r]);
			}

			//写工作表3
			XSSFRow sheet2Row = sheet2.createRow(i+1);
			XSSFCell cell1 = sheet2Row.createCell(0);
			XSSFCell cell2 = sheet2Row.createCell(1);
			cell1.setCellValue(deviceMap.get("DEVICE_ID_LIST").get(i));
			cell2.setCellValue("10082026659847886699");
		}

		try {
			OutputStream outputStream = new FileOutputStream(filePath);
			excel.write(outputStream);
			//关闭流
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("写完毕");
	}

	/**
	 * 获取可用的tcp端口号
	 * @return
	 */
	public static List<Integer> getAvailableTcpPort() {
		List<Integer> l = Lists.newArrayList();
		// 指定范围10000到65535
		for (int i = 10000; i <= 65535; i++) {
			try {
				new ServerSocket(i).close();
				l.add(i);
			} catch (IOException e) { // 抛出异常表示不可以，则进行下一个
				continue;
			}
		}
		return l;
	}

	/**
	 * 生成设备
	 * @param deviceCount 设备数量
	 * @return
	 */
	public static Map<String,List<String>> createDevice(Integer deviceCount){

		List<String> deviceIdList = Lists.newArrayList();
		List<String> macList = Lists.newArrayList();
		List<String> ipList = Lists.newArrayList();

		StringBuffer sb =new StringBuffer("10082025111000000000");
		Integer[] ipEle = {0,0,0,0};
		for(int i=1; i <= deviceCount; i++) {
			String s = "" + i;
			sb.replace(sb.length()-s.length(), sb.length(), s);

			String deviceId = sb.toString();
			deviceIdList.add(deviceId);

			String mac = deviceId.substring(8);
			String[] macValue = new String[6];
			int startindex = 0;
			for(int j=0; j<6 ; j++) {
				macValue[j] = mac.substring(startindex, startindex+2);
				startindex = startindex+2;
			}
			List<String> list = Arrays.asList(macValue);
			String MAC = org.apache.commons.lang3.StringUtils.join(list.toArray(),"-");
			macList.add(MAC);

			//虚拟IP生成
			ipEle[0]++;
			if(ipEle[0] == 255){
				ipEle[0] = 0 ;
				ipEle[1]++ ;
			}
			if(ipEle[1] == 255){
				ipEle[0] = 0 ;
				ipEle[1] = 0 ;
				ipEle[2]++ ;
			}
			if(ipEle[2] == 255){
				ipEle[0] = 0 ;
				ipEle[1] = 0 ;
				ipEle[2] = 0 ;
				ipEle[3]++ ;
			}
			if(ipEle[3] == 255){
				break;
			}
			List<Integer> intList = Arrays.asList(ipEle);
			String IP = org.apache.commons.lang3.StringUtils.join(intList.toArray(),".");
			ipList.add(IP);

			//System.out.println(deviceId + "	" + MAC + "	" + IP);
		}

		deviceIdList.add(0,"10082025D0B60AB00384");
		macList.add(0,"D0:B6:0A:B0:03:84");
		ipList.add(0,"89:89:89:89");

		Map<String,List<String>> deviceMap = Maps.newHashMap();
		deviceMap.put("DEVICE_ID_LIST",deviceIdList);
		deviceMap.put("DEVICE_MAC_LIST",macList);
		deviceMap.put("DEVICE_IP_LIST",ipList);

		return deviceMap;
	}
}
