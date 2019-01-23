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
 * �ͻ���
 * ���ճ��,������� -- ���з���ʽ
 * 
 * @author zxy-un
 * 
 * 2018-����9:09:34
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
					// LineBasedFrameDecoder�Ĺ���ԭ���������α���ByteBuf�еĿɶ��ֽ�,�жϿ��Ƿ���"\n"���ߡ�\r\n��,�����,���Դ�λ��Ϊ����λ��
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024)); // ���յ����Ķ���ת�����ַ�����Ȼ��������ú����handler
					ch.pipeline().addLast(new StringDecoder());
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
	public static void main(String[] args)throws Exception {
		new TimeClient().connect(8080, "127.0.0.1");
	}
}
