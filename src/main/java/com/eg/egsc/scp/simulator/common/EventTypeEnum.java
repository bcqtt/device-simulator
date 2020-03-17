/**
 * Copyright 2017-2025 Evergrande Group.
 */

package com.eg.egsc.scp.simulator.common;

import org.apache.commons.lang3.StringUtils;


/**
 * 事件类型枚举.
 *
 * @author lifuping
 * @since 2018年1月11日
 */
public enum EventTypeEnum {

  //门禁事件类型
  FACE_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"人脸开门上报",30000),
  REMOTE_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"远程开门上报",30001),
  BURSH_CARD_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"CPU卡开门上报",30002),
  QR_CODE_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"二维码开门上报",30003),
  FINGER_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"指纹开门上报",30004),
  PASSWORD_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"密码开门上报",30005),
  BUTTON_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"按钮开门上报",30006),
  BLACKLIST_OPEN_DOOR_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"黑名单开门上报",30007),
  FACE_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"人脸验证失败上报",30008),
  REMOTE_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"远程验证失败上报",30009),
  CPU_CARD_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"CPU卡验证失败上报",30010),
  QR_CODE_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"二维码验证失败上报",30011),
  FINGER_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"指纹验证失败上报",30012),
  PASSWORD_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"密码验证失败上报",30013),
  DYN_PASSWORD_OPEN_DOOR_SUCCESS_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"动态密码开门成功上报",30015),
  DYN_PASSWORD_OPEN_DOOR_FAIL_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"动态密码开门失败上报",30016),
  BLUETOOTH_OPEN_DOOR_SUCCESS(EventTypeEnum.COM_UPLOAD_EVENT,"蓝牙开门成功上报",30021),
  BLUETOOTH_OPEN_DOOR_FAIL(EventTypeEnum.COM_UPLOAD_EVENT,"蓝牙开门失败上报",30022),
  TAMPER_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"防拆报警",30300),
  OPEN_DOOR_TIMEOUT_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"门开超时报警（门开后未关门超时报警）",30301),
  OPEN_DOOR_ABNORMAL_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"异常开门报警",30302),
  DANGLE_AFTER_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"尾随报警",30303),
  DEVICE_FAILURE_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"设备故障报警",30304),
  COM_DOOR_GATE_CONTROL(EventTypeEnum.COM_GATE_CONTROL,"远程开门",30600),
  COM_DOOR_LOAD_CERTIFICATE(EventTypeEnum.COM_LOAD_CERTIFICATE,"权限下发",30601),
  COM_DOOR_DELETE_CERTIFICATE(EventTypeEnum.COM_DELETE_CERTIFICATE,"凭证删除",30603),
  COM_DOOR_LOAD_CERTIFICATE_IN_BATCH("COM_LOAD_CERTIFICATE_IN_BATCH","批量下发固定凭证信息",30604),

  //车场事件类型
  BURSH_CARD_OPEN_PARK_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"刷卡开闸事件上报",10001),
  CARNO_OPEN_PARK_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"车牌开闸事件上报",10002),
  REMOTE_OPEN_PARK_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"车场远程开闸事件上报",10003),
  MANUAL_OPEN_PARK_RECORD(EventTypeEnum.COM_UPLOAD_RECORD,"手动开闸事件上报",10004),
  COM_READ_CERTIFICATE_IN_BATCH("COM_READ_CERTIFICATE_IN_BATCH","批量读取固定凭证信息",10005),
  COM_DELETE_CERTIFICATE_IN_BATCH("COM_DELETE_CERTIFICATE_IN_BATCH","清除固定凭证信息",10006),
  COM_PARK_GATE_CONTROL(EventTypeEnum.COM_GATE_CONTROL,"开关闸",10007),
  COM_PLAY_VOICE("COM_PLAY_VOICE","报语音",10008),
  PAK_INTERCOM_CONTROL("PAK_INTERCOM_CONTROL","停车场对讲控制",10009),
  PAK_LED_DISPLAY("PAK_LED_DISPLAY","LED显示屏显示数据",10012),
  PAK_LOAD_LED_INFO("PAK_LOAD_LED_INFO","LED显示屏显示数据",10013),
  PAK_LOAD_LEFT_CAR_SEAT("PAK_LOAD_LEFT_CAR_SEAT","下载剩余车位数",10015),
  COM_SNAP_PICTURE("COM_SNAP_PICTURE","图片抓拍",10016),
  PAK_CONTROL_LOCK("PAK_CONTROL_LOCK","升降车位锁",10018),
  COM_PARK_LOAD_CERTIFICATE(EventTypeEnum.COM_LOAD_CERTIFICATE,"下发固定凭证信息",10019),
  COM_PARK_DELETE_CERTIFICATE(EventTypeEnum.COM_DELETE_CERTIFICATE,"删除固定凭证信息",10020),
  COM_PARK_READ_CERTIFICATE("COM_READ_CERTIFICATE","读取固定凭证信息",10021),
  COM_PARK_COM_LOAD_CERTIFICATE_IN_BATCH("COM_LOAD_CERTIFICATE_IN_BATCH","批量下发固定凭证信息",10022),

  //巡更事件类型
  PAT_UPLOAD_GPS("PAT_UPLOAD_GPS","上报GPS信息",70000),
