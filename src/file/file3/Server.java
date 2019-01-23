package file.file3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

	public Server() {
	}

	public void bind(int port) throws Exception {
		// ����NIO�߳���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// ��������������������
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChildChannelHandler())//
					.option(ChannelOption.SO_BACKLOG, 1024) // ����tcp������ // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			// �󶨶˿� ͬ���ȴ��󶨳ɹ�
			ChannelFuture f = b.bind(port).sync(); // (7)
			// �ȵ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
		} finally {
			// �����ͷ��߳���Դ
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	/**
	 * �����¼�������
	 */
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// ���Jboss�����л�������빤��
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			// ��������IO
			ch.pipeline().addLast(new ServerHandler());
		}
	}

	public static void main(String[] args) throws Exception {
		new Server().bind(9999);
	}
}
