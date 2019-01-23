package com.netty3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理通道
 * 模拟粘包/拆包故障场景
 * @author zxy-un
 * 
 * 2018-下午8:43:38
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8").substring(0,req.length - System.getProperty("line.separator").length());
		System.out.println("===== :" +body + "; ----- : "+ ++counter);
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
		ctx.close();
	}
}
