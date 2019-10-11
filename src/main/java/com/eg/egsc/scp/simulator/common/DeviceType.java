package com.eg.egsc.scp.simulator.common;

import org.apache.commons.lang3.StringUtils;

public enum DeviceType {
  PARK("车场", 2005), 
  DOOR("门禁", 2009), 
  LIFT_CONTROL("梯控", 2016), 
  PATROL("巡更", 2017), 
  INFO_SCREEN("信息发布屏", 2018), 
  MANUFACTURER_CONTROL("电梯厂商控制器",2020), 
  ELETRIC_FENCE("电子围栏", 2021), 
  ELECTRIC_CARPORT_CONTROLLER("电子车位控制器", 2023),
  SMART_CHARGE("智慧充电", 2025),
  SMART_METER("智能电表", 2026);

  private String name;// 设备名称
  private Integer code;// 设备编码（根据设备管理提供的设备分类表中的值设定）

  private DeviceType(String name, Integer code) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public Integer getCode() {
    return code;
  }


  /**
   * 通过编号拿到枚举 String.
   * 
   * @param code required
   * @return
   */
  public static DeviceType getEnumByCode(String code) {
    // 如果传入的设备编码为空，则直接返回
    if (StringUtils.isEmpty(code)) {
      return null;
    }
    DeviceType[] types = DeviceType.values();// 得到枚举值数组
    // 遍历枚举
    for (DeviceType temp : types) {
      // 判断传入的编号和枚举的编号是否相等
      if (StringUtils.trim(code).equals(temp.getCode().toString())) {
        return temp;
      }
    }
    return null;
  }
}
