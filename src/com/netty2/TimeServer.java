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
 * 服务端
 * @author zxy-un
 * 
 * 2018-下午5:38:24
 */
public class TimeServer {
	
	/**
	 * 绑定 
	 * @author zxy-un
	 * 
	 * @param port
	 * @throws Exception
	 * 
	 * 下午5:41:13
	 */
	public void bind(int port) throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 异步线程池(接收进来的连接)
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // 异步线程池(处理已经被接收的连接)
		try {
			ServerBootstrap b = new ServerBootstrap(); // 是一个启动NIO服务的辅助启动类
			b.group(bossGroup, workerGroup)  // 添加工作线程组
			 .channel(NioServerSocketChannel.class)   // 设置管道模式 (ServerSocketChannel以NIO的selector为基础进行实现的,用来接收新的连接)
			 .option(ChannelOption.SO_BACKLOG, 1024)  // 配置BLOCK大小,设置指定的通道实现的配置参数(option()是提供给NioServerSocketChannel用来接收进来的连接)
			 .childHandler(new ChildChannelHandler());// 我们自己处理的类
			
			ChannelFuture f = b.bind(port).sync(); // 绑定端口并启动去接收进来的连接
			f.channel().closeFuture().sync(); // 这里会一直等待,直到socket被关闭
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
	 * 2018-下午5:41:28
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new TimeServerHandler());
		}
		
	}
	
	/**
	 * 执行方法
	 * @author zxy-un
	 * 
	 * @param args
	 * @throws Exception
	 * 
	 * 下午5:41:32
	 */
	public static void main(String[] args)throws Exception {
		new TimeServer().bind(8080);
	}
}
