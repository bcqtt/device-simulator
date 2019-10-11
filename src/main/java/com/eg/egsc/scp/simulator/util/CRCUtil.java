/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.util;

/**
 * CRC16校验工具类,生成byte数组校验值和对byte数组进行校验对比.
 * 
 * @Author wanyuanming
 * @since 2018年1月8日
 */
public class CRCUtil {
  /**
   * 构造方法.
   */
  private CRCUtil() {}

  /**
   * 获取CRC16校验码的byte数组.
   * @param buf required
   * @return byte[]
   */
  public static byte[] getParamCRC(byte[] buf) {
    int checkCode = 0;
    checkCode = crc16CCITTFalse(buf, buf.length);
    byte[] crcByte = new byte[2];
    crcByte[0] = (byte) ((checkCode >> 8) & 0xff);
    crcByte[1] = (byte) (checkCode & 0xff);
    return crcByte;
  }

  /**
   * 计算CRC校验码，返回CRC码.
   * CRC-16/CCITT-FALSE x16+x12+x5+1 算法 算法信息 Name:CRC-16/CCITT-FAI Width:16 Poly:0x1021 Init:0xFFFF
   * RefIn:False RefOut:False XorOut:0x0000
   *
   * @param bytes 数据源
   * @param length 数据源长度
   * @return
   */
  public static int crc16CCITTFalse(byte[] bytes, int length) {
    int crc = 0xffff; // initial value
    int polynomial = 0x1021; // poly value
    for (int index = 0; index < length; index++) {
      byte b = bytes[index];
      for (int i = 0; i < 8; i++) {
        boolean bit = ((b >> (7 - i) & 1) == 1);
        boolean c15 = ((crc >> 15 & 1) == 1);
        crc <<= 1;
        if (c15 ^ bit) {
          crc ^= polynomial;
        }
      }
    }
    crc &= 0xffff;
    return crc;
  }

  /**
   * CRC校验是否通过.
   * @param srcByte 源数据
   * @param vbyte 验证码字节
   * @return
   */
  public static boolean isPassCRC(byte[] srcByte, byte[] vbyte) {
    // 进行计算，得到CRC校验结果
    int calcCRC = calcCRC(srcByte);
    byte[] bytes = new byte[2];
    bytes[0] = (byte) ((calcCRC >> 8) & 0xff);
    bytes[1] = (byte) (calcCRC & 0xff);
    // 比较
    return bytes[0] == vbyte[0] && bytes[1] == vbyte[1];
  }

  /**
   * 对buf的字节作crc校验，返回校验结果.
   * 
   * @param buf 源数据
   */
  private static int calcCRC(byte[] buf) {
    int end = buf.length;
    // 初始值
    int crc = 0xffff;
    // 多项式
    int polynomial = 0x1021;
    for (int index = 0; index < end; index++) {
      byte b = buf[index];
      for (int i = 0; i < 8; i++) {
        boolean bit = ((b >> (7 - i) & 1) == 1);
        boolean c15 = ((crc >> 15 & 1) == 1);
        crc <<= 1;
        if (c15 ^ bit) {
          crc ^= polynomial;
        }
      }
    }
    crc &= 0xffff;
    return crc;
  }

  /**
   * 16进制字符转成byte.
   */
  public static byte toByte(char c) {
    byte b = (byte) "0123456789ABCDEF".indexOf(c);
    if (b == -1) {
      b = (byte) "0123456789abcdef".indexOf(c);
    }
    return b;
  }

  /**
   * 16进制字符串转成二进制数组.
   * @return byte[]
   */
  public static byte[] hex2Byte(String hex) {
    byte[] arrByte = {};
    if ((hex == null) || ("".equals(hex))) {
      return arrByte;
    }
    int len = hex.length() / 2;
    byte[] result = new byte[len];
    char[] achar = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = ((byte) (toByte(achar[pos]) << 4 | (int) toByte(achar[(pos + 1)])));
    }
    return result;
  }
  
  /**
   * byte[]转换为short
   * @param b
   * @return short
   */
  public static short crc16OfShort(byte[] b) {
	  byte[] crc16 = getParamCRC(b);
      short s = 0;
      short s0 = (short) (crc16[0] & 0xff);// 最低位
      short s1 = (short) (crc16[1] & 0xff);
      s1 <<= 8;
      s = (short) (s0 | s1);
      return s;
  }


}
