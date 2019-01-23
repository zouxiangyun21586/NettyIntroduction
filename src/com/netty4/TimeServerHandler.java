package com.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端通道
 * 解决粘包,半包问题 -- 换行符方式
 * 
 * @author zxy-un
 * 
 * 2018-上午9:13:15
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		String body = (String)msg;
//		System.out.println(body + "----" + ++counter);
//		System.out.println("======================"+body);
		System.out.println("The teim server reveive order :" +body + "; the counter is : "+ ++counter);
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : " BAD ORDER";
		currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
		
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception
	{
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		System.out.println(cause.getMessage()+"----------");
		ctx.close();
	}
}
