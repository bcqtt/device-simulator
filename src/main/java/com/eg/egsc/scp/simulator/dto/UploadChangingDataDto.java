package com.eg.egsc.scp.simulator.dto;

import lombok.Data;

import java.util.List;

/**
 * 上报充电枪充电计量数据
 *
 * @Class Name UploadChangingDataDto
 * @Author Administrator
 * @Create In 2018年10月11日
 */
@Data
public class UploadChangingDataDto {

    /**
     * 类型ID
     */
    private Integer type;

    /**
     * 是否正在充电，1表示正在充电，0表示未充电
     */
    private Integer isCharging;

    /**
     * 会话ID
     */
    private String orderNumber;

    /**
     * 本次充电开始时间，若非充电状态该字段为空
     */
    private String startTime;

    /**
     * 累计充电时间，单位（秒）
     */
    private Integer duarationTime;

    /**
     * 充电前的电量值（精度0.1度）
     */
    private Integer startQuantity;

    /**
     * 已充电量（精度0.1度）
     */
    private Integer usedQuantity;

    /**
     * 输出电压（精度0.1V）
     */
    private Integer voltageOut;

    /**
     * 电流（精度0.001A）
     */
    private Integer current;

    /**
     * 当前输出功率（精度0.1瓦）
     */
    private Integer power;
    /**
     * 3孔连接状态，0表示未连接，1表示连接但是未供电，2表示正在供电中
     */
    private Integer switch3Status;

    /**
     * 7孔连接状态，
     * 0表示未连接，
     * 1表示插座、枪已连接好，
     * 2表示插座、枪、车已经连接好但是未供电，
     * 3表示插座、枪、车已经连接好且已经给车发送开始充电指令，
     * 4表示插座、枪、车已经连接好且正在充电，当状态发送变化时立即上报
     */
    private Integer switch7Status;

    /**
     * 3孔插座的锁状态，0表示未上锁，1表示已上锁
     */
    private Integer lock3Status;

    /**
     * 7孔插座的锁状态，0表示未上锁，1表示已上锁
     */
    private Integer lock7Status;

    /**
     * 0：非急停状态，1，急停状态
     */
    private Integer urgentStatus;

    /**
     * 智能插座设备状态：设备故障（正常、过压、欠压、过流、欠流、漏电）
     */
    private Integer devStatus;

    /**
     * 已充电量列表(精度:0.001度，如：[0.3,0.4,0]表示不同分段的电量)
     */
    private List<Integer> usedQuantityList;

    /**
     * 计费规则id
     */
    private String ruleId;

    /**
     * 工作状态
     * 0：充电枪故障，1：未插枪，2：已插枪未充电，3：充电启动中，4：充电中
     */
    private int switchStatus;

}
