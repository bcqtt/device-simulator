package com.eg.egsc.scp.simulator.component;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.factory.SimpleMessageFactory;
import com.eg.egsc.scp.simulator.util.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;

import java.util.Date;

@Slf4j
public class CommondUtil {

    /**
     * 响应网关执行结果
     * @param deviceCode
     * @param ctx
     */
    public static void responsePayRule(String deviceCode, ChannelHandlerContext ctx) {
        ProtocolBody msgBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_COM_PAY_RULE);
        ctx.writeAndFlush(msgBody);
        //log.info("响应网关[{}]的[{}]消息: {}",deviceCode,EventTypeEnum.CHARGE_COM_PAY_RULE.getCommand(),msgBody);
    }

    /**
     * 查询设备状态响应
     * @param deviceCode
     * @param chargeComDevStatus
     */
    public static void responseDevStatus(String deviceCode, EventTypeEnum chargeComDevStatus,ChannelHandlerContext ctx) {
        ProtocolBody msgBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_COM_DEV_STATUS);
        ctx.writeAndFlush(msgBody);
    }

    /**
     * 响应启动充电请求，上报启动充电结果
     * @param ctx
     */
    public static void responseStartCharge(String deviceCode, ChannelHandlerContext ctx,String orderNumber) {
        String startTime = DateUtils.formatDate2(new Date());
        LocalStore.getInstance().getOrderMap().put(deviceCode,orderNumber);
        LocalStore.getInstance().getStartTimeMap().put(deviceCode,startTime);
        //响应启动充电请求
        ProtocolBody responseBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_COM_START_CHARGE);
        ctx.writeAndFlush(responseBody);
        //上报启动充电结果
        ProtocolBody resultBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_UPLOAD_START_RESULT);
        ctx.writeAndFlush(resultBody);
    }

    /**
     * 响应启动充电请求，上报启动充电结果
     * @param ctx
     */
    public static void responseStopCharge(String deviceCode, ChannelHandlerContext ctx) {
        String endTime = DateUtils.formatDate2(new Date());
        //响应启动充电请求
        ProtocolBody responseBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_COM_STOP_CHARGE);
        ctx.writeAndFlush(responseBody);
        //上报启动充电结果
        ProtocolBody resultBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_UPLOAD_STOP_RESULT);
        ctx.writeAndFlush(resultBody);

        //移除缓存
        LocalStore.getInstance().getOrderMap().remove(deviceCode);
        LocalStore.getInstance().getStartTimeMap().remove(deviceCode);
        LocalStore.getInstance().stopCharge(deviceCode);
    }

    public static void responseRealData(String deviceCode, EventTypeEnum chargeComReqRealData, ChannelHandlerContext ctx) {
        ProtocolBody responseBody =  SimpleMessageFactory.createMessage(deviceCode, chargeComReqRealData);
        ctx.writeAndFlush(responseBody);
    }
}