// PAT_USER_LOGIN("PAT_USER_LOGIN","上报用户登录信息",70001),//作废
  PAT_VAL_TASK("PAT_VAL_TASK","校验派遣任务上报",70001),
  PAT_USER_LOGIN_STATUS("PAT_USER_LOGIN_STATUS","上报用户登录状态信息",70002),
  PAT_TASK_SYN("PAT_TASK_SYN","巡查任务统一上报",70003),
  PAT_GET_TASK_REQUEST("PAT_GET_TASK_REQUEST","获取任务信息",70004),
  PAT_UPLOAD_EMERGENCY("PAT_UPLOAD_EMERGENCY","上报应急事件",70005),
  PAT_TASK_STATUS("PAT_TASK_STATUS","上报巡查任务状态",70006),
  PAT_DEV_EXE_FEEDBACK("PAT_DEV_EXE_FEEDBACK","设备通用反馈上报",70007),//作废
  PAT_POINT_RESULT("PAT_POINT_RESULT","上报巡查点结果",70008),
  PAT_UPLOAD_SENDPERSON_FINISH("PAT_UPLOAD_SENDPERSON_FINISH","派遣人员完成上报",70009),
  PAT_SEND_MESSAGE("PAT_SEND_MESSAGE","发布消息到巡更设备",70010),//下发文本消息/派遣任务
  PAT_SEND_TASK("PAT_SEND_TASK","下发巡更任务",70012),
  PAT_EXE_FEEDBACK("PAT_EXE_FEEDBACK","下发执行反馈",70013),
  PAT_SEND_LOGOUT("PAT_SEND_LOGOUT","强制退出通知下发",70014),  // 组件下发强制退出通知
  PAT_SEND_VAL_RES("PAT_SEND_VAL_RES","校验派遣任务结果下发",70015),  // 组件下发校验派遣任务结果
  PAT_INTERCOM_CTRL("PAT_INTERCOM_CTRL","巡更对讲控制",70017),  // 巡更对讲控制
  PAT_INTERCOM_UPLOAD("PAT_INTERCOM_UPLOAD","巡更对讲记录上传",70018),  // 巡更对讲记录上传

  //广告屏事件类型
  ADS_TRANS_MATERIAL("ADS_TRANS_MATERIAL","透传素材属性",60000),
  ADS_ADD_PROGRAM("ADS_ADD_PROGRAM","新增节目",60001),
  ADS_SET_PROGRAM("ADS_SET_PROGRAM","修改节目",60002),
  ADS_DELETE_PROGRAM("ADS_DELETE_PROGRAM","删除节目",60003),
  ADS_ADD_SCHEDULE("ADS_ADD_SCHEDULE","新增日程",60004),
  ADS_SET_SCHEDULE("ADS_SET_SCHEDULE","修改日程",60005),
  ADS_DELETE_SCHEDULE("ADS_DELETE_SCHEDULE","删除日程",60006),
  ADS_PUBLISH_SCHEDULE("ADS_PUBLISH_SCHEDULE","发布日程",60007),

  //梯控事件类型
  FAC_VISIT_CONTROL("FAC_VISIT_CONTROL","电梯访客联动控制",50000),
  FAC_KEY_CONTROL("FAC_KEY_CONTROL","电梯按键权限控制",50001),
  FAC_CALLING("FAC_CALLING","外呼叫电梯控制",50002),
  FAC_STATUS_REQ("FAC_STATUS_REQ","电梯轿厢状态查询",50003),
  FAC_UPLOAD_DEV_STATUS("FAC_UPLOAD_DEV_STATUS","梯控设备状态上报",50004),
  FAC_COM_LOAD_CERTIFICATE(EventTypeEnum.COM_LOAD_CERTIFICATE,"下发固定凭证信息",50005),
  FAC_DELETE_CERTIFICATE(EventTypeEnum.COM_DELETE_CERTIFICATE,"删除固定凭证信息",50006),
  FAC_ELEVATOR_RECORD("FAC_ELEVATOR_RECORD","乘梯事件上报",50007),
  FAC_COM_LOAD_CERTIFICATE_IN_BATCH("COM_LOAD_CERTIFICATE_IN_BATCH","批量下发固定凭证信息",50008),
  FAC_LIFT_LIGHTING("FAC_LIFT_LIGHTING","电梯轿厢按键点亮控制",50009),
  FAC_DELAYED_CLOSING("FAC_DELAYED_CLOSING","电梯轿厢延迟关门控制",50010),
  FAC_INTER_CALL_AUTH("FAC_INTER_CALL_AUTH","内呼叫电梯授权控制",50015),
  FAC_INTER_CALL_LIGHTING("FAC_INTER_CALL_LIGHTING","内呼叫电梯点亮控制",50016),


  //电子车位控制器
  PAK_SEND_SHOWINFO("PAK_SEND_SHOWINFO","下发电子车牌显示屏显示信息",93002),

  //电子围栏
  ELETRIC_FENCE_UPLOAD_ALARM(EventTypeEnum.COM_UPLOAD_EVENT,"防区上报报警事件",91001),
  ELETRIC_FENCE_UPLOAD_ALARM_SETUP(EventTypeEnum.COM_UPLOAD_EVENT,"布防事件",91101),
  ELETRIC_FENCE_UPLOAD_ALARM_CANCEL(EventTypeEnum.COM_UPLOAD_EVENT,"撤防事件",91102),
  ELETRIC_FENCE_BOUN_CHAN_SETUPALARM("BOUN_CHAN_SETUPALARM","周界防区布防设置",91200),
  ELETRIC_FENCE_BOUN_CHAN_CANCELALARM("BOUN_CHAN_SETUPALARM","周界防区撤防设置",91201),
  ELETRIC_FENCE_BOUN_SUBCHAN_CLEARALARM("BOUN_SUBCHAN_CLEARALARM","周界防区消警",91202),
  BOUN_SUBCHAN_RECOVER("BOUN_SUBCHAN_RECOVER","防区恢复",91103),
  ELETRIC_FENCE_OFFLINE_RECOVER(EventTypeEnum.COM_UPLOAD_EVENT,"离线恢复",91104),
  ELETRIC_FENCE_BOUN_SUBCHAN_CLEARALARM_EVENT(EventTypeEnum.COM_UPLOAD_EVENT,"子系统消警事件",91100),
  ELETRIC_FENCE_BOUN_SUBCHAN_OFFLINEALARM_EVENT(EventTypeEnum.COM_UPLOAD_EVENT,"防区离线报警事件",91003),

  //公共事件类型
  COM_CORRECTION("COM_CORRECTION","时钟同步",4),
  COM_READ_TIME("COM_READ_TIME","读取时间",5),
  COM_SET_TIME("COM_SET_TIME","设置时间",6),
  COM_DEV_RESET("COM_DEV_RESET","设备恢复出厂",9),
  COM_QUERY_DIR("COM_QUERY_DIR","设备目录(子设备)查询",11),
  COM_READ_SYSTEM_VERSION("COM_READ_SYSTEM_VERSION","读取系统版本信息",12),

  COM_QUERY_DEV_STATUS("COM_QUERY_DEV_STATUS","设备状态查询",10010),
  COM_UPLOAD_DEV_STATUS("COM_UPLOAD_DEV_STATUS","设备状态上报",10011),
  COM_READ_VOL("COM_READ_VOL","读取音量参数",10019),
  COM_SET_VOL("COM_SET_VOL","设置音量",10020),

  COM_MANAGE_DEVICE_LIST("COM_MANAGE_DEVICE_LIST","设备列表维护(设备创建,删除,更新)",90000),
  COM_READ_DEVICE_LIST("COM_READ_DEVICE_LIST","解析设备列表(设备管理下发设备列表)",90001),
  COM_READ_PARAMETER("COM_READ_PARAMETER","读取设备参数",90002),
  COM_SETTING_PARAMETERS("COM_SETTING_PARAMETERS","设备参数下发",90003),
  COM_NOTIFY_UPDATE("COM_NOTIFY_UPDATE","通知设备升级",90004),
  // 90005 控制设备开关重启
  COM_DEV_REGISTER("COM_DEV_REGISTER","设备注册",90006),
  // 90007 设备注册信息上报
  // 90008 上报设备参数获取相应
  // 90009 上报设备参数配置响应
  COM_HEARTBEAT("COM_HEARTBEAT","设备心跳",90018),
  COM_UPLOAD_SUB_DEV("COM_UPLOAD_SUB_DEV","子设备信息上报",90011),
  COM_UPLOAD_ERROR_CERTIFICATE("COM_UPLOAD_ERROR_CERTIFICATE","凭证处理失败记录上报",90012),
  COM_UPGRADE_EXCEPTION(EventTypeEnum.COM_UPLOAD_EVENT,"设备升级异常",90013),
  // 90014 设备开关重启控制结果上报（注意： 之前定好的事件ID是90012， 但是设备一直不支持(未实现)该事件已被使用而改为90014占位等后面支持了使用）
  COM_RESTART_REPORT("COM_RESTART_REPORT","设备开关重启控制结果上报",90014),

  //充电桩事件类型 95000-95999
  CHARGE_QUERY_SUBDEVICE("COM_QUERY_SUBDEVICE", "查询子设备", 95000),
  CHARGE_START_CHARGING("COM_START_CHARGING", "启动充电", 95001),
  CHARGE_STOP_CHARGING("COM_STOP_CHARGING", "停止充电", 95002),
  CHARGE_RESUME_CHARGING("COM_RESUME_CHARGING", "重启充电", 95005),
  CHARGE_PAUSE_CHARGING("COM_PAUSE_CHARGING", "暂停充电", 95006),
  CHARGE_START_CHARGING_MONITOR("COM_START_CHARGING_MONITOR", "启动充电", 95001),  //调试模拟器用
  CHARGE_STOP_CHARGING_MONITOR("COM_STOP_CHARGING_MONITOR", "停止充电", 95002),    //调试模拟器用
  CHARGE_QUERY_DEVICE_STATUS("COM_QUERY_DEVICE_STATUS", "查询设备状态", 95003),
  CHARGE_QUERY_VOLTAGE_CURRENT("CHARGE_QUERY_VOLTAGE_CURRENT", "查询电表实时电压电流", 95004),
  //95000
  CHARGE_UPLOAD_SUBDEVICE("COM_UPLOAD_SUBDEVICE", "子设备上报", 95100),
  CHARGE_UPLOAD_START_RESULT("COM_UPLOAD_START_RESULT", "上报启动充电结果", 95001),
  CHARGE_UPLOAD_STOP_RESULT("COM_UPLOAD_STOP_RESULT", "上报停止充电结果", 95002),
  CHARGE_UPLOAD_RESUME_RESULT("COM_UPLOAD_RESUME_RESULT", "上报重启充电结果", 95005),
  CHARGE_UPLOAD_PAUSE_RESULT("COM_UPLOAD_PAUSE_RESULT", "上报暂停充电结果", 95006),
  CHARGE_UPLOAD_DEVICE_ONLINE("COM_UPLOAD_DEVICE_ONLINE", "设备上线", 95103),
