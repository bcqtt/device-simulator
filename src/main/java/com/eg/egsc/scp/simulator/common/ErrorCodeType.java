package com.eg.egsc.scp.simulator.common;

public enum ErrorCodeType {

  // 以下参考《...轻量级接入协议...》的9.1异常代码定义 ErrorCodeType
  NO_SURPORT("设备不支持", 1),
  DATA_ERROR("数据错误", 2),
  SUBDEVICE_TIMEOUT("子设备超时未返回", 3),
  CREDENCE_FMT_ERR("凭证格式不符", 4),
  CREDENCE_DATA_GET_FAIL("凭证资料获取失败", 5),
  EXCEED_MAX_CREDENCE_NUM("超出最大凭证数量", 6),
  DEV_INTERNAL_PROC_FAIL("设备内部处理失败", 7),
  CREDENCE_FILE_RESOLVE_FAIL("解析固定凭证文件失败", 8),
  DEV_DISCONNECT_FROM_SEEVER("设备与图片服务器断开",9),
  CHILD_DEV_OFFLINE("子设备不在线", 10),
  DEV_NOT_SUPPORT_THE_INST("设备不支持该指令",11),
  CREDENCE_LOW_QUALITY("凭证质量差",12),
  // NOT_ONLINE与国标网关编码一致为：405
  NOT_ONLINE("设备不在线",405); 
  
  private String name;
  private int code;

  ErrorCodeType(String name, int index) {
    this.name = name;
    this.code = index;
  }
  
  public String getName() {
    return name;
  }

  public int getCode() {
    return code;
  }

}
