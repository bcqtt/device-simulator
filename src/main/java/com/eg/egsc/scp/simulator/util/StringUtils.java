/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

/**
 * 反射工具类.
 * 
 * @Author wanyuanming
 * @since 2018年1月8日
 */
public class StringUtils {
	/**
	 * 构造方法.
	 */
	private StringUtils() {

	}

	/**
	 * 获取UUID包含"_"符号.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @return String
	 */
	public static String getUUIDString() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取32位UUID.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 获取16位随机字符串
	 * @return String
	 */
	public static String getUUID16() {
		int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
	}

	/**
	 * 是否空字符串.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @param string required
	 * @return boolean
	 */
	public static boolean isBlank(String string) {
		return org.apache.commons.lang3.StringUtils.isBlank(string);
	}

	/**
	 * 是否非空字符串.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @param string required
	 * @return boolean
	 */
	public static boolean isNotBlank(String string) {
		return org.apache.commons.lang3.StringUtils.isNotBlank(string);
	}

	/**
	 * 字符串为空判断，null字符也认为是空.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @param string required
	 * @return boolean
	 */
	public static boolean isEmpty(String string) {
		// 如果为null字符串
		if ("null".equalsIgnoreCase(string)) {
			return true;
		}
		return string == null || string.trim().length() == 0;
	}

	/**
	 * 是否非空字符串,null字符认为是空.
	 * 
	 * @Create In 2017年12月13日 By wanyuanming
	 * @param string required
	 * @return boolean
	 */
	public static boolean isNotEmpty(String string) {
		// 如果为null字符串
		if ("null".equalsIgnoreCase(string)) {
			return false;
		}
		return !isEmpty(string);
	}

	/**
	 * 将byte[]转为各种进制的字符串.
	 * 
	 * @param bytes byte[]
	 * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
	 * @return 转换后的字符串
	 */
	public static String binary(byte[] bytes, int radix) {
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
	}

	/**
	 * 进制保留4位.
	 * 
	 * @Methods Name subLast4String
	 * @Create In 2017年12月18日 By zhangweixian
	 * @param str required
	 * @return String
	 */
	public static String subLast4String(String str) {
		// 如果为字符串为空
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		String temp = "000" + str;
		return temp.substring(temp.length() - 4, temp.length());
	}

	/**
	 * 字符串转十六进制.
	 * 
	 * @Methods Name str2HexStr
	 * @Create In 2017年12月19日 By zhangweixian
	 * @param str required
	 * @return String
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		// 遍历字符串对应的byte数组，对应的元素转16进制
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）.
	 * 
	 * @param hexString required
	 * @param bytes     required
	 * @return String
	 */
	public static String decode(String hexString, String bytes) {
		ByteArrayOutputStream hexToStr = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2) {
			hexToStr.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		}
		return new String(hexToStr.toByteArray());
	}

	/**
	 * 可以替换大部分空白字符， 不限于空格\s 可以匹配空格、制表符、换页符等空白字符的其中任意一个.
	 * 
	 * @param str required
	 * @return String
	 */
	public static String replaceBlank(String str) {
		// 如果是空值直接返回
		if (isEmpty(str)) {
			return null;
		}
		return str.replaceAll("\\s*", "");
	}

	/**
	 * 返回随机字符串，同时包含数字、大小写字母.
	 * 
	 * @param len 字符串长度，不能小于3
	 * @return String 随机字符串
	 */
	public static String randomStr(int len) {
		if (len < 3) {
			throw new IllegalArgumentException("字符串长度不能小于3");
		}
		// 数组，用于存放随机字符
		char[] chArr = new char[len];
		// 为了保证必须包含数字、大小写字母
		chArr[0] = (char) ('0' + StdRandom.uniform(0, 10));
		chArr[1] = (char) ('A' + StdRandom.uniform(0, 26));
		chArr[2] = (char) ('a' + StdRandom.uniform(0, 26));

		char[] codes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z' };
		// charArr[3..len-1]随机生成codes中的字符
		for (int i = 3; i < len; i++) {
			chArr[i] = codes[StdRandom.uniform(0, codes.length)];
		}

		// 将数组chArr随机排序
		for (int i = 0; i < len; i++) {
			int r = i + StdRandom.uniform(len - i);
			char temp = chArr[i];
			chArr[i] = chArr[r];
			chArr[r] = temp;
		}

		return new String(chArr);
	}

	/**
	 * 将String转成BCD码
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] strToBCDBytes(String s) {

		if (s.length() % 2 != 0) {
			s = "0" + s;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length; i += 2) {
			int high = cs[i] - 48;
			int low = cs[i + 1] - 48;
			baos.write(high << 4 | low);
		}
		return baos.toByteArray();
	}

	/**
	 * 将BCD码转成String
	 * 
	 * @param b
	 * @return
	 */
	public static String bcdToString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return sb.toString();
	}

}
