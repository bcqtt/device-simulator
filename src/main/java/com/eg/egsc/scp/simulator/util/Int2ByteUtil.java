/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.util;

/**
 * int和byte数组互相转换工具类.
 * 
 * @Author zhangweixian
 * @since 2018年1月8日
 */
public class Int2ByteUtil {

  private Int2ByteUtil() {}

  /**
   * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序.
   * 
   * @param value 要转换的int值
   * @return byte数组
   */
  public static byte[] intTo4Bytes(int value) {
    byte[] targets = new byte[4];
    targets[3] = (byte) (value & 0xFF);
    targets[2] = (byte) (value >> 8 & 0xFF);
    targets[1] = (byte) (value >> 16 & 0xFF);
    targets[0] = (byte) (value >> 24 & 0xFF);
    return targets;
  }


  /**
   * 将int数值转换为占2个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序.
   */
  public static byte[] intTo2Bytes(int value) {
    byte[] targets = new byte[2];
    targets[0] = (byte) (value >> 8 & 0xFF);
    targets[1] = (byte) (value & 0xFF);
    return targets;
  }


  /**
   * int整数转一个字节byte数组.
   * 
   * @Methods Name intTo1Bytes
   * @Create In 2017年12月19日 By zhangweixian
   * @param value required
   * @return byte[]
   */
  public static byte[] intTo1Bytes(int value) {
    byte[] targets = new byte[1];
    targets[0] = (byte) (value & 0xFF);
    return targets;
  }


  /**
   * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用.
   * 
   * @param bytes 数组
   * @param off 从数组的第offset位开始
   * @return int数值
   */
  public static int byte4ToInt(byte[] bytes, int off) {
    int b0 = bytes[off] & 0xFF;
    int b1 = bytes[off + 1] & 0xFF;
    int b2 = bytes[off + 2] & 0xFF;
    int b3 = bytes[off + 3] & 0xFF;
    return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
  }


  /**
   * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序.
   */
  public static int byte2ToInt(byte[] bytes, int off) {
    int high = bytes[off];
    int low = bytes[off + 1];
    return (high << 8 & 0xFF00) | (low & 0xFF);
  }
}
