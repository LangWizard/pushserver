package com.gspzone.pushserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handles a server-side channel.
 */
public class PushServerHandler extends ChannelInboundHandlerAdapter { // (1)
	/**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接开始...");
        AllChannel.group.add(ctx.channel());
    }
 
    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接关闭...");
        AllChannel.group.remove(ctx.channel());
    }
 
    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("信息接收完毕...");
    }
 
    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        System.out.println("客户端连接断开了");
        ctx.close();
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msginfo) {
        MsgInfo mi = (MsgInfo) msginfo;
        System.out.println("msg:"+mi.getInfo());
        System.out.println("Yes, A new client in = " + ctx.name());
    }

}
