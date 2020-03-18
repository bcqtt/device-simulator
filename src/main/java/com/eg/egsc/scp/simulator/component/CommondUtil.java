package com.eg.egsc.scp.simulator.component;

import com.eg.egsc.scp.simulator.common.EventTypeEnum;
import com.eg.egsc.scp.simulator.common.RequestMessageManager;
import io.netty.channel.ChannelHandlerContext;

public class CommondUtil {

    /**
     * 响应网关执行结果
     * @param chargeComPayRule
     * @param ctx
     */
    public static void responseGateway(EventTypeEnum chargeComPayRule, ChannelHandlerContext ctx) {
        RequestMessageManager.writeAndFlush(chargeComPayRule.getCommand(), ctx, "1",null);
    }

    /**
     * 响应查询设备状态
     * @param chargeComDevStatus
     * @param ctx
     * @param s
     * @param o
     */
    public static void responseDevStatus(EventTypeEnum chargeComDevStatus, ChannelHandlerContext ctx, String s, Object o) {
        RequestMessageManager.writeAndFlush(chargeComDevStatus.getCommand(), ctx, "1",null);
    }
}
