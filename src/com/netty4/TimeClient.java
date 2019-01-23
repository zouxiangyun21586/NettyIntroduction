package com.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 客户端
 * 解决粘包,半包问题 -- 换行符方式
 * 
 * @author zxy-un
 * 
 * 2018-上午9:09:34
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
					// LineBasedFrameDecoder的工作原理是它依次遍历ByteBuf中的可读字节,判断看是否有"\n"或者“\r\n”,如果有,就以此位置为结束位置
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024)); // 将收到到的对象转换成字符串，然后继续调用后面的handler
					ch.pipeline().addLast(new StringDecoder());
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
