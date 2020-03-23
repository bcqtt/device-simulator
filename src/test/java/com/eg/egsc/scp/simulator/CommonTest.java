package com.eg.egsc.scp.simulator;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import com.eg.egsc.scp.simulator.component.ComputerInfo;
import com.eg.egsc.scp.simulator.component.DeviceEnv;
import com.eg.egsc.scp.simulator.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class CommonTest {

	@Test
	public void qrcodeTest() throws WriterException, IOException {
		String content = "http://www.evergrande.com/";
		int qrcode_width = 400;
		int qrcode_height = 400;

		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcode_width,
				qrcode_height, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}

		File outputfile = new File("d:/data/qrcode.png");
		ImageIO.write(image, "png", outputfile);
		System.out.println("保存成功");
	}

	@Test
	public void randomMac4Qemu() {
		Random random = new Random();
		String[] mac = { String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
				String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
				String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)) };
		System.out.println(String.join(":", mac));
	}

	@Test
	public void testStringToBCD() {
		String address = "AAAAAAAAAAAA";
		address = "48EDA938A582";
		byte[] addbyte = StringUtils.strToBCDBytes(address);
		System.out.println(addbyte);
		System.out.println(StringUtils.bcdToString(addbyte));

		String num = "000257";   //32, 71, 118
		byte[] bcdcode = ByteUtils.str2Bcd(num);
		System.out.println(bcdcode);
		System.out.println(ByteUtils.bcd2Str(bcdcode));
	}

	@Test
	public void testIntToBinaryString() {
		String str = ByteUtils.intToBinaryString(0x43);
		System.out.println(str);
	}

	@Test
	public void dataType() {
		String str = "12121211";
		byte[] decode = DatatypeConverter.parseHexBinary(str);
		for(byte b : decode) {
			System.out.print(b+" ");
		}
		byte[] encode = {0x23,0x45,0x23,0x54,0x12};
		String str1 = DatatypeConverter.printHexBinary(encode);
		System.out.println();
		System.out.println(str1);
	}
	
	
	@Test
	public void testIntTo4Bytes() {
		byte[] result = Int2ByteUtil.intTo4Bytes(123);
		
		int num =  ByteUtils.bytesToInt2(result, 0);
		System.out.println(num);
		
		byte[] ip = ByteUtils.strNumberToByte("172.25.83.231");
		byte[] version = ByteUtils.strNumberToByte("0.0.1");
		System.out.println(result);
		
		byte[] b = ByteUtils.binStrToByteArr("10000001");
		System.out.println(b);
	}
	
	
	  /**
	   * 转换BCD码表示的电流数值 AABBCC.DD (千瓦时)
	   * @param btNum 4个字节： DDCCBBAA
	   * @return float BCD码表示的度数：AABBCC.DD (kWh)
	   */
	@Test
	public void testCopy() {
		byte[] btNum = {(byte)0x44,(byte)0x33,(byte)0x22,(byte)0x11};
		
		System.out.println(ByteUtils.bcd2Str(btNum));
		
		int current = 0;
	    current += ((btNum[3]&0xF0) >> 4)*100000;
	    current += (btNum[3]&0x0F) * 10000;
	    current += ((btNum[2]&0xF0) >> 4) * 1000;
	    current += (btNum[2]&0x0F) * 100;
	    current += ((btNum[1]&0xF0) >> 4) * 10;
	    current += (btNum[1]&0x0F) * 1;
	    String currentStr = String.valueOf(current);
	    currentStr += ".";
	    currentStr += low4BitsToBCDStr((byte)( (btNum[0]&0xF0 )>>4) );
	    currentStr += low4BitsToBCDStr((byte)( btNum[0]&0x0F ));
	    System.out.println( currentStr );
		
	}
	
	private static String low4BitsToBCDStr(byte bt) {
	    bt = (byte)(bt & 0x0F);
	    switch (bt) {
	      case 0:
	        return "0";
	      case 1:
	        return "1";
	      case 2:
	        return "2";
	      case 3:
	        return "3";
	      case 4:
	        return "4";
	      case 5:
	        return "5";
	      case 6:
	        return "6";
	      case 7:
	        return "7";
	      case 8:
	        return "8";
	      case 9:
	        return "9";
	      default:
	        break;
	    }
	    return "";
	  }	
	
	@Test
	public void poiTest() {
		try {
			DeviceInitUtil.deviceInit("D:/template5-DEV.xlsm");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateDeviceInfo() throws IOException {
		DeviceInitUtil.writeDeviceInfo("D:/template5-UAT-202003.xlsm",2);
	}

	@Test
	public void testComputerInfo() throws IOException {
//		System.out.println(ComputerInfo.getComputerID());
		System.out.println(ComputerInfo.getMacAddress());
		System.out.println(DeviceEnv.getLocalMac());
	}

	@Test
	public void testIp() {
		int count = 100000;
		Set set = new HashSet();
		for (int i = 0; i < count; i++) {
			String randomIp = StringUtils.getRandomIp();
			set.add(randomIp);
		}
		set.forEach(s -> {
			System.err.println(s);
		});

	}


}