//  CHARGE_UPLOAD_HEARTBEAT_STATUS("COM_HEARTBEAT_STATUS", "心跳异常上报", 95110),
  CHARGE_UPLOAD_PLUG_STATUS("COM_UPLOAD_PLUG_STATUS", "上报充电枪状态", 95003),
//  CHARGE_UPLOAD_CHARGING_DATA("COM_UPLOAD_CHARGING_DATA", "上报充电枪充电计量数据", 95121),
  CHARGE_UPLOAD_DEVICE_STATUS("COM_UPLOAD_DEVICE_STATUS", "设备状态上报", 95122),
  CHARGE_UPLOAD_EXCEPTION("COM_UPLOAD_EXCEPTION", "异常上报", 95130),
  CHARGE_UPLOAD_EVENT("COM_UPLOAD_EVENT", "事件上报", 95131),
  CHARGE_UPLOAD_VOLTAGE_CURRENT("COM_UPLOAD_VOLTAGE_CURRENT", "上报变压器实时电压、电流、功率（智能电表/集中器）", 95200),
  COM_REQUEST_QR_CODE_AGAIN("COM_REQUEST_QR_CODE_AGAIN", "二次请求序列号", 95201),
  CHARGE_COM_PAY_RULE("COM_PAY_RULE", "下发结算计费规则", 95011),
  CHARGE_COM_CLOUD_STATUS_SYNC("COM_CLOUD_STATUS_SYNC", "小区状态同步", 95010),

  //设备的事件
  COM_START_CHARGE("COM_START_CHARGE","开始充电",10000),
  COM_STOP_CHARGE("COM_STOP_CHARGE","停止充电",10001),
  COM_POWER_CONTROL("COM_POWER_CONTROL","功率控制",10002),
  COM_UPLOAD_START_RESULT("COM_UPLOAD_START_RESULT","开始充电结果上报",10003),
  COM_UPLOAD_STOP_RESULT("COM_UPLOAD_STOP_RESULT","停止充电结果上报",10004),
  COM_DEV_STATUS("COM_DEV_STATUS","查询设备状态",10005),
  COM_SET_LOCK("COM_SET_LOCK","电子锁开关",10006),
  COM_CHARGE_UPLOAD_EVENT("COM_UPLOAD_EVENT","插座实时数据上报/事件上报",95121),
  COM_SET_QR_CODE("COM_SET_QR_CODE","设置序列号",95121);

  //命令
  String command;
  // 名称
  String name;
  // 编号
  Integer no;
  private static final String COM_UPLOAD_RECORD = "COM_UPLOAD_RECORD";
  private static final String COM_UPLOAD_EVENT = "COM_UPLOAD_EVENT";
  private static final String COM_LOAD_CERTIFICATE = "COM_LOAD_CERTIFICATE";
  private static final String COM_DELETE_CERTIFICATE = "COM_DELETE_CERTIFICATE";
  private static final String COM_GATE_CONTROL = "COM_GATE_CONTROL";
