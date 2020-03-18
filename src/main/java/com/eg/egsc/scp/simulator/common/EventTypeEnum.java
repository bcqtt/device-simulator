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

    // 公共事件类型
    COM_REQUEST_TIME("COM_REQUEST_TIME", "请求时间", 3),
    //COM_CORRECTION("COM_CORRECTION", "时钟同步", 4),
    COM_READ_TIME("COM_READ_TIME", "读取时间", 5),
    COM_SET_TIME("COM_SET_TIME", "设置时间", 6),
    //COM_DEV_RESET("COM_DEV_RESET", "设备恢复出厂", 9),
    COM_QUERY_DIR("COM_QUERY_DIR", "设备目录(子设备)查询", 11),
    COM_READ_SYSTEM_VERSION("COM_READ_SYSTEM_VERSION", "读取系统版本信息", 12),
    COM_NOTIFY_UPLOADLOG("COM_NOTIFY_UPLOADLOG", "通知设备日志上报", 13),

    //COM_QUERY_DEV_STATUS("COM_QUERY_DEV_STATUS", "设备状态查询", 10010),
    COM_UPLOAD_DEV_STATUS("COM_UPLOAD_DEV_STATUS", "上报设备的状态", 10011),
    //COM_READ_VOL("COM_READ_VOL", "读取音量参数", 10019),
    //COM_SET_VOL("COM_SET_VOL", "设置音量", 10020),

    COM_MANAGE_DEVICE_LIST("COM_MANAGE_DEVICE_LIST", "设备列表维护(设备创建,删除,更新)", 90000),
    COM_READ_DEVICE_LIST("COM_READ_DEVICE_LIST", "解析设备列表(设备管理下发设备列表)", 90001),
    COM_READ_PARAMETER("COM_READ_PARAMETER", "读取设备参数", 90002),
    COM_SETTING_PARAMETERS("COM_SETTING_PARAMETERS", "设备参数下发", 90003),
    COM_NOTIFY_UPDATE("COM_NOTIFY_UPDATE", "通知设备升级", 90004),
    // 90005 控制设备开关重启
    COM_DEV_REGISTER("COM_DEV_REGISTER", "设备注册", 90006),
    // 90007 设备注册信息上报
    // 90008 上报设备参数获取相应
    // 90009 上报设备参数配置响应
    COM_HEARTBEAT("COM_HEARTBEAT", "设备心跳", 90018),
    COM_UPLOAD_SUB_DEV("COM_UPLOAD_SUB_DEV", "子设备信息上报", 90011),
    COM_DEV_UPGRADING("COM_DEV_UPGRADING", "固件升级中状态上报", 90011),
    COM_UPLOAD_ERROR_CERTIFICATE("COM_UPLOAD_ERROR_CERTIFICATE", "凭证处理失败记录上报", 90012),
    COM_UPGRADE_EXCEPTION("COM_UPGRADE_EXCEPTION", "设备升级异常", 90013),
    // 90014 设备开关重启控制结果上报（注意： 之前定好的事件ID是90012，
    // 但是设备一直不支持(未实现)该事件已被使用而改为90014占位等后面支持了使用）
    COM_RESTART_REPORT("COM_RESTART_REPORT", "设备开关重启控制结果上报", 90014),
    COM_UPGRADE_REPORT("COM_UPGRADE_REPORT", "升级状态上报", 90015),
    COM_UPLOAD_UPLOADLOG_RESULT("COM_UPLOAD_UPLOADLOG_RESULT", "日志上上传结果上报", 90016),

    // 充电桩事件类型 95000-95999 下行
//  CHARGE_COM_QUERY_SUBDEVICE("COM_QUERY_DIR", "查询子设备", 95000),
    CHARGE_COM_START_CHARGE("COM_START_CHARGE", "启动充电", 95001),
    CHARGE_COM_STOP_CHARGE("COM_STOP_CHARGE", "停止充电", 95002),
    CHARGE_COM_DEV_STATUS("COM_DEV_STATUS", "查询设备状态", 95003),
    CHARGE_COM_POWER_CONTROL("COM_POWER_CONTROL", "功率控制", 95005),
    CHARGE_COM_SET_LOCK("COM_SET_LOCK", "电子锁开关控制", 95006),
    CHARGE_COM_REQ_REAL_DATA("COM_REQ_REAL_DATA", "查询充电实时数据", 95007),
    CHARGE_COM_SET_FORBIDDEN("COM_SET_FORBIDDEN", "启用/禁用充电插座", 95008),
    CHARGE_COM_SET_QR_CODE("COM_SET_QR_CODE", "设置二维码/序列号", 95009),
    CHARGE_COM_CLOUD_STATUS_SYNC("COM_CLOUD_STATUS_SYNC", "小区状态同步", 95010),
    CHARGE_COM_PAY_RULE("COM_PAY_RULE", "下发结算计费规则", 95011),

    CHARGE_COM_QUERY_METER_DATA("COM_QUERY_VOLTAGE_CURRENT", "查询变压器实时电压、电流、功率（智能电表/集中器）", 95200),
    COM_REQUEST_METER_KWH("COM_REQUEST_METER_KWH", "请求当前正向有功总电能", 95202),

    // 95000 上行
    //CHARGE_UPLOAD_SUBDEVICE("COM_UPLOAD_SUBDEVICE", "子设备上报", 95100),
    CHARGE_UPLOAD_START_RESULT("COM_UPLOAD_START_RESULT", "上报启动充电结果", 95001),
    CHARGE_UPLOAD_STOP_RESULT("COM_UPLOAD_STOP_RESULT", "上报停止充电结果", 95002),
    //CHARGE_UPLOAD_PAUSE_RESULT("COM_UPLOAD_PAUSE_RESULT", "暂停充电结果", 95006),
    //CHARGE_UPLOAD_DEVICE_ONLINE("COM_UPLOAD_DEVICE_ONLINE", "设备上线", 95103),
    CHARGE_UPLOAD_STOP_EVENT("COM_UPLOAD_STOP_EVENT", "停止充电事件上报", 95132),
    CHARGE_UPLOAD_EVENT("COM_UPLOAD_EVENT", "事件上报", 95121),
    //CHARGE_UPLOAD_DEVICE_STATUS("COM_UPLOAD_DEVICE_STATUS", "设备状态上报", 95122),
    CHARGE_NETWORK_BREAK_EVENT("CHARGE_NETWORK_BREAK_EVENT", "网络中断事件上报", 95123),
    //COM_RECORD_UPLOAD("COM_RECORD_UPLOAD", "网络中断事件上报", 95124),
    //CHARGE_UPLOAD_EXCEPTION("COM_UPLOAD_EXCEPTION", "异常上报", 95130),
    CHARGE_UPLOAD_VOLTAGE_CURRENT("COM_UPLOAD_VOLTAGE_CURRENT", "上报变压器实时电压、电流、功率（智能电表/集中器）", 95200),
    COM_REQUEST_QR_CODE("COM_REQUEST_QR_CODE", "二次请求序列号", 95201);
    //CHARGE_START_RESPONSE("CHARGE_START_RESPONSE", "响应启动充电/预约充电", 95210)

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
     * @param name    required
     * @param no      required
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
