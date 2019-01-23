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
 * 客户端 (入门级 -- 简单的例子)
 * @author zxy-un
 * 
 * 2018-下午5:34:12
 */
public class TimeClient {
	
	/**
	 * 连接
	 * @author zxy-un
	 * 
	 * @param port
	 * @param host
	 * @throws Exception
	 * 
	 * 下午5:44:02
	 */
	public void connect(int port,String host)throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup(); // 连接池
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class) // 将连接池放入管道  (channel: 管道)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch)throws Exception
				{
					ch.pipeline().addLast(new TimeClientHandler());
				}
			});
			// 发起异步连接操作
			ChannelFuture f = b.connect(host,port).sync();
			
			//等待客户端链路关闭
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
	 * 下午5:37:49
	 */
	public static void main(String[] args)throws Exception {
		new TimeClient().connect(8080, "127.0.0.1");
	}
}
