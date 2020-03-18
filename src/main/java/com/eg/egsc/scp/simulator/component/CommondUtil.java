package com.eg.egsc.scp.simulator.component;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import com.eg.egsc.scp.simulator.dto.ProtocolBody;
import com.eg.egsc.scp.simulator.factory.SimpleMessageFactory;
import com.eg.egsc.scp.simulator.util.DateUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.tomcat.jni.Local;

import java.util.Date;

public class CommondUtil {

    /**
     * 响应网关执行结果
     * @param deviceCode
     * @param ctx
     */
    public static void responseGateway(String deviceCode, ChannelHandlerContext ctx) {
        ProtocolBody msgBody =  SimpleMessageFactory.createMessage(deviceCode, EventTypeEnum.CHARGE_COM_PAY_RULE);
        ctx.writeAndFlush(msgBody);
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
}
