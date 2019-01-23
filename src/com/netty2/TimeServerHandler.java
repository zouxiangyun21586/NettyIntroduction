package com.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理通道
 * @author zxy-un
 * 
 * 2018-下午5:42:00
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 接收方法(事件处理方法???  --- 每当从客户端收到新的数据时， 这个方法会在收到消息时被调用)
	 */
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		
		ByteBuf buf = (ByteBuf)msg; // ByteBuf是一个引用计数对象
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("The time server receive order : " + body);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}
	
	/**
	 * 处理方法
	 */
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception
	{
		ctx.flush();
	}
	
	/**
	 * 异常方法
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		System.out.println("server3");
		ctx.close();
	}
}
