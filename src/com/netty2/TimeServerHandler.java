package com.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ����˴���ͨ��
 * @author zxy-un
 * 
 * 2018-����5:42:00
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * ���շ���(�¼�������???  --- ÿ���ӿͻ����յ��µ�����ʱ�� ������������յ���Ϣʱ������)
	 */
	public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception
	{
		
		ByteBuf buf = (ByteBuf)msg; // ByteBuf��һ�����ü�������
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("The time server receive order : " + body);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}
	
	/**
	 * ������
	 */
	public void channelReadComplete(ChannelHandlerContext ctx)throws Exception
	{
		ctx.flush();
	}
	
	/**
	 * �쳣����
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		System.out.println("server3");
		ctx.close();
	}
}
