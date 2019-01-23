package com.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * @author zxy-un
 * 
 * 2018-下午8:44:18
 */
public class TimeClient {
	public void connect(int port,String host)throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch)throws Exception
				{
					// 模拟粘包/拆包故障场景
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
	public static void main(String[] args)throws Exception {
		new TimeClient().connect(8080, "127.0.0.1");
	}
}
