package com.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端发送
 * @author zxy-un
 * 
 * 2018-下午5:38:03
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	private final ByteBuf firstMessage;
	
	/**
	 * 构造方法
	 * @author zxy-un
	 * 
	 * 下午5:40:51
	 */
	public TimeClientHandler()
	{
		byte[] req = "==========================".getBytes();
		firstMessage = Unpooled.buffer(req.length); // buffer: 缓冲区,实际上是一个容器,是一个连续数组
		firstMessage.writeBytes(req);
	}
	
	public void channelActive(ChannelHandlerContext ctx)
	{
		ctx.writeAndFlush(firstMessage);
	}
	
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is : " + body);
	}
	
	/**
	 * 异常方法
	 */
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		ctx.close();
	}
}
