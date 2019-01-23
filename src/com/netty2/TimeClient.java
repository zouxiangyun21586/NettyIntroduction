package com.netty2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * �ͻ��� (���ż� -- �򵥵�����)
 * @author zxy-un
 * 
 * 2018-����5:34:12
 */
public class TimeClient {
	
	/**
	 * ����
	 * @author zxy-un
	 * 
	 * @param port
	 * @param host
	 * @throws Exception
	 * 
	 * ����5:44:02
	 */
	public void connect(int port,String host)throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup(); // ���ӳ�
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class) // �����ӳط���ܵ�  (channel: �ܵ�)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch)throws Exception
				{
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			// �����첽���Ӳ���
			ChannelFuture f = b.connect(host,port).sync();
			
			//�ȴ��ͻ�����·�ر�
			f.channel().closeFuture().sync();
			
		}finally
		{
			group.shutdownGracefully();
		}
	}
	
	/**
	 * 
	 * @author zxy-un
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 * ����5:37:49
	 */
	public static void main(String[] args)throws Exception {
		new TimeClient().connect(8080, "127.0.0.1");
	}
}
