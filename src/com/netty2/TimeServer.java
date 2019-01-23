package com.netty2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * �����
 * @author zxy-un
 * 
 * 2018-����5:38:24
 */
public class TimeServer {
	
	/**
	 * �� 
	 * @author zxy-un
	 * 
	 * @param port
	 * @throws Exception
	 * 
	 * ����5:41:13
	 */
	public void bind(int port) throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // �첽�̳߳�(���ս���������)
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // �첽�̳߳�(�����Ѿ������յ�����)
		try {
			ServerBootstrap b = new ServerBootstrap(); // ��һ������NIO����ĸ���������
			b.group(bossGroup, workerGroup)  // ��ӹ����߳���
			 .channel(NioServerSocketChannel.class)   // ���ùܵ�ģʽ (ServerSocketChannel��NIO��selectorΪ��������ʵ�ֵ�,���������µ�����)
			 .option(ChannelOption.SO_BACKLOG, 1024)  // ����BLOCK��С,����ָ����ͨ��ʵ�ֵ����ò���(option()���ṩ��NioServerSocketChannel�������ս���������)
			 .childHandler(new ChildChannelHandler());// �����Լ��������
			
			ChannelFuture f = b.bind(port).sync(); // �󶨶˿ڲ�����ȥ���ս���������
			f.channel().closeFuture().sync(); // �����һֱ�ȴ�,ֱ��socket���ر�
		}finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	/**
	 * 
	 * @author zxy-un
	 * 
	 * 2018-����5:41:28
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	
	/**
	 * ִ�з���
	 * @author zxy-un
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 * ����5:41:32
	 */
	public static void main(String[] args)throws Exception {
		new TimeServer().bind(8080);
	}
}
