package com.eg.egsc.scp.simulator;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.ByteUtils;
import com.eg.egsc.scp.simulator.util.Keys;
import com.eg.egsc.scp.simulator.util.RSAUtils;
import com.eg.egsc.scp.simulator.util.StringUtils;

public class TestEncrypt {
	
	@Test
	public void testByte() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] b = {0x03,0x00};   //十进制数是1280
		
		String bN = new BigInteger(1, b).toString(10);
		short bNn = Short.parseShort(bN);
		
		short sho = ByteUtils.byteToShortBigend(b);
		byte[] dataByte = ByteUtils.short2ByteBigend(bNn);
		//System.out.println(ByteUtils.byteToBinaryString(dataByte[0]));
		
		
		String data1 = "{\"Command\":\"COM_DEV_REGISTER\",\"Data\":[{}],\"Result\":0}";
		String data2 = "{\"Command\":\"COM_QUERY_DIR\"}";
		String data3 = "{\"Command\":\"COM_SETTING_PARAMETERS\",\"Data\":[{\"fileServerUrl\":\"\",\"ntpServer\":\"\"}]}";
		String data4 = "{\"Command\":\"COM_DEV_REGISTER\",\"Data\":[{}],\"Result\":0}";
//		byte[] aesEncrypt1 = AESUtils.encrypt(data1, Constant.AESKEY_VALUE);
//		byte[] aesEncrypt2 = AESUtils.encrypt(data2, Constant.AESKEY_VALUE);
//		byte[] aesEncrypt3 = AESUtils.encrypt(data3, Constant.AESKEY_VALUE);
//		byte[] aesEncrypt4 = AESUtils.encrypt(data1, "lQh53vI2X534W006");
		byte[] aesEncrypt5 = AESUtils.encrypt(data4, "HacSo2a06ID7cnc3");
		String hexStr = AESUtils.parseByte2HexStr(aesEncrypt5);
		System.out.println(hexStr);
		System.out.println(AESUtils.decrypt(hexStr, "HacSo2a06ID7cnc3"));
	}
	
	@Test
	public void test2() throws Exception {
		String data = "8, 123, -125, 16, -96, -60, 20, 77, 14, 100, -73, 99, 14, 102, 36, 114, 55, 119, -97, -98, -35, 102, -85, -120, 125, 14, 64, -102, -64, 87, 44, 117, 4, 27, -86, 25, 55, 0, -12, -128, -107, 102, 107, 98, 68, 113, 37, 71, -125, 14, -26, 114, 55, -90, -117, -18, 73, -97, -104, -100, 82, 84, -103, -86, -45, 100, 0, -31, 109, -70, -4, 43, 30, 66, 75, 81, 24, 66, 63, 121, 11, 94, 48, 120, 86, -110, 20, 39, 21, -76, -74, -94, 29, 62, -124, 55, -17, 77, -58, 3, -81, 105, -21, -127, 19, -26, -95, -106, -45, 67, -19, 112, -120, 89, -118, 3, 56, -16, -96, -128, 23, -15, -121, -69, 49, 126, -52, -98, 15, 11, 125, 100, -73, 67, 69, 7, 90, -48, -101, -95, 111, 100, -111, 13, 26, 80, 26, 74, -58, -16, 101, -111, -96, 125, 63, -32, -37, -68, 89, -56, 114, -8, -56, 80, 106, 68, -67, 101, -32, 74, -99, -28, 126, 75, 3, -38, 89, 10, -100, 117, -89, 48, -31, -109, 91, 105, 104, 116, 21, -47, -128, 100, 73, -41, 111, 27, 64, 46, -47, -112, -34, -82, -95, 9, -96, -26, -8, 44, 57, 87, 86, -43, 39, 58, -103, -1, 77, -102, 121, 63, 54, 52, 61, -19, -98, 91, 79, -61, 33, -72, -45, -100, -97, -81, 82, -67, 43, -31, 19, -72, 102, -14, 15, -52, 27, 120, -97, 127, -110, -124, -128, 82, 59, -37, -48, -3";
		data = "-116, -80, -110, -71, 52, -43, 112, -120, -24, 114, 91, 45, 119, 59, -59, 77, -122, -114, 110, -71, -8, -122, 81, -60, -94, -100, -36, -86, -113, -26, -104, -72, -96, 46, 50, 106, -113, -26, -38, -55, -70, -26, -111, 127, 70, 104, 98, 100, 35, -25, 0, 28, -61, 74, 77, 44, 71, -1, 12, 120, 80, -45, 86, 48, 32, -5, 39, 57, 48, 9, 102, 103, -114, 64, -10, 20, 28, -28, -105, -98, 57, 72, 80, -26, 37, 122, 126, -92, -111, 70, -29, 117, -106, -78, -57, -65, 108, -64, -9, -73, -41, -119, 96, -54, 34, 46, -60, 60, 68, 53, -86, -74, -49, 38, 8, -70, -128, -67, 119, -34, 75, 47, -52, -89, -126, -25, -20, -11, 73, 35, -16, 113, 24, 45, -87, 116, -32, 84, 94, 118, -34, -98, -109, -107, -116, -10, 80, -15, 21, 44, 23, -39, -113, 78, 15, 78, 99, -62, -38, -111, 118, 92, -117, 54, 81, 92, 86, -3, -37, -32, 68, -20, 48, 23, 38, 99, 75, -56, 13, -128, -87, 58, 27, -35, -127, 44, -21, 24, -53, 84, 94, -112, -109, 21, -9, 81, 46, 87, -90, -95, 14, 4, -22, 96, -81, 68, -46, 109, -117, -18, 93, -1, -86, -37, 97, -21, 120, -91, 33, -30, 22, -97, -36, 109, -59, 4, -40, -21, -75, 77, 75, 22, -116, 55, 84, 70, 59, 96, -76, -24, -8, -63, 15, -81, 26, 40, 105, -70, 9, -73, -104, -72, -14, 87, 98, 60, 8, 6, -47, -16, -15, -39, 55, 4, -34, -117, -123, 54, 9, 50, -7, 13, -113, -25, 90, -128, -81, 105, -71, -43, -44, 68, 71, -92, 125, 43, -68, 93, 126, 106, 114, -101, -85, 6, 17, -37, -112, 28, -3, -71, 97, -101, -32, -49, 43, -57, 94, -77, -47, 99, 121, 102, 34, 47, -86, -88, 9, -26, 12, -115, -42, 109, 64, -20, -20, 127, 45, -75, 45, -25, 124, 31, -82, 78, 121, -99, -13, 32, -101, 34, 31, 55, 3, -21, -95, -52, -31, -45, -76, -64, 41, -1, 34, -5, -30, -79, -24, -68, 14, -59, -79, -35, 105, -113, 69, -32, 123, -99, -104, 14, 48, -121, -23, 90, -102, 2, -18, 77, -72, -111, 119, -116, 104, -88";
		data = "123, 34, 67, 111, 109, 109, 97, 110, 100, 34, 58, 34, 67, 79, 77, 95, 68, 69, 86, 95, 82, 69, 71, 73, 83, 84, 69, 82, 34, 44, 34, 68, 97, 116, 97, 34, 58, 91, 123, 34, 97, 101, 115, 75, 101, 121, 34, 58, 34, 54, 48, 48, 48, 48, 48, 49, 56, 52, 55, 52, 50, 50, 48, 51, 57, 34, 44, 34, 100, 101, 118, 105, 99, 101, 73, 68, 34, 58, 34, 49, 48, 48, 56, 50, 48, 50, 53, 68, 48, 66, 54, 48, 65, 66, 48, 48, 48, 53, 52, 34, 44, 34, 105, 112, 34, 58, 34, 49, 55, 50, 46, 50, 53, 46, 56, 52, 46, 49, 49, 34, 44, 34, 109, 97, 99, 34, 58, 34, 57, 48, 58, 102, 48, 58, 53, 50, 58, 48, 55, 58, 100, 56, 58, 102, 51, 34, 44, 34, 109, 97, 99, 78, 79, 34, 58, 48, 44, 34, 109, 97, 110, 117, 102, 97, 99, 116, 117, 114, 101, 114, 34, 58, 34, -26, -127, -110, -27, -92, -89, 34, 44, 34, 109, 97, 115, 107, 34, 58, 34, 50, 53, 53, 46, 50, 53";
		String[] dataStrs = data.split(",");
		byte[] dataByte = new byte[dataStrs.length];
		for(int i=0;i<dataStrs.length;i++) {
			dataByte[i] = (byte) Integer.parseInt(dataStrs[i].trim());
//			dataByte[i] = (byte)Integer.parseInt(dataStrs[i].trim(), 16);
		}
		
		System.out.println("数组长度：" + dataByte.length);
		String dataStr = new String(dataByte, "UTF-8");
		System.out.println(dataStr);
		
		byte[] decryptByte = RSAUtils.decryptByPrivateKey2(dataByte, Keys.SERVER_PRIVATE_KEY);
		
		System.out.println(new String(decryptByte,"UTF-8"));
		
		
	}
	
	@Test
	public void test3() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String key = "2025080021222302";   //10082025D0B60AB0005E
		String aesStr = "7644781296AEF7E26977B7C5ED86C63133450DF7E009F2EC534BE7BCA81B9E50E2774EAD2ABCDEA92EE3055F853BC8F8E72ADE7E92E2964681AFEF89C94530CE";
		key = "2025D0B60AB0005E";
		aesStr = "0880F96E1E42040ADAF41F84938D6A0838A528F5162FEE10D8A77CD8FB62351FBF68F235142D9F6633B0CEFD63078D6C0FC5AF088C27FA3DE4C1B72A5C6393778FFA7198FA7F1030B53A250C1E35383D";
		key = "1wYYI13N5Iy0lr6f";
		aesStr = "7B22436F6D6D616E64223A22434F4D5F4445565F5245474953544552222C224572726F72436F6465223A312C224572726F724D657373616765223A22E8AEBEE5A487E4B88DE694AFE68C81222C22526573756C74223A317D";
		byte[] data = AESUtils.decrypt(aesStr, key);
		System.out.println(new String(data,"UTF-8"));
		
	}
	
	@Test
	public void test4() {
		String addr = "/100.120.151.111:65179";
		String ip = addr.substring(1,addr.indexOf(":"));
		System.out.println(ip);
	}
	
	@Test
	public void test5() {
		String uuid16 = StringUtils.getUUID16();
		System.out.println(uuid16);
	}
	
}
