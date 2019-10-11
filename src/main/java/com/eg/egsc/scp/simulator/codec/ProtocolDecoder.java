package com.eg.egsc.scp.simulator.codec;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eg.egsc.scp.simulator.common.Constant;
import com.eg.egsc.scp.simulator.component.LocalStore;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.dto.ProtocolHeader;
import com.eg.egsc.scp.simulator.util.AESUtils;
import com.eg.egsc.scp.simulator.util.DateUtils;
import com.eg.egsc.scp.simulator.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 轻量级网关解码类.
 *
 * @Author wanyuanming
 * @since 2018年1月8日
 */
//@Component
public class ProtocolDecoder extends ByteToMessageDecoder {
  //private final Logger logger = LoggerFactory.getLogger(ProtocolDecoder.class);
  private static final Log log = LogFactory.getLog(ProtocolDecoder.class);
  // 存储分包的后半截内容数据
  private Map<String, Object> map = new HashMap<>();

  /**
   * 默认头部长度.
   */
  public static final int BASE_LENGTH = 57;
  public static final int READ_LENGTH = 512;

  /**
   * 重写解码方法.
   *
   * @Methods decode
   * @Create In 2017年12月20日 By zhangweixian
   * @param ctx required
   * @param buffer required
   * @param out required
   */
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out)
      throws Exception {
//    long startDate = System.currentTimeMillis();
//    long startTime = System.nanoTime();
    // 分包
    if (!MapUtils.isEmpty(map)) {
      ProtocolBody body = (ProtocolBody) map.get(Constant.PROTOCOL);
      Integer bufferLength = (Integer) map.get(Constant.PROTOCOL_READLENGTH);
      ProtocolHeader header = body.getProtocolHeader();
      // 剩余长度
      Integer residueLength = header.getDataLength() - bufferLength;
      // 剩余读取总长度
      Integer residueTotalLength = buffer.writerIndex() - buffer.readerIndex();
      // 当前要读取的长度大于固定值就取固定值值,否则取剩余总长度.
      Integer readLength = residueTotalLength > READ_LENGTH ? READ_LENGTH : residueTotalLength;
      // 如果读取长度大于协议剩余长度，取协议剩余长度
      readLength = readLength > residueLength ? residueLength : readLength;

      byte[] srcbytes = new byte[readLength];
      buffer.readBytes(srcbytes);
      byte[] destByte = new byte[body.getDataBytes().length + srcbytes.length];
      System.arraycopy(body.getDataBytes(), 0, destByte, 0, body.getDataBytes().length);
      System.arraycopy(srcbytes, 0, destByte, body.getDataBytes().length, srcbytes.length);
      body.setDataByte(destByte);
      if (readLength + bufferLength < header.getDataLength()) {
        // 缓存协议体
        map.put(Constant.PROTOCOL, body);
        map.put(Constant.PROTOCOL_READLENGTH, readLength + bufferLength);
//        long busEnd = System.nanoTime();
//        logger.info(PerformanceTest.buildLogContent(startDate, startTime, busEnd, "1.解码器"));
        return;
      }
      body.setDataByte(destByte);
      out.add(body);
      map.clear();
      printDebugMsg(ctx, body);
    }

    // 判断buffer是否有字节可读 并且写索引小于最大容量
    if (buffer.isReadable() && buffer.isWritable() && buffer.readableBytes() >= BASE_LENGTH) {
      // 组装协议头
      ProtocolHeader header = assembleHeader(buffer);
      String packageNo = header.getPackageNo();
      
      // 组装协议体
      ProtocolBody body = new ProtocolBody();
      body.setProtocolHeader(header);
      // 剩余读取总长度
      Integer residueTotalLength = buffer.writerIndex() - buffer.readerIndex();
      // 当前要读取的长度大于固定值就取固定值值,否则取剩余总长度.
      Integer readLength = residueTotalLength > READ_LENGTH ? READ_LENGTH : residueTotalLength;
      // 如果读取长度大于协议剩余长度，取协议剩余长度
      readLength = readLength > header.getDataLength() ? header.getDataLength() : readLength;

      byte[] contentBytes = new byte[readLength];
      buffer.readBytes(contentBytes);
      body.setDataByte(contentBytes);
      if (readLength < header.getDataLength()) {
        // 缓存协议体
        map.put(Constant.PROTOCOL, body);
        map.put(Constant.PROTOCOL_READLENGTH, readLength);
        return;
      }
      
      //String jsonDataStr = new String(contentBytes,"UTF-8");
      //JSONObject obj = JSONObject.parseObject(jsonDataStr);
      //String command = obj.getString("Command");
      //LocalStore.getInstance().getMap().put("DEVICE:MEG:PACKAGENO:" + command, packageNo);
      body.setDataByte(contentBytes);
      out.add(body);
      printDebugMsg(ctx, body);
    }
  }

  private void printDebugMsg(ChannelHandlerContext ctx, ProtocolBody body) {
    if (log.isDebugEnabled()) {
      String allBytesHexStr = AESUtils.parseByte2HexStr(body.getData().getBytes());
      log.info(String.format("网关读取客户端(%s)上传的数据十六进制:%s", ctx.channel().remoteAddress(), allBytesHexStr));
    }
  }

  /**
   * 组装协议头,读取前面57个.
   *
   * @param buffer 字节缓冲
   * @param header void
   */
  private ProtocolHeader assembleHeader(ByteBuf buffer) {
    ProtocolHeader header = new ProtocolHeader();
    String version = parseString(buffer, 4);
    String srcID = parseString(buffer, 20);
    String destID = parseString(buffer, 20);
    String request = parseNumber(buffer, 1);
    String packNo = parseNumber(buffer, 4);
    String contentLength = parseNumber(buffer, 4);
    short hold = Short.parseShort(parseNumber(buffer, 2));
    String crc = parseNumber(buffer, 2);
    header.setVersion(version);
    header.setSrcID(srcID);
    header.setDestId(destID);
    header.setDataLength(Integer.parseInt(contentLength));
    header.setCrc16(crc);
    header.setHold(hold);
    header.setRequestFlag(request);
    header.setPackageNo(packNo);
    return header;
  }

  /**
   * byte数组转成字符串.
   *
   * @Methods Name parseString
   * @Create In 2017年12月20日 By zhangweixian
   * @param buffer required
   * @param length required
   * @return String
   */
  private String parseString(ByteBuf buffer, int length) {
    byte[] bytes = new byte[length];
    String str = null;
    try {
      buffer.readBytes(bytes);
      str = new String(bytes, Constant.ENCODING);
    } catch (Exception e) {
      log.debug("解码器转换字符串发生异常:" + e.getMessage());
      return null;
    }
    return str;
  }


  /**
   * byte数组转成10进制数字字符串.
   *
   * @Methods Name parseNumber
   * @Create In 2017年12月20日 By zhangweixian
   * @param buffer required
   * @param length required
   * @return String
   */
  private String parseNumber(ByteBuf buffer, int length) {
    byte[] bytes = new byte[length];
    buffer.readBytes(bytes);
    String result = null;
    try {
      result = StringUtils.binary(bytes, 10);
    } catch (Exception e) {
      log.debug("时间为" + DateUtils.formatDate2(new Date()) + "解码器转换数字异常:" + e.getMessage());
      return null;
    }
    return result;
  }

}
