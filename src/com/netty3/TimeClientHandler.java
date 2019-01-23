package com.netty3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端发送
 * 模拟粘包/拆包故障场景
 * @author zxy-un
 * 
 * 2018-下午8:43:23
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	private int counter;
	
	private byte[] req;
	
	public TimeClientHandler()
	{
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	}
	
	public void channelActive(ChannelHandlerContext ctx)
	{
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}
	}
	
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is : " + body +" ; the counter is :" + ++counter);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		ctx.close();
	}
}