//  private static final String COM_OFFLINE_RECOVERY = "COM_OFFLINE_RECOVERY";
  /**
   * 构造方法.
   *
   * @param command required
   * @param name required
   * @param no required
   */
  private EventTypeEnum(String command, String name, Integer no) {
    this.command = command;
    this.name = name;
    this.no = no;
  }

  public String getCommand() {
    return command;
  }

  public String getName() {
    return name;
  }

  public Integer getNo() {
    return no;
  }

  @Override
  public String toString() {
    return "EventTypeEnum [command=" + command +
        ", name=" + name +
        ", no=" + no +
        "]";
  }


  /**
   * 通过名称拿到编号 String.
   *
   * @param name required
   * @return
   */
  public static Integer getNoByName(String name) {
    // 如果对象为空直接返回
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      // 当传入名称和枚举的名称相等
      if (StringUtils.trim(name).equals(temp.getName())) {
        return temp.getNo();
      }
    }
    return null;
  }


  /**
   * 通过编号拿到名称 String.
   *
   * @param no required
   * @return
   */
  public static String getNameByNo(Integer no) {
    // 如果对象为空直接返回
    if (no == null) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      // 当传入号码和枚举的号码相等
      if (temp.getNo().equals(no)) {
        return temp.getName();
      }
    }
    return null;
  }

  /**
   * 通过编号拿到命令 String.
   *
   * @param no required
   * @return
   */
  public static String getCommandByNo(Integer no) {
    if (no == null) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      if (temp.getNo().equals(no)) {
        return temp.getCommand();
      }
    }
    return null;
  }

  /**
   * 通过命令拿到编号 String.
   *
   * @param command required
   * @return
   */
  public static Integer getNoByCommand(String command) {
    // 如果对象为空直接返回
    if (StringUtils.isEmpty(command)) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      // 当传入命令和枚举的命令相等
      if (StringUtils.trim(command).equals(temp.getCommand())) {
        return temp.getNo();
      }
    }
    return null;
  }

  /**
   * 通过命令拿到枚举 String.
   *
   * @param command required
   * @return
   */
  public static EventTypeEnum getEnumByCommand(String command) {
    // 如果传入的命令为空，则直接返回null
    if (StringUtils.isEmpty(command)) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      // 当传入命令和枚举的命令相等
      if (temp.getCommand().equals(command.trim())) {
        return temp;
      }
    }
    return null;
  }


  /**
   * 通过编号拿到枚举 String.
   *
   * @param no required
   * @return
   */
  public static EventTypeEnum getEnumByNo(Integer no) {
    // 如果对象为空直接返回
    if (no == null) {
      return null;
    }
    EventTypeEnum[] types = EventTypeEnum.values();
    // 遍历枚举
    for (EventTypeEnum temp : types) {
      // 当传入命令和枚举的命令相等
      if (temp.getNo().equals(no)) {
        return temp;
      }
    }
    return null;
  }
}
